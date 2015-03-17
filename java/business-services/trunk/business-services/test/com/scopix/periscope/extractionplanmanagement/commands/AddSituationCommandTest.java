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
 *  AddSituationCommandTest.java
 * 
 *  Created on 08-09-2010, 12:04:52 PM
 * 
 */

package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.commands.AddSituationCommand;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import mockup.GenericDAOMockup;
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
public class AddSituationCommandTest {

    public AddSituationCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("AddSituationCommandTest");
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
        Situation situation = new Situation();
        situation.setId(1);
        AddSituationCommand instance = new AddSituationCommand();
        GenericDAOMockup daoMock = new GenericDAOMockup();
        instance.setDao(daoMock);
        instance.execute(situation);
        assertEquals(daoMock.getBusinessObject().getId(), situation.getId());
    }


}