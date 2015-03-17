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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author nelson
 */
@Entity
public class ExtractionPlanRange extends BusinessObject implements Cloneable {

    @ManyToOne
    private ExtractionPlanCustomizing extractionPlanCustomizing;
    @Temporal(TemporalType.TIME)
    private Date initialTime;
    @Temporal(TemporalType.TIME)
    private Date endTime;
    private Integer samples;
    private Integer frecuency;  //In minutes
    private Integer duration; //In seconds
    /**
     * de 1 a 7 donde 1 = domingo
     */
    private Integer dayOfWeek;
    /**
     * se define @OneToMany como relacion con la lista extractionPlanRangeDetails
     * mappedBy = "extractionPlanRange" que esta mapeado en extractionPlanRange
     * se se carga lazy solo cuando es llamada
     */
    @OneToMany(mappedBy = "extractionPlanRange", fetch = FetchType.LAZY)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<ExtractionPlanRangeDetail> extractionPlanRangeDetails;
    /**
     * determina si las muestras detalle deben generarse en forma aleatorias o no
     */
    @Enumerated(EnumType.STRING)
    private ExtractionPlanRangeType extractionPlanRangeType;

    public ExtractionPlanCustomizing getExtractionPlanCustomizing() {
        return extractionPlanCustomizing;
    }

    public void setExtractionPlanCustomizing(ExtractionPlanCustomizing extractionPlanCustomizing) {
        this.extractionPlanCustomizing = extractionPlanCustomizing;
    }

    public Date getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(Date initialTime) {
        this.initialTime = initialTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getSamples() {
        return samples;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public Integer getFrecuency() {
        return frecuency;
    }

    public void setFrecuency(Integer frecuency) {
        this.frecuency = frecuency;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ExtractionPlanRangeDetail> getExtractionPlanRangeDetails() {
        if (extractionPlanRangeDetails == null) {
            extractionPlanRangeDetails = new ArrayList<ExtractionPlanRangeDetail>();
        }
        return extractionPlanRangeDetails;
    }

    public void setExtractionPlanRangeDetails(List<ExtractionPlanRangeDetail> extractionPlanRangeDetails) {
        this.extractionPlanRangeDetails = extractionPlanRangeDetails;
    }

    public ExtractionPlanRangeType getExtractionPlanRangeType() {
        return extractionPlanRangeType;
    }

    public void setExtractionPlanRangeType(ExtractionPlanRangeType extractionPlanRangeType) {
        this.extractionPlanRangeType = extractionPlanRangeType;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ExtractionPlanRange cloned = (ExtractionPlanRange) super.clone();
        cloned.setId(null);
        cloned.setExtractionPlanRangeDetails(new ArrayList<ExtractionPlanRangeDetail>());
        for (ExtractionPlanRangeDetail detail : this.getExtractionPlanRangeDetails()) {
            ExtractionPlanRangeDetail detailCloned = (ExtractionPlanRangeDetail) detail.clone();
            detailCloned.setExtractionPlanRange(cloned);
            cloned.getExtractionPlanRangeDetails().add(detailCloned);
        }
        return cloned;
    }
}
