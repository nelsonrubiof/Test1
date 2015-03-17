package com.scopix.reporting.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.AddUploadProcessDetailResultEvent;
    import com.scopix.periscope.webservices.businessservices.ArrayOfInt;
    import com.scopix.periscope.webservices.businessservices.ArrayOfUploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailAddDTO;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.reporting.model.arrays.ArrayOfStoreVO;
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    import com.scopix.reporting.model.events.AddUploadProcessDetailCommandResultEvent;
    import com.scopix.reporting.model.vo.AreaTypeVO;
    import com.scopix.reporting.model.vo.StoreVO;
    import com.scopix.reporting.model.vo.UploadProcessDetailVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.formatters.DateFormatter;
    import mx.rpc.events.FaultEvent;

    public class AddUploadProcessDetailCommand extends BaseCommand
    {
        public function AddUploadProcessDetailCommand() {
            super(new AddUploadProcessDetailCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(stList:ArrayOfStoreVO, atList:ArrayOfAreaTypeVO, edDate:Date, sessionId:Number):void {
            webService = ReportingWebServicesClient.getInstance().getWebService();
            
            addWSListener(AddUploadProcessDetailResultEvent.AddUploadProcessDetail_RESULT,
                            resultWS, faultWS);
            
            var storeIdList:ArrayOfInt = getStoreIdList(stList);
            var areaTypeIdList:ArrayOfInt = getAreaTypeIdList(atList);

            //enviando la fecha en el formato YYYY-MM-DD
            var dfDate:DateFormatter = new DateFormatter();
            dfDate.formatString = GlobalParameters.DATE_FORMAT;
            var endDate:String = dfDate.format(edDate);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as ReportingWebServices).addUploadProcessDetail(storeIdList, areaTypeIdList, endDate, sessionId);
            } else {
                //test
                var t:AddUploadProcessDetailResultEvent = new AddUploadProcessDetailResultEvent();
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:AddUploadProcessDetailResultEvent):void {
            removeWSListener();
            
            var data:UploadProcessDetailAddDTO = evt.result;
            
            var uploadProcessDetailListAggregate:ArrayOfUploadProcessDetailVO = transformToVO(data.aggregate);
            var uploadProcessDetailListUnknow:ArrayOfUploadProcessDetailVO = transformToVO(data.unknown);
            
            (event as AddUploadProcessDetailCommandResultEvent).uploadProcessDetailListAggregate = uploadProcessDetailListAggregate;
            (event as AddUploadProcessDetailCommandResultEvent).uploadProcessDetailListUnknow = uploadProcessDetailListUnknow;
            
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

        private function getStoreIdList(paramList:ArrayOfStoreVO):ArrayOfInt {
            var list:ArrayOfInt = new ArrayOfInt();
            
            for each (var vo:StoreVO in paramList) {
                list.addNumber(vo.id);
            }
            
            return list;
        }

        private function getAreaTypeIdList(paramList:ArrayOfAreaTypeVO):ArrayOfInt {
            var list:ArrayOfInt = new ArrayOfInt();
            
            for each (var vo:AreaTypeVO in paramList) {
                list.addNumber(vo.id);
            }
            
            return list;
        }
    }
}