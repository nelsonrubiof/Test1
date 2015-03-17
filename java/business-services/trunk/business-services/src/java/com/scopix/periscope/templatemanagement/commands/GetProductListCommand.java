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
 * GetProductListCommand.java
 *
 * Created on 20-08-2008, 04:01:29 PM
 *
 */
package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetProductListCommand {

    private Logger log = Logger.getLogger(GetMetricTemplateListCommand.class);

    public List<Product> execute(Product product) throws ScopixException {
        log.debug("start");
        TemplateManagementHibernateDAOImpl dao = SpringSupport.getInstance().
                findBeanByClassName(TemplateManagementHibernateDAOImpl.class);
        List<Product> products = null;
        products = dao.getProductList(product);
        log.debug("end");
        return products;
    }
}
