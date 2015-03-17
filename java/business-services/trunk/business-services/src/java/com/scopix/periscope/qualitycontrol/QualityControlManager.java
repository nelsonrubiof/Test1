/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QualityControlManager.java
 *
 * Created on 04-06-2008, 03:35:23 PM
 *
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.businesswarehouse.transfer.commands.AddRejectErrorListCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.DeleteEvidencesAndProofsCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.RejectBWCommand;
import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.commands.GetCorporateCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceProviderCommand;
import com.scopix.periscope.corporatestructuremanagement.dto.AreaDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.EvaluationQueueManager;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.commands.GetEvidenceCommand;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.executors.threadpool.ScopixPoolExecutor;
import com.scopix.periscope.jarperreport.util.ReportType;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.commands.GetEvidenceDTOByMetricResultCommnad;
import com.scopix.periscope.qualitycontrol.commands.GetEvidenceDTOCommand;
import com.scopix.periscope.qualitycontrol.commands.MetricResultByObservedSituationCommand;
import com.scopix.periscope.qualitycontrol.commands.GetEvidenceFinishedCommand;
import com.scopix.periscope.qualitycontrol.commands.GetEvidenceFinishedListCommand;
import com.scopix.periscope.qualitycontrol.commands.GetMotivoRejectedCommand;
import com.scopix.periscope.qualitycontrol.commands.GetMotivosRejectsCommand;
import com.scopix.periscope.qualitycontrol.commands.ObservedSituationFinishedListCommand;
import com.scopix.periscope.qualitycontrol.commands.RejectEvidenceByObservedSituationsCommand;
import com.scopix.periscope.qualitycontrol.commands.RejectEvidenceFinishedCommand;
import com.scopix.periscope.qualitycontrol.commands.SaveQualityEvaluationCommand;
import com.scopix.periscope.qualitycontrol.commands.TransFormQualityEvaluationDTOToBusinessObjectCommand;
import com.scopix.periscope.qualitycontrol.commands.TransformMotivosRejectsToDTOCommand;
import com.scopix.periscope.qualitycontrol.dto.EvidenceFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.MetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.MotivoRejectedDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedMetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedSituationFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.QualityEvaluationDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import com.scopix.periscope.reporting.ReportingManager;
import com.scopix.periscope.securitymanagement.QualityControlManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.securitymanagement.permissions.QualityControlFlexManagerPermissions;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = QualityControlManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class QualityControlManager implements InitializingBean {

    private static final Logger log = Logger.getLogger(QualityControlManager.class);
    private static Integer REJECT_SLEEP_RETRY_IN_SECONDS;
    private static ScopixPoolExecutor rejectPoolExecutor;

    private GetCorporateCommand corporateCommand;
    private GetEvidenceFinishedCommand evidenceFinishedCommand;
    private SecurityManager securityManager;
    private ObservedSituationFinishedListCommand observedSituationFinishedListCommand;
    private RejectBWCommand rejectBWCommand;
    private DeleteEvidencesAndProofsCommand deleteEvidencesAndProofsCommand;
    private AddRejectErrorListCommand addRejectErrorListCommand;

    /**
     * Retorna los Store y las Areas de un Corporate
     */
    public List<StoreDTO> getStores(long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QualityControlManagerPermissions.GET_STORES_FOR_QC_MANAGER_PERMISSION);
        log.debug("security ok");
        List<Store> stores = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null,
                sessionId);
        log.debug("stores = " + stores.size());
        //DTOTranformUtil.getInstance().setRecursiveLevels(1);
        log.debug("before transform");

        LinkedHashMap<String, Boolean> colsAreas = new LinkedHashMap<String, Boolean>();
        colsAreas.put("description", Boolean.FALSE);
        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();
        for (Store store : stores) {
            StoreDTO sdto = new StoreDTO();
            sdto.setId(store.getId());
            sdto.setName(store.getName());
            sdto.setDescription(store.getDescription());
            sdto.setAreas(new ArrayList<AreaDTO>());
            storeDTOs.add(sdto);

            for (Area a : store.getAreas()) {
                AreaDTO adto = new AreaDTO();
                adto.setId(a.getId());
                adto.setName(a.getName());
                adto.setDescription(a.getDescription());
                sdto.getAreas().add(adto);
            }
            SortUtil.sortByColumn(colsAreas, sdto.getAreas());
        }

//        List<StoreDTO> storeDTOs = DTOTranformUtil.getInstance().transformEntityListToDOTList(stores, StoreDTO.class, false);
        log.debug("after transform, stores = " + storeDTOs.size());
        return storeDTOs;
    }

    public List<ObservedMetricResultDTO> getEvidenceFinished(FilteringData filters, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QualityControlManagerPermissions.GET_EVIDENCE_FINISHED_PERMISSION);
        //agregamos le Corporate para saber a que metodo debemos llamar en el DAO de Quality

        Corporate corporate = getCorporateCommand().execute();

        List<ObservedMetricResultDTO> evidenceFinishedDTOs = null;
        evidenceFinishedDTOs = getEvidenceFinishedCommand().execute(filters, corporate);
        log.debug("end");
        return evidenceFinishedDTOs;
    }

    public void rejectsEvaluations(List<Integer> observedMetricIds, String comments, long sessionId) throws ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QualityControlManagerPermissions.REJECT_EVALUATION_PERMISSION);

        String userName = secMan.getUserName(sessionId);

        //antes de ejecutar el reject debemos modificar todos los reportingData asocociados si existen
        SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                rejectReportingDataByObservedMetricsIds(observedMetricIds, userName);

        RejectEvidenceFinishedCommand rejectEvidenceFinishedCommand = new RejectEvidenceFinishedCommand();
        rejectEvidenceFinishedCommand.execute(observedMetricIds, comments);

        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshMetricQueue();
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshSituationQueue();
        //SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshOperatorQueue();

        //Aqui implementar el llamado a TransferToAlternativeSFTPCommand con la evidencia respectiva para copiar
        //nuevamente el archivo a MOE para su re-evaluación.
        log.debug("end");
    }

    public List<EvidenceFinishedDTO> getEvidenceFinishedList(Date start, Date end, boolean rejected, long sessionId) throws
            ScopixException {
        log.debug("start");
        SecurityManager secMan = SpringSupport.getInstance().findBeanByClassName(
                com.scopix.periscope.securitymanagement.SecurityManager.class);
        secMan.checkSecurity(sessionId, QualityControlManagerPermissions.GET_EVIDENCE_FINISHED_LIST_PERMISSION);
        List<EvidenceFinishedDTO> evidenceFinishedDTOs = null;
        GetEvidenceFinishedListCommand command = new GetEvidenceFinishedListCommand();
        evidenceFinishedDTOs = command.execute(start, end, rejected);
        log.debug("end");
        return evidenceFinishedDTOs;
    }

    public byte[] getQualityControlReport(Date start, Date end, boolean rejected, ReportType rt, long sessionId) throws
            ScopixException {
        log.debug("start");
        List<EvidenceFinishedDTO> evidenceFinishedDTOs = getEvidenceFinishedList(start, end, rejected, sessionId);
        byte[] doc = null;
//        try {
//            ReportGenerator rg = new ReportGenerator();
//            HashMap param = new HashMap();
//            param.put("size", evidenceFinishedDTOs != null ? evidenceFinishedDTOs.size() : 0);
//            doc = rg.generateReportFromClasspath("qc_report.jasper", evidenceFinishedDTOs, ReportType.CSV, param);
//        } catch (Exception e) {
//            log.error("Error creating document: " + e.getMessage(), e);
//            throw new ScopixException(e);
//        }
        return doc;
    }

    public GetCorporateCommand getCorporateCommand() {
        if (corporateCommand == null) {
            corporateCommand = new GetCorporateCommand();
        }
        return corporateCommand;
    }

    public void setCorporateCommand(GetCorporateCommand corporateCommand) {
        this.corporateCommand = corporateCommand;
    }

    public GetEvidenceFinishedCommand getEvidenceFinishedCommand() {
        if (evidenceFinishedCommand == null) {
            evidenceFinishedCommand = new GetEvidenceFinishedCommand();
        }
        return evidenceFinishedCommand;
    }

    public void setEvidenceFinishedCommand(GetEvidenceFinishedCommand evidenceFinishedCommand) {
        this.evidenceFinishedCommand = evidenceFinishedCommand;
    }

    // Metodos Creados para Quality Flex
    public List<ObservedSituationFinishedDTO> getObservedSituationFinished(FilteringData filters, long sessionId)
            throws ScopixException {
        List<ObservedSituationFinishedDTO> situationFinishedDTOs = null;
        getSecurityManager().checkSecurity(sessionId,
                QualityControlFlexManagerPermissions.GET_SITUATION_FINISHED_LIST_PERMISSION);

        situationFinishedDTOs = getObservedSituationFinishedListCommand().execute(filters);
        return situationFinishedDTOs;
    }

    public List<MetricResultDTO> getMetricResultByObservedSituation(Integer situationFinishedId, long sessionId)
            throws ScopixException {
        List<MetricResultDTO> list = null;
        getSecurityManager().checkSecurity(sessionId, QualityControlFlexManagerPermissions.GET_METRIC_RESULT_LIST_PERMISSION);
        MetricResultByObservedSituationCommand command = new MetricResultByObservedSituationCommand();
        list = command.execute(situationFinishedId);
        return list;
    }

    public Map getFile(String path) throws MalformedURLException, SmbException, UnknownHostException {
        SmbFile fileImage = new SmbFile(path);
        Integer size = (int) fileImage.length();
        InputStream inputStream = new SmbFileInputStream(fileImage);
        Map result = new HashMap();
        result.put("size", size);
        result.put("inputStream", inputStream);
        return result;
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

    public ObservedSituationFinishedListCommand getObservedSituationFinishedListCommand() {
        if (observedSituationFinishedListCommand == null) {
            observedSituationFinishedListCommand = new ObservedSituationFinishedListCommand();
        }
        return observedSituationFinishedListCommand;
    }

    public void setObservedSituationFinishedListCommand(ObservedSituationFinishedListCommand value) {
        this.observedSituationFinishedListCommand = value;
    }

    public EvidenceDTO getEvidenceDTO(Integer evidenceId, Integer evidenceEvaluationId) {
        //getSecurityManager().checkSecurity(sessionId, QualityControlManagerPermissions.GET_SITUATION_FINISHED_PERMISSION);
        GetEvidenceDTOCommand command = new GetEvidenceDTOCommand();

        EvidenceDTO evidenceDTO = command.execute(evidenceId, evidenceEvaluationId);
        return evidenceDTO;
    }

    public List<MotivoRejectedDTO> getMotivoRejectedDTOs(long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, QualityControlFlexManagerPermissions.GET_MOTIVO_REJECTED_LIST_PERMISSION);
        GetMotivosRejectsCommand command = new GetMotivosRejectsCommand();
        List<MotivoRejected> list = command.execute();

        TransformMotivosRejectsToDTOCommand command2 = new TransformMotivosRejectsToDTOCommand();
        return command2.execute(list);
    }

    public List<EvidenceDTO> getEvidenceDTOByMetricResult(Integer metricResultId, Integer situationFinishedId, long sessionId)
            throws ScopixException {
        getSecurityManager().checkSecurity(sessionId,
                QualityControlFlexManagerPermissions.GET_EVIDENCE_BY_METRIC_RESULT_LIST_PERMISSION);
        List<EvidenceDTO> result = null;
        GetEvidenceDTOByMetricResultCommnad command = new GetEvidenceDTOByMetricResultCommnad();
        result = command.execute(metricResultId, situationFinishedId);
        return result;
    }

    public void saveEvaluations(List<QualityEvaluationDTO> qedtos, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, QualityControlFlexManagerPermissions.SAVE_EVALUATION_PERMISSION);
        TransFormQualityEvaluationDTOToBusinessObjectCommand command = new TransFormQualityEvaluationDTOToBusinessObjectCommand();
        SaveQualityEvaluationCommand command2 = new SaveQualityEvaluationCommand();
        List<QualityEvaluation> evaluations = command.execute(qedtos);
        if (!evaluations.isEmpty()) {
            QualityEvaluation ev0 = evaluations.get(0);
            if (ev0.getQualityEvaluationType().equals(QualityEvaluationType.REJECTED)) {
                rejectsEvaluationsByQualityEvaluations(evaluations);
            }
            for (QualityEvaluation evaluation : evaluations) {
                command2.execute(evaluation);
            }
        }
    }

    private void rejectsEvaluationsByQualityEvaluations(List<QualityEvaluation> evaluations) throws ScopixException {
        log.debug("start");
        GetMotivoRejectedCommand command = new GetMotivoRejectedCommand();
        if (evaluations != null && !evaluations.isEmpty()) {

            String evaluationUser = null;
            String comments = null;
            Set<Integer> osIds = new HashSet<Integer>();
            for (QualityEvaluation evaluation : evaluations) {
                if (evaluationUser == null) {
                    evaluationUser = evaluation.getEvaluationUser();
                }
                if (comments == null) {
                    //se debe recuperar primero el objeto de Rechazo para pedir su descripcion
                    MotivoRejected mr = command.execute(evaluation.getMotivoRechazo().getId());
                    comments = mr.getDescription() + " " + evaluation.getMessageOperator();

                }
                osIds.add(evaluation.getObservedSituation().getId());
            }
            SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    rejectReportingDataByObservedSituationsIds(osIds, evaluationUser);

            RejectEvidenceByObservedSituationsCommand rejectEvidenceFinishedCommand
                    = new RejectEvidenceByObservedSituationsCommand();
            rejectEvidenceFinishedCommand.execute(osIds, comments);

            SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshMetricQueue();
            SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshSituationQueue();

        }
        log.debug("end");
    }

    public EvidenceProvider getEvidenceProvider(Integer evidenceId) throws ScopixException {
        GetEvidenceCommand command = new GetEvidenceCommand();
        GetEvidenceProviderCommand evidenceProviderCommand = new GetEvidenceProviderCommand();
        Evidence e = command.execute(evidenceId);
        EvidenceProvider provider = null;
        if (e != null) {
            if (!e.getEvidenceRequests().isEmpty()) {
                //provider = e.getEvidenceRequests().get(0).getEvidenceProvider();
                provider = evidenceProviderCommand.execute(e.getEvidenceRequests().get(0).getEvidenceProvider().getId());
                provider.getEvidenceProviderTemplates().isEmpty();
            }
        }
        return provider;
    }

    public Evidence getEvidence(Integer evidenceId) throws ScopixException {
        GetEvidenceCommand command = new GetEvidenceCommand();

        Evidence e = command.execute(evidenceId);
        e.getObservedMetrics().isEmpty();
        return e;
    }

    public SituationTemplate getSituationTemplateByEvidenceProvider(EvidenceProvider ep, Integer evidenceId) {
        SituationTemplate st = null;
        for (Evidence e : ep.getEvidenceRequests().get(0).getEvidences()) {
            if (e.getId().equals(evidenceId)) {
                st = e.getObservedMetrics().get(0).getObservedSituation().getSituation().
                        getSituationTemplate();
                break;
            }
        }
        return st;

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("start");
        try {
            ClassPathResource res = new ClassPathResource("system.properties");
            Properties prop = new Properties();
            prop.load(res.getInputStream());

            REJECT_SLEEP_RETRY_IN_SECONDS = Integer.valueOf(prop.getProperty("RejectSleepRetryInSeconds"));

            String maxThreads = prop.getProperty("RejectMaxThreads");
            if (maxThreads.length() == 0) {
                maxThreads = "1";
            }
            LinkedBlockingQueue<Runnable> threadQueue = new LinkedBlockingQueue<Runnable>();
            setRejectPoolExecutor(new ScopixPoolExecutor(Integer.parseInt(maxThreads), threadQueue, "RejectPool"));

        } catch (IOException e) {
            log.error("Cannot initialize TransferProofManager bean." + e.getMessage());
            throw new ScopixException(e);
        } catch (NumberFormatException e) {
            log.error("Cannot initialize TransferProofManager bean." + e.getMessage());
            throw new ScopixException(e);
//        } catch (SQLGrammarException e) {
//            log.warn("Cannot initialize TransferProofManager bean. Exist database?");
            //throw new PeriscopeException(e);
        }
        log.info("end");
    }

    /**
     *
     * @param observedSituationEvaluationList
     * @param indicatorValuesList
     * @param observedMetricIds
     */
    public void rejectToBW(List<Integer> observedSituationEvaluationList, List<Integer> indicatorValuesList,
            List<Integer> observedMetricIds) {
        log.info("start");
        int cont = 1;

        do {
            log.debug("try: " + cont);
            try {
                //Solo en caso de que el siguiente comando falle, se reintenta y se hace rollback en si aplica.
                getRejectBWCommand().execute(observedSituationEvaluationList, indicatorValuesList);

                //Si el siguiente comando falla, no es tan importante como el anterior, por lo tanto la secuencia de rechazo
                //continúa.
                getDeleteEvidencesAndProofsCommand().execute(observedMetricIds);
                break;
            } catch (ScopixException pex) {
                log.error("ERROR: " + pex.getMessage(), pex);
                if (cont >= 3) {
                    getAddRejectErrorListCommand().execute(observedMetricIds, observedMetricIds);
                    break;
                } else {
                    try {
                        Thread.sleep(REJECT_SLEEP_RETRY_IN_SECONDS * 1000);
                    } catch (InterruptedException iex) {
                        log.error("ERROR: " + iex.getMessage());
                    }
                    cont++;
                }
            }
        } while (true);
        log.info("end");
    }

    /**
     * @return the rejectPoolExecutor
     */
    public static ScopixPoolExecutor getRejectPoolExecutor() {
        return rejectPoolExecutor;
    }

    /**
     * @param aRejectPoolExecutor the rejectPoolExecutor to set
     */
    public static void setRejectPoolExecutor(ScopixPoolExecutor aRejectPoolExecutor) {
        rejectPoolExecutor = aRejectPoolExecutor;
    }

    /**
     * @return the rejectBWCommand
     */
    public RejectBWCommand getRejectBWCommand() {
        if (rejectBWCommand == null) {
            rejectBWCommand = new RejectBWCommand();
        }
        return rejectBWCommand;
    }

    /**
     * @param rejectBWCommand the rejectBWCommand to set
     */
    public void setRejectBWCommand(RejectBWCommand rejectBWCommand) {
        this.rejectBWCommand = rejectBWCommand;
    }

    /**
     * @return the deleteEvidencesAndProofsCommand
     */
    public DeleteEvidencesAndProofsCommand getDeleteEvidencesAndProofsCommand() {
        if (deleteEvidencesAndProofsCommand == null) {
            deleteEvidencesAndProofsCommand = new DeleteEvidencesAndProofsCommand();
        }
        return deleteEvidencesAndProofsCommand;
    }

    /**
     * @param deleteEvidencesAndProofsCommand the deleteEvidencesAndProofsCommand to set
     */
    public void setDeleteEvidencesAndProofsCommand(DeleteEvidencesAndProofsCommand deleteEvidencesAndProofsCommand) {
        this.deleteEvidencesAndProofsCommand = deleteEvidencesAndProofsCommand;
    }

    /**
     * @return the addRejectErrorListCommand
     */
    public AddRejectErrorListCommand getAddRejectErrorListCommand() {
        if (addRejectErrorListCommand == null) {
            addRejectErrorListCommand = new AddRejectErrorListCommand();
        }
        return addRejectErrorListCommand;
    }

    /**
     * @param addRejectErrorListCommand the addRejectErrorListCommand to set
     */
    public void setAddRejectErrorListCommand(AddRejectErrorListCommand addRejectErrorListCommand) {
        this.addRejectErrorListCommand = addRejectErrorListCommand;
    }

}
