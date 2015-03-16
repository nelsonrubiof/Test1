/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.controllers;

import com.scopix.periscope.corporatestructuremanagement.services.webservices.CorporateWebServices;
import com.scopix.periscope.evidencemanagement.FileManager;
import java.io.InputStream;
import java.util.Map;
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
public class GetCorporateLogoControllerTest {

    public GetCorporateLogoControllerTest() {
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
        GetCorporateLogoController instance = new GetCorporateLogoController();
        CorporateWebServices service = Mockito.mock(CorporateWebServices.class, "Servicio de test");
        FileManager fileManager = Mockito.mock(FileManager.class, "Servicio de test");
        Map ret = Mockito.mock(Map.class, "Contenido de File de Test");
        InputStream is = Mockito.mock(InputStream.class, "InputStream de  Test");

        HttpServletRequest request = null;
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class, "Response de Test");

        instance.setFileManager(fileManager);
        instance.setCorporateWebServices(service);

        Mockito.when(fileManager.getFile(Mockito.anyString())).thenReturn(ret);
        Mockito.when(ret.get("is")).thenReturn(is);
        Mockito.when(ret.get("size")).thenReturn(1);
        Mockito.when(is.read(Mockito.any(byte[].class))).thenReturn(1, -2);


        ModelAndView expResult = null;
        ModelAndView result = instance.handleRequestInternal(request, response);
        Assert.assertEquals("Se esepra que de Model sea null", expResult, result);
    }

    @Test
    public void testGetCorporateWebServices() {
        GetCorporateLogoController instance = new GetCorporateLogoController();
        CorporateWebServices result = instance.getCorporateWebServices();
        Assert.assertNotNull("Se espera que retorne un instancia de Servicio", result);
    }

    @Test
    public void testGetFileManager() {
        GetCorporateLogoController instance = new GetCorporateLogoController();
        FileManager result = instance.getFileManager();
        Assert.assertNotNull("Se espera que el manager sea dstinto de null", result);
    }
}
