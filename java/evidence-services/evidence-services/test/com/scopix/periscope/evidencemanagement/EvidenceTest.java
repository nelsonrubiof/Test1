/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement;

import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import java.util.Date;
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
public class EvidenceTest {
    
    public EvidenceTest() {
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
    public void testGetExtractionPlanDetail() {
        System.out.println("getExtractionPlanDetail");
        Evidence instance = new Evidence();
        ExtractionPlanDetail expResult = null;
        ExtractionPlanDetail result = instance.getExtractionPlanDetail();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlanDetail() {
        System.out.println("setExtractionPlanDetail");
        ExtractionPlanDetail extractionPlanDetail = null;
        Evidence instance = new Evidence();
        instance.setExtractionPlanDetail(extractionPlanDetail);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceType() {
        System.out.println("getEvidenceType");
        Evidence instance = new Evidence();
        int expResult = 0;
        int result = instance.getEvidenceType();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceType() {
        System.out.println("setEvidenceType");
        int evidenceType = 0;
        Evidence instance = new Evidence();
        instance.setEvidenceType(evidenceType);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceUri() {
        System.out.println("getEvidenceUri");
        Evidence instance = new Evidence();
        String expResult = "";
        String result = instance.getEvidenceUri();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceUri() {
        System.out.println("setEvidenceUri");
        String evidenceUri = "";
        Evidence instance = new Evidence();
        instance.setEvidenceUri(evidenceUri);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceTimestamp() {
        System.out.println("getEvidenceTimestamp");
        Evidence instance = new Evidence();
        Date expResult = null;
        Date result = instance.getEvidenceTimestamp();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceTimestamp() {
        System.out.println("setEvidenceTimestamp");
        Date evidenceTimestamp = null;
        Evidence instance = new Evidence();
        instance.setEvidenceTimestamp(evidenceTimestamp);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDeviceId() {
        System.out.println("getDeviceId");
        Evidence instance = new Evidence();
        Integer expResult = null;
        Integer result = instance.getDeviceId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDeviceId() {
        System.out.println("setDeviceId");
        Integer deviceId = null;
        Evidence instance = new Evidence();
        instance.setDeviceId(deviceId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProcessId() {
        System.out.println("getProcessId");
        Evidence instance = new Evidence();
        Integer expResult = null;
        Integer result = instance.getProcessId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetProcessId() {
        System.out.println("setProcessId");
        Integer processId = null;
        Evidence instance = new Evidence();
        instance.setProcessId(processId);
        fail("The test case is a prototype.");
    }
}