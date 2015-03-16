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
 * Evidence.java
 *
 * Created on 06-05-2008, 07:44:43 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class IndicatorValues extends BusinessObject {

    @ManyToOne
    private Indicator indicator;

    @ManyToOne
    private ObservedSituation observedSituation;

    private Double numerator;

    private Double denominator;
    
    @Lob
    private String state;


    @NotNull
    private boolean sentToMIS;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentToMISDate;
    
    private Integer storeId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date evaluationDate;

    public Indicator getIndicator() {
        return indicator;
    }

    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    public ObservedSituation getObservedSituation() {
        return observedSituation;
    }

    public void setObservedSituation(ObservedSituation observedSituation) {
        this.observedSituation = observedSituation;
    }

    public Double getNumerator() {
        return numerator;
    }

    public void setNumerator(Double numerator) {
        this.numerator = numerator;
    }

    public Double getDenominator() {
        return denominator;
    }

    public void setDenominator(Double denominator) {
        this.denominator = denominator;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isSentToMIS() {
        return sentToMIS;
    }

    public void setSentToMIS(boolean sentToMIS) {
        this.sentToMIS = sentToMIS;
    }

    public Date getSentToMISDate() {
        return sentToMISDate;
    }

    public void setSentToMISDate(Date sentToMISDate) {
        this.sentToMISDate = sentToMISDate;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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
}
