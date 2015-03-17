package com.scopix.periscope.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.CenterEvidence;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.model.Camara;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.Metric;

/**
 * Clase encargada del manejo de la métrica de tiempos en la parte derecha de la pantalla de evaluación
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class RightTimeMetricBean extends GenericForwardComposer implements Serializable, RightMetricDetail {

    private String login;
    private Metric metric;
    private Image imgFinal;
    private Image imgInicial;
    private Label lblElapsed;
    private Session mySession;
    private String evidenceURL;
    private Label lblIniSquares;
    private Label lblFinSquares;
    private String templatePath;
    private String evidenceType;
    private boolean allEvaluated;
    private Label lblTFinalMenor;
    private Label lblTiempoFinal;
    private Label lblCurrentTIme;
    private String playerPosition;
    private Label lblValorMetrica;
    private Button btnNextCamara;
    private Label lblTiempoInicial;
    private Button btnAsignarFinal;
    private String operatorImagesIp;
    private ShowMessage showMessage;
    private Button btnAsignarInicial;
    private Combobox cmbMultiCamaras;
    private int currentCameraId = -1;
    private Button btnEnviarFinalizar;
    private Groupbox groupBoxMultiCamara;
    private CenterEvidence centerEvidence;
    private LeftMetricList leftMetricBean;
    private UserCredentialManager userCredentialManager;
    private List<Camara> lstCamaras = new ArrayList<Camara>();
    private static final long serialVersionUID = 56879593814535068L;
    private HashMap<Integer, String> iniSquaresInfo = new HashMap<Integer, String>();
    private HashMap<Integer, String> finSquaresInfo = new HashMap<Integer, String>();
    private static Logger log = Logger.getLogger(RightTimeMetricBean.class);

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

            // Si el usuario no se encuentra autenticado, lo redirige a la pantalla de login
            if (!getUserCredentialManager().isAuthenticated()) {
                getExecution().sendRedirect("login.zul");
            } else {
                getImgFinal().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblElapsed().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getImgInicial().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblTFinalMenor().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblValorMetrica().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblTiempoFinal().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblCurrentTIme().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblTiempoInicial().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblIniSquares().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblFinSquares().setAttribute("org.zkoss.zk.ui.updateByClient", true);

                // Si todas las métricas están evaluadas, habilita el botón de enviar
                if (isAllEvaluated()) {
                    getBtnEnviarFinalizar().setDisabled(false);
                }

                List<Evidence> lstEvidencias = getMetric().getEvidencias();
                // Valida si la métrica es multicámara para habilitar o no el correspondiente combobox
                processMulticameraValidation(lstEvidencias);

                int cameraId = Integer.valueOf(lstEvidencias.get(0).getEvidenceProvider().getId());
                setCurrentCameraId(cameraId);
                getMetric().setCurrentCameraId(cameraId);
                log.debug("cameraId: " + cameraId + " - " + login);
                // Establece variables la métrica
                prepareMeasureTime(lstEvidencias);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Establece variables la métrica como datos marcas, url de la evidencia y creación de la parte central de la pantalla
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param lstEvidencias lista de evidencias
     * @since 6.0
     * @date 14/11/2013
     */
    protected void prepareMeasureTime(List<Evidence> lstEvidencias) {
        log.info("start - " + login);
        // Validaciones para mostrar datos de los tiempos inicial y final
        processInitialValues();
        processFinalValues();

        if (getMetric().getIniSquaresInfo() != null) {
            setIniSquaresInfo(getMetric().getIniSquaresInfo());
        }
        if (getMetric().getFinSquaresInfo() != null) {
            setFinSquaresInfo(getMetric().getFinSquaresInfo());
        }

        String value = getMetric().getDescription();
        if (value != null) {
            getLblValorMetrica().setValue(value);
        }

        setTemplatePath(lstEvidencias.get(0).getTemplatePath());
        setEvidenceURL(lstEvidencias.get(0).getEvidencePath());
        setEvidenceType(lstEvidencias.get(0).getEvidenceType());
        setOperatorImagesIp(lstEvidencias.get(0).getOperatorImagesIp());

        log.debug("templatePath: " + getTemplatePath() + " - " + login);
        log.debug("evidenceURL: " + getEvidenceURL() + " - " + login);
        log.debug("evidenceType: " + getEvidenceType() + " - " + login);

        // Crea la parte central de la pantalla
        prepareCenterEvidence(getEvidenceType());

        String zoneName = getLeftMetricBean().getGlobalEvaluation().getNorthHeaderBean().getZoneName();
        log.debug("zoneName: " + zoneName + " - " + login);
        String[] saZone = zoneName.split("/");

        getLeftMetricBean().getGlobalEvaluation().getNorthHeaderBean()
                .setZoneName(saZone[0] + "/" + lstEvidencias.get(0).getEvidenceProvider().getDescripcion());

        boolean cantEval = getMetric().isCantEval();
        if (cantEval) {
            Clients.evalJavaScript("noEvalGuardar = 'S';");
        } else {
            Clients.evalJavaScript("noEvalGuardar = 'N';");
        }
        log.info("end - " + login);
    }

    /**
     * Valida si la métrica es multicámara para habilitar o no el correspondiente combobox
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param lstEvidencias lista de evidencias
     * @since 6.0
     * @date 21/02/2013
     */
    protected void processMulticameraValidation(List<Evidence> lstEvidencias) {
        log.info("start - " + login);
        if (getMetric().isMultiple()) {
            log.debug("la metrica es multicamara, metricId: " + getMetric().getMetricId() + " - " + login);
            for (Evidence evidencia : lstEvidencias) {
                Camara camara = new Camara();
                camara.setId(Integer.valueOf(evidencia.getEvidenceProvider().getId()));
                camara.setEvidencePath(evidencia.getEvidencePath());
                camara.setEvidenceType(evidencia.getEvidenceType().toString());
                camara.setTemplatePath(evidencia.getTemplatePath());
                camara.setName(evidencia.getEvidenceProvider().getDescripcion());
                camara.setOperatorImagesIp(evidencia.getOperatorImagesIp());
                getLstCamaras().add(camara);
            }
            getBtnNextCamara().setVisible(true);
            getCmbMultiCamaras().setVisible(true);
            getGroupBoxMultiCamara().setVisible(true);

        } else {
            log.debug("la metrica NO es multicamara, metricId: " + getMetric().getMetricId() + " - " + login);
            getBtnNextCamara().setVisible(false);
            getCmbMultiCamaras().setVisible(false);
            getGroupBoxMultiCamara().setVisible(false);
        }
        log.info("end - " + login);
    }

    /**
     * Validaciones para mostrar datos del tiempo inicial
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 21/02/2013
     */
    protected void processInitialValues() {
        log.info("start - " + login);
        String tInicial = getMetric().gettInicial();
        String urlImgInicial = getMetric().getUrlTInicial();

        if (tInicial != null) {
            getLblTiempoInicial().setValue(tInicial);
        }
        if (urlImgInicial != null) {
            getImgInicial().setSrc(urlImgInicial);
            getImgInicial().setTooltip("Tiempo Inicial");
        }
        log.info("end - " + login);
    }

    /**
     * Validaciones para mostrar datos del tiempo final
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 21/02/2013
     */
    protected void processFinalValues() {
        log.info("start - " + login);
        String tFinal = getMetric().gettFinal();
        String urlImgFinal = getMetric().getUrlTFinal();

        if (tFinal != null) {
            getLblTiempoFinal().setValue(tFinal);
        }
        if (urlImgFinal != null) {
            getImgFinal().setSrc(urlImgFinal);
            getImgFinal().setTooltip("Tiempo Final");
        }
        log.info("end - " + login);
    }

    /**
     * Define página a ser mostrada en la parte central para crear una instancia del correspondiente bean dependiendo del tipo de
     * evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param evidenceType tipo de evidencia
     * @since 6.0
     * @date 21/02/2013
     */
    public void prepareCenterEvidence(String evidenceType) {
        log.info("start - " + login);
        Include centerInclude = new Include();
        centerEvidence = createCenterEvidenceBean(evidenceType);

        if (evidenceType.equalsIgnoreCase(EnumEvidenceType.IMAGE.toString())) {
            centerInclude.setSrc("centerImageEval.zul");
        } else {
            centerInclude.setSrc("centerVideoEval.zul");
            getCenterEvidence().setPlayerPosition(getPlayerPosition());
        }
        prepareCenterDetail(centerInclude);
        log.info("end - " + login);
    }

    /**
     * Prepara la inicialización del bean y la pantalla de la parte central
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param centerInclude página a incluir en la parte central de la pantalla
     * @since 6.0
     * @date 20/02/2013
     */
    public void prepareCenterDetail(Include centerInclude) {
        log.info("start - " + login);
        getCenterEvidence().setTemplatePath(getTemplatePath());
        getCenterEvidence().setMetric(getMetric());
        getCenterEvidence().setMetricType(getMetric().getType());
        getCenterEvidence().setOperatorImagesIp(getOperatorImagesIp());
        // Ubicación evidencia y tipo (video, imágen)
        getCenterEvidence().setEvidenceURL(getEvidenceURL());
        getCenterEvidence().setEvidenceType(getEvidenceType());
        // Vincula a nivel de eventos la página con la instancia del bean
        getCenterEvidence().bindComponentAndParent(centerInclude, this);
        // Adiciona en la parte derecha del borderlayout la página a incluir
        getLeftMetricBean().getGlobalEvaluation().setCenterInclude(centerInclude);
        log.info("end - " + login);
    }

    /**
     * Crea una instancia de bean de la parte central de la pantalla dependiendo del tipo de evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param evidenceType tipo de evidencia
     * @since 6.0
     * @date 21/02/2013
     * @return CenterEvidence instancia de la parte central de la pantalla
     */
    public CenterEvidence createCenterEvidenceBean(String evidenceType) {
        log.info("start - " + login);
        CenterEvidence centerEvidence = null;

        if (evidenceType.equalsIgnoreCase(EnumEvidenceType.IMAGE.toString())) {
            centerEvidence = new CenterImageEvalBean();
        } else {
            centerEvidence = new CenterVideoEvalBean();
        }
        log.info("end - " + login);
        return centerEvidence;
    }

    /**
     * Si la métrica es multicámara, ubica el correspondiente combobox en la primera posición
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    public void onAfterRenderMulticamara(ForwardEvent event) {
        log.info("start - " + login);
        if (getMetric().isMultiple()) {
            getCmbMultiCamaras().setSelectedIndex(0);
        }
        log.info("end - " + login);
    }

    /**
     * Invocado al momento de presionar el botón "siguiente"
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    public void onClickSiguiente(ForwardEvent event) {
        log.info("start - " + login);
        List lstValues = setMetricEvalValues();
        boolean evaluada = (Boolean) lstValues.get(0);
        String valorMetrica = (String) lstValues.get(1);

        if (evaluada) {
            log.debug("la metrica actual esta evaluada - " + login);
            int selectedIndex = 0;
            if (getCmbMultiCamaras().isVisible()) {
                selectedIndex = getCmbMultiCamaras().getSelectedIndex();
                setCurrentCameraId(getLstCamaras().get(selectedIndex).getId());
            }
            saveCurrentEvidenceEvalData(getCurrentCameraId(), false);
            getMetric().setCurrentCameraId(getCurrentCameraId());

            if (validateCamerasEval(evaluada)) {
                getShapesData();
                updateLeftMetric(valorMetrica, evaluada);
                int numMetrica = Integer.valueOf(getMetric().getNumMetrica());
                getLeftMetricBean().validateMetricEval(numMetrica + 1);
            }
        } else {
            log.debug("la metrica actual no esta evaluada - " + login);
            String titulo = Labels.getLabel("eval.validarEvalTitle");
            String mensaje = Labels.getLabel("eval.validarEvalText");
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza la métrica actual en la parte izquierda de la pantalla
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param valorMetrica valor por actualizar
     * @param evaluada indica si la métrica está evaluada o no
     * @since 6.0
     * @date 08/03/2013
     */
    public void updateLeftMetric(String valorMetrica, boolean evaluada) {
        log.info("start - " + login);
        // Actualiza métrica en listado izquierdo
        int numMetrica = Integer.valueOf(getMetric().getNumMetrica());

        Metric metrica = (Metric) getLeftMetricBean().getMetricasModel().get(numMetrica);
        metrica.setEvaluada(evaluada);
        metrica.settInicial(getLblTiempoInicial().getValue());
        metrica.settFinal(getLblTiempoFinal().getValue());
        metrica.setDescription(valorMetrica);
        metrica.setUrlTInicial(getImgInicial().getSrc());
        metrica.setUrlTFinal(getImgFinal().getSrc());

        // HashMap<Integer, String> tiempoMarcasInicial = metrica.getTiempoMarcasInicial();
        // HashMap<Integer, String> tiempoMarcasFinal = metrica.getTiempoMarcasFinal();
        //
        // tiempoMarcasInicial.clear();
        // tiempoMarcasFinal.clear();
        //
        // tiempoMarcasInicial.put(getCurrentCameraId(), getCenterEvidence().getLblTiempoMarcasInicial().getValue());
        // tiempoMarcasFinal.put(getCurrentCameraId(), getCenterEvidence().getLblTiempoMarcasFinal().getValue());
        //
        // metrica.setTiempoMarcasInicial(tiempoMarcasInicial);
        // metrica.setTiempoMarcasFinal(tiempoMarcasFinal);
        metrica.setEndEvaluationTime(new Date());
        getLeftMetricBean().getMetricasModel().set(numMetrica, metrica);
        log.info("end - " + login);
    }

    @Override
    public List setMetricEvalValues() {
        log.info("start - " + login);
        List values = new ArrayList();
        boolean evaluada = true;
        String valorMetrica = getLblValorMetrica().getValue();

        if (!valorMetrica.equalsIgnoreCase(Labels.getLabel("eval.noDefinido"))) {
            evaluada = true;
        } else {
            evaluada = false;
        }
        values.add(evaluada);
        values.add(valorMetrica);

        log.debug("metricId: " + getMetric().getMetricId() + ", evaluada: " + evaluada + ", valorMetrica: " + valorMetrica
                + " - " + login);
        log.info("end - " + login);
        return values;
    }

    /**
     * Valida que todas las cámaras hayan sido evaluadas
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param evaluada indica si el valor de la métrica es diferente a "no definido"
     * @since 6.0
     * @date 08/03/2013
     * @return boolean true si al menos una cámara fue evaluada
     */
    @Override
    public boolean validateCamerasEval(boolean evaluada) {
        log.info("start - " + login);
        boolean isValid = false;

        if (getCmbMultiCamaras().isVisible()) {
            if (validateTimeEval(true)) { // valida tiempo inicial
                if (validateTimeEval(false)) { // valida tiempo final
                    isValid = true;
                }
            }
        } else {
            boolean tiempoInicialTiene = false;
            if (!getIniSquaresInfo().isEmpty()) {
                for (Map.Entry e : getIniSquaresInfo().entrySet()) {
                    int cameraId = Integer.valueOf(e.getKey().toString());

                    String iniSquareInfo = getIniSquaresInfo().get(cameraId);
                    if (iniSquareInfo != null && !iniSquareInfo.trim().equals("")) {
                        tiempoInicialTiene = true;
                        break;
                    }
                }
            }

            if (tiempoInicialTiene) {
                for (Map.Entry e : getFinSquaresInfo().entrySet()) {
                    int cameraId = Integer.valueOf(e.getKey().toString());

                    String finSquareInfo = getFinSquaresInfo().get(cameraId);
                    if (finSquareInfo != null && !finSquareInfo.trim().equals("")) {
                        isValid = true;
                        break;
                    }
                }
            }
        }

        if (!isValid && evaluada) {
            isValid = true;
        }

        log.info("end, isValid: " + isValid + " - " + login);
        return isValid;
    }

    /**
     * Muestra mensaje en pantalla
     * 
     * @param titulo título del mensaje
     * @param mensaje cuerpo del mensaje
     * @param tipo tipo de mensaje
     */
    protected void mostrarMensaje(String titulo, String mensaje, String tipo) {
        getShowMessage().mostrarMensaje(titulo, mensaje, tipo);
    }

    /**
     * Valida si el tiempo (inicial o final) fue evaluado
     * 
     * @param isInitial hashmap a validar, tiempo inicial o tiempo final
     * @return true en caso de estar validado como evaluado
     */
    protected boolean validateTimeEval(boolean isInitial) {
        boolean isEvaluated = false;
        HashMap<Integer, String> squareInfo = null;

        if (isInitial) {
            squareInfo = getIniSquaresInfo();
        } else {
            squareInfo = getFinSquaresInfo();
        }

        for (int i = 0; i < getLstCamaras().size(); i++) {
            Camara camara = getLstCamaras().get(i);
            String squareData = squareInfo.get(camara.getId());

            if (squareData != null && !squareData.isEmpty()) {
                isEvaluated = true;
                break;
            }
        }
        return isEvaluated;
    }

    /**
     * Invocado al momento de cambiar de cámara (multicámara) Almacena información de la evaluación y prepara siguiente evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    public void onSelectCamara(ForwardEvent event) {
        log.info("start - " + login);
        int selectedIndex = getCmbMultiCamaras().getSelectedIndex();
        int selectedCameraId = getLstCamaras().get(selectedIndex).getId();

        setCurrentCameraId(selectedCameraId);
        setCurrentCamera(selectedIndex);
        log.info("end - " + login);
    }

    /**
     * Cambia hacia la cámara en donde se hicieron las marcas
     * 
     * @param idCamaraMarcas id de la cámara en donde se hicieron las marcas
     * @param tiempoMarcas tiempo en el que se hicieron las marcas
     */
    @Override
    public void changeCameraVerMarcas(int idCamaraMarcas, String tiempoMarcas) {
        for (int i = 0; i < getLstCamaras().size(); i++) {
            Camara camara = getLstCamaras().get(i);
            if (idCamaraMarcas == camara.getId()) {
                setCurrentCameraId(camara.getId());
                getLeftMetricBean().getLblPlayerTime().setValue(tiempoMarcas);
                setCurrentCamera(i);
                getCmbMultiCamaras().setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Selecciona la siguiente cámara (multicámara)
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/04/2013
     */
    public void onClickNextCamara(ForwardEvent event) {
        log.info("start - " + login);
        int selectedIndex = getCmbMultiCamaras().getSelectedIndex();

        if (selectedIndex + 1 >= getLstCamaras().size()) {
            String titulo = Labels.getLabel("eval.ultimaCamaraTitle");
            String mensaje = Labels.getLabel("eval.ultimaCamaraText"); // Usted se encuentra en la última cámara
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        } else {
            setCurrentCamera(selectedIndex + 1);
            getCmbMultiCamaras().setSelectedIndex(selectedIndex + 1);
        }
        log.info("end - " + login);
    }

    /**
     * Establece la cámara seleccionada
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param selectedIndex índice seleccionado
     * @since 6.0
     * @date 08/04/2013
     */
    protected void setCurrentCamera(int selectedIndex) {
        log.info("start, selectedIndex: " + selectedIndex + " - " + login);
        Camara camara = getLstCamaras().get(selectedIndex);
        // setCurrentCameraId(camara.getId());
        setEvidenceURL(camara.getEvidencePath());
        setEvidenceType(camara.getEvidenceType());
        setTemplatePath(camara.getTemplatePath());
        setOperatorImagesIp(camara.getOperatorImagesIp());

        String zoneName = getLeftMetricBean().getGlobalEvaluation().getNorthHeaderBean().getZoneName();
        String[] saZone = zoneName.split("/");

        getLeftMetricBean().getGlobalEvaluation().getNorthHeaderBean().setZoneName(saZone[0] + "/" + camara.getName());

        saveCurrentEvidenceEvalData(camara.getId(), true);
        Clients.evalJavaScript("deleteAllCurrentShapes('S','S','N');");

        setPlayerPosition(getLeftMetricBean().getLblPlayerTime().getValue());
        prepareCenterEvidence(camara.getEvidenceType());
        log.info("end - " + login);
    }

    /**
     * Almacena información de la evaluación y prepara siguiente evidencia
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param selectedCameraId id de la cámara seleccionada
     * @param isCambioCamara indica si hubo cambio de cámara
     * @since 6.0
     * @date 08/03/2013
     */
    @Override
    public void saveCurrentEvidenceEvalData(int selectedCameraId, boolean isCambioCamara) {
        log.info("start, selectedCameraId: " + selectedCameraId + ", isCambioCamara: " + isCambioCamara + " - " + login);

        updateEvalData();
        String currentTime = getLblCurrentTIme().getValue();

        if (isCambioCamara) {
            getMetric().setCurrentTime(currentTime);
        } else {
            getMetric().setCurrentTime(null);
        }

        getMetric().setCurrentCameraId(selectedCameraId);

        int numMetrica = Integer.valueOf(getMetric().getNumMetrica());

        Metric metricaLeftModel = (Metric) getLeftMetricBean().getMetricasModel().get(numMetrica);
        metricaLeftModel.setCirclesInfo(getMetric().getCirclesInfo());
        metricaLeftModel.setSquaresInfo(getMetric().getSquaresInfo());
        metricaLeftModel.setCurrentTime(getMetric().getCurrentTime());
        metricaLeftModel.setCurrentCameraId(getMetric().getCurrentCameraId());

        metricaLeftModel.settInicial(getLblTiempoInicial().getValue());
        metricaLeftModel.settFinal(getLblTiempoFinal().getValue());
        metricaLeftModel.setUrlTInicial(getImgInicial().getSrc());
        metricaLeftModel.setUrlTFinal(getImgFinal().getSrc());

        getLeftMetricBean().getMetricasModel().set(numMetrica, metricaLeftModel);

        setCurrentCameraId(selectedCameraId);
        log.info("end - " + login);
    }

    @Override
    public void updateEvalData() {
        // String iniSquaresValue = getLblIniSquares().getValue();
        // String finSquaresValue = getLblFinSquares().getValue();
        //
        // if(iniSquaresValue!=null && !iniSquaresValue.isEmpty()){
        // getIniSquaresInfo().clear();
        // getIniSquaresInfo().put(getCurrentCameraId(), iniSquaresValue);
        // getMetric().setIniSquaresInfo(getIniSquaresInfo());
        // }
        //
        // if(finSquaresValue!=null && !finSquaresValue.isEmpty()){
        // getFinSquaresInfo().clear();
        // getFinSquaresInfo().put(getCurrentCameraId(), finSquaresValue);
        // getMetric().setFinSquaresInfo(getFinSquaresInfo());
        // }
    }

    /**
     * Invocado para establecer la imágen y tiempo inicial
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @throws ParseException
     * @since 6.0
     * @date 08/03/2013
     */
    public void onClickInicial(ForwardEvent event) throws ParseException {
        log.info("start - " + login);
        if (getLblTFinalMenor().getValue().equalsIgnoreCase("N")) {
            String iniSquaresValue = getLblIniSquares().getValue();
            getIniSquaresInfo().clear();
            getIniSquaresInfo().put(getCurrentCameraId(), iniSquaresValue);

            getMetric().setIniSquaresInfo(getIniSquaresInfo());

            getMetric().getTiempoMarcasInicial().clear();
            getMetric().getTiempoMarcasInicial().put(getCurrentCameraId(),
                    getCenterEvidence().getLblTiempoMarcasInicial().getValue());

            String elapsedTime = getLblElapsed().getValue();

            Client cliente = (Client) getSession().getAttribute("CLIENT");
            String clientName = cliente.getName();

            String markData = getLblIniSquares().getValue();

            // http://173.204.188.250:8080/operator-images-services/services/REST/
            String operImgServicesURL = processOperatorImagesURL(cliente.getOperatorImgServicesURL());

            // Establece ubicación de la evidencia
            String storeName = getLeftMetricBean().getSituation().getStoreName();
            String evidenceDate = getLeftMetricBean().getSituation().getEvidenceDateTime();

            // it's executed in order to check that it's a valid date
            Date d1 = DateUtils.parseDate(evidenceDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
            String[] dateParser = StringUtils.split(DateFormatUtils.format(d1, "yyyy-MM-dd"), "-");
            String year = dateParser[0];
            String month = dateParser[1];
            String day = dateParser[2];

            String fileDate = year + month + day;
            String fileName = FilenameUtils.getName(getEvidenceURL()); // with extension

            // getSnapshotWithMark/{elapsedTime}/{markData}/{corporateName}/{store}/{date}/{fileName}
            String imageURL = operImgServicesURL + "getSnapshotWithMark/" + elapsedTime + "/" + markData + "/" + clientName + "/"
                    + storeName + "/" + fileDate + "/" + fileName;
            log.debug("url imagen inicial: [" + imageURL + "]");

            getImgInicial().setTooltip("Tiempo Inicial");
            getImgInicial().setSrc(imageURL);
            getBtnAsignarFinal().setDisabled(false); // habilita botón de tiempo final

        } else {
            getImgInicial().setTooltip("");
            getLblTiempoInicial().setValue(Labels.getLabel("eval.noDefinido"));
            getImgInicial().setSrc("/img/blackBack.png");
        }

        // Hay una imágen marcada en el tiempo final, procede a calcular diferencia
        if (getImgFinal().getTooltip() != null && !getImgFinal().getTooltip().equals("")) {
            calculateTime();
        } else {
            getMetric().setEvaluada(false);
            getMetric().setCantEval(false);
            getMetric().setCantEvalObs(null);
        }

        // Actualiza métrica en parte izquierda
        int numMetrica = Integer.valueOf(getMetric().getNumMetrica());
        getLeftMetricBean().getMetricasModel().set(numMetrica, getMetric());
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
     * Invocado para establecer la imágen y tiempo final
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @throws ParseException
     * @since 6.0
     * @date 08/03/2013
     */
    public void onClickFinal(ForwardEvent event) throws ParseException {
        log.info("start - " + login);
        if (getLblTFinalMenor().getValue().equalsIgnoreCase("N")) {
            // Actualiza valor del tiempo final
            processTiempoFinal();
        } else {
            getImgFinal().setTooltip("");
            getLblTiempoFinal().setValue(Labels.getLabel("eval.noDefinido"));
            getImgFinal().setSrc("/img/blackBack.png");
        }
        validateTimeCalculation();
        log.info("end - " + login);
    }

    protected void validateTimeCalculation() {
        log.info("start - " + login);
        // Hay una imágen marcada en el tiempo inicial, procede a calcular diferencia
        if (getImgInicial().getTooltip() != null && !getImgInicial().getTooltip().equals("")) {
            calculateTime();
        } else {
            getMetric().setEvaluada(false);
            getMetric().setCantEval(false);
            getMetric().setCantEvalObs(null);
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza valor del tiempo inicial
     * 
     * @author carlos polo
     * @version 1.0.0
     * @throws ParseException
     * @since 6.0
     * @date 08/03/2013
     */
    protected void processTiempoFinal() throws ParseException {
        log.info("start - " + login);
        String finSquaresValue = getLblFinSquares().getValue();
        getFinSquaresInfo().clear();
        getFinSquaresInfo().put(getCurrentCameraId(), finSquaresValue);

        getMetric().setFinSquaresInfo(getFinSquaresInfo());
        getMetric().getTiempoMarcasFinal().clear();
        getMetric().getTiempoMarcasFinal().put(getCurrentCameraId(), getCenterEvidence().getLblTiempoMarcasFinal().getValue());

        Client cliente = (Client) Sessions.getCurrent().getAttribute("CLIENT");
        String elapsedTime = getLblElapsed().getValue();
        String clientName = cliente.getName();

        String markData = getLblFinSquares().getValue();

        // http://173.204.188.250:8080/operator-images-services/services/REST/
        String operImgServicesURL = processOperatorImagesURL(cliente.getOperatorImgServicesURL());

        // Establece ubicación de la evidencia
        String storeName = getLeftMetricBean().getSituation().getStoreName();
        String evidenceDate = getLeftMetricBean().getSituation().getEvidenceDateTime();

        // it's executed in order to check that it's a valid date
        Date d1 = DateUtils.parseDate(evidenceDate, new String[] { "yyyy-MM-dd HH:mm:ss" });
        String[] dateParser = StringUtils.split(DateFormatUtils.format(d1, "yyyy-MM-dd"), "-");
        String year = dateParser[0];
        String month = dateParser[1];
        String day = dateParser[2];

        String fileDate = year + month + day;
        String fileName = FilenameUtils.getName(getEvidenceURL()); // with extension

        // getSnapshotWithMark/{elapsedTime}/{markData}/{corporateName}/{store}/{date}/{fileName}
        String imageURL = operImgServicesURL + "getSnapshotWithMark/" + elapsedTime + "/" + markData + "/" + clientName + "/"
                + storeName + "/" + fileDate + "/" + fileName;

        log.debug("url imagen final: [" + imageURL + "]");

        getImgFinal().setTooltip("Tiempo Final");
        getImgFinal().setSrc(imageURL);

        // Actualiza métrica en parte izquierda
        int numMetrica = Integer.valueOf(getMetric().getNumMetrica());
        getLeftMetricBean().getMetricasModel().set(numMetrica, getMetric());
        log.info("end - " + login);
    }

    /**
     * Calcula la diferencia entre los tiempos final e inicial
     * 
     * @author carlos polo
     * @version 1.0.0
     * @since 6.0
     * @date 08/03/2013
     */
    protected void calculateTime() {
        log.info("start - " + login);
        String strTiempoInicial = getLblTiempoInicial().getValue();
        String strTiempoFinal = getLblTiempoFinal().getValue();

        String notDefined = Labels.getLabel("eval.noDefinido");

        if (!strTiempoInicial.equalsIgnoreCase(notDefined) && !strTiempoFinal.equalsIgnoreCase(notDefined)) {

            String[] saTiempo1 = strTiempoInicial.split(":");
            String[] saTiempo2 = strTiempoFinal.split(":");

            Integer mins1 = Integer.valueOf(saTiempo1[0]);
            Integer segs1 = Integer.valueOf(saTiempo1[1]);

            Integer mins2 = Integer.valueOf(saTiempo2[0]);
            Integer segs2 = Integer.valueOf(saTiempo2[1]);

            Long tiempo1 = Long.valueOf((mins1 * 60) + segs1);
            Long tiempo2 = Long.valueOf((mins2 * 60) + segs2);
            Long difference = tiempo2 - tiempo1;

            if (difference >= 0) {
                // Actualiza valor de la métrica con el valor de la diferencia entre los tiempos inicial y final
                processTimeValue(difference);
            }
        }
        log.info("end - " + login);
    }

    /**
     * Actualiza valor de la métrica con el valor de la diferencia entre los tiempos inicial y final
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    protected void processTimeValue(Long difference) {
        log.info("start - " + login);
        Long minutes = difference / 60;
        Long seconds = difference % 60;

        String strMinutes = String.valueOf(minutes);
        String strSeconds = String.valueOf(seconds);

        if (minutes < 10) {
            strMinutes = "0" + strMinutes;
        }
        if (seconds < 10) {
            strSeconds = "0" + strSeconds;
        }
        getLblValorMetrica().setValue((strMinutes + ":" + strSeconds));
        updateMetricValue((strMinutes + ":" + strSeconds));

        // indica que la evaluación de la métrica fue cambiada
        getMetric().setChanged(true);
        getMetric().setEvaluada(true);
        getMetric().setCantEval(false);
        getMetric().setCantEvalObs(null);
        log.info("end - " + login);
    }

    /**
     * Logica para enviar y finalizar una evaluación
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    public void onClickFinalizar(ForwardEvent event) {
        log.info("start - " + login);
        List lstValues = setMetricEvalValues();
        boolean evaluada = (Boolean) lstValues.get(0);
        String valorMetrica = (String) lstValues.get(1);

        log.debug("evaluada: " + evaluada + ", valorMetrica: " + valorMetrica + " - " + login);
        if (evaluada) {
            log.debug("la metrica se encuentra evaluada - " + login);
            int selectedIndex = 0;
            if (getCmbMultiCamaras().isVisible()) {
                selectedIndex = getCmbMultiCamaras().getSelectedIndex();
                setCurrentCameraId(getLstCamaras().get(selectedIndex).getId());
            }
            saveCurrentEvidenceEvalData(getCurrentCameraId(), false);
            getMetric().setCurrentCameraId(getCurrentCameraId());

            if (validateCamerasEval(evaluada)) {
                getShapesData();
                Clients.evalJavaScript("hideAllCurrentCircles(); hideAllCurrentSquares();");
                // Actualiza la métrica actual en la parte izquierda de la pantalla
                updateLeftMetric(valorMetrica, evaluada);

                if (getLeftMetricBean().validateAllEvaluations()) { // Todas las métricas se encuentran evaluadas
                    String titulo = Labels.getLabel("eval.finalizar");
                    String mensaje = Labels.getLabel("eval.finalizarEvals"); // Desea finalizar las evaluaciones?

                    // Muestra el mensaje de confirmación de envío de la evaluación
                    showMessageBoxEnvioEval(mensaje, titulo);
                }
            }
        } else {
            log.debug("debe evaluar la metrica actual - " + login);
            String titulo = Labels.getLabel("eval.validarEvalTitle");
            String mensaje = Labels.getLabel("eval.validarEvalText");
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        }
        log.info("end - " + login);
    }

    /**
     * Muestra el mensaje de confirmación de envío de la evaluación
     *
     * @author carlos polo
     * @version 1.0.0
     * @param mensaje mensaje del messagebox
     * @param titulo título dem messagebox
     * @since 6.0
     * @date 02/04/2013
     */
    protected void showMessageBoxEnvioEval(String mensaje, String titulo) {
        log.info("start - " + login);
        Messagebox.show(mensaje, titulo, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                if (((Integer) event.getData()).intValue() == Messagebox.OK) {
                    log.debug("el usuario confirma envio de la evaluacion - " + login);
                    // Envía la evaluación, indicando que debe redireccionar a la pantalla de inicio
                    getLeftMetricBean().sendEvaluation(true, false);

                } else if (((Integer) event.getData()).intValue() == Messagebox.CANCEL) {
                    log.debug("el usuario cancelo el envio de la evaluacion - " + login);
                    getMySession().removeAttribute("CIRCLES_COUNT");
                    getMySession().removeAttribute("SQUARES_COUNT");
                    Clients.evalJavaScript("showAllCurrentCircles(); showAllCurrentSquares();");
                }
            }
        });
        log.info("end - " + login);
    }

    @Override
    public void getShapesData() {
        log.info("start - " + login);
        getLblIniSquares().setValue("");
        getLblFinSquares().setValue("");
    }

    /**
     * Muestra snapshot con marca para el tiempo inicial
     * 
     * @param event evento generado
     */
    public void onClickVerMarcaInicial(ForwardEvent event) {

        String imgInicialSrc = getImgInicial().getSrc();
        if (imgInicialSrc != null && !imgInicialSrc.contains("blackBack.png")) {
            getBtnAsignarInicial().setDisabled(false);
            getCenterEvidence().onClickVerMarcas(event);
        } else {
            String titulo = Labels.getLabel("verMarca.inicial.title"); // Ver marca inicial
            String mensaje = Labels.getLabel("verMarca.inicial.msg"); // Por favor realice una marca para el tiempo inicial

            getBtnAsignarInicial().setDisabled(true);
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        }
    }

    /**
     * Muestra snapshot con marca para el tiempo final
     * 
     * @param event evento generado
     */
    public void onClickVerMarcaFinal(ForwardEvent event) {

        String imgFinalSrc = getImgFinal().getSrc();
        if (imgFinalSrc != null && !imgFinalSrc.contains("blackBack.png")) {
            getBtnAsignarFinal().setDisabled(false);
            getCenterEvidence().onClickVerMarcas(event);
        } else {
            String titulo = Labels.getLabel("verMarca.final.title"); // Ver marca final
            String mensaje = Labels.getLabel("verMarca.final.msg"); // Por favor realice una marca para el tiempo final

            getBtnAsignarFinal().setDisabled(true);
            getShowMessage().mostrarMensaje(titulo, mensaje, Messagebox.EXCLAMATION);
        }
    }

    @Override
    public Metric getMetric() {
        return metric;
    }

    /**
     * @return the leftMetricBean
     */
    @Override
    public LeftMetricList getLeftMetricBean() {
        return leftMetricBean;
    }

    /**
     * @param leftMetricBean the leftMetricBean to set
     */
    public void setLeftMetricBean(LeftMetricList leftMetricBean) {
        this.leftMetricBean = leftMetricBean;
    }

    /**
     * @return the centerEvidence
     */
    @Override
    public CenterEvidence getCenterEvidence() {
        return centerEvidence;
    }

    /**
     * @param centerEvidence the centerEvidence to set
     */
    public void setCenterEvidence(CenterEvidence centerEvidence) {
        this.centerEvidence = centerEvidence;
    }

    @Override
    public void bindComponentAndParent(Include rightInclude, LeftMetricBean leftMetricBean) {
        this.bindComponent(rightInclude);
        this.setLeftMetricBean(leftMetricBean);
    }

    @Override
    public void setMetric(Metric currentMetric) {
        metric = currentMetric;
    }

    @Override
    public Long getMetricResult() {
        return null;
    }

    /**
     * @return the evidenceType
     */
    public String getEvidenceType() {
        return evidenceType;
    }

    /**
     * @param evidenceType the evidenceType to set
     */
    public void setEvidenceType(String evidenceType) {
        this.evidenceType = evidenceType;
    }

    /**
     * @return the cmbMultiCamaras
     */
    public Combobox getCmbMultiCamaras() {
        return cmbMultiCamaras;
    }

    /**
     * @param cmbMultiCamaras the cmbMultiCamaras to set
     */
    public void setCmbMultiCamaras(Combobox cmbMultiCamaras) {
        this.cmbMultiCamaras = cmbMultiCamaras;
    }

    /**
     * @return the lstCamaras
     */
    public List<Camara> getLstCamaras() {
        return lstCamaras;
    }

    /**
     * @param lstCamaras the lstCamaras to set
     */
    public void setLstCamaras(List<Camara> lstCamaras) {
        this.lstCamaras = lstCamaras;
    }

    /**
     * @return the groupBoxMultiCamara
     */
    public Groupbox getGroupBoxMultiCamara() {
        return groupBoxMultiCamara;
    }

    /**
     * @param groupBoxMultiCamara the groupBoxMultiCamara to set
     */
    public void setGroupBoxMultiCamara(Groupbox groupBoxMultiCamara) {
        this.groupBoxMultiCamara = groupBoxMultiCamara;
    }

    @Override
    public void updateMetricValue(String value) {
        getLeftMetricBean().updateMetricValue(value);
    }

    @Override
    public Label getLblValorMetrica() {
        return lblValorMetrica;
    }

    @Override
    public void setTimeButtonsDisabledState(boolean state) {
        getBtnAsignarFinal().setDisabled(state);
        getBtnAsignarInicial().setDisabled(state);
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
     * @return the imgFinal
     */
    public Image getImgFinal() {
        return imgFinal;
    }

    /**
     * @param imgFinal the imgFinal to set
     */
    public void setImgFinal(Image imgFinal) {
        this.imgFinal = imgFinal;
    }

    /**
     * @return the imgInicial
     */
    public Image getImgInicial() {
        return imgInicial;
    }

    /**
     * @param imgInicial the imgInicial to set
     */
    public void setImgInicial(Image imgInicial) {
        this.imgInicial = imgInicial;
    }

    /**
     * @return the btnAsignarInicial
     */
    public Button getBtnAsignarInicial() {
        return btnAsignarInicial;
    }

    /**
     * @param btnAsignarInicial the btnAsignarInicial to set
     */
    public void setBtnAsignarInicial(Button btnAsignarInicial) {
        this.btnAsignarInicial = btnAsignarInicial;
    }

    /**
     * @return the btnAsignarFinal
     */
    public Button getBtnAsignarFinal() {
        return btnAsignarFinal;
    }

    /**
     * @param btnAsignarFinal the btnAsignarFinal to set
     */
    public void setBtnAsignarFinal(Button btnAsignarFinal) {
        this.btnAsignarFinal = btnAsignarFinal;
    }

    /**
     * @param lblValorMetrica the lblValorMetrica to set
     */
    public void setLblValorMetrica(Label lblValorMetrica) {
        this.lblValorMetrica = lblValorMetrica;
    }

    /**
     * @return the evidenceURL
     */
    public String getEvidenceURL() {
        return evidenceURL;
    }

    /**
     * @param evidenceURL the evidenceURL to set
     */
    public void setEvidenceURL(String evidenceURL) {
        this.evidenceURL = evidenceURL;
    }

    /**
     * @return the lblTFinalMenor
     */
    public Label getLblTFinalMenor() {
        return lblTFinalMenor;
    }

    /**
     * @param lblTFinalMenor the lblTFinalMenor to set
     */
    public void setLblTFinalMenor(Label lblTFinalMenor) {
        this.lblTFinalMenor = lblTFinalMenor;
    }

    /**
     * @return the lblTiempoFinal
     */
    public Label getLblTiempoFinal() {
        return lblTiempoFinal;
    }

    /**
     * @param lblTiempoFinal the lblTiempoFinal to set
     */
    public void setLblTiempoFinal(Label lblTiempoFinal) {
        this.lblTiempoFinal = lblTiempoFinal;
    }

    /**
     * @return the lblTiempoInicial
     */
    public Label getLblTiempoInicial() {
        return lblTiempoInicial;
    }

    /**
     * @param lblTiempoInicial the lblTiempoInicial to set
     */
    public void setLblTiempoInicial(Label lblTiempoInicial) {
        this.lblTiempoInicial = lblTiempoInicial;
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
     * @return the templatePath
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * @param templatePath the templatePath to set
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
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
     * @return the btnNextCamara
     */
    public Button getBtnNextCamara() {
        return btnNextCamara;
    }

    /**
     * @param btnNextCamara the btnNextCamara to set
     */
    public void setBtnNextCamara(Button btnNextCamara) {
        this.btnNextCamara = btnNextCamara;
    }

    @Override
    public void setAllCounting(boolean allCounting) {

    }

    /**
     * @return the currentCameraId
     */
    @Override
    public int getCurrentCameraId() {
        return currentCameraId;
    }

    /**
     * @param currentCameraId the currentCameraId to set
     */
    public void setCurrentCameraId(int currentCameraId) {
        this.currentCameraId = currentCameraId;
    }

    /**
     * @return the btnEnviarFinalizar
     */
    @Override
    public Button getBtnEnviarFinalizar() {
        return btnEnviarFinalizar;
    }

    /**
     * @param btnEnviarFinalizar the btnEnviarFinalizar to set
     */
    public void setBtnEnviarFinalizar(Button btnEnviarFinalizar) {
        this.btnEnviarFinalizar = btnEnviarFinalizar;
    }

    /**
     * @return the allEvaluated
     */
    public boolean isAllEvaluated() {
        return allEvaluated;
    }

    /**
     * @param allEvaluated the allEvaluated to set
     */
    @Override
    public void setAllEvaluated(boolean allEvaluated) {
        this.allEvaluated = allEvaluated;
    }

    @Override
    public void setNumberInputBoxState(boolean state) {

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
     * @return the lblCurrentTIme
     */
    public Label getLblCurrentTIme() {
        return lblCurrentTIme;
    }

    /**
     * @param lblCurrentTIme the lblCurrentTIme to set
     */
    public void setLblCurrentTIme(Label lblCurrentTIme) {
        this.lblCurrentTIme = lblCurrentTIme;
    }

    /**
     * @return the iniSquaresInfo
     */
    public HashMap<Integer, String> getIniSquaresInfo() {
        return iniSquaresInfo;
    }

    /**
     * @param iniSquaresInfo the iniSquaresInfo to set
     */
    public void setIniSquaresInfo(HashMap<Integer, String> iniSquaresInfo) {
        this.iniSquaresInfo = iniSquaresInfo;
    }

    /**
     * @return the finSquaresInfo
     */
    public HashMap<Integer, String> getFinSquaresInfo() {
        return finSquaresInfo;
    }

    /**
     * @param finSquaresInfo the finSquaresInfo to set
     */
    public void setFinSquaresInfo(HashMap<Integer, String> finSquaresInfo) {
        this.finSquaresInfo = finSquaresInfo;
    }

    /**
     * @return the lblIniSquares
     */
    public Label getLblIniSquares() {
        return lblIniSquares;
    }

    /**
     * @param lblIniSquares the lblIniSquares to set
     */
    public void setLblIniSquares(Label lblIniSquares) {
        this.lblIniSquares = lblIniSquares;
    }

    /**
     * @return the lblFinSquares
     */
    public Label getLblFinSquares() {
        return lblFinSquares;
    }

    /**
     * @param lblFinSquares the lblFinSquares to set
     */
    public void setLblFinSquares(Label lblFinSquares) {
        this.lblFinSquares = lblFinSquares;
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
    public void setOperatorImagesIp(String operatorImagesIp) {
        this.operatorImagesIp = operatorImagesIp;
    }
}