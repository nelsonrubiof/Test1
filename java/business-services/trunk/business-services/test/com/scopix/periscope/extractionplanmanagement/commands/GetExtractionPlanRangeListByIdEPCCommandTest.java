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
 *  GetExtractionPlanRangeListByIdEPCCommandTest.java
 * 
 *  Created on 28-09-2010, 03:57:01 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.ArrayList;
import java.util.List;
import mockup.ExtractionPlanCustomizingDAOMock;
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
public class GetExtractionPlanRangeListByIdEPCCommandTest {

    public GetExtractionPlanRangeListByIdEPCCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetExtractionPlanRangeListByIdEPCCommandTest");
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
    public void testExecute() throws Exception {
        System.out.println("execute");
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(1);
        GetExtractionPlanRangeListByIdEPCCommand instance = new GetExtractionPlanRangeListByIdEPCCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        List<ExtractionPlanRange> expResult = new ArrayList<ExtractionPlanRange>();
        expResult.add(ManagerMock.genExtractionPlanRange(1, 1, 30, 5, 3));
        expResult.add(ManagerMock.genExtractionPlanRange(2, 2, 15, 5, 1));
        List<ExtractionPlanRange> result = instance.execute(epc);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getDayOfWeek(), result.get(1).getDayOfWeek());
    }

    @Test
    public void testExecute2() throws Exception {
        System.out.println("execute2");
        ExtractionPlanCustomizing epc = null;
        GetExtractionPlanRangeListByIdEPCCommand instance = new GetExtractionPlanRangeListByIdEPCCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        try {
            List<ExtractionPlanRange> result = instance.execute(epc);
            fail("Se esperaba una excepcion al no recibir el epc");
        } catch (ScopixException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testExecute3() throws Exception {
        System.out.println("execute3");
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(2);
        GetExtractionPlanRangeListByIdEPCCommand instance = new GetExtractionPlanRangeListByIdEPCCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        List<ExtractionPlanRange> result = instance.execute(epc);
        assertEquals(0, result.size());
    }
}
