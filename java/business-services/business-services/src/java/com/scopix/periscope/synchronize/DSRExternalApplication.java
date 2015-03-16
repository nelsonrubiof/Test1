/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  DSRExternalApplication.java
 * 
 *  Created on 03-03-2014, 11:32:05 AM
 * 
 */
package com.scopix.periscope.synchronize;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.reporting.commands.DateOfEvidenceCommand;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.webservices.dsrevaluation.client.EvaluationDSRWebServicesClient;
import com.scopix.periscope.webservices.dsrevaluation.dto.EvaluationDSRContainerDTO;
import com.scopix.periscope.webservices.dsrevaluation.dto.EvaluationDSRDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Nelson
 * @version 1.0.0
 */
@SpringBean(rootClass = DSRExternalApplication.class)
@Transactional(rollbackFor = {ScopixException.class})
public class DSRExternalApplication implements ExternalApplication {

    public static final String DATE_FORMAT_DSR = "dd-MM-yyyy HH:mm:ss";
    private static Logger log = Logger.getLogger(DSRExternalApplication.class);
    WebClient client;
    private PropertiesConfiguration configuration;
    private GetObservedSituationCommand observedSituationCommand;
    private DateOfEvidenceCommand dateOfEvidenceCommand;

    @Override
    public void sendData(int observedSituationId) throws ScopixException {
        //cargamos la data de reporting data asociada al observed situation 
        log.info("start");

        //recuperamos el contenido del observedSituation al igual que lo hace reporting
        List<EvaluationDSRDTO> list = getDataByObservedSituationId(observedSituationId);
        if (!list.isEmpty()) {

            //Data
            EvaluationDSRContainerDTO data = new EvaluationDSRContainerDTO();

//            EvaluationDSRDTO evaldto = new EvaluationDSRDTO();
//            list.add(evaldto);
            data.setEvaluations(list);
            log.debug("list.size " + list.size());

            Response response = getClient().post(data);
            log.debug("Status response " + response.getStatus());
            log.debug("response " + response.getEntity());
        }
        log.info("end");
    }

    private WebClient getClient() throws ScopixException {
        if (client == null) {
            client = new EvaluationDSRWebServicesClient().getWebClient();
            client.path("/sendDSREvaluations").accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
        }
        return client;
    }

    /**
     * @return the configuration
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public PropertiesConfiguration getConfiguration() throws ScopixException {
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("system.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("Error obteniendo configuration" + e, e);
                throw new ScopixException(e);
            }
        }
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }

    private List<EvaluationDSRDTO> getDataByObservedSituationId(int observedSituationId) throws ScopixException {
        log.info("start " + observedSituationId);

        ObservedSituation observedSituation = getObservedSituationCommand().execute(observedSituationId);
        observedSituation.getObservedSituationEvaluations().isEmpty();
        observedSituation.getIndicatorValues().isEmpty();
        log.debug("[evaluateObservedSituation run] ObservedSituationId = " + observedSituation.getId());

        //ordenamos por metricTemplate.id los ObservedMetric del observedSituacion
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("metric.metricTemplate.id", Boolean.FALSE);
        SortUtil.sortByColumn(cols, observedSituation.getObservedMetrics());

        List<EvaluationDSRDTO> ret = generateDataFromObservedSituation(observedSituation);

        log.info("end ret" + ret.size());
        return ret;
    }

    private List<EvaluationDSRDTO> generateDataFromObservedSituation(ObservedSituation observedSituation) throws ScopixException {
        log.info("start");
        List<EvaluationDSRDTO> evaluationThere = new ArrayList<EvaluationDSRDTO>();
        String[] metricTemplateIds = getConfiguration().getStringArray("DSR.METRICID");
        String[] situationTemplateIds = getConfiguration().getStringArray("DSR.SITUATION_TEMPLATE_ID");

        Store store = getStore(observedSituation);
        Area area = getArea(observedSituation, store);
        Set<Integer> omIds = new HashSet<Integer>();
        //recorremos las metricas
        Integer situationTemplateID = observedSituation.getSituation().getSituationTemplate().getId();
        if (ArrayUtils.contains(situationTemplateIds, String.valueOf(situationTemplateID))) {
            for (ObservedMetric om : observedSituation.getObservedMetrics()) {
            // determinar cuales son las situaciones que corresponden 

                log.debug("om: " + om);
                MetricTemplate mt = om.getMetric().getMetricTemplate();
                log.debug("mt.getId():" + mt.getId());
                log.debug("ArrayUtils.contains(metricTemplateIds, String.valueOf(mt.getId()))"
                    + ArrayUtils.contains(metricTemplateIds, String.valueOf(mt.getId())));
                if (ArrayUtils.contains(metricTemplateIds, String.valueOf(mt.getId()))) {
                    //recuperamos la fecha de la evidencia
                    log.debug("EvidenceEvaluations size:" + om.getEvidenceEvaluations().size());
                    Date evidenceDate = getDateOfEvidenceCommand().execute(om.getId());
                    //recorremos las evaluaciones de la metrica
                    for (EvidenceEvaluation ee : om.getEvidenceEvaluations()) {
                        log.debug("ee " + ee);
                        //si la evidencia no es rechazada y no tiene cantdo seguimos
                        log.debug("om:id " + om.getId() + " ee.isRejected() " + ee.isRejected()
                            + " ee.getCantDoReason()" + ee.getCantDoReason());
                        if (!ee.isRejected() && ee.getCantDoReason() == null) {
                            EvaluationDSRDTO data = new EvaluationDSRDTO();

                            data.setEvidenceDate(DateFormatUtils.format(evidenceDate, DATE_FORMAT_DSR));
                            data.setAreaId(area.getId());
                            data.setAreaDescription(area.getDescription());
                            data.setStoreId(store.getId());
                            data.setStoreName(store.getDescription());
                            data.setMetricId(mt.getId());
                            data.setMetricName(mt.getName());
                            if (om.getMetricEvaluation() != null) {
                                data.setValue(om.getMetricEvaluation().getMetricEvaluationResult().doubleValue());
                            }

                            //agregamos el valor solo si no existe el la lista
                            if (!omIds.contains(om.getId())) {
                                if (data.getValue() != null) {
                                    evaluationThere.add(data);
                                    omIds.add(om.getId());
                                }
                            }
                        }
                    }
                } // end id containsMetricTemplate

            } // end for
        } //end if containsSituationTemplate
        log.info("end evaluationThere: " + evaluationThere.size());
        return evaluationThere;
    }

    /**
     * @return the observedSituationCommand
     */
    public GetObservedSituationCommand getObservedSituationCommand() {
        if (observedSituationCommand == null) {
            observedSituationCommand = new GetObservedSituationCommand();
        }
        return observedSituationCommand;
    }

    /**
     * @param observedSituationCommand the observedSituationCommand to set
     */
    public void setObservedSituationCommand(GetObservedSituationCommand observedSituationCommand) {
        this.observedSituationCommand = observedSituationCommand;
    }

    /**
     * @return the dateOfEvidenceCommand
     */
    public DateOfEvidenceCommand getDateOfEvidenceCommand() {
        if (dateOfEvidenceCommand == null) {
            dateOfEvidenceCommand = new DateOfEvidenceCommand();
        }
        return dateOfEvidenceCommand;
    }

    /**
     * @param dateOfEvidenceCommand the dateOfEvidenceCommand to set
     */
    public void setDateOfEvidenceCommand(DateOfEvidenceCommand dateOfEvidenceCommand) {
        this.dateOfEvidenceCommand = dateOfEvidenceCommand;
    }

    private Store getStore(ObservedSituation observedSituation) {
        Store s = observedSituation.getObservedMetrics().get(0).getMetric().getStore();
        return s;

    }

    private Area getArea(ObservedSituation observedSituation, Store store) {
        Area a = null;
        for (Area area : observedSituation.getSituation().getSituationTemplate().getAreaType().getAreas()) {
            if (area.getStore().getId().equals(store.getId())) {
                a = area;
                break;
            }
        }
        return a;
    }

}
