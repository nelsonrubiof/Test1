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
 * Evaluator.java
 *
 * Created on 27-03-2008, 03:46:21 PM
 *
 */
package com.scopix.periscope.templatemanagement;

/**
 *
 * @author C�sar Abarza Suazo
 */
public class Evaluator {

    private String springBeanEvaluatorName;
    private EvaluatorType evaluatorType;

    public String getSpringBeanEvaluatorName() {
        return springBeanEvaluatorName;
    }

    public void setSpringBeanEvaluatorName(String springBeanEvaluatorName) {
        this.springBeanEvaluatorName = springBeanEvaluatorName;
    }

    public EvaluatorType getEvaluatorType() {
        return evaluatorType;
    }

    public void setEvaluatorType(EvaluatorType evaluatorType) {
        this.evaluatorType = evaluatorType;
    }

    public enum EvaluatorType {

        BRT,
        CCT,
        ST,
        RSET;
    }
}
