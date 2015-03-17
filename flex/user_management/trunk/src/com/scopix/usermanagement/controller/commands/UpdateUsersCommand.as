package com.scopix.usermanagement.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.ArrayOfPeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.DeleteUserListResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.UpdateUserListResultEvent;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO;
    import com.scopix.usermanagement.model.events.UpdateUsersCommandResultEvent;
    import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class UpdateUsersCommand extends BaseCommand
    {
    	public function UpdateUsersCommand() {
    		super(new UpdateUsersCommandResultEvent(), new WSFaultEvent());
    	}

        public function execute(users:ArrayOfPeriscopeUserVO, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();
            
            addWSListener(UpdateUserListResultEvent.UpdateUserList_RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
            	var periscopeUserListDTO:ArrayOfPeriscopeUserDTO = transformToDTO(users);
            	
            	(webService as SecurityWebServices).updateUserList(periscopeUserListDTO, sessionId);
            } else {
                //test
                var t:DeleteUserListResultEvent = new DeleteUserListResultEvent();
                
                webService.dispatchEvent(t);
            }
        }

        private function resultWS(evt:UpdateUserListResultEvent):void {
            removeWSListener();
            
            dispatchEvent(event);
            
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function transformToDTO(list:ArrayOfPeriscopeUserVO):ArrayOfPeriscopeUserDTO {
            var periscopeUserListDTO:ArrayOfPeriscopeUserDTO = new ArrayOfPeriscopeUserDTO();
            
            for each (var periscopeUser:PeriscopeUserVO in list) {
                periscopeUserListDTO.addPeriscopeUserDTO(periscopeUser.toDTO());
            }
            
            return periscopeUserListDTO;
        }
    }
}