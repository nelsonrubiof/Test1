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
 *  TemplatesManagerTest.java
 * 
 *  Created on 22-09-2010, 12:42:00 PM
 * 
 */
package com.scopix.periscope.templatemanagement;

import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;
import java.util.AbstractList;
import java.util.ArrayList;
import mockup.SecurityManagerMock;
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
public class TemplatesManagerTest {

    private static SecurityManager securityManager;

    public static SecurityManager getSecurityManager() {
        return securityManager;
    }

    public static void setSecurityManager(SecurityManager aSecurityManager) {
        securityManager = aSecurityManager;
    }

    public TemplatesManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("TemplatesManagerTest");
        setSecurityManager(new SecurityManagerMock());

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
    public void testGetSituationTemplateList() throws Exception {
        System.out.println("getSituationTemplateList");
        SituationTemplate situationTemplate = null;
        long sessionId = 0L;
        TemplatesManager instance = new TemplatesManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setSituationTemplateListCommand(ManagerMock.genSituationTemplateListCommand());
        //el command utilizado por esta implementacion de SituationTemplateListCommand retorna una lista de 3 ST nuevos
        List<SituationTemplate> expResult = new ArrayList<SituationTemplate>();
        expResult.add(new SituationTemplate());
        expResult.add(new SituationTemplate());
        expResult.add(new SituationTemplate());
        List<SituationTemplate> result = instance.getSituationTemplateList(situationTemplate, sessionId);
        assertEquals(expResult.size(), result.size());
        
    }

    @Test
    public void testGetSituationTemplateDTOs() throws Exception {
        System.out.println("getSituationTemplateDTOs");
        SituationTemplate situationTemplate = null;
        long sessionId = 0L;
        TemplatesManager instance = new TemplatesManager();
        instance.setSecurityManager(getSecurityManager());
        instance.setSituationTemplateListCommand(ManagerMock.genSituationTemplateListCommand());
        //el command utilizado por esta implementacion de SituationTemplateListCommand retorna una lista de 3 ST nuevos
        List<SituationTemplateDTO> expResult = new ArrayList<SituationTemplateDTO>();
        expResult.add(ManagerMock.genSituationTemplateDTO(null, null));
        expResult.add(ManagerMock.genSituationTemplateDTO(null, null));
        expResult.add(ManagerMock.genSituationTemplateDTO(null, null));
        List<SituationTemplateDTO> result = instance.getSituationTemplateDTOs(situationTemplate, sessionId);
        assertEquals(expResult.size(), result.size());
    }
}
