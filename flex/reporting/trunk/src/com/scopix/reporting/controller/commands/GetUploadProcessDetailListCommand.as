package com.scopix.reporting.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ArrayOfUploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.GetUploadProcessDetailResultEvent;
    import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    import com.scopix.reporting.model.events.GetUploadProcessDetailListCommandResultEvent;
    import com.scopix.reporting.model.vo.UploadProcessDetailVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;

    public class GetUploadProcessDetailListCommand extends BaseCommand
    {
        public function GetUploadProcessDetailListCommand() {
            super(new GetUploadProcessDetailListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(sessionId:Number):void {
            webService = ReportingWebServicesClient.getInstance().getWebService();
            
            addWSListener(GetUploadProcessDetailResultEvent.GetUploadProcessDetail_RESULT,
                            resultWS, faultWS);
            if (!GlobalParameters.getInstance().test) {
                (webService as ReportingWebServices).getUploadProcessDetail(sessionId);
            } else {
                //test
                var t:GetUploadProcessDetailResultEvent = new GetUploadProcessDetailResultEvent();
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:GetUploadProcessDetailResultEvent):void {
            removeWSListener();
            
            var data:ArrayOfUploadProcessDetailDTO = evt.result;
            
            var uploadProcessDetailList:ArrayOfUploadProcessDetailVO = transformToVO(data);
            
            (event as GetUploadProcessDetailListCommandResultEvent).uploadProcessDetailList = uploadProcessDetailList;
            
            dispatchEvent(event);
            
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function transformToVO(data:ArrayOfUploadProcessDetailDTO):ArrayOfUploadProcessDetailVO {
            var list:ArrayOfUploadProcessDetailVO = new ArrayOfUploadProcessDetailVO();
            var uploadProcessDetailVO:UploadProcessDetailVO = null;
            
            for each (var dto:UploadProcessDetailDTO in data) {
                uploadProcessDetailVO = new UploadProcessDetailVO();
                
                uploadProcessDetailVO.fromDTO(dto);
                
                list.addUploadProcessDetailVO(uploadProcessDetailVO);
            }
            
            return list;
        }
    }
}