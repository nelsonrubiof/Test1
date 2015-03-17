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
 *  HttpServletUtilsTest.java
 * 
 *  Created on 17-05-2013, 11:58:13 AM
 * 
 */
package com.scopix.periscope.operatorimages.services;

import javax.servlet.http.HttpServletResponse;
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
public class HttpServletUtilsTest {
    
    public HttpServletUtilsTest() {
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
    public void testGetResponse() {
        try {
            HttpServletUtils instance = new HttpServletUtils();
            HttpServletResponse expResult = null;
            HttpServletResponse result = instance.getResponse();
            fail("para test no puede llegar ya que busca PhaseInterceptorChain.getCurrentMessage() ");
        } catch (Exception e) {
            assertTrue("Se espera Excepcion", true);
        }
        
    }
}