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
 *  RemoveExtractionPlanRangesDaysCommandTest.java
 * 
 *  Created on 06-10-2010, 10:20:49 AM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import mockup.ExtractionPlanCustomizingDAOMock;
import mockup.GenericDAOMockup;
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
public class RemoveExtractionPlanRangesDaysCommandTest {

    public RemoveExtractionPlanRangesDaysCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("RemoveExtractionPlanRangesDaysCommandTest");
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
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos2DetallesForDays(new Integer[]{1,2,3,4});
        List<Integer> days = new ArrayList<Integer>();
        days.add(2);
        days.add(4);
        RemoveExtractionPlanRangesDaysCommand instance = new RemoveExtractionPlanRangesDaysCommand();
        instance.setDao(new GenericDAOMockup());
        instance.setDaoEPC(new ExtractionPlanCustomizingDAOMock());
        instance.execute(epc, days);
        assertEquals(4, epc.getExtractionPlanRanges().size());
        assertEquals(0, epc.getExtractionPlanRangesByDay(2).size());
    }

    
}