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
 * AxisP3301ImageExtractionRequest.java
 *
 * Created on 16-08-2010, 04:06:35 PM
 *
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class ArecontImageExtractionRequest extends EvidenceExtractionRequest {

    @Override
    public Date getExtractionStartTime() {
        //validar como se entrega la imagen al min o despues
        return this.getRequestedTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return false;
    }
}
