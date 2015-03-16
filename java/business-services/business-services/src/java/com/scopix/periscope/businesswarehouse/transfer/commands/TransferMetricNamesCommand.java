/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * TransferMetricNamesCommand.java
 *
 * Created on 07-11-2012, 04:53:39 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferMetricNamesCommand {
    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferMetricNamesCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Transferencia de nombres de metricas a bw
     * 
     * @throws PeriscopeException 
     */
    public void execute() throws ScopixException {
        log.info(START);
        List<String> sqls = new ArrayList<String>();
        try {
            List<Map> metricNamesBS = getDao().getMetricNamesFromBS();
            for (Map map : metricNamesBS) {
                List<String> names = (List<String>) map.get("names");
                String product = (String) map.get("product");
                Integer area = (Integer) map.get("area");
                Integer productId = getDaoBW().getProductId(area, product);
                Integer metricNameId = getDaoBW().getMetricNameIdFromBW(names, productId);
                StringBuilder sql = new StringBuilder();
                if (metricNameId != null && metricNameId > 0) {
                    sql.append("UPDATE metrics_names SET ");
                    boolean first = true;
                    for (int i = 0; i < names.size(); i++) {
                        if (!first) {
                            sql.append(", ");
                        }
                        sql.append("metric").append((i + 1)).append("_name = '");
                        sql.append(StringEscapeUtils.escapeSql(names.get(i))).append("'");
                        first = false;
                    }
                    sql.append(" WHERE id = ").append(metricNameId);
                } else {
                    sql.append("INSERT INTO metrics_names (id, product_id, metric_count");
                    for (int i = 0; i < names.size(); i++) {
                        sql.append(", metric").append((i + 1)).append("_name");
                    }
                    sql.append(") VALUES (nextval('metrics_names_seq'), ");
                    sql.append(productId).append(", ");
                    sql.append(names.size());
                    for (String name : names) {
                        sql.append(", '").append(StringEscapeUtils.escapeSql(name)).append("'");
                    }
                    sql.append(")");
                }
                Collections.sort(sqls);

                if (Collections.binarySearch(sqls, sql.toString()) < 0) {
                    sqls.add(sql.toString());
                }
            }
            getDaoBW().executeSQL(sqls);
        } catch (SQLException ex) {
            log.error("error = " + ex.getMessage());
            throw new ScopixException(ex);
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
}