/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement;

import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
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
public class ProcessEESTest {
    
    public ProcessEESTest() {
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
        ProcessEES instance = new ProcessEES();
        ExtractionPlan expResult = null;
        ExtractionPlan result = instance.getExtractionPlan();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlan() {
        System.out.println("setExtractionPlan");
        ExtractionPlan extractionPlan = null;
        ProcessEES instance = new ProcessEES();
        instance.setExtractionPlan(extractionPlan);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProcessIdEes() {
        System.out.println("getProcessIdEes");
        ProcessEES instance = new ProcessEES();
        Integer expResult = null;
        Integer result = instance.getProcessIdEes();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetProcessIdEes() {
        System.out.println("setProcessIdEes");
        Integer processIdEes = null;
        ProcessEES instance = new ProcessEES();
        instance.setProcessIdEes(processIdEes);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProcessIdLocal() {
        System.out.println("getProcessIdLocal");
        ProcessEES instance = new ProcessEES();
        Integer expResult = null;
        Integer result = instance.getProcessIdLocal();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetProcessIdLocal() {
        System.out.println("setProcessIdLocal");
        Integer processIdLocal = null;
        ProcessEES instance = new ProcessEES();
        instance.setProcessIdLocal(processIdLocal);
        fail("The test case is a prototype.");
    }
}