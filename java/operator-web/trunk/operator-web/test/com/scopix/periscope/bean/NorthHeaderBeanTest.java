package com.scopix.periscope.bean;

import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Label;

/**
 * Clase de pruebas de com.scopix.periscope.bean.NorthHeaderBean
 * 
 * @author Carlos
 */
public class NorthHeaderBeanTest {
    
    public NorthHeaderBeanTest() {
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
    public void testDoAfterCompose() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Session mySession = Mockito.mock(Session.class);
        Component component = Mockito.mock(Component.class);
        GlobalEvaluation evaluacionBean = Mockito.mock(GlobalEvaluation.class);
        UserCredentialManager usrCredentialManager = Mockito.mock(UserCredentialManager.class);
        //Inyecta dependencia
        northBean.setMySession(mySession);
        northBean.setEvaluacionBean(evaluacionBean);
        northBean.setUserCredentialManager(usrCredentialManager);
        //Define comportamiento
        Mockito.when(mySession.getAttribute("LOGIN")).thenReturn("login");
        Mockito.when(usrCredentialManager.isAuthenticated()).thenReturn(true);
        //Invoca método a probar
        northBean.doAfterCompose(component);
        //Verifica ejecución
        Mockito.verify(evaluacionBean).northComponentReady(northBean);
    }

    @Test
    public void testGetEvaluacionBean() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        GlobalEvaluation evalBean = Mockito.mock(GlobalEvaluation.class);
        //Se inyecta dependencia
        northBean.setEvaluacionBean(evalBean);

        Assert.assertNotNull(northBean.getEvaluacionBean());
        Assert.assertEquals(evalBean, northBean.getEvaluacionBean());
    }

    @Test
    public void testGetLblLogin() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblLogin = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblLogin(lblLogin);

        Assert.assertNotNull(northBean.getLblLogin());
        Assert.assertEquals(lblLogin, northBean.getLblLogin());
    }

    @Test
    public void testGetLblStore() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblStore = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblStore(lblStore);

        Assert.assertNotNull(northBean.getLblStore());
        Assert.assertEquals(lblStore, northBean.getLblStore());
    }

    @Test
    public void testGetLblClient() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblClient = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblClient(lblClient);

        Assert.assertNotNull(northBean.getLblClient());
        Assert.assertEquals(lblClient, northBean.getLblClient());
    }

    @Test
    public void testGetLblZone() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblZone = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblZone(lblZone);

        Assert.assertNotNull(northBean.getLblZone());
        Assert.assertEquals(lblZone, northBean.getLblZone());
    }

    @Test
    public void testGetLblEvidenceDate() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblEvidenceDate = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblEvidenceDate(lblEvidenceDate);

        Assert.assertNotNull(northBean.getLblEvidenceDate());
        Assert.assertEquals(lblEvidenceDate, northBean.getLblEvidenceDate());
    }

    @Test
    public void testGetLogin() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblLogin = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblLogin(lblLogin);
        //Define comportamiento
        String login = "login";
        Mockito.when(lblLogin.getValue()).thenReturn(login);
        //Invoca método a probar
        northBean.getLogin();
        //Verifica ejecución
        Assert.assertEquals(login, northBean.getLogin());
        Mockito.verify(lblLogin, Mockito.atLeastOnce()).getValue();
    }

    @Test
    public void testGetClientName() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblClient = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblClient(lblClient);
        //Define comportamiento
        String clientName = "name";
        Mockito.when(lblClient.getValue()).thenReturn(clientName);
        //Invoca método a probar
        northBean.getClientName();
        //Verifica ejecución
        Assert.assertEquals(clientName, northBean.getClientName());
        Mockito.verify(lblClient, Mockito.atLeastOnce()).getValue();
    }

    @Test
    public void testGetStoreName() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblStore = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblStore(lblStore);
        //Define comportamiento
        String storeName = "name";
        Mockito.when(lblStore.getValue()).thenReturn(storeName);
        //Invoca método a probar
        northBean.getStoreName();
        //Verifica ejecución
        Assert.assertEquals(storeName, northBean.getStoreName());
        Mockito.verify(lblStore, Mockito.atLeastOnce()).getValue();
    }

    @Test
    public void testGetZoneName() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblZone = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblZone(lblZone);
        //Define comportamiento
        String zoneName = "name";
        Mockito.when(lblZone.getValue()).thenReturn(zoneName);
        //Invoca método a probar
        northBean.getZoneName();
        //Verifica ejecución
        Assert.assertEquals(zoneName, northBean.getZoneName());
        Mockito.verify(lblZone, Mockito.atLeastOnce()).getValue();
    }

    @Test
    public void testGetEvidenceDate() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Label lblEvidenceDate = Mockito.mock(Label.class);
        //Se inyecta dependencia
        northBean.setLblEvidenceDate(lblEvidenceDate);
        //Define comportamiento
        String evDate = "date";
        Mockito.when(lblEvidenceDate.getValue()).thenReturn(evDate);
        //Invoca método a probar
        northBean.getEvidenceDate();
        //Verifica ejecución
        Assert.assertEquals(evDate, northBean.getEvidenceDate());
        Mockito.verify(lblEvidenceDate, Mockito.atLeastOnce()).getValue();
    }

    @Test
    public void testGetUserCredentialManager() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        //Se inyecta dependencia
        northBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(northBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, northBean.getUserCredentialManager());
    }
    
    @Test(expected=NullPointerException.class)
    public void testGetUserCredentialManager2() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        Assert.assertNotNull(northBean.getUserCredentialManager());
    }

    @Test
    public void testGetExecution() {
        //Crea instancia de la clase a probar
        NorthHeaderBean northBean = new NorthHeaderBean();
        //Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        //Se inyecta dependencia
        northBean.setExecution(execution);

        Assert.assertNotNull(northBean.getExecution());
        Assert.assertEquals(execution, northBean.getExecution());
    }
}