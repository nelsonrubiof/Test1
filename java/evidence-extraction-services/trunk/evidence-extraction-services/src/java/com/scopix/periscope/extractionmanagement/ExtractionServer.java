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
 * ExtractionServer.java
 *
 * Created on 02-07-2008, 04:43:25 AM
 *
 */
package com.scopix.periscope.extractionmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author marko.perich
 */
@Entity
public class ExtractionServer extends BusinessObject {

    @OneToMany(mappedBy = "extractionServer", fetch = FetchType.LAZY)
    private List<EvidenceProvider> evidenceProviders;

    @OneToOne(mappedBy = "extractionServer")
    private ExtractionPlan extractionPlan;

    private Integer serverId;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public ExtractionPlan getExtractionPlan() {
        return extractionPlan;
    }

    public void setExtractionPlan(ExtractionPlan extractionPlan) {
        this.extractionPlan = extractionPlan;
    }

    public List<EvidenceProvider> getEvidenceProviders() {
        return evidenceProviders;
    }

    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
    }
}
