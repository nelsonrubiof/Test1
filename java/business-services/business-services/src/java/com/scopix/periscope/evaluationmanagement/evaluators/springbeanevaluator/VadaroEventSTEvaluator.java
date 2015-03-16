/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  VadaroEventSTEvaluator.java
 * 
 *  Created on 10-07-2014, 12:34:12 PM
 * 
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
 * @author Nelson
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_ST, 
 description = "Evaluador de Eventos Vadaro para situacion")
@SpringBean(rootClass = VadaroEventSTEvaluator.class)
public class VadaroEventSTEvaluator  implements EvidenceEvaluatorForST {
    private static final Logger log = Logger.getLogger(VadaroEventSTEvaluator.class);

    @Override
    public void evaluate(PendingEvaluation pendingEvaluation) throws ScopixException {
        log.info("start");
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
        log.info("end");
    }

    @Override
    public boolean hasChangePendingEvaluation() {
        return true;
    }
}
