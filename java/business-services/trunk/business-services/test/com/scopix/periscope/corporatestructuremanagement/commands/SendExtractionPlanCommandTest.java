/*
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * SendExtractionPlanCommandTest.java
 * 
 * Created on 14-09-2010, 04:27:07 PM
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mockup.CorporateStructureManagementHibernateDAOMock;
import mockup.EvidenceServicesWebServiceMock;
import mockup.GenericDAOMockup;
import mockup.ManagerMock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceProviderDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.ExtractionPlanDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.ExtractionPlanDetailDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.MetricRequestDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationRequestDTO;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;

/**
 *
 * @author desarrollo
 */
@Ignore
public class SendExtractionPlanCommandTest {

    public SendExtractionPlanCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("SendExtractionPlanCommandTest");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateExtractionPlanDTO() throws Exception {
        System.out.println("createExtractionPlanDTO");
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = ManagerMock.genEvidenceExtractionServicesServer(1,
                "Server Mock 1", null, null, null, null, null, null, null);
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        instance.setDaoCorporateStructureManager(new CorporateStructureManagementHibernateDAOMock());
        instance.setGenericDao(new GenericDAOMockup());
        ExtractionPlanDTO expResult = new ExtractionPlanDTO();
        expResult.setStoreName("Store Mock 1");
        expResult.setExtractionPlanDetails(new ArrayList<ExtractionPlanDetailDTO>());

        expResult.getExtractionPlanDetails().add(new ExtractionPlanDetailDTO());
        ExtractionPlanManager manager = ManagerMock.genWizardManager();
        instance.setWizardManager(manager);

        Store s = ManagerMock.genStore(1, "Store Mock 1", "store test");
        ExtractionPlanDTO result = instance.createExtractionPlanDTO(evidenceExtractionServicesServer, s);

        assertEquals(expResult.getExtractionPlanDetails().size(), result.getExtractionPlanDetails().size());
        assertEquals(expResult.getStoreName(), result.getStoreName());
    }

    @Test
    public void testCreateEvidenceProviderDTOs() {
        System.out.println("createEvidenceProviderDTOs");
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = ManagerMock.genEvidenceExtractionServicesServer(1,
                "Server Mock 1", null, null, null, null, null, null, null);
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        instance.setEvidenceProviders(ManagerMock.genListaEvidenceProvider(2, ManagerMock.genEvidenceProviderType()));
        List<EvidenceProviderDTO> expResult = new ArrayList<EvidenceProviderDTO>();
        expResult.add(new EvidenceProviderDTO());
        expResult.add(new EvidenceProviderDTO());

        List<EvidenceProviderDTO> result = instance.createEvidenceProviderDTOs(evidenceExtractionServicesServer);
        assertEquals(expResult.size(), result.size());

    }

    @Test
    public void testCreateSituationRequestDTOs() {
        System.out.println("createSituationRequestDTOs");
        Store store = ManagerMock.genStore(1, "Store mock 1", "Store");
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        instance.setEvidenceProviders(ManagerMock.genListaEvidenceProvider(2, ManagerMock.genEvidenceProviderType()));
        ExtractionPlanManager manager = ManagerMock.genWizardManager();
        instance.setWizardManager(manager);

        List<SituationRequestDTO> expResult = new ArrayList<SituationRequestDTO>();
        List<SituationRequestDTO> result = instance.createNewSituationRequestDTOs(store);
        assertEquals(expResult.size(), result.size());

    }

    @Test
    public void testCreateSituationRequestDTOs2() {
        System.out.println("createSituationRequestDTOs2");
        Store store = ManagerMock.genStore(1, "Store mock 1", "Store");
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        instance.setEvidenceProviders(ManagerMock.genListaEvidenceProvider(2, ManagerMock.genEvidenceProviderType()));
        ExtractionPlanManager manager = ManagerMock.genWizardManager2();
        instance.setWizardManager(manager);

        List<SituationRequestDTO> expResult = new ArrayList<SituationRequestDTO>();
        // expResult.add(new SituationRequestDTO());
        // se espera que se cree una Situacion para la configuracion ingresada
        List<SituationRequestDTO> result = instance.createNewSituationRequestDTOs(store);
        assertEquals(expResult.size(), result.size());

    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");
        EvidenceExtractionServicesServer evidenceExtractionServicesServer = new EvidenceExtractionServicesServer();
        evidenceExtractionServicesServer.setId(1);
        EvidenceServicesWebServiceMock evidenceServicesWebService = new EvidenceServicesWebServiceMock();
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        instance.setEvidenceProviders(ManagerMock.genListaEvidenceProvider(2, ManagerMock.genEvidenceProviderType()));
        instance.setGenericDao(new GenericDAOMockup());
        instance.setWizardManager(ManagerMock.genWizardManager2());
        instance.setDaoCorporateStructureManager(new CorporateStructureManagementHibernateDAOMock());
        // con la configuracion generada se espera que exista soli 2 DTO en la lista
        Store s = ManagerMock.genStore(1, "store test", "store test");
        instance.execute(evidenceExtractionServicesServer, evidenceServicesWebService, s);
        assertEquals(1, evidenceServicesWebService.getEessdtos().size());
    }

    @Test
    public void testGetSituationRequestDTO() {
        System.out.println("getSituationRequestDTO");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCUYSensores();
        List<SituationRequestDTO> situationRequestDTOs = ManagerMock.genListaSituationRequestDTOs(1);
        ExtractionPlanRange epr = epc.getExtractionPlanRanges().get(0);
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        SituationRequestDTO expResult = ManagerMock.genSituationRequestDTO(1, 30, 5);

        // encuentra SituationRequestDTO en la lista situationRequestDTOs
        SituationRequestDTO result = instance.getSituationRequestDTO(epc, situationRequestDTOs, epr);

        assertEquals(expResult.getSituationTemplateId(), result.getSituationTemplateId());
        assertEquals(expResult.getDuration(), result.getDuration());
        assertEquals(expResult.getFrecuency(), result.getFrecuency());

    }

    @Test
    public void testGetSituationRequestDTO2() {
        System.out.println("getSituationRequestDTO2");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCUYSensores();
        List<SituationRequestDTO> situationRequestDTOs = ManagerMock.genListaSituationRequestDTOs(0);
        ExtractionPlanRange epr = epc.getExtractionPlanRanges().get(0);
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();

        SituationRequestDTO expResult = ManagerMock.genSituationRequestDTO(epc.getSituationTemplate().getId(),
                epr.getFrecuency(), epr.getDuration());

        // no encuentra la Situacion en la lista situationRequestDTOs
        SituationRequestDTO result = instance.getSituationRequestDTO(epc, situationRequestDTOs, epr);

        assertEquals(expResult.getSituationTemplateId(), result.getSituationTemplateId());
        assertEquals(expResult.getDuration(), result.getDuration());
        assertEquals(expResult.getFrecuency(), result.getFrecuency());

    }

    @Test
    public void testGetMetricRequestDTO() {
        System.out.println("getMetricRequestDTO");
        ExtractionPlanMetric epm = ManagerMock.genExtractionPlanMetricSoloMT();
        epm.setId(1);
        SituationRequestDTO situationRequestDTO = ManagerMock.genSituationRequestDTO(1, 30, 5);
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        MetricRequestDTO expResult = new MetricRequestDTO();
        expResult.setMetricTemplateId(epm.getId());

        // no lo encuentra en los dtos del situationRequestDTO
        MetricRequestDTO result = instance.getMetricRequestDTO(epm, situationRequestDTO);

        assertEquals(expResult.getMetricTemplateId(), result.getMetricTemplateId());
    }

    @Test
    public void testGetMetricRequestDTO2() {
        System.out.println("getMetricRequestDTO2");
        ExtractionPlanMetric epm = ManagerMock.genExtractionPlanMetricSoloMT();
        SituationRequestDTO situationRequestDTO = ManagerMock.genSituationRequestDTOConMetricRequestDTO(1, 30, 5, 1);

        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        MetricRequestDTO expResult = ManagerMock.genMetricRequestDTO(1);

        // encuentra MetricRequestDTO en los dtos del situationRequestDTO
        MetricRequestDTO result = instance.getMetricRequestDTO(epm, situationRequestDTO);
        assertEquals(expResult.getMetricTemplateId(), result.getMetricTemplateId());
    }

    @Test
    public void testAddEvidenceProviderDtoToMetricRequestDto() {
        System.out.println("addEvidenceProviderDtoToMetricRequestDto");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        MetricRequestDTO metricRequestDTO = ManagerMock.genMetricRequestDTO(1);
        SendExtractionPlanCommand instance = new SendExtractionPlanCommand();
        instance.setEvidenceProviders(ManagerMock.genListaEvidenceProvider(2, null));

        // se espera que se generen 2 EvidenceProviderRequestDTO dentro del metricRequestDTO
        instance.addEvidenceProviderDtoToMetricRequestDto(epc, metricRequestDTO);
        assertEquals(2, metricRequestDTO.getEvidenceProviderRequestDTOs().size());
    }
}
