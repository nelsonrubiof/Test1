package com.scopix.reporting.controller.actions
{
    import com.scopix.global.ApplicationStates;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.GetStateUploadingAutomaticResultEvent;
    import com.scopix.periscope.webservices.businessservices.clients.CorporateWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.controller.commands.AddUploadProcessDetailCommand;
    import com.scopix.reporting.controller.commands.CancelUploadCommand;
    import com.scopix.reporting.controller.commands.DeleteUploadProcessDetailListCommand;
    import com.scopix.reporting.controller.commands.DisableUploadAutomaticCommand;
    import com.scopix.reporting.controller.commands.EnableUploadAutomaticCommand;
    import com.scopix.reporting.controller.commands.GetAreaTypesForCorporateCommand;
    import com.scopix.reporting.controller.commands.GetCorporateListCommand;
    import com.scopix.reporting.controller.commands.GetStateUploadingAutomaticCommand;
    import com.scopix.reporting.controller.commands.GetStoreListForCorporateCommand;
    import com.scopix.reporting.controller.commands.GetUploadProcessCommand;
    import com.scopix.reporting.controller.commands.GetUploadProcessDetailListCommand;
    import com.scopix.reporting.controller.commands.UploadProcessNowCommand;
    import com.scopix.reporting.model.ReportingModel;
    import com.scopix.reporting.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.reporting.model.arrays.ArrayOfStoreVO;
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    import com.scopix.reporting.model.events.AddUploadProcessDetailCommandResultEvent;
    import com.scopix.reporting.model.events.DisableUploadAutomaticCommandResultEvent;
    import com.scopix.reporting.model.events.EnableUploadAutomaticCommandResultEvent;
    import com.scopix.reporting.model.events.GetAreaTypesForCorporateCommandResultEvent;
    import com.scopix.reporting.model.events.GetCorporateListCommandResultEvent;
    import com.scopix.reporting.model.events.GetStateUploadingAutomaticCommandResultEvent;
    import com.scopix.reporting.model.events.GetStoreListForCorporateCommandResultEvent;
    import com.scopix.reporting.model.events.GetUploadProcessCommandResultEvent;
    import com.scopix.reporting.model.events.GetUploadProcessDetailListCommandResultEvent;
    import com.scopix.reporting.model.vo.CorporateParameter;
    import com.scopix.reporting.model.vo.CorporateVO;
    import com.scopix.reporting.model.vo.UploadProcessDetailVO;
    import com.scopix.reporting.model.vo.UploadProcessVO;
    import com.scopix.reporting.view.UploadDialogList;
    import com.scopix.security.model.SecurityModel;
    
    import commons.PopUpUtils;
    import commons.events.GenericErrorEvent;
    
    import flash.events.TimerEvent;
    import flash.utils.Timer;
    
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.events.CloseEvent;
    import mx.formatters.DateFormatter;
    import mx.managers.PopUpManager;
    import mx.resources.ResourceManager;
    import mx.rpc.events.ResultEvent;
    
    public class ReportingAction
    {
        private static var _instance:ReportingAction;
        private var warningAlert:Alert;

        /** modelos usados **/
        private var securityModel:SecurityModel;
        private var reportingModel:ReportingModel;
        private var dateFormatLocal:DateFormatter;
        
        public static function getInstance():ReportingAction {
            if (_instance == null) {
                _instance = new ReportingAction();
            }
            
            return _instance;
        }
        
        public function ReportingAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            
            securityModel = SecurityModel.getInstance();
            reportingModel = ReportingModel.getInstance();
            dateFormatLocal = new DateFormatter();
        }
        
        public function showSelectCorporate():void {
            //cargando formatos de fechas
            GlobalParameters.getInstance().dateFormat = ResourceManager.getInstance().getString('resources', 'date_format');
            GlobalParameters.getInstance().dateTimeFormat = ResourceManager.getInstance().getString('resources', 'date_time_format');

            var getCorporateListCommand:GetCorporateListCommand = new GetCorporateListCommand();
            getCorporateListCommand.addCommandListener(getCorporateListResult, applicationError);
            getCorporateListCommand.execute(securityModel.userName, securityModel.sessionId);
        }
        
        private function getCorporateListResult(event:GetCorporateListCommandResultEvent):void {
            reportingModel.corporateList = event.corporateList;
            
            PopUpUtils.getInstance().showLoading(false);
            Application.application.currentState = ApplicationStates.SELECT_CORPORATE;
        }
        
        public function controlPanelView(corporateSelected:CorporateVO):void {
            PopUpUtils.getInstance().showLoading(true);
            
            reportingModel.tnControlPanelSelectedIndex = 0;
            
            reportingModel.corporateIdSelected = corporateSelected.id;
            reportingModel.corporateDescriptionSelected = corporateSelected.description;
            reportingModel.tnControlPanelSelectedIndex = 0;
            
            //creando la instancia del webservice al CORE de acuerdo a la URL configurada
            //en el archivo de parametros
            var cp:CorporateParameter = GlobalParameters.getInstance().getCorporateParameter(corporateSelected.id);
            if (cp != null && 
                cp.corporateUrl != null && cp.corporateUrl.length > 0 &&
                cp.reportingUrl != null && cp.reportingUrl.length > 0) {
                CorporateWebServicesClient.getInstance().config(cp.corporateUrl);
                ReportingWebServicesClient.getInstance().config(cp.reportingUrl);
            } else {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage('selectcorporate.corporate_parameter_not_exist', 'commons.error');
                return;
            }

            //cargando stores
            var command:GetStoreListForCorporateCommand = new GetStoreListForCorporateCommand();
            command.addCommandListener(getStoreListForCorporateResult, applicationError);
            command.execute(securityModel.sessionId);
            
            //cargar area types
            //obtener el Upload Process DTO, si viene nulo es porque no hay un proceso corriendo
            //PopUpUtils.getInstance().showLoading(false);
            //Application.application.currentState = ApplicationStates.CONTROL_PANEL;
        }
        
        private function getStoreListForCorporateResult(evt:GetStoreListForCorporateCommandResultEvent):void {
            reportingModel.storeList = evt.storeList;
            
			var command:GetStateUploadingAutomaticCommand = new GetStateUploadingAutomaticCommand();	
			command.addCommandListener(getStatusEnableDisableUploadingAutomaticResult, applicationError);
			command.execute(securityModel.sessionId);
            
        }
		
		private function getStatusEnableDisableUploadingAutomaticResult(evt:GetStateUploadingAutomaticCommandResultEvent):void {
			reportingModel.enableUploadingAutomaticStatus = evt.state;
			if (reportingModel.enableUploadingAutomaticStatus==true) {
				reportingModel.enableUploadingAutomaticLabel = ResourceManager.getInstance().getString('resources','statuscomponent.disabledAutomaticProcess');
			} else {
				reportingModel.enableUploadingAutomaticLabel = ResourceManager.getInstance().getString('resources','statuscomponent.enabledAutomaticProcess');
			}
			//recuperamos las areas para el corporate
			var command:GetAreaTypesForCorporateCommand = new GetAreaTypesForCorporateCommand();
			command.addCommandListener(getAreaTypeListForCorporateResult, applicationError);
			command.execute(securityModel.sessionId);
		}
        
        private function getAreaTypeListForCorporateResult(evt:GetAreaTypesForCorporateCommandResultEvent):void {
            reportingModel.areaTypeList = evt.areaTypeList;
            
            var command:GetUploadProcessCommand = new GetUploadProcessCommand();
            command.addCommandListener(getUploadProcessResult, applicationError);
            
            //esta es la llamada oficial
            command.execute(securityModel.sessionId);
            
            //solo para pruebas
            //command.execute(0);
        }
		
        
        private function getUploadProcessResult(evt:GetUploadProcessCommandResultEvent):void {
            var up:UploadProcessVO = evt.uploadProcess;
            
            if (up != null) {
                updateUploadData(up);
                if (up.processState == "RUNNING") {
					reportingModel.enableUploadingAutomaticButtonEnable= true;
                    //proceso en ejecución
                    reportingModel.cancelProcessButtonEnable = true;
		            reportingModel.runNowButtonEnable = false;
					
					reportingModel.autoRefreshStatus = true;
					
                    //agendar llamada por timer
                    reportingModel.statusProcessTimer = new Timer(5 * 1000, 0);
                    reportingModel.statusProcessTimer.addEventListener(TimerEvent.TIMER, getStatusProcessTimerResult);
                    reportingModel.statusProcessTimer.start();
                
					reportingModel.autoRefreshLabel = ResourceManager.getInstance().getString('resources','statuscomponent.cancelAutoRefresh');
					
                    PopUpUtils.getInstance().showLoading(false);
                    Application.application.currentState = ApplicationStates.CONTROL_PANEL;
                    
                } else {
					reportingModel.enableUploadingAutomaticButtonEnable= true;
                	//si esta finalizado pedimos el detalle
                	reportingModel.cancelProcessButtonEnable = false;
	                reportingModel.runNowButtonEnable = true;
					reportingModel.autoRefreshStatus = false;
										
					reportingModel.autoRefreshLabel = ResourceManager.getInstance().getString('resources','statuscomponent.cancelAutoRefresh');
	                //solicitando datos del proceso definido
	                var command1:GetUploadProcessDetailListCommand = new GetUploadProcessDetailListCommand();
	                command1.addCommandListener(getUploadProcessDetailListResult, applicationError);
	                command1.execute(securityModel.sessionId);
                }
                
            } else {
                //no hay proceso en ejecución
                reportingModel.cancelProcessButtonEnable = false;
                reportingModel.runNowButtonEnable = true;
                
                up = new UploadProcessVO();
                updateUploadData(up);
                
                var command:GetUploadProcessDetailListCommand = new GetUploadProcessDetailListCommand();
                command.addCommandListener(getUploadProcessDetailListResult, applicationError);
                command.execute(securityModel.sessionId);
            }
        }
        
        private function getUploadProcessDetailListResult(evt:GetUploadProcessDetailListCommandResultEvent):void {
            reportingModel.toProcessList = evt.uploadProcessDetailList;
            
            reportingModel.deleteButtonEnable = false;

            PopUpUtils.getInstance().showLoading(false);
            Application.application.currentState = ApplicationStates.CONTROL_PANEL;
        }

        public function addUploadProcessDetail(stList:ArrayOfStoreVO, atList:ArrayOfAreaTypeVO, edDate:Date):void {
            if (stList == null || atList == null || stList.length == 0 || atList.length == 0) {
                Alert.show(ResourceManager.getInstance().getString('resources','definitioncomponent.store_areatype_must_be_selected'), 
                           ResourceManager.getInstance().getString('resources','commons.error'), 
                           Alert.OK, 
                           null, null);
                return;
            }
            
            if (edDate == null) {
                Alert.show(ResourceManager.getInstance().getString('resources','definitioncomponent.date_must_be_selected'), 
                           ResourceManager.getInstance().getString('resources','commons.error'), 
                           Alert.OK, 
                           null, null);
                return;
            }
            
            PopUpUtils.getInstance().showLoading(true);
            
            reportingModel.endDate = edDate;
            
            var command:AddUploadProcessDetailCommand = new AddUploadProcessDetailCommand();
            command.addCommandListener(addUploadProcessDetailResult, applicationError);
            command.execute(stList, atList, edDate, securityModel.sessionId);
        }
        
        private function addUploadProcessDetailResult(evt:AddUploadProcessDetailCommandResultEvent):void {
            reportingModel.toProcessList = evt.uploadProcessDetailListAggregate;
            
            if (evt.uploadProcessDetailListUnknow.length > 0) {

				var customAlert:UploadDialogList = PopUpManager.createPopUp(Application.application.controlPanel, UploadDialogList, true) as UploadDialogList;
                customAlert.title = ResourceManager.getInstance().getString('resources','commons.info');
                customAlert.subTitle = ResourceManager.getInstance().getString('resources','definitioncomponent.combination_unexist');
                customAlert.infList = evt.uploadProcessDetailListUnknow;

				PopUpManager.centerPopUp(customAlert);

            }
            
            PopUpUtils.getInstance().showLoading(false);
        }
        
        public function deleteUploadProcessDetailList():void {
            
            var listToDelete:ArrayOfUploadProcessDetailVO = new ArrayOfUploadProcessDetailVO();
            
            for each (var upd:UploadProcessDetailVO in reportingModel.toProcessList) {
                if (upd.selected) {
                    listToDelete.addUploadProcessDetailVO(upd);
                }
            }
            
            if (listToDelete.length > 0) {
                PopUpUtils.getInstance().showLoading(true);
                
                var command:DeleteUploadProcessDetailListCommand = new DeleteUploadProcessDetailListCommand();
                command.addCommandListener(deleteUploadProcessDetailListResult, applicationError);
                command.execute(listToDelete, securityModel.sessionId);
            }
        }
        
        private function deleteUploadProcessDetailListResult(evt:GetUploadProcessDetailListCommandResultEvent):void {
            reportingModel.toProcessList = evt.uploadProcessDetailList;
            
            reportingModel.deleteButtonEnable = false;
            
            if (reportingModel.toProcessList.length == 0) {
                reportingModel.selectAllValue = false;
            }
            
            PopUpUtils.getInstance().showLoading(false);
        }
        
        public function uploadNow():void {
            Alert.show(ResourceManager.getInstance().getString
                ('resources','definitioncomponent.confirm_upload'), 
                       ResourceManager.getInstance().getString('resources','commons.warning'), 
                       Alert.YES | Alert.CANCEL, 
                       null, confirmUpload);
        }
        
        private function confirmUpload(evt:CloseEvent):void {
            if (evt.detail == Alert.YES) {
                PopUpUtils.getInstance().showLoading(true);
                
                var command:UploadProcessNowCommand = new UploadProcessNowCommand();
                command.addCommandListener(uploadProcessNowResult, applicationError);
                command.execute(securityModel.sessionId);
            }
        }
        
        private function uploadProcessNowResult(evt:GetUploadProcessCommandResultEvent):void {
            var up:UploadProcessVO = evt.uploadProcess;
            
            reportingModel.cancelProcessButtonEnable = true;
            reportingModel.runNowButtonEnable = false;
            
            updateUploadData(up);
            
            //cambiar vista del tab navigator, agendar consultas del estado del proceso
            reportingModel.tnControlPanelSelectedIndex = 0;
            
            //limpiar formulario de definicion de proceso
            cleanDefinitionForm();
            
            reportingModel.statusProcessTimer = new Timer(5 * 1000, 0);
            reportingModel.statusProcessTimer.addEventListener(TimerEvent.TIMER, getStatusProcessTimerResult);
            reportingModel.statusProcessTimer.start();

			//actualizar button de refresh
			reportingModel.autoRefreshButtonEnable = true;
			reportingModel.autoRefreshStatus = true;
			reportingModel.autoRefreshLabel = ResourceManager.getInstance().getString('resources','statuscomponent.cancelAutoRefresh');
            PopUpUtils.getInstance().showLoading(false);
        }
        
        private function getStatusProcessTimerResult(evt:TimerEvent):void {
            var command:GetUploadProcessCommand = new GetUploadProcessCommand();
            command.addCommandListener(getStatusUploadProcessResult, applicationError);
            command.execute(securityModel.sessionId);
        }
        
        private function getStatusUploadProcessResult(evt:GetUploadProcessCommandResultEvent):void {
            var up:UploadProcessVO = evt.uploadProcess;
            
            if (up != null) {
                updateUploadData(up);
                if (up.processState == "FINISHED") {
                	reportingModel.cancelProcessButtonEnable = false;
	                reportingModel.runNowButtonEnable = true;
	                
	                stopGetStatusUploadProcess();
	            }
            } else {
                reportingModel.cancelProcessButtonEnable = false;
                reportingModel.runNowButtonEnable = true;
                
                stopGetStatusUploadProcess();
            }
        }
        
        public function cancelUpload():void {
            PopUpUtils.getInstance().showLoading(true);
            
            //agregar logica para pedir confirmacion antes??
            
            var command:CancelUploadCommand = new CancelUploadCommand();
            command.addCommandListener(cancelUploadResult, applicationError);
            command.execute(securityModel.sessionId);
        }
		
		public function enabledDisablesRefresh():void {
				if (reportingModel.autoRefreshStatus == true) { //autorefresh activo
					reportingModel.autoRefreshStatus = false;
					reportingModel.autoRefreshLabel = ResourceManager.getInstance().getString('resources','statuscomponent.autoRefresh');
					stopGetStatusUploadProcess();
				} else if (reportingModel.autoRefreshStatus == false){ //autorefresh desactivado
					reportingModel.autoRefreshStatus = true;
					reportingModel.autoRefreshLabel = ResourceManager.getInstance().getString('resources','statuscomponent.cancelAutoRefresh');
					reportingModel.statusProcessTimer.addEventListener(TimerEvent.TIMER, getStatusProcessTimerResult);
					reportingModel.statusProcessTimer.start();
				}
		}
        
        private function cancelUploadResult(evt:GetUploadProcessCommandResultEvent):void {
            var up:UploadProcessVO = evt.uploadProcess;

            reportingModel.cancelProcessButtonEnable = false;
            reportingModel.runNowButtonEnable = true;
			
			
			//actualizar button de refresh
			reportingModel.autoRefreshButtonEnable = false;
			reportingModel.autoRefreshStatus = true;
			reportingModel.autoRefreshLabel = ResourceManager.getInstance().getString('resources','statuscomponent.cancelAutoRefresh');

            updateUploadData(up);
            
			//el getStatusUploadProcessResult se encarga de revisar si el proceso actual esta finalizado si es asi se cancela el refresh
            //stopGetStatusUploadProcess();

            PopUpUtils.getInstance().showLoading(false);

/*             Alert.show(ResourceManager.getInstance().getString('resources','definitioncomponent.store_areatype_must_be_selected'), 
                       ResourceManager.getInstance().getString('resources','commons.error'), 
                       Alert.OK, 
                       null, null); */
        }
		
		
		//Manejo de Subida Automática
		public function enableDisableUploadingAutomatic():void {
			if (reportingModel.enableUploadingAutomaticStatus == true) { //Subida Automatica activo
				reportingModel.enableUploadingAutomaticStatus = false;
				reportingModel.enableUploadingAutomaticLabel = ResourceManager.getInstance().getString('resources','statuscomponent.enabledAutomaticProcess');
				disableUploadAutomatic();
				//enviar cambio de estado a backEnd
			} else if (reportingModel.enableUploadingAutomaticStatus == false){ //Subida Automatica desactivado
				reportingModel.enableUploadingAutomaticStatus = true;
				reportingModel.enableUploadingAutomaticLabel = ResourceManager.getInstance().getString('resources','statuscomponent.disabledAutomaticProcess');
				//enviar cambio de estado a backEnd
				enableUploadAutomatic();
			}
		}
		
		private function disableUploadAutomatic():void {
			// bloqueamos todos los procesos visuales
			PopUpUtils.getInstance().showLoading(true);
			
			var command:DisableUploadAutomaticCommand = new DisableUploadAutomaticCommand();
			command.addCommandListener(disableUploadAutomaticResult, applicationError);
			command.execute(securityModel.sessionId);			
		}
		
		public function disableUploadAutomaticResult(evt:DisableUploadAutomaticCommandResultEvent):void{
			//
			PopUpUtils.getInstance().showLoading(false);
		}
		
		private function enableUploadAutomatic():void {
			// bloqueamos todos los procesos visuales
			PopUpUtils.getInstance().showLoading(true);
			
			var command:EnableUploadAutomaticCommand = new EnableUploadAutomaticCommand();
			command.addCommandListener(enableUploadAutomaticResult, applicationError);
			command.execute(securityModel.sessionId);			
		}
		
		public function enableUploadAutomaticResult(evt:EnableUploadAutomaticCommandResultEvent):void{
			//
			PopUpUtils.getInstance().showLoading(false);
		}
		
        
        private function updateUploadData(up:UploadProcessVO):void {
            //ver el tema de no aplicar logica con un objeto null, sino mas bien un estado, 
            //ya que al terminar la idea es detectar dicho cambio, de lo contrario se borrarian
            //los datos en pantalla
            
            dateFormatLocal.formatString = GlobalParameters.getInstance().dateTimeFormat;
            
    		if (up.startDate != null ) {
                reportingModel.startDateTime = dateFormatLocal.format(up.startDate);
                reportingModel.endDateTime = dateFormatLocal.format(up.endDate); 
            } else {
                reportingModel.startDateTime = "-";
                reportingModel.endDateTime= "-";
            }

            if (up.processState != null) {
                reportingModel.status = up.processState;
            } else {
                reportingModel.status = "-";
            }
            
            //reportingModel.observations = up.motiveClosing;
			reportingModel.observations = up.observations;
            reportingModel.globalPercent = up.globalPercent;
            reportingModel.globalTotal = up.globalTotal;
            reportingModel.globalProcessed = up.globalProcessed;
            
            reportingModel.onProcessList = up.processDetails;
			reportingModel.user = up.loginUser;
			reportingModel.userRunning = up.loginUserRunning;

        }
        
        public function logout():void {
            Application.application.currentState = ApplicationStates.LOGIN;
        }
        
        private function stopGetStatusUploadProcess():void {
        	if (reportingModel.statusProcessTimer != null) {
        		reportingModel.statusProcessTimer.stop();
        		reportingModel.statusProcessTimer.removeEventListener(TimerEvent.TIMER, getStatusProcessTimerResult);
        	}
        }
        
        public function validateDeleteButtonEnable():void {
            var enable:Boolean = false;
            
            for each (var upd:UploadProcessDetailVO in reportingModel.toProcessList) {
                if (upd.selected) {
                    enable = true;
                    break;
                }
            }
            
            reportingModel.deleteButtonEnable = enable;
        }
        
        public function selectAll(val:Boolean):void {
            for each (var upd:UploadProcessDetailVO in reportingModel.toProcessList) {
                upd.selected = val;
            }
            validateDeleteButtonEnable();
            reportingModel.toProcessList.refresh();
        }
        
        private function cleanDefinitionForm():void {
            reportingModel.toProcessList = null;
            reportingModel.endDate = null;
            reportingModel.selectedIndexStores = -1;
            reportingModel.selectedIndexAreaType = -1;
        }

        public function changeClient():void {
            Application.application.currentState = ApplicationStates.SELECT_CORPORATE;
        }

        /****************************************************************************************
        * Comunes
        *****************************************************************************************/
        public function applicationError(event:GenericErrorEvent):void {
            var message:String = event.message;
            
            stopGetStatusUploadProcess();
            
            PopUpManager.removePopUp(warningAlert);
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showProgressBar(false);
            PopUpUtils.getInstance().showMessage(message,'commons.error');
        }
    }
}