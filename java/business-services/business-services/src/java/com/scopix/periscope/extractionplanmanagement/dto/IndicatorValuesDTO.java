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
 * IndicatorValuesDTO.java
 *
 * Created on 02-11-2009, 06:08:32 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.Date;

/**
 *
 * @author Cesar Abarza
 */
public class IndicatorValuesDTO {

    private String indicatorName;
    private Date date;
    private String situation;
    private Double numerator;
    private Double denominator;
    private String state;
    private boolean sentToMIS;
    private Date sentToMISDate;
    private String storeName;
    private Date evaluationDate;

    /**
     * @return the indicatorName
     */
    public String getIndicatorName() {
        return indicatorName;
    }

    /**
     * @param indicatorName the indicatorName to set
     */
    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    /**
     * @return the situation
     */
    public String getSituation() {
        return situation;
    }

    /**
     * @param situation the situation to set
     */
    public void setSituation(String situation) {
        this.situation = situation;
    }

    /**
     * @return the numerator
     */
    public Double getNumerator() {
        return numerator;
    }

    /**
     * @param numerator the numerator to set
     */
    public void setNumerator(Double numerator) {
        this.numerator = numerator;
    }

    /**
     * @return the denominator
     */
    public Double getDenominator() {
        return denominator;
    }

    /**
     * @param denominator the denominator to set
     */
    public void setDenominator(Double denominator) {
        this.denominator = denominator;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the sentToMIS
     */
    public boolean isSentToMIS() {
        return sentToMIS;
    }

    /**
     * @param sentToMIS the sentToMIS to set
     */
    public void setSentToMIS(boolean sentToMIS) {
        this.sentToMIS = sentToMIS;
    }

    /**
     * @return the sentToMISDate
     */
    public Date getSentToMISDate() {
        return sentToMISDate;
    }

    /**
     * @param sentToMISDate the sentToMISDate to set
     */
    public void setSentToMISDate(Date sentToMISDate) {
        this.sentToMISDate = sentToMISDate;
    }

    /**
     * @return the storeName
     */
    public String getStoreName() {
        return storeName;
    }

    /**
     * @param storeName the storeName to set
     */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * @return the evaluationDate
     */
    public Date getEvaluationDate() {
        return evaluationDate;
    }

    /**
     * @param evaluationDate the evaluationDate to set
     */
    public void setEvaluationDate(Date evaluationDate) {
        this.evaluationDate = evaluationDate;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
