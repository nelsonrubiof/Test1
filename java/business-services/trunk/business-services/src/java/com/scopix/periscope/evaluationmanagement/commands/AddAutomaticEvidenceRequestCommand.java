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
 * AddEvidenceCommand.java
 *
 * Created on 08-05-2008, 03:21:19 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddAutomaticEvidenceRequestCommand {

    private static Logger log = Logger.getLogger(AddAutomaticEvidenceRequestCommand.class);
    private GenericDAO genericDAO;
    private EvaluationHibernateDAO evaluationHibernateDAO;

    public EvidenceRequest execute(EvidenceRequest evidenceRequest) throws ScopixException {
        log.info("start");
        EvidenceRequest result = null;
        result = getEvaluationHibernateDAO().getEvidenceRequestForAMetric(evidenceRequest);
        if (result != null) {
            log.debug("after getEvidenceRequestForAMetric result:" + result.getId());
        } else {
            log.debug("after getEvidenceRequestForAMetric no existe evidenceRequest para " + evidenceRequest.getId());
        }
        if (result == null) {
            evidenceRequest.setId(getEvaluationHibernateDAO().getNextIdForEvidenceRequest());
            log.debug("new Id " + evidenceRequest.getId());
            
            getEvaluationHibernateDAO().saveEvidenceRequest(evidenceRequest);

            result = evidenceRequest;
            log.debug("er after save: " + result.getId());
            if (result.getId() == null) {
                result = getEvaluationHibernateDAO().getEvidenceRequestForAMetric(evidenceRequest);
                //log.debug("er " + result != null ? result.getId() : null);
            }
        }
        log.info("end");
        return result;
    }

    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }

    public EvaluationHibernateDAO getEvaluationHibernateDAO() {
        if (evaluationHibernateDAO == null) {
            evaluationHibernateDAO = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return evaluationHibernateDAO;
    }

    public void setEvaluationHibernateDAO(EvaluationHibernateDAO evaluationHibernateDAO) {
        this.evaluationHibernateDAO = evaluationHibernateDAO;
    }
}
