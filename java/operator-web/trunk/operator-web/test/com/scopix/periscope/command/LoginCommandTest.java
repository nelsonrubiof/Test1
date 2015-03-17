package com.scopix.periscope.command;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase de pruebas de com.scopix.periscope.command.LoginCommand
 * 
 * @author Carlos
 */
public class LoginCommandTest {
    
    public LoginCommandTest() {
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
//        //Instancia clase a probar
//        LoginCommand loginCommand = new LoginCommand();
//        //Crea mock object
//        Class securityWebService = Mockito.mock(Class.class);
//        //Define comportamiento
//        Long resultado = new Long(0);
//        Mockito.when(securityWebService.login("carpolva", "carpolva", 0)).thenReturn(resultado);
//        //Invoca método a probar
//        Long result = loginCommand.execute("carpolva", "carpolva", securityWebService);
//        Assert.assertNotNull(result);
//        Assert.assertEquals(result, resultado);
    }
    
    @Test
    public void testExecute2() throws Exception {
//        //Instancia clase a probar
//        LoginCommand loginCommand = new LoginCommand();
//        //Crea mock object
//        Class securityWebService = Mockito.mock(Class.class);
//        PeriscopeException periscopeException = Mockito.mock(PeriscopeException.class);
//
//        try{
//            //Define comportamiento
//            Mockito.when(securityWebService.login("carpolva", "carpolva", 0)).thenThrow(periscopeException);
//            Mockito.when(periscopeException.getMessage()).thenReturn("message");
//            //Invoca método a probar
//            loginCommand.execute("carpolva", "carpolva", securityWebService);
//
//        } catch (ScopixException ex) {
//            Assert.assertEquals(ex.getMessage(), "message");
//        }
    }
    
    @Test
    public void testExecute3() throws Exception {
//        //Instancia clase a probar
//        LoginCommand loginCommand = new LoginCommand();
//        //Crea mock object
//        Class securityWebService = Mockito.mock(Class.class);
//        PeriscopeSecurityException periscopeException = Mockito.mock(PeriscopeSecurityException.class);
//
//        try{
//            //Define comportamiento
//            Mockito.when(securityWebService.login("carpolva", "carpolva", 0)).thenThrow(periscopeException);
//            Mockito.when(periscopeException.getMessage()).thenReturn("message");
//            //Invoca método a probar
//            loginCommand.execute("carpolva", "carpolva", securityWebService);
//
//        } catch (ScopixException ex) {
//            Assert.assertEquals(ex.getMessage(), "message");
//        }
    }
}