package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.events.GetMetricTemplateListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.MetricTemplateDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetMetricTemplateListCommand extends BaseCommand
    {
        public function GetMetricTemplateListCommand() {
            super(new GetMetricTemplateListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(epcId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getMetricTemplates(epcId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            var mtList:ArrayOfMetricTemplateVO = transformToVO(data);
            (event as GetMetricTemplateListCommandResultEvent).mtList = mtList;
            dispatchEvent(event);
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfMetricTemplateVO {
            var list:ArrayOfMetricTemplateVO = new ArrayOfMetricTemplateVO();
            var mt:MetricTemplateVO = null;
            
            for each(var dto:MetricTemplateDTO in data) {
                mt = new MetricTemplateVO();
                mt.fromDTO(dto);
                list.addMetricTemplateVO(mt);
            }
            
            return list;
        }
    }
}