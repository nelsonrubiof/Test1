package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetExtractionPlanRangeCommand extends BaseCommand {
        public function GetExtractionPlanRangeCommand() {
            super(new GetExtractionPlanRangeCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(eprId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getExtractionPlanRange(eprId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ExtractionPlanRangeDTO = evt.result as ExtractionPlanRangeDTO;
            var epr:ExtractionPlanRangeVO = transformToVO(data);
            (event as GetExtractionPlanRangeCommandResultEvent).epr = epr;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ExtractionPlanRangeDTO):ExtractionPlanRangeVO {
            var epr:ExtractionPlanRangeVO = new ExtractionPlanRangeVO();
            epr.fromDTO(data);
            return epr;
        }
    }
}
