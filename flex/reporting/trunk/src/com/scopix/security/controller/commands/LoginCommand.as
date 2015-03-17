package com.scopix.security.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.LoginResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.security.model.events.LoginCommandResultEvent;
    
    import commons.commands.BaseCommand;
    import commons.events.WSSecurityFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class LoginCommand extends BaseCommand
    {
        public function LoginCommand() {
            super(new LoginCommandResultEvent(), new WSSecurityFaultEvent());
        }
        
        public function execute(user:String, encryptedPassword:String):void {
            webService = SecurityWebServicesClient.getInstance().getWebService(); 
            
            addWSListener(LoginResultEvent.Login_RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as SecurityWebServices).login(user, encryptedPassword, 0);
            } else {
                //test
                var t:LoginResultEvent = new LoginResultEvent();
                t.result = 321654;
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:LoginResultEvent):void {
            removeWSListener();
            
            //recepcion respuesta WS
            var sessionId:Number = evt.result;
            
            (event as LoginCommandResultEvent).sessionId = sessionId;
            
            dispatchEvent(event);
            
            removeCommandListener();
        }

        private function faultWS(event:FaultEvent):void {
            fault(event);
            removeWSListener();
            removeCommandListener();
        }
    }
}