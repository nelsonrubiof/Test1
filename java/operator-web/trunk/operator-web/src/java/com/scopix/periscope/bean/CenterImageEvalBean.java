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
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Equivalence;
import com.scopix.periscope.model.Metric;

/**
 * Clase encargada del manejo de la imágen en la parte central de la pantalla de evaluación
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class CenterImageEvalBean extends GenericForwardComposer implements Serializable, CenterEvidence {

    private String login;
    private Metric metric;
    private String metricType;
    private Image imgEvidence;
    private Session mySession;
    private Label lblPlantilla;
    private Image imgPlantilla;
    private Button btnDeshacer;
    private String evidenceURL;
    private String alternateSrc;
    private String templatePath;
    private String evidenceType;
    private Label lblConfirmClose;
    private Button btnDeshacerTodo;
    private String operatorImagesIp;
    private ShowMessage showMessage;
    private String clientTemplateURL;
    private String operImgServicesURL;
    private Label lblCenterNomMetrica;
    private PopCenterImageEvalBean popImageEval;
    private RightMetricDetail rightMetricDetail;
    private UserCredentialManager userCredentialManager;
    private static final long serialVersionUID = 3692580045617593543L;
    private static Logger log = Logger.getLogger(CenterImageEvalBean.class);

    /**
     * Auto forward events and wire accessible variables of the specified component into a controller Java object; a subclass that
     * override this method should remember to call super.doAfterCompose(comp) or it will not work.
     *
     * @author carlos polo
     * @version 1.0.0
     * @param comp componente
     * @since 6.0 @date 20/02/2013
     */
    @Override
    public void doAfterCompose(Component comp) {
        login = ((String) getMySession().getAttribute("LOGIN"));
        log.info("start - " + login);
        try {
            super.doAfterCompose(comp);

            setPopImageEval((PopCenterImageEvalBean) getMySession().getAttribute("popCenterImageBean"));
            getPopImageEval().setCenterImageEvalBean(this);

            // Si el usuario no se encuentra autenticado, lo redirige a la pantalla de login
            if (!getUserCredentialManager().isAuthenticated()) {
                getExecution().sendRedirect("login.zul");
            } else {
                // Texto: Por favor presione el botón "Enviar y Finalizar" y cierre la sesión en la pantalla de inicio
                // para una correcta salida de la aplicación.
                getLblConfirmClose().setValue(Labels.getLabel("eval.confirmClose"));
                // El usuario se encuentra autenticado, continua el proceso de carga de la pantalla
                getLblPlantilla().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                // Establece el src de la imágen evidencia
                processEvidenceSrc();

                if (getMetric() != null) {
                    // Si la métrica tiene figuras previamente marcadas, dibuja la figura
                    processMetricShapes();
                    // Actualiza conteo de la métrica indicando si hay cambio de cámara o no
                    processMetricDescription();

                    if (getTemplatePath() != null && FilenameUtils.isExtension(getTemplatePath(), "png")) {
                        processTemplatePath();
                        Clients.evalJavaScript("plantilla = 'S';");
                    }
                }
                Clients.evalJavaScript("updateLblCurrentEvType('" + EnumEvidenceType.IMAGE.toString() + "');");
                // Actualiza el tiempo de inicio evaluación de la métrica
                updateMetricStarTime();
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
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

        log.debug("getAlternateSrc(): " + getAlternateSrc() + " - " + login);
        if (getAlternateSrc() != null) {
            getImgEvidence().setSrc(getAlternateSrc());
        } else {
            // http://173.204.188.250:8080/operator-images-services/services/REST/
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

            // http://173.204.188.250:8080/operator-images-services/services/REST/getImage/Lowes/404/20140724/xxxx.jpg
            getImgEvidence().setSrc(
                    operImgServicesURL + "getImage/" + cliente.getName() + "/" + storeName + "/" + fileDate + "/" + fileName);
        }
        log.info("end - " + login);
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
     * Si la métrica tiene figuras previamente marcadas, dibuja la figura (círculo o cuadrado) dependiendo del tipo
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 25/03/2013
     */
    public void processMetricShapes() {
        log.info("start - " + login);
        if (getMetric().getType().equals(EnumEvaluationType.NUMBER_INPUT.toString())) {
            getBtnDeshacer().setDisabled(true);
            getBtnDeshacerTodo().setDisabled(true);
        }

        // Obtiene información si tiene figuras marcadas previamente para mostrarlas
        int cameraId = getMetric().getCurrentCameraId();

        String circlesInfo = getMetric().getCirclesInfo().get(cameraId);
        String squaresInfo = getMetric().getSquaresInfo().get(cameraId);

        String noSquaresInfo = getMetric().getNoSquaresInfo().get(cameraId);
        String yesSquaresInfo = getMetric().getYesSquaresInfo().get(cameraId);

        log.debug("circlesInfo: [" + circlesInfo + "], squaresInfo: [" + squaresInfo + "]");

        if (circlesInfo != null && !circlesInfo.trim().equals("")) {
            Clients.evalJavaScript("drawCircles('" + circlesInfo + "');");
        }
        if (squaresInfo != null && !squaresInfo.trim().equals("")) {
            Clients.evalJavaScript("drawSquares('" + squaresInfo + "');");
        }

        // Procesamiento para dibujar marcas YES/NO
        drawYesNoSquares(yesSquaresInfo, noSquaresInfo);
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
     * Muestra u oculta la plantilla de la evidencia
     *
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 25/03/2013
     */
    public void onClickPlantilla(ForwardEvent event) {
        log.info("start - " + login);
        if (getTemplatePath() != null) {
            String valuePlantilla = getLblPlantilla().getValue();

            if (valuePlantilla.equals("S")) {
                processTemplatePath();
            } else {
                getImgPlantilla().setVisible(false);
            }
        } else {
            log.debug("La evidencia no tiene plantilla asociada&" + login);
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
     * @param event
     * @since 6.0 @date 02/04/2013
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
     * Actualiza valor de la métrica, invocado cuando se hace click sobre la evidencia o se presionan los botones deshacer o
     * deshacer todo
     *
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 25/03/2013
     */
    public void onClickUpdateMetricValue(ForwardEvent event) {
        log.info("start - " + login);
        updateMetricValue();
        log.info("end - " + login);
    }

    /**
     * Actualiza valor de la métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0 @date 25/03/2013
     */
    public void updateMetricValue() {
        log.info("start - " + login);
        getRightMetricDetail().updateMetricValue(getRightMetricDetail().getLblValorMetrica().getValue());
        log.info("end - " + login);
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

    /**
     * @return the evidenceURL
     */
    public String getEvidenceType() {
        return evidenceType;
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
    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    /**
     * @return the metric
     */
    public Metric getMetric() {
        return metric;
    }

    @Override
    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    /**
     * @return the metricType
     */
    public String getMetricType() {
        return metricType;
    }

    /**
     * @return the imgEvidence
     */
    public Image getImgEvidence() {
        return imgEvidence;
    }

    /**
     * @param imgEvidence the imgEvidence to set
     */
    public void setImgEvidence(Image imgEvidence) {
        this.imgEvidence = imgEvidence;
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

    @Override
    public void setPlayerPosition(String playerPosition) {
        // Nothing
    }

    /**
     * @return the alternateSrc
     */
    public String getAlternateSrc() {
        return alternateSrc;
    }

    /**
     * @param alternateSrc the alternateSrc to set
     */
    @Override
    public void setAlternateSrc(String alternateSrc) {
        this.alternateSrc = alternateSrc;
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
     * @return the popImageEval
     */
    public PopCenterImageEvalBean getPopImageEval() {
        return popImageEval;
    }

    /**
     * @param popImageEval the popImageEval to set
     */
    public void setPopImageEval(PopCenterImageEvalBean popImageEval) {
        this.popImageEval = popImageEval;
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

    @Override
    public Label getLblTiempoMarcas() {
        return new Label();
    }

    @Override
    public Label getLblTiempoMarcasInicial() {
        return new Label();
    }

    @Override
    public Label getLblTiempoMarcasFinal() {
        return new Label();
    }

    @Override
    public void onClickVerMarcas(ForwardEvent event) {

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