package com.scopix.periscope.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.common.XMLParser;
import com.scopix.periscope.enums.EnumLabels;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Corporate;
import com.scopix.periscope.model.Equivalence;
import com.scopix.periscope.model.Office;
import com.scopix.periscope.model.Queue;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase para procesamiento del inicio de sesión de la aplicación
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class LoginBean extends GenericForwardComposer implements Serializable {

    private Textbox txtLogin;
    private Session mySession;
    private Combobox cmbColas;
    private Textbox txtPassword;
    private Combobox cmbClientes;
    private Combobox cmbOficinas;
    private List<Queue> lstColas;
    private ShowMessage showMessage;
    private List<Client> lstClientes;
    private List<Office> lstOficinas;
    private OperatorManager operatorManager;
    private AnnotateDataBinder annotateDataBinder;
    private UserCredentialManager userCredentialManager;
    private static Logger log = Logger.getLogger(LoginBean.class);
    private static final long serialVersionUID = -8083591450298308774L;

    /**
     * Auto forward events and wire accessible variables of the specified component into a controller Java object; a subclass that
     * override this method should remember to call super.doAfterCompose(comp) or it will not work.
     *
     * @author carlos polo
     * @version 1.0.0
     * @param comp componente
     * @since 6.0 @date 04/02/2013
     */
    @Override
    public void doAfterCompose(Component comp) {
        log.info("start");
        try {
            super.doAfterCompose(comp);

            if (getUserCredentialManager().isAuthenticated()) {
                // Si el usuario ya se encuentra autenticado, lo redirige a la pantalla de evaluación
                String login = ((String) getMySession().getAttribute("LOGIN"));
                log.debug("usuario autenticado, se redireccionara a la pantalla: evaluation.zul - " + login);
                getExecution().sendRedirect("evaluation.zul");
            } else {
                log.debug("antes de getXmlParser().parseAppParameters()");
                List<Corporate> corporateList = getXmlParser().parseAppCorporates();

                String snapshotTime = getOperatorManager().getSnapshotTime();
                log.debug("snapshotTime: " + snapshotTime);

                getMySession().setAttribute("SNAP_TIME", snapshotTime);
                log.debug("despues de getXmlParser().parseAppParameters() y getSnapshotTime");
                // Convierte corporates a objetos client del modelo
                parseCorporates(corporateList);
                // Obtiene y establece en sesión número de intentos parametrizado en caso de fallos durante
                // el envío de las evaluaciones
                getRetryNumber();

                setOfficesList();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("end");
    }

    /**
     * Obtiene oficinas parametrizadas
     *
     * @author carlos polo
     * @version 2.0.5
     * @since 6.0
     * @date 24/07/2014
     */
    private void setOfficesList() {
        log.info("start");
        List<Office> lstOffices = getXmlParser().parseAppOffices();
        Collections.sort(lstOffices);

        Office office0 = new Office();
        office0.setName(EnumLabels.COMBO_DEFAULT.toString());
        lstOffices.add(0, office0);

        setLstOficinas(lstOffices);
        log.info("end");
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
        log.info("start");
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
                log.error(e.getMessage(), e);
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

    public void onAfterRenderOficinas(ForwardEvent event) {
        getCmbOficinas().setSelectedIndex(0);
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
        log.info("start");
        String titulo = EnumLabels.CLIENTE.toString();
        String idCliente = getCmbClientes().getSelectedItem().getValue();

        if (idCliente.equals("-1")) {
            String mensaje = EnumLabels.CMBCLIENTES_TOOLTIP.toString();

            // Por favor seleccione un cliente
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        } else {
            // Carga las colas del cliente seleccionado
            for (Client cliente : lstClientes) {
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
        log.info("end");
    }

    public void onSelectColas(ForwardEvent event) {
        // Abre el combo de colas del cliente
        getCmbOficinas().open();
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
        String login = getTxtLogin().getValue();
        log.info("start - " + login);
        String password = getTxtPassword().getValue();

        String nombreCola = getCmbColas().getSelectedItem().getValue();
        String idCliente = getCmbClientes().getSelectedItem().getValue();
        String nombreOficina = getCmbOficinas().getSelectedItem().getValue().toString().toUpperCase();

        try {
            if ("-1".equals(idCliente)) {
                String titulo = EnumLabels.CLIENTE.toString();
                String mensaje = EnumLabels.CMBCLIENTES_TOOLTIP.toString();
                // Por favor seleccione un cliente
                getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);

            } else if (Labels.getLabel("default.opcionPredeterminada").equals(nombreCola)) {
                String titulo = EnumLabels.COLA.toString();
                String mensaje = EnumLabels.CMBCOLAS_TOOLTIP.toString();
                // Por favor seleccione una cola
                getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);

            } else if (Labels.getLabel("default.opcionPredeterminada").equalsIgnoreCase(nombreOficina)) {
                String titulo = EnumLabels.OFICINA.toString();
                String mensaje = EnumLabels.CMBOFICINA_TOOLTIP.toString();
                // Por favor seleccione la oficina en donde se encuentre ubicado
                getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);

            } else {
                if ("DEFAULT".equalsIgnoreCase(nombreCola)) {
                    nombreCola = null;
                    log.debug("nombre cola es DEFAULT, se envia null - " + login);
                }

                for (Client cliente : lstClientes) {
                    if (cliente.getUniqueCorporateId().equalsIgnoreCase(idCliente)) {
                        log.debug("invocando servicio de autenticacion, login - " + login);
                        // Pasó exitosamente las validaciones, procede a invocar servicio de autenticación
                        Long sessionId = getUserCredentialManager().authenticate(login, password, cliente.getSecurity());

                        if (sessionId != null) {
                            getMySession().setAttribute("CLIENT", cliente);
                            getMySession().setAttribute("QUEUE_NAME", nombreCola);
                            getMySession().setAttribute("LOGIN", login);
                            getMySession().setAttribute("SESSION_ID", sessionId);

                            // Obtiene equivalencias de la oficina
                            List<Equivalence> officeEquivalences = getOfficeEquivalences(nombreOficina, login);
                            getMySession().setAttribute("OFFICE_EQUIVALENCES", officeEquivalences);

                            log.debug("obteniendo situacion para evaluar. " + "Cliente: [" + cliente.getName() + "], cola: ["
                                    + nombreCola + "] - " + login);
                            Situation situation = getOperatorManager().getNextEvidence(nombreCola, cliente.getName(), sessionId,
                                    login);

                            if (situation == null) {
                                log.debug("no existen elementos por evaluar - " + login);
                                String mensaje = Labels.getLabel("eval.noSituacionEval"); // No existen elementos por evaluar
                                String titulo = Labels.getLabel("eval.noSituacionEvalTitle");

                                Messagebox.show(mensaje, titulo, Messagebox.OK, Messagebox.QUESTION, new EventListener() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        if (((Integer) event.getData()).intValue() == Messagebox.OK) {
                                            return;
                                        }
                                    }
                                });
                            } else {
                                log.debug("redireccionando a pantalla de evaluacion - " + login);
                                session.setAttribute("FIRST_SITUATION", situation);
                                getExecution().sendRedirect("evaluation.zul");
                            }
                        } else {
                            log.debug("usuario o clave incorrectos - " + login);
                            String titulo = EnumLabels.INGRESO.toString();
                            String mensaje = EnumLabels.LOGIN_ERROR.toString();
                            // Usuario o clave incorrectos
                            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.ERROR);
                        }
                    }
                }
            }
        } catch (ScopixException ex) {
            log.error(ex.getMessage() + " - " + login, ex);
        }
        log.info("end - " + login);
    }

    /**
     * Obtiene equivalencias de la oficina
     * 
     * @param officeName
     * @param login
     * @return
     */
    private List<Equivalence> getOfficeEquivalences(String officeName, String login) {
        log.info("start, officeName: [" + officeName + "] - " + login);
        List<Equivalence> officeEquivalences = null;

        for (Office office : getLstOficinas()) {
            if (office.getName().toUpperCase().equalsIgnoreCase(officeName)) {
                officeEquivalences = office.getEquivalences();
                break;
            }
        }
        log.info("end - " + login);
        return officeEquivalences;
    }

    /**
     * Obtiene número de intentos parametrizado en caso de fallos durante el envío de las evaluaciones
     *
     * @author carlos polo
     * @version 1.1.5
     * @since 6.0
     * @date 22/07/2013
     */
    protected void getRetryNumber() {
        log.info("start");
        Integer retryNumber = getOperatorManager().getRetryNumber();
        getMySession().setAttribute("RETRY_NUMBER", retryNumber);
        log.info("end, retryNumber: " + retryNumber);
    }

    /**
     * @return the txtLogin
     */
    public Textbox getTxtLogin() {
        return txtLogin;
    }

    /**
     * @param txtLogin the txtLogin to set
     */
    public void setTxtLogin(Textbox txtLogin) {
        this.txtLogin = txtLogin;
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

    /**
     * @return the txtPassword
     */
    public Textbox getTxtPassword() {
        return txtPassword;
    }

    /**
     * @param txtPassword the txtPassword to set
     */
    public void setTxtPassword(Textbox txtPassword) {
        this.txtPassword = txtPassword;
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
     * @return the userCredentialManager
     */
    public UserCredentialManager getUserCredentialManager() {
        if (userCredentialManager == null) {
            userCredentialManager = UserCredentialManager.getIntance(getMySession());
        }
        return userCredentialManager;
    }

    /**
     * @param userCredentialManager the userCredentialManager to set
     */
    public void setUserCredentialManager(UserCredentialManager userCredentialManager) {
        this.userCredentialManager = userCredentialManager;
    }

    /**
     * @return the execution
     */
    public Execution getExecution() {
        return execution;
    }

    /**
     * @param execution the execution to set
     */
    public void setExecution(Execution execution) {
        this.execution = execution;
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
     * @return the xmlParser
     */
    public XMLParser getXmlParser() {
        return XMLParser.getInstance();
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

    /**
     * @return the lstOficinas
     */
    public List<Office> getLstOficinas() {
        return lstOficinas;
    }

    /**
     * @param lstOficinas the lstOficinas to set
     */
    public void setLstOficinas(List<Office> lstOficinas) {
        this.lstOficinas = lstOficinas;
    }

    /**
     * @return the cmbOficinas
     */
    public Combobox getCmbOficinas() {
        return cmbOficinas;
    }

    /**
     * @param cmbOficinas the cmbOficinas to set
     */
    public void setCmbOficinas(Combobox cmbOficinas) {
        this.cmbOficinas = cmbOficinas;
    }
}