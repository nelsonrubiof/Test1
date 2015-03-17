package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServices;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfStoreVO;
    import com.scopix.qualitycontrol.model.events.GetStoreListForCorporateCommandResultEvent;
    import com.scopix.qualitycontrol.model.vo.AreaVO;
    import com.scopix.qualitycontrol.model.vo.StoreVO;

    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetStoreListForCorporateCommand extends BaseCommand {
        public function GetStoreListForCorporateCommand() {
            super(new GetStoreListForCorporateCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(sessionId:Number):void {
            webService = QualityControlWebServicesClient.getInstance().getWebService();

            //addWSListener(GetStoresResultEvent.GetStores_RESULT, resultWS, faultWS);
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as QualityControlWebServices).getStores(sessionId);
                    //(webService as CorporateWebServices).getStoreDTOs(sessionId);
            } else {
                //test
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
            var areaVO:AreaVO = null;
            for each (var dto:StoreDTO in data) {
                storeVO = new StoreVO();
                storeVO.fromDTO(dto)
                list.addStoreVO(storeVO);
            }

            return list;
        }
    }
}
