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
 * GetEvidenceProviderTypeCommand.java
 *
 * Created on 31-03-2008, 06:33:28 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderType;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetEvidenceProviderTypeCommand {

    public EvidenceProviderType execute(Integer id) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        EvidenceProviderType evidenceProviderType = null;

        try {
            evidenceProviderType = dao.get(id, EvidenceProviderType.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.corporateStructureManagement.evidenceProviderType.elementNotFound",
                    objectRetrievalFailureException);
        }

        return evidenceProviderType;
    }
}
