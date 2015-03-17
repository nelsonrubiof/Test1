package com.scopix.usermanagement.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.ArrayOfRolesGroupDTO;
    import com.scopix.periscope.security.services.webservices.GetRolesGroupListForUserResultEvent;
    import com.scopix.periscope.security.services.webservices.RolesGroupDTO;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.test.SecurityDataTest;
    import com.scopix.usermanagement.model.arrays.ArrayOfRolesGroupVO;
    import com.scopix.usermanagement.model.events.GetRolesGroupListCommandResultEvent;
    import com.scopix.usermanagement.model.vo.RolesGroupVO;

    import flash.events.EventDispatcher;

    import mx.rpc.events.FaultEvent;
    import commons.events.WSFaultEvent;
    import commons.commands.BaseCommand;

    public class GetRolesGroupForUserCommand extends BaseCommand {
        public function GetRolesGroupForUserCommand() {
            super(new GetRolesGroupListCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(userName:String, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();

            addWSListener(GetRolesGroupListForUserResultEvent.GetRolesGroupListForUser_RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as SecurityWebServices).getRolesGroupListForUser(userName, sessionId);
            } else {
                //test
                var t:GetRolesGroupListForUserResultEvent = new GetRolesGroupListForUserResultEvent();
                t.result = SecurityDataTest.getInstance().getRolesGroupList2();
                webService.dispatchEvent(t);
            }
        }

        private function resultWS(evt:GetRolesGroupListForUserResultEvent):void {
            removeWSListener();

            var data:ArrayOfRolesGroupDTO = evt.result;

            var rolesGroup:ArrayOfRolesGroupVO = transformToVO(data);

            (event as GetRolesGroupListCommandResultEvent).rolesGroupList = rolesGroup;

            dispatchEvent(event);

            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        public function transformToVO(data:ArrayOfRolesGroupDTO):ArrayOfRolesGroupVO {
            var rolesGroup:ArrayOfRolesGroupVO = new ArrayOfRolesGroupVO();

            if (data != null) {
                for each (var dto:RolesGroupDTO in data) {
                    var roleGroup:RolesGroupVO = new RolesGroupVO();

                    roleGroup.fromDTO(dto);

                    rolesGroup.addRolesGroupVO(roleGroup);
                }
            }

            return rolesGroup;
        }
    }
}
