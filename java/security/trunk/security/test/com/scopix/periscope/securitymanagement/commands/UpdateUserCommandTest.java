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
 *  UpdateUserCommandTest.java
 * 
 *  Created on 05-11-2010, 01:38:07 PM
 * 
 */

package com.scopix.periscope.securitymanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.securitymanagement.PeriscopeUser;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nelson
 */
public class UpdateUserCommandTest {

    public UpdateUserCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
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
        PeriscopeUser periscopeUser = ManagerMock.createPeriscopeUser(1);
        UpdateUserCommand instance = new UpdateUserCommand();
        GenericDAO dao = EasyMock.createMock(GenericDAO.class);        
        //EasyMock.replay(dao);
        instance.setDao(dao);
        
        EasyMock.expect(dao.get(periscopeUser.getId(), PeriscopeUser.class)).andReturn(ManagerMock.createPeriscopeUser(1));
        EasyMock.replay(dao);        
        instance.execute(periscopeUser);
        
        EasyMock.verify(dao);
        //fail("The test case is a prototype.");
    }

    

}