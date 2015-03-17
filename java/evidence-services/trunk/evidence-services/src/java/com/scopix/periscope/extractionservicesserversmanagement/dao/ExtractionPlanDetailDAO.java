/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * EvidenceExtractionServicesServerDAO.java
 * 
 * Created on 02-07-2008, 12:08:19 AM
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessResourceFailureException;

import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * 
 * @author marko.perich
 */
@SpringBean(rootClass = ExtractionPlanDetailDAO.class)
public class ExtractionPlanDetailDAO extends DAOHibernate<ExtractionPlanDetailDAO, Integer> {

    private GenericDAO dao;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static Logger log = Logger.getLogger(ExtractionPlanDetailDAO.class);

    /**
     * Close conection, close statement and close result set
     * 
     * @param rs
     * @param st
     */
    private void closeConnection(Connection con, ResultSet rs, Statement st) {
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
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
                log.error("con error " + ex);
            }
        }
    }

    public ExtractionPlan getExtractionPlanEnable(Integer evidenceExtractionServicesServerId) {
        log.info("start");
        ExtractionPlan extractionPlan = null;
        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.addOrder(Order.desc("id"));
        criteria.add(Restrictions.isNull("expiration"));
        criteria.createCriteria("evidenceExtractionServicesServer").add(
                Restrictions.eq("serverId", evidenceExtractionServicesServerId));
        List<ExtractionPlan> extractionPlans = criteria.list();
        if (extractionPlans != null && !extractionPlans.isEmpty()) {
            extractionPlan = extractionPlans.get(0);
        }
        log.info("end");
        return extractionPlan;
    }

    /**
     * Recupera el ExtractionPlan para el evidenceExtractionServicesServerId solicitado
     * 
     * @param evidenceExtractionServicesServerId
     * @return ExtractionPlan plan para evidenceExtractionServicesServerId solicitado
     */
    public ExtractionPlan getExtractionPlanByEvidenceExtractionServicesServer(Integer evidenceExtractionServicesServerId) {
        log.info("start");
        ExtractionPlan extractionPlan = null;
        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.addOrder(Order.desc("id"));
        criteria.createCriteria("evidenceExtractionServicesServer")
                .add(Restrictions.eq("id", evidenceExtractionServicesServerId));
        List<ExtractionPlan> extractionPlans = criteria.list();
        if (extractionPlans != null && !extractionPlans.isEmpty()) {
            extractionPlan = extractionPlans.get(0);
        }
        log.info("end");
        return extractionPlan;
    }

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public List<ExtractionPlan> getExtractionPlanByExpirationDate(Date dateStart) {

        Criteria criteria = this.getSession().createCriteria(ExtractionPlan.class);
        criteria.add(Restrictions.or(Restrictions.gt("expiration", dateStart), Restrictions.isNull("expiration")));
        criteria.addOrder(Order.asc("expiration"));
        List<ExtractionPlan> extractionPlans = criteria.list();
        return extractionPlans;
    }

    public Map<Integer, Integer> getEvidenceRequestByProviderIds(Set<Integer> providerIds, String storeName, Date dateStart,
            Date dateEnd) {
        Map<Integer, Integer> result = new HashMap<Integer, Integer>();
        Connection con = null;
        ResultSet rs = null;
        Statement statement = null;
        StringBuilder sb = new StringBuilder();

        sb.append(" select distinct epd.id, er.device_id ");
        sb.append(" from evidence_request er , extraction_plan_detail epd , ");
        sb.append(" extraction_plan ep, evidence_extraction_services_server eess");
        sb.append(" where eess.name  = '").append(storeName).append("' ");
        sb.append(" and ep.evidence_extraction_services_server_id = eess.id ");
        sb.append(" and ((ep.expiration BETWEEN '").append(DateFormatUtils.format(dateStart, DATE_FORMAT)).append("'");
        sb.append("       and '").append(DateFormatUtils.format(dateEnd, DATE_FORMAT)).append("')  or ep.expiration is null) ");
        sb.append(" and epd.extraction_plan_id = ep.id ");
        sb.append(" and er.extraction_plan_detail_id = epd.id  ");
        sb.append(" and er.device_id in(").append(StringUtils.join(providerIds, ",")).append(")");
        try {
            con = getSession().connection();
            statement = con.createStatement();
            rs = statement.executeQuery(sb.toString());
            while (rs.next()) {
                result.put(rs.getInt(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            log.error("no es posible recupera conjunto evidenceRequest Provider " + e, e);
            result = new HashMap<Integer, Integer>();
        } finally {
            closeConnection(con, rs, statement);
        }
        return result;
    }

    /**
     * borra todas las referencias de una evidencia ExtractionPlan
     */
    public void deleteAutomaticEvidence(Integer evidenceId, Integer extractionPlanDetailId) throws ScopixException {
        log.info("start [evidenceId:" + evidenceId + "][extractionPlanDetailId:" + extractionPlanDetailId + "]");
        try {
            UpdateWork delete = new UpdateWork();
            String sqlDelete = "delete from evidence where id =" + evidenceId + ";";
            delete.setSql(sqlDelete);
            this.getSession().doWork(delete);
            log.info("evidence borradas " + delete.getRowsAffected() + " para id " + evidenceId);

            sqlDelete = "delete from evidence_request where id =" + extractionPlanDetailId + ";";
            delete.setSql(sqlDelete);
            this.getSession().doWork(delete);
            log.info("evidence_request borradas " + delete.getRowsAffected() + " para extractionPlanDetailId "
                    + extractionPlanDetailId);

            sqlDelete = "delete from extraction_plan_detail where id =" + extractionPlanDetailId + ";";
            delete.setSql(sqlDelete);
            this.getSession().doWork(delete);
            log.info("extraction_plan_detail borradas " + delete.getRowsAffected());
        } catch (DataAccessResourceFailureException e) {
            throw new ScopixException(e);
        } catch (IllegalStateException e) {
            throw new ScopixException(e);
        } catch (HibernateException e) {
            throw new ScopixException(e);
        } finally {
            this.releaseSession(this.getSession());
        }

        log.info("end");

    }

    public void saveExtractionPlanDetail(ExtractionPlanDetail extractionPlanDetail) throws ScopixException {
        log.debug("init");
        // Save ExtractionPlanDetail and their evidenceRequest
        // Statement stEPD = null;
        // Statement stER = null;
        // Statement st = null;
        // Connection conn = null;
        ResultSet rs = null;
        Session session = this.getSession();

        try {
            int counter = 1;
            int epdId = 0;
            String sql = "select nextval('extraction_plan_detail_seq') as SEC";
            SelectWork selectWork = new SelectWork();
            selectWork.setSql(sql);
            session.doWork(selectWork);
            rs = selectWork.getResultSet();
            rs.next();
            epdId = rs.getInt("SEC");
            if (epdId == 0) {
                throw new ScopixException("No es posible obtener la secuencia");
            }
            closeConnection(null, rs, null);

            StringBuilder insertEPD = new StringBuilder("INSERT INTO extraction_plan_detail(id,extraction_plan_id) ");
            insertEPD.append("VALUES (").append(epdId).append(",").append(extractionPlanDetail.getExtractionPlan().getId())
                    .append(")");
            extractionPlanDetail.setId(epdId);
            UpdateWork updateWork = new UpdateWork();
            updateWork.setSql(insertEPD.toString());
            session.doWork(updateWork);
            log.debug("extraction_plan_detail insertdas " + updateWork.getRowsAffected());
            // stEPD = conn.createStatement();
            // stEPD.executeUpdate(insertEPD.toString());

            log.debug("ExtractionPalnDetail id = " + epdId);
            for (EvidenceRequest er : extractionPlanDetail.getEvidenceRequests()) {
                er.setExtractionPlanDetail(extractionPlanDetail);

                StringBuilder insertER = new StringBuilder("INSERT INTO evidence_request(id,request_type,requested_time,");
                insertER.append("duration,device_id,");
                insertER.append("business_services_request_id,day_of_week,extraction_plan_detail_id, type, priorization)");

                insertER.append("VALUES (nextval('evidence_requests_seq'),'").append(er.getRequestType()).append("',");
                insertER.append("to_timestamp('").append(DateFormatUtils.format(er.getRequestedTime(), "HH:mm"));
                insertER.append("', 'HH24:MI'),");
                insertER.append(er.getDuration()).append(",");
                insertER.append(er.getDeviceId()).append(",");
                insertER.append(er.getBusinessServicesRequestId()).append(",");
                insertER.append(er.getDayOfWeek()).append(",");
                insertER.append(epdId).append(", '").append(er.getType().name()).append("', ");
                insertER.append(er.getPriorization()).append(")");

                updateWork.setSql(insertER.toString());
                session.doWork(updateWork);
                log.debug("evidence_request insertdas " + updateWork.getRowsAffected());

                // stER = conn.createStatement();
                // stER.executeUpdate(insertER.toString());
                log.debug("EvidenceRequest Count = " + counter++);
                // closeConnection(null, null, stER);
            }

        } catch (SQLException e) {
            throw new ScopixException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException ex) {
                    log.error("rs error " + ex);
                }
            }
            this.releaseSession(session);
        }
        log.info("end");
    }

    public void saveAllExtractionPlanDetail(List<ExtractionPlanDetail> extractionPlanDetails) throws ScopixException {
        log.info("start");
        ResultSet rs = null;
        Session session = this.getSession();
        // recorremos todos los planes para almacenar sus datos
        StringBuilder valuesPlanDetail = new StringBuilder();
        StringBuilder valuesRequest = new StringBuilder();
        int countSql = 0;
        UpdateWork updateWork = new UpdateWork();
        try {
            int epdId = 0;
            String sql = "select nextval('extraction_plan_detail_seq') as SEC";
            SelectWork selectWork = new SelectWork();
            selectWork.setSql(sql);
            session.doWork(selectWork);
            rs = selectWork.getResultSet();
            rs.next();
            epdId = rs.getInt("SEC");
            if (epdId == 0) {
                throw new ScopixException("No es posible obtener la secuencia");
            }
            String baseInsertPlanDetail = "INSERT INTO extraction_plan_detail(id,extraction_plan_id) VALUES";
            String baseInsertRequest = "INSERT INTO evidence_request(id,request_type,requested_time, duration,"
                    + "device_id,business_services_request_id,day_of_week,extraction_plan_detail_id, type, live, priorization) VALUES";
            closeConnection(null, rs, null);
            log.info("epdId " + epdId);
            for (ExtractionPlanDetail epd : extractionPlanDetails) {
                // recuperamos el id
                try {
                    epd.setId(epdId);

                    // generamos los values para cada tipo de insert
                    // generamos el sql de insert para el plan
                    if (valuesPlanDetail.length() > 0) {
                        valuesPlanDetail.append(",");
                    }
                    valuesPlanDetail.append("(").append(epdId).append(",");
                    valuesPlanDetail.append(epd.getExtractionPlan().getId()).append(")");
                    // incrementamos el contador de sql
                    countSql++;
                    // generamos los sql para los request del plan
                    for (EvidenceRequest er : epd.getEvidenceRequests()) {
                        er.setExtractionPlanDetail(epd);
                        if (valuesRequest.length() > 0) {
                            valuesRequest.append(",");
                        }
                        valuesRequest.append(" (nextval('evidence_requests_seq'),'").append(er.getRequestType()).append("',");
                        valuesRequest.append("to_timestamp('").append(DateFormatUtils.format(er.getRequestedTime(), "HH:mm"));
                        valuesRequest.append("', 'HH24:MI'),");
                        valuesRequest.append(er.getDuration()).append(",");
                        valuesRequest.append(er.getDeviceId()).append(",");
                        valuesRequest.append(er.getBusinessServicesRequestId()).append(",");
                        valuesRequest.append(er.getDayOfWeek()).append(",");
                        valuesRequest.append(epdId).append(", '").append(er.getType().name()).append("', ");
                        valuesRequest.append(er.getLive()).append(",");
                        valuesRequest.append(er.getPriorization()).append(")");
                        countSql++;
                    }
                    epdId++;

                } catch (HibernateException e) {
                    log.error("NO es posible generar data para plan ");
                }

                try {
                    // revisamos si son mas de 1000 y ejecutamos a base de datos
                    if (countSql > 5000) {
                        // ejecutamos el insert a base datos
                        updateWork.setSql(baseInsertPlanDetail + valuesPlanDetail.toString() + ";");
                        session.doWork(updateWork);
                        updateWork.setSql(baseInsertRequest + valuesRequest.toString() + ";");
                        session.doWork(updateWork);

                        valuesRequest.setLength(0);
                        valuesPlanDetail.setLength(0);
                        countSql = 0;

                        log.info("insert " + epdId);
                    }
                } catch (HibernateException e) {
                    log.error("No se pudo ejecutar insert masivo " + baseInsertPlanDetail + valuesPlanDetail.toString());
                    log.error("No se pudo ejecutar insert masivo " + baseInsertRequest + valuesRequest.toString());
                    log.error(e, e);
                    valuesRequest.setLength(0);
                    valuesPlanDetail.setLength(0);
                    countSql = 0;
                }
            }

            try {
                // revisamos si son mas de 1000 y ejecutamos a base de datos
                // ejecutamos el insert a base datos
                if (countSql > 0) {
                    updateWork.setSql(baseInsertPlanDetail + valuesPlanDetail.toString() + ";");
                    session.doWork(updateWork);
                    updateWork.setSql(baseInsertRequest + valuesRequest.toString() + ";");
                    session.doWork(updateWork);

                    log.info("insert " + epdId);
                }
            } catch (HibernateException e) {
                log.error("No se pudo ejecutar insert masivo " + baseInsertPlanDetail + valuesPlanDetail.toString());
                log.error("No se pudo ejecutar insert masivo " + baseInsertRequest + valuesRequest.toString());
                log.error(e, e);
                valuesRequest.setLength(0);
                valuesPlanDetail.setLength(0);
                countSql = 0;
            }

            // actualizamos indice
            log.debug("before update secuencia extraction_plan_detail_seq" + (epdId + 1));
            updateWork.setSql("ALTER SEQUENCE extraction_plan_detail_seq RESTART WITH " + (epdId + 1) + ";");
            session.doWork(updateWork);

        } catch (SQLException e) {
            log.error("no se puede obtener indice " + e, e);
            throw new ScopixException(e);
        } finally {
            closeConnection(null, rs, null);
            this.releaseSession(session);

        }

        log.info("end");
    }
}
