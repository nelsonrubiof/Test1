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
 * GetEvidenceEvaluationCommand.java
 *
 * Created on 08-05-2008, 03:35:20 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetEvidenceEvaluationCommand {

    public EvidenceEvaluation execute(Integer id) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        EvidenceEvaluation evidenceEvaluation = null;

        //PENDING error message
        try {
            evidenceEvaluation = dao.get(id, EvidenceEvaluation.class);
            evidenceEvaluation.getEvidences().isEmpty();
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("PENDING", objectRetrievalFailureException);
        }

        return evidenceEvaluation;
    }
}
