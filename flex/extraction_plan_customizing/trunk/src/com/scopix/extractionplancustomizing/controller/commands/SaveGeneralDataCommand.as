package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.SaveGeneralDataCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class SaveGeneralDataCommand extends BaseCommand {
        public function SaveGeneralDataCommand() {
            super(new SaveGeneralDataCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epc:ExtractionPlanCustomizingVO, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            //generando objeto DTO
            var dto:ExtractionPlanCustomizingDTO = transformToDTO(epc);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).saveEPCGeneral(dto, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToDTO(epc:ExtractionPlanCustomizingVO):ExtractionPlanCustomizingDTO {
            var dto:ExtractionPlanCustomizingDTO = epc.toDTO();

            return dto;
        }
    }
}
