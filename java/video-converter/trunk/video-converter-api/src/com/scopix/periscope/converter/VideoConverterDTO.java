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
 * VideoConverterDTO.java
 * 
 * Created on 20-08-2013, 12:44:00 PM
 */
package com.scopix.periscope.converter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Objeto para comunicación del servicio
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@XmlRootElement(name = "videoConverterDTO")
public class VideoConverterDTO {

    private String fileName;
    private Integer evidenceFileId;
    private String urlNotificacion;
    private String waitForConverter;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public Integer getEvidenceFileId() {
        return evidenceFileId;
    }

    public void setEvidenceFileId(Integer evidenceFileId) {
        this.evidenceFileId = evidenceFileId;
    }

    /**
     * @return the waitForConverter
     */
    public String getWaitForConverter() {
        return waitForConverter;
    }

    /**
     * @param waitForConverter the waitForConverter to set
     */
    public void setWaitForConverter(String waitForConverter) {
        this.waitForConverter = waitForConverter;
    }
}