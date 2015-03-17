/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * VideoExtractionCommand.java
 *
 * Created on 22-05-2008, 06:01:14 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.BroadwareEvidenceProvider;
import com.scopix.periscope.extractionmanagement.BroadwareImageExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author marko.perich
 */
@SpringBean
public class BroadwareImageExtractionCommand implements
        ProviderAdaptor<BroadwareImageExtractionRequest, BroadwareEvidenceProvider> {

    private static Logger log = Logger.getLogger(BroadwareImageExtractionCommand.class);
    private String broadwareCallback;
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private Date date;

    public BroadwareImageExtractionCommand() {
        if (broadwareCallback == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                broadwareCallback = prop.getProperty("BroadwareCallback");
            } catch (IOException e) {
                log.warn("[BroadwareCallback]", e);
            }

        }
    }

    public void execute(BroadwareImageExtractionRequest imageER, BroadwareEvidenceProvider evProv,
            ExtractEvidencePoolExecutor eepe, Date date) throws ScopixException {
        extractEvidencePoolExecutor = eepe;
        this.date = date;
        prepareEvidence(imageER, evProv);
    }

    @Override
    public void prepareEvidence(BroadwareImageExtractionRequest evidenceRequest, BroadwareEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {
            long startUTC = 0;
            long stopUTC = 0;


            Calendar dia = Calendar.getInstance();
            if (date != null) {
                dia.setTime(date);
            }

            // se utiliza creationTimestamp cuando est� presente para solicitar evidencia del d�a que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dia.setTime(evidenceRequest.getEvidenceDate()); //getCreationTimestamp()
            }

            dia.set(Calendar.HOUR_OF_DAY, 0);
            dia.set(Calendar.MINUTE, 0);
            dia.set(Calendar.SECOND, 0);
            dia.set(Calendar.MILLISECOND, 0);

            Date requestedTime = DateUtils.parseDate(
                    DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"), new String[]{"HH:mm:ss"});
            Calendar hora = Calendar.getInstance();
            hora.setTime(requestedTime);
            log.debug("hora: " + hora.getTime());

            //agregamos los valores al dia ya que la hora esta para 01-01-1970 y esto puede estar en otra zona horaria
            dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
            dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
            dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(dia.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();

//            startUTC = (dia.getTimeInMillis() + hora.getTimeInMillis()) + (dia.get(Calendar.ZONE_OFFSET) + hora.get(
//                    Calendar.DST_OFFSET));
            startUTC = dia.getTimeInMillis();
            log.debug("startUTC: " + startUTC);

            stopUTC = startUTC + 1;
            String serverIP = evidenceProvider.getIpAddress();
            String source = evidenceProvider.getLoopName();
            //agregamos el store name para el callback
            String notifyURL = broadwareCallback + "spring/broadwareimagefileready?filename=" + name;

            String url = "http://" + serverIP
                    + "/cgi-bin/smanager.bwt?command=save"
                    + "&source=" + source
                    + "&savemode=local&name=" + name
                    + "&startUTC=" + startUTC
                    + "&stopUTC=" + stopUTC
                    + "&saveformat=bwm"
                    + "&notifyURL=" + notifyURL;

            //create task and pass to pool executor
            BroadwareImageExtractionThread biet = new BroadwareImageExtractionThread();
            biet.init(url);
            extractEvidencePoolExecutor.runTask(biet);

            //register evidence file
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + ".jpg");
            if (evidenceFile == null) {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(startUTC));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + ".jpg");
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }
}
