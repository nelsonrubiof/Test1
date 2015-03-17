/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@XmlRootElement(name = "marks")
public class Marks {

    private Double elapsedTime;
    private String pathOrigen;
    private String pathDestino;
    private Integer metricId;
    private Integer evidenceId;
    private Date evidenceDate;
    private Integer result;
    private String corporateName;
    private String storeName;
    private String areaName;
    private Integer situationId;
    private Integer cameraId;
    private String cameraName;
    private String metricName;
    private List<Shapes> circles;
    private List<Shapes> squares;
    private Boolean withNumber;

    /**
     * @return the circles
     */
    public List<Shapes> getCircles() {
        if (circles == null) {
            circles = new ArrayList<Shapes>();
        }
        return circles;
    }

    /**
     * @param circles the circles to set
     */
    public void setCircles(List<Shapes> circles) {
        this.circles = circles;
    }

    /**
     * @return the squares
     */
    public List<Shapes> getSquares() {
        if (squares == null) {
            squares = new ArrayList<Shapes>();
        }
        return squares;
    }

    /**
     * @param squares the squares to set
     */
    public void setSquares(List<Shapes> squares) {
        this.squares = squares;
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
     * @return the evidenceDate
     */
    public Date getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }

    /**
     * @return the pathOrigen
     */
    public String getPathOrigen() {
        return pathOrigen;
    }

    /**
     * @param pathOrigen the pathOrigen to set
     */
    public void setPathOrigen(String pathOrigen) {
        this.pathOrigen = pathOrigen;
    }

    /**
     * @return the pathDestino
     */
    public String getPathDestino() {
        return pathDestino;
    }

    /**
     * @param pathDestino the pathDestino to set
     */
    public void setPathDestino(String pathDestino) {
        this.pathDestino = pathDestino;
    }

    /**
     * @return the elapsedTime
     */
    public Double getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @param elapsedTime the elapsedTime to set
     */
    public void setElapsedTime(Double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * @return the result
     */
    public Integer getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * @return the withNumber
     */
    public Boolean getWithNumber() {
        return withNumber;
    }

    /**
     * @param withNumber the withNumber to set
     */
    public void setWithNumber(Boolean withNumber) {
        this.withNumber = withNumber;
    }

    /**
     * @return the corporateName
     */
    public String getCorporateName() {
        return corporateName;
    }

    /**
     * @param corporateName the corporateName to set
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
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
     * @return the situationId
     */
    public Integer getSituationId() {
        return situationId;
    }

    /**
     * @param situationId the situationId to set
     */
    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    /**
     * @return the cameraId
     */
    public Integer getCameraId() {
        return cameraId;
    }

    /**
     * @param cameraId the cameraId to set
     */
    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
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
     * @return the cameraName
     */
    public String getCameraName() {
        return cameraName;
    }

    /**
     * @param cameraName the cameraName to set
     */
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    /**
     * @return the metricName
     */
    public String getMetricName() {
        return metricName;
    }

    /**
     * @param metricName the metricName to set
     */
    public void setMetricName(String metricName) {
        this.metricName = metricName;
    }
}