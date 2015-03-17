package com.scopix.periscope.manager;

import com.scopix.periscope.command.LoginCommand;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Clase de pruebas de com.scopix.periscope.manager.SecurityManager
 * 
 * @author Carlos
 */
public class SecurityManagerTest {
    
    public SecurityManagerTest() {
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

    /**
     * Invocación servicio para obtener lista de clientes
     * 
     * @author  carlos polo
     * @version 1.0.0
     * @since   6.0
     * @date    26/03/2013
     */
//    @Test
//    public void testAuthenticateUser() throws Exception {
//        //Crea instancia de la clase a probar
//        SecurityManager secManager = new SecurityManager();
//        //Crea mock object
//        Class securityWebService = Mockito.mock(Class.class);
//        LoginCommand loginCommand = Mockito.mock(LoginCommand.class);
//        //Inyecta dependencia
//        secManager.setLoginCommand(loginCommand);
//        secManager.setSecurityWebService(securityWebService);
//        //Define comportamiento
//        Long param = new Long(0);
//        Mockito.when(loginCommand.execute("login", "password", securityWebService)).thenReturn(param);
//        //Se invoca el método a probar
//        Long result = secManager.authenticateUser("login", "password");
//        //Verifica ejecución
//        Assert.assertNotNull(result);
//        Assert.assertEquals(result, param);
//    }

    @Test
    public void testGetLoginCommand() {
        //Crea instancia de la clase a probar
        SecurityManager secManager = new SecurityManager();
        //Crea mock object
        LoginCommand nextEvCommand = Mockito.mock(LoginCommand.class);
        //Inyecta dependencia
        secManager.setLoginCommand(nextEvCommand);

        Assert.assertNotNull(secManager.getLoginCommand());
        Assert.assertEquals(nextEvCommand, secManager.getLoginCommand());
    }
    
    @Test
    public void testGetLoginCommand2() {
        //Crea instancia de la clase a probar
        SecurityManager secManager = new SecurityManager();
        Assert.assertNotNull(secManager.getLoginCommand());
    }

    @Test
    public void testHash256() throws Exception {
        Assert.assertNotNull(SecurityManager.hash256("data", "login"));
    }

    @Test
    public void testBytesToHex() {
        Assert.assertNotNull(SecurityManager.bytesToHex("data".getBytes(), "login"));
    }
}