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
 *  CreateExtractionPlanCustomizingCommandTest.java
 * 
 *  Created on 23-09-2010, 04:05:19 PM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import mockup.GenericDAOStStoreCompletoMockup;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.templatemanagement.SituationTemplate;
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
public class CreateExtractionPlanCustomizingCommandTest {

    public CreateExtractionPlanCustomizingCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("CreateExtractionPlanCustomizingCommandTest");
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
        SituationTemplate st = ManagerMock.genSituationTemplate(1, "Situation Template 1");
        st.setAreaType(ManagerMock.genAreaType(1, "AreaType 1", "AreaType 1"));
        Store s = ManagerMock.genStore(1, "Store 1", "Store 1");
        CreateExtractionPlanCustomizingCommand instance = new CreateExtractionPlanCustomizingCommand();
        instance.setDao(new GenericDAOStStoreCompletoMockup());

        ExtractionPlanCustomizing expResult = ManagerMock.genExtractionPlanSimple(null, false, null);
        expResult.setAreaType(ManagerMock.genAreaType(1, "AreaType 1", "AreaType 1"));
        expResult.setStore(ManagerMock.genStore(1, "Store 1", "Store 1"));
        expResult.setSituationTemplate(ManagerMock.genSituationTemplate(1, "Situation Template 1"));
        ExtractionPlanCustomizing result = instance.execute(st, s, "test");
        assertEquals(expResult.getId(), result.getId());
        assertEquals(expResult.getStore().getName(), result.getStore().getName());
        assertEquals(expResult.getAreaType().getId(), result.getAreaType().getId());

    }
}