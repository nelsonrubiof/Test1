/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import java.util.Map;

/**
 *
 * @author Cesar
 */
public interface EvidenceEvaluatorForMT {

	/**
	 * Evaluates a specific metric.
	 * 
	 * @param observedMetric
	 * @param pendingEvaluationId
     * @param hash
	 * @return if the metric was succefully achieved.
	 * @throws ScopixException
	 */
	Boolean evaluate(ObservedMetric observedMetric, Integer pendingEvaluationId) throws ScopixException;

    void setDao(GenericDAO dao);
}
