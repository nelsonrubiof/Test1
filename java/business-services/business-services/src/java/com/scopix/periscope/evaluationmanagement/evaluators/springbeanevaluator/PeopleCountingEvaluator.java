/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.evaluators.EvidenceEvaluatorForMT;
import com.scopix.periscope.evaluationmanagement.evaluators.EvidenceEvaluatorForST;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Cesar
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_ST, description = "Evaluador para People Counting")
@SpringBean(rootClass = PeopleCountingEvaluator.class)
public class PeopleCountingEvaluator implements  EvidenceEvaluatorForST {

    private Logger log = Logger.getLogger(PeopleCountingEvaluator.class);

    @Override
    public void evaluate(PendingEvaluation pendingEvaluation) throws ScopixException {
        for (ObservedMetric om : pendingEvaluation.getObservedSituation().getObservedMetrics()) {
            String evaluatorName = om.getMetric().getMetricTemplate().getEvidenceSpringBeanEvaluatorName();
            if (evaluatorName != null && evaluatorName.length() > 0) {
                try {
                    EvidenceEvaluatorForMT evidenceEvaluator = (EvidenceEvaluatorForMT) SpringSupport.getInstance().
                            findBeanByClassName(Class.forName(evaluatorName));
                    evidenceEvaluator.evaluate(om, pendingEvaluation.getId());
                } catch (ClassNotFoundException ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public boolean hasChangePendingEvaluation() {
        return true;
    }

}



