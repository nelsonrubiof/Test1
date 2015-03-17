package com.scopix.periscope.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Radiogroup;

/**
 * Clase composer de la ventana popup (casos "no se puede evaluar") que se muestra al presionar los correspondientes botónes en la
 * parte izquierda de la pantalla
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class PopLeftMetricBean extends GenericForwardComposer implements Serializable {

    private String login;
    private Session mySession;
    private Radiogroup myRadioGroup;
    private LeftMetricBean leftMetricBean;
    private static final long serialVersionUID = -1241380083905352453L;
    private static Logger log = Logger.getLogger(PopLeftMetricBean.class);

    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);
            Sessions.getCurrent().setAttribute("popLeftMetricBean", this);
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Invocado cuando se presiona el botón para guardar la información relacionada con definir que no se pudo evaluar una o todas
     * las métricas
     *
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 04/02/2013
     */
    public void onClickNoEvalGuardar(ForwardEvent event) {
        log.info("start - " + login);
        leftMetricBean.onClickNoEvalGuardar(event);
        log.info("end - " + login);
    }

    /**
     * @return the leftMetricBean
     */
    public LeftMetricBean getLeftMetricBean() {
        return leftMetricBean;
    }

    /**
     * @param leftMetricBean the leftMetricBean to set
     */
    public void setLeftMetricBean(LeftMetricBean leftMetricBean) {
        this.leftMetricBean = leftMetricBean;
    }

    /**
     * @return the myRadioGroup
     */
    public Radiogroup getMyRadioGroup() {
        return myRadioGroup;
    }

    /**
     * @param myRadioGroup the myRadioGroup to set
     */
    public void setMyRadioGroup(Radiogroup myRadioGroup) {
        this.myRadioGroup = myRadioGroup;
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