package com.scopix.periscope.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.CenterEvidence;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.manager.OperatorManager;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Equivalence;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 * Clase encargada del manejo del video en la parte central de la pantalla de evaluación
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class CenterVideoEvalBean extends GenericForwardComposer implements Serializable, CenterEvidence {

    private String login;
    private Metric metric;
    private Image jwImage;
    private Label lblPause;
    private String fileName;
    private String httpPath;
    private String diskPath;
    private Label lblElapsed;
    private Session mySession;
    private Label lblVideoURL;
    private String metricType;
    private Image imgPlantilla;
    private Label lblPlantilla;
    private String evidenceURL;
    private Button btnDeshacer;
    private Button btnVerMarcas;
    private String evidenceType;
    private String templatePath;
    private Image myTransparent;
    private Label lblCurrentTime;
    private Label lblIsPlayerTime;
    private String playerPosition;
    private Label lblConfirmClose;
    private Button btnDeshacerTodo;
    private Label lblTiempoMarcas;
    private String operatorImagesIp;
    private ShowMessage showMessage;
    private Label lblSpritesVttFile;
    private String clientTemplateURL;
    private Label lblCenterNomMetrica;
    private String operImgServicesURL;
    private Label lblTiempoMarcasFinal;
    private Label lblTiempoMarcasInicial;
    private OperatorManager operatorManager;
    private PopCenterVideoEvalBean popVideoEval;
    private RightMetricDetail rightMetricDetail;
    private UserCredentialManager userCredentialManager;
    private static final long serialVersionUID = -7060711240220863406L;
    private static Logger log = Logger.getLogger(CenterVideoEvalBean.class);

    /**
     * Auto forward events and wire accessible variables of the specified component into a controller Java object; a subclass that
     * override this method should remember to call super.doAfterCompose(comp) or it will not work.
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param comp componente
     * @since 6.0
     * @date 20/02/2013
     */
    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);

            setPopVideoEval((PopCenterVideoEvalBean) getSession().getAttribute("popCenterVideoBean"));
            getPopVideoEval().setCenterVideoEvalBean(this);

            // Si el usuario no se encuentra autenticado, lo redirige a la pantalla de login
            if (!getUserCredentialManager().isAuthenticated()) {
                getExecution().sendRedirect("login.zul");
            } else {
                // Texto: Por favor presione el botón "Enviar y Finalizar" y cierre la sesión en la pantalla de inicio
                // para una correcta salida de la aplicación.
                getLblConfirmClose().setValue(Labels.getLabel("eval.confirmClose"));
                getJwImage().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblPause().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblElapsed().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblPlantilla().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getMyTransparent().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblCurrentTime().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblIsPlayerTime().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblTiempoMarcas().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblSpritesVttFile().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblTiempoMarcasFinal().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblTiempoMarcasInicial().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                // Establece el src de la imágen evidencia
                processEvidenceSrc();

                Client cliente = (Client) getSession().getAttribute("CLIENT");
                setClientTemplateURL(cliente.getTemplateUrl());

                // Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
                setButtonsAvailability();
                // Procesamiento en base a la posición del reproductor para determinar si carga el player
                processPlayerPosition();

                if (getMetric().getType().equals(EnumEvaluationType.NUMBER_INPUT.toString())) {
                    getBtnDeshacer().setDisabled(true);
                    getBtnDeshacerTodo().setDisabled(true);
                }

                if (getTemplatePath() != null && FilenameUtils.isExtension(getTemplatePath(), "png")) {
                    processTemplatePath();
                    Clients.evalJavaScript("plantilla = 'S';");
                }
                // Actualiza conteo de la métrica indicando si hay cambio de cámara o no
                processMetricDescription();

                Clients.evalJavaScript("updateLblCurrentEvType('" + EnumEvidenceType.VIDEO.toString() + "'); "
                        + "activateTimeInterval();");

                updateMetricStarTime();
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
    }

    /**
     * Actualiza el tiempo de inicio evaluación de la métrica en el modelo de la lista izquierda
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 07/05/2013
     */
    public void updateMetricStarTime() {
        log.info("start - " + login);
        Date fechaActual = new Date();
        int numMetrica = Integer.valueOf(getMetric().getNumMetrica());

        if (getMetric().getStartEvaluationTime() == null) {
            log.debug("metricId: " + getMetric().getMetricId() + ", " + "estableciendo metric startEvaluationTime: "
                    + fechaActual + " - " + login);
            getMetric().setStartEvaluationTime(fechaActual);

            // Actualiza la métrica en el modelo (parte izquierda) con la fecha inicio de evaluación
            getRightMetricDetail().getLeftMetricBean().getMetricasModel().set(numMetrica, getMetric());
        } else {
            log.debug("metricId: " + getMetric().getMetricId() + ", ya tiene un startEvaluationTime: "
                    + getMetric().getStartEvaluationTime() + " - " + login);
        }
        log.info("end - " + login);
    }

    /**
     * Establece el src de la imágen evidencia
     *
     * @author carlos polo
     * @version 1.0.0
     * @throws ParseException
     * @since 6.0
     * @date 25/03/2013
     */
    public void processEvidenceSrc() throws ParseException {
        log.info("start - " + login);
        Client cliente = (Client) getMySession().getAttribute("CLIENT");
        setClientTemplateURL(cliente.getTemplateUrl());

        String clientName = cliente.getName();
        // /data/ftp/evidence/Lowes/
        String evidenceBasePath = cliente.getEvidenceUrl();

        // http://64.151.117.136:8080/operator-images-services/services/REST/
        String operImgServicesURL = processOperatorImagesURL(cliente.getOperatorImgServicesURL());
        setOperImgServicesURL(operImgServicesURL);

        // Establece ubicación de la evidencia
        String storeName = getRightMetricDetail().getLeftMetricBean().getSituation().getStoreName();
        String evidenceDate = getRightMetricDetail().getLeftMetricBean().getSituation().getEvidenceDateTime();

        // it's executed in order to check that it's a valid date
        Date d1 = DateUtils.parseDate(evidenceDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
        String[] dateParser = StringUtils.split(DateFormatUtils.format(d1, "yyyy-MM-dd"), "-");
        String year = dateParser[0];
        String month = dateParser[1];
        String day = dateParser[2];

        String fileDate = year + month + day;
        String fileName = FilenameUtils.getName(getEvidenceURL()); // with extension

        // \\64.151.127.244\periscope.data\evidence\Lowes\478\2014\03\28\20140328_2508789.mp4
        int index1 = getEvidenceURL().indexOf(clientName);
        if (index1 != -1) {
            int index2 = index1 + clientName.length() + 1;
            String fileLocation = getEvidenceURL().substring(index2, getEvidenceURL().length());

            // home/scopix.admin/data/ftp/evidence/Harris/87/2013/04/25/20130425_382634.mp4
            String filePath = FilenameUtils.separatorsToUnix(evidenceBasePath + fileLocation);

            setHttpPath(operImgServicesURL + "getVideo/" + cliente.getName() + "/" + storeName + "/" + fileDate + "/" + fileName);
            setFileName(filePath);

            setDiskPath(cliente.getEvidenceUrl());
            // Ej: http://domain:8080/operator-web/harris/avi_mp4_1.mp4
            getLblVideoURL().setValue(getHttpPath());
        }
        log.info("end, videoURL: [" + getHttpPath() + "] - " + login);
    }

    /**
     * Define el URL de operator-images-services
     * 
     * @param operImgServicesURL
     * @return
     */
    private String processOperatorImagesURL(String operImgServicesURL) {
        log.info("start, operImgServicesURL: [" + operImgServicesURL + "] - " + login);
        if (getOperatorImagesIp() != null) { // si desde cache viene un URL de operator-images, se usa ese
            operImgServicesURL = getOperatorImagesIp() + "services/REST/"; // protocol://domainip:port/context
        }
        log.info("end, operImgServicesURL: [" + operImgServicesURL + "] - " + login);
        return operImgServicesURL;
    }

    /**
     * Procesamiento en base a la posición del reproductor para determinar si carga el player
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 11/06/2013
     */
    protected void processPlayerPosition() {
        log.info("start - " + login);
        // Inicializa el reproductor y carga el correspondiente video
        String videoURL = getHttpPath();
        String nombreVideo = FilenameUtils.getBaseName(getFileName()); // nombre sin extensión

        String filePath = FilenameUtils.getPathNoEndSeparator(getFileName()); // ej, data/ftp/evidence/Lowes/404/2014/04/03
        String[] saFilePath = filePath.split("/");// "/"
        String clientName = saFilePath[3];
        String store = saFilePath[4];
        String date = saFilePath[5] + saFilePath[6] + saFilePath[7];

        // /getVttVideoFile/{corporateName}/{store}/{date}/{fileName}
        Client cliente = (Client) getMySession().getAttribute("CLIENT");
        String spritesVttFile = cliente.getOperatorImgServicesURL() + "getVttVideoFile/" + clientName + "/" + store + "/" + date
                + "/" + nombreVideo + ".vtt";

        log.info("videoURL: [" + videoURL + "], spritesVttFile: [" + spritesVttFile + "] - " + login);
        getLblSpritesVttFile().setValue(spritesVttFile);

        if (getPlayerPosition() == null) {
            // Configura y carga el reproductor de video
            Clients.evalJavaScript("loadVideoPlayer('" + videoURL + "','" + spritesVttFile + "');");
        } else {
            // Cambio de métrica/cámara, posición anterior del player
            String tiempo = getPlayerPosition() + "PA";
            getLblElapsed().setValue(tiempo);
            onClickPauseOrPlay(null);
            setLblCurrentTimeValue();
            Clients.evalJavaScript("clickPause = 'S'; updateLblPause(); updateTimeLabels('" + tiempo + "')");
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza valor de la métrica indicando si hay cambio de cámara o no
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 11/06/2013
     */
    protected void processMetricDescription() {
        log.info("start - " + login);
        String desc = getMetric().getDescription();
        getLblCenterNomMetrica().setValue(getMetric().getName());

        if (getMetric().getType().equals(EnumEvaluationType.COUNTING.toString())) {
            try {
                Integer.parseInt(desc);
                Clients.evalJavaScript("cambioCamara = 'N'; metricCount=" + desc + "; updateLblValorMetrica();");
            } catch (NumberFormatException e) {
                Clients.evalJavaScript("cambioCamara = 'N';");
            }
        } else {
            Clients.evalJavaScript("cambioCamara = 'N'; metricValue='" + desc + "'; updateLblValorMetrica();");
        }
        log.info("end - " + login);
    }

    /**
     * Establece disponibilidad de los botones verMarcas, deshacer y deshacerTodo
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    public void setButtonsAvailability() {
        log.info("start, metricType: [" + getMetricType() + "] - " + login);
        if (getMetricType() != null) {
            if (getMetricType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                getBtnVerMarcas().setDisabled(true);
                getBtnDeshacer().setDisabled(true);
                getBtnDeshacerTodo().setDisabled(true);
            } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
                getBtnVerMarcas().setDisabled(true);
            } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                getRightMetricDetail().setTimeButtonsDisabledState(true);
            }
        }

        if (getMetric() != null) {
            String tiempoMarcas = getMetric().getTiempoMarcas().get(getRightMetricDetail().getCurrentCameraId());

            if (tiempoMarcas != null && !tiempoMarcas.trim().equals("")) {
                tiempoMarcas = tiempoMarcas.substring(0, tiempoMarcas.length() - 2);
                Clients.evalJavaScript("tiempoMarcas = " + tiempoMarcas + ";");
            }
        }
        log.info("end - " + login);
    }

    /**
     * Muestra/Oculta snapshot o imágen de plantilla dependiendo del estado del reproductor
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    public void onClickPauseOrPlay(ForwardEvent event) {
        log.info("start - " + login);
        String elapsedTimeText = getLblElapsed().getValue();
        int elapsedLength = elapsedTimeText.length();

        log.debug("elapsedTimeText: [" + elapsedTimeText + "] - " + login);
        String state = elapsedTimeText.substring(elapsedLength - 2, elapsedLength);
        String elapsedTime = elapsedTimeText.substring(0, elapsedLength - 2);

        if (state.equals("PL")) {
            // Se reproduce el video nuevamente, se oculta la imágen
            getJwImage().setVisible(false);
            getMyTransparent().setVisible(false);
            getImgPlantilla().setVisible(false);

            if (getMetricType() != null) {
                if (getMetricType().equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                    getRightMetricDetail().setTimeButtonsDisabledState(true);
                } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
                    // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                    getRightMetricDetail().setTimeButtonsDisabledState(true);
                } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                    // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                    getRightMetricDetail().setNumberInputBoxState(true);
                } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                    getRightMetricDetail().setTimeButtonsDisabledState(true);
                }
            }
            processPlayerTime();
        } else {
            processPause(elapsedTime);
        }
        log.info("end - " + login);
    }

    /**
     * Deshabilita botónes de asignar tiempos o campo de entrada de número
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 07/11/2013
     */
    public void onClickStop(ForwardEvent event) {
        log.info("start, metricType: [" + getMetricType() + "] - " + login);
        if (getMetricType() != null) {
            if (getMetricType().equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
                // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                getRightMetricDetail().setTimeButtonsDisabledState(true);
            } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                getRightMetricDetail().setNumberInputBoxState(true);
            } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                getRightMetricDetail().setTimeButtonsDisabledState(true);
            }
        }
        log.info("end - " + login);
    }

    protected void processPlayerTime() {
        log.info("start - " + login);
        if (getLblIsPlayerTime().getValue().equals("S")) {
            getLblIsPlayerTime().setValue("N");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage() + " - " + login, ex);
            }
            Clients.evalJavaScript("jwplayer().seek(" + getPlayerPosition() + ");");
        }
        log.info("end - " + login);
    }

    /**
     * Muestra/oculta la plantilla de la evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    @Override
    public void onClickVerMarcas(ForwardEvent event) {
        log.info("start - " + login);
        Metric currentMetric = getRightMetricDetail().getMetric();
        String currentMetricType = currentMetric.getType();
        int currentCameraId = getRightMetricDetail().getCurrentCameraId();

        if (currentMetricType.equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
            processYesNoMarks(currentMetric, currentCameraId);
        } else if (currentMetricType.equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
            processTimeMarks(currentMetric, currentCameraId, event.getName());
        }
        log.info("end - " + login);
    }

    /**
     * Proceso para mostrar marcas en caso de métrica MEASURE_TIME
     * 
     * @param currentMetric métrica actual
     * @param currentCameraId cámara actual
     * @param timeType indica si es para el tiempo inicial o final
     */
    protected void processTimeMarks(Metric currentMetric, int currentCameraId, String timeType) {
        if (timeType.equalsIgnoreCase("onClickVerMarcaInicial")) {
            // Tiempo inicial
            processInitialTime(currentMetric, currentCameraId);
        } else {
            // Tiempo final
            processFinalTime(currentMetric, currentCameraId);
        }
    }

    /**
     * Proceso para mostrar marcas en caso de métrica MEASURE_TIME para el tiempo inicial
     * 
     * @param currentMetric métrica actual
     * @param currentCameraId cámara actual
     */
    protected void processInitialTime(Metric currentMetric, int currentCameraId) {
        int marksCameraId = -1;
        HashMap<Integer, String> iniSquaresInfo = currentMetric.getIniSquaresInfo();

        if (!iniSquaresInfo.isEmpty()) {
            for (Map.Entry e : iniSquaresInfo.entrySet()) {
                marksCameraId = Integer.valueOf(e.getKey().toString());
                break;
            }
            // Proceso para dibujar marcas MEASURE_TIME
            String tiempoMarcasInicial = currentMetric.getTiempoMarcasInicial().get(marksCameraId);
            // la cámara en donde se hicieron marcas es la misma en donde se encuentra actualmente
            if (marksCameraId == currentCameraId) {
                // Pausa y muestra snapshot
                tiempoMarcasInicial = processPause(tiempoMarcasInicial);
                // Dibuja marcas en pantalla
                processDrawYesNoSquares("YES_SQUARE", iniSquaresInfo, marksCameraId);
                // Oculta reproductor
                Clients.evalJavaScript("verMarcas('" + tiempoMarcasInicial + "')");
            } else { // se debe cambiar de cámara
                tiempoMarcasInicial = processElapsedTime(tiempoMarcasInicial);
                getRightMetricDetail().changeCameraVerMarcas(marksCameraId, tiempoMarcasInicial);
                // Dibuja marcas en pantalla
                processDrawYesNoSquares("YES_SQUARE", iniSquaresInfo, marksCameraId);
            }
        }
    }

    /**
     * Proceso para mostrar marcas en caso de métrica MEASURE_TIME para el tiempo final
     * 
     * @param currentMetric métrica actual
     * @param currentCameraId cámara actual
     */
    protected void processFinalTime(Metric currentMetric, int currentCameraId) {
        int marksCameraId = -1;
        HashMap<Integer, String> finSquaresInfo = currentMetric.getFinSquaresInfo();

        if (!finSquaresInfo.isEmpty()) {
            for (Map.Entry e : finSquaresInfo.entrySet()) {
                marksCameraId = Integer.valueOf(e.getKey().toString());
                break;
            }
            // Proceso para dibujar marcas MEASURE_TIME
            String tiempoMarcasFinal = currentMetric.getTiempoMarcasFinal().get(marksCameraId);
            // la cámara en donde se hicieron marcas es la misma en donde se encuentra actualmente
            if (marksCameraId == currentCameraId) {
                // Pausa y muestra snapshot
                tiempoMarcasFinal = processPause(tiempoMarcasFinal);
                // Dibuja marcas en pantalla
                processDrawYesNoSquares("YES_SQUARE", finSquaresInfo, marksCameraId);
                // Oculta reproductor
                Clients.evalJavaScript("verMarcas('" + tiempoMarcasFinal + "')");
            } else { // se debe cambiar de cámara
                tiempoMarcasFinal = processElapsedTime(tiempoMarcasFinal);
                getRightMetricDetail().changeCameraVerMarcas(marksCameraId, tiempoMarcasFinal);
                // Dibuja marcas en pantalla
                processDrawYesNoSquares("YES_SQUARE", finSquaresInfo, marksCameraId);
            }
        }
    }

    /**
     * Proceso para mostrar marcas en caso de métrica YES/NO
     * 
     * @param currentMetric métrica actual
     * @param currentCameraId cámara actual
     */
    protected void processYesNoMarks(Metric currentMetric, int currentCameraId) {
        log.info("start - " + login);
        int marksCameraId = -1;

        HashMap<Integer, String> yesSquaresInfo = currentMetric.getYesSquaresInfo();
        HashMap<Integer, String> noSquaresInfo = currentMetric.getNoSquaresInfo();

        if (!yesSquaresInfo.isEmpty()) {
            for (Map.Entry e : yesSquaresInfo.entrySet()) {
                marksCameraId = Integer.valueOf(e.getKey().toString());
                break;
            }
            // Proceso para dibujar marcas YES/NO
            subProcessYesNoMarks(currentMetric, currentCameraId, marksCameraId, yesSquaresInfo, "YES_SQUARE");
        } else if (!noSquaresInfo.isEmpty()) {
            for (Map.Entry e : noSquaresInfo.entrySet()) {
                marksCameraId = Integer.valueOf(e.getKey().toString());
                break;
            }
            // Proceso para dibujar marcas YES/NO
            subProcessYesNoMarks(currentMetric, currentCameraId, marksCameraId, noSquaresInfo, "NO_SQUARE");
        }
        log.info("end - " + login);
    }

    /**
     * Proceso para dibujar marcas YES/NO
     * 
     * @param currentMetric métrica actual
     * @param currentCameraId cámara actual
     * @param marksCameraId cámara en donde se realizaron las marcas
     * @param squaresInfo información de la marca
     * @param type indica si debe dibujar marcas YES o NO
     */
    protected void subProcessYesNoMarks(Metric currentMetric, int currentCameraId, int marksCameraId,
            HashMap<Integer, String> squaresInfo, String type) {

        String tiempoMarcas = currentMetric.getTiempoMarcas().get(marksCameraId);
        // la cámara en donde se hicieron marcas es la misma en donde se encuentra actualmente
        if (marksCameraId == currentCameraId) {
            // Pausa y muestra snapshot
            tiempoMarcas = processPause(tiempoMarcas);
            // Dibuja marcas en pantalla
            processDrawYesNoSquares(type, squaresInfo, marksCameraId);
            // Oculta reproductor
            Clients.evalJavaScript("verMarcas('" + tiempoMarcas + "')");
        } else { // se debe cambiar de cámara
            tiempoMarcas = processElapsedTime(tiempoMarcas);
            getRightMetricDetail().changeCameraVerMarcas(marksCameraId, tiempoMarcas);
            // Dibuja marcas en pantalla
            processDrawYesNoSquares(type, squaresInfo, marksCameraId);
        }
    }

    /**
     * Dibuja marcas YES/NO en pantalla
     * 
     * @param type indica si debe dibujar marcas YES o NO
     * @param squaresInfo información de la marca
     * @param marksCameraId id de la cámara en donde se hicieron las marcas
     */
    protected void processDrawYesNoSquares(String type, HashMap<Integer, String> squaresInfo, int marksCameraId) {
        // Dibuja marcas en pantalla
        if (type.equalsIgnoreCase("YES_SQUARE")) {
            drawYesNoSquares(squaresInfo.get(marksCameraId), null);
        } else {
            drawYesNoSquares(null, squaresInfo.get(marksCameraId));
        }
    }

    protected String processPause(String elapsedTime) {
        log.info("start - " + login);
        elapsedTime = processElapsedTime(elapsedTime);
        subProcessPause(elapsedTime);
        log.info("end - " + login);
        return elapsedTime;
    }

    protected String processElapsedTime(String elapsedTime) {
        log.info("start, elapsedTime: [" + elapsedTime + "] - " + login);
        if (elapsedTime.length() > 2) {
            String text = elapsedTime.substring(elapsedTime.length() - 2, elapsedTime.length());
            if (text.equals("PL") || text.equals("PA")) {
                elapsedTime = elapsedTime.substring(0, elapsedTime.length() - 2);
            }
        }
        log.info("end - " + login);
        return elapsedTime;
    }

    protected void subProcessPause(String elapsedTime) {
        log.info("start, elapsedTime: [" + elapsedTime + "] - " + login);
        if (getLblIsPlayerTime().getValue().equals("S")) {
            getLblIsPlayerTime().setValue("N");
            getImgPlantilla().setVisible(false);
            getJwImage().setVisible(false);
            getMyTransparent().setVisible(false);
        } else {
            try {
                Client cliente = (Client) getMySession().getAttribute("CLIENT");

                // Establece ubicación de la evidencia
                String storeName = getRightMetricDetail().getLeftMetricBean().getSituation().getStoreName();
                String evidenceDate = getRightMetricDetail().getLeftMetricBean().getSituation().getEvidenceDateTime();

                // it's executed in order to check that it's a valid date
                Date d1 = DateUtils.parseDate(evidenceDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
                String[] dateParser = StringUtils.split(DateFormatUtils.format(d1, "yyyy-MM-dd"), "-");
                String year = dateParser[0];
                String month = dateParser[1];
                String day = dateParser[2];

                String fileDate = year + month + day;
                String fileName = FilenameUtils.getName(getEvidenceURL()); // with extension

                String snapshotURL = getOperImgServicesURL() + "getSnapshot/" + elapsedTime + "/" + cliente.getName() + "/"
                        + storeName + "/" + fileDate + "/" + fileName;
                log.debug("snapshotURL: [" + snapshotURL + "], metricType: [" + getMetricType() + "] - " + login);

                // Se pausa video, se muestra la imágen
                getJwImage().setSrc(snapshotURL);
                getJwImage().setVisible(true);
                getMyTransparent().setVisible(true);

                if (getMetricType() != null) {
                    if (getMetricType().equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                        getRightMetricDetail().setTimeButtonsDisabledState(false);
                    } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                        // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                        getRightMetricDetail().setNumberInputBoxState(false);
                        getRightMetricDetail().setPlayerPosition(elapsedTime);
                    }
                }
            } catch (ParseException e) {
                log.error("error parsing videoDate: [" + e.getMessage() + "]", e);
            }
        }
        log.info("end - " + login);
    }

    /**
     * Procesamiento para dibujar marcas YES/NO
     *
     * @author carlos polo
     * @version 1.0.0
     * @param yesSquaresInfo información para marcas de tipo YES
     * @param noSquaresInfo información para marcas de tipo NO
     * @since 6.0
     * @date 11/06/2013
     */
    protected void drawYesNoSquares(String yesSquaresInfo, String noSquaresInfo) {
        log.info("start, yesSquaresInfo: [" + yesSquaresInfo + "], noSquaresInfo: [" + noSquaresInfo + "] - " + login);
        if (yesSquaresInfo != null && !yesSquaresInfo.trim().equals("")) {
            drawShapeByJavaScript(yesSquaresInfo, "YES_SQUARE");
        }

        if (noSquaresInfo != null && !noSquaresInfo.trim().equals("")) {
            drawShapeByJavaScript(noSquaresInfo, "NO_SQUARE");
        }
        log.info("end - " + login);
    }

    /**
     * Dibuja una figura (círculo o cuadrado) dependiendo del tipo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param shapeInfo datos de la figura
     * @param type tipo de figura
     * @since 6.0
     * @date 25/03/2013
     */
    public void drawShapeByJavaScript(String shapeInfo, String type) {
        log.info("start - " + login);
        String[] shapeArr = shapeInfo.split("#");

        for (int i = 0; i < shapeArr.length; i++) {
            String shapeStyle = shapeArr[i];
            String[] shapeData = shapeStyle.split("_");

            String coordenadas = shapeData[0];
            String size = shapeData[1];

            String[] coordsArray = coordenadas.split(":");
            String[] sizeArray = size.split(":");

            String xPos = coordsArray[0];
            String yPos = coordsArray[1];

            String width = sizeArray[0];
            String height = sizeArray[1];

            xPos = xPos.substring(0, xPos.length() - 2);
            yPos = yPos.substring(0, yPos.length() - 2);
            width = width.substring(0, width.length() - 2);
            height = height.substring(0, height.length() - 2);

            if (type.equals("YES_SQUARE")) {
                Clients.evalJavaScript("drawYesSquare(" + xPos + "," + yPos + "," + width + "," + height + ");");
            } else if (type.equals("NO_SQUARE")) {
                Clients.evalJavaScript("drawNoSquare(" + xPos + "," + yPos + "," + width + "," + height + ");");
            }
        }
        log.info("end - " + login);
    }

    /**
     * Ejecutado cuando se hace click sobre el snapshot
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 25/03/2013
     */
    public void onClickEvidenceImage(ForwardEvent event) {
        log.info("start - " + login);
        String paused = getLblPause().getValue();

        log.debug("paused: " + paused + " - " + login);
        if (paused.equals("S")) {
            if (getMetricType() != null && !getMetricType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                onClickUpdateMetricValue(null);
            }
            // habilita botónes de tiempo inicial y final (tipo métrica: tiempo)
            getRightMetricDetail().setTimeButtonsDisabledState(false);

            HashMap<Integer, String> tiempoMarcas = getMetric().getTiempoMarcas();
            tiempoMarcas.put(getRightMetricDetail().getCurrentCameraId(), getLblTiempoMarcas().getValue());
            getMetric().setTiempoMarcas(tiempoMarcas);
        }
        log.info("end - " + login);
    }

    /**
     * Ejecutado cuando se hace click en deshacer, deshacer todo o para actualizar el valor de la métrica actual
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 25/03/2013
     */
    public void onClickUpdateMetricValue(ForwardEvent event) {
        log.info("start - " + login);
        getRightMetricDetail().updateMetricValue(getRightMetricDetail().getLblValorMetrica().getValue());
        log.info("end - " + login);
    }

    /**
     * Ejecutado cuando se hace click en verLimites, para ocultar o mostrar la plantilla
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 25/03/2013
     */
    public void onClickPlantilla(ForwardEvent event) {
        log.info("start, templatePath: [" + getTemplatePath() + "] - " + login);
        if (getTemplatePath() != null) {
            String valuePlantilla = getLblPlantilla().getValue();

            if (valuePlantilla.equals("S")) {
                processTemplatePath();
            } else {
                getImgPlantilla().setVisible(false);
            }
        } else {
            String titulo = Labels.getLabel("eval.noPlantillaTitle");
            String mensaje = Labels.getLabel("eval.noPlantillaText"); // La evidencia no tiene una plantilla asociada

            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.INFORMATION);
        }
        log.info("end - " + login);
    }

    /**
     * Muestra la plantilla procesando el path de la misma
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 02/04/2013
     */
    protected void processTemplatePath() {
        log.info("start - " + login);
        Client cliente = (Client) getMySession().getAttribute("CLIENT");
        List<Equivalence> officeEquivalences = (List<Equivalence>) getMySession().getAttribute("OFFICE_EQUIVALENCES");

        String storeName = getRightMetricDetail().getLeftMetricBean().getSituation().getStoreName();
        String fileName = FilenameUtils.getName(getTemplatePath()); // with extension

        String templateUrl = officeEquivalences.get(0).getOperatorImgServicesURL() + "getTemplate/" + cliente.getName() + "/"
                + storeName + "/" + fileName;

        log.debug("templateUrl: [" + templateUrl + "]");
        getImgPlantilla().setSrc(templateUrl);
        getImgPlantilla().setVisible(true);
        log.info("end - " + login);
    }

    /**
     * Invocado al presionar PLAY en los controles del video
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 02/04/2013
     */
    public void onClickPlay(ForwardEvent event) {
        log.info("start - " + login);
        getImgPlantilla().setVisible(false);
        getJwImage().setVisible(false);
        getMyTransparent().setVisible(false);

        String paused = getLblPause().getValue();

        log.debug("paused: " + paused + " - " + login);
        if (paused.equals("S")) {
            if (getMetricType() != null) {
                if (getMetricType().equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
                    // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                    getRightMetricDetail().setTimeButtonsDisabledState(true);
                } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                    // deshabilita botónes de tiempo inicial y final (tipo métrica: tiempo)
                    getRightMetricDetail().setNumberInputBoxState(true);
                } else if (getMetricType().equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                    getRightMetricDetail().setTimeButtonsDisabledState(true);
                }
            }
            getRightMetricDetail().updateEvalData();
        }

        if (getLblIsPlayerTime().getValue().equals("S")) {
            log.debug("playerPosition: [" + getPlayerPosition() + "] - " + login);
            getLblIsPlayerTime().setValue("N");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage() + " - " + login, ex);
            }
            Clients.evalJavaScript("jwplayer().seek(" + getPlayerPosition() + ");");
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza el valor del label que muestra el tiempo en curso del video
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 05/04/2013
     */
    protected void setLblCurrentTimeValue() {
        log.info("start - " + login);
        if (getPlayerPosition() != null && !getPlayerPosition().trim().equals("")) {
            int position = 0;
            int index = getPlayerPosition().indexOf(".");

            if (index != -1) { // tiene decimal
                position = Integer.valueOf(getPlayerPosition().substring(0, index));
            } else { // no tiene decimal
                position = Integer.parseInt(getPlayerPosition());
            }
            String valor = processLblCurrentTimeValue(position);
            getLblIsPlayerTime().setValue("S");
            Clients.evalJavaScript("currentTime = '" + getLblCurrentTime().getValue() + "'; updateLblCurrentTimeValue('" + valor
                    + "')");
        }
        log.info("end - " + login);
    }

    protected String processLblCurrentTimeValue(int position) {
        log.info("start - " + login);
        double doubleMins = position / 60;
        long minutos = Math.round(doubleMins); // parte entera del decimal

        double doubleSegs = position % 60;
        long segundos = Math.round(doubleSegs); // parte entera del decimal

        String strMinutos = String.valueOf(minutos);
        String strSegundos = String.valueOf(segundos);

        if (minutos < 10) {
            strMinutos = "0" + strMinutos;
        }
        if (segundos < 10) {
            strSegundos = "0" + strSegundos;
        }
        log.info("end - " + login);
        return (strMinutos + ":" + strSegundos);
    }

    /**
     * @return the rightMetricDetail
     */
    public RightMetricDetail getRightMetricDetail() {
        return rightMetricDetail;
    }

    /**
     * @param rightMetricDetail the rightMetricDetail to set
     */
    public void setRightMetricDetail(RightMetricDetail rightMetricDetail) {
        this.rightMetricDetail = rightMetricDetail;
    }

    /**
     * @return the evidenceType
     */
    public String getEvidenceType() {
        return evidenceType;
    }

    /**
     * @return the lblElapsed
     */
    public Label getLblElapsed() {
        return lblElapsed;
    }

    /**
     * @param lblElapsed the lblElapsed to set
     */
    public void setLblElapsed(Label lblElapsed) {
        this.lblElapsed = lblElapsed;
    }

    /**
     * @return the jwImage
     */
    public Image getJwImage() {
        return jwImage;
    }

    /**
     * @param jwImage the jwImage to set
     */
    public void setJwImage(Image jwImage) {
        this.jwImage = jwImage;
    }

    /**
     * @return the evidenceURL
     */
    @Override
    public String getEvidenceURL() {
        return evidenceURL;
    }

    @Override
    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    @Override
    public void bindComponentAndParent(Include centerInclude, RightMetricDetail rightMetricBean) {
        this.bindComponent(centerInclude);
        this.setRightMetricDetail(rightMetricBean);
    }

    @Override
    public void setEvidenceURL(String evidenceURL) {
        this.evidenceURL = evidenceURL;
    }

    @Override
    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    public String getMetricType() {
        return metricType;
    }

    /**
     * @return the lblVideoURL
     */
    public Label getLblVideoURL() {
        return lblVideoURL;
    }

    /**
     * @param lblVideoURL the lblVideoURL to set
     */
    public void setLblVideoURL(Label lblVideoURL) {
        this.lblVideoURL = lblVideoURL;
    }

    /**
     * @return the btnVerMarcas
     */
    public Button getBtnVerMarcas() {
        return btnVerMarcas;
    }

    /**
     * @param btnVerMarcas the btnVerMarcas to set
     */
    public void setBtnVerMarcas(Button btnVerMarcas) {
        this.btnVerMarcas = btnVerMarcas;
    }

    /**
     * @return the btnDeshacer
     */
    public Button getBtnDeshacer() {
        return btnDeshacer;
    }

    /**
     * @param btnDeshacer the btnDeshacer to set
     */
    public void setBtnDeshacer(Button btnDeshacer) {
        this.btnDeshacer = btnDeshacer;
    }

    /**
     * @return the btnDeshacerTodo
     */
    public Button getBtnDeshacerTodo() {
        return btnDeshacerTodo;
    }

    /**
     * @param btnDeshacerTodo the btnDeshacerTodo to set
     */
    public void setBtnDeshacerTodo(Button btnDeshacerTodo) {
        this.btnDeshacerTodo = btnDeshacerTodo;
    }

    @Override
    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    /**
     * @return the metric
     */
    public Metric getMetric() {
        return metric;
    }

    /**
     * @return the httpPath
     */
    public String getHttpPath() {
        return httpPath;
    }

    /**
     * @param httpPath the httpPath to set
     */
    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    /**
     * @return the diskPath
     */
    public String getDiskPath() {
        return diskPath;
    }

    /**
     * @param diskPath the diskPath to set
     */
    public void setDiskPath(String diskPath) {
        this.diskPath = diskPath;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the templatePath
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * @param templatePath the templatePath to set
     */
    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * @return the lblPlantilla
     */
    public Label getLblPlantilla() {
        return lblPlantilla;
    }

    /**
     * @param lblPlantilla the lblPlantilla to set
     */
    public void setLblPlantilla(Label lblPlantilla) {
        this.lblPlantilla = lblPlantilla;
    }

    /**
     * @return the imgPlantilla
     */
    public Image getImgPlantilla() {
        return imgPlantilla;
    }

    /**
     * @param imgPlantilla the imgPlantilla to set
     */
    public void setImgPlantilla(Image imgPlantilla) {
        this.imgPlantilla = imgPlantilla;
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
     * @return the userCredentialManager
     */
    public UserCredentialManager getUserCredentialManager() {
        if (userCredentialManager == null) {
            userCredentialManager = UserCredentialManager.getIntance(getSession());
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
     * @return the session
     */
    public Session getSession() {
        return Sessions.getCurrent();
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
     * @return the lblPause
     */
    public Label getLblPause() {
        return lblPause;
    }

    /**
     * @param lblPause the lblPause to set
     */
    public void setLblPause(Label lblPause) {
        this.lblPause = lblPause;
    }

    @Override
    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    /**
     * @return the playerPosition
     */
    public String getPlayerPosition() {
        return playerPosition;
    }

    /**
     * @return the lblIsPlayerTime
     */
    public Label getLblIsPlayerTime() {
        return lblIsPlayerTime;
    }

    /**
     * @param lblIsPlayerTime the lblIsPlayerTime to set
     */
    public void setLblIsPlayerTime(Label lblIsPlayerTime) {
        this.lblIsPlayerTime = lblIsPlayerTime;
    }

    /**
     * @param alternateSrc the alternateSrc to set
     */
    @Override
    public void setAlternateSrc(String alternateSrc) {

    }

    /**
     * @return the popVideoEval
     */
    public PopCenterVideoEvalBean getPopVideoEval() {
        return popVideoEval;
    }

    /**
     * @param popVideoEval the popVideoEval to set
     */
    public void setPopVideoEval(PopCenterVideoEvalBean popVideoEval) {
        this.popVideoEval = popVideoEval;
    }

    /**
     * @return the clientTemplateURL
     */
    public String getClientTemplateURL() {
        return clientTemplateURL;
    }

    /**
     * @param clientTemplateURL the clientTemplateURL to set
     */
    public void setClientTemplateURL(String clientTemplateURL) {
        this.clientTemplateURL = clientTemplateURL;
    }

    /**
     * @return the operImgServicesURL
     */
    public String getOperImgServicesURL() {
        return operImgServicesURL;
    }

    /**
     * @param operImgServicesURL the operImgServicesURL to set
     */
    public void setOperImgServicesURL(String operImgServicesURL) {
        this.operImgServicesURL = operImgServicesURL;
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
     * @return the myTransparent
     */
    public Image getMyTransparent() {
        return myTransparent;
    }

    /**
     * @param myTransparent the myTransparent to set
     */
    public void setMyTransparent(Image myTransparent) {
        this.myTransparent = myTransparent;
    }

    /**
     * @return the lblCurrentTime
     */
    public Label getLblCurrentTime() {
        return lblCurrentTime;
    }

    /**
     * @param lblCurrentTime the lblCurrentTime to set
     */
    public void setLblCurrentTime(Label lblCurrentTime) {
        this.lblCurrentTime = lblCurrentTime;
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
     * @return the lblConfirmClose
     */
    public Label getLblConfirmClose() {
        return lblConfirmClose;
    }

    /**
     * @param lblConfirmClose the lblConfirmClose to set
     */
    public void setLblConfirmClose(Label lblConfirmClose) {
        this.lblConfirmClose = lblConfirmClose;
    }

    /**
     * @return the lblTiempoMarcas
     */
    @Override
    public Label getLblTiempoMarcas() {
        return lblTiempoMarcas;
    }

    /**
     * @param lblTiempoMarcas the lblTiempoMarcas to set
     */
    public void setLblTiempoMarcas(Label lblTiempoMarcas) {
        this.lblTiempoMarcas = lblTiempoMarcas;
    }

    /**
     * @return the lblTiempoMarcasFinal
     */
    @Override
    public Label getLblTiempoMarcasFinal() {
        return lblTiempoMarcasFinal;
    }

    /**
     * @param lblTiempoMarcasFinal the lblTiempoMarcasFinal to set
     */
    public void setLblTiempoMarcasFinal(Label lblTiempoMarcasFinal) {
        this.lblTiempoMarcasFinal = lblTiempoMarcasFinal;
    }

    /**
     * @return the lblTiempoMarcasInicial
     */
    @Override
    public Label getLblTiempoMarcasInicial() {
        return lblTiempoMarcasInicial;
    }

    /**
     * @param lblTiempoMarcasInicial the lblTiempoMarcasInicial to set
     */
    public void setLblTiempoMarcasInicial(Label lblTiempoMarcasInicial) {
        this.lblTiempoMarcasInicial = lblTiempoMarcasInicial;
    }

    /**
     * @return the lblSpritesVttFile
     */
    public Label getLblSpritesVttFile() {
        return lblSpritesVttFile;
    }

    /**
     * @param lblSpritesVttFile the lblSpritesVttFile to set
     */
    public void setLblSpritesVttFile(Label lblSpritesVttFile) {
        this.lblSpritesVttFile = lblSpritesVttFile;
    }

    public Label getLblCenterNomMetrica() {
        return lblCenterNomMetrica;
    }

    public void setLblCenterNomMetrica(Label lblCenterNomMetrica) {
        this.lblCenterNomMetrica = lblCenterNomMetrica;
    }

    /**
     * @return the operatorImagesIp
     */
    public String getOperatorImagesIp() {
        return operatorImagesIp;
    }

    /**
     * @param operatorImagesIp the operatorImagesIp to set
     */
    @Override
    public void setOperatorImagesIp(String operatorImagesIp) {
        this.operatorImagesIp = operatorImagesIp;
    }
}