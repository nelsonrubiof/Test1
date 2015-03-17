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
 * ObservedSituationEvaluationDTO.java
 *
 * Created on 02-11-2009, 06:06:43 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement.dto;

import java.util.Date;

/**
 *
 * @author Cesar Abarza
 */
public class ObservedSituationEvaluationDTO {

    private String situation;
    private Date date;
    private Double evaluationResult;
    private Integer compliant;
    private String ruleName;
    //Guardar en orden
    private Double metric1;
    private String metricName1;
    private Double metric2;
    private String metricName2;
    private Double metric3;
    private String metricName3;
    private Double metric4;
    private String metricName4;
    private Double metric5;
    private String metricName5;
    private Double metric6;
    private String metricName6;
    private Double metric7;
    private String metricName7;
    private Double metric8;
    private String metricName8;
    private Double metric9;
    private String metricName9;
    private Double metric10;
    private String metricName10;
    private boolean sentToMIS;
    private Date sentToMISDate;
    private Double target;
    private Double standard;
    private Integer metricCount;
    private String department;
    private String product;
    private String areaName;
    private String storeName;
    private String state;
    private Date evaluationDate;

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
     * @return the evaluationResult
     */
    public Double getEvaluationResult() {
        return evaluationResult;
    }

    /**
     * @param evaluationResult the evaluationResult to set
     */
    public void setEvaluationResult(Double evaluationResult) {
        this.evaluationResult = evaluationResult;
    }

    /**
     * @return the compliant
     */
    public Integer getCompliant() {
        return compliant;
    }

    /**
     * @param compliant the compliant to set
     */
    public void setCompliant(Integer compliant) {
        this.compliant = compliant;
    }

    /**
     * @return the ruleName
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * @param ruleName the ruleName to set
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * @return the metric1
     */
    public Double getMetric1() {
        return metric1;
    }

    /**
     * @param metric1 the metric1 to set
     */
    public void setMetric1(Double metric1) {
        this.metric1 = metric1;
    }

    /**
     * @return the metricName1
     */
    public String getMetricName1() {
        return metricName1;
    }

    /**
     * @param metricName1 the metricName1 to set
     */
    public void setMetricName1(String metricName1) {
        this.metricName1 = metricName1;
    }

    /**
     * @return the metric2
     */
    public Double getMetric2() {
        return metric2;
    }

    /**
     * @param metric2 the metric2 to set
     */
    public void setMetric2(Double metric2) {
        this.metric2 = metric2;
    }

    /**
     * @return the metricName2
     */
    public String getMetricName2() {
        return metricName2;
    }

    /**
     * @param metricName2 the metricName2 to set
     */
    public void setMetricName2(String metricName2) {
        this.metricName2 = metricName2;
    }

    /**
     * @return the metric3
     */
    public Double getMetric3() {
        return metric3;
    }

    /**
     * @param metric3 the metric3 to set
     */
    public void setMetric3(Double metric3) {
        this.metric3 = metric3;
    }

    /**
     * @return the metricName3
     */
    public String getMetricName3() {
        return metricName3;
    }

    /**
     * @param metricName3 the metricName3 to set
     */
    public void setMetricName3(String metricName3) {
        this.metricName3 = metricName3;
    }

    /**
     * @return the metric4
     */
    public Double getMetric4() {
        return metric4;
    }

    /**
     * @param metric4 the metric4 to set
     */
    public void setMetric4(Double metric4) {
        this.metric4 = metric4;
    }

    /**
     * @return the metricName4
     */
    public String getMetricName4() {
        return metricName4;
    }

    /**
     * @param metricName4 the metricName4 to set
     */
    public void setMetricName4(String metricName4) {
        this.metricName4 = metricName4;
    }

    /**
     * @return the metric5
     */
    public Double getMetric5() {
        return metric5;
    }

    /**
     * @param metric5 the metric5 to set
     */
    public void setMetric5(Double metric5) {
        this.metric5 = metric5;
    }

    /**
     * @return the metricName5
     */
    public String getMetricName5() {
        return metricName5;
    }

    /**
     * @param metricName5 the metricName5 to set
     */
    public void setMetricName5(String metricName5) {
        this.metricName5 = metricName5;
    }

    /**
     * @return the metric6
     */
    public Double getMetric6() {
        return metric6;
    }

    /**
     * @param metric6 the metric6 to set
     */
    public void setMetric6(Double metric6) {
        this.metric6 = metric6;
    }

    /**
     * @return the metricName6
     */
    public String getMetricName6() {
        return metricName6;
    }

    /**
     * @param metricName6 the metricName6 to set
     */
    public void setMetricName6(String metricName6) {
        this.metricName6 = metricName6;
    }

    /**
     * @return the metric7
     */
    public Double getMetric7() {
        return metric7;
    }

    /**
     * @param metric7 the metric7 to set
     */
    public void setMetric7(Double metric7) {
        this.metric7 = metric7;
    }

    /**
     * @return the metricName7
     */
    public String getMetricName7() {
        return metricName7;
    }

    /**
     * @param metricName7 the metricName7 to set
     */
    public void setMetricName7(String metricName7) {
        this.metricName7 = metricName7;
    }

    /**
     * @return the metric8
     */
    public Double getMetric8() {
        return metric8;
    }

    /**
     * @param metric8 the metric8 to set
     */
    public void setMetric8(Double metric8) {
        this.metric8 = metric8;
    }

    /**
     * @return the metricName8
     */
    public String getMetricName8() {
        return metricName8;
    }

    /**
     * @param metricName8 the metricName8 to set
     */
    public void setMetricName8(String metricName8) {
        this.metricName8 = metricName8;
    }

    /**
     * @return the metric9
     */
    public Double getMetric9() {
        return metric9;
    }

    /**
     * @param metric9 the metric9 to set
     */
    public void setMetric9(Double metric9) {
        this.metric9 = metric9;
    }

    /**
     * @return the metricName9
     */
    public String getMetricName9() {
        return metricName9;
    }

    /**
     * @param metricName9 the metricName9 to set
     */
    public void setMetricName9(String metricName9) {
        this.metricName9 = metricName9;
    }

    /**
     * @return the metric10
     */
    public Double getMetric10() {
        return metric10;
    }

    /**
     * @param metric10 the metric10 to set
     */
    public void setMetric10(Double metric10) {
        this.metric10 = metric10;
    }

    /**
     * @return the metricName10
     */
    public String getMetricName10() {
        return metricName10;
    }

    /**
     * @param metricName10 the metricName10 to set
     */
    public void setMetricName10(String metricName10) {
        this.metricName10 = metricName10;
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
     * @return the target
     */
    public Double getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(Double target) {
        this.target = target;
    }

    /**
     * @return the standard
     */
    public Double getStandard() {
        return standard;
    }

    /**
     * @param standard the standard to set
     */
    public void setStandard(Double standard) {
        this.standard = standard;
    }

    /**
     * @return the metricCount
     */
    public Integer getMetricCount() {
        return metricCount;
    }

    /**
     * @param metricCount the metricCount to set
     */
    public void setMetricCount(Integer metricCount) {
        this.metricCount = metricCount;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the product
     */
    public String getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
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
