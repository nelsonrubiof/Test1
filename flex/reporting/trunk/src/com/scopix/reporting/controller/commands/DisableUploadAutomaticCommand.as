package com.scopix.reporting.controller.commands
{
	import com.scopix.global.GlobalParameters;
	import com.scopix.periscope.webservices.businessservices.DisabledUploadingAutomaticResultEvent;
	import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
	import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
	import com.scopix.reporting.model.events.DisableUploadAutomaticCommandResultEvent;
	
	import commons.commands.BaseCommand;
	import commons.events.GenericErrorEvent;
	import commons.events.GenericEvent;
	import commons.events.WSFaultEvent;
	
	import mx.rpc.events.FaultEvent;
	
	public class DisableUploadAutomaticCommand extends BaseCommand {
		
		public function DisableUploadAutomaticCommand() {
			super(new DisableUploadAutomaticCommandResultEvent(), new WSFaultEvent());
		}
		
		public function execute(sessionId:Number):void {
			webService = ReportingWebServicesClient.getInstance().getWebService();
			
			addWSListener(DisabledUploadingAutomaticResultEvent.DisabledUploadingAutomatic_RESULT,
				resultWS, faultWS);
			
			if (!GlobalParameters.getInstance().test) {
				(webService as ReportingWebServices).disabledUploadingAutomatic(sessionId);
			} else {
				//test
				var t:DisabledUploadingAutomaticResultEvent = new DisabledUploadingAutomaticResultEvent();
				
				webService.dispatchEvent(t);
			}
		}
		
		private function resultWS(evt:DisabledUploadingAutomaticResultEvent):void {
			removeWSListener();

			//se asume que se deshabilito la subida automatica 
			
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