package com.scopix.usermanagement.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.ArrayOfInt;
    import com.scopix.periscope.security.services.webservices.DeleteUserListResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO;
    import com.scopix.usermanagement.model.events.DeleteUsersCommandResultEvent;
    import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class DeleteUsersCommand extends BaseCommand
    {
    	public function DeleteUsersCommand() {
    		super(new DeleteUsersCommandResultEvent(), new WSFaultEvent());
    	}

        public function execute(list:ArrayOfPeriscopeUserVO, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();
            
            addWSListener(DeleteUserListResultEvent.DeleteUserList_RESULT, resultWS, faultWS);
            
            
            if (!GlobalParameters.getInstance().test) {
                var ids:ArrayOfInt = getIds(list);
                
                (webService as SecurityWebServices).deleteUserList(ids, sessionId);
            } else {
                //test
                var t:DeleteUserListResultEvent = new DeleteUserListResultEvent();
                
                webService.dispatchEvent(t);
            }
        }
        
        public function resultWS(evt:DeleteUserListResultEvent):void {
            removeWSListener();
            
            dispatchEvent(event);
            
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function getIds(list:ArrayOfPeriscopeUserVO):ArrayOfInt {
            var ids:ArrayOfInt = new ArrayOfInt();
            
            for each (var periscopeUser:PeriscopeUserVO in list) {
                ids.addNumber(periscopeUser.userId);
            }
            
            return ids;
        }
    }
}