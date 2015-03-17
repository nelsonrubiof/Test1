/*
 * 
 * Copyright @ 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * UserLoginHibernateDAOImpl.java
 *
 * Created on 12-05-2008, 06:14:01 PM
 *
 */
package com.scopix.periscope.securitymanagement.dao;

import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.Role;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.UserLogin;
import com.scopix.periscope.securitymanagement.UserState;
import com.scopix.periscope.securitymanagement.dto.RolesGroupDTO;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean(rootClass = UserLoginHibernateDAOImpl.class)
public class UserLoginHibernateDAOImpl extends DAOHibernate<UserLogin, Long> implements UserLoginHibernateDAO {
    
    Logger log = Logger.getLogger(UserLoginHibernateDAOImpl.class);

    @Override
    public void save(UserLogin userLogin) {
        this.getSession().saveOrUpdate(userLogin);
    }

    @Override
    public PeriscopeUser getUser(String user, String password) {
        PeriscopeUser periscopeUser = null;
        Criteria criteria = this.getSession().createCriteria(PeriscopeUser.class);
        criteria.add(Restrictions.eq("name", user));
        criteria.add(Restrictions.eq("password", password));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.add(Restrictions.eq("userState", UserState.ACTIVE));
        periscopeUser = (PeriscopeUser) criteria.uniqueResult();
        return periscopeUser;
    }

    @Override
    public PeriscopeUser getUserByName(String user) {
        PeriscopeUser periscopeUser = null;
        Criteria criteria = this.getSession().createCriteria(PeriscopeUser.class);
        criteria.add(Restrictions.eq("name", user));
        periscopeUser = (PeriscopeUser) criteria.uniqueResult();
        return periscopeUser;
    }

    @Override
    public List<String> getUserPrivileges(PeriscopeUser periscopeUser) {
        List<String> privileges = null;
        List<Integer> rgIds = new ArrayList<Integer>();
        for (RolesGroup rolesGroup : periscopeUser.getRolesGroups()) {
            rgIds.add(rolesGroup.getId());
        }
        Criteria criteria = this.getSession().createCriteria(Role.class).setProjection(Projections.property("roleName"));
        Criteria rg = criteria.createCriteria("rolesGroups");
        rg.add(Restrictions.in("id", rgIds));
        privileges = criteria.list();
        return privileges;
    }

    @Override
    public List<RolesGroupDTO> getRolesGroupForUser(String userName) {
        log.info("start");
        List<RolesGroupDTO> list = null;
        
        StringBuilder sql = new StringBuilder();
        sql.append("select rg.ID as rolesGroupId, rg.NAME as name, rg.DESCRIPTION as description");
        sql.append(" from");
        sql.append(" PERISCOPE_USER pu");
        sql.append(", REL_PERISCOPE_USER_ROLES_GROUP rel");
        sql.append(", ROLES_GROUP rg");
        sql.append(" where");
        sql.append(" pu.ID = rel.PERISCOPE_USER_ID");
        sql.append(" and rel.ROLES_GROUP_ID = rg.ID");
        sql.append(" and rg.DELETED = false");
        sql.append(" and pu.NAME = '").append(userName).append("'");
        sql.append(" order by 3");
        
        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("rolesGroupId")
                .addScalar("name")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(RolesGroupDTO.class)).list();

        log.info("end");
        return list;
    }
}
