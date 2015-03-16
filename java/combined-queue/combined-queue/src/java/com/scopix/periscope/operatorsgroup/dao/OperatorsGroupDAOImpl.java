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
 *  OperatorsGroupDAOImpl.java
 * 
 *  Created on May 28, 2014, 1:02:14 PM
 * 
 */
package com.scopix.periscope.operatorsgroup.dao;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.math.BigInteger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = OperatorsGroupDAO.class)
public class OperatorsGroupDAOImpl extends DAOHibernate<BusinessObject, Integer> implements OperatorsGroupDAO {


    /**
     * Finds Group by user name
     *
     * @param groupName
     * @return boolean 
     */
    @Override
    public boolean existGroupName(String groupName) {
        StringBuilder sql = new StringBuilder();
        sql.append("select count(*) as cant");
        sql.append(" from operators_group");
        sql.append(" where  group_name = '");
        sql.append(groupName);
        sql.append("'");
        Session s = this.getSession();
        Query query = s.createSQLQuery(sql.toString()).addScalar("cant", Hibernate.BIG_INTEGER);

        Integer ret = ((BigInteger) query.uniqueResult()).intValue();
        if (ret > 0) {
            return true;
        }
        return false;
    }
}
