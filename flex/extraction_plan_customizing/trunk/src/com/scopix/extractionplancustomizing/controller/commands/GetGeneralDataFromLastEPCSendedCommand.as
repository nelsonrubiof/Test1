package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanCustomizingGeneralDataCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetGeneralDataFromLastEPCSendedCommand extends BaseCommand {
        private var type:String;

        public function GetGeneralDataFromLastEPCSendedCommand() {
            super(new GetExtractionPlanCustomizingGeneralDataCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(stId:Number, storeId:Number, sessionId:Number, type:String):void {
            this.type = type;
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getUltimoExtractionPlanCustomizingEnviado(stId, storeId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ExtractionPlanCustomizingDTO = evt.result as ExtractionPlanCustomizingDTO;
            var epc:ExtractionPlanCustomizingVO = transformToVO(data);
            (event as GetExtractionPlanCustomizingGeneralDataCommandResultEvent).epc = epc;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(dto:ExtractionPlanCustomizingDTO):ExtractionPlanCustomizingVO {
            var epc:ExtractionPlanCustomizingVO = new ExtractionPlanCustomizingVO();
            epc.fromDTO(dto, type);
            return epc;
        }
    }
}
