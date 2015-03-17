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
 * Product.java
 *
 * Created on 20-08-2008, 03:48:10 PM
 *
 */
package com.scopix.periscope.templatemanagement;

import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import org.hibernate.validator.NotNull;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Product extends BusinessObject {

    @NotNull
    @Lob
    private String name;
    @Lob
    private String description;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<SituationTemplate> situationTemplates;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<IndicatorProductAndAreaType> ipaats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SituationTemplate> getSituationTemplates() {
        return situationTemplates;
    }

    public void setSituationTemplates(List<SituationTemplate> situationTemplates) {
        this.situationTemplates = situationTemplates;
    }

    /**
     * @return the ipaats
     */
    public List<IndicatorProductAndAreaType> getIpaats() {
        if (ipaats == null) {
            ipaats = new ArrayList<IndicatorProductAndAreaType>();
        }
        return ipaats;
    }

    /**
     * @param ipaats the ipaats to set
     */
    public void setIpaats(List<IndicatorProductAndAreaType> ipaats) {
        this.ipaats = ipaats;
    }
}
