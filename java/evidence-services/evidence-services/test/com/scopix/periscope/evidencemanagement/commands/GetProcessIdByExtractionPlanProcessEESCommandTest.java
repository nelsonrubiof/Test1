/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.commands;

import com.scopix.periscope.evidencemanagement.ProcessEES;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceExtractionServicesServerDAO;
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
public class GetProcessIdByExtractionPlanProcessEESCommandTest {
    
    public GetProcessIdByExtractionPlanProcessEESCommandTest() {
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
    public void testGetDao() {
        System.out.println("getDao");
        GetProcessIdByExtractionPlanProcessEESCommand instance = new GetProcessIdByExtractionPlanProcessEESCommand();
        EvidenceExtractionServicesServerDAO expResult = null;
        EvidenceExtractionServicesServerDAO result = instance.getDao();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDao() {
        System.out.println("setDao");
        EvidenceExtractionServicesServerDAO dao = null;
        GetProcessIdByExtractionPlanProcessEESCommand instance = new GetProcessIdByExtractionPlanProcessEESCommand();
        instance.setDao(dao);
        fail("The test case is a prototype.");
    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        ExtractionPlan extractionPlan = null;
        Integer eesProcessId = null;
        GetProcessIdByExtractionPlanProcessEESCommand instance = new GetProcessIdByExtractionPlanProcessEESCommand();
        ProcessEES expResult = null;
        ProcessEES result = instance.execute(extractionPlan, eesProcessId);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}