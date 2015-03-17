package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.periscope.webservices.security.SecurityWebServices;
    import com.scopix.periscope.webservices.security.SecurityWebServicesClient;
    import com.scopix.qualitycontrol.model.events.KeepAliveEvent;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.ResultEvent;

    public class KeepAliveCommand extends BaseCommand {
        private var currentUsr:String = "";
        private var currentPass:String = "";

        public function KeepAliveCommand() {
            super(new KeepAliveEvent(), new WSFaultEvent());
        }

        public function execute(sessionId:Number):void {
            webService = SecurityWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            (webService as SecurityWebServices).checkSecurity(sessionId, "GET_LIST_CORPORATE_PERMISSION");

        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
        }

        private function faultWS(event:Object):void {
            removeWSListener();
        }
    }
}
