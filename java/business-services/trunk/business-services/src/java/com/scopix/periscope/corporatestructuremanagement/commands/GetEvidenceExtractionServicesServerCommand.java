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
 * GetEvidenceExtractionServicesServerCommand.java
 *
 * Created on 31-03-2008, 06:16:09 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetEvidenceExtractionServicesServerCommand {

    private GenericDAO dao;

    public EvidenceExtractionServicesServer execute(Integer id) throws ScopixException {

        EvidenceExtractionServicesServer evidenceExtractionServicesServer = null;

        try {
            evidenceExtractionServicesServer = getDao().get(id, EvidenceExtractionServicesServer.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException(
                    "periscopeexception.corporateStructureManagement.evidenceExtractionServicesServer.elementNotFound",
                    objectRetrievalFailureException);
        }

        return evidenceExtractionServicesServer;
    }

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
