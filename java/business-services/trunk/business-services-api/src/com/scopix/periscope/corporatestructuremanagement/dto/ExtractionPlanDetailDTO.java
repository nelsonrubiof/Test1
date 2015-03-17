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
 * ExtractionPlanDetailDTO.java
 *
 * Created on 27-06-2008, 06:30:36 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marko.perich
 */
public class ExtractionPlanDetailDTO {

    private List<EvidenceRequestDTO> evidenceRequestDTOs;

    public List<EvidenceRequestDTO> getEvidenceRequestDTOs() {
        if (evidenceRequestDTOs == null) {
            evidenceRequestDTOs = new ArrayList<EvidenceRequestDTO>();
        }
        return evidenceRequestDTOs;
    }

    public void setEvidenceRequestDTOs(List<EvidenceRequestDTO> evidenceRequestDTOs) {
        this.evidenceRequestDTOs = evidenceRequestDTOs;
    }
}
