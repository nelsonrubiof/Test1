/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * AutomaticPeopleDetectionMTEvaluator.java
 * 
 * Created on 12-02-2013, 12:38:10 PM
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.scopix.peoplefilter.dto.GhostbusterDto;
import com.scopix.peoplefilter.request.GhostbusterRequestProcessor;
import com.scopix.periscope.cache.ScopixCache;
import com.scopix.periscope.evaluationmanagement.AutomaticEvaluationResult;
import com.scopix.periscope.evaluationmanagement.AutomaticEvaluationResultDTO;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.evaluationmanagement.commands.AddAutomaticEvaluationResultCommand;
import com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc.AnalyticsUCUtilities;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;

/**
 *
 * @author Emo
 * @version 1.0.0
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_MT, description = "Analiza automáticamente si la imagen contiene información para ser evaluada.")
@SpringBean(rootClass = AutomaticPeopleDetectionMTEvaluator.class)
public class AutomaticPeopleDetectionMTEvaluator extends AbstractEvidenceEvaluatorForMT implements InitializingBean {

    private static final Logger log = Logger.getLogger(AutomaticPeopleDetectionMTEvaluator.class);
    private AnalyticsUCUtilities utilities;

    @Autowired
    private ScopixCache scopixCache;
    boolean initializedCache = false;

    private String totalTimeKey = "APD_TOTAL_TIME";
    private String totalEvalKey = "APD_TOTAL_EVAL";
    private String totalEvalTimeKey = "APD_TOTAL_EVAL_TIME";
    private String tempTimeKey = "APD_TEMP_TIME";
    private String tempEvalKey = "APD_TEMP_EVAL";
    private String tempEvalTimeKey = "APD_TEMP_EVAL_TIME";
    private String lastTempEvalKey = "APD_LAST_EVAL";
    private String lastTempEvalTimeKey = "APD_LAST_EVAL_TIME";

    private Long evalTime = 0l;
    private Long totalTimeInit = 0l;
    private Long lastResetTime = 0l;

    private GhostbusterRequestProcessor processor;

    /**
     * evalua un pending evaluation con algoritmo de Counter desarrollado por Pablo Lluch
     *
     * @param om
     * @param pendingEvaluationId
     * @return Boolean
     * @throws ScopixException
     */
    @Override
    public Boolean evaluate(ObservedMetric om, Integer pendingEvaluationId) throws ScopixException {
        startStatisticGathering();

        log.info("start [om:" + om.getId() + "][peId:" + pendingEvaluationId + "]");
        Boolean result = true;

        // recuperar desde el manager
        SituationTemplate template = om.getMetric().getSituation().getSituationTemplate();
        List<EvidenceEvaluation> evaluations = new ArrayList<EvidenceEvaluation>();
        log.debug("evidences:" + om.getEvidences().size());

        for (Evidence ev : om.getEvidences()) {
            log.debug("[ev.id:" + ev.getId() + "][ev.getEvidencePath:" + ev.getEvidencePath() + "]");

            // Check if evidence already exist
            Boolean hasPeople = null;
            String evaluationPath;

            long startTimeMillis = System.currentTimeMillis();
            AutomaticEvaluationResult aer = getAutomaticEvaluationResult(ev, template);
            long requestDuration = System.currentTimeMillis() - startTimeMillis;

            String datePath = DateFormatUtils.format(ev.getEvidenceDate(), "yyyy\\MM\\dd");

            if (aer != null) {
                log.debug("AutomaticEvaluationResult found: [aer.id:" + aer.getId() + "]");
                hasPeople = aer.getResult();
                evaluationPath = aer.getEvaluationPath();
            } else {
                log.debug("AutomaticEvaluationResult no found... processing.");
                evaluationPath = FilenameUtils.separatorsToSystem("/data/ftp/evidence/"
                        + om.getMetric().getStore().getCorporate().getName() + "/" + om.getMetric().getStore().getName() + "/"
                        + datePath + "/" + ev.getEvidencePath());

                GhostbusterDto dto = new GhostbusterDto(evaluationPath, null, om.getMetric().getStore().getCorporate().getName(),
                        om.getMetric().getStore().getName(), getCameraName(ev), getSituationTemplateFile(ev, template), false);

                log.debug("Sending request.");
                startTimeMillis = System.currentTimeMillis();
                hasPeople = processor.process(dto);
                requestDuration = System.currentTimeMillis() - startTimeMillis;
                log.debug("Request response: " + hasPeople);

                if (hasPeople == null) {
                    throw new ScopixException("Error resultado no recibido");
                }
            }

            String evidencePath = FilenameUtils.separatorsToSystem("/data/ftp/evidence/"
                    + om.getMetric().getStore().getCorporate().getName() + "/" + om.getMetric().getStore().getName() + "/"
                    + datePath + "/" + ev.getEvidencePath());

            result = result && !hasPeople;

            Date initEvaluation = new Date();

            // Con gente va a evaluar
            if (!hasPeople) {
                // modificar booleano evaluationOK
                Date endEvaluation = new Date();

                // generamos el ee
                EvidenceEvaluation ee = genEvidenceEvaluation(0, ev, om, pendingEvaluationId,
                        "AutomaticPeopleDetectionEvaluator", initEvaluation, endEvaluation);
                // ultimo valor sacado desde el parseo del XML
                String proofPath = FilenameUtils.separatorsToSystem("/data/ftp/proofs/"
                        + om.getMetric().getStore().getCorporate().getName() + "/" + om.getMetric().getStore().getName() + "/"
                        + datePath);

                Proof proof = generateProof(ee, ev, 0, proofPath, om, evidencePath);

                if (ee.getProofs() == null) {
                    ee.setProofs(new ArrayList<Proof>());
                }
                ee.getProofs().add(proof);

                // agregamos el ee a la lista para ser devuelta al que nos llamo
                ee.setObservedMetric(om);
                evaluations.add(ee);
            }

            storeResult(hasPeople, requestDuration, evaluationPath, ev, om, template, pendingEvaluationId);
        }
        om.getEvidenceEvaluations().addAll(evaluations);
        log.info("Final Result: " + result);

        sendStatistics();
        log.info("end");

        return result;
    }

    private void sendStatistics() {
        log.debug("Start");

        evalTime = System.currentTimeMillis() - evalTime;

        scopixCache.set(totalTimeKey, 600000, System.currentTimeMillis() - totalTimeInit);
        scopixCache.count(totalEvalKey, 1l);
        scopixCache.count(totalEvalTimeKey, evalTime);
        scopixCache.set(tempTimeKey, 600000, System.currentTimeMillis() - lastResetTime);
        scopixCache.count(tempEvalKey, 1);
        scopixCache.count(tempEvalTimeKey, evalTime);

        log.debug("End");
    }

    private void startStatisticGathering() {
        log.debug("Start");
        if (!initializedCache) {
            initStatisticsGathering();
            initializedCache = true;

            // Start counters
            totalTimeInit = System.currentTimeMillis();
            lastResetTime = System.currentTimeMillis();
        }

        evalTime = System.currentTimeMillis();

        // TODO Remove magic number
        if (System.currentTimeMillis() - lastResetTime > 60000) {
            resetTempStatistics();
        }

        log.debug("End");
    }

    private void resetTempStatistics() {
        log.debug("Start");

        lastResetTime = System.currentTimeMillis();
        scopixCache.set(tempTimeKey, 600000, 0);
        scopixCache.set(tempEvalKey, 600000, 0);
        scopixCache.set(tempEvalTimeKey, 600000, 0);

        // TODO duplicate values of last minute stats
        // scopixCache.set(lastTempEvalKey, 600000, 0);
        // scopixCache.set(lastTempEvalTimeKey, 600000, 0);

        log.debug("End");
    }

    private void initStatisticsGathering() {
        scopixCache.set(totalTimeKey, 600000, 0);
        scopixCache.set(totalEvalKey, 600000, 0);
        scopixCache.set(totalEvalTimeKey, 600000, 0);
        scopixCache.set(tempTimeKey, 600000, 0);
        scopixCache.set(tempEvalKey, 600000, 0);
        scopixCache.set(tempEvalTimeKey, 600000, 0);
        scopixCache.set(lastTempEvalKey, 600000, 0);
        scopixCache.set(lastTempEvalTimeKey, 600000, 0);
    }

    /**
     * Return Evaluation Automatic is Exist
     */
    private AutomaticEvaluationResult getAutomaticEvaluationResult(Evidence ev, SituationTemplate template)
            throws ScopixException {
        log.info("start");
        AutomaticEvaluationResult aer = null;
        AutomaticEvaluationResultDTO aerDto = (AutomaticEvaluationResultDTO) scopixCache.get("AER[EV" + ev.getId() + ".ST"
                + template.getId() + "]");

        // si no lo encontramos en la base de datos lo buscamos en el hash de evaluation automatica
        if (aerDto == null) {
            Set<Integer> situationsEq = getSituationsEquals(template.getId());
            log.debug("situation " + template.getId() + " eq to " + situationsEq + " ev.id:" + ev.getId());
            if (situationsEq != null) {
                for (Integer stTemp : situationsEq) {
                    SituationTemplate st = new SituationTemplate();
                    st.setId(stTemp);
                    String key = "AER[EV" + ev.getId() + ".ST" + st.getId() + "]";
                    aerDto = (AutomaticEvaluationResultDTO) scopixCache.get(key);
                    // aer = (AutomaticEvaluationResult) getEvaluatorManager().getHashEvaluationAutomatic().get(key);
                    // aer = (new GetAutomaticEvaluationResultByEvidenceAndSituationTemplateCommand()).execute(ev, st);
                    if (aerDto != null) {
                        log.debug("found in memcache " + key);
                        break;
                    }
                }
            }
        }

        if (aerDto != null) {
            log.debug("found in " + "[" + ev.getId() + "." + template.getId() + "]" + "[" + ev.getId() + "."
                    + aerDto.getSituationTemplateId() + "]");
            aer = new AutomaticEvaluationResult();
            aer.copyFromDto(aerDto);
            aer.setSituationTemplate(template);
            log.info("end");
        }
        log.info("end");
        return aer;
    }

    private void storeResult(Boolean hasPeople, long requestDuration, String evaluationPath, Evidence ev, ObservedMetric om,
            SituationTemplate template, Integer pendingEvaluationId) throws ScopixException {

        AutomaticEvaluationResult aer = new AutomaticEvaluationResult(hasPeople, requestDuration, evaluationPath, ev, om,
                template, pendingEvaluationId);
        AddAutomaticEvaluationResultCommand addCommand = new AddAutomaticEvaluationResultCommand();
        addCommand.execute(aer);
        scopixCache.set("AER[EV" + ev.getId() + ".ST" + template.getId() + "]", 1800, aer.copyToDto());
        // getEvaluatorManager().getHashEvaluationAutomatic().put(, aer);

    }

    private String getCameraName(Evidence ev) {
        return ev.getEvidenceRequests().get(0).getEvidenceProvider().getDescription().toUpperCase().replace(" ", "");
    }

    private String getSituationTemplateFile(Evidence ev, SituationTemplate template) {
        return ev.getEvidenceRequests().get(0).getEvidenceProvider().getTemplatePath(template.getId());
    }

    private EvidenceEvaluation genEvidenceEvaluation(Integer evaluationResult, Evidence ev, ObservedMetric om, Integer peId,
            String evaluationUser, Date initEvaluation, Date endEvaluation) {
        log.debug("suma:" + evaluationResult + ", Evidence:" + ev.getId() + " , ObservedMetric:" + om.getId() + ", peId:" + peId
                + ", evaluationUser:" + evaluationUser);
        EvidenceEvaluation ee = new EvidenceEvaluation();
        ee.setEvaluationDate(new Date());
        ee.setEvaluationUser(evaluationUser);
        ee.setEvidenceResult(evaluationResult);
        ee.getEvidences().add(ev);
        ee.setObservedMetric(om);
        ee.setPendingEvaluation(new PendingEvaluation());
        ee.getPendingEvaluation().setId(peId);
        // se le agrega la fecha actual
        ee.setInitEvaluation(initEvaluation);
        ee.setEndEvaluation(endEvaluation);

        return ee;
    }

    private Proof generateProof(EvidenceEvaluation ee, Evidence ev, Integer evaluationResult, String proofPath,
            ObservedMetric om, String evidencePath) {
        String fileName = DateFormatUtils.format(ev.getEvidenceDate(), "yyyyMMdd_hhmm") + "_" + om.getMetric().getId() + "_"
                + ev.getId();
        String nameProof = getUtilities().genUniqueFile(proofPath, fileName, "jpg", null);

        Proof proof = new Proof();
        proof.setEvidenceEvaluation(ee);

        proof.setPathWithMarks("proofs_with_marks/" + nameProof);
        proof.setPathWithoutMarks("proofs/" + nameProof);

        proof.setProofDate(new Date());
        proof.setProofOrder(0);
        proof.setEvidence(ev);
        proof.setProofResult(evaluationResult);

        try {
            log.info("copiar original file " + evidencePath + " a " + proofPath + "\\" + proof.getPathWithoutMarks());
            log.info("copiar proof marks file " + evidencePath + " a " + proofPath + "\\" + proof.getPathWithMarks());
            FileUtils.copyFile(new File(evidencePath),
                    new File(FilenameUtils.separatorsToSystem(proofPath + "/" + proof.getPathWithoutMarks())), false);
            FileUtils.copyFile(new File(evidencePath),
                    new File(FilenameUtils.separatorsToSystem(proofPath + "/" + proof.getPathWithMarks())), false);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        }
        return proof;
    }

    /**
     * @return the utilities
     */
    public AnalyticsUCUtilities getUtilities() {
        if (utilities == null) {
            utilities = SpringSupport.getInstance().findBeanByClassName(AnalyticsUCUtilities.class);
        }
        return utilities;
    }

    /**
     * @param utilities
     *            the utilities to set
     */
    public void setUtilities(AnalyticsUCUtilities utilities) {
        this.utilities = utilities;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        processor = SpringSupport.getInstance().findBeanByClassName(GhostbusterRequestProcessor.class);
    }

    /**
     * Return HashSet
     */
    private Set<Integer> getSituationsEquals(Integer id) {

        Set<Integer> ret;
        try {
            Map<Integer, Set<Integer>> situationsEq = new HashMap<Integer, Set<Integer>>();
            String[] situationsEquals = getPropertiesConfiguration().getStringArray("situationEquals");
            for (String eq : situationsEquals) {
                String[] st = StringUtils.split(eq, "=");
                Integer stId = Integer.parseInt(st[0]);
                String[] others = StringUtils.split(st[1], "|");
                for (String other : others) {
                    Integer o = Integer.parseInt(other);
                    addDataMap(stId, o, situationsEq);
                }
            }
            ret = situationsEq.get(id);
        } catch (ScopixException e) {
            log.warn("");
            ret = new HashSet<Integer>();
        }
        return ret;
    }

    private static void addDataMap(Integer stId1, Integer stId2, Map<Integer, Set<Integer>> situationsEq) {
        if (situationsEq.containsKey(stId1)) {
            situationsEq.get(stId1).add(stId2);
        } else {
            Set<Integer> set = new HashSet<Integer>();
            set.add(stId2);
            situationsEq.put(stId1, set);
        }

        if (situationsEq.containsKey(stId2)) {
            situationsEq.get(stId2).add(stId1);
        } else {
            Set<Integer> set = new HashSet<Integer>();
            set.add(stId1);
            situationsEq.put(stId2, set);
        }
    }

}
