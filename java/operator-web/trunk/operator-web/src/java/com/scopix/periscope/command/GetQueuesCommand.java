package com.scopix.periscope.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.scopix.periscope.enums.EnumLabels;
import com.scopix.periscope.model.Queue;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.queuemanagement.dto.OperatorQueueDTO;
import com.scopix.periscope.queuemanagement.services.webservices.QueueManagementWebServices;

/**
 * Clase intermediaria entre la capa web y la de servicios para la invocación de servicios relacionados con la obtención de colas
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class GetQueuesCommand implements Serializable {

    private static final long serialVersionUID = 4009648892746712408L;
    private static Logger log = Logger.getLogger(GetQueuesCommand.class);

    /**
     * Obtiene las colas del cliente
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param corporateName nombre del cliente
     * @return List<Queue> colas del cliente
     * @since 6.0
     * @date 19/02/2013
     * @throws ScopixException excepción en la obtención de las colas
     */
    public List<Queue> execute(String corporateName) throws ScopixException {
        log.info("start, corporateName: [" + corporateName + "]");
        List<Queue> queues = new ArrayList<Queue>();
        try {
            QueueManagementWebServices queueManagementService = (QueueManagementWebServices) SpringSupport.getInstance()
                    .findBean("QueueManagementWebServicesClient-" + corporateName);

            List<OperatorQueueDTO> lstOperatorQueueDTO = queueManagementService.getOperatorQueues();

            // Procesa las colas del cliente
            processOperatorQueues(lstOperatorQueueDTO, queues);

        } catch (ScopixWebServiceException ex) {
            log.error(ex, ex);
            throw new ScopixException(ex.getMessage(), ex);
        }

        log.info("end");
        return queues;
    }

    /**
     * Procesa las colas del cliente
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param lstOperatorQueueDTO lista de colas DTO
     * @param queues lista de colas a retornar
     * @since 6.0
     * @date 19/02/2013
     */
    protected void processOperatorQueues(List<OperatorQueueDTO> lstOperatorQueueDTO, List<Queue> queues) {
        if (lstOperatorQueueDTO != null) {
            Queue rejectedQueue = null;

            for (OperatorQueueDTO queueDTO : lstOperatorQueueDTO) {
                if (queueDTO.getName().equalsIgnoreCase("REJECTED")) {
                    rejectedQueue = new Queue();
                    rejectedQueue.setId(queueDTO.getId());
                    rejectedQueue.setName(queueDTO.getName());
                    continue;
                }

                Queue queue = new Queue();
                queue.setId(queueDTO.getId());
                queue.setName(queueDTO.getName());

                queues.add(queue);
            }

            // ordena las métricas alfabéticamente por nombre
            Collections.sort(queues);

            Queue queue0 = new Queue();
            queue0.setId(-1);
            queue0.setName(EnumLabels.COMBO_DEFAULT.toString());
            queues.add(0, queue0);

            if (rejectedQueue != null) {
                queues.add(rejectedQueue);
            }
        }
    }
}