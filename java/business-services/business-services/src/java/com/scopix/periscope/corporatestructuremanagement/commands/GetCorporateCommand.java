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
 * GetCorporateCommand.java
 *
 * Created on 24-06-2008, 04:06:25 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.List;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetCorporateCommand {

    public Corporate execute() throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        Corporate corporate = null;

        try {
            List<Corporate> list = dao.getAll(Corporate.class);
            if (!list.isEmpty()) {
                corporate = list.get(0);
            }

        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            //PENDING message
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }

        return corporate;
    }
}
