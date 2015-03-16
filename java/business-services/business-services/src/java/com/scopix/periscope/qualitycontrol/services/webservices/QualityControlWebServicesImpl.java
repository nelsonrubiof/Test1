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
 * QualityControlWebServicesImpl.java
 *
 * Created on 04-06-2008, 04:29:11 PM
 *
 */
package com.scopix.periscope.qualitycontrol.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.QualityControlManager;
import com.scopix.periscope.qualitycontrol.dto.EvidenceFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.MetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.MotivoRejectedDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedMetricResultDTO;
import com.scopix.periscope.qualitycontrol.dto.ObservedSituationFinishedDTO;
import com.scopix.periscope.qualitycontrol.dto.QualityEvaluationDTO;
import com.scopix.periscope.queuemanagement.dto.FilteringData;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@WebService(endpointInterface = "com.scopix.periscope.qualitycontrol.services.webservices.QualityControlWebServices")
//@CustomWebService(serviceClass = QualityControlWebServices.class)
@SpringBean(rootClass = QualityControlWebServices.class)
public class QualityControlWebServicesImpl implements QualityControlWebServices {

    private static final Logger log = Logger.getLogger(QualityControlWebServicesImpl.class);

    @Override
    public List<StoreDTO> getStores(long sessionId) throws ScopixWebServiceException {
        List<StoreDTO> stores = null;
        try {
            stores = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).getStores(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }

        StoreDTO dtoDummy = new StoreDTO();
        stores.add(dtoDummy);

        return stores;
    }

    @Override
    public List<ObservedMetricResultDTO> getEvidenceFinished(FilteringData filters, long sessionId)
        throws ScopixWebServiceException {
        List<ObservedMetricResultDTO> evidenceFinishedDTOs = null;
        try {
            evidenceFinishedDTOs = SpringSupport.getInstance().findBeanByClassName(
                QualityControlManager.class).getEvidenceFinished(filters, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return evidenceFinishedDTOs;
    }

    @Override
    public void rejectsEvaluations(List<Integer> observedMetricIds, String comments, long sessionId)
        throws ScopixWebServiceException {
        try {
            SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).rejectsEvaluations(observedMetricIds,
                comments, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
    }

    @Override
    public List<EvidenceFinishedDTO> getEvidenceFinishedList(Date start, Date end, boolean rejected, long sessionId) throws
        ScopixWebServiceException {
        List<EvidenceFinishedDTO> evidenceFinishedDTOs = null;
        try {
            evidenceFinishedDTOs = SpringSupport.getInstance().findBeanByClassName(
                QualityControlManager.class).getEvidenceFinishedList(start, end, rejected, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return evidenceFinishedDTOs;
    }

    /**
     * Metodos para Quality Flex
     *
     * @param filters
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException
     */
    @Override
    public List<ObservedSituationFinishedDTO> getObservedSituationFinished(FilteringData filters, long sessionId)
        throws ScopixWebServiceException {
        log.info("start");
        List<ObservedSituationFinishedDTO> dTOs = null;
        try {
            dTOs = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).
                getObservedSituationFinished(filters, sessionId);
            if (dTOs != null && !dTOs.isEmpty()) {
                ObservedSituationFinishedDTO dummy = new ObservedSituationFinishedDTO();
                dTOs.add(dummy);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        log.info("end " + dTOs);
        return dTOs;
    }

    @Override
    public List<MetricResultDTO> getMetricResultByObservedSituation(Integer situationFinishedId, long sessionId)
        throws ScopixWebServiceException {
        List<MetricResultDTO> dTOs = null;
        try {
            dTOs = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).
                getMetricResultByObservedSituation(situationFinishedId, sessionId);

            if (dTOs != null && !dTOs.isEmpty()) {
                MetricResultDTO dummy = new MetricResultDTO();
                dTOs.add(dummy);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dTOs;
    }

    @Override
    public void saveEvaluations(List<QualityEvaluationDTO> qedtos, long sessionId) throws ScopixWebServiceException {
        try {
            SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).saveEvaluations(qedtos, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
    }

    @Override
    public List<MotivoRejectedDTO> getMotivosRejected(long sessionId) throws ScopixWebServiceException {
        List<MotivoRejectedDTO> result = null;
        try {
            result = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).
                getMotivoRejectedDTOs(sessionId);
            if (result != null && !result.isEmpty()) {
                MotivoRejectedDTO dummy = new MotivoRejectedDTO();
                result.add(dummy);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return result;
    }

    /**
     *
     * @param metricResultId
     * @param situationFinishedId
     * @param sessionId
     * @return
     * @throws ScopixWebServiceException
     */
    @Override
    public List<EvidenceDTO> getEvidenceDTOByMetricResult(Integer metricResultId, Integer situationFinishedId,
        long sessionId)
        throws ScopixWebServiceException {
        List<EvidenceDTO> result = null;
        try {
            result = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class).
                getEvidenceDTOByMetricResult(metricResultId, situationFinishedId, sessionId);
            if (result != null && !result.isEmpty()) {
            EvidenceDTO dummy = new EvidenceDTO();
            result.add(dummy);
            }

        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return result;
    }
}
