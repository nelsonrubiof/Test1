package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeDetailVO;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeDetailListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeDetailVO;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDetailDTO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class RegenerateDetailForRangeCommand extends BaseCommand {
        public function RegenerateDetailForRangeCommand() {
            super(new GetExtractionPlanRangeDetailListCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcId:Number, eprVO:ExtractionPlanRangeVO, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            var dto:ExtractionPlanRangeDTO = transformToDTO(eprVO);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).regenerateDetailForRange(epcId, dto, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            var eprd:ArrayOfExtractionPlanRangeDetailVO = transformToVO(data);
            (event as GetExtractionPlanRangeDetailListCommandResultEvent).eprd = eprd;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToDTO(vo:ExtractionPlanRangeVO):ExtractionPlanRangeDTO {
            var dto:ExtractionPlanRangeDTO = new ExtractionPlanRangeDTO();
            dto = vo.toDTO();
            return dto;
        }

        private function transformToVO(data:ArrayCollection):ArrayOfExtractionPlanRangeDetailVO {
            var list:ArrayOfExtractionPlanRangeDetailVO = new ArrayOfExtractionPlanRangeDetailVO();
            var vo:ExtractionPlanRangeDetailVO = null;

            for each (var dto:ExtractionPlanRangeDetailDTO in data) {
                vo = new ExtractionPlanRangeDetailVO();
                vo.fromDTO(dto);

                list.addExtractionPlanRangeDetailVO(vo);
            }

            return list;
        }
    }
}
