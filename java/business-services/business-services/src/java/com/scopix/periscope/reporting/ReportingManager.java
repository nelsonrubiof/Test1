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
 *  ReportingManager.java
 * 
 *  Created on 12-01-2011, 11:08:27 AM
 * 
 */
package com.scopix.periscope.reporting;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaTypeCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetAreaTypeListCommand;
import com.scopix.periscope.corporatestructuremanagement.commands.GetStoreListCommand;
import com.scopix.periscope.corporatestructuremanagement.dto.AreaTypeDTO;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.commands.FormulasBySTAndStoreCommand;
import com.scopix.periscope.evaluationmanagement.commands.GetObservedSituationCommand;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import static com.scopix.periscope.reporting.ReportingManager.DATE_FORMAT;
import com.scopix.periscope.reporting.commands.AddReportingDataCommand;
import com.scopix.periscope.reporting.commands.AllLastEvaluationsCommand;
import com.scopix.periscope.reporting.commands.CalculaMaxRecordsUploadProcessDetailCommand;
import com.scopix.periscope.reporting.commands.DateOfEvidenceCommand;
import com.scopix.periscope.reporting.commands.DeleteUploadProcessDetailByIdCommand;
import com.scopix.periscope.reporting.commands.GenerateDataFromDataOldCommand;
import com.scopix.periscope.reporting.commands.GenerateDataFromObservedSituation;
import com.scopix.periscope.reporting.commands.GetNewDataUploadingCommand;
import com.scopix.periscope.reporting.commands.LastEvaluationsCommand;
import com.scopix.periscope.reporting.commands.RejectReportingDataFromObservedMetricCommand;
import com.scopix.periscope.reporting.commands.RejectReportingDataFromObservedSituationIdsCommand;
import com.scopix.periscope.reporting.commands.ReportingDataByObservedSituationCommand;
import com.scopix.periscope.reporting.commands.ReportingDataListByObservedSituationCommand;
import com.scopix.periscope.reporting.commands.ReportingUploadThread;
import com.scopix.periscope.reporting.commands.SaveUploadProccessDetailCommand;
import com.scopix.periscope.reporting.commands.SaveUploadProcessCommand;
import com.scopix.periscope.reporting.commands.TargetFromObservedSituationEvaluation;
import com.scopix.periscope.reporting.commands.TranformAreaTypesToDTOs;
import com.scopix.periscope.reporting.commands.TransformUploadProcessDetailListToDTO;
import com.scopix.periscope.reporting.commands.TransformUploadProcessToDTO;
import com.scopix.periscope.reporting.commands.UploadProcessCommand;
import com.scopix.periscope.reporting.commands.UploadProcessDetailListCommand;
import com.scopix.periscope.reporting.digester.ReportingDigester;
import com.scopix.periscope.reporting.dto.UploadProcessDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailAddDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailDTO;
import com.scopix.periscope.reporting.transfer.ReportingUploadTransferManager;
import com.scopix.periscope.securitymanagement.CorporateStructureManagerPermissions;
import com.scopix.periscope.securitymanagement.ReportingManagerPermissions;
import com.scopix.periscope.securitymanagement.SecurityManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
@SpringBean(rootClass = ReportingManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class ReportingManager {

    /**
     *
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    /**
     *
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Logger log = Logger.getLogger(ReportingManager.class);
    private SecurityManager securityManager;
    private UploadProcessCommand uploadProcessCommand;
    private TransformUploadProcessToDTO transformUploadProcessToDTO;
    private GetAreaTypeCommand areaTypeCommand;
    private GetAreaTypeListCommand areaTypeListCommand;
    private TranformAreaTypesToDTOs tranformAreaTypesToDTOs;
    private UploadProcessDetailListCommand uploadProcessDetailListCommand;
    private TransformUploadProcessDetailListToDTO transformUploadProcessDetailListToDTO;
    private DeleteUploadProcessDetailByIdCommand deleteUploadProcessDetailByIdCommand;
    private CalculaMaxRecordsUploadProcessDetailCommand calculaMaxRecordsUploadProcessDetailCommand;
    private SaveUploadProcessCommand saveUploadProcessCommand;
    private GetStoreListCommand storeListCommand;
    private SaveUploadProccessDetailCommand saveUploadProccessDetailCommand;
    private UploadProcess processRunning;
    private GetObservedSituationCommand observedSituationCommand;
    private FormulasBySTAndStoreCommand formulasBySTAndStoreCommand;
    private AddReportingDataCommand addReportingDataCommand;
    private DateOfEvidenceCommand dateOfEvidenceCommand;
    private GenerateDataFromObservedSituation generateDataFormObservedSituation;
    private TargetFromObservedSituationEvaluation targetFromObservedSituationEvaluation;
    private ReportingDataByObservedSituationCommand reportingDataByObservedSituationCommand;
    private ReportingDataListByObservedSituationCommand reportingDataListByObservedSituationCommand;
    private GenerateDataFromDataOldCommand generateDataFromDataOldCommand;
    private GetNewDataUploadingCommand newDataUploadingCommand;

    /**
     *
     * @return SecurityManager instancia de SecurityManager
     */
    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return securityManager;
    }

    /**
     *
     * @param securityManager instancia de SecurityManager
     */
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    /**
     * Retorna el ultimo proceso enviado o el proceso que esta en ejecucion
     *
     * @param sessionId session de usuario
     * @return UploadProcessDTO proceso de subida Transformado para servicio
     * @throws ScopixException Excepcion en caso de error
     */
    public UploadProcessDTO getUploadProcess(long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, ReportingManagerPermissions.GET_UPLOAD_PROCESS_PERMISSION);
        //revisamos si existe uno en ejecucion preguntando al thread de ejecucion de lo contrario
        UploadProcess up;
        if (processRunning != null) {
            up = processRunning;
        } else {
            up = getUploadProcessCommand().execute(ProcessState.FINISHED);
        }


        UploadProcessDTO dto = getTransformUploadProcessToDTO().execute(up);
        log.debug("end");
        return dto;
    }

    /**
     *
     * @param sessionId session de ususario conectado
     * @return List&lt;AreaTypeDTO&gt; Lista de areaType del sistema
     * @throws ScopixException Excepcion en caso de error
     */
    public List<AreaTypeDTO> getAreaTypeDTOList(long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, CorporateStructureManagerPermissions.GET_LIST_AREATYPE_PERMISSION);

        List<AreaType> lista = getAreaTypeListCommand().execute(null);
        log.debug("end");
        return getTranformAreaTypesToDTOs().execute(lista);
    }

    /**
     *
     * @param sessionId session de usuario conectado
     * @return List&lt;UploadProcessDetailDTO&gt; Lista detalles de proceso de subida
     * @throws ScopixException Excepcion en caso de error
     */
    public List<UploadProcessDetailDTO> getUploadProcessDetails(long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, ReportingManagerPermissions.GET_UPLOAD_PROCESS_DETAIL_PERMISSION);

        List<UploadProcessDetail> details = getUploadProcessDetailListCommand().execute();
        log.debug("end");
        return getTransformUploadProcessDetailListToDTO().execute(details);
    }

    /**
     * Retorna un Objeto con 2 listas la primera <b>aggregate</b> la cual contiene todas las conbinaciones store, areaType
     * agregadas la segunda <b>unknown</b> contiene aquellas conbinaciones que no se pudieron agregar
     *
     * @param storesId Lista de stores a subir
     * @param areasTypeId Lista de areaType con la cual se quiere combinar los stores
     * @param endDateParam dia hasta cuando se deben considerar los datos
     * @param sessionId session del usuario conectado
     * @return UploadProcessDetailAddDTO Detalles de los datos generados
     * @throws ScopixException Excepcion en caso de error
     */
    public UploadProcessDetailAddDTO addUploadProcessDetail(List<Integer> storesId, List<Integer> areasTypeId,
            String endDateParam, long sessionId) throws ScopixException {
        log.debug("start");
        Date endDate = null;
        try {
            endDate = DateUtils.parseDate(endDateParam, new String[]{DATE_FORMAT});
        } catch (ParseException pex) {
            throw new ScopixException(pex.getMessage(), pex);
        }
        String userLogin = "";
        getSecurityManager().checkSecurity(sessionId, ReportingManagerPermissions.ADD_UPLOAD_PROCESS_DETAIL_PERMISSION);
        userLogin = getSecurityManager().getUserName(sessionId);

        //recuperamos todos los stores seleccionados y ubicamos en cada uno el area type seleccionada

        List<Store> l = getStoreListCommand().execute(null);
        //sacamos solo los que estan en la lista de storesId
        List<Store> listaVerificacion = new ArrayList<Store>();
        for (Integer id : storesId) {
            for (Store s : l) {
                if (id.equals(s.getId())) {
                    listaVerificacion.add(s);
                    break;
                }
            }
        }

        List<UploadProcessDetail> listAggregate = new ArrayList<UploadProcessDetail>();
        List<UploadProcessDetail> listUnknown = new ArrayList<UploadProcessDetail>();
        for (Store s : listaVerificacion) {
            for (Integer idAreaType : areasTypeId) {
                boolean areaTypeAggregate = false;
                for (Area a : s.getAreas()) {
                    if (a.getAreaType().getId().equals(idAreaType)) {
                        areaTypeAggregate = true;
                        UploadProcessDetail aggregate = new UploadProcessDetail();
                        aggregate.setStore(s);
                        aggregate.setAreaType(a.getAreaType());
                        aggregate.setDateEnd(endDate);
                        aggregate.setUpRecords(0);
                        aggregate.setTotalRecords(0);
                        listAggregate.add(aggregate);
                        break;
                    }
                }
                if (!areaTypeAggregate) {
                    UploadProcessDetail unknown = new UploadProcessDetail();
                    unknown.setStore(s);
                    AreaType at = getAreaTypeCommand().execute(idAreaType);
                    unknown.setAreaType(at);
                    //evitamos que el parser en flex se caiga
                    unknown.setDateEnd(new Date());
                    listUnknown.add(unknown);
                }
            }
        }
        //almacenamos los detalles
        //Crear command para almacenar los detalles si eliminar los que ya existen
        //ahora transformamos a dtos y las agreamos al objeto a retornar
        //recupero los detalles actuales
        List<UploadProcessDetail> detailActuales = getUploadProcessDetailListCommand().execute();
        //hacemos un marge con los de la listaAggregate

        UploadProcess up = getUploadProcessCommand().execute(ProcessState.SCHEDULED);

        if (up == null) {
            up = new UploadProcess();
            up.setProcessState(ProcessState.SCHEDULED);
            up.setDateProcess(endDate);

        } else {
            if (up.getDateProcess().before(endDate)) {
                up.setDateProcess(endDate);
            }
        }
        //se coloca el usuario que esta realizando la operacion
        up.setLoginUser(userLogin);
        getSaveUploadProcessCommand().execute(up);
        for (UploadProcessDetail d : listAggregate) {
            d.setUploadProcess(up);
        }

        listAggregate = repeatDetailListVerify(detailActuales, listAggregate);
        getSaveUploadProccessDetailCommand().execute(listAggregate);

        //se necesita obtener la lista completa, incluido el nuevo registro creado
        List<UploadProcessDetail> updList = getUploadProcessDetailListCommand().execute();
        //List<UploadProcessDetailDTO> detailDTOsAggregate = getTransformUploadProcessDetailListToDTO().execute(listAggregate);
        List<UploadProcessDetailDTO> detailDTOsAggregate = getTransformUploadProcessDetailListToDTO().execute(updList);
        List<UploadProcessDetailDTO> detailDTOsUnknown = getTransformUploadProcessDetailListToDTO().execute(listUnknown);

        UploadProcessDetailAddDTO addDTO = new UploadProcessDetailAddDTO();
        addDTO.setAggregate(detailDTOsAggregate);
        addDTO.setUnknown(detailDTOsUnknown);
        log.debug("end");
        return addDTO;
    }

    private List<UploadProcessDetail> repeatDetailListVerify(List<UploadProcessDetail> detailActuales,
            List<UploadProcessDetail> listAggregate) {
        log.debug("start");
        List<UploadProcessDetail> lista = new ArrayList<UploadProcessDetail>();
        for (UploadProcessDetail aggregate : listAggregate) {
            boolean existe = false;
            for (UploadProcessDetail detailOrigen : detailActuales) {
                if (detailOrigen.getUploadProcess().getId().equals(aggregate.getUploadProcess().getId())
                        && detailOrigen.getAreaType().getId().equals(aggregate.getAreaType().getId())
                        && detailOrigen.getStore().getId().equals(aggregate.getStore().getId())) {
                    existe = true;
                    break;
                }
            }
            if (!existe) {
                lista.add(aggregate);
            }
        }
        log.debug("end");
        return lista;
    }

    /**
     * Elimina detalles de subida
     *
     * @param updId ids de detalles que se desean eliminiar
     * @param sessionId session de usuario conectado
     * @return List&lt;UploadProcessDetailDTO&gt; Lista de detalles de subida
     * @throws ScopixException Excepcion en caso de error
     */
    public List<UploadProcessDetailDTO> deleteUploadProcessDetail(List<Integer> updId, long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, ReportingManagerPermissions.DELETE_UPLOAD_PROCESS_DETAIL_PERMISSION);
        getDeleteUploadProcessDetailByIdCommand().execute(updId);
        //recuperamos los detalles almacenados 
        List<UploadProcessDetailDTO> list = getUploadProcessDetails(sessionId);
        log.debug("end");
        return list;
    }

    /**
     * Inicializa un proceso para la subida de datos a Reporting
     *
     * @param sessionId session de usuario conectado
     * @return UploadProcessDTO proceso activo
     * @throws ScopixException Excepcion en caso de error
     */
    public UploadProcessDTO uploadNow(long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, ReportingManagerPermissions.UPLOAD_PROCESS_NOW_PERMISSION);
        if (processRunning != null) {
            throw new ScopixException("PROCESS_IN_EXECUTION");
        }

        UploadProcessDTO dto = null;
        processRunning = getUploadProcessCommand().execute(ProcessState.SCHEDULED);
        if (processRunning == null) {
            throw new ScopixException("PROCESS_NULL");
        }

        try {
            String userName = getSecurityManager().getUserName(sessionId);
            //calculamos todos los maximos para los detalles antes de dejarlo en el thread

            Integer totProcess = getCalculaMaxRecordsUploadProcessDetailCommand().execute(processRunning);
            processRunning.setProcessState(ProcessState.RUNNING);
            processRunning.setTotalGlobal(totProcess);
            processRunning.setTotalUpload(0);
            processRunning.setStartDateProcess(new Date());
            processRunning.setLoginUserRunning(userName);
            ReportingUploadThread thread = new ReportingUploadThread();
            thread.setName("Reporting_data_uploding_" + processRunning.getId());
            thread.init(processRunning);

            thread.start();
            dto = getTransformUploadProcessToDTO().execute(processRunning);

        } catch (ScopixException e) {
            processRunning = null;
            log.error(e, e);
            throw new ScopixException("ERROR_RUN_NOW", e);
        }
//        catch (PeriscopeSecurityException pe) {
//            throw new ScopixException(pe.getMessage(), pe);
//        }
        log.debug("end");
        return dto;
    }

    /**
     * Inicializa un proceso automatico de subida de datos a reporting
     *
     * @throws ScopixException Excepcion en caso de error
     */
    public void uploadAutomaticNow() throws ScopixException {
        log.debug("start");
        processRunning = getUploadProcessCommand().execute(ProcessState.SCHEDULED);
        if (processRunning != null) {
            String userName = "System";
            Integer totProcess = getCalculaMaxRecordsUploadProcessDetailCommand().execute(processRunning);
            processRunning.setProcessState(ProcessState.RUNNING);
            processRunning.setTotalGlobal(totProcess);
            processRunning.setTotalUpload(0);
            processRunning.setStartDateProcess(new Date());
            processRunning.setLoginUserRunning(userName);
            ReportingUploadThread thread = new ReportingUploadThread();
            thread.setName("Reporting_data_uploding_automtic_" + processRunning.getId());
            thread.init(processRunning);
            thread.start();
        } else {
            log.info("No existe proceso a ejecutar");
        }
        log.debug("end");
    }

    /**
     * Cancela un proceso activo, si este ya termino retorna este a el frontend
     *
     * @param sessionId session usuario conectado
     * @return UploadProcessDTO ultimo proceso en ejecucion
     * @throws ScopixException Excepcion en caso de error
     */
    public UploadProcessDTO cancelUpload(long sessionId) throws ScopixException {
        log.debug("start");
        getSecurityManager().checkSecurity(sessionId, ReportingManagerPermissions.CANCEL_UPLOAD_PROCESS_PERMISSION);

        ReportingUploadTransferManager manager = SpringSupport.getInstance().
                findBeanByClassName(ReportingUploadTransferManager.class);
        //esto puede dejar null el processRunning
        manager.setCancelProcess(true);

        UploadProcessDTO dto = null;

        if (processRunning != null) {
            processRunning.setProcessState(ProcessState.FINISHED);
            processRunning.setMotiveClosing(MotiveClosing.MANUAL);
            dto = getTransformUploadProcessToDTO().execute(processRunning);
            processRunning = null;
        } else {
            dto = getUploadProcess(sessionId);
        }
        log.debug("end");
        return dto;
    }

    /**
     *
     * @return UploadProcessCommand instancia de commando
     */
    public UploadProcessCommand getUploadProcessCommand() {
        if (uploadProcessCommand == null) {
            uploadProcessCommand = new UploadProcessCommand();
        }
        return uploadProcessCommand;
    }

    /**
     *
     * @param uploadProcessCommand instancia de commando
     */
    public void setUploadProcessCommand(UploadProcessCommand uploadProcessCommand) {
        this.uploadProcessCommand = uploadProcessCommand;
    }

    /**
     *
     * @return TransformUploadProcessToDTO instancia de commando
     */
    public TransformUploadProcessToDTO getTransformUploadProcessToDTO() {
        if (transformUploadProcessToDTO == null) {
            transformUploadProcessToDTO = new TransformUploadProcessToDTO();
        }
        return transformUploadProcessToDTO;
    }

    /**
     *
     * @param transformUploadProcessToDTO instancia de commando
     */
    public void setTransformUploadProcessToDTO(TransformUploadProcessToDTO transformUploadProcessToDTO) {
        this.transformUploadProcessToDTO = transformUploadProcessToDTO;
    }

    /**
     *
     * @return GetAreaTypeListCommand instancia de commando
     */
    public GetAreaTypeListCommand getAreaTypeListCommand() {
        if (areaTypeListCommand == null) {
            areaTypeListCommand = new GetAreaTypeListCommand();
        }
        return areaTypeListCommand;
    }

    /**
     *
     * @param areaTypeListCommand instancia de commando
     */
    public void setAreaTypeListCommand(GetAreaTypeListCommand areaTypeListCommand) {
        this.areaTypeListCommand = areaTypeListCommand;
    }

    /**
     *
     * @return TranformAreaTypesToDTOs instancia de commando
     */
    public TranformAreaTypesToDTOs getTranformAreaTypesToDTOs() {
        if (tranformAreaTypesToDTOs == null) {
            tranformAreaTypesToDTOs = new TranformAreaTypesToDTOs();
        }
        return tranformAreaTypesToDTOs;
    }

    /**
     *
     * @param tranformAreaTypesToDTOs instancia de commando
     */
    public void setTranformAreaTypesToDTOs(TranformAreaTypesToDTOs tranformAreaTypesToDTOs) {
        this.tranformAreaTypesToDTOs = tranformAreaTypesToDTOs;
    }

    /**
     *
     * @return UploadProcessDetailListCommand instancia de commando
     */
    public UploadProcessDetailListCommand getUploadProcessDetailListCommand() {
        if (uploadProcessDetailListCommand == null) {
            uploadProcessDetailListCommand = new UploadProcessDetailListCommand();
        }
        return uploadProcessDetailListCommand;
    }

    /**
     *
     * @param uploadProcessDetailListCommand instancia de commando
     */
    public void setUploadProcessDetailListCommand(UploadProcessDetailListCommand uploadProcessDetailListCommand) {
        this.uploadProcessDetailListCommand = uploadProcessDetailListCommand;
    }

    /**
     *
     * @return TransformUploadProcessDetailListToDTO instancia de commando
     */
    public TransformUploadProcessDetailListToDTO getTransformUploadProcessDetailListToDTO() {
        if (transformUploadProcessDetailListToDTO == null) {
            transformUploadProcessDetailListToDTO = new TransformUploadProcessDetailListToDTO();
        }
        return transformUploadProcessDetailListToDTO;
    }

    /**
     *
     * @param value instancia de commando TransformUploadProcessDetailListToDTO
     */
    public void setTransformUploadProcessDetailListToDTO(TransformUploadProcessDetailListToDTO value) {
        this.transformUploadProcessDetailListToDTO = value;
    }

    /**
     *
     * @return DeleteUploadProcessDetailByIdCommand instancia de commando
     */
    public DeleteUploadProcessDetailByIdCommand getDeleteUploadProcessDetailByIdCommand() {
        if (deleteUploadProcessDetailByIdCommand == null) {
            deleteUploadProcessDetailByIdCommand = new DeleteUploadProcessDetailByIdCommand();
        }
        return deleteUploadProcessDetailByIdCommand;
    }

    /**
     *
     * @param value instancia de commando DeleteUploadProcessDetailByIdCommand
     */
    public void setDeleteUploadProcessDetailByIdCommand(DeleteUploadProcessDetailByIdCommand value) {
        this.deleteUploadProcessDetailByIdCommand = value;
    }

    /**
     *
     * @return UploadProcess processo activo de subida
     */
    public UploadProcess getProcessRunning() {
        return processRunning;
    }

    /**
     *
     * @param processRunning proceso activo de subida
     */
    public void setProcessRunning(UploadProcess processRunning) {
        this.processRunning = processRunning;
    }

    /**
     *
     * @return CalculaMaxRecordsUploadProcessDetailCommand instancia de commando
     */
    public CalculaMaxRecordsUploadProcessDetailCommand getCalculaMaxRecordsUploadProcessDetailCommand() {
        if (calculaMaxRecordsUploadProcessDetailCommand == null) {
            calculaMaxRecordsUploadProcessDetailCommand = new CalculaMaxRecordsUploadProcessDetailCommand();
        }
        return calculaMaxRecordsUploadProcessDetailCommand;
    }

    /**
     *
     * @param value instancia de commando CalculaMaxRecordsUploadProcessDetailCommand
     */
    public void setCalculaMaxRecordsUploadProcessDetailCommand(CalculaMaxRecordsUploadProcessDetailCommand value) {
        this.calculaMaxRecordsUploadProcessDetailCommand = value;
    }

    /**
     *
     * @return SaveUploadProcessCommand instancia de commando
     */
    public SaveUploadProcessCommand getSaveUploadProcessCommand() {
        if (saveUploadProcessCommand == null) {
            saveUploadProcessCommand = new SaveUploadProcessCommand();
        }
        return saveUploadProcessCommand;
    }

    /**
     *
     * @param saveUploadProcessCommand instancia de commando
     */
    public void setSaveUploadProcessCommand(SaveUploadProcessCommand saveUploadProcessCommand) {
        this.saveUploadProcessCommand = saveUploadProcessCommand;
    }

    /**
     *
     * @return GetStoreListCommand instancia de commando
     */
    public GetStoreListCommand getStoreListCommand() {
        if (storeListCommand == null) {
            storeListCommand = new GetStoreListCommand();
        }
        return storeListCommand;
    }

    /**
     *
     * @param storeListCommand instancia de commando
     */
    public void setStoreListCommand(GetStoreListCommand storeListCommand) {
        this.storeListCommand = storeListCommand;
    }

    /**
     *
     * @return SaveUploadProccessDetailCommand instancia de commando
     */
    public SaveUploadProccessDetailCommand getSaveUploadProccessDetailCommand() {
        if (saveUploadProccessDetailCommand == null) {
            saveUploadProccessDetailCommand = new SaveUploadProccessDetailCommand();
        }
        return saveUploadProccessDetailCommand;
    }

    /**
     *
     * @param saveUploadProccessDetailCommand instancia de commando
     */
    public void setSaveUploadProccessDetailCommand(SaveUploadProccessDetailCommand saveUploadProccessDetailCommand) {
        this.saveUploadProccessDetailCommand = saveUploadProccessDetailCommand;
    }

    /**
     *
     * @return GetAreaTypeCommand instancia de commando
     */
    public GetAreaTypeCommand getAreaTypeCommand() {
        if (areaTypeCommand == null) {
            areaTypeCommand = new GetAreaTypeCommand();
        }
        return areaTypeCommand;
    }

    /**
     *
     * @param areaTypeCommand instancia de commando
     */
    public void setAreaTypeCommand(GetAreaTypeCommand areaTypeCommand) {
        this.areaTypeCommand = areaTypeCommand;
    }

    /**
     * Crea un registro ReportingData dado un Observed situation
     *
     * @param observedSituationId id Observed situatcion del cual se desea crear datos en reporting
     * @throws ScopixException Excepcio en caso de Error
     */
    public void generateReportingData(int observedSituationId) throws ScopixException {
        log.debug("start");
        ObservedSituation observedSituation = null;
        try {

            observedSituation = getObservedSituationCommand().execute(observedSituationId);
            observedSituation.getObservedSituationEvaluations().isEmpty();
            observedSituation.getIndicatorValues().isEmpty();
            log.debug("[evaluateObservedSituation run] ObservedSituationId = " + observedSituation.getId());

            //ordenamos por metricTemplate.id los ObservedMetric del observedSituacion
            LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
            cols.put("metric.metricTemplate.id", Boolean.FALSE);
            SortUtil.sortByColumn(cols, observedSituation.getObservedMetrics());

            /**
             * el proceso puede retornar null para los casos en el no existe ningun valor en metricEvaluation y no ha sido
             * cancelada cantDo en falso
             */
            List<ReportingData> l = generateReportingDataByObservedSituation(observedSituation);
            if (l != null && !l.isEmpty()) {
                for (ReportingData data : l) {
                    //data.setObservedSituationId(observedSituation.getId());
                    getAddReportingDataCommand().execute(data);
                }
            } else {
                log.warn("No se genera reporting data para osId:" + observedSituationId);
            }


        } catch (Exception e) {
            log.error("ERROR = " + e, e);
            throw new ScopixException(e);
        }
        log.debug("end");
    }

    /**
     *
     * @return GetObservedSituationCommand instancia de commando
     */
    public GetObservedSituationCommand getObservedSituationCommand() {
        if (observedSituationCommand == null) {
            observedSituationCommand = new GetObservedSituationCommand();
        }
        return observedSituationCommand;
    }

    /**
     *
     * @param observedSituationCommand instancia de commando
     */
    public void setObservedSituationCommand(GetObservedSituationCommand observedSituationCommand) {
        this.observedSituationCommand = observedSituationCommand;
    }

    /**
     *
     * @return FormulasBySTAndStoreCommand instancia de commando
     */
    public FormulasBySTAndStoreCommand getFormulasBySTAndStoreCommand() {
        if (formulasBySTAndStoreCommand == null) {
            formulasBySTAndStoreCommand = new FormulasBySTAndStoreCommand();
        }
        return formulasBySTAndStoreCommand;
    }

    /**
     *
     * @param formulasBySTAndStoreCommand instancia de commando
     */
    public void setFormulasBySTAndStoreCommand(FormulasBySTAndStoreCommand formulasBySTAndStoreCommand) {
        this.formulasBySTAndStoreCommand = formulasBySTAndStoreCommand;
    }

    private ReportingData getReportingDataFromList(List<ReportingData> list, Integer omId) {
        for (ReportingData data : list) {
            if (data.getObservedMetricId().equals(omId)) {
                return data;
            }
        }
        return null;
    }

    private List<ReportingData> generateReportingDataByObservedSituation(ObservedSituation os) throws Exception {
        log.info("start");
        List<ReportingData> listReportingThere = getReportingDataListByObservedSituationCommand().execute(os.getId());
        //Date evidenceDate = getDateOfEvidenceCommand().execute(os.getId());
        Double target = getTargetFromObservedSituationEvaluation().execute(os.getId());
        PendingEvaluation pendingEvaluation = os.getPendingEvaluation();
        //recorremos todos los Observed Metrics para llenar los N Reporting Data
        log.debug("ObservedMetrics size:" + os.getObservedMetrics().size());
        Integer count = os.getObservedMetrics().size();
        boolean addList = false;
        for (ObservedMetric om : os.getObservedMetrics()) {
            log.debug("EvidenceEvaluations size:" + om.getEvidenceEvaluations());
            Date evidenceDate = getDateOfEvidenceCommand().execute(om.getId());
            for (EvidenceEvaluation ee : om.getEvidenceEvaluations()) {
                addList = false;
                if (!ee.isRejected()) {
                    ReportingData data = getReportingDataFromList(listReportingThere, om.getId());
                    if (data == null) {
                        log.debug("creando new reporting_data");
                        data = new ReportingData();
                        data.setReject(false);
                        data.setMetricCount(os.getObservedMetrics().size());
                        data.setDateRecord(new Date());
                        data.setSituationTemplateId(os.getSituation().getSituationTemplate().getId());
                        data.setEvaluationUser(ee.getEvaluationUser());
                        data.setObservedMetricId(om.getId());
                        if (om.getMetricEvaluation() != null) {
                            data.setMetricVal(om.getMetricEvaluation().getMetricEvaluationResult().doubleValue());
                        }
                        data.setObservedSituationId(os.getId());
                        data.setTarget(target);
                        data.setMetricTemplateId(om.getMetric().getMetricTemplate().getId());
                        data.setMetricCount(count);
                        data.setDepartment(os.getSituation().getSituationTemplate().getAreaType().getDescription());
                        data.setProduct(os.getSituation().getSituationTemplate().getProduct().getDescription());
                        data.setAreaId(os.getSituation().getSituationTemplate().getAreaType().getId());
                        data.setStoreId(os.getObservedMetrics().get(0).getMetric().getStore().getId());
                        data.setEvidenceDate(evidenceDate);
                        //se agrega para obtener el dato desde el pendign evaluation asociado 
                        //y no de las evaluaciones particulares
                        data.setEvaluationStartDate(pendingEvaluation.getEvaluationStartDate());
                        data.setEvaluationEndDate(pendingEvaluation.getEvaluationEndDate());
                        addList = true;
                    }
                    //agregamos nueva columna 

                    data.setSentToMIS(false);
//                    if (data.getEvaluationStartDate() == null) {
//                        data.setEvaluationStartDate(ee.getInitEvaluation());
//                    } else if (ee.getInitEvaluation() != null && data.getEvaluationStartDate() != null
//                            && data.getEvaluationStartDate().after(ee.getInitEvaluation())) {
//                        data.setEvaluationStartDate(ee.getInitEvaluation());
//                    }
//                    
//                    if (data.getEvaluationEndDate() == null) {
//                        data.setEvaluationEndDate(ee.getEndEvaluation());
//                    } else if (ee.getEndEvaluation() != null && data.getEvaluationEndDate() != null
//                            && data.getEvaluationEndDate().before(ee.getEndEvaluation())) {
//                        data.setEvaluationEndDate(ee.getEndEvaluation());
//                    }

                    if (data.getCantDoReason() == null || data.getCantDoReason().length() == 0) {
                        data.setCantDoReason(ee.getCantDoReason());
                        data.setCantDo(((ee.getCantDoReason() != null && ee.getCantDoReason().length() > 0) ? true : false));
                    }

                    if (data.getMetricVal() == null && !data.isCantDo()) {
                        //no agregamos datos con sin metricVal que no esten rechazados
                        log.debug("metricVal " + data.getMetricVal() + " cant_do" + data.isCantDo());
                        continue;
                    }
                    if (addList) {
                        log.debug("Agregando a lista nuevo reporting_Data");
                        listReportingThere.add(data);
                    }
                }
            }

        }
        log.info("end");
        return listReportingThere;
    }

    /**
     *
     * @return AddReportingDataCommand instancia de commando
     */
    public AddReportingDataCommand getAddReportingDataCommand() {
        if (addReportingDataCommand == null) {
            addReportingDataCommand = new AddReportingDataCommand();
        }
        return addReportingDataCommand;
    }

    /**
     *
     * @param addReportingDataCommand instancia de commando
     */
    public void setAddReportingDataCommand(AddReportingDataCommand addReportingDataCommand) {
        this.addReportingDataCommand = addReportingDataCommand;
    }

    /**
     * Marca como rejected los datos de reporting dado los observed Metric soliciatdos, almacenando el usuario que realizo la
     * operación
     *
     * @param observedMetricIds id de Observed Metruc a revertir
     * @param userName Nombre de usuario
     */
    public void rejectReportingDataByObservedMetricsIds(List<Integer> observedMetricIds, String userName) {
        log.debug("start");
        RejectReportingDataFromObservedMetricCommand command = new RejectReportingDataFromObservedMetricCommand();
        command.execute(observedMetricIds, userName);
        log.debug("end");

    }

    /**
     * Marca co rejected los datos de reportinga dado los observed situation solicitados, almacenando el usuario que realizo la
     * operación
     *
     * @param osIds ids de Observed situation
     * @param evaluationUser usuario que realiza la operación
     */
    public void rejectReportingDataByObservedSituationsIds(Set<Integer> osIds, String evaluationUser) {
        log.debug("start");
        RejectReportingDataFromObservedSituationIdsCommand command = new RejectReportingDataFromObservedSituationIdsCommand();
        command.execute(osIds, evaluationUser);
        log.debug("end");
    }

    /**
     * crea los datos desde los oobserved situation del sistema
     *
     * @throws ScopixException Excepcion en caso de Error
     */
    public void generateDataFormObservedSituation() throws ScopixException {
        log.debug("start");
        getGenerateDataFormObservedSituation().execute();
        log.debug("end");
    }

    /**
     *
     * @return DateOfEvidenceCommand instancia de commando
     */
    public DateOfEvidenceCommand getDateOfEvidenceCommand() {
        if (dateOfEvidenceCommand == null) {
            dateOfEvidenceCommand = new DateOfEvidenceCommand();
        }
        return dateOfEvidenceCommand;
    }

    /**
     *
     * @param value instancia de commando DateOfEvidenceCommand
     */
    public void setDateOfEvidenceCommand(DateOfEvidenceCommand value) {
        this.dateOfEvidenceCommand = value;
    }

    /**
     *
     * @return GenerateDataFromObservedSituation instancia de commando
     */
    public GenerateDataFromObservedSituation getGenerateDataFormObservedSituation() {
        if (generateDataFormObservedSituation == null) {
            generateDataFormObservedSituation = new GenerateDataFromObservedSituation();
        }
        return generateDataFormObservedSituation;
    }

    /**
     *
     * @param generateDataFormObservedSituation instancia de commando
     */
    public void setGenerateDataFormObservedSituation(GenerateDataFromObservedSituation generateDataFormObservedSituation) {
        this.generateDataFormObservedSituation = generateDataFormObservedSituation;
    }

    /**
     *
     * @return TargetFromObservedSituationEvaluation instancia de commando
     */
    public TargetFromObservedSituationEvaluation getTargetFromObservedSituationEvaluation() {
        if (targetFromObservedSituationEvaluation == null) {
            targetFromObservedSituationEvaluation = new TargetFromObservedSituationEvaluation();
        }
        return targetFromObservedSituationEvaluation;
    }

    /**
     *
     * @param value instancia de commando TargetFromObservedSituationEvaluation
     */
    public void setTargetFromObservedSituationEvaluation(TargetFromObservedSituationEvaluation value) {
        this.targetFromObservedSituationEvaluation = value;
    }

    /**
     *
     * @return ReportingDataByObservedSituationCommand instancia de commando
     */
    public ReportingDataByObservedSituationCommand getReportingDataByObservedSituationCommand() {
        if (reportingDataByObservedSituationCommand == null) {
            reportingDataByObservedSituationCommand = new ReportingDataByObservedSituationCommand();
        }
        return reportingDataByObservedSituationCommand;
    }

    /**
     *
     * @param value instancia de commando
     */
    public void setReportingDataByObservedSituationCommand(ReportingDataByObservedSituationCommand value) {
        this.reportingDataByObservedSituationCommand = value;
    }

    /**
     * usado para test
     *
     * @param uploadProcessId id Processo a lanzar
     * @throws ScopixException Excepcion en caso de Error
     */
    public void uploadProcess(Integer uploadProcessId) throws ScopixException {
        if (uploadProcessId != null && processRunning == null) {
            GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
            processRunning = dao.get(uploadProcessId, UploadProcess.class);
            processRunning.getProcessDetails().isEmpty();
            Integer totProcess = getCalculaMaxRecordsUploadProcessDetailCommand().execute(processRunning);
            processRunning.setProcessState(ProcessState.RUNNING);
            processRunning.setTotalGlobal(totProcess);
            processRunning.setTotalUpload(0);
            processRunning.setStartDateProcess(new Date());
            processRunning.setLoginUserRunning("test");
            ReportingUploadThread thread = new ReportingUploadThread();
            thread.setName("Reporting_data_uploding_" + processRunning.getId());
            thread.init(processRunning);
            thread.start();
        }
    }

    /**
     *
     * @return ReportingDataListByObservedSituationCommand instancia de commando
     */
    public ReportingDataListByObservedSituationCommand getReportingDataListByObservedSituationCommand() {
        if (reportingDataListByObservedSituationCommand == null) {
            reportingDataListByObservedSituationCommand = new ReportingDataListByObservedSituationCommand();
        }
        return reportingDataListByObservedSituationCommand;
    }

    /**
     *
     * @param value instancia de commando
     */
    public void setReportingDataListByObservedSituationCommand(ReportingDataListByObservedSituationCommand value) {
        this.reportingDataListByObservedSituationCommand = value;
    }

    /**
     * Copia los datos desde reporting_data_old a reporting_data nueva version
     *
     * @throws ScopixException Excepcion en caso de Error
     * @deprecated ya no se debe utilizar ya que tablas old ya no existen
     */
    public void generateDataFromDataOld() throws ScopixException {
        log.info("start");
        getGenerateDataFromDataOldCommand().execute();
        log.info("end");

    }

    /**
     *
     * @return instancia de Commando
     * @deprecated ya no se utiliza
     */
    public GenerateDataFromDataOldCommand getGenerateDataFromDataOldCommand() {
        if (generateDataFromDataOldCommand == null) {
            generateDataFromDataOldCommand = new GenerateDataFromDataOldCommand();
        }
        return generateDataFromDataOldCommand;
    }

    /**
     *
     * @param generateDataFromDataOldCommand instancia de commando
     * @deprecated ya no se debe utilizar
     */
    public void setGenerateDataFromDataOldCommand(GenerateDataFromDataOldCommand generateDataFromDataOldCommand) {
        this.generateDataFromDataOldCommand = generateDataFromDataOldCommand;
    }

    /**
     * Responde con las ultimas evaluaciones para las situaciones solicitadas de un store
     *
     * @param situationTemplatesIds id se situation template soliciatas
     * @param storeId id de store asociado
     * @param cantGroup cantidad de grupo de datos por fecha
     * @param sessionId session de usuario conectado
     * @return Map<String, Map<String, Double>> Mapa con metricas por situacion fecha
     * @throws ScopixException Excepcion en caso de Error
     */
    public Map<String, Map<String, Double>> getLastEvaluation(List<Integer> situationTemplatesIds, Integer storeId,
            Integer cantGroup) throws ScopixException {
        log.info("start");
        //revisar como sacar sql
        LastEvaluationsCommand lastEvaluationsCommand = new LastEvaluationsCommand();
        Map<String, Map<String, Double>> ret = lastEvaluationsCommand.execute(situationTemplatesIds, storeId, cantGroup);
        log.info("end");
        return ret;
    }

    public Map<String, Map<String, Double>> getAllLastEvaluation(List<Integer> situationTemplatesIds, List<Integer> storeIds) {
        log.info("start");
        AllLastEvaluationsCommand lastEvaluationsCommand = new AllLastEvaluationsCommand();
        Map<String, Map<String, Double>> ret = lastEvaluationsCommand.execute(situationTemplatesIds, storeIds);
        log.info("end");
        return ret;
    }

    /**
     *
     * @return boolean para determinar si existen datos por subir a reporting data
     */
    public boolean existNewDataUploading() {
        boolean ret = false;
        //verificamos si existe nueva data  
        try {
            Integer count = getNewDataUploadingCommand().execute();
            ret = count > 0;
        } catch (ScopixException e) {
            log.error(e, e);
        }
        return ret;
    }

    /**
     * @return the newDataUploadingCommand
     */
    public GetNewDataUploadingCommand getNewDataUploadingCommand() {
        if (newDataUploadingCommand == null) {
            newDataUploadingCommand = new GetNewDataUploadingCommand();
        }
        return newDataUploadingCommand;
    }

    /**
     * @param newDataUploadingCommand the newDataUploadingCommand to set
     */
    public void setNewDataUploadingCommand(GetNewDataUploadingCommand newDataUploadingCommand) {
        this.newDataUploadingCommand = newDataUploadingCommand;
    }

    /**
     *
     * @return UploadProcess ultimo proceso agendado, si no existe retorna null
     */
    public UploadProcess isUploadProcessScheduled() {
        log.info("Start");
        UploadProcess up;
        try {
            up = getUploadProcessCommand().execute(ProcessState.SCHEDULED);
        } catch (ScopixException e) {
            log.warn("No existe Proceso Agendado " + e);
            up = null;
        }
        log.info("end");
        return up;
    }

    /**
     * Crea un nuevo Upload Process para todos los Store con todas sus areas para el momento que ha sido invocado
     *
     * @throws ScopixException Excepcion en caso de Error
     */
    public void createNewUploadProcess() throws ScopixException {
        //recuperamos todos los stores
        Date endDate = new Date();
        List<Store> l = getStoreListCommand().execute(null);
        List<UploadProcessDetail> listAggregate = new ArrayList<UploadProcessDetail>();
        for (Store s : l) {
            // agregamos todas las areas del store
            for (Area a : s.getAreas()) {
                UploadProcessDetail aggregate = new UploadProcessDetail();
                aggregate.setStore(s);
                aggregate.setAreaType(a.getAreaType());
                aggregate.setDateEnd(endDate);
                aggregate.setUpRecords(0);
                aggregate.setTotalRecords(0);
                listAggregate.add(aggregate);
            }
        }

        UploadProcess up = getUploadProcessCommand().execute(ProcessState.SCHEDULED);

        if (up == null) {
            up = new UploadProcess();
            up.setProcessState(ProcessState.SCHEDULED);
            up.setDateProcess(endDate);

        } else {
            if (up.getDateProcess().before(endDate)) {
                up.setDateProcess(endDate);
            }
        }
        //se coloca el usuario que esta realizando la operacion
        up.setLoginUser("System");
        getSaveUploadProcessCommand().execute(up);
        for (UploadProcessDetail d : listAggregate) {
            d.setUploadProcess(up);
        }
        getSaveUploadProccessDetailCommand().execute(listAggregate);

    }

    /**
     * recupera el estado del proceso automatico si esta habilitado o no
     *
     * @param sessionId session usuario conectado
     * @return Boolean con el estado del proceso automatico segun properties
     */
    public Boolean getStateUploadingAutomatic(long sessionId) {
        log.info("start");
        Boolean ret = Boolean.FALSE;
        ReportingDigester rd = SpringSupport.getInstance().findBeanByClassName(ReportingDigester.class);
        if (rd.getConfiguration() != null) {
            ret = rd.getConfiguration().getBoolean("uploading.state.active", Boolean.FALSE);
        }
        log.info("end");
        return ret;
    }

    /**
     * Deshabilita la subida automatica de datos a reporting modificando properties asociado
     *
     * @param sessionId session usuario conectado
     * @throws ScopixException Excepcion en caso de Error
     */
    public void disabledUploadingAutomatic(long sessionId) throws ScopixException {
        log.info("start");
        //verificar permisos
        ReportingDigester rd = SpringSupport.getInstance().findBeanByClassName(ReportingDigester.class);
        if (rd.getConfiguration() != null) {
            try {
                String userLogin = getSecurityManager().getUserName(sessionId);
                rd.getConfiguration().setProperty("user.modification", userLogin);
                rd.getConfiguration().setProperty("user.date_modification", new Date());
                rd.getConfiguration().setProperty("uploading.state.active", Boolean.FALSE);
                rd.getConfiguration().save();
                rd.removeCronTrigger();
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException("NO SE PUEDE ALMACENAR", e);
            }
        } else {
            log.debug("archivo de properties no activo");
            throw new ScopixException("NO_FILE_PROPERTIES");
        }
        log.info("end");
    }

    /**
     * Activa la subida automatica de datos a reporting modificando properties asociado
     *
     * @param sessionId session del usuario conectado
     * @throws ScopixException Excepcion en caso de Error
     */
    public void enabledUploadingAutomatic(long sessionId) throws ScopixException {
        log.info("start");
        //verificar permisos
        ReportingDigester rd = SpringSupport.getInstance().findBeanByClassName(ReportingDigester.class);
        if (rd.getConfiguration() != null) {
            try {
                String userLogin = getSecurityManager().getUserName(sessionId);
                rd.getConfiguration().setProperty("user.modification", userLogin);
                rd.getConfiguration().setProperty("user.date_modification", new Date());
                rd.getConfiguration().setProperty("uploading.state.active", Boolean.TRUE);
                rd.getConfiguration().save();
                rd.generateCronTrigger();
            } catch (ConfigurationException e) {
                log.error(e, e);
                throw new ScopixException("NO SE PUEDE ALMACENAR", e);
            }
        } else {
            log.debug("archivo de properties no activo");
            throw new ScopixException("NO_FILE_PROPERTIES");
        }
        log.info("end");
    }
}
