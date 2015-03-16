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
 *  CounterSafewayAnalyticsUCSTEvaluator.java
 * 
 *  Created on 13-02-2013, 05:23:12 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationQueueManager;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.evaluators.EvidenceEvaluatorForST;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * 
 * @author EmO
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_ST, 
 description = "Analiza automáticamente si las imagenes de la situación contienen información para ser evaluada.")
@SpringBean(rootClass = AutomaticPeopleDetectionSTEvaluator.class)
public class AutomaticPeopleDetectionSTEvaluator implements EvidenceEvaluatorForST {

    private static Logger log = Logger.getLogger(AutomaticPeopleDetectionSTEvaluator.class);

    @Override
    public void evaluate(PendingEvaluation pendingEvaluation) throws ScopixException {
        log.info("start");
        try {
            pendingEvaluation.setEvaluationQueue(EvaluationQueue.AUTOMATIC_ANALITICS);
			pendingEvaluation.setEvaluationState(EvaluationState.ENQUEUED);
            EvaluationQueueManager evaluationQueueManager = SpringSupport.getInstance().findBeanByClassName(
                    EvaluationQueueManager.class);

			evaluationQueueManager.enqueue(pendingEvaluation, EvaluationQueue.AUTOMATIC_ANALITICS);
        } catch (Exception e) {
            pendingEvaluation.fail();
			pendingEvaluation.setEvaluationState(EvaluationState.FINISHED);
            log.error("error =" + e, e);
            throw new ScopixException(e);
        }
        log.info("end");
    }

    @Override
    public boolean hasChangePendingEvaluation() {
        return false;
    }
}
