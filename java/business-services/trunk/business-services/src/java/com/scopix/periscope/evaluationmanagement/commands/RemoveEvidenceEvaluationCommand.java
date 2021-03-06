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
 * RemoveEvidenceEvaluationCommand.java
 *
 * Created on 08-05-2008, 03:37:14 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class RemoveEvidenceEvaluationCommand {

    private Logger log = Logger.getLogger(RemoveObservedMetricCommand.class);

    public void execute(Integer id) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            dao.remove(id, EvidenceEvaluation.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.evidenceEvaluation");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            log.error("Error = " + dataIntegrityViolationException.getMessage(), dataIntegrityViolationException);
            //"periscopeexception.entity.validate.constraintViolation", new String[]{
            throw new ScopixException("label.evidenceEvaluation");
        }
        log.debug("end");
    }
}
