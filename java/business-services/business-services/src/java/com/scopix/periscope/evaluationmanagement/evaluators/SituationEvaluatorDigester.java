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
 * SituationEvaluatorDigester.java
 *
 * Created on 07-08-2008, 03:40:13 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingManager;
import com.scopix.periscope.synchronize.SynchronizeManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * This class is the digester of SITUATION queue
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
@SpringBean(rootClass = SituationEvaluatorDigester.class)
public class SituationEvaluatorDigester implements InitializingBean {

    private static Logger log = Logger.getLogger(SituationEvaluatorDigester.class);

    private SituationEvaluatorDigester() {
    }

    /**
     * reemplaza al initMethod de spring anterior
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        this.evaluateSituationQueue();
    }

    /**
     * This method create a threat that consume elements from the queue
     *
     */
    private void evaluateSituationQueue() {
        try {
            final Thread t;
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    log.debug("[evaluateSituationQueue run] init");
                    while (true) {
                        try {
                            int observedSituationId = 0;
                            try {
                                observedSituationId = SpringSupport.getInstance().findBeanByClassName(
                                        SituationEvaluatorManager.class).getNextObservedSituation();
                            } catch (Exception e) {
                                log.warn("Error" + e);
                                observedSituationId = 0;
                            }
                            if (observedSituationId > 0) {
                                try {
                                    SpringSupport.getInstance().findBeanByClassName(SituationEvaluatorManager.class).
                                            evaluateObservedSituation(observedSituationId);
                                } catch (ScopixException e) {
                                    log.warn("Error OSE:" + e);
                                }

                                //Creamos su simil en Reporting Data
                                try {
                                    SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                                            generateReportingData(observedSituationId);
                                } catch (ScopixException e) {
                                    log.warn("Error RD:" + e);
                                }

                                try {
                                    SpringSupport.getInstance().findBeanByClassName(SynchronizeManager.class).
                                            sendDataOtherSystem(observedSituationId);
                                } catch (ScopixException e) {
                                    log.warn("Error RD:" + e);
                                }
                            }
                        } catch (Exception e) {
                            log.error("Exception " + e, e);
                        }
                    }
                }
            });
            t.setName("SituationEvaluator");
            t.start();
        } catch (Exception e) {
            log.warn("Exception " + e.getMessage(), e);
        }
    }

}
