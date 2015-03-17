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
 *  AddUserCommandTest.java
 * 
 *  Created on 05-11-2010, 11:37:13 AM
 * 
 */

package com.scopix.periscope.securitymanagement.commands;


import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.dao.UserLoginHibernateDAO;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author nelson
 */
public class AddUserCommandTest extends MockObjectTestCase {

    public AddUserCommandTest() {
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
        PeriscopeUser periscopeUser = new PeriscopeUser();
        periscopeUser.setName("marko.perich");
        AddUserCommand instance = new AddUserCommand();
        
        Mock mockGenericDAO = new Mock(GenericDAO.class);
        Mock mockUserLoginDAO = new Mock(UserLoginHibernateDAO.class);

        GenericDAO genericDAO = (GenericDAO) mockGenericDAO.proxy();
        mockGenericDAO.expects(once()).method("save");

        UserLoginHibernateDAO loginHibernateDAO = (UserLoginHibernateDAO) mockUserLoginDAO.proxy();
        mockUserLoginDAO.expects(once()).method("getUserByName");

        instance.setGenericDAO(genericDAO);
        instance.setUserLoginHibernateDAO(loginHibernateDAO);

        instance.execute(periscopeUser);
        
    }

    @Test
    public void testExecute2() throws Exception {
        System.out.println("execute2");
        PeriscopeUser periscopeUser = new PeriscopeUser();
        periscopeUser.setName("marko.perich");
        AddUserCommand instance = new AddUserCommand();

        Mock mockGenericDAO = new Mock(GenericDAO.class);
        Mock mockUserLoginDAO = new Mock(UserLoginHibernateDAO.class);

        GenericDAO genericDAO = (GenericDAO) mockGenericDAO.proxy();
        mockGenericDAO.expects(once()).method("get").will(returnValue(this));

        UserLoginHibernateDAO loginHibernateDAO = (UserLoginHibernateDAO) mockUserLoginDAO.proxy();
        mockUserLoginDAO.expects(once()).method("getUserByName").will(returnValue(new PeriscopeUser()));

        instance.setGenericDAO(genericDAO);
        instance.setUserLoginHibernateDAO(loginHibernateDAO);

        try {
            instance.execute(periscopeUser);
            fail("Se experaba una excepcion");
        } catch (ScopixException e) {
        }

    }
}