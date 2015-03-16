/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

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
public class MetricRequestTest {
    
    public MetricRequestTest() {
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
        MetricRequest o = null;
        MetricRequest instance = new MetricRequest();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetMetricTemplateId() {
        System.out.println("getMetricTemplateId");
        MetricRequest instance = new MetricRequest();
        Integer expResult = null;
        Integer result = instance.getMetricTemplateId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetMetricTemplateId() {
        System.out.println("setMetricTemplateId");
        Integer metricTemplateId = null;
        MetricRequest instance = new MetricRequest();
        instance.setMetricTemplateId(metricTemplateId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceProviderRequests() {
        System.out.println("getEvidenceProviderRequests");
        MetricRequest instance = new MetricRequest();
        List expResult = null;
        List result = instance.getEvidenceProviderRequests();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceProviderRequests() {
        System.out.println("setEvidenceProviderRequests");
        List<EvidenceProviderRequest> evidenceProviderRequests = null;
        MetricRequest instance = new MetricRequest();
        instance.setEvidenceProviderRequests(evidenceProviderRequests);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSituationRequest() {
        System.out.println("getSituationRequest");
        MetricRequest instance = new MetricRequest();
        SituationRequest expResult = null;
        SituationRequest result = instance.getSituationRequest();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSituationRequest() {
        System.out.println("setSituationRequest");
        SituationRequest situationRequest = null;
        MetricRequest instance = new MetricRequest();
        instance.setSituationRequest(situationRequest);
        fail("The test case is a prototype.");
    }
}