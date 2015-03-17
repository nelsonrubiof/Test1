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
 *  GetSituationRequestRealRandomCommand.java
 * 
 *  Created on 25-11-2013, 12:45:20 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.SituationRequestRealRandom;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson Rubio
 * @autor-email nelson.rubio@scopixsolutions.com
 * @version
 */
public class GetSituationRequestRealRandomCommand {

    private static Logger log = Logger.getLogger(GetSituationRequestRealRandomCommand.class);
    private GenericDAO dao;

    /**
     * @return the dao
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

    public SituationRequestRealRandom execute(Integer realRandomId) {
        log.info("start [realRandomId:" + realRandomId + "]");
        SituationRequestRealRandom ret = getDao().get(realRandomId, SituationRequestRealRandom.class);
        log.info("end");
        return ret;
    }
}
