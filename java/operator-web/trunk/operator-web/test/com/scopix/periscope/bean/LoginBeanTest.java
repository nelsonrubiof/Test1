package com.scopix.periscope.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Textbox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Queue;

/**
 * Clase de pruebas de com.scopix.periscope.bean.LoginBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class LoginBeanTest {

    public LoginBeanTest() {
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
    public void testOnAfterRenderClientes() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        // Inyecta dependencia
        loginBean.setCmbClientes(cmbClientes);
        // Invoca método a probar
        loginBean.onAfterRenderClientes(null);
        // Verifica ejecución
        Mockito.verify(cmbClientes).setSelectedIndex(0);
    }

    /**
     * Se invoca una vez seleccionado un cliente para mostrar sus respectivas colas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnSelectClientes() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock objects
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencia
        loginBean.setCmbClientes(cmbClientes);
        loginBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("-1");
        // Invoca método a probar
        loginBean.onSelectClientes(null);
        // Verifica comportamiento
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Se invoca una vez seleccionado un cliente para mostrar sus respectivas colas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnSelectClientes2() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock objects
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Client> lstClient = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setUniqueCorporateId("0");
        Client client2 = new Client();
        client2.setUniqueCorporateId("1");
        lstClient.add(client1);
        lstClient.add(client2);
        // Inyecta dependencia
        loginBean.setLstClientes(lstClient);
        loginBean.setCmbClientes(cmbClientes);
        loginBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        loginBean.onSelectClientes(null);
        // Verifica comportamiento
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Se invoca una vez seleccionado un cliente para mostrar sus respectivas colas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnSelectClientes3() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock objects
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Queue> lstQueue = new ArrayList();
        List<Client> lstClient = new ArrayList<Client>();

        Client client1 = new Client();
        client1.setUniqueCorporateId("0");
        Client client2 = new Client();
        client2.setUniqueCorporateId("1");

        client2.setQueues(lstQueue);

        lstClient.add(client1);
        lstClient.add(client2);
        // Inyecta dependencia
        loginBean.setLstClientes(lstClient);
        loginBean.setCmbClientes(cmbClientes);
        loginBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        loginBean.onSelectClientes(null);
        // Verifica comportamiento
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Se invoca una vez seleccionado un cliente para mostrar sus respectivas colas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnSelectClientes4() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock objects
        Combobox cmbColas = Mockito.mock(Combobox.class);
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        AnnotateDataBinder dataBinder = Mockito.mock(AnnotateDataBinder.class);

        List<Queue> lstQueue = new ArrayList();
        List<Client> lstClient = new ArrayList<Client>();

        Client client1 = new Client();
        client1.setUniqueCorporateId("0");
        Client client2 = new Client();
        client2.setUniqueCorporateId("1");

        Queue queue1 = new Queue();
        queue1.setName("name");
        lstQueue.add(queue1);

        client2.setQueues(lstQueue);

        lstClient.add(client1);
        lstClient.add(client2);
        // Inyecta dependencia
        loginBean.setCmbColas(cmbColas);
        loginBean.setLstClientes(lstClient);
        loginBean.setCmbClientes(cmbClientes);
        loginBean.setShowMessage(showMessage);
        loginBean.setAnnotateDataBinder(dataBinder);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        loginBean.onSelectClientes(null);
        // Verifica comportamiento
        Mockito.verify(dataBinder).loadComponent(cmbColas);
        Mockito.verify(cmbColas).open();
    }

    /**
     * Se invoca una vez seleccionado un cliente para mostrar sus respectivas colas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnSelectClientes5() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock objects
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Client> lstClient = new ArrayList<Client>();
        // Inyecta dependencia
        loginBean.setLstClientes(lstClient);
        loginBean.setCmbClientes(cmbClientes);
        loginBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        loginBean.onSelectClientes(null);
    }

    @Test
    public void testGetTxtLogin() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        Textbox txtLogin = Mockito.mock(Textbox.class);
        // Se inyecta dependencia
        loginBean.setTxtLogin(txtLogin);

        Assert.assertNotNull(loginBean.getTxtLogin());
        Assert.assertEquals(txtLogin, loginBean.getTxtLogin());
    }

    @Test
    public void testGetCmbColas() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        Combobox cmbColas = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        loginBean.setCmbColas(cmbColas);

        Assert.assertNotNull(loginBean.getCmbColas());
        Assert.assertEquals(cmbColas, loginBean.getCmbColas());
    }

    @Test
    public void testGetTxtPassword() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        Textbox txtPassword = Mockito.mock(Textbox.class);
        // Se inyecta dependencia
        loginBean.setTxtPassword(txtPassword);

        Assert.assertNotNull(loginBean.getTxtPassword());
        Assert.assertEquals(txtPassword, loginBean.getTxtPassword());
    }

    @Test
    public void testGetCmbClientes() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        loginBean.setCmbClientes(cmbClientes);

        Assert.assertNotNull(loginBean.getCmbClientes());
        Assert.assertEquals(cmbClientes, loginBean.getCmbClientes());
    }

    @Test
    public void testGetLstColas() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        List<Queue> lstColas = new ArrayList<Queue>();
        // Se inyecta dependencia
        loginBean.setLstColas(lstColas);

        Assert.assertNotNull(loginBean.getLstColas());
        Assert.assertEquals(lstColas, loginBean.getLstColas());
    }

    @Test
    public void testGetLstClientes() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        List<Client> lstClientes = new ArrayList<Client>();
        // Se inyecta dependencia
        loginBean.setLstClientes(lstClientes);

        Assert.assertNotNull(loginBean.getLstClientes());
        Assert.assertEquals(lstClientes, loginBean.getLstClientes());
    }

    @Test
    public void testGetAnnotateDataBinder() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        AnnotateDataBinder dataBinder = Mockito.mock(AnnotateDataBinder.class);
        // Se inyecta dependencia
        loginBean.setAnnotateDataBinder(dataBinder);

        Assert.assertNotNull(loginBean.getAnnotateDataBinder());
        Assert.assertEquals(dataBinder, loginBean.getAnnotateDataBinder());
    }

    @Test(expected = NullPointerException.class)
    public void testGetAnnotateDataBinder2() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        Assert.assertNotNull(loginBean.getAnnotateDataBinder());
        // Espera null por el objeto page
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        loginBean.setShowMessage(showMessage);

        Assert.assertNotNull(loginBean.getShowMessage());
        Assert.assertEquals(showMessage, loginBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        Assert.assertNotNull(loginBean.getShowMessage());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        loginBean.setExecution(execution);

        Assert.assertNotNull(loginBean.getExecution());
        Assert.assertEquals(execution, loginBean.getExecution());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        loginBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(loginBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, loginBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        Assert.assertNotNull(loginBean.getUserCredentialManager());
    }

    @Test
    public void testGetOperatorManager() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        // Crea mock object
        OperatorManager opManager = Mockito.mock(OperatorManager.class);
        // Se inyecta dependencia
        loginBean.setOperatorManager(opManager);

        Assert.assertNotNull(loginBean.getOperatorManager());
        Assert.assertEquals(opManager, loginBean.getOperatorManager());
    }

    @Test
    public void testGetOperatorManager2() {
        // Crea instancia de la clase a probar
        LoginBean loginBean = new LoginBean();
        Assert.assertNotNull(loginBean.getOperatorManager());
    }
}