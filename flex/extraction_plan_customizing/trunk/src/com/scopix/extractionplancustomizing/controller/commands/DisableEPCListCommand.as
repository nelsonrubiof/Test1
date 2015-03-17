package com.scopix.extractionplancustomizing.controller.commands {

	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanCustomizingVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfInt;
	import com.scopix.extractionplancustomizing.model.events.DisableEPCListCommandResultEvent;
	import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
	import com.scopix.global.GlobalParameters;
	import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
	import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
	
	import commons.commands.BaseCommand;
	import commons.events.GenericErrorEvent;
	import commons.events.GenericEvent;
	import commons.events.WSFaultEvent;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;

	public class DisableEPCListCommand extends BaseCommand {
		public function DisableEPCListCommand() {
			super(new DisableEPCListCommandResultEvent(), new WSFaultEvent());
		}
        
        public function execute(disableEPCList:ArrayOfExtractionPlanCustomizingVO, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);
            
            var idList:ArrayCollection = new ArrayCollection();
            for each (var epc:ExtractionPlanCustomizingVO in disableEPCList) {
                idList.addItem(epc.id);
            }

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).disableExtractionPlanCustomizings(idList, sessionId);
            }
        }
        
        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
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