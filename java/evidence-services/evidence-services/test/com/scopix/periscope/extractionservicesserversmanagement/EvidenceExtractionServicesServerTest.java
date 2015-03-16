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
public class EvidenceExtractionServicesServerTest {
    
    public EvidenceExtractionServicesServerTest() {
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
    public void testGetName() {
        System.out.println("getName");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setName(name);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getUrl();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        String url = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setUrl(url);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceProviders() {
        System.out.println("getEvidenceProviders");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        List expResult = null;
        List result = instance.getEvidenceProviders();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetEvidenceProviders() {
        System.out.println("setEvidenceProviders");
        List<EvidenceProvider> evidenceProviders = null;
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setEvidenceProviders(evidenceProviders);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetServerId() {
        System.out.println("getServerId");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        Integer expResult = null;
        Integer result = instance.getServerId();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetServerId() {
        System.out.println("setServerId");
        Integer serverId = null;
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setServerId(serverId);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSshAddress() {
        System.out.println("getSshAddress");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getSshAddress();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSshAddress() {
        System.out.println("setSshAddress");
        String sshAddress = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setSshAddress(sshAddress);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSshPort() {
        System.out.println("getSshPort");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getSshPort();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSshPort() {
        System.out.println("setSshPort");
        String sshPort = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setSshPort(sshPort);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSshLocalTunnelPort() {
        System.out.println("getSshLocalTunnelPort");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getSshLocalTunnelPort();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSshLocalTunnelPort() {
        System.out.println("setSshLocalTunnelPort");
        String sshLocalTunnelPort = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setSshLocalTunnelPort(sshLocalTunnelPort);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSshRemoteTunnelPort() {
        System.out.println("getSshRemoteTunnelPort");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getSshRemoteTunnelPort();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSshRemoteTunnelPort() {
        System.out.println("setSshRemoteTunnelPort");
        String sshRemoteTunnelPort = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setSshRemoteTunnelPort(sshRemoteTunnelPort);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSshPassword() {
        System.out.println("getSshPassword");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getSshPassword();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSshPassword() {
        System.out.println("setSshPassword");
        String sshPassword = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setSshPassword(sshPassword);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetSshUser() {
        System.out.println("getSshUser");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        String expResult = "";
        String result = instance.getSshUser();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetSshUser() {
        System.out.println("setSshUser");
        String sshUser = "";
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setSshUser(sshUser);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetIdAtBusinessServices() {
        System.out.println("getIdAtBusinessServices");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        Integer expResult = null;
        Integer result = instance.getIdAtBusinessServices();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetIdAtBusinessServices() {
        System.out.println("setIdAtBusinessServices");
        Integer idAtBusinessServices = null;
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setIdAtBusinessServices(idAtBusinessServices);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlans() {
        System.out.println("getExtractionPlans");
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        List expResult = null;
        List result = instance.getExtractionPlans();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetExtractionPlans() {
        System.out.println("setExtractionPlans");
        List<ExtractionPlan> extractionPlans = null;
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.setExtractionPlans(extractionPlans);
        fail("The test case is a prototype.");
    }

    @Test
    public void testAddExtractionPlan() {
        System.out.println("addExtractionPlan");
        ExtractionPlan ep = null;
        EvidenceExtractionServicesServer instance = new EvidenceExtractionServicesServer();
        instance.addExtractionPlan(ep);
        fail("The test case is a prototype.");
    }
}