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
 *  GetExtractionPlanCustomizingCommandTest.java
 * 
 *  Created on 13-09-2010, 11:31:31 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.templatemanagement.SituationTemplate;
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
public class GetExtractionPlanCustomizingCommandTest {

    public GetExtractionPlanCustomizingCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetExtractionPlanCustomizingCommandTest");
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
        SituationTemplate st = ManagerMock.genSituationTemplate(null, null, null, null, 1, null, "Situation Template Mock 1",
                null, null);
        Store store = ManagerMock.genStore(null, null, null, null, null, null, null, null, null, 1, null, null, null,
                "Store Mock 1", null, null, null, null, null, null);
        GetExtractionPlanCustomizingBySTAndStoreCommand instance = new GetExtractionPlanCustomizingBySTAndStoreCommand();
        instance.setDao(new ExtractionPlanCustomizingDAOMock());
        ExtractionPlanCustomizing expResult = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        ExtractionPlanCustomizing result = instance.execute(st, store);
        assertEquals(expResult.getStore().getId(), result.getStore().getId());
        assertEquals(expResult.getSituationTemplate().getName(), result.getSituationTemplate().getName());
    }
}
