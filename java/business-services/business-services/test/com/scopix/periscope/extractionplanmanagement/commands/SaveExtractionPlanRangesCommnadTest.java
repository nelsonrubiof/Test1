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
 *  SaveExtractionPlanRangesCommnadTest.java
 * 
 *  Created on 05-10-2010, 05:11:59 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import java.util.ArrayList;
import java.util.List;
import mockup.GenericDAOListMockup;
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
public class SaveExtractionPlanRangesCommnadTest {

    public SaveExtractionPlanRangesCommnadTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("SaveExtractionPlanRangesCommnadTest");
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
        List<ExtractionPlanRange> planRanges = new ArrayList<ExtractionPlanRange>();
        planRanges.add(ManagerMock.genExtractionPlanRange(1, 1, 30, 5, 3));
        planRanges.add(ManagerMock.genExtractionPlanRange(2, 2, 30, 5, 3));
        SaveExtractionPlanRangesCommnad instance = new SaveExtractionPlanRangesCommnad();
        GenericDAOListMockup dao = new GenericDAOListMockup();
        instance.setDao(dao);
        instance.execute(planRanges);
        assertEquals(dao.getBusinessObjects().size(), planRanges.size());
    }
}
