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
 *  ReportingUploadTransferManager.java
 * 
 *  Created on 31-01-2011, 10:10:52 AM
 * 
 */
package com.scopix.periscope.reporting.transfer;

import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.UploadProcessDetail;
import com.scopix.periscope.reporting.commands.AreaTypeFormBsCommand;
import com.scopix.periscope.reporting.commands.CalendarInitDatesCommand;
import com.scopix.periscope.reporting.commands.MetricNamesFromBsCommand;
import com.scopix.periscope.reporting.commands.ProductAndAreaDTOListCommand;
import com.scopix.periscope.reporting.commands.ReportingDataListCommand;
import com.scopix.periscope.reporting.transfer.commands.CleanDataForProcessCommand;
import com.scopix.periscope.reporting.transfer.commands.LastDateHierarchyReportingCommand;
import com.scopix.periscope.reporting.transfer.commands.PrepareDataForProcessCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferAreaTypeCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferDatesCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferMetricNamesCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferMetricTemplateCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferProductAndAreaCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferProductCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferReportingDataCommand;
import com.scopix.periscope.reporting.transfer.commands.TransferStoreToReportingCommand;
import com.scopix.periscope.reporting.transfer.commands.ValidateConnectionReportingDataCommand;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.commands.GetMetricTemplateListCommand;
import com.scopix.periscope.templatemanagement.commands.GetProductListCommand;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.PersistenceException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * Manager Encargado de hacer las tranferencias de datos a la base de datos Reporting
 *
 * @author nelson
 */
@SpringBean(rootClass = ReportingUploadTransferManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class ReportingUploadTransferManager {

    private static Logger log = Logger.getLogger(ReportingUploadTransferManager.class);
    private TransferStoreToReportingCommand transferStoreToReportingCommand;
    private ProductAndAreaDTOListCommand productAndAreaDTOListCommand;
    private TransferProductAndAreaCommand transferProductAndAreaCommand;
    private ReportingDataListCommand reportingDataListCommand;
    private TransferReportingDataCommand transferReportingDataCommand;
    private MetricNamesFromBsCommand metricNamesFromBsCommand;
    private TransferMetricNamesCommand transferMetricNamesCommand;
    private CalendarInitDatesCommand calendarInitDatesCommand;
    private TransferDatesCommand transferDatesCommand;
    private PrepareDataForProcessCommand prepareDateForProcessCommand;
    private CleanDataForProcessCommand cleanDataForProcessCommand;
    private ValidateConnectionReportingDataCommand validateConnectionReportingDataCommand;
    private AreaTypeFormBsCommand areaTypeFormBsCommand;
    private TransferAreaTypeCommand transferAreaTypeCommand;
    private GetProductListCommand productListCommand;
    private TransferProductCommand transferProductCommand;
    private GetMetricTemplateListCommand metricTemplateListCommand;
    private TransferMetricTemplateCommand transferMetricTemplateCommand;
    private boolean cancelProcess;

    public void transferStores(UploadProcess up) throws ScopixException {
        log.debug("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }
        List<Store> stores = new ArrayList<Store>();

        for (UploadProcessDetail detail : up.getProcessDetails()) {
            stores.add(detail.getStore());
        }

        getTransferStoreToReportingCommand().execute(stores);
        log.debug("end");
    }

    public void transferProductAndAreas(UploadProcess process) throws ScopixException {
        log.debug("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }

        Set<Integer> areaTypeIds = new HashSet<Integer>();
        for (UploadProcessDetail detail : process.getProcessDetails()) {
            areaTypeIds.add(detail.getAreaType().getId());
        }

        List<ProductAndAreaDTO> dtos = getProductAndAreaDTOListCommand().execute(areaTypeIds.toArray(new Integer[0]));

        getTransferProductAndAreaCommand().execute(dtos);
        log.debug("end");
    }

    public TransferStoreToReportingCommand getTransferStoreToReportingCommand() {
        if (transferStoreToReportingCommand == null) {
            transferStoreToReportingCommand = new TransferStoreToReportingCommand();
        }
        return transferStoreToReportingCommand;
    }

    public void setTransferStoreToReportingCommand(TransferStoreToReportingCommand transferStoreToReportingCommand) {
        this.transferStoreToReportingCommand = transferStoreToReportingCommand;
    }

    public void transferMetricNames(UploadProcess process) throws ScopixException {
        log.info("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }

        List<Map> metricNamesBS = getMetricNamesFromBsCommand().execute();
        getTransferMetricNamesCommand().execute(metricNamesBS);
        log.info("end");
    }

    public void transferAreaType() throws ScopixException {
        log.info("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }

        List<Map<String, Object>> list = getAreaTypeFormBsCommand().execute();
        getTransferAreaTypeCommand().execute(list);

        log.info("end");
    }

    public ProductAndAreaDTOListCommand getProductAndAreaDTOListCommand() {
        if (productAndAreaDTOListCommand == null) {
            productAndAreaDTOListCommand = new ProductAndAreaDTOListCommand();
        }
        return productAndAreaDTOListCommand;
    }

    public void setProductAndAreaDTOListCommand(ProductAndAreaDTOListCommand productAndAreaDTOListCommand) {
        this.productAndAreaDTOListCommand = productAndAreaDTOListCommand;
    }

    public TransferProductAndAreaCommand getTransferProductAndAreaCommand() {
        if (transferProductAndAreaCommand == null) {
            transferProductAndAreaCommand = new TransferProductAndAreaCommand();
        }
        return transferProductAndAreaCommand;
    }

    public void setTransferProductAndAreaCommand(TransferProductAndAreaCommand transferProductAndAreaCommand) {
        this.transferProductAndAreaCommand = transferProductAndAreaCommand;
    }

    public void transferReportingData(UploadProcessDetail detail) throws ScopixException {
        log.debug("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }
        List<ReportingData> reportingDatas = getReportingDataListCommand().execute(detail);
        List<MetricTemplate> metricTemplates = getMetricTemplateListCommand().execute(null);
        getTransferReportingDataCommand().execute(reportingDatas, detail, metricTemplates);
        log.debug("end");
    }

    public ReportingDataListCommand getReportingDataListCommand() {
        if (reportingDataListCommand == null) {
            reportingDataListCommand = new ReportingDataListCommand();
        }
        return reportingDataListCommand;
    }

    public void setReportingDataListCommand(ReportingDataListCommand reportingDataListCommand) {
        this.reportingDataListCommand = reportingDataListCommand;
    }

    public TransferReportingDataCommand getTransferReportingDataCommand() {
        if (transferReportingDataCommand == null) {
            transferReportingDataCommand = new TransferReportingDataCommand();
        }
        return transferReportingDataCommand;
    }

    public void setTransferReportingDataCommand(TransferReportingDataCommand transferReportingDataCommand) {
        this.transferReportingDataCommand = transferReportingDataCommand;
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

    public MetricNamesFromBsCommand getMetricNamesFromBsCommand() {
        if (metricNamesFromBsCommand == null) {
            metricNamesFromBsCommand = new MetricNamesFromBsCommand();
        }
        return metricNamesFromBsCommand;
    }

    public void setMetricNamesFromBsCommand(MetricNamesFromBsCommand metricNamesFromBsCommand) {
        this.metricNamesFromBsCommand = metricNamesFromBsCommand;
    }

    public void tranferDates(UploadProcess process) throws ScopixException {
        log.debug("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new PersistenceException("MANUAL");
        }
        LinkedHashMap<Integer, String> initYears = getCalendarInitDatesCommand().execute();
        getTransferDatesCommand().execute(process, initYears);
        log.debug("end");
    }

    public CalendarInitDatesCommand getCalendarInitDatesCommand() {
        if (calendarInitDatesCommand == null) {
            calendarInitDatesCommand = new CalendarInitDatesCommand();
        }
        return calendarInitDatesCommand;
    }

    public void setCalendarInitDatesCommand(CalendarInitDatesCommand calendarInitDatesCommand) {
        this.calendarInitDatesCommand = calendarInitDatesCommand;
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

    public boolean isCancelProcess() {
        return cancelProcess;
    }

    public void setCancelProcess(boolean value) {
        this.cancelProcess = value;
        getTransferReportingDataCommand().setCancelProcess(value);
    }

    public void prepareDataForProcess() {
        getPrepareDateForProcessCommand().execute();
    }

    public void cleanDataForProcess() {
        getCleanDataForProcessCommand().execute();
    }

    public PrepareDataForProcessCommand getPrepareDateForProcessCommand() {
        if (prepareDateForProcessCommand == null) {
            prepareDateForProcessCommand = new PrepareDataForProcessCommand();
        }
        return prepareDateForProcessCommand;
    }

    public void setPrepareDateForProcessCommand(PrepareDataForProcessCommand prepareDateForProcessCommand) {
        this.prepareDateForProcessCommand = prepareDateForProcessCommand;
    }

    public CleanDataForProcessCommand getCleanDataForProcessCommand() {
        if (cleanDataForProcessCommand == null) {
            cleanDataForProcessCommand = new CleanDataForProcessCommand();
        }
        return cleanDataForProcessCommand;
    }

    public void setCleanDataForProcessCommand(CleanDataForProcessCommand cleanDataForProcessCommand) {
        this.cleanDataForProcessCommand = cleanDataForProcessCommand;
    }

    public void validateConnection() throws ScopixException {
        getValidateConnectionReportingDataCommand().execute();
    }

    public ValidateConnectionReportingDataCommand getValidateConnectionReportingDataCommand() {
        if (validateConnectionReportingDataCommand == null) {
            validateConnectionReportingDataCommand = new ValidateConnectionReportingDataCommand();
        }
        return validateConnectionReportingDataCommand;
    }

    public void setValidateConnectionReportingDataCommand(ValidateConnectionReportingDataCommand value) {
        this.validateConnectionReportingDataCommand = value;
    }

    public AreaTypeFormBsCommand getAreaTypeFormBsCommand() {
        if (areaTypeFormBsCommand == null) {
            areaTypeFormBsCommand = new AreaTypeFormBsCommand();
        }
        return areaTypeFormBsCommand;
    }

    public void setAreaTypeFormBsCommand(AreaTypeFormBsCommand areaTypeFormBsCommand) {
        this.areaTypeFormBsCommand = areaTypeFormBsCommand;
    }

    public TransferAreaTypeCommand getTransferAreaTypeCommand() {
        if (transferAreaTypeCommand == null) {
            transferAreaTypeCommand = new TransferAreaTypeCommand();
        }
        return transferAreaTypeCommand;
    }

    public void setTransferAreaTypeCommand(TransferAreaTypeCommand transferAreaTypeCommand) {
        this.transferAreaTypeCommand = transferAreaTypeCommand;
    }

    public void transferProduct(UploadProcess process) throws ScopixException {
        log.debug("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }
        List<Product> products = getProductListCommand().execute(null);
        getTransferProductCommand().execute(products);
        log.debug("end");
    }

    public GetProductListCommand getProductListCommand() {
        if (productListCommand == null) {
            productListCommand = new GetProductListCommand();
        }
        return productListCommand;
    }

    public void setProductListCommand(GetProductListCommand productListCommand) {
        this.productListCommand = productListCommand;
    }

    public TransferProductCommand getTransferProductCommand() {
        if (transferProductCommand == null) {
            transferProductCommand = new TransferProductCommand();
        }
        return transferProductCommand;
    }

    public void setTransferProductCommand(TransferProductCommand transferProductCommand) {
        this.transferProductCommand = transferProductCommand;
    }

    public void transferMetricTemplate(UploadProcess process) throws ScopixException {
        log.info("start");
        if (isCancelProcess()) {
            setCancelProcess(false);
            throw new ScopixException("MANUAL");
        }

        List<MetricTemplate> metricTemplates = getMetricTemplateListCommand().execute(null);

        getTransferMetricTemplateCommand().execute(metricTemplates);
        log.info("end");
    }

    public GetMetricTemplateListCommand getMetricTemplateListCommand() {
        if (metricTemplateListCommand == null) {
            metricTemplateListCommand = new GetMetricTemplateListCommand();
        }
        return metricTemplateListCommand;
    }

    public void setMetricTemplateListCommand(GetMetricTemplateListCommand metricTemplateListCommand) {
        this.metricTemplateListCommand = metricTemplateListCommand;
    }

    public TransferMetricTemplateCommand getTransferMetricTemplateCommand() {
        if (transferMetricTemplateCommand == null) {
            transferMetricTemplateCommand = new TransferMetricTemplateCommand();
        }
        return transferMetricTemplateCommand;
    }

    public void setTransferMetricTemplateCommand(TransferMetricTemplateCommand transferMetricTemplateCommand) {
        this.transferMetricTemplateCommand = transferMetricTemplateCommand;
    }

    /**
     * revisa cuanto falta para que se acabe el calendario
     */
    public void revisionDateHierarchy(UploadProcess process) throws ScopixException {


        LastDateHierarchyReportingCommand command = new LastDateHierarchyReportingCommand();

        Integer lastDay = command.execute();
        Date nowDate = new Date();
        nowDate = DateUtils.setHours(nowDate, 0);
        nowDate = DateUtils.setMinutes(nowDate, 0);
        nowDate = DateUtils.setSeconds(nowDate, 0);
        nowDate = DateUtils.setMilliseconds(nowDate, 0);
        if (lastDay != null) {
            try {
                Date lastDayDate = DateUtils.parseDate(lastDay.toString(), new String[]{"yyyyMMdd"});
                if (lastDayDate.before(nowDate)) {
                    process.setComments(process.getComments() + "El calendario expiro " + lastDay + " \n");
                    throw new ScopixException("Error revisando Calendario del Cliente");
                } else {
                    Date lastDayDateNew = DateUtils.addDays(lastDayDate, -30);
                    if (lastDayDateNew.before(nowDate)) {
                        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
                        long diferencia = (lastDayDate.getTime() - nowDate.getTime()) / MILLSECS_PER_DAY;
                        process.setComments(process.getComments() + "****\n");
                        process.setComments(process.getComments() + "****\n");
                        process.setComments(process.getComments() + "****\n");
                        process.setComments(process.getComments()
                                + "**** Advertencia Faltan " + diferencia
                                + " dias para que termino de calendario del cliente \n");
                        process.setComments(process.getComments() + "****\n");
                        process.setComments(process.getComments() + "****\n");
                        process.setComments(process.getComments() + "****\n");
                    }


                }
            } catch (ParseException e) {
                log.error(e, e);
                throw new ScopixException(e);
            } catch (NumberFormatException e) {
                log.error(e, e);
                throw new ScopixException(e);
            }
        } else {
            process.setComments(process.getComments() + "No existe Date Hierarchy \n");
            throw new ScopixException("No es posible continuar faltan datos");
        }
    }
}
