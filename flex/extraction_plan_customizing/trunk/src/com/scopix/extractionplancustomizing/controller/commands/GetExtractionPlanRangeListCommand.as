package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeVO;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanRangeListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetExtractionPlanRangeListCommand extends BaseCommand {
        public function GetExtractionPlanRangeListCommand() {
            super(new GetExtractionPlanRangeListCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getExtractionPlanRanges(epcId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
			if (data != null) {
				data.removeItemAt(data.length - 1);
			}
            var epRangeList:ArrayOfExtractionPlanRangeVO = transformToVO(data);
            (event as GetExtractionPlanRangeListCommandResultEvent).epRangeList = epRangeList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfExtractionPlanRangeVO {
            var epRangeList:ArrayOfExtractionPlanRangeVO = new ArrayOfExtractionPlanRangeVO();
            var epRange:ExtractionPlanRangeVO = null;

            for each (var dto:ExtractionPlanRangeDTO in data) {
                epRange = new ExtractionPlanRangeVO();
                epRange.fromDTO(dto);
                epRangeList.addExtractionPlanRangeVO(epRange);
            }

            return epRangeList;
        }
    }
}
