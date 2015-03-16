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
 *  ListEvidenceExtractionRequestCommand.java
 * 
 *  Created on 03-01-2014, 09:58:11 AM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.RequestTimeZone;
import com.scopix.periscope.extractionmanagement.SituationRequestRange;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAO;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

import java.util.List;

import org.apache.log4j.Logger;

/**
 *
 * @author Emo
 */
public class ListSituationEvidenceExtractionRequestCommand {

    private static Logger log = Logger.getLogger(ListSituationEvidenceExtractionRequestCommand.class);
    private EvidenceExtractionDAO dao;

    /**
     * @return the dao
     */
    public EvidenceExtractionDAO getDao() {
        if (dao == null) {
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

    public List<SituationRequestRange> execute(List<RequestTimeZone> requestTimeZones, Integer dayOfWeek) {
        log.info("start");
        List<SituationRequestRange> list = getDao().getSituationRequestRangeRealRandomByRequestTimeZone(requestTimeZones,
                dayOfWeek);
        log.info("end " + list.size());
        return list;
    }
}
