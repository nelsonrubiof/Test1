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
 * UpdateEvidenceServicesServerCommand.java
 *
 * Created on 27-06-2008, 06:21:25 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceServicesServer;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateEvidenceServicesServerCommand {

    public void execute(EvidenceServicesServer server) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        EvidenceServicesServer serverTemp = null;

        try {
            serverTemp = dao.get(server.getId(), EvidenceServicesServer.class);

            serverTemp.setEvidencePath(server.getEvidencePath());
            serverTemp.setProofPath(server.getProofPath());
            serverTemp.setUrl(server.getUrl());
            serverTemp.setAlternativeEvidencePath(server.getAlternativeEvidencePath());
            serverTemp.setAlternativeSFTPip(server.getAlternativeSFTPip());
            serverTemp.setAlternativeSFTPuser(server.getAlternativeSFTPuser());
            serverTemp.setAlternativeSFTPpassword(server.getAlternativeSFTPpassword());
            serverTemp.setAlternativeMode(server.getAlternativeMode());
            serverTemp.setAlternativeRemoteSFTPPath(server.getAlternativeRemoteSFTPPath());
            serverTemp.setLocalFilePath(server.getLocalFilePath());

            dao.save(serverTemp);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            throw new ScopixException("periscopeexception.templateManagement.businessRuleTemplate.elementNotFound",
                    objectRetrievalFailureException);
        }
    }
}
