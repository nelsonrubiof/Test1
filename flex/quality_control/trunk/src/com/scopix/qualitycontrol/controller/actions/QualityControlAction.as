package com.scopix.qualitycontrol.controller.actions
{	
	import com.scopix.global.ApplicationEffects;
	import com.scopix.global.ApplicationStates;
	import com.scopix.global.GlobalParameters;
	import com.scopix.periscope.webservices.businessservices.CorporateWebServicesClient;
	import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
	import com.scopix.qualitycontrol.controller.commands.AcceptSituationCommand;
	import com.scopix.qualitycontrol.controller.commands.GetCorporateListCommand;
	import com.scopix.qualitycontrol.controller.commands.GetEvidenceVOByMetricResultListComand;
	import com.scopix.qualitycontrol.controller.commands.GetMetricResultByOSListComand;
	import com.scopix.qualitycontrol.controller.commands.GetMotivoRejectdCommand;
	import com.scopix.qualitycontrol.controller.commands.GetObservedSituationFinishedCommand;
	import com.scopix.qualitycontrol.controller.commands.GetStoreListForCorporateCommand;
	import com.scopix.qualitycontrol.controller.commands.RejectedSituationCommand;
	import com.scopix.qualitycontrol.model.QualityControlModel;
	import com.scopix.qualitycontrol.model.arrays.ArrayOfInt;
	import com.scopix.qualitycontrol.model.arrays.ArrayOfObservedSituationFinishedVO;
	import com.scopix.qualitycontrol.model.arrays.ArrayOfQualityEvaluationVO;
	import com.scopix.qualitycontrol.model.events.AcceptSituationCommandResultEvent;
	import com.scopix.qualitycontrol.model.events.GetCorporateListCommandResultEvent;
	import com.scopix.qualitycontrol.model.events.GetEvidenceVOByMetricResultListComandResultEvent;
	import com.scopix.qualitycontrol.model.events.GetMetricResultByOSListComandResultEvent;
	import com.scopix.qualitycontrol.model.events.GetMotivoRejectdCommandResultEvent;
	import com.scopix.qualitycontrol.model.events.GetObservedSituationFinishedComandResultEvent;
	import com.scopix.qualitycontrol.model.events.GetStoreListForCorporateCommandResultEvent;
	import com.scopix.qualitycontrol.model.events.RejectedSituationCommandResultEvent;
	import com.scopix.qualitycontrol.model.vo.CorporateParameter;
	import com.scopix.qualitycontrol.model.vo.CorporateVO;
	import com.scopix.qualitycontrol.model.vo.MetricResultVO;
	import com.scopix.qualitycontrol.model.vo.ObservedSituationFinishedVO;
	import com.scopix.qualitycontrol.model.vo.QualityEvaluationVO;
	import com.scopix.qualitycontrol.model.vo.StoreVO;
	import com.scopix.security.model.SecurityModel;
	
	import commons.PopUpUtils;
	import commons.events.GenericErrorEvent;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Alert;
	import mx.core.FlexGlobals;
	import mx.managers.PopUpManager;

	public class QualityControlAction{
		
		private static var _instance:QualityControlAction;
/*		private var storeSelected:StoreVO;
		private var areaSelected:AreaVO;
		private var dateSelected:String;
		private var hourIniSelected:String;
		private var hourEndSelected:String;
*/		
		//modelos usados
		private var securityModel:SecurityModel;
		private var qualityModel:QualityControlModel;
		private var warningAlert:Alert;
		
		
		public function QualityControlAction(){
			if (_instance != null) {
				throw new Error("This class can only be accessed through getInstance method");
			}
			securityModel = SecurityModel.getInstance();
			qualityModel = QualityControlModel.getInstance();
		}
		
		public static function getInstance():QualityControlAction {
			if (_instance == null) {
				_instance = new QualityControlAction();
			}
			return _instance;
		}
		
		//funciones comunes 
		public function logout():void {
			qualityModel.corporateDescriptionSelected = "";
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.LOGIN;
		}
		
		public function showCorporateView():void {
			var getCorporateListCommand:GetCorporateListCommand = new GetCorporateListCommand();
			getCorporateListCommand.addCommandListener(getCorporateListResult, applicationError);
			getCorporateListCommand.execute(securityModel.userName, securityModel.sessionId);
		}
		
		private function getCorporateListResult(event:GetCorporateListCommandResultEvent):void {
			qualityModel.corporateList = event.corporateList;
			PopUpUtils.getInstance().showLoading(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.SELECT_CORPORATE;
		}
        
        private function initializeVariables():void {
            qualityModel.corporateDescriptionSelected = "";
            qualityModel.percentValue = 10;
            qualityModel.unmarkObservedSituationList = new ArrayOfObservedSituationFinishedVO();
            qualityModel.remarkObservedSituationList = new ArrayOfObservedSituationFinishedVO();
        }
		
		public function applicationError(event:GenericErrorEvent):void {
			var message:String = event.message;
			
			//Application.application.progressBarPopUp.removeEventListener(CloseEvent.CLOSE, sendingEPCWarning);
			stopGetStatusTimer();
			
			PopUpManager.removePopUp(warningAlert);
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			PopUpUtils.getInstance().showMessage(message,'commons.error');
		}
		
		//Muestra los filtros para el corporate seleccionado
		public function filterView(corporateSelected:CorporateVO):void {
			PopUpUtils.getInstance().showLoading(true);
            initializeVariables();
            
			qualityModel.corporateIdSelected = corporateSelected.id;
			qualityModel.corporateDescriptionSelected = corporateSelected.description;
			
			//creando la instancia del webservice al CORE de acuerdo a la URL configurada
			//en el archivo de parametros
			var cp:CorporateParameter = GlobalParameters.getInstance().getCorporateParameter(corporateSelected.id);
			if (cp != null && cp.qualityUrl != null && cp.qualityUrl.length > 0 &&
				cp != null && cp.corporateUrl != null && cp.corporateUrl.length > 0) {
				//cargamos los webservices asociados al corporate
				QualityControlWebServicesClient.getInstance().config(cp.qualityUrl);
				CorporateWebServicesClient.getInstance().config(cp.corporateUrl);
				qualityModel.evidenceProofUrl = cp.evidenceProofUrl;
				qualityModel.templateUrl = cp.templateUrl;
			} else {
				PopUpUtils.getInstance().showLoading(false);
				PopUpUtils.getInstance().showMessage('selectcorporate.corporate_parameter_not_exist', 'commons.error');
				return;
			}
			cleanFilterViewAll();
			//cargando filtros
			var getStoreListForCorporateCommand:GetStoreListForCorporateCommand = new GetStoreListForCorporateCommand();
			getStoreListForCorporateCommand.addCommandListener(getStoreListForCorporateResult, applicationError);
			getStoreListForCorporateCommand.execute(securityModel.sessionId);
		}
		
		private function getStoreListForCorporateResult(event:GetStoreListForCorporateCommandResultEvent):void {
			if (event.storeList.length == 0) {
				PopUpUtils.getInstance().showLoading(false);
				PopUpUtils.getInstance().showProgressBar(false);
				PopUpUtils.getInstance().showMessage('selectcorporate.stores_not_exist', 'commons.error');
				return;
			}
			
			qualityModel.storesFilter = event.storeList;
			
			qualityModel.storeIdSelected = qualityModel.storesFilter.getStoreVOAt(0).id;
			
			var storeSelected:StoreVO = qualityModel.storesFilter.getStoreVOAt(0);
			
			qualityModel.storeDescriptionSelected = storeSelected.description;
			qualityModel.storeIdSelected = storeSelected.id;
			
			qualityModel.areasFilter = storeSelected.areas;
			qualityModel.areaDescriptionSelected = storeSelected.areas.getAreaVOAt(0).description;
			qualityModel.areaIdSelected= storeSelected.areas.getAreaVOAt(0).id;

			var getMotivoRejectdCommand:GetMotivoRejectdCommand = new GetMotivoRejectdCommand();
			getMotivoRejectdCommand.addCommandListener(getMotivoRejectedResult, applicationError);
			getMotivoRejectdCommand.execute(securityModel.sessionId, QualityControlModel.MODEL);
			
		}
		
		private function getMotivoRejectedResult(event:GetMotivoRejectdCommandResultEvent):void{
			
			qualityModel.motivosRejected = event.motivoRejectedVOList;
			
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.FILTER_VIEW;
			
			qualityModel.filterEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
		}
		
		
		private function stopGetStatusTimer():void {
			if (qualityModel.getStatusTimer != null) {
				qualityModel.getStatusTimer.stop();
				//qualityModel.getStatusSendEPCTimer.removeEventListener(TimerEvent.TIMER, getStatusSendEPC);
			}
		}
		
		/**
		 * Metodo para fixear el problema de doble llamada a PopUpUtils.getInstance().showLoading(true);
		 **/
		public function getFilterSituationInit(percent:Number, reloadPercent:Boolean):void {
			PopUpUtils.getInstance().showLoading(true);
			getFilterSituation(percent, reloadPercent);
		}
		
		public function filterData(percent:Number, reloadPercent:Boolean):void {
			PopUpUtils.getInstance().showLoading(true);
			getFilterSituation(percent, reloadPercent);
		}
		
		public function getFilterSituation(percent:Number, reloadPercent:Boolean):void {
            qualityModel.percentValue = percent;
            qualityModel.reloadPercent = reloadPercent;
			qualityModel.runFilterEffect = true;
			qualityModel.observedSituationFinishedList = null;
			qualityModel.metricResultList = null;
			qualityModel.evidenceResultList = null;
			
			getSituationList(qualityModel.storeIdSelected, qualityModel.areaIdSelected, 
					qualityModel.dateSelected, qualityModel.startHourSelected, qualityModel.endHourSelected);
		}
		
		public function getSituationList(storeId:Number, areaId:Number, date:String, initialTime:String, endTime:String):void {
            
			var getSituationListCommand:GetObservedSituationFinishedCommand = new GetObservedSituationFinishedCommand(); 
			getSituationListCommand.addCommandListener(getSituationListResult, applicationError);
			//parametros: storeId, status, sessionId
			getSituationListCommand.execute(storeId, areaId, date,initialTime, endTime, securityModel.sessionId, QualityControlModel.MODEL);
		}
		
		private function getSituationListResult(event:GetObservedSituationFinishedComandResultEvent):void {
			//cargar los Resultados del Filtro
			qualityModel.observedSituationFinishedList = event.osfList;
            

            if (qualityModel.reloadPercent) {
                selectPercent(qualityModel.percentValue);
            } else {
                //remarcando los objetos que aun deben ser revisados
                for each (var osf:ObservedSituationFinishedVO in qualityModel.remarkObservedSituationList) {
                    for each (var osf2:ObservedSituationFinishedVO in qualityModel.observedSituationFinishedList) {
                        if (osf.observedSituationId == osf2.observedSituationId) {
                            osf2.selectedBackgroundColor = true;
                        }
                    }
                }
            }
            
            qualityModel.observedSituationFinishedList.refresh();
			
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.FILTER_VIEW;
			qualityModel.filterEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
		}
		
		public function getMetricResultByOS(vo:ObservedSituationFinishedVO):void {
			if (vo == null ) {
				// no hacemos nada
				return;
			}
			if (qualityModel.observedSituationFinishedSelected != null &&
				qualityModel.observedSituationFinishedSelected.observedSituationId == vo.observedSituationId) {
				//no hacemos nada ya esta en edicion
				return;
			}
			
			PopUpUtils.getInstance().showLoading(true);
			qualityModel.runFilterEffect = true;
			qualityModel.evidenceResultList = null;
			qualityModel.observedSituationFinishedSelected = vo;
			var getMetricResultByOSListCommand:GetMetricResultByOSListComand = new GetMetricResultByOSListComand(); 
			getMetricResultByOSListCommand.addCommandListener(getMetricResultByOSListResult, applicationError);
			//parametros: storeId, status, sessionId
			getMetricResultByOSListCommand.execute(vo, securityModel.sessionId, QualityControlModel.MODEL);			
		}
		
		private function getMetricResultByOSListResult(event:GetMetricResultByOSListComandResultEvent): void {
			
			//cargar los Resultados del Filtro
			qualityModel.metricResultList = event.metricResultList;
			
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.FILTER_VIEW;
			//qualityModel.filterEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
		}
		
		public function loadEvidenceByMetric(vo:MetricResultVO):void {
			if (vo == null){
				return;
			}
			if (qualityModel.metricResultSelected != null && 
				qualityModel.metricResultSelected.metricId == vo.metricId){
				//no hacemos nada es la metrica activa
				return; 
			}  
			
			PopUpUtils.getInstance().showLoading(true);
			qualityModel.runFilterEffect = true;
			qualityModel.metricResultSelected = vo;
			qualityModel.evidenceResultList = null;
			
			
			qualityModel.proofPrePath = vo.proofPrePath;
			qualityModel.evidencePrePath =vo.evidencePrePath;
			
			var getEvidenceVOByMetricResultListCommand:GetEvidenceVOByMetricResultListComand = new GetEvidenceVOByMetricResultListComand(); 
			getEvidenceVOByMetricResultListCommand.addCommandListener(getEvidenceVOByMetricResultListResult, applicationError);
			//parametros: storeId, status, sessionId
			getEvidenceVOByMetricResultListCommand.execute(vo.metricId, qualityModel.observedSituationFinishedSelected.observedSituationId, 
				securityModel.sessionId, QualityControlModel.MODEL);		
			
		}
		public function getEvidenceVOByMetricResultListResult(evt:GetEvidenceVOByMetricResultListComandResultEvent):void{
			//cargar los Resultados del Filtro
			qualityModel.evidenceResultList = evt.evidenceVOList;
			
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.FILTER_VIEW;
			//qualityModel.filterEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
		}
		
		public function rejectedSituation():void {
			PopUpUtils.getInstance().showLoading(true);
			qualityModel.runFilterEffect = true;

			var vos:ArrayOfQualityEvaluationVO = new ArrayOfQualityEvaluationVO();
            qualityModel.remarkObservedSituationList = new ArrayOfObservedSituationFinishedVO();
			
			for each (var osf:ObservedSituationFinishedVO in qualityModel.observedSituationFinishedList) {
				//generamos el VO con los datos necesarios
				if (osf.selected) {
					var vo:QualityEvaluationVO = new QualityEvaluationVO();
					vo.clasificacion = qualityModel.clasificacionSelected;
					vo.fechaEvidencia = qualityModel.dateSelected + " " +osf.evidenceDate;			
					vo.messageOperator = qualityModel.textOperator;
					vo.motivoRechazo = qualityModel.motivosRejectedSelected;
					vo.observaciones = qualityModel.observaciones;
					vo.observedSituationId = osf.observedSituationId;
					vo.operador = osf.evaluationUser;
					vo.result = "REJECTED";
					vo.user = securityModel.userName;
					vos.addQualityEvaluationVO(vo);
				} else if (osf.selectedBackgroundColor) {
                    qualityModel.remarkObservedSituationList.addObservedSituationFinishedVO(osf);
                }
			}
			
			var rejectedSituationCommand:RejectedSituationCommand = new RejectedSituationCommand();
			rejectedSituationCommand.addCommandListener(rejectedSituationResult, applicationError);
			rejectedSituationCommand.execute(vos, securityModel.sessionId, QualityControlModel.MODEL);
		}
		
		public function rejectedSituationResult(evt:RejectedSituationCommandResultEvent):void {
			//limpiamos parametros iniciales
			getFilterSituation(qualityModel.percentValue, false);
		}

		public function selectAllObservedSituationFinished(val:Boolean):void {
			for each (var osf:ObservedSituationFinishedVO in qualityModel.observedSituationFinishedList) {
				osf.selected = val;
            }

			qualityModel.acceptVisible = val;
			qualityModel.rejectedVisible = val; 
			qualityModel.observedSituationFinishedList.refresh();
		}
		
		public function viewLoading():void {
			PopUpUtils.getInstance().showLoading(true);
			qualityModel.runFilterEffect = true;
		}
		
		public function closeLoading():void {
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.FILTER_VIEW;
			qualityModel.filterEffect = ApplicationEffects.getInstance().getGlowGreenEffect();
		}
		
		public function downloadEvidence(evidenceId:Number, evidencePath:String):void {			
			var dataRequest:URLRequest = new URLRequest(qualityModel.evidenceProofUrl + "?" +
				"evidenceId=" + evidenceId +
				"&evidencePrePath=" + qualityModel.evidencePrePath+
				"&randomParam=" + Math.random());
			
			navigateToURL(dataRequest ,"_blank");			
		}
		
		public function validateSelectedObservedSituationFinished():void{
			var ret:Boolean = false;
			for each (var osf:ObservedSituationFinishedVO in qualityModel.observedSituationFinishedList) {
				ret = ret || osf.selected ;
			}			
			qualityModel.acceptVisible = ret;
			qualityModel.rejectedVisible = ret;
		}
		
		
		public function acceptSituation():void {
			PopUpUtils.getInstance().showLoading(true);
			
			var vos:ArrayOfQualityEvaluationVO = new ArrayOfQualityEvaluationVO();
            qualityModel.unmarkObservedSituationList = new ArrayOfObservedSituationFinishedVO();
			
			for each (var osf:ObservedSituationFinishedVO in qualityModel.observedSituationFinishedList) {
				//generamos el VO con los datos necesarios
				if (osf.selected ) {
					var vo:QualityEvaluationVO = new QualityEvaluationVO();
					vo.fechaEvidencia = qualityModel.dateSelected + " " +osf.evidenceDate;			
					vo.observaciones = qualityModel.observaciones;
					vo.observedSituationId = osf.observedSituationId;
					vo.operador = osf.evaluationUser;
					vo.result = "OK";
					vo.user = securityModel.userName;
					vos.addQualityEvaluationVO(vo);
                    
                    if (osf.selectedBackgroundColor) {
                        qualityModel.unmarkObservedSituationList.addObservedSituationFinishedVO(osf);
                    }
				}
			}
			
			var acceptSituationCommand:AcceptSituationCommand = new AcceptSituationCommand();
			acceptSituationCommand.addCommandListener(acceptSituationResult, applicationError);
			acceptSituationCommand.execute(vos, securityModel.sessionId, QualityControlModel.MODEL);
		}
		
		public function acceptSituationResult(evt:AcceptSituationCommandResultEvent):void {
			//limpiamos las situaciones seleccionadas
			selectAllObservedSituationFinished(false);
            
            //sacando el resaltado a las que ya fueron procesadas
            for each (var osf2:ObservedSituationFinishedVO in qualityModel.unmarkObservedSituationList) {
                osf2.selectedBackgroundColor = false;
            }
            
			PopUpUtils.getInstance().showLoading(false);
			PopUpUtils.getInstance().showProgressBar(false);
			FlexGlobals.topLevelApplication.currentState = ApplicationStates.FILTER_VIEW;
		}

		public function cleanFilterViewAll():void {
			
			qualityModel.observedSituationFinishedList = null;
			qualityModel.metricResultList = null;
			cleanFilterView();
		}
		public function cleanFilterView():void {
			qualityModel.metricResultSelected = null;
			qualityModel.evidenceResultList = null;
			qualityModel.evidencePrePath = null;
			qualityModel.proofPrePath = null;		
			
			qualityModel.motivosRejectedSelected = 0;
			qualityModel.clasificacionSelected = 0;
			qualityModel.textOperator = null;
			qualityModel.observaciones = null;
		}
        
        public function selectPercent(percent:Number):void {
            for each (var os:ObservedSituationFinishedVO in qualityModel.observedSituationFinishedList) {
                os.selectedBackgroundColor = false;
            }
            
            selectRandomList(percent);
        }
        
        private function selectRandomList(percent:Number):void {
            var posList:ArrayOfInt = new ArrayOfInt();
            var totalItems:Number = qualityModel.observedSituationFinishedList.length;
            var subTotalItems:Number = (percent * totalItems) / 100;
            var ranNumber:Number = 0;
            var pos:Number = 0;
            for (var i:Number=0; i<subTotalItems; i++) {
                ranNumber = Math.random();
                pos = Math.round(ranNumber * totalItems);
                if (posList.getNumberIndex(pos) < 0 && pos < totalItems) {
                    posList.addNumber(pos);
                } else {
                    //este if completo obliga a mantener el porcentaje de registros 
                    //en caso de repetirse la posicion generada por el random
                    //y ademas no tomar una posicion mayor al total de items
                    i--;
                    continue;
                }
                qualityModel.observedSituationFinishedList.getObservedSituationFinishedVOAt(pos).selectedBackgroundColor = true;
            }
            
            qualityModel.observedSituationFinishedList.refresh();
        }
	}
}