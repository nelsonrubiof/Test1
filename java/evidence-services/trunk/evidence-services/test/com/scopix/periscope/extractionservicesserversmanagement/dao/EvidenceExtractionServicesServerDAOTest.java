/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import com.scopix.periscope.evidencemanagement.ProcessEES;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
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
public class EvidenceExtractionServicesServerDAOTest {
    
    public EvidenceExtractionServicesServerDAOTest() {
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
    public void testGetEvidenceExtractionServicesServerByEvidenceExtractionServicesIdInBusinessServices() {
        System.out.println("getEvidenceExtractionServicesServerByEvidenceExtractionServicesIdInBusinessServices");
        Integer eessId = null;
        String storeName = "";
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        EvidenceExtractionServicesServer expResult = null;
        EvidenceExtractionServicesServer result = instance.getEvidenceExtractionServicesServerByEvidenceExtractionServicesIdInBusinessServices(eessId, storeName);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceExtractionServicesServerByServerId() {
        System.out.println("getEvidenceExtractionServicesServerByServerId");
        Integer serverId = null;
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        EvidenceExtractionServicesServer expResult = null;
        EvidenceExtractionServicesServer result = instance.getEvidenceExtractionServicesServerByServerId(serverId);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetAllEvidenceExtractionServicesServerEnabled() {
        System.out.println("getAllEvidenceExtractionServicesServerEnabled");
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        List expResult = null;
        List result = instance.getAllEvidenceExtractionServicesServerEnabled();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceRequestEnabled() {
        System.out.println("getEvidenceRequestEnabled");
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        List expResult = null;
        List result = instance.getEvidenceRequestEnabled();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetRemoteEvidenceRequestIdList() {
        System.out.println("getRemoteEvidenceRequestIdList");
        List<Integer> ids = null;
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        List expResult = null;
        List result = instance.getRemoteEvidenceRequestIdList(ids);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetProcessEES() {
        System.out.println("getProcessEES");
        Integer extractionPlanId = null;
        Integer eesProcessId = null;
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        ProcessEES expResult = null;
        ProcessEES result = instance.getProcessEES(extractionPlanId, eesProcessId);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetNextProcessIdAutoGenerado() {
        System.out.println("getNextProcessIdAutoGenerado");
        EvidenceExtractionServicesServerDAO instance = new EvidenceExtractionServicesServerDAO();
        Integer expResult = null;
        Integer result = instance.getNextProcessIdAutoGenerado();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}