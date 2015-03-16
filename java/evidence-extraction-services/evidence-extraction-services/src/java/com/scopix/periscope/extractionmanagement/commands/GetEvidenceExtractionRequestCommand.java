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
 *  GetEvidenceExtractionRequestCommand.java
 * 
 *  Created on 22-11-2013, 03:15:44 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAO;
import com.scopix.periscope.extractionplanmanagement.commands.dao.EvidenceExtractionRequestDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.log4j.Logger;

/**
 * Recupera un Evidence Extraction Request dado su id
 * @author Nelson Rubio
 * @autor-email nelson.rubio@scopixsolutions.com
 * @version 1.0.0
 */
public class GetEvidenceExtractionRequestCommand {

    private static Logger log = Logger.getLogger(GetEvidenceExtractionRequestCommand.class);
    private EvidenceExtractionRequestDAO dao;

    /**
     * @return the dao
     */
    public EvidenceExtractionRequestDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findDao(EvidenceExtractionRequestDAO.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvidenceExtractionRequestDAO dao) {
        this.dao = dao;
    }

    public EvidenceExtractionRequest execute(Integer evidenceExtractionRequestId) throws ScopixException {
        log.info("start [evidenceExtractionRequestId:" + evidenceExtractionRequestId + "]");
        EvidenceExtractionRequest eer = getDao().getEvidenceExtractionRequestById(evidenceExtractionRequestId);
        log.info("end");
        return eer;
    }
}
