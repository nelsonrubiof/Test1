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
 * AddSituationCommand.java
 *
 * Created on 01-04-2008, 12:38:57 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateEvidenceRequestCommand {

    public void execute(EvidenceRequest evidenceRequest) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        //PENDING ERROR MESSAGES
        try {
            EvidenceRequest ev = dao.get(evidenceRequest.getId(), EvidenceRequest.class);
            ev.setEvidenceTime(evidenceRequest.getEvidenceTime());
            ev.setEvidenceProvider(evidenceRequest.getEvidenceProvider());
            ev.setEvidences(evidenceRequest.getEvidences());
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("PENDING", objectRetrievalFailureException);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            throw new ScopixException("PENDING", dataIntegrityViolationException);
        }
    }
}
