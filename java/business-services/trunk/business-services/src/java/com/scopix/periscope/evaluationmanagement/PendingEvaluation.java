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
 * PendingEvaluation.java
 *
 * Created on 09-05-2008, 12:45:33 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.queuemanager.Prioritization;
import com.scopix.periscope.periscopefoundation.states.clients.Stateful;
import com.scopix.periscope.periscopefoundation.states.clients.StatefulConfiguration;
import com.scopix.periscope.periscopefoundation.states.configs.StateTransition;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.RejectedHistory;
import com.scopix.periscope.queuemanagement.OperatorQueue;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@Entity
@StatefulConfiguration(configuratorClass = QueuedObjectStateTransition.class)
public class PendingEvaluation extends BusinessObject implements Prioritization, Stateful<EvaluationState>,
        Comparable<PendingEvaluation> {

    @Enumerated(EnumType.STRING)
    private EvaluationState evaluationState;
    @Enumerated(EnumType.STRING)
    private EvaluationQueue evaluationQueue;
    @Enumerated(EnumType.STRING)
    private CreationType creationType;
    @OneToOne(fetch = FetchType.EAGER)
    private ObservedSituation observedSituation;
    private Integer priority;
    private boolean changePriority;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pendingEvaluation")
    private List<EvidenceEvaluation> evidenceEvaluations;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pendingEvaluation")
    private List<RejectedHistory> rejectedHistorys;
    @ManyToOne
    private OperatorQueue operatorQueue;
    private static Comparator<PendingEvaluation> comparatorByPriority;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    private String userName; //user_name
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date evaluationStartDate; //evaluation_start_date
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date evaluationEndDate; //evaluation_end_date

    public static Comparator<PendingEvaluation> getComparatorByPriority() {
        comparatorByPriority = new Comparator<PendingEvaluation>() {

            @Override
            public int compare(PendingEvaluation o1, PendingEvaluation o2) {
                return o1.getPriority().compareTo(o2.getPriority());
            }
        };

        return comparatorByPriority;
    }

    public static void setComparatorByPriority(Comparator<PendingEvaluation> aComparatorByPriority) {
        comparatorByPriority = aComparatorByPriority;
    }

    public EvaluationState getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(EvaluationState evaluationState) {
        this.evaluationState = evaluationState;
    }

    public EvaluationQueue getEvaluationQueue() {
        return evaluationQueue;
    }

    public void setEvaluationQueue(EvaluationQueue evaluationQueue) {
        this.evaluationQueue = evaluationQueue;
    }

    public ObservedSituation getObservedSituation() {
        return observedSituation;
    }

    public void setObservedSituation(ObservedSituation observedSituation) {
        this.observedSituation = observedSituation;
    }

    @StateTransition(enumType = "BACK_TO_QUEUE")
    public void backToQueue() throws InvalidOperationException {
        if (this.getEvidenceEvaluations() != null) {
            for (EvidenceEvaluation evidenceEvaluation : this.getEvidenceEvaluations()) {
                evidenceEvaluation.setRejected(true);
                if (evidenceEvaluation.getObservedMetric().getEvaluationState() != null) {
                    evidenceEvaluation.getObservedMetric().reject();
                }
            }
        }
        this.setEvidenceEvaluations(null);

		this.setPriority(1);

        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshMetricQueue();
        SpringSupport.getInstance().findBeanByClassName(EvaluationQueueManager.class).refreshSituationQueue();
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void changeStateType(EvaluationState evaluationState) {
        this.setEvaluationState(evaluationState);
    }

    public EvaluationState getStateType() {
        return this.getEvaluationState();
    }

    public int compareTo(PendingEvaluation o) {
        return this.getId() - o.getId();
    }

    public List<EvidenceEvaluation> getEvidenceEvaluations() {
        if (evidenceEvaluations == null) {
            evidenceEvaluations = new ArrayList<EvidenceEvaluation>();
        }
        return evidenceEvaluations;
    }

    public void setEvidenceEvaluations(List<EvidenceEvaluation> evidenceEvaluations) {
        this.evidenceEvaluations = evidenceEvaluations;
    }

    public boolean isChangePriority() {
        return changePriority;
    }

    public void setChangePriority(boolean changePriority) {
        this.changePriority = changePriority;
    }

    public List<RejectedHistory> getRejectedHistorys() {
        if (rejectedHistorys == null) {
            rejectedHistorys = new ArrayList<RejectedHistory>();
        }
        return rejectedHistorys;
    }

    public void setRejectedHistorys(List<RejectedHistory> rejectedHistorys) {
        this.rejectedHistorys = rejectedHistorys;
    }

    /**
     * @return the operatorQueue
     */
    public OperatorQueue getOperatorQueue() {
        return operatorQueue;
    }

    /**
     * @param operatorQueue the operatorQueue to set
     */
    public void setOperatorQueue(OperatorQueue operatorQueue) {
        this.operatorQueue = operatorQueue;
    }

    public CreationType getCreationType() {
        return creationType;
    }

    public void setCreationType(CreationType creationType) {
        this.creationType = creationType;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the evaluationStartDate
     */
    public Date getEvaluationStartDate() {
        return evaluationStartDate;
    }

    /**
     * @param evaluationStartDate the evaluationStartDate to set
     */
    public void setEvaluationStartDate(Date evaluationStartDate) {
        this.evaluationStartDate = evaluationStartDate;
    }

    /**
     * @return the evaluationEndDate
     */
    public Date getEvaluationEndDate() {
        return evaluationEndDate;
    }

    /**
     * @param evaluationEndDate the evaluationEndDate to set
     */
    public void setEvaluationEndDate(Date evaluationEndDate) {
        this.evaluationEndDate = evaluationEndDate;
    }
}