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
 *  StatusSendEPCDTO.java
 * 
 *  Created on 26-11-2010, 11:50:30 AM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

/**
 *
 * @author nelson
 */
public class StatusSendEPCDTO {

    private String message;
    private Integer current;
    private Integer max;
    /**
     * -2 No hay nada corriendo
     * -1 Proceso en ejecucion
     * 0 Termino Exitoso
     * 1 Termino Erroneo
     */
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
