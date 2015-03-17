package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfInt;
    import com.scopix.extractionplancustomizing.model.events.SendExtractionPlanCustomizingCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.vo.StatusFromSendEPCVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.CorporateWebServices;
    import com.scopix.periscope.webservices.businessservices.CorporateWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.StatusSendEPCDTO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class SendExtractionPlanCustomizingCommand extends BaseCommand {
        public function SendExtractionPlanCustomizingCommand() {
            super(new SendExtractionPlanCustomizingCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcListToSend:ArrayOfExtractionPlanCustomizingVO, storeId:Number, sessionId:Number):void {
            webService = CorporateWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            var epcListIds:ArrayOfInt = new ArrayOfInt();
            for each (var epcTemp:ExtractionPlanCustomizingVO in epcListToSend) {
                epcListIds.addNumber(epcTemp.id);
            }

            if (!GlobalParameters.getInstance().test) {
				var lista:ArrayCollection = new ArrayCollection(epcListIds.toArray());
				var storeIdTemp:int = storeId;
                (webService as CorporateWebServices).sendExtractionPlanCustomizings(lista, storeIdTemp, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var dto:StatusSendEPCDTO = evt.result as StatusSendEPCDTO;
            var vo:StatusFromSendEPCVO = transformToVO(dto);
            (event as SendExtractionPlanCustomizingCommandResultEvent).statusFromSendEPCVO = vo;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(dto:StatusSendEPCDTO):StatusFromSendEPCVO {
            var vo:StatusFromSendEPCVO = new StatusFromSendEPCVO();
            vo.fromDTO(dto);
            return vo;
        }
    }
}
