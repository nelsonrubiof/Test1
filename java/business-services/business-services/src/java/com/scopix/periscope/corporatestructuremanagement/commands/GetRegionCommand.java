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
 * GetRegionCommand.java
 *
 * Created on 04-08-2008, 07:04:58 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.corporatestructuremanagement.Region;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetRegionCommand {

    public Region execute(Integer id) throws ScopixException {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        Region region = null;

        try {
            region = dao.get(id, Region.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            //PENDING message
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }

        return region;

    }
}
