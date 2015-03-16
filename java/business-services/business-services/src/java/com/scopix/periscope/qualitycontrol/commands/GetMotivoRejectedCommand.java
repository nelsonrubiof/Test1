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
 *  GetMotivoRejectedCommand.java
 * 
 *  Created on 16-12-2011, 11:01:16 AM
 * 
 */

package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.qualitycontrol.MotivoRejected;

/**
 *
 * @author nelson
 */
public class GetMotivoRejectedCommand {
    private GenericDAO dao;

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public MotivoRejected execute(Integer motivoRechazoId) {
        MotivoRejected mr = getDao().get(motivoRechazoId, MotivoRejected.class);
        return mr;
    }
}
