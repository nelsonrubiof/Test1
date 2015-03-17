package com.scopix.usermanagement.model.events
{
    import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;
    
    import commons.events.GenericEvent;
    
    public class GetClientListCommandResultEvent extends GenericEvent
    {
        public static var GET_CLIENT_LIST_EVENT:String = "get_client_list_event";
        private var _clientList:ArrayOfCorporateVO;
        
        public function GetClientListCommandResultEvent(corporateList:ArrayOfCorporateVO = null)
        {
            super(GET_CLIENT_LIST_EVENT);
            _clientList = corporateList;
        }
        
        public function get clientList():ArrayOfCorporateVO {
            return _clientList;
        }
        
        public function set clientList(list:ArrayOfCorporateVO):void {
        	_clientList = list;
        }
    }
}