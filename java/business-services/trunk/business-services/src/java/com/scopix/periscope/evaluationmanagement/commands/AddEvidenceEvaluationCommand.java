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
 * AddEvidenceEvaluationCommand.java
 *
 * Created on 08-05-2008, 03:32:14 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddEvidenceEvaluationCommand {

    public void execute(EvidenceEvaluation evidenceEvaluation) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        dao.save(evidenceEvaluation);        
        ObservedMetric omAux = dao.get(evidenceEvaluation.getObservedMetric().getId(), ObservedMetric.class);
        omAux.getEvidenceEvaluations().add(evidenceEvaluation);
    }
}
