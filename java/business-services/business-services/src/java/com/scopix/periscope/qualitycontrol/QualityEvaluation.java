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
 *  QualityEvaluation.java
 * 
 *  Created on 14-12-2011, 03:22:44 PM
 * 
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nelson
 */
@Entity
public class QualityEvaluation extends BusinessObject {

    private String evaluationUser;
    @Enumerated(EnumType.STRING)
    private QualityEvaluationType qualityEvaluationType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRevision;
    @OneToOne
    private MotivoRejected motivoRechazo;
    @OneToOne
    private Clasificacion clasificacion;
    private String operador;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEvidencia;
    @Lob
    private String observaciones;
    @OneToOne
    private ObservedSituation observedSituation;
    @Lob
    private String messageOperator;

    public String getEvaluationUser() {
        return evaluationUser;
    }

    public void setEvaluationUser(String evaluationUser) {
        this.evaluationUser = evaluationUser;
    }

    public QualityEvaluationType getQualityEvaluationType() {
        return qualityEvaluationType;
    }

    public void setQualityEvaluationType(QualityEvaluationType qualityEvaluationType) {
        this.qualityEvaluationType = qualityEvaluationType;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public MotivoRejected getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(MotivoRejected motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public Clasificacion getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Clasificacion clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Date getFechaEvidencia() {
        return fechaEvidencia;
    }

    public void setFechaEvidencia(Date fechaEvidencia) {
        this.fechaEvidencia = fechaEvidencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public ObservedSituation getObservedSituation() {
        return observedSituation;
    }

    public void setObservedSituation(ObservedSituation observedSituation) {
        this.observedSituation = observedSituation;
    }

    public String getMessageOperator() {
        return messageOperator;
    }

    public void setMessageOperator(String messageOperator) {
        this.messageOperator = messageOperator;
    }
}
