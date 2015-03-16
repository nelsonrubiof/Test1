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
 *  TransformSituationTemplateToDTOCommandTest.java
 * 
 *  Created on 22-09-2010, 12:46:29 PM
 * 
 */

package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;
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
public class TransformSituationTemplateToDTOCommandTest {

    public TransformSituationTemplateToDTOCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("TransformSituationTemplateToDTOCommandTest");
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
        List<SituationTemplate> situationTemplateList = ManagerMock.genSituationTemplateList2();
        TransformSituationTemplateToDTOCommand instance = new TransformSituationTemplateToDTOCommand();
        List<SituationTemplateDTO> expResult = new ArrayList<SituationTemplateDTO>();
        expResult.add(ManagerMock.genSituationTemplateDTO(1,"ST 1"));
        expResult.add(ManagerMock.genSituationTemplateDTO(2,"ST 2"));
        List<SituationTemplateDTO> result = instance.execute(situationTemplateList);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(0).getId(), result.get(0).getId());
        
    }

}