package com.scopix.reporting.controller.commands
{
    import com.scopix.periscope.security.services.webservices.CheckSecurityResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.reporting.model.events.KeepAliveEvent;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

  public class KeepAliveCommand extends BaseCommand
  {
    private var currentUsr:String = "";
    private var currentPass:String = "";

    public function KeepAliveCommand() {
        super(new KeepAliveEvent(), new WSFaultEvent());
    }
    
    public function execute(sessionId:Number) : void {
        webService = SecurityWebServicesClient.getInstance().getWebService();
        
        addWSListener(CheckSecurityResultEvent.CheckSecurity_RESULT, resultWS, faultWS);
        
        (webService as SecurityWebServices).checkSecurity(sessionId, "GET_LIST_CORPORATE_PERMISSION");
        
    }

    private function resultWS(evt:CheckSecurityResultEvent) : void {
        removeWSListener();
    }

    private function faultWS( event : Object ) : void {
        removeWSListener();
    }
  }
}