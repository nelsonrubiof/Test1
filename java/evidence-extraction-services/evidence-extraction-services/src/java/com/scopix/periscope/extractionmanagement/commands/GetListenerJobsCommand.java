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
 *  GetRealRandomJobsCommand.java
 * 
 *  Created on 06-01-2014, 09:59:44 AM
 * 
 */

package com.scopix.periscope.extractionmanagement.commands;

import java.util.List;

import org.apache.log4j.Logger;

import com.scopix.periscope.extractionmanagement.ScopixListenerJob;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAO;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * 
 * @author EmO
 */
public class GetListenerJobsCommand {
    private static Logger log = Logger.getLogger(GetListenerJobsCommand.class);
    
    private EvidenceExtractionDAO dao;

    /**
     * @return the dao
     */
    public EvidenceExtractionDAO getDao() {
        if (dao == null){
            dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionDAOImpl.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvidenceExtractionDAO dao) {
        this.dao = dao;
    }

	public List<ScopixListenerJob> execute(String storeName) {
        log.info("start");
		List<ScopixListenerJob> ret = getDao().getListenerJobs(storeName);
        log.info("end");
        return ret;
    }
}
