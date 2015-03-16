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
 *  CorporateStructureManagerTest.java
 * 
 *  Created on 22-09-2010, 03:12:22 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement;

import mockup.CorporateStructureManagementHibernateDAOMock;
import mockup.GenericDAOMockup;

import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetEvidenceExtractionServicesServerCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.SendExtractionPlanCommand;
//import org.easymock.EasyMock;
import com.scopix.periscope.extractionplanmanagement.commands.GetExtractionPlanCustomizingByIdCommand;

import mockup.GenericDAOEpcActivoMockup;
import mockup.EvidenceServicesWebServiceClientMock;

import com.scopix.periscope.corporatestructuremanagement.dto.PeriodIntervalDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.extractionplanmanagement.commands.GetCantidadDetailsCommand;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

import java.util.List;

import mockup.SecurityManagerMock;

import com.scopix.periscope.securitymanagement.SecurityManager;

import java.util.ArrayList;

import mockup.EvidenceServicesWebServiceMock;
import mockup.ExtractionPlanCustomizingDAOMock;
import mockup.ManagerMock;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author nelson
 */
@Ignore
public class CorporateStructureManagerTest {

    private static SecurityManager securityManager;

    public static SecurityManager getSecurityManager() {
        return securityManager;
    }

    public static void setSecurityManager(SecurityManager aSecurityManager) {
        securityManager = aSecurityManager;
    }

    public CorporateStructureManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("CorporateStructureManagerTest");
        setSecurityManager(new SecurityManagerMock());

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
    public void testGetStoreList() throws Exception {
        System.out.println("getStoreList");
        Store store = null;
        long sessionId = 0L;
        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        //el command generado contiene un mock que genera 2 store para la lista
        instance.setStoreListCommand(ManagerMock.genStoreListCommand());
        List<Store> expResult = new ArrayList<Store>();
        expResult.add(ManagerMock.genStore(1, "Store 1", "Store 1"));
        expResult.add(ManagerMock.genStore(2, "Store 2", "Store 2"));
        List<Store> result = instance.getStoreList(store, sessionId);

        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getDescription(), result.get(0).getDescription());

    }

    @Test
    public void testGetStoreDTOs() throws Exception {
        System.out.println("getStoreDTOs");
        Store store = null;
        long sessionId = 0L;
        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        //el command generado contiene un mock que genera 2 store para la lista
        instance.setStoreListCommand(ManagerMock.genStoreListCommand());
        //no seteamos el command de transform ya que no ejecuta nada solo recorre la lista y genera un dto no tiene
        //acceso a base de datos
        List<StoreDTO> expResult = new ArrayList<StoreDTO>();
        expResult.add(ManagerMock.genStoreDTO(1, "Store 1", "Store 1"));
        expResult.add(ManagerMock.genStoreDTO(2, "Store 2", "Store 2"));

        List<StoreDTO> result = instance.getStoreDTOs(store, sessionId);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getDescription(), result.get(1).getDescription());

    }

    @Test
    public void testGetStoreDTOs2() throws Exception {
        System.out.println("getStoreDTOs2");
        Store store = null;
        long sessionId = 0L;
        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        //el command generado contiene un mock que genera 2 store para la lista con 2 periodos cada store
        instance.setStoreListCommand(ManagerMock.genStoreListCommandConPeriod());
        //no seteamos el command de transform ya que no ejecuta nada solo recorre la lista y genera un dto no tiene
        //acceso a base de datos
        List<StoreDTO> expResult = new ArrayList<StoreDTO>();
        StoreDTO dto = ManagerMock.genStoreDTO(1, "Store 1", "Store 1");
        dto.setPeriodIntervalDTOs(new ArrayList<PeriodIntervalDTO>());
        dto.getPeriodIntervalDTOs().add(new PeriodIntervalDTO());
        dto.getPeriodIntervalDTOs().add(new PeriodIntervalDTO());
        expResult.add(dto);
        dto = ManagerMock.genStoreDTO(2, "Store 2", "Store 2");
        dto.setPeriodIntervalDTOs(new ArrayList<PeriodIntervalDTO>());
        dto.getPeriodIntervalDTOs().add(new PeriodIntervalDTO());
        dto.getPeriodIntervalDTOs().add(new PeriodIntervalDTO());
        expResult.add(dto);

        List<StoreDTO> result = instance.getStoreDTOs(store, sessionId);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getDescription(), result.get(0).getDescription());
        assertEquals(expResult.get(0).getPeriodIntervalDTOs().size(), result.get(0).getPeriodIntervalDTOs().size());

    }

    @Test
    public void testGetEvidenceExtractionServicesServer() throws Exception {
        System.out.println("getEvidenceExtractionServicesServer");
        int idEvidenceExtractionServicesServer = 1;
        long sessionId = 0L;
        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setEvidenceExtractionServicesServerCommand(ManagerMock.genEvidenceExtractionServicesServerCommand());

        EvidenceExtractionServicesServer expResult = ManagerMock.genEvidenceExtractionServicesServer(1,
                "Evidence Extraction Services Server 1");
        expResult.setEvidenceServicesServer(ManagerMock.genEvidenceServicesServer(1));
        EvidenceExtractionServicesServer result = instance.getEvidenceExtractionServicesServer(idEvidenceExtractionServicesServer, sessionId);
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getName(), result.getName());

    }

    @Test
    public void testSendExtractionPlanToEES() throws Exception {
        System.out.println("sendExtractionPlanToEES");
        Integer evidenceExtractionServicesServerId = 1;
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        long sessionId = 0L;

        /**
         * creamos un EPM con los datos necesarios
         */
        ExtractionPlanManager extractionPlanManager = new ExtractionPlanManager();
        extractionPlanManager.setSecurityManager(getSecurityManager());
        extractionPlanManager.setAddSituationCommand(ManagerMock.genAddSituationCommand());

        extractionPlanManager.setGetStoreCommand(ManagerMock.genStoreCommand());
        extractionPlanManager.setAddSituationCommand(ManagerMock.genAddSituationCommand());
        extractionPlanManager.setAreaListCommand(ManagerMock.genAreaListCommand());
        extractionPlanManager.setAddMetricCommand(ManagerMock.genAddMetricCommand());
        extractionPlanManager.setAreaListCommand(ManagerMock.genAreaListCommand());
        extractionPlanManager.setActivateWizardCustomizingCommand(ManagerMock.genActivateWizardCustomizingCommand());
        extractionPlanManager.setInactivateEPCCustomizingCommand(ManagerMock.genInactivateEPCCustomizingCommand());


        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setEvidenceExtractionServicesServerCommand(ManagerMock.genEvidenceExtractionServicesServerCommand());
        EvidenceServicesWebServiceClientMock serviceClient = new EvidenceServicesWebServiceClientMock();
        instance.setWebServiceClient(serviceClient);
        instance.setSendExtractionPlanCommand(ManagerMock.genSendExtractionPlanCommand());
        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());
        instance.setExtractionPlanManager(extractionPlanManager);
        GetCantidadDetailsCommand cantidadDetailsCommand = EasyMock.createMock(GetCantidadDetailsCommand.class);
        ExtractionPlanCustomizingDAOImpl dao = EasyMock.createMock(ExtractionPlanCustomizingDAOImpl.class);

        EasyMock.expect(dao.getCantidadDetailesForEpcStore(epc.getId(), null)).andReturn(1);
        instance.setCantidadDetails(cantidadDetailsCommand);


        //instance.sendExtractionPlanToEES(evidenceExtractionServicesServerId, epc, sessionId);
        Store s = ManagerMock.genStore(1, "store test", "store test");
        instance.sendExtractionPlanToEES(evidenceExtractionServicesServerId, new ArrayList<Integer>(), sessionId, s);
        assertEquals(1, ((EvidenceServicesWebServiceMock) serviceClient.getWebService()).getEessdtos().size());

    }

    @Test
    public void testSendExtractionPlanToEES2() throws Exception {
        System.out.println("sendExtractionPlanToEES2");
        Integer evidenceExtractionServicesServerId = 1;
        ExtractionPlanCustomizing epc = null;
        long sessionId = 0L;

        /**
         * creamos un EPM con los datos necesarios
         */
        ExtractionPlanManager extractionPlanManager = new ExtractionPlanManager();
        extractionPlanManager.setSecurityManager(getSecurityManager());
        extractionPlanManager.setAddSituationCommand(ManagerMock.genAddSituationCommand());

        extractionPlanManager.setGetStoreCommand(ManagerMock.genStoreCommand());
        extractionPlanManager.setAddSituationCommand(ManagerMock.genAddSituationCommand());
        extractionPlanManager.setAreaListCommand(ManagerMock.genAreaListCommand());
        extractionPlanManager.setAddMetricCommand(ManagerMock.genAddMetricCommand());
        extractionPlanManager.setAreaListCommand(ManagerMock.genAreaListCommand());
        extractionPlanManager.setActivateWizardCustomizingCommand(ManagerMock.genActivateWizardCustomizingCommand());
        extractionPlanManager.setInactivateEPCCustomizingCommand(ManagerMock.genInactivateEPCCustomizingCommand());
        extractionPlanManager.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());

        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setEvidenceExtractionServicesServerCommand(ManagerMock.genEvidenceExtractionServicesServerCommand());
        EvidenceServicesWebServiceClientMock serviceClient = new EvidenceServicesWebServiceClientMock();
        instance.setWebServiceClient(serviceClient);
        instance.setSendExtractionPlanCommand(ManagerMock.genSendExtractionPlanCommand());
        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());
        instance.setExtractionPlanManager(extractionPlanManager);
        GetCantidadDetailsCommand cantidadDetailsCommand = EasyMock.createMock(GetCantidadDetailsCommand.class);
        ExtractionPlanCustomizingDAOImpl dao = EasyMock.createMock(ExtractionPlanCustomizingDAOImpl.class);

        EasyMock.expect(dao.getCantidadDetailesForEpcStore(null, 1)).andReturn(1);
        instance.setCantidadDetails(cantidadDetailsCommand);

        Store s = ManagerMock.genStore(1, "store test", "store test");
        instance.sendExtractionPlanToEES(evidenceExtractionServicesServerId, new ArrayList<Integer>(), sessionId, s);
        assertEquals(1, ((EvidenceServicesWebServiceMock) serviceClient.getWebService()).getEessdtos().size());
    }

    @Test
    public void testSendExtractionPlanCustomizing() throws Exception {
        System.out.println("sendExtractionPlanCustomizing");
        long sessionId = 0L;
        Integer storeId = null; //2;
        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setExtractionPlanCustomizingByIdCommand(ManagerMock.genExtractionPlanCustomizingByIdCommandRetNull());



        //se prueba la caida por no existir storeId con id recibido
        try {
            //instance.sendExtractionPlanCustomizing(extractionPlanCustomizingId, sessionId);
            instance.sendExtractionPlanCustomizing(new ArrayList<Integer>(), storeId, sessionId);
            fail("Se eperaba excepcion");
        } catch (ScopixException e) {
            assertTrue(true);
//        } catch (PeriscopeSecurityException e) {
//            fail("Se esperarba un PeriscopeException no PeriscopeSecurityException");
        }
    }

    @Test
    public void testSendExtractionPlanCustomizing2() throws Exception {
        System.out.println("sendExtractionPlanCustomizing2");
        Integer extractionPlanCustomizingId = 1;
        long sessionId = 0L;
        int storeId = 2;
        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        GetExtractionPlanCustomizingByIdCommand command = new GetExtractionPlanCustomizingByIdCommand();
        GenericDAOEpcActivoMockup dao = new GenericDAOEpcActivoMockup();
        command.setDao(dao);
        instance.setExtractionPlanCustomizingByIdCommand(command);

        Store s = EasyMock.createMock(Store.class);
        EvidenceServicesServer ess = EasyMock.createMock(EvidenceServicesServer.class);
        EasyMock.expect(ess.getUrl()).andReturn(null);
        EvidenceExtractionServicesServer eess = EasyMock.createMock(EvidenceExtractionServicesServer.class);
        EasyMock.expect(eess.getId()).andReturn(1);
        EasyMock.expect(eess.getEvidenceServicesServer()).andReturn(ess);
        //EasyMock.expect(eess.getStores().get(0)).andReturn(s);
        EasyMock.expect(s.getId()).andReturn(1);
        ExtractionPlanManager epm = EasyMock.createMock(ExtractionPlanManager.class);

        EasyMock.expect(s.getEvidenceExtractionServicesServer()).andReturn(eess);
        GetStoreCommand storeCommand = EasyMock.createMock(GetStoreCommand.class);
        EasyMock.expect(storeCommand.execute(2)).andReturn(s);


        GetEvidenceExtractionServicesServerCommand geessc = EasyMock.createMock(GetEvidenceExtractionServicesServerCommand.class);
        EasyMock.expect(geessc.execute(1)).andReturn(eess);

        EvidenceServicesWebServiceClientMock serviceClient = new EvidenceServicesWebServiceClientMock();

        GetExtractionPlanCustomizingListCommand gepclc = new GetExtractionPlanCustomizingListCommand();
        ExtractionPlanCustomizingDAOMock dAOMock = new ExtractionPlanCustomizingDAOMock();
        gepclc.setDao(dAOMock);

        GetCantidadDetailsCommand cantidadDetailsCommand = new GetCantidadDetailsCommand();
        cantidadDetailsCommand.setDao(dAOMock);

        SendExtractionPlanCommand sepc = EasyMock.createMock(SendExtractionPlanCommand.class);
        sepc.setEvidenceProviders(ManagerMock.genListaEvidenceProvider(2, ManagerMock.genEvidenceProviderType()));
        sepc.setGenericDao(new GenericDAOMockup());
        sepc.setWizardManager(ManagerMock.genWizardManager2());
        sepc.setDaoCorporateStructureManager(new CorporateStructureManagementHibernateDAOMock());


        instance.setStoreCommand(storeCommand);
        instance.setEvidenceExtractionServicesServerCommand(geessc);
        instance.setWebServiceClient(serviceClient);
        instance.setExtractionPlanManager(epm);
        instance.setExtractionPlanCustomizingListCommand(gepclc);
        instance.setCantidadDetails(cantidadDetailsCommand);
        instance.setSendExtractionPlanCommand(sepc);

        EasyMock.replay(storeCommand);
        EasyMock.replay(s);
        EasyMock.replay(eess);
        EasyMock.replay(geessc);



        //se prueba que no hace nada ya que el epc recibido esta activo, es decir ya fue enviado
        //instance.sendExtractionPlanCustomizing(extractionPlanCustomizingId, storeId, sessionId);
        instance.sendExtractionPlanCustomizing(new ArrayList<Integer>(), storeId, sessionId);

        assertTrue(true);
        //fin
    }

    @Test
    public void testSendExtractionPlanCustomizingFull() throws Exception {
        System.out.println("sendExtractionPlanCustomizingFull");
        Integer storeId = 1;
        long sessionId = 0L;


        ExtractionPlanManager extractionPlanManager = new ExtractionPlanManager();
        extractionPlanManager.setSecurityManager(getSecurityManager());
        extractionPlanManager.setAddSituationCommand(ManagerMock.genAddSituationCommand());

        extractionPlanManager.setGetStoreCommand(ManagerMock.genStoreCommand());
        extractionPlanManager.setAddSituationCommand(ManagerMock.genAddSituationCommand());
        extractionPlanManager.setAreaListCommand(ManagerMock.genAreaListCommand());
        extractionPlanManager.setAddMetricCommand(ManagerMock.genAddMetricCommand());
        extractionPlanManager.setAreaListCommand(ManagerMock.genAreaListCommand());
        extractionPlanManager.setActivateWizardCustomizingCommand(ManagerMock.genActivateWizardCustomizingCommand());


        CorporateStructureManager instance = new CorporateStructureManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setStoreCommand(ManagerMock.genStoreCommand2());


        instance.setEvidenceExtractionServicesServerCommand(ManagerMock.genEvidenceExtractionServicesServerCommand());
        EvidenceServicesWebServiceClientMock serviceClient = new EvidenceServicesWebServiceClientMock();
        instance.setWebServiceClient(serviceClient);
        instance.setSendExtractionPlanCommand(ManagerMock.genSendExtractionPlanCommand());
        instance.setExtractionPlanCustomizingListCommand(ManagerMock.genExtractionPlanCustomizingListCommand());
        instance.setExtractionPlanManager(extractionPlanManager);
        GetCantidadDetailsCommand cantidadDetailsCommand = EasyMock.createMock(GetCantidadDetailsCommand.class);
        ExtractionPlanCustomizingDAOImpl dao = EasyMock.createMock(ExtractionPlanCustomizingDAOImpl.class);

        EasyMock.expect(dao.getCantidadDetailesForEpcStore(null, storeId)).andReturn(1);
        instance.setCantidadDetails(cantidadDetailsCommand);

        instance.sendExtractionPlanCustomizingFull(storeId, sessionId);
        assertEquals(1, ((EvidenceServicesWebServiceMock) serviceClient.getWebService()).getEessdtos().size());
    }
}
