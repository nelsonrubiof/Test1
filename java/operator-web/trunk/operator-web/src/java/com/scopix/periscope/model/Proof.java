package com.scopix.periscope.model;

import java.io.Serializable;

import com.scopix.periscope.operatorimages.MarksDTO;

/**
 * Clase para referenciar datos de los proofs de la aplicación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
public class Proof implements Serializable {

    private Integer result;
    private MarksDTO markDTO;
    private Integer metricId;
    private Integer evidenceId;
    private String pathWithMarks;
    private String pathWithOutMarks;
    private boolean isInicial = false;
    private static final long serialVersionUID = -6079667480138269723L;

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
     * @return the pathWithMarks
     */
    public String getPathWithMarks() {
        return pathWithMarks;
    }

    /**
     * @param pathWithMarks the pathWithMarks to set
     */
    public void setPathWithMarks(String pathWithMarks) {
        this.pathWithMarks = pathWithMarks;
    }

    /**
     * @return the pathWithOutMarks
     */
    public String getPathWithOutMarks() {
        return pathWithOutMarks;
    }

    /**
     * @param pathWithOutMarks the pathWithOutMarks to set
     */
    public void setPathWithOutMarks(String pathWithOutMarks) {
        this.pathWithOutMarks = pathWithOutMarks;
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
     * @return the isInicial
     */
    public boolean isIsInicial() {
        return isInicial;
    }

    /**
     * @param isInicial the isInicial to set
     */
    public void setIsInicial(boolean isInicial) {
        this.isInicial = isInicial;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * @return the markDTO
     */
    public MarksDTO getMarkDTO() {
        return markDTO;
    }

    /**
     * @param markDTO the markDTO to set
     */
    public void setMarkDTO(MarksDTO markDTO) {
        this.markDTO = markDTO;
    }
}