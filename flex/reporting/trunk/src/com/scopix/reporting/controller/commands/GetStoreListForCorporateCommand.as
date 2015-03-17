package com.scopix.reporting.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ArrayOfStoreDTO;
    import com.scopix.periscope.webservices.businessservices.GetStoresResultEvent;
    import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
    import com.scopix.periscope.webservices.businessservices.StoreDTO;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.model.arrays.ArrayOfStoreVO;
    import com.scopix.reporting.model.events.GetStoreListForCorporateCommandResultEvent;
    import com.scopix.reporting.model.vo.StoreVO;
    import com.scopix.test.ReportingDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class GetStoreListForCorporateCommand extends BaseCommand
    {
        public function GetStoreListForCorporateCommand() {
            super(new GetStoreListForCorporateCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(sessionId:Number):void {
            //webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            webService = ReportingWebServicesClient.getInstance().getWebService();
            
            addWSListener(GetStoresResultEvent.GetStores_RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ReportingWebServices).getStores(sessionId);
            } else {
                //test
                var t:GetStoresResultEvent = new GetStoresResultEvent();
                var test:ReportingDataTest = ReportingDataTest.getInstance();
                t.result = test.getStoreList();

                webService.dispatchEvent(t);
            }
        }

        private function resultWS(evt:GetStoresResultEvent):void {
            removeWSListener();
            
            var data:ArrayOfStoreDTO = evt.result;
            
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

        private function transformToVO(data:ArrayOfStoreDTO):ArrayOfStoreVO {
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