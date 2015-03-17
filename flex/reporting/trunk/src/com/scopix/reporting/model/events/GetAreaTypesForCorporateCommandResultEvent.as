package com.scopix.reporting.model.events
{
    import com.scopix.reporting.model.arrays.ArrayOfAreaTypeVO;
    
    import commons.events.GenericEvent;

    public class GetAreaTypesForCorporateCommandResultEvent extends GenericEvent
    {
        public static var GET_AREA_TYPE_LIST_EVENT:String = "get_area_type_list_event";
        
        private var _areaTypeList:ArrayOfAreaTypeVO;
        
        public function GetAreaTypesForCorporateCommandResultEvent(val:ArrayOfAreaTypeVO = null)
        {
            super(GET_AREA_TYPE_LIST_EVENT);
            this._areaTypeList = val;
        }
        
        public function get areaTypeList():ArrayOfAreaTypeVO {
            return _areaTypeList;
        }
        
        public function set areaTypeList(val:ArrayOfAreaTypeVO):void {
            _areaTypeList = val;
        }
    }
}