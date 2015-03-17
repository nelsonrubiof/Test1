package com.scopix.reporting.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ArrayOfInt;
    import com.scopix.periscope.webservices.businessservices.ArrayOfUploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.DeleteUploadProcessDetailResultEvent;
    import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    import com.scopix.reporting.model.events.GetUploadProcessDetailListCommandResultEvent;
    import com.scopix.reporting.model.vo.UploadProcessDetailVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class DeleteUploadProcessDetailListCommand extends BaseCommand
    {
        public function DeleteUploadProcessDetailListCommand() {
            super(new GetUploadProcessDetailListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(list:ArrayOfUploadProcessDetailVO, sessionId:Number):void {
            webService = ReportingWebServicesClient.getInstance().getWebService();
            
            addWSListener(DeleteUploadProcessDetailResultEvent.DeleteUploadProcessDetail_RESULT,
                            resultWS, faultWS);
            
            var listToDelete:ArrayOfInt = getUploadProcessDetailIdList(list);

            if (!GlobalParameters.getInstance().test) {
                (webService as ReportingWebServices).deleteUploadProcessDetail(listToDelete, sessionId);
            } else {
                //test
                var t:DeleteUploadProcessDetailResultEvent = new DeleteUploadProcessDetailResultEvent();
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:DeleteUploadProcessDetailResultEvent):void {
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
        
        private function getUploadProcessDetailIdList(paramList:ArrayOfUploadProcessDetailVO):ArrayOfInt {
            var list:ArrayOfInt = new ArrayOfInt();
            
            for each (var vo:UploadProcessDetailVO in paramList) {
                if (vo.selected) {
                    list.addNumber(vo.id);
                }
            }
            
            return list;
        }
    }
}