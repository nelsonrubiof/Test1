package com.scopix.extractionplancustomizing.controller.actions
{
    import com.scopix.enum.EPCStatesEnum;
    import com.scopix.extractionplancustomizing.controller.commands.DisableEPCListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetCorporateListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetEPCDetailListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetEvidenceProviderListStoreSituationTemplateCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetExtractionPlanCustomizingListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetGeneralDataFromLastEPCDoNotSendedCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetInitialStatusFromSendEPCCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetMetricTemplateListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetSensorListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetSituationTemplateListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetStatusFromSendEPCCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetStoreListForCorporateCommand;
    import com.scopix.extractionplancustomizing.controller.commands.NewExtractionPlanCustomizingCommand;
    import com.scopix.extractionplancustomizing.controller.commands.SaveGeneralDataCommand;
    import com.scopix.extractionplancustomizing.controller.commands.SendExtractionPlanCustomizingCommand;
    import com.scopix.extractionplancustomizing.controller.commands.SendExtractionPlanCustomizingFullCommand;
    import com.scopix.extractionplancustomizing.controller.commands.ValidateSendFullExtractionPlanCustomizingCommand;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingLastSentModel;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingModel;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    import com.scopix.extractionplancustomizing.model.events.DisableEPCListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetCorporateListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetEPCDetailListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetEvidenceProviderListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanCustomizingGeneralDataCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanCustomizingListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetMetricTemplateListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetSensorListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetSituationTemplateListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetStatusFromSendEPCCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetStoreListForCorporateCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.NewExtractionPlanCustomizingCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.SaveGeneralDataCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.SendExtractionPlanCustomizingCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.ValidateSendFullExtractionPlanCustomizingCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.CorporateParameter;
    import com.scopix.extractionplancustomizing.model.vo.CorporateVO;
    import com.scopix.extractionplancustomizing.model.vo.EvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.vo.SensorVO;
    import com.scopix.extractionplancustomizing.model.vo.SituationTemplateVO;
    import com.scopix.extractionplancustomizing.model.vo.StatusFromSendEPCVO;
    import com.scopix.extractionplancustomizing.model.vo.StoreVO;
    import com.scopix.global.ApplicationEffects;
    import com.scopix.global.ApplicationStates;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.CorporateWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.security.model.SecurityModel;
    import com.scopix.util.UtilityFunctions;
    
    import commons.PopUpUtils;
    import commons.events.GenericErrorEvent;
    
    import flash.events.TimerEvent;
    import flash.utils.Timer;
    
    import mx.collections.Sort;
    import mx.collections.SortField;
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.core.FlexGlobals;
    import mx.events.CloseEvent;
    import mx.managers.PopUpManager;
    import mx.resources.ResourceManager;
    
    public class ExtractionPlanCustomizingAction
    {
        /** variable para manejo de singleton **/
        private static var _instance:ExtractionPlanCustomizingAction;
        
        private var storeSelected:StoreVO;
        
        /** modelos usados **/
        private var securityModel:SecurityModel;
        private var epcModel:ExtractionPlanCustomizingModel;
        private var epcModelLastSent:ExtractionPlanCustomizingLastSentModel;
        
        private var warningAlert:Alert;
        
        public static const SEND_EPC:String = "sendEPC";
        public static const SEND_FULL_EPC:String = "sendFullEPC";


        public function ExtractionPlanCustomizingAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            securityModel = SecurityModel.getInstance();
            epcModel = ExtractionPlanCustomizingModel.getInstance();
            epcModelLastSent = ExtractionPlanCustomizingLastSentModel.getInstance();
        }

        public static function getInstance():ExtractionPlanCustomizingAction {
            if (_instance == null) {
                _instance = new ExtractionPlanCustomizingAction();
            }
            return _instance;
        }
        
        public function verifyEPCEdition(stId:Number, storeId:Number):void {
            PopUpUtils.getInstance().showLoading(true);
            
            var command:GetGeneralDataFromLastEPCDoNotSendedCommand = new GetGeneralDataFromLastEPCDoNotSendedCommand();
            command.addCommandListener(verifyEPCEditionResult, verifyEPCEditionFault);
            command.execute(stId, storeId, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
        }
        
        private function verifyEPCEditionResult(evt:GetExtractionPlanCustomizingGeneralDataCommandResultEvent):void {
            //epc last sent existe, continuar
            var epc:ExtractionPlanCustomizingVO = evt.epc;
            
            epcModel.selectedGlobalTabNavIndex = 0;
            epcModel.verifyEdition = true;
            
            verifyState(epc);
        }
        
        private function verifyEPCEditionFault(evt:GenericErrorEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage('edition.do_not_exist','commons.error');
            epcModel.selectedGlobalTabNavIndex = 1;
        }
                
        public function logout():void {
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.LOGIN;
        }
        
        public function showCorporateView():void {
            var getCorporateListCommand:GetCorporateListCommand = new GetCorporateListCommand();
            getCorporateListCommand.addCommandListener(getCorporateListResult, applicationError);
            getCorporateListCommand.execute(securityModel.userName, securityModel.sessionId);
        }

        private function getCorporateListResult(event:GetCorporateListCommandResultEvent):void {
            epcModel.corporateList = event.corporateList;
            
            PopUpUtils.getInstance().showLoading(false);
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.SELECT_CORPORATE;
        }
        
        public function epcListView(corporateSelected:CorporateVO):void {
            PopUpUtils.getInstance().showLoading(true);
            
            epcModel.corporateIdSelected = corporateSelected.id;
            epcModel.corporateDescriptionSelected = corporateSelected.description;
            
            //creando la instancia del webservice al CORE de acuerdo a la URL configurada
            //en el archivo de parametros
            var cp:CorporateParameter = GlobalParameters.getInstance().getCorporateParameter(corporateSelected.id);
            if (cp != null && cp.epcUrl != null && cp.epcUrl.length > 0 && cp.corporateUrl != null && cp.corporateUrl.length > 0) {
                ExtractionPlanManagerWebServicesClient.getInstance().config(cp.epcUrl);
                CorporateWebServicesClient.getInstance().config(cp.corporateUrl);
            } else {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('selectcorporate.corporate_parameter_not_exist', 'commons.error');
                return;
            }

            //cargando filtros
            var getStoreListForCorporateCommand:GetStoreListForCorporateCommand = new GetStoreListForCorporateCommand();
            getStoreListForCorporateCommand.addCommandListener(getStoreListForCorporateResult, applicationError);
            getStoreListForCorporateCommand.execute(securityModel.sessionId);
        }
        
        private function getStoreListForCorporateResult(event:GetStoreListForCorporateCommandResultEvent):void {
            if (event.storeList.length == 0) {
                PopUpUtils.getInstance().showMessage('selectcorporate.stores_not_exist', 'commons.error');
                return;
            }
            
            epcModel.storesFilter = event.storeList;
            
            epcModel.storeIdSelected = epcModel.storesFilter.getStoreVOAt(0).id;
            
            this.storeSelected = epcModel.storesFilter.getStoreVOAt(0);
            
            var getSituationTemplateListCommand:GetSituationTemplateListCommand = new GetSituationTemplateListCommand();
            getSituationTemplateListCommand.addCommandListener(getSituationTemplateListResult, applicationError);
            getSituationTemplateListCommand.execute(securityModel.sessionId);
        }
        
        private function getSituationTemplateListResult(event:GetSituationTemplateListCommandResultEvent):void {
            if (event.stList.length == 0) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('selectcorporate.situation_template_not_exist', 'commons.error');
                return;
            }
            
            epcModel.stList = event.stList;
            
            getEPCList(epcModel.storeIdSelected, EPCStatesEnum.ALL);
        }
        
        private function getEPCListResult(event:GetExtractionPlanCustomizingListCommandResultEvent):void {
            epcModel.storeDescriptionSelected = this.storeSelected.description;
            epcModel.storeIdSelected = this.storeSelected.id;
            
            //cargar los EPC para el cliente, store seleccionado
            epcModel.epcList = event.epcList;
            
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showProgressBar(false);
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.EPC_LIST;
            epcModel.verticalScrollPositionCalendar = 0;
            epcModel.filterEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
        } 
        
        public function getFilterEPCList(store:StoreVO, status:EPCStatesEnum):void {
            PopUpUtils.getInstance().showLoading(true);
            epcModel.runFilterEffect = true;
            epcModel.filterValue = status;
            
            this.storeSelected = store;
            
            getEPCList(store.id, status)
        }
        
        public function backToEPCList():void {
            PopUpUtils.getInstance().showLoading(true);
            
            getEPCList(epcModel.storeIdSelected, epcModel.filterValue);
        }
        
        public function reloadEPC():void {
            if (epcModel.isModified) {
                Alert.show(ResourceManager.getInstance().getString('resources','epcmanagement.reloadEPC'), 
                           ResourceManager.getInstance().getString('resources','commons.warning'), 
                           Alert.YES | Alert.CANCEL, 
                           null, confirmReloadEPC);
            }
        }
        
        private function confirmReloadEPC(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                PopUpManager.removePopUp(event.currentTarget as Alert);
                PopUpUtils.getInstance().showLoading(true);
/*                 var getEvidenceProviderListCommand:GetEvidenceProviderListCommand = new GetEvidenceProviderListCommand();
                getEvidenceProviderListCommand.addCommandListener(getEPListResult, applicationError);
                getEvidenceProviderListCommand.execute(epcModel.epc.id, securityModel.sessionId); */
                var command:GetEvidenceProviderListStoreSituationTemplateCommand = new GetEvidenceProviderListStoreSituationTemplateCommand();
                command.addCommandListener(getEPStoreSituationTemplateResult, applicationError);
                command.execute(epcModel.epc.store.id, epcModel.epc.situationTemplate.id, securityModel.sessionId);
            }
        }
        
        public function getEPCList(storeId:Number, status:EPCStatesEnum):void {
            var getEPCListCommand:GetExtractionPlanCustomizingListCommand = new GetExtractionPlanCustomizingListCommand(); 
            getEPCListCommand.addCommandListener(getEPCListResult, applicationError);
            //parametros: storeId, status, sessionId
            getEPCListCommand.execute(storeId, status, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
        }
        
        public function preSendEPC(epcListToSend:ArrayOfExtractionPlanCustomizingVO, sendType:String):void {

            if (epcListToSend != null && epcListToSend.length == 0) {
                PopUpUtils.getInstance().showMessage('epclist.select_epc_list', 'commons.error');
                return;
            }
        	epcModel.epcListToSend = epcListToSend;
        	epcModel.sendType = sendType;
        	
        	PopUpUtils.getInstance().showLoading(true);
        	var command:GetInitialStatusFromSendEPCCommand = new GetInitialStatusFromSendEPCCommand();
        	command.addCommandListener(preSendEPCResult, applicationError);
        	command.execute(securityModel.sessionId);
        }
        
        private function preSendEPCResult(evt:GetStatusFromSendEPCCommandResultEvent):void {
        	PopUpUtils.getInstance().showLoading(false);
        	var statusVO:StatusFromSendEPCVO = evt.statusFromSendEPCVO;
        	
        	if (statusVO == null || (statusVO.status != -1 && statusVO.status != -2)) {
                Alert.show(ResourceManager.getInstance().getString('resources','progressbar.status_is_null'), 
                           ResourceManager.getInstance().getString('resources','commons.error'), 
                           Alert.OK, 
                           null, null);
                return;
        	}
        	
            PopUpUtils.getInstance().progressBarPopUp.progressBar.setProgress(0, 100);
            
            //status -2: no hay proceso en ejecución por lo tanto realizar el proceso de envío normalmente
        	if (statusVO.status == -2) {
                if (epcModel.sendType == ExtractionPlanCustomizingAction.SEND_EPC) {
                    sendEPC();
                } else if (epcModel.sendType == ExtractionPlanCustomizingAction.SEND_FULL_EPC) {
        	        sendFullEPC();
        	    }
            }
            //status -1: existe un proceso en ejecución. Mostrar barra de progreso, actualizar estados y agendar los llamados para consultar estado 
            else if (statusVO.status == -1) {
                PopUpUtils.getInstance().showProgressBar(true);
                FlexGlobals.topLevelApplication.progressBarPopUp.addEventListener(CloseEvent.CLOSE, sendingEPCWarning);

                    PopUpUtils.getInstance().progressBarPopUp.progressBar.setProgress(statusVO.current, statusVO.max);

                if (epcModel.getStatusSendEPCTimer == null) {
                    epcModel.getStatusSendEPCTimer = new Timer(GlobalParameters.getInstance().getStatusSendEPCInterval * 1000, 0);
                }
                epcModel.getStatusSendEPCTimer.addEventListener(TimerEvent.TIMER, getStatusSendEPC);
                epcModel.getStatusSendEPCTimer.start();
            }
        }
        
        public function sendFullEPC():void {
            //determinar en flex las areas a modificar
            var validateSendFullExtractionPlanCustomizingCommand:ValidateSendFullExtractionPlanCustomizingCommand =
                new ValidateSendFullExtractionPlanCustomizingCommand();
            validateSendFullExtractionPlanCustomizingCommand.addCommandListener(validateSendFullEPCResult, applicationError);
            validateSendFullExtractionPlanCustomizingCommand.execute(epcModel.epcList);
        }
        
        private function validateSendFullEPCResult(event:ValidateSendFullExtractionPlanCustomizingCommandResultEvent):void {
            var list:ArrayOfEPCVO = event.list;
            
            if (list != null && list.length > 0) {
                //mensaje indicando areas en edicion que seran enviadas
                var areas:String = "";
                for each (var epc:ExtractionPlanCustomizingVO in list) {
                    areas = areas.concat(epc.situationTemplate.name).concat(" - ").concat(epc.area.description).concat("\n");
                }
                
                Alert.show(ResourceManager.getInstance().getString('resources','epclist.send_epc_full_areas').concat("\n\n").concat(areas).concat("\n"), 
                           ResourceManager.getInstance().getString('resources','commons.warning'), 
                           Alert.YES | Alert.NO, 
                           null, confirmSendEPCFull);
            } else {
                //mensaje indicando que se enviara el EP completo sin indicar areas en edicion ya que no hay
                Alert.show(ResourceManager.getInstance().getString('resources','epclist.send_epc_full'), 
                           ResourceManager.getInstance().getString('resources','commons.warning'), 
                           Alert.YES | Alert.NO, 
                           null, confirmSendEPCFull);
            }
        }
        
        public function sendEPC():void {
            
            if (epcModel.epcListToSend == null) {
                //enviar mensaje de error indicando que se debe seleccionar un epc para enviar
                PopUpUtils.getInstance().showMessage('epclist.select_epc_send', 'commons.error');
                return;
            }
            for each (var epcTemp2:ExtractionPlanCustomizingVO in epcModel.epcListToSend) {
                if (!epcTemp2.canBeSent) {
                    PopUpUtils.getInstance().showMessage('epclist.cannot_be_sent', 'commons.error');
                    return;
                }
            }
            
            var stArea:String = "";
            for each (var epcTemp:ExtractionPlanCustomizingVO in epcModel.epcListToSend) {
                stArea = epcTemp.situationTemplate.name.concat(" - ").concat(epcTemp.area.description).concat("\n");
            }
            
            Alert.show(ResourceManager.getInstance().getString('resources','epclist.send_epc').concat("\n\n").concat(stArea).concat("\n"), 
                       ResourceManager.getInstance().getString('resources','commons.warning'), 
                       Alert.YES | Alert.NO, 
                       null, confirmSendEPC);
        }
        
        private function confirmSendEPC(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpManager.removePopUp(evt.currentTarget as Alert);
                
                PopUpUtils.getInstance().showProgressBar(true);
                FlexGlobals.topLevelApplication.progressBarPopUp.addEventListener(CloseEvent.CLOSE, sendingEPCWarning);
                
                var sendExtractionPlanCustomizingCommand:SendExtractionPlanCustomizingCommand = new SendExtractionPlanCustomizingCommand();
                sendExtractionPlanCustomizingCommand.addCommandListener(scheduleGetStatusSendEPCTimer, applicationError);
                sendExtractionPlanCustomizingCommand.execute(epcModel.epcListToSend, epcModel.storeIdSelected, securityModel.sessionId);
            }
        }
        
        private function confirmSendEPCFull(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpManager.removePopUp(evt.currentTarget as Alert);
                
                PopUpUtils.getInstance().showProgressBar(true);
                FlexGlobals.topLevelApplication.progressBarPopUp.addEventListener(CloseEvent.CLOSE, sendingEPCWarning);
                
                var sendExtractionPlanCustomizingFullCommand:SendExtractionPlanCustomizingFullCommand = new SendExtractionPlanCustomizingFullCommand();
                sendExtractionPlanCustomizingFullCommand.addCommandListener(scheduleGetStatusSendEPCTimer, applicationError);
                sendExtractionPlanCustomizingFullCommand.execute(epcModel.storeIdSelected, securityModel.sessionId);
            }
        }
        
        private function sendingEPCWarning(evt:CloseEvent):void {

                FlexGlobals.topLevelApplication.progressBarPopUp.removeEventListener(CloseEvent.CLOSE, sendingEPCWarning);
                
                warningAlert = Alert.show(ResourceManager.getInstance().getString('resources','progressbar.warning_send_epc'), 
                           ResourceManager.getInstance().getString('resources','commons.warning'),
                           Alert.OK,
                           null, null);
                
                stopGetStatusSendEPCTimer();
                PopUpUtils.getInstance().showProgressBar(false);
        }
        
        private function scheduleGetStatusSendEPCTimer(evt:SendExtractionPlanCustomizingCommandResultEvent):void {
        	var statusVO:StatusFromSendEPCVO = evt.statusFromSendEPCVO;
        	
        	if (statusVO.status == -1) {
        		PopUpUtils.getInstance().progressBarPopUp.progressBar.setProgress(statusVO.current, statusVO.max);
        	}
        	
        	if (epcModel.getStatusSendEPCTimer == null) {
        		epcModel.getStatusSendEPCTimer = new Timer(GlobalParameters.getInstance().getStatusSendEPCInterval * 1000, 0);
        	}
        	epcModel.getStatusSendEPCTimer.addEventListener(TimerEvent.TIMER, getStatusSendEPC);
        	epcModel.getStatusSendEPCTimer.start();
        }
        
        private function getStatusSendEPC(event:TimerEvent):void {
        	var command:GetStatusFromSendEPCCommand = new GetStatusFromSendEPCCommand();
        	command.addCommandListener(getStatusSendEPCResult, applicationError);
        	command.execute(securityModel.sessionId);
        }
        
        private function getStatusSendEPCResult(evt:GetStatusFromSendEPCCommandResultEvent):void {
        	var statusVO:StatusFromSendEPCVO = evt.statusFromSendEPCVO;
        	
        	if (statusVO.status == -1) {
        		PopUpUtils.getInstance().progressBarPopUp.progressBar.setProgress(statusVO.current, statusVO.max);
        	} else {
        		FlexGlobals.topLevelApplication.progressBarPopUp.removeEventListener(CloseEvent.CLOSE, sendingEPCWarning);
        		stopGetStatusSendEPCTimer();
        		PopUpUtils.getInstance().showProgressBar(false);
        		
        		if (statusVO.status == 0) {
		            PopUpUtils.getInstance().showMessage('epclist.send_successful', 'commons.info');
		            getEPCList(epcModel.storeIdSelected, epcModel.filterValue);
        		} else if (statusVO.status == 1) {
		            PopUpUtils.getInstance().showMessage(statusVO.message, 'commons.info');
        		}
        	}
        }
        
        private function stopGetStatusSendEPCTimer():void {
        	if (epcModel.getStatusSendEPCTimer != null) {
        		epcModel.getStatusSendEPCTimer.stop();
        		epcModel.getStatusSendEPCTimer.removeEventListener(TimerEvent.TIMER, getStatusSendEPC);
        	}
        }
        
        /*
        private function sendEPCResult(event:SendExtractionPlanCustomizingCommandResultEvent):void {
            //PopUpUtils.getInstance().showLoading(false);
            
            //mostrar mensaje de envio exitoso
            PopUpUtils.getInstance().showMessage('epclist.send_successful', 'commons.info');
            getEPCList(epcModel.storeIdSelected, epcModel.filterValue);
        }*/
        
        public function manageEPC(epc:ExtractionPlanCustomizingVO):void {
            PopUpUtils.getInstance().showLoading(true);
            
            if (epc == null) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('epclist.select_epc_manage', 'commons.error');
                return;
            }
            epcModel.epc = null;
            epcModel.epcDetailList = null;
            epcModelLastSent.epc = null;
            epcModelLastSent.epcDetailList = null;

            epcModel.evidenceProviderList = null;
            epcModel.sensorList = null;
            epcModel.metricTemplateList = null;
            epcModel.multiCamera = false;
			epcModel.randomCamera = false; 
            epcModelLastSent.evidenceProviderList = null;
            epcModelLastSent.sensorList = null;
            epcModelLastSent.metricTemplateList = null;
            epcModelLastSent.multiCamera = false;
			epcModelLastSent.randomCamera = false;			

            epcModel.subtitle = epc.store.description + " - " + epc.situationTemplate.name + " - " + epc.area.description;

            epcModel.verifyEdition = false;
            epcModel.verifyLastSent = false;
            
            verifyState(epc);
        }
        
        public function verifyState(epc:ExtractionPlanCustomizingVO):void {
            //si se va a cargar uno existente, se debe verificar el estado del EPC. Si esta en "sent" se carga la pantalla
            //LAST SENT con los datos del EPC. Si esta en "edition" entonces se carga la pantalla EDITION con sus datos para que puedan ser modificados.
            if (epc.status == EPCStatesEnum.SENT) {
                ExtractionPlanCustomizingLastSentModel.getInstance().storeIdSelected = epcModel.storeIdSelected;
                ExtractionPlanCustomizingLastSentAction.getInstance().manageEPC(epc);
            } else if (epc.status == EPCStatesEnum.EDITION) {

                //guardar el epc seleccionado
                epcModel.epc = epc;
                
                //Al editar, indicar modo edicion y luego cargar datos similar al crear un nuevo epc
                epcModel.selectedTabNav = 0;
                epcModel.editMode = true;
                epcModel.loadFirstTime = true;
    
/*                 var getEvidenceProviderListCommand:GetEvidenceProviderListCommand = new GetEvidenceProviderListCommand();
                getEvidenceProviderListCommand.addCommandListener(getEPListResult, applicationError);
                getEvidenceProviderListCommand.execute(epc.id, securityModel.sessionId); */
                var command:GetEvidenceProviderListStoreSituationTemplateCommand = new GetEvidenceProviderListStoreSituationTemplateCommand();
                command.addCommandListener(getEPStoreSituationTemplateResult, applicationError);
                command.execute(epc.store.id, epc.situationTemplate.id, securityModel.sessionId);
            } else {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('epclist.epc_without_state', 'commons.error');
                return;
            }
        }
        
/*         public function getEPStoreSituationTemplateResult(event:GetEvidenceProviderListCommandResultEvent):void {
            var epList:ArrayOfEvidenceProviderVO = event.epList;
            epcModel.evidenceProviderList = epList;

            var getEvidenceProviderListCommand:GetEvidenceProviderListCommand = new GetEvidenceProviderListCommand();
            getEvidenceProviderListCommand.addCommandListener(getEPListResult, applicationError);
            getEvidenceProviderListCommand.execute(epcModel.epc.id, securityModel.sessionId);
        } */
        
        public function selectSituationTemplateView():void {
            epcModel.stSelectedIndex = 0;
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.SELECT_SITUATION_TEMPLATE;
        }
        
        public function cancelNewEPC():void {
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.EPC_LIST;
        }
        
        public function newEPC(st:SituationTemplateVO):void {
            PopUpUtils.getInstance().showLoading(true);
            
            epcModel.situationTemplateIdSelected = st.id;
            //una ves seleccionado el par tienda - situation template, se debe guardar
            //inmediatamente el epc, luego cargar las opciones como evidence providers,
            //metric templates, sensors y luego pasar al modo edicion
            epcModel.selectedGlobalTabNavIndex = 0;
            epcModel.selectedTabNav = 0;
            epcModel.editMode = false;

            var newExtractionPlanCustomizingCommand:NewExtractionPlanCustomizingCommand = new NewExtractionPlanCustomizingCommand();
            newExtractionPlanCustomizingCommand.addCommandListener(newEPCResult, applicationError);
            newExtractionPlanCustomizingCommand.execute(epcModel.storeIdSelected, st.id, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
        }

        public function newEPCResult(event:NewExtractionPlanCustomizingCommandResultEvent):void {
            var epc:ExtractionPlanCustomizingVO = event.epc;
            epcModel.epc = epc;
            epcModel.multiCamera = false;
			epcModel.randomCamera = false;
            epcModel.enableMulticamera = false;
            
            //cargando evidence providers
/*             var getEvidenceProviderListCommand:GetEvidenceProviderListCommand = new GetEvidenceProviderListCommand();
            getEvidenceProviderListCommand.addCommandListener(getEPListResult, applicationError);
            getEvidenceProviderListCommand.execute(epc.id, securityModel.sessionId); */
            var command:GetEvidenceProviderListStoreSituationTemplateCommand = new GetEvidenceProviderListStoreSituationTemplateCommand();
            command.addCommandListener(getEPStoreSituationTemplateResult, applicationError);
            command.execute(epc.store.id, epc.situationTemplate.id, securityModel.sessionId);
        }
        
        public function getEPStoreSituationTemplateResult(event:GetEvidenceProviderListCommandResultEvent):void {
            var epList:ArrayOfEvidenceProviderVO = event.epList;
            epcModel.evidenceProviderList = epList;		
            
            //agregar evidence providers que no esten
            //addMissingEvidenceProviders(epList);
            //epcModel.evidenceProviderList.addAll(epList);
            
            //cargando sensors
            var getSensorListCommand:GetSensorListCommand = new GetSensorListCommand();
            getSensorListCommand.addCommandListener(getSensorListResult, applicationError);
            getSensorListCommand.execute(epcModel.epc.id, securityModel.sessionId);
        }
        
        public function getSensorListResult(event:GetSensorListCommandResultEvent):void {
            var sensorList:ArrayOfSensorVO = event.sensorList;
            epcModel.sensorList = sensorList;
            
            //cargando metric templates
            var getMetricTemplateListCommand :GetMetricTemplateListCommand = new GetMetricTemplateListCommand();
            getMetricTemplateListCommand.addCommandListener(getMTListResult, applicationError);
            getMetricTemplateListCommand.execute(epcModel.epc.id, securityModel.sessionId);
        }
        
        public function getMTListResult(event:GetMetricTemplateListCommandResultEvent):void {
            var mtList:ArrayOfMetricTemplateVO = event.mtList;
            epcModel.metricTemplateList = mtList;
            
            epcModel.evidenceProviderListEnabled = false;

            //verificando si mostrar la pantalla para ingresar un EPC o cargar uno existente
            if (epcModel.editMode) {
                var command:GetGeneralDataFromLastEPCDoNotSendedCommand = new
                GetGeneralDataFromLastEPCDoNotSendedCommand();
                command.addCommandListener(getExtractionPlanCustomizingGeneralDataCommandResult, applicationError);
                command.execute(epcModel.epc.situationTemplate.id, epcModel.storeIdSelected, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
            } else {
                PopUpUtils.getInstance().showLoading(false);
                FlexGlobals.topLevelApplication.currentState = ApplicationStates.NEW_EDIT_EPC;
            }
            
            //epcModel.tnEditVisibleValue = true;
            //epcModel.tnLastSentVisibleValue = false;
        }
        
        private function getExtractionPlanCustomizingGeneralDataCommandResult(evt:GetExtractionPlanCustomizingGeneralDataCommandResultEvent):void {
            var epc:ExtractionPlanCustomizingVO = evt.epc;
            
            epcModel.verifyEdition = true;
			//se agrega para tener la priorization del epc
            epcModel.priorization = epc.priorization;
			//se agrega para tener el random de camaras
			epcModel.randomCamera = epc.randomCamera;
			
            epcModel.isModified = false;
            
            //actualizar variables del modelo
            epcModel.multiCamera = epc.multiCamera;
            
            //UtilityFunctions.markSelected(epcModel.evidenceProviderList, epc.evidenceProviders);
            UtilityFunctions.markSelected(epcModel.sensorList, epc.sensors);
            markMetricTemplates(epc.metricTemplates);

            trace(epcModel.metricTemplateList.getMetricTemplateVOAt(0).order);
            if (epcModel.metricTemplateList.length > 0) {
				//siempre ordenamos
			//} &&
            //    epcModel.metricTemplateList.getMetricTemplateVOAt(0).order.toString() != 'NaN') {
                var s:Sort = new Sort();
                s.fields = [new SortField("order")];
                s.compareFunction = UtilityFunctions.compareOrderMetricTemplate;
                var mtlTemp:ArrayOfMetricTemplateVO = new ArrayOfMetricTemplateVO(epcModel.metricTemplateList.toArray());
                mtlTemp.sort = s;
                
                mtlTemp.refresh();
                
                epcModel.metricTemplateList = new ArrayOfMetricTemplateVO(mtlTemp.toArray());
            } else {
                epcModel.metricTemplateList.refresh();
            }
            
            epcModel.evidenceProviderList.refresh();
            epcModel.sensorList.refresh();
            
            verifyEnableMultiCamera();
            
            PopUpUtils.getInstance().showLoading(false);
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.NEW_EDIT_EPC;
            epcModel.selectedGlobalTabNavIndex = 0;
        }
        
        public function saveEPC():void {
            PopUpUtils.getInstance().showLoading(true);
            
            var epc:ExtractionPlanCustomizingVO = new ExtractionPlanCustomizingVO();
            
            epc.id = epcModel.epc.id;
            epc.multiCamera = epcModel.multiCamera;
            epc.situationTemplate = epcModel.epc.situationTemplate;
            epc.status = epcModel.epc.status;
            epc.store = epcModel.epc.store;
            epc.area = epcModel.epc.area;
			epc.priorization = epcModel.priorization;
			epc.randomCamera = epcModel.randomCamera;
            
            //adjuntando los evidence providers
/*             var epTemp:ArrayOfEvidenceProviderVO = new ArrayOfEvidenceProviderVO();
            for each (var epEle:EvidenceProviderVO in epcModel.evidenceProviderList) {
                if (epEle.selected) {
                    epTemp.addEvidenceProviderVO(epEle);
                }
            } */
            //epc.evidenceProviders = epTemp;
            
            //adjuntando los sensores
            var sensoresTemp:ArrayOfSensorVO = new ArrayOfSensorVO();
            for each (var sEle:SensorVO in epcModel.sensorList) {
                if (sEle.selected) {
                    sensoresTemp.addSensorVO(sEle);
                }
            }
            epc.sensors = sensoresTemp;
            
			//adjuntando los metric templates
            var mtTemp:ArrayOfMetricTemplateVO = new ArrayOfMetricTemplateVO();
            for each (var mtEle:MetricTemplateVO in epcModel.metricTemplateList) {				
				if (mtEle.selected) {
					//debido a que variableName solo se ocupa para DashBoard ya no se envia
					//&& mtEle.variableName != null && mtEle.variableName.length > 0) {
                    if (mtEle.children!=null && mtEle.children.length > 0) {
						mtEle.order = mtTemp.length + 1;
                        //mtEle.order = epcModel.metricTemplateList.getMetricTemplateVOIndex(mtEle) + 1;
                        mtTemp.addMetricTemplateVO(mtEle);
                    } else {
                        PopUpUtils.getInstance().showLoading(false);
                        PopUpUtils.getInstance().showMessageParam('epcmanagement.must_select_provider', 'commons.error', mtEle.description);
                        return;
                    }
                } 
				
            }
            epc.metricTemplates = mtTemp;
            
            var command:SaveGeneralDataCommand = new SaveGeneralDataCommand();
            command.addCommandListener(saveEPCResult, applicationError);
            
            command.execute(epc, securityModel.sessionId);
        }
        
        private function saveEPCResult(evt:SaveGeneralDataCommandResultEvent): void {
            PopUpUtils.getInstance().showLoading(false);
            
            epcModel.isModified = false;
            //confirmar el guardado visualmente
            epcModel.saveEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
			epcModel.metricTemplateList.refresh();
        }
        
        public function loadCalendar():void {
            PopUpUtils.getInstance().showLoading(true);
            var calendarAction:CalendarAction = CalendarAction.getInstance();
            
            calendarAction.init();
        }
        
        public function loadEPCDetail():void {
            PopUpUtils.getInstance().showLoading(true);
            var command:GetEPCDetailListCommand = new GetEPCDetailListCommand();
            command.addCommandListener(loadEPCDetailResult, applicationError);
            command.execute(epcModel.epc.id, securityModel.sessionId);
        }
        
        private function loadEPCDetailResult(evt:GetEPCDetailListCommandResultEvent):void {
            epcModel.epcDetailList = evt.list;
            PopUpUtils.getInstance().showLoading(false);
        }
        
        private function markMetricTemplates(mtsSelected:ArrayOfMetricTemplateVO):void {
			
            for each (var mt:MetricTemplateVO in epcModel.metricTemplateList) {
                for each (var mtS:MetricTemplateVO in mtsSelected) {
                    if (mtS.id == mt.id) {
                        mt.selected = true;
                        mt.variableName = mtS.variableName;
                        mt.order = mtS.order;
                        
                        break;
                    }
                }
            }
			//agregamos orden a las otras metricas para mantener el orden alfabetico
			
			var pos:Number = 1;
			if (mtsSelected != null) {
				pos = mtsSelected.length + 1;
			}
			for each(var mr:MetricTemplateVO in epcModel.metricTemplateList ) {
				if ((mr as MetricTemplateVO).order.toString() == 'NaN') {
					(mr as MetricTemplateVO).order = pos;
					pos++;
				}
			}
        }
		
		public function verifyEnableMultiCamera():void {
		    var cont:Number = 0;
		    
		    //cambio, se habilita multicamara si existe mas de un provider para la dupla store-situation template
		    if (epcModel.evidenceProviderList.length > 1) {
		        epcModel.enableMulticamera = true;
		    } else {
		        epcModel.enableMulticamera = false;
		    }
		}
		
		private function addMissingEvidenceProviders(list:ArrayOfEvidenceProviderVO):void {
		    var allList:ArrayOfEvidenceProviderVO = epcModel.evidenceProviderList;
		    
		    for each (var ep:EvidenceProviderVO in list) {
		        var temp:EvidenceProviderVO = UtilityFunctions.searchEvidenceProvider(ep.id, allList);
		        if (temp.id <= 0) {
		            epcModel.evidenceProviderList.addEvidenceProviderVO(ep);
		        }
		    }
		}
		
		public function addProviderToMetricTemplateSelected(ep:EvidenceProviderVO):void {
		    if (epcModel.metricTemplateSelected.children == null) {
		        epcModel.metricTemplateSelected.children = new ArrayOfEvidenceProviderVO();
		    }
		    epcModel.metricTemplateSelected.children.addEvidenceProviderVO(ep);
		}
		
		public function removeProviderFromMetricTemplateSelected(ep:EvidenceProviderVO):void {
		    var cont:Number = 0;
		    for each (var epTemp:EvidenceProviderVO in epcModel.metricTemplateSelected.children) {
		        if (epTemp.id == ep.id) {
		            epcModel.metricTemplateSelected.children.removeItemAt(cont);
		            return;
		        }
		        cont++;
		    }
		}
		
		public function removeProvidersFromMetricTemplateUnselected(mt:MetricTemplateVO):void {
		    for each (var ep:EvidenceProviderVO in mt.children) {
		        ep.selected = false;
		    }
		    if (mt.children != null) {
		        mt.children.removeAll();
		    }
		}
        
        public function disableEPCList(list:ArrayOfExtractionPlanCustomizingVO):void {
            
            if (list == null || list.length == 0) {
                PopUpUtils.getInstance().showMessage('epclist.select_epc_disable', 'commons.error');
                return;
            }
            
            epcModel.disableList = list;
            
            Alert.show(ResourceManager.getInstance().getString('resources','epclist.confirm_disable_epc'), 
                ResourceManager.getInstance().getString('resources','commons.warning'), 
                Alert.YES | Alert.NO, 
                null, confirmDisableEPC);
        }
        
        private function confirmDisableEPC(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpManager.removePopUp(evt.currentTarget as Alert);
                PopUpUtils.getInstance().showLoading(true);
                
                var command:DisableEPCListCommand = new DisableEPCListCommand();
                command.addCommandListener(disableEPCListResult, applicationError);
                command.execute(epcModel.disableList, securityModel.sessionId);
            }
        }
        
        private function disableEPCListResult(evt:DisableEPCListCommandResultEvent):void {
            getEPCList(epcModel.storeIdSelected, epcModel.filterValue);
        }
        
        public function deleteEPCList(list:ArrayOfExtractionPlanCustomizingVO):void {
            
            if (list == null || list.length == 0) {
                PopUpUtils.getInstance().showMessage('epclist.select_epc_delete', 'commons.error');
                return;
            }
            
            for each (var epcTemp:ExtractionPlanCustomizingVO in list) {
                if (epcTemp.status != EPCStatesEnum.EDITION) {
                    PopUpUtils.getInstance().showMessage('epclist.epc_edition_only', 'commons.error');
                    return;
                }
            }
            
            epcModel.deleteList = list;
            
            Alert.show(ResourceManager.getInstance().getString('resources','epclist.confirm_delete_epc'), 
                ResourceManager.getInstance().getString('resources','commons.warning'), 
                Alert.YES | Alert.NO, 
                null, confirmDeleteEPC);
        }
        
        private function confirmDeleteEPC(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpManager.removePopUp(evt.currentTarget as Alert);
                PopUpUtils.getInstance().showLoading(true);
                
                var command:DisableEPCListCommand = new DisableEPCListCommand();
                command.addCommandListener(deleteEPCListResult, applicationError);
                command.execute(epcModel.deleteList, securityModel.sessionId);
            }
        }
        
        private function deleteEPCListResult(evt:DisableEPCListCommandResultEvent):void {
            getEPCList(epcModel.storeIdSelected, epcModel.filterValue);
        }

        public function applicationError(event:GenericErrorEvent):void {
            var message:String = event.message;
            
            FlexGlobals.topLevelApplication.progressBarPopUp.removeEventListener(CloseEvent.CLOSE, sendingEPCWarning);
            stopGetStatusSendEPCTimer();
            
            PopUpManager.removePopUp(warningAlert);
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showProgressBar(false);
            PopUpUtils.getInstance().showMessage(message,'commons.error');
        }
		
		public function setEPC(epcNew:ExtractionPlanCustomizingVO):void {
			epcModel.epc = epcNew;
		}
    }
}