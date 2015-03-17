package com.scopix.reporting.controller.commands
{
	import com.scopix.global.GlobalParameters;
	import com.scopix.periscope.webservices.businessservices.EnabledUploadingAutomaticResultEvent;
	import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
	import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
	import com.scopix.reporting.model.events.EnableUploadAutomaticCommandResultEvent;
	
	import commons.commands.BaseCommand;
	import commons.events.GenericErrorEvent;
	import commons.events.GenericEvent;
	import commons.events.WSFaultEvent;
	
	import mx.rpc.events.FaultEvent;
	
	public class EnableUploadAutomaticCommand extends BaseCommand
	{
		
		
		public function EnableUploadAutomaticCommand() {
			super(new EnableUploadAutomaticCommandResultEvent(), new WSFaultEvent());
		}
		
		public function execute(sessionId:Number):void {
			webService = ReportingWebServicesClient.getInstance().getWebService();
			
			addWSListener(EnabledUploadingAutomaticResultEvent.EnabledUploadingAutomatic_RESULT,
				resultWS, faultWS);
			
			if (!GlobalParameters.getInstance().test) {
				(webService as ReportingWebServices).enabledUploadingAutomatic(sessionId);
			} else {
				//test
				var t:EnabledUploadingAutomaticResultEvent = new EnabledUploadingAutomaticResultEvent();
				
				webService.dispatchEvent(t);
			}
		}
		
		private function resultWS(evt:EnabledUploadingAutomaticResultEvent):void {
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