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
 *  Process.java
 * 
 *  Created on 23-10-2012, 05:31:05 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import javax.persistence.Entity;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.ManyToOne;

/**
 * Clase
 *
 * @version 1.0.0
 * @author nelson
 */
@Entity
public class Process extends BusinessObject {

    @ManyToOne
    private EvidenceExtractionServicesServer eess;
    private Integer processIdEs;

    /**
     * @return the eess
     */
    public EvidenceExtractionServicesServer getEess() {
        return eess;
    }

    /**
     * @param eess the eess to set
     */
    public void setEess(EvidenceExtractionServicesServer eess) {
        this.eess = eess;
    }

    /**
     * @return the processIdEs
     */
    public Integer getProcessIdEs() {
        return processIdEs;
    }

    /**
     * @param processIdEs the processIdEs to set
     */
    public void setProcessIdEs(Integer processIdEs) {
        this.processIdEs = processIdEs;
    }
    
}
