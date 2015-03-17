package com.scopix.periscope.common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase de pruebas de com.scopix.periscope.common.ShowMessage
 * 
 * @author Carlos
 */
public class ShowMessageTest {
    
    public ShowMessageTest() {
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
    public void testGetInstance2() {
        //Crea instancia de la clase a probar
        ShowMessage showMessage = new ShowMessage();
        //Crea mock object
        ShowMessage showMessage2 = Mockito.mock(ShowMessage.class);
        //Inyecta dependencia
        showMessage.setInstance(showMessage2);
        //Invoca método a probar
        Assert.assertNotNull(showMessage.getInstance());
    }

    @Test
    public void testGetInstance() {
        //Crea instancia de la clase a probar
        ShowMessage showMessage = new ShowMessage();
        showMessage.setInstance(null);
        Assert.assertNotNull(showMessage.getInstance());
    }
    
    @Test
    public void testSetInstance() {
        //Crea instancia de la clase a probar
        ShowMessage showMessage = new ShowMessage();
        //Crea mock object
        ShowMessage showMessage2 = Mockito.mock(ShowMessage.class);
        //Inyecta dependencia
        showMessage.setInstance(showMessage2);
        //Invoca método a probar
        Assert.assertNotNull(showMessage.getInstance());
    }

    @Test(expected=NullPointerException.class)
    public void testMostrarMensaje() {
        //Crea instancia de la clase a probar
        ShowMessage showMessage = new ShowMessage();
        showMessage.mostrarMensaje("1", "1", "1");
    }
}