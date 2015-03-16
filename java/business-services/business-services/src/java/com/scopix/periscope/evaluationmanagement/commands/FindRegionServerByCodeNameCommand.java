/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  FindRegionServerByCodeNameCommand.java
 * 
 *  Created on Sep 5, 2014, 10:37:29 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.evaluationmanagement.dao.RegionServerHibernateDAO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Sebastian
 */
public class FindRegionServerByCodeNameCommand {

    private RegionServerHibernateDAO regionServerHibernateDAO;

    /**
     * returns the desired Region Server given a code name
     *
     * @param regionServercodeName
     * @return RegionServer
     */
    public RegionServer execute(String regionServercodeName) {
        return getRegionServerHibernateDAO().getByCodename(regionServercodeName);
    }

    /**
     * @return the regionServerHibernateDAO
     */
    public RegionServerHibernateDAO getRegionServerHibernateDAO() {
        if (regionServerHibernateDAO == null) {
            regionServerHibernateDAO = SpringSupport.getInstance().findBeanByClassName(RegionServerHibernateDAO.class);
        }
        return regionServerHibernateDAO;
    }

    /**
     * @param regionServerHibernateDAO the regionServerHibernateDAO to set
     */
    public void setRegionServerHibernateDAO(RegionServerHibernateDAO regionServerHibernateDAO) {
        this.regionServerHibernateDAO = regionServerHibernateDAO;
    }
}
