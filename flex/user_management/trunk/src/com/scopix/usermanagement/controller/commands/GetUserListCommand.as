package com.scopix.usermanagement.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.ArrayOfPeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.GetUserListMinimalInfoResultEvent;
    import com.scopix.periscope.security.services.webservices.GetUserListResultEvent;
    import com.scopix.periscope.security.services.webservices.PeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.test.SecurityDataTest;
    import com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO;
    import com.scopix.usermanagement.model.events.GetUserListCommandResultEvent;
    import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import flash.events.EventDispatcher;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.soap.SOAPFault;
    
    public class GetUserListCommand extends BaseCommand
    {
    	public function GetUserListCommand() {
    		super(new GetUserListCommandResultEvent(), new WSFaultEvent());
    	}

        public function execute(corporateId:Number, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();
            
            addWSListener(GetUserListMinimalInfoResultEvent.GetUserListMinimalInfo_RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as SecurityWebServices).getUserListMinimalInfo(corporateId, sessionId);
            } else {
                //solo test
                var t:GetUserListResultEvent = new GetUserListResultEvent();
                t.result = SecurityDataTest.getInstance().getUserList();
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:GetUserListMinimalInfoResultEvent):void {
            removeWSListener();
            
            var data:ArrayOfPeriscopeUserDTO = evt.result;
            
            var periscopeUserList:ArrayOfPeriscopeUserVO = transformToVO(data);
            
            (event as GetUserListCommandResultEvent).periscopeUserList = periscopeUserList;
            
            dispatchEvent(event);
            
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayOfPeriscopeUserDTO):ArrayOfPeriscopeUserVO {
            var periscopeUserList:ArrayOfPeriscopeUserVO = new ArrayOfPeriscopeUserVO();
            
            if (data != null) {
                for each (var periscopeUserDTO:PeriscopeUserDTO in data) {
                    var periscopeUser:PeriscopeUserVO = new PeriscopeUserVO();
                    
                    periscopeUser.fromDTO(periscopeUserDTO);
                    
                    periscopeUserList.addUserVO(periscopeUser);
                }
            }
            
            return periscopeUserList;
        }
    }
}