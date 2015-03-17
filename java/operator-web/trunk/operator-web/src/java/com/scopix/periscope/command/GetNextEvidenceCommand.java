package com.scopix.periscope.command;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.scopix.periscope.common.WebServicesUtil;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.evaluationroutingmanagement.services.webservices.EvaluationRoutingWebServices;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase intermediaria entre la capa web y la de servicios para la invocación de servicios relacionados con la obtención de
 * evidencias
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class GetNextEvidenceCommand implements Serializable {

    private static final long serialVersionUID = 6992403627109489805L;
    private static Logger log = Logger.getLogger(GetNextEvidenceCommand.class);

    /**
     * Obtiene siguiente evidencia del web service
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param queueName nombre de la cola
     * @param corporateName nombre del cliente seleccionado
     * @param sessionId sessionId
     * @param login login del usuario autenticado
     * @return SituationSendDTO objeto retornado bien sea por el web service o de manera simulada
     * @since 6.0
     * @date 19/02/2013
     * @throws ScopixException excepción en la obtención de la siguiente situación
     */
    public Situation execute(String queueName, String corporateName, Long sessionId, String login) throws ScopixException {
        log.info("start - " + login);
        Situation situation = null;
        // Invoca el servicio web para obtener la siguiente evidencia
        SituationSendDTO situationSendDTO = getNextEvidenceDTO(queueName, corporateName, sessionId, login);

        if (situationSendDTO != null) {
            log.debug("Convirtiendo objeto DTO al modelo - " + login);
            // Convierte el objeto retornado por el web service a un objeto del modelo de la capa cliente
            situation = WebServicesUtil.toSituation(situationSendDTO, login);
        }
        log.info("end - " + login);
        return situation;
    }

    /**
     * Obtiene siguiente evidencia del web service
     * 
     * @author carlos polo
     * @version 2.0.0
     * @param queueName nombre de la cola
     * @param corporateName nombre del cliente seleccionado
     * @param sessionId sessionId
     * @param login login del usuario autenticado
     * @return SituationSendDTO objeto retornado bien sea por el web service o de manera simulada
     * @since 6.0
     * @date 19/02/2013
     * @throws ScopixException excepción en la obtención de la siguiente situación
     */
    protected SituationSendDTO getNextEvidenceDTO(String queueName, String corporateName, Long sessionId, String login)
            throws ScopixException {

        log.info("start, obteniendo siguiente situación - " + login);
        SituationSendDTO situationSendDTO = null;

        try {
            EvaluationRoutingWebServices evaluationRoutingService = (EvaluationRoutingWebServices) SpringSupport.getInstance()
                    .findBean("EvaluationRoutingWebServicesClient");

            // Invocación de servicio para obtener situación (colas combinadas)
            situationSendDTO = evaluationRoutingService.getNextEvidence(queueName, corporateName, sessionId);

        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }

        log.info("end - " + login);
        return situationSendDTO;
    }
}