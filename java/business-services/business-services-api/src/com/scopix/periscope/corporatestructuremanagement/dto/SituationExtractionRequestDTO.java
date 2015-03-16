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
 *  SituationExtractionRequestDTO.java
 * 
 *  Created on 05-06-2012, 12:22:58 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

/**
 *
 * @author nelson
 */
public class SituationExtractionRequestDTO {

    private String timeSample; //Formato HHmm

    public String getTimeSample() {
        return timeSample;
    }

    public void setTimeSample(String timeSample) {
        this.timeSample = timeSample;
    }

}
