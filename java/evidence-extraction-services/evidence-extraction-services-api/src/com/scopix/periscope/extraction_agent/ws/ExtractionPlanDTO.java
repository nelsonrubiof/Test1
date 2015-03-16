/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * ExtractioPlanDTO.java
 *
 * Created on 15 de junio de 2007, 15:59
 *
 */

package com.scopix.periscope.extraction_agent.ws;

/**
 *
 * @author marko.perich
 * @version 1.0.0
 */
public class ExtractionPlanDTO {
    
    public static final String UPLOAD_TYPE_BATCH = "BATCH";
    private static final String UPLOAD_TYPE_REALTIME = "REALTIME";
    
    private int extractionPlanID; // extraction plan ID
    private int reqID; // requirement ID
    private int deviceID; // device ID
    private int sampleStartHour;
    private int sampleStartMinute;
    private int sampleStartSecond;
    private int sampleLengthSecs;
    private String uploadType;
    private int maxUploadDelaySecs;
    
    /** Creates a new instance of ExtractioPlanDTO */
    public ExtractionPlanDTO() {
    }

    public int getExtractionPlanID() {
        return extractionPlanID;
    }

    public void setExtractionPlanID(int epID) {
        this.extractionPlanID = epID;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public int getSampleStartHour() {
        return sampleStartHour;
    }

    public void setSampleStartHour(int sampleStartHour) {
        this.sampleStartHour = sampleStartHour;
    }

    public int getSampleStartMinute() {
        return sampleStartMinute;
    }

    public void setSampleStartMinute(int sampleStartMinute) {
        this.sampleStartMinute = sampleStartMinute;
    }

    public int getSampleStartSecond() {
        return sampleStartSecond;
    }

    public void setSampleStartSecond(int sampleStartSecond) {
        this.sampleStartSecond = sampleStartSecond;
    }

    public int getSampleLengthSecs() {
        return sampleLengthSecs;
    }

    public void setSampleLengthSecs(int sampleLengthSecs) {
        this.sampleLengthSecs = sampleLengthSecs;
    }

    public int getMaxUploadDelaySecs() {
        return maxUploadDelaySecs;
    }

    public void setMaxUploadDelaySecs(int maxUploadDelaySecs) {
        this.maxUploadDelaySecs = maxUploadDelaySecs;
    }

}
