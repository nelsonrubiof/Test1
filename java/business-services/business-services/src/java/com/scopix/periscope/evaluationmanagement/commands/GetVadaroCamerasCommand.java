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
 * GetObservedSituationCommand.java
 *
 * Created on 08-05-2008, 01:42:55 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import java.util.List;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Returns vadaro cameras
 *
 * @author Emo
 */
public class GetVadaroCamerasCommand {

    private CorporateStructureManagementHibernateDAO dao;

    public CorporateStructureManagementHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManagementHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(CorporateStructureManagementHibernateDAO dao) {
        this.dao = dao;
    }

    public List<EvidenceProvider> execute(Integer storeId) {
        return getDao().getEvidenceProvidersByType("VadaroProvider", storeId);
    }
}
