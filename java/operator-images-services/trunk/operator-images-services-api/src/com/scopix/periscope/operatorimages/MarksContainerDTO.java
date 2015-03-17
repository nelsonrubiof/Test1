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
@XmlRootElement(name = "marksContainer")
public class MarksContainerDTO {

    private Long sessionId;
    private Integer situationId;
    private List<MarksDTO> marks;

    /**
     * @return the marks
     */
    public List<MarksDTO> getMarks() {
        return marks;
    }

    /**
     * @param marks the marks to set
     */
    public void setMarks(List<MarksDTO> marks) {
        this.marks = marks;
    }

    public Integer getSituationId() {
        return situationId;
    }

    public void setSituationId(Integer situationId) {
        this.situationId = situationId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}