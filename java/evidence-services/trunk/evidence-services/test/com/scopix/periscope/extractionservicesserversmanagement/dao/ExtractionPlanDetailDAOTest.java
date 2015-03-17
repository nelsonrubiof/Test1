/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionservicesserversmanagement.dao;

import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlan;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class ExtractionPlanDetailDAOTest {
    
    public ExtractionPlanDetailDAOTest() {
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
    public void testSaveExtractionPlanDetail() throws Exception {
        System.out.println("saveExtractionPlanDetail");
        ExtractionPlanDetail extractionPlanDetail = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        instance.saveExtractionPlanDetail(extractionPlanDetail);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlanEnable() {
        System.out.println("getExtractionPlanEnable");
        Integer evidenceExtractionServicesServerId = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        ExtractionPlan expResult = null;
        ExtractionPlan result = instance.getExtractionPlanEnable(evidenceExtractionServicesServerId);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlanByEvidenceExtractionServicesServer() {
        System.out.println("getExtractionPlanByEvidenceExtractionServicesServer");
        Integer evidenceExtractionServicesServerId = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        ExtractionPlan expResult = null;
        ExtractionPlan result = instance.getExtractionPlanByEvidenceExtractionServicesServer(evidenceExtractionServicesServerId);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetDao() {
        System.out.println("getDao");
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        GenericDAO expResult = null;
        GenericDAO result = instance.getDao();
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testSetDao() {
        System.out.println("setDao");
        GenericDAO dao = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        instance.setDao(dao);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetExtractionPlanByExpirationDate() {
        System.out.println("getExtractionPlanByExpirationDate");
        Date dateStart = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        List expResult = null;
        List result = instance.getExtractionPlanByExpirationDate(dateStart);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetEvidenceRequestByProviderIds() {
        System.out.println("getEvidenceRequestByProviderIds");
        Set<Integer> providerIds = null;
        String storeName = "";
        Date dateStart = null;
        Date dateEnd = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        Map expResult = null;
        Map result = instance.getEvidenceRequestByProviderIds(providerIds, storeName, dateStart, dateEnd);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    @Test
    public void testDeleteAutomaticEvidence() throws Exception {
        System.out.println("deleteAutomaticEvidence");
        Integer evidenceId = null;
        Integer extractionPlanDetailId = null;
        ExtractionPlanDetailDAO instance = new ExtractionPlanDetailDAO();
        instance.deleteAutomaticEvidence(evidenceId, extractionPlanDetailId);
        fail("The test case is a prototype.");
    }
}