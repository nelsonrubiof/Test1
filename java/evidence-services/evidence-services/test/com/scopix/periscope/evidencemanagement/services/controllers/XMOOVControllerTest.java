/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evidencemanagement.services.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nelson
 */
public class XMOOVControllerTest {
    
    public XMOOVControllerTest() {
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
    public void testHandleRequestInternal() throws Exception {
        System.out.println("handleRequestInternal");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        XMOOVController instance = new XMOOVController();
        ModelAndView expResult = null;
        ModelAndView result = instance.handleRequestInternal(request, response);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}