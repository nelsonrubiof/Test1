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
 * GetAreaTypeCommand.java
 *
 * Created on 07-05-2008, 07:24:42 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetAreaTypeCommand {

    private static Logger log = Logger.getLogger(GetAreaTypeCommand.class);
    private GenericDAO dao;

    public AreaType execute(Integer id) throws ScopixException {
        log.debug("start");
        AreaType areaType = null;

        try {
            areaType = getDao().get(id, AreaType.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            //PENDING message
            throw new ScopixException("elementNotFound", objectRetrievalFailureException);
        }
        log.debug("end");
        return areaType;
    }

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }
}
