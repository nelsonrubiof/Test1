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
 *  SensorAndEvidenceExtractionServicesServerDTO.java
 * 
 *  Created on 20-09-2012, 05:00:08 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

/**
 *
 * @author Gustavo Alvarez
 */
public class SensorAndEvidenceExtractionServicesServerDTO {
    private String sensorName;
    private String storeName;
    private EvidenceExtractionServicesServerDTO evidenceExtractionServicesServerDTO; 

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public EvidenceExtractionServicesServerDTO getEvidenceExtractionServicesServerDTO() {
        return evidenceExtractionServicesServerDTO;
    }

    public void setEvidenceExtractionServicesServerDTO(EvidenceExtractionServicesServerDTO evidenceExtractionServicesServerDTO) {
        this.evidenceExtractionServicesServerDTO = evidenceExtractionServicesServerDTO;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
