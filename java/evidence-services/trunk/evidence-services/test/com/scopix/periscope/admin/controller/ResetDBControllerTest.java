/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.admin.controller;

import com.scopix.periscope.admin.EvidenceServicesAdminService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nelson
 */
public class ResetDBControllerTest {

    public ResetDBControllerTest() {
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
        ResetDBController instance = new ResetDBController();
        EvidenceServicesAdminService service = Mockito.mock(EvidenceServicesAdminService.class, "Servicio de Test");
        instance.setAdminService(service);
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ModelAndView result = instance.handleRequestInternal(request, response);
        Assert.assertNotNull("Se espera que no sea null", result);        
    }
}