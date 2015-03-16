/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import com.scopix.periscope.evidencemanagement.Evidence;
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
public class ExtractionPlanDetailTest {
    
    public ExtractionPlanDetailTest() {
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
    public void testGetExtractionPlan() {
        System.out.println("getExtractionPlan");
        ExtractionPlanDetail instance = new ExtractionPlanDetail();
        ExtractionPlan expResult = null;
        ExtractionPlan result = instance.getExtractionPlan();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlan() {
        System.out.println("setExtractionPlan");
        ExtractionPlan extractionPlan = null;
        ExtractionPlanDetail instance = new ExtractionPlanDetail();
        instance.setExtractionPlan(extractionPlan);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceRequests() {
        System.out.println("getEvidenceRequests");
        ExtractionPlanDetail instance = new ExtractionPlanDetail();
        List expResult = null;
        List result = instance.getEvidenceRequests();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceRequests() {
        System.out.println("setEvidenceRequests");
        List<EvidenceRequest> evidenceRequests = null;
        ExtractionPlanDetail instance = new ExtractionPlanDetail();
        instance.setEvidenceRequests(evidenceRequests);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidences() {
        System.out.println("getEvidences");
        ExtractionPlanDetail instance = new ExtractionPlanDetail();
        List expResult = null;
        List result = instance.getEvidences();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidences() {
        System.out.println("setEvidences");
        List<Evidence> evidences = null;
        ExtractionPlanDetail instance = new ExtractionPlanDetail();
        instance.setEvidences(evidences);
        fail("The test case is a prototype.");
    }
}