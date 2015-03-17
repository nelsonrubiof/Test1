package com.scopix.periscope.bean;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * Clase composer de la ventana popup zoom que se muestra en la evaluación de videos
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class PopCenterVideoEvalBean extends GenericForwardComposer implements Serializable {

    private String login;
    private Session mySession;
    private CenterVideoEvalBean centerVideoEvalBean;
    private static final long serialVersionUID = 4849752702738046967L;
    private static Logger log = Logger.getLogger(PopCenterVideoEvalBean.class);

    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);
            Sessions.getCurrent().setAttribute("popCenterVideoBean", this);
        } catch (Exception ex) {
            log.error(ex.getMessage() + " - " + login, ex);
        }
        log.info("end - " + login);
    }

    /**
     * @return the centerVideoEvalBean
     */
    public CenterVideoEvalBean getCenterVideoEvalBean() {
        return centerVideoEvalBean;
    }

    /**
     * @param centerVideoEvalBean the centerVideoEvalBean to set
     */
    public void setCenterVideoEvalBean(CenterVideoEvalBean centerVideoEvalBean) {
        this.centerVideoEvalBean = centerVideoEvalBean;
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