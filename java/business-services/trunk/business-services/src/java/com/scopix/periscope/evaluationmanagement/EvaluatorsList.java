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
 * EvaluatorsList.java
 *
 * Created on 19-08-2008, 10:33:47 AM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator.Evaluator;
import com.scopix.periscope.periscopefoundation.util.classeslocator.observers.SpringBeansLocatorObserver;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;

/**
 * This class contains three list, two for springBeanEvaluatorNames and another of rules names
 *
 * @author C�sar Abarza Suazo.
 */
@SpringBean(rootClass = EvaluatorsList.class)
public class EvaluatorsList implements InitializingBean {

    private Logger log = Logger.getLogger(EvaluatorsList.class);
    private static Map<String, String> springBeanEvaluatorsForEvidenceMT;
    private static Map<String, String> springBeanEvaluatorsForEvidenceST;
    private static Map<String, String> springBeanEvaluatorsForMetric;
    private static List<String> ruleEvaluators;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.loadSpringBeanEvaluatorsForEvidenceMT();
        this.loadSpringBeanEvaluatorsForEvidenceST();
        this.loadSpringBeanEvaluatorsForMetric();
    }

    /**
     * Load spring beans for evidence looking for Evaluator annotation and EvaluatorType EVIDENCE from MetricTemplate
     */
    private void loadSpringBeanEvaluatorsForEvidenceMT() {
        log.debug("start");
        springBeanEvaluatorsForEvidenceMT = new HashMap<String, String>();
        List<Class> beans = SpringBeansLocatorObserver.getInstance().getAllSpringBeans();
        for (Class clazz : beans) {
            if (clazz.isAnnotationPresent(Evaluator.class) && Evaluator.EvaluatorType.EVIDENCE_MT.equals(
                    (((Evaluator) clazz.getAnnotation(Evaluator.class)).evaluatorType()))) {
                log.debug("class = " + clazz.getName() + ", Description "
                        + ((Evaluator) clazz.getAnnotation(Evaluator.class)).description());
                springBeanEvaluatorsForEvidenceMT.put(clazz.getName(), ((Evaluator) clazz.getAnnotation(Evaluator.class)).
                        description());
            }
        }
        log.debug("end");
    }

    /**
     * Load spring beans for evidence looking for Evaluator annotation and EvaluatorType EVIDENCE for SituationTemplate
     */
    private void loadSpringBeanEvaluatorsForEvidenceST() {
        log.debug("start");
        springBeanEvaluatorsForEvidenceST = new HashMap<String, String>();
        List<Class> beans = SpringBeansLocatorObserver.getInstance().getAllSpringBeans();
        for (Class clazz : beans) {
            if (clazz.isAnnotationPresent(Evaluator.class) && Evaluator.EvaluatorType.EVIDENCE_ST.equals(
                    (((Evaluator) clazz.getAnnotation(Evaluator.class)).evaluatorType()))) {
                log.debug("class = " + clazz.getName() + ", Description "
                        + ((Evaluator) clazz.getAnnotation(Evaluator.class)).description());
                springBeanEvaluatorsForEvidenceST.put(clazz.getName(), ((Evaluator) clazz.getAnnotation(Evaluator.class)).
                        description());
            }
        }
        log.debug("end");
    }

    /**
     * Load spring beans for metrics looking for Evaluator annotation and EvaluatorType METRIC
     */
    private void loadSpringBeanEvaluatorsForMetric() {
        log.debug("start");
        springBeanEvaluatorsForMetric = new LinkedHashMap<String, String>();
        List<Class> beans = SpringBeansLocatorObserver.getInstance().getAllSpringBeans();
        for (Class clazz : beans) {
            if (clazz.isAnnotationPresent(Evaluator.class) && Evaluator.EvaluatorType.METRIC.equals(
                    (((Evaluator) clazz.getAnnotation(Evaluator.class)).evaluatorType()))) {
                log.debug("class = " + clazz.getName() + ", Description "
                        + ((Evaluator) clazz.getAnnotation(Evaluator.class)).description());
                springBeanEvaluatorsForMetric.put(clazz.getName(),
                        ((Evaluator) clazz.getAnnotation(Evaluator.class)).description());
            }
        }
        log.debug("end");
    }

    private void loadRuleEvaluators() {
        ruleEvaluators = getRules("rules/");

    }

    /**
     * Load from classpath reading all files ending with '.drl'
     *
     * @param path
     * @return
     */
    public List<String> getRules(String path) {
        log.debug("start, path = " + path);
        List<String> list = new ArrayList<String>();
        try {
            ClassPathResource cpr = new ClassPathResource(path);
            File f = cpr.getFile();
            if (f.isDirectory()) {
                log.debug("directory");
                String[] files = f.list();
                for (String s : files) {
                    log.debug("file = " + s);
                    if (s.endsWith(".drl")) {
                        list.add(path + s);
                    } else {
                        list.addAll(getRules(path + s + "/"));
                    }
                }
            }
        } catch (Exception e) {
            log.debug("error = " + e.getMessage(), e);
        }
        log.debug("end");
        return list;
    }

    public Map<String, String> getSpringBeanEvaluatorsForEvidenceMT() {
        if (springBeanEvaluatorsForEvidenceMT == null) {
            springBeanEvaluatorsForEvidenceMT = new HashMap<String, String>();
        }
        return springBeanEvaluatorsForEvidenceMT;
    }

    public void setSpringBeanEvaluatorsForEvidenceMT(Map<String, String> aSpringBeanEvaluators) {
        springBeanEvaluatorsForEvidenceMT = aSpringBeanEvaluators;
    }

    public Map<String, String> getSpringBeanEvaluatorsForEvidenceST() {
        if (springBeanEvaluatorsForEvidenceST == null) {
            springBeanEvaluatorsForEvidenceST = new HashMap<String, String>();
        }
        return springBeanEvaluatorsForEvidenceST;
    }

    public void setSpringBeanEvaluatorsForEvidenceST(Map<String, String> aSpringBeanEvaluators) {
        springBeanEvaluatorsForEvidenceST = aSpringBeanEvaluators;
    }

    public Map<String, String> getSpringBeanEvaluatorsForMetric() {
        if (springBeanEvaluatorsForMetric == null) {
            springBeanEvaluatorsForMetric = new LinkedHashMap<String, String>();
        }
        return springBeanEvaluatorsForMetric;
    }

    public void setSpringBeanEvaluatorsForMetric(Map<String, String> aSpringBeanEvaluatorsForMetric) {
        springBeanEvaluatorsForMetric = aSpringBeanEvaluatorsForMetric;
    }

    public List<String> getRuleEvaluators() {
        this.loadRuleEvaluators();
        if (ruleEvaluators == null) {
            ruleEvaluators = new ArrayList<String>();
        }
        return ruleEvaluators;
    }

    public void setRuleEvaluators(List<String> aRuleEvaluators) {
        ruleEvaluators = aRuleEvaluators;
    }
}
