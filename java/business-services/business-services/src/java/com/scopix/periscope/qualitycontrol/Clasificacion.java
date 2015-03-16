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
 *  Clasificacion.java
 * 
 *  Created on 13-12-2011, 05:05:32 PM
 * 
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

/**
 *
 * @author nelson
 */
@Entity
public class Clasificacion extends BusinessObject {

    private String description;
    @ManyToMany(targetEntity = MotivoRejected.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE}, mappedBy = "clasificacions")
    private List<MotivoRejected> motivoRejecteds;

    

    public List<MotivoRejected> getMotivoRejecteds() {
        if (motivoRejecteds == null) {
            motivoRejecteds = new ArrayList<MotivoRejected>();
        }
        return motivoRejecteds;
    }

    public void setMotivoRejecteds(List<MotivoRejected> motivoRejecteds) {
        this.motivoRejecteds = motivoRejecteds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
