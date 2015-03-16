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
 *  DeleteUploadProcessDetailByIdCommand.java
 * 
 *  Created on 14-01-2011, 03:48:32 PM
 * 
 */
package com.scopix.periscope.reporting.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.reporting.UploadProcessDetail;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * Elimina los UploadProcessDetail segun lista de ids recibidos
 * @author nelson
 */
public class DeleteUploadProcessDetailByIdCommand {

    private Logger log = Logger.getLogger(DeleteUploadProcessDetailByIdCommand.class);
    private GenericDAO dao;

    public void execute(List<Integer> updId) throws ScopixException {
        for (Integer id : updId) {
            try {
                getDao().remove(id, UploadProcessDetail.class);
            } catch (RuntimeException e) {
                log.error("No es posible borrar detalles " + id, e);
                throw new ScopixException("NO_DELETE_DETAIL", e);
            }
        }
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
