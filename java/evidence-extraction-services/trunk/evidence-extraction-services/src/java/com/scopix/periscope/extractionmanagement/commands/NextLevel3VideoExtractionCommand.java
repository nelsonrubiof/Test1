package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.NextLevel3EvidenceProvider;
import com.scopix.periscope.extractionmanagement.NextLevel3VideoExtractionRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.nextlevel.NextLevelManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.schedulermanagement.SchedulerManager;
import java.text.ParseException;
import java.util.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/*
 * 
 * Copyright (c) 2012, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 * Clase para invocar operaciones para extracción de evidencias para la nueva versión
 * de integración entre EES y NextLevel
 *
 * @author    carlos polo
 * @created   19-oct-2012
 *
 */
@SpringBean
public class NextLevel3VideoExtractionCommand implements
        ProviderAdaptor<NextLevel3VideoExtractionRequest, NextLevel3EvidenceProvider> {

    private Date date;
    private Set<String> alternativesFileName;
    private SchedulerManager schedulerManager;
    private ExtractEvidencePoolExecutor extractEvidencePoolExecutor;
    private static Logger log = Logger.getLogger(NextLevel3VideoExtractionCommand.class);
    private static final String PROPERTIES_NEXTLEVEL3_VIDEOFORMAT = "nextLevel3.videoFormat";

    public void execute(NextLevel3VideoExtractionRequest extractionRequest, NextLevel3EvidenceProvider evidenceProvider,
            ExtractEvidencePoolExecutor eepe, Date date, Set<String> alternativesFileName) throws ScopixException {

        this.date = date;
        this.alternativesFileName = alternativesFileName;
        this.extractEvidencePoolExecutor = eepe;
        prepareEvidence(extractionRequest, evidenceProvider);
    }

    @Override
    public void prepareEvidence(NextLevel3VideoExtractionRequest evidenceRequest, 
    NextLevel3EvidenceProvider evidenceProvider) throws ScopixException {

        log.info("Inicio prepareEvidence, evidenceRequestID: " + evidenceRequest.getId());
        try {
            long startUTC = 0;
            long endUTC = 0;
            Calendar dia = Calendar.getInstance();
            if (date != null) {
                dia.setTime(date);
            }

            /**
             * se utiliza creationTimestamp cuando está presente para solicitar evidencia del día que corresponde en evidencia
             * Auto_Generada
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

            //the name of the clip must be in local time
            String name = DateFormatUtils.format(dia.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();
            endUTC = (startUTC + (evidenceRequest.getLengthInSecs() * 1000)) * 1000;

            String suffix = getExtractionManager().getStringProperties(PROPERTIES_NEXTLEVEL3_VIDEOFORMAT);

            String nameNextLevel = evidenceProvider.getUuid() + "_" + startUTC * 1000 + "_" + endUTC + suffix;
            log.debug("Solicitando: " + nameNextLevel);

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + suffix);

            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(null); //lo dejamos en null para poder recuperarlo una vez recibido el file
                evidenceFile.setAlternativeFileName(nameNextLevel);
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                //le colocamos la hora que corresponde en el server destino si existe time zone difinido
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + suffix);
                evidenceFile.setAlternativeFileName(nameNextLevel);
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }

            //Adiciona la relación del archivo solicitado con la hora de solicitud, para realizar la medición
            //del timeOut al momento de escuchar los eventos generados.
            HashMap<String, Calendar> hmArchivosTimeOut = getNextLevelManager().getHmArchivosTimeOut();
            hmArchivosTimeOut.put(nameNextLevel, Calendar.getInstance());

            //recuperamos el store name asociado al request que se esta solicitando
            callNextLevel(startUTC, evidenceRequest.getExtractionPlan().getStoreName(), evidenceProvider, evidenceRequest);
            log.info("end");
        } catch (ParseException e) {
            throw new ScopixException(e.getMessage(), e);
        }
    }

    private void callNextLevel(long startUTC, String storeName, NextLevel3EvidenceProvider evidenceProvider,
            NextLevel3VideoExtractionRequest evidenceRequest) throws ScopixException {
        log.info("Inicio callNextLevel");
        Integer duration = evidenceRequest.getLengthInSecs();

        NextLevel3VideoExtractionThread nlvet = new NextLevel3VideoExtractionThread();

        nlvet.init(startUTC, duration, evidenceProvider.getUuid(), alternativesFileName, storeName,
                evidenceProvider.getUrlGateway(), evidenceProvider.getUserName(), evidenceProvider.getPassword());

        extractEvidencePoolExecutor.pause();
        extractEvidencePoolExecutor.runTask(nlvet);
        extractEvidencePoolExecutor.resume();

        log.info("Fin callNextLevel");
    }

    private void configureDate(Calendar dia, NextLevel3VideoExtractionRequest evidenceRequest) throws ParseException {
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

    public SchedulerManager getSchedulerManager() {
        if (schedulerManager == null) {
            schedulerManager = SpringSupport.getInstance().findBeanByClassName(SchedulerManager.class);
        }
        return schedulerManager;
    }

    public void setSchedulerManager(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    /**
     * Obtiene única instancia del tipo NextLevelManager
     *
     * @author carlos polo
     * @version 3.0
     * @return NextLevelManager
     * @date 22-oct-2012
     */
    public NextLevelManager getNextLevelManager() {
        return SpringSupport.getInstance().findBeanByClassName(NextLevelManager.class);
    }

    /**
     * Obtiene única instancia del tipo ExtractionManager
     *
     * @author carlos polo
     * @version 3.0
     * @return ExtractionManager
     * @date 24-oct-2012
     */
    public ExtractionManager getExtractionManager() {
        return SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
    }
}