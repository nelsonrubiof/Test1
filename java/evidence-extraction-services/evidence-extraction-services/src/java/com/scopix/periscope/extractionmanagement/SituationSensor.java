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
 * EvidenceRequest.java
 *
 * Created on 16-06-2008, 05:34:27 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * This class represents a Evidence request from business services
 * 
 * @author marko.perich
 */
@Entity
public class SituationSensor extends BusinessObject implements Comparable<SituationSensor> {

    private Integer sensorId;
    private String name;
    private String description;
    @ManyToOne
    private SituationRequest situationRequest;

    public int compareTo(SituationSensor o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the sensorId
     */
    public Integer getSensorId() {
        return sensorId;
    }

    /**
     * @param sensorId the sensorId to set
     */
    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the situationRequest
     */
    public SituationRequest getSituationRequest() {
        return situationRequest;
    }

    /**
     * @param situationRequest the situationRequest to set
     */
    public void setSituationRequest(SituationRequest situationRequest) {
        this.situationRequest = situationRequest;
    }
}
