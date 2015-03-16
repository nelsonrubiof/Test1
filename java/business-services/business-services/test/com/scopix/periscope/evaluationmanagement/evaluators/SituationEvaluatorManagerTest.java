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
 *  SituationEvaluatorManagerTest.java
 * 
 *  Created on 13-09-2010, 11:08:58 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.evaluationmanagement.IndicatorValues;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationCommand;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.commands.AddObservedSituationEvaluationCommand;

import mockup.GenericDAOMockup;
import mockup.ManagerMock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
@Ignore
public class SituationEvaluatorManagerTest {

    public SituationEvaluatorManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("SituationEvaluatorManagerTest");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Testea la evaluación de las fórmulas.
     *
     * Las fórmulas son entregadas por el DAO ObservedSituationHibernateDAOMock.
     * 
     * @throws Exception
     */
    @Test
    public void testEvaluateObservedSituation() throws Exception {
        System.out.println("evaluateObservedSituation");
        int observedSituationId = 1;

        SituationEvaluatorManager instance = new SituationEvaluatorManager();

        //colocamos los command por orden de ejecucion en el metodo
        GetObservedSituationCommand command = ManagerMock.genObservedSituationCommand();
        instance.setObservedSituationCommand(command);
        GenericDAOMockup dao = (GenericDAOMockup) command.getDao();

        instance.setAddIndicatorValuesCommand(ManagerMock.genAddIndicatorValuesCommand());
        instance.setFormulasBySTAndStoreCommand(ManagerMock.genFormulasBySTAndStoreCommand());
        instance.setObservedSituationEvaluationCommand(ManagerMock.genObservedSituationEvaluationCommand());

        instance.evaluateObservedSituation(observedSituationId);

        // Evaluación COMPLIANT
        assertNotNull(((ObservedSituation) dao.getBusinessObject()).getObservedSituationEvaluations().get(0));
        assertEquals(new Integer(1), ((ObservedSituation) dao.getBusinessObject()).getObservedSituationEvaluations().get(0).getAreaId());
        assertEquals(new Integer(1), ((ObservedSituation) dao.getBusinessObject()).getObservedSituationEvaluations().get(0).getCompliant());

        // Evaluación NON-COMPLIANT
        assertNotNull(((ObservedSituation) dao.getBusinessObject()).getObservedSituationEvaluations().get(1));
        assertEquals(new Integer(1), ((ObservedSituation) dao.getBusinessObject()).getObservedSituationEvaluations().get(1).getAreaId());
        assertEquals(new Integer(0), ((ObservedSituation) dao.getBusinessObject()).getObservedSituationEvaluations().get(1).getCompliant());

        // Indicador
        assertNotNull(((ObservedSituation) dao.getBusinessObject()).getIndicatorValues().get(0));
        assertEquals(new Double(10), ((ObservedSituation) dao.getBusinessObject()).getIndicatorValues().get(0).getNumerator());
        assertEquals(new Double(2), ((ObservedSituation) dao.getBusinessObject()).getIndicatorValues().get(0).getDenominator());

    }

}
