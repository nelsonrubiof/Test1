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
 * AreaType.java
 *
 * Created on 25-04-2008, 07:04:11 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.queuemanagement.OperatorQueueDetail;
import com.scopix.periscope.templatemanagement.SituationTemplate;
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
public class AreaType extends BusinessObject implements Comparable<AreaType> {

    @Lob
    private String name;
    @Lob
    private String description;
    @OneToMany(mappedBy = "areaType", fetch = FetchType.LAZY)
    private List<Area> areas;
    @OneToMany(mappedBy = "areaType", fetch = FetchType.LAZY)
    private List<IndicatorProductAndAreaType> ipaats;
    @OneToMany(mappedBy = "areaType", fetch = FetchType.LAZY)
    private List<SituationTemplate> situationTemplates;

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

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public int compareTo(AreaType o) {
        return this.getId() - o.getId();
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

//    public List<OperatorQueueDetail> getOperatorQueueDetails() {
//        return operatorQueueDetails;
//    }
//
//    public void setOperatorQueueDetails(List<OperatorQueueDetail> operatorQueueDetails) {
//        this.operatorQueueDetails = operatorQueueDetails;
//    }
}
