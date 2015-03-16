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
 * ExtractionPlanWebServiceImpl.java
 *
 * Created on 18-06-2008, 01:08:27 PM
 *
 */
package com.scopix.periscope.evidencemanagement.services.impl;

import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.evidencemanagement.EvidenceManager;
import com.scopix.periscope.evidencemanagement.dto.NewAutomaticEvidenceDTO;
import com.scopix.periscope.evidencemanagement.dto.NewEvidenceDTO;
import com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author marko.perich
 * @version 6.2.1
 */
//@CustomWebService(serviceClass = EvidenceWebService.class)
@WebService(endpointInterface = "com.scopix.periscope.evidencemanagement.services.webservices.EvidenceWebService")
@SpringBean(rootClass = EvidenceWebService.class)
public class EvidenceWebServiceImpl implements EvidenceWebService {

    private static Logger log = Logger.getLogger(EvidenceWebServiceImpl.class);

    protected EvidenceManager getEvidenceManager() {

        return SpringSupport.getInstance().
                findBeanByClassName(EvidenceManager.class);
    }

    protected ExtractionPlanDetail getExtractionPlanDetail(Integer extractionPlanDetailId) throws ScopixException {
        ExtractionPlanDetail detail = null;
        try {
            detail = HibernateSupport.getInstance().findGenericDAO().get(extractionPlanDetailId,
                    ExtractionPlanDetail.class);
        } catch (Exception e) {
            throw new ScopixException(e);
        }
        return detail;
    }

    @Override
    public void acceptNewEvidence(NewEvidenceDTO newEvidenceDTO) throws ScopixWebServiceException {
        try {
            Evidence evidence = new Evidence();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date reqDate = sdf.parse(newEvidenceDTO.getEvidenceDate());
            evidence.setEvidenceTimestamp(reqDate);
            ExtractionPlanDetail extractionPlanDetail = getExtractionPlanDetail(newEvidenceDTO.getExtractionPlanDetailId());
            evidence.setExtractionPlanDetail(extractionPlanDetail);
            evidence.setEvidenceUri(newEvidenceDTO.getFilename());

            getEvidenceManager().acceptNewEvidence(evidence);
        } catch (ParseException e) {
            log.error("Error parsing evidence date: " + newEvidenceDTO.getEvidenceDate(), e);
            throw new ScopixWebServiceException("Error parsing evidence date: " + newEvidenceDTO.getEvidenceDate(), e);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
    }

    @Override
    public void acceptAutomaticNewEvidence(NewAutomaticEvidenceDTO newAutomaticEvidenceDTO) throws ScopixWebServiceException {
        log.info("start");
        EvidenceManager evidenceManager = getEvidenceManager();
        Evidence evidence = null;

        try {
            log.debug("newAutomaticEvidenceDTO.getSituationMetricList():" + newAutomaticEvidenceDTO.getSituationMetrics());
            evidence = evidenceManager.acceptAutomaticNewEvidence(newAutomaticEvidenceDTO);
        } catch (ConstraintViolationException e) {
            log.warn("ConstraintViolationException Error terminado Proceso  " + e);
            throw new ScopixWebServiceException("DUPLICIDAD");
        } catch (ScopixException e) {
            log.warn("ConstraintViolationException Error terminado Proceso  " + e);
            throw new ScopixWebServiceException("DUPLICIDAD");
        }
        //enviamos a business-services
        try {
            evidenceManager.sendNewAutomaticEvidenceToBusinessServices(evidence, newAutomaticEvidenceDTO);
        } catch (ScopixException e) {
            //si hay error itentamos borrar todo lo referente a la evidencia automatica creada
            try {
                evidenceManager.deleteEvidence(evidence);
            } catch (ScopixException e2) {
                log.error("Error elimiando datos " + e2, e2);
            }
            throw new ScopixWebServiceException(e);
        }
        log.info("end");

    }

    @Override
    public Map<Integer, Integer> getEvidenceRequestsByProvider(String dateEvidenceStart, String dateEvidenceEnd,
            Set<Integer> providerIds, String storeName) throws ScopixWebServiceException {

        EvidenceManager evidenceManager = getEvidenceManager();
        Map<Integer, Integer> map = evidenceManager.getEvidenceRequestsByProvider(dateEvidenceStart, dateEvidenceEnd, providerIds,
                storeName);
        return map;
    }
}
