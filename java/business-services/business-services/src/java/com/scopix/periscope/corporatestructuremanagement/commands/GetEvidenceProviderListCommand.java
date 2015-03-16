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
 * GetEvidenceProviderListCommand.java
 *
 * Created on 31-03-2008, 06:57:16 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetEvidenceProviderListCommand {

    private CorporateStructureManagementHibernateDAO dao;

    public List<EvidenceProvider> execute(EvidenceProvider evidenceProvider) {
        List<EvidenceProvider> list = getDao().getEvidenceProvidersList(evidenceProvider);
        return list;
    }

    public CorporateStructureManagementHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManagementHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(CorporateStructureManagementHibernateDAO dao) {
        this.dao = dao;
    }
}
