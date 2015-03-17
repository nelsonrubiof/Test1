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
 * AddEvidenceExtractionServicesServerCommand.java
 *
 * Created on 31-03-2008, 01:04:46 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddEvidenceExtractionServicesServerCommand {

    public void execute(EvidenceExtractionServicesServer evidenceServicesServer) {
        GenericDAOHibernate dao = (GenericDAOHibernate) HibernateSupport.getInstance().findGenericDAO();
        dao.getHibernateTemplate().save(evidenceServicesServer);
//        dao.save(evidenceServicesServer);
    }
}
