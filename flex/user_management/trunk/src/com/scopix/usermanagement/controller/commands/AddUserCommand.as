package com.scopix.usermanagement.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.AddUserResultEvent;
    import com.scopix.periscope.security.services.webservices.PeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.usermanagement.model.events.AddUserCommandResultEvent;
    import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class AddUserCommand extends BaseCommand
    {
    	public function AddUserCommand() {
    		super(new AddUserCommandResultEvent(), new WSFaultEvent());
    	}
    	
        public function execute(user:PeriscopeUserVO, sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();
            
            addWSListener(AddUserResultEvent.AddUser_RESULT, resultWS, faultWS);
            
            var periscopeUserDTO:PeriscopeUserDTO = user.toDTO();
            
            if (!GlobalParameters.getInstance().test) {
                (webService as SecurityWebServices).addUser(periscopeUserDTO, sessionId);
            } else {
                //test
                var t:AddUserResultEvent = new AddUserResultEvent();
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:AddUserResultEvent):void {
            removeWSListener();
            
            dispatchEvent(event);
            
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
    }
}