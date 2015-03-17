/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author nelson
 * @version 1.0.0
 */
@XmlRootElement(name = "resultMarks")
public class ResultMarks {

    private Integer evidenceId;
    private Integer metricId;
    private String fileName;
    private String processed = "N";

    /**
     * @return the evidenceId
     */
    public Integer getEvidenceId() {
        return evidenceId;
    }

    /**
     * @param evidenceId the evidenceId to set
     */
    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    /**
     * @return the metricId
     */
    public Integer getMetricId() {
        return metricId;
    }

    /**
     * @param metricId the metricId to set
     */
    public void setMetricId(Integer metricId) {
        this.metricId = metricId;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the processed
     */
    public String getProcessed() {
        return processed;
    }

    /**
     * @param processed the processed to set
     */
    public void setProcessed(String processed) {
        this.processed = processed;
    }
}
