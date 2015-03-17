package com.scopix.usermanagement.controller.commands {
    import com.scopix.periscope.security.services.webservices.AreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfAreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.GetAreaTypesForUserResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.usermanagement.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.usermanagement.model.events.GetAreaTypesCommandResultEvent;
    import com.scopix.usermanagement.model.vo.AreaTypeVO;
    
    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;

    public class GetAreaTypesForUserCommand extends BaseCommand {
        public function GetAreaTypesForUserCommand() {
            super(new GetAreaTypesCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(userName:String, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();
            addWSListener(GetAreaTypesForUserResultEvent.GetAreaTypesForUser_RESULT, resultWS, faultWS);

            (webService as SecurityWebServices).getAreaTypesForUser(userName, sessionId);
        }

        private function resultWS(evt:GetAreaTypesForUserResultEvent):void {
            removeWSListener();
            var data:ArrayOfAreaTypeDTO = evt.result;
            var areaTypes:ArrayOfAreaTypeVO = transformToVO(data);
            (event as GetAreaTypesCommandResultEvent).areaTypes = areaTypes;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        public function transformToVO(data:ArrayOfAreaTypeDTO):ArrayOfAreaTypeVO {
            var areaTypes:ArrayOfAreaTypeVO = new ArrayOfAreaTypeVO();

            if (data != null) {
                for each (var dto:AreaTypeDTO in data) {
                    var areaType:AreaTypeVO = new AreaTypeVO();
                    areaType.fromDTO(dto);
                    areaTypes.addAreaTypeVO(areaType);
                }
            }

            return areaTypes;
        }
    }
}
