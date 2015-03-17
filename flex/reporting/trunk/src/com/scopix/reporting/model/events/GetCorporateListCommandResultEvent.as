package com.scopix.reporting.model.events
{
    import com.scopix.reporting.model.arrays.ArrayOfCorporateVO;
    
    import commons.events.GenericEvent;
    
    public class GetCorporateListCommandResultEvent extends GenericEvent
    {
        public static var GET_CORPORATE_LIST_EVENT:String = "get_corporate_list_event";
        
        private var _corporateList:ArrayOfCorporateVO;
        
        public function GetCorporateListCommandResultEvent(corporateList:ArrayOfCorporateVO = null)
        {
            super(GET_CORPORATE_LIST_EVENT);
            this._corporateList = corporateList;
        }
        
        public function get corporateList():ArrayOfCorporateVO {
            return _corporateList;
        }
        
        public function set corporateList(cList:ArrayOfCorporateVO):void {
            this._corporateList = cList;
        }
    }
}