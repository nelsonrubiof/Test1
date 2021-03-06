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

import com.scopix.periscope.extractionmanagement.ScopixJob;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAO;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class GetRealRandomJobsCommand {
    private static Logger log = Logger.getLogger(GetRealRandomJobsCommand.class);
    
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

    public List<ScopixJob> execute() {
        log.info("start");
        List<ScopixJob> ret = getDao().getRealRandomJobs();
        log.info("end");
        return ret;
    }
}
