package com.scopix.periscope.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.East;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.West;

import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.GlobalEvaluation;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.interfaces.NorthEvalHeader;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.model.UserEvaluation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.thread.SpritesPreloadThread;

/**
 * Clase controladora para procesamiento de las evaluaciones de evidencias
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class EvaluationBean extends GenericForwardComposer implements Serializable, GlobalEvaluation {

    private String login;
    private West westSide;
    private North northSide;
    private Session mySession;
    private Situation situation;
    private LeftMetricBean leftMetricBean;
    private Borderlayout evalBorderLayout;
    private LeftMetricList metricInterface;
    private OperatorManager operatorManager;
    private NorthHeaderBean northHeaderBean;
    private NorthEvalHeader headerInterface;
    private UserCredentialManager userCredentialManager;
    private static final long serialVersionUID = 4781542327756363839L;
    private static Logger log = Logger.getLogger(EvaluationBean.class);

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
                // Obtiene la siguiente evidencia por evaluar
                setSituation();
            }
            // Clients.evalJavaScript("activateEvaluationsValuesInterval();");
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Obtiene la siguiente situación
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 04/02/2013
     */
    @Override
    public void setSituation() {
        log.info("start - " + login);
        this.situation = (Situation) Sessions.getCurrent().getAttribute("FIRST_SITUATION");

        try {
            if (situation == null) {
                // Obtiene de la sesión los datos obtenidos al momento de iniciar sesión
                String queueName = (String) getMySession().getAttribute("QUEUE_NAME");
                Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");

                Client cliente = (Client) getMySession().getAttribute("CLIENT");

                // Obtiene siguiente situación
                this.situation = getOperatorManager().getNextEvidence(queueName, cliente.getName(), sessionId, login);

                if (situation == null) {
                    String mensaje = Labels.getLabel("eval.noSituacionEval2");
                    String titulo = Labels.getLabel("eval.noSituacionEvalTitle");
                    log.debug("No hay situaciones por evaluar");

                    Messagebox.show(mensaje, titulo, Messagebox.OK, Messagebox.QUESTION, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            if (((Integer) event.getData()).intValue() == Messagebox.OK) {
                                Clients.evalJavaScript("needToConfirmCenterImg = false;");
                                getExecution().sendRedirect("inicio.zul");
                            }
                        }
                    });
                } else {
                    // Prepara la inicialización del bean y la pantalla de la parte izquierda
                    prepareWestSide();
                    // Prepara la inicialización del bean y la pantalla de la parte superior
                    prepareNorthSide();
                }
            } else {
                Sessions.getCurrent().removeAttribute("FIRST_SITUATION");
                // Prepara la inicialización del bean y la pantalla de la parte izquierda
                prepareWestSide();
                // Prepara la inicialización del bean y la pantalla de la parte superior
                prepareNorthSide();
            }

            Clients.evalJavaScript("clearCronoInterval(); initCronoInterval();");
        } catch (ScopixException e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte superior
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 20/02/2013
     */
    protected void prepareNorthSide() {
        log.info("start - " + login);
        // Instancia un nuevo bean de encabezados (parte superior de la pantalla)
        setNorthHeaderBean(new NorthHeaderBean());
        // Obtiene parte superior del borderlayout
        setNorthSide(getEvalBorderLayout().getNorth());
        // Crea página a ser incluída en la parte superior
        Include northInclude = new Include();
        northInclude.setSrc("northHeaders.zul");
        // Vincula a nivel de eventos la página con la instancia del bean
        getNorthHeaderBean().bindComponent(northInclude);

        if (!getNorthSide().getChildren().isEmpty()) {
            getNorthSide().removeChild(getNorthSide().getChildren().get(0));
        }
        // Adiciona en la parte superior del borderlayout la página a incluir
        getNorthSide().appendChild(northInclude);
        // Se le establece al bean de la parte superior quién será su controller (bean padre)
        getNorthHeaderBean().setEvaluacionBean(this);
        log.info("end - " + login);
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte izquierda
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 20/02/2013
     */
    protected void prepareWestSide() {
        log.info("start - " + login);
        // Instancia un nuevo bean de métricas (parte izquierda de la pantalla)
        setLeftMetricBean(new LeftMetricBean());
        // Obtiene parte izquierda del borderlayout
        setWestSide(getEvalBorderLayout().getWest());
        // Crea página a ser incluída en la parte izquierda
        Include leftInclude = new Include();
        leftInclude.setSrc("leftMetrics.zul");
        // Vincula a nivel de eventos la página con la instancia del bean
        getLeftMetricBean().bindComponent(leftInclude);

        if (!getWestSide().getChildren().isEmpty()) {
            getWestSide().removeChild(getWestSide().getChildren().get(0));
        }
        // Adiciona en la parte izquierda del borderlayout la página a incluir
        getWestSide().appendChild(leftInclude);
        // Se le establece al bean de la parte izquierda quién será su controller (bean padre)
        getLeftMetricBean().setEvaluacionBean(this);
        log.info("end - " + login);
    }

    @Override
    public void setRightInclude(Include rightInclude) {
        log.info("start - " + login);
        East east = getEvalBorderLayout().getEast();
        // Si el componente de la derecha ya tiene un hijo, se elimina
        if (!east.getChildren().isEmpty()) {
            east.removeChild(east.getChildren().get(0));
        }
        east.appendChild(rightInclude);
        log.info("end - " + login);
    }

    /**
     * Método invocado una vez el bean de la parte izquierda (west) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    @Override
    public void leftComponentReady(LeftMetricList bean) {
        log.info("start - " + login);
        // Establece la instancia de la interfáz como el bean recién construído
        setMetricInterface(bean);

        ListModelList metricasModel = new ListModelList();
        List<Metric> lstMetricas = getSituation().getMetricas();

        for (Metric metrica : lstMetricas) {
            metricasModel.add(metrica);
        }

        // Le envía el listado de métricas al bean de la parte izquierda
        getMetricInterface().setMetricasModel(metricasModel);
        getMetricInterface().setSituation(getSituation());

        // Verifica si existe una observación de parte del área de calidad
        if (getSituation().isRejected()) {
            // Envía la observación de calidad
            String observation = getSituation().getRejectedObservation();
            getMetricInterface().setQualityObservation(observation);
        }

        precargarArchivos();
        log.info("end - " + login);
    }

    private void precargarArchivos() {
        log.info("start - " + login);
        try {
            String imagesURLs = "";
            String videoNames = "";
            String evidencesBasePath = "/data/ftp/evidence/";
            Client cliente = (Client) getMySession().getAttribute("CLIENT");
            String defaultOperatorImagesURL = cliente.getOperatorImgServicesURL();

            String storeName = getSituation().getStoreName();
            String evidenceDate = getSituation().getEvidenceDateTime();

            // it's executed in order to check that it's a valid date
            Date d1 = DateUtils.parseDate(evidenceDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
            String[] dateParser = StringUtils.split(DateFormatUtils.format(d1, "yyyy-MM-dd"), "-");
            String year = dateParser[0];
            String month = dateParser[1];
            String day = dateParser[2];

            String fileDate = year + month + day;
            String clientName = cliente.getName();

            for (Metric metrica : getSituation().getMetricas()) {
                List<Evidence> metricEvidences = metrica.getEvidencias();
                for (Evidence evidence : metricEvidences) {
                    String operatorImagesURL = "";
                    String fileName = FilenameUtils.getName(evidence.getEvidencePath()); // with extension

                    if (evidence.getOperatorImagesIp() != null) {
                        operatorImagesURL = evidence.getOperatorImagesIp() + "services/REST/";
                    } else {
                        operatorImagesURL = defaultOperatorImagesURL;
                    }

                    if (evidence.getEvidenceType().equalsIgnoreCase(EnumEvidenceType.IMAGE.toString())) {
                        if (!imagesURLs.contains(fileName)) {
                            imagesURLs = imagesURLs + operatorImagesURL + "getImage/" + clientName + "/" + storeName + "/"
                                    + fileDate + "/" + fileName + ";";
                        }
                    } else if (evidence.getEvidenceType().equalsIgnoreCase(EnumEvidenceType.VIDEO.toString())) {
                        if (!videoNames.contains(fileName)) {
                            String evidencePath = evidence.getEvidencePath();
                            int index = evidencePath.indexOf(clientName);

                            String videoSubPath = evidencePath.substring(index, evidencePath.length());
                            String videoFullPath = FilenameUtils.separatorsToUnix(evidencesBasePath + videoSubPath);

                            videoNames = videoNames + videoFullPath + ";";
                        }
                    }
                }
            }

            log.debug("imagesURLs: [" + imagesURLs + "]");
            if (imagesURLs != null && !"".equals(imagesURLs.trim())) {
                Clients.evalJavaScript("precargarImagenes('" + imagesURLs + "');");

            } else if (videoNames != null && !"".equals(videoNames.trim())) {
                log.debug("videoNames: [" + videoNames + "]");
                videoNames = videoNames.substring(0, videoNames.length() - 1); // elimina último ;

                SpritesPreloadThread spritesPreloadThread = new SpritesPreloadThread();
                spritesPreloadThread.init(videoNames);

                spritesPreloadThread.run();
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        log.info("end - " + login);
    }

    /**
     * Método invocado una vez el bean de la parte superior (north) finaliza su construcción
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param bean beanHijo
     * @since 6.0
     * @date 20/02/2013
     */
    @Override
    public void northComponentReady(NorthEvalHeader bean) {
        log.info("start - " + login);
        // Establece la instancia de la interfáz como el bean recién construído
        setHeaderInterface(bean);

        String storeDesc = getSituation().getStoreDescription(); // tienda
        String clientName = getSituation().getClient(); // cliente
        List<Metric> metricas = getSituation().getMetricas();
        String zoneName = getSituation().getArea(); // área de la tienda
        String evidenceDate = getSituation().getEvidenceDateTime(); // fecha/hora de la evidencia
        String camera = metricas.get(0).getEvidencias().get(0).getEvidenceProvider().getDescripcion();

        // Envía los datos para el respectivo bean
        getHeaderInterface().setClientName(clientName);
        getHeaderInterface().setStoreName(storeDesc);
        getHeaderInterface().setZoneName(zoneName + "/" + camera);
        getHeaderInterface().setEvidenceDate(evidenceDate);

        log.debug("clientName: " + clientName + ", storeName: " + storeDesc + ", zoneName: " + zoneName + ", camera: " + camera
                + ", evidenceDate: " + evidenceDate + " - " + login);

        String login = (String) getMySession().getAttribute("LOGIN"); // login del usuario autenticado
        getHeaderInterface().setLogin(login);
        log.info("end - " + login);
    }

    @Override
    public void setCenterInclude(Include centerInclude) {
        log.info("start - " + login);
        Center center = getEvalBorderLayout().getCenter();
        // Si el componente del centro ya tiene un hijo, se elimina
        if (!center.getChildren().isEmpty()) {
            center.removeChild(center.getChildren().get(0));
        }
        center.appendChild(centerInclude);
        log.info("end - " + login);
    }

    public void onRefreshEvalValues$evaluationWindow(Event event) {
        log.info("start - " + login);
        try {
            Long sessionId = (Long) getMySession().getAttribute("SESSION_ID");
            if (sessionId != null) {
                UserEvaluation userEvaluation = getOperatorManager().getHmUsersEvaluations().get(sessionId);

                if (userEvaluation != null) {
                    getLeftMetricBean().getLblEvalPendientes().setValue(String.valueOf(userEvaluation.getPending()));
                    getLeftMetricBean().getLblEvalExitosas().setValue(String.valueOf(userEvaluation.getSuccess()));
                    getLeftMetricBean().getLblEvalFallidas().setValue(String.valueOf(userEvaluation.getFail()));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.info("end - " + login);
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
            userCredentialManager = UserCredentialManager.getIntance(session);
        }
        return userCredentialManager;
    }

    /**
     * @param operatorManager the operatorManager to set
     */
    public void setOperatorManager(OperatorManager operatorManager) {
        this.operatorManager = operatorManager;
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

    public void setSituation2(Situation situation) {
        this.situation = situation;
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
     * @return the westSide
     */
    public West getWestSide() {
        return westSide;
    }

    /**
     * @param westSide the westSide to set
     */
    public void setWestSide(West westSide) {
        this.westSide = westSide;
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
     * @param evalBorderLayout the evalBorderLayout to set
     */
    public void setEvalBorderLayout(Borderlayout evalBorderLayout) {
        this.evalBorderLayout = evalBorderLayout;
    }

    public Borderlayout getEvalBorderLayout() {
        return evalBorderLayout;
    }

    /**
     * @return the metricInterface
     */
    public LeftMetricList getMetricInterface() {
        return metricInterface;
    }

    /**
     * @param metricInterface the metricInterface to set
     */
    public void setMetricInterface(LeftMetricList metricInterface) {
        this.metricInterface = metricInterface;
    }

    /**
     * @return the situation
     */
    public Situation getSituation() {
        return situation;
    }

    /**
     * @return the northHeaderBean
     */
    @Override
    public NorthHeaderBean getNorthHeaderBean() {
        return northHeaderBean;
    }

    /**
     * @param northHeaderBean the northHeaderBean to set
     */
    public void setNorthHeaderBean(NorthHeaderBean northHeaderBean) {
        this.northHeaderBean = northHeaderBean;
    }

    /**
     * @return the northSide
     */
    public North getNorthSide() {
        return northSide;
    }

    /**
     * @param northSide the northSide to set
     */
    public void setNorthSide(North northSide) {
        this.northSide = northSide;
    }

    /**
     * @return the headerInterface
     */
    public NorthEvalHeader getHeaderInterface() {
        return headerInterface;
    }

    /**
     * @param headerInterface the headerInterface to set
     */
    public void setHeaderInterface(NorthEvalHeader headerInterface) {
        this.headerInterface = headerInterface;
    }
}