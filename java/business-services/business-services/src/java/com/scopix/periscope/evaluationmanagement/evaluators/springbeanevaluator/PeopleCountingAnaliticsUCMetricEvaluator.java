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
 *  PeopleCountingAnaliticsUCMetricEvaluator.java
 * 
 *  Created on 25-01-2012, 10:38:05 AM
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
import com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc.PeopleCountingAnaliticsUCDetection;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author nelson
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_MT, description
        = "Recupera el valor del analisis UC")
@SpringBean(rootClass = PeopleCountingAnaliticsUCMetricEvaluator.class)
public class PeopleCountingAnaliticsUCMetricEvaluator extends AbstractEvidenceEvaluatorForMT {

    private static Logger log = Logger.getLogger(PeopleCountingAnaliticsUCMetricEvaluator.class);
    private String urlAnalytics;
    private JCIFSUtil jcifsUtil;
    private Integer maxDetections;
    private PropertiesConfiguration configuration;
    private AnalyticsUCUtilities utilities;

    @Override
	public Boolean evaluate(ObservedMetric om, Integer pendingEvaluationId) throws ScopixException {
        log.info("start");
        //recorremos todos las evidencias de este OM 
        Integer evaluationResult = null;
        initialize();
        for (Evidence ev : om.getEvidences()) {
            //y llamamos a la dll pasando la ruta de la evidencia
            //cliente, idCamara, rutacompleta
            String datePath = DateFormatUtils.format(ev.getEvidenceDate(), "yyyy\\MM\\dd");
            String evidencePath = ev.getEvidenceServicesServer().getEvidencePath() + om.getMetric().getStore().
                    getCorporate().getName()
                    + "\\" + om.getMetric().getStore().getName() + "\\" + datePath + "\\" + ev.getEvidencePath();
            String url = getUrlAnalytics() + "/analytics.php"
                    + "?corporate=" + om.getMetric().getStore().getCorporate().getName()
                    + "&cameraID=" + ev.getEvidenceRequests().get(0).getEvidenceProvider().getId()
                    + "&path=" + evidencePath;
            Date initEvaluation = new Date();
            getUtilities().sendUrl(url);
            //si ret = FIN OK - a definir buscamos el xml asociado con el nombre de la imagen

            String evidenceName = FilenameUtils.getBaseName(ev.getEvidencePath());
            String xmlName = evidenceName + ".xml";
            String folder = "/" + om.getMetric().getStore().getCorporate().getName()
                    + "/" + ev.getEvidenceRequests().get(0).getEvidenceProvider().getId();
            String ret = getUtilities().readUrl(getUrlAnalytics() + folder + "/" + xmlName);
            //parseamos el xml y obtenemos las cantidades para tomar la desicion
            evaluationResult = evaluateResultUC(ret, pendingEvaluationId);
            //modificar booleano evaluationOK
            Date endEvaluation = new Date();

            //si es OK
            String fileName = evidenceName + ".jpg";
            if (evaluationResult != null) {
                //recuperamos el file marcado
                File f = null;
                if (evaluationResult > 0) {
                    f = getUtilities().readFile(getUrlAnalytics() + folder + "/" + fileName);
                } else {
                    log.debug("no existen resultados se hace referencia al archivo original desde " + evidencePath);
//                    try {
                    f = getEvidenceFile(evidencePath);
//                        f = File.createTempFile("tmpAnalytics", v".jpg");
//                        FileUtils.copyFile(new File(evidencePath), f);
//                    } catch (IOException e) {
//                        log.debug("Error recuperando Evidence File " + e, e);
//                    }
                }

                //generamos el ee
                EvidenceEvaluation ee = genEvidenceEvaluation(evaluationResult, ev, om, pendingEvaluationId,
                        this.getClass().getSimpleName(), initEvaluation, endEvaluation);
                //ultimo valor sacado desde el parseo del XML
                String proodPath = ev.getEvidenceServicesServer().getProofPath()
                        + om.getMetric().getStore().getCorporate().getName() + "\\"
                        + om.getMetric().getStore().getName() + "\\" + datePath;

                Proof p = generateProof(ee, ev, f, evaluationResult, proodPath, om, evidencePath);

                if (ee.getProofs() == null) {
                    ee.setProofs(new ArrayList<Proof>());
                }
                ee.getProofs().add(p);

                //agregamos el ee a la lista para ser devuelta al que nos llamo
                ee.setObservedMetric(om);
                om.getEvidenceEvaluations().add(ee);
            }
            //Si es NOOK o despues de todo
            String urlDelete = getUrlAnalytics() + "/delete.php?name=" + evidenceName + "&folder=" + folder;
            getUtilities().sendUrl(urlDelete);
            if (evaluationResult == null) {
                //salimos con error
                throw new ScopixException("Error resultado no recibido");
            }

        }
        log.info("end");
		return true;

    }

    private EvidenceEvaluation genEvidenceEvaluation(int suma, Evidence ev, ObservedMetric om, Integer pendingEvaluationId,
            String evaluationUser, Date initEvaluation, Date endEvaluation) {
        log.debug("suma:" + suma + ", Evidence:" + ev.getId() + " , ObservedMetric:" + om.getId()
                + ", pendingEvaluationId:" + pendingEvaluationId + ", evaluationUser:" + evaluationUser);
        EvidenceEvaluation ee = new EvidenceEvaluation();
        ee.setEvaluationDate(new Date());
        ee.setEvaluationUser(evaluationUser);
        ee.setEvidenceResult(suma);
        ee.getEvidences().add(ev);
        ee.setObservedMetric(om);
        ee.setPendingEvaluation(new PendingEvaluation());
        ee.getPendingEvaluation().setId(pendingEvaluationId);
        //se le agrega la fecha actual
        ee.setInitEvaluation(initEvaluation);
        ee.setEndEvaluation(endEvaluation);

        return ee;
        //getDao().save(ee);
    }

    private Proof generateProof(EvidenceEvaluation ee, Evidence ev, File f, Integer result, String proofPath, ObservedMetric om,
            String evidencePath) {
        String fileName = DateFormatUtils.format(ev.getEvidenceDate(), "yyyyMMdd_hhmm")
                + "_" + om.getMetric().getId() + "_" + ev.getId();
        String nameProof = getUtilities().genUniqueFile(proofPath, fileName, "jpg", null);

        Proof proof = new Proof();
        proof.setEvidenceEvaluation(ee);
        proof.setPathWithMarks("proofs_with_marks\\" + nameProof); //f.name //copia de la evidencia
        proof.setPathWithoutMarks("proofs\\" + nameProof); //archivo evaluado por aplicacion
        proof.setProofDate(new Date()); //Date when proof is created in the system
        proof.setProofOrder(0);
        proof.setEvidence(ev);
        proof.setProofResult(result);

        try {
            log.info("copiar file " + evidencePath + " a " + proofPath + "\\" + proof.getPathWithoutMarks());
            log.info("copiar file " + f.getAbsolutePath() + " a " + proofPath + "\\" + proof.getPathWithMarks());

            File evidenceFile = getEvidenceFile(evidencePath);
            getJcifsUtil().createFileSmb(evidenceFile, proofPath + "\\" + proof.getPathWithoutMarks());
            getJcifsUtil().createFileSmb(f, proofPath + "\\" + proof.getPathWithMarks());

            //borramos el temporal
            FileUtils.forceDelete(f);
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (ScopixException e) {
            log.error("ScopixException " + e, e);
        }
        //addProofCommand.execute(proof);
        return proof;
    }

    private Integer evaluateResultUC(String xml, Integer pendingEvaluationId) {

        //primero parseamos el xml
        List<PeopleCountingAnaliticsUCDetection> detections = getUtilities().parseXmlUC(xml);
        //segun datos retornamos true o false
        Integer cantDetections = null;

        if (detections != null) {
            cantDetections = detections.size();
        }

        //almacenar data
        SaveResultAnalyticsCommand saveResultAnalyticsCommand = new SaveResultAnalyticsCommand();
        saveResultAnalyticsCommand.execute(xml, pendingEvaluationId, cantDetections, ResultAnalyticsType.IMAGE_ANALYTICS);

        if (cantDetections != null && cantDetections > 15) {// 0 && cantDetections != 1) {
            //esto significa que la evaluacion no pasa a quality sino a Operator
            cantDetections = null;
        }
        return cantDetections;
    }

    private void initialize() {
        try {
            configuration = new PropertiesConfiguration("system.properties");
            configuration.setReloadingStrategy(new FileChangedReloadingStrategy());

        } catch (ConfigurationException e) {
            log.error("NO se puede levantar propertie URL_COUNTER_SAFEWAY_ANALYTICS " + e, e);
        }
    }

    public JCIFSUtil getJcifsUtil() {
        if (jcifsUtil == null) {
            jcifsUtil = SpringSupport.getInstance().findBeanByClassName(JCIFSUtil.class);
        }

        return jcifsUtil;
    }

    public void setJcifsUtil(JCIFSUtil jcifsUtil) {
        this.jcifsUtil = jcifsUtil;
    }

    private File getEvidenceFile(String evidencePath) {
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
            log.error("ScopixException no es posible recuperar " + evidencePath, e);
        }
        return f;
    }

    /**
     * @return the maxDetections
     */
    public Integer getMaxDetections() {
        if (configuration == null) {
            initialize();
        }
        maxDetections = configuration.getInt("IMAGE_ANALYTICS_MAX_DETECTION");
        return maxDetections;
    }

    /**
     * @param maxDetections the maxDetections to set
     */
    public void setMaxDetections(Integer maxDetections) {
        this.maxDetections = maxDetections;
    }

    /**
     * @return the urlAnalytics
     */
    public String getUrlAnalytics() {
        if (configuration == null) {
            initialize();
        }
        // recargamos siempre el valor
        urlAnalytics = configuration.getString("IMAGE_ANALYTICS_URL");

        return urlAnalytics;
    }

    /**
     * @param urlAnalytics the urlAnalytics to set
     */
    public void setUrlAnalytics(String urlAnalytics) {
        this.urlAnalytics = urlAnalytics;
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
