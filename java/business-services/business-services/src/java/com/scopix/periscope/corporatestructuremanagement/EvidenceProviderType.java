/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceProviderType.java
 *
 * Created on 27-03-2008, 02:56:35 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class EvidenceProviderType extends BusinessObject implements Comparable<EvidenceProviderType>{

    @Lob
    private String description;

    @OneToMany(mappedBy = "evidenceProviderType", fetch = FetchType.LAZY)
    private List<EvidenceProvider> evidenceProvider;
    
    @Lob
    private String pattern;

    private boolean automatic;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EvidenceProvider> getEvidenceProvider() {
        if (evidenceProvider == null) {
            evidenceProvider = new ArrayList<EvidenceProvider>();
        }
        return evidenceProvider;
    }

    public void setEvidenceProvider(List<EvidenceProvider> evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public int compareTo(EvidenceProviderType o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the automatic
     */
    public boolean isAutomatic() {
        return automatic;
    }

    /**
     * @param automatic the automatic to set
     */
    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }
}
