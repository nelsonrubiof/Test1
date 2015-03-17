/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author Cesar
 */
public interface EvidenceEvaluatorForST {

    void evaluate(PendingEvaluation pendingEvaluation) throws ScopixException;
    /**
     * Se utiliza para determinar si se debe pasar al siguiente estado un PendingEvaluation 
     */
    boolean hasChangePendingEvaluation();
}
