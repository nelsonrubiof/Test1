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
 *  DeleteContainerTest.java
 * 
 *  Created on 07-06-2013, 12:14:44 PM
 * 
 */
package com.scopix.periscope.operatorimages;

import java.util.ArrayList;
import java.util.List;
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
public class DeleteContainerTest {

    public DeleteContainerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetPathOrigen() {
        DeleteContainer instance = new DeleteContainer();
        String expResult = "";
        instance.setPathOrigen(expResult);
        String result = instance.getPathOrigen();
        assertEquals(expResult, result);        
    }

    @Test
    public void testGetList() {
        DeleteContainer instance = new DeleteContainer();
        List<String> expResult = new ArrayList<String>();
        instance.setList(expResult);
        List result = instance.getList();
        assertEquals(expResult, result);        
    }
}