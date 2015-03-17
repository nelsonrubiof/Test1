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
 * EvaluationQueue.java
 *
 * Created on 12-05-2008, 10:40:47 AM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.queuemanager.QueueType;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public enum EvaluationQueue implements QueueType {

    OPERATOR {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    }, AUTOMATIC {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    }, SUPERVISOR {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    }, METRIC {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    }, SITUATION {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    }, COMPLIANCE_CONDITION {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    }, AUTOMATIC_ANALITICS {
        @Override
        public Boolean isBlocker() {
            return Boolean.TRUE;
        }
    };
}
