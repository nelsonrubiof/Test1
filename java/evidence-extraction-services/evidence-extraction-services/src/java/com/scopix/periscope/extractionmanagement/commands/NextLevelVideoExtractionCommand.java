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
 * AxisP3301ImageExtractionCommand.java
 *
 * Created on 16-08-2010, 04:25:15 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.NextLevelEvidenceProvider;
import com.scopix.periscope.extractionmanagement.NextLevelVideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.schedulermanagement.SchedulerManager;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
@SpringBean
public class NextLevelVideoExtractionCommand implements
        ProviderAdaptor<NextLevelVideoExtractionRequest, NextLevelEvidenceProvider> {

    private static Logger log = Logger.getLogger(NextLevelVideoExtractionCommand.class);
    private Date date;
    private SchedulerManager schedulerManager;
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private Set<String> alternativesFileName;
    private static final String EXTENSION = ".flv";

    public void execute(NextLevelVideoExtractionRequest extractionRequest, NextLevelEvidenceProvider evidenceProvider,
            ExtractEvidencePoolExecutor eepe, Date date, Set<String> alternativesFileName) throws ScopixException {
        this.extractEvidencePoolExecutor = eepe;
        this.alternativesFileName = alternativesFileName;
        this.date = date;
        prepareEvidence(extractionRequest, evidenceProvider);
    }

    @Override
    public void prepareEvidence(NextLevelVideoExtractionRequest evidenceRequest, NextLevelEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start evidenceRequestID:" + evidenceRequest.getId());
        try {
            long startUTC = 0;
            long endUTC = 0;
            Calendar dia = Calendar.getInstance();
            if (date != null) {
                dia.setTime(date);
            }

            /**
             * se utiliza creationTimestamp cuando está presente para solicitar evidencia del día que corresponde
             * en evidencia Auto_Generada
             */
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dia.setTime(evidenceRequest.getEvidenceDate()); //getCreationTimestamp()
            }

            /**
             * Si existe un TimeZone debemos calcular que hora es en el destino es decir de donde se recupera la imagen
             */
            Calendar evidenceDate = Calendar.getInstance();
            evidenceDate.setTimeInMillis(dia.getTimeInMillis());
            if (evidenceRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                dia.setTimeZone(TimeZone.getTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId()));

                double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId());
                Date newDateOfDiffHours = new Date(dia.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                evidenceDate.setTimeInMillis(newDateOfDiffHours.getTime());
                configureDate(evidenceDate, evidenceRequest);
            }

            configureDate(dia, evidenceRequest);

            startUTC = dia.getTimeInMillis();
            log.debug("startUTC: " + startUTC);

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(dia.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();
            endUTC = (startUTC + (evidenceRequest.getLengthInSecs() * 1000)) * 1000;
            String nameNextLevel = evidenceProvider.getUuid() + "_" + startUTC * 1000 + "_" + endUTC + EXTENSION;
            log.debug("solicitando " + nameNextLevel);
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + EXTENSION);
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(null); //lo dejamos en null para poder recuperarlo una vez recibido el file
                evidenceFile.setAlternativeFileName(nameNextLevel);
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                //le colocamos la hora que corresponde en el server destino si existe time zone difinido
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + EXTENSION);
                evidenceFile.setAlternativeFileName(nameNextLevel);
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }
            //recuperamos el store name asociado al request que se esta solicitando
            callNextLevel(startUTC, evidenceRequest.getExtractionPlan().getStoreName(), evidenceProvider, evidenceRequest);
            log.info("end");
        } catch (ParseException e) {
            throw new ScopixException(e.getMessage(), e);
        }
    }

    private void configureDate(Calendar dia, NextLevelVideoExtractionRequest evidenceRequest) throws ParseException {
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        Date requestedTime = DateUtils.parseDate(
                DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"),
                new String[]{"HH:mm:ss"});
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
    }

    private void callNextLevel(long startUTC, String storeName, NextLevelEvidenceProvider evidenceProvider,
            NextLevelVideoExtractionRequest evidenceRequest) throws ScopixException {
        log.info("start");
        Integer duration = evidenceRequest.getLengthInSecs();

        NextLevelVideoExtractionThread nlvet = new NextLevelVideoExtractionThread();

        nlvet.init(startUTC, duration, evidenceProvider.getUuid(), alternativesFileName, storeName,
                evidenceProvider.getUrlGateway(),
                evidenceProvider.getUserName(), evidenceProvider.getPassword()); //name,
        extractEvidencePoolExecutor.pause();
        extractEvidencePoolExecutor.runTask(nlvet);
        extractEvidencePoolExecutor.resume();

        log.info("end");
    }

    public SchedulerManager getSchedulerManager() {
        if (schedulerManager == null) {
            schedulerManager = SpringSupport.getInstance().findBeanByClassName(SchedulerManager.class);
        }
        return schedulerManager;
    }

    public void setSchedulerManager(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }
}
