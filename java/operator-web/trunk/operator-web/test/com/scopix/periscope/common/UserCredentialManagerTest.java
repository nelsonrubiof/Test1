package com.scopix.periscope.common;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Session;

import com.scopix.periscope.manager.SecurityManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Clase de pruebas de com.scopix.periscope.common.UserCredentialManager
 * 
 * @author Carlos
 */
public class UserCredentialManagerTest {
    
    public UserCredentialManagerTest() {
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
    public void testGetIntance_0args() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        //Crea mock objects
        Session mySession = Mockito.mock(Session.class);
        //Inyecta dependencia
        usrCredentialManager.setMySession(mySession);
        //Invoca método a probar y verifica ejecución
        Assert.assertNotNull(usrCredentialManager.getIntance());
    }

    @Test
    public void testGetIntance_Session() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        //Crea mock objects
        Session mySession = Mockito.mock(Session.class);
        //Define comportamiento
        Mockito.when(mySession.getAttribute(Matchers.anyString())).thenReturn(null);
        //Invoca método a probar y verifica ejecución
        Assert.assertNotNull(usrCredentialManager.getIntance(mySession));
    }
    
    @Test
    public void testGetIntance_Session2() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        //Crea mock objects
        Session mySession = Mockito.mock(Session.class);
        UserCredentialManager usrModel = Mockito.mock(UserCredentialManager.class);
        //Define comportamiento
        Mockito.when(mySession.getAttribute(Matchers.anyString())).thenReturn(usrModel);
        //Invoca método a probar y verifica ejecución
        Assert.assertNotNull(usrCredentialManager.getIntance(mySession));
    }

    @Test
    public void testAuthenticate() throws ScopixException {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        //Crea mock object
        Session mySession = Mockito.mock(Session.class);
        SecurityManager secManager = Mockito.mock(SecurityManager.class);
        //Se inyecta dependencia
        usrCredentialManager.setMySession(mySession);
        usrCredentialManager.setSecManager(secManager);
        //Define comportamiento
        Mockito.when(secManager.authenticateUser(Matchers.anyString(), Matchers.anyString(), "1")).thenReturn(new Long(0));
        //Invoca método a probar
        Assert.assertNotNull(usrCredentialManager.authenticate("user", "password", "1"));
    }
    
    @Test
    public void testAuthenticate2() throws ScopixException {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        //Crea mock object
        SecurityManager secManager = Mockito.mock(SecurityManager.class);
        ScopixException scopixException = Mockito.mock(ScopixException.class);
        //Se inyecta dependencia
        usrCredentialManager.setSecManager(secManager);
        //Define comportamiento
        Mockito.when(secManager.authenticateUser(Matchers.anyString(), Matchers.anyString(), "1")).thenThrow(scopixException);
        //Invoca método a probar
        Assert.assertNull(usrCredentialManager.authenticate("user", "password", "1"));
    }

    @Test
    public void testIsAuthenticated() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        boolean authenticated = true;
        //Se inyecta dependencia
        usrCredentialManager.setAuthenticated(authenticated);

        Assert.assertNotNull(usrCredentialManager.isAuthenticated());
        Assert.assertEquals(authenticated, usrCredentialManager.isAuthenticated());
    }

    @Test
    public void testGetSecManager() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        //Crea mock object
        SecurityManager secManager = Mockito.mock(SecurityManager.class);
        //Se inyecta dependencia
        usrCredentialManager.setSecManager(secManager);

        Assert.assertNotNull(usrCredentialManager.getSecManager());
        Assert.assertEquals(secManager, usrCredentialManager.getSecManager());
    }
    
    @Test
    public void testGetSecManager2() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        Assert.assertNotNull(usrCredentialManager.getSecManager());
    }
    
    @Test
    public void testGetMySession() {
        //Crea instancia de la clase a probar
        UserCredentialManager usrCredentialManager = new UserCredentialManager();
        usrCredentialManager.setMySession(null);
        //en realidad no debe retornar null, solo que para pruebas no se puede testear 
        //el comportamiento de Sessions.getCurrent();
        Assert.assertNull(usrCredentialManager.getMySession());
    }
}