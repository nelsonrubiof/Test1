/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  EvidenceTransmStrategyFEContainerDTO.java
 * 
 *  Created on Jul 30, 2014, 11:57:16 AM
 * 
 */
package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@XmlRootElement(name = "EvidenceTransmStrategyFEContainerDTO")
public class EvidenceTransmStrategyFEContainerDTO extends RestResponseDTO{

    private List<EvidenceTransmStrategyFEDTO> evidenceTransmissionStrategies;

    /**
     * @return the evidenceTransmissionStrategies
     */
    public List<EvidenceTransmStrategyFEDTO> getEvidenceTransmissionStrategies() {
        return evidenceTransmissionStrategies;
    }

    /**
     * @param evidenceTransmissionStrategies the evidenceTransmissionStrategies
     * to set
     */
    public void setEvidenceTransmissionStrategies(List<EvidenceTransmStrategyFEDTO> evidenceTransmissionStrategies) {
        this.evidenceTransmissionStrategies = evidenceTransmissionStrategies;
    }
}
