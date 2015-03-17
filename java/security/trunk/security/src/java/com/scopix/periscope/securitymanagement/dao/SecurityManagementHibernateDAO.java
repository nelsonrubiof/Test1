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
 * SecurityManagementHibernateDAO.java
 *
 * Created on 16-06-2008, 01:53:46 PM
 *
 */
package com.scopix.periscope.securitymanagement.dao;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.securitymanagement.AreaType;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.Role;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.Store;
import com.scopix.periscope.securitymanagement.dto.AreaTypeDTO;
import com.scopix.periscope.securitymanagement.dto.CorporateDTO;
import com.scopix.periscope.securitymanagement.dto.PeriscopeUserDTO;
import com.scopix.periscope.securitymanagement.dto.StoreDTO;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = SecurityManagementHibernateDAO.class)
public class SecurityManagementHibernateDAO extends DAOHibernate<PeriscopeUser, Long> {

    private static Logger log = Logger.getLogger(SecurityManagementHibernateDAO.class);

    /**
     * Obtain a list of AreaType object using filters.
     *
     * @param areaType Filter object
     * @return List<AreaType> List of AreaType objects
     */
    public List<AreaType> getAreaTypeList(AreaType areaType) {
        log.debug("start");

        List<AreaType> areaTypes = null;
        Criteria criteria = this.getSession().createCriteria(AreaType.class);
        criteria.addOrder(Order.asc("id"));
        if (areaType != null) {
            if (areaType.getId() != null && areaType.getId() != 0) {
                criteria.add(Restrictions.eq("id", areaType.getId()));
            }
            if (areaType.getName() != null && areaType.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", areaType.getName(), MatchMode.ANYWHERE));
            }
            if (areaType.getDescription() != null && areaType.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", areaType.getDescription(), MatchMode.ANYWHERE));
            }
            if (areaType.getCorporate() != null && areaType.getCorporate().getId() != null
                    && areaType.getCorporate().getId() > 0) {
                criteria.add(Restrictions.eq("corporate.id", areaType.getCorporate().getId()));
            }
        }
        areaTypes = criteria.list();
        log.debug("end");

        return areaTypes;
    }

    public List<Corporate> getCorporateList(Corporate corporate) {
        log.debug("start");

        List<Corporate> corporates = null;
        Criteria criteria = this.getSession().createCriteria(Corporate.class);
        criteria.addOrder(Order.asc("id"));
        if (corporate != null) {
            if (corporate.getId() != null) {
                criteria.add(Restrictions.eq("id", corporate.getId()));
            }
            if (corporate.getName() != null && corporate.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", corporate.getName(), MatchMode.ANYWHERE));
            }
            if (corporate.getDescription() != null && corporate.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", corporate.getDescription(), MatchMode.ANYWHERE));
            }
            if (corporate.getActive() != null && corporate.getActive()) {
                criteria.add(Restrictions.eq("active", corporate.getActive()));
            }
        }
        corporates = criteria.list();
        log.debug("end");

        return corporates;
    }

    public List<CorporateDTO> getCorporatesForUser(String userName) throws ScopixException {
        log.info("start");
        List<CorporateDTO> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select c.id as corporateId, c.NAME as name, c.DESCRIPTION as description");
        sql.append(" from");
        sql.append(" PERISCOPE_USER pu");
        sql.append(", REL_PERISCOPE_USER_CORPORATE rel");
        sql.append(", CORPORATE c");
        sql.append(" where");
        sql.append(" pu.ID = rel.PERISCOPE_USER_ID");
        sql.append(" and rel.CORPORATE_ID = c.ID");
        sql.append(" and pu.NAME = '").append(userName).append("'");
        sql.append(" and c.ACTIVE = TRUE");

        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("corporateId")
                .addScalar("name")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(CorporateDTO.class)).list();

        log.info("end");
        return list;
    }

    /**
     * Obtain a list of Store object using filters.
     *
     * @param store Filter object
     * @return List<Store> List of Store objects
     */
    public List<Store> getStoreList(Store store) {
        log.debug("start");

        List<Store> stores = null;
        Criteria criteria = this.getSession().createCriteria(Store.class);
        criteria.addOrder(Order.asc("id"));
        if (store != null) {
            if (store.getId() != null && store.getId() != 0) {
                criteria.add(Restrictions.eq("id", store.getId()));
            }
            if (store.getName() != null && store.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", store.getName(), MatchMode.ANYWHERE));
            }
            if (store.getDescription() != null && store.getDescription().length() > 0) {
                criteria.add(Restrictions.ilike("description", store.getDescription(), MatchMode.ANYWHERE));
            }
            if (store.getCorporate() != null && store.getCorporate().getId() != null && store.getCorporate().getId() > 0) {
                criteria.add(Restrictions.eq("corporate.id", store.getCorporate().getId()));
            }
        }
        stores = criteria.list();
        log.debug("end");

        return stores;
    }

    public List<PeriscopeUser> getUserList(PeriscopeUser periscopeUser) {
        log.debug("start");

        List<PeriscopeUser> periscopeUsers = null;
        Criteria criteria = this.getSession().createCriteria(PeriscopeUser.class);
        criteria.addOrder(Order.asc("name"));
        if (periscopeUser != null) {
            if (periscopeUser.getName() != null && periscopeUser.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", periscopeUser.getName(), MatchMode.ANYWHERE));
            }
            if (periscopeUser.getUserState() != null) {
                criteria.add(Restrictions.eq("userState", periscopeUser.getUserState()));
            }
            if (periscopeUser.getStartDate() != null) {
                criteria.add(Restrictions.eq("startDate", periscopeUser.getStartDate()));
            }
            if (periscopeUser.getMainCorporate() != null) {
                criteria.add(Restrictions.eq("mainCorporate.id", periscopeUser.getMainCorporate().getId()));
            }
            criteria.add(Restrictions.eq("deleted", periscopeUser.isDeleted()));
        }
        periscopeUsers = criteria.list();
        log.debug("end");

        return periscopeUsers;
    }

    public List<PeriscopeUserDTO> getUserListMinimalInfo(Integer corporateId) {
        log.info("start");

        List<PeriscopeUserDTO> list = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select ID as userId, NAME as userName, EMAIL as email, FULL_NAME as fullName, JOB_POSITION as jobPosition");
        sql.append(" , MODIFICATION_DATE as modificationDate, START_DATE as startDate, PASSWORD as password");
        sql.append(" , MAIN_CORPORATE_ID as mainCorporateId, USER_STATE as userState");
        sql.append(" from");
        sql.append(" PERISCOPE_USER");
        sql.append(" where");
        sql.append(" MAIN_CORPORATE_ID = ").append(corporateId);
        sql.append(" and DELETED = false");

        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("userId")
                .addScalar("userName")
                .addScalar("email")
                .addScalar("fullName")
                .addScalar("jobPosition")
                .addScalar("modificationDate")
                .addScalar("startDate")
                .addScalar("password")
                .addScalar("mainCorporateId")
                .addScalar("userState")
                .setResultTransformer(Transformers.aliasToBean(PeriscopeUserDTO.class)).list();

        log.info("end");
        return list;
    }

    public PeriscopeUser findSpecificUserByName(String name) {
        log.debug("start");

        PeriscopeUser periscopeUser = null;
        Criteria criteria = this.getSession().createCriteria(PeriscopeUser.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.setMaxResults(1);
        periscopeUser = (PeriscopeUser) criteria.uniqueResult();
        log.debug("end");

        return periscopeUser;
    }

    public List<RolesGroup> getRolesGroupList(RolesGroup rolesGroup) {
        log.debug("start");

        List<RolesGroup> rolesGroups = null;
        Criteria criteria = this.getSession().createCriteria(RolesGroup.class);
        criteria.addOrder(Order.asc("name"));
        if (rolesGroup != null) {
            if (rolesGroup.getName() != null && rolesGroup.getName().length() > 0) {
                criteria.add(Restrictions.ilike("name", rolesGroup.getName(), MatchMode.ANYWHERE));
            }
            if (rolesGroup.getDescription() != null) {
                criteria.add(Restrictions.ilike("description", rolesGroup.getDescription(), MatchMode.ANYWHERE));
            }
            criteria.add(Restrictions.eq("deleted", rolesGroup.isDeleted()));
        }
        rolesGroups = criteria.list();
        log.debug("end");

        return rolesGroups;
    }

    public RolesGroup findSpecificRolesGroupByName(String name) {
        log.debug("start");

        RolesGroup rolesGroup = null;
        Criteria criteria = this.getSession().createCriteria(RolesGroup.class);
        criteria.add(Restrictions.eq("name", name));
        criteria.add(Restrictions.eq("deleted", false));
        criteria.setMaxResults(1);
        rolesGroup = (RolesGroup) criteria.uniqueResult();
        log.debug("end");

        return rolesGroup;
    }

    public List<Role> getRoleList(Role role) {
        log.debug("start");

        List<Role> roles = null;
        Criteria criteria = this.getSession().createCriteria(Role.class);
        criteria.addOrder(Order.asc("roleName"));
        if (role != null) {
            if (role.getRoleName() != null && role.getRoleName().length() > 0) {
                criteria.add(Restrictions.ilike("roleName", role.getRoleName(), MatchMode.ANYWHERE));
            }
        }
        roles = criteria.list();
        log.debug("end");

        return roles;
    }

    public List<StoreDTO> getStoresForCorporate(Integer corporateId, String userName) {
        log.info("start");
        List<StoreDTO> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select s.ID as storeId, s.NAME as name, s.DESCRIPTION as description");
        sql.append(" from");
        sql.append(" CORPORATE c");
        sql.append(", STORE s");
        sql.append(", REL_PERISCOPE_USER_STORE rel");
        sql.append(", PERISCOPE_USER pu");
        sql.append(" where");
        sql.append(" c.ID = s.CORPORATE_ID");
        sql.append(" and s.ID = rel.STORE_ID");
        sql.append(" and rel.PERISCOPE_USER_ID = pu.ID");
        if (corporateId > 0) {
            sql.append(" and c.ID = ").append(corporateId);
        }
        sql.append(" and pu.NAME = '").append(userName).append("'");
        sql.append(" order by 3");
        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("storeId")
                .addScalar("name")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(StoreDTO.class)).list();

        log.info("end");
        return list;
    }

    public List<AreaTypeDTO> getAreaTypesForCorporate(Integer corporateId, String userName) {
        log.info("start");
        List<AreaTypeDTO> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select at.ID as areaTypeId, at.NAME as name, at.DESCRIPTION as description");
        sql.append(" from");
        sql.append(" CORPORATE c");
        sql.append(", REL_PERISCOPE_USER_CORPORATE rel1");
        sql.append(", PERISCOPE_USER pu");
        sql.append(", REL_PERISCOPE_USER_AREA_TYPE rel2");
        sql.append(", AREA_TYPE at");
        sql.append(" where");
        sql.append(" c.ID = rel1.CORPORATE_ID");
        sql.append(" and rel1.PERISCOPE_USER_ID = pu.ID");
        sql.append(" and pu.ID = rel2.PERISCOPE_USER_ID");
        sql.append(" and rel2.AREA_TYPE_ID = at.ID");
        sql.append(" and c.ID = ").append(corporateId);
        sql.append(" and pu.NAME = '").append(userName).append("'");
        sql.append(" order by 3");

        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("areaTypeId")
                .addScalar("name")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(AreaTypeDTO.class)).list();

        log.info("end");
        return list;
    }

    public List<StoreDTO> getStoresForUser(String userName) {
        log.info("start");
        List<StoreDTO> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select s.ID as storeId, s.NAME as name, s.DESCRIPTION as description");
        sql.append(" from");
        sql.append(" PERISCOPE_USER pu");
        sql.append(", REL_PERISCOPE_USER_STORE rel");
        sql.append(", STORE s");
        sql.append(" where");
        sql.append(" pu.ID = rel.PERISCOPE_USER_ID");
        sql.append(" and rel.STORE_ID = s.ID");
        sql.append(" and pu.NAME = '").append(userName).append("'");
        sql.append(" order by 3");

        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("storeId")
                .addScalar("name")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(StoreDTO.class)).list();

        log.info("end");
        return list;
    }

    public List<AreaTypeDTO> getAreaTypesForUser(String userName) {
        log.info("start");
        List<AreaTypeDTO> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select at.ID as areaTypeId, at.NAME as name, at.DESCRIPTION as description");
        sql.append(" from");
        sql.append(" PERISCOPE_USER pu");
        sql.append(", REL_PERISCOPE_USER_AREA_TYPE rel");
        sql.append(", AREA_TYPE at");
        sql.append(" where");
        sql.append(" pu.ID = rel.PERISCOPE_USER_ID");
        sql.append(" and rel.AREA_TYPE_ID = at.ID");
        sql.append(" and pu.NAME = '").append(userName).append("'");
        sql.append(" order by 3");

        list = this.getSession().createSQLQuery(sql.toString())
                .addScalar("areaTypeId")
                .addScalar("name")
                .addScalar("description")
                .setResultTransformer(Transformers.aliasToBean(AreaTypeDTO.class)).list();

        log.info("end");
        return list;
    }
}