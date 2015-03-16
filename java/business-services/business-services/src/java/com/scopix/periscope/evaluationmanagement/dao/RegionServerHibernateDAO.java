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
 *  RegionServerHibernateDAO.java
 * 
 *  Created on Sep 5, 2014, 11:04:16 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.evaluationmanagement.RegionServer;

/**
 *
 * @author Sebastian
 */
public interface RegionServerHibernateDAO {

    /**
     * finds the desires region server given a region server codename
     * @param regionServerCodeName
     * @return RegionServer
     */
    RegionServer getByCodename(String regionServerCodeName);
}
