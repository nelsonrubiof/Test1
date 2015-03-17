/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * QueuedObjectStateTransition.java
 *
 * Created on 16-05-2008, 04:36:01 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.states.configs.StateConfigurationHolder;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public abstract class QueuedObjectStateTransition {

    public static void configure(StateConfigurationHolder conf) {
        conf.configureTransition(EvaluationState.ENQUEUED, PendingEvaluationOperations.ASIGN_TO_CHECK, EvaluationState.CHECKING);
        conf.configureTransition(EvaluationState.ENQUEUED, PendingEvaluationOperations.BACK_TO_QUEUE, EvaluationState.ENQUEUED);
        conf.configureTransition(EvaluationState.CHECKING, PendingEvaluationOperations.BACK_TO_QUEUE, EvaluationState.ENQUEUED);
        conf.configureTransition(EvaluationState.FINISHED, PendingEvaluationOperations.BACK_TO_QUEUE, EvaluationState.ENQUEUED);
        conf.configureTransition(EvaluationState.DELETED, PendingEvaluationOperations.BACK_TO_QUEUE, EvaluationState.ENQUEUED);
        conf.configureTransition(EvaluationState.ENQUEUED, PendingEvaluationOperations.DELETE, EvaluationState.DELETED);
        conf.configureTransition(EvaluationState.CHECKING, PendingEvaluationOperations.DELETE, EvaluationState.DELETED);
        conf.configureTransition(EvaluationState.CHECKING, PendingEvaluationOperations.COMPUTE_RESULTS, EvaluationState.FINISHED);
        conf.configureTransition(EvaluationState.ENQUEUED, PendingEvaluationOperations.FAIL, EvaluationState.ERROR);
        conf.configureTransition(EvaluationState.CHECKING, PendingEvaluationOperations.FAIL, EvaluationState.ERROR);
        conf.configureTransition(EvaluationState.DELETED, PendingEvaluationOperations.FAIL, EvaluationState.ERROR);
        conf.configureTransition(EvaluationState.FINISHED, PendingEvaluationOperations.FAIL, EvaluationState.ERROR);
        conf.configureTransition(EvaluationState.ERROR, PendingEvaluationOperations.BACK_TO_QUEUE, EvaluationState.ENQUEUED);
    }

    protected QueuedObjectStateTransition() {
    }

    public enum PendingEvaluationOperations {

        ASIGN_TO_CHECK, BACK_TO_QUEUE, DELETE, COMPUTE_RESULTS, FAIL;

        @Override
        public String toString() {
            return this.name();
        }
    }
}
