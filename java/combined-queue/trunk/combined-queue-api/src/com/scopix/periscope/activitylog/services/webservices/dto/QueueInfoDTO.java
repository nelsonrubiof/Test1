/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  QueueInfoDTO.java
 * 
 *  Created on Jul 8, 2014, 5:00:53 PM
 * 
 */

package com.scopix.periscope.activitylog.services.webservices.dto;

/**
 *
 * @author Sebastian
 */
public class QueueInfoDTO {
    
    private String corporateName;
    private String queueName ;
    private Integer evaluations;
    private Integer avgTimePerEval;

    /**
     * @return the corporateName
     */
    public String getCorporateName() {
        return corporateName;
    }

    /**
     * @param corporateName the corporateName to set
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    /**
     * @return the queueName
     */
    public String getQueueName() {
        return queueName;
    }

    /**
     * @param queueName the queueName to set
     */
    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    /**
     * @return the evaluations
     */
    public Integer getEvaluations() {
        return evaluations;
    }

    /**
     * @param evaluations the evaluations to set
     */
    public void setEvaluations(Integer evaluations) {
        this.evaluations = evaluations;
    }

    /**
     * @return the avgTimePerEval
     */
    public Integer getAvgTimePerEval() {
        return avgTimePerEval;
    }

    /**
     * @param avgTimePerEval the avgTimePerEval to set
     */
    public void setAvgTimePerEval(Integer avgTimePerEval) {
        this.avgTimePerEval = avgTimePerEval;
    }

}
