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
 *  GetEvidenceExtractionRequestBySituationCommand.java
 * 
 *  Created on 20-12-2011, 04:28:14 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionplanmanagement.commands.dao.EvidenceExtractionRequestDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class GetEvidenceExtractionRequestBySituationCommand {

    private static Logger log = Logger.getLogger(GetEvidenceExtractionRequestBySituationCommand.class);
    private EvidenceExtractionRequestDAO dao;

    public List<EvidenceExtractionRequest> execute(SituationRequest situationRequest, Date fechaSolicitud)
            throws ScopixException {
        return getDao().getEvidenceExtractionRequestBySituation(situationRequest, fechaSolicitud);
    }

    public EvidenceExtractionRequestDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionRequestDAO.class);
        }
        return dao;
    }

    public void setDao(EvidenceExtractionRequestDAO dao) {
        this.dao = dao;
    }
}
