package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfString;
    import com.scopix.extractionplancustomizing.model.events.GetCopyCalendarMessagesCommandResultEvent;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetCopyCalendarMessagesCommand extends BaseCommand
    {
        public function GetCopyCalendarMessagesCommand()
        {
            super(new GetCopyCalendarMessagesCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(stId:Number, storeId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).copyCalendarActions(stId, storeId, sessionId);
            }
        }
        
        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var messageList:ArrayCollection = evt.result as ArrayCollection;
			if (messageList != null) {
				messageList.removeItemAt(messageList.length - 1);
			}
            (event as GetCopyCalendarMessagesCommandResultEvent).messageList = new ArrayOfString(messageList.toArray());
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