/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceManager.java
 *
 * Created on 17-06-2008, 02:34:48 PM
 *
 */
package com.scopix.periscope.evidencemanagement;

import com.scopix.periscope.corporatestructuremanagement.services.webservices.CorporateWebServices;
import com.scopix.periscope.corporatestructuremanagement.services.webservices.client.CorporateWebServiceClient;
import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evaluationmanagement.services.webservices.client.EvaluationWebServicesClient;
import com.scopix.periscope.evidencemanagement.commands.AcceptAutomaticNewEvidenceCommand;
import com.scopix.periscope.evidencemanagement.commands.AcceptNewEvidenceCommand;
import com.scopix.periscope.evidencemanagement.commands.DeleteEvidenceAutomaticCommand;
import com.scopix.periscope.evidencemanagement.commands.GetProcessIdByExtractionPlanProcessEESCommand;
import com.scopix.periscope.evidencemanagement.dto.NewAutomaticEvidenceDTO;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.extractionservicesserversmanagement.dao.ExtractionPlanDetailDAO;
import com.scopix.periscope.periscopefoundation.evidence_common.exception.ConfigurationException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.services.webservices.SecurityWebServices;
import com.scopix.periscope.securitymanagement.services.webservices.client.SecurityWebServicesClient;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import java.util.Properties;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
@SpringBean(rootClass = EvidenceManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class EvidenceManager implements InitializingBean {

    private static Logger log = Logger.getLogger(EvidenceManager.class);
    private static String periscopeUser;
    private static String periscopePassword;
    private static Integer corporateId;
    private static Long sessionId;
    private GenericDAO genericDAO;
    private EvaluationWebServices evaluationWebService;
    private SecurityWebServices securityWebServices;
    private CorporateWebServices corporateWebServices;
    private DeleteEvidenceAutomaticCommand deleteEvidenceAutomaticCommand;
    private ExtractionPlanDetailDAO extractionPlanDetailDAO;
    private GetProcessIdByExtractionPlanProcessEESCommand processIdByExtractionPlanProcessEESCommand;
    private AcceptAutomaticNewEvidenceCommand acceptAutomaticNewEvidenceCommand;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("start");
        try {

            //Get and Set connection properties
            ClassPathResource res = new ClassPathResource("system.properties");
            Properties prop = new Properties();
            prop.load(res.getInputStream());
            periscopeUser = prop.getProperty("periscope.user");
            periscopePassword = prop.getProperty("periscope.password");
        } catch (InvalidDataAccessResourceUsageException ex) {
            log.warn("Cannot initialize ExtractionManager bean." + ex.getMessage());
        } catch (IOException ex) {
            log.error("Cannot initialize ExtractionManager bean." + ex.getMessage());
            throw new ScopixException("Cannot initialize ExtractionManager bean.", ex);
        }
        log.info("end");
    }

    public void acceptNewEvidence(Evidence evidence) throws ScopixException {
        log.info("start");
        // save evidence
        getGenericDAO().save(evidence);
        // get the reference to evaluationWebService

        AcceptNewEvidenceCommand acceptNewEvidenceCommand = new AcceptNewEvidenceCommand();
        //se le saca security se asume que solo llegan solicitudes desde el interior del sistema por vpn e IP interna
        //acceptNewEvidenceCommand.execute(evidence, getEvaluationWebService(), getSessionId());
        acceptNewEvidenceCommand.execute(evidence, getEvaluationWebService());
        log.info("end");
    }

    /**
     * Evidence evidence, List<Map<String, Integer>> situationMetricList
     */
    public Evidence acceptAutomaticNewEvidence(NewAutomaticEvidenceDTO newAutomaticEvidenceDTO) throws ScopixException {
        log.info("start");
        // save evidence
        Evidence evidence = null;
        try {
            if (newAutomaticEvidenceDTO != null) {
                evidence = new Evidence();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                Date reqDate = sdf.parse(newAutomaticEvidenceDTO.getEvidenceDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(reqDate);
                //recuperamos el extractionPlan asociado al evidenceExtractionServicesServer que esta llegando

                log.debug("extractionPlanServerId:" + newAutomaticEvidenceDTO.getExtractionPlanServerId());
                ExtractionPlan extractionPlan = getExtractionPlanDetailDAO().getExtractionPlanByEvidenceExtractionServicesServer(
                        newAutomaticEvidenceDTO.getExtractionPlanServerId());

                ProcessEES process = getProcessIdByExtractionPlanProcessEESCommand().execute(extractionPlan,
                        newAutomaticEvidenceDTO.getProcessId());

                ExtractionPlanDetail extractionPlanDetail = new ExtractionPlanDetail();
                extractionPlanDetail.setExtractionPlan(extractionPlan);

                EvidenceRequest evidenceRequest = new EvidenceRequest();
                evidenceRequest.setRequestedTime(reqDate);
                evidenceRequest.setDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));
                evidenceRequest.setExtractionPlanDetail(extractionPlanDetail);
                evidenceRequest.setDeviceId(newAutomaticEvidenceDTO.getEvidenceProviderId());
                //reemplazamos el envio del nuevo processID            
                //evidenceRequest.setProcessId(newAutomaticEvidenceDTO.getProcessId());
                evidenceRequest.setProcessId(process.getProcessIdLocal());

                evidenceRequest.setType(EvidenceRequestType.AUTO_GENERATED);

                extractionPlanDetail.getEvidenceRequests().add(evidenceRequest);

                getExtractionPlanDetailDAO().saveExtractionPlanDetail(extractionPlanDetail);

                evidence.setEvidenceTimestamp(reqDate);
                evidence.setExtractionPlanDetail(extractionPlanDetail);
                evidence.setEvidenceUri(newAutomaticEvidenceDTO.getFileName());
                //reemplazamos el envio del nuevo processID            
                //evidence.setProcessId(newAutomaticEvidenceDTO.getProcessId());
                evidence.setProcessId(process.getProcessIdLocal());

                evidence.setDeviceId(newAutomaticEvidenceDTO.getEvidenceProviderId());
                getGenericDAO().save(evidence);
            }
        } catch (ParseException e) {
            log.error("ParseException " + e, e);
            throw new ScopixException(e);
        }

        log.info("end");
        return evidence;
    }

    private Integer getCorporateId() throws ScopixException {
        if (corporateId == null) {
            corporateId = getCorporateWebServices().getCorporateId();
        }
        return corporateId;
    }

    private Long getSessionId() throws ScopixException {

        if (sessionId != null) {
            //Validate if sessionId is still valid
            String permission = "ACCEPT_NEW_EVIDENCE_PERMISSION";
            try {
                getSecurityWebServices().checkSecurity(sessionId, permission);
            } catch (Exception e) {
                sessionId = null;
            }
        }
        //If sessionId was not valid login again and obtain a new sessionId
        if (sessionId == null) {
            sessionId = getSecurityWebServices().login(periscopeUser, periscopePassword, getCorporateId());
        }
        return sessionId;
    }

    public Map<Integer, Integer> getEvidenceRequestsByProvider(String dateEvidenceStart,
            String dateEvidenceEnd, Set<Integer> providerIds, String storeName) {
        log.info("start");
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        //recuperar todos los extraction plan que esten entre las fechas determinadas
        try {
            Date dateStart = DateUtils.parseDate(dateEvidenceStart, new String[]{"yyyy-MM-dd hh:mm:ss"});
            Date dateEnd = DateUtils.parseDate(dateEvidenceEnd, new String[]{"yyyy-MM-dd hh:mm:ss"});
            Map<Integer, Integer> r = getExtractionPlanDetailDAO().getEvidenceRequestByProviderIds(providerIds, storeName, dateStart,
                    dateEnd);
            result.putAll(r);
        } catch (ParseException e) {
            log.error(e, e);
        }
        log.info("end");
        return result;
    }

    /**
     * Envia Nueva evidencia Automatica a Business Services
     *
     * @param evidence
     * @param newAutomaticEvidenceDTO
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixException
     */
    public void sendNewAutomaticEvidenceToBusinessServices(Evidence evidence, NewAutomaticEvidenceDTO newAutomaticEvidenceDTO)
            throws ScopixException {
        log.info("start");
        if (newAutomaticEvidenceDTO != null) {
//            getAcceptAutomaticNewEvidenceCommand().execute(evidence, getEvaluationWebService(),
//                    newAutomaticEvidenceDTO.getSituationMetricList(), getSessionId());

            // se elimina chequeo de seguridad se asume que el llamado solo se realiza por sistema, getSessionId()
            getAcceptAutomaticNewEvidenceCommand().execute(evidence, getEvaluationWebService(),
                    newAutomaticEvidenceDTO.getSituationMetrics());

        }
        log.info("end");
    }

    /**
     * Borra una evidencia y su relación con EvidenceRequest y ExtractionPlanDetail
     *
     * @param evidence
     */
    public void deleteEvidence(Evidence evidence) throws ScopixException {
        log.info("start [processID:" + evidence.getProcessId() + "]");

        getDeleteEvidenceAutomaticCommand().execute(evidence);
        log.info("end");
    }

    /**
     * @return the genericDAO
     */
    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    /**
     * @param genericDAO the genericDAO to set
     */
    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    /**
     * @return the evaluationWebService
     */
    public EvaluationWebServices getEvaluationWebService() throws ScopixException {
        if (evaluationWebService == null) {
            try {
                evaluationWebService = SpringSupport.getInstance().findBeanByClassName(EvaluationWebServicesClient.class).
                        getWebService();
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException(e);
            }
        }
        return evaluationWebService;
    }

    /**
     * @param evaluationWebService the evaluationWebService to set
     */
    public void setEvaluationWebService(EvaluationWebServices evaluationWebService) {
        this.evaluationWebService = evaluationWebService;
    }

    /**
     * @return the securityWebServices
     */
    public SecurityWebServices getSecurityWebServices() throws ScopixException {
        if (securityWebServices == null) {
            try {
                securityWebServices = SecurityWebServicesClient.getInstance().getWebService();
            } catch (ConfigurationException e) {
                throw new ScopixException(e);
            } catch (IOException e) {
                throw new ScopixException(e);
            }
        }
        return securityWebServices;
    }

    /**
     * @param securityWebServices the securityWebServices to set
     */
    public void setSecurityWebServices(SecurityWebServices securityWebServices) {
        this.securityWebServices = securityWebServices;
    }

    /**
     * @return the corporateWebServices
     */
    public CorporateWebServices getCorporateWebServices() throws ScopixException {
        if (corporateWebServices == null) {
            try {
                corporateWebServices = SpringSupport.getInstance().findBeanByClassName(CorporateWebServiceClient.class).
                        getWebService();
            } catch (ConfigurationException e) {
                throw new ScopixException(e);
            }
        }
        return corporateWebServices;
    }

    /**
     * @param corporateWebServices the corporateWebServices to set
     */
    public void setCorporateWebServices(CorporateWebServices corporateWebServices) {
        this.corporateWebServices = corporateWebServices;
    }

    /**
     * @return the deleteEvidenceAutomaticCommand
     */
    public DeleteEvidenceAutomaticCommand getDeleteEvidenceAutomaticCommand() {
        if (deleteEvidenceAutomaticCommand == null) {
            deleteEvidenceAutomaticCommand = new DeleteEvidenceAutomaticCommand();
        }
        return deleteEvidenceAutomaticCommand;
    }

    /**
     * @param deleteEvidenceAutomaticCommand the deleteEvidenceAutomaticCommand to set
     */
    public void setDeleteEvidenceAutomaticCommand(DeleteEvidenceAutomaticCommand deleteEvidenceAutomaticCommand) {
        this.deleteEvidenceAutomaticCommand = deleteEvidenceAutomaticCommand;
    }

    /**
     * @return the extractionPlanDetailDAO
     */
    public ExtractionPlanDetailDAO getExtractionPlanDetailDAO() {
        if (extractionPlanDetailDAO == null) {
            extractionPlanDetailDAO = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanDetailDAO.class);
        }
        return extractionPlanDetailDAO;
    }

    /**
     * @param extractionPlanDetailDAO the extractionPlanDetailDAO to set
     */
    public void setExtractionPlanDetailDAO(ExtractionPlanDetailDAO extractionPlanDetailDAO) {
        this.extractionPlanDetailDAO = extractionPlanDetailDAO;
    }

    /**
     * @return the processIdByExtractionPlanProcessEESCommand
     */
    public GetProcessIdByExtractionPlanProcessEESCommand getProcessIdByExtractionPlanProcessEESCommand() {
        if (processIdByExtractionPlanProcessEESCommand == null) {
            processIdByExtractionPlanProcessEESCommand = new GetProcessIdByExtractionPlanProcessEESCommand();
        }
        return processIdByExtractionPlanProcessEESCommand;
    }

    /**
     * @param value the processIdByExtractionPlanProcessEESCommand to set
     */
    public void setProcessIdByExtractionPlanProcessEESCommand(GetProcessIdByExtractionPlanProcessEESCommand value) {
        this.processIdByExtractionPlanProcessEESCommand = value;
    }

    /**
     * @return the acceptAutomaticNewEvidenceCommand
     */
    public AcceptAutomaticNewEvidenceCommand getAcceptAutomaticNewEvidenceCommand() {
        if (acceptAutomaticNewEvidenceCommand == null) {
            acceptAutomaticNewEvidenceCommand = new AcceptAutomaticNewEvidenceCommand();
        }

        return acceptAutomaticNewEvidenceCommand;
    }

    /**
     * @param acceptAutomaticNewEvidenceCommand the acceptAutomaticNewEvidenceCommand to set
     */
    public void setAcceptAutomaticNewEvidenceCommand(AcceptAutomaticNewEvidenceCommand acceptAutomaticNewEvidenceCommand) {
        this.acceptAutomaticNewEvidenceCommand = acceptAutomaticNewEvidenceCommand;
    }
}
