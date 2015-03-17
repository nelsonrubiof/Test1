package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDetailRequestVO;
    import com.scopix.extractionplancustomizing.model.events.GetEPCDetailListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.DetailRequestVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.DetalleSolicitudDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetEPCDetailListCommand extends BaseCommand
    {
        public function GetEPCDetailListCommand()
        {
            super(new GetEPCDetailListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(epcId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getDetalleSolicitudes(epcId, sessionId);
            }
        }
        
        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            var detailList:ArrayOfDetailRequestVO = transformToVO(data);
            (event as GetEPCDetailListCommandResultEvent).list = detailList;
            dispatchEvent(event);
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function transformToVO(dtoList:ArrayCollection):ArrayOfDetailRequestVO {
            var list:ArrayOfDetailRequestVO = new ArrayOfDetailRequestVO();
            var vo:DetailRequestVO = null;
            
            for each (var dto:DetalleSolicitudDTO in dtoList) {
                vo = new DetailRequestVO();
                vo.fromDTO(dto);
                
                list.addDetailRequestVO(vo);
            }
            
            return list;
        }
    }
}