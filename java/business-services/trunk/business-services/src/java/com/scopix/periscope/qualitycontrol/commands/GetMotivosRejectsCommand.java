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
 *  GetMotivosRejectsCommand.java
 * 
 *  Created on 13-12-2011, 06:15:48 PM
 * 
 */

package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.qualitycontrol.MotivoRejected;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author nelson
 */
public class GetMotivosRejectsCommand {
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

    public List<MotivoRejected> execute() {
        List<MotivoRejected> list = getDao().getAll(MotivoRejected.class);
        for (MotivoRejected mr:list){
            mr.getClasificacions().isEmpty();
        }
        //ordenamos la lista por description
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("description", Boolean.FALSE);
        SortUtil.sortByColumn(cols, list);
        return list;
    }
}
