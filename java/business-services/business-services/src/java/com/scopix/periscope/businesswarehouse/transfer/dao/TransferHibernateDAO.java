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
 * TransferHibernateDAO.java
 *
 * Created on 16-06-2008, 01:53:46 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.dao;

import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesLog;
import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesStatus;
import com.scopix.periscope.businesswarehouse.transfer.commands.ProofBW;
import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.ObservedSituationEvaluation;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = TransferHibernateDAO.class)
public class TransferHibernateDAO extends DAOHibernate<BusinessObject, Integer> {

    private Logger log = Logger.getLogger(TransferHibernateDAO.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource ds) {
        setJdbcTemplate(new JdbcTemplate(ds));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Close conection, close statement and close result set
     *
     * @param rs
     * @param st
     */
    private void closeConnection(Connection con, ResultSet rs, Statement st) {
        log.debug("init");
        if (rs != null) {
            try {
                rs.close();
                rs = null;
            } catch (SQLException ex) {
                log.error("rs error " + ex.getMessage());
            }
        }
        if (st != null) {
            try {
                st.close();
                st = null;
            } catch (SQLException ex) {
                log.error("st error " + ex.getMessage());
            }
        }
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException ex) {
                log.error("con error " + ex.getMessage());
            }
        }
        log.debug("end");
    }

    /**
     * Execute one by one the sqls in the list
     *
     * @param sqls
     */
    public void executeSQL(List<String> sqls) {
        log.info("init");
        for (String sql : sqls) {
            getJdbcTemplate().update(sql);
        }
        log.info("end");
    }

    /**
     * This method execute a insert into facts
     *
     * @param sql
     * @throws java.sql.SQLException
     */
    public void executeSQL(String sql) throws SQLException {
        log.info("init");
        if (sql.length() > 0) {
            getJdbcTemplate().update(sql);
        } else {
            log.info("nada que ejecutar");
        }
        log.info("end");
    }

    /**
     * Obtain a list of a list of metric names from business services, all distinct conbinations of metric names posible form
     * observed_situation_evaluation where state is null
     *
     * @return
     * @throws java.sql.SQLException
     */
    public List<Map> getMetricNamesFromBS() throws SQLException {
        log.debug("init");
        List<Map> metricNames = new ArrayList<Map>();
        ResultSet rs = null;
        Statement st = null;
        Connection conBS = null;
        try {
            conBS = getJdbcTemplate().getDataSource().getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT DISTINCT  ");
            for (int i = 1; i <= 50; i++) {
                sql.append("(select mt.description from metric_template mt, metric m, observed_metric om  ");
                sql.append("    where ose.metric_id").append(i).append(" = om.id ");
                sql.append("    and m.id = om.metric_id ");
                sql.append("    and mt.id = m.metric_template_id) AS metric").append(i).append(", ");

            }
            sql.append(" ose.metric_count, ose.product, ose.area_id  ");
            sql.append(" FROM observed_situation_evaluation ose ");
            sql.append(" WHERE state is null ");
            st = conBS.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs != null) {
                while (rs.next()) {
                    Map map = new HashMap();
                    Integer metricCount = rs.getInt("metric_count");

                    if (metricCount != null && metricCount > 0) {
                        List<String> names = new ArrayList<String>();
                        for (int i = 0; i < metricCount; i++) {
                            String name = rs.getString("metric" + (i + 1));
                            names.add(name);
                        }
                        map.put("names", names);
                        map.put("product", rs.getString("product"));
                        map.put("area", rs.getInt("area_id"));

                    }
                    metricNames.add(map);
                }
            }
        } finally {
            closeConnection(conBS, rs, st);
            log.debug("end");
        }
        return metricNames;
    }

    /**
     * This method return a list of observed situation evaluations that state is null and sentTOMIS is false
     *
     * @return
     */
    public List<ObservedSituationEvaluation> getObservedSituationEvaluations(Date date) {
        log.debug("init");
        List<ObservedSituationEvaluation> oses = null;
        Session session = this.getSession();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            for (int i = 1; i <= 50; i++) {
                if (i > 1) {
                    sql.append(",");
                }
                sql.append("metric_id").append(i).append(",").append("metric").append(i);
            }
            sql.append(", ose.id");
            sql.append(", ose.metric_count");
            sql.append(", os.id as observed_situation_id");
            sql.append(", ose.area_id");
            sql.append(", ose.product");
            sql.append(", ose.store_id");
            sql.append(", ose.rule_name");
            sql.append(", ose.compliant");
            sql.append(", ose.target");
            sql.append(" from observed_situation_evaluation ose, observed_situation os");
            sql.append(" where");
            sql.append(" ose.observed_situation_id = os.id");
            sql.append(" and ose.state is null");
            sql.append(" and sent_tomis = false");
            sql.append(" and os.observed_situation_date = ?");
            sql.append(" order by ose.id");

            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql.toString(), new Object[]{date});

            oses = new ArrayList<ObservedSituationEvaluation>();
            ObservedSituationEvaluation ose = null;
            ObservedSituation os = null;
            for (Map<String, Object> m : list) {
                ose = new ObservedSituationEvaluation();
                ose.setId((Integer) m.get("id"));

                Integer metricCount = (Integer) m.get("metric_count");
                ose.setMetricCount(metricCount);
                ose.setAreaId((Integer) m.get("area_id"));
                ose.setProduct((String) m.get("product"));
                ose.setStoreId((Integer) m.get("store_id"));
                ose.setRuleName((String) m.get("rule_name"));
                ose.setCompliant((Integer) m.get("compliant"));
                ose.setTarget((Double) m.get("target"));

                os = new ObservedSituation();
                os.setId((Integer) m.get("observed_situation_id"));
                ose.setObservedSituation(os);

                for (int j = 1; j <= metricCount; j++) {
                    setValueFromProperty("metricId" + j, ObservedSituationEvaluation.class, ose, m.get("metric_id" + j));
                    setValueFromProperty("metric" + j, ObservedSituationEvaluation.class, ose, m.get("metric" + j));
                }

                oses.add(ose);
            }
        } finally {
            this.releaseSession(session);
        }
        log.debug("end");
        return oses;
    }

    private void setValueFromProperty(String propertyName, Class classObject, Object instanciatedObject, Object value) {

        String name = "set" + StringUtils.capitalize(propertyName);

        try {
            Method m = classObject.getMethod(name, value.getClass());
            m.invoke(instanciatedObject, value);
        } catch (IllegalArgumentException e) {
            log.warn(e);
        } catch (InvocationTargetException e) {
            log.warn(e);
        } catch (NoSuchMethodException e) {
            log.warn(e);
        } catch (SecurityException e) {
            log.warn(e);
        } catch (IllegalAccessException e) {
            log.warn(e);
        }
    }

    /**
     * This method get the min date of evidences for a observed situation
     *
     * @param observedSituationId
     * @return
     * @throws java.sql.SQLException
     */
    public Date getMinDateOfEvidence(Integer observedSituationId) throws SQLException {
        log.debug("init");
        Date date = null;
        ResultSet rs = null;
        Statement st = null;
        Connection conBS = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT min(e.evidence_date) AS date FROM evidence e, observed_metric om, ");
            sql.append("rel_observed_metric_evidence rel ");
            sql.append("WHERE om.observed_situation_id = ").append(observedSituationId);
            sql.append(" AND rel.observed_metric_id = om.id AND rel.evidence_id = e.id");

            conBS = getJdbcTemplate().getDataSource().getConnection();
            st = conBS.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs != null) {
                while (rs.next()) {
                    date = rs.getTimestamp("date");
                }
            }
        } finally {
            closeConnection(conBS, rs, st);
            log.debug("end");
        }
        return date;
    }

    /**
     * This method return a list of Indicator values that state is null and sentTOMIS is false
     *
     * @return
     */
    public Set<Integer> getIndicatorValues(Date date) {
        log.debug("init");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        List<IndicatorValues> indicatorValues = null;
        Set<Integer> indicatorIds = new HashSet<Integer>();
        ResultSet rs = null;
        Statement st = null;
        Connection conBS = null;

        try {

            StringBuilder sql = new StringBuilder();
            sql.append("select iv.id as id from indicator_values iv, observed_situation os");
            sql.append(" where ");
            sql.append(" iv.state is null");
            sql.append(" and iv.sent_tomis = false");
            sql.append(" and iv.observed_situation_id = os.id");
            sql.append(" and os.observed_situation_date <= timestamp '").append(sdf.format(date)).append("'");

            conBS = getJdbcTemplate().getDataSource().getConnection();
            st = conBS.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs != null) {
                while (rs.next()) {
                    indicatorIds.add(rs.getInt("id"));
                }
            }

//            session = this.getSession();
//            Criteria criteria = session.createCriteria(IndicatorValues.class);
//            criteria.addOrder(Order.asc("id"));
//            criteria.add(Restrictions.isNull("state"));
//            criteria.add(Restrictions.eq("sentToMIS", false));
//            criteria.createCriteria("observedSituation").add(Restrictions.le("observedSituationDate", date));
//            indicatorValues = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (SQLException sqlex) {
            log.error("error = " + sqlex.getMessage());
        } finally {
            closeConnection(conBS, rs, st);
            log.debug("end");
        }

        return indicatorIds;
    }

    /**
     * Metodo que devuelve el detalle de un indicator dado, para ser transferido a BW
     *
     * @param indicatorId
     * @return Map<String, Object> datos a devolver: productDescription areaTypeId indicator.name indicator.storeId
     * indicator.numerator indicator.denominator
     *
     */
    public Map<String, Object> getIndicatorDetail(Integer indicatorId) {
        log.debug("init");
        Map<String, Object> result = new HashMap<String, Object>();

        try {

            StringBuilder sql = new StringBuilder();
            sql.append("select iv.id, iv.denominator, iv.numerator, iv.store_id, i.name as indicator_name,");
            sql.append(" p.description as product_description, at.id as area_type_id, os.id as observed_situation_id");
            sql.append(" from indicator_values iv, indicator i, observed_situation os, situation s,");
            sql.append("    situation_template st, product p, area_type at");
            sql.append(" where iv.indicator_id = i.id and");
            sql.append("    iv.observed_situation_id = os.id and");
            sql.append("    os.situation_id = s.id and");
            sql.append("    s.situation_template_id = st.id and");
            sql.append("    st.product_id = p.id and");
            sql.append("    st.area_type_id = at.id and");
            sql.append("    iv.id = ").append(indicatorId);

            result = getJdbcTemplate().queryForMap(sql.toString());

        } catch (Exception ex) {
            log.error("error = " + ex.getMessage());
        }

        return result;
    }

    public List<ProductAndAreaDTO> getProductsAndAreas() {
        log.debug("init");
        List<ProductAndAreaDTO> list = new ArrayList<ProductAndAreaDTO>();
        ResultSet rs = null;
        Statement st = null;
        Connection conBS = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select distinct p.description as product, a.description as area, a.id from situation_template st, ");
            sql.append("product p, area_type a where p.id = st.product_id and a.id = st.area_type_id and st.active = true");

            conBS = getJdbcTemplate().getDataSource().getConnection();
            st = conBS.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs != null) {
                while (rs.next()) {
                    ProductAndAreaDTO dto = new ProductAndAreaDTO();
                    dto.setProduct(rs.getString("product"));
                    dto.setArea(rs.getString("area"));
                    dto.setAreaId(rs.getInt("id"));

                    list.add(dto);
                }
            }
        } catch (SQLException ex) {
            log.error("error = " + ex.getMessage());
        } finally {
            closeConnection(conBS, rs, st);
            log.debug("end");
        }

        log.debug("end");
        return list;
    }

    public List<IndicatorProductAndAreaType> getIndicatorProductAndAreaTypes(Integer indicatorId) {
        List<IndicatorProductAndAreaType> ipaats = null;
        Session session = null;
        try {
            if (indicatorId != null) {
                session = this.getSession();
                Criteria criteria = session.createCriteria(IndicatorProductAndAreaType.class);
                criteria.add(Restrictions.eq("indicator.id", indicatorId));
                ipaats = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
            }
        } finally {
            this.releaseSession(session);
        }
        return ipaats;
    }

    public LinkedHashMap<Integer, String> getCalendarInitDates() {
        LinkedHashMap<Integer, String> lista = new LinkedHashMap<Integer, String>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            String sql = "select * from calendar_init_dates order by year_calendar asc";

            con = getJdbcTemplate().getDataSource().getConnection();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    lista.put(rs.getInt("year_calendar"), rs.getString("init_date"));
                }
            }
        } catch (Exception ex) {
            log.debug("Error: " + ex.getMessage(), ex);
        } finally {
            closeConnection(con, rs, st);
            log.debug("end");
        }

        return lista;
    }

    /**
     * Inserta una tupla de datos que no pudieron ser eliminado en proceso manual desde quality
     */
    public void addRejectError(List<Integer> oseIds, List<Integer> valIds) {
        if (oseIds.size() > 500 || valIds.size() > 500) {
            List<Object[]> arraysOseIds = new ArrayList<Object[]>();
            while (oseIds.size() > 0) {
                Object[] oseInsert = ArrayUtils.subarray(oseIds.toArray(), 0, 500);
                arraysOseIds.add(oseInsert);
                oseIds.removeAll(Arrays.asList(oseInsert));
            }
            List<Object[]> arraysValIds = new ArrayList<Object[]>();
            while (oseIds.size() > 0) {
                Object[] valInsert = ArrayUtils.subarray(valIds.toArray(), 0, 500);
                arraysValIds.add(valInsert);
                valIds.removeAll(Arrays.asList(valInsert));
            }
            for (int i = 0; i < arraysOseIds.size(); i++) {
                Object[] osIds = arraysOseIds.get(i);
                Object[] vaIds = null;
                if (arraysValIds.size() > i) {
                    vaIds = arraysValIds.get(i);
                } else {
                    vaIds = new Object[]{""};
                }
                StringBuilder sql = new StringBuilder("insert into reject_error ");
                sql.append("(id, ose_id, indicator_values) values(");
                sql.append("nextval('reject_error_seq'), ");
                sql.append("'").append(StringUtils.join(osIds, ",")).append("', ");
                sql.append("'").append(StringUtils.join(vaIds, ",")).append("' ");
                sql.append(")");
                try {
                    getJdbcTemplate().update(sql.toString());
                } catch (DataAccessException e) {
                    log.warn("No es posible insertar reject_error [" + StringUtils.join(oseIds.toArray(), ",") + "]"
                            + "[" + StringUtils.join(valIds.toArray(), ",") + "]" + e);
                }
            }
            if (arraysValIds.size() > arraysOseIds.size()) {
                for (int i = (arraysOseIds.size() - 1); i < arraysValIds.size(); i++) {
                    Object[] vaIds = arraysValIds.get(i);
                    StringBuilder sql = new StringBuilder("insert into reject_error ");
                    sql.append("(id, ose_id, indicator_values) values(");
                    sql.append("nextval('reject_error_seq'), ");
                    sql.append("'', ");
                    sql.append("'").append(StringUtils.join(vaIds, ",")).append("' ");
                    sql.append(")");
                    try {
                        getJdbcTemplate().update(sql.toString());
                    } catch (DataAccessException e) {
                        log.warn("No es posible insertar reject_error [" + StringUtils.join(oseIds.toArray(), ",") + "]"
                                + "[" + StringUtils.join(valIds.toArray(), ",") + "]" + e);
                    }
                }
            }

        } else {
            StringBuilder sql = new StringBuilder("insert into reject_error ");
            sql.append("(id, ose_id, indicator_values) values(");
            sql.append("nextval('reject_error_seq'), ");
            sql.append("'").append(StringUtils.join(oseIds.toArray(), ",")).append("', ");
            sql.append("'").append(StringUtils.join(valIds.toArray(), ",")).append("' ");
            sql.append(")");
            try {
                getJdbcTemplate().update(sql.toString());
            } catch (DataAccessException e) {
                log.warn("No es posible insertar reject_error [" + StringUtils.join(oseIds.toArray(), ",") + "]"
                        + "[" + StringUtils.join(valIds.toArray(), ",") + "]" + e);
            }
        }
    }

    public List<Map> getRejectError() {

        List<Map> l = new ArrayList<Map>();
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //se cambia formato por problemas de proxy connection
            con = getJdbcTemplate().getDataSource().getConnection();
            st = con.createStatement();
            rs = st.executeQuery("select * from reject_error ");
            while (rs.next()) {
                Map values = new HashMap();
                values.put("id", rs.getInt("id"));
                values.put("ose_id", rs.getString("ose_id"));
                values.put("indicator_values", rs.getString("indicator_values"));
                l.add(values);
            }
        } catch (SQLException e) {
            log.error("No es posible recuperar reject_error " + e);
        } catch (DataAccessException e) {
            log.error("No es posible recuperar reject_error " + e);
        } finally {
            closeConnection(con, rs, st);
        }
        return l;
    }

    public void deleteRejectError(Set<Integer> idsDelete) {

        if (idsDelete != null && idsDelete.size() > 0) {
            StringBuilder sqlDelete = new StringBuilder("delete from reject_error ");
            sqlDelete.append(" where id in(");
            sqlDelete.append(StringUtils.join(idsDelete.toArray(), ",")).append(");");
            Connection con = null;
            Statement st = null;
            try {
                con = getJdbcTemplate().getDataSource().getConnection();
                //se modifica ejecucion por problemas de proxy connection
                //getJdbcTemplate().update(sqlDelete.toString());
                st = con.createStatement();
                st.executeUpdate(sqlDelete.toString());
            } catch (SQLException e) {
                log.warn("No es posible eliminar reject_error [" + StringUtils.join(idsDelete.toArray(), ",") + "]" + e);
            } catch (DataAccessException e) {
                log.warn("No es posible eliminar reject_error [" + StringUtils.join(idsDelete.toArray(), ",") + "]" + e);
            } finally {
                closeConnection(con, null, st);
            }
        }
    }

    public List<EvidenceProvider> getEvidenceProviderListByIds(List<Integer> evidenceProviderIdListBW) {
        log.info("[evidenceProviderIdListBW: " + StringUtils.join(evidenceProviderIdListBW, ",") + "]");
        List<EvidenceProvider> list = null;
        Session session = this.getSession();

        if (evidenceProviderIdListBW != null && evidenceProviderIdListBW.size() > 0) {
            Criteria criteria = session.createCriteria(EvidenceProvider.class);

            criteria.add(Restrictions.in("id", evidenceProviderIdListBW));
            list = criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }

        return list;
    }

    public List<EvidenceProvider> getEvidenceProviderListNotInIds(List<Integer> evidenceProviderIdListBW) {
        List<EvidenceProvider> list = null;

        Session session = this.getSession();
        Criteria criteria = session.createCriteria(EvidenceProvider.class);

        if (evidenceProviderIdListBW != null && evidenceProviderIdListBW.size() > 0) {
            criteria.add(Restrictions.not(Restrictions.in("id", evidenceProviderIdListBW)));
        }

        list = criteria.list();

        return list;
    }

    public List<TransferProofFilesLog> getPendingFilesToTransfer() {
        List<TransferProofFilesLog> list = null;
        Session session = this.getSession();

        Criteria criteria = session.createCriteria(TransferProofFilesLog.class);

        criteria.add(Restrictions.eq("status", TransferProofFilesStatus.CREATED));
        list = criteria.list();

        return list;
    }

    public Date getInitialTransferDateForProofFiles(Date endDate, Date uploadPivotDate) {
        Date res = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select min(os.observed_situation_date) from");
        sql.append(" observed_situation os, observed_metric om, evidence_evaluation ee, proof");
        sql.append(" where");
        sql.append(" os.observed_situation_date <= '").append(DateFormatUtils.format(endDate, "yyyy-MM-dd")).append("'");
        sql.append(" and os.observed_situation_date >= '").append(DateFormatUtils.format(uploadPivotDate, "yyyy-MM-dd"))
                .append("'");
        sql.append(" and os.id = om.observed_situation_id");
        sql.append(" and om.id = ee.observed_metric_id");
        sql.append(" and ee.id = proof.evidence_evaluation_id");
        sql.append(" and proof.sent_tomis = false");
        sql.append(" and proof.sent_tomisdata = true");
        sql.append(" and ee.evidence_result is not null");
        sql.append(" and ee.rejected = false");

        try {
            res = (Date) getJdbcTemplate().queryForObject(sql.toString(), Date.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("no existen archivos pendientes por subir");
        }

        return res;
    }

    public Date getInitialTransferDateForProofData(Date endDate, Date uploadPivotDate) {
        Date res = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select min(os.observed_situation_date) from");
        sql.append(" observed_situation os, observed_metric om, evidence_evaluation ee, proof");
        sql.append(" where");
        sql.append(" os.observed_situation_date <= '").append(DateFormatUtils.format(endDate, "yyyy-MM-dd")).append("'");
        sql.append(" and os.observed_situation_date >= '").append(DateFormatUtils.format(uploadPivotDate, "yyyy-MM-dd")).append("'");
        sql.append(" and os.id = om.observed_situation_id");
        sql.append(" and om.id = ee.observed_metric_id");
        sql.append(" and ee.id = proof.evidence_evaluation_id");
        sql.append(" and proof.sent_tomisdata = false");
        sql.append(" and ee.evidence_result is not null");
        sql.append(" and ee.rejected = false");

        try {
            res = (Date) getJdbcTemplate().queryForObject(sql.toString(), Date.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("no existen archivos pendientes por subir");
        }

        return res;
    }

    public Date getInitialTransferDateForOSE(Date endDate) {
        Date res = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select min(os.observed_situation_date) from");
        sql.append(" observed_situation_evaluation ose, observed_situation os, observed_metric om, evidence_evaluation ee");
        sql.append(" where");
        sql.append(" os.observed_situation_date <= '").append(DateFormatUtils.format(endDate, "yyyy-MM-dd")).append("'");
        sql.append(" and ose.observed_situation_id = os.id");
        sql.append(" and os.id = om.observed_situation_id");
        sql.append(" and om.id = ee.observed_metric_id");
        sql.append(" and ee.evidence_result is not null");
        sql.append(" and ose.sent_tomis = false;");

        try {
            res = (Date) getJdbcTemplate().queryForObject(sql.toString(), Date.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("no existen archivos pendientes por subir");
        }

        return res;
    }

    public List<ProofBW> getTransferProofList(Date time, Date uploadPivotDate) {
        List<ProofBW> res = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select proof.*, place.name as place_name from");
        sql.append(" place, metric, observed_metric om, observed_situation os, evidence_evaluation ee, proof,");
        sql.append(" observed_situation_evaluation ose");
        sql.append(" where");
        sql.append(" os.observed_situation_date = '").append(DateFormatUtils.format(time, "yyyy-MM-dd")).append("'");
        sql.append(" and os.observed_situation_date >= '").append(DateFormatUtils.format(uploadPivotDate, "yyyy-MM-dd")).append("'");
        sql.append(" and place.id = metric.store_id");
        sql.append(" and metric.id = om.metric_id");
        sql.append(" and om.observed_situation_id = os.id ");
        sql.append(" and om.id = ee.observed_metric_id");
        sql.append(" and ose.observed_situation_id = os.id");
        sql.append(" and ee.id = proof.evidence_evaluation_id");
        sql.append(" and proof.sent_tomis = false");
        sql.append(" and proof.sent_tomisdata = true");
        sql.append(" and ee.evidence_result is not null");
        sql.append(" and ee.rejected = false");

        try {
            List<Map<String, Object>> mapResult = getJdbcTemplate().queryForList(sql.toString());
            ProofBW p = null;
            for (Map m : mapResult) {
                p = new ProofBW();
                p.setId((Integer) m.get("id"));
                p.setPathWithMarks((String) m.get("path_with_marks"));
                p.setPathWithoutMarks((String) m.get("path_without_marks"));
                p.setStoreName((String) m.get("place_name"));

                res.add(p);
            }

        } catch (EmptyResultDataAccessException e) {
            log.warn("no existen archivos pendientes por subir");
        }

        return res;
    }

    public void updateTransferProofFilesLogStatus(Integer id, TransferProofFilesStatus transferProofFilesStatus) {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE transfer_proof_files_log SET status = '").append(transferProofFilesStatus).append("'");

        getJdbcTemplate().update(sql.toString());
    }

    public void updateProofSentToMis(List<Integer> proofIds, Boolean val) {
        StringBuilder sql = new StringBuilder();
        String proofsIdsStr = StringUtils.join(proofIds, ",");

        sql.append("UPDATE proof set sent_tomis = ").append(val.booleanValue()).append(" where id in (");
        sql.append(proofsIdsStr).append(")");

        getJdbcTemplate().update(sql.toString());
    }

    public void updateProofSentToMisData(List<Integer> proofIds, Boolean val) {
        StringBuilder sql = new StringBuilder();
        String proofsIdsStr = StringUtils.join(proofIds, ",");

        sql.append("UPDATE proof set sent_tomisdata = ").append(val.booleanValue()).append(" where id in (");
        sql.append(proofsIdsStr).append(")");

        getJdbcTemplate().update(sql.toString());
    }

    public String getMetricTypeName(Integer omId) {
        StringBuilder sql = new StringBuilder();

        sql.append("select mt.metric_type_element from observed_metric om, metric, metric_template mt");
        sql.append(" where");
        sql.append(" om.id = ").append(omId);
        sql.append(" and om.metric_id = metric.id");
        sql.append(" and metric.metric_template_id = mt.id");

        String resp = null;
        try {
            resp = (String) getJdbcTemplate().queryForObject(sql.toString(), String.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No existe metric_type_element para el observed_metric_id dado omId:" + omId);
        }

        return resp;
    }

    /**
     *
     * @param omId
     * @return Map Estructura del Map: id, evidence_date, evidence_provider_id, store_id
     */
    public List<Map<String, Object>> getEvidencesForObservedMetric(Integer omId) {
        StringBuilder sql = new StringBuilder();

        sql.append("select distinct evidence.id, evidence.evidence_date,");
        sql.append("    (select distinct(evidence_provider_id)  from evidence_request er, rel_evidence_request_evidence rel");
        sql.append("    where");
        sql.append("    rel.evidence_id = evidence.id");
        sql.append("    and rel.evidence_request_id = er.id) as evidence_provider_id,");
        sql.append("    (select distinct(ep.store_id) from evidence_request er, rel_evidence_request_evidence rel, ");
        sql.append("    evidence_provider ep");
        sql.append("    where");
        sql.append("    rel.evidence_id = evidence.id");
        sql.append("    and rel.evidence_request_id = er.id");
        sql.append("    and ep.id = er.evidence_provider_id) as store_id");
        sql.append(" from evidence, rel_observed_metric_evidence rel");
        sql.append(" where");
        sql.append(" rel.observed_metric_id = ").append(omId);
        sql.append(" and rel.evidence_id = evidence.id");
        sql.append(" order by evidence.id");

        List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql.toString());

        return mapList;
    }

    /**
     *
     * @param omId
     * @return Map Estructura del Map: id, proof_result, path_with_marks, path_without_marks, proof_order, proof_result,
     * evidence_id
     */
    public List<Map<String, Object>> getProofsForObservedMetric(Integer omId) {
        StringBuilder sql = new StringBuilder();


        //Aqui se obtienen todos los proof con estado rejected = false
        sql.append("select proof.id, proof_result, path_with_marks, path_without_marks, proof_order, proof_result,");
        sql.append(" sent_tomis, evidence_id,");
        sql.append(" ee.rejected");
        sql.append(" from proof, evidence_evaluation ee ");
        sql.append(" where ");
        sql.append(" ee.observed_metric_id = ").append(omId);
        sql.append(" and proof.evidence_evaluation_id = ee.id");
        sql.append(" and ee.rejected = false");
        sql.append(" order by evidence_id, proof_order");

        List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql.toString());

        return mapList;
    }

    public Integer getNextIdEvidenceAndProof() {
        Integer id = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT nextval('evidences_and_proofs_id_seq') ");
        id = getJdbcTemplate().queryForInt(sql.toString());
        return id;
    }

    public List<Integer> getObservedMetricIds(Date time) {
        List<Integer> omIds = null;

        StringBuilder sql = new StringBuilder();

        sql.append("select distinct om.id from");
        sql.append(" place, metric, observed_metric om, observed_situation os, evidence_evaluation ee, proof");
        sql.append(" where");
        sql.append(" os.observed_situation_date = '").append(DateFormatUtils.format(time, "yyyy-MM-dd")).append("'");
        sql.append(" and place.id = metric.store_id");
        sql.append(" and metric.id = om.metric_id");
        sql.append(" and om.observed_situation_id = os.id ");
        sql.append(" and om.id = ee.observed_metric_id");
        sql.append(" and ee.id = proof.evidence_evaluation_id");
        sql.append(" and proof.sent_tomisdata = false");
        sql.append(" and ee.evidence_result is not null");
        sql.append(" and ee.rejected = false");
        sql.append(" order by om.id");

        omIds = getJdbcTemplate().queryForList(sql.toString(), Integer.class);

        return omIds;
    }

    public List<Integer> getProofToClean() {
        List<Integer> proofIds = null;
        StringBuilder sql = new StringBuilder();

        sql.append("select p.id from proof p, evidence_evaluation ee");
        sql.append(" where p.evidence_evaluation_id = ee.id");
        sql.append(" and p.sent_tomisdata = true");
        sql.append(" and ee.rejected = true");

        proofIds = getJdbcTemplate().queryForList(sql.toString(), Integer.class);

        return proofIds;
    }

    public Proof getProof(Integer id) {
        log.info("start");
        Proof proof = null;

        StringBuilder sql = new StringBuilder();

        sql.append("select p.id as proof_id, e.id as evidence_id, p.path_with_marks, p.path_without_marks");
        sql.append(" from evidence e, proof p");
        sql.append(" where p.evidence_id = e.id");
        sql.append(" and p.id = ").append(id);

        List<Map<String, Object>> mapResult = getJdbcTemplate().queryForList(sql.toString());
        if (mapResult.size() == 1) {
            proof = new Proof();
            proof.setId((Integer) mapResult.get(0).get("proof_id"));
            proof.setPathWithMarks((String) mapResult.get(0).get("path_with_marks"));
            proof.setPathWithoutMarks((String) mapResult.get(0).get("path_without_marks"));

            Integer evidenceId = (Integer) mapResult.get(0).get("evidence_id");
            Evidence evidence = new Evidence();
            evidence.setId(evidenceId);

            proof.setEvidence(evidence);

            log.debug("proofId: " + proof.getId() + ", evidenceId: " + proof.getEvidence().getId());
        }

        log.info("end");
        return proof;
    }

    public List<Proof> getProofList(Set<Integer> proofIds) {
        log.info("start");
        List<Proof> proofs = null;
        String proofsIdsStr = StringUtils.join(proofIds, ",");

        StringBuilder hql = new StringBuilder();

        hql.append("select");
        hql.append(" p.id as id,");
        hql.append(" p.pathWithMarks as pathWithMarks,");
        hql.append(" p.pathWithoutMarks as pathWithoutMarks,");
        hql.append(" p.proofDate as proofDate,");
        hql.append(" p.sentToMIS as sentToMIS,");
        hql.append(" p.sentToMISData as sentToMISData");
        hql.append(" from Proof p");
        hql.append(" where");
        hql.append(" p.id in (").append(proofsIdsStr).append(")");

        proofs = this.getSession().createQuery(hql.toString()).setResultTransformer(Transformers.aliasToBean(Proof.class)).list();

        log.debug("proofs size: " + proofs.size());
        log.info("end");
        return proofs;
    }

    public void updateProofsTransferProofFileLogId(Set<Integer> proofIds, Integer transferProofFileLogId) {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        String proofsIdsStr = StringUtils.join(proofIds, ",");

        sql.append("UPDATE proof SET transfer_proof_file_log_id = ").append(transferProofFileLogId).append(" where id in (");
        sql.append(proofsIdsStr).append(")");

        getJdbcTemplate().update(sql.toString());

        log.info("end");
    }
}
