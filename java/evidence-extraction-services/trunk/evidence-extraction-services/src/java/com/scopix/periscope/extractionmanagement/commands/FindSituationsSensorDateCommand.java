/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * FindSituationsCommand.java
 *
 * Created on 03-04-2009, 10:31:11 AM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAO;
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class FindSituationsSensorDateCommand {

    private static Logger log = Logger.getLogger(FindSituationsSensorDateCommand.class);
    private EvidenceExtractionDAO dao;

    public List<SituationRequest> execute(String camId, Date date) throws ScopixException {
        log.info("start");

        List<SituationRequest> list = new ArrayList<SituationRequest>();

        try {
            List<SituationRequest> listSensor = getDao().findSituationsForASensor(camId, date);
            List<SituationRequest> listEP = getDao().findSituationsForAEvidenceProvider(camId, date);
            if (listSensor != null && !listSensor.isEmpty()) {
                list.addAll(listSensor);
            }
            if (listEP != null && !listEP.isEmpty()) {
                if (list.isEmpty()) {
                    list.addAll(listEP);
                } else {
                    Collections.sort(list);
                    for (SituationRequest sr : listEP) {
                        if (Collections.binarySearch(list, sr) < 0) {
                            list.add(sr);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new ScopixException(e);
        }
        log.info("end");

        return list;
    }

    public EvidenceExtractionDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionDAOImpl.class);
        }
        return dao;
    }

    public void setDao(EvidenceExtractionDAO dao) {
        this.dao = dao;
    }
}
