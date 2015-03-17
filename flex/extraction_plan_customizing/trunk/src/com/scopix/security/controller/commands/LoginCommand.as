package com.scopix.security.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.security.SecurityWebServices;
    import com.scopix.periscope.webservices.security.SecurityWebServicesClient;
    import com.scopix.security.model.events.LoginCommandResultEvent;
    
    import commons.commands.BaseCommand;
    import commons.events.WSSecurityFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    public class LoginCommand extends BaseCommand
    {
        public function LoginCommand() {
            super(new LoginCommandResultEvent(), new WSSecurityFaultEvent());
        }
        
        public function execute(user:String, encryptedPassword:String):void {
            webService = SecurityWebServicesClient.getInstance().getWebService(); 
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as SecurityWebServices).login(user, encryptedPassword, 0);
            }
        }
        
        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            
            //recepcion respuesta WS
            var sessionId:Number = new Number(evt.result);
            
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