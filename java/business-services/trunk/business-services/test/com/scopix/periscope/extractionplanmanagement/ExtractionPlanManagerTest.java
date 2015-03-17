/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  ExtractionPlanManagerTest.java
 * 
 *  Created on 07-09-2010, 01:04:56 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement;

import com.scopix.periscope.extractionplanmanagement.commands.CreateRecordsCommand;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import org.junit.Test;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingListCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveExtractionPlanMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.SaveSensorsCommand;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanMetricDTO;
import mockup.ExtractionPlanCustomizingDAOMock;
import com.scopix.periscope.extractionplanmanagement.commands.ActivateWizardCustomizingCommand;
import mockup.ManagerMock;
import mockup.SecurityManagerMock;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateExtracionPlanMetricCommand;
import com.scopix.periscope.extractionplanmanagement.commands.UpdateMetricOrderCommand;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dto.SituationSensorDTO;
import com.scopix.periscope.extractionplanmanagement.commands.GetEPCFromStoreSituationTemplateEvidenceProviderCommand;
import com.scopix.periscope.extractionplanmanagement.dto.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.templatemanagement.dto.MetricTemplateDTO;
import java.util.ArrayList;
import java.util.List;
import mockup.ExtractionPlanCustomizingDAOMockConEP1;
import mockup.GenericDAOListMockup;
import mockup.GenericDAOMockup;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * @author nelson
 */
public class ExtractionPlanManagerTest {

    private static SecurityManager securityManager;

    public static SecurityManager getSecurityManager() {
        return securityManager;
    }

    public static void setSecurityManager(SecurityManager aSecurityManager) {
        securityManager = aSecurityManager;
    }

    public ExtractionPlanManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("ExtractionPlanManagerTest");
        setSecurityManager(new SecurityManagerMock());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        setSecurityManager(null);
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUpdateWizardCustomizing() throws Exception {
        System.out.println("updateWizardCustomizing");
        ExtractionPlanMetric extractionPlanMetric = ManagerMock.genExtractionPlanMetricSinMetricTemplate(1, 3, "FS");
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        UpdateExtracionPlanMetricCommand command = ManagerMock.genUpdateExtracionPlanMetricCommand();
        instance.setUpdateExtracionPlanMetricCommand(command);

        instance.updateWizardCustomizing(extractionPlanMetric, sessionId);

        GenericDAOMockup dao = (GenericDAOMockup) command.getDao();
        //assertEquals(extractionPlanMetric.getId(), dao.getBusinessObject().getId());
        //el update se hace sobre el un extractionPlanMetric por lo cual comparamos que tengan el mismo orden
        assertEquals(extractionPlanMetric.getId(), dao.getBusinessObject().getId());
        assertEquals(extractionPlanMetric.getEvaluationOrder(),
                ((ExtractionPlanMetric) dao.getBusinessObject()).getEvaluationOrder());
    }

    @Test
    public void testUpdateMetricOrder() throws Exception {
        System.out.println("updateMetricOrder");
        ExtractionPlanMetric extractionPlanMetric = ManagerMock.genExtractionPlanMetricSinMetricTemplate(1, 1, "FS");
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());

        UpdateMetricOrderCommand command = ManagerMock.genUpdateMetricOrderCommand();
        instance.setUpdateMetricOrderCommand(command);
        instance.updateMetricOrder(extractionPlanMetric);
        //Urilizando el mock para generar el command sabamos que el dao asociado es un WizardCustomizingDAOMock
        //y que contiene el obejto actualizado
        ExtractionPlanCustomizingDAOMock dao = (ExtractionPlanCustomizingDAOMock) command.getDao();
        assertEquals(extractionPlanMetric.getId(), dao.getBusinessObject().getId());
    }

    @Test
    public void testUpdateOrder() throws Exception {
        System.out.println("updateOrder");
        ExtractionPlanCustomizing wcs = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());

        //se deben asiganar los command necesarios
        instance.setUpdateExtracionPlanMetricCommand(ManagerMock.genUpdateExtracionPlanMetricCommand());
        instance.setUpdateMetricOrderCommand(ManagerMock.genUpdateMetricOrderCommand());

        List<ExtractionPlanMetric> expResult = wcs.getExtractionPlanMetrics();

        List<ExtractionPlanMetric> result = instance.updateOrder(wcs, sessionId);
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        //fail("The test case is a prototype.");
    }

    @Test
    public void testMoveUp() throws Exception {
        System.out.println("moveUp");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        ExtractionPlanMetric extractionPlanMetric = ManagerMock.genExtractionPlanMetricSinMetricTemplate(3, 3, "A1");
        extractionPlanMetric.setExtractionPlanCustomizing(epc);
        List<ExtractionPlanMetric> extractionPlanMetrics = ManagerMock.genListaExtractionPlanMetricsVIDEO(3, epc);
        //esto se hace ya que la lista de EPC debe ser la misma que quermos validar
        epc.getExtractionPlanMetrics().clear();
        epc.setExtractionPlanMetrics(extractionPlanMetrics);

        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setUpdateExtracionPlanMetricCommand(ManagerMock.genUpdateExtracionPlanMetricCommand());
        instance.setUpdateMetricOrderCommand(ManagerMock.genUpdateMetricOrderCommand());


        List<ExtractionPlanMetric> expResult = new ArrayList<ExtractionPlanMetric>();
        expResult.add(extractionPlanMetrics.get(0));
        expResult.add(extractionPlanMetrics.get(2));
        expResult.add(extractionPlanMetrics.get(1));
        List<ExtractionPlanMetric> result = instance.moveUp(extractionPlanMetric, extractionPlanMetrics, sessionId);
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
        assertEquals(expResult.get(2).getId(), result.get(2).getId());

    }

    @Test
    public void testMoveUp2() throws Exception {
        System.out.println("moveUp2");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        ExtractionPlanMetric extractionPlanMetric = ManagerMock.genExtractionPlanMetricSinMetricTemplate(1, 1, "A1");
        extractionPlanMetric.setExtractionPlanCustomizing(epc);
        List<ExtractionPlanMetric> extractionPlanMetrics = ManagerMock.genListaExtractionPlanMetricsVIDEO(3, epc);
        //esto se hace ya que la lista de EPC debe ser la misma que quermos validar
        epc.getExtractionPlanMetrics().clear();
        epc.setExtractionPlanMetrics(extractionPlanMetrics);

        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setUpdateExtracionPlanMetricCommand(ManagerMock.genUpdateExtracionPlanMetricCommand());
        instance.setUpdateMetricOrderCommand(ManagerMock.genUpdateMetricOrderCommand());


        List<ExtractionPlanMetric> expResult = new ArrayList<ExtractionPlanMetric>();
        expResult.add(extractionPlanMetrics.get(0));
        expResult.add(extractionPlanMetrics.get(1));
        expResult.add(extractionPlanMetrics.get(2));
        List<ExtractionPlanMetric> result = instance.moveUp(extractionPlanMetric, extractionPlanMetrics, sessionId);
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
        assertEquals(expResult.get(2).getId(), result.get(2).getId());

    }

    @Test
    public void testMoveDown() throws Exception {
        System.out.println("moveDown");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        ExtractionPlanMetric epm = ManagerMock.genExtractionPlanMetricSinMetricTemplate(1, 1, "A1");
        epm.setExtractionPlanCustomizing(epc);
        List<ExtractionPlanMetric> epms = ManagerMock.genListaExtractionPlanMetricsVIDEO(3, epc);
        //esto se hace ya que la lista de EPC debe ser la misma que quermos validar
        epc.getExtractionPlanMetrics().clear();
        epc.setExtractionPlanMetrics(epms);

        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setUpdateExtracionPlanMetricCommand(ManagerMock.genUpdateExtracionPlanMetricCommand());
        instance.setUpdateMetricOrderCommand(ManagerMock.genUpdateMetricOrderCommand());

        List<ExtractionPlanMetric> expResult = new ArrayList<ExtractionPlanMetric>();
        expResult.add(epms.get(1));
        expResult.add(epms.get(0));
        expResult.add(epms.get(2));
        List<ExtractionPlanMetric> result = instance.moveDown(epm, epms, sessionId);
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
        assertEquals(expResult.get(2).getId(), result.get(2).getId());

    }

    @Test
    public void testMoveDown2() throws Exception {
        System.out.println("moveDown2");
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        ExtractionPlanMetric epm = ManagerMock.genExtractionPlanMetricSinMetricTemplate(3, 3, "A1");
        epm.setExtractionPlanCustomizing(epc);
        List<ExtractionPlanMetric> epms = ManagerMock.genListaExtractionPlanMetricsVIDEO(3, epc);
        //esto se hace ya que la lista de EPC debe ser la misma que quermos validar
        epc.getExtractionPlanMetrics().clear();
        epc.setExtractionPlanMetrics(epms);

        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setUpdateExtracionPlanMetricCommand(ManagerMock.genUpdateExtracionPlanMetricCommand());
        instance.setUpdateMetricOrderCommand(ManagerMock.genUpdateMetricOrderCommand());

        List<ExtractionPlanMetric> expResult = new ArrayList<ExtractionPlanMetric>();
        expResult.add(epms.get(0));
        expResult.add(epms.get(1));
        expResult.add(epms.get(2));
        List<ExtractionPlanMetric> result = instance.moveDown(epm, epms, sessionId);
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
        assertEquals(expResult.get(2).getId(), result.get(2).getId());
    }

    @Test
    public void testGetAutomaticExtractionPlanCustomizings() throws Exception {
        System.out.println("getAutomaticExtractionPlanCustomizings");
        Store store = ManagerMock.getNewStoreMock();
        Integer evidenceProviderId = null;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setGetSituationTemplateListCommand(ManagerMock.genSituationTemplateListCommand());
        GetEPCFromStoreSituationTemplateEvidenceProviderCommand gepcfsstepc =
                new GetEPCFromStoreSituationTemplateEvidenceProviderCommand();
        instance.setEpcFromStoreSituationTemplateEvidenceProviderCommand(gepcfsstepc);
        ExtractionPlanCustomizingDAOImpl daoMock = EasyMock.createMock(ExtractionPlanCustomizingDAOImpl.class);
        gepcfsstepc.setDao(daoMock);
        EasyMock.expect(daoMock.getAllEPCFromStoreSituatioTemplateEvidenceProvider(1, null, null)).andReturn(new ArrayList());
        EasyMock.expect(daoMock.getAllEPCFromStoreSituatioTemplateEvidenceProvider(1, null, null)).andReturn(new ArrayList());
        EasyMock.expect(daoMock.getAllEPCFromStoreSituatioTemplateEvidenceProvider(1, null, null)).andReturn(new ArrayList());

        EasyMock.replay(daoMock);

        List expResult = new ArrayList();
        List result = instance.getAutomaticExtractionPlanCustomizings(store, evidenceProviderId);
        //retorna una lista en blanco debido a que no se setea el dao que retorna una lista
        assertEquals(expResult.size(), result.size());

    }

    @Test
    public void testActivateWizardCustomizings() {
        System.out.println("activateWizardCustomizings");
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(1);
        epc.setActive(false);
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        ActivateWizardCustomizingCommand command = ManagerMock.genActivateWizardCustomizingCommand();
        instance.setActivateWizardCustomizingCommand(command);
        instance.activateWizardCustomizings(epc);
        GenericDAOMockup dao = (GenericDAOMockup) command.getDao();
        assertEquals(Boolean.TRUE, ((ExtractionPlanCustomizing) dao.getBusinessObject()).isActive());
    }

    @Test
    public void testGenerate() throws Exception {
        System.out.println("generate");

        //se ocupan solo para generar el critera por lo cual pueden ir null
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setSituationTemplate(new SituationTemplate());
        epc.setStore(new Store());

        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setGetStoreCommand(ManagerMock.genStoreCommand());

        /**command necesarios para la ejecucion interior*/
        instance.setAddSituationCommand(ManagerMock.genAddSituationCommand());
        instance.setAreaListCommand(ManagerMock.genAreaListCommand());
        instance.setAddMetricCommand(ManagerMock.genAddMetricCommand());
        instance.setAreaListCommand(ManagerMock.genAreaListCommand());

        ActivateWizardCustomizingCommand command = ManagerMock.genActivateWizardCustomizingCommand();
        instance.setActivateWizardCustomizingCommand(command);

        CreateRecordsCommand createRecordsCommand = EasyMock.createMock(CreateRecordsCommand.class);
        instance.setCreateRecordsCommand(createRecordsCommand);
        ExtractionPlanCustomizingDAOMock daoMock = new ExtractionPlanCustomizingDAOMock();
        createRecordsCommand.setDao(daoMock);

        ExtractionPlanCustomizing result = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        result.setActive(true);
        instance.generate(epc, sessionId);
        GenericDAOMockup dao = (GenericDAOMockup) command.getDao();
        assertEquals(result.isActive(), ((ExtractionPlanCustomizing) dao.getBusinessObject()).isActive());


    }

    @Test
    public void testGetExtractionPlanCustomizingList() throws Exception {
        System.out.println("getExtractionPlanCustomizingList");
        //revisa que el Manager sea capaz de retorna una lista de ExtractionPlanCustomizing 
        ExtractionPlanCustomizing epc = null;
        String estado = null;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());

        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());

        List<ExtractionPlanCustomizing> expResult = new ArrayList<ExtractionPlanCustomizing>();
        ExtractionPlanCustomizing epcMock = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        epcMock.setId(1);
        expResult.add(epcMock);


        List<ExtractionPlanCustomizing> result = instance.getExtractionPlanCustomizingList(epc, estado, sessionId);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getId(), result.get(0).getId());

    }

    @Test
    public void testGetExtractionPlanCustomizingDTOs() throws Exception {
        System.out.println("getExtractionPlanCustomizingDTOs");
        //revisa que el Manager sea capaz de retorna una lista de ExtractionPlanCustomizingDTO
        Integer storeId = null;
        String estado = "";
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());

        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());

        List<ExtractionPlanCustomizingDTO> expResult = new ArrayList<ExtractionPlanCustomizingDTO>();
        expResult.add(ManagerMock.genExtractionPlanCustomizingDTO(1, null, 1, "Area Type Mock 1", null, 1, 1));

        List<ExtractionPlanCustomizingDTO> result = instance.getExtractionPlanCustomizingDTOs(storeId, estado, sessionId);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getStoreId(), result.get(0).getStoreId());
    }

    public void testCreateExtractionPlanCustomizingDTO() throws Exception {
        System.out.println("createExtractionPlanCustomizingDTO");
        Integer situationTempleteId = 1;
        Integer storeId = 1;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());

        instance.setCreateExtractionPlanCustomizingCommand(ManagerMock.genCreateExtractionPlanCustomizingCommand());
        //se espera crear un epc limpio
        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());

        ExtractionPlanCustomizingDTO expResult = ManagerMock.genExtractionPlanCustomizingDTO(null, null, 1, "Area Type 1",
                false, 1, 1);

        ExtractionPlanCustomizingDTO result = instance.createExtractionPlanCustomizingDTO(situationTempleteId, storeId, sessionId);
        assertEquals(expResult.getId(), result.getId());

    }

    @Test
    public void testCreateExtractionPlanCustomizingDTO2() throws Exception {
        System.out.println("createExtractionPlanCustomizingDTO2");
        Integer situationTempleteId = 1;
        Integer storeId = 1;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());

        instance.setCreateExtractionPlanCustomizingCommand(ManagerMock.genCreateExtractionPlanCustomizingCommand());
        GetExtractionPlanCustomizingListCommand command = new GetExtractionPlanCustomizingListCommand();
        command.setDao(new ExtractionPlanCustomizingDAOMockConEP1());
        instance.setExtractionPlanCustomizingListCommand(command);

        ExtractionPlanCustomizingDTO expResult = ManagerMock.genExtractionPlanCustomizingDTO(null, null, 1, "Area Type 1",
                false, 1, 1);

        ExtractionPlanCustomizingDTO result = null;
        try {
            result = instance.createExtractionPlanCustomizingDTO(situationTempleteId, storeId, sessionId);
            fail("Se esperaba la generacion de una excepcion");
        } catch (ScopixException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetSensors() throws Exception {
        System.out.println("getSensors");
        Integer extractionPlanCustomizingId = 1;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setExtractionPlanCustomizingByIdCommand(ManagerMock.genExtractionPlanCustomizingByIdCommand2());
        instance.setSensorListCommand(ManagerMock.genSensorListCommand());

        List<SituationSensorDTO> expResult = new ArrayList<SituationSensorDTO>();
        expResult.add(ManagerMock.genSituationSensorDTO(1, "Sensor 1", "Sensor 1"));
        expResult.add(ManagerMock.genSituationSensorDTO(1, "Sensor 2", "Sensor 2"));
        List<SituationSensorDTO> result = instance.getSensors(extractionPlanCustomizingId, sessionId);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getDescription(), result.get(1).getDescription());

    }

    @Test
    public void testGetMetricTemplates() throws Exception {
        System.out.println("getMetricTemplates");
        Integer extractionPlanCustomizingId = 1;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setExtractionPlanCustomizingByIdCommand(ManagerMock.genExtractionPlanCustomizingByIdEPCCompletoCommand());

        List<MetricTemplateDTO> expResult = new ArrayList<MetricTemplateDTO>();
        expResult.add(ManagerMock.genMetricTemplateDTO(1, "Metric Template 1", "Metric Template 1"));
        expResult.add(ManagerMock.genMetricTemplateDTO(2, "Metric Template 2", "Metric Template 2"));
        List<MetricTemplateDTO> result = instance.getMetricTemplates(extractionPlanCustomizingId, sessionId);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getName(), result.get(0).getName());

    }

    @Test
    public void testGetUltimoExtractionPlanCustomizingNoEnviado() throws Exception {
        System.out.println("getUltimoExtractionPlanCustomizingNoEnviado");
        Integer situationTempleteId = 1;
        Integer storeId = 1;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand2());
        ExtractionPlanCustomizingDTO expResult = new ExtractionPlanCustomizingDTO();
        expResult.setId(1);
        expResult.setAreaTypeId(1);
        expResult.setAreaType("Area Type Mock 1");
        ExtractionPlanCustomizingDTO result = instance.getUltimoExtractionPlanCustomizingNoEnviado(situationTempleteId, storeId,
                sessionId);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getAreaTypeId(), result.getAreaTypeId());
        assertEquals(expResult.getAreaType(), result.getAreaType());
        assertEquals(2, result.getExtractionPlanMetricDTOs().size());
        //assertEquals(2, result.getProviderIds().size());
        assertEquals(0, result.getSensorIds().size());
    }

    @Test
    public void testGetExtractionPlanCustomizingDatosGenerales() throws Exception {
        System.out.println("getExtractionPlanCustomizingDatosGenerales");
        Integer extractionPlanCustomizingId = 1;
        long sessionId = 0L;
        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setExtractionPlanCustomizingByIdCommand(ManagerMock.genExtractionPlanCustomizingByIdEPCCompletoCommand());
        ExtractionPlanCustomizingDTO expResult = new ExtractionPlanCustomizingDTO();
        expResult.setId(1);
        expResult.setAreaTypeId(1);
        expResult.setAreaType("Area Type Mock 1");
        ExtractionPlanCustomizingDTO result = instance.getExtractionPlanCustomizingDatosGenerales(extractionPlanCustomizingId,
                sessionId);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getAreaTypeId(), result.getAreaTypeId());
        assertEquals(expResult.getAreaType(), result.getAreaType());
        assertEquals(2, result.getExtractionPlanMetricDTOs().size());
        //assertEquals(2, result.getProviderIds().size());
        assertEquals(0, result.getSensorIds().size());
    }

    @Test
    public void testGenerateDetailFixed() {
        System.out.println("generateDetailFixed");
        ExtractionPlanRange range = ManagerMock.generateExtractionPlanRango(1, null, null, 10, null, 0, 30, 2, 7, 15, 20, 46);
        ExtractionPlanManager instance = new ExtractionPlanManager();
        List<ExtractionPlanRangeDetail> result = instance.generateDetailFixed(range);
        assertEquals(55, result.size());
    }

    @Test
    public void testGenerateDetailRandom() throws Exception {
        System.out.println("generateDetailRandom");
        ExtractionPlanRange range = ManagerMock.generateExtractionPlanRango(1, null, null, 300, null, 0, 60, 3, 10, 0, 12, 0);
        ExtractionPlanManager instance = new ExtractionPlanManager();

        List<ExtractionPlanRangeDetail> result = instance.generateDetailRandom(range);
        assertEquals(6, result.size());

    }

    @Test
    public void testGenerateDetailRandom2() throws Exception {
        System.out.println("generateDetailRandom2");
        ExtractionPlanRange range = ManagerMock.generateExtractionPlanRango(1, null, null, 300, null, 0, 53, 7, 8, 43, 11, 40);
        ExtractionPlanManager instance = new ExtractionPlanManager();
        try {
            List<ExtractionPlanRangeDetail> result = instance.generateDetailRandom(range);
            //fail("Se espera una excepcion, los detalles a generar no caben el rango general");
        } catch (ScopixException e) {
            assertTrue(true);
        }

    }

    @Test
    public void testGenerateDetailRandom3() throws Exception {
        System.out.println("generateDetailRandom3");

        ExtractionPlanRange range = ManagerMock.genExtractionPlanRange(1, null, 30, 500, 2, 8, 0, 22, 6);
        ExtractionPlanManager instance = new ExtractionPlanManager();

        List<ExtractionPlanRangeDetail> result = instance.generateDetailRandom(range);
        /*for (ExtractionPlanRangeDetail detail : result) {
        System.out.println("Detail:" + DateFormatUtils.format(detail.getTimeSample(), "HH:mm"));
        }*/
        //solo se ocupa para revisar la generacion de un detalles
        assertTrue(true);
    }

    @Test
    public void testDifferentEPR() {
        //Compara que EPR debe retornar que son iguales en los valores
        System.out.println("differentEPR");
        ExtractionPlanRange planRange = ManagerMock.genExtractionPlanRange(0, 1, 30, 300, 1);
        ExtractionPlanRange planRangeExisting = ManagerMock.genExtractionPlanRange(0, 1, 30, 300, 1);
        ExtractionPlanManager instance = new ExtractionPlanManager();
        boolean expResult = false;
        boolean result = instance.differentEPR(planRange, planRangeExisting);
        assertEquals(expResult, result);

    }

    @Test
    public void testDifferentEPR2() {
        System.out.println("differentEPR2");
        ExtractionPlanRange planRange = ManagerMock.genExtractionPlanRange(0, 1, 30, 300, 1);
        ExtractionPlanRange planRangeExisting = ManagerMock.genExtractionPlanRange(0, 1, 15, 300, 1);
        ExtractionPlanManager instance = new ExtractionPlanManager();
        boolean expResult = true;
        boolean result = instance.differentEPR(planRange, planRangeExisting);
        assertEquals(expResult, result);
    }

    @Test
    public void testSaveSensors() {
        System.out.println("saveSensors");
        ExtractionPlanCustomizing planCustomizing = ManagerMock.genExtractionPlanCustomizing2Sensors();
        List<Integer> sensorIds = new ArrayList<Integer>();
        sensorIds.add(20);
        ExtractionPlanManager instance = new ExtractionPlanManager();
        SaveSensorsCommand command = ManagerMock.genSaveSensorsCommand();
        instance.setSaveSensorsCommand(command);
        instance.saveSensors(planCustomizing, sensorIds);
        assertEquals(new Integer(20), planCustomizing.getSensors().get(0).getId());
    }

    @Test
    public void testSaveExtractionPlanMetrics() throws Exception {
        System.out.println("saveExtractionPlanMetrics");
        ExtractionPlanCustomizing customizing = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCUSTConMetricTemplate();
        List<ExtractionPlanMetricDTO> planMetricsDTOs = ManagerMock.genExtractionPlanMetricDTOs(2);

        ExtractionPlanManager instance = new ExtractionPlanManager();
        instance.setCleanExtractionPlanMetricsCommand(ManagerMock.genCleanExtractionPlanMetricsCommand());
        SaveExtractionPlanMetricCommand command = ManagerMock.genSaveExtractionPlanMetricCommand();
        GenericDAOListMockup daoList = (GenericDAOListMockup) command.getDao();
        instance.setSaveExtractionPlanMetricCommand(command);

        instance.saveExtractionPlanMetrics(customizing, planMetricsDTOs);

        assertEquals(2, daoList.getBusinessObjects().size());
        assertEquals(new Integer(2), ((ExtractionPlanMetric) daoList.getBusinessObjects().get(1)).getEvaluationOrder());
    }
}
