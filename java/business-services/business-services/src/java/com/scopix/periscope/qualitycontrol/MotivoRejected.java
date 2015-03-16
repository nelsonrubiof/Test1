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
 *  MotivoRejected.java
 * 
 *  Created on 13-12-2011, 05:04:22 PM
 * 
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 *
 * @author nelson
 */
@Entity
public class MotivoRejected extends BusinessObject {

    private String description;
    @ManyToMany(targetEntity = Clasificacion.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "rel_motivo_rejected_clasificacion", joinColumns = {
        @JoinColumn(name = "motivo_rejected_id")},
    inverseJoinColumns = {
        @JoinColumn(name = "clasificacion_id")})
    private List<Clasificacion> clasificacions;


    public List<Clasificacion> getClasificacions() {
        if (clasificacions == null) {
            clasificacions = new ArrayList<Clasificacion>();
        }
        return clasificacions;
    }

    public void setClasificacions(List<Clasificacion> clasificacions) {
        this.clasificacions = clasificacions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
