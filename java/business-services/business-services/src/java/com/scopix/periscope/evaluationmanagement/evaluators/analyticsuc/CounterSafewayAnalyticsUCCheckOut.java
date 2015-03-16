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
 *  CounterSafewayAnalyticsUCCheckOut.java
 * 
 *  Created on 12-02-2013, 06:14:28 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc;

/**
 *
 * @author nelson
 */
public class CounterSafewayAnalyticsUCCheckOut {

    private Integer id;
    private Integer number;
    private String checkoutState;
    private Integer detectedNumber;
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * @return the checkoutState
     */
    public String getCheckoutState() {
        return checkoutState;
    }

    /**
     * @param checkoutState the checkoutState to set
     */
    public void setCheckoutState(String checkoutState) {
        this.checkoutState = checkoutState;
    }

    /**
     * @return the detectedNumber
     */
    public Integer getDetectedNumber() {
        return detectedNumber;
    }

    /**
     * @param detectedNumber the detectedNumber to set
     */
    public void setDetectedNumber(Integer detectedNumber) {
        this.detectedNumber = detectedNumber;
    }

}
