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
 * AutomaticEvaluationResult.java
 *
 * Created on 10-04-2014, 02:28:16 AM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.io.Serializable;
import java.text.ParseException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author EmO
 */
@Entity
public class AutomaticEvaluationResult extends BusinessObject implements Comparable<AutomaticEvaluationResult>, Serializable {

    private static final long serialVersionUID = 2407672388063355817L;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp = new Date();
    private Boolean result;
    private long requestDuration;
    private String evaluationPath;
    @OneToOne(fetch = FetchType.LAZY)
    private Evidence evidence;
    @ManyToOne(fetch = FetchType.LAZY)
    private SituationTemplate situationTemplate;
    @OneToOne(fetch = FetchType.LAZY)
    private ObservedMetric observedMetric;
    private Integer pendingEvaluationId;

    public AutomaticEvaluationResult() {
    }

    public AutomaticEvaluationResult(Boolean result, long requestDuration, String evaluationPath, Evidence evidence,
        ObservedMetric observedMetric, SituationTemplate situationTemplate, Integer pendingEvaluationId) {
        this.result = result;
        this.requestDuration = requestDuration;
        this.evaluationPath = evaluationPath;
        this.evidence = evidence;
        this.observedMetric = observedMetric;
        this.situationTemplate = situationTemplate;
        this.pendingEvaluationId = pendingEvaluationId;

    }

    @Override
    public int compareTo(AutomaticEvaluationResult o) {
        return this.getId() - o.getId();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public long getRequestDuration() {
        return requestDuration;
    }

    public void setRequestDuration(long requestDuration) {
        this.requestDuration = requestDuration;
    }

    public String getEvaluationPath() {
        return evaluationPath;
    }

    public void setEvaluationPath(String evaluationPath) {
        this.evaluationPath = evaluationPath;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public ObservedMetric getObservedMetric() {
        return observedMetric;
    }

    public void setObservedMetric(ObservedMetric observedMetric) {
        this.observedMetric = observedMetric;
    }

    public Integer getPendingEvaluationId() {
        return pendingEvaluationId;
    }

    public void setPendingEvaluationId(Integer pendingEvaluationId) {
        this.pendingEvaluationId = pendingEvaluationId;
    }

    public SituationTemplate getSituationTemplate() {
        return situationTemplate;
    }

    public void setSituationTemplate(SituationTemplate situationTemplate) {
        this.situationTemplate = situationTemplate;
    }

    public AutomaticEvaluationResultDTO copyToDto() {
        AutomaticEvaluationResultDTO dto = new AutomaticEvaluationResultDTO();
        dto.setTimestamp(DateFormatUtils.format(this.timestamp, "yyyy-MM-dd HH:mm:ss"));
        dto.setResult(this.result);
        dto.setRequestDuration(this.requestDuration);
        dto.setEvaluationPath(this.evaluationPath);
        dto.setEvidenceId(this.getEvidence().getId());
        dto.setSituationTemplateId(this.getSituationTemplate().getId());
        dto.setObservedMetricId(this.getObservedMetric().getId());
        dto.setPendingEvaluationId(this.pendingEvaluationId);
        return dto;
    }

    public void copyFromDto(AutomaticEvaluationResultDTO dto) {
        try {
            setTimestamp(DateUtils.parseDate(dto.getTimestamp(), new String[]{"yyyy-MM-dd HH:mm:ss"}));
        } catch (ParseException e) {
            setTimestamp(new Date());
        }
        setResult(dto.getResult());
        setRequestDuration(dto.getRequestDuration());
        setEvaluationPath(dto.getEvaluationPath());
        Evidence e = new Evidence();
        e.setId(dto.getEvidenceId());
        setEvidence(e);

        SituationTemplate st = new SituationTemplate();
        st.setId(dto.getSituationTemplateId());
        setSituationTemplate(st);

        ObservedMetric om = new ObservedMetric();
        om.setId(dto.getObservedMetricId());
        setObservedMetric(om);

        setPendingEvaluationId(dto.getPendingEvaluationId());
    }

}
