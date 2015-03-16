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
 * EvidenceExtractionServicesServerDAO.java
 *
 * Created on 02-07-2008, 12:08:19 AM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import com.scopix.periscope.evidencemanagement.ProcessEES;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author marko.perich
 */
@SpringBean(rootClass = EvidenceExtractionServicesServerDAO.class)
public class EvidenceExtractionServicesServerDAO extends DAOHibernate<EvidenceExtractionServicesServer, Integer> {

    private static Logger log = Logger.getLogger(EvidenceExtractionServicesServerDAO.class);

    /**
     * Metodo que retorna el evidence extraction a partir del id existente en business services.
     *
     * @param eessId
     * @return
     */
    public EvidenceExtractionServicesServer getEvidenceExtractionServicesServerByEvidenceExtractionServicesIdInBusinessServices(
            Integer eessId, String storeName) {
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = null;

        Criteria criteria = this.getSession().createCriteria(EvidenceExtractionServicesServer.class);
        criteria.add(Restrictions.eq("idAtBusinessServices", eessId));
        Criteria criteriaExtractionPlans = criteria.createCriteria("extractionPlans");
        criteriaExtractionPlans.add(Restrictions.isNull("expiration"));
        criteriaExtractionPlans.add(Restrictions.eq("storeName", storeName));

        List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = criteria.list();
        if (evidenceExtractionServicesServers != null && !evidenceExtractionServicesServers.isEmpty()) {
            evidenceExtractionServicesServer = evidenceExtractionServicesServers.get(0);
        }
        return evidenceExtractionServicesServer;
    }

    public EvidenceExtractionServicesServer getEvidenceExtractionServicesServerByServerId(Integer serverId) {
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = null;
        Criteria criteria = this.getSession().createCriteria(EvidenceExtractionServicesServer.class);
        criteria.add(Restrictions.eq("serverId", serverId));
        criteria.createCriteria("extractionPlans").add(Restrictions.isNull("expiration"));
        List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = criteria.list();
        if (evidenceExtractionServicesServers != null && !evidenceExtractionServicesServers.isEmpty()) {
            evidenceExtractionServicesServer = evidenceExtractionServicesServers.get(0);
        }
        return evidenceExtractionServicesServer;

    }

    public List<EvidenceExtractionServicesServer> getAllEvidenceExtractionServicesServerEnabled() {
        Criteria criteria = this.getSession().createCriteria(EvidenceExtractionServicesServer.class);
        criteria.createCriteria("extractionPlans").add(Restrictions.isNull("expiration"));
        List<EvidenceExtractionServicesServer> evidenceExtractionServicesServers = criteria.list();
        return evidenceExtractionServicesServers;

    }

    public List<EvidenceRequest> getEvidenceRequestEnabled() {
        Criteria criteria = this.getSession().createCriteria(EvidenceRequest.class);
        criteria.addOrder(Order.asc("id"));
        criteria.createCriteria("extractionPlanDetail").createCriteria("extractionPlan").add(Restrictions.isNull("expiration"));
        List<EvidenceRequest> list = criteria.list();
        return list;
    }

    public List<Integer> getRemoteEvidenceRequestIdList(List<Integer> ids) {
        List<Integer> ers = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select business_services_request_id from evidence_request where extraction_plan_detail_id in (");
        sql.append(StringUtils.join(ids, ","));
        sql.append(")");
//        for (Integer id : ids) {
//            sql += id + ", ";
//        }
        //sql = sql.substring(0, sql.length() - 2) + ")";
        SQLQuery query = this.getSession().createSQLQuery(sql.toString());
        ers = query.list();
        this.getSession().flush();
        return ers;
    }

    public ProcessEES getProcessEES(Integer extractionPlanId, Integer eesProcessId) {
        ProcessEES processEES = null;
        try {
            Criteria criteria = this.getSession().createCriteria(ProcessEES.class);
            criteria.add(Restrictions.eq("processIdEes", eesProcessId));
            criteria.createCriteria("extractionPlan").add(Restrictions.eq("id", extractionPlanId));
            processEES = (ProcessEES) criteria.uniqueResult();

        } catch (DataAccessResourceFailureException e) {
            log.error("DataAccessResourceFailureException " + e, e);
            throw (e);
        } catch (IllegalStateException e) {
            log.error("IllegalStateException " + e, e);
            throw (e);
        } catch (HibernateException e) {
            log.error("HibernateException " + e, e);
            throw (e);
        }
        return processEES;
    }

    public Integer getNextProcessIdAutoGenerado() {
        Connection conn = null;
        ResultSet rs = null;
        Statement st = null;
        Integer id = null;
        try {
            String sql = "select nextval('process_id_seq') as SEC";
            conn = this.getSession().connection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            rs.next();
            id = rs.getInt("SEC");
        } catch (DataAccessResourceFailureException e) {
        } catch (IllegalStateException e) {
        } catch (HibernateException e) {
        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    log.error("rs error " + ex);
                }
            }
            if (st != null) {
                try {
                    st.close();
                    st = null;
                } catch (SQLException ex) {
                    log.error("st error " + ex);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (SQLException ex) {
                    log.error("con error " + ex);
                }
            }
        }
        return id;
    }
}
