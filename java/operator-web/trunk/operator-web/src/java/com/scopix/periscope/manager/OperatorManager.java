package com.scopix.periscope.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FilenameUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.zkoss.zk.ui.Session;

import com.scopix.periscope.command.GetNextEvidenceCommand;
import com.scopix.periscope.command.GetQueuesCommand;
import com.scopix.periscope.command.SendEvaluationCommand;
import com.scopix.periscope.common.WebServicesUtil;
import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.model.Client;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Proof;
import com.scopix.periscope.model.Queue;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.model.UserEvaluation;
import com.scopix.periscope.operatorimages.MarksContainerDTO;
import com.scopix.periscope.operatorimages.MarksDTO;
import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.operatorimages.ResultMarksDTO;
import com.scopix.periscope.operatorimages.ShapesDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.transport.http.HTTPConduit;

/**
 * Manager para la invocación de servicios relacionados con la obtención de situaciones y clientes
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SpringBean(rootClass = OperatorManager.class)
@SuppressWarnings("rawtypes")
public class OperatorManager implements Serializable, InitializingBean {

    private GetQueuesCommand queuesCommand;
    private Map<String, Class> queuesWebServices;
    private PropertiesConfiguration systConfiguration;
    private SendEvaluationCommand sendEvaluationCommand;
    private HashMap<Integer, Situation> hmEvalSituations;
    private GetNextEvidenceCommand getNextEvidenceCommand;
    private HashMap<Long, UserEvaluation> hmUsersEvaluations;
    private static final long serialVersionUID = 8658472513677068483L;
    private static Logger log = Logger.getLogger(OperatorManager.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        // cargamos el system.properties
        getSystConfiguration();
    }

    /**
     * Obtiene siguiente evidencia del web service
     *
     * @author carlos polo
     * @version 1.0.0
     * @param queueName nombre de la cola
     * @param corporateName nombre del cliente seleccionado
     * @param sessionId sessionId
     * @param login login del usuario autenticado
     * @return SituationSendDTO objeto retornado bien sea por el web service o de manera simulada
     * @since 6.0
     * @date 19/02/2013
     * @throws ScopixException excepción en la obtención de la siguiente situación.
     */
    public Situation getNextEvidence(String queueName, String corporateName, Long sessionId, String login) throws ScopixException {
        log.info("start, preparandose para obtener siguiente situacion - " + login);
        // Invoca servicio para solicitar siguiente situación
        Situation situation = getGetNextEvidenceCommand().execute(queueName, corporateName, sessionId, login);
        log.info("end - " + login);
        return situation;
    }

    /**
     * Invocación servicio para enviar una evaluación al core
     *
     * @author carlos polo
     * @version 1.0.0
     * @param situationId situación por enviar
     * @since 6.0
     * @return boolean true exitoso false falla
     * @throws ScopixException excepción durante el envío de la evaluación
     * @date 26/04/2013
     */
    public boolean sendEvaluation(Integer situationId, int retryNumber) throws ScopixException {
        log.info("start, situationId: [" + situationId + "], retryNumber: [" + retryNumber + "]");
        String login = null;
        boolean exito = false;

        try {
            Situation situation = getHmEvalSituations().get(situationId);

            if (situation != null) {
                login = situation.getEvaluationLogin();
                Long sessionId = situation.getSessionId();
                Client cliente = situation.getCliente();
                // Convierte objeto retornado por el modelo web (situation) a una lista de
                // objetos del modelo del servicio para envío de la evaluación
                log.debug("invocando transformacion de la situacion a DTO, situationId: [" + situationId + "] - " + login);
                List<EvidenceEvaluationDTO> lstEvidenceEvaluationDTO = WebServicesUtil.toSituationDTO(situation, login);

                log.debug("invocando servicio para enviar evaluacion, situationId: [" + situationId + "] - " + login);
                // Invoca servicio para enviar la evaluación y actualización del core
                getSendEvaluationCommand().execute(lstEvidenceEvaluationDTO, cliente.getName(), sessionId, login);
                // removiendo situación del hashmap
                removeSituationFromHash(situationId);
                exito = true;
            }
        } catch (ScopixException e) {
            log.error(e.getMessage() + " - " + login, e);
            if (retryNumber > 0) {
                retryNumber--;
                log.debug("reintentando envio al core de situationId: [" + situationId + "]");
                sendEvaluation(situationId, retryNumber);
            }
        }
        log.info("end, exito: " + exito + " - " + login);
        return exito;
    }

    /**
     * Genera todos los proofs de la evaluación por enviar
     *
     * @author carlos polo
     * @version 1.0.0
     * @param situation situación a enviar
     * @param cliente cliente evaluado
     * @param snapshotTime tiempo para generar snapshot
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @since 6.0
     * @return true si el proceso es exitoso
     * @throws ScopixException excepción durante la generación de proofs
     * @date 26/04/2013
     */
    public boolean generateProofs(Situation situation, Session mySession) throws ScopixException {

        log.info("start");
        String login = situation.getEvaluationLogin();
        String snapshotTime = situation.getSnapshotTime();
        Client cliente = situation.getCliente();

        // Recorre las métricas de la situación y solicita la creación para cada métrica con el file de la evidencia
        List<Metric> lstMetricas = situation.getMetricas();
        String evidenceDate = situation.getEvidenceDateTime();

        log.debug("snapshotTime: " + snapshotTime + " - " + login);
        List<MarksDTO> lstMarks = new ArrayList<MarksDTO>();

        for (Metric metrica : lstMetricas) {
            log.debug("generando proof para metricId: " + metrica.getMetricId() + " - " + login);
            lstMarks.addAll(generateMetricProofs(situation, cliente, metrica, evidenceDate, snapshotTime, login, mySession));
        }

        // Ej, http://64.151.117.136:8080/operator-images-services/services/REST/
        //WebClient serviceClient = WebClient.create(cliente.getOperatorImgServicesURL());
        //se cambia para utilizar una ip o url privada
        WebClient serviceClient = WebClient.create(cliente.getOperatorImgPrivateServicesURL());
        //new code
        HTTPConduit conduit = WebClient.getConfig(serviceClient).getHttpConduit();
        TLSClientParameters params
                = conduit.getTlsClientParameters();

        if (params == null) {
            params = new TLSClientParameters();
            conduit.setTlsClientParameters(params);
        }

        params.setTrustManagers(new TrustManager[]{new X509TrustManager() {

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }
        });

        params.setDisableCNCheck(true);
        // end new code
        serviceClient.path("/generateAsynchronousProofs").accept(MediaType.APPLICATION_JSON_TYPE)
                .type(MediaType.APPLICATION_JSON_TYPE);

        MarksContainerDTO marksContainer = new MarksContainerDTO();
        marksContainer.setMarks(lstMarks);
        marksContainer.setSituationId(situation.getId());
        marksContainer.setSessionId(situation.getSessionId());

        try {
            // Invoca servicio
            log.debug("invocando generacion asincrona de proofs en el servidor, sessionId: [" + situation.getSessionId() + "] - "
                    + login);
            serviceClient.post(marksContainer, ResultMarksContainerDTO.class);
            log.debug("invocacion asincrona finalizada - " + login);

        } catch (Exception e) {
            if (e instanceof ScopixException) {
                throw ((ScopixException) e);
            } else {
                throw new ScopixException(e.getMessage(), e);
            }
        }

        // almacena en memoria la evaluación enviada para el correspondiente callback
        getHmEvalSituations().put(situation.getId(), situation);
        log.info("end - " + login);
        return true;
    }

    /**
     * Procesamiento de los nombres de proof generados
     *
     * @author carlos polo
     * @version 1.0.0
     * @param container respuesta del servicio
     * @param login login del usuario autenticado
     * @param situation situación evaluada
     * @since 6.0
     * @date 26/04/2013
     */
    public void processProofsNames(ResultMarksContainerDTO container) {
        log.info("start");
        String login = null;

        if (container != null) {
            List<ResultMarksDTO> resultMarks = container.getResults();

            if (resultMarks != null && !resultMarks.isEmpty()) {
                Integer situationId = container.getSituationId();
                log.debug("situationId: [" + situationId + "]");
                Situation situation = getHmEvalSituations().get(situationId);

                if (situation != null) {
                    List<Metric> lstMetricas = situation.getMetricas();
                    login = situation.getEvaluationLogin();

                    log.debug("situationId: [" + situationId
                            + "], estableciendo nombres de proofs de acuerdo a lo retornado por el servicio - " + login);

                    for (Metric metrica : lstMetricas) {
                        for (Proof proof : metrica.getProofs()) {
                            for (ResultMarksDTO resultMark : resultMarks) {
                                if (proof.getMetricId().equals(resultMark.getMetricId())
                                        && proof.getEvidenceId().equals(resultMark.getEvidenceId())) {

                                    if (resultMark.getProcessed().equalsIgnoreCase("N")) {
                                        String proofName = resultMark.getFileName();
                                        log.debug("proofName: " + proofName);
                                        proof.setPathWithOutMarks("proofs\\" + proofName);
                                        proof.setPathWithMarks("proofs_with_marks\\" + proofName);

                                        resultMark.setProcessed("S");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    getHmEvalSituations().put(situationId, situation);
                }
            }
        }
        log.info("end - " + login);
    }

    /**
     * Genera proofs en particular para una métrica
     *
     * @author carlos polo
     * @version 1.0.0
     * @param situation situación evaluada
     * @param cliente cliente evaluado
     * @param metrica métrica evaluada
     * @param evidenceDate fecha de la evidencia
     * @param snapshotTime tiempo para generar snapshot
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @return List<Marks> lista de marcas
     * @since 6.0
     * @throws ScopixException excepción durante la generación de proofs
     * @date 26/04/2013
     */
    protected List<MarksDTO> generateMetricProofs(Situation situation, Client cliente, Metric metrica, String evidenceDate,
            String snapshotTime, String login, Session mySession) throws ScopixException {

        log.info("start - " + login);
        boolean generaProofs = true;
        List<MarksDTO> lstMarks = null;

        try {
            Integer evidenceEvalDTOId = metrica.getEvidenceEvaluationDTOId();
            if (evidenceEvalDTOId != null && !metrica.isChanged()) {
                // la métrica fue evaluada previamente de forma automática y no la re-evaluaron/cambiado manualmente
                // por lo tanto no hay necesidad de volver a generarle proofs
                generaProofs = false;
                // para no generar nullPointerException en el addAll en donde se invoca este método
                lstMarks = new ArrayList<MarksDTO>();
            }

            log.debug("generaProofs: [" + generaProofs + "], metricId: [" + metrica.getMetricId() + "], "
                    + "metric description: [" + metrica.getDescription() + "], evidenceEvalDTOId: [" + evidenceEvalDTOId + "] - "
                    + login);
            if (generaProofs) {
                String metricType = metrica.getType();

                if (metricType.equalsIgnoreCase(EnumEvaluationType.COUNTING.toString())) {
                    lstMarks = generateCountingProofs(situation, cliente, metrica, evidenceDate, snapshotTime, login, mySession);
                } else if (metricType.equalsIgnoreCase(EnumEvaluationType.YES_NO.toString())) {
                    lstMarks = generateYesNoProofs(situation, cliente, metrica, evidenceDate, login, mySession);
                } else if (metricType.equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                    lstMarks = generateNumberInputProofs(situation, cliente, metrica, evidenceDate, login, mySession);
                } else {
                    // MEASURE_TIME
                    lstMarks = generateMeasureTimeProofs(situation, cliente, metrica, evidenceDate, login, mySession);
                }
            }
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end - " + login);
        return lstMarks;
    }

    /**
     * Genera proofs para una métrica de COUNTING
     *
     * @author carlos polo
     * @version 2.0.0
     * @param situation situación evaluada
     * @param cliente cliente evaluado
     * @param metrica métrica evaluada
     * @param evidenceDate fecha de la evidencia
     * @param snapshotTime tiempo para generar snapshot
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @return List<Marks> lista de marcas
     * @since 6.0
     * @throws ScopixException excepción durante la generación de proofs
     * @date 03/12/2013
     */
    protected List<MarksDTO> generateCountingProofs(Situation situation, Client cliente, Metric metrica, String evidenceDate,
            String snapshotTime, String login, Session mySession) throws ScopixException {

        Integer metricId = metrica.getMetricId();
        String metricName = metrica.getName();
        log.info("start, generating proof for, CLIENT: [" + cliente.getName() + "], STORE: [" + situation.getStoreName() + "], "
                + "AREA: [" + situation.getArea() + "], SITUATION_ID: [" + situation.getId() + "], " + "METRIC_ID: [" + metricId
                + "] - " + login);

        List<MarksDTO> lstMarks = new ArrayList<MarksDTO>();

        String squareColorCode = null;
        List<Evidence> lstEvidencias = metrica.getEvidencias();

        for (Evidence evidencia : lstEvidencias) {
            Proof proof = new Proof();
            MarksDTO markDTO = new MarksDTO();

            markDTO.setCorporateName(cliente.getName());
            markDTO.setStoreName(situation.getStoreName());
            markDTO.setAreaName(situation.getArea());
            markDTO.setSituationId(Integer.valueOf(situation.getId()));

            Integer cameraId = evidencia.getEvidenceProvider().getId();
            String cameraName = evidencia.getEvidenceProvider().getDescripcion();
            markDTO.setCameraId(cameraId);
            markDTO.setCameraName(cameraName);

            proof.setMetricId(metrica.getMetricId());
            markDTO.setEvidenceId(evidencia.getEvidenceId());

            log.debug("evidenceDate from situationSendDTO: [" + evidenceDate + "], cameraId: [" + cameraId + "], "
                    + "cameraName: [" + cameraName + "], metricId: [" + metricId + "], metricName: [" + metricName + "] - "
                    + login);

            markDTO.setEvidenceDate(evidenceDate);
            markDTO.setMetricId(metrica.getMetricId());
            markDTO.setMetricName(metricName);

            // Información por cámara de las marcas realizadas
            HashMap<Integer, String> circlesInfo = metrica.getCirclesInfo();
            HashMap<Integer, String> squaresInfo = metrica.getSquaresInfo();

            squareColorCode = "FFAADD00"; // verde

            // Procesa los círculos (en caso de existir) para asignarlos al brand
            processMarkCircles(markDTO, circlesInfo, cameraId, login);
            // Procesa las marcas (en caso de existir) para asignarlas al brand
            processMarkSquares(markDTO, squaresInfo, cameraId, squareColorCode, login);
            // Procesa los paths orígen y destino para asignarlos al objeto brand
            processMarkPaths(markDTO, evidencia, cliente, login, mySession);

            if (snapshotTime != null && !snapshotTime.trim().equals("")) {
                markDTO.setElapsedTime(new Double(snapshotTime));
            }
            markDTO.setWithNumber(Boolean.TRUE);

            if (metrica.isCantEval()) {
                // No se pudo evaluar la métrica
                log.debug("no se generan proofs en el servidor para metricId: " + metricId + " porque se "
                        + "definio que la metrica no se puede evaluar, motivo: [" + metrica.getDescription() + "] - " + login);
            } else {
                int result = Integer.parseInt(metrica.getDescription());

                if (metrica.isMultiple()) { // es multicámara
                    String circlesData = circlesInfo.get(cameraId);

                    if (circlesData != null && !circlesData.trim().equals("") && !circlesData.trim().equals("0")) {
                        String[] circlesArr = circlesData.split("#");
                        result = circlesArr.length;
                    }
                }

                proof.setResult(result);
                markDTO.setResult(result);
                lstMarks.add(markDTO);
            }
            proof.setEvidenceId(evidencia.getEvidenceId());
            proof.setPathWithOutMarks("proofs\\");
            proof.setPathWithMarks("proofs_with_marks\\");

            proof.setMarkDTO(markDTO); // relaciona proof con markdto
            metrica.getProofs().add(proof);
        } // fin for
        log.info("end");
        return lstMarks;
    }

    /**
     * Genera proofs para una métrica de YES/NO
     *
     * @author carlos polo
     * @version 2.0.0
     * @param situation situación evaluada
     * @param cliente cliente evaluado
     * @param metrica métrica evaluada
     * @param evidenceDate fecha de la evidencia
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @return List<Marks> lista de marcas
     * @since 6.0
     * @throws ScopixException excepción durante la generación de proofs
     * @date 03/12/2013
     */
    protected List<MarksDTO> generateYesNoProofs(Situation situation, Client cliente, Metric metrica, String evidenceDate,
            String login, Session mySession) throws ScopixException {

        Integer metricId = metrica.getMetricId();
        String metricName = metrica.getName();
        log.info("start, generating proof for, CLIENT: [" + cliente.getName() + "], STORE: [" + situation.getStoreName() + "], "
                + "AREA: [" + situation.getArea() + "], SITUATION_ID: [" + situation.getId() + "], " + "METRIC_ID: [" + metricId
                + "] - " + login);

        List<MarksDTO> lstMarks = new ArrayList<MarksDTO>();

        int markCameraId = -1;
        String squareColorCode = null;
        List<Evidence> lstEvidencias = metrica.getEvidencias();
        // Hashmaps que tendrán datos de puntos o marcas
        HashMap<Integer, String> genericSquaresInfo = new HashMap<Integer, String>();

        // Información por cámara de las marcas realizadas
        HashMap<Integer, String> yesSquaresInfo = metrica.getYesSquaresInfo();
        HashMap<Integer, String> noSquaresInfo = metrica.getNoSquaresInfo();
        // Tiempo en que se realizaron las marcas <idCamara,tiempo>
        HashMap<Integer, String> tiempoMarcas = metrica.getTiempoMarcas();

        if (!yesSquaresInfo.isEmpty() || !noSquaresInfo.isEmpty()) { // YES/NO
            if (validateEmptyHashmap(yesSquaresInfo)) {
                // Sí está vacío el hash de "yes", así que se marcó "no".
                genericSquaresInfo = noSquaresInfo;
                squareColorCode = "FFFF0000"; // rojo
            } else {
                // Sí está vacío el hash de "no", así que se marcó "yes".
                genericSquaresInfo = yesSquaresInfo;
                squareColorCode = "FFAADD00"; // verde
            }
        }

        String snapshotTime = processSnapshotTime(tiempoMarcas);

        for (Map.Entry e : genericSquaresInfo.entrySet()) {
            markCameraId = Integer.valueOf(e.getKey().toString());
        }

        for (Evidence evidencia : lstEvidencias) {
            Integer cameraId = evidencia.getEvidenceProvider().getId();
            if (markCameraId != cameraId) {
                continue;
            }

            Proof proof = new Proof();
            MarksDTO markDTO = new MarksDTO();

            markDTO.setCorporateName(cliente.getName());
            markDTO.setStoreName(situation.getStoreName());
            markDTO.setAreaName(situation.getArea());
            markDTO.setSituationId(Integer.valueOf(situation.getId()));

            String cameraName = evidencia.getEvidenceProvider().getDescripcion();
            markDTO.setCameraId(cameraId);
            markDTO.setCameraName(cameraName);

            proof.setMetricId(metrica.getMetricId());
            markDTO.setEvidenceId(evidencia.getEvidenceId());

            log.debug("evidenceDate from situationSendDTO: [" + evidenceDate + "], cameraId: [" + cameraId + "], "
                    + "cameraName: [" + cameraName + "], metricId: [" + metricId + "], metricName: [" + metricName + "] - "
                    + login);

            markDTO.setEvidenceDate(evidenceDate);
            markDTO.setMetricId(metrica.getMetricId());
            markDTO.setMetricName(metricName);

            // Procesa las marcas (en caso de existir) para asignarlas al brand
            processMarkSquares(markDTO, genericSquaresInfo, cameraId, squareColorCode, login);
            // Procesa los paths orígen y destino para asignarlos al objeto brand
            processMarkPaths(markDTO, evidencia, cliente, login, mySession);

            if (snapshotTime != null && !snapshotTime.trim().equals("")) {
                markDTO.setElapsedTime(new Double(snapshotTime));
            }
            markDTO.setWithNumber(Boolean.FALSE);

            if (metrica.isCantEval()) {
                // No se pudo evaluar la métrica
                log.debug("no se generan proofs en el servidor para metricId: " + metricId + " porque se "
                        + "definio que la metrica no se puede evaluar, motivo: [" + metrica.getDescription() + "] - " + login);
            } else {
                int result = -1;
                String descripcion = metrica.getDescription();

                if (descripcion.equalsIgnoreCase("S")) {
                    result = 1;
                } else if (descripcion.equalsIgnoreCase("N")) {
                    result = 0;
                }

                markDTO.setResult(result);
                lstMarks.add(markDTO);
            }
            proof.setEvidenceId(evidencia.getEvidenceId());
            proof.setPathWithOutMarks("proofs\\");
            proof.setPathWithMarks("proofs_with_marks\\");
            proof.setMarkDTO(markDTO); // relaciona proof con markdto

            metrica.getProofs().add(proof);
        } // fin for

        return lstMarks;
    }

    /**
     * Genera proofs para una métrica de NUMBER_INPUT
     *
     * @author carlos polo
     * @version 2.0.0
     * @param situation situación evaluada
     * @param cliente cliente evaluado
     * @param metrica métrica evaluada
     * @param evidenceDate fecha de la evidencia
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @return List<Marks> lista de marcas
     * @since 6.0
     * @throws ScopixException excepción durante la generación de proofs
     * @date 03/12/2013
     */
    protected List<MarksDTO> generateNumberInputProofs(Situation situation, Client cliente, Metric metrica, String evidenceDate,
            String login, Session mySession) throws ScopixException {

        Integer metricId = metrica.getMetricId();
        String metricName = metrica.getName();
        log.info("start, generating proof for, CLIENT: [" + cliente.getName() + "], STORE: [" + situation.getStoreName() + "], "
                + "AREA: [" + situation.getArea() + "], SITUATION_ID: [" + situation.getId() + "], " + "METRIC_ID: [" + metricId
                + "] - " + login);

        List<MarksDTO> lstMarks = new ArrayList<MarksDTO>();

        int markCameraId = -1;
        List<Evidence> lstEvidencias = metrica.getEvidencias();

        // Información por cámara de las marcas realizadas
        HashMap<Integer, String> numberInputEval = metrica.getNumberInputEval();
        // Tiempo en que se realizó la entrada de número
        HashMap<Integer, String> tiempoMarcas = metrica.getTiempoMarcas();

        String snapshotTime = processSnapshotTime(tiempoMarcas);

        for (Map.Entry e : numberInputEval.entrySet()) {
            markCameraId = Integer.valueOf(e.getKey().toString());
        }

        for (Evidence evidencia : lstEvidencias) {
            Integer cameraId = evidencia.getEvidenceProvider().getId();
            if (markCameraId != cameraId) {
                continue;
            }

            Proof proof = new Proof();
            MarksDTO markDTO = new MarksDTO();

            markDTO.setCorporateName(cliente.getName());
            markDTO.setStoreName(situation.getStoreName());
            markDTO.setAreaName(situation.getArea());
            markDTO.setSituationId(Integer.valueOf(situation.getId()));

            String cameraName = evidencia.getEvidenceProvider().getDescripcion();
            markDTO.setCameraId(cameraId);
            markDTO.setCameraName(cameraName);

            proof.setMetricId(metrica.getMetricId());
            markDTO.setEvidenceId(evidencia.getEvidenceId());

            log.debug("evidenceDate from situationSendDTO: [" + evidenceDate + "], cameraId: [" + cameraId + "], "
                    + "cameraName: [" + cameraName + "], metricId: [" + metricId + "], metricName: [" + metricName + "] - "
                    + login);

            markDTO.setEvidenceDate(evidenceDate);
            markDTO.setMetricId(metrica.getMetricId());
            markDTO.setMetricName(metricName);

            // Procesa los paths orígen y destino para asignarlos al objeto brand
            processMarkPaths(markDTO, evidencia, cliente, login, mySession);

            if (snapshotTime != null && !snapshotTime.trim().equals("")) {
                markDTO.setElapsedTime(new Double(snapshotTime));
            }
            markDTO.setWithNumber(Boolean.TRUE);

            if (metrica.isCantEval()) {
                // No se pudo evaluar la métrica
                log.debug("no se generan proofs en el servidor para metricId: " + metricId + " porque se "
                        + "definio que la metrica no se puede evaluar, motivo: [" + metrica.getDescription() + "] - " + login);
            } else {
                int result = Integer.parseInt(metrica.getDescription());
                markDTO.setResult(result);
                lstMarks.add(markDTO);
            }
            proof.setEvidenceId(evidencia.getEvidenceId());
            proof.setPathWithOutMarks("proofs\\");
            proof.setPathWithMarks("proofs_with_marks\\");
            proof.setMarkDTO(markDTO); // relaciona proof con markdto

            metrica.getProofs().add(proof);
        } // fin for

        return lstMarks;
    }

    /**
     * Genera proofs para una métrica de MEASURE_TIME
     *
     * @author carlos polo
     * @version 2.0.0
     * @param situation situación evaluada
     * @param cliente cliente evaluado
     * @param metrica métrica evaluada
     * @param evidenceDate fecha de la evidencia
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @return List<Marks> lista de marcas
     * @since 6.0
     * @throws ScopixException excepción durante la generación de proofs
     * @date 03/12/2013
     */
    protected List<MarksDTO> generateMeasureTimeProofs(Situation situation, Client cliente, Metric metrica, String evidenceDate,
            String login, Session mySession) throws ScopixException {

        Integer metricId = metrica.getMetricId();
        log.info("start, generating proof for, CLIENT: [" + cliente.getName() + "], STORE: [" + situation.getStoreName() + "], "
                + "AREA: [" + situation.getArea() + "], SITUATION_ID: [" + situation.getId() + "], " + "METRIC_ID: [" + metricId
                + "] - " + login);

        List<MarksDTO> lstMarks = new ArrayList<MarksDTO>();

        int markIniCameraId = -1;
        int markFinCameraId = -1;
        List<Evidence> lstEvidencias = metrica.getEvidencias();

        // Información por cámara de las marcas realizadas
        HashMap<Integer, String> iniSquaresInfo = metrica.getIniSquaresInfo();
        HashMap<Integer, String> finSquaresInfo = metrica.getFinSquaresInfo();
        // Tiempo en que se realizaron las marcas <idCamara,tiempo>
        HashMap<Integer, String> tiempoMarcasInicial = metrica.getTiempoMarcasInicial();
        HashMap<Integer, String> tiempoMarcasFinal = metrica.getTiempoMarcasFinal();

        String snapshotTimeIni = processSnapshotTime(tiempoMarcasInicial);
        String snapshotTimeFin = processSnapshotTime(tiempoMarcasFinal);

        metrica.setSnapshotTimeInicial(snapshotTimeIni);
        metrica.setSnapshotTimeFinal(snapshotTimeFin);

        for (Map.Entry e : iniSquaresInfo.entrySet()) {
            markIniCameraId = Integer.valueOf(e.getKey().toString());
        }

        for (Map.Entry e : finSquaresInfo.entrySet()) {
            markFinCameraId = Integer.valueOf(e.getKey().toString());
        }

        for (Evidence evidencia : lstEvidencias) {
            Integer cameraId = evidencia.getEvidenceProvider().getId();
            // No coincide con ninguna de las cámaras en donde se marcaron el tInicial o tFinal
            if (cameraId != markIniCameraId && cameraId != markFinCameraId) {
                continue;
            }

            if (cameraId == markIniCameraId && cameraId != markFinCameraId) {
                lstMarks = subProcessMeasureTimeProofs(situation, cliente, metrica, evidenceDate, snapshotTimeIni, login,
                        mySession, evidencia, lstMarks, iniSquaresInfo, markIniCameraId, true);
            } else if (cameraId == markFinCameraId && cameraId != markIniCameraId) {
                lstMarks = subProcessMeasureTimeProofs(situation, cliente, metrica, evidenceDate, snapshotTimeFin, login,
                        mySession, evidencia, lstMarks, finSquaresInfo, markFinCameraId, false);
            } else {
                // las marcas inicial y final se hicieron para la misma cámara
                lstMarks = subProcessMeasureTimeProofs(situation, cliente, metrica, evidenceDate, snapshotTimeIni, login,
                        mySession, evidencia, lstMarks, iniSquaresInfo, markIniCameraId, true);

                lstMarks = subProcessMeasureTimeProofs(situation, cliente, metrica, evidenceDate, snapshotTimeFin, login,
                        mySession, evidencia, lstMarks, finSquaresInfo, markFinCameraId, false);
            }
        } // fin for

        return lstMarks;
    }

    /**
     * Obtiene tiempo en que se realizaron las marcas para una cámara determinada
     *
     * @param tiempoMarcas hashmap con referencia de cámara y tiempo
     * @return tiempo en que se realizaron las marcas para una cámara determinada
     */
    protected String processSnapshotTime(HashMap<Integer, String> tiempoMarcas) {
        String snapshotTime = null;
        for (Map.Entry e : tiempoMarcas.entrySet()) {
            int idCamaraTiempo = Integer.valueOf(e.getKey().toString());
            snapshotTime = tiempoMarcas.get(idCamaraTiempo);
        }

        if (snapshotTime != null && snapshotTime.length() > 2) {
            String text = snapshotTime.substring(snapshotTime.length() - 2, snapshotTime.length());
            if (text.equals("PL") || text.equals("PA")) {
                snapshotTime = snapshotTime.substring(0, snapshotTime.length() - 2);
            }
        }

        return snapshotTime;
    }

    /**
     * Procesa las marcas de tiempo inicial o final
     *
     * @param situation
     * @param cliente
     * @param metrica
     * @param evidenceDate
     * @param snapshotTime
     * @param login
     * @param mySession
     * @param evidencia
     * @param lstMarks
     * @param squaresInfo
     * @param cameraId
     * @param isInicial
     * @return lista de marcas
     */
    protected List<MarksDTO> subProcessMeasureTimeProofs(Situation situation, Client cliente, Metric metrica,
            String evidenceDate, String snapshotTime, String login, Session mySession, Evidence evidencia,
            List<MarksDTO> lstMarks, HashMap<Integer, String> squaresInfo, int cameraId, boolean isInicial) {

        log.info("start, snapshotTime: [" + snapshotTime + "] - " + login);
        Proof proof = new Proof();
        MarksDTO markDTO = new MarksDTO();
        String metricName = metrica.getName();
        Integer metricId = metrica.getMetricId();

        markDTO.setCorporateName(cliente.getName());
        markDTO.setStoreName(situation.getStoreName());
        markDTO.setAreaName(situation.getArea());
        markDTO.setSituationId(Integer.valueOf(situation.getId()));

        String cameraName = evidencia.getEvidenceProvider().getDescripcion();
        markDTO.setCameraId(cameraId);
        markDTO.setCameraName(cameraName);

        proof.setMetricId(metrica.getMetricId());
        markDTO.setEvidenceId(evidencia.getEvidenceId());

        log.debug("evidenceDate from situationSendDTO: [" + evidenceDate + "], cameraId: [" + cameraId + "], " + "cameraName: ["
                + cameraName + "], metricId: [" + metricId + "], metricName: [" + metricName + "] - " + login);

        markDTO.setEvidenceDate(evidenceDate);
        markDTO.setMetricId(metrica.getMetricId());
        markDTO.setMetricName(metricName);

        String squareColorCode = "FFAADD00"; // verde

        // Procesa las marcas (en caso de existir) para asignarlas al brand
        processMarkSquares(markDTO, squaresInfo, cameraId, squareColorCode, login);
        // Procesa los paths orígen y destino para asignarlos al objeto brand
        processMarkPaths(markDTO, evidencia, cliente, login, mySession);

        if (snapshotTime != null && !snapshotTime.trim().equals("")) {
            markDTO.setElapsedTime(new Double(snapshotTime));
        }

        markDTO.setWithNumber(Boolean.FALSE);

        if (metrica.isCantEval()) {
            // No se pudo evaluar la métrica
            log.debug("no se generan proofs en el servidor para metricId: " + metricId + " porque se "
                    + "definio que la metrica no se puede evaluar, motivo: [" + metrica.getDescription() + "] - " + login);
        } else {
            String tiempo = metrica.getDescription();
            int minutos = Integer.parseInt(tiempo.split(":")[0]);
            int segundos = Integer.parseInt(tiempo.split(":")[1]);
            int result = (minutos * 60) + segundos;

            markDTO.setResult(result);
            lstMarks.add(markDTO);
        }
        proof.setIsInicial(isInicial);
        proof.setEvidenceId(evidencia.getEvidenceId());
        proof.setPathWithOutMarks("proofs\\");
        proof.setPathWithMarks("proofs_with_marks\\");
        proof.setMarkDTO(markDTO); // relaciona proof con markdto

        metrica.getProofs().add(proof);

        log.info("end - " + login);
        return lstMarks;
    }

    /**
     * Valida si el hashmap no tiene información de marcas
     *
     * @param squaresHashmap estructura con información de marcas
     * @return true en caso que esté vacío
     */
    protected boolean validateEmptyHashmap(HashMap<Integer, String> squaresHashmap) {
        boolean isReallyEmpty = true;
        for (Map.Entry e : squaresHashmap.entrySet()) {
            int cameraId = Integer.valueOf(e.getKey().toString());

            String cameraCircles = squaresHashmap.get(cameraId);
            if (cameraCircles != null && !cameraCircles.trim().equals("") && !cameraCircles.trim().equals("0")) {
                isReallyEmpty = false;
            }
        }
        return isReallyEmpty;
    }

    /**
     * Procesa los círculos (en caso de existir) para asignarlos al mark
     *
     * @author carlos polo
     * @version 1.0.0
     * @param markDTO objeto mark
     * @param circlesInfo datos con coordenadas y tamaños de círculos
     * @param cameraId id de la cámara (provider)
     * @param login login del usuario autenticado
     * @since 6.0
     * @date 26/04/2013
     */
    protected void processMarkCircles(MarksDTO markDTO, HashMap<Integer, String> circlesInfo, int cameraId, String login) {
        log.info("start, cameraId: [" + cameraId + "] - " + login);
        String cameraCircles = circlesInfo.get(cameraId);
        if (cameraCircles != null && !cameraCircles.trim().equals("") && !cameraCircles.trim().equals("0")) {
            // Estructura datos círculos o marcas, ejemplo:
            // datosCirculo1#datosCirculo2
            // 371px:243px_30px:30px#407px:223px_30px:30px
            // coordenadaX:coordenadaY_width:height#coordenadaX:coordenadaY_width:height
            String[] circlesArr = cameraCircles.split("#");
            List<ShapesDTO> circles = generateMarks(circlesArr, "FFFFD700", login); // puntos amarillos
            markDTO.setCircles(circles);
        }
        log.info("end - " + login);
    }

    /**
     * Procesa las marcas (en caso de existir) para asignarlas al brand
     *
     * @author carlos polo
     * @version 1.0.0
     * @param mark objeto mark
     * @param squaresInfo datos con coordenadas y tamaños de marcas de grupo
     * @param cameraId id de la cámara (provider)
     * @param squareColorCode código de color del cuadrado
     * @param login login del usuario autenticado
     * @since 6.0
     * @date 26/04/2013
     */
    protected void processMarkSquares(MarksDTO mark, HashMap<Integer, String> squaresInfo, int cameraId, String squareColorCode,
            String login) {
        log.info("start, cameraId: [" + cameraId + "] - " + login);
        String cameraSquares = squaresInfo.get(cameraId);
        if (cameraSquares != null && !cameraSquares.trim().equals("") && !cameraSquares.trim().equals("0")) {
            String[] square = cameraSquares.split("#");
            List<ShapesDTO> squares = generateMarks(square, squareColorCode, login);
            mark.setSquares(squares);
        }
        log.info("end - " + login);
    }

    /**
     * Procesa los paths orígen y destino para asignarlos al objeto brand
     *
     * @author carlos polo
     * @version 1.0.0
     * @param mark objeto mark
     * @param evidencia evidencia
     * @param cliente cliente evaluado
     * @param login login del usuario autenticado
     * @param mySession sesión del usuario
     * @since 6.0
     * @date 26/04/2013
     */
    protected void processMarkPaths(MarksDTO mark, Evidence evidencia, Client cliente, String login, Session mySession) {
        log.info("start - " + login);
        // 127.0.0.1\periscope.data\evidence\mallmarina\002\2013\04\25\20130425_5109.jpg
        int index1 = evidencia.getEvidencePath().indexOf(cliente.getName());
        if (index1 != -1) {
            int index2 = index1 + cliente.getName().length() + 1;
            String fileLocation = evidencia.getEvidencePath().substring(index2, evidencia.getEvidencePath().length());
            // home/scopix.admin/data/periscope.data/evidence/mallmarina/
            String path = FilenameUtils.separatorsToUnix(cliente.getEvidenceUrl() + fileLocation);
            String destino = FilenameUtils.separatorsToUnix(FilenameUtils.getFullPath(cliente.getProofPath() + fileLocation));

            mark.setPathOrigen(path);
            mark.setPathDestino(destino);
            mySession.setAttribute("PATH_DESTINO", destino);
        }
        log.info("end - " + login);
    }

    /**
     * Genera las marcas de la evaluación
     *
     * @author carlos polo
     * @version 1.0.0
     * @param shapeArr datos de coordenadas y tamaños de la figura
     * @param color color de la figura
     * @param login login del usuario autenticado
     * @since 6.0
     * @date 26/04/2013
     * @return List<Shapes> lista de figuras
     */
    protected List<ShapesDTO> generateMarks(String[] shapeArr, String color, String login) {
        log.info("start - " + login);
        List<ShapesDTO> lstShapes = new ArrayList<ShapesDTO>();

        for (int i = 0; i < shapeArr.length; i++) {
            String shapeStyle = shapeArr[i];
            String[] shapeData = shapeStyle.split("_");

            String coordenadas = shapeData[0];
            String size = shapeData[1];

            String[] coordsArray = coordenadas.split(":");
            String[] sizeArray = size.split(":");

            String x = coordsArray[0];
            String y = coordsArray[1];

            String width = sizeArray[0];
            String height = sizeArray[1];

            // El substring es para quitar el "px"
            int xPos = Integer.valueOf(x.substring(0, x.length() - 2)) - 386;
            int yPos = Integer.valueOf(y.substring(0, y.length() - 2)) - 78;
            // El substring es para quitar el "px"
            int w = Integer.valueOf(width.substring(0, width.length() - 2));
            int h = Integer.valueOf(height.substring(0, height.length() - 2));

            ShapesDTO shape = new ShapesDTO();
            shape.setPositionX(xPos);
            shape.setPositionY(yPos);
            shape.setWidth(w);
            shape.setHeight(h);

            shape.setColor(color);
            lstShapes.add(shape);
        }
        log.info("end - " + login);
        return lstShapes;
    }

    /**
     * Invocación servicio para obtener siguiente situación por evaluar
     *
     * @author carlos polo
     * @version 1.0.0
     * @param corporateName nombre del cliente
     * @return List<Queue> colas del cliente
     * @since 6.0
     * @throws ScopixException excepción durante la obtención de las colas del cliente
     * @date 24/04/2013
     */
    public List<Queue> getClientQueues(String corporateName) throws ScopixException {
        log.info("start, corporateName: [" + corporateName + "]");
        // Invoca comando para solicitar colas
        List<Queue> lstQueues = getQueuesCommand().execute(corporateName);
        log.info("end");
        return lstQueues;
    }

    /**
     * @return the getNextEvidenceCommand
     */
    public GetNextEvidenceCommand getGetNextEvidenceCommand() {
        if (getNextEvidenceCommand == null) {
            getNextEvidenceCommand = new GetNextEvidenceCommand();
        }
        return getNextEvidenceCommand;
    }

    /**
     * @param getNextEvidenceCommand the getNextEvidenceCommand to set
     */
    public void setGetNextEvidenceCommand(GetNextEvidenceCommand getNextEvidenceCommand) {
        this.getNextEvidenceCommand = getNextEvidenceCommand;
    }

    /**
     * @return the queuesWebServices
     */
    public Map<String, Class> getQueuesWebServices() {
        if (queuesWebServices == null) {
            queuesWebServices = new HashMap<String, Class>();
        }
        return queuesWebServices;
    }

    /**
     * @param queuesWebServices the queuesWebServices to set
     */
    public void setQueuesWebServices(Map<String, Class> queuesWebServices) {
        this.queuesWebServices = queuesWebServices;
    }

    /**
     * @return the queuesCommand
     */
    public GetQueuesCommand getQueuesCommand() {
        if (queuesCommand == null) {
            queuesCommand = new GetQueuesCommand();
        }
        return queuesCommand;
    }

    /**
     * @param queuesCommand the queuesCommand to set
     */
    public void setQueuesCommand(GetQueuesCommand queuesCommand) {
        this.queuesCommand = queuesCommand;
    }

    /**
     * @return the sendEvaluationCommand
     */
    public SendEvaluationCommand getSendEvaluationCommand() {
        if (sendEvaluationCommand == null) {
            sendEvaluationCommand = new SendEvaluationCommand();
        }
        return sendEvaluationCommand;
    }

    /**
     * @param sendEvaluationCommand the sendEvaluationCommand to set
     */
    public void setSendEvaluationCommand(SendEvaluationCommand sendEvaluationCommand) {
        this.sendEvaluationCommand = sendEvaluationCommand;
    }

    /**
     * @return the systConfiguration
     */
    public PropertiesConfiguration getSystConfiguration() {
        if (systConfiguration == null) {
            try {
                systConfiguration = new PropertiesConfiguration("system.properties");
                systConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
                log.debug("[systConfiguration.basePath:" + systConfiguration.getBasePath() + "]");
            } catch (ConfigurationException e) {
                log.error("No es posible abrir webservices.properties " + e, e);
            }
        }
        return systConfiguration;
    }

    public String getSnapshotTime() {
        return getSystConfiguration().getString("snapshot.time", "1");
    }

    public int getRetryNumber() {
        return getSystConfiguration().getInt("retry.number", 2);
    }

    public void removeSituationFromHash(Integer situationId) {
        log.info("start, removiendo de hashmap situation con id: [" + situationId + "]");
        Situation situation = getHmEvalSituations().get(situationId);
        if (situation != null) {
            getHmEvalSituations().remove(situation);
        }
        log.info("end");
    }

    public HashMap<Integer, Situation> getHmEvalSituations() {
        if (hmEvalSituations == null) {
            hmEvalSituations = new HashMap<Integer, Situation>();
        }
        return hmEvalSituations;
    }

    public HashMap<Long, UserEvaluation> getHmUsersEvaluations() {
        if (hmUsersEvaluations == null) {
            hmUsersEvaluations = new HashMap<Long, UserEvaluation>();
        }
        return hmUsersEvaluations;
    }

    public void setHmUsersEvaluations(HashMap<Long, UserEvaluation> hmUsersEvaluations) {
        this.hmUsersEvaluations = hmUsersEvaluations;
    }
}
