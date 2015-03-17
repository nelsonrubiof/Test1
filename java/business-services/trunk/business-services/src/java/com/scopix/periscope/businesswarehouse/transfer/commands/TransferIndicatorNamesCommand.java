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
 * TransferIndicatorNamesCommand.java
 *
 * Created on 07-11-2012, 04:54:23 PM
 *
 */


package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferIndicatorNamesCommand {
    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferStoresCommand.class);
    private GenericDAO genericDao;
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Transferencia de nombre de indicadores
     * 
     * @throws PeriscopeException 
     */
    public void execute() throws ScopixException {
        log.info(START);
        List<String> sqls = new ArrayList<String>();
        try {
            List<Indicator> names = getGenericDao().getAll(Indicator.class);
            if (names != null && !names.isEmpty()) {
                for (Indicator name : names) {
                    List<IndicatorProductAndAreaType> ipaats = getDao().getIndicatorProductAndAreaTypes(name.getId());
                    for (IndicatorProductAndAreaType ipaat : ipaats) {
                        StringBuilder sql = new StringBuilder();
                        Integer productId = getDaoBW().getProductId(ipaat.getAreaType().getId(),
                                ipaat.getProduct().getDescription());
                        Integer id = getDaoBW().getIndicatorNameId(name.getName(), productId);
                        if (id != null) {
                            //UPDATE
                            sql.append("UPDATE indicator_names set product_id = ").append(productId).append(", ");
                            sql.append("name = '").append(StringEscapeUtils.escapeSql(name.getName())).append("', ");
                            sql.append("x_axis = '").append(StringEscapeUtils.escapeSql(name.getLabelX())).append("', ");
                            sql.append("y_axis = '").append(StringEscapeUtils.escapeSql(name.getLabelY())).append("', ");
                            sql.append("initial_time = ").append(name.getInitialTime()).append(", ");
                            sql.append("ending_time = ").append(name.getEndingTime());
                            sql.append(" WHERE id = ").append(id);
                        } else {
                            //INSERT
                            sql.append("INSERT INTO indicator_names(id, product_id, name, x_axis, y_axis, initial_time,");
                            sql.append("ending_time)");
                            sql.append(" VALUES (nextval('indicator_name_seq'), ");
                            sql.append(productId).append(", ");
                            sql.append("'").append(StringEscapeUtils.escapeSql(name.getName())).append("', ");
                            sql.append("'").append(StringEscapeUtils.escapeSql(name.getLabelX())).append("', ");
                            sql.append("'").append(StringEscapeUtils.escapeSql(name.getLabelY())).append("', ");
                            sql.append(name.getInitialTime()).append(" , ");
                            sql.append(name.getEndingTime()).append(")");
                        }
                        sqls.add(sql.toString());
                    }
                }
                getDaoBW().executeSQL(sqls);
            }
        } catch (Exception e) {
            log.error("error = " + e.getMessage());
            throw new ScopixException(e);
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
    
    public GenericDAO getGenericDao() {
        if (genericDao == null) {
            genericDao = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDao;
    }

    public void setGenericDao(GenericDAO genericDao) {
        this.genericDao = genericDao;
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