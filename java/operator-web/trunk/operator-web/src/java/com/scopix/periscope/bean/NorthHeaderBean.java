package com.scopix.periscope.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Label;

import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import com.scopix.periscope.interfaces.NorthEvalHeader;

/**
 * Clase encargada del manejo de los textos encabezados en la parte superior de la pantalla de evaluación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class NorthHeaderBean extends GenericForwardComposer implements Serializable, NorthEvalHeader {

    private String login;
    private Label lblZone;
    private Label lblLogin;
    private Label lblStore;
    private Label lblClient;
    private Session mySession;
    private Label lblEvidenceDate;
    private GlobalEvaluation evaluacionBean;
    private UserCredentialManager userCredentialManager;
    private static final long serialVersionUID = 2298248291547042757L;
    private static Logger log = Logger.getLogger(NorthHeaderBean.class);

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
                // Notifica al bean "padre" que finalizó su construcción
                getEvaluacionBean().northComponentReady(this);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * @return the evaluacionBean
     */
    public GlobalEvaluation getEvaluacionBean() {
        return evaluacionBean;
    }

    /**
     * @param evaluacionBean the evaluacionBean to set
     */
    public void setEvaluacionBean(GlobalEvaluation evaluacionBean) {
        this.evaluacionBean = evaluacionBean;
    }

    /**
     * @return the lblLogin
     */
    public Label getLblLogin() {
        return lblLogin;
    }

    /**
     * @param lblLogin the lblLogin to set
     */
    public void setLblLogin(Label lblLogin) {
        this.lblLogin = lblLogin;
    }

    /**
     * @return the lblStore
     */
    public Label getLblStore() {
        return lblStore;
    }

    /**
     * @param lblStore the lblStore to set
     */
    public void setLblStore(Label lblStore) {
        this.lblStore = lblStore;
    }

    /**
     * @return the lblClient
     */
    public Label getLblClient() {
        return lblClient;
    }

    /**
     * @param lblClient the lblClient to set
     */
    public void setLblClient(Label lblClient) {
        this.lblClient = lblClient;
    }

    /**
     * @return the lblZone
     */
    public Label getLblZone() {
        return lblZone;
    }

    /**
     * @param lblZone the lblZone to set
     */
    public void setLblZone(Label lblZone) {
        this.lblZone = lblZone;
    }

    /**
     * @return the lblEvidenceDate
     */
    public Label getLblEvidenceDate() {
        return lblEvidenceDate;
    }

    /**
     * @param lblEvidenceDate the lblEvidenceDate to set
     */
    public void setLblEvidenceDate(Label lblEvidenceDate) {
        this.lblEvidenceDate = lblEvidenceDate;
    }

    @Override
    public String getLogin() {
        return getLblLogin().getValue();
    }

    @Override
    public void setLogin(String login) {
        getLblLogin().setValue(login);
    }

    @Override
    public String getClientName() {
        return getLblClient().getValue();
    }

    @Override
    public void setClientName(String clientName) {
        getLblClient().setValue(clientName);
    }

    @Override
    public String getStoreName() {
        return getLblStore().getValue();
    }

    @Override
    public void setStoreName(String storeName) {
        getLblStore().setValue(storeName);
    }

    @Override
    public String getZoneName() {
        return getLblZone().getValue();
    }

    @Override
    public void setZoneName(String zoneName) {
        getLblZone().setValue(zoneName);
    }

    @Override
    public String getEvidenceDate() {
        return getLblEvidenceDate().getValue();
    }

    @Override
    public void setEvidenceDate(String evidenceDate) {
        getLblEvidenceDate().setValue(evidenceDate);
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
}