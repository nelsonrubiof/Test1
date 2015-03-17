package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.NewExtractionPlanCustomizingCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class NewExtractionPlanCustomizingCommand extends BaseCommand {
        private var type:String;

        public function NewExtractionPlanCustomizingCommand() {
            super(new NewExtractionPlanCustomizingCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(storeId:Number, situationTemplateId:Number, sessionId:Number, type:String):void {
            this.type = type;
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).createExtractionPlanCustomizing(situationTemplateId, storeId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ExtractionPlanCustomizingDTO = evt.result as ExtractionPlanCustomizingDTO;
            var epcVO:ExtractionPlanCustomizingVO = transformToVO(data);
            (event as NewExtractionPlanCustomizingCommandResultEvent).epc = epcVO;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ExtractionPlanCustomizingDTO):ExtractionPlanCustomizingVO {
            var epc:ExtractionPlanCustomizingVO = new ExtractionPlanCustomizingVO();
            epc.fromDTO(data, type);
            return epc;
        }
    }
}
