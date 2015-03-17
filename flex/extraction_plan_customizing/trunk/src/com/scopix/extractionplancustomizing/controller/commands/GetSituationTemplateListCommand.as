package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSituationTemplateVO;
    import com.scopix.extractionplancustomizing.model.events.GetSituationTemplateListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.SituationTemplateVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.SituationTemplateDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    public class GetSituationTemplateListCommand extends BaseCommand
    {
        public function GetSituationTemplateListCommand() {
            super(new GetSituationTemplateListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getSituationTemplates(sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            var stList:ArrayOfSituationTemplateVO = transformToVO(data);
            (event as GetSituationTemplateListCommandResultEvent).stList = stList;
            dispatchEvent(event);
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfSituationTemplateVO {
            var list:ArrayOfSituationTemplateVO = new ArrayOfSituationTemplateVO();
            var st:SituationTemplateVO = null;
            
            for each (var dto:SituationTemplateDTO in data) {
                st = new SituationTemplateVO();
                st.fromDTO(dto)
                
                list.addSituationTemplateVO(st);
            }
            
            return list;
        }
    }
}