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
 *  SituationCutJob.java
 * 
 *  Created on 06-06-2012, 04:57:07 PM
 * 
 */
package com.scopix.periscope.schedulermanagement;

import com.scopix.periscope.extractionmanagement.EvidenceProviderRequest;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.SituationExtractionRequest;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author nelson
 */
public class SituationCutJob implements Job {

    private static Logger log = Logger.getLogger(SituationCutJob.class.getName());
    public static final String CONTROLLER_INJECTION = "spring/automaticevidenceinjectioncontroller";
    public static final String SITUATION_EXTRACTION_REQUEST = "SITUATION_EXTRACTION_REQUEST";
    public static final String URL_PROPERTY = "SituationExtractionRequest.url";
    private ExtractionManager extractionManager;

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        log.info("start " + jec.getJobDetail().getKey().getName());

        String jobName = jec.getJobDetail().getKey().getName();

        JobDataMap jobData = jec.getJobDetail().getJobDataMap();
        SituationExtractionRequest request = (SituationExtractionRequest) jobData.get(SITUATION_EXTRACTION_REQUEST);
        //debemos llamar al controller de injeccion de Evidencias
        try {
            Calendar c = Calendar.getInstance();
            String cameraName = getRandCamera(request);
            String[] query = new String[6];
            query[0] = "sensorID=RANDOM_CAMERA_" + request.getSituationRequestRange().getSituationRequest().getId();
            query[1] = "date=" + DateFormatUtils.format(c, "yyyyMMdd"); //yyyyMMdd
            query[2] = "time=" + DateFormatUtils.format(request.getTimeSample(), "HHmm"); //HHmm
            query[3] = "delay=0";
            query[4] = "check_interval=FALSE";
            query[5] = "cameraName=" + URLEncoder.encode(cameraName, "UTF-8");
            //recuperar url desde properties
            String urlProperties = getUrl();
            String queryString = StringUtils.join(query, "&");
            log.debug("url=" + urlProperties + CONTROLLER_INJECTION + "?" + queryString);
            URL url = new URL(urlProperties + CONTROLLER_INJECTION + "?" + queryString);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                log.info(inputLine);
            }
            in.close();
        } catch (IOException e) {
            throw new JobExecutionException("Error executing job " + jobName + "." + e, e);
        }
        log.info("end");
    }

    /**
     * Retorna una camara en forma random desde la lista de camaras de la situacion
     */
    private String getRandCamera(SituationExtractionRequest request) {
        Set<String> cameras = new HashSet<String>();

        List<EvidenceProviderRequest> list = request.getSituationRequestRange().
                getSituationRequest().getEvidenceProviderRequests();
        log.debug("EvidenceProviderRequests.size:" + list.size());
        for (EvidenceProviderRequest epr : list) {            
            cameras.add(epr.getEvidenceProvider().getDescription());
        }
        log.debug("cameras:" + cameras.size());
        String name = null;
        if (cameras.size() > 0) {
            String[] names = cameras.toArray(new String[0]);
            int position = (int) (Math.random() * 100) % cameras.size();
            name = names[position];
        }
        if (name == null || name.length() == 0) {
            log.error("no se recupera name de camara");
            name = "";
        }
        return name;
    }

    private String getUrl() {
        String ret = getExtractionManager().getStringProperties(URL_PROPERTY);
        return ret;

    }

    public ExtractionManager getExtractionManager() {
        if (extractionManager == null) {
            extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        }
        return extractionManager;
    }

    public void setExtractionManager(ExtractionManager extractionManager) {
        this.extractionManager = extractionManager;
    }
}
