package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

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
 *  PeopleCountingAnaliticsUCDetection.java
 * 
 *  Created on 06-02-2012, 02:53:20 PM
 * 
 */

/**
 *
 * @author nelson
 */
public class PeopleCountingAnaliticsUCDetection {

    private Integer x;
    private Integer y;
    private Integer width;
    private Integer height;
    private Double maxResponse;
    private Integer numResponses;
    private Double averageResponse;

    public PeopleCountingAnaliticsUCDetection() {
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getMaxResponse() {
        return maxResponse;
    }

    public void setMaxResponse(Double maxResponse) {
        this.maxResponse = maxResponse;
    }

    public Integer getNumResponses() {
        return numResponses;
    }

    public void setNumResponses(Integer numResponses) {
        this.numResponses = numResponses;
    }

    public Double getAverageResponse() {
        return averageResponse;
    }

    public void setAverageResponse(Double averageResponse) {
        this.averageResponse = averageResponse;
    }
}
