package com.scopix.periscope.command;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationroutingmanagement.services.webservices.EvaluationRoutingWebServices;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase intermediaria entre la capa web y la de servicios para la invocación de servicios relacionados con el envío de
 * situaciones
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class SendEvaluationCommand implements Serializable {

    private EvaluationRoutingWebServices evaluationRoutingService;
    private static final long serialVersionUID = 5757977532849956843L;
    private static Logger log = Logger.getLogger(SendEvaluationCommand.class);

    /**
     * Obtiene las colas del cliente
     * 
     * @author carlos polo
     * @version 2.0.0
     * @param lstEvidenceEvaluationDTO DTO de evaluación por enviar
     * @param corporateName nombre del cliente seleccionado
     * @param sessionId id de sessión del usuario autenticado
     * @param login login del usuario autenticado
     * @since 6.0
     * @date 19/02/2013
     * @throws ScopixException excepción en el envío de la evaluación
     */
    public void execute(List<EvidenceEvaluationDTO> lstEvidenceEvaluationDTO, String corporateName, Long sessionId, String login)
            throws ScopixException {

        log.info("start - " + login);
        try {
            // Invoca el servicio
            log.debug("invocando servicio para enviar evaluación - " + login);
            getEvaluationRoutingService().sendEvidenceEvaluation(lstEvidenceEvaluationDTO, corporateName, sessionId);

        } catch (Exception ex) {
            throw new ScopixException(ex.getMessage(), ex);
        }
        log.info("end - " + login);
    }

    /**
     * @return the evaluationRoutingService
     */
    public EvaluationRoutingWebServices getEvaluationRoutingService() {
        if (evaluationRoutingService == null) {
            evaluationRoutingService = (EvaluationRoutingWebServices) SpringSupport.getInstance().findBean(
                    "EvaluationRoutingWebServicesClient");
        }
        return evaluationRoutingService;
    }

    /**
     * @param evaluationRoutingService the evaluationRoutingService to set
     */
    public void setEvaluationRoutingService(EvaluationRoutingWebServices evaluationRoutingService) {
        this.evaluationRoutingService = evaluationRoutingService;
    }
}