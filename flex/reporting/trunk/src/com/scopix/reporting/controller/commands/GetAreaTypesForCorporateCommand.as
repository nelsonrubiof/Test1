package com.scopix.reporting.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.AreaTypeDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfAreaTypeDTO;
    import com.scopix.periscope.webservices.businessservices.GetAreasTypeResultEvent;
    import com.scopix.periscope.webservices.businessservices.ReportingWebServices;
    import com.scopix.periscope.webservices.businessservices.clients.ReportingWebServicesClient;
    import com.scopix.reporting.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.reporting.model.events.GetAreaTypesForCorporateCommandResultEvent;
    import com.scopix.reporting.model.vo.AreaTypeVO;
    import com.scopix.test.ReportingDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class GetAreaTypesForCorporateCommand extends BaseCommand
    {
        public function GetAreaTypesForCorporateCommand() {
            super(new GetAreaTypesForCorporateCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(sessionId:Number):void {
            webService = ReportingWebServicesClient.getInstance().getWebService();
            
            addWSListener(GetAreasTypeResultEvent.GetAreasType_RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as ReportingWebServices).getAreasType(sessionId);
            } else {
                //test
                var t:GetAreasTypeResultEvent = new GetAreasTypeResultEvent();
                var test:ReportingDataTest = ReportingDataTest.getInstance();
                t.result = test.getAreaTypeList();
                
                webService.dispatchEvent(t);
            }
        }
        
        private function resultWS(evt:GetAreasTypeResultEvent):void {
            removeWSListener();
            
            var data:ArrayOfAreaTypeDTO = evt.result;
            
            var areaTypeList:ArrayOfAreaTypeVO = transformToVO(data);
            
            (event as GetAreaTypesForCorporateCommandResultEvent).areaTypeList = areaTypeList;
            
            dispatchEvent(event);
            
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function transformToVO(data:ArrayOfAreaTypeDTO):ArrayOfAreaTypeVO {
            var list:ArrayOfAreaTypeVO = new ArrayOfAreaTypeVO();
            var areaTypeVO:AreaTypeVO = null;
            
            for each (var dto:AreaTypeDTO in data) {
                areaTypeVO = new AreaTypeVO();
                areaTypeVO.fromDTO(dto);
                
                list.addAreaTypeVO(areaTypeVO);
            }
            
            return list;
        }
    }
}