/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
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

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author César Abarza Suazo.
 */
@SpringBean(rootClass = TransferBWHibernateDAO.class) //, initMethod = "init")
public class TransferBWHibernateDAO { //extends DAOHibernateBW<BusinessObject, Integer> {

    private Logger log = Logger.getLogger(TransferBWHibernateDAO.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSourceBW(DataSource ds) {
        setJdbcTemplate(new JdbcTemplate(ds));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
    public void executeSQLFacts(String sql) {
        log.info("init");
        if (sql.length() > 0) {
            getJdbcTemplate().update(sql);
        } else {
            log.info("nada que ejecutar");
        }
        log.info("end");
    }

    /**
     * Get stores id from business warehouse services data base
     *
     * @return
     * @throws java.sql.SQLException
     */
    public List<Integer> getStoresIdFromBW() {
        log.debug("init");
        List<Integer> storesIds = new ArrayList<Integer>();
        storesIds = getJdbcTemplate().queryForList("SELECT id FROM store_hierarchy", Integer.class);
        log.debug("end");
        return storesIds;
    }

    /**
     * Get the product_hierarchy id from business warehouse for teh combination area_id and product
     *
     * @param areaId
     * @param productName
     * @return
     * @throws java.sql.SQLException
     */
    public Integer getProductId(Integer areaId, String productName) {
        log.info("init");
        Integer productId = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT id FROM product_hierarchy WHERE area_id = ").append(areaId);
            sql.append(" AND product = '").append(StringEscapeUtils.escapeSql(productName)).append("'");

            productId = getJdbcTemplate().queryForInt(sql.toString());
        } catch (EmptyResultDataAccessException e) {
            productId = null;
            log.warn("no exites id para " + areaId + ":" + productName);
        }
        log.info("end");
        return productId;
    }

    /**
     * This method return the metric name id from business warehouse services
     *
     * @param names
     * @param productId
     * @return
     * @throws java.sql.SQLException
     */
    public Integer getMetricNameIdFromBW(List<String> names, Integer productId) {
        log.debug("init");
        Integer metricNameId = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM metrics_names WHERE product_id=").append(productId);
        for (int i = 0; i < names.size(); i++) {
            String metricName = names.get(i);
            sql.append(" AND metric").append(i + 1).append("_name = '").
                    append(StringEscapeUtils.escapeSql(metricName)).append("'");
        }
        sql.append(" AND metric_count = ").append(names.size());
        try {
            metricNameId = getJdbcTemplate().queryForInt(sql.toString());
        } catch (EmptyResultDataAccessException e) {
            log.warn("no existe metric id para " + productId + ":" + StringUtils.join(names.toArray(), ","));
        }
        log.debug("end");
        return metricNameId;
    }

    public Integer getIndicatorNameId(String indicatorName, Integer productId) {
        log.debug("init");
        Integer id = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT i.id FROM indicator_names i");
        sql.append(" WHERE i.name='");
        sql.append(StringEscapeUtils.escapeSql(indicatorName));
        sql.append("'");
        sql.append(" AND i.product_id = ");
        sql.append(productId);

        Integer result = null;
        try {
            result = getJdbcTemplate().queryForInt(sql.toString());
        } catch (EmptyResultDataAccessException e) {
            log.warn("no existe indicator_name " + indicatorName + ":" + productId);
        }
        id = result;
        log.debug("end");
        return id;
    }

    public void deleteComplianceFacts(List<Integer> ids) throws ScopixException {
        log.debug("start");
        try {
            if (ids.size() > 500) {
                StringBuilder sql = new StringBuilder();
                int count = 0;
                for (Integer id : ids) {
                    sql.append("DELETE FROM compliance_facts WHERE id = ").append(id).append(";");
                    count++;
                    if (count > 500) {
                        count = 0;
                        getJdbcTemplate().update(sql.toString());
                        sql.setLength(0);
                    }
                }
                getJdbcTemplate().update(sql.toString());

            } else {
                String id = StringUtils.join(ids.toArray(), ",");
                deleteComplianceFacts(id);
            }
        } catch (DataAccessException e) {
            log.error("Error eliminando compliance facts", e);
            throw new ScopixException(e);
        }
        log.debug("end");
    }

    public void deleteComplianceFacts(String oseIds) {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM compliance_facts WHERE id in (");
        sql.append(oseIds.trim()); //ids.toString().replaceAll("(\\[)|(\\])", "")
        sql.append(")");

        getJdbcTemplate().update(sql.toString());
        log.debug("end");
    }

    public void deleteIndicatorFacts(List<Integer> ids) {
        log.debug("start");
        if (ids.size() > 500) {
            StringBuilder sql = new StringBuilder();
            int count = 0;
            for (Integer id : ids) {
                sql.append("DELETE FROM indicator_facts WHERE id = ").append(id).append(";");
                count++;
                if (count > 500) {
                    count = 0;
                    getJdbcTemplate().update(sql.toString());
                    sql.setLength(0);
                }
            }
            getJdbcTemplate().update(sql.toString());

        } else {
            String id = StringUtils.join(ids.toArray(), ",");
            deleteIndicatorFacts(id);
        }
        log.debug("end");
    }

    public void deleteIndicatorFacts(String indicatorValues) {
        log.debug("start");
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM indicator_facts WHERE id in (");
        sql.append(indicatorValues.trim()); //ids.toString().replaceAll("(\\[)|(\\])", "")
        sql.append(")");

        getJdbcTemplate().update(sql.toString());
        log.debug("end");
    }

    public Date getLastDate() {
        Date date = null;
        String sql = "select max(day) from  date_hierarchy";
        date = (Date) getJdbcTemplate().queryForObject(sql, Date.class);
        log.debug("end");
        return date;
    }

    public void validateConnection(boolean retry) throws ScopixException {
        int count = 1;
        String sql = " select now()";
        while (true) {
            try {
                Date now = (Date) getJdbcTemplate().queryForObject(sql, Date.class);
                //si pasa el sql hay conexion
                break;
            } catch (DataAccessResourceFailureException e) {
                if (retry) {
                    if (count == 3) {
                        throw new ScopixException("No se puedo conectar a servidor de datos BW");
                    }
                    try {
                        Thread.currentThread().sleep(10000);
                    } catch (InterruptedException e2) {
                        log.warn("no se pudo hacer espera");
                    }

                    count++;
                } else {
                    throw new ScopixException("No se puedo conectar a servidor de datos BW", e);
                }
            }
        }

    }

    public List<Integer> getEvidenceProviderList() {
        List<Integer> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM evidence_provider");
        try {
            list = getJdbcTemplate().queryForList(sql.toString(), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("no hay providers en BW");
        }

        return list;
    }

    public List<Integer> getProofList(List<Integer> omIds) {
        List<Integer> list = null;

        StringBuilder sql = new StringBuilder();
        sql.append("select p.id from evidences_and_proofs eap, proof p ");
        sql.append(" where ");
        sql.append(" p.evidences_and_proofs_id = eap.id");
        sql.append(" and observed_metric_id in (");
        sql.append(StringUtils.join(omIds.toArray(), ",")).append(")");

        list = getJdbcTemplate().queryForList(sql.toString(), Integer.class);

        return list;
    }

    public void deleteProofs(List<Integer> eapIdsList) throws ScopixException {
        StringBuilder sql = new StringBuilder();
        try {
            if (eapIdsList != null && !eapIdsList.isEmpty()) {
                sql.append("delete from proof where id in (");
                sql.append(StringUtils.join(eapIdsList.toArray(), ",")).append(")");

                getJdbcTemplate().execute(sql.toString());
            }
        } catch (DataAccessException e) {
            log.error("Error eliminando proofs", e);
            throw new ScopixException(e);
        }
    }

    public void deleteEmptyEvidencesAndProofs() throws ScopixException {
        try {
            getJdbcTemplate().execute(getSqlDeleteEmptyEvidencesAndProofs());
        } catch (DataAccessException dex) {
            throw new ScopixException(dex);
        }
    }

    private String sqlDeleteEmptyEvidencesAndProofs;

    private String getSqlDeleteEmptyEvidencesAndProofs() {
        if (sqlDeleteEmptyEvidencesAndProofs == null) {
            sqlDeleteEmptyEvidencesAndProofs = "delete from evidences_and_proofs where id in ("
                    + " select eap.id from evidences_and_proofs eap left outer join proof p on p.evidences_and_proofs_id = eap.id"
                    + " where p.id is null)";
        }
        return sqlDeleteEmptyEvidencesAndProofs;
    }

}
