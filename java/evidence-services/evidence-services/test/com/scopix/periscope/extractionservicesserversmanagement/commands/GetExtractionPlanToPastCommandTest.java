/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.commands;

import java.util.List;
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
public class GetExtractionPlanToPastCommandTest {
    
    public GetExtractionPlanToPastCommandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
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
        String date = "";
        Integer extractionServicesServerId = null;
        String storeName = "";
        GetExtractionPlanToPastCommand instance = new GetExtractionPlanToPastCommand();
        List expResult = null;
        List result = instance.execute(date, extractionServicesServerId, storeName);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceRequestIds() throws Exception {
        System.out.println("getEvidenceRequestIds");
        List<Integer> evidenceServicesRequestIds = null;
        GetExtractionPlanToPastCommand instance = new GetExtractionPlanToPastCommand();
        List expResult = null;
        List result = instance.getEvidenceRequestIds(evidenceServicesRequestIds);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}