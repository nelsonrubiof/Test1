package com.scopix.usermanagement.controller.commands {
    import com.scopix.periscope.security.services.webservices.ArrayOfStoreDTO;
    import com.scopix.periscope.security.services.webservices.GetStoresForCorporateResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.StoreDTO;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.usermanagement.model.arrays.ArrayOfStoreVO;
    import com.scopix.usermanagement.model.events.GetStoresCommandResultEvent;
    import com.scopix.usermanagement.model.vo.StoreVO;
    
    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;

    public class GetStoresForCorporateCommand extends BaseCommand {
        public function GetStoresForCorporateCommand() {
            super(new GetStoresCommandResultEvent(), new WSFaultEvent());
        }
		
		public function execute(corporateId:Number, sessionId:Number):void {
			webService = SecurityWebServicesClient.getInstance().getWebService();
			addWSListener(GetStoresForCorporateResultEvent.GetStoresForCorporate_RESULT, resultWS, faultWS);
			
			(webService as SecurityWebServices).getStoresForCorporate(corporateId, sessionId);
		}
		
		private function resultWS(evt:GetStoresForCorporateResultEvent):void {
			removeWSListener();
			var data:ArrayOfStoreDTO = evt.result;
			var stores:ArrayOfStoreVO = transformToVO(data);
			(event as GetStoresCommandResultEvent).stores = stores;
			dispatchEvent(event);
			removeCommandListener();
		}
		
		private function faultWS(evt:FaultEvent):void {
			fault(evt);
			removeWSListener();
			removeCommandListener();
		}
		
		public function transformToVO(data:ArrayOfStoreDTO):ArrayOfStoreVO {
			var stores:ArrayOfStoreVO = new ArrayOfStoreVO();
			
			if (data != null) {
				for each (var dto:StoreDTO in data) {
					var store:StoreVO = new StoreVO();
					store.fromDTO(dto);
					stores.addStoreVO(store);
				}
			}
			
			return stores;
		}
    }
}
