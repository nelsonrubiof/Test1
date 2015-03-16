/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * VadaroEventMTEvaluator.java
 * 
 * Created on 10-07-2014, 12:36:56 PM
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import scopix.cameraaggregator.Aggregator;
import scopix.cameraaggregator.utils.AggregatorEvent;
import scopix.cameraaggregator.utils.AggregatorSituation;

import com.scopix.periscope.ScopixParseUtils;
import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.commands.AddAreaCommand;
import com.scopix.periscope.evaluationmanagement.CreationType;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.commands.AddEvidenceEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddPendingEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetAreaByAreaTypeStoreCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetLastVadaroEventsCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetVadaroCamerasCommand;
import com.scopix.periscope.evaluationmanagement.commands.SaveMetricCommand;
import com.scopix.periscope.evaluationmanagement.commands.SaveSituationCommand;
import com.scopix.periscope.evaluationmanagement.commands.SaveVadaroEventCommand;
import com.scopix.periscope.evaluationmanagement.evaluators.AutomaticEvaluatorManager;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.extractionplanmanagement.commands.GetEPCFromStoreSituationTemplateEvidenceProviderCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateListCommand;
import com.scopix.periscope.vadaro.VadaroEvent;

/**
 *
 * @author Nelson
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_MT, description = "Evaluador de Eventos Vadaro para metrica.")
@SpringBean(rootClass = VadaroEventMTEvaluator.class)
public class VadaroEventMTEvaluator extends AbstractEvidenceEvaluatorForMT {

    private static final Logger log = Logger.getLogger(VadaroEventMTEvaluator.class);

    private GetSituationTemplateListCommand situationTemplateListCommand;
    private GetEPCFromStoreSituationTemplateEvidenceProviderCommand epcByStoreSituationTemplateCommand;
    private SaveSituationCommand saveSituationCommand;
    private SaveMetricCommand saveMetricCommand;
    private SaveVadaroEventCommand saveVadaroEventCommand;
    private GetVadaroCamerasCommand getVadaroCamerasCommand;
    private GetLastVadaroEventsCommand getLastVadaroEventsCommand;
    private AddEvidenceEvaluationCommand addEvidenceEvaluationCommand;

    // private int amountTime = getPropertiesConfiguration().getInt("vadaro.process.amountTime", 30);
    private Integer amountTime = 30;

    public VadaroEventMTEvaluator() {
        try {
            amountTime = getPropertiesConfiguration().getInteger("vadaroEvents.range.time", 30);
            log.debug("Started with time range: " + amountTime);
        } catch (ScopixException e) {
            log.debug("Cannot load system properties configuration.");
        }
    }

    @Override
    public Boolean evaluate(ObservedMetric om, Integer pendingEvaluationId) throws ScopixException {
        log.info("start");
        Store store = om.getMetric().getArea().getStore();
        Date initEvaluation = new Date();
        List<EvidenceEvaluation> evaluations = new ArrayList<EvidenceEvaluation>();
        // recorremos todas las evidenciad retornadas por la metrica
        for (Evidence ev : om.getEvidences()) {
            // verificamos que sea un xml
            log.debug("is xml " + FilenameUtils.isExtension(ev.getEvidencePath(), "xml"));
            if (FilenameUtils.isExtension(ev.getEvidencePath(), "xml")) {
                String path = getEvidencePath(ev, om);
                String pathFile = FilenameUtils.separatorsToSystem(path + ev.getEvidencePath());
                VadaroEvent event = ScopixParseUtils.parseEvent(pathFile);
                event.setStore(store);
                event.setEvidence(ev);
                getSaveVadaroEventCommand().execute(event);
                log.debug("Saved: " + event);

                try {
                    // Get last events from vadaro cameras
                    List<VadaroEvent> events = getLastVadaroEventsCommand().execute(amountTime, store.getId(),
                        event.getTime());
                    log.debug("Retrieved events number: " + (events != null ? events.size() : 0));

                    // TODO Obtener el conjunto de cámaras correspondientes a la situación para el store
                    List<EvidenceProvider> providers = getVadaroCamerasCommand().execute(store.getId());

                    HashMap<String, Integer> cameras = new HashMap<String, Integer>();

                    for (int i = 0; i < providers.size(); i++) {
                        cameras.put(providers.get(i).getName(), i);
                    }

                    log.debug("Cameras amount: " + (cameras.size()));
                    Aggregator aggregator = new Aggregator(cameras);

                    // List<AggregatorSituation> results = new ArrayList<AggregatorSituation>();
                    // try {
                    //
                    List<AggregatorSituation> results = aggregator.result(convertVadaroEventsToAggregator(events));
                    // } catch (NullPointerException e) {

                    // log.debug("Catched NPE on Aggregator");
                    // [AggregatorSituation{evidenceDate=Tue Aug 05 17:08:02 CLT 2014,
                    // situationName=QL_Vadaro_customer_tracking, camera=Queue_Tail, metrics={queue_length=4, wait_time=62}},
                    // AggregatorSituation{evidenceDate=Tue Aug 05 17:08:02 CLT 2014,
                    // situationName=QL_Vadaro_customer_tracking, camera=Queue_Tail, metrics={queue_length=4, wait_time=62}}]
                    // Map<String, Integer> metrics2 = new HashMap<String, Integer>();
                    // metrics2.put("queue_length", 4);
                    // metrics2.put("wait_time", 45);
                    //
                    // AggregatorSituation sit = new AggregatorSituation();
                    // sit.setCamera(providers.get(0).getName());
                    // sit.setEvidenceDate(new Date());
                    // sit.setMetrics(metrics2);
                    // sit.setSituationName("QL_Vadaro_customer_tracking");
                    // results.add(sit);
                    // }
                    log.debug("Aggregator results: " + (results != null ? results.size() : "null"));

                    String situationTemplateName = "";
                    Date evidenceDate = null;
                    String camera = "";
                    if (results != null) {

                        for (AggregatorSituation situationInAggregator : results) {
                            if (situationInAggregator == null) {
                                continue;
                            }
                            situationTemplateName = situationInAggregator.getSituationName();
                            evidenceDate = situationInAggregator.getEvidenceDate();
                            camera = situationInAggregator.getCamera();
                            SituationTemplate situationTemplate = null;
                            SituationTemplate test = new SituationTemplate();
                            test.setName(situationTemplateName);
                            List<SituationTemplate> situationTemplates = getSituationTemplateListCommand().execute(test);

                            if (!situationTemplates.isEmpty()) {
                                situationTemplate = situationTemplates.get(0);
                            }

                            if (situationTemplate != null) {
                                log.debug("Found situation template: " + situationTemplate.getName());
                                // debemos generar todas las situaciones necesarias junto a las metricas
                                Situation newSituation = createSituation(situationTemplate, evidenceDate);
                                // debemos generar todas las metricas asociadas dado los metricTemplates

                                //se crea 1 por situacion
                                log.debug("creating observed_situation");
                                // create Observed Situation (Evaluation Manager acceptNewEvence)
                                // se debe mejorar este codigo
                                AddObservedSituationCommand addObservedSituationCommand = new AddObservedSituationCommand();
                                ObservedSituation observedSituation = new ObservedSituation();
                                observedSituation.setObservedSituationDate(evidenceDate);
                                observedSituation.setSituation(newSituation);
                                // se agrega para mantener dato
                                observedSituation.setEvidenceDate(evidenceDate);
                                // agregegamos el os a los datos
                                addObservedSituationCommand.execute(observedSituation);

                                // mejorar este codigo
                                log.debug("observed_situation_id " + observedSituation.getId());

                                // create PendingEvaluation (Evaluation Manager acceptNewEvence)
                                PendingEvaluation pendingEvaluationNew = new PendingEvaluation();
                                pendingEvaluationNew.setObservedSituation(observedSituation);
                                pendingEvaluationNew.setCreationType(CreationType.AUTOMATIC);
                                pendingEvaluationNew.setCreationDate(new Date());
                                pendingEvaluationNew.setEvaluationState(EvaluationState.FINISHED);
                                pendingEvaluationNew.setUserName("SYSTEM");
                                pendingEvaluationNew.setEvaluationStartDate(new Date());
                                pendingEvaluationNew.setEvaluationEndDate(new Date());

                                AddPendingEvaluationCommand addPendingEvaluationCommand = new AddPendingEvaluationCommand();
                                addPendingEvaluationCommand.execute(pendingEvaluationNew);
                                log.debug("Added pending evaluation " + pendingEvaluationNew.getId());

                                int order = 1;
                                List<Metric> metrics = new ArrayList<Metric>();
                                if (situationInAggregator.getMetrics() != null && !situationInAggregator.getMetrics().isEmpty()) {
                                    for (String key : situationInAggregator.getMetrics().keySet()) {
                                        // create all metrics
                                        for (MetricTemplate mt : situationTemplate.getMetricTemplate()) {
                                            if (mt.getName().equals(key)) {
                                                Metric m = createMetric(store, situationTemplate, mt, evidenceDate, newSituation, order++);
                                                metrics.add(m);
                                                break;
                                            }
                                        }
                                    }
                                    log.debug("Created metrics: " + metrics.size());
                                    // create Observed Metric (Evaluation Manager acceptNewEvence)
                                    AddObservedMetricCommand addObservedMetricCommand = new AddObservedMetricCommand();
                                    for (Metric metric : metrics) {
                                        ObservedMetric observedMetric = new ObservedMetric();
                                        observedMetric.setMetric(metric);
                                        observedMetric.setObservedMetricDate(evidenceDate);
                                        observedMetric.setObservedSituation(observedSituation);
                                        observedSituation.getObservedMetrics().add(observedMetric);
                                        observedMetric.getEvidences().add(ev);
                                        // se agrega para mantener dato
                                        observedMetric.setEvidenceDate(evidenceDate);

                                        addObservedMetricCommand.execute(observedMetric);

                                        log.debug("saving observed metric id " + observedMetric.getId());

                                        EvidenceEvaluation ee = genEvidenceEvaluation(
                                            situationInAggregator.getMetrics().get(metric.getMetricTemplate().getName()), ev, observedMetric,
                                            pendingEvaluationNew.getId(), "VadaroEventMTEvaluator", initEvaluation, new Date());

                                        ee.setObservedMetric(observedMetric);

                                        getAddEvidenceEvaluationCommand().execute(ee);
                                    }
                                }

                                //update data add observed metric
                                log.debug("update observedSituation" + observedSituation);
                                addObservedSituationCommand.execute(observedSituation);

                                pendingEvaluationNew.computeResult();
                                log.debug("pendingEvaluation compute Result");
                                SpringSupport.getInstance().findBeanByClassName(AutomaticEvaluatorManager.class).
                                    enqueueObservedMetric(pendingEvaluationNew.getId());

                            } else {
                                throw new ScopixException("No situationTemplate found.");
                            }
                        }

                    } else {
                        log.debug("Aggregator return null for events");
                    }

                } catch (ScopixException e) {
                    log.error(e, e);

                }
            }
        }
        log.info("end");
        return true;

    }

    private List<AggregatorEvent> convertVadaroEventsToAggregator(List<VadaroEvent> events) {

        List<AggregatorEvent> ret = new ArrayList<AggregatorEvent>();

        for (VadaroEvent vadaroEvent : events) {
            AggregatorEvent aggregatorEvent = new AggregatorEvent(vadaroEvent.getTime(), vadaroEvent.getService(),
                vadaroEvent.getCameraName(), vadaroEvent.getEntered(), vadaroEvent.getExited(), vadaroEvent.getAbandoned(),
                vadaroEvent.getLength(), vadaroEvent.getWaitTime().intValue(), vadaroEvent.getServiceTime().intValue());

            ret.add(aggregatorEvent);
        }
        log.debug("Aggregator first event: " + (ret.size() > 0 ? ret.get(0) : "None"));

        return ret;
    }

    /**
     * @return the situationTemplateListCommand
     */
    public GetSituationTemplateListCommand getSituationTemplateListCommand() {
        if (situationTemplateListCommand == null) {
            situationTemplateListCommand = new GetSituationTemplateListCommand();
        }
        return situationTemplateListCommand;
    }

    /**
     * @param situationTemplateListCommand the situationTemplateListCommand to set
     */
    public void setSituationTemplateListCommand(GetSituationTemplateListCommand situationTemplateListCommand) {
        this.situationTemplateListCommand = situationTemplateListCommand;
    }

    /**
     * @return the epcByStoreSituationTemplateCommand
     */
    public GetEPCFromStoreSituationTemplateEvidenceProviderCommand getEpcByStoreSituationTemplateCommand() {
        if (epcByStoreSituationTemplateCommand == null) {
            epcByStoreSituationTemplateCommand = new GetEPCFromStoreSituationTemplateEvidenceProviderCommand();
        }
        return epcByStoreSituationTemplateCommand;
    }

    /**
     * @param epcByStoreSituationTemplateCommand the epcByStoreSituationTemplateCommand to set
     */
    public void setEpcByStoreSituationTemplateCommand(
        GetEPCFromStoreSituationTemplateEvidenceProviderCommand epcByStoreSituationTemplateCommand) {
        this.epcByStoreSituationTemplateCommand = epcByStoreSituationTemplateCommand;
    }

    private EvidenceEvaluation genEvidenceEvaluation(Integer evaluationResult, Evidence ev, ObservedMetric om, Integer peId,
        String evaluationUser, Date initEvaluation, Date endEvaluation) {
        log.debug("suma:" + evaluationResult + ", Evidence:" + ev.getId() + " , ObservedMetric:" + om.getId() + ", peId:" + peId
            + ", evaluationUser:" + evaluationUser);
        EvidenceEvaluation ee = new EvidenceEvaluation();
        ee.setEvaluationDate(new Date());
        ee.setEvaluationUser(evaluationUser);
        ee.setEvidenceResult(evaluationResult);
        ee.getEvidences().add(ev);
        ee.setObservedMetric(om);
        ee.setPendingEvaluation(new PendingEvaluation());
        ee.getPendingEvaluation().setId(peId);
        // se le agrega la fecha actual
        ee.setInitEvaluation(initEvaluation);
        ee.setEndEvaluation(endEvaluation);

        return ee;
    }

    private Situation createSituation(SituationTemplate situationTemplate, Date evidenceDate) throws ScopixException {
        // recuperamos el Process Asociado al EES y process recibo desde ES
        // Create Situation si es necesario
        Situation situation = new com.scopix.periscope.extractionplanmanagement.Situation();
        situation.setDescription(situationTemplate.getName() + " " + DateFormatUtils.format(evidenceDate, "HH:mm:ss"));
        situation.setSituationTemplate(situationTemplate);
        // de aqui en adelante continuamos normal

        getSaveSituationCommand().execute(situation);
        log.debug("situation = " + situation.getId());
        return situation;
    }

    /**
     * @return the saveSituationCommand
     */
    public SaveSituationCommand getSaveSituationCommand() {
        if (saveSituationCommand == null) {
            saveSituationCommand = new SaveSituationCommand();
        }
        return saveSituationCommand;
    }

    /**
     * @param saveSituationCommand the saveSituationCommand to set
     */
    public void setSaveSituationCommand(SaveSituationCommand saveSituationCommand) {
        this.saveSituationCommand = saveSituationCommand;
    }

    private Metric createMetric(Store store, SituationTemplate st, MetricTemplate mt, Date evidenceDate, Situation situation,
        int order) throws ScopixException {
        log.info("start");
        // Create Metric
        Metric metric = new Metric();
        // recuperamos el Area del epc especifico
        GetAreaByAreaTypeStoreCommand command = new GetAreaByAreaTypeStoreCommand();
        Area area = command.execute(st.getAreaType().getId(), store.getId());
        // si no existe el area la creamos para no perder evidencias
        if (area == null) {
            log.debug("Agregamos nueva area ");
            area = createNewArea(st.getAreaType(), store);
        }
        StringBuilder description = new StringBuilder();
        description.append(mt.getName()).append(" ");
        description.append(DateFormatUtils.format(evidenceDate, "HH:mm:ss")).append(" ");
        description.append(area.getDescription());
        metric.setArea(area);
        metric.setDescription(description.toString());
        metric.setMetricOrder(order);
        metric.setMetricTemplate(mt);
        metric.setMetricVariableName("");
        metric.setSituation(situation);
        metric.setStore(store);
        metric.setExtractionPlanMetric(new ExtractionPlanMetric());
        getSaveMetricCommand().execute(metric);
        log.info("end [metric:" + metric.getId() + "]");
        return metric;
    }

    public Area createNewArea(AreaType areaType, Store store) throws ScopixException {
        log.info("start [areaType:" + areaType.getId() + "][store:" + store.getId() + "]");
        Area a = new Area();
        a.setStore(store);
        a.setAreaType(areaType);
        a.setName(areaType.getName());
        a.setDescription(areaType.getDescription());
        a = addAutomanticArea(a);
        log.info("end");
        return a;
    }

    private Area addAutomanticArea(Area a) throws ScopixException {
        log.info("start");
        AddAreaCommand command = new AddAreaCommand();
        command.execute(a);
        log.info("end");
        return a;
    }

    /**
     * @return the saveMetricCommand
     */
    public SaveMetricCommand getSaveMetricCommand() {
        if (saveMetricCommand == null) {
            saveMetricCommand = new SaveMetricCommand();
        }
        return saveMetricCommand;
    }

    public SaveVadaroEventCommand getSaveVadaroEventCommand() {
        if (saveVadaroEventCommand == null) {
            saveVadaroEventCommand = new SaveVadaroEventCommand();
        }
        return saveVadaroEventCommand;
    }

    public GetVadaroCamerasCommand getVadaroCamerasCommand() {
        if (getVadaroCamerasCommand == null) {
            getVadaroCamerasCommand = new GetVadaroCamerasCommand();
        }
        return getVadaroCamerasCommand;
    }

    public GetLastVadaroEventsCommand getLastVadaroEventsCommand() {
        if (getLastVadaroEventsCommand == null) {
            getLastVadaroEventsCommand = new GetLastVadaroEventsCommand();
        }
        return getLastVadaroEventsCommand;
    }

    public AddEvidenceEvaluationCommand getAddEvidenceEvaluationCommand() {
        if (addEvidenceEvaluationCommand == null) {
            addEvidenceEvaluationCommand = new AddEvidenceEvaluationCommand();
        }
        return addEvidenceEvaluationCommand;
    }

    /**
     * @param saveMetricCommand the saveMetricCommand to set
     */
    public void setSaveMetricCommand(SaveMetricCommand saveMetricCommand) {
        this.saveMetricCommand = saveMetricCommand;
    }

}
