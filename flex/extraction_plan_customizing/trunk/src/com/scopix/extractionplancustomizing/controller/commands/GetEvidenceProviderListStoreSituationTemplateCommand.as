package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.events.GetEvidenceProviderListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.EvidenceProviderVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetEvidenceProviderListStoreSituationTemplateCommand extends BaseCommand {
        public function GetEvidenceProviderListStoreSituationTemplateCommand() {
            super(new GetEvidenceProviderListCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(storeId:Number, stId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getEvidenceProvidersStoreSituationTemplate(storeId, stId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            var epList:ArrayOfEvidenceProviderVO = transformToVO(data);
            (event as GetEvidenceProviderListCommandResultEvent).epList = epList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfEvidenceProviderVO {
            var list:ArrayOfEvidenceProviderVO = new ArrayOfEvidenceProviderVO();
            var epVO:EvidenceProviderVO = null;

            for each (var dto:EvidenceProviderDTO in data) {
                epVO = new EvidenceProviderVO();
                epVO.fromDTO(dto);
                list.addEvidenceProviderVO(epVO);
            }

            return list;
        }
    }
}
