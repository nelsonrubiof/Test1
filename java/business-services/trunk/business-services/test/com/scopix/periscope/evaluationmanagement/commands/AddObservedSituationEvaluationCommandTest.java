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
 *  AddObservedSituationEvaluationCommandTest.java
 * 
 *  Created on 13-09-2010, 11:32:43 AM
 * 
 */

package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.ObservedSituationEvaluation;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import mockup.GenericDAOMockup;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
public class AddObservedSituationEvaluationCommandTest {

    public AddObservedSituationEvaluationCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("AddObservedSituationEvaluationCommandTest");
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

    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        ObservedSituationEvaluation observedSituationEvaluation = new ObservedSituationEvaluation();
        observedSituationEvaluation.setId(2);
        observedSituationEvaluation.setDepartment("departamento");
        AddObservedSituationEvaluationCommand instance = new AddObservedSituationEvaluationCommand();
        GenericDAOMockup dao = new GenericDAOMockup();
        instance.setDao(dao);
        instance.execute(observedSituationEvaluation);
        assertEquals(new Integer(2), dao.getBusinessObject().getId());
        assertEquals("departamento", ((ObservedSituationEvaluation)dao.getBusinessObject()).getDepartment());

    }


}