package com.scopix.extractionplancustomizing.controller.commands
{
	
	import com.scopix.extractionplancustomizing.model.events.CopyFullEPCToEditionResultEvent;
	import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
	import com.scopix.global.GlobalParameters;
	import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
	import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
	import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
	
	import commons.commands.BaseCommand;
	import commons.events.WSFaultEvent;
	
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	
	public class CopyEPCFullToEditionCommand extends BaseCommand
	{
		private var type:String;
		public function CopyEPCFullToEditionCommand() {
			super(new CopyFullEPCToEditionResultEvent(), new WSFaultEvent());
		}
		
		public function execute(epcId:Number, sessionId:Number, type:String):void {
			this.type = type;
			webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
			addWSListener(ResultEvent.RESULT, resultWS, faultWS);
			if (!GlobalParameters.getInstance().test) {
				//parametros: epcId, sessionId				
				(webService as ExtractionPlanManagerWebServices).copyEPCFullToEdition(epcId, sessionId);
			}
		}
		private function resultWS(evt:ResultEvent):void {
			removeWSListener();
			var data:ExtractionPlanCustomizingDTO = evt.result as ExtractionPlanCustomizingDTO;
			var epc:ExtractionPlanCustomizingVO = transformToVO(data);
			(event as CopyFullEPCToEditionResultEvent).epc = epc;
			dispatchEvent(event);
			removeCommandListener();
		}
		
		private function faultWS(evt:FaultEvent):void {
			fault(evt);
			removeWSListener();
			removeCommandListener();
		}
		
		private function transformToVO(dto:ExtractionPlanCustomizingDTO):ExtractionPlanCustomizingVO {
			var epcVO:ExtractionPlanCustomizingVO = new ExtractionPlanCustomizingVO();
			
			epcVO.fromDTO(dto, type);
			
			return epcVO;
		}
	}
}