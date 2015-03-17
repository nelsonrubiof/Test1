/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * OperatorWebServiceImpl.java
 * 
 * Created on 2/09/2014
 */
package com.scopix.periscope.services;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.UserEvaluation;
import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.operatorweb.services.OperatorWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

@WebService(endpointInterface = "com.scopix.periscope.operatorweb.services.OperatorWebService")
@SpringBean(rootClass = OperatorWebService.class)
public class OperatorWebServiceImpl implements OperatorWebService {

    private OperatorManager operatorManager;
    private static Logger log = Logger.getLogger(OperatorWebServiceImpl.class);

    @POST
    @Consumes("application/json")
    @Path("/notifyProofsGeneration")
    @Override
    public void notifyProofsGeneration(ResultMarksContainerDTO container) throws ScopixException {
        log.info("start");

        Boolean error = container.getError();
        Long sessionId = container.getSessionId();
        Integer situationId = container.getSituationId();

        log.debug("sessionId: [" + sessionId + "], error: [" + error + "]");
        UserEvaluation userEvaluation = getOperatorManager().getHmUsersEvaluations().get(sessionId);

        Integer successCounter = userEvaluation.getSuccess();
        Integer failCounter = userEvaluation.getFail();
        Integer pendingCounter = userEvaluation.getPending();
        log.debug("successCounter: [" + successCounter + "], failCounter: [" + failCounter + "], pendingCounter: ["
                + pendingCounter + "]");

        try {
            pendingCounter--;
            userEvaluation.setPending(pendingCounter);

            if (!error) { // éxito
                getOperatorManager().processProofsNames(container);

                log.debug("disponiendose a enviar situacion al core, situationId: [" + situationId + "]");
                int retryNumber = getOperatorManager().getRetryNumber();
                boolean exito = getOperatorManager().sendEvaluation(situationId, retryNumber);

                log.debug("exito: [" + exito + "]");
                if (exito) {
                    log.info("envio al core exitoso, situationId: [" + situationId + "]");
                    successCounter++;
                    userEvaluation.setSuccess(successCounter);
                } else {
                    log.error("error en envio al core para situationId: [" + situationId + "]");
                    failCounter = processFail(situationId, userEvaluation, failCounter);
                }
            } else {
                log.error("error en generacion de proofs para situationId: [" + situationId + "], mensaje de error: ["
                        + container.getErrorMsg() + "]");

                failCounter = processFail(situationId, userEvaluation, failCounter);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            failCounter = processFail(situationId, userEvaluation, failCounter);
        }

        // actualiza en el hashmap los valores de evaluación del usuario
        getOperatorManager().getHmUsersEvaluations().put(sessionId, userEvaluation);
        log.info("end, sessionId: [" + sessionId + "], successCounter: [" + successCounter + "], failCounter: [" + failCounter
                + "], pendingCounter: [" + pendingCounter + "]");
    }

    /**
     * @param situationId
     * @param userEvaluation
     * @param failCounter
     * @return
     */
    private Integer processFail(Integer situationId, UserEvaluation userEvaluation, Integer failCounter) {
        log.info("start, situationId: [" + situationId + "], failCounter: [" + failCounter + "]");
        // removiendo situación del hashmap
        getOperatorManager().removeSituationFromHash(situationId);

        failCounter++;
        userEvaluation.setFail(failCounter);

        log.info("end, failCounter: [" + failCounter + "]");
        return failCounter;
    }

    public OperatorManager getOperatorManager() {
        if (operatorManager == null) {
            operatorManager = SpringSupport.getInstance().findBeanByClassName(OperatorManager.class);
        }
        return operatorManager;
    }
}