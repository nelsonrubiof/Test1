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
 *  GetExtractionPlanRangeDetailListByIdEPRCommandTest.java
 * 
 *  Created on 28-09-2010, 05:37:17 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
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
public class GetExtractionPlanRangeDetailListByIdEPRCommandTest {

    public GetExtractionPlanRangeDetailListByIdEPRCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetExtractionPlanRangeDetailListByIdEPRCommandTest");
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
        ExtractionPlanRange planRange = new ExtractionPlanRange();
        planRange.setId(1);
        GetExtractionPlanRangeDetailListByIdEPRCommand instance = new GetExtractionPlanRangeDetailListByIdEPRCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        List<ExtractionPlanRangeDetail> expResult = new ArrayList<ExtractionPlanRangeDetail>();
        expResult.add(ManagerMock.genExtractionPlanRangeDetail(1, null));
        expResult.add(ManagerMock.genExtractionPlanRangeDetail(2, null));
        List<ExtractionPlanRangeDetail> result = instance.execute(planRange);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getId(), result.get(1).getId());
    }

    @Test
    public void testExecute2() throws Exception {
        System.out.println("execute2");
        ExtractionPlanRange planRange = null;
        GetExtractionPlanRangeDetailListByIdEPRCommand instance = new GetExtractionPlanRangeDetailListByIdEPRCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        try {
            List<ExtractionPlanRangeDetail> result = instance.execute(planRange);
            fail("Se esperaba una excepcion");
        } catch (ScopixException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testExecute3() throws Exception {
        System.out.println("execute3");
        ExtractionPlanRange planRange = new ExtractionPlanRange();
        planRange.setId(2);
        GetExtractionPlanRangeDetailListByIdEPRCommand instance = new GetExtractionPlanRangeDetailListByIdEPRCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        List<ExtractionPlanRangeDetail> result = instance.execute(planRange);
        assertEquals(0, result.size());
    }
}
