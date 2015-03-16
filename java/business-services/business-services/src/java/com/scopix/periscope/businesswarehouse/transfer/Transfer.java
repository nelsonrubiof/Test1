/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Transfer.java
 *
 * Created on 30-09-2008, 12:04:55 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.configuration.ConversionException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class Transfer implements Job {

    private static final String START = "start";
    private static final String END = "end";
    private static Logger log = Logger.getLogger(Transfer.class.getName());
    private static boolean working = false;
    private TransferManager transferManager;
    private HashMap hmParameters;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(START);
        if (!isWorking()) {
            setWorking(true);
            try {
                if (validateExecution(context)) {
                    String endDateParameter = context.getJobDetail().getJobDataMap().getString("END_DATE");

                    //carga de los parametros y se almacenan en un hashmap
                    loadProcessParameters(endDateParameter);

                    //transferencia de datos de menor cantidad
                    transferInitialDataforAuditing();

                    //transferencia de datos de mayor cantidad. Estos dependen de los anteriores
                    transferDataForAuditing();

                    //proceso final. Limpieza y refresco de datos
                    finishTransferDataForAuditing();

                }
            } catch (ScopixException e) {
                log.error("Error: " + e.getMessage());
                throw new JobExecutionException(e);
            } catch (Exception ex) {
                //se deja la captura de esta excepción en caso de algun error no controlado debido a que si ocurre, el job
                //se queda pegado.
                log.error("Error: " + ex.getMessage());
                throw new JobExecutionException(ex);
            } finally {
                setWorking(false);
            }
        } else {
            log.info("Transfer in progress... exit");
        }
        log.info(END);
    }

    /**
     * Se finaliza la transferencia limpiando datos rechazados y evidencesAndProofs que quedan sin asociacion en BW
     * y se llama a mondrian.
     */
    private void finishTransferDataForAuditing() {
        log.info(START);
        String mondrianCacheURL = (String) hmParameters.get(TransferManager.MONDRIAN_CACHE_URL);

        getTransferManager().deleteFromRejectError();
        getTransferManager().cleanEvidenceAndProofs();
        if (mondrianCacheURL != null && mondrianCacheURL.length() > 0) {
            getTransferManager().refreshMondrianCache(mondrianCacheURL);
        }
        log.info(END);
    }

    /**
     * Transferencia de compliance facts y data de proofs
     * 
     * @throws PeriscopeException
     */
    private void transferDataForAuditing() throws ScopixException {
        log.info(START);
        int maxAttempt = (Integer) hmParameters.get(TransferManager.MAX_ATTEMPT);
        long intervalAttempt = (Long) hmParameters.get(TransferManager.INTERVAL_ATTEMPT);
        Date endDate = (Date) hmParameters.get(TransferManager.END_DATE);
        Date uploadProofPivotDate = (Date) hmParameters.get(TransferManager.UPLOAD_PROOF_PIVOT_DATE);
        int cont = 0;
        while (true) {
            try {
                getTransferManager().transferComplianceFactAndIndicatorValues(endDate);
                getTransferManager().transferProofsData(endDate, uploadProofPivotDate);

                break;
            } catch (ScopixException e) {
                log.warn("Problema en la transferencia de datos...", e);
                if (cont < maxAttempt) {
                    cont++;
                    log.debug("reintento " + cont + " de " + maxAttempt);
                    try {
                        Thread.sleep(intervalAttempt);
                    } catch (InterruptedException ex) {
                        log.error(ex, ex);
                        throw new ScopixException("Error en Sleep", ex);
                    }
                } else {
                    log.error("Transferencia de datos superó el máximo de reintentos", e);
                    throw new ScopixException("Transferencia superó el máximo de reintentos", e);
                }
            }
        }
        log.info(END);
    }

    /**
     * Transferencia de datos iniciales necesarios para mostrar Auditing
     * 
     * @throws PeriscopeException
     */
    private void transferInitialDataforAuditing() throws ScopixException {
        log.info(START);
        int maxAttempt = (Integer) hmParameters.get(TransferManager.MAX_ATTEMPT);
        long intervalAttempt = (Long) hmParameters.get(TransferManager.INTERVAL_ATTEMPT);
        String[] weekEndDays = (String[]) hmParameters.get(TransferManager.WEEK_END_DAYS);
        String weekName = (String) hmParameters.get(TransferManager.WEEK_NAME);
        String weekEndDesc = (String) hmParameters.get(TransferManager.WEEK_END_DESC);
        String weekDayDesc = (String) hmParameters.get(TransferManager.WEEK_DAY_DESC);
        Integer firstDayOfWeek = (Integer) hmParameters.get(TransferManager.FIRST_DAY_OF_WEEK);
        Date endDate = (Date) hmParameters.get(TransferManager.END_DATE);
        String corporateLocale = (String) hmParameters.get(TransferManager.CORPORATE_LOCALE);
        String pivotDate = (String) hmParameters.get(TransferManager.PIVOT_DATE);
        int cont = 0;
        while (true) {
            try {
                getTransferManager().tranferDates(endDate, corporateLocale, pivotDate, weekEndDays, weekName, firstDayOfWeek,
                        weekEndDesc, weekDayDesc);
                getTransferManager().transferStores();
                getTransferManager().transferProductAndAreas();
                getTransferManager().transferMetricNames();
                getTransferManager().transferIndicatorNames();
                getTransferManager().transferEvidenceProvider();

                break;
            } catch (ScopixException e) {
                log.warn("Problema en la transferencia de datos...", e);
                if (cont < maxAttempt) {
                    cont++;
                    log.debug("reintento " + cont + " de " + maxAttempt);
                    try {
                        Thread.sleep(intervalAttempt);
                    } catch (InterruptedException ex) {
                        log.error(ex, ex);
                        throw new ScopixException("Error en Sleep", ex);
                    }
                } else {
                    log.error("Transferencia de datos superó el máximo de reintentos", e);
                    throw new ScopixException("Transferencia superó el máximo de reintentos", e);
                }
            }
        }
        log.info(END);
    }

    /**
     * Carga de parámetros del archivo de parámetros del sistema
     * 
     * @param endDateParameter
     * @throws PeriscopeException
     */
    private void loadProcessParameters(String endDateParameter) throws ScopixException {
        log.info(START);
        //se verifica la carga de parámetros. Si alguno de los necesarios tiene problemas, se termina.
        try {
            int maxAttempt = getTransferManager().getPropertiesConfiguration().getInt(TransferManager.MAX_ATTEMPT);
            long intervalAttempt = getTransferManager().getPropertiesConfiguration().
                    getLong(TransferManager.INTERVAL_ATTEMPT_IN_MINUTES) * 60 * 1000;
            String[] weekEndDays = getTransferManager().getPropertiesConfiguration().
                    getStringArray(TransferManager.WEEK_END_DAYS);
            String weekName = getTransferManager().getPropertiesConfiguration().getString(TransferManager.WEEK_NAME);
            String weekEndDesc = getTransferManager().getPropertiesConfiguration().getString(TransferManager.WEEK_END_DESC);
            String weekDayDesc = getTransferManager().getPropertiesConfiguration().getString(TransferManager.WEEK_DAY_DESC);
            Integer firstDayOfWeek = getTransferManager().getPropertiesConfiguration().
                    getInteger(TransferManager.FIRST_DAY_OF_WEEK, 1);
            Boolean uploadProofEnabled = getTransferManager().getPropertiesConfiguration().
                    getBoolean(TransferManager.UPLOAD_PROOF_ENABLED);
            String corporateLocale = getTransferManager().getPropertiesConfiguration().
                    getString(TransferManager.CORPORATE_LOCALE);
            String pivotDate = getTransferManager().getPropertiesConfiguration().getString(TransferManager.PIVOT_DATE);
            Date uploadProofPivotDate = DateUtils.parseDate(
                    getTransferManager().getPropertiesConfiguration().getString(TransferManager.UPLOAD_PROOF_PIVOT_DATE),
                    new String[]{"yyyy-MM-dd"});
            Integer daysBefore = getTransferManager().getPropertiesConfiguration().getInteger(TransferManager.DAYS_BEFORE, 0);
            String mondrianCacheURL = getTransferManager().getPropertiesConfiguration().
                    getString(TransferManager.MONDRIAN_CACHE_URL);

            Date endDate = getEndDate(endDateParameter, daysBefore);

            log.debug("maxAttempt: " + maxAttempt);
            log.debug("intervalAttempt: " + intervalAttempt);
            log.debug("weekEndDays: " + weekEndDays.length);
            log.debug("weekName: " + weekName);
            log.debug("weekEndDesc: " + weekEndDesc);
            log.debug("weekDayDesc: " + weekDayDesc);
            log.debug("firstDayOfWeek: " + firstDayOfWeek);
            log.debug("uploadProofEnabled: " + uploadProofEnabled);
            log.debug("corporateLocale: " + corporateLocale);
            log.debug("pivotDate: " + pivotDate);
            log.debug("uploadPivotDate: " + uploadProofPivotDate);
            log.debug("endDateParameter: " + endDateParameter);
            log.debug("daysBefore: " + daysBefore);
            log.debug("endDate: " + endDate);
            log.debug("mondrianCacheURL: " + mondrianCacheURL);

            //no se verifica el parametro mondrianCacheURL, ya que si no esta o existe un problema, los datos
            //estarán igual, solo que no se verá reflejado. Si hay algun problema en la visualizacion
            //en auditing, verificar datos y luego este parámetro y su llamada.
            //Los numeros tampoco se verifican ya que tienen valores por defecto que no inhabilitan el proceso
            if (corporateLocale == null || endDate == null
                    || pivotDate == null || uploadProofPivotDate == null
                    || daysBefore == null || weekEndDays == null || weekEndDays.length == 0
                    || weekName == null || weekEndDesc == null || weekDayDesc == null) {
                log.error("Problema en la carga de parametros para la transferencia");
                throw new ScopixException("Error: Problema en la carga de parametros para la transferencia");
            }

            getHmParameters().put(TransferManager.MAX_ATTEMPT, maxAttempt);
            getHmParameters().put(TransferManager.INTERVAL_ATTEMPT, intervalAttempt);
            getHmParameters().put(TransferManager.WEEK_END_DAYS, weekEndDays);
            getHmParameters().put(TransferManager.WEEK_NAME, weekName);
            getHmParameters().put(TransferManager.WEEK_END_DESC, weekEndDesc);
            getHmParameters().put(TransferManager.WEEK_DAY_DESC, weekDayDesc);
            getHmParameters().put(TransferManager.FIRST_DAY_OF_WEEK, firstDayOfWeek);
            getHmParameters().put(TransferManager.UPLOAD_PROOF_ENABLED, uploadProofEnabled);
            getHmParameters().put(TransferManager.CORPORATE_LOCALE, corporateLocale);
            getHmParameters().put(TransferManager.PIVOT_DATE, pivotDate);
            getHmParameters().put(TransferManager.UPLOAD_PROOF_PIVOT_DATE, uploadProofPivotDate);
            getHmParameters().put(TransferManager.END_DATE, endDate);
            getHmParameters().put(TransferManager.MONDRIAN_CACHE_URL, mondrianCacheURL);

        } catch (ConversionException pex) {
            log.error("Error: " + pex.getMessage());
            throw new ScopixException(pex);
        } catch (ParseException pex) {
            log.error("Error: " + pex.getMessage());
            throw new ScopixException(pex);
        }
        log.info(END);
    }

    /**
     * Función que entrega la fecha formateada de acuerdo al formato definido en ella.
     * Si el parametro endDateParameter no viene, se calcula a partir de la fecha actual hacia atras el numero
     * de dias indicado en el parámetro daysBefore
     * 
     * @param endDateParameter
     * @param daysBefore
     * @return
     * @throws PeriscopeException
     */
    private Date getEndDate(String endDateParameter, Integer daysBefore) throws ScopixException {
        log.info(START);
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (endDateParameter == null) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, (daysBefore * -1));
            endDate = cal.getTime();
        } else {
            try {
                endDate = sdf.parse(endDateParameter);
            } catch (ParseException e) {
                log.error(e, e);
                throw new ScopixException("Error en formato de fecha", e);
            }
        }

        log.info(END);
        return endDate;
    }

    /**
     * This method validate if other job is running before
     *
     * @param context
     * @return
     */
    public boolean validateExecution(JobExecutionContext context) {
        log.info(START);
        boolean resp = false;
        try {
            List<JobExecutionContext> list = context.getScheduler().getCurrentlyExecutingJobs();
            int count = 0;
            for (JobExecutionContext jobEx : list) {
                if ("TransferJob".equals(jobEx.getJobDetail().getName())
                        || "TransferSimpleJob".equals(jobEx.getJobDetail().getName())) {
                    count++;
                }
            }
            if (count == 1) {
                resp = true;
            }
        } catch (SchedulerException ex) {
            log.error("Error when validating other instance is running", ex);
        }
        log.info(END);
        return resp;
    }

    public static boolean isWorking() {
        return working;
    }

    public static void setWorking(boolean aWorking) {
        working = aWorking;
    }

    public TransferManager getTransferManager() {
        if (transferManager == null) {
            transferManager = SpringSupport.getInstance().findBeanByClassName(TransferManager.class);
        }

        return transferManager;
    }

    public void setTransferManager(TransferManager transferManager) {
        this.transferManager = transferManager;
    }

    public HashMap getHmParameters() {
        if (hmParameters == null) {
            hmParameters = new HashMap();
        }
        return hmParameters;
    }

    public void setHmParameters(HashMap hmParameters) {
        this.hmParameters = hmParameters;
    }
}
