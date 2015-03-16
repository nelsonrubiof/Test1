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
 * AddBusinessRuleCheckCommand.java
 *
 * Created on 08-05-2008, 12:59:35 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateObservedMetricCommand {

    public void execute(ObservedMetric observedMetric) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            ObservedMetric aux = dao.get(observedMetric.getId(), ObservedMetric.class);
            aux.setMetric(observedMetric.getMetric());
            aux.setEvaluationState(observedMetric.getEvaluationState());
            aux.setEvidenceEvaluations(observedMetric.getEvidenceEvaluations());
            aux.setEvidences(observedMetric.getEvidences());
            aux.setMetricEvaluation(observedMetric.getMetricEvaluation());
            aux.setObservedMetricDate(observedMetric.getObservedMetricDate());
            aux.setObservedSituation(observedMetric.getObservedSituation());
            aux.setPriority(observedMetric.getPriority());
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }
    }
}
