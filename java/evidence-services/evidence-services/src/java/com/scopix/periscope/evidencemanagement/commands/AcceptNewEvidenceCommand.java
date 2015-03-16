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
 * AcceptNewEvidenceCommand.java
 *
 * Created on 18-06-2008, 09:55:28 PM
 *
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceAvailableDTO;
import com.scopix.periscope.evaluationmanagement.services.webservices.EvaluationWebServices;
import com.scopix.periscope.evidencemanagement.Evidence;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceRequest;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
public class AcceptNewEvidenceCommand {

    private static Logger log = Logger.getLogger(AcceptNewEvidenceCommand.class);

    //Se elimina la solicitud de session 
    public void execute(Evidence evidence, EvaluationWebServices webservice) throws ScopixException { //, long sessionId
        notifyToBusinessServices(evidence, webservice); //, sessionId
    }

    //se elimina la solicitud de chequeo de seguridad , long sessionId
    
    private void notifyToBusinessServices(Evidence evidence, EvaluationWebServices webservice) throws
            ScopixException {
        log.info("start");
        try {
            if (webservice != null && evidence != null) {
                EvidenceAvailableDTO evidenceAvailableDTO = new EvidenceAvailableDTO();
                evidenceAvailableDTO.setEvidenceDate(evidence.getEvidenceTimestamp());
                evidenceAvailableDTO.setPath(evidence.getEvidenceUri());
                evidenceAvailableDTO.setEvidenceServicesServerId(evidence.getExtractionPlanDetail().getExtractionPlan().
                        getEvidenceExtractionServicesServer().getServerId());

                // obtener la lista de ids de evidence requests
                List<Integer> evidenceRequestIds = new ArrayList<Integer>();
                for (EvidenceRequest evidenceRequest : evidence.getExtractionPlanDetail().getEvidenceRequests()) {
                    evidenceRequestIds.add(evidenceRequest.getBusinessServicesRequestId());
                }
                evidenceAvailableDTO.setEvidenceRequestIds(evidenceRequestIds);
                webservice.newEvidenceAvailable(evidenceAvailableDTO); //, sessionId
            } 
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e);
        }
        log.info("end");
    }
}
