package com.scopix.reporting.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.GetUploadProcessResultEvent;
    import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDTO;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.model.events.GetUploadProcessCommandResultEvent;
    import com.scopix.reporting.model.vo.UploadProcessVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;

    public class GetUploadProcessCommand extends BaseCommand
    {
        public function GetUploadProcessCommand()
        {
            super(new GetUploadProcessCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(sessionId:Number):void {
            webService = ReportingWebServicesClient.getInstance().getWebService();
            
            addWSListener(GetUploadProcessResultEvent.GetUploadProcess_RESULT,
                            resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ReportingWebServices).getUploadProcess(sessionId);
            } else {
                //test
                var t:GetUploadProcessResultEvent = new GetUploadProcessResultEvent();
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:GetUploadProcessResultEvent):void {
            removeWSListener();
            
            //TODO verificar si el java devuelve NULL que llega AQUI
            var data:UploadProcessDTO = evt.result;
            
            var uploadProcessVO:UploadProcessVO = transformToVO(data);
            
            (event as GetUploadProcessCommandResultEvent).uploadProcess = uploadProcessVO;

            dispatchEvent(event);
            
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function transformToVO(data:UploadProcessDTO):UploadProcessVO {
            var vo:UploadProcessVO = null;
            
            if (data != null) {
                vo = new UploadProcessVO();
                vo.fromDTO(data);
            }
            
            return vo;
        }
    }
}