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
 * MetricEvaluatorDigester.java
 *
 * Created on 09-06-2008, 05:48:04 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = MetricEvaluatorDigester.class)
public class MetricEvaluatorDigester implements InitializingBean {

    private static Logger log = Logger.getLogger(MetricEvaluatorDigester.class);

    private MetricEvaluatorDigester() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.evaluateMetricQueue();
    }

    private void evaluateMetricQueue() {
        try {
            final Thread t = new Thread(new Runnable() {
                public void run() {
                    log.debug("[evaluateMetricQueue run] init");
                    while (true) {
                        try {
                            int observedMetricId = SpringSupport.getInstance().findBeanByClassName(MetricEvaluatorManager.class).
                                    getNextObservedMetric();
                            SpringSupport.getInstance().findBeanByClassName(MetricEvaluatorManager.class).evaluateObservedMetric(
                                    observedMetricId);
                        } catch (Exception e) {
                            log.warn("Error" + e.getMessage());
                        }
                    }
                }
            });
            t.setName("MetricEvaluator");
            t.start();
        } catch (Exception e) {
            log.warn("" + e.getMessage(), e);
        }
    }
}
