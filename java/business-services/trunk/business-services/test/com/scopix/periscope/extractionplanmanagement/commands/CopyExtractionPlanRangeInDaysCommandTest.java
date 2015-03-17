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
 *  CopyExtractionPlanRangeInDaysTest.java
 * 
 *  Created on 06-10-2010, 10:49:33 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import java.util.Date;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import java.util.ArrayList;
import java.util.List;
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
public class CopyExtractionPlanRangeInDaysCommandTest {

    public CopyExtractionPlanRangeInDaysCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("CopyExtractionPlanRangeInDaysCommandTest");
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
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanSimple(1, true, Boolean.TRUE);
        List<ExtractionPlanRange> rangosDay = new ArrayList<ExtractionPlanRange>();
        int pos =1;
        ExtractionPlanRange range = ManagerMock.genExtractionPlanRange(pos++, 1, 30, 300, 1);
        range.getExtractionPlanRangeDetails().add(ManagerMock.genExtractionPlanRangeDetail(Math.round(pos), new Date()));
        rangosDay.add(range);
        List<Integer> days = new ArrayList<Integer>();
        days.add(2);
        days.add(3);
        CopyExtractionPlanRangeInDaysCommand instance = new CopyExtractionPlanRangeInDaysCommand();
        List<ExtractionPlanRange> expResult = new ArrayList<ExtractionPlanRange>();
        range = ManagerMock.genExtractionPlanRange(pos++, 2, 30, 300, 1);
        range.getExtractionPlanRangeDetails().add(ManagerMock.genExtractionPlanRangeDetail(Math.round(pos), new Date()));
        expResult.add(range);
        range = ManagerMock.genExtractionPlanRange(pos++, 3, 30, 300, 1);
        range.getExtractionPlanRangeDetails().add(ManagerMock.genExtractionPlanRangeDetail(Math.round(pos), new Date()));
        expResult.add(range);
        List<ExtractionPlanRange> result = instance.execute(epc, rangosDay, days);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getDuration(), result.get(1).getDuration());

        
    }
}
