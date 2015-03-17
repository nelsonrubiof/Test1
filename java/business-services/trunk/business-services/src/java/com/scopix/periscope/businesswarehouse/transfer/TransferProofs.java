/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * TransferProofs.java
 *
 * Created on 08-11-2012, 05:53:38 PM
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
 * @author Gustavo Alvarez
 */
public class TransferProofs implements Job {

    private static final String START = "start";
    private static final String END = "end";
    private static Logger log = Logger.getLogger(TransferProofs.class.getName());
    private static boolean working = false;
    private TransferManager transferManager;
    private HashMap hmParameters;

    /**
     * Subida de arhivos de proofs.
     * 
     * @param context
     * @throws JobExecutionException 
     */
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

                    //transferencia de proofs
                    transferProofFilesForAuditing();
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
     * Aca se hace la transferencia de archivos como tal hacia el servidor destino.
     * 
     * @throws PeriscopeException
     */
    private void transferProofFilesForAuditing() throws ScopixException {
        log.info(START);
        int maxAttempt = (Integer) hmParameters.get(TransferManager.MAX_ATTEMPT);
        long intervalAttempt = (Long) hmParameters.get(TransferManager.INTERVAL_ATTEMPT);
        boolean uploadProofEnabled = (Boolean) hmParameters.get(TransferManager.UPLOAD_PROOF_ENABLED);
        Date endDate = (Date) hmParameters.get(TransferManager.END_DATE);
        Date uploadProofPivotDate = (Date) hmParameters.get(TransferManager.UPLOAD_PROOF_PIVOT_DATE);
        String compressBasePath = (String) hmParameters.get(TransferManager.COMPRESS_BASE_PATH);
        String compressPrefix = (String) hmParameters.get(TransferManager.COMPRESS_PREFIX);
        String compressDestinationPath = (String) hmParameters.get(TransferManager.COMPRESS_DESTINATION_PATH);
        String remotePath = (String) hmParameters.get(TransferManager.REMOTE_PATH);
        String host = (String) hmParameters.get(TransferManager.REMOTE_SFTP_HOST);
        String user = (String) hmParameters.get(TransferManager.REMOTE_SFTP_USER);
        String password = (String) hmParameters.get(TransferManager.REMOTE_SFTP_PASS);

        //transferencia de archivos de proofs (jpg) para Auditing (BW)
        int cont = 0;
        while (true) {
            try {
                log.info("uploadProofEnabled: " + uploadProofEnabled);
                if (uploadProofEnabled) {
                    getTransferManager().transferProofsToBW(endDate, uploadProofPivotDate, compressBasePath, compressPrefix,
                            compressDestinationPath, remotePath, host, user, password);
                }
                break;
            } catch (ScopixException e) {
                log.warn("Problema en la transferencia...", e);
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
                    log.error("Transferencia superó el máximo de reintentos", e);
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
            Boolean uploadProofEnabled = getTransferManager().getPropertiesConfiguration().
                    getBoolean(TransferManager.UPLOAD_PROOF_ENABLED);
            Date uploadProofPivotDate = DateUtils.parseDate(
                    getTransferManager().getPropertiesConfiguration().
                    getString(TransferManager.UPLOAD_PROOF_PIVOT_DATE), new String[]{"yyyy-MM-dd"});
            Integer daysBefore = getTransferManager().getPropertiesConfiguration().
                    getInteger(TransferManager.DAYS_BEFORE, 0);
            String compressBasePath = getTransferManager().getPropertiesConfiguration().
                    getString(TransferManager.COMPRESS_BASE_PATH);
            String compressPrefix = getTransferManager().getPropertiesConfiguration().getString(TransferManager.COMPRESS_PREFIX);
            String compressDestinationPath = getTransferManager().getPropertiesConfiguration().
                    getString(TransferManager.COMPRESS_DESTINATION_PATH);
            String remotePath = getTransferManager().getPropertiesConfiguration().getString(TransferManager.REMOTE_PATH);
            String host = getTransferManager().getPropertiesConfiguration().getString(TransferManager.REMOTE_SFTP_HOST);
            String user = getTransferManager().getPropertiesConfiguration().getString(TransferManager.REMOTE_SFTP_USER);
            String password = getTransferManager().getPropertiesConfiguration().getString(TransferManager.REMOTE_SFTP_PASS);

            Date endDate = getEndDate(endDateParameter, daysBefore);

            log.debug("maxAttempt: " + maxAttempt);
            log.debug("intervalAttempt: " + intervalAttempt);
            log.debug("uploadProofEnabled: " + uploadProofEnabled);
            log.debug("uploadPivotDate: " + uploadProofPivotDate);
            log.debug("endDateParameter: " + endDateParameter);
            log.debug("daysBefore: " + daysBefore);
            log.debug("endDate: " + endDate);
            log.debug("compressBasePath: " + compressBasePath);
            log.debug("compressPrefix: " + compressPrefix);
            log.debug("compressDestinationPath: " + compressDestinationPath);
            log.debug("remotePath: " + remotePath);
            log.debug("host: " + host);
            log.debug("user: " + user);
            log.debug("password: " + password);

            //Los numeros no se verifican ya que tienen valores por defecto que no inhabilitan el proceso
            if (uploadProofPivotDate == null || endDate == null
                    || compressBasePath == null || compressBasePath.length() == 0
                    || compressPrefix == null || compressPrefix.length() == 0
                    || compressDestinationPath == null || compressDestinationPath.length() == 0
                    || remotePath == null || remotePath.length() == 0
                    || host == null || host.length() == 0
                    || user == null || user.length() == 0
                    || password == null || password.length() == 0) {
                log.error("Problema en la carga de parametros para la transferencia");
                throw new ScopixException("Error: Problema en la carga de parametros para la transferencia");
            }

            getHmParameters().put(TransferManager.MAX_ATTEMPT, maxAttempt);
            getHmParameters().put(TransferManager.INTERVAL_ATTEMPT, intervalAttempt);
            getHmParameters().put(TransferManager.UPLOAD_PROOF_ENABLED, uploadProofEnabled);
            getHmParameters().put(TransferManager.UPLOAD_PROOF_PIVOT_DATE, uploadProofPivotDate);
            getHmParameters().put(TransferManager.END_DATE, endDate);
            getHmParameters().put(TransferManager.COMPRESS_BASE_PATH, compressBasePath);
            getHmParameters().put(TransferManager.COMPRESS_PREFIX, compressPrefix);
            getHmParameters().put(TransferManager.COMPRESS_DESTINATION_PATH, compressDestinationPath);
            getHmParameters().put(TransferManager.REMOTE_PATH, remotePath);
            getHmParameters().put(TransferManager.REMOTE_SFTP_HOST, host);
            getHmParameters().put(TransferManager.REMOTE_SFTP_USER, user);
            getHmParameters().put(TransferManager.REMOTE_SFTP_PASS, password);

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
        //String endDateParameter = context.getJobDetail().getJobDataMap().getString("END_DATE");
        if (endDateParameter == null) {
            //Integer daysBefore = property.getInteger("DAYS_BEFORE", 0);
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
                        || "TransferProofSimpleJob".equals(jobEx.getJobDetail().getName())) {
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