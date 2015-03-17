package com.scopix.periscope.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * Clase composer de la ventana popup zoom que se muestra en la evaluación de imágenes
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class PopCenterImageEvalBean extends GenericForwardComposer implements Serializable {

    private String login;
    private Session mySession;
    private CenterImageEvalBean centerImageEvalBean;
    private static final long serialVersionUID = -5687012830505206438L;
    private static Logger log = Logger.getLogger(PopCenterImageEvalBean.class);

    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);
            Sessions.getCurrent().setAttribute("popCenterImageBean", this);
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * @return the centerImageEvalBean
     */
    public CenterImageEvalBean getCenterImageEvalBean() {
        return centerImageEvalBean;
    }

    /**
     * @param centerImageEvalBean the centerImageEvalBean to set
     */
    public void setCenterImageEvalBean(CenterImageEvalBean centerImageEvalBean) {
        this.centerImageEvalBean = centerImageEvalBean;
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