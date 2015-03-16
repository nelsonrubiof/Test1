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
 *  ProductAndAreaDTOListCommand.java
 * 
 *  Created on 31-01-2011, 05:55:27 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAO;
import com.scopix.periscope.reporting.dao.ReportingHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * Recupera la lista de ProductAndAreaDTO para ser transferidos a reporting
 * @author nelson
 */
public class ProductAndAreaDTOListCommand {

    private static Logger log = Logger.getLogger(ProductAndAreaDTOListCommand.class);
    private ReportingHibernateDAO dao;

    public List<ProductAndAreaDTO> execute(Integer[] areaTypeIds) {
        log.debug("start");
        List<ProductAndAreaDTO> lista = getDao().getProductsAndAreasByAreaTypes(areaTypeIds);
        log.debug("end");
        return lista;
    }

    public ReportingHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ReportingHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ReportingHibernateDAO dao) {
        this.dao = dao;
    }
}
