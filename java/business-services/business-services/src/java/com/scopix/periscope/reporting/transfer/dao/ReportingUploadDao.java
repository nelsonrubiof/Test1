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
 *  ReportingUploadDao.java
 * 
 *  Created on 28-01-2011, 06:05:47 PM
 * 
 */
package com.scopix.periscope.reporting.transfer.dao;

import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class ReportingUploadDao {

    private static Logger log = Logger.getLogger(ReportingUploadDao.class);
    private JdbcTemplate jdbcTemplateREP;
    private JdbcTemplate jdbcTemplateBS;
    private boolean cancelProcess;
    private Map<String, Integer> products;
    private Map<String, Integer> metricIds;
    private Map<String, Integer> metricTemplateIds;
    private String sqlInsertReportingData;
    private String sqlNewOsId;

    public void setDataSourceRep(DataSource dataSource) {
        this.setJdbcTemplateREP(new JdbcTemplate(dataSource));
    }

    public void setDataSourceBS(DataSource dataSource) {
        this.setJdbcTemplateBS(new JdbcTemplate(dataSource));
    }

    public JdbcTemplate getJdbcTemplateBS() {
        return jdbcTemplateBS;
    }

    public void setJdbcTemplateBS(JdbcTemplate jdbcTemplateBS) {
        this.jdbcTemplateBS = jdbcTemplateBS;
    }

    public JdbcTemplate getJdbcTemplateREP() {
        return jdbcTemplateREP;
    }

    public void setJdbcTemplateREP(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplateREP = jdbcTemplate;
    }

    public List<Integer> getStoreIds() {
        log.info("start");
        List<Integer> storesIds = getJdbcTemplateREP().queryForList("SELECT id FROM store_hierarchy", Integer.class);
        log.info("end");
        return storesIds;
    }

    public void transferStore(List<Store> stores) {
        log.info("start");
        List<Integer> idsFromReporting = getStoreIds();
        Set<Integer> set = new HashSet<Integer>();
        for (Integer id : idsFromReporting) {
            set.add(id);
        }
        StringBuilder sqlExecutor = new StringBuilder();
        if (stores != null && !stores.isEmpty()) {
//            if (idsFromReporting == null) {
//                idsFromReporting = new ArrayList<Integer>();
//            }

            StringBuilder sql = new StringBuilder();
            for (Store store : stores) {

                if (set.contains(store.getId())) {
                    //Update
                    sql.setLength(0);
                    sql.append("UPDATE store_hierarchy SET nation = '");
                    sql.append(store.getCountry().getDescription()).append("', ");
                    sql.append(" region = '");
                    sql.append(StringEscapeUtils.escapeSql(store.getRegion().getDescription())).append("', ");
                    sql.append(" store = '").append(StringEscapeUtils.escapeSql(store.getDescription())).append("', ");
                    sql.append(" latitude = ").append(store.getLatitudeCoordenate()).append(", ");
                    sql.append(" longitude = ").append(store.getLongitudeCoordenate()).append(" ");
                    sql.append(" WHERE id = ").append(store.getId()).append(";");
                } else {
                    //Insert
                    sql.setLength(0);
                    sql.append("INSERT INTO store_hierarchy (id, nation, region, store, latitude, longitude) ");
                    sql.append(" VALUES (");
                    sql.append(store.getId()).append(", '");
                    sql.append(StringEscapeUtils.escapeSql(store.getCountry().getDescription())).append("', '");
                    sql.append(StringEscapeUtils.escapeSql(store.getRegion().getDescription())).append("', ");
                    sql.append("'").append(StringEscapeUtils.escapeSql(store.getDescription())).append("', ");
                    sql.append(store.getLatitudeCoordenate()).append(", ");
                    sql.append(store.getLongitudeCoordenate()).append(");");
                    set.add(store.getId());
                }
                sqlExecutor.append(sql.toString());
            }
            getJdbcTemplateREP().update(sqlExecutor.toString());
        }
        log.info("end");

    }

    public void transferProductAndArea(List<ProductAndAreaDTO> dtos) {
        log.info("start");
        Integer productId;
        if (dtos != null) {
            StringBuilder sqlExecutor = new StringBuilder();
            for (ProductAndAreaDTO dto : dtos) {
                StringBuilder sql = new StringBuilder();
                productId = getProductId(dto.getAreaId(), dto.getProduct());
                if (productId != null && productId > 0) {
                    sql.append("UPDATE product_hierarchy ");
                    sql.append("SET department = '").append(StringEscapeUtils.escapeSql(dto.getArea())).append("', ");
                    sql.append("product = '").append(StringEscapeUtils.escapeSql(dto.getProduct())).append("', ");
                    sql.append("product_id = ").append(dto.getProductId()).append(" ");
                    sql.append("WHERE id = ").append(productId).append(";");
                } else {
                    //Insert
                    sql.append("INSERT INTO product_hierarchy (id, department, product, area_id, product_id) ");
                    sql.append("VALUES (nextval('product_seq'), ");
                    sql.append("'").append(StringEscapeUtils.escapeSql(dto.getArea())).append("', ");
                    sql.append("'").append(StringEscapeUtils.escapeSql(dto.getProduct())).append("', ");
                    sql.append(dto.getAreaId()).append(",");
                    sql.append(dto.getProductId()).append(");");
                }
                sqlExecutor.append(sql.toString());
            }
            getJdbcTemplateREP().update(sqlExecutor.toString());
        }
        log.info("end");
    }

    public void transferProduct(List<Product> productsBs) {
        log.info("start");
        Integer productId;
        if (productsBs != null) {
            StringBuilder sqlExecutor = new StringBuilder();
            for (Product product : productsBs) {
                StringBuilder sql = new StringBuilder();
                productId = getProduct(product.getId());
                if (productId != null && productId > 0) {
                    sql.append("UPDATE product ");
                    sql.append("SET product = '").append(StringEscapeUtils.escapeSql(product.getDescription())).append("' ");
                    sql.append("WHERE bs_id = ").append(productId).append(";");
                } else {
                    //Insert
                    sql.append("INSERT INTO product (bs_id, product) ");
                    sql.append("VALUES (");
                    sql.append("").append(product.getId()).append(", ");
                    sql.append("'").append(StringEscapeUtils.escapeSql(product.getDescription())).append("');");
                }
                sqlExecutor.append(sql.toString());
            }
            getJdbcTemplateREP().update(sqlExecutor.toString());
        }
        log.info("end");
    }

    public Integer getProductId(Integer areaId, String product) {
        log.info("start");
        Integer productId;
        try {
            productId = getJdbcTemplateREP().queryForInt("SELECT id FROM product_hierarchy WHERE area_id = " + areaId
                    + " AND product = '" + StringEscapeUtils.escapeSql(product) + "'");
        } catch (EmptyResultDataAccessException e) {
            productId = null;
        }
        log.info("end");
        return productId;
    }

    private void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                log.error("no es posible cerrar conexion " + e, e);
            }
        }
    }

    private Integer getProduct(Integer id) {
        log.info("start");
        Integer productId;
        try {
            productId = getJdbcTemplateREP().queryForInt("SELECT bs_id FROM product WHERE bs_id = " + id);
        } catch (EmptyResultDataAccessException e) {
            productId = null;
        }
        log.info("end");
        return productId;
    }

    public void transferMetricTemplates(List<MetricTemplate> metricTemplates) {
        log.info("start");
        Integer metricTemplateIdReporting;
        initMetricTemplateByReporting();
        if (metricTemplates != null) {
            StringBuilder sqlExecutor = new StringBuilder();
            for (MetricTemplate metric : metricTemplates) {
                StringBuilder sql = new StringBuilder();
                metricTemplateIdReporting = getMetricTemplate(metric.getId(), metric.getDescription());
                if (metricTemplateIdReporting != null && metricTemplateIdReporting > 0) {
                    sql.append("UPDATE metric_template ");
                    sql.append("SET description = '").append(StringEscapeUtils.escapeSql(metric.getDescription())).append("' ");
                    sql.append("WHERE id = ").append(metricTemplateIdReporting).append(";");
                } else {
                    //Insert
                    sql.append("INSERT INTO metric_template (id, description, bs_id) ");
                    sql.append(" VALUES (");
                    sql.append(" nextval('metric_template_seq'),");
                    sql.append("'").append(StringEscapeUtils.escapeSql(metric.getDescription())).append("',");
                    sql.append("").append(metric.getId()).append("); ");
                }
                sqlExecutor.append(sql.toString());
            }
            getJdbcTemplateREP().update(sqlExecutor.toString());
        }
        log.info("end");
    }

    private Integer getMetricTemplate(Integer id, String description) {
        log.info("start");
        String key = StringUtils.join(new Object[]{id, description}, ".");
        Integer metricTemplateId;
        try {
            metricTemplateId = getMetricTemplateIds().get(key);
        } catch (Exception e) {
            metricTemplateId = null;
        }
        log.info("end");
        return metricTemplateId;
    }

    public void transferMetricNames(List<Map> metricNamesBS) {
        log.info("start");
        StringBuilder sqlExecutor = new StringBuilder();
        for (Map map : metricNamesBS) {
            List<String> names = (List<String>) map.get("names");
            List<Integer> ids = (List<Integer>) map.get("ids");
            String product = (String) map.get("product");
            Integer area = (Integer) map.get("area");
            Integer productId = getProductId(area, product);
            Integer stId = (Integer) map.get("st_id");
            //todo modificar para recuperar por st_id
            Integer metricNameId;
            if (productId != null) {
                metricNameId = getMetricNameIdFromReporting(names, productId, stId);
            } else {
                log.debug("no existe product id para " + product);
                continue;
            }


            StringBuilder sql = new StringBuilder();
            if (metricNameId != null && metricNameId > 0) {
                //Update
                sql.append("UPDATE metrics_names SET ");
                boolean first = true;
                for (int i = 0; i < names.size(); i++) {
                    if (!first) {
                        sql.append(", ");
                    }
                    sql.append("metric_desc_").append((i + 1)).append(" = '");
                    sql.append(StringEscapeUtils.escapeSql(names.get(i))).append("'");
                    first = false;
                    if (i + 1 == 50) {
                        break;
                    }
                }
                for (int i = 0; i < ids.size(); i++) {
                    sql.append(", metric_id_").append((i + 1)).append(" = '").append(ids.get(i)).append("'");
                    if (i + 1 == 50) {
                        break;
                    }
                }
                sql.append(" WHERE id = ");
                sql.append(metricNameId).append(";");
            } else {
                //Insert
                sql.append("INSERT INTO metrics_names (id, product_id, metric_count");
                for (int i = 0; i < names.size(); i++) {
                    sql.append(", metric_desc_").append((i + 1));
                    if (i + 1 == 50) {
                        break;
                    }

                }
                for (int i = 0; i < ids.size(); i++) {
                    sql.append(", metric_id_").append((i + 1));
                    if (i + 1 == 50) {
                        break;
                    }
                }
                sql.append(") VALUES (").append(stId).append(", ").append(productId).append(", ");
                sql.append(names.size());
                for (int i = 0; i < names.size(); i++) {
                    String name = names.get(i);
                    sql.append(", '").append(StringEscapeUtils.escapeSql(name)).append("'");
                    if (i + 1 == 50) {
                        break;
                    }
                }
                for (int i = 0; i < ids.size(); i++) {
                    Integer id = ids.get(i);
                    sql.append(", '").append(id).append("'");
                    if (i + 1 == 50) {
                        break;
                    }
                }
                sql.append(");");
            }
            sqlExecutor.append(sql.toString());
        }
        getJdbcTemplateREP().update(sqlExecutor.toString());
        log.info("end");
    }

    public Integer getMetricNameIdFromReporting(List<String> names, Integer productId, Integer stId) {
        log.info("start");
        Integer metricNameId = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM metrics_names WHERE product_id=").append(productId);
        sql.append(" and id=").append(stId).append(" ");
        for (int i = 0; i < names.size(); i++) {
            String metricName = StringEscapeUtils.escapeSql(names.get(i));
            sql.append(" AND metric_desc_").append(i + 1).append(" = '").append(StringEscapeUtils.escapeSql(metricName));
            sql.append("'");
            if (i + 1 == 50) {
                //la solicitud no puede ser mayor a 50
                break;
            }
        }
        int metricCount = names.size();
        if (metricCount > 50) {
            metricCount = 50;
        }
        sql.append(" AND metric_count = ").append(names.size());
        try {
            metricNameId = getJdbcTemplateREP().queryForInt(sql.toString());
        } catch (EmptyResultDataAccessException e) {
            log.warn("no es encontro valor sql:" + sql.toString() + " e:" + e);
            metricNameId = null;
        }
        log.info("end");
        return metricNameId;
    }

    public Date getLastDate() {
        log.info("start");
        Date date = null;
        String sql = "select max(day) from  date_hierarchy";
        try {
            date = (Date) getJdbcTemplateREP().queryForObject(sql, Date.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("No existe LastDate en reporting");
            date = null;
        }
        log.info("end");
        return date;

    }

    public void transferDates(UploadProcess process, Locale locale, LinkedHashMap<Integer, String> initYears,
            Calendar calIterator, Date startDate, ArrayList<Integer> weekEnds, String weekName) {
        log.info("start");
        Iterator<Integer> years = initYears.keySet().iterator();
        Integer thisYear = years.next();
        Integer weekOfYear = 1;
        boolean startYear = true;
        Calendar calDummy = null;
        int weekCounterHelper = 1;
        StringBuilder sqlExecutor = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        while (Integer.valueOf(DateFormatUtils.format(calIterator.getTime(), "yyyyMMdd")).intValue()
                <= Integer.valueOf(DateFormatUtils.format(process.getDateProcess(), "yyyyMMdd")).intValue()) {

            // validando si estan en el mismo año de acuerdo a los
            // inicios de años configurados
            String nextYear = initYears.get(thisYear + 1);
            if (nextYear != null
                    && DateFormatUtils.format(calIterator.getTime(), "dd-MM-yyyy").equalsIgnoreCase(nextYear)) {
                thisYear = years.next();
                weekOfYear = 1;
                startYear = true;
            }

            // calendario usado para los QUARTER
            if (startYear) {
                calDummy = Calendar.getInstance(locale);
                calDummy.set(thisYear, 0, 1);
                startYear = false;
            }

            if (Integer.valueOf(DateFormatUtils.format(startDate, "yyyyMMdd")).intValue()
                    <= Integer.valueOf(DateFormatUtils.format(calIterator.getTime(), "yyyyMMdd")).intValue()) {

                String id = DateFormatUtils.format(calIterator.getTime(), "yyyyMMdd");
                String month = DateFormatUtils.format(calIterator.getTime(), "MMM yyyy");
                String day = "to_timestamp('" + id + "', 'YYYYMMDD')";
                String week = weekName + " " + weekOfYear + " " + thisYear;
                int weekOrd = (thisYear * 100) + weekOfYear;
                int monthOrd = (calIterator.get(Calendar.YEAR) * 100) + (calIterator.get(Calendar.MONTH) + 1);
                int quarterOrd = (thisYear * 100) + getQuarter(calDummy);
                String dayOfWeek = DateFormatUtils.format(calIterator.getTime(), "EEEE", locale);
                // define el dia inicial de la semana (de 1 a 7), siendo
                // 1 para el primero y 7 para el ultimo.
                // por ejemplo, para el caso Cabelas el dia 1
                // corresponde al domingo y en el caso de Lowes es el
                // sábado
                int dayOfWeekOrd = ((calIterator.get(Calendar.DAY_OF_WEEK) + 7
                        - Integer.valueOf(SystemConfig.getStringParameter("FIRST_DAY_OF_WEEK"))) % 7) + 1;

                String specialDayFilter = DateFormatUtils.format(calIterator.getTime(), "ddMM");
                String specialDay = SystemConfig.getStringParameter(specialDayFilter + "_DESC") != null
                        ? SystemConfig.getStringParameter(specialDayFilter + "_DESC") : "General";
                int specialDayOrd = Integer.parseInt(SystemConfig.getStringParameter(specialDayFilter + "_ORD") != null
                        ? SystemConfig.getStringParameter(specialDayFilter
                        + "_ORD") : "1");
                // define si es dia de semana o fin de semana de acuerdo
                // al primer dia de la semana definido previamente
                String weekDay = null;
                if (weekEnds.contains(dayOfWeekOrd)) {
                    weekDay = SystemConfig.getStringParameter("WEEK_END_DESC");
                } else {
                    weekDay = SystemConfig.getStringParameter("WEEK_DAY_DESC");
                }

                String dayString = DateFormatUtils.format(calIterator.getTime(), "yyyy-MM-dd");


                sql.setLength(0);
                sql.append("INSERT INTO date_hierarchy (id, year, month, week, day, quarter_ord, quarter, month_ord, ");
                sql.append("week_ord, day_of_week, day_of_week_ord, special_day, special_day_ord, week_day, ");
                sql.append("day_string) ");
                sql.append("VALUES (");
                sql.append(id).append(", ");
                sql.append(thisYear).append(", '");
                sql.append(month).append("', '");
                sql.append(week).append("', ");
                sql.append(day).append(", ");
                sql.append(quarterOrd).append(", ");
                sql.append("'").append("Q").append(this.getQuarter(calDummy)).append(" ").append(thisYear).append("', ");
                sql.append(monthOrd).append(", ");
                sql.append(weekOrd).append(", '");
                sql.append(dayOfWeek).append("', ");
                sql.append(dayOfWeekOrd).append(", '");
                sql.append(specialDay).append("', ");
                sql.append(specialDayOrd).append(", '");
                sql.append(weekDay).append("', '");
                sql.append(dayString).append("');");

                sqlExecutor.append(sql.toString());
            }
            // Contador de semanas. Suma una semana cada 7 dias
            weekCounterHelper++;
            if (weekCounterHelper > 7) {
                weekCounterHelper = 1;
                weekOfYear++;
            }

            calIterator.add(Calendar.DATE, 1);
            calDummy.add(Calendar.DATE, 1);

            if (calDummy.get(Calendar.YEAR) > thisYear && weekOfYear > 3) {
                // dias de holgura para mantener los nombres de los meses como corresponde (de acuerdo a la fecha)
                calDummy.add(Calendar.DATE, -20);
            }
        }

        getJdbcTemplateREP().update(sqlExecutor.toString());
        log.info("end");
    }

    private Statement executeUpdateReportingNew(StringBuilder sqlExecutor, Connection con, Statement statement,
            List<String> deleteOS, Set<Integer> idsOSBs) throws SQLException {
        log.info("start");
        log.debug(" delete " + deleteOS + " sql " + sqlExecutor.toString());
        if (sqlExecutor.length() > 0) {
            try {
                if (statement == null) {
                    statement = con.createStatement();
                }
                StringBuilder prevSql = new StringBuilder();
                //borramos todos los reportingFact que esten haciendo referencia a algun obsituacion que queremos borrar
                prevSql.append("Delete ");
                prevSql.append(" from reporting_fact ");
                prevSql.append(" where observed_situation_id in ");
                prevSql.append("  ( select id ");
                prevSql.append("   FROM observed_situation ");
                prevSql.append("   where ");
                prevSql.append("    observed_situation_id_bs in (").append(StringUtils.join(idsOSBs.toArray(), ",")).append(") ");
                prevSql.append("    and reject=false and ");
                prevSql.append("    reject_datetime is null);");
                for (String sql : deleteOS) {
                    prevSql.append(sql);
                }
                log.debug("prevSql " + prevSql.toString());
                statement.executeUpdate(prevSql.toString());
                log.debug("sqlExecutor");
                statement.executeUpdate(sqlExecutor.toString());
                log.debug("finalizando insert masivo");
            } catch (SQLException e) {

                log.error("Error ejecutantdo sql " + sqlExecutor.toString() + " e:" + e);
                throw (e);
            }
        }
        log.info("end");
        return statement;
    }

    private StringBuilder generateSqlData(ReportingData data, List<MetricTemplate> metricTemplates) {
        /**
         * Integer dateId, Integer productId, String hour, Integer hourId
         */
        String sqlInsert = getSqlInsertReportingData();
        StringBuilder sqlData = new StringBuilder();
        sqlData.append(sqlInsert);
        sqlData.append(data.getId()).append(", ");
        sqlData.append(data.getObservedMetricId()).append(", ");
        String key = null;
        for (MetricTemplate mt : metricTemplates) {
            if (mt.getId().equals(data.getMetricTemplateId())) {
                key = StringUtils.join(new Object[]{mt.getId(), mt.getDescription()}, ".");
                break;
            }
        }
        //se utiliza para mantener history de las metricas
        Integer idMetricTemplateReporting = null;
        if (key != null) {
            idMetricTemplateReporting = getMetricTemplateIds().get(key);
        }
        sqlData.append(idMetricTemplateReporting).append(", ");
        sqlData.append(data.getMetricVal()).append(", ");
        addParameter(data.getEvaluationStartDate(), sqlData, ",");
        addParameter(data.getEvaluationEndDate(), sqlData, ",");
        sqlData.append(data.isCantDo()).append(", ");
        addParameter(data.getCantDoReason(), sqlData, ",");
        addParameter(data.getDateRecord(), sqlData, ",");
        sqlData.append("{osId}");
        sqlData.append(");");
        return sqlData;
    }

    private StringBuilder generateSqlOS(Map<String, Object> values, Set<Integer> idsDelete, Integer osIdCurrent) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into observed_situation ");
        sql.append("(id, cant_do, cant_do_reason, evaluation_init, evaluation_end, evaluation_user, ");
        sql.append(" evidence_date, metric_count, observed_situation_id_bs, reject , reject_user, ");
        sql.append(" reject_datetime, target, reporting_data_ids,hour, date_id,hour_id, product_id,store_id, ");
        sql.append(" situation_template_id ) values (");
        sql.append((Integer) values.get("idOS")).append(", ");
        addParameter(values.get("cantDoSituation"), sql, ",");
        addParameter(values.get("cantDoReasonSituation"), sql, ",");
        addParameter(values.get("evaluationDateInit"), sql, ",");
        addParameter(values.get("evaluationDateEnd"), sql, ",");
        addParameter(values.get("evaluationUser"), sql, ",");
        addParameter(values.get("evidenceDate"), sql, ",");
        sql.append(idsDelete.size()).append(", ");
        sql.append(osIdCurrent).append(", ");
        addParameter(values.get("reject"), sql, ",");
        addParameter(values.get("rejectUser"), sql, ",");
        addParameter(values.get("rejectDate"), sql, ",");
        addParameter(values.get("target"), sql, ",");
        addParameter(StringUtils.join(idsDelete.toArray(), ","), sql, ",");
        sql.append("to_timestamp('").append(values.get("hour")).append("', 'HH24:MI'), ");
        sql.append((Integer) values.get("dateId")).append(", ");
        sql.append((Integer) values.get("hourId")).append(",");
        sql.append((Integer) values.get("productId")).append(",");
        sql.append((Integer) values.get("storeId")).append(", ");
        sql.append((Integer) values.get("situationTemplateId")).append(" ");
        sql.append(");");
        return sql;
    }

    private int getQuarter(Calendar cal) {
        log.info("start");
        int[] months = {3, 6, 9, 12};
        int month = cal.get(Calendar.MONTH) + 1;
        for (int i = 0; i < months.length; i++) {
            if (i == 0) {
                if (month > 0 && month <= months[i]) {
                    log.info("end");
                    return i + 1;
                }
            } else if (month > months[i - 1] && month <= months[i]) {
                log.info("end");
                return i + 1;
            }
        }
        log.info("end");
        return 0;
    }

    public boolean isCancelProcess() {
        return cancelProcess;
    }

    public void setCancelProcess(boolean cancelProcess) {
        this.cancelProcess = cancelProcess;
    }

    private void sortReportingDatas(List<ReportingData> reportingDatas) {
        //ordenamos los resporting data antes de seguir
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("observedSituationId", Boolean.FALSE);
        cols.put("reject", Boolean.TRUE);
        cols.put("rejectedDate", Boolean.TRUE);
        cols.put("metricTemplateId", Boolean.FALSE);
        SortUtil.sortByColumn(cols, reportingDatas);
    }

    private void validateCancellProcess() throws ScopixException {
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }
    }

    private boolean getChangeRejectDate(Date rejectDate, ReportingData data) {
        boolean changeRejectDate = false;
        if (rejectDate != null && data.getRejectedDate() != null
                && !rejectDate.equals(data.getRejectedDate())) {
            changeRejectDate = true;
        } else if (rejectDate == null && data.getRejectedDate() != null) {
            changeRejectDate = true;
        } else if (rejectDate != null && data.getRejectedDate() == null) {
            changeRejectDate = true;
        }
        return changeRejectDate;
    }

    private Map<String, Object> initValuesInsertOS() {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("idOS", null); //Integer
        ret.put("evaluationDateInit", null); //Date
        ret.put("evaluationDateEnd", null); //Date
        ret.put("evidenceDate", null); //Date
        ret.put("cantDoReasonSituation", null); //String
        ret.put("cantDoSituation", Boolean.FALSE); //Boolean
        ret.put("reject", Boolean.FALSE); //Boolean
        ret.put("rejectUser", null); //String
        ret.put("rejectDate", null); //Date
        ret.put("target", null); //Double
        ret.put("evaluationUser", null); //String
        ret.put("hour", null); //String
        ret.put("dateId", null); //Integer
        ret.put("hourId", null); //Integer
        ret.put("productId", null); //Integer
        ret.put("storeId", null); //Integer
        return ret;


    }

    /**
     * carga de datos nueva estructura 2011-08-30
     *
     */
    public void procesaDetailNew(List<ReportingData> reportingDatas, UploadProcessDetail detail,
            List<MetricTemplate> metricTemplates) throws ScopixException {
        log.info("start");
        int count = 0;
        int totProcess = detail.getUploadProcess().getTotalUpload();
        Connection con = null;
        Statement statement = null;
        Set<Integer> idRDUpload = new HashSet<Integer>();
        Set<Integer> idRDUploadTmp = new HashSet<Integer>();
        Set<Integer> idsDelete = new HashSet<Integer>();
        Set<Integer> idsReportingInOS = new HashSet<Integer>();
        List<String> sqlDeleteOS = new ArrayList<String>();

        try {
            sortReportingDatas(reportingDatas);
            con = getJdbcTemplateREP().getDataSource().getConnection();
            con.setAutoCommit(false);
            StringBuilder sqlDataReporting = new StringBuilder();
            StringBuilder sqlExecutor = new StringBuilder();
            //variables para el manejo de OS
            Integer osIdCurrent = 0;
            boolean changeReject = false;
            boolean changeRejectDate;
            Map<String, Object> valuesInsert = initValuesInsertOS();
            Set<Integer> idsRDToCompliance = new HashSet<Integer>();
            Set<Integer> idsOBbs = new HashSet<Integer>();
            Set<Integer> idsOSBs = new HashSet<Integer>();
            for (ReportingData data : reportingDatas) {
                try {
                    if (idsDelete.contains(data.getId())) {
                        log.debug("data ya en ejecucion " + data.getId());
                        continue;
                    }
                    validateCancellProcess();
                    Boolean reject = (Boolean) valuesInsert.get("reject");
                    if (reject != null && !reject.equals(data.isReject())) {
                        changeReject = true;
                    }

                    changeRejectDate = getChangeRejectDate((Date) valuesInsert.get("rejectDate"), data);
                    if (osIdCurrent.equals(0) || !osIdCurrent.equals(data.getObservedSituationId()) || changeReject
                            || changeRejectDate) {
                        //cambiamos ya que por cada vuelta se debe verificar condicion
                        changeReject = false;
                        //si cambia el osId se deben guardar el OS con los datos y los reporting data asociados
                        // o su condicion de reject
                        if (!osIdCurrent.equals(0) && !osIdCurrent.equals(data.getObservedSituationId())) {
                            detail.setUpRecords(detail.getUpRecords() + 1);
                            detail.getUploadProcess().setTotalUpload(totProcess + detail.getUpRecords());
                        }
                        if (!osIdCurrent.equals(0)) {
                            if (statement == null) {
                                statement = con.createStatement();
                            }
                            valuesInsert.put("idOS", getNewOsIdLocal());
                            sqlExecutor.append(generateSqlOS(valuesInsert, idsReportingInOS, osIdCurrent));
                            String s = StringUtils.replace(sqlDataReporting.toString(), "{osId}",
                                    ((Integer) valuesInsert.get("idOS")).toString());
                            idsRDToCompliance.add((Integer) valuesInsert.get("idOS"));
                            sqlExecutor.append(s);
                            sqlDeleteOS.add(generateSqlDeleteOSReporting(osIdCurrent));
                            idsOSBs.add(osIdCurrent);
                            count++;
                            sqlDataReporting.setLength(0);
                            if (count >= 1000) {
                                statement = executeUpdateReportingNew(sqlExecutor, con, statement, sqlDeleteOS, idsOSBs);
                                sqlExecutor.setLength(0);
                                idsDelete.clear();
                                idsOBbs.clear();
                                sqlDeleteOS.clear();
                                idsOSBs.clear();
                                saveReportingData(idRDUploadTmp, detail.getUploadProcess().getId());
                                idRDUploadTmp.clear();
                                count = 0;
                            }
                        }

                        idsReportingInOS.clear();
                        //generamos los datos necesarios para el OS relacionado
                        osIdCurrent = data.getObservedSituationId();
                        idsOSBs.add(osIdCurrent);
                        valuesInsert.put("evaluationDateInit", data.getEvaluationStartDate());
                        valuesInsert.put("evaluationDateEnd", data.getEvaluationEndDate());
                        valuesInsert.put("cantDoReasonSituation", data.getCantDoReason());
                        valuesInsert.put("cantDoSituation", Boolean.valueOf(data.isCantDo()));
                        valuesInsert.put("reject", Boolean.valueOf(data.isReject()));
                        valuesInsert.put("rejectUser", data.getRejectedUser());
                        valuesInsert.put("rejectDate", data.getRejectedDate());
                        valuesInsert.put("target", data.getTarget());
                        valuesInsert.put("evaluationUser", data.getEvaluationUser());
                        valuesInsert.put("evidenceDate", data.getEvidenceDate());
                        valuesInsert.put("situationTemplateId", data.getSituationTemplateId());

                        valuesInsert.put("storeId", data.getStoreId());
                        //valuesInsert.put("hour", null);
                        //valuesInsert.put("dateId", null);
                        //valuesInsert.put("hourId", null);
                        valuesInsert.put("productId", getProductIdByMap(data.getAreaId(), data.getProduct()));
                        if (valuesInsert.get("productId") == null) {
                            log.warn("prodId no existe [" + data.getAreaId() + ":" + data.getProduct() + "]");
                            continue;
                        }
                        if (data.getEvidenceDate() != null) {
                            Integer dateId = new Integer(DateFormatUtils.format(data.getEvidenceDate(), "yyyyMMdd"));
                            String hour = DateFormatUtils.format(data.getEvidenceDate(), "HH:mm");
                            Integer hourId = new Integer(DateFormatUtils.format(data.getEvidenceDate(), "HH")) + 1;
                            valuesInsert.put("dateId", dateId);
                            valuesInsert.put("hour", hour);
                            valuesInsert.put("hourId", hourId);
                        }
                    } else {
                        if (valuesInsert.get("evaluationDateInit") != null && data.getEvaluationStartDate() != null
                                && ((Date) valuesInsert.get("evaluationDateInit")).after(data.getEvaluationStartDate())) {
                            valuesInsert.put("evaluationDateInit", data.getEvaluationStartDate());
                        }
                        if (valuesInsert.get("evaluationDateEnd") != null && data.getEvaluationEndDate() != null
                                && ((Date) valuesInsert.get("evaluationDateEnd")).before(data.getEvaluationEndDate())) {
                            valuesInsert.put("evaluationDateEnd", data.getEvaluationEndDate());
                        }
                        if (!(Boolean) valuesInsert.get("cantDoSituation")) {
                            valuesInsert.put("cantDoReasonSituation", data.getCantDoReason());
                            valuesInsert.put("cantDoSituation", Boolean.valueOf(data.isCantDo()));
                        }
                    }

                    idsReportingInOS.add(data.getId());
                    idsDelete.add(data.getId());
                    StringBuilder sqlData = generateSqlData(data, metricTemplates); //, dateId, productId, hour, hourId);
                    sqlDataReporting.append(sqlData.toString());
                    data.setSentToMIS(true);
                    data.setSentToMISDate(new Date());
                    data.setState("OK");
                    data.setUploadProcessId(detail.getUploadProcess().getId());
                    idRDUpload.add(data.getId());
                    idRDUploadTmp.add(data.getId());
                    count++;
                } catch (IllegalArgumentException e) {
                    data.setState(e.getMessage());
                    log.error(e, e);
                } catch (ScopixException e) {
                    log.error(e, e);
                    if (e.getMessage().equals("MANUAL")) {
                        throw (e);
                    }
                } catch (Exception e) {
                    data.setState(e.getMessage());
                    log.error(e, e);
                    throw new ScopixException(e);
                }
            } //end for reportingData

            if (statement == null) {
                statement = con.createStatement();
            }
            //Integer
            valuesInsert.put("idOS", getNewOsIdLocal());
            sqlExecutor.append(generateSqlOS(valuesInsert, idsReportingInOS, osIdCurrent));
            String s = StringUtils.replace(sqlDataReporting.toString(), "{osId}",
                    ((Integer) valuesInsert.get("idOS")).toString());
            sqlExecutor.append(s);
            idsRDToCompliance.add((Integer) valuesInsert.get("idOS"));
            sqlDeleteOS.add(generateSqlDeleteOSRejected(osIdCurrent, (Boolean) valuesInsert.get("reject"),
                    (Date) valuesInsert.get("rejectDate")));
            statement = executeUpdateReportingNew(sqlExecutor, con, statement, sqlDeleteOS, idsOSBs); //idsDelete,
            saveReportingData(idRDUploadTmp, detail.getUploadProcess().getId());
            con.commit();
            updateComplianceReportingFact(idsRDToCompliance, statement, detail.getUploadProcess());
            log.debug("commit para detail");
            con.commit();
            if (statement != null) {
                statement.close();
            }
            detail.setUpRecords(detail.getUpRecords() + 1);
            detail.getUploadProcess().setTotalUpload(totProcess + detail.getUpRecords());

        } catch (SQLException e) {
            try {
                con.rollback();
                revertRPBs(idRDUpload);
                detail.setUpRecords(0);
                throw new ScopixException(e);
            } catch (SQLException e2) {
                throw new ScopixException(e2);
            }
        } catch (ScopixException e) {
            try {
                //revertimos en el servidor remoto
                con.rollback();
                if (e.getMessage().equals("MANUAL")) {
                    revertRPBs(idRDUpload);
                    detail.setUpRecords(0);
                }
                throw (e);
            } catch (SQLException e2) {
                throw new ScopixException(e2);
            }
        } finally {
            closeConnection(con);
        }
        log.info("end");
    }

    private String generateSqlDeleteOSReporting(Integer osIdCurrent) {
        //nos aseguramos de borrar algun os activo
        String sqlDelete = "DELETE FROM observed_situation where observed_situation_id_bs=" + osIdCurrent
                + " and reject=false and reject_datetime is null;";
        return sqlDelete;
    }

    private String generateSqlDeleteOSRejected(Integer osIdCurrent, Boolean reject, Date rejectDate) {
        String sqlDelete = "DELETE FROM observed_situation where observed_situation_id_bs=" + osIdCurrent
                + " and reject=" + reject;
        if (rejectDate != null) {
            sqlDelete += " and reject_datetime ='" + DateFormatUtils.format(rejectDate, "yyyy-MM-dd HH:mm:ss") + "';";
        } else {
            sqlDelete += " and reject_datetime is null;";
        }
        return sqlDelete;
    }

    public String saveReportingData(ReportingData data) {
        log.info("start");
        StringBuilder sql = new StringBuilder();
        sql.append("update reporting_data set ");
        sql.append(" sent_tomis = ").append(data.isSentToMIS()).append(", ");
        sql.append(" sent_tomisdate = '").append(DateFormatUtils.format(data.getSentToMISDate(), "yyyy-MM-dd HH:mm:ss"));
        sql.append("' , ");
        sql.append(" state = '").append(StringEscapeUtils.escapeSql(data.getState())).append("', ");
        sql.append(" upload_process_id = ").append(data.getUploadProcessId());
        sql.append(" where id = ").append(data.getId()).append(";");
        log.info("end");
        return sql.toString();
    }

    public void saveReportingData(Set<Integer> ids, Integer processId) {
        log.info("2 start");
        if (!ids.isEmpty()) {
            StringBuilder sql = new StringBuilder();
            sql.append("update reporting_data set ");
            sql.append(" sent_tomis = true, ");
            sql.append(" sent_tomisdate = '").append(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
            sql.append("' , ");
            sql.append(" state = 'OK', ");
            sql.append(" upload_process_id = ").append(processId);
            sql.append(" where id in(").append(StringUtils.join(ids, ",")).append(");");
            getJdbcTemplateBS().update(sql.toString());
        }
        log.info("end");
    }

    private void revertRPBs(Set<Integer> idRDUpload) {
        log.info("start");
        if (idRDUpload.size() > 0) {
            String ids = StringUtils.join(idRDUpload.toArray(), ",");
            StringBuilder sqlRevert = new StringBuilder();
            sqlRevert.append("update reporting_data set sent_tomis = false where id in(");
            sqlRevert.append(ids).append(");");
            getJdbcTemplateBS().update(sqlRevert.toString());
        }
        log.info("end");

    }

    public void prepareDataForProcess() {
        log.info("start");
        //prepara la data para ser procesada
        setProducts(new HashMap<String, Integer>());
        //llena el map de products
        StringBuilder sqlProduct = new StringBuilder();
        sqlProduct.append("select * from product_hierarchy");
        List<Map<String, Object>> lista = getJdbcTemplateREP().queryForList(sqlProduct.toString());
        for (Map m : lista) {
            Integer id = (Integer) m.get("id");
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
        List<Map<String, Object>> listaMetric = getJdbcTemplateREP().queryForList(sqlMetrics.toString());
        for (Map m : listaMetric) {
            Integer id = (Integer) m.get("id");
            Integer producId = (Integer) m.get("product_id");
            Integer count = (Integer) m.get("metric_count");
            StringBuilder key = new StringBuilder(StringUtils.join(new Object[]{id, producId}, "."));
            for (int i = 1; i <= count; i++) {
                key.append(".").append(m.get("metric_desc_" + i));
                if (!getMetricIds().containsKey(key.toString())) {
                    getMetricIds().put(key.toString(), id);
                }
            }
        }
        //cargamos todos los metricTemplate desde Reporting
        initMetricTemplateByReporting();

        //log.debug(getMetricIds());
        log.info("end");
    }

    public void cleanDataForProcess() {
        //limpia los maps antes de salir
        setProducts(null);
        setMetricIds(null);
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

    public void validateConnection() throws ScopixException {
        int count = 1;
        String sql = " select now()";
        while (true) {
            try {
                Date now = (Date) getJdbcTemplateREP().queryForObject(sql, Date.class);
                //si pasa el sql hay conexion
                break;
            } catch (DataAccessResourceFailureException e) {
                if (count == 2) {
                    throw new ScopixException("NO se pudo conectar a servidor de datos " + e);
                }
                try {
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e2) {
                    log.warn("no se pudo hacer espera");
                }

                count++;
            }
        }
    }

    public void transferAreaTypes(List<Map<String, Object>> areaTypes) {
        log.info("start");
        StringBuilder sqlExecutor = new StringBuilder();
        for (Map map : areaTypes) {
            Integer id = (Integer) map.get("id");
            String name = (String) map.get("name");
            String description = (String) map.get("description");
            Integer idReporting = getAreaTypeIdFromReporting(id);
            if (idReporting != null && idReporting > 0) {
                //Update
                sqlExecutor.append(" UPDATE area_type SET name='").append(StringEscapeUtils.escapeSql(name)).append("', ");
                sqlExecutor.append(" description='").append(description).append("' where id=").append(id).append(";");
            } else {
                //Update
                sqlExecutor.append(" insert into area_type (id, name, description) values ");
                sqlExecutor.append(" (").append(id).append(",'").append(StringEscapeUtils.escapeSql(name));
                sqlExecutor.append("','").append(StringEscapeUtils.escapeSql(description)).append("');");
            }
        }
        getJdbcTemplateREP().update(sqlExecutor.toString());
        log.info("end");
    }

    private Integer getAreaTypeIdFromReporting(Integer id) {
        log.info("start");
        Integer areaTypeId;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT id FROM area_type WHERE id=").append(id);
        try {
            areaTypeId = getJdbcTemplateREP().queryForInt(sql.toString());
        } catch (EmptyResultDataAccessException e) {
            log.warn("no es encontro valor sql:" + sql.toString() + " e:" + e);
            areaTypeId = null;
        }
        log.info("end");
        return areaTypeId;
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

    private String getSqlInsertReportingData() {
        if (sqlInsertReportingData == null) {
            sqlInsertReportingData = "INSERT INTO reporting_fact (id, "
                    + "observed_metric_id, metric_template_id, metric_val, "
                    + " evaluation_start_date, evaluation_end_date, "
                    + " cant_do, "
                    + "cant_do_reason, date_record, observed_situation_id)"
                    + "VALUES (";
        }
        return sqlInsertReportingData;
    }

    private Integer getNewOsIdLocal() {
        Integer id = null;
        try {
            id = (Integer) getJdbcTemplateBS().queryForObject(getSqlNewOsId(), Integer.class);
        } catch (DataAccessException e) {
            log.error("no es posible recuperar new OsId en reporting " + e, e);
        }
        return id;
    }

    private String getSqlNewOsId() {
        if (sqlNewOsId == null) {
            sqlNewOsId = "select nextval('reporting_data_observed_situation_seq')";
        }
        return sqlNewOsId;
    }

    private void updateComplianceReportingFact(Set<Integer> idsRDToCompliance, Statement statement, UploadProcess process) {

        log.info("start");
        String sql = "select update_compliance_os(id) from observed_situation where id in("
                + StringUtils.join(idsRDToCompliance, ",") + ")";
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            log.error("Error ejecutando " + sql + " " + e, e);
            process.setComments(process.getComments() + "Error update_compliance_os " + e + "\n");
            //throw (e);
        }
        log.info("end");
    }

    public Map<String, Integer> getMetricTemplateIds() {
        return metricTemplateIds;
    }

    public void setMetricTemplateIds(Map<String, Integer> value) {
        this.metricTemplateIds = value;
    }

    private void initMetricTemplateByReporting() {
        List<Map<String, Object>> res = getJdbcTemplateREP().queryForList("SELECT * FROM metric_template");
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Map<String, Object> row : res) {
            Integer id = (Integer) row.get("id");
            String description = (String) row.get("description");
            Integer idBs = (Integer) row.get("bs_id");
            String key = StringUtils.join(new Object[]{idBs, description}, ".");
            map.put(key, id);
        }
        setMetricTemplateIds(map);
        log.debug("map:" + map);
    }

    public Integer getLastDateHierarchyReporting() {
        String sql = "select max(id) from date_hierarchy";
        Integer ret = null;
        try {
            ret = getJdbcTemplateREP().queryForInt(sql);
        } catch (DataAccessException e) {
            log.warn("no es encontro valor sql:" + sql + " e:" + e);
        }
        return ret;
    }
}
