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
 *  GetStoreListCommandTest.java
 * 
 *  Created on 22-09-2010, 03:15:33 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAO;
import java.util.ArrayList;
import java.util.List;
import mockup.CorporateStructureManagementHibernateDAOMock;
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
public class GetStoreListCommandTest {

    public GetStoreListCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetStoreListCommandTest");
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
        Store store = null;
        GetStoreListCommand instance = new GetStoreListCommand();
        instance.setDao(new CorporateStructureManagementHibernateDAOMock());
        List<Store> expResult = new ArrayList<Store>();
        expResult.add(ManagerMock.genStore(1, "Store 1", "Store 1"));
        expResult.add(ManagerMock.genStore(2, "Store 2", "Store 2"));
        List<Store> result = instance.execute(store);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getName(), result.get(1).getName());
    }
}
