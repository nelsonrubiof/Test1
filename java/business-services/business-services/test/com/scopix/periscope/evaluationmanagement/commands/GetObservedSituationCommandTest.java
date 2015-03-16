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
 *  GetObservedSituationCommandTest.java
 * 
 *  Created on 13-09-2010, 11:30:53 AM
 * 
 */

package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
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
public class GetObservedSituationCommandTest {

    public GetObservedSituationCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetObservedSituationCommandTest");
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
        Integer id = 2;
        GetObservedSituationCommand instance = new GetObservedSituationCommand();
        instance.setDao(new GenericDAOMockup());
        ObservedSituation expResult = new ObservedSituation();
        expResult.setId(2);
        ObservedSituation result = instance.execute(id);
        assertEquals(expResult.getId(), result.getId());
    }

   
}