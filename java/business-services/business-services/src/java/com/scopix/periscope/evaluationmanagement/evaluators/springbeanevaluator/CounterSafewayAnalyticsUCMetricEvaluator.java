/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  CounterSafewayAnalyticsUCMetricEvaluator.java
 * 
 *  Created on 12-02-2013, 12:38:10 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.JCIFSUtil;
import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.evaluationmanagement.ResultAnalyticsType;
import com.scopix.periscope.evaluationmanagement.commands.SaveResultAnalyticsCommand;
import com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc.AnalyticsUCUtilities;
import com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc.CounterSafewayAnalyticsUCDetection;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_MT, description
        = "Recupera el valor del conteo realizado para cajas por Analytics UC")
@SpringBean(rootClass = CounterSafewayAnalyticsUCMetricEvaluator.class)
public class CounterSafewayAnalyticsUCMetricEvaluator extends AbstractEvidenceEvaluatorForMT {

    private static Logger log = Logger.getLogger(CounterSafewayAnalyticsUCMetricEvaluator.class);
    private String urlCounterAnalytics;
    private JCIFSUtil jcifsUtil;
    private PropertiesConfiguration configuration;
    private Integer maxDetections;
    private AnalyticsUCUtilities utilities;

    /**
     * evalua un pending evaluation con algoritmo de Counter desarrollado por Pablo Lluch
     *
     * @param om
     * @param peId
     * @throws PeriscopeException
     */
    @Override
    public Boolean evaluate(ObservedMetric om, Integer peId) throws ScopixException {
        log.info("start [om:" + om.getId() + "][peId:" + peId + "]");
        //recorremos todos las evidencias de este OM 
        Integer evaluationResult = null;
        initialize();
        List<EvidenceEvaluation> evaluations = new ArrayList<EvidenceEvaluation>();
        log.debug("evidences:" + om.getEvidences().size());
        for (Evidence ev : om.getEvidences()) {
            log.debug("[ev.id:" + ev.getId() + "][ev.getEvidencePath:" + ev.getEvidencePath() + "]");
            //y llamamos a la dll pasando la ruta de la evidencia
            //cliente, tienda, nombre, rutacompleta de imagen

            String datePath = DateFormatUtils.format(ev.getEvidenceDate(), "yyyy\\MM\\dd");
            String evidencePath = ev.getEvidenceServicesServer().getEvidencePath()
                    + om.getMetric().getStore().getCorporate().getName() + "\\"
                    + om.getMetric().getStore().getName() + "\\"
                    + datePath + "\\"
                    + ev.getEvidencePath();
            String url = null;
            try {
                url = getUrlCounterAnalytics() + "/counter.php"
                        + "?corporate=" + om.getMetric().getStore().getCorporate().getName()
                        + "&storeName=" + om.getMetric().getStore().getName()
                        + "&cameraName=" + ev.getEvidenceRequests().get(0).getEvidenceProvider().getName()
                        + "&path=" + URLEncoder.encode(evidencePath, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("no parse url for Analytics UC " + e, e);
            }
            Date initEvaluation = new Date();

            String evidenceName = FilenameUtils.getBaseName(ev.getEvidencePath());
            String xmlName = evidenceName + ".xml";
            String folder = "/" + om.getMetric().getStore().getCorporate().getName()
                    + "/" + om.getMetric().getStore().getName()
                    + "/" + ev.getEvidenceRequests().get(0).getEvidenceProvider().getName();
            //leemos la ejecucion esperando un 0
            Integer retEvaluacion = Integer.valueOf(getUtilities().readUrl(url));
            if (retEvaluacion == 0) {
                String ret = getUtilities().readUrl(getUrlCounterAnalytics() + folder + "/" + xmlName);
                //parseamos el xml y obtenemos las cantidades para tomar la desicion
                evaluationResult = evaluateResultAnalytics(ret, peId);
                //modificar booleano evaluationOK
                Date endEvaluation = new Date();

                //si es OK
                String fileName = evidenceName + ".jpg";
                if (evaluationResult != null) {
                    //recuperamos el file marcado
                    File f;
                    if (evaluationResult >= 0) { //asi copiamos desde el server 
                        f = getUtilities().readFile(getUrlCounterAnalytics() + folder + "/" + fileName);
                    } else {
                        log.debug("no existen resultados se hace referencia al archivo original desde " + evidencePath);
                        f = getTempEvidenceFile(evidencePath);
                    }

                    //generamos el ee
                    EvidenceEvaluation ee = genEvidenceEvaluation(evaluationResult, ev, om, peId, "CounterAnalyticsUCMetricEvaluator",
                            initEvaluation, endEvaluation);
                    //ultimo valor sacado desde el parseo del XML
                    String proofPath = ev.getEvidenceServicesServer().getProofPath()
                            + om.getMetric().getStore().getCorporate().getName() + "\\"
                            + om.getMetric().getStore().getName() + "\\" + datePath;

                    Proof p = generateProof(ee, ev, f, evaluationResult, proofPath, om, evidencePath);

                    if (ee.getProofs() == null) {
                        ee.setProofs(new ArrayList<Proof>());
                    }
                    ee.getProofs().add(p);

                    //agregamos el ee a la lista para ser devuelta al que nos llamo
                    ee.setObservedMetric(om);
                    evaluations.add(ee);
                }
            }
            //Si es NOOK o despues de todo
            String urlDelete = getUrlCounterAnalytics() + "/delete.php?name=" + evidenceName + "&folder=" + folder;
            getUtilities().sendUrl(urlDelete);
            if (evaluationResult == null) {
                //salimos con error
                return false;
            }
        }
        om.getEvidenceEvaluations().addAll(evaluations);
        log.info("end");
        return true;
    }

    private void initialize() {
        try {
            configuration = new PropertiesConfiguration("system.properties");
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());

        } catch (ConfigurationException e) {
            log.error("NO se puede levantar propertie URL_COUNTER_ANALYTICS " + e, e);
        }
    }

    private Integer evaluateResultAnalytics(String xml, Integer peId) {
        //primero parseamos el xml
        CounterSafewayAnalyticsUCDetection detection = getUtilities().parseXmlCounterSafewayAnalytics(xml);
        //segun datos retornamos la cantidad o null
        Integer cantDetections = null;

        if (detection != null) {
            cantDetections = detection.getOpenCounters();
        }

        //almacenar data
        //revisar donde almacenar   
        SaveResultAnalyticsCommand saveResultAnalyticsCommand = new SaveResultAnalyticsCommand();
        saveResultAnalyticsCommand.execute(xml, peId, cantDetections, ResultAnalyticsType.COUNTER_ANALYTICS);

        if (cantDetections != null && cantDetections > getMaxDetections()) { //dejar properties autoload // 0 && cantDetections != 1) {
            //esto significa que la evaluacion no pasa a quality sino a Operator
            cantDetections = null;
        }
        return cantDetections;
    }

    private File getTempEvidenceFile(String evidencePath) {
        File f = null;
        try {
            f = File.createTempFile("test", "." + FilenameUtils.getExtension(evidencePath));
            Map<String, Object> map = getJcifsUtil().getFileSmb(evidencePath);
            InputStream inputStream = (InputStream) map.get("is");
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(f.getAbsolutePath());

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            inputStream.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("IOException no es posible recuperar " + evidencePath, e);
        } catch (ScopixException e) {
            log.error("PeriscopeException no es posible recuperar " + evidencePath, e);
        }
        return f;
    }

    /**
     * @return the jcifsUtil
     */
    public JCIFSUtil getJcifsUtil() {
        if (jcifsUtil == null) {
            jcifsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        }
        return jcifsUtil;
    }

    /**
     * @param jcifsUtil the jcifsUtil to set
     */
    public void setJcifsUtil(JCIFSUtil jcifsUtil) {
        this.jcifsUtil = jcifsUtil;
    }

    private EvidenceEvaluation genEvidenceEvaluation(Integer evaluationResult, Evidence ev, ObservedMetric om, Integer peId,
            String evaluationUser, Date initEvaluation, Date endEvaluation) {
        log.debug("suma:" + evaluationResult + ", Evidence:" + ev.getId() + " , ObservedMetric:" + om.getId()
                + ", peId:" + peId + ", evaluationUser:" + evaluationUser);
        EvidenceEvaluation ee = new EvidenceEvaluation();
        ee.setEvaluationDate(new Date());
        ee.setEvaluationUser(evaluationUser);
        ee.setEvidenceResult(evaluationResult);
        ee.getEvidences().add(ev);
        ee.setObservedMetric(om);
        ee.setPendingEvaluation(new PendingEvaluation());
        ee.getPendingEvaluation().setId(peId);
        //se le agrega la fecha actual
        ee.setInitEvaluation(initEvaluation);
        ee.setEndEvaluation(endEvaluation);

        return ee;
    }

    private Proof generateProof(EvidenceEvaluation ee, Evidence ev, File f, Integer evaluationResult, String proofPath,
            ObservedMetric om, String evidencePath) {
        String fileName = DateFormatUtils.format(ev.getEvidenceDate(), "yyyyMMdd_hhmm") + "_" + om.getMetric().getId() + "_"
                + ev.getId();
        String nameProof = getUtilities().genUniqueFile(proofPath, fileName, "jpg", null);

        Proof proof = new Proof();
        proof.setEvidenceEvaluation(ee);
        proof.setPathWithMarks("proofs_with_marks\\" + nameProof); //f.name //copia de la evidencia
        proof.setPathWithoutMarks("proofs\\" + nameProof); //archivo evaluado por aplicacion
        proof.setProofDate(new Date()); //Date when proof is created in the system
        proof.setProofOrder(0);
        proof.setEvidence(ev);
        proof.setProofResult(evaluationResult);
        String fisicalProofPath = getProofPath(ev, om);
        String fisicalEvidencePath = getEvidencePath(ev, om) + "/" + ev.getEvidencePath();

        try {
            File tempEvidenceFile = getTempEvidenceFile(evidencePath);
            log.info("copiar original file " + fisicalEvidencePath
                    + " a " + fisicalProofPath + "\\" + proof.getPathWithoutMarks());
            FileUtils.copyFile(tempEvidenceFile,
                    new File(FilenameUtils.separatorsToSystem(fisicalProofPath + "/" + proof.getPathWithoutMarks())));
            log.info("copiar proof marks file " + f.getAbsolutePath()
                    + " a " + fisicalProofPath + "\\" + proof.getPathWithMarks());
            FileUtils.copyFile(f, new File(FilenameUtils.separatorsToSystem(fisicalProofPath + "/" + proof.getPathWithMarks())));
//            getJcifsUtil().createFileSmb(tempEvidenceFile, proofPath + "\\" + proof.getPathWithoutMarks());
//            getJcifsUtil().createFileSmb(f, proofPath + "\\" + proof.getPathWithMarks());
            //borramos el temporal
            FileUtils.forceDelete(f);
            //borramos la copía temporal de la evidencia original
            FileUtils.forceDelete(tempEvidenceFile);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        }
//        } catch (ScopixException e) {
//            log.error("PeriscopeException " + e, e);
//        }
        return proof;
    }

    /**
     * @return the urlCounterAnalytics
     */
    public String getUrlCounterAnalytics() {
        if (configuration == null) {
            initialize();
        }
        // recargamos siempre el valor
        urlCounterAnalytics = configuration.getString("COUNTER_ANALYTICS_URL");

        return urlCounterAnalytics;
    }

    /**
     * @param urlCounterAnalytics the urlCountingAnalytics to set
     */
    public void setUrlCounterAnalytics(String urlCounterAnalytics) {
        this.urlCounterAnalytics = urlCounterAnalytics;
    }

    /**
     * @return the maxDetections
     */
    public Integer getMaxDetections() {
        if (configuration == null) {
            initialize();
        }
        maxDetections = configuration.getInt("COUNTER_ANALYTICS_MAX_DETECTION");
        return maxDetections;
    }

    /**
     * @param maxDetections the maxDetections to set
     */
    public void setMaxDetections(Integer maxDetections) {
        this.maxDetections = maxDetections;
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
     * @param utilities the utilities to set
     */
    public void setUtilities(AnalyticsUCUtilities utilities) {
        this.utilities = utilities;
    }
}
