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
 * MetricEvaluatorManager.java
 *
 * Created on 10-06-2008, 02:11:14 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.bestcode.mathparser.IMathParser;
import com.bestcode.mathparser.MathParserFactory;
import com.scopix.periscope.extractionplanmanagement.CompliantTypeEnum;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.EvaluationQueue;
import com.scopix.periscope.evaluationmanagement.EvaluationState;
import com.scopix.periscope.evaluationmanagement.IndicatorValues;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.ObservedSituationEvaluation;
import com.scopix.periscope.evaluationmanagement.RulesLog;
import com.scopix.periscope.evaluationmanagement.commands.AddIndicatorValuesCommand;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedSituationEvaluationCommand;
import com.scopix.periscope.evaluationmanagement.commands.FormulasBySTAndStoreCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.queuemanager.QueueManager;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author César Abarza Suazo.
 */
@SpringBean(rootClass = SituationEvaluatorManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class SituationEvaluatorManager {

    private static final Integer MAX_METRIC_COUNT = 50;
    private static Logger log = Logger.getLogger(SituationEvaluatorManager.class);
    private GetObservedSituationCommand observedSituationCommand;
    private AddIndicatorValuesCommand addIndicatorValuesCommand;
    private AddObservedSituationEvaluationCommand observedSituationEvaluationCommand;
    private FormulasBySTAndStoreCommand formulasBySTAndStoreCommand;
    private GenericDAO genericDAO;

    /**
     * This method obtain the next ObservedSituation in the queue.
     * @return id of the first observed situation in the queue
     */
    public int getNextObservedSituation() throws Exception {
        log.debug("[getNextObservedSituation run] Calling dequeueObject");
        ObservedSituation observedSituation = null;
        try {
            QueueManager queueManagerSituation = new QueueManager(EvaluationQueue.SITUATION);
            while (true) {
                observedSituation = (ObservedSituation) queueManagerSituation.dequeueObject(ObservedSituation.class);
                if (observedSituation == null
                        || observedSituation.getEvaluationState().getName().equals(EvaluationState.ENQUEUED.getName())) {
                    break;
                }
            }
            log.debug("observedSituation " + observedSituation);
            observedSituation.asignToCheck();
        } catch (Exception e) {
            observedSituation.fail();
            log.error("error = " + e.getMessage(), e);
            rulesLog(observedSituation, e);
            throw e;
        }
        log.info("end " + observedSituation.getId());
        return observedSituation.getId();
    }

    /**
     * This method invoke the rule for evaluate the observed situation 
     * @param observedSituationId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void evaluateObservedSituation(int observedSituationId) throws ScopixException {
        log.info("start " + observedSituationId);
        ObservedSituation observedSituation = null;
        try {

            observedSituation = getObservedSituationCommand().execute(observedSituationId);
            //Load bags
            observedSituation.getObservedSituationEvaluations().isEmpty();
            observedSituation.getIndicatorValues().isEmpty();
            //
            log.debug("[evaluateObservedSituation run] ObservedSituationId = " + observedSituation.getId());
            Store store = observedSituation.getObservedMetrics().get(0).getMetric().getStore();
            SituationTemplate situationTemplate = observedSituation.getSituation().getSituationTemplate();

            // obtener todas las formulas para SituationTemplate-Store
            FormulasBySTAndStoreCommand command = getFormulasBySTAndStoreCommand();
            List<Formula> formulas = command.execute(situationTemplate, store);

            Collections.sort(observedSituation.getObservedMetrics(), ObservedMetric.getComparatorByMetricOrder());

            if (!formulas.isEmpty()) {
                for (Formula f : formulas) {
                    if (f.getCompliantType() != null) {
                        this.evaluateComplianceFormula(observedSituation, f);
                        log.debug("formula compliant type is null");
                    }
//                    else {
//                        this.evaluateIndicatorFormula(observedSituation, f);
//                    }
                }
                //Actualizar el resultado obtenido con la ejecucion de las reglas
                //solo se pasa por este ciclo si la formula es getCompliantType() != null
                if (observedSituation.getObservedSituationEvaluations() != null
                        && !observedSituation.getObservedSituationEvaluations().isEmpty()) {
                    //Guardar cada ObservedSituationEvaluation
                    for (ObservedSituationEvaluation ose : observedSituation.getObservedSituationEvaluations()) {
                        ose.setObservedSituation(observedSituation);
                        getObservedSituationEvaluationCommand().execute(ose);
                    }
                }
                //Actualizar el resultado obtenido con la ejecucion de las reglas para los indicadores
                if (observedSituation.getIndicatorValues() != null && !observedSituation.getIndicatorValues().isEmpty()) {
                    //Guardar cada IndicatorValue
                    for (IndicatorValues value : observedSituation.getIndicatorValues()) {
                        value.setObservedSituation(observedSituation);
                        getAddIndicatorValuesCommand().execute(value);
                    }
                }
            }
            //se mueve a fuera del if para que quede FINISHED y no CHECKING
            observedSituation.computeResult();
        } catch (Exception e) {
            log.error("ERROR = " + e.getMessage(), e);
            this.rulesLog(observedSituation, e);
            observedSituation.fail();
            //se retira excepcion ya que esto produce roolback 
        }
        log.info("end " + observedSituationId);
    }

    /**
     * This method evaluate a indicator formula and add a indicatorValue to the os
     * @param os
     * @param f
     * @throws Exception
     */
    protected void evaluateIndicatorFormula(ObservedSituation os, Formula f) throws Exception {
        boolean evaluate = true;
        String numeratorFormula = f.getFormula();
        String denominatorFormula = f.getDenominator();
        //Obtener el numerador
        IMathParser numeratorParser = MathParserFactory.create();
        numeratorParser.setExpression(numeratorFormula);
//        for (ObservedMetric om : os.getObservedMetrics()) {
//            if (om.getMetricEvaluation() != null) {
//                numeratorParser.setVariable(om.getMetric().getMetricVariableName(), om.getMetricEvaluation().
//                        getMetricEvaluationResult());
//            } else {
//                evaluate = false;
//                break;
//            }
//        }
        if (evaluate) {
            Double numeratorResult = numeratorParser.evaluate();
            //Obtener el denominador
            IMathParser denominatorParser = MathParserFactory.create();
            denominatorParser.setExpression(denominatorFormula);
            for (ObservedMetric om : os.getObservedMetrics()) {
                denominatorParser.setVariable(om.getMetric().getMetricVariableName(), om.getMetricEvaluation().
                        getMetricEvaluationResult());
            }
            Double denominatorResult = denominatorParser.evaluate();

            IndicatorValues value = new IndicatorValues();
            value.setIndicator(f.getIndicator());
            value.setNumerator(numeratorResult);
            value.setDenominator(denominatorResult);
            value.setStoreId(os.getObservedMetrics().get(0).getMetric().getStore().getId());
            value.setEvaluationDate(new Date());
            os.getIndicatorValues().add(value);
        }
    }

    /**
     * This method evaluate a compliance formula, this can be a compliant or a not compliant
     * @param os
     * @param f
     * @throws Exception
     */
    private void evaluateComplianceFormula(ObservedSituation os, Formula f) throws Exception {
        boolean evaluate = true;
        String expresion = f.getFormula();
        IMathParser parser = MathParserFactory.create();
        parser.setExpression(expresion);
//        for (ObservedMetric om : os.getObservedMetrics()) {
//            //nos aseguramos que exista nombre de Varaible
//            if (om.getMetricEvaluation() != null && om.getMetric().getMetricVariableName() != null) {
//                parser.setVariable(om.getMetric().getMetricVariableName(), om.getMetricEvaluation().
//                        getMetricEvaluationResult());
//            } else {
//                evaluate = false;
//                break;
//            }
//        }
        if (evaluate) {
            Double result = parser.evaluate();
            if (result > 0) {
                ObservedSituationEvaluation ose = new ObservedSituationEvaluation();
                //Set the rule name
                ose.setRuleName(f.getDescription());
                //Target
                ose.setTarget(f.getTarget());
                //Standard
                ose.setStandard(f.getStandard());
                //Metric count
                ose.setMetricCount(os.getObservedMetrics().size());
                //Compliant Value
                if (f.getCompliantType().equals(CompliantTypeEnum.COMPLIANT)) {
                    ose.setCompliant(1);
                } else {
                    ose.setCompliant(0);
                }
                //Set Metric Values
                int count = 1;
                for (ObservedMetric om : os.getObservedMetrics()) {
                    PropertyDescriptor metricIdDescriptor = new PropertyDescriptor("metricId" + count,
                            ObservedSituationEvaluation.class);
                    metricIdDescriptor.getWriteMethod().invoke(ose, om.getId());
                    PropertyDescriptor metricDescriptor = new PropertyDescriptor("metric" + count,
                            ObservedSituationEvaluation.class);
                    metricDescriptor.getWriteMethod().invoke(ose,
                            om.getMetricEvaluation().getMetricEvaluationResult().doubleValue());
                    if (count == MAX_METRIC_COUNT) {
                        break;
                    }
                    count++;
                }
                ose.setDepartment(os.getSituation().getSituationTemplate().getAreaType().getDescription());
                ose.setAreaId(os.getSituation().getSituationTemplate().getAreaType().getId());
                ose.setProduct(os.getSituation().getSituationTemplate().getProduct().getDescription());
                ose.setStoreId(os.getObservedMetrics().get(0).getMetric().getStore().getId());
                ose.setEvaluationDate(new Date());
                os.getObservedSituationEvaluations().add(ose);
            }
        }
    }

    /**
     * This method save log for rules process
     * @param os
     * @param e
     */
    public void rulesLog(ObservedSituation os, Exception e) {
        RulesLog rulesLog = new RulesLog();
        rulesLog.setObservedSituation(os);
        rulesLog.setMessage(e.getMessage());
        rulesLog.setLogDate(new Date());
        getGenericDAO().save(rulesLog);
    }

    public GetObservedSituationCommand getObservedSituationCommand() {
        if (observedSituationCommand == null) {
            observedSituationCommand = new GetObservedSituationCommand();
        }
        return observedSituationCommand;
    }

    public void setObservedSituationCommand(GetObservedSituationCommand observedSituationCommand) {
        this.observedSituationCommand = observedSituationCommand;
    }

    public AddIndicatorValuesCommand getAddIndicatorValuesCommand() {
        if (addIndicatorValuesCommand == null) {
            addIndicatorValuesCommand = new AddIndicatorValuesCommand();
        }
        return addIndicatorValuesCommand;
    }

    public void setAddIndicatorValuesCommand(AddIndicatorValuesCommand addIndicatorValuesCommand) {
        this.addIndicatorValuesCommand = addIndicatorValuesCommand;
    }

    public AddObservedSituationEvaluationCommand getObservedSituationEvaluationCommand() {
        if (observedSituationEvaluationCommand == null) {
            observedSituationEvaluationCommand = new AddObservedSituationEvaluationCommand();
        }
        return observedSituationEvaluationCommand;
    }

    public void setObservedSituationEvaluationCommand(AddObservedSituationEvaluationCommand observedSituationEvaluationCommand) {
        this.observedSituationEvaluationCommand = observedSituationEvaluationCommand;
    }

    public FormulasBySTAndStoreCommand getFormulasBySTAndStoreCommand() {
        if (formulasBySTAndStoreCommand == null) {
            formulasBySTAndStoreCommand = new FormulasBySTAndStoreCommand();
        }
        return formulasBySTAndStoreCommand;
    }

    public void setFormulasBySTAndStoreCommand(FormulasBySTAndStoreCommand formulasBySTAndStoreCommand) {
        this.formulasBySTAndStoreCommand = formulasBySTAndStoreCommand;
    }

    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
