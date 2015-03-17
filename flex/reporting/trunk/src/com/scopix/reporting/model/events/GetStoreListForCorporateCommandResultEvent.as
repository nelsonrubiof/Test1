package com.scopix.reporting.model.events
{
    
    import com.scopix.reporting.model.arrays.ArrayOfStoreVO;
    
    import commons.events.GenericEvent;

    public class GetStoreListForCorporateCommandResultEvent extends GenericEvent
    {
        public static var GET_STORE_LIST_EVENT:String = "get_store_list_event";
        
        private var _storeList:ArrayOfStoreVO;
        
        public function GetStoreListForCorporateCommandResultEvent(storeList:ArrayOfStoreVO = null)
        {
            super(GET_STORE_LIST_EVENT);
            this._storeList = storeList;
        }
        
        public function get storeList():ArrayOfStoreVO {
            return _storeList;
        }
        
        public function set storeList(list:ArrayOfStoreVO):void {
            this._storeList = list;
        }
    }
}