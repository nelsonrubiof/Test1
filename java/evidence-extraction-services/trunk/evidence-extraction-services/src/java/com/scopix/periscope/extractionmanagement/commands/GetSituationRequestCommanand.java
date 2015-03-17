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
 *  GetSituationRequestCommanand.java
 * 
 *  Created on 10-10-2012, 01:13:57 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author nelson
 */
public class GetSituationRequestCommanand {
    private GenericDAO dao;

    /**
     * @return GenericDAO desde configuracion
     */
    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(GenericDAO dao) {
        this.dao = dao;
    }

    public SituationRequest execute(Integer id) {
        SituationRequest sr = getDao().get(id, SituationRequest.class);
        return sr;
    }
}
