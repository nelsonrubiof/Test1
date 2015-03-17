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
 *  Cisco3520F121_73ImageExtractionRequest.java
 * 
 *  Created on 03-06-2013, 11:36:23 AM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@Entity
public class Cisco3520F_1_2_1ImageExtractionRequest extends EvidenceExtractionRequest {

    /**
     *
     * @return Date fecha y hora de la solicitud
     */
    @Override
    public Date getExtractionStartTime() {
        return this.getRequestedTime();
    }

    /**
     *
     * @return boolean determina si se puede solicitar evidencia pasada para el tipo de provider
     */
    @Override
    public boolean getAllowsExtractionPlanToPast() {
        //al ser imagen no se puede pedir para el pasado
        return false;
    }
}
