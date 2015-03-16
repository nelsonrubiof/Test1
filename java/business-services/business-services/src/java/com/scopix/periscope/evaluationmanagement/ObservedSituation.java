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
 * ObservedSituation.java
 *
 * Created on 27-03-2008, 02:38:40 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.businesswarehouse.transfer.commands.RejectBWCommand;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.queuemanager.Prioritization;
import com.scopix.periscope.periscopefoundation.states.clients.Stateful;
import com.scopix.periscope.periscopefoundation.states.clients.StatefulConfiguration;
import com.scopix.periscope.periscopefoundation.states.configs.StateTransition;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo
 */
@Entity
@StatefulConfiguration(configuratorClass = QueuedObjectStateTransition.class)
public class ObservedSituation extends BusinessObject implements Comparable<ObservedSituation>, Prioritization,
        Stateful<EvaluationState> {

    private static Logger log = Logger.getLogger(ObservedSituation.class);

    @OneToMany(mappedBy = "observedSituation", fetch = FetchType.LAZY)
    private List<ObservedSituationEvaluation> observedSituationEvaluations;
    @OneToMany(mappedBy = "observedSituation", fetch = FetchType.EAGER)
    private List<ObservedMetric> observedMetrics;
    @ManyToOne
    private Situation situation;
    @OneToOne(mappedBy = "observedSituation")
    @PrimaryKeyJoinColumn
    private PendingEvaluation pendingEvaluation;
    @Temporal(TemporalType.DATE)
    private Date observedSituationDate;
    @Enumerated(EnumType.STRING)
    private EvaluationState evaluationState;
    @OneToMany(mappedBy = "observedSituation", fetch = FetchType.LAZY)
    private List<RulesLog> rulesLogs;
    @OneToMany(mappedBy = "observedSituation", fetch = FetchType.LAZY)
    private List<IndicatorValues> indicatorValues;

    @Temporal(TemporalType.TIMESTAMP)
    private Date evidenceDate;

    public List<ObservedMetric> getObservedMetrics() {
        if (observedMetrics == null) {
            observedMetrics = new ArrayList<ObservedMetric>();
        }
        return observedMetrics;
    }

    public void setObservedMetrics(List<ObservedMetric> observedMetrics) {
        this.observedMetrics = observedMetrics;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public List<ObservedSituationEvaluation> getObservedSituationEvaluations() {
        if (observedSituationEvaluations == null) {
            observedSituationEvaluations = new ArrayList<ObservedSituationEvaluation>();
        }
        return observedSituationEvaluations;
    }

    public void setObservedSituationEvaluations(List<ObservedSituationEvaluation> observedSituationEvaluations) {
        this.observedSituationEvaluations = observedSituationEvaluations;
    }

    public Date getObservedSituationDate() {
        return observedSituationDate;
    }

    public void setObservedSituationDate(Date observedSituationDate) {
        this.observedSituationDate = observedSituationDate;
    }

    public int compareTo(ObservedSituation o) {
        return this.getId() - o.getId();
    }

    public void setPriority(Integer priority) {
        return;
    }

    public Integer getPriority() {
        return null;
    }

    @StateTransition(enumType = "BACK_TO_QUEUE")
    public void backToQueue() throws InvalidOperationException {
    }

    @StateTransition(enumType = "ASIGN_TO_CHECK")
    public void asignToCheck() throws InvalidOperationException {
    }

    @StateTransition(enumType = "DELETE")
    public void delete() throws InvalidOperationException {
    }

    @StateTransition(enumType = "COMPUTE_RESULTS")
    public void computeResult() throws InvalidOperationException {
    }

    @StateTransition(enumType = "FAIL")
    public void fail() throws InvalidOperationException {
    }

    //CHECKSTYLE:OFF
    public void reject() {
        List<Integer> oseIds = new ArrayList<Integer>();
        List<Integer> valIds = new ArrayList<Integer>();
        //Remove ObservedSituationEvaluation from BS
        for (ObservedSituationEvaluation ose : this.getObservedSituationEvaluations()) {
            oseIds.add(ose.getId());
            try {
                HibernateSupport.getInstance().findGenericDAO().remove(ose);
            } catch (Exception e) {
            }
        }
        //Remove IndicatorValues from BS
        for (IndicatorValues val : this.getIndicatorValues()) {
            valIds.add(val.getId());
            try {
                HibernateSupport.getInstance().findGenericDAO().remove(val);
            } catch (Exception e) {
            }
        }
        //Remove Compliance_facts from bw
        //Remove indicator_facts from bw
        try {
            RejectBWCommand command = new RejectBWCommand();
            command.execute(oseIds, valIds);
        } catch (ScopixException pex) {
            log.warn("ERROR: " + pex.getMessage());
        }        //Set evaluationState = null
        this.setEvaluationState(null);
    }
    //CHECKSTYLE:ON

    public void changeStateType(EvaluationState evaluationState) {
        this.setEvaluationState(evaluationState);
    }

    public EvaluationState getStateType() {
        return this.getEvaluationState();
    }

    public EvaluationState getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(EvaluationState evaluationState) {
        this.evaluationState = evaluationState;
    }

    public List<RulesLog> getRulesLogs() {
        if (rulesLogs == null) {
            rulesLogs = new ArrayList<RulesLog>();
        }
        return rulesLogs;
    }

    public void setRulesLogs(List<RulesLog> rulesLogs) {
        this.rulesLogs = rulesLogs;
    }

    public List<IndicatorValues> getIndicatorValues() {
        if (indicatorValues == null) {
            indicatorValues = new ArrayList<IndicatorValues>();
        }
        return indicatorValues;
    }

    public void setIndicatorValues(List<IndicatorValues> indicatorValues) {
        this.indicatorValues = indicatorValues;
    }

    /**
     * @return the pendingEvaluation
     */
    public PendingEvaluation getPendingEvaluation() {
        return pendingEvaluation;
    }

    /**
     * @param pendingEvaluation the pendingEvaluation to set
     */
    public void setPendingEvaluation(PendingEvaluation pendingEvaluation) {
        this.pendingEvaluation = pendingEvaluation;
    }

    /**
     * @return the evidenceDate
     */
    public Date getEvidenceDate() {
        return evidenceDate;
    }

    /**
     * @param evidenceDate the evidenceDate to set
     */
    public void setEvidenceDate(Date evidenceDate) {
        this.evidenceDate = evidenceDate;
    }
}
