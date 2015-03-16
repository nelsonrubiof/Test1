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
 *  GetSituationTemplateListCommandTest.java
 * 
 *  Created on 08-09-2010, 11:32:14 AM
 * 
 */

package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAO;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAOImpl;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import mockup.TemplateManagementHibernateDAOMock;
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
public class GetSituationTemplateListCommandTest {

    public GetSituationTemplateListCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetSituationTemplateListCommandTest");
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
        SituationTemplate situationTemplate = null;
        GetSituationTemplateListCommand instance = new GetSituationTemplateListCommand();
        instance.setDao(new TemplateManagementHibernateDAOMock());
        List expResult = new ArrayList();
        expResult.add(new Object());
        expResult.add(new Object());
        expResult.add(new Object());
        List result = instance.execute(situationTemplate);
        assertEquals(expResult.size(), result.size());
    }

}