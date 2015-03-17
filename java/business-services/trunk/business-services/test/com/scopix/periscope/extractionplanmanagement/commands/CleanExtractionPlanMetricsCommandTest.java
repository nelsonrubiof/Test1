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
 *  CleanExtractionPlanMetricsCommandTest.java
 * 
 *  Created on 29-09-2010, 01:29:53 PM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import mockup.ExtractionPlanCustomizingDAOMock;
import mockup.ManagerMock;
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
public class CleanExtractionPlanMetricsCommandTest {

    public CleanExtractionPlanMetricsCommandTest() {
        System.out.println("CleanExtractionPlanMetricsCommandTest");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
        ExtractionPlanCustomizing customizing = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        CleanExtractionPlanMetricsCommand instance = new CleanExtractionPlanMetricsCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        instance.execute(customizing);
        assertEquals(0, customizing.getExtractionPlanMetrics().size());
        
    }

}