/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement;

import java.util.HashMap;
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
public class EvidenceProviderTest {
    
    public EvidenceProviderTest() {
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
    public void testGetDescription() {
        System.out.println("getDescription");
        EvidenceProvider instance = new EvidenceProvider();
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String description = "";
        EvidenceProvider instance = new EvidenceProvider();
        instance.setDescription(description);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceExtractionServicesServer() {
        System.out.println("getEvidenceExtractionServicesServer");
        EvidenceProvider instance = new EvidenceProvider();
        EvidenceExtractionServicesServer expResult = null;
        EvidenceExtractionServicesServer result = instance.getEvidenceExtractionServicesServer();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceExtractionServicesServer() {
        System.out.println("setEvidenceExtractionServicesServer");
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = null;
        EvidenceProvider instance = new EvidenceProvider();
        instance.setEvidenceExtractionServicesServer(evidenceExtractionServicesServer);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDefinitionData() {
        System.out.println("getDefinitionData");
        EvidenceProvider instance = new EvidenceProvider();
        HashMap expResult = null;
        HashMap result = instance.getDefinitionData();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDefinitionData() {
        System.out.println("setDefinitionData");
        HashMap<String, String> definitionData = null;
        EvidenceProvider instance = new EvidenceProvider();
        instance.setDefinitionData(definitionData);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProviderType() {
        System.out.println("getProviderType");
        EvidenceProvider instance = new EvidenceProvider();
        String expResult = "";
        String result = instance.getProviderType();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetProviderType() {
        System.out.println("setProviderType");
        String providerType = "";
        EvidenceProvider instance = new EvidenceProvider();
        instance.setProviderType(providerType);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDeviceId() {
        System.out.println("getDeviceId");
        EvidenceProvider instance = new EvidenceProvider();
        Integer expResult = null;
        Integer result = instance.getDeviceId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDeviceId() {
        System.out.println("setDeviceId");
        Integer deviceId = null;
        EvidenceProvider instance = new EvidenceProvider();
        instance.setDeviceId(deviceId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceProviderRequests() {
        System.out.println("getEvidenceProviderRequests");
        EvidenceProvider instance = new EvidenceProvider();
        List expResult = null;
        List result = instance.getEvidenceProviderRequests();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceProviderRequests() {
        System.out.println("setEvidenceProviderRequests");
        List<EvidenceProviderRequest> evidenceProviderRequests = null;
        EvidenceProvider instance = new EvidenceProvider();
        instance.setEvidenceProviderRequests(evidenceProviderRequests);
        fail("The test case is a prototype.");
    }

    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        EvidenceProvider o = null;
        EvidenceProvider instance = new EvidenceProvider();
        int expResult = 0;
        int result = instance.compareTo(o);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}