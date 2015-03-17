/*
 * 
 * Copyright © 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QueueManager.java
 *
 * Created on 20-05-2008, 11:16:03 AM
 *
 */


package com.scopix.periscope.queuemanagement;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.queuemanagement.dto.PendingEvaluationDTO;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
@SpringBean(rootClass=QueueManager.class)
public class QueueManager {
    /**
     *
     * @param queueList Lista de PendingEvaluationDTO
     * @param checksIds lista de Ids Seleccionados
     * @param uncheck booleano que indica si hay que sacar la seleccion a la lista 
     */
    public void setChecked(List<PendingEvaluationDTO> queueList, List<Integer> checksIds, boolean uncheck) {
        if (uncheck) {
            this.uncheck(queueList);
        }
        Collections.sort(queueList);
        for (int chk : checksIds) {
            PendingEvaluationDTO pendingEvaluationDTO = new PendingEvaluationDTO();
            pendingEvaluationDTO.setPendingEvaluationId(chk);
            int index = Collections.binarySearch(queueList, pendingEvaluationDTO);
            if (index >= 0) {
                queueList.get(index).setChecked(true);
            }
        }
    }

    /**
     *
     * @param queueList Lista de PendingEvaluationDTO a sacarle el check
     */
    public void uncheck(List<PendingEvaluationDTO> queueList) {
        Collections.sort(queueList, PendingEvaluationDTO.getComparatorByChecked());
        for (PendingEvaluationDTO pendingEvaluationDTO : queueList) {
            if (pendingEvaluationDTO.isChecked()) {
                pendingEvaluationDTO.setChecked(false);
            } else {
                break;
            }
        }
    }
}