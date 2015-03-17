package com.scopix.reporting.controller.commands
{
	import com.scopix.global.GlobalParameters;
	import com.scopix.periscope.webservices.businessservices.GetStateUploadingAutomaticResultEvent;
	import com.scopix.periscope.webservices.businessservices.GetUploadProcessResultEvent;
	import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
	import com.scopix.periscope.webservices.businessservices.UploadProcessDTO;
	import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
	import com.scopix.reporting.model.events.GetStateUploadingAutomaticCommandResultEvent;
	
	import commons.commands.BaseCommand;
	import commons.events.GenericErrorEvent;
	import commons.events.GenericEvent;
	import commons.events.WSFaultEvent;
	
	import mx.rpc.events.FaultEvent;
	
	public class GetStateUploadingAutomaticCommand extends BaseCommand
	{
		public function GetStateUploadingAutomaticCommand() {
			super(new GetStateUploadingAutomaticCommandResultEvent(), new WSFaultEvent());
		}
		
		public function execute(sessionId:Number):void {
			webService = ReportingWebServicesClient.getInstance().getWebService();
			
			addWSListener(GetStateUploadingAutomaticResultEvent.GetStateUploadingAutomatic_RESULT,
				resultWS, faultWS);
			
			if (!GlobalParameters.getInstance().test) {
				(webService as ReportingWebServices).getStateUploadingAutomatic(sessionId);
			} else {
				//test
				var t:GetStateUploadingAutomaticResultEvent = new GetStateUploadingAutomaticResultEvent();
				
				webService.dispatchEvent(t);
			}
		}
		
		private function resultWS(evt:GetStateUploadingAutomaticResultEvent):void {
			removeWSListener();
			
			//TODO verificar si el java devuelve NULL que llega AQUI
			var data:Boolean = evt.result;
			
			
			(event as GetStateUploadingAutomaticCommandResultEvent).state= data;
			
			dispatchEvent(event);
			
			removeCommandListener();
		}
		
		private function faultWS(evt:FaultEvent):void {
			fault(evt);
			removeWSListener();
			removeCommandListener();
		}
	}
}