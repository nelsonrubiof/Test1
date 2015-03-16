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
 * EvidenceRequestVO.java
 *
 * Created on 08-04-2009, 12:59:54 PM
 *
 */


package com.scopix.periscope.extractionmanagement.dto;

import com.scopix.periscope.extractionmanagement.EvidenceProvider;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class EvidenceRequestVO implements Comparable<EvidenceRequestVO>{

    private EvidenceProvider evidenceProvider;

    private List<SituationMetricDTO> situationMetricDTOs;
    
    @Override
    public int compareTo(EvidenceRequestVO o) {
        return this.getEvidenceProvider().getDeviceId() - o.getEvidenceProvider().getDeviceId();
    }

    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    public List<SituationMetricDTO> getSituationMetricDTOs() {
        if (situationMetricDTOs == null) {
            situationMetricDTOs = new ArrayList<SituationMetricDTO>();
        }
        return situationMetricDTOs;
    }

    public void setSituationMetricDTOs(List<SituationMetricDTO> situationMetricDTOs) {
        this.situationMetricDTOs = situationMetricDTOs;
    }
}