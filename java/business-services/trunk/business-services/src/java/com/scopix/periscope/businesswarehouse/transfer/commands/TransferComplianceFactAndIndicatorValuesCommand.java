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
 * AddAreaCommand.java
 *
 * Created on 07-05-2008, 03:36:14 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituationEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferComplianceFactAndIndicatorValuesCommand {

    private static final String START = "start";
    private static final String END = "end";
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;
    private GenericDAO genericDao;
    private Logger log = Logger.getLogger(TransferComplianceFactAndIndicatorValuesCommand.class);
    private Map<String, Integer> products;
    private Map<String, Integer> metricIds;
    private Map<String, Integer> indicatorNameIds;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat sdfHour = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat sdfH = new SimpleDateFormat("HH");
    private String sqlInsertComplianceFacts;

    /**
     * Transferencia de compliance facts a bw hasta la fecha dada.
     *
     * @param endDate
     * @throws PeriscopeException
     */
    public void execute(Date endDate) throws ScopixException {
        try {

            this.prepareDataForProcess();

            //Compliance
            this.transferComplianceFacts(endDate);

            //Indicators
            this.transferIndicatorValues(endDate);

        } catch (JDBCConnectionException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }
    }

    /**
     * Se encarga de transferir los compliance facts a bw
     *
     * @param endDate
     * @throws PeriscopeException
     */
    public void transferComplianceFacts(Date endDate) throws ScopixException {
        log.info(START);
        StringBuilder sqlBW = new StringBuilder();
        StringBuilder sqlBS = new StringBuilder();

        try {
            Date initDate = getDao().getInitialTransferDateForOSE(endDate);

            if (initDate == null) {
                log.info("sin OSE para subir");
                return;
            }

            Calendar calIterator = Calendar.getInstance();
            Calendar calEndDate = Calendar.getInstance();
            calIterator.setTime(initDate);
            calEndDate.setTime(endDate);
            calEndDate.add(Calendar.DATE, 1);
            calEndDate.set(Calendar.HOUR, 0);
            calEndDate.set(Calendar.HOUR_OF_DAY, 0);
            calEndDate.set(Calendar.MINUTE, 0);
            calEndDate.set(Calendar.SECOND, 0);
            calEndDate.set(Calendar.MILLISECOND, 0);

            log.debug("calEndDate: " + calEndDate.toString());
            while (calIterator.before(calEndDate)) {
                log.debug("calIterator: " + calIterator.getTime().toString());

                List<ObservedSituationEvaluation> oses = getDao().getObservedSituationEvaluations(calIterator.getTime());
                log.debug("oses: " + oses.size());
                int count = 0;
                if (oses != null && !oses.isEmpty()) {
                    for (ObservedSituationEvaluation ose : oses) {
                        Integer productId = getProductIdByMap(ose.getAreaId(), ose.getProduct());
                        //Date id && hour_id la menor de las evidencias
                        Date date = getDao().getMinDateOfEvidence(ose.getObservedSituation().getId());
                        String hour = null;
                        Integer dateId = null;
                        Integer hourId = null;
                        if (date != null) {
                            dateId = new Integer(sdf.format(date));
                            hour = sdfHour.format(date);
                            hourId = new Integer(sdfH.format(date)) + 1;
                        } else {
                            log.debug("date null for observed situation id = " + ose.getObservedSituation().getId());
                            continue;
                        }
                        //metric names id
                        List<String> names = new ArrayList<String>();
                        for (int i = 0; i < ose.getMetricCount(); i++) {
                            PropertyDescriptor prop =
                                    new PropertyDescriptor("metricId" + (i + 1), ObservedSituationEvaluation.class);
                            Integer omId = (Integer) prop.getReadMethod().invoke(ose);
                            ObservedMetric om = getGenericDao().get(omId, ObservedMetric.class);
                            log.debug("om: " + om.toString() + ", omId: " + omId);
                            String metricName = om.getMetric().getMetricTemplate().getDescription();
                            names.add(metricName);
                        }
                        Integer metricNameId = getMetricNameIdByMap(names, productId);

                        //String prevSql = "DELETE FROM compliance_facts where id = " + ose.getId();
                        sqlBW.append("DELETE FROM compliance_facts where id = ").append(ose.getId()).append(";");
                        log.debug("eliminando compliance facts id = " + ose.getId());
                        //CHECKSTYLE:OFF
                        //Create Insert Statement

                        sqlBW.append(getSqlInsertComplianceFacts());
                        sqlBW.append(ose.getId()).append(", ");
                        sqlBW.append(ose.getCompliant()).append(", ");
                        sqlBW.append(ose.getTarget()).append(", ");
                        sqlBW.append(dateId).append(", ");
                        sqlBW.append(productId).append(", ");
                        sqlBW.append(ose.getStoreId()).append(", ");
                        for (int i = 1; i <= 50; i++) {
                            sqlBW.append(getValueMetric(ose, i)).append(", ");
                        }
                        for (int i = 1; i <= 50; i++) {
                            sqlBW.append(getValueMetricId(ose, i)).append(", ");
                        }
                        sqlBW.append(metricNameId).append(", ");
                        sqlBW.append("'").append(StringEscapeUtils.escapeSql(ose.getRuleName())).append("', ");
                        sqlBW.append("to_timestamp('").append(hour).append("', 'HH24:MI'), ");
                        sqlBW.append(ose.getMetricCount()).append(", ");
                        sqlBW.append(hourId).append(")");
                        sqlBW.append("; ");
                        //CHECKSTYLE:ON

                        log.debug("agregando compliance fact id = " + ose.getId());
                        ose.setSentToMIS(true);
                        ose.setSentToMISDate(new Date());
                        ose.setState("OK");

                        sqlBS.append(createSQLUpdateOSE(ose));

                        count++;
                        if (count > 500) {
                            log.debug("insertando batch de compliance facts");
                            count = 0;
                            getDaoBW().executeSQLFacts(sqlBW.toString());
                            sqlBW.setLength(0);
                            getDao().executeSQL(sqlBS.toString());
                            sqlBS.setLength(0);
                        }
                    }
                    //ejecutar lo que quede pendiente
                    getDaoBW().executeSQLFacts(sqlBW.toString());
                    sqlBW.setLength(0);
                    getDao().executeSQL(sqlBS.toString());
                    sqlBS.setLength(0);
                }
                calIterator.add(Calendar.DATE, 1);
            }

        } catch (IllegalAccessException ex) {
            log.error("IllegalAccessException", ex);
            throw new ScopixException(ex);
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException", ex);
            throw new ScopixException(ex);
        } catch (InvocationTargetException ex) {
            log.error("InvocationTargetException", ex);
            throw new ScopixException(ex);
        } catch (IntrospectionException ex) {
            log.error("IntrospectionException", ex);
            throw new ScopixException(ex);
        } catch (SQLException ex) {
            log.error("SQLExceptions", ex);
            throw new ScopixException(ex);
        } catch (DataAccessException ex) {
            log.error("DataAccessException", ex);
            throw new ScopixException(ex);
        }
        log.info(END);
    }

    /**
     *
     * @param ose
     * @param id
     * @return
     */
    private Double getValueMetric(ObservedSituationEvaluation ose, int id) {

        Double metricValue = null;
        Class c = ose.getClass();
        Method metric;
        try {
            metric = c.getMethod("getMetric" + id);
            metricValue = (Double) metric.invoke(ose);
        } catch (NoSuchMethodException e) {
            log.warn("NoSuchFieldException " + e);
        } catch (SecurityException e) {
            log.warn("SecurityException " + e);
        } catch (IllegalAccessException e) {
            log.warn("IllegalAccessException " + e);
        } catch (IllegalArgumentException e) {
            log.warn("IllegalArgumentException " + e);
        } catch (InvocationTargetException e) {
            log.warn("InvocationTargetException " + e);
        }
        return metricValue;
    }

    /**
     *
     * @param ose
     * @param id
     * @return
     */
    private Integer getValueMetricId(ObservedSituationEvaluation ose, int id) {

        Integer metricValue = null;
        Class c = ose.getClass();
        Method metric;
        try {
            metric = c.getMethod("getMetricId" + id);
            metricValue = (Integer) metric.invoke(ose);
        } catch (NoSuchMethodException e) {
            log.warn("NoSuchFieldException " + e);
        } catch (SecurityException e) {
            log.warn("SecurityException " + e);
        } catch (IllegalAccessException e) {
            log.warn("IllegalAccessException " + e);
        } catch (IllegalArgumentException e) {
            log.warn("IllegalArgumentException " + e);
        } catch (InvocationTargetException e) {
            log.warn("InvocationTargetException " + e);
        }
        return metricValue;
    }

    /**
     *
     * @param ose
     * @return
     */
    public String createSQLUpdateOSE(ObservedSituationEvaluation ose) {
        StringBuilder sql = new StringBuilder();
        sql.append("update observed_situation_evaluation ");
        sql.append("set sent_tomis = 'true', ");
        sql.append("sent_tomisdate = now(), ");
        sql.append("state = '").append(ose.getState()).append("' ");
        sql.append("where id =").append(ose.getId());
        sql.append(";");

        return sql.toString();
    }

    /**
     * Se hace la transferencia de los valores de indicadores a bw
     *
     * @param endDate
     */
    public void transferIndicatorValues(Date endDate) {
        log.info(START);
        StringBuilder sqlBW = new StringBuilder();
        StringBuilder sqlBS = new StringBuilder();

        int cont = 0;
        Set<Integer> indicatorValues = getDao().getIndicatorValues(endDate);
        for (Integer value : indicatorValues) {
            try {
                Map<String, Object> indicatorDetailMap = getDao().getIndicatorDetail(value);

                String productDescription = (String) indicatorDetailMap.get("product_description");

                Integer areaTypeId = (Integer) indicatorDetailMap.get("area_type_id");

                Integer productId = getProductIdByMap(areaTypeId, productDescription);
                Integer indicatorNameId = getIndicatorNameIdByMap((String) indicatorDetailMap.get("indicator_name"), productId);

                //Date id && hour_id la menor de las evidencias
                Date date = getDao().getMinDateOfEvidence((Integer) indicatorDetailMap.get("observed_situation_id"));
                Integer dateId = null;
                Integer hourId = null;
                if (date != null) {
                    dateId = new Integer(sdf.format(date));
                    hourId = new Integer(sdfH.format(date)) + 1;
                }
                //Create Insert Statement
                sqlBW.append("DELETE from indicator_facts where id = ").append(value).append(";");
                sqlBW.append("INSERT INTO indicator_facts (id, store_id, product_id, date_id, hour_id, indicator_id,");
                sqlBW.append("numerator, denominator) VALUES (");
                sqlBW.append(value).append(", ");
                sqlBW.append((Integer) indicatorDetailMap.get("store_id")).append(", ");
                sqlBW.append(productId).append(", ");
                sqlBW.append(dateId).append(", ");
                sqlBW.append(hourId).append(", ");
                sqlBW.append(indicatorNameId).append(", ");
                sqlBW.append((Double) indicatorDetailMap.get("numerator")).append(", ");
                sqlBW.append((Double) indicatorDetailMap.get("denominator")).append(")").append(";");

                sqlBS.append("update indicator_values set sent_tomis = true,");
                sqlBS.append(" sent_tomisdate = now(),");
                sqlBS.append(" state = 'OK'");
                sqlBS.append(" where id = ").append(value).append(";");
            } catch (SQLException ex) {
                sqlBS.append("update indicator_values set state = ").append(ex.getMessage());
                sqlBS.append(" where id = ").append(value).append(";");
                log.error("SQLException = " + ex.getMessage());
            } finally {
                log.debug("indicator guardado= " + value);
            }

            cont++;
            if (cont > 500) {
                cont = 0;
                try {
                    getDao().executeSQL(sqlBS.toString());
                    sqlBS.setLength(0);
                    getDaoBW().executeSQLFacts(sqlBW.toString());
                    sqlBW.setLength(0);
                } catch (SQLException ex) {
                    log.error("SQLException = " + ex.getMessage());
                }
            }

        } //end for each
        // ejecutar los sqlBW restantes al salir del for
        try {
            getDao().executeSQL(sqlBS.toString());
            sqlBS.setLength(0);
            getDaoBW().executeSQLFacts(sqlBW.toString());
            sqlBW.setLength(0);
        } catch (SQLException ex) {
            log.error("SQLException = " + ex.getMessage());
        }

        log.info(END);
    }

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }

    public TransferBWHibernateDAO getDaoBW() {
        if (daoBW == null) {
            daoBW = SpringSupport.getInstance().findBeanByClassName(TransferBWHibernateDAO.class);
        }
        return daoBW;
    }

    public void setDaoBW(TransferBWHibernateDAO daoBW) {
        this.daoBW = daoBW;
    }

    public GenericDAO getGenericDao() {
        if (genericDao == null) {
            genericDao = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDao;
    }

    public void setGenericDao(GenericDAO genericDao) {
        this.genericDao = genericDao;
    }

    /**
     * Este metodo carga ciertos datos necesarios para las transferencias posteriores dentro de esta clase.
     *
     * @throws PeriscopeException
     */
    public void prepareDataForProcess() throws ScopixException {
        log.info(START);
        //prepara la data para ser procesada
        setProducts(new HashMap<String, Integer>());
        //llena el map de products
        StringBuilder sqlProduct = new StringBuilder();
        sqlProduct.append("select * from product_hierarchy");
        try {
            List<Map<String, Object>> lista = getDaoBW().getJdbcTemplate().queryForList(sqlProduct.toString());
            for (Map m : lista) {
                Integer id = (Integer) m.get("id");
                //String department = (String) m.get("department");
                String product = (String) m.get("product");
                Integer areaId = (Integer) m.get("area_id");
                String key = StringUtils.join(new Object[]{areaId, product}, ".");
                if (!getProducts().containsKey(key)) {
                    getProducts().put(key, id);
                }
            }

            setMetricIds(new HashMap<String, Integer>());
            //llena el map de metricsId
            StringBuilder sqlMetrics = new StringBuilder();
            sqlMetrics.append("select * from metrics_names");
            List<Map<String, Object>> listaMetric = getDaoBW().getJdbcTemplate().queryForList(sqlMetrics.toString());
            for (Map m : listaMetric) {
                Integer id = (Integer) m.get("id");
                Integer producId = (Integer) m.get("product_id");
                Integer count = (Integer) m.get("metric_count");
                StringBuilder key = new StringBuilder(producId.toString());
                for (int i = 1; i <= count; i++) {
                    key.append(".").append(m.get("metric" + i + "_name"));
                    if (!getMetricIds().containsKey(key.toString())) {
                        getMetricIds().put(key.toString(), id);
                    }
                }
            }

            setIndicatorNameIds(new HashMap<String, Integer>());
            StringBuilder sqlIndicatorNames = new StringBuilder();
            sqlIndicatorNames.append("SELECT * FROM indicator_names ");
            List<Map<String, Object>> listaIndicatorNames =
                    getDaoBW().getJdbcTemplate().queryForList(sqlIndicatorNames.toString());
            for (Map m : listaIndicatorNames) {
                Integer id = (Integer) m.get("id");
                Integer producId = (Integer) m.get("product_id");
                StringBuilder key = new StringBuilder();
                key.append(producId.toString()).append(".").append(m.get("name"));
                if (!getIndicatorNameIds().containsKey(key.toString())) {
                    getIndicatorNameIds().put(key.toString(), id);
                }
            }
            log.debug(getIndicatorNameIds().toString());
        } catch (DataAccessException ex) {
            log.error("DataAccessException", ex);
            throw new ScopixException(ex);
        }

        log.info(END);
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public Map<String, Integer> getMetricIds() {
        return metricIds;
    }

    public void setMetricIds(Map<String, Integer> value) {
        this.metricIds = value;
    }

    private Integer getProductIdByMap(Integer areaId, String product) {
        String key = StringUtils.join(new Object[]{areaId, product}, ".");
        Integer ret = getProducts().get(key);
        return ret;
    }

    private Integer getMetricNameIdByMap(List<String> names, Integer productId) {
        String key = productId + "." + StringUtils.join(names.toArray(), ".");
        Integer ret = getMetricIds().get(key);
        return ret;
    }

    private Integer getIndicatorNameIdByMap(String name, Integer productId) {
        String key = productId + "." + name;
        Integer ret = getIndicatorNameIds().get(key);
        if (ret == null) {
            log.error("Indicador no encontrado en mapa. Key = " + "'" + key + "'");
        }
        return ret;
    }

    /**
     * @return the indicatorNameIds
     */
    public Map<String, Integer> getIndicatorNameIds() {
        return indicatorNameIds;
    }

    /**
     * @param indicatorNameIds the indicatorNameIds to set
     */
    public void setIndicatorNameIds(Map<String, Integer> indicatorNameIds) {
        this.indicatorNameIds = indicatorNameIds;
    }

    private String getSqlInsertComplianceFacts() {
        if (sqlInsertComplianceFacts == null) {
            sqlInsertComplianceFacts = "INSERT INTO compliance_facts (id, compliance, target, date_id, product_id, store_id, "
                    + " metric1, metric2, metric3, metric4, metric5, metric6, metric7, metric8, metric9, metric10,"
                    + " metric11, metric12, metric13, metric14, metric15, metric16, metric17, metric18, metric19,"
                    + " metric20, metric21, metric22, metric23, metric24, metric25, metric26, metric27, metric28,"
                    + " metric29, metric30, metric31, metric32, metric33, metric34, metric35, metric36, metric37,"
                    + " metric38, metric39, metric40, metric41, metric42, metric43, metric44, metric45, metric46,"
                    + " metric47, metric48, metric49, metric50, metric_id1, metric_id2, metric_id3, metric_id4,"
                    + " metric_id5, metric_id6, metric_id7, metric_id8, metric_id9, metric_id10, metric_id11,"
                    + " metric_id12, metric_id13, metric_id14, metric_id15, metric_id16, metric_id17, metric_id18,"
                    + " metric_id19, metric_id20, metric_id21, metric_id22, metric_id23, metric_id24, metric_id25,"
                    + " metric_id26, metric_id27, metric_id28, metric_id29, metric_id30, metric_id31, metric_id32,"
                    + " metric_id33, metric_id34, metric_id35, metric_id36, metric_id37, metric_id38, metric_id39,"
                    + " metric_id40, metric_id41, metric_id42, metric_id43, metric_id44, metric_id45, metric_id46,"
                    + " metric_id47, metric_id48, metric_id49, metric_id50, metric_names_id, rule_name, hour, "
                    + " metric_count, hour_id) VALUES (";
        }
        return sqlInsertComplianceFacts;

    }
}
