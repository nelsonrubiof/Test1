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
 *  QualityEvaluationDTO.java
 * 
 *  Created on 22-11-2011, 05:25:07 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.dto;

/**
 *
 * @author nelson
 */
public class QualityEvaluationDTO {

    private String user;
    private String result;
    private String fechaRevision;
    private Integer motivoRechazo;
    private Integer clasificacion;
    private String operador;
    private String fechaEvidencia;
    private String observaciones;
    private Integer observedSituationId;
    private String messageOperator;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(String fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Integer getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(Integer motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Integer getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Integer clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public String getFechaEvidencia() {
        return fechaEvidencia;
    }

    public void setFechaEvidencia(String fechaEvidencia) {
        this.fechaEvidencia = fechaEvidencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getMessageOperator() {
        return messageOperator;
    }

    public void setMessageOperator(String messageOperator) {
        this.messageOperator = messageOperator;
    }

    public Integer getObservedSituationId() {
        return observedSituationId;
    }

    public void setObservedSituationId(Integer observedSituationId) {
        this.observedSituationId = observedSituationId;
    }


}
