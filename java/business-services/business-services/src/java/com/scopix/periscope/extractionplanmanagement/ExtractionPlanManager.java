/*
 *
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ExtractionPlanManager.java
 *
 * Created on 15-12-2008, 12:19:54 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import com.bestcode.mathparser.IMathParser;
import com.bestcode.mathparser.MathParserFactory;
import com.csvreader.CsvReader;
import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetSensorListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreListCommand;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.evaluationmanagement.EvaluationQueueManager;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.extractionplanmanagement.commands.ActivateWizardCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.AddFormulaCommand;
import com.scopix.periscope.extractionplanmanagement.commands.AddMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.AddSituationCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CleanDetailExtractionPlanRangeCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CleanExtractionPlanMetricsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CleanSensorsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CopyExtractionPlanRangeInDaysCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CreateExtractionPlanCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.CreateRecordsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.DeleteExtractionPlanRangeByIdCommand;
import com.scopix.periscope.extractionplanmanagement.commands.DeleteFormulaCommand;
import com.scopix.periscope.extractionplanmanagement.commands.DisableExtractionPlanCustomizingListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.EditFormulaCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GenerateDetalleSolucitudDTOFromEPCCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetEPCFromStoreSituationTemplateEvidenceProviderCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingByIdCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanRangeByIdCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanRangeDetailListByIdEPRCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanRangeListByIdEPCCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetFormulaCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetFormulaListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.GetIndicatorNameListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.InactivateEPCCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.ReProcessCommand;
import com.scopix.periscope.extractionplanmanagement.commands.RemoveExtractionPlanRangesDaysCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanCustomizingCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanMetricsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanRangeCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanRangeDetailCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanRangesCommnad;
import com.scopix.periscope.extractionplanmanagement.commands.SaveSensorsCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformDTOtoExtractionPlanRangeCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformEPCGeneralToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformEvidenceProvidersToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformExtractionPlanCustomizingToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformExtractionPlanCustomizingsToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformExtractionPlanRangeDetailToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformExtractionPlanRangeToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformExtractionPlanRangesToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformMetricTemplateToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.TransformSensorsToDTOCommand;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateExtracionPlanMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateMetricOrderCommand;
import com.scopix.periscope.extractionplanmanagement.dto.DetalleSolicitudDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanMetricDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanRangeDetailDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.CorporateStructureManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.securitymanagement.TemplatesManagerPermissions;
import com.scopix.periscope.securitymanagement.WizardManagerPermissions;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateListCommand;
import com.scopix.periscope.templatemanagement.dto.MetricTemplateDTO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cesar
 *
 */
@SpringBean(rootClass = ExtractionPlanManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class ExtractionPlanManager {

    private SecurityManager securityManager;
    private GetSituationTemplateListCommand getSituationTemplateListCommand;
    private GetStoreCommand getStoreCommand;
    private AddSituationCommand addSituationCommand;
    private GetAreaListCommand areaListCommand;
    private AddMetricCommand addMetricCommand;
    private UpdateExtracionPlanMetricCommand updateExtracionPlanMetricCommand;
    private UpdateMetricOrderCommand updateMetricOrderCommand;
    private ActivateWizardCustomizingCommand activateWizardCustomizingCommand;
    private GetExtractionPlanCustomizingListCommand extractionPlanCustomizingListCommand;
    private TransformExtractionPlanCustomizingsToDTOCommand transformExtractionPlanCustomizingsToDTOCommand;
    private CreateExtractionPlanCustomizingCommand createExtractionPlanCustomizingCommand;
    private TransformExtractionPlanCustomizingToDTOCommand transformExtractionPlanCustomizingToDTOCommand;
    private GetExtractionPlanCustomizingByIdCommand extractionPlanCustomizingByIdCommand;
    private GetEvidenceProviderListCommand evidenceProviderListCommand;
    private TransformEvidenceProvidersToDTOCommand transformEvidenceProvidersToDTOCommand;
    private GetSensorListCommand sensorListCommand;
    private TransformSensorsToDTOCommand transformSensorsToDTOCommand;
    private TransformMetricTemplateToDTOCommand transformMetricTemplateToDTOCommand;
    private TransformEPCGeneralToDTOCommand transformEPCGeneralToDTOCommand;
    private GetExtractionPlanRangeListByIdEPCCommand extractionPlanRangeListByIdEPCCommand;
    private TransformExtractionPlanRangesToDTOCommand transformExtractionPlanRangesToDTOCommand;
    private SaveSensorsCommand saveSensorsCommand;
    private CleanExtractionPlanMetricsCommand cleanExtractionPlanMetricsCommand;
    private SaveExtractionPlanMetricCommand saveExtractionPlanMetricCommand;
    private SaveExtractionPlanCustomizingCommand saveExtractionPlanCustomizingCommand;
    private TransformDTOtoExtractionPlanRangeCommand transformDTOtoExtractionPlanRangeCommand;
    private SaveExtractionPlanRangeCommand saveExtractionPlanRangeCommand;
    private CleanDetailExtractionPlanRangeCommand cleanDetailExtractionPlanRangeCommand;
    private SaveExtractionPlanRangeDetailCommand saveExtractionPlanRangeDetailCommand;
    private GetExtractionPlanRangeDetailListByIdEPRCommand extractionPlanRangeDetailListByIdEPRCommand;
    private TransformExtractionPlanRangeDetailToDTOCommand transformExtractionPlanRangeDetailToDTOCommand;
    private SaveExtractionPlanRangesCommnad saveExtractionPlanRangesCommnad;
    private RemoveExtractionPlanRangesDaysCommand removeExtractionPlanRangesDaysCommand;
    private CopyExtractionPlanRangeInDaysCommand copyExtractionPlanRangeInDaysComnad;
    private DeleteExtractionPlanRangeByIdCommand deleteExtractionPlanRangeByIdCommand;
    private TransformExtractionPlanRangeToDTOCommand transformExtractionPlanRangeToDTOCommand;
    private GenerateDetalleSolucitudDTOFromEPCCommand generateDetalleSolucitudDTOFromEPCCommand;
    private GetExtractionPlanRangeByIdCommand extractionPlanRangeByIdCommand;
    private InactivateEPCCustomizingCommand inactivateEPCCustomizingCommand;
    private GetSituationTemplateCommand situationTemplateCommand;
    private GetEPCFromStoreSituationTemplateEvidenceProviderCommand epcFromStoreSituationTemplateEvidenceProviderCommand;
    private DisableExtractionPlanCustomizingListCommand disableExtractionPlanCustomizingListCommand;
    private CreateRecordsCommand createRecordsCommand;
    private SaveExtractionPlanMetricsCommand saveExtractionPlanMetricsCommand;
    private CleanSensorsCommand cleanSensorsCommand;
    /**
     * hasta aqui
     */
    private Logger log = Logger.getLogger(ExtractionPlanManager.class);
    //utilizado para revisar que dia se esta procesando
    private Integer currentDay;
    //utilizado para saber cual es la cantidad de records creados;
    //se debe colocar en 0 desde quien llama al crearteRecords
    private int currentCreateRecord;

    /**
     * Obtain a list of wizard customizing
     *
     * @param epc represent the data for filtering
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingList(ExtractionPlanCustomizing epc,
        String estado, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_WIZARD_CUSTOMIZING_LIST_PERMISSION);

        List<ExtractionPlanCustomizing> wizardCustomizings = getExtractionPlanCustomizingListCommand().execute(epc, estado);
        log.debug("end, result = " + wizardCustomizings.size());
        return wizardCustomizings;
    }

    /**
     * This method is for inactivate a wizard customizing
     *
     * @param id of the wizard customizing to inactivate
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void inactivateExtractionPlanCustomizing(Integer id, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.INACTIVATE_WIZARD_CUSTOMIZING_PERMISSION);

        getInactivateEPCCustomizingCommand().execute(id);
        log.debug("end");
    }

    public InactivateEPCCustomizingCommand getInactivateEPCCustomizingCommand() {
        if (inactivateEPCCustomizingCommand == null) {
            inactivateEPCCustomizingCommand = new InactivateEPCCustomizingCommand();
        }
        return inactivateEPCCustomizingCommand;
    }

    public void setInactivateEPCCustomizingCommand(InactivateEPCCustomizingCommand inactivateEPCCustomizingCommand) {
        this.inactivateEPCCustomizingCommand = inactivateEPCCustomizingCommand;
    }

    /**
     * This method is for update a wizard customizing
     *
     * @param extractionPlanMetric new data of the wizard customizing
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void updateWizardCustomizing(ExtractionPlanMetric extractionPlanMetric, long sessionId)
        throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_EXTRACTION_PLAN_METRIC_PERMISSION);
        getUpdateExtracionPlanMetricCommand().execute(extractionPlanMetric);
        log.debug("end");
    }

    /**
     * Update the order of a wizard customizing metric
     *
     * @param wcs lista de wizard customizings
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<ExtractionPlanMetric> updateOrder(ExtractionPlanCustomizing wcs, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_ORDER_PERMISSION);

        if (wcs != null && wcs.getExtractionPlanMetrics() != null && !wcs.getExtractionPlanMetrics().isEmpty()) {
            LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
            colsSort.put("evaluationOrder", Boolean.FALSE);
            SortUtil.sortByColumn(colsSort, wcs.getExtractionPlanMetrics());
            int order = 1;
            for (ExtractionPlanMetric extractionPlanMetric : wcs.getExtractionPlanMetrics()) {
                extractionPlanMetric.setEvaluationOrder(order);
                this.updateWizardCustomizing(extractionPlanMetric, sessionId);
                this.updateMetricOrder(extractionPlanMetric);
                order++;
            }
        }
        log.debug("end");
        List<ExtractionPlanMetric> ret = null;
        if (wcs != null) {
            ret = wcs.getExtractionPlanMetrics();
        }
        return ret;
    }

    /**
     * Este metodo actualiza el orden de las metricas relacionadas al extraction plan indicado
     *
     * @param extractionPlanMetric debe contener el id y el orden
     * @throws ScopixException
     */
    protected void updateMetricOrder(ExtractionPlanMetric extractionPlanMetric) throws ScopixException {
        getUpdateMetricOrderCommand().execute(extractionPlanMetric);
    }

    /**
     * Move Up the order of the selected wizard customizing
     *
     * @param extractionPlanMetric selected wizard customizing
     * @param wcs list of wizard customizing
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<ExtractionPlanMetric> moveUp(ExtractionPlanMetric extractionPlanMetric,
        List<ExtractionPlanMetric> extractionPlanMetrics, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.MOVE_UP_PERMISSION);

        Collections.sort(extractionPlanMetrics, new BeanComparator("evaluationOrder"));
        int index = Collections.binarySearch(extractionPlanMetrics, extractionPlanMetric, new BeanComparator("evaluationOrder"));
        if (index > 0) {
            extractionPlanMetrics.get(index).setEvaluationOrder(extractionPlanMetrics.get(index).getEvaluationOrder() - 1);
            extractionPlanMetrics.get(index - 1).setEvaluationOrder(extractionPlanMetrics.get(index - 1).
                getEvaluationOrder() + 1);
            extractionPlanMetrics = this.updateOrder(extractionPlanMetric.getExtractionPlanCustomizing(), sessionId);
        }
        log.debug("end");
        return extractionPlanMetrics;
    }

    /**
     * Move Down the order of the selected wizard customizing
     *
     * @param epm selected wizard customizing
     * @param epms list of wizard customizing
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<ExtractionPlanMetric> moveDown(ExtractionPlanMetric epm, List<ExtractionPlanMetric> epms, long sessionId)
        throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.MOVE_DOWN_PERMISSION);
        Collections.sort(epms, new BeanComparator("evaluationOrder"));
        int index = Collections.binarySearch(epms, epm, new BeanComparator("evaluationOrder"));
        if (index >= 0 && index < (epms.size() - 1)) {
            epms.get(index).setEvaluationOrder(epms.get(index).getEvaluationOrder() + 1);
            epms.get(index + 1).setEvaluationOrder(epms.get(index + 1).getEvaluationOrder() - 1);
            epms = this.updateOrder(epm.getExtractionPlanCustomizing(), sessionId);
        }
        log.debug("end");
        return epms;
    }

    private List<Integer> getListDay() {
        //generamos una lista de todos los dias que vamos a borrar
        List<Integer> days = new ArrayList<Integer>();
        for (int i = 1; i <= 7; i++) {
            days.add(i);
        }
        return days;
    }

    /**
     * Validate if at least one of evidenceProviders is automatic
     *
     * @param epc
     * @return
     */
    private boolean isAutomaticEvidenceRequest(ExtractionPlanCustomizing extractionPlanCustomizing) {
        log.debug("start");
        boolean resp = false;
//        if (extractionPlanCustomizing != null) {
        List<EvidenceProvider> evidenceProviders = new ArrayList<EvidenceProvider>();
        if (extractionPlanCustomizing.getSensors() != null && !extractionPlanCustomizing.getSensors().isEmpty()) {
            resp = true;
        }
        if (!resp) {
            //revisamos todos los evidence provider, buscado si existe alguno de tipo automatico
            for (ExtractionPlanMetric epm : extractionPlanCustomizing.getExtractionPlanMetrics()) {
                evidenceProviders.addAll(epm.getEvidenceProviders());
            }
            for (EvidenceProvider ep : evidenceProviders) {
                if (ep.getEvidenceProviderType().isAutomatic()) {
                    resp = true;
                    break;
                }
            }
        }
//        }
        log.debug("end, resp = " + resp);
        return resp;
    }

    private boolean isAlgunRangoRealRandom(ExtractionPlanCustomizing epc) {
        log.info("start");
        boolean ret = false;
        for (ExtractionPlanRange epr : epc.getExtractionPlanRanges()) {
            if (epr.getExtractionPlanRangeType().equals(ExtractionPlanRangeType.REAL_RANDOM)) {
                ret = true;
                break;
            }
        }
        log.info("end " + ret);
        return ret;
    }

    /**
     * This method return all automatic ExtractionPlanCustomizing for a store
     *
     * @param store
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<ExtractionPlanCustomizing> getAutomaticExtractionPlanCustomizings(Store store) throws ScopixException {
        return getAutomaticExtractionPlanCustomizings(store, null);
    }

    /**
     * This method return all automatic ExtractionPlanCustomizing
     *
     * @param store
     * @param evidenceProviderId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<ExtractionPlanCustomizing> getAutomaticExtractionPlanCustomizings(Store store, Integer evidenceProviderId)
        throws ScopixException {
        log.debug("start, storeId = " + store.getId() + ", evidenceProviderId = " + evidenceProviderId);
        List<ExtractionPlanCustomizing> extractionPlanCustomizings = new ArrayList<ExtractionPlanCustomizing>();
        SituationTemplate st = new SituationTemplate();
        st.setActive(true);
        List<SituationTemplate> situationTemplates = getGetSituationTemplateListCommand().execute(st);
        for (SituationTemplate situationTemplate : situationTemplates) {
            log.debug("situationTemplateId = " + situationTemplate);
            ExtractionPlanCustomizing epc = getEpcFromStoreSituationTemplateEvidenceProviderCommand().execute(store.getId(),
                situationTemplate.getId(), evidenceProviderId);
            if (epc == null) {
                continue;
            }
            if (isAutomaticEvidenceRequest(epc) || (epc != null && epc.isRandomCamera()) || isAlgunRangoRealRandom(epc)) {
                //debemos incorporar todos los epc que contienen random de camaras
                extractionPlanCustomizings.add(epc);
            }
        }
        log.debug("end, result = " + extractionPlanCustomizings.size());
        return extractionPlanCustomizings;
    }

    /**
     * Generate the situations, metrics and evidence request
     *
     * @param situationTemplateId Id of the situation template
     * @param storeId Id of the store
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void generate(ExtractionPlanCustomizing epc, long sessionId) throws ScopixException {
        log.debug("storeId = " + epc.getStore().getId() + ", situationTemplateId = " + epc.getSituationTemplate().getId());
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GENERATE_RECORDS_PERMISSION);

        int requestCreados = getCreateRecordsCommand().execute(epc);

        this.activateWizardCustomizings(epc);
        //aumentamos la cantidad creada
        int metrics = epc.getExtractionPlanMetrics().size();
        int providers = 0;
        for (ExtractionPlanMetric epm : epc.getExtractionPlanMetrics()) {
            providers += epm.getEvidenceProviders().size();
        }
        int incremento = 0;
        if (metrics > 0 && providers > 0) {
            incremento = requestCreados / metrics / providers;
        }
        setCurrentCreateRecord(getCurrentCreateRecord() + incremento);
        log.debug("storeId = " + epc.getStore().getId());
    }

    public void activateEPC(ExtractionPlanCustomizing epc) {
        log.info("start");
        activateWizardCustomizings(epc);
        log.info("end");
    }

    /**
     * This method change the state to active
     *
     * @param epc
     */
    protected void activateWizardCustomizings(ExtractionPlanCustomizing epc) {
        log.info("start");
        getActivateWizardCustomizingCommand().execute(epc.getId());
        log.info("end");
    }

    /**
     * This method return a list of formulas
     *
     * @param formula
     * @return
     */
    public List<Formula> getFormulaList(Formula formula, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_FORMULA_LIST_PERMISSION);
        GetFormulaListCommand command = new GetFormulaListCommand();
        List<Formula> formulas = command.execute(formula);
        return formulas;
    }

    /**
     * This method retur a specific formula
     *
     * @param id
     * @return
     */
    public Formula getFormula(Integer id, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_FORMULA_PERMISSION);
        GetFormulaCommand command = new GetFormulaCommand();
        Formula formula = command.execute(id);
        return formula;
    }

    /**
     * This method add a new formula
     *
     * @param formula
     */
    public void addFormula(Formula formula, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.ADD_FORMULA_PERMISSION);
        AddFormulaCommand command = new AddFormulaCommand();
        command.execute(formula);
    }

    /**
     * This method edith a existent formula
     *
     * @param formula
     */
    public void editFormula(Formula formula, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.EDIT_FORMULA_PERMISSION);
        EditFormulaCommand command = new EditFormulaCommand();
        command.execute(formula);
    }

    /**
     * this method is for test the formula
     *
     * @param formula
     * @param vars
     * @return
     * @throws Exception
     */
    public Double testFormula(String formula, HashMap<String, Double> vars, long sessionId) throws Exception {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.TEST_FORMULA_PERMISSION);
        IMathParser parser = MathParserFactory.create();
        parser.setExpression(formula);
        for (String var : vars.keySet()) {
            parser.setVariable(var, vars.get(var));
        }
        Double result = parser.evaluate();
        return result;
    }

    /**
     * This method delete a formula from system
     *
     * @param id
     */
    public void deleteFormula(Integer id, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.DELETE_FORMULA_PERMISSION);
        DeleteFormulaCommand command = new DeleteFormulaCommand();
        command.execute(id);
    }

    public List<Indicator> getIndicatorList(AreaType areaType, Product product) {
        GetIndicatorNameListCommand command = new GetIndicatorNameListCommand();
        List<Indicator> indicators = command.execute(areaType, product);
        return indicators;
    }

    public void reprocess(Date startDate, Date endDate, List<Integer> situationTemplateIds, List<Integer> storeIds,
        long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.REPROCESS_DATA_PERMISSION);
        ReProcessCommand command = new ReProcessCommand();
        command.execute(startDate, endDate, situationTemplateIds, storeIds);
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshSituationQueue();
    }

    public byte[] generateOSEReport(Date startDate, Date endDate, List<Integer> situationTemplateIds, List<Integer> storeIds)
        throws Exception {
        byte[] doc = null;
//        try {
//            GetObservedSituationEvaluationDTOsCommand command = new GetObservedSituationEvaluationDTOsCommand();
//            List<ObservedSituationEvaluationDTO> list = command.execute(startDate, endDate, situationTemplateIds, storeIds);
//            ReportGenerator rg = new ReportGenerator();
//            HashMap<String, Object> param = new HashMap();
//            param.put("startDate", startDate);
//            param.put("endDate", endDate);
//            param.put("size", list != null ? list.size() : 0);
//            doc = rg.generateReportFromClasspath("/reports/ose_report.jasper", list, ReportType.EXCEL, param);
//        } catch (Exception e) {
//            log.error("Error creating document: " + e.getMessage(), e);
//            throw e;
//        }
        return doc;
    }

    public byte[] generateIndicatorReport(Date startDate, Date endDate, List<Integer> situationTemplateIds,
        List<Integer> storeIds) throws Exception {
        byte[] doc = null;
//        try {
//            GetIndicatorValueDTOsCommand command = new GetIndicatorValueDTOsCommand();
//            List<IndicatorValuesDTO> list = command.execute(startDate, endDate, situationTemplateIds, storeIds);
//            ReportGenerator rg = new ReportGenerator();
//            HashMap param = new HashMap();
//            param.put("startDate", startDate);
//            param.put("endDate", endDate);
//            param.put("size", list != null ? list.size() : 0);
//            doc = rg.generateReportFromClasspath("/reports/ind_report.jasper", list, ReportType.EXCEL, param);
//        } catch (Exception e) {
//            log.error("Error creating document: " + e.getMessage(), e);
//            throw e;
//        }
        return doc;
    }

    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    public GetSituationTemplateListCommand getGetSituationTemplateListCommand() {
        if (getSituationTemplateListCommand == null) {
            getSituationTemplateListCommand = new GetSituationTemplateListCommand();
        }
        return getSituationTemplateListCommand;
    }

    public void setGetSituationTemplateListCommand(GetSituationTemplateListCommand getSituationTemplateListCommand) {
        this.getSituationTemplateListCommand = getSituationTemplateListCommand;
    }

    public GetStoreCommand getGetStoreCommand() {
        if (getStoreCommand == null) {
            getStoreCommand = new GetStoreCommand();
        }
        return getStoreCommand;
    }

    public void setGetStoreCommand(GetStoreCommand getStoreCommand) {
        this.getStoreCommand = getStoreCommand;
    }

    public AddSituationCommand getAddSituationCommand() {
        if (addSituationCommand == null) {
            addSituationCommand = new AddSituationCommand();
        }
        return addSituationCommand;
    }

    public void setAddSituationCommand(AddSituationCommand addSituationCommand) {
        this.addSituationCommand = addSituationCommand;
    }

    public GetAreaListCommand getAreaListCommand() {
        if (areaListCommand == null) {
            areaListCommand = new GetAreaListCommand();
        }
        return areaListCommand;
    }

    public void setAreaListCommand(GetAreaListCommand areaListCommand) {
        this.areaListCommand = areaListCommand;
    }

    public AddMetricCommand getAddMetricCommand() {
        if (addMetricCommand == null) {
            addMetricCommand = new AddMetricCommand();
        }
        return addMetricCommand;
    }

    public void setAddMetricCommand(AddMetricCommand addMetricCommand) {
        this.addMetricCommand = addMetricCommand;
    }

    public UpdateExtracionPlanMetricCommand getUpdateExtracionPlanMetricCommand() {
        if (updateExtracionPlanMetricCommand == null) {
            updateExtracionPlanMetricCommand = new UpdateExtracionPlanMetricCommand();
        }
        return updateExtracionPlanMetricCommand;
    }

    public void setUpdateExtracionPlanMetricCommand(UpdateExtracionPlanMetricCommand updateExtracionPlanMetricCommand) {
        this.updateExtracionPlanMetricCommand = updateExtracionPlanMetricCommand;
    }

    public UpdateMetricOrderCommand getUpdateMetricOrderCommand() {
        if (updateMetricOrderCommand == null) {
            updateMetricOrderCommand = new UpdateMetricOrderCommand();
        }
        return updateMetricOrderCommand;
    }

    public void setUpdateMetricOrderCommand(UpdateMetricOrderCommand updateMetricOrderCommand) {
        this.updateMetricOrderCommand = updateMetricOrderCommand;
    }

    public ActivateWizardCustomizingCommand getActivateWizardCustomizingCommand() {
        if (activateWizardCustomizingCommand == null) {
            activateWizardCustomizingCommand = new ActivateWizardCustomizingCommand();
        }
        return activateWizardCustomizingCommand;
    }

    public void setActivateWizardCustomizingCommand(ActivateWizardCustomizingCommand activateWizardCustomizingCommand) {
        this.activateWizardCustomizingCommand = activateWizardCustomizingCommand;
    }

    /**
     * Metodos generados a partir de cambios en modelo de EPC
     */
    public void saveExtractionPlanMetrics(ExtractionPlanCustomizing customizing, List<ExtractionPlanMetricDTO> planMetricsDTOs)
        throws ScopixException {
        cleanExtractionPlanMetrics(customizing);
        //recorremos todas las metricas y las almacenamos
        for (ExtractionPlanMetricDTO planMetric : planMetricsDTOs) {
            //guardamos la metrica y la asociamos al customizing que llega            
            saveExtractionPlanMetric(customizing, planMetric);
        }
    }

    private void cleanExtractionPlanMetrics(ExtractionPlanCustomizing customizing) throws ScopixException {
        //se eliminan desde base datos todas las entradas de ExtractionPlanMetric para el customizing recibido
        getCleanExtractionPlanMetricsCommand().execute(customizing);
    }

    public CleanExtractionPlanMetricsCommand getCleanExtractionPlanMetricsCommand() {
        if (cleanExtractionPlanMetricsCommand == null) {
            cleanExtractionPlanMetricsCommand = new CleanExtractionPlanMetricsCommand();
        }
        return cleanExtractionPlanMetricsCommand;
    }

    public void setCleanExtractionPlanMetricsCommand(CleanExtractionPlanMetricsCommand cleanExtractionPlanMetricsCommand) {
        this.cleanExtractionPlanMetricsCommand = cleanExtractionPlanMetricsCommand;
    }

    private void saveExtractionPlanMetric(ExtractionPlanCustomizing customizing, ExtractionPlanMetricDTO planMetricDTO) {
        //guarda un ExtractionPlanMetric en base de datos
        //generamos el nuevo ExtractionPlanMetric dado el dto y lo almacenamos
        getSaveExtractionPlanMetricCommand().execute(customizing, planMetricDTO);
    }

    public SaveExtractionPlanMetricCommand getSaveExtractionPlanMetricCommand() {
        if (saveExtractionPlanMetricCommand == null) {
            saveExtractionPlanMetricCommand = new SaveExtractionPlanMetricCommand();
        }
        return saveExtractionPlanMetricCommand;
    }

    public void setSaveExtractionPlanMetricCommand(SaveExtractionPlanMetricCommand saveExtractionPlanMetricCommand) {
        this.saveExtractionPlanMetricCommand = saveExtractionPlanMetricCommand;
    }

    public void saveExtractionPlanRanges(ExtractionPlanCustomizing customizing, List<ExtractionPlanRange> planRanges)
        throws ScopixException {
        customizing.getExtractionPlanRanges().clear();
        //recorremos todos los Rangos y los almacenamos
        for (ExtractionPlanRange epr : planRanges) {
            //asociamos todos los datos necesarios
            epr.setExtractionPlanCustomizing(customizing);
            saveExtractionPlanRange(epr, true);
        }
    }

    private void saveExtractionPlanRange(ExtractionPlanRange extractionPlanRange, boolean generateDetail)
        throws ScopixException {
        log.info("start");
        //guardamos es ExtractionPlanRange recibido limpiamos los detalles del rango
        if (generateDetail || (extractionPlanRange.getId() == null || extractionPlanRange.getId() == 0)) {
            if (extractionPlanRange.getId() == 0) {
                extractionPlanRange.setId(null);
            }
            extractionPlanRange.getExtractionPlanRangeDetails().clear();

            //borramos todos los detalles del rango
            cleanDetailExtractionPlanRange(extractionPlanRange);
            //si corresponde generamos el detalle
            List<ExtractionPlanRangeDetail> details = generateDetailExtractionPlanRange(extractionPlanRange);
            extractionPlanRange.setExtractionPlanRangeDetails(details);
        }
        getSaveExtractionPlanRangeCommand().execute(extractionPlanRange);
        log.info("end");
    }

    public void cleanDetailExtractionPlanRange(ExtractionPlanRange extractionPlanRange) throws ScopixException {
        //borra todos los detalles de un ExtractionPlanRange desde base de datos        
        getCleanDetailExtractionPlanRangeCommand().execute(extractionPlanRange);
    }

    public SaveExtractionPlanRangeCommand getSaveExtractionPlanRangeCommand() {
        if (saveExtractionPlanRangeCommand == null) {
            saveExtractionPlanRangeCommand = new SaveExtractionPlanRangeCommand();
        }
        return saveExtractionPlanRangeCommand;
    }

    public void setSaveExtractionPlanRangeCommand(SaveExtractionPlanRangeCommand saveExtractionPlanRangeCommand) {
        this.saveExtractionPlanRangeCommand = saveExtractionPlanRangeCommand;
    }

    public CleanDetailExtractionPlanRangeCommand getCleanDetailExtractionPlanRangeCommand() {
        if (cleanDetailExtractionPlanRangeCommand == null) {
            cleanDetailExtractionPlanRangeCommand = new CleanDetailExtractionPlanRangeCommand();
        }
        return cleanDetailExtractionPlanRangeCommand;
    }

    public void setCleanDetailExtractionPlanRangeCommand(CleanDetailExtractionPlanRangeCommand value) {
        this.cleanDetailExtractionPlanRangeCommand = value;
    }

    public List<ExtractionPlanRangeDetail> generateDetailExtractionPlanRange(ExtractionPlanRange extractionPlanRange)
        throws ScopixException {
        //determinar algoritmo para la generación del detalle generar los detalles segun algoritmo
        List<ExtractionPlanRangeDetail> details;

        //Para los casos de AUTOMATIC_EVIDENCE y REAL_RANDOM No se generan Detalles
        switch (extractionPlanRange.getExtractionPlanRangeType()) {
            case FIXED:
                details = generateDetailFixed(extractionPlanRange);
                break;
            case RANDOM:
                details = generateDetailRandom(extractionPlanRange);
                break;
            case AUTOMATIC_EVIDENCE:
            case REAL_RANDOM:
            default:
                details = new ArrayList<ExtractionPlanRangeDetail>();
                break;
        }

        return details;
    }

    protected List<ExtractionPlanRangeDetail> generateDetailFixed(ExtractionPlanRange range) {
        List<ExtractionPlanRangeDetail> details = new ArrayList<ExtractionPlanRangeDetail>();
        log.debug("start range " + range.getId() + " initialTime=" + range.getInitialTime() + " endTime=" + range.getEndTime());
        if (range.getInitialTime() == null || range.getEndTime() == null) {
            //no hacemos nada ya que el rango tiene errores
            return details;
        }

        int largoBloque = range.getFrecuency() / range.getSamples();

        Date time = range.getInitialTime();
        while (time.before(range.getEndTime())) {
            Date timeMuestra = time;
            for (int i = 0; i < range.getSamples(); i++) {
                ExtractionPlanRangeDetail detail = new ExtractionPlanRangeDetail();
                detail.setExtractionPlanRange(range);
                detail.setTimeSample(timeMuestra);
                details.add(detail);
                timeMuestra = DateUtils.addMinutes(timeMuestra, largoBloque);
                if (timeMuestra.after(range.getEndTime())) {
                    //si sig toma de muetra sobrepasa el termino del rango no hacemos nada
                    log.info("timeMuestra:" + timeMuestra + " rangoEndTime:" + range.getEndTime());
                    break;
                }
            }
            //sumarle al time la frecuencia
            time = DateUtils.addMinutes(time, range.getFrecuency());
        }
        log.debug("end details=" + details.size());
        return details;
    }

    public Date getRandomDateBetween(Date desde, Date hasta) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(desde);
        BigDecimal decFrom = new BigDecimal(cal.getTimeInMillis());

        cal.setTime(hasta);
        BigDecimal decTo = new BigDecimal(cal.getTimeInMillis());

        BigDecimal selisih = decTo.subtract(decFrom);
        BigDecimal factor = selisih.multiply(new BigDecimal(Math.random()));
        cal.setTimeInMillis((factor.add(decFrom)).longValue());
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public double diffDateInMin(Date fecha1, Date fecha2) {
        Date fMayor;
        Date fMenor;
        if (fecha1.compareTo(fecha2) > 0) {
            fMayor = fecha1;
            fMenor = fecha2;
        } else {
            fMayor = fecha2;
            fMenor = fecha1;
        }
        double diff = (fMayor.getTime() - fMenor.getTime()) / (1000 * 60);

        return diff;
    }

    protected List<ExtractionPlanRangeDetail> generateDetailRandom(ExtractionPlanRange range) throws ScopixException {
        log.debug("start range " + range.getId() + " initialTime=" + DateFormatUtils.format(range.getInitialTime(), "HH:mm")
            + " endTime=" + DateFormatUtils.format(range.getEndTime(), "HH:mm"));
        List<ExtractionPlanRangeDetail> details = new ArrayList<ExtractionPlanRangeDetail>();
        int largoBloque = range.getFrecuency() / range.getSamples();
        int duracionMinutes = 1;
        if (range.getDuration() >= 60) {
            duracionMinutes = (range.getDuration() / 60);
        }

        //generamos los posibles terminos en minutos 
        //es decir desde el punto de inicio en cuantos minutos se puede comenzar
        Integer[] topeMuestraMinutos = new Integer[range.getSamples()];
        topeMuestraMinutos[0] = largoBloque - duracionMinutes;
        for (int i = 1; i < range.getSamples(); i++) {
            topeMuestraMinutos[i] = topeMuestraMinutos[i - 1] + largoBloque;
        }
        log.info("topeMuestraMinutos " + StringUtils.join(topeMuestraMinutos, ","));
        //desde el incio de la frecuencia agregamos los rangos intermedios para la toma de las muestras
        Date initFrecuency = range.getInitialTime();

        //revisar que pasa con el untimo bloque del rango
        while (initFrecuency.before(range.getEndTime())) {
            double diff = diffDateInMin(initFrecuency, range.getEndTime());
            boolean validarDiff = diff < range.getFrecuency();
            Date iniMuestra = initFrecuency;
            boolean endCiclo = false;
            for (int tope : topeMuestraMinutos) {
                if (validarDiff) {
                    if (tope > diff) {
                        tope = (int) diff;
                        endCiclo = true;
                    }
                }
                Date tomaMuestra = getRandomDateBetween(iniMuestra, DateUtils.addMinutes(initFrecuency, tope));
                if (tomaMuestra.after(DateUtils.addMinutes(range.getEndTime(), -duracionMinutes))) {
                    log.error("tomaMuestra=" + DateFormatUtils.format(tomaMuestra, "HH:mm")
                        + " endTime=" + DateFormatUtils.format(range.getEndTime(), "HH:mm")
                        + " duracionMinutes=" + duracionMinutes);
                }
                ExtractionPlanRangeDetail detail = new ExtractionPlanRangeDetail();
                detail.setExtractionPlanRange(range);
                detail.setTimeSample(tomaMuestra);
                details.add(detail);
                iniMuestra = DateUtils.addMinutes(initFrecuency, tope + duracionMinutes);
                if (endCiclo) {
                    break;
                }
            }
            initFrecuency = DateUtils.addMinutes(initFrecuency, range.getFrecuency());
        }
        log.debug("end");
        return details;
    }

    public int aleatorio(int min, int max) {
        return (int) (Math.random() * (max - min)) + min;
    }

    public void saveExtractionPlanRangeDetail(ExtractionPlanRangeDetail detail) {
        //alamacena un ExtractionPlanRangeDetail en base de datos
        getSaveExtractionPlanRangeDetailCommand().execute(detail);
    }

    public SaveExtractionPlanRangeDetailCommand getSaveExtractionPlanRangeDetailCommand() {
        if (saveExtractionPlanRangeDetailCommand == null) {
            saveExtractionPlanRangeDetailCommand = new SaveExtractionPlanRangeDetailCommand();
        }
        return saveExtractionPlanRangeDetailCommand;
    }

    public void setSaveExtractionPlanRangeDetailCommand(SaveExtractionPlanRangeDetailCommand value) {
        this.saveExtractionPlanRangeDetailCommand = value;
    }

    public void saveSensors(ExtractionPlanCustomizing planCustomizing, List<Integer> sensorIds) {
        getSaveSensorsCommand().execute(planCustomizing, sensorIds);
    }

    public SaveSensorsCommand getSaveSensorsCommand() {
        if (saveSensorsCommand == null) {
            saveSensorsCommand = new SaveSensorsCommand();
        }
        return saveSensorsCommand;
    }

    public void setSaveSensorsCommand(SaveSensorsCommand saveSensorsCommand) {
        this.saveSensorsCommand = saveSensorsCommand;
    }

    public List<ExtractionPlanCustomizingDTO> getExtractionPlanCustomizingDTOs(Integer storeId, String estado,
        long sessionId) throws ScopixException {
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        Store store = new Store();
        store.setId(storeId);
        epc.setStore(store);

        List<ExtractionPlanCustomizing> lista = getExtractionPlanCustomizingList(epc, estado, sessionId);
        //Solo retornamos los epc donde el situation template este activo
        List<ExtractionPlanCustomizing> customizings = new ArrayList<ExtractionPlanCustomizing>();
        for (ExtractionPlanCustomizing customizing : lista) {
            if (customizing.getSituationTemplate().getActive().equals(Boolean.TRUE)) {
                customizings.add(customizing);
            }
        }
        List<ExtractionPlanCustomizingDTO> extractionPlanCustomizingDTOs
            = getTransformExtractionPlanCustomizingsToDTOCommand().execute(customizings);
        return extractionPlanCustomizingDTOs;
    }

    public GetExtractionPlanCustomizingListCommand getExtractionPlanCustomizingListCommand() {
        if (extractionPlanCustomizingListCommand == null) {
            extractionPlanCustomizingListCommand = new GetExtractionPlanCustomizingListCommand();
        }
        return extractionPlanCustomizingListCommand;
    }

    public void setExtractionPlanCustomizingListCommand(GetExtractionPlanCustomizingListCommand value) {
        this.extractionPlanCustomizingListCommand = value;
    }

    public TransformExtractionPlanCustomizingsToDTOCommand getTransformExtractionPlanCustomizingsToDTOCommand() {
        if (transformExtractionPlanCustomizingsToDTOCommand == null) {
            transformExtractionPlanCustomizingsToDTOCommand = new TransformExtractionPlanCustomizingsToDTOCommand();
        }
        return transformExtractionPlanCustomizingsToDTOCommand;
    }

    public void setTransformExtractionPlanCustomizingsToDTOCommand(TransformExtractionPlanCustomizingsToDTOCommand value) {
        this.transformExtractionPlanCustomizingsToDTOCommand = value;
    }

    public ExtractionPlanCustomizingDTO createExtractionPlanCustomizingDTO(Integer situationTempleteId, Integer storeId,
        long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.CREATE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        String userName = getSecurityManager().getUserName(sessionId);
        return createExtractionPlanCustomizingDTO(situationTempleteId, storeId, userName);
    }

    private ExtractionPlanCustomizingDTO createExtractionPlanCustomizingDTO(Integer situationTempleteId, Integer storeId,
        String userName) throws ScopixException {
        if (situationTempleteId == null || storeId == null) {
            throw new ScopixException("NOT_SITUATION_TEMPLATE_OR_STORE"); //no se puede continuar sin store, st
        }

        SituationTemplate st = new SituationTemplate();
        st.setId(situationTempleteId);
        Store s = new Store();
        s.setId(storeId);
        ExtractionPlanCustomizing customizing = new ExtractionPlanCustomizing();
        customizing.setSituationTemplate(st);
        customizing.setStore(s);

        List<ExtractionPlanCustomizing> epcs = getExtractionPlanCustomizingListCommand().execute(customizing, "EDITION");
        if (!epcs.isEmpty()) {
            //no se puede continuar si existe uno en edicion
            throw new ScopixException("EXIST_EPC_SITUATION_TEMPLATE_STORE");
        }
        //debemos crear el epc necesario

        ExtractionPlanCustomizing epc = getCreateExtractionPlanCustomizingCommand().execute(st, s, userName);
        //antes de continuar nos preocupamos que existe una area_store para el epc que se esta creando
        Area area = null;
        for (Area a : epc.getAreaType().getAreas()) {
            if (a.getStore().getId().equals(epc.getStore().getId())) {
                //asumimos que es el area que esta entrando
                area = a;
                break;
            }
        }

        //si no existe el area la creamos para no perder evidencias 
        if (area == null) {
            area = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class).createNewArea(epc.getAreaType(),
                epc.getStore());
            log.debug("se crea nueva area " + area);
        }

        //transformamos el epc a dto y lo enviamos        
        ExtractionPlanCustomizingDTO dto = getTransformExtractionPlanCustomizingToDTOCommand().execute(epc);
        return dto;
    }

    public CreateExtractionPlanCustomizingCommand getCreateExtractionPlanCustomizingCommand() {
        if (createExtractionPlanCustomizingCommand == null) {
            createExtractionPlanCustomizingCommand = new CreateExtractionPlanCustomizingCommand();
        }
        return createExtractionPlanCustomizingCommand;
    }

    public void setCreateExtractionPlanCustomizingCommand(CreateExtractionPlanCustomizingCommand value) {
        this.createExtractionPlanCustomizingCommand = value;
    }

    public TransformExtractionPlanCustomizingToDTOCommand getTransformExtractionPlanCustomizingToDTOCommand() {
        if (transformExtractionPlanCustomizingToDTOCommand == null) {
            transformExtractionPlanCustomizingToDTOCommand = new TransformExtractionPlanCustomizingToDTOCommand();
        }
        return transformExtractionPlanCustomizingToDTOCommand;
    }

    public void setTransformExtractionPlanCustomizingToDTOCommand(TransformExtractionPlanCustomizingToDTOCommand value) {
        this.transformExtractionPlanCustomizingToDTOCommand = value;
    }

    /**
     * Retorna la lista de Evidence Provider para un store situation template
     *
     * @param storeId
     * @param situationTemplateId
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public List<EvidenceProviderDTO> getEvidenceProvidersStoreSituationTemplate(Integer storeId, Integer situationTemplateId,
        long sessionId) throws ScopixException {
        log.info("start [storeId:" + storeId + "][situationTemplateId:" + situationTemplateId + "]");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_EVIDENCE_PROVIDER_PERMISSION);
        List<EvidenceProviderDTO> ret = getEvidenceProvidersStoreSituationTemplate(storeId, situationTemplateId);
        log.info("end [providers:" + ret.size() + "]");
        return ret;
    }

    private List<EvidenceProviderDTO> getEvidenceProvidersStoreSituationTemplate(Integer storeId, Integer situationTemplateId)
        throws ScopixException {
        log.info("start [storeId:" + storeId + "][situationTemplateId:" + situationTemplateId + "]");
        if (situationTemplateId == null || storeId == null) {
            throw new ScopixException("situationTemplateId or storeId is null");
        }

        //SituationTemplate st = getSituationTemplateCommand().execute(situationTemplateId);
        //para recupera la lista segun criterios
        EvidenceProvider evidenceProvider = new EvidenceProvider();
        evidenceProvider.setStore(new Store());
        evidenceProvider.getStore().setId(storeId);
        /**
         * Mejorar para ocupar el areType de la situacion UPDATE Se saca nuevamente
         */
        //evidenceProvider.setAreas(st.getAreaType().getAreas());
        List<EvidenceProvider> evidenceProviders = getEvidenceProviderListCommand().execute(evidenceProvider);
        log.info("end " + evidenceProviders.size());
        return getTransformEvidenceProvidersToDTOCommand().execute(evidenceProviders);
    }

    public GetSituationTemplateCommand getSituationTemplateCommand() {
        if (situationTemplateCommand == null) {
            situationTemplateCommand = new GetSituationTemplateCommand();
        }
        return situationTemplateCommand;
    }

    public void setSituationTemplateCommand(GetSituationTemplateCommand situationTemplateCommand) {
        this.situationTemplateCommand = situationTemplateCommand;
    }

    public GetExtractionPlanCustomizingByIdCommand getExtractionPlanCustomizingByIdCommand() {
        if (extractionPlanCustomizingByIdCommand == null) {
            extractionPlanCustomizingByIdCommand = new GetExtractionPlanCustomizingByIdCommand();
        }
        return extractionPlanCustomizingByIdCommand;
    }

    public void setExtractionPlanCustomizingByIdCommand(GetExtractionPlanCustomizingByIdCommand value) {
        this.extractionPlanCustomizingByIdCommand = value;
    }

    public GetEvidenceProviderListCommand getEvidenceProviderListCommand() {
        if (evidenceProviderListCommand == null) {
            evidenceProviderListCommand = new GetEvidenceProviderListCommand();
        }
        return evidenceProviderListCommand;
    }

    public void setEvidenceProviderListCommand(GetEvidenceProviderListCommand evidenceProviderListCommand) {
        this.evidenceProviderListCommand = evidenceProviderListCommand;
    }

    public TransformEvidenceProvidersToDTOCommand getTransformEvidenceProvidersToDTOCommand() {
        if (transformEvidenceProvidersToDTOCommand == null) {
            transformEvidenceProvidersToDTOCommand = new TransformEvidenceProvidersToDTOCommand();
        }
        return transformEvidenceProvidersToDTOCommand;
    }

    public void setTransformEvidenceProvidersToDTOCommand(TransformEvidenceProvidersToDTOCommand value) {
        this.transformEvidenceProvidersToDTOCommand = value;
    }

    public List<SituationSensorDTO> getSensors(Integer extractionPlanCustomizingId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_SENSOR_PERMISSION);

        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc == null) {
            throw new ScopixException("NOT_EXIST_EPC_REQUESTED"); //no existe epc solicitado
        }

        Sensor sensor = new Sensor();
        sensor.setArea(new Area());
        sensor.setStore(new Store());
        sensor.getStore().setId(epc.getStore().getId());
        sensor.getArea().setAreaType(epc.getSituationTemplate().getAreaType());

        List<Sensor> sensors = getSensorListCommand().execute(sensor);

        return getTransformSensorsToDTOCommand().execute(sensors);
    }

    public GetSensorListCommand getSensorListCommand() {
        if (sensorListCommand == null) {
            sensorListCommand = new GetSensorListCommand();
        }
        return sensorListCommand;
    }

    public void setSensorListCommand(GetSensorListCommand sensorListCommand) {
        this.sensorListCommand = sensorListCommand;
    }

    public TransformSensorsToDTOCommand getTransformSensorsToDTOCommand() {
        if (transformSensorsToDTOCommand == null) {
            transformSensorsToDTOCommand = new TransformSensorsToDTOCommand();
        }
        return transformSensorsToDTOCommand;
    }

    public void setTransformSensorsToDTOCommand(TransformSensorsToDTOCommand transformSensorsToDTOCommand) {
        this.transformSensorsToDTOCommand = transformSensorsToDTOCommand;
    }

    public List<MetricTemplateDTO> getMetricTemplates(Integer extractionPlanCustomizingId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.LIST_METRIC_TEMPLATE_PERMISSION);

        return getMetricTemplateDTO(extractionPlanCustomizingId);
    }

    private List<MetricTemplateDTO> getMetricTemplateDTO(Integer extractionPlanCustomizingId) throws ScopixException {
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc == null) {
            throw new ScopixException("NOT_EXIST_EPC_REQUESTED"); //no existe epc solicitado
        }
        List<MetricTemplate> metricTemplates = new ArrayList<MetricTemplate>();

        List<MetricTemplateDTO> metricTemplateDTOs = null;
        if (epc.getSituationTemplate() != null) {
            metricTemplates = epc.getSituationTemplate().getMetricTemplate();
            LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
            cols.put("description", Boolean.FALSE);
            SortUtil.sortByColumn(cols, metricTemplates);

            metricTemplateDTOs = getTransformMetricTemplateToDTOCommand().execute(metricTemplates);
        }

        return metricTemplateDTOs;
    }

    public TransformMetricTemplateToDTOCommand getTransformMetricTemplateToDTOCommand() {
        if (transformMetricTemplateToDTOCommand == null) {
            transformMetricTemplateToDTOCommand = new TransformMetricTemplateToDTOCommand();
        }
        return transformMetricTemplateToDTOCommand;
    }

    public void setTransformMetricTemplateToDTOCommand(TransformMetricTemplateToDTOCommand transformMetricTemplateToDTOCommand) {
        this.transformMetricTemplateToDTOCommand = transformMetricTemplateToDTOCommand;
    }

    public ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingNoEnviado(Integer situationTempleteId, Integer storeId,
        long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);

        return getUltimoExtractionPlanCustomizingNoEnviado(situationTempleteId, storeId);
    }

    private ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingNoEnviado(Integer situationTempleteId,
        Integer storeId) throws ScopixException {
        if (situationTempleteId == null || storeId == null) {
            //no se puede continuar sin store, st
            throw new ScopixException("NOT_SITUATION_TEMPLATE_OR_STORE");
            //no se reciben datos necesarios
        }
        SituationTemplate st = new SituationTemplate();
        st.setId(situationTempleteId);
        Store s = new Store();
        s.setId(storeId);
        ExtractionPlanCustomizing customizing = new ExtractionPlanCustomizing();
        customizing.setSituationTemplate(st);
        customizing.setStore(s);

        List<ExtractionPlanCustomizing> epcs = getExtractionPlanCustomizingListCommand().execute(customizing,
            GetExtractionPlanCustomizingListCommand.EDITION);
        if (epcs.isEmpty()) {
            throw new ScopixException("NOT_EXIST_EPC_EDITION_SITUATION_TEMPLATE_STORE");
        }
        if (epcs.size() > 1) {
            throw new ScopixException("EXIST_MULTIPLE_EPC_EDITION_SITUATION_TEMPLATE_STORE");
        }

        return getTransformEPCGeneralToDTOCommand().execute(epcs.get(0));
    }

    public TransformEPCGeneralToDTOCommand getTransformEPCGeneralToDTOCommand() {
        if (transformEPCGeneralToDTOCommand == null) {
            transformEPCGeneralToDTOCommand = new TransformEPCGeneralToDTOCommand();
        }
        return transformEPCGeneralToDTOCommand;
    }

    public void setTransformEPCGeneralToDTOCommand(TransformEPCGeneralToDTOCommand transformEPCGeneralToDTOCommand) {
        this.transformEPCGeneralToDTOCommand = transformEPCGeneralToDTOCommand;
    }

    /**
     * Recupera el ultimo ExtractionPlanCustomizing enviado
     */
    public ExtractionPlanCustomizingDTO getUltimoExtractionPlanCustomizingEnviado(Integer situationTempleteId, Integer storeId,
        long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);

        if (situationTempleteId == null || storeId == null) {
            //no se puede continuar sin store, st
            throw new ScopixException("NOT_SITUATION_TEMPLATE_OR_STORE"); //no se reciben datos necesarios
        }
        SituationTemplate st = new SituationTemplate();
        st.setId(situationTempleteId);
        Store s = new Store();
        s.setId(storeId);
        ExtractionPlanCustomizing customizing = new ExtractionPlanCustomizing();
        customizing.setSituationTemplate(st);
        customizing.setStore(s);

        List<ExtractionPlanCustomizing> epcs = getExtractionPlanCustomizingListCommand().execute(customizing,
            GetExtractionPlanCustomizingListCommand.SENT);
        if (epcs.isEmpty()) {
            throw new ScopixException("NOT_EXIST_EPC_SENT_SITUATION_TEMPLATE_STORE");
        }
        if (epcs.size() > 1) {
            throw new ScopixException("EXIST_MULTIPLE_EPC_SEND_SITUATION_TEMPLATE_STORE");
        }
        return getTransformEPCGeneralToDTOCommand().execute(epcs.get(0));
    }

    public ExtractionPlanCustomizingDTO getExtractionPlanCustomizingDatosGenerales(Integer extractionPlanCustomizingId,
        long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        ExtractionPlanCustomizing customizing = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (customizing == null) {
            throw new ScopixException("NOT_EXIST_EPC_REQUESTED"); //no existe EPC solicitado
        }

        return getTransformEPCGeneralToDTOCommand().execute(customizing);
    }

    public List<ExtractionPlanRangeDTO> getExtractionPlanRanges(Integer extractionPlanCustomizingId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_RANGES_LIST_PERMISSION);

        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(extractionPlanCustomizingId);
        //recuperamos la lista de ExtractionPlanRange para el EPC
        List<ExtractionPlanRange> planRanges = getExtractionPlanRangeListByIdEPCCommand().execute(epc);

        //transformamos la lista en dtos y la retornamos
        return getTransformExtractionPlanRangesToDTOCommand().execute(planRanges);
    }

    public GetExtractionPlanRangeListByIdEPCCommand getExtractionPlanRangeListByIdEPCCommand() {
        if (extractionPlanRangeListByIdEPCCommand == null) {
            extractionPlanRangeListByIdEPCCommand = new GetExtractionPlanRangeListByIdEPCCommand();
        }
        return extractionPlanRangeListByIdEPCCommand;
    }

    public void setExtractionPlanRangeListByIdEPCCommand(GetExtractionPlanRangeListByIdEPCCommand value) {
        this.extractionPlanRangeListByIdEPCCommand = value;
    }

    public TransformExtractionPlanRangesToDTOCommand getTransformExtractionPlanRangesToDTOCommand() {
        if (transformExtractionPlanRangesToDTOCommand == null) {
            transformExtractionPlanRangesToDTOCommand = new TransformExtractionPlanRangesToDTOCommand();
        }
        return transformExtractionPlanRangesToDTOCommand;
    }

    public void setTransformExtractionPlanRangesToDTOCommand(TransformExtractionPlanRangesToDTOCommand value) {
        this.transformExtractionPlanRangesToDTOCommand = value;
    }

    public List<ExtractionPlanRangeDetailDTO> getExtractionPlanRangeDetails(Integer extractionPlanRangeId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_RANGES_DETAIL_LIST_PERMISSION);
        ExtractionPlanRange planRange = new ExtractionPlanRange();
        planRange.setId(extractionPlanRangeId);
        //recuperamos todos los detalles de un planRange

        List<ExtractionPlanRangeDetail> planRangeDetails = getExtractionPlanRangeDetailListByIdEPRCommand().execute(planRange);

        return getTransformExtractionPlanRangeDetailToDTOCommand().execute(planRangeDetails);
    }

    public GetExtractionPlanRangeDetailListByIdEPRCommand getExtractionPlanRangeDetailListByIdEPRCommand() {
        if (extractionPlanRangeDetailListByIdEPRCommand == null) {
            extractionPlanRangeDetailListByIdEPRCommand = new GetExtractionPlanRangeDetailListByIdEPRCommand();
        }
        return extractionPlanRangeDetailListByIdEPRCommand;
    }

    public void setExtractionPlanRangeDetailListByIdEPRCommand(GetExtractionPlanRangeDetailListByIdEPRCommand value) {
        this.extractionPlanRangeDetailListByIdEPRCommand = value;
    }

    public TransformExtractionPlanRangeDetailToDTOCommand getTransformExtractionPlanRangeDetailToDTOCommand() {
        if (transformExtractionPlanRangeDetailToDTOCommand == null) {
            transformExtractionPlanRangeDetailToDTOCommand = new TransformExtractionPlanRangeDetailToDTOCommand();
        }
        return transformExtractionPlanRangeDetailToDTOCommand;
    }

    public void setTransformExtractionPlanRangeDetailToDTOCommand(TransformExtractionPlanRangeDetailToDTOCommand value) {
        this.transformExtractionPlanRangeDetailToDTOCommand = value;
    }

    public void saveEPCGeneral(ExtractionPlanCustomizingDTO planCustomizingDTO, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_WIZARD_CUSTOMIZING_PERMISSION);
        saveEPCGeneral(planCustomizingDTO);
        log.info("end");
    }

    private void saveEPCGeneral(ExtractionPlanCustomizingDTO planCustomizingDTO) throws ScopixException {
        log.info("start");
        //recuperamos le epc para el id del dto
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(planCustomizingDTO.getId());
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC"); //ya fue enviado o anulado
        }

        List<Integer> sensors = new ArrayList<Integer>();
        for (int s : planCustomizingDTO.getSensorIds()) {
            sensors.add(Integer.valueOf(s));
        }

        List<ExtractionPlanMetricDTO> metricDTOs = new ArrayList<ExtractionPlanMetricDTO>();
        for (ExtractionPlanMetricDTO dTO : planCustomizingDTO.getExtractionPlanMetricDTOs()) {
            metricDTOs.add(dTO);
        }
        saveSensors(epc, sensors);

        saveExtractionPlanMetrics(epc, metricDTOs);
        epc.setOneEvaluation(planCustomizingDTO.getOneEvaluation());
        //recuperar del campo nuevo priorizacion del dto
        epc.setPriorization(null);
        if (planCustomizingDTO.getPriorization() != null && planCustomizingDTO.getPriorization() != 0) {
            epc.setPriorization(planCustomizingDTO.getPriorization());
        }
        epc.setRandomCamera(planCustomizingDTO.getRandomCamera());
        getSaveExtractionPlanCustomizingCommand().execute(epc);
        log.debug("end");
    }

    /**
     * @return the saveExtractionPlanCustomizingCommand
     */
    public SaveExtractionPlanCustomizingCommand getSaveExtractionPlanCustomizingCommand() {
        if (saveExtractionPlanCustomizingCommand == null) {
            saveExtractionPlanCustomizingCommand = new SaveExtractionPlanCustomizingCommand();
        }
        return saveExtractionPlanCustomizingCommand;
    }

    /**
     * @param value the saveExtractionPlanCustomizingCommand to set
     */
    public void setSaveExtractionPlanCustomizingCommand(SaveExtractionPlanCustomizingCommand value) {
        this.saveExtractionPlanCustomizingCommand = value;
    }

    //graba un ExtractionPlanRange y genera sus detalles
    public ExtractionPlanRangeDTO saveExtractionPlanRange(Integer extractionPlanCustomizingId,
        ExtractionPlanRangeDTO planRangeDTO, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION);

        ExtractionPlanRangeDTO ret = saveExtractionPlanRange(extractionPlanCustomizingId, planRangeDTO);
        log.info("end");
        return ret;
    }

    private ExtractionPlanRangeDTO saveExtractionPlanRange(Integer extractionPlanCustomizingId,
        ExtractionPlanRangeDTO planRangeDTO) throws ScopixException {
        log.debug("start");
        //recuperamos el epc asociado
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC");
        }
        //transformamos el DTO en un rango valido para el sistema no lo recupera desde base de datos
        ExtractionPlanRange planRange = getTransformDTOtoExtractionPlanRangeCommand().execute(epc, planRangeDTO);
        boolean regenerateDetail = true;
        ExtractionPlanRange planRangeExisting = null;
        if (planRange.getId() != null && planRange.getId() > 0) {
            planRangeExisting = getExtractionPlanRangeByIdCommand().execute(planRange.getId());
            regenerateDetail = differentEPR(planRange, planRangeExisting);
            if (regenerateDetail) {
                copyDataExtractionPlanRange(planRange, planRangeExisting);
            }
        }
        //guardamos y generamos sus detalles
        if (planRangeExisting != null) {
            saveExtractionPlanRange(planRangeExisting, regenerateDetail);
        } else {
            saveExtractionPlanRange(planRange, regenerateDetail);
        }

        log.debug("end");
        return getTransformExtractionPlanRangeToDTOCommand().execute(planRange);
    }

    private void copyDataExtractionPlanRange(ExtractionPlanRange from, ExtractionPlanRange to) {
        //copiamos los datos desde planRange a planRangeExisting
        to.setInitialTime(from.getInitialTime());
        to.setEndTime(from.getEndTime());
        to.setSamples(from.getSamples());
        to.setFrecuency(from.getFrecuency());
        to.setDuration(from.getDuration());
        to.setDayOfWeek(from.getDayOfWeek());
        to.setExtractionPlanRangeType(from.getExtractionPlanRangeType());
    }

    /**
     * busca diferencias entre los ExtractionPlanRange si existe retorna true
     *
     */
    protected boolean differentEPR(ExtractionPlanRange planRange, ExtractionPlanRange planRangeExisting) {
        boolean ret = false;
        ret = ret || (!planRange.getDayOfWeek().equals(planRangeExisting.getDayOfWeek()));
        ret = ret || (!planRange.getDuration().equals(planRangeExisting.getDuration()));
        if (planRange.getEndTime() != null && planRangeExisting.getEndTime() != null) {
            ret = ret || (!planRange.getEndTime().equals(planRangeExisting.getEndTime()));
        } else {
            ret = ret || (planRange.getEndTime() != planRangeExisting.getEndTime());
        }
        if (planRange.getInitialTime() != null && planRangeExisting.getInitialTime() != null) {
            ret = ret || (!planRange.getInitialTime().equals(planRangeExisting.getInitialTime()));
        } else {
            ret = ret || (planRange.getInitialTime() != planRangeExisting.getInitialTime());
        }
        ret = ret || (!planRange.getFrecuency().equals(planRangeExisting.getFrecuency()));
        ret = ret || (!planRange.getSamples().equals(planRangeExisting.getSamples()));

        if (planRange.getExtractionPlanRangeType() != null && planRangeExisting.getExtractionPlanRangeType() != null) {
            ret = ret || (!planRange.getExtractionPlanRangeType().equals(planRangeExisting.getExtractionPlanRangeType()));
        } else {
            ret = ret || (planRange.getExtractionPlanRangeType() != planRangeExisting.getExtractionPlanRangeType());
        }
        return ret;
    }

    public TransformDTOtoExtractionPlanRangeCommand getTransformDTOtoExtractionPlanRangeCommand() {
        if (transformDTOtoExtractionPlanRangeCommand == null) {
            transformDTOtoExtractionPlanRangeCommand = new TransformDTOtoExtractionPlanRangeCommand();
        }
        return transformDTOtoExtractionPlanRangeCommand;
    }

    public void setTransformDTOtoExtractionPlanRangeCommand(TransformDTOtoExtractionPlanRangeCommand value) {
        this.transformDTOtoExtractionPlanRangeCommand = value;
    }

    /**
     * ocupado para la generacion masiva de epc
     */
    public void generateDetallesRanges() throws ScopixException {
        StringBuffer cantEpcProcesados = new StringBuffer();
        try {
            GetExtractionPlanRangeListByIdEPCCommand eprCommand = new GetExtractionPlanRangeListByIdEPCCommand();
            ExtractionPlanCustomizing epcFiltro = new ExtractionPlanCustomizing();

            List<ExtractionPlanCustomizing> planCustomizings
                = getExtractionPlanCustomizingListCommand().execute(epcFiltro, "SENT");
            for (ExtractionPlanCustomizing epc : planCustomizings) {
                List<ExtractionPlanRange> ranges = eprCommand.execute(epc);
                log.debug("start process epcId=" + epc.getId().toString() + " ranges " + ranges.size());
                saveExtractionPlanRanges(epc, ranges);
                cantEpcProcesados.append(epc.getId().toString());
                cantEpcProcesados.append(",");
                log.debug("end process epcId=" + epc.getId().toString());
            }
        } catch (Exception e) {
            log.debug("Procesados " + cantEpcProcesados);
            log.error("Error " + e, e);
            throw new ScopixException(e);
        }
    }

    public List<ExtractionPlanRangeDetailDTO> regenerateDetailForRange(Integer extractionPlanCustomizingId,
        ExtractionPlanRangeDTO planRangeDTO, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION);

        //recuperamos el epc asociado
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC");
        }
        ExtractionPlanRange planRange = getTransformDTOtoExtractionPlanRangeCommand().execute(epc, planRangeDTO);
        saveExtractionPlanRange(planRange, true);

        List<ExtractionPlanRangeDetailDTO> detailDTOs
            = getTransformExtractionPlanRangeDetailToDTOCommand().execute(planRange.getExtractionPlanRangeDetails());
        return detailDTOs;
    }

    /**
     * Retorna las posibles acciones al copiar un calendario en EPC
     */
    public List<String> copyCalendarActions(Integer situationTemplateId, Integer storeId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.COPY_EXTRACTION_PLAN_RANGE_PERMISSION);
        List<String> actions = new ArrayList<String>();
        if (situationTemplateId == null || storeId == null) {
            throw new ScopixException("NOT_SITUATION_TEMPLATE_OR_STORE");
        }
        //existe epc para situationTemplateId storeId
        //sino exite agregamos el mensaje que se va a crear un epc y su calendario
        ExtractionPlanCustomizing epcFiltro = new ExtractionPlanCustomizing();
        SituationTemplate st = new SituationTemplate();
        st.setId(situationTemplateId);
        Store store = new Store();
        store.setId(storeId);
        epcFiltro.setSituationTemplate(st);
        epcFiltro.setStore(store);

        //recuperamos los epc en edicion para un st store
        List<ExtractionPlanCustomizing> epcEditions = getExtractionPlanCustomizingList(epcFiltro,
            GetExtractionPlanCustomizingListCommand.EDITION, sessionId);
        if (epcEditions.size() > 1) {
            throw new ScopixException("EXIST_MULTIPLE_EPC_EDITION_SITUATION_TEMPLATE_STORE"); //Existe mas de 1 Epc en edición
        }

        if (epcEditions.isEmpty()) {
            actions.add("CREATE_NEW_EPC"); //Se crea un nuevo Epc para SituationTemplate store con calendario");
        } else {
            ExtractionPlanCustomizing epcEdition = epcEditions.get(0);
            if (epcEdition.getExtractionPlanRanges().isEmpty()) {
                actions.add("CREATION_CALENDAR_BY_REQUESTED"); //Se creara el calendario basado en la solicitud
            } else {
                actions.add("REPLACE_CALENDAR_EPC"); //Se reemplazara el calendario para el epc " + epcEdition.getId());
            }
        }
        return actions;
    }

    public void copyCalendar(Integer extractionPlanCustomizingId, Integer situationTemplateId, Integer storeId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.COPY_EXTRACTION_PLAN_RANGE_PERMISSION);

        //Recuperamos el epc de donde se sacaran los datos
        ExtractionPlanCustomizing epcOrigen = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (situationTemplateId == null || storeId == null) {
            throw new ScopixException("NOT_SITUATION_TEMPLATE_OR_STORE");
        }

        //recuperamos los epc en edicion para un st store
        ExtractionPlanCustomizing epcFiltro = new ExtractionPlanCustomizing();
        SituationTemplate st = new SituationTemplate();
        st.setId(situationTemplateId);
        Store store = new Store();
        store.setId(storeId);
        epcFiltro.setSituationTemplate(st);
        epcFiltro.setStore(store);
        List<ExtractionPlanCustomizing> epcEditions = getExtractionPlanCustomizingList(epcFiltro,
            GetExtractionPlanCustomizingListCommand.EDITION, sessionId);

        if (epcEditions.size() > 1) {
            throw new ScopixException("EXIST_MULTIPLE_EPC_EDITION_SITUATION_TEMPLATE_STORE"); //Existe mas de 1 Epc en edición
        }

        ExtractionPlanCustomizing epcEdition = null;
        if (epcEditions.isEmpty()) {
            //creamos nuevo epc
            String userName = getSecurityManager().getUserName(sessionId);
            epcEdition = getCreateExtractionPlanCustomizingCommand().execute(st, store, userName);
        } else {
            epcEdition = epcEditions.get(0);
        }
        if (epcEdition.getExtractionPlanRanges().size() > 0) {
            //borramos rango y detalles actuales            
            Integer[] days = new Integer[]{1, 2, 3, 4, 5, 6, 7};
            getRemoveExtractionPlanRangesDaysCommand().execute(epcEdition, Arrays.asList(days));
            //epcEdition.getExtractionPlanRanges().clear();
        }
        copyExtractionPlanRange(epcOrigen, epcEdition);
        //copiamos rangos y detelles
    }

    public SaveExtractionPlanRangesCommnad getSaveExtractionPlanRangesCommnad() {
        if (saveExtractionPlanRangesCommnad == null) {
            saveExtractionPlanRangesCommnad = new SaveExtractionPlanRangesCommnad();
        }
        return saveExtractionPlanRangesCommnad;
    }

    public void setSaveExtractionPlanRangesCommnad(SaveExtractionPlanRangesCommnad saveExtractionPlanRangesCommnad) {
        this.saveExtractionPlanRangesCommnad = saveExtractionPlanRangesCommnad;
    }

    public void copyDayInDays(Integer extractionPlanCustomizingId, Integer day, List<Integer> days, Boolean copyDetail,
        long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.COPY_EXTRACTION_PLAN_RANGE_PERMISSION);
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC"); //el epc ya fue enviado
        }

        //recuperamos el rangos a copiar
        List<ExtractionPlanRange> rangosDay = epc.getExtractionPlanRangesByDay(day);
        //limpiamos los rangos dias que llegan en days

        getRemoveExtractionPlanRangesDaysCommand().execute(epc, days);
        //si copyDetail true copiamos los rangos y detalles
        List<ExtractionPlanRange> newRanges = getCopyExtractionPlanRangeInDaysComnad().execute(epc, rangosDay, days);
        if (copyDetail.equals(Boolean.TRUE)) {
            getSaveExtractionPlanRangesCommnad().execute(newRanges);
        } else {
            //si no regeneramos los detalles
            for (ExtractionPlanRange range : newRanges) {
                saveExtractionPlanRange(range, true);
            }
        }
    }

    public RemoveExtractionPlanRangesDaysCommand getRemoveExtractionPlanRangesDaysCommand() {
        if (removeExtractionPlanRangesDaysCommand == null) {
            removeExtractionPlanRangesDaysCommand = new RemoveExtractionPlanRangesDaysCommand();
        }
        return removeExtractionPlanRangesDaysCommand;
    }

    public void setRemoveExtractionPlanRangesDaysCommand(RemoveExtractionPlanRangesDaysCommand value) {
        this.removeExtractionPlanRangesDaysCommand = value;
    }

    public CopyExtractionPlanRangeInDaysCommand getCopyExtractionPlanRangeInDaysComnad() {
        if (copyExtractionPlanRangeInDaysComnad == null) {
            copyExtractionPlanRangeInDaysComnad = new CopyExtractionPlanRangeInDaysCommand();
        }
        return copyExtractionPlanRangeInDaysComnad;
    }

    public void setCopyExtractionPlanRangeInDaysComnad(CopyExtractionPlanRangeInDaysCommand value) {
        this.copyExtractionPlanRangeInDaysComnad = value;
    }

    public void deleteExtractionPlanRange(Integer extractionPlanCustomizingId, Integer extractionPlanRangeId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.DELETE_EXTRACTION_PLAN_RANGE_PERMISSION);
        //recuperams el epc y revisamos que este en edicion
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC"); //el epc ya fue enviado
        }
        //primero borramos los detalles de este epr
        ExtractionPlanRange eprDelete = new ExtractionPlanRange();
        eprDelete.setId(extractionPlanRangeId);
        getCleanDetailExtractionPlanRangeCommand().execute(eprDelete);
        getDeleteExtractionPlanRangeByIdCommand().execute(extractionPlanRangeId);
    }

    public DeleteExtractionPlanRangeByIdCommand getDeleteExtractionPlanRangeByIdCommand() {
        if (deleteExtractionPlanRangeByIdCommand == null) {
            deleteExtractionPlanRangeByIdCommand = new DeleteExtractionPlanRangeByIdCommand();
        }
        return deleteExtractionPlanRangeByIdCommand;
    }

    public void setDeleteExtractionPlanRangeByIdCommand(DeleteExtractionPlanRangeByIdCommand value) {
        this.deleteExtractionPlanRangeByIdCommand = value;
    }

    public void regenerateDetailForEPC(Integer extractionPlanCustomizingId, List<Integer> days, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION);
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC"); //el epc ya fue enviado
        }
        //regenera todos los rangos para los dias recibidos
        for (Integer day : days) {
            for (ExtractionPlanRange range : epc.getExtractionPlanRangesByDay(day)) {
                saveExtractionPlanRange(range, true);
            }
        }
    }

    public ExtractionPlanRangeDTO getExtractionPlanRange(Integer extractionPlanRangeId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_RANGE_PERMISSION);
        //recuperamos el ExtractionPlanRange asociado al id
        ExtractionPlanRange planRange = getExtractionPlanRangeByIdCommand().execute(extractionPlanRangeId);
        ExtractionPlanRangeDTO planRangeDTO = getTransformExtractionPlanRangeToDTOCommand().execute(planRange);
        return planRangeDTO;
    }

    public GetExtractionPlanRangeByIdCommand getExtractionPlanRangeByIdCommand() {
        if (extractionPlanRangeByIdCommand == null) {
            extractionPlanRangeByIdCommand = new GetExtractionPlanRangeByIdCommand();
        }
        return extractionPlanRangeByIdCommand;
    }

    public void setExtractionPlanRangeByIdCommand(GetExtractionPlanRangeByIdCommand extractionPlanRangeByIdCommand) {
        this.extractionPlanRangeByIdCommand = extractionPlanRangeByIdCommand;
    }

    public TransformExtractionPlanRangeToDTOCommand getTransformExtractionPlanRangeToDTOCommand() {
        if (transformExtractionPlanRangeToDTOCommand == null) {
            transformExtractionPlanRangeToDTOCommand = new TransformExtractionPlanRangeToDTOCommand();
        }
        return transformExtractionPlanRangeToDTOCommand;
    }

    public void setTransformExtractionPlanRangeToDTOCommand(TransformExtractionPlanRangeToDTOCommand value) {
        this.transformExtractionPlanRangeToDTOCommand = value;
    }

    /**
     * Elimina los dias de un epc determinado
     */
    public void cleanExtractionPlanCutomizingDay(Integer extractionPlanCustomizingId, List<Integer> days, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.UPDATE_EXTRACTION_PLAN_RANGE_PERMISSION);
        cleanExtractionPlanCutomizingDay(extractionPlanCustomizingId, days);
    }

    private void cleanExtractionPlanCutomizingDay(Integer extractionPlanCustomizingId, List<Integer> days)
        throws ScopixException {
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epc.isActive() != null) {
            throw new ScopixException("NOT_EDITION_EPC"); //el epc ya fue enviado
        }
        //eliminamos todos los rangos para los dias recibidos
        getRemoveExtractionPlanRangesDaysCommand().execute(epc, days);
    }

    public List<DetalleSolicitudDTO> getDetalleSolicitudes(Integer extractionPlanCustomizingId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.GET_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        ExtractionPlanCustomizing epc = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        //verificar si se necesita relizar alguna validacion
        return getGenerateDetalleSolucitudDTOFromEPCCommand().execute(epc);
    }

    public GenerateDetalleSolucitudDTOFromEPCCommand getGenerateDetalleSolucitudDTOFromEPCCommand() {
        if (generateDetalleSolucitudDTOFromEPCCommand == null) {
            generateDetalleSolucitudDTOFromEPCCommand = new GenerateDetalleSolucitudDTOFromEPCCommand();
        }
        return generateDetalleSolucitudDTOFromEPCCommand;
    }

    public void setGenerateDetalleSolucitudDTOFromEPCCommand(GenerateDetalleSolucitudDTOFromEPCCommand value) {
        this.generateDetalleSolucitudDTOFromEPCCommand = value;
    }

    /**
     * copia un epc a en edicion para el mismo Situation Template / Store
     */
    public ExtractionPlanCustomizingDTO copyEPCToEdition(Integer extractionPlanCustomizingId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.COPY_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        //recuperamos el epc actual
        ExtractionPlanCustomizing epcOrigen = getExtractionPlanCustomizingByIdCommand().
            execute(extractionPlanCustomizingId);
        if (epcOrigen.isActive() == null) {
            throw new ScopixException("EPC_IN_EDITION"); //epc esta en edicion no se puede modificar
        }

        //revisamos si existe un epc en edicion para la tupla st y store
        List<ExtractionPlanCustomizing> list = getExtractionPlanCustomizingListCommand().execute(epcOrigen,
            GetExtractionPlanCustomizingListCommand.EDITION);
        ExtractionPlanCustomizing epcEdicion = null;
        if (list.isEmpty()) {
            //sino existe lo creamos y copiamos el calendario
            String userName = getSecurityManager().getUserName(sessionId);
            epcEdicion = getCreateExtractionPlanCustomizingCommand().execute(epcOrigen.getSituationTemplate(),
                epcOrigen.getStore(), userName);
        } else if (list.size() == 1) {
            //si existe borramos el calendario existente
            epcEdicion = list.get(0);
        } else {
            throw new ScopixException("EXIST_MULTIPLE_EPC_EDITION_SITUATION_TEMPLATE_STORE");
            //existen mas de 1 epc en edicion para st store
        }
        List<Integer> days = getListDay();

        cleanExtractionPlanCutomizingDay(epcEdicion.getId(), days, sessionId);
        //  copiamos el calendario
        copyExtractionPlanRange(epcOrigen, epcEdicion);
        //guardamos los rangos copiados
        getSaveExtractionPlanRangesCommnad().execute(epcEdicion.getExtractionPlanRanges());

        return getTransformExtractionPlanCustomizingToDTOCommand().execute(epcEdicion);
    }

    public void disableExtractionPlanCustomizings(List<Integer> epcIds, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.DISABLE_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        getDisableExtractionPlanCustomizingListCommand().execute(epcIds);
    }

    public Integer getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(Integer currentDay) {
        this.currentDay = currentDay;
    }

    public int getCurrentCreateRecord() {
        return currentCreateRecord;
    }

    public void setCurrentCreateRecord(int currentCreateRecord) {
        this.currentCreateRecord = currentCreateRecord;
    }

    public GetEPCFromStoreSituationTemplateEvidenceProviderCommand getEpcFromStoreSituationTemplateEvidenceProviderCommand() {
        if (epcFromStoreSituationTemplateEvidenceProviderCommand == null) {
            epcFromStoreSituationTemplateEvidenceProviderCommand = new GetEPCFromStoreSituationTemplateEvidenceProviderCommand();
        }
        return epcFromStoreSituationTemplateEvidenceProviderCommand;
    }

    public void setEpcFromStoreSituationTemplateEvidenceProviderCommand(
        GetEPCFromStoreSituationTemplateEvidenceProviderCommand value) {
        this.epcFromStoreSituationTemplateEvidenceProviderCommand = value;
    }

    public DisableExtractionPlanCustomizingListCommand getDisableExtractionPlanCustomizingListCommand() {
        if (disableExtractionPlanCustomizingListCommand == null) {
            disableExtractionPlanCustomizingListCommand = new DisableExtractionPlanCustomizingListCommand();
        }
        return disableExtractionPlanCustomizingListCommand;
    }

    public void setDisableExtractionPlanCustomizingListCommand(
        DisableExtractionPlanCustomizingListCommand disableExtractionPlanCustomizingListCommand) {
        this.disableExtractionPlanCustomizingListCommand = disableExtractionPlanCustomizingListCommand;
    }

    public CreateRecordsCommand getCreateRecordsCommand() {
        if (createRecordsCommand == null) {
            createRecordsCommand = new CreateRecordsCommand();
        }
        return createRecordsCommand;
    }

    public void setCreateRecordsCommand(CreateRecordsCommand createRecordsCommand) {
        this.createRecordsCommand = createRecordsCommand;
    }

    public ExtractionPlanCustomizingDTO copyEPCFullToEdition(Integer extractionPlanCustomizingId, long sessionId)
        throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, WizardManagerPermissions.COPY_EXTRACTION_PLAN_CUSTOMIZING_PERMISSION);
        //recuperamos el epc actual
        ExtractionPlanCustomizing epcOrigen = getExtractionPlanCustomizingByIdCommand().execute(extractionPlanCustomizingId);
        if (epcOrigen.isActive() == null) {
            throw new ScopixException("EPC_IN_EDITION"); //epc esta en edicion no se puede modificar
        }

        //revisamos si existe un epc en edicion para la tupla st y store
        List<ExtractionPlanCustomizing> list = getExtractionPlanCustomizingListCommand().execute(epcOrigen,
            GetExtractionPlanCustomizingListCommand.EDITION);
        ExtractionPlanCustomizing epcEdicion = null;
        if (list.isEmpty()) {
            //sino existe lo creamos y copiamos el calendario
            String userName = getSecurityManager().getUserName(sessionId);
            epcEdicion = getCreateExtractionPlanCustomizingCommand().execute(epcOrigen.getSituationTemplate(),
                epcOrigen.getStore(), userName);
        } else if (list.size() == 1) {
            //si existe borramos el calendario existente
            epcEdicion = list.get(0);
        } else {
            throw new ScopixException("EXIST_MULTIPLE_EPC_EDITION_SITUATION_TEMPLATE_STORE");
        }

        //Si el epOriginal contiene sensores se tratan e copiar al nuevo epEdicion
        if (epcEdicion != null && !epcOrigen.getSensors().isEmpty()) {
            log.debug("copiando sensores");
            List<Integer> sensors = new ArrayList<Integer>();
            for (Sensor s : epcOrigen.getSensors()) {
                sensors.add(Integer.valueOf(s.getId()));
            }
            log.debug("guardando sensores");
            saveSensors(epcEdicion, sensors);
        }

        List<Integer> days = getListDay();

        cleanExtractionPlanCutomizingDay(epcEdicion.getId(), days, sessionId);

        copyDataBasica(epcOrigen, epcEdicion);

        //limpiamos todos los epm para asi crear todo nuevamente por si existe algun cambio
        cleanExtractionPlanMetrics(epcEdicion);
        //  copiamos el calendario
        copyExtractionPlanRange(epcOrigen, epcEdicion);
        //copiamos las metricas
        copyExtractionPlanMetric(epcOrigen, epcEdicion);
        //guardamos los rangos copiados        
        getSaveExtractionPlanRangesCommnad().execute(epcEdicion.getExtractionPlanRanges());

        return getTransformExtractionPlanCustomizingToDTOCommand().execute(epcEdicion);
    }

    private void copyDataBasica(ExtractionPlanCustomizing epcOrigen, ExtractionPlanCustomizing epcEdicion) {
        epcEdicion.setOneEvaluation(epcOrigen.isOneEvaluation());
        epcEdicion.setPriorization(epcOrigen.getPriorization());
        epcEdicion.setRandomCamera(epcOrigen.isRandomCamera());
    }

    private void copyExtractionPlanRange(ExtractionPlanCustomizing epcOrigen, ExtractionPlanCustomizing epcEdition)
        throws ScopixException {
        try {
            List<ExtractionPlanRange> planRangesDestino = new ArrayList<ExtractionPlanRange>();
            for (ExtractionPlanRange planRange : epcOrigen.getExtractionPlanRanges()) {
                ExtractionPlanRange planRangeDestino = (ExtractionPlanRange) planRange.clone();
                planRangeDestino.setExtractionPlanCustomizing(epcEdition);
                planRangesDestino.add(planRangeDestino);
            }

            getSaveExtractionPlanRangesCommnad().execute(planRangesDestino);
        } catch (CloneNotSupportedException e) {
            log.error("Error " + e, e);
            throw new ScopixException("ERROR_CLON_RANGE");
        }
    }

    private void copyExtractionPlanMetric(ExtractionPlanCustomizing epcOrigen, ExtractionPlanCustomizing epcEdicion)
        throws ScopixException {
        try {
            List<ExtractionPlanMetric> planMetricsDestino = new ArrayList<ExtractionPlanMetric>();
            for (ExtractionPlanMetric planMetricOrigen : epcOrigen.getExtractionPlanMetrics()) {
                ExtractionPlanMetric planMetricDestino = (ExtractionPlanMetric) planMetricOrigen.clone();
                planMetricDestino.setExtractionPlanCustomizing(epcEdicion);
                planMetricsDestino.add(planMetricDestino);
            }

            //debido a que no existen restrictiones actuales no importa que provider esten habilitados, 
            // solo que el Area Pertenesca al Store
//            List<Area> areas = epcEdicion.getSituationTemplate().getAreaType().getAreas();
//            //revisamos si los ep que se agregaron corresponden a los de la situacion
//            for (ExtractionPlanMetric epm : planMetricsDestino) {
//                Iterator<EvidenceProvider> iterator = epm.getEvidenceProviders().iterator();
//                while (iterator.hasNext()) {
//                    EvidenceProvider ep = iterator.next();
//                    if (!pertenece(ep, areas)) {
//                        iterator.remove();
//                    }
//                }
//            }
            getSaveExtractionPlanMetricsCommand().execute(planMetricsDestino);
        } catch (CloneNotSupportedException e) {
            log.error("Error " + e, e);
            throw new ScopixException("ERROR_CLON_METRIC"); //no se pudo copiar el calendario", e);
        }
    }

    private boolean pertenece(EvidenceProvider ep, List<Area> areas) {
        boolean ret = false;
        for (Area a : areas) {
            //se modifica ya que el EvidenceProvider pertenece a muchas areas
            for (Area epArea : ep.getAreas()) {
                if (epArea.getId().equals(a.getId())) {
                    ret = true;
                    break;
                }
            }
            if (ret) {
                break;
            }
        }
        return ret;

    }

    public SaveExtractionPlanMetricsCommand getSaveExtractionPlanMetricsCommand() {
        if (saveExtractionPlanMetricsCommand == null) {
            saveExtractionPlanMetricsCommand = new SaveExtractionPlanMetricsCommand();
        }
        return saveExtractionPlanMetricsCommand;
    }

    public void setSaveExtractionPlanMetricsCommand(SaveExtractionPlanMetricsCommand saveExtractionPlanMetricsCommand) {
        this.saveExtractionPlanMetricsCommand = saveExtractionPlanMetricsCommand;
    }

    public CleanSensorsCommand getCleanSensorsCommand() {
        if (cleanSensorsCommand == null) {
            cleanSensorsCommand = new CleanSensorsCommand();
        }
        return cleanSensorsCommand;
    }

    public void setCleanSensorsCommand(CleanSensorsCommand cleanSensorsCommand) {
        this.cleanSensorsCommand = cleanSensorsCommand;
    }

    private void cleanSensors(List<Integer> sensors) throws ScopixException {
        getCleanSensorsCommand().execute(sensors);
    }

    public void processNewEPCCSV(File tmp) throws ScopixException {
        log.info("start");
        //read CSV for news EPC
        try {
            List<Store> stores = new GetStoreListCommand().execute(null);
            List<SituationTemplate> situationTemplates = new GetSituationTemplateListCommand().execute(null);
            List<Sensor> sensors = new GetSensorListCommand().execute(null);
            CsvReader csv = new CsvReader(new FileReader(tmp));
            char delimiter = CharUtils.toChar(",");
            csv.setDelimiter(delimiter);
            csv.readHeaders();
            //read all csv
            ExtractionPlanCustomizingDTO epcCurrentDTO = null;
            while (csv.readRecord()) {
                String storeName = csv.get("StoreName");
                String situationTemplateName = csv.get("SituationTemplate");
                String sensorName = csv.get("SensorName");

                log.debug("[store in csv:" + storeName + "][situationTemplate in csv:" + situationTemplateName + "]"
                    + "[sensor in csv: " + sensorName + "]");
                SituationTemplate situationTemplate = getSituationTemplate(situationTemplateName, situationTemplates);
                Store store = getStore(storeName, stores);

                if (store == null) {
                    log.debug("Store is null");
                    continue;
                }

                if (situationTemplate == null) {
                    log.debug("SituationTemplate is null");
                    continue;
                }

                Sensor sensor = null;
                if (sensorName != null) {
                    sensor = getSensor(sensorName, sensors);
                    if (sensor == null) {
                        log.debug("Sensor is null");
                        continue;
                    }
                }

                log.debug("store:" + store.getId() + " situationTemplate:" + situationTemplate.getId());

                if (epcCurrentDTO == null || !epcCurrentDTO.getStoreId().equals(store.getId())
                    || !epcCurrentDTO.getSituationTemplateId().equals(situationTemplate.getId())) {
                    epcCurrentDTO = createEpcDTO(situationTemplate, store, sensor, csv);
                }

                ExtractionPlanRangeDTO planRangeDTO = new ExtractionPlanRangeDTO();
                planRangeDTO.setDayOfWeek(Integer.valueOf(csv.get("day_of_week")));
                planRangeDTO.setId(0);
                planRangeDTO.setInitialTime(csv.get("start_time"));
                planRangeDTO.setEndTime(csv.get("end_time"));
                planRangeDTO.setType(csv.get("type").toUpperCase());
                planRangeDTO.setFrecuency(Integer.valueOf(csv.get("frecuency")));
                planRangeDTO.setDuration(Integer.valueOf(csv.get("length")));
                planRangeDTO.setSamples(Integer.valueOf(csv.get("samples")));

                saveExtractionPlanRange(epcCurrentDTO.getId(), planRangeDTO);

            }
        } catch (FileNotFoundException e) {
            log.error(e, e);
            throw new ScopixException(e);
        } catch (IOException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }
        log.info("start");

    }

    private ExtractionPlanCustomizingDTO createEpcDTO(SituationTemplate situationTemplate, Store store, Sensor sensor,
        CsvReader csv) throws ScopixException {
        Integer[] days = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        ExtractionPlanCustomizingDTO epcCurrentDTO = null;
        try {
            try {
                epcCurrentDTO = createExtractionPlanCustomizingDTO(situationTemplate.getId(), store.getId(), "SYSTEM");
            } catch (ScopixException e) {
                if (e.getMessage().equals("EXIST_EPC_SITUATION_TEMPLATE_STORE")) {
                    //request last epc from edition
                    epcCurrentDTO = getUltimoExtractionPlanCustomizingNoEnviado(situationTemplate.getId(), store.getId());
                } else {
                    throw (e);
                }
            }
            //modify all data in epc
            epcCurrentDTO.setOneEvaluation(Boolean.valueOf(csv.get("one_evaluation")));
            if (csv.get("priorization") != null && csv.get("priorization").length() > 0) {
                epcCurrentDTO.setPriorization(Integer.valueOf(csv.get("priorization")));
            }
            epcCurrentDTO.setRandomCamera(Boolean.valueOf(csv.get("random_camera")));

            epcCurrentDTO.setExtractionPlanMetricDTOs(new ArrayList<ExtractionPlanMetricDTO>());

            epcCurrentDTO.setSensorIds(new ArrayList<Integer>());
            
            if (sensor != null) {
                //append sensor
                epcCurrentDTO.getSensorIds().add(sensor.getId());
            }
            
            List<EvidenceProviderDTO> providerDTOs = getEvidenceProvidersStoreSituationTemplate(store.getId(),
                situationTemplate.getId());
            String[] cameras = StringUtils.split(csv.get("providers"), ",");
            //clean providers not epc
            List<EvidenceProviderDTO> providerDTOsClean = cleanProviders(providerDTOs, cameras);
            List<MetricTemplateDTO> metricTemplateDTOs = getMetricTemplateDTO(epcCurrentDTO.getId());
            //add cameras all metrics for epc
            asociaMetricasProviderAEPC(epcCurrentDTO, metricTemplateDTOs, providerDTOsClean);
            saveEPCGeneral(epcCurrentDTO);
            //clean calendar
            cleanExtractionPlanCutomizingDay(epcCurrentDTO.getId(), Arrays.asList(days));
        } catch (ScopixException e) {
            log.error(e, e);
            throw (e);
        } catch (IOException e) {
            log.error(e, e);
            throw new ScopixException(e);
        } catch (NumberFormatException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }
        return epcCurrentDTO;
    }

    private Store getStore(String storeName, List<Store> stores) {
        for (Store store : stores) {
            if (store.getName().equals(storeName)) {
                return store;
            }

        }
        return null;
    }

    private SituationTemplate getSituationTemplate(String situationTemplateName, List<SituationTemplate> situationTemplates) {
        for (SituationTemplate st : situationTemplates) {

            if (st.getName().equals(situationTemplateName)) {
                return st;
            }
        }
        return null;
    }

    private Sensor getSensor(String sensorName, List<Sensor> sensors) {
        for (Sensor s : sensors) {
            if (s.getName().equals(sensorName)) {
                return s;
            }
        }
        return null;
    }

    private void asociaMetricasProviderAEPC(ExtractionPlanCustomizingDTO newDTO, List<MetricTemplateDTO> metricTemplateDTOs,
        List<EvidenceProviderDTO> providerDTOs) {

        int order = 0;
        for (MetricTemplateDTO metricTemplateDTO : metricTemplateDTOs) {
            order++;
            ExtractionPlanMetricDTO extractionPlanMetricDTO = new ExtractionPlanMetricDTO();
            extractionPlanMetricDTO.setMetricTemplateId(metricTemplateDTO.getId());
            extractionPlanMetricDTO.setEvidenceProviderDTOs(providerDTOs);
            extractionPlanMetricDTO.setEvaluationOrder(order);
            newDTO.getExtractionPlanMetricDTOs().add(extractionPlanMetricDTO);
        }
    }

    private List<EvidenceProviderDTO> cleanProviders(List<EvidenceProviderDTO> providerDTOs, String[] providers) {
        List<EvidenceProviderDTO> ret = new ArrayList<EvidenceProviderDTO>();
        for (String providerName : providers) {
            for (EvidenceProviderDTO dto : providerDTOs) {
                Integer providerNameInt = Integer.valueOf(providerName);
                Integer name = Integer.valueOf(StringUtils.split(dto.getDescription(), " ")[1]);
                if (providerNameInt.equals(name)) {
                    log.debug("provider " + dto.getId());
                    ret.add(dto);
                    break;
                }
            }
        }
        return ret;
    }

}
