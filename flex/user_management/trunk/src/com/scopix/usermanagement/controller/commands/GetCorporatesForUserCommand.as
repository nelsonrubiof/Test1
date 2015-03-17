package com.scopix.usermanagement.controller.commands {
    import com.scopix.periscope.security.services.webservices.ArrayOfCorporateDTO;
    import com.scopix.periscope.security.services.webservices.CorporateDTO;
    import com.scopix.periscope.security.services.webservices.GetCorporatesForUserMinimalInfoResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;
    import com.scopix.usermanagement.model.events.GetCorporatesCommandResultEvent;
    import com.scopix.usermanagement.model.vo.CorporateVO;

    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;

    import mx.rpc.events.FaultEvent;

    public class GetCorporatesForUserCommand extends BaseCommand {
        public function GetCorporatesForUserCommand() {
            super(new GetCorporatesCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(userName:String, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();
            addWSListener(GetCorporatesForUserMinimalInfoResultEvent.GetCorporatesForUserMinimalInfo_RESULT, resultWS, faultWS);

            (webService as SecurityWebServices).getCorporatesForUserMinimalInfo(userName, sessionId);
        }

        private function resultWS(evt:GetCorporatesForUserMinimalInfoResultEvent):void {
            removeWSListener();
            var data:ArrayOfCorporateDTO = evt.result;
            var corporates:ArrayOfCorporateVO = transformToVO(data);
            (event as GetCorporatesCommandResultEvent).corporates = corporates;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        public function transformToVO(data:ArrayOfCorporateDTO):ArrayOfCorporateVO {
            var stores:ArrayOfCorporateVO = new ArrayOfCorporateVO();

            if (data != null) {
                for each (var dto:CorporateDTO in data) {
                    var corporate:CorporateVO = new CorporateVO();
                    corporate.fromDTO(dto);
                    stores.addCorporateVO(corporate);
                }
            }

            return stores;
        }
    }
}
