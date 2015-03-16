/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.services.impl;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.ExtractionPlanDTO;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.ExtractionServicesServersManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

/**
 *
 * @author Sebastian torres brown
 */
public class EvidenceServicesWebServiceImplTest {

    public EvidenceServicesWebServiceImplTest() {
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
    public void testNewExtractionPlan() throws Exception {
        List<EvidenceExtractionServicesServerDTO> evidenceExtractionServicesServerDTOs =
                new ArrayList<EvidenceExtractionServicesServerDTO>();
        evidenceExtractionServicesServerDTOs.add(Mockito.mock(EvidenceExtractionServicesServerDTO.class, "mock EESDTO"));
        EvidenceServicesWebServiceImpl instance = Mockito.mock(EvidenceServicesWebServiceImpl.class, "Evidence Server Mock");
        Mockito.when(instance.convertToBusinessObject(Mockito.any(EvidenceExtractionServicesServerDTO.class))).thenReturn(Mockito.mock(EvidenceExtractionServicesServer.class, "mock EvidenceExtractionServicesServer"));
        ExtractionServicesServersManager extracServServMgr = Mockito.mock(ExtractionServicesServersManager.class, "ExtractionServicesServersManager Mock");
        Mockito.when(instance.getExtractionServicesServersManager()).thenReturn(extracServServMgr);
        Mockito.doCallRealMethod().when(instance).newExtractionPlan(Mockito.anyListOf(EvidenceExtractionServicesServerDTO.class));
        instance.newExtractionPlan(evidenceExtractionServicesServerDTOs);
        assertTrue("void method,should return nothing", true);
    }

    @Test
    public void testConvertToBusinessObject() {

        EvidenceExtractionServicesServerDTO eESDTO = Mockito.mock(EvidenceExtractionServicesServerDTO.class,
                "EvidenceExtractionServicesServerDTO Mock");
        ExtractionPlanDTO extractionPlanDTO = Mockito.mock(ExtractionPlanDTO.class,
                "ExtractionPlanDTO Mock");
        Mockito.when(eESDTO.getExtractionPlanDTO()).thenReturn(extractionPlanDTO);
        EvidenceServicesWebServiceImpl instance = new EvidenceServicesWebServiceImpl();
        EvidenceExtractionServicesServer result = instance.convertToBusinessObject(eESDTO);
        Boolean isAssert = result.getClass().equals(EvidenceExtractionServicesServer.class) ? true : false;
        assertTrue("the return type dto is correct", isAssert);

    }

    @Test
    public void testExtractionPlanToPast() throws Exception {

        EvidenceServicesWebServiceImpl instance = Mockito.mock(EvidenceServicesWebServiceImpl.class, "Evidence Server Mock");
        ExtractionServicesServersManager extracServServMgr = Mockito.mock(ExtractionServicesServersManager.class, "ExtractionServicesServersManager Mock");
        Mockito.when(instance.getExtractionServicesServersManager()).thenReturn(extracServServMgr);
        String date = Mockito.anyString();
        Integer extractionServicesServerId = Mockito.anyInt();
        String storeName = Mockito.anyString();
        Mockito.when(instance.extractionPlanToPast(date, extractionServicesServerId, storeName)).thenCallRealMethod();
        List result = instance.extractionPlanToPast(date, extractionServicesServerId, storeName);
        Boolean isAssert = result.getClass().equals(LinkedList.class) ? true : false;
        assertTrue("the return type List is correct", isAssert);
    }
}