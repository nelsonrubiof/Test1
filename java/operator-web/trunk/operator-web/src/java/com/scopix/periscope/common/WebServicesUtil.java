package com.scopix.periscope.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.scopix.periscope.enums.EnumEvaluationType;
import com.scopix.periscope.enums.EnumEvidenceType;
import com.scopix.periscope.enums.EnumMarkType;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceEvaluationDTO;
import com.scopix.periscope.evaluationmanagement.dto.EvidenceSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.MarquisDTO;
import com.scopix.periscope.evaluationmanagement.dto.MetricSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.evaluationmanagement.dto.RegionTransferSendDTO;
import com.scopix.periscope.evaluationmanagement.dto.SituationSendDTO;
import com.scopix.periscope.model.Equivalence;
import com.scopix.periscope.model.Evidence;
import com.scopix.periscope.model.EvidenceProvider;
import com.scopix.periscope.model.Metric;
import com.scopix.periscope.model.Proof;
import com.scopix.periscope.model.Situation;
import com.scopix.periscope.operatorimages.ShapesDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Clase para la conversion de objetos entre el modelo capa cliente y objetos retornados por los web services de evaluación
 *
 * @author carlos polo
 * @version 2.0.0
 * @since 6.0
 */
public class WebServicesUtil implements Serializable {

    private static final long serialVersionUID = -27954694355064197L;
    private static Logger log = Logger.getLogger(WebServicesUtil.class);

    /**
     * Convierte objeto retornado por el web service de obtención de evidencias (SituationSendDTO) a un objeto del modelo de la
     * capa de cliente
     *
     * @author carlos polo
     * @version 1.0.0
     * @param situationSendDTO objeto retornado por el web service
     * @param login login del usuario autenticado
     * @return Situation objeto convertido al modelo capa cliente
     * @since 6.0
     * @date 19/02/2013
     * @throws ScopixException excepción durante la conversión de objetos
     */
    @SuppressWarnings("unchecked")
    public static Situation toSituation(SituationSendDTO situationSendDTO, String login) throws ScopixException {
        log.info("start - " + login);
        Situation situation = new Situation();

        try {
            Integer pendingEvalId = situationSendDTO.getPendingEvaluationId();
            log.debug("Transformando situationSendDTO.pendingEvaluationId: " + pendingEvalId + " - " + login);

            situation.setPendingEvaluationId(pendingEvalId);
            situation.setClient(situationSendDTO.getCorporate());
            situation.setStoreName(situationSendDTO.getStoreName());
            situation.setStoreDescription(situationSendDTO.getStoreDescription());
            situation.setArea(situationSendDTO.getArea());
            situation.setEvidenceDateTime(situationSendDTO.getEvidenceDateTime());
            situation.setProductName(situationSendDTO.getProductName());
            situation.setProductDescription(situationSendDTO.getProductDescription());
            situation.setId(situationSendDTO.getSituationId());

            String rejectedObs = situationSendDTO.getRejectedObservation();

            if (rejectedObs != null) {
                situation.setRejected(true);
                situation.setRejectedObservation(rejectedObs);
            }

            List<MetricSendDTO> lstMetricas = situationSendDTO.getMetrics();

            int count = 0;
            Session mySession = Sessions.getCurrent();
            List<Equivalence> officeEquivalences = (List<Equivalence>) mySession.getAttribute("OFFICE_EQUIVALENCES");

            if (lstMetricas != null && !lstMetricas.isEmpty()) {
                for (MetricSendDTO metricDTO : lstMetricas) {
                    log.debug("Transformando metricSendDTO.getMetricId: " + metricDTO.getMetricId() + " - " + login);

                    if (metricDTO.isMultiple()) {
                        // Procesamiento para las métricas que sean multicámara
                        processMulticameraMetric(count, metricDTO, situation, login, officeEquivalences);
                    } else {
                        // Procesamiento para las métricas que tengan una sola cámara
                        processSingleCameraMetric(count, metricDTO, situation, login, officeEquivalences);
                    }
                    count++;
                }
            }
            // Ordena por la lista de métricas de acuerdo al órden parametrizado
            log.debug("ordenando metricas - " + login);
            List<Metric> metricas = situation.getMetricas();
            Collections.sort(metricas);
            situation.setMetricas(metricas);

        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }

        log.info("end - " + login);
        return situation;
    }

    /**
     * Procesamiento para las métricas que sean multicámara
     *
     * @author carlos polo
     * @version 1.0.0
     * @param count contador de registros
     * @param metricDTO dto correspondiente a la métrica
     * @param situation situación en procesamiento
     * @param login login del usuario autenticado
     * @param officeEquivalences equivalencias de oficina seleccionada
     * @since 6.0
     * @date 26/04/2013
     */
    protected static void processMulticameraMetric(int count, MetricSendDTO metricDTO, Situation situation, String login,
            List<Equivalence> officeEquivalences) {

        log.info("start - " + login);
        Metric metrica = new Metric();
        metrica.setNumMetrica(String.valueOf(count));

        // Múltiples evidencias para la métrica (multicámara)
        List<EvidenceSendDTO> lstEvidences = metricDTO.getEvidences();

        metrica.setOrder(metricDTO.getOrder());
        metrica.setEvalInstruction(metricDTO.getEvalInstruction());
        metrica.setMetricId(metricDTO.getMetricId());
        metrica.setName(metricDTO.getName());
        metrica.setDescription(Labels.getLabel("eval.noDefinido"));
        metrica.setType(metricDTO.getType());

        if (lstEvidences != null && !lstEvidences.isEmpty()) {
            if (lstEvidences.size() > 1) {
                metrica.setMultiple(true);
            } else {
                metrica.setMultiple(false);
            }

            metrica.setCurrentCameraId(lstEvidences.get(0).getEvidenceProvider().getId());

            // Subprocesamiento de métricas multicámaras
            subProcessMulticameraMetric(lstEvidences, metrica, login, officeEquivalences);
            metrica.setCurrentEvidenceId(String.valueOf(metrica.getEvidencias().get(0).getEvidenceId()));
        }
        situation.getMetricas().add(metrica);
        log.info("end - " + login);
    }

    /**
     * Subprocesamiento de métricas multicámaras
     *
     * @author carlos polo
     * @version 1.0.0
     * @param lstEvidences listado de evidencias
     * @param metrica métrica en procesamiento
     * @param login login del usuario autenticado
     * @param officeEquivalences equivalencias de oficina seleccionada
     * @since 6.0
     * @date 26/04/2013
     */
    protected static void subProcessMulticameraMetric(List<EvidenceSendDTO> lstEvidences, Metric metrica, String login,
            List<Equivalence> officeEquivalences) {

        log.info("start - " + login);
        for (EvidenceSendDTO evidenceDTO : lstEvidences) {
            Evidence evidencia = new Evidence();

            List<RegionTransferSendDTO> lstRegionTransfers = evidenceDTO.getRegionTransfers();
            // Obtiene IP del operator images correspondiente a la oficina equivalente seleccionada
            String operatorImagesIp = getOfficeOperatorImagesIp(officeEquivalences, lstRegionTransfers, login);

            evidencia.setOperatorImagesIp(operatorImagesIp); // protocol://domainip:port/context
            evidencia.setEvidenceId(evidenceDTO.getEvidenceId());
            evidencia.setEvidencePath(evidenceDTO.getEvidencePath());
            evidencia.setProofPath(evidenceDTO.getProofPath());

            String evidenceType = evidenceDTO.getEvidenceType();
            if (evidenceType.equalsIgnoreCase(EnumEvidenceType.IMAGE.toString())) {
                evidencia.setEvidenceType(EnumEvidenceType.IMAGE.toString());
            } else {
                evidencia.setEvidenceType(EnumEvidenceType.VIDEO.toString());
            }

            EvidenceProvider evidenceProvider = new EvidenceProvider();
            evidenceProvider.setId(evidenceDTO.getEvidenceProvider().getId());
            evidenceProvider.setDescripcion(evidenceDTO.getEvidenceProvider().getDescription());
            evidenceProvider.setViewOrder(evidenceDTO.getEvidenceProvider().getViewOrder());

            evidencia.setEvidenceProvider(evidenceProvider);

            if (evidenceDTO.getTemplatePath() != null && FilenameUtils.isExtension(evidenceDTO.getTemplatePath(), "png")) {
                evidencia.setTemplatePath(evidenceDTO.getTemplatePath());
            }
            metrica.getEvidencias().add(evidencia);
        }
        log.info("end - " + login);
    }

    /**
     * Obtiene IP del operator images correspondiente a la oficina equivalente seleccionada
     *
     * @param officeEquivalences
     * @param lstRegionTransfers
     * @param login
     * @return
     */
    private static String getOfficeOperatorImagesIp(List<Equivalence> officeEquivalences,
            List<RegionTransferSendDTO> lstRegionTransfers, String login) {

        log.info("start - " + login);
        String operatorImagesIp = null;

        if (lstRegionTransfers != null && !lstRegionTransfers.isEmpty()) {
            for (RegionTransferSendDTO regionTransferSendDTO : lstRegionTransfers) {
                String serverCodeName = regionTransferSendDTO.getServerCodeName();
                log.debug("serverCodeName: [" + serverCodeName + "] - " + login);

                if (serverCodeName != null) {
                    serverCodeName = serverCodeName.toUpperCase();
                    if (officeEquivalences != null && !officeEquivalences.isEmpty()) {
                        for (Equivalence equivalence : officeEquivalences) {
                            String equivalenceName = equivalence.getName().toUpperCase();

                            log.debug("equivalenceName: [" + equivalenceName + "] - " + login);
                            if (serverCodeName.equalsIgnoreCase(equivalenceName)) {
                                operatorImagesIp = regionTransferSendDTO.getIp();
                                break;
                            }
                        }
                    }
                }

                if (operatorImagesIp != null) {
                    break;
                }
            }
        }
        log.info("end, operatorImagesIp: [" + operatorImagesIp + "] - " + login);
        return operatorImagesIp;
    }

    /**
     * Procesamiento para las métricas que tengan una sola cámara
     *
     * @author carlos polo
     * @version 1.0.0
     * @param count contador de registros
     * @param metricDTO dto correspondiente a la métrica
     * @param situation situación en procesamiento
     * @param login login del usuario autenticado
     * @param officeEquivalences equivalencias de oficina seleccionada
     * @since 6.0
     * @date 26/04/2013
     */
    protected static void processSingleCameraMetric(int count, MetricSendDTO metricDTO, Situation situation, String login,
            List<Equivalence> officeEquivalences) {

        log.info("start - " + login);
        // Una sola evidencia para la métrica
        List<EvidenceSendDTO> lstEvidences = metricDTO.getEvidences();
        List<EvidenceEvaluationDTO> lstEvidenceEvalDTO = metricDTO.getEvaluationDTOs();

        if (lstEvidences != null && !lstEvidences.isEmpty()) {
            for (EvidenceSendDTO evidenceDTO : lstEvidences) {
                // Verifica si la métrica fue previamente evaluada (proceso automático)
                boolean isEvaluated = isMetricEvaluated(lstEvidenceEvalDTO, evidenceDTO);

                Metric metrica = new Metric();
                metrica.setNumMetrica(String.valueOf(count));
                metrica.setOrder(metricDTO.getOrder());
                metrica.setEvalInstruction(metricDTO.getEvalInstruction());
                metrica.setMetricId(metricDTO.getMetricId());
                metrica.setName(metricDTO.getName());
                metrica.setType(metricDTO.getType());
                metrica.setCurrentCameraId(evidenceDTO.getEvidenceProvider().getId());

                if (isEvaluated) {
                    // Establece la métrica como evaluada y su correspondiente valor
                    metrica.setEvaluada(true);
                    EvidenceEvaluationDTO evidenceEvalDTO = getEvidenceEvalDTO(lstEvidenceEvalDTO, evidenceDTO.getEvidenceId());
                    setMetricPreviousResult(metrica, evidenceEvalDTO);
                } else {
                    // no está evaluada, pone valor por defecto
                    metrica.setDescription(Labels.getLabel("eval.noDefinido"));
                }

                Evidence evidencia = new Evidence();

                List<RegionTransferSendDTO> lstRegionTransfers = evidenceDTO.getRegionTransfers();
                // Obtiene IP del operator images correspondiente a la oficina equivalente seleccionada
                String operatorImagesIp = getOfficeOperatorImagesIp(officeEquivalences, lstRegionTransfers, login);

                evidencia.setOperatorImagesIp(operatorImagesIp);
                evidencia.setEvidenceId(evidenceDTO.getEvidenceId());
                evidencia.setEvidencePath(evidenceDTO.getEvidencePath());
                evidencia.setProofPath(evidenceDTO.getProofPath());

                String evidenceType = evidenceDTO.getEvidenceType();

                if (evidenceType.equalsIgnoreCase(EnumEvidenceType.IMAGE.toString())) {
                    evidencia.setEvidenceType(EnumEvidenceType.IMAGE.toString());
                } else {
                    evidencia.setEvidenceType(EnumEvidenceType.VIDEO.toString());
                }

                EvidenceProvider evidenceProvider = new EvidenceProvider();
                evidenceProvider.setId(evidenceDTO.getEvidenceProvider().getId());
                evidenceProvider.setDescripcion(evidenceDTO.getEvidenceProvider().getDescription());
                evidenceProvider.setViewOrder(evidenceDTO.getEvidenceProvider().getViewOrder());

                evidencia.setEvidenceProvider(evidenceProvider);

                if (evidenceDTO.getTemplatePath() != null && FilenameUtils.isExtension(evidenceDTO.getTemplatePath(), "png")) {
                    evidencia.setTemplatePath(evidenceDTO.getTemplatePath());
                }

                metrica.setCurrentEvidenceId(String.valueOf(evidencia.getEvidenceId()));
                metrica.getEvidencias().add(evidencia);
                situation.getMetricas().add(metrica);
            }
        }
        log.info("end - " + login);
    }

    /**
     *
     * @param lstEvidenceEvalDTO
     * @param evidenceId
     * @return
     */
    private static EvidenceEvaluationDTO getEvidenceEvalDTO(List<EvidenceEvaluationDTO> lstEvidenceEvalDTO, Integer evidenceId) {
        log.info("start");
        Integer id = -1;
        EvidenceEvaluationDTO evidenceEvaluationDTO = null;

        for (EvidenceEvaluationDTO evidenceEvalDTO : lstEvidenceEvalDTO) {
            List<Integer> lstDTOEvidenceIds = evidenceEvalDTO.getEvidenceIds();
            if (lstDTOEvidenceIds.contains(evidenceId)) {
                evidenceEvaluationDTO = evidenceEvalDTO;
                id = evidenceEvaluationDTO.getId();
            }
        }
        log.info("end, evidenceEvaluationDTO: [" + evidenceEvaluationDTO + "], id: [" + id + "]");
        return evidenceEvaluationDTO;
    }

    /**
     * Verifica si la métrica fue previamente evaluada (proceso automático)
     *
     * @param lstEvidenceEvalDTO
     * @param evidenceDTO
     * @return
     */
    protected static boolean isMetricEvaluated(List<EvidenceEvaluationDTO> lstEvidenceEvalDTO, EvidenceSendDTO evidenceDTO) {
        log.info("start");
        boolean evaluada = false;
        if (lstEvidenceEvalDTO != null && !lstEvidenceEvalDTO.isEmpty()) {
            for (EvidenceEvaluationDTO evidenceEvalDTO : lstEvidenceEvalDTO) {

                List<Integer> lstDTOEvidenceIds = evidenceEvalDTO.getEvidenceIds();
                if (lstDTOEvidenceIds != null && !lstDTOEvidenceIds.isEmpty()) {
                    evaluada = lstDTOEvidenceIds.contains(evidenceDTO.getEvidenceId());
                    if (evaluada) {
                        break;
                    }
                }
            }
        }
        log.info("end, evaluada: [" + evaluada + "]");
        return evaluada;
    }

    /**
     * Establece la métrica como evaluada y con su correspondiente valor
     *
     * @param metrica
     * @param evidenceEvalDTO
     */
    protected static void setMetricPreviousResult(Metric metrica, EvidenceEvaluationDTO evidenceEvalDTO) {
        log.info("start, metricId: [" + metrica.getMetricId() + "], metricName: [" + metrica.getName() + "]");
        String metricType = metrica.getType();
        Date fechaActual = new Date();
        Integer evidenceEvalDTOId = evidenceEvalDTO.getId();
        Integer metricResult = evidenceEvalDTO.getEvidenceEvaluationResult();

        // setea fecha inicial y final igual para todas las métricas previamente evaluadas
        metrica.setStartEvaluationTime(fechaActual);
        metrica.setEndEvaluationTime(fechaActual);
        metrica.setEvidenceEvaluationDTOId(evidenceEvalDTOId);
        log.debug("metricType: [" + metricType + "], " + "metricResult: [" + metricResult + "], evidenceEvalDTOId: ["
                + evidenceEvalDTOId + "]");

        if (EnumEvaluationType.YES_NO.toString().equalsIgnoreCase(metricType)) {
            if (metricResult == 1) {
                metrica.setDescription("S");
            } else {
                metrica.setDescription("N");
            }
        } else {
            metrica.setDescription(String.valueOf(metricResult));
        }
        log.info("end");
    }

    /**
     * Convierte objeto retornado por el modelo web (situation) a una lista de objetos del modelo del servicio para envío de la
     * evaluación
     *
     * @author carlos polo
     * @version 1.0.0
     * @param situation objeto modelo web
     * @param login login del usuario autenticado
     * @return List<EvidenceEvaluationDTO> lista de objetos modelo web service
     * @since 6.0
     * @date 26/04/2013
     * @throws ScopixException exception durante la conversión de objetos
     */
    public static List<EvidenceEvaluationDTO> toSituationDTO(Situation situation, String login) throws ScopixException {

        log.info("start - " + login);
        List<ProofDTO> proofsDTOList;
        List<EvidenceEvaluationDTO> evidencesDTOList = new ArrayList<EvidenceEvaluationDTO>();

        try {
            if (situation != null) {
                // Obtiene las métricas evaluadas de la situación
                List<Metric> lstMetricas = situation.getMetricas();

                if (lstMetricas != null && !lstMetricas.isEmpty()) {
                    for (Metric metrica : lstMetricas) {
                        EvidenceEvaluationDTO evidenceEvaluationDTO = new EvidenceEvaluationDTO();

                        Set<Integer> setEvidenceIds = new HashSet<Integer>();
                        List<Evidence> lstEvidencias = metrica.getEvidencias();
                        for (Evidence evidencia : lstEvidencias) {
                            setEvidenceIds.add(evidencia.getEvidenceId());
                        }

                        // si el id viene != null, es porque esa métrica fue previamente evaluada de forma automática
                        evidenceEvaluationDTO.setId(metrica.getEvidenceEvaluationDTOId());
                        evidenceEvaluationDTO.setEvidenceIds(new ArrayList<Integer>(setEvidenceIds));

                        // pendingEvaluationId
                        evidenceEvaluationDTO.setPendingEvaluationId(situation.getPendingEvaluationId());
                        // metricId
                        evidenceEvaluationDTO.setMetricId(metrica.getMetricId());

                        Date startEvalTime = metrica.getStartEvaluationTime();
                        Date endEvalTime = metrica.getEndEvaluationTime();

                        log.debug("metricId: " + metrica.getMetricId() + ", valor: " + metrica.getDescription()
                                + ", startEvaluationTime: " + startEvalTime + ", endEvaluationTime: " + endEvalTime
                                + ", metrica.getType(): " + metrica.getType() + " - " + login);

                        // initEvaluation
                        evidenceEvaluationDTO.setInitEvaluation(startEvalTime); // Tiempo inicial
                        endEvalTime = endEvalTime == null ? new Date() : endEvalTime;

                        // endEvaluation
                        evidenceEvaluationDTO.setEndEvaluation(endEvalTime); // Tiempo final

                        Long differenceInSegs = getTimeDifferenceInSeconds(startEvalTime, endEvalTime, login);
                        // evaluationTimeInSeconds
                        evidenceEvaluationDTO.setEvaluationTimeInSeconds(differenceInSegs);

                        if (metrica.isCantEval()) { // no se pudo evaluar
                            evidenceEvaluationDTO.setCantDoReason(metrica.getCantEvalObs());
                        } else {
                            int metricResult = -1;
                            String descripcion = metrica.getDescription();

                            if (descripcion.equalsIgnoreCase("S")) {
                                metricResult = 1;
                            } else if (descripcion.equalsIgnoreCase("N")) {
                                metricResult = 0;
                            } else {
                                if (metrica.getType().equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
                                    String tiempo = metrica.getDescription();

                                    if (tiempo != null && !tiempo.trim().equals("") && !metrica.isCantEval()) {
                                        int minutos = Integer.parseInt(tiempo.split(":")[0]);
                                        int segundos = Integer.parseInt(tiempo.split(":")[1]);

                                        metricResult = (minutos * 60) + segundos;

                                    } else if (metrica.isCantEval()) {
                                        evidenceEvaluationDTO.setCantDoReason(metrica.getCantEvalObs());
                                    }
                                } else {
                                    metricResult = Integer.parseInt(metrica.getDescription());
                                }
                            }

                            evidenceEvaluationDTO.setEvidenceEvaluationResult(metricResult);
                        }
                        proofsDTOList = new ArrayList<ProofDTO>();

                        // Procesa los atributos correspondientes a los proofs para asignarlos al evidenceEvaluationDTO
                        processMetricProofs(metrica, proofsDTOList, login);

                        evidenceEvaluationDTO.setProofs(proofsDTOList);
                        evidencesDTOList.add(evidenceEvaluationDTO);
                    }
                }
            }
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end - " + login);
        return evidencesDTOList;
    }

    /**
     * Procesa los atributos correspondientes a los proofs para asignarlos al evidenceEvaluationDTO
     *
     * @param metrica métrica en procesamiento
     * @param objectFactory object factory para proofDTO
     * @param objectFactory2 object factory para arrayOfint
     * @param proofsDTOList lista de proofs
     * @param login login del usuario autenticado
     */
    protected static void processMetricProofs(Metric metrica, List<ProofDTO> proofsDTOList, String login) {

        log.info("start - " + login);
        List<Proof> metricProofs = metrica.getProofs();

        if (metricProofs != null && !metricProofs.isEmpty()) {
            log.debug("metricId: [" + metrica.getMetricId() + "], metricDesc: [" + metrica.getDescription() + "], proofsSize: "
                    + metricProofs.size() + " - " + login);

            int order = 0;

            for (Proof proof : metricProofs) {
                ProofDTO proofDTO = new ProofDTO();
                // Invoca servicio que retorna paths (o se invoca desde antes)
                proofDTO.setPathWithMarks(proof.getPathWithMarks());
                proofDTO.setPathWithoutMarks(proof.getPathWithOutMarks());
                proofDTO.setOrder(order);
                proofDTO.setEvidenceId(proof.getEvidenceId());

                if (!metrica.isCantEval()) {
                    int metricResult = -1;
                    String descripcion = metrica.getDescription();

                    if (descripcion.equalsIgnoreCase("S")) {
                        metricResult = 1;
                    } else if (descripcion.equalsIgnoreCase("N")) {
                        metricResult = 0;
                    } else {
                        if (metrica.getType().equalsIgnoreCase(EnumEvaluationType.MEASURE_TIME.toString())) {
                            if (proof.isIsInicial()) {
                                metricResult = Double.valueOf(metrica.getSnapshotTimeInicial()).intValue();
                            } else {
                                metricResult = Double.valueOf(metrica.getSnapshotTimeFinal()).intValue();
                            }
                        } else if (metrica.getType().equalsIgnoreCase(EnumEvaluationType.NUMBER_INPUT.toString())) {
                            metricResult = Integer.parseInt(metrica.getDescription());
                        } else {
                            // conteo
                            if (metrica.isMultiple()) {
                                metricResult = proof.getResult();
                            } else {
                                metricResult = Integer.parseInt(metrica.getDescription());
                            }
                        }
                    }
                    proofDTO.setProofResult(metricResult);
                }
                log.debug("proofDTO evidenceId: [" + proofDTO.getEvidenceId() + "], " + "proofDTO order: [" + proofDTO.getOrder()
                        + "], proofDTO result: [" + proofDTO.getProofResult() + "]");

                proofDTO.setMarquis(processMarquis(proof, login));
                proofsDTOList.add(proofDTO);
                order++;
            }// fin for proofs métrica
        } else {
            log.debug("metricId: " + metrica.getMetricId() + ", no tiene proofs asociados - " + login);
        }
        log.info("end - " + login);
    }

    private static List<MarquisDTO> processMarquis(Proof proof, String login) {
        log.info("start - " + login);
        List<MarquisDTO> lstMarquisDTO = new ArrayList<MarquisDTO>();

        if (proof != null && proof.getMarkDTO() != null) {
            List<ShapesDTO> lstCircles = proof.getMarkDTO().getCircles();
            List<ShapesDTO> lstSquares = proof.getMarkDTO().getSquares();

            if (lstCircles != null) {
                lstMarquisDTO.addAll(subProcessMarquis(lstCircles, EnumMarkType.CIRCLE.toString(), login));
            }

            if (lstSquares != null) {
                lstMarquisDTO.addAll(subProcessMarquis(lstSquares, EnumMarkType.SQUARE.toString(), login));
            }
        }

        log.info("end, lstMarquisDTO size: [" + lstMarquisDTO.size() + "] - " + login);
        return lstMarquisDTO;
    }

    private static List<MarquisDTO> subProcessMarquis(List<ShapesDTO> lstShapes, String type, String login) {
        log.info("start, type: [" + type + "] - " + login);
        List<MarquisDTO> lstMarquisDTO = new ArrayList<MarquisDTO>();

        if (lstShapes != null && !lstShapes.isEmpty()) {
            for (ShapesDTO shapeDTO : lstShapes) {
                MarquisDTO marquisDTO = new MarquisDTO();
                marquisDTO.setType(type);
                marquisDTO.setColor(shapeDTO.getColor());
                marquisDTO.setX(shapeDTO.getPositionX());
                marquisDTO.setY(shapeDTO.getPositionY());
                marquisDTO.setWidth(shapeDTO.getWidth());
                marquisDTO.setHeight(shapeDTO.getHeight());

                lstMarquisDTO.add(marquisDTO);
            }
        }
        log.info("end, lstMarquisDTO size: [" + lstMarquisDTO.size() + "] - " + login);
        return lstMarquisDTO;
    }

    /**
     * Calcula la diferencia en segundos entre dos fechas
     *
     * @author carlos polo
     * @version 1.0.0
     * @param startEvalTime
     * @param endEvalTime
     * @param login login del usuario autenticado
     * @return Long diferencia en segundos
     * @since 6.0
     * @date 26/04/2013
     */
    protected static Long getTimeDifferenceInSeconds(Date startEvalTime, Date endEvalTime, String login) {
        log.info("start - " + login);
        // Representación de las fechas en milisegundos
        long milis1 = startEvalTime.getTime();
        long milis2 = endEvalTime.getTime();
        // Calcular la diferencia en milisengundos
        long diff = milis2 - milis1;
        // Calcula la diferencia en segundos
        long diffSeconds = diff / 1000;

        log.info("end - " + login);
        return diffSeconds;
    }
}
