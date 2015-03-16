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
 * AddProductCommand.java
 *
 * Created on 20-08-2008, 03:55:36 PM
 *
 */
package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.Product;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddProductCommand {

    public void execute(Product product) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        dao.save(product);
    }
}
