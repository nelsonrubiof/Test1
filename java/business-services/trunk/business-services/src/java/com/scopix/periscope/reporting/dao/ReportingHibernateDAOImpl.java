/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
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
 *  ReportingHibernateDAOImpl.java
 * 
 *  Created on 13-01-2011, 06:03:04 PM
 * 
 */
package com.scopix.periscope.reporting.dao;

import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.DAOHibernate;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.reporting.ProcessState;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.UploadProcessDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = ReportingHibernateDAOImpl.class)
public class ReportingHibernateDAOImpl extends DAOHibernate<BusinessObject, Integer> implements ReportingHibernateDAO {

    private Logger log = Logger.getLogger(ReportingHibernateDAOImpl.class);
    private JdbcTemplate jdbcTemplate;
    //sql para la generacion o consultas de datos
    private String sqlValidarOmIdEE;
    private String sqlTargetByObservedSituationId;
    private String sqlValudacionOsIdReporting;
    private String sqlBaseCantDo;
    private String sqlCantDoReason;
    private String sqlInsertReportingData;
    private String sqlUpdateReportingData;

    public void setDataSource(DataSource ds) {
        setJdbcTemplate(new JdbcTemplate(ds));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UploadProcess getUploadProcessInExecutionOrLastSend(ProcessState processState) throws ScopixException {
        log.debug("start");
        Criteria c = this.getSession().createCriteria(UploadProcess.class);
        c.add(Restrictions.eq("processState", processState));
        c.addOrder(Order.desc("startDateProcess"));
        c.setMaxResults(1);

        UploadProcess up = (UploadProcess) c.uniqueResult();
        //Ojo esto puede ser null
        log.debug("end");
        return up;
    }

    @Override
    public List<UploadProcessDetail> getUploadProcessDetailList(ProcessState processState) throws ScopixException {
        log.debug("start");
        Criteria c = this.getSession().createCriteria(UploadProcessDetail.class);
        c.createCriteria("uploadProcess").add(Restrictions.eq("processState", processState));
        List<UploadProcessDetail> l = (List<UploadProcessDetail>) c.list();
        log.debug("end");
        return l;
    }

    @Override
    public Integer getTotalRecords(UploadProcessDetail detail) throws ScopixException {
        log.debug("start");
        Date dateLimit = detail.getDateEnd();
        log.debug(dateLimit);
        dateLimit = DateUtils.addHours(dateLimit, 23);
        dateLimit = DateUtils.addMinutes(dateLimit, 59);
        dateLimit = DateUtils.addSeconds(dateLimit, 59);

        StringBuilder sql = new StringBuilder("select count(distinct rd.observed_situation_id) as cant ");
        sql.append(" from reporting_data rd ");
        sql.append(" where rd.evidence_date <= ?");
        sql.append(" and rd.sent_tomis = false ");
        sql.append(" and rd.area_id = ?");
        sql.append(" and rd.store_id = ?");


        Integer cantRecords = getJdbcTemplate().queryForInt(sql.toString(),
                new Object[]{dateLimit, detail.getAreaType().getId(), detail.getStore().getId()});
        log.debug("end");
        return cantRecords;
    }

    @Override
    public List<ProductAndAreaDTO> getProductsAndAreasByAreaTypes(Integer[] areaTypeIds) {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append(" distinct p.description as product, p.id as productId, ");
        sql.append(" a.description as area, ");
        sql.append(" a.id as areaId");
        sql.append(" from situation_template st, ");
        sql.append(" product p, area_type a");
        sql.append(" where p.id = st.product_id and ");
        sql.append(" a.id = st.area_type_id"); //and st.active = true
        List<Map<String, Object>> l = getJdbcTemplate().queryForList(sql.toString());

        List<ProductAndAreaDTO> data = new ArrayList<ProductAndAreaDTO>();
        for (Map<String, Object> m : l) {
            ProductAndAreaDTO dto = new ProductAndAreaDTO();
            dto.setArea((String) m.get("area"));
            dto.setAreaId((Integer) m.get("areaId"));
            dto.setProduct((String) m.get("product"));
            dto.setProductId((Integer) m.get("productId"));
            data.add(dto);
        }
        log.debug("end");
        return data;
    }

    @Override
    public List<Map> getMetricNamesFromBS() {
        log.debug("start");
        List<Map> metricNames = new ArrayList<Map>();

        StringBuilder sql = new StringBuilder();

        sql.append("select ");
        sql.append(" st.id as st_id, ");
        sql.append(" mt.id as mt_id, ");
        sql.append(" mt.description as mt_name, ");
        sql.append(" p.description as product_name, ");
        sql.append(" st.area_type_id as area_id ");
        sql.append(" from situation_template st, metric_template mt, product p, rel_metric_template_situation_template rel ");
        sql.append(" where ");
        sql.append(" st.product_id = p.id ");
        sql.append(" and rel.metric_template_id = mt.id ");
        sql.append(" and rel.situation_template_id = st.id ");
        sql.append(" order by st.id, mt.id");

        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql.toString());

        Map<Integer, Map> values = new HashMap<Integer, Map>();
        for (Map m : list) {

            Integer stId = (Integer) m.get("st_id");
            String product = (String) m.get("product_name");
            Integer areaId = (Integer) m.get("area_id");
            Integer mtId = (Integer) m.get("mt_id");
            String mtDesc = (String) m.get("mt_name");

            LinkedHashMap metricas = null;
            Map data = null;
            if (values.containsKey(stId)) {
                data = values.get(stId);
                metricas = (LinkedHashMap) data.get("metricas");
            } else {
                data = new LinkedHashMap();
                data.put("product", product);
                data.put("area", areaId);
                metricas = new LinkedHashMap();
                data.put("metricas", metricas);
                values.put(stId, data);

            }
            Map metrica = new HashMap();
            metrica.put("id", mtId);
            metrica.put("desc", mtDesc);
            metricas.put("data" + mtId, metrica);

        }

        //revisar generacion de datos
        for (Integer stId : values.keySet()) {
            Map map = new HashMap();

            String product = (String) values.get(stId).get("product");
            Integer areaId = (Integer) values.get(stId).get("area");

            map.put("product", product);
            map.put("area", areaId);
            map.put("st_id", stId);

            List<Integer> ids = new ArrayList<Integer>();
            List<String> names = new ArrayList<String>();
            LinkedHashMap<String, HashMap> metricas = (LinkedHashMap) values.get(stId).get("metricas");
            for (HashMap val : metricas.values()) {
                Integer id = (Integer) val.get("id");
                String desc = (String) val.get("desc");
                ids.add(id);
                names.add(desc);
            }
            map.put("names", names);
            map.put("ids", ids);
            metricNames.add(map);
        }
        log.debug("end");
        return metricNames;
    }

    @Override
    public List<Map<String, Object>> getAreaTypeFromBS() {
        log.debug("start");

        StringBuilder sql = new StringBuilder();
        sql.append("select * from area_type");
        List<Map<String, Object>> areaTypes = getJdbcTemplate().queryForList(sql.toString());
        log.debug("end");
        return areaTypes;
    }

    @Override
    public List<ReportingData> getReportingDataFormDetail(UploadProcessDetail detail) {
        log.debug("start");

        Date d = detail.getDateEnd();
        d = DateUtils.setHours(d, 23);
        d = DateUtils.setMinutes(d, 59);
        d = DateUtils.setSeconds(d, 59);

        StringBuilder sql = new StringBuilder();
        sql.append("select * from reporting_data where ");
        sql.append(" sent_tomis  = false ");
        sql.append(" and area_id = ?");
        sql.append(" and store_id = ?");
        sql.append(" and evidence_date  <=? ");
        sql.append(" order by id");

//        sql.append(" select rd.*, s.situation_template_id");
//        sql.append(" from reporting_data rd, observed_situation os, situation s");
//        sql.append(" where rd.sent_tomis = false ");
//        sql.append(" and rd.area_id = ? ");
//        sql.append(" and rd.store_id = ? ");
//        sql.append(" and rd.evidence_date  <=? ");
//        sql.append(" and os.id = rd.observed_situation_id ");
//        sql.append(" and s.id = os.situation_id ");
//        sql.append(" order by id");
        List<ReportingData> reportingDatas = getJdbcTemplate().query(sql.toString(),
                new Object[]{detail.getAreaType().getId(), detail.getStore().getId(), d}, new ReportingDataMapper());

        log.debug("end");
        return reportingDatas;
    }

    @Override
    public Date getDateOfEvidence(Integer observedMetricId) {
        log.debug("start");
        Date date;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(" distinct e.evidence_date AS date ");
        sql.append(" FROM evidence e,");
        sql.append(" rel_observed_metric_evidence rel ");
        sql.append(" WHERE rel.observed_metric_id= ").append(observedMetricId);
        sql.append(" AND rel.evidence_id = e.id");

        try {
            date = (Date) getJdbcTemplate().queryForObject(sql.toString(), Date.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("sql " + sql.toString() + " e:" + e);
            date = null;
        }
        log.debug("end");
        return date;
    }

    @Override
    public void saveReportingData(ReportingData data) {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("update reporting_data set ");
        sql.append(" sent_tomis = ").append(data.isSentToMIS()).append(", ");
        sql.append(" sent_tomisdate = ? , ");
        sql.append(" state = '").append(data.getState()).append("' ");
        sql.append(" where id = ").append(data.getId());

        getJdbcTemplate().update(sql.toString(), new Object[]{data.getSentToMISDate()});
        log.debug("end");
    }

    @Override
    public void rejectReportingDataByObservedMetricIds(List<Integer> observedMetricIds, String userName) {
        log.debug("start");

        String ids = StringUtils.join(observedMetricIds.toArray(), ",");
        try {
            StringBuilder sqlUpdate = new StringBuilder();
            sqlUpdate.append("update reporting_data set ");
            sqlUpdate.append(" sent_tomis = false ,");
            sqlUpdate.append(" reject = true ,");
            sqlUpdate.append(" rejected_date = now(),");
            sqlUpdate.append(" rejected_user = '").append(userName).append("' ");
            sqlUpdate.append(" where observed_situation_id in( ");
            sqlUpdate.append("select om.observed_situation_id from observed_metric om where om.id in (").append(ids).append(")");
            sqlUpdate.append(") and reject = false ");
            int rowAfected = getJdbcTemplate().update(sqlUpdate.toString());
            log.debug("actualizados " + rowAfected + " reporting_data");
        } catch (DataAccessException e) {
            log.error("No se pudo actualizar reporting data para omIds " + ids, e);
        }
        log.debug("end");
    }

    @Override
    public void rejectReportingDataByObservedSituationIds(Set<Integer> osIds, String evaluationUser) {
        log.debug("start");

        String ids = StringUtils.join(osIds.toArray(), ",");
        try {
            StringBuilder sqlUpdate = new StringBuilder();
            sqlUpdate.append("update reporting_data set ");
            sqlUpdate.append(" sent_tomis = false ,");
            sqlUpdate.append(" reject = true ,");
            sqlUpdate.append(" rejected_date = now(),");
            sqlUpdate.append(" rejected_user = '").append(evaluationUser).append("' ");
            sqlUpdate.append(" where observed_situation_id in( ").append(ids).append(" )");
            sqlUpdate.append(" and reject = false ");
            int rowAfected = getJdbcTemplate().update(sqlUpdate.toString());
            log.debug("actualizados " + rowAfected + " reporting_data");
        } catch (DataAccessException e) {
            log.error("No se pudo actualizar reporting data para omIds " + ids, e);
        }
        log.debug("end");
    }

    private Map<String, Object> initValuesInsert() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("startDate", null); //Date
        ret.put("endDate", null); //Date
        ret.put("reject", Boolean.FALSE); //Boolean
        ret.put("evaluationUser", null); //String

        ret.put("omId", null); //Integer
        ret.put("metricTemplateId", null); //Integer
        ret.put("evidenceDate", null); //Date
        ret.put("metricValue", null); //Double

        ret.put("areaId", null); //Integer
        ret.put("department", null); //String
        ret.put("storeId", null); //Integer
        return ret;

    }

    @Override
    public void genereDataFromObservedSituation() throws ScopixException {
        log.info("start");
        StringBuilder sqlBase = getSQLBase();
        StringBuilder sqlDataObservedMetricMetricTemplate = getSQLDataObservedMetricMetricTemplate();
        StringBuilder sqlDatosBasicos = getSQLDatosBasicos();
        StringBuilder sqlProduct = getSQLProduct();
        StringBuilder sqlDataEvidenceEvaluation = getSQLDataEvidenceEvaluationByOmId();
        StringBuilder sqlMetricValue = getSQLMetricValue();
        List<Integer> resultBase = getJdbcTemplate().queryForList(sqlBase.toString(), Integer.class);
        int count = 1;
        int totProceso = resultBase.size();
        for (Integer osId : resultBase) {
            List<Map<String, Object>> dataOMMT = getJdbcTemplate().queryForList(sqlDataObservedMetricMetricTemplate.toString(),
                    new Object[]{osId});
            log.info("[osId:" + osId + "][" + count + ":" + totProceso + "]");
            count++;
            if (!validarOmIdsVsEE(dataOMMT)) {
                //alguno de los observedMetric no tiene evidence evaluation asociados
                log.debug("Existen observedMetric no tiene evidence evaluation para el osId " + osId);
                continue;
            }
            Map<String, Object> valuesInsert = initValuesInsert();
            String product = (String) getJdbcTemplate().queryForObject(sqlProduct.toString(), new Object[]{osId}, String.class);
            Double target = getTargetFromObservedSituationEvaluation(osId);
            List<Map<String, Object>> datosBasicos = getJdbcTemplate().queryForList(
                    sqlDatosBasicos.toString(), new Object[]{osId});
            if (!datosBasicos.isEmpty()) {
                Map db = datosBasicos.get(0);
                valuesInsert.put("areaId", (Integer) db.get("area_id"));
                valuesInsert.put("department", (String) db.get("department"));
                valuesInsert.put("storeId", (Integer) db.get("store_id"));
            }
            //recorremos los observedMetric/MetricTemplate

            for (Map<String, Object> data : dataOMMT) {
                valuesInsert.put("startDate", null);
                valuesInsert.put("endDate", null);
                valuesInsert.put("reject", Boolean.FALSE);
                valuesInsert.put("evaluationUser", null);

                valuesInsert.put("omId", (Integer) data.get("omId"));
                valuesInsert.put("metricTemplateId", (Integer) data.get("mtId"));
                valuesInsert.put("evidenceDate", getDateOfEvidence((Integer) valuesInsert.get("omId")));

                Double metricValue = null;
                try {
                    metricValue = (Double) getJdbcTemplate().queryForObject(sqlMetricValue.toString(),
                            new Object[]{(Integer) valuesInsert.get("omId")}, Double.class);
                } catch (EmptyResultDataAccessException e) {
                    log.warn("no existe metricValue en db para [osId:" + osId + "][omId:"
                            + (Integer) valuesInsert.get("omId") + "]");
                }

                List<Map<String, Object>> dataEvidenceEvaluationOmId = getJdbcTemplate().queryForList(
                        sqlDataEvidenceEvaluation.toString(),
                        new Object[]{(Integer) valuesInsert.get("omId")});
                StringBuilder sqlExecutor = new StringBuilder();

                if (!dataEvidenceEvaluationOmId.isEmpty() && dataEvidenceEvaluationOmId.size() == 1) {
                    //solo pedimos el primero no deberia haber mas segun sql
                    Map m2 = dataEvidenceEvaluationOmId.get(0);

                    valuesInsert.put("startDate", (Date) m2.get("min"));
                    valuesInsert.put("endDate", (Date) m2.get("max"));
                    valuesInsert.put("reject", (Boolean) m2.get("rejected"));
                    valuesInsert.put("evaluationUser", (String) m2.get("evaluation_user"));
                    sqlExecutor = generateSqlExecutor(osId, metricValue, target, product, valuesInsert);
                    if (sqlExecutor.length() > 0) {
                        getJdbcTemplate().update(sqlExecutor.toString());
                    }
                } else if (!dataEvidenceEvaluationOmId.isEmpty() && dataEvidenceEvaluationOmId.size() > 1) {
                    for (Map<String, Object> m2 : dataEvidenceEvaluationOmId) {
                        valuesInsert.put("startDate", (Date) m2.get("min"));
                        valuesInsert.put("endDate", (Date) m2.get("max"));
                        valuesInsert.put("reject", (Boolean) m2.get("rejected"));
                        valuesInsert.put("evaluationUser", (String) m2.get("evaluation_user"));
                        sqlExecutor = generateSqlExecutor(osId, metricValue, target, product, valuesInsert);
                        if (sqlExecutor.length() > 0) {
                            getJdbcTemplate().update(sqlExecutor.toString());
                        }
                    }
                }
            }

        }

        log.info("end");

    }

    private StringBuilder generateSqlExecutor(Integer osId, Double metricValue, Double target, String product,
            Map<String, Object> values) {
        List<Map<String, Object>> cantDoList = getJdbcTemplate().queryForList(getSqlCantDoReason(), new Object[]{
            (Integer) values.get("omId"), (Boolean) values.get("reject"), (String) values.get("evaluationUser")});
        StringBuilder sqlExecutor = new StringBuilder();
        String cantDoReason = null;

        if (!cantDoList.isEmpty()) {
            Map dataCantDo = cantDoList.get(0);
            cantDoReason = (String) dataCantDo.get("cant_do_reason");
        }
        Boolean cantDo = (cantDoReason != null) ? Boolean.TRUE : Boolean.FALSE;

        if (verificaExisteDato(osId, (Integer) values.get("omId"), (String) values.get("evaluationUser"),
                (Boolean) values.get("reject"), cantDo)) {
            log.debug("existe data para [osId:" + osId + "][" + values.get("omId") + "][" + values.get("evaluationUser") + "]"
                    + "[" + values.get("reject") + "][" + cantDo + "]");
            return sqlExecutor;
        }

        sqlExecutor.append(getSqlInsert());
        sqlExecutor.append(" ").append((Integer) values.get("areaId")).append(", ");
        addParameter(values.get("department"), sqlExecutor, ",");
        sqlExecutor.append(" ").append((Integer) values.get("storeId")).append(", ");
        sqlExecutor.append(" ").append((Integer) values.get("osId")).append(", ");
        addParameter(values.get("evidenceDate"), sqlExecutor, ",");
        addParameter(values.get("startDate"), sqlExecutor, ",");
        addParameter(values.get("endDate"), sqlExecutor, ",");
        sqlExecutor.append(" 0, ");
        sqlExecutor.append(" ").append((Integer) values.get("omId")).append(", ");
        sqlExecutor.append(" ").append((Integer) values.get("metricTemplateId")).append(", ");
        addParameter(metricValue, sqlExecutor, ", ");
        addParameter(Boolean.FALSE, sqlExecutor, ", ");
        addParameter(values.get("reject"), sqlExecutor, ", ");
        addParameter(cantDo, sqlExecutor, ", ");
        addParameter(cantDoReason, sqlExecutor, ",");
        addParameter(values.get("evaluationUser"), sqlExecutor, ",");
        addParameter(target, sqlExecutor, ", ");
        addParameter(product, sqlExecutor, "");
        sqlExecutor.append(");");
        return sqlExecutor;
    }

    public void genereDataFromObservedSituationOld() throws ScopixException {
        log.debug("start");
        StringBuilder sqlBase = getSQLBase();
        StringBuilder sqlDatosBasicos = getSQLDatosBasicos();
        StringBuilder sqlEvaluationDate = getSQLEvaluationDate();
        StringBuilder sqlDataObservedMetricMetricTemplate = getSQLDataObservedMetricMetricTemplate();
        StringBuilder sqlInsert = new StringBuilder();
        StringBuilder sqlMetricValue = getSQLMetricValue();
        StringBuilder sqlProduct = getSQLProduct();
        List<Integer> resultBase = getJdbcTemplate().queryForList(sqlBase.toString(), Integer.class);
        int totProceso = resultBase.size();
        int count = 0;
        for (Integer osId : resultBase) {
            //Integer osId = (Integer) osId.get("id");
            log.info("osId:" + osId + " [" + count + ":" + totProceso + "]");
            count++;

            List<Map<String, Object>> dataOMMT = getJdbcTemplate().queryForList(sqlDataObservedMetricMetricTemplate.toString(),
                    new Object[]{osId});//, Integer.class
            if (!validarOmIdsVsEE(dataOMMT)) {
                //alguno de los observedMetric no tiene evidence evaluation asociados
                continue;
            }
            //limpiamos el sql;
            sqlInsert.setLength(0);
            sqlInsert.append("insert into reporting_data (id, area_id, department, store_id,observed_situation_id, ");
            sqlInsert.append(" evaluation_date, evaluation_start_date, evaluation_end_date,");

            Date evidenceDataMin = getDateOfEvidence(osId);
            Integer areaId = null;
            String department = null;
            Integer storeId = null;

            String product = (String) getJdbcTemplate().queryForObject(sqlProduct.toString(), new Object[]{osId}, String.class);

            List<Map<String, Object>> datosBasicos = getJdbcTemplate().queryForList(sqlDatosBasicos.toString(),
                    new Object[]{osId});
            if (!datosBasicos.isEmpty()) {
                Map db = datosBasicos.get(0);
                areaId = (Integer) db.get("area_id");
                department = (String) db.get("department");
                storeId = (Integer) db.get("store_id");
            }

            Date startDate = null;
            Date endDate = null;
            Boolean reject = Boolean.FALSE;
            List<Map<String, Object>> resultEvidenceDate = getJdbcTemplate().queryForList(
                    sqlEvaluationDate.toString(), new Object[]{osId});
            if (!resultEvidenceDate.isEmpty()) {
                //solo pedimos el primero no deberia haber mas segun sql
                Map m2 = resultEvidenceDate.get(0);
                startDate = (Date) m2.get("min");
                endDate = (Date) m2.get("max");
                reject = (Boolean) m2.get("rejected");
            }
            sqlInsert.append("metric_count").append(",");
            for (int pos = 1; pos <= dataOMMT.size(); pos++) {
                sqlInsert.append("observed_metric_id").append(",");
                sqlInsert.append("metric_template_id").append(pos).append(",");
                sqlInsert.append("metric_val").append(",");

            }
            sqlInsert.append("sent_tomis, reject, cant_do, cant_do_reason, evaluation_user, target, product) values (");

            sqlInsert.append(osId).append(",");
            sqlInsert.append(areaId).append(",");
            sqlInsert.append("'").append(department).append("',");
            sqlInsert.append(storeId).append(",");
            sqlInsert.append(osId).append(",");
            sqlInsert.append("?,"); //osDate
            sqlInsert.append("?,"); //startDate
            sqlInsert.append("?,"); //endDate
            sqlInsert.append(dataOMMT.size()).append(","); //metric_count
            int metric1 = 0;
            for (Map<String, Object> data : dataOMMT) {
                Integer omId = (Integer) data.get("omId");
                sqlInsert.append(omId).append(",");
                Double metricValue = null;
                try {
                    metricValue = (Double) getJdbcTemplate().queryForObject(sqlMetricValue.toString(),
                            new Object[]{omId}, Double.class);
                } catch (EmptyResultDataAccessException e) {
                    //no hay datos
                    log.warn("no existe metricValue en db para [osId:" + osId + "][omId:" + omId + "] " + e);
                }
                if (metric1 == 0 && metricValue == null) {
                    metric1++;
                }
                sqlInsert.append(metricValue).append(",");
            }
            sqlInsert.append("false,").append(reject).append(","); //sent_tomis, reject

            String ids = StringUtils.join(dataOMMT.toArray(), ",");
            String sqlCantDo = getSqlBaseCantDo();
            sqlCantDo = StringUtils.replace(sqlCantDo, "{ids}", ids);
            List<Map<String, Object>> cantDoReasons = getJdbcTemplate().queryForList(sqlCantDo);
            if (!cantDoReasons.isEmpty()) {
                String user = "";
                String cantDoReason = "";
                Boolean bCantDo = Boolean.FALSE;
                for (Map cantDo : cantDoReasons) {
                    user = (String) cantDo.get("evaluation_user");
                    cantDoReason = (String) cantDo.get("cant_do_reason");
                    if (cantDoReason != null) {
                        bCantDo = Boolean.TRUE;
                        break;
                    }
                }
                sqlInsert.append(bCantDo.toString()).append(","); //cant_do
                if (cantDoReason != null) { //para no colocar null en el campo en base de datos
                    sqlInsert.append("'").append(cantDoReason).append("',"); //cant_do_reason
                } else {
                    sqlInsert.append("").append(cantDoReason).append(","); //cant_do_reason
                }
                sqlInsert.append("'").append(user).append("'"); //evaluation_user
            } else {
                sqlInsert.append("false,null, null");
            }
            Double target = getTargetFromObservedSituationEvaluation(osId);
            sqlInsert.append(", ").append(target).append(","); //agregamos el target
            sqlInsert.append("'").append(product).append("')");

            try {
                getJdbcTemplate().update(sqlInsert.toString(), new Object[]{evidenceDataMin, startDate, endDate});
            } catch (DataAccessException e) {
                //se asume que ya existe
                log.warn("verificar condicion para osId" + osId + " e:" + e);
            }
        }
        log.debug("end");
    }

    private StringBuilder getSQLProduct() {
        log.debug("start");
        StringBuilder sqlProduct = new StringBuilder();
        sqlProduct.append("select p.description ");
        sqlProduct.append(" from product p, ");
        sqlProduct.append(" situation_template st, situation s, ");
        sqlProduct.append(" observed_situation os ");
        sqlProduct.append(" where os.id = ? ");
        sqlProduct.append(" and s.id = os.situation_id ");
        sqlProduct.append(" and st.id = s.situation_template_id");
        sqlProduct.append(" and p.id = st.product_id");
        log.debug("end");
        return sqlProduct;
    }

    private StringBuilder getSQLDatosBasicos() {
        log.debug("start");
        StringBuilder sqlDatosBasicos = new StringBuilder();
        sqlDatosBasicos.append("select distinct(m.store_id), a.id as area_id, a.description as department ");
        sqlDatosBasicos.append(" from observed_metric om, metric m, place p, area_type a");
        sqlDatosBasicos.append(" where om.observed_situation_id = ? ");
        sqlDatosBasicos.append(" and m.id = om.metric_id ");
        sqlDatosBasicos.append(" and p.id = m.area_id ");
        sqlDatosBasicos.append(" and a.id = p.area_type_id");
        log.debug("end");
        return sqlDatosBasicos;
    }

    private StringBuilder getSQLMetricValue() {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select me.metric_evaluation_result ");
        sql.append(" from metric_evaluation me ");
        sql.append(" where me.observed_metric_id =?");
        log.debug("end");
        return sql;
    }

    private StringBuilder getSQLDataObservedMetricMetricTemplate() {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select om.id as omId ,mt.id as mtId, mt.description");
        sql.append(" from observed_metric om, metric m, metric_template mt ");
        sql.append(" where ");
        sql.append(" mt.id = m.metric_template_id ");
        sql.append(" and om.metric_id = m.id ");
        sql.append(" and  om.observed_situation_id = ? ");
        sql.append(" order by om.observed_situation_id, mt.id");
        log.debug("end");
        return sql;
    }

    private StringBuilder getSQLDataEvidenceEvaluationByOmId() {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select min(ee.init_evaluation), max(ee.end_evaluation), ee.rejected, evaluation_user ");
        sql.append(" from evidence_evaluation ee ");
        sql.append(" where ");
        sql.append(" ee.init_evaluation is not null ");
        sql.append(" and ee.observed_metric_id = ? ");
        sql.append(" group by ee.rejected, ee.evaluation_user ");
        log.info("end");
        return sql;
    }

    private StringBuilder getSQLEvaluationDate() {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("select min(ee.init_evaluation) , max(ee.end_evaluation), ee.rejected ");
        sql.append("    from evidence_evaluation ee");
        sql.append("    where ");
        sql.append("        ee.init_evaluation is not null and");
        sql.append("        ee.observed_metric_id in(select om.id from observed_metric om ");
        sql.append("                             where om.observed_situation_id = ?) ");
        sql.append(" group by rejected");
        log.debug("end");
        return sql;
    }

    private StringBuilder getSQLBase() {
        log.debug("start");
        StringBuilder sql1 = new StringBuilder();
        sql1.append("select ");
        sql1.append(" os.id "); //os.observed_situation_date ya no se ocupa
        sql1.append(" from observed_situation os");
        sql1.append(" where (os.evaluation_state ='FINISHED' or os.evaluation_state ='CHECKING') and ");
        sql1.append(" os.observed_situation_date >= '2011-02-21'");
        //comentar en produccion
        //sql1.append(" os.observed_situation_date >= '2008-01-01'");
        sql1.append("  order by os.id  ");
        //comentar en produccion
        sql1.append(" limit 5000");
        //and os.observed_situation_date <= '2010-03-03'
        log.debug("end");
        return sql1;
    }

    private boolean verificaExisteDato(Integer osId, Integer omId, String evaluationUser, Boolean reject, Boolean cantDo) {
        log.debug("start");
        boolean ret = false;
        try {
            Integer count = getJdbcTemplate().queryForInt(getSqlValudacionOsIdReporting(), new Object[]{cantDo, reject,
                evaluationUser, omId, osId});
            ret = count > 0;
        } catch (EmptyResultDataAccessException e) {
            log.info("No existe data para osId " + osId);
        }
        log.debug("end");
        return ret;
    }

    @Override
    public Double getTargetFromObservedSituationEvaluation(Integer osId) {
        log.debug("start");
        Double ret = new Double(0);
        try {
            ret = (Double) getJdbcTemplate().queryForObject(getSqlTargetByObservedSituationId(), new Object[]{osId},
                    Double.class);
        } catch (EmptyResultDataAccessException e) {
            //si no exite valor colocamos 0
            log.warn("no existe target para osId " + osId + " " + e);
            ret = new Double(0);
        }
        log.debug("end");
        return ret;
    }

    private boolean validarOmIdsVsEE(List<Map<String, Object>> mapData) {
        boolean ret = true;
        for (Map data : mapData) {
            Integer omId = (Integer) data.get("omId");
            Integer count = getJdbcTemplate().queryForInt(getSqlValidaOmIdEE(), new Object[]{omId});
            if (count.equals(Integer.valueOf(0))) {
                ret = false;
                break;
            }
        }
        log.debug(ret);
        return ret;
    }

    private String getSqlValidaOmIdEE() {
        if (sqlValidarOmIdEE == null) {
            sqlValidarOmIdEE = "select count(1) from evidence_evaluation  ee where ee.observed_metric_id =?";
        }
        return sqlValidarOmIdEE;
    }

    private String getSqlTargetByObservedSituationId() {
        if (sqlTargetByObservedSituationId == null) {
            sqlTargetByObservedSituationId = "select target from observed_situation_evaluation  ose "
                    + " where "
                    + " ose.id = (select max(id) from observed_situation_evaluation  where observed_situation_id =  ?)";
        }
        return sqlTargetByObservedSituationId;
    }

    private String getSqlValudacionOsIdReporting() {
        if (sqlValudacionOsIdReporting == null) {
            sqlValudacionOsIdReporting = "select count(1) from reporting_data where "
                    + " cant_do  = ? "
                    + " and reject  = ? "
                    + " and evaluation_user  = ? "
                    + " and observed_metric_id  = ? "
                    + " and observed_situation_id  = ?";
        }
        return sqlValudacionOsIdReporting;
    }

    private String getSqlCantDoReason() {
        if (sqlCantDoReason == null) {
            sqlCantDoReason = "select distinct ee.cant_do_reason "
                    + "  from evidence_evaluation ee "
                    + " where "
                    + " ee.observed_metric_id = ? and ee.rejected = ? and ee.evaluation_user=? ";
        }
        return sqlCantDoReason;
    }

    private String getSqlBaseCantDo() {
        if (sqlBaseCantDo == null) {
            sqlBaseCantDo = "select ee.cant_do_reason, ee.evaluation_user "
                    + " from evidence_evaluation ee "
                    + " where "
                    + " ee.observed_metric_id in({ids})";
        }
        return sqlBaseCantDo;
    }

    /**
     * Retorna un Reporting Data que no halla sido rechazado, dato un
     * ObservedSituationId
     */
    @Override
    public ReportingData getReportingDataByObservedSituation(Integer osId) {
        log.debug("start");
        ReportingData data = null;
        Session session = this.getSession();
        Criteria criteria = session.createCriteria(
                ReportingData.class);
        criteria.add(Restrictions.eq("reject", false));
        criteria.add(Restrictions.eq("observedSituationId", osId));
        data = (ReportingData) criteria.uniqueResult();
        log.debug("end");
        return data;
    }

    @Override
    public List<ReportingData> getReportingDataListByObservedSituation(Integer osId) {
        log.info("start");
        List<ReportingData> reportingDatas = null;
        StringBuilder sql = new StringBuilder("select * from reporting_data where ");
        sql.append("reject = false");
        sql.append(" and observed_situation_id = ? ");
        sql.append(" order by metric_template_id");
        reportingDatas = getJdbcTemplate().query(sql.toString(), new Object[]{osId}, new ReportingDataMapper());
        log.info("end");
        return reportingDatas;
    }

    private StringBuilder getSqlInsert() {
        StringBuilder sql = new StringBuilder();
        //id es un nextval
        sql.append("insert into reporting_data (date_record, area_id, department, store_id,observed_situation_id, ");
        sql.append(" evidence_date, evaluation_start_date, evaluation_end_date, metric_count,");
        sql.append(" observed_metric_id, metric_template_id, metric_val, ");
        sql.append(" sent_tomis, reject, cant_do, cant_do_reason, evaluation_user, target, product) values (now(), ");
        return sql;
    }

    private void addParameter(Object o, StringBuilder sql, String separator) {
        if (o != null) {
            if (o instanceof Date) {
                sql.append("'").append(DateFormatUtils.format((Date) o, "yyyy-MM-dd HH:mm:ss")).append("'").append(separator);
            } else if (o instanceof String) {
                sql.append("'").append(StringEscapeUtils.escapeSql((String) o)).append("'").append(separator);
            } else if (o instanceof Boolean) {
                sql.append((Boolean) o).append(separator);
            } else if (o instanceof Double) {
                sql.append("'").append((Double) o).append("'").append(separator);
            }
        } else {
            sql.append("null").append(separator);
        }
    }

    @Override
    public void addReportingData(ReportingData data) {
        if (data.getId() == null) {
            insertReportingData(data);
        } else {
            updateReportingData(data);
        }
    }

    /**
     * area_id, cant_do, cant_do_reason, date_record, department,
     * evaluation_start_date, evaluation_end_date, evaluation_user,
     * evidence_date, metric_template_id, metric_val, metric_count,
     * observed_situation_id, product, reject, rejected_date, rejected_user,
     * rule_name, sent_tomis, sent_tomisdate, state, store_id, target,
     * upload_process_id, observed_metric_id
     */
    private void insertReportingData(ReportingData data) {
        String sqlInsert = getSqlInsertReportingData();

        getJdbcTemplate().update(sqlInsert,
                new Object[]{data.getAreaId(), data.isCantDo(), data.getCantDoReason(),
            data.getDateRecord(), data.getDepartment(), data.getEvaluationStartDate(), data.getEvaluationEndDate(),
            data.getEvaluationUser(), data.getEvidenceDate(), data.getMetricTemplateId(), data.getMetricVal(),
            data.getMetricCount(), data.getObservedSituationId(), data.getProduct(), data.isReject(),
            data.getRejectedDate(), data.getRejectedUser(), null, data.isSentToMIS(), data.getSentToMISDate(),
            data.getState(), data.getStoreId(), data.getTarget(), data.getUploadProcessId(), data.getObservedMetricId(),
            data.getSituationTemplateId()});
    }

    private void updateReportingData(ReportingData data) {
        String sqlUpdate = getSqlUpdateReportingData();
        getJdbcTemplate().update(sqlUpdate,
                new Object[]{data.getAreaId(), data.isCantDo(), data.getCantDoReason(),
            data.getDateRecord(), data.getDepartment(), data.getEvaluationStartDate(), data.getEvaluationEndDate(),
            data.getEvaluationUser(), data.getEvidenceDate(), data.getMetricTemplateId(), data.getMetricVal(),
            data.getMetricCount(), data.getObservedSituationId(), data.getProduct(), data.isReject(),
            data.getRejectedDate(), data.getRejectedUser(), null, data.isSentToMIS(), data.getSentToMISDate(),
            data.getState(), data.getStoreId(), data.getTarget(), data.getUploadProcessId(), data.getObservedMetricId(),
            data.getSituationTemplateId(),
            data.getId()});
    }

    private String getSqlInsertReportingData() {
        if (sqlInsertReportingData == null) {
            sqlInsertReportingData = "insert into reporting_data (area_id, cant_do, cant_do_reason, date_record, department, "
                    + "evaluation_start_date, evaluation_end_date, evaluation_user, evidence_date, metric_template_id, "
                    + "metric_val, metric_count, observed_situation_id, product, reject, rejected_date, rejected_user, "
                    + "rule_name, sent_tomis, sent_tomisdate, state, store_id, target, upload_process_id, observed_metric_id, "
                    + " situation_template_id ) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        }
        return sqlInsertReportingData;
    }

    private String getSqlUpdateReportingData() {
        if (sqlUpdateReportingData == null) {
            sqlUpdateReportingData = "update reporting_data "
                    + "set area_id = ?, cant_do = ?, cant_do_reason = ?, date_record = ?, department = ?, "
                    + "evaluation_start_date = ?, evaluation_end_date = ?, evaluation_user = ?, evidence_date = ?, "
                    + "metric_template_id = ?, metric_val = ?, metric_count = ?, observed_situation_id = ?, product = ?, "
                    + "reject = ?, rejected_date = ?, rejected_user = ?, rule_name = ?, sent_tomis = ?, sent_tomisdate = ?, "
                    + "state = ?, store_id = ?, target = ?, upload_process_id = ?, observed_metric_id = ?, "
                    + "situation_template_id = ? "
                    + "where id = ?";
        }
        return sqlUpdateReportingData;
    }

    @Override
    public void generateReportingDataFromDataOld() throws ScopixException {
        log.info("start");
        String sqlCount = "select count(1) from reporting_data_old";

        String sqlInsertByReportingData = "insert into reporting_data "
                + "(id,area_id, cant_do, cant_do_reason, date_record, department, "
                + "evaluation_start_date, evaluation_end_date, evaluation_user, evidence_date, metric_template_id, "
                + "metric_val, metric_count, observed_situation_id, product, reject, rejected_date, rejected_user, "
                + "rule_name, sent_tomis, sent_tomisdate, state, store_id, target, upload_process_id, observed_metric_id) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String sqlDataEvaluation = "select init_evaluation, end_evaluation from evidence_evaluation "
                + " where observed_metric_id = ? "
                + " and init_evaluation is not null "
                + " and end_evaluation is not null order by id";
        String sqlMetricTemplateId = "select m.metric_template_id from  metric m, observed_metric om "
                + " where   "
                + " om.id = ? "
                + " and m.id = om.metric_id";
        Connection con = null;

        try {
            con = getJdbcTemplate().getDataSource().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stmt1InsertReportingID = con.prepareStatement(sqlInsertByReportingData);
            PreparedStatement stmt2InsertReporting = con.prepareStatement(getSqlInsertReportingData());
            PreparedStatement stmt3SelectDataEvaluation = con.prepareStatement(sqlDataEvaluation);
            PreparedStatement stmt4SelectMetricTemplateId = con.prepareStatement(sqlMetricTemplateId);
            Statement statementCount = con.createStatement();
            ResultSet rsCount = statementCount.executeQuery(sqlCount);
            rsCount.next();
            int countData = rsCount.getInt(1);
            //comentar
            //countData = 250000;
            int ciclos = (int) countData / 10000;
            double mod = countData % 10000;
            if (mod > 0) {
                ciclos = ciclos + 1;
            }
            int count = 0;
            log.debug("inicio proceso [ciclos:" + ciclos + "]");
            Statement statement = con.createStatement();
            ResultSet rs = null;
            for (int i = 0; i < ciclos; i++) {
                log.debug("ciclos " + (i + 1) + " de " + ciclos);
                int offset = i * 10000;
                rs = statement.executeQuery("select * from reporting_data_old order by id limit 10000 offset " + offset);
                log.debug("recorriendo data [offset:" + offset + "]");

                while (rs.next()) {
                    Integer id = (Integer) rs.getObject("id");
                    Integer areaId = (Integer) rs.getObject("area_id");
                    Integer metricCount = (Integer) rs.getObject("metric_count");
                    Integer storeId = (Integer) rs.getObject("store_id");
                    Integer osId = (Integer) rs.getObject("observed_situation_id");
                    Integer uploadProcessId = (Integer) rs.getObject("upload_process_id");

                    String department = rs.getString("department");
                    String product = rs.getString("product");
                    String ruleName = rs.getString("rule_name");
                    String state = rs.getString("state");
                    String cantDoReason = rs.getString("cant_do_reason");
                    String evaluationUser = rs.getString("evaluation_user");
                    String rejectedUser = rs.getString("rejected_user");
                    Double target = rs.getDouble("target");
                    Boolean sentTomis = rs.getBoolean("sent_tomis");
                    Boolean reject = rs.getBoolean("reject");
                    Boolean cantDo = rs.getBoolean("cant_do");
                    Date evidenceDate = getDate(rs, "evaluation_date");
                    Date sentTomisDate = getDate(rs, "sent_tomisdate");
                    Date dateRecord = getDate(rs, "date_record");
                    Date rejectedDate = getDate(rs, "rejected_date");

                    Integer metricTemplateId = null;

                    for (int j = 1; j <= metricCount; j++) {
                        Double metricVal = rs.getDouble("metric" + j);
                        Integer omId = (Integer) rs.getObject("metric_id" + j);
                        metricTemplateId = getMetricTemplateId(stmt4SelectMetricTemplateId, omId);
                        //recuperar las fehcas ini y end
                        Date evaluationStartDate = null;
                        Date evaluationEndDate = null;
                        stmt3SelectDataEvaluation.setInt(1, omId);
                        ResultSet rsEvaluationDates = stmt3SelectDataEvaluation.executeQuery();
                        while (rsEvaluationDates.next()) {
                            evaluationStartDate = getDate(rsEvaluationDates, "init_evaluation");
                            evaluationEndDate = getDate(rsEvaluationDates, "end_evaluation");
                        }
                        rsEvaluationDates.close();
                        if (j == 1) {
                            Object[] param = new Object[]{id, areaId, cantDo, cantDoReason, dateRecord, department,
                                evaluationStartDate, evaluationEndDate, evaluationUser, evidenceDate, metricTemplateId, metricVal,
                                metricCount, osId, product, reject, rejectedDate, rejectedUser, ruleName, sentTomis,
                                sentTomisDate, state, storeId, target, uploadProcessId, omId};
                            int paramSet = 1;
                            for (Object o : param) {
                                if (o instanceof Date) {
                                    if (o != null) {
                                        stmt1InsertReportingID.setTimestamp(paramSet++,
                                                new java.sql.Timestamp(((Date) o).getTime()));
                                    } else {
                                        stmt1InsertReportingID.setTimestamp(paramSet++, null);
                                    }
                                } else if (o instanceof String) {
                                    stmt1InsertReportingID.setString(paramSet++, (String) o);
                                } else {
                                    stmt1InsertReportingID.setObject(paramSet++, o);
                                }
                            }
                            stmt1InsertReportingID.executeUpdate();
                            count++;
                        } else {
                            Object[] param = new Object[]{areaId, cantDo, cantDoReason, dateRecord, department,
                                evaluationStartDate, evaluationEndDate, evaluationUser, evidenceDate, metricTemplateId, metricVal,
                                metricCount, osId, product, reject, rejectedDate, rejectedUser, ruleName, sentTomis,
                                sentTomisDate, state, storeId, target, uploadProcessId, omId};
                            int paramSet = 1;
                            for (Object o : param) {
                                if (o instanceof Date) {
                                    if (o != null) {
                                        stmt2InsertReporting.setTimestamp(paramSet++,
                                                new java.sql.Timestamp(((Date) o).getTime()));
                                    } else {
                                        stmt2InsertReporting.setTimestamp(paramSet++, null);
                                    }
                                } else if (o instanceof String) {
                                    stmt2InsertReporting.setString(paramSet++, (String) o);
                                } else {
                                    stmt2InsertReporting.setObject(paramSet++, o);
                                }
                            }
                            stmt2InsertReporting.executeUpdate();
                            count++;
                        }
                    }
                    if (count > 1000) {
                        log.debug("commit 1000 registros");
                        con.commit();
                        count = 0;
                    }
                }
                rs.close();
            }
            log.debug("before comit final");
            con.commit();
            log.debug("before close stmt2InsertReporting");
            stmt2InsertReporting.close();
            log.debug("before close stmt1InsertReportingID");
            stmt1InsertReportingID.close();
            log.debug("before close stmt3SelectDataEvaluation");
            stmt3SelectDataEvaluation.close();
            log.debug("before close statementCount");
            statementCount.close();
            log.debug("before close statement");
            statement.close();
            log.debug("before close stmt4SelectMetricTemplateId");
            stmt4SelectMetricTemplateId.close();
        } catch (SQLException e) {
            log.error(e, e);
            try {
                con.rollback();
            } catch (SQLException e2) {
                log.error("no es posible ejecutar rollback " + e, e);
            }
            log.error("Error ejecutando " + e, e);
        } finally {
            if (con != null) {
                try {
                    log.debug("before conection close()");
                    con.close();
                } catch (SQLException e) {
                    log.error("no es posible cerrar la conexion " + e, e);
                }
            }
        }
        log.info("end");
    }

    private Date getDate(ResultSet rs, String columName) {
        Date date = null;
        try {
            if (rs.getTimestamp(columName) != null) {
                date = new Date(rs.getTimestamp(columName).getTime());
            }
        } catch (SQLException e) {
            log.warn("no es posible recuperar data para columna " + columName);
        }
        return date;
    }

    private Integer getMetricTemplateId(PreparedStatement stmt4SelectMetricTemplateId, Integer observedMetricId) {
        Integer metricTempateId = null;
        try {
            stmt4SelectMetricTemplateId.setInt(1, observedMetricId);
            ResultSet rs = stmt4SelectMetricTemplateId.executeQuery();
            rs.next();
            metricTempateId = (Integer) rs.getObject(1);
            rs.close();
        } catch (SQLException e) {
            log.error("No se puede recuperar metricTempateId para observedMetricId:" + observedMetricId);
        }
        return metricTempateId;
    }

    @Override
    public Map<String, Map<String, Double>> getLastEvaluations(List<Integer> situationTemplatesIds,
            Integer storeId, Integer cantGrupos) {
        Map<String, Map<String, Double>> ret = new HashMap<String, Map<String, Double>>();
        StringBuilder sql = new StringBuilder();

        sql.append("select rd.evidence_date, rd.metric_template_id, rd.metric_val, st.id as st_id  ");
        sql.append(" from ");
        sql.append(" situation_template  st, situation s, observed_situation os, reporting_data rd");
        sql.append(" where st.active = true ");
        sql.append(" and st.id in (").append(StringUtils.join(situationTemplatesIds, ",")).append(") ");
        sql.append(" and s.situation_template_id = st.id ");
        sql.append(" and os.situation_id = s.id ");
        sql.append(" and rd.observed_situation_id = os.id ");
        //sql.append(" and rd.evidence_date between ? and ? ");
        sql.append(" and rd.reject = false ");
        sql.append(" and rd.store_id = ?");
        sql.append(" order by 1 desc limit 500"); //con esto recuperamos los primeros 50 resultados
        List<Map<String, Object>> resultSql = getJdbcTemplate().queryForList(sql.toString(), new Object[]{storeId}); //start, end,
        int count = cantGrupos;
        for (Map<String, Object> row : resultSql) {
            Integer stId = (Integer) row.get("st_id");
            Date evidenceDate = (Date) row.get("evidence_date");
            String keyEvidenceDate = DateFormatUtils.format(evidenceDate, "yyyyMMddHHmm");
            Integer metricTemplateId = (Integer) row.get("metric_template_id");
            Double metricValue = (Double) row.get("metric_val");
            if (!ret.containsKey(keyEvidenceDate) && ret.size() < count) {
                Map<String, Double> values = new HashMap<String, Double>();
                values.put(stId + "." + metricTemplateId, metricValue);
                ret.put(keyEvidenceDate, values);
            } else if (ret.size() <= count) {
                Map<String, Double> values = ret.get(keyEvidenceDate);
                if (values != null) {
                    values.put(stId + "." + metricTemplateId, metricValue);
                }
            }
        }
        return ret;
    }

    @Override
    public Integer getCantNewDataNoSending() throws ScopixException {
        Integer count = null;
        try {
            StringBuilder sql = new StringBuilder("select count(1) from reporting_data  where sent_tomis  = false");
            count = getJdbcTemplate().queryForInt(sql.toString());
        } catch (DataAccessException e) {
            log.error("Error recuperando cantidad nuevos datos " + e, e);
            throw new ScopixException(e);
        }
        return count;
    }

    @Override
    public Map<String, Map<String, Double>> getAllLastEvaluations(List<Integer> situationTemplatesIds, List<Integer> storeIds) {
        log.info("start [situationTemplatesIds:" + situationTemplatesIds + "][storeIds:" + storeIds + "]");

        Map<String, Map<String, Double>> ret = new HashMap<String, Map<String, Double>>();
        StringBuilder sql = new StringBuilder();

        sql.append("select rd.evidence_date, rd.metric_template_id, rd.metric_val, st.id as st_id, rd.store_id as store_id, p.id as area_id");
        sql.append(" from ");
        sql.append(" situation_template  st, situation s, observed_situation os, reporting_data rd, place p");
        sql.append(" where st.active = true ");
        sql.append(" and st.id in (").append(StringUtils.join(situationTemplatesIds, ",")).append(") ");
        sql.append(" and s.situation_template_id = st.id ");
        sql.append(" and os.situation_id = s.id ");
        sql.append(" and rd.observed_situation_id = os.id ");
        //sql.append(" and rd.evidence_date between ? and ? ");
        sql.append(" and rd.reject = false ");
        sql.append(" and p.area_type_id = rd.area_id and p.store_id =rd.store_id ");
        sql.append(" and rd.store_id in(").append(StringUtils.join(storeIds, ",")).append(") ");
        sql.append(" order by 1 desc limit 500"); //con esto recuperamos los primeros 50 resultados
        List<Map<String, Object>> resultSql = getJdbcTemplate().queryForList(sql.toString()); //new Object[]{storeId},start, end,

        for (Map<String, Object> row : resultSql) {
            Integer stId = (Integer) row.get("st_id");
            Date evidenceDate = (Date) row.get("evidence_date");
            Integer storeId = (Integer) row.get("store_id");
            Integer areaId = (Integer) row.get("area_id");
            String keyEvidenceDate = DateFormatUtils.format(evidenceDate, "yyyyMMddHHmm") + "." + storeId + "." + areaId;
            Integer metricTemplateId = (Integer) row.get("metric_template_id");
            Double metricValue = (Double) row.get("metric_val");

            if (!ret.containsKey(keyEvidenceDate)) {
                Map<String, Double> values = new HashMap<String, Double>();
                //values.put(stId + "." + metricTemplateId+ "."  + areaId, metricValue);
                values.put(stId + "." + metricTemplateId, metricValue);
                ret.put(keyEvidenceDate, values);
            } else {
                Map<String, Double> values = ret.get(keyEvidenceDate);
                if (values != null) {
                    values.put(stId + "." + metricTemplateId, metricValue);
                }
            }
        }
        log.info("end");
        return ret;
    }
}
