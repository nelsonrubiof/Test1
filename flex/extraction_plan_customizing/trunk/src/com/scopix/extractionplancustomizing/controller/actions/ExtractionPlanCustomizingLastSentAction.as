package com.scopix.extractionplancustomizing.controller.actions
{
    import com.scopix.enum.EPCStatesEnum;
    import com.scopix.extractionplancustomizing.controller.commands.CopyEPCFullToEditionCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetEPCDetailListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetEvidenceProviderListStoreSituationTemplateCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetGeneralDataFromLastEPCSendedCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetMetricTemplateListCommand;
    import com.scopix.extractionplancustomizing.controller.commands.GetSensorListCommand;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingLastSentModel;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingModel;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    import com.scopix.extractionplancustomizing.model.events.CopyFullEPCToEditionResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetEPCDetailListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetEvidenceProviderListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanCustomizingGeneralDataCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetMetricTemplateListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.events.GetSensorListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.vo.StoreVO;
    import com.scopix.global.ApplicationEffects;
    import com.scopix.global.ApplicationStates;
    import com.scopix.security.model.SecurityModel;
    import com.scopix.util.UtilityFunctions;
    
    import commons.PopUpUtils;
    import commons.events.GenericErrorEvent;
    
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.core.FlexGlobals;
    import mx.events.CloseEvent;
    import mx.resources.ResourceManager;
    
    public class ExtractionPlanCustomizingLastSentAction
    {
        /** variable para manejo de singleton **/
        private static var _instance:ExtractionPlanCustomizingLastSentAction;
        
        private var storeSelected:StoreVO;
        
        /** modelos usados **/
        private var securityModel:SecurityModel;
        private var epcModelLastSent:ExtractionPlanCustomizingLastSentModel;
        private var epcModel:ExtractionPlanCustomizingModel;


        public function ExtractionPlanCustomizingLastSentAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            securityModel = SecurityModel.getInstance();
            epcModelLastSent = ExtractionPlanCustomizingLastSentModel.getInstance();
            epcModel = ExtractionPlanCustomizingModel.getInstance();
        }

        public static function getInstance():ExtractionPlanCustomizingLastSentAction {
            if (_instance == null) {
                _instance = new ExtractionPlanCustomizingLastSentAction();
            }
            return _instance;
        }
        
        public function verifyEPCLastSent(stId:Number, storeId:Number):void {
            PopUpUtils.getInstance().showLoading(true);
            epcModelLastSent.storeIdSelected = storeId;
            epcModelLastSent.stList = epcModel.stList;
            epcModelLastSent.storesFilter = epcModel.storesFilter;
            
            var command:GetGeneralDataFromLastEPCSendedCommand = new GetGeneralDataFromLastEPCSendedCommand();
            command.addCommandListener(verifyEPCLastSentResult, verifyEPCLastSentFault);
            command.execute(stId, storeId, securityModel.sessionId, ExtractionPlanCustomizingLastSentModel.LAST_SENT_MODEL);
        }
        
        private function verifyEPCLastSentResult(evt:GetExtractionPlanCustomizingGeneralDataCommandResultEvent):void {
            //epc last sent existe, continuar
            var epc:ExtractionPlanCustomizingVO = evt.epc;
            
            epcModel.verifyLastSent = true;
            
            manageEPC(epc);
        }
        
        private function verifyEPCLastSentFault(evt:GenericErrorEvent):void {
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage('lastsent.do_not_exist','commons.error');
            epcModel.selectedGlobalTabNavIndex = 0;
        }
        
        public function backToEPCList():void {
            PopUpUtils.getInstance().showLoading(true);
            
            ExtractionPlanCustomizingAction.getInstance().
            getEPCList(ExtractionPlanCustomizingModel.getInstance().storeIdSelected, EPCStatesEnum.ALL);
            epcModelLastSent.epc = null;
            epcModel.epc = null;
        }
        
        public function manageEPC(epc:ExtractionPlanCustomizingVO):void {
            //PopUpUtils.getInstance().showLoading(true);
            /*
            if (epc == null) {
                PopUpUtils.getInstance().showLoading(false);
                //enviar mensaje de error indicando que se debe seleccionar un epc para enviar
                PopUpUtils.getInstance().showMessage('epclist.select_epc_manage', 'commons.error');
                return;
            }*/
            //epcModel.tnEditVisibleValue = false;
            //epcModel.tnLastSentVisibleValue = true;

            //guardar el epc seleccionado
            epcModelLastSent.epc = epc;
            
            //Al editar, indicar modo edicion y luego cargar datos similar al crear un nuevo epc
            epcModel.selectedTabNavLastSent = 0;
            epcModelLastSent.loadFirstTime = true;

            var command:GetEvidenceProviderListStoreSituationTemplateCommand = new GetEvidenceProviderListStoreSituationTemplateCommand();
            command.addCommandListener(getEPListResult, applicationError);
            command.execute(epc.store.id, epc.situationTemplate.id, securityModel.sessionId);
        }
        
        public function cancelNewEPC():void {
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.EPC_LIST;
        }
        
         public function getEPListResult(event:GetEvidenceProviderListCommandResultEvent):void {
            var epList:ArrayOfEvidenceProviderVO = event.epList;
            epcModelLastSent.evidenceProviderList = epList;
            
            //cargando sensors
            var getSensorListCommand:GetSensorListCommand = new GetSensorListCommand();
            getSensorListCommand.addCommandListener(getSensorListResult, applicationError);
            getSensorListCommand.execute(epcModelLastSent.epc.id, securityModel.sessionId);
        } 
        
        public function getSensorListResult(event:GetSensorListCommandResultEvent):void {
            var sensorList:ArrayOfSensorVO = event.sensorList;
            epcModelLastSent.sensorList = sensorList;
            
            //cargando metric templates
            var getMetricTemplateListCommand :GetMetricTemplateListCommand = new GetMetricTemplateListCommand();
            getMetricTemplateListCommand.addCommandListener(getMTListResult, applicationError);
            getMetricTemplateListCommand.execute(epcModelLastSent.epc.id, securityModel.sessionId);
        }
        
        public function getMTListResult(event:GetMetricTemplateListCommandResultEvent):void {
            var mtList:ArrayOfMetricTemplateVO = event.mtList;
            epcModelLastSent.metricTemplateList = mtList;
            
            var command:GetGeneralDataFromLastEPCSendedCommand = new GetGeneralDataFromLastEPCSendedCommand();
            command.addCommandListener(getExtractionPlanCustomizingGeneralDataCommandResult, applicationError);
            command.execute(epcModelLastSent.epc.situationTemplate.id, epcModelLastSent.storeIdSelected, securityModel.sessionId, ExtractionPlanCustomizingLastSentModel.LAST_SENT_MODEL);
        }
        
        private function getExtractionPlanCustomizingGeneralDataCommandResult(evt:GetExtractionPlanCustomizingGeneralDataCommandResultEvent):void {
            var epc:ExtractionPlanCustomizingVO = evt.epc;
            
            epcModel.verifyLastSent = true;
            
            //actualizar variables del modelo
            epcModelLastSent.multiCamera = epc.multiCamera;
			epcModelLastSent.priorization = epc.priorization;
			epcModelLastSent.randomCamera = epc.randomCamera;
            
            //UtilityFunctions.markSelected(epcModelLastSent.evidenceProviderList, epc.evidenceProviders);
            UtilityFunctions.markSelected(epcModelLastSent.sensorList, epc.sensors);
            markMetricTemplates(epc.metricTemplates);
            
            epcModelLastSent.metricTemplateList = epc.metricTemplates;
            //epcModelLastSent.evidenceProviderList.refresh();
            epcModelLastSent.sensorList.refresh();
            epcModelLastSent.metricTemplateList.refresh();
            
            PopUpUtils.getInstance().showLoading(false);
            FlexGlobals.topLevelApplication.currentState = ApplicationStates.NEW_EDIT_EPC;
            epcModel.selectedGlobalTabNavIndex = 1;
            
            //epcModel.tnEditVisibleValue = false;
            //epcModel.tnLastSentVisibleValue = true;
        }
        
        public function loadCalendar():void {
            PopUpUtils.getInstance().showLoading(true);
            var calendarLastSentAction:CalendarLastSentAction = CalendarLastSentAction.getInstance();
            
            calendarLastSentAction.init();
        }

        public function loadEPCDetail():void {
            PopUpUtils.getInstance().showLoading(true);
            var command:GetEPCDetailListCommand = new GetEPCDetailListCommand();
            command.addCommandListener(loadEPCDetailResult, applicationError);
            command.execute(epcModelLastSent.epc.id, securityModel.sessionId);
        }
        
        private function loadEPCDetailResult(evt:GetEPCDetailListCommandResultEvent):void {
            epcModelLastSent.epcDetailList = evt.list;
            PopUpUtils.getInstance().showLoading(false);
        }
        
        public function applicationError(event:GenericErrorEvent):void {
            var message:String = event.message;
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage(message,'commons.error');
        }
        
        private function markMetricTemplates(mtsSelected:ArrayOfMetricTemplateVO):void {
            for each (var mt:MetricTemplateVO in epcModelLastSent.metricTemplateList) {
                for each (var mtS:MetricTemplateVO in mtsSelected) {
                    if (mtS.id == mt.id) {
                        mt.selected = true;
                        mt.variableName = mtS.variableName;
                        mt.order = mtS.order;
                        
                        var indexSource:Number = epcModelLastSent.metricTemplateList.getMetricTemplateVOIndex(mt);
                        var indexDest:Number = mt.order - 1;
                        
                        var mtTemp:MetricTemplateVO = epcModelLastSent.metricTemplateList.getMetricTemplateVOAt(indexDest);
                        
                        epcModelLastSent.metricTemplateList.setMetricTemplateVOAt(mt, indexDest);
                        epcModelLastSent.metricTemplateList.setMetricTemplateVOAt(mtTemp, indexSource);
                        break;
                    }
                }
            }
        }
		
		/**
		 * Copia de EPC Full a Edicion
		 */
		public function copyFullEPC():void {
			Alert.show(ResourceManager.getInstance().getString
				('resources','epc.confirm_copy_full_to_edition'), 
				ResourceManager.getInstance().getString('resources','commons.warning'), 
				Alert.YES | Alert.CANCEL, 
				null, confirmCopyFullEPCToEdition);
		}
		
		private function confirmCopyFullEPCToEdition(evt:CloseEvent):void {
			if (evt.detail == Alert.YES) {
				PopUpUtils.getInstance().showLoading(true);
				var command:CopyEPCFullToEditionCommand = new CopyEPCFullToEditionCommand();
				command.addCommandListener(copyFullEPCToEditionResult, applicationError);
				command.execute(epcModelLastSent.epc.id, securityModel.sessionId, ExtractionPlanCustomizingModel.MODEL);
			}
		}
		
		private function copyFullEPCToEditionResult(evt:CopyFullEPCToEditionResultEvent):void {
			
			/**
			 * recargamos el epc en edicion
			 */			
			PopUpUtils.getInstance().showLoading(false);
			epcModelLastSent.copyFullEPCToEditionEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
			ExtractionPlanCustomizingAction.getInstance().setEPC(evt.epc);
			PopUpUtils.getInstance().showLoading(true);
			var command:GetEvidenceProviderListStoreSituationTemplateCommand = new GetEvidenceProviderListStoreSituationTemplateCommand();
			command.addCommandListener(ExtractionPlanCustomizingAction.getInstance().getEPStoreSituationTemplateResult, applicationError);
			command.execute(epcModelLastSent.epc.store.id, epcModelLastSent.epc.situationTemplate.id, securityModel.sessionId);
			
		}
    }
}