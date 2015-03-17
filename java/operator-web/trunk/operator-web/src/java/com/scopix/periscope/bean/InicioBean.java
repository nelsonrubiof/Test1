package com.scopix.periscope.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.common.XMLParser;
import com.scopix.periscope.enums.EnumLabels;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Corporate;
import com.scopix.periscope.model.Queue;
import com.scopix.periscope.model.UserEvaluation;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase asociada a la pantalla de inicio
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class InicioBean extends GenericForwardComposer implements Serializable {

    private String login;
    private Combobox cmbColas;
    private Session mySession;
    private Combobox cmbClientes;
    private List<Queue> lstColas;
    private ShowMessage showMessage;
    private List<Client> lstClientes;
    private Label lblConfirmCloseInicio;
    private OperatorManager operatorManager;
    private AnnotateDataBinder annotateDataBinder;
    private UserCredentialManager userCredentialManager;
    private static final long serialVersionUID = -8192215429001537554L;
    private static Logger log = Logger.getLogger(InicioBean.class);

    /**
     * Auto forward events and wire accessible variables of the specified component into a controller Java object; a subclass that
     * override this method should remember to call super.doAfterCompose(comp) or it will not work.
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param comp componente
     * @since 6.0
     * @date 04/02/2013
     */
    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);

            // Si el usuario no se encuentra autenticado, lo redirige a la pantalla de login
            if (!getUserCredentialManager().isAuthenticated()) {
                getExecution().sendRedirect("login.zul");
            } else {
                // Texto: Por favor presione en "Cerrar Sesión" en la presente
                // pantalla para una correcta salida de la aplicación.
                getLblConfirmCloseInicio().setValue(Labels.getLabel("eval.confirmClose2"));
                log.debug("antes de getXmlParser().parseAppParameters() - " + login);
                List<Corporate> corporateList = getXmlParser().parseAppCorporates();
                log.debug("despues de getXmlParser().parseAppParameters() - " + login);

                parseCorporates(corporateList);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Convierte corporates a objetos client del modelo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param lstCorporate
     * @since 6.0
     * @date 25/04/2013
     */
    protected void parseCorporates(List<Corporate> lstCorporate) {
        log.info("start - " + login);
        List<Client> listaCombo = new ArrayList<Client>();

        // Cliente predeterminado
        Client cliente0 = new Client();
        cliente0.setUniqueCorporateId("-1");
        cliente0.setName(EnumLabels.COMBO_DEFAULT.toString()); // opción del combo: "por favor seleccione..."
        cliente0.setDescription(EnumLabels.COMBO_DEFAULT.toString());

        List<Queue> colas0 = new ArrayList<Queue>();
        Queue cola1 = new Queue();
        cola1.setName("");
        cola1.setId(-1);
        colas0.add(cola1);
        cliente0.setQueues(colas0);

        listaCombo.add(cliente0);

        for (Corporate corporate : lstCorporate) {
            Client cliente = new Client();
            try {
                cliente.setName(corporate.getName());
                cliente.setSecurity(corporate.getSecurity());
                cliente.setProofPath(corporate.getProofPath());
                cliente.setDescription(corporate.getDescription());
                cliente.setUniqueCorporateId(corporate.getId());
                cliente.setTemplateUrl(corporate.getTemplateUrl());
                cliente.setEvidenceUrl(corporate.getEvidenceUrl());
                cliente.setOperatorImgServicesURL(corporate.getOperatorImgServicesURL());
                cliente.setOperatorImgPrivateServicesURL(corporate.getOperatorImgPrivateServicesURL());

                // Invoca servicio para obtener las colas del cliente
                log.debug("antes de obtener colas para cliente: [" + corporate.getName() + "]");
                List<Queue> lstQueues = getOperatorManager().getClientQueues(corporate.getName().toLowerCase());

                Queue queue1 = new Queue();
                queue1.setName("DEFAULT");
                lstQueues.add(queue1);

                // Invoca manager con servicio para obtener las colas del cliente
                cliente.setQueues(lstQueues);
            } catch (Exception e) {
                log.error(e.getMessage() + " - " + login, e);
                continue;
            }
            log.debug("adicionando al combobox el cliente: [" + cliente.getName() + "]");
            listaCombo.add(cliente);
        }

        setLstClientes(listaCombo);
        if (!getLstClientes().isEmpty()) {
            // Carga lista de colas vacía (hasta que se seleccione un cliente)
            setLstColas(getLstClientes().get(0).getQueues());
        }

        log.info("end");
    }

    /**
     * Se invoca una vez renderizado el combo de clientes para ubicarlo en su primera posición
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 04/02/2013
     */
    public void onAfterRenderClientes(ForwardEvent event) {
        getCmbClientes().setSelectedIndex(0);
    }

    /**
     * Se invoca una vez seleccionado un cliente para mostrar sus respectivas colas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 04/02/2013
     */
    public void onSelectClientes(ForwardEvent event) {
        log.info("start - " + login);
        String titulo = EnumLabels.CLIENTE.toString();
        String idCliente = getCmbClientes().getSelectedItem().getValue();

        if (idCliente.equals("-1")) {
            String mensaje = EnumLabels.CMBCLIENTES_TOOLTIP.toString();

            // Por favor seleccione un cliente
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        } else {
            // Carga las colas del cliente seleccionado
            for (Client cliente : getLstClientes()) {
                if (cliente.getUniqueCorporateId().equalsIgnoreCase(idCliente)) {
                    List<Queue> colasCliente = cliente.getQueues();

                    if (colasCliente != null && colasCliente.size() > 0) {
                        // Setea las colas del combo de clientes
                        setLstColas(colasCliente);
                        // Refresca el combo de las colas del cliente
                        getAnnotateDataBinder().loadComponent(getCmbColas());
                        // Abre el combo de colas del cliente
                        getCmbColas().open();
                    } else {
                        String mensaje = EnumLabels.CLIENTE_NO_COLAS.toString();
                        // No existen colas para el cliente seleccionado
                        getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
                    }
                    break;
                }
            }
        }
        log.info("end - " + login);
    }

    /**
     * Validación de datos ingresados para el inicio de sesión
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 04/02/2013
     */
    public void onClickIngresar(ForwardEvent event) {
        log.info("start - " + login);
        String nombreCola = getCmbColas().getSelectedItem().getValue();
        String idCliente = getCmbClientes().getSelectedItem().getValue();

        if (idCliente.equals("-1")) {
            String titulo = EnumLabels.CLIENTE.toString();
            String mensaje = EnumLabels.CMBCLIENTES_TOOLTIP.toString();
            // Por favor seleccione un cliente
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        } else if (nombreCola.equals(Labels.getLabel("default.opcionPredeterminada"))) {
            String titulo = EnumLabels.COLA.toString();
            String mensaje = EnumLabels.CMBCOLAS_TOOLTIP.toString();
            // Por favor seleccione una cola
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        } else {
            if ("DEFAULT".equalsIgnoreCase(nombreCola)) {
                nombreCola = null;
                log.debug("nombre cola es DEFAULT, se envia null");
            }

            for (Client cliente : getLstClientes()) {
                if (cliente.getUniqueCorporateId().equalsIgnoreCase(idCliente)) {
                    getMySession().setAttribute("CLIENT", cliente);
                    getMySession().setAttribute("QUEUE_NAME", nombreCola);

                    log.debug("cliente: " + cliente.getName() + ", cola: " + nombreCola + " - " + login);
                    // No necesita mostrar mensaje de confirmación de cierre/recarga de página
                    Clients.evalJavaScript("needToConfirmInicio = false;");
                    getExecution().sendRedirect("evaluation.zul");
                    break;
                }
            }
        }
        log.info("end - " + login);
    }

    /**
     * Cierra la sesión actual
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 04/02/2013
     */
    public void onClickCerrarSesion(ForwardEvent event) {
        log.info("start - " + login);
        // Invoca servicio para cerrar la sesión a nivel de security
        Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");
        Client cliente = (Client) getMySession().getAttribute("CLIENT");

        if (sessionId != null) {
            UserEvaluation userEvaluation = getOperatorManager().getHmUsersEvaluations().get(sessionId);
            if (userEvaluation != null) {
                getOperatorManager().getHmUsersEvaluations().remove(userEvaluation);
            }
            // logout en security
            getUserCredentialManager().getSecManager().logout(sessionId, login, cliente.getSecurity());
        }

        // Cierra la sesión web
        closeWebSession();
        // No necesita mostrar mensaje de confirmación de cierre/recarga de página
        Clients.evalJavaScript("needToConfirmInicio = false;");
        // Redirige a la pantalla de login
        getExecution().sendRedirect("login.zul");
        log.info("end - " + login);
    }

    /**
     * Cierra la sesión web
     */
    private void closeWebSession() {
        log.info("start - " + login);
        Session mySession = Sessions.getCurrent();
        // Cierra la Http session
        HttpSession s = (HttpSession) mySession.getNativeSession();
        s.invalidate();
        // Cierra la Zk session
        mySession.invalidate();
        log.info("end - " + login);
    }

    /**
     * @return the operatorManager
     */
    public OperatorManager getOperatorManager() {
        if (operatorManager == null) {
            operatorManager = SpringSupport.getInstance().findBeanByClassName(OperatorManager.class);
        }
        return operatorManager;
    }

    /**
     * @param operatorManager the operatorManager to set
     */
    public void setOperatorManager(OperatorManager operatorManager) {
        this.operatorManager = operatorManager;
    }

    /**
     * @return the lstClientes
     */
    public List<Client> getLstClientes() {
        return lstClientes;
    }

    /**
     * @param lstClientes the lstClientes to set
     */
    public void setLstClientes(List<Client> lstClientes) {
        this.lstClientes = lstClientes;
    }

    /**
     * @return the lstColas
     */
    public List<Queue> getLstColas() {
        return lstColas;
    }

    /**
     * @param lstColas the lstColas to set
     */
    public void setLstColas(List<Queue> lstColas) {
        this.lstColas = lstColas;
    }

    /**
     * @return the cmbClientes
     */
    public Combobox getCmbClientes() {
        return cmbClientes;
    }

    /**
     * @param cmbClientes the cmbClientes to set
     */
    public void setCmbClientes(Combobox cmbClientes) {
        this.cmbClientes = cmbClientes;
    }

    /**
     * @return the showMessage
     */
    public ShowMessage getShowMessage() {
        if (showMessage == null) {
            showMessage = ShowMessage.getInstance();
        }
        return showMessage;
    }

    /**
     * @param showMessage the showMessage to set
     */
    public void setShowMessage(ShowMessage showMessage) {
        this.showMessage = showMessage;
    }

    /**
     * @return the annotateDataBinder
     */
    public AnnotateDataBinder getAnnotateDataBinder() {
        if (annotateDataBinder == null) {
            annotateDataBinder = (AnnotateDataBinder) page.getAttribute("binder");
        }
        return annotateDataBinder;
    }

    /**
     * @param annotateDataBinder the annotateDataBinder to set
     */
    public void setAnnotateDataBinder(AnnotateDataBinder annotateDataBinder) {
        this.annotateDataBinder = annotateDataBinder;
    }

    /**
     * @return the cmbColas
     */
    public Combobox getCmbColas() {
        return cmbColas;
    }

    /**
     * @param cmbColas the cmbColas to set
     */
    public void setCmbColas(Combobox cmbColas) {
        this.cmbColas = cmbColas;
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

    /**
     * @return the xmlParser
     */
    public XMLParser getXmlParser() {
        return XMLParser.getInstance();
    }

    /**
     * @param userCredentialManager the userCredentialManager to set
     */
    public void setUserCredentialManager(UserCredentialManager userCredentialManager) {
        this.userCredentialManager = userCredentialManager;
    }

    /**
     * @return the userCredentialManager
     */
    public UserCredentialManager getUserCredentialManager() {
        if (userCredentialManager == null) {
            userCredentialManager = UserCredentialManager.getIntance(getMySession());
        }
        return userCredentialManager;
    }

    /**
     * @return the lblConfirmCloseInicio
     */
    public Label getLblConfirmCloseInicio() {
        return lblConfirmCloseInicio;
    }

    /**
     * @param lblConfirmCloseInicio the lblConfirmCloseInicio to set
     */
    public void setLblConfirmCloseInicio(Label lblConfirmCloseInicio) {
        this.lblConfirmCloseInicio = lblConfirmCloseInicio;
    }

    /**
     * @return the mySession
     */
    public Session getMySession() {
        if (mySession == null) {
            mySession = Sessions.getCurrent();
        }
        return mySession;
    }

    /**
     * @param mySession the mySession to set
     */
    public void setMySession(Session mySession) {
        this.mySession = mySession;
    }
}