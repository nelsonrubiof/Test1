/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * GetLastVadaroEventsCommand.java
 * 
 * Created on 04-08-2014, 01:42:55 PM
 */
package com.scopix.periscope.evaluationmanagement.commands;

import java.util.List;

import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.vadaro.VadaroEvent;
import java.util.Date;

/**
 * Returns vadaro last events
 * 
 * @author Emo
 */
public class GetLastVadaroEventsCommand {

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

    public List<VadaroEvent> execute(int minutes, Integer storeId, Date timeEvidence) {
        return getDao().getLastVadaroEvents(minutes, storeId, timeEvidence);
    }
}
