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
 * TransferProductAndAreasCommand.java
 *
 * Created on 07-11-2012, 04:52:05 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.dao.TransferBWHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
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
public class TransferProductAndAreasCommand {
    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferProductAndAreasCommand.class);
    private TransferHibernateDAO dao;
    private TransferBWHibernateDAO daoBW;

    /**
     * Encargada de la transferencia de productos y areas a bw
     * 
     * @throws PeriscopeException 
     */
    public void execute() throws ScopixException {
        log.info(START);
        List<String> sqls = new ArrayList<String>();
        try {
            List<ProductAndAreaDTO> dtos = getDao().getProductsAndAreas();
            Integer productId = null;
            if (dtos != null) {
                for (ProductAndAreaDTO dto : dtos) {
                    StringBuilder sql = new StringBuilder();

                    productId = getDaoBW().getProductId(dto.getAreaId(), dto.getProduct());
                    if (productId != null && productId > 0) {
                        sql.append("UPDATE product_hierarchy SET department = '");
                        sql.append(StringEscapeUtils.escapeSql(dto.getArea())).append("', ");
                        sql.append("product = '").append(StringEscapeUtils.escapeSql(dto.getProduct())).append("' ");
                        sql.append("WHERE id = ").append(productId);
                    } else {
                        //Insert
                        sql.append("INSERT INTO product_hierarchy (id, department, product, area_id) ");
                        sql.append("VALUES (nextval('product_seq'), ");
                        sql.append("'").append(StringEscapeUtils.escapeSql(dto.getArea())).append("', ");
                        sql.append("'").append(StringEscapeUtils.escapeSql(dto.getProduct())).append("', ");
                        sql.append(dto.getAreaId()).append(")");
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