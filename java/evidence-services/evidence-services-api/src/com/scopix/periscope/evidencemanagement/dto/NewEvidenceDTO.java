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
 * NewEvidenceDTO.java
 *
 * Created on 18-06-2008, 03:59:34 AM
 *
 */
package com.scopix.periscope.evidencemanagement.dto;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
public class NewEvidenceDTO {

    private String evidenceDate;

    private int extractionPlanDetailId;

    //CHECKSTYLE:OFF
    private String filename;
    //CHECKSTYLE:ON

//    public int getExtractionPlanDetailId() {
//        return extractionPlanDetailId;
//    }
//
//    public void setExtractionPlanDetailId(int extractionPlanDetailId) {
//        this.extractionPlanDetailId = extractionPlanDetailId;
//    }
//
//    //CHECKSTYLE:OFF
//    public String getFilename() {
//        return Filename;
//    }
//
//    public void setFilename(String Filename) {
//        this.Filename = Filename;
//    }
//    //CHECKSTYLE:ON
//
//    public String getEvidenceDate() {
//        return evidenceDate;
//    }
//
//    public void setEvidenceDate(String evidenceDate) {
//        this.evidenceDate = evidenceDate;
//    }

    /**
     * @return the evidenceDate
     */
    public String getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(String evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the extractionPlanDetailId
     */
    public int getExtractionPlanDetailId() {
        return extractionPlanDetailId;
    }

    /**
     * @param extractionPlanDetailId the extractionPlanDetailId to set
     */
    public void setExtractionPlanDetailId(int extractionPlanDetailId) {
        this.extractionPlanDetailId = extractionPlanDetailId;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
