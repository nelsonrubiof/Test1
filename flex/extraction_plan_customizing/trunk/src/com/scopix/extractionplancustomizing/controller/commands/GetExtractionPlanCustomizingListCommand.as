package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.enum.EPCStatesEnum;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
    import com.scopix.extractionplancustomizing.model.events.GetExtractionPlanCustomizingListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
    import com.scopix.test.EPCDataTest;
    import com.scopix.util.StateUtil;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    public class GetExtractionPlanCustomizingListCommand extends BaseCommand
    {
        private var type:String;

        public function GetExtractionPlanCustomizingListCommand() {
            super(new GetExtractionPlanCustomizingListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(storeId:Number, status:EPCStatesEnum, sessionId:Number, type:String):void {
            this.type = type;
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                //status se traspasa a un estado comun que se entienda en el backend
                var state:String = StateUtil.getInstance().getStateToSendWS(status);
                
                //parametros: storeId, estado, sessionId
                (webService as ExtractionPlanManagerWebServices).getExtractionPlanCustomizings(storeId, state, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
			if (data != null) {
				data.removeItemAt(data.length-1);
			}
            var epcList:ArrayOfEPCVO = transformToVO(data);
            (event as GetExtractionPlanCustomizingListCommandResultEvent).epcList = epcList;
            dispatchEvent(event);
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfEPCVO {
            var list:ArrayOfEPCVO = new ArrayOfEPCVO();
            var epcVO:ExtractionPlanCustomizingVO = null;
            
            for each (var dto:ExtractionPlanCustomizingDTO in data) {
                epcVO = new ExtractionPlanCustomizingVO();
                epcVO.fromDTO(dto, type);
                list.addEPCVO(epcVO);
            }

            return  list;
        }
    }
}