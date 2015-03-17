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
 *  RegionServerHibernateDAOImpl.java
 * 
 *  Created on Sep 5, 2014, 11:06:39 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.evaluationmanagement.RegionServer;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = RegionServerHibernateDAO.class)
public class RegionServerHibernateDAOImpl extends DAOHibernate<RegionServer, Integer> implements RegionServerHibernateDAO {

    @Override
    public RegionServer getByCodename(String codeName) {
        Criteria criteria = this.getSession().createCriteria(RegionServer.class);
        criteria.add(Restrictions.eq("codeName", codeName));
        RegionServer regionServer = (RegionServer) criteria.uniqueResult();
        return regionServer;
    }
}
