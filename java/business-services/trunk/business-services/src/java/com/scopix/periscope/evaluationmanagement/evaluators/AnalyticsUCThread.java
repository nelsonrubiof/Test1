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
 *  AnalyticsUCThread.java
 * 
 *  Created on 26-01-2012, 04:46:40 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author nelson
 */
public class AnalyticsUCThread extends Thread {
    
    private Integer pendingEvaluationId;
    private boolean initialized;
    
    public void init(Integer id) {
        pendingEvaluationId = id;
        initialized = true;
        this.setName(this.getClass().getSimpleName() + "-" + pendingEvaluationId);
    }
    
    @Override
    public void run() {
        if (initialized) {
            //llamamos al manager para que este realize la evaluacion de todos los osMetric del PE y determine que se hace
            AutomaticEvaluatorManager manager = SpringSupport.getInstance().findBeanByClassName(AutomaticEvaluatorManager.class);
            manager.evaluateAnalyticsEvaluation(pendingEvaluationId);
            
        }
    }
}
