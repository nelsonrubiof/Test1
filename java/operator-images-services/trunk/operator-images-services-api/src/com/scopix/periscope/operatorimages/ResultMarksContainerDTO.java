/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.operatorimages;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@XmlRootElement(name = "resultMarksContainer")
public class ResultMarksContainerDTO {

    private Boolean error;
    private Long sessionId;
    private String errorMsg;
    private Integer situationId;
    private List<ResultMarksDTO> results;

    /**
     * @return the results
     */
    public List<ResultMarksDTO> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(List<ResultMarksDTO> results) {
        this.results = results;
    }

    public Integer getSituationId() {
        return situationId;
    }

    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}