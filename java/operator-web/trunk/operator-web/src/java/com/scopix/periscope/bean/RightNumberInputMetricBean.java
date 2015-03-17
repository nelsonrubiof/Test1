package com.scopix.periscope.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

import com.scopix.periscope.common.ShowMessage;
import com.scopix.periscope.common.UserCredentialManager;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.interfaces.CenterEvidence;
import com.scopix.periscope.interfaces.LeftMetricList;
import com.scopix.periscope.interfaces.RightMetricDetail;
import com.scopix.periscope.model.Camara;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.Metric;

/**
 * Clase encargada del manejo de la métrica de entrada directa en la parte derecha de la pantalla de evaluación
 * 
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class RightNumberInputMetricBean extends GenericForwardComposer implements Serializable, RightMetricDetail {

    private String login;
    private Metric metric;
    private Intbox iboxValor;
    private Session mySession;
    private String evidenceURL;
    private Label lblNomMetrica;
    private String templatePath;
    private String evidenceType;
    private Label lblCurrentTIme;
    private String playerPosition;
    private Label lblValorMetrica;
    private Button btnNextCamara;
    private boolean allEvaluated;
    private String operatorImagesIp;
    private ShowMessage showMessage;
    private Combobox cmbMultiCamaras;
    private int currentCameraId = -1;
    private Button btnEnviarFinalizar;
    private Groupbox groupBoxMultiCamara;
    private CenterEvidence centerEvidence;
    private LeftMetricList leftMetricBean;
    private UserCredentialManager userCredentialManager;
    private List<Camara> lstCamaras = new ArrayList<Camara>();
    private static final long serialVersionUID = 56879593814535068L;
    private static Logger log = Logger.getLogger(RightNumberInputMetricBean.class);
    private HashMap<Integer, String> numberInputEval = new HashMap<Integer, String>();

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
                getLblNomMetrica().setValue(getMetric().getName() + " : ");
                getLblValorMetrica().setAttribute("org.zkoss.zk.ui.updateByClient", true);
                getLblCurrentTIme().setAttribute("org.zkoss.zk.ui.updateByClient", true);

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
                // Establece variables de conteo como datos de círculos, marcas
                // url de la evidencia y creación de la parte central de la pantalla
                prepareNumberInput(lstEvidencias);
            }
        } catch (Exception e) {
            log.error(e.getMessage() + " - " + login, e);
        }
        log.info("end - " + login);
    }

    /**
     * Establece variables de evaluación url de la evidencia y creación de la parte central de la pantalla
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param lstEvidencias lista de evidencias
     * @since 6.0
     * @date 21/02/2013
     */
    protected void prepareNumberInput(List<Evidence> lstEvidencias) {
        log.info("start - " + login);
        if (getMetric().getNumberInputEval() != null) {
            setNumberInputEval(getMetric().getNumberInputEval());
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
     * Lógica para hacer transición entre métricas
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
        metrica.setDescription(valorMetrica);

        if (getEvidenceType().equalsIgnoreCase(EnumEvidenceType.VIDEO.toString())) {
            HashMap<Integer, String> tiempoMarcas = metrica.getTiempoMarcas();
            if (tiempoMarcas != null && getCenterEvidence().getLblTiempoMarcas() != null) {
                tiempoMarcas.put(getCurrentCameraId(), getCenterEvidence().getLblTiempoMarcas().getValue());
                metrica.setTiempoMarcas(tiempoMarcas);
            }
        }

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

        try {
            if (!valorMetrica.equalsIgnoreCase(Labels.getLabel("eval.noDefinido"))) {
                Integer.valueOf(valorMetrica);
                evaluada = true;
            } else {
                evaluada = false;
            }
        } catch (NumberFormatException ex) {
            if (getMetric().isCantEval()) {
                evaluada = true;
            } else {
                evaluada = false;
            }
        }
        values.add(evaluada);
        values.add(valorMetrica);

        log.debug("metricId: " + getMetric().getMetricId() + ", evaluada: " + evaluada + ", valorMetrica: " + valorMetrica
                + " - " + login);
        log.info("end - " + login);
        return values;
    }

    /**
     * Valida que al menos una cámara haya sido evaluada
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
            for (int i = 0; i < getLstCamaras().size(); i++) {
                Camara camara = getLstCamaras().get(i);
                String cameraNumberInput = getNumberInputEval().get(camara.getId());

                if (cameraNumberInput != null && !cameraNumberInput.isEmpty()) {
                    isValid = true;
                    break;
                }
            }
        } else {
            for (Map.Entry e : getNumberInputEval().entrySet()) {
                int cameraId = Integer.valueOf(e.getKey().toString());

                String cameraNumberInput = getNumberInputEval().get(cameraId);
                if (cameraNumberInput != null && !cameraNumberInput.trim().equals("")) {
                    isValid = true;
                    break;
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
        setCurrentCamera(selectedIndex);

        int selectedCameraId = getLstCamaras().get(selectedIndex).getId();
        setCurrentCameraId(selectedCameraId);
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
     * @param event
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

        String currentTime = getLblCurrentTIme().getValue();

        if (isCambioCamara) {
            getMetric().setCurrentTime(currentTime);
        } else {
            getMetric().setCurrentTime(null);
        }

        getMetric().setCurrentCameraId(selectedCameraId);
        getMetric().setNumberInputEval(getNumberInputEval());

        int numMetrica = Integer.valueOf(getMetric().getNumMetrica());

        Metric metricaLeftModel = (Metric) getLeftMetricBean().getMetricasModel().get(numMetrica);
        metricaLeftModel.setCirclesInfo(getMetric().getCirclesInfo());
        metricaLeftModel.setSquaresInfo(getMetric().getSquaresInfo());
        metricaLeftModel.setCurrentTime(getMetric().getCurrentTime());
        metricaLeftModel.setCurrentCameraId(getMetric().getCurrentCameraId());
        metricaLeftModel.setNumberInputEval(getNumberInputEval());

        getLeftMetricBean().getMetricasModel().set(numMetrica, metricaLeftModel);

        setCurrentCameraId(selectedCameraId);
        log.info("end - " + login);
    }

    @Override
    public void updateEvalData() {

    }

    /**
     * Lógica para enviar y finalizar una evaluación
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

    /**
     * Actualiza valor de la métrica al cambiar el valor en el intbox
     * 
     * @author carlos polo
     * @version 1.0.0
     * @param event evento generado en el click
     * @since 6.0
     * @date 08/03/2013
     */
    public void onChangingValor(ForwardEvent event) {
        String value = getLblValorMetrica().getValue();
        log.info("start, value: [" + value + "] - " + login);

        if (value != null && !value.trim().equals("")) {
            try {
                int intValue = Integer.parseInt(value);
                if (intValue >= 0) {
                    getNumberInputEval().clear();
                    getNumberInputEval().put(getCurrentCameraId(), value);

                    if (getMetric().getTiempoMarcas() != null) {
                        getMetric().getTiempoMarcas().put(getCurrentCameraId(), getPlayerPosition());
                    }

                    updateMetricValue(value);
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage() + " - " + login, e);
            }
        }
        log.info("end - " + login);
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
    }

    /**
     * @param lblValorMetrica the lblValorMetrica to set
     */
    public void setLblValorMetrica(Label lblValorMetrica) {
        this.lblValorMetrica = lblValorMetrica;
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
     * @return the iboxValor
     */
    public Intbox getIboxValor() {
        return iboxValor;
    }

    /**
     * @param iboxValor the iboxValor to set
     */
    public void setIboxValor(Intbox iboxValor) {
        this.iboxValor = iboxValor;
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
    public void getShapesData() {

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
     * @return the lblNomMetrica
     */
    public Label getLblNomMetrica() {
        return lblNomMetrica;
    }

    /**
     * @param lblNomMetrica the lblNomMetrica to set
     */
    public void setLblNomMetrica(Label lblNomMetrica) {
        this.lblNomMetrica = lblNomMetrica;
    }

    @Override
    public void setNumberInputBoxState(boolean state) {
        getIboxValor().setDisabled(state);
    }

    /**
     * @return the numberInputEval
     */
    public HashMap<Integer, String> getNumberInputEval() {
        return numberInputEval;
    }

    /**
     * @param numberInputEval the numberInputEval to set
     */
    public void setNumberInputEval(HashMap<Integer, String> numberInputEval) {
        this.numberInputEval = numberInputEval;
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