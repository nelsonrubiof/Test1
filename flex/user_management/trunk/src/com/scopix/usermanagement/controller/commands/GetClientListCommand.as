package com.scopix.usermanagement.controller.commands
{
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.security.services.webservices.ArrayOfCorporateDTO;
    import com.scopix.periscope.security.services.webservices.CorporateDTO;
    import com.scopix.periscope.security.services.webservices.GetCorporatesForUserMinimalInfoResultEvent;
    import com.scopix.periscope.security.services.webservices.GetCorporatesForUserResultEvent;
    import com.scopix.periscope.security.services.webservices.SecurityWebServices;
    import com.scopix.periscope.security.services.webservices.client.SecurityWebServicesClient;
    import com.scopix.test.SecurityDataTest;
    import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;
    import com.scopix.usermanagement.model.events.GetClientListCommandResultEvent;
    import com.scopix.usermanagement.model.vo.CorporateVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    
    public class GetClientListCommand extends BaseCommand
    {
    	private var _userName:String;
    	
    	public function GetClientListCommand() {
    		super(new GetClientListCommandResultEvent(), new WSFaultEvent());
    	}

        public function execute(userName:String, sessionId:Number):void {
        	//temporal solution
        	_userName = userName;
        	
            webService = SecurityWebServicesClient.getInstance().getWebService();
            
            addWSListener(GetCorporatesForUserMinimalInfoResultEvent.GetCorporatesForUserMinimalInfo_RESULT, resultWS, faultWS);
            
            if (!GlobalParameters.getInstance().test) {
                (webService as SecurityWebServices).getCorporatesForUserMinimalInfo(userName, sessionId);
            } else {
                //test
                var test:SecurityDataTest = SecurityDataTest.getInstance();
                var t:GetCorporatesForUserResultEvent = new GetCorporatesForUserResultEvent();
                t.result = test.getClientsData();
                webService.dispatchEvent(t);
            }
        }
        
        public function resultWS(evt:GetCorporatesForUserMinimalInfoResultEvent):void {
        	removeWSListener();
        	
            var data:ArrayOfCorporateDTO = evt.result;
            
            var corporateList:ArrayOfCorporateVO = transformToVO(data);
            
            (event as GetClientListCommandResultEvent).clientList = corporateList;
            
            dispatchEvent(event);
            
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
        
        private function transformToVO(data:ArrayOfCorporateDTO):ArrayOfCorporateVO {
            var corporateList:ArrayOfCorporateVO = new ArrayOfCorporateVO();
            
            if (data != null) {
                for each (var corporateDTO:CorporateDTO in data) {
                	var corporate:CorporateVO = new CorporateVO();

                	if (_userName == "admin") {
	                    corporate.fromDTO(corporateDTO);
	                    
	                    corporateList.addCorporateVO(corporate);
                    } else {
                    	if (corporateDTO.corporateId != 0) {
		                    corporate.fromDTO(corporateDTO);
		                    
		                    corporateList.addCorporateVO(corporate);
	                    }
                    }
                }
            }
            
            return corporateList;
        }
    }
}