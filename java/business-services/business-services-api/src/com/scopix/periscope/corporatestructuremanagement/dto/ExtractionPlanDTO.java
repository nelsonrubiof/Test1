package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.ArrayList;
import java.util.List;

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
 * ExtractionPlanDTO.java
 *
 * Created on 18-06-2008, 03:09:31 AM
 *
 */
/**
 *
 * @author marko.perich
 */
public class ExtractionPlanDTO {

    private String providerTypeDescription;
    private String storeName;
    private Integer storeId;
    private String timeZone;
    private List<ExtractionPlanDetailDTO> extractionPlanDetails;
    private List<SituationRequestDTO> situationRequestDTOs;
    private List<StoreTimeDTO> storeTimeDTOs;

    public List<ExtractionPlanDetailDTO> getExtractionPlanDetails() {
        if (extractionPlanDetails == null){
            extractionPlanDetails = new ArrayList<ExtractionPlanDetailDTO>();
        }
        return extractionPlanDetails;
    }

    public void setExtractionPlanDetails(List<ExtractionPlanDetailDTO> extractionPlanDetails) {
        this.extractionPlanDetails = extractionPlanDetails;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProviderTypeDescription() {
        return providerTypeDescription;
    }

    public void setProviderTypeDescription(String providerTypeDescription) {
        this.providerTypeDescription = providerTypeDescription;
    }

    public List<SituationRequestDTO> getSituationRequestDTOs() {
        if (situationRequestDTOs == null) {
            situationRequestDTOs = new ArrayList<SituationRequestDTO>();
        }
        return situationRequestDTOs;
    }

    public void setSituationRequestDTOs(List<SituationRequestDTO> situationRequestDTOs) {
        this.situationRequestDTOs = situationRequestDTOs;
    }

    /**
     * @return the storeTimeDTOs
     */
    public List<StoreTimeDTO> getStoreTimeDTOs() {
        if (storeTimeDTOs == null) {
            storeTimeDTOs = new ArrayList<StoreTimeDTO>();
        }
        return storeTimeDTOs;
    }

    /**
     * @param storeTimeDTOs the storeTimeDTOs to set
     */
    public void setStoreTimeDTOs(List<StoreTimeDTO> storeTimeDTOs) {
        this.storeTimeDTOs = storeTimeDTOs;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
