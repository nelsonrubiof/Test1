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
 *  GetEvidenceProviderListCommandTest.java
 * 
 *  Created on 27-09-2010, 04:19:08 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
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
public class GetEvidenceProviderListCommandTest {

    public GetEvidenceProviderListCommandTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        System.out.println("GetEvidenceProviderListCommandTest");
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
        EvidenceProvider evidenceProvider = null;
        GetEvidenceProviderListCommand instance = new GetEvidenceProviderListCommand();        
        instance.setDao(new CorporateStructureManagementHibernateDAOMock());
        List<EvidenceProvider> expResult = new ArrayList<EvidenceProvider>();
        expResult.add(ManagerMock.genEvidenceProvider(1, "Evidence Provider 1", "Evidence Provider 1"));
        expResult.add(ManagerMock.genEvidenceProvider(2, "Evidence Provider 2", "Evidence Provider 2"));
        List<EvidenceProvider> result = instance.execute(evidenceProvider);
        assertEquals(expResult.size(), result.size());
        assertEquals(expResult.get(1).getName(), result.get(1).getName());
        
    }
}
