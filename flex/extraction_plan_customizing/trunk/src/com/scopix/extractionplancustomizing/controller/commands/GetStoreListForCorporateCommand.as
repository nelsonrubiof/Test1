package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfStoreVO;
    import com.scopix.extractionplancustomizing.model.events.GetStoreListForCorporateCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.StoreVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
    import com.scopix.test.EPCDataTest;

    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetStoreListForCorporateCommand extends BaseCommand {
        public function GetStoreListForCorporateCommand() {
            super(new GetStoreListForCorporateCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            //webService = CorporateWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);
            //addWSListener(GetStoreDTOsResultEvent.GetStoreDTOs_RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getStores(sessionId);
                    //(webService as CorporateWebServices).getStoreDTOs(sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            if (data != null) {
                data.removeItemAt(data.length - 1);
            }
            var storeList:ArrayOfStoreVO = transformToVO(data);
            (event as GetStoreListForCorporateCommandResultEvent).storeList = storeList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfStoreVO {
            var list:ArrayOfStoreVO = new ArrayOfStoreVO();
            var storeVO:StoreVO = null;

            for each (var dto:StoreDTO in data) {
                storeVO = new StoreVO();
                storeVO.fromDTO(dto)

                list.addStoreVO(storeVO);
            }

            return list;
        }
    }
}
