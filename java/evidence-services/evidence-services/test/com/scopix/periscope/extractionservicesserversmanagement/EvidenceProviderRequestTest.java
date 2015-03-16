/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

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
public class EvidenceProviderRequestTest {
    
    public EvidenceProviderRequestTest() {
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
    public void testCompareTo() {
        System.out.println("compareTo");
        EvidenceProviderRequest o = null;
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDeviceId() {
        System.out.println("getDeviceId");
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        Integer expResult = null;
        Integer result = instance.getDeviceId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDeviceId() {
        System.out.println("setDeviceId");
        Integer deviceId = null;
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        instance.setDeviceId(deviceId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceProvider() {
        System.out.println("getEvidenceProvider");
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        EvidenceProvider expResult = null;
        EvidenceProvider result = instance.getEvidenceProvider();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceProvider() {
        System.out.println("setEvidenceProvider");
        EvidenceProvider evidenceProvider = null;
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        instance.setEvidenceProvider(evidenceProvider);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetMetricRequest() {
        System.out.println("getMetricRequest");
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        MetricRequest expResult = null;
        MetricRequest result = instance.getMetricRequest();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetMetricRequest() {
        System.out.println("setMetricRequest");
        MetricRequest metricRequest = null;
        EvidenceProviderRequest instance = new EvidenceProviderRequest();
        instance.setMetricRequest(metricRequest);
        fail("The test case is a prototype.");
    }
}