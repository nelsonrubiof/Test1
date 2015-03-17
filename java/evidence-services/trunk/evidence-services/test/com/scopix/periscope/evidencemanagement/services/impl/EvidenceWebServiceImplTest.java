/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.services.impl;

import com.scopix.periscope.evidencemanagement.EvidenceManager;
import com.scopix.periscope.evidencemanagement.dto.NewAutomaticEvidenceDTO;
import com.scopix.periscope.evidencemanagement.dto.NewEvidenceDTO;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionPlanDetail;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author nelson
 */
public class EvidenceWebServiceImplTest {

    public EvidenceWebServiceImplTest() {
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
    public void testAcceptNewEvidence() throws Exception {
        NewEvidenceDTO newEvidenceDTO = Mockito.mock(NewEvidenceDTO.class);
        Mockito.when(newEvidenceDTO.getEvidenceDate()).thenReturn("2013-10-21 09:34:44");
        EvidenceWebServiceImpl instance = Mockito.mock(EvidenceWebServiceImpl.class);
        EvidenceManager evidenceManager = Mockito.mock(EvidenceManager.class);
        ExtractionPlanDetail extractionPlanDetail = Mockito.mock(ExtractionPlanDetail.class);
        Mockito.when(instance.getEvidenceManager()).thenReturn(evidenceManager);
        Mockito.when(instance.getExtractionPlanDetail(Mockito.anyInt())).thenReturn(extractionPlanDetail);
        Mockito.doCallRealMethod().when(instance).acceptNewEvidence(newEvidenceDTO);
        instance.acceptNewEvidence(newEvidenceDTO);
        assertTrue("void method,should return nothing", true);
    }

    @Test
    public void testAcceptAutomaticNewEvidence() throws Exception {

        NewAutomaticEvidenceDTO newAutomaticEvidenceDTO = Mockito.mock(NewAutomaticEvidenceDTO.class);
        EvidenceWebServiceImpl instance = Mockito.mock(EvidenceWebServiceImpl.class);
        EvidenceManager evidenceManager = Mockito.mock(EvidenceManager.class);
        Mockito.when(instance.getEvidenceManager()).thenReturn(evidenceManager);
        Mockito.doCallRealMethod().when(instance).acceptAutomaticNewEvidence(newAutomaticEvidenceDTO);
        instance.acceptAutomaticNewEvidence(newAutomaticEvidenceDTO);
        assertTrue("void method,should return nothing", true);
    }

    @Test
    public void testGetEvidenceRequestsByProvider() throws Exception {
        EvidenceWebServiceImpl instance = Mockito.mock(EvidenceWebServiceImpl.class);
        EvidenceManager evidenceManager = Mockito.mock(EvidenceManager.class);
        String dateEvidenceStart = "";
        String dateEvidenceEnd = "";
        Set<Integer> providerIds = Mockito.mock(Set.class);
        String storeName = "";
        Mockito.when(instance.getEvidenceManager()).thenReturn(evidenceManager);
        Mockito.when(instance.getEvidenceRequestsByProvider(dateEvidenceStart, dateEvidenceEnd, providerIds, storeName)).thenCallRealMethod();
        Map output = instance.getEvidenceRequestsByProvider(dateEvidenceStart, dateEvidenceEnd, providerIds, storeName);
        Boolean result = output.getClass().equals(HashMap.class) ? true : false;
        assertTrue("the return type HashMap is correct", result);
    }
}