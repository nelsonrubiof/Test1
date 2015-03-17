package com.scopix.periscope.bean;

import java.util.ArrayList;
import java.util.Iterator;
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

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Queue;

/**
 * Clase de pruebas de com.scopix.periscope.bean.InicioBean
 * 
 * @author Carlos
 */
@SuppressWarnings(value = { "unchecked", "rawtypes" })
public class InicioBeanTest {

    public InicioBeanTest() {
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
     * Se invoca una vez renderizado el combo de clientes para ubicarlo en su primera posición
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnAfterRenderClientes() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        // Inyecta dependencia
        inicioBean.setCmbClientes(cmbClientes);
        // Invoca método a probar
        inicioBean.onAfterRenderClientes(null);
        // Verifica comportamiento
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
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencia
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("-1");
        // Invoca método a probar
        inicioBean.onSelectClientes(null);
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
        InicioBean inicioBean = new InicioBean();
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
        inicioBean.setLstClientes(lstClient);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onSelectClientes(null);
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
        InicioBean inicioBean = new InicioBean();
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
        inicioBean.setLstClientes(lstClient);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onSelectClientes(null);
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
        InicioBean inicioBean = new InicioBean();
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
        inicioBean.setCmbColas(cmbColas);
        inicioBean.setLstClientes(lstClient);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        inicioBean.setAnnotateDataBinder(dataBinder);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onSelectClientes(null);
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
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Comboitem comboItem = Mockito.mock(Comboitem.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Client> lstClient = new ArrayList<Client>();
        // Inyecta dependencia
        inicioBean.setLstClientes(lstClient);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(comboItem);
        Mockito.when(comboItem.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onSelectClientes(null);
    }

    /**
     * Validación de datos ingresados para el inicio de sesión
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnClickIngresar() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Combobox cmbColas = Mockito.mock(Combobox.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        Comboitem cmbItemColas = Mockito.mock(Comboitem.class);
        Comboitem cmbItemClientes = Mockito.mock(Comboitem.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencia
        inicioBean.setCmbColas(cmbColas);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbColas.getSelectedItem()).thenReturn(cmbItemColas);
        Mockito.when(cmbItemColas.getValue()).thenReturn("-1");
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(cmbItemClientes);
        Mockito.when(cmbItemClientes.getValue()).thenReturn("-1");
        // Invoca método a probar
        inicioBean.onClickIngresar(null);
        // Verifica comportamiento
        Mockito.verify(showMessage).mostrarMensaje(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());
    }

    /**
     * Validación de datos ingresados para el inicio de sesión
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnClickIngresar2() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Client cliente = Mockito.mock(Client.class);
        Combobox cmbColas = Mockito.mock(Combobox.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        List<Client> lstClientes = Mockito.mock(List.class);
        Comboitem cmbItemColas = Mockito.mock(Comboitem.class);
        Iterator<Client> iterator = Mockito.mock(Iterator.class);
        Comboitem cmbItemClientes = Mockito.mock(Comboitem.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Inyecta dependencia
        inicioBean.setCmbColas(cmbColas);
        inicioBean.setLstClientes(lstClientes);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbColas.getSelectedItem()).thenReturn(cmbItemColas);
        Mockito.when(cmbItemColas.getValue()).thenReturn("");
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(cmbItemClientes);
        Mockito.when(cmbItemClientes.getValue()).thenReturn("1");

        Mockito.when(lstClientes.iterator()).thenReturn(iterator);
        Mockito.when(iterator.hasNext()).thenReturn(true).thenReturn(false);
        Mockito.when(iterator.next()).thenReturn(cliente);
        Mockito.when(cliente.getUniqueCorporateId()).thenReturn("2");
        // Invoca método a probar
        inicioBean.onClickIngresar(null);
        // Verifica comportamiento
        Mockito.verify(showMessage, Mockito.never()).mostrarMensaje(Matchers.anyString(), Matchers.anyString(),
                Matchers.anyString());
    }

    /**
     * Validación de datos ingresados para el inicio de sesión
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test(expected = NullPointerException.class)
    public void testOnClickIngresar3() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Combobox cmbColas = Mockito.mock(Combobox.class);
        Execution execution = Mockito.mock(Execution.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        Comboitem cmbItemColas = Mockito.mock(Comboitem.class);
        Comboitem cmbItemClientes = Mockito.mock(Comboitem.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Client> lstClient = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setUniqueCorporateId("1");
        lstClient.add(client1);
        inicioBean.setLstClientes(lstClient);
        // Inyecta dependencia
        inicioBean.setCmbColas(cmbColas);
        inicioBean.setExecution(execution);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbColas.getSelectedItem()).thenReturn(cmbItemColas);
        Mockito.when(cmbItemColas.getValue()).thenReturn("1");
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(cmbItemClientes);
        Mockito.when(cmbItemClientes.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onClickIngresar(null);
        // Se espera NullPointerException por Sessions.getCurrent();
    }

    /**
     * Validación de datos ingresados para el inicio de sesión
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnClickIngresar4() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Combobox cmbColas = Mockito.mock(Combobox.class);
        Execution execution = Mockito.mock(Execution.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        Comboitem cmbItemColas = Mockito.mock(Comboitem.class);
        Comboitem cmbItemClientes = Mockito.mock(Comboitem.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Client> lstClient = new ArrayList<Client>();
        inicioBean.setLstClientes(lstClient);
        // Inyecta dependencia
        inicioBean.setCmbColas(cmbColas);
        inicioBean.setExecution(execution);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbColas.getSelectedItem()).thenReturn(cmbItemColas);
        Mockito.when(cmbItemColas.getValue()).thenReturn("1");
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(cmbItemClientes);
        Mockito.when(cmbItemClientes.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onClickIngresar(null);
    }

    /**
     * Validación de datos ingresados para el inicio de sesión
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event
     * @since 6.0
     * @date 04/02/2013
     */
    @Test
    public void testOnClickIngresar5() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock objects
        Combobox cmbColas = Mockito.mock(Combobox.class);
        Execution execution = Mockito.mock(Execution.class);
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        Comboitem cmbItemColas = Mockito.mock(Comboitem.class);
        Comboitem cmbItemClientes = Mockito.mock(Comboitem.class);
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);

        List<Client> lstClient = new ArrayList<Client>();
        Client client1 = new Client();
        client1.setUniqueCorporateId("2");
        lstClient.add(client1);
        inicioBean.setLstClientes(lstClient);
        // Inyecta dependencia
        inicioBean.setCmbColas(cmbColas);
        inicioBean.setExecution(execution);
        inicioBean.setCmbClientes(cmbClientes);
        inicioBean.setShowMessage(showMessage);
        // Define comportamiento
        Mockito.when(cmbColas.getSelectedItem()).thenReturn(cmbItemColas);
        Mockito.when(cmbItemColas.getValue()).thenReturn("1");
        Mockito.when(cmbClientes.getSelectedItem()).thenReturn(cmbItemClientes);
        Mockito.when(cmbItemClientes.getValue()).thenReturn("1");
        // Invoca método a probar
        inicioBean.onClickIngresar(null);
        // Se espera NullPointerException por Sessions.getCurrent();
    }

    @Test
    public void testGetOperatorManager() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        OperatorManager opManager = Mockito.mock(OperatorManager.class);
        // Se inyecta dependencia
        inicioBean.setOperatorManager(opManager);

        Assert.assertNotNull(inicioBean.getOperatorManager());
        Assert.assertEquals(opManager, inicioBean.getOperatorManager());
    }

    @Test
    public void testGetOperatorManager2() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        Assert.assertNotNull(inicioBean.getOperatorManager());
    }

    @Test
    public void testGetLstClientes() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        List<Client> lstClientes = new ArrayList<Client>();
        // Se inyecta dependencia
        inicioBean.setLstClientes(lstClientes);

        Assert.assertNotNull(inicioBean.getLstClientes());
        Assert.assertEquals(lstClientes, inicioBean.getLstClientes());
    }

    @Test
    public void testGetLstColas() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        List<Queue> lstColas = new ArrayList<Queue>();
        // Se inyecta dependencia
        inicioBean.setLstColas(lstColas);

        Assert.assertNotNull(inicioBean.getLstColas());
        Assert.assertEquals(lstColas, inicioBean.getLstColas());
    }

    @Test
    public void testGetCmbClientes() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        Combobox cmbClientes = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        inicioBean.setCmbClientes(cmbClientes);

        Assert.assertNotNull(inicioBean.getCmbClientes());
        Assert.assertEquals(cmbClientes, inicioBean.getCmbClientes());
    }

    @Test
    public void testGetShowMessage() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        ShowMessage showMessage = Mockito.mock(ShowMessage.class);
        // Se inyecta dependencia
        inicioBean.setShowMessage(showMessage);

        Assert.assertNotNull(inicioBean.getShowMessage());
        Assert.assertEquals(showMessage, inicioBean.getShowMessage());
    }

    @Test
    public void testGetShowMessage2() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        Assert.assertNotNull(inicioBean.getShowMessage());
    }

    @Test
    public void testGetAnnotateDataBinder() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        AnnotateDataBinder dataBinder = Mockito.mock(AnnotateDataBinder.class);
        // Se inyecta dependencia
        inicioBean.setAnnotateDataBinder(dataBinder);

        Assert.assertNotNull(inicioBean.getAnnotateDataBinder());
        Assert.assertEquals(dataBinder, inicioBean.getAnnotateDataBinder());
    }

    @Test(expected = NullPointerException.class)
    public void testGetAnnotateDataBinder2() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        Assert.assertNotNull(inicioBean.getAnnotateDataBinder());
        // Espera null por el objeto page
    }

    @Test
    public void testGetCmbColas() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        Combobox cmbColas = Mockito.mock(Combobox.class);
        // Se inyecta dependencia
        inicioBean.setCmbColas(cmbColas);

        Assert.assertNotNull(inicioBean.getCmbColas());
        Assert.assertEquals(cmbColas, inicioBean.getCmbColas());
    }

    @Test
    public void testGetExecution() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        Execution execution = Mockito.mock(Execution.class);
        // Se inyecta dependencia
        inicioBean.setExecution(execution);

        Assert.assertNotNull(inicioBean.getExecution());
        Assert.assertEquals(execution, inicioBean.getExecution());
    }

    @Test
    public void testGetUserCredentialManager() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        // Crea mock object
        UserCredentialManager userCredentialMgr = Mockito.mock(UserCredentialManager.class);
        // Se inyecta dependencia
        inicioBean.setUserCredentialManager(userCredentialMgr);

        Assert.assertNotNull(inicioBean.getUserCredentialManager());
        Assert.assertEquals(userCredentialMgr, inicioBean.getUserCredentialManager());
    }

    @Test(expected = NullPointerException.class)
    public void testGetUserCredentialManager2() {
        // Crea instancia de la clase a probar
        InicioBean inicioBean = new InicioBean();
        Assert.assertNotNull(inicioBean.getUserCredentialManager());
    }
}