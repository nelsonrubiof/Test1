/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * RequestedVideo.java
 *
 * Created on 13-06-2008, 12:53:59 PM
 *
 */
package com.scopix.periscope.extractionplanmanagement;

import javax.persistence.Entity;

/**
 *
 * @author César Abarza Suazo.
 */
@Entity
public class RequestedVideo extends EvidenceRequest {

    private Integer duration;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
