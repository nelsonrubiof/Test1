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
 *  SaveExtractionPlanCustomizingCommandTest.java
 * 
 *  Created on 04-10-2010, 11:38:39 AM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
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
public class SaveExtractionPlanCustomizingCommandTest {

    public SaveExtractionPlanCustomizingCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("SaveExtractionPlanCustomizingCommandTest");
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
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanSimple(1, true, Boolean.FALSE);
        GenericDAOMockup dao = new GenericDAOMockup();
        SaveExtractionPlanCustomizingCommand instance = new SaveExtractionPlanCustomizingCommand();
        instance.setDao(dao);
        instance.execute(epc);
        assertEquals(dao.getBusinessObject().getId(), epc.getId());
    }

}