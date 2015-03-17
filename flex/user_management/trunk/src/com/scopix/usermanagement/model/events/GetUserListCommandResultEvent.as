package com.scopix.usermanagement.model.events
{
    import com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO;
    
    import commons.events.GenericEvent;
    
    public class GetUserListCommandResultEvent extends GenericEvent
    {
        public static var GET_USERS_LIST_EVENT:String = "get_users_list_event";
        private var _periscopeUserList:ArrayOfPeriscopeUserVO;
        
        public function GetUserListCommandResultEvent(periscopeUserList:ArrayOfPeriscopeUserVO = null)
        {
            super(GET_USERS_LIST_EVENT);
            _periscopeUserList = periscopeUserList;
        }
        
        public function get periscopeUserList():ArrayOfPeriscopeUserVO {
            return _periscopeUserList;
        }
        
        public function set periscopeUserList(list:ArrayOfPeriscopeUserVO):void {
        	_periscopeUserList = list;
        }
    }
}