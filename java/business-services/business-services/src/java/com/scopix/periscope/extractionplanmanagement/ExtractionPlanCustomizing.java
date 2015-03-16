/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Sensor;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Cesar
 * @version 1.0.0
 */
@Entity
public class ExtractionPlanCustomizing extends BusinessObject implements Comparable<ExtractionPlanCustomizing> {

    @ManyToOne
    private SituationTemplate situationTemplate;
    @ManyToOne
    private Store store;
    @OneToMany(mappedBy = "extractionPlanCustomizing", fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE})
    private List<ExtractionPlanMetric> extractionPlanMetrics;
    @OneToMany(mappedBy = "extractionPlanCustomizing", fetch = FetchType.LAZY)
    @Cascade({CascadeType.SAVE_UPDATE})
    private List<ExtractionPlanRange> extractionPlanRanges;
    /**
     * true means only one evaluation for all evidences false means one evaluation for evidence Multicamara
     */
    private boolean oneEvaluation;
    /**
     * null, means the records are not created true, means the records are created and are active false, means inactive, the
     * records must not considered when extraction plan is created
     */
    private Boolean active;
    @ManyToOne
    private AreaType areaType;
    //revisar definicion
    @ManyToMany(targetEntity = Sensor.class, fetch = FetchType.LAZY)
    /**
     * cascade = {CascadeType.PERSIST, CascadeType.MERGE}
     */
    @Cascade({CascadeType.SAVE_UPDATE})
    @JoinTable(name = "rel_extraction_plan_customizing_sensor",
            joinColumns = {
        @JoinColumn(name = "extraction_plan_customizing_id")},
            inverseJoinColumns = {
        @JoinColumn(name = "sensor_id")})
    private List<Sensor> sensors;
    private Integer priorization;
    private boolean randomCamera;
    /**
     * se agregan para mantener auditabilidad de un epc
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date disabledDate;
    private String creationUser;

    /**
     * @return the situationTemplate
     */
    public SituationTemplate getSituationTemplate() {
        return situationTemplate;
    }

    /**
     * @param situationTemplate the situationTemplate to set
     */
    public void setSituationTemplate(SituationTemplate situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    /**
     * @return the areaType
     */
    public AreaType getAreaType() {
        return areaType;
    }

    /**
     * @param areaType the areaType to set
     */
    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    @Override
    public int compareTo(ExtractionPlanCustomizing o) {
        return this.getId() - o.getId();
    }

    /**
     *
     * @return Boolean determina si esta activo o no un EPC
     */
    public Boolean isActive() {
        return active;
    }

    /**
     *
     * @param active Boolean indica si el epc esta activo o no
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     *
     * @return Store asociado al EPC
     */
    public Store getStore() {
        return store;
    }

    /**
     *
     * @param store Store asociado al epc
     */
    public void setStore(Store store) {
        this.store = store;
    }

    /**
     *
     * @return boolean
     */
    public boolean isOneEvaluation() {
        return oneEvaluation;
    }

    /**
     *
     * @param oneEvaluation boolean para indicar si es multicamara o solo una
     */
    public void setOneEvaluation(boolean oneEvaluation) {
        this.oneEvaluation = oneEvaluation;
    }

    /**
     *
     * @return List<ExtractionPlanMetric> lista de metricas
     */
    public List<ExtractionPlanMetric> getExtractionPlanMetrics() {
        if (extractionPlanMetrics == null) {
            extractionPlanMetrics = new ArrayList<ExtractionPlanMetric>();
        }
        return extractionPlanMetrics;
    }

    /**
     *
     * @param extractionPlanMetrics Lista de metricas para un EPC
     */
    public void setExtractionPlanMetrics(List<ExtractionPlanMetric> extractionPlanMetrics) {        
        this.extractionPlanMetrics = extractionPlanMetrics;
    }

    /**
     *
     * @return List<ExtractionPlanRange> lista de rangos
     */
    public List<ExtractionPlanRange> getExtractionPlanRanges() {
        if (extractionPlanRanges == null) {
            extractionPlanRanges = new ArrayList<ExtractionPlanRange>();
        }
        return extractionPlanRanges;
    }

    /**
     *
     * @param extractionPlanRanges lista de Rangos para un EPC
     */
    public void setExtractionPlanRanges(List<ExtractionPlanRange> extractionPlanRanges) {
        this.extractionPlanRanges = extractionPlanRanges;
    }

    /**
     *
     * @return List<Sensor> lista de sensores asociados
     */
    public List<Sensor> getSensors() {
        if (sensors == null) {
            sensors = new ArrayList<Sensor>();
        }
        return sensors;
    }

    /**
     *
     * @param sensors lista de sensores
     */
    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    /**
     * retorna los rangos para un dia de la semana en particular
     *
     * @param day dia de la semana para el cual se necesitan los rangos
     * @return List<ExtractionPlanRange> rangos de un EPC
     */
    public List<ExtractionPlanRange> getExtractionPlanRangesByDay(Integer day) {
        List<ExtractionPlanRange> ranges = new ArrayList<ExtractionPlanRange>();
        for (ExtractionPlanRange range : getExtractionPlanRanges()) {
            if (range.getDayOfWeek().equals(day)) {
                ranges.add(range);
            }
        }
        return ranges;
    }

    /**
     *
     * @return Integer de priorizacion
     */
    public Integer getPriorization() {
        return priorization;
    }

    /**
     *
     * @param priorization valor para priorizacion
     */
    public void setPriorization(Integer priorization) {
        this.priorization = priorization;
    }

    /**
     *
     * @return boolean indicando si es con random de camara
     */
    public boolean isRandomCamera() {
        return randomCamera;
    }

    /**
     *
     * @param randomCamera valor para random de camara
     */
    public void setRandomCamera(boolean randomCamera) {
        this.randomCamera = randomCamera;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the sendDate
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate the sendDate to set
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * @return the disabledDate
     */
    public Date getDisabledDate() {
        return disabledDate;
    }

    /**
     * @param disabledDate the disabledDate to set
     */
    public void setDisabledDate(Date disabledDate) {
        this.disabledDate = disabledDate;
    }

    /**
     * @return the creationUser
     */
    public String getCreationUser() {
        return creationUser;
    }

    /**
     * @param creationUser the creationUser to set
     */
    public void setCreationUser(String creationUser) {
        this.creationUser = creationUser;
    }
}
