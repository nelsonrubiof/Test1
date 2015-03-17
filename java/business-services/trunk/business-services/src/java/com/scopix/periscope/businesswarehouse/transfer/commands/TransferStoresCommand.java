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
 * TransferStoresCommand.java
 *
 * Created on 07-11-2012, 04:51:47 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Gustavo Alvarez
 */
public class TransferStoresCommand {

    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferStoresCommand.class);
    private GenericDAO genericDao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Encargada de la transferencia de tiendas hacia bw
     * 
     * @throws PeriscopeException 
     */
    public void execute() throws ScopixException {
        log.info(START);
        List<String> sqls = new ArrayList<String>();
        try {
            List<Store> stores = getGenericDao().getAll(Store.class);
            List<Integer> storeIds = getDaoBW().getStoresIdFromBW();
            if (stores != null && !stores.isEmpty()) {
                if (storeIds == null) {
                    storeIds = new ArrayList<Integer>();
                }
                Collections.sort(storeIds);
                for (Store store : stores) {
                    StringBuilder sql = null;
                    if (Collections.binarySearch(storeIds, store.getId()) >= 0) {
                        //Update
                        sql = new StringBuilder();
                        sql.append("UPDATE store_hierarchy SET nation = '");
                        sql.append(store.getCountry().getDescription()).append("', ");
                        sql.append("region = '").append(StringEscapeUtils.escapeSql(store.getRegion().getDescription()));
                        sql.append("', ");
                        sql.append("store = '").append(StringEscapeUtils.escapeSql(store.getDescription())).append("', ");
                        sql.append("store_name = '").append(StringEscapeUtils.escapeSql(store.getName())).append("', ");
                        sql.append("latitude = ").append(store.getLatitudeCoordenate()).append(", ");
                        sql.append("longitude = ").append(store.getLongitudeCoordenate()).append(" ");
                        sql.append("WHERE id = ").append(store.getId());
                    } else {
                        //Insert
                        sql = new StringBuilder();
                        sql.append("INSERT INTO store_hierarchy (id, nation, region, store, store_name, latitude, longitude) ");
                        sql.append("VALUES (");
                        sql.append(store.getId()).append(", ");
                        sql.append(" '").append(StringEscapeUtils.escapeSql(store.getCountry().getDescription())).append("', ");
                        sql.append(" '").append(StringEscapeUtils.escapeSql(store.getRegion().getDescription())).append("', ");
                        sql.append(" '").append(StringEscapeUtils.escapeSql(store.getDescription())).append("', ");
                        sql.append(" '").append(StringEscapeUtils.escapeSql(store.getName())).append("', ");
                        sql.append(store.getLatitudeCoordenate()).append(", ");
                        sql.append(store.getLongitudeCoordenate()).append(")");
                    }
                    Collections.sort(sqls);

                    if (Collections.binarySearch(sqls, sql.toString()) < 0) {
                        sqls.add(sql.toString());
                    }
                }
            }
            getDaoBW().executeSQL(sqls);
        } catch (DataAccessException ex) {
            log.error("error = " + ex.getMessage());
            throw new ScopixException(ex);
        }
        log.info(END);
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