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
 * TransferManager.java
 *
 * Created on 08-11-2012, 03:11:50 PM
 *
 */
package com.scopix.periscope.businesswarehouse.transfer;

import com.scopix.periscope.businesswarehouse.transfer.commands.CleanEvidenceAndProofsCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.DeleteFromRejectErrorCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.RefreshMondrianCacheCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferComplianceFactAndIndicatorValuesCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferDatesCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferEvidenceProviderCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferIndicatorNamesCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferMetricNamesCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferProductAndAreasCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferProofsCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferProofsDataCommand;
import com.scopix.periscope.businesswarehouse.transfer.commands.TransferStoresCommand;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Cesar
 */
@SpringBean(rootClass = TransferManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class TransferManager implements InitializingBean {

    private static final String PROFILE_PROPERTIES_PATH = "/config/profiles/profile.server.properties";
    private static final String START = "start";
    private static final String END = "end";
    private Logger log = Logger.getLogger(TransferManager.class);
    private PropertiesConfiguration propertiesConfiguration;
    public static final String MAX_ATTEMPT = "MAX_ATTEMPT";
    public static final String INTERVAL_ATTEMPT_IN_MINUTES = "INTERVAL_ATTEMPT_IN_MINUTES";
    public static final String INTERVAL_ATTEMPT = "INTERVAL_ATTEMPT";
    public static final String WEEK_END_DAYS = "WEEK_END_DAYS";
    public static final String WEEK_NAME = "WEEK_NAME";
    public static final String FIRST_DAY_OF_WEEK = "FIRST_DAY_OF_WEEK";
    public static final String WEEK_END_DESC = "WEEK_END_DESC";
    public static final String WEEK_DAY_DESC = "WEEK_DAY_DESC";
    public static final String UPLOAD_PROOF_ENABLED = "UPLOAD_PROOF_ENABLED";
    public static final String CORPORATE_LOCALE = "CORPORATE_LOCALE";
    public static final String PIVOT_DATE = "PIVOT_DATE";
    public static final String UPLOAD_PROOF_PIVOT_DATE = "UPLOAD_PROOF_PIVOT_DATE";
    public static final String DAYS_BEFORE = "DAYS_BEFORE";
    public static final String MONDRIAN_CACHE_URL = "MONDRIAN_CACHE_URL";
    public static final String END_DATE = "END_DATE";
    public static final String COMPRESS_BASE_PATH = "COMPRESS_BASE_PATH";
    public static final String COMPRESS_PREFIX = "COMPRESS_PREFIX";
    public static final String COMPRESS_DESTINATION_PATH = "COMPRESS_DESTINATION_PATH";
    public static final String REMOTE_PATH = "REMOTE_PATH";
    public static final String REMOTE_SFTP_HOST = "REMOTE_SFTP_HOST";
    public static final String REMOTE_SFTP_USER = "REMOTE_SFTP_USER";
    public static final String REMOTE_SFTP_PASS = "REMOTE_SFTP_PASS";
    private TransferComplianceFactAndIndicatorValuesCommand transferComplianceFactAndIndicatorValuesCommand;
    private TransferProofsCommand transferProofsCommand;
    private TransferDatesCommand transferDatesCommand;
    private TransferStoresCommand transferStoresCommand;
    private TransferProductAndAreasCommand transferProductAndAreasCommand;
    private TransferMetricNamesCommand transferMetricNamesCommand;
    private TransferIndicatorNamesCommand transferIndicatorNamesCommand;
    private TransferEvidenceProviderCommand transferEvidenceProviderCommand;
    private TransferProofsDataCommand transferProofsDataCommand;
    private DeleteFromRejectErrorCommand deleteFromRejectErrorCommand;
    private CleanEvidenceAndProofsCommand cleanEvidenceAndProofsCommand;
    private RefreshMondrianCacheCommand refreshMondrianCacheCommand;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.scheduleJob();
    }

    /**
     *
     */
    private void scheduleJob() {
        log.info(START);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        try {
            //Transform minutes in miliseconds
            long repeatInterval = SystemConfig.getIntegerParameter("REPEAT_INTERVAL_IN_MINUTES") * 60 * 1000;
            String time = SystemConfig.getStringParameter("EXECUTION_TIME");
            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            if (!sched.isStarted()) {
                sched.start();
            }
            Date startTime = new Date();
            startTime = sdf2.parse((sdf.format(startTime) + " " + time));

            JobDetail job = new JobDetail("TransferJob", "TransferGroup", ScheduleTransfer.class);

            JobDataMap jobDataMap = new JobDataMap();
            job.setJobDataMap(jobDataMap);

            SimpleTrigger trigger = new SimpleTrigger("TransferTrigger", "TransferGroup", startTime, null,
                    SimpleTrigger.REPEAT_INDEFINITELY, repeatInterval);

            sched.scheduleJob(job, trigger);
        } catch (Exception ex) {
            log.warn("Error when scheduling transfer", ex);
        }
        log.info(END);
    }

    /**
     * Método encargado de transferir las fechas a BW.
     *
     * @param endDate Fecha final para la transferencia
     * @param corporateLocale Localización para el calendario
     * @param pivotDate Fecha pivote (inicial) para la transferencia
     * @param weekEndDays Dias de fin de semana
     * @param weekName Nombre de la semana en el idioma necesario
     * @param firstDayOfWeek Primer dia de la semana
     * @param weekEndDesc Descripción de la palabra "fin de semana" en el idioma necesario
     * @param weekDayDesc Descripción de la palabra "dia de semana" en el idioma necesario
     * @throws PeriscopeException
     */
    public void tranferDates(Date endDate, String corporateLocale, String pivotDate, String[] weekEndDays, String weekName,
            Integer firstDayOfWeek, String weekEndDesc, String weekDayDesc) throws ScopixException {
        getTransferDatesCommand().execute(endDate, corporateLocale, pivotDate, weekEndDays, weekName, firstDayOfWeek,
                weekEndDesc, weekDayDesc);
    }

    /**
     * Encargada de la transferencia de tiendas hacia bw
     *
     * @throws PeriscopeException
     */
    public void transferStores() throws ScopixException {
        getTransferStoresCommand().execute();
    }

    /**
     * Encargada de la transferencia de productos y areas a bw
     *
     * @throws PeriscopeException
     */
    public void transferProductAndAreas() throws ScopixException {
        getTransferProductAndAreasCommand().execute();
    }

    /**
     * Transferencia de nombres de metricas a bw
     *
     * @throws PeriscopeException
     */
    public void transferMetricNames() throws ScopixException {
        getTransferMetricNamesCommand().execute();
    }

    /**
     * Transferencia de nombre de indicadores
     *
     * @throws PeriscopeException
     */
    public void transferIndicatorNames() throws ScopixException {
        getTransferIndicatorNamesCommand().execute();
    }

    /**
     * Transferencia de evidence providers a bw
     *
     * @throws PeriscopeException
     */
    public void transferEvidenceProvider() throws ScopixException {
        getTransferEvidenceProviderCommand().execute();
    }

    /**
     * Transferencia de los datos de proofs a bw
     *
     * @param endDate Fecha final
     * @param uploadPivotDate Fecha pivote (inicial)
     * @throws PeriscopeException
     */
    public void transferProofsData(Date endDate, Date uploadPivotDate) throws ScopixException {
        getTransferProofsDataCommand().execute(endDate, uploadPivotDate);
    }

    /**
     * Transferencia de compliance facts a bw hasta la fecha dada.
     *
     * @param endDate
     * @throws PeriscopeException
     */
    public void transferComplianceFactAndIndicatorValues(Date endDate) throws ScopixException {
        getTransferComplianceFactAndIndicatorValuesCommand().execute(endDate);
    }

    /**
     * Elimina los rechazados de BW
     */
    public void deleteFromRejectError() {
        getDeleteFromRejectErrorCommand().execute();
    }

    /**
     * Limpia los evidences and proofs que esten sin asociación en bw
     */
    public void cleanEvidenceAndProofs() {
        getCleanEvidenceAndProofsCommand().execute();
    }

    /**
     * Refrescar mondrian
     *
     * @param mondrianCacheURL url a invocar
     */
    public void refreshMondrianCache(String mondrianCacheURL) {
        getRefreshMondrianCacheCommand().execute(mondrianCacheURL);
    }

    /**
     * Encargada de transferir los proofs (imagenes) al servidor destino
     *
     * @param corporateName Nombre del cliente
     * @param endDate Fecha final del traspaso
     * @param uploadPivotDate Fecha inicial o pivote a tomar en cuenta para la transferencia
     * @param compressBasePath Ruta al directorio base de los archivos a comprimir
     * @param compressPrefix Prefijo de los archivos comprimidos generados
     * @param compressDestinationPath Ruta al directorio destino donde quedaran los archivos comprimidos
     * @param remotePath Ruta en el servidor destino donde quedaran los archivos comprimidos
     * @param host IP servidor destino
     * @param user Usuario para el traspaso por sftp
     * @param password Password del usuario para el traspaso
     * @throws PeriscopeException
     */
    void transferProofsToBW(Date endDate, Date uploadPivotDate, String compressBasePath, String compressPrefix,
            String compressDestinationPath, String remotePath, String host, String user, String password)
            throws ScopixException {
        String corporateName = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                getCorporateName();

        getTransferProofsCommand().execute(corporateName, endDate, uploadPivotDate, compressBasePath, compressPrefix,
                compressDestinationPath, remotePath, host, user, password);
    }

    public TransferComplianceFactAndIndicatorValuesCommand getTransferComplianceFactAndIndicatorValuesCommand() {
        if (transferComplianceFactAndIndicatorValuesCommand == null) {
            transferComplianceFactAndIndicatorValuesCommand = new TransferComplianceFactAndIndicatorValuesCommand();
        }
        return transferComplianceFactAndIndicatorValuesCommand;
    }

    public void setTransferComplianceFactAndIndicatorValuesCommand(
            TransferComplianceFactAndIndicatorValuesCommand transferComplianceFactAndIndicatorValuesCommand) {
        this.transferComplianceFactAndIndicatorValuesCommand = transferComplianceFactAndIndicatorValuesCommand;
    }

    public TransferProofsCommand getTransferProofsCommand() {
        if (transferProofsCommand == null) {
            transferProofsCommand = new TransferProofsCommand();
        }
        return transferProofsCommand;
    }

    public void setTransferProofsCommand(TransferProofsCommand transferProofsCommand) {
        this.transferProofsCommand = transferProofsCommand;
    }

    public TransferDatesCommand getTransferDatesCommand() {
        if (transferDatesCommand == null) {
            transferDatesCommand = new TransferDatesCommand();
        }
        return transferDatesCommand;
    }

    public void setTransferDatesCommand(TransferDatesCommand transferDatesCommand) {
        this.transferDatesCommand = transferDatesCommand;
    }

    public TransferStoresCommand getTransferStoresCommand() {
        if (transferStoresCommand == null) {
            transferStoresCommand = new TransferStoresCommand();
        }
        return transferStoresCommand;
    }

    public void setTransferStoresCommand(TransferStoresCommand transferStoresCommand) {
        this.transferStoresCommand = transferStoresCommand;
    }

    public TransferProductAndAreasCommand getTransferProductAndAreasCommand() {
        if (transferProductAndAreasCommand == null) {
            transferProductAndAreasCommand = new TransferProductAndAreasCommand();
        }
        return transferProductAndAreasCommand;
    }

    public void setTransferProductAndAreasCommand(TransferProductAndAreasCommand transferProductAndAreasCommand) {
        this.transferProductAndAreasCommand = transferProductAndAreasCommand;
    }

    public TransferMetricNamesCommand getTransferMetricNamesCommand() {
        if (transferMetricNamesCommand == null) {
            transferMetricNamesCommand = new TransferMetricNamesCommand();
        }
        return transferMetricNamesCommand;
    }

    public void setTransferMetricNamesCommand(TransferMetricNamesCommand transferMetricNamesCommand) {
        this.transferMetricNamesCommand = transferMetricNamesCommand;
    }

    public TransferIndicatorNamesCommand getTransferIndicatorNamesCommand() {
        if (transferIndicatorNamesCommand == null) {
            transferIndicatorNamesCommand = new TransferIndicatorNamesCommand();
        }
        return transferIndicatorNamesCommand;
    }

    public void setTransferIndicatorNamesCommand(TransferIndicatorNamesCommand transferIndicatorNamesCommand) {
        this.transferIndicatorNamesCommand = transferIndicatorNamesCommand;
    }

    public TransferEvidenceProviderCommand getTransferEvidenceProviderCommand() {
        if (transferEvidenceProviderCommand == null) {
            transferEvidenceProviderCommand = new TransferEvidenceProviderCommand();
        }
        return transferEvidenceProviderCommand;
    }

    public void setTransferEvidenceProviderCommand(TransferEvidenceProviderCommand transferEvidenceProviderCommand) {
        this.transferEvidenceProviderCommand = transferEvidenceProviderCommand;
    }

    public TransferProofsDataCommand getTransferProofsDataCommand() {
        if (transferProofsDataCommand == null) {
            transferProofsDataCommand = new TransferProofsDataCommand();
        }
        return transferProofsDataCommand;
    }

    public void setTransferProofsDataCommand(TransferProofsDataCommand transferProofsDataCommand) {
        this.transferProofsDataCommand = transferProofsDataCommand;
    }

    public DeleteFromRejectErrorCommand getDeleteFromRejectErrorCommand() {
        if (deleteFromRejectErrorCommand == null) {
            deleteFromRejectErrorCommand = new DeleteFromRejectErrorCommand();
        }
        return deleteFromRejectErrorCommand;
    }

    public void setDeleteFromRejectErrorCommand(DeleteFromRejectErrorCommand deleteFromRejectErrorCommand) {
        this.deleteFromRejectErrorCommand = deleteFromRejectErrorCommand;
    }

    public CleanEvidenceAndProofsCommand getCleanEvidenceAndProofsCommand() {
        if (cleanEvidenceAndProofsCommand == null) {
            cleanEvidenceAndProofsCommand = new CleanEvidenceAndProofsCommand();
        }
        return cleanEvidenceAndProofsCommand;
    }

    public void setCleanEvidenceAndProofsCommand(CleanEvidenceAndProofsCommand cleanEvidenceAndProofsCommand) {
        this.cleanEvidenceAndProofsCommand = cleanEvidenceAndProofsCommand;
    }

    public RefreshMondrianCacheCommand getRefreshMondrianCacheCommand() {
        if (refreshMondrianCacheCommand == null) {
            refreshMondrianCacheCommand = new RefreshMondrianCacheCommand();
        }
        return refreshMondrianCacheCommand;
    }

    public void setRefreshMondrianCacheCommand(RefreshMondrianCacheCommand refreshMondrianCacheCommand) {
        this.refreshMondrianCacheCommand = refreshMondrianCacheCommand;
    }

    public PropertiesConfiguration getPropertiesConfiguration() throws ScopixException {
        if (propertiesConfiguration == null) {
            try {
                propertiesConfiguration = new PropertiesConfiguration(PROFILE_PROPERTIES_PATH);
                propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException ex) {
                log.error("ConfigurationException", ex);
                throw new ScopixException("Error", ex);
            }
        }
        return propertiesConfiguration;
    }

    public void setPropertyConfigurator(PropertiesConfiguration propertiesConfiguration) {
        this.propertiesConfiguration = propertiesConfiguration;
    }
}
