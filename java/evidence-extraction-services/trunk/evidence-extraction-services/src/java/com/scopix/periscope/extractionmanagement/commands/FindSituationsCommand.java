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
import com.scopix.periscope.extractionmanagement.dao.EvidenceExtractionDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class FindSituationsCommand {

    public List<SituationRequest> execute(String camId) throws ScopixException {
        EvidenceExtractionDAOImpl dao = SpringSupport.getInstance().findBeanByClassName(EvidenceExtractionDAOImpl.class);
        List<SituationRequest> list = new ArrayList<SituationRequest>();

        try {
            List<SituationRequest> listSensor = dao.findSituationsForASensor(camId);
            List<SituationRequest> listEP = dao.findSituationsForAEvidenceProvider(camId);
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

        return list;
    }
}
