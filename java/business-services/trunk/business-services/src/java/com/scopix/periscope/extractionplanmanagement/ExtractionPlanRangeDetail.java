/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nelson
 */
@Entity
public class ExtractionPlanRangeDetail extends BusinessObject implements Cloneable {

    @ManyToOne
    private ExtractionPlanRange extractionPlanRange;
    @Temporal(value = TemporalType.TIME)
    private Date timeSample;
    @OneToMany(mappedBy = "extractionPlanRangeDetail", fetch = FetchType.LAZY)
    private List<EvidenceRequest> evidenceRequests;

    public ExtractionPlanRange getExtractionPlanRange() {
        return extractionPlanRange;
    }

    public void setExtractionPlanRange(ExtractionPlanRange extractionPlanRange) {
        this.extractionPlanRange = extractionPlanRange;
    }

    public Date getTimeSample() {
        return timeSample;
    }

    public void setTimeSample(Date timeSample) {
        this.timeSample = timeSample;
    }

    public List<EvidenceRequest> getEvidenceRequests() {
        return evidenceRequests;
    }

    public void setEvidenceRequests(List<EvidenceRequest> evidenceRequests) {
        this.evidenceRequests = evidenceRequests;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ExtractionPlanRangeDetail cloned = (ExtractionPlanRangeDetail) super.clone();
        cloned.setId(null);
        cloned.setEvidenceRequests(new ArrayList<EvidenceRequest>());
        return cloned;
    }
}
