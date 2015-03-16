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
 *  GetExtractionPlanCustomizingListCommandTest.java
 * 
 *  Created on 22-09-2010, 04:37:53 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import java.util.ArrayList;
import java.util.List;
import mockup.ExtractionPlanCustomizingDAOMock;
import mockup.ExtractionPlanCustomizingDAOMockConEP1;
import mockup.ManagerMock;
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
public class GetExtractionPlanCustomizingListCommandTest {

    public GetExtractionPlanCustomizingListCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetExtractionPlanCustomizingListCommandTest");
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
    public void testExecute() {
        System.out.println("execute");
        ExtractionPlanCustomizing epc = null;
        String estado = "";
        GetExtractionPlanCustomizingListCommand instance = new GetExtractionPlanCustomizingListCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        List<ExtractionPlanCustomizing> expResult = new ArrayList<ExtractionPlanCustomizing>();
        ExtractionPlanCustomizing epcMock = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        epcMock.setId(1);
        expResult.add(epcMock);
        List<ExtractionPlanCustomizing> result = instance.execute(epc, estado);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getId(), result.get(0).getId());

    }

    @Test
    public void testExecute2() {
        System.out.println("execute2");
        ExtractionPlanCustomizing epc = null;
        String estado = "SENT";
        GetExtractionPlanCustomizingListCommand instance = new GetExtractionPlanCustomizingListCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMockConEP1());

        List<ExtractionPlanCustomizing> expResult = new ArrayList<ExtractionPlanCustomizing>();
        expResult.add(ManagerMock.genExtractionPlanSimple(1, true, Boolean.TRUE));
        expResult.add(ManagerMock.genExtractionPlanSimple(2, true, Boolean.TRUE));

        List<ExtractionPlanCustomizing> result = instance.execute(epc, estado);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
        assertEquals(expResult.get(1).isActive(), result.get(1).isActive());

    }

    @Test
    public void testExecute3() {
        System.out.println("execute3");
        ExtractionPlanCustomizing epc = null;
        String estado = "EDITION";
        GetExtractionPlanCustomizingListCommand instance = new GetExtractionPlanCustomizingListCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMockConEP1());

        List<ExtractionPlanCustomizing> expResult = new ArrayList<ExtractionPlanCustomizing>();
        expResult.add(ManagerMock.genExtractionPlanSimple(1, true, Boolean.FALSE));
        expResult.add(ManagerMock.genExtractionPlanSimple(2, true, Boolean.FALSE));

        List<ExtractionPlanCustomizing> result = instance.execute(epc, estado);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
        assertEquals(expResult.get(1).isActive(), result.get(1).isActive());

    }

    @Test
    public void testExecute4() {
        System.out.println("execute4");
        ExtractionPlanCustomizing epc = null;
        String estado = "ALL";
        GetExtractionPlanCustomizingListCommand instance = new GetExtractionPlanCustomizingListCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMockConEP1());

        List<ExtractionPlanCustomizing> expResult = new ArrayList<ExtractionPlanCustomizing>();
        //lo que se crea internamente en el mock
        //st = 1, store = 1, epc = 1, active = true
        //st = 1, store = 1, epc = 2, active = true
        //st = 1, store = 1, epc = 1, active = false
        //st = 1, store = 1, epc = 2, active = false
        //al recorres estos valores el merge arroja
        //la conbinacion st = 1, store = 1, epc = 2, active = false

        expResult.add(ManagerMock.genExtractionPlanSimple(2, true, Boolean.FALSE));

        List<ExtractionPlanCustomizing> result = instance.execute(epc, estado);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        assertEquals(expResult.get(0).isActive(), result.get(0).isActive());

    }
}
