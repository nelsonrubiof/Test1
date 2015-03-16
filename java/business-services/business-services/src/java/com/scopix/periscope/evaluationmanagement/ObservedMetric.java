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
 * ObservedMetric.java
 *
 * Created on 06-05-2008, 06:45:58 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.queuemanager.Prioritization;
import com.scopix.periscope.periscopefoundation.states.clients.Stateful;
import com.scopix.periscope.periscopefoundation.states.clients.StatefulConfiguration;
import com.scopix.periscope.periscopefoundation.states.configs.StateTransition;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 * 
 * @author Gustavo Alvarez
 */
@Entity
@StatefulConfiguration(configuratorClass = QueuedObjectStateTransition.class)
public class ObservedMetric extends BusinessObject implements Comparable<ObservedMetric>, Prioritization,
		Stateful<EvaluationState> {

	private static Logger log = Logger.getLogger(ObservedMetric.class);

	@ManyToOne
	private ObservedSituation observedSituation;
	@ManyToMany(targetEntity = Evidence.class, fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "rel_observed_metric_evidence", joinColumns = { @JoinColumn(name = "observed_metric_id") }, inverseJoinColumns = { @JoinColumn(name = "evidence_id") })
	private List<Evidence> evidences;
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "observedMetric")
	@PrimaryKeyJoinColumn
	private MetricEvaluation metricEvaluation;
	@OneToMany(mappedBy = "observedMetric", fetch = FetchType.LAZY)
	private List<EvidenceEvaluation> evidenceEvaluations;
	@ManyToOne
	private Metric metric;
	@Temporal(TemporalType.DATE)
	private Date observedMetricDate;
	@Enumerated(EnumType.STRING)
	private EvaluationState evaluationState;
	private static Comparator<ObservedMetric> comparatorByMetricOrder;

	@Temporal(TemporalType.TIMESTAMP)
	private Date evidenceDate;

	public static Comparator<ObservedMetric> getComparatorByMetricOrder() {
		comparatorByMetricOrder = new Comparator<ObservedMetric>() {

			public int compare(ObservedMetric o1, ObservedMetric o2) {
				return o1.getMetric().getMetricOrder() - o2.getMetric().getMetricOrder();
			}
		};

		return comparatorByMetricOrder;
	}

	public static void setComparatorByMetricOrder(Comparator<ObservedMetric> aComparatorByMetricOrder) {
		comparatorByMetricOrder = aComparatorByMetricOrder;
	}

	public ObservedSituation getObservedSituation() {
		return observedSituation;
	}

	public void setObservedSituation(ObservedSituation observedSituation) {
		this.observedSituation = observedSituation;
	}

	public void addEvidence(Evidence e) {
		for (Evidence evidence : getEvidences()) {
			if (e.getId().equals(evidence.getId())) {
				log.debug("No se adiciona evidencia duplicada al observed metric id: [" + e.getId() + "]");
				return;
			}
		}
		getEvidences().add(e);
	}

	public List<Evidence> getEvidences() {
		if (evidences == null) {
			evidences = new ArrayList<Evidence>();
		}
		return evidences;
	}

	public void setEvidences(List<Evidence> evidences) {
		this.evidences = evidences;
	}

	public MetricEvaluation getMetricEvaluation() {
		return metricEvaluation;
	}

	public void setMetricEvaluation(MetricEvaluation metricEvaluation) {
		this.metricEvaluation = metricEvaluation;
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

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	public Date getObservedMetricDate() {
		return observedMetricDate;
	}

	public void setObservedMetricDate(Date observedMetricDate) {
		this.observedMetricDate = observedMetricDate;
	}

	public int compareTo(ObservedMetric o) {
		return this.getId().compareTo(o.getId());
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

	// CHECKSTYLE:OFF
	public void reject() {
		if (this.getMetricEvaluation() != null) {
			MetricEvaluation me = this.getMetricEvaluation();
			try {
				// delete metric evaluation
				HibernateSupport.getInstance().findGenericDAO().remove(me);
			} catch (Exception e) {
			}
		}
		this.setEvaluationState(null);
		if (this.getObservedSituation().getEvaluationState() != null) {
			this.getObservedSituation().reject();
		}
	}

	// CHECKSTYLE:ON

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
