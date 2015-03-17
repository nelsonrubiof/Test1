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
 *  VadaroImageExtractionRequest.java
 * 
 *  Created on 09-07-2014, 04:54:58 PM
 * 
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Date;
import javax.persistence.Entity;
import org.apache.log4j.Logger;

/**
 *
 * @author Nelson
 */
@Entity
public class VadaroXmlExtractionRequest extends EvidenceExtractionRequest {

    @Override
    public Date getExtractionStartTime() {
        return this.getRequestedTime();
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return false;
    }

}
