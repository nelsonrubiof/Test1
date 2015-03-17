package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.CopyCalendarCommandResultEvent;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class CopyCalendarCommand extends BaseCommand {
        public function CopyCalendarCommand() {
            super(new CopyCalendarCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcId:Number, stId:Number, storeId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).copyCalendar(epcId, stId, storeId, sessionId);
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
