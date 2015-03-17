/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * Video.java
 * 
 * Created on 14-08-2013, 12:45:24 PM
 */
package com.scopix.periscope.converter;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.periscopefoundation.BusinessObject;

/**
 * Clase que representa la tabla en donde se almacenarán los registros de conversión de videos
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@Entity
@SuppressWarnings("serial")
public class Video extends BusinessObject {

    private String estado;

    private String pathOriginal;

    private String pathConvertido;

    private Integer evidenceFileId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSolicitud;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConversion;

    private String urlNotificacion;

    @Lob
    private String mensajeError;

    /**
     * @return the pathOriginal
     */
    public String getPathOriginal() {
        return pathOriginal;
    }

    /**
     * @param pathOriginal the pathOriginal to set
     */
    public void setPathOriginal(String pathOriginal) {
        this.pathOriginal = pathOriginal;
    }

    /**
     * @return the pathConvertido
     */
    public String getPathConvertido() {
        return pathConvertido;
    }

    /**
     * @param pathConvertido the pathConvertido to set
     */
    public void setPathConvertido(String pathConvertido) {
        this.pathConvertido = pathConvertido;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the fechaSolicitud
     */
    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * @param fechaSolicitud the fechaSolicitud to set
     */
    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    /**
     * @return the fechaConversion
     */
    public Date getFechaConversion() {
        return fechaConversion;
    }

    /**
     * @param fechaConversion the fechaConversion to set
     */
    public void setFechaConversion(Date fechaConversion) {
        this.fechaConversion = fechaConversion;
    }

    /**
     * @return the urlNotificacion
     */
    public String getUrlNotificacion() {
        return urlNotificacion;
    }

    /**
     * @param urlNotificacion the urlNotificacion to set
     */
    public void setUrlNotificacion(String urlNotificacion) {
        this.urlNotificacion = urlNotificacion;
    }

    /**
     * @return the mensajeError
     */
    public String getMensajeError() {
        return mensajeError;
    }

    /**
     * @param mensajeError the mensajeError to set
     */
    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Integer getEvidenceFileId() {
        return evidenceFileId;
    }

    public void setEvidenceFileId(Integer evidenceFileId) {
        this.evidenceFileId = evidenceFileId;
    }
}