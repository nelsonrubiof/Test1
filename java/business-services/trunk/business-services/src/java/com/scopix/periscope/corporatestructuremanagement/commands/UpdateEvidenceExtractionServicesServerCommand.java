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
 * UpdateEvidenceExtractionServicesServerCommand.java
 *
 * Created on 27-06-2008, 06:22:00 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateEvidenceExtractionServicesServerCommand {

    public void execute(EvidenceExtractionServicesServer server) throws ScopixException {
//        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        GenericDAOHibernate dao = (GenericDAOHibernate) HibernateSupport.getInstance().findGenericDAO();

        EvidenceExtractionServicesServer serverTemp = null;

        try {
            serverTemp = dao.get(server.getId(), EvidenceExtractionServicesServer.class);

            serverTemp.setUrl(server.getUrl());
            serverTemp.setEvidenceServicesServer(server.getEvidenceServicesServer());
            serverTemp.setUseTunnel(server.isUseTunnel());
            serverTemp.setSshAddress(server.getSshAddress());
            serverTemp.setSshPort(server.getSshPort());
            serverTemp.setSshUser(server.getSshUser());
            serverTemp.setSshPassword(server.getSshPassword());
            serverTemp.setSshLocalTunnelPort(server.getSshLocalTunnelPort());
            serverTemp.setSshRemoteTunnelPort(server.getSshRemoteTunnelPort());
            serverTemp.setName(server.getName());
//            dao.save(serverTemp);
            dao.getHibernateTemplate().saveOrUpdate(serverTemp);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }
    }
}
