package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.SaveExtractionPlanRangeCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class SaveExtractionPlanRangeCommand extends BaseCommand {
        public function SaveExtractionPlanRangeCommand() {
            super(new SaveExtractionPlanRangeCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcId:Number, eprVO:ExtractionPlanRangeVO, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            var dto:ExtractionPlanRangeDTO = transformToDTO(eprVO);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).saveExtractionPlanRange(epcId, dto, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ExtractionPlanRangeDTO = evt.result as ExtractionPlanRangeDTO;
            var epr:ExtractionPlanRangeVO = transformToVO(data);
            (event as SaveExtractionPlanRangeCommandResultEvent).epr = epr;
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

        private function transformToDTO(vo:ExtractionPlanRangeVO):ExtractionPlanRangeDTO {
            var dto:ExtractionPlanRangeDTO = new ExtractionPlanRangeDTO();
            dto = vo.toDTO();
            return dto;
        }
    }
}
