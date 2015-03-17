package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeDetailVO;
    
    import commons.events.GenericEvent;

    public class GetExtractionPlanRangeDetailListCommandResultEvent extends GenericEvent
    {
        public static var GET_EPR_DETAIL_LIST_EVENT:String = "get_epr_detail_list_event";
        private var _eprd:ArrayOfExtractionPlanRangeDetailVO;
        
        public function GetExtractionPlanRangeDetailListCommandResultEvent(eprd:ArrayOfExtractionPlanRangeDetailVO = null)
        {
            super(GET_EPR_DETAIL_LIST_EVENT);
            this._eprd = eprd;
        }
        
        public function get eprd():ArrayOfExtractionPlanRangeDetailVO {
            return _eprd;
        }
        
        public function set eprd(eprd:ArrayOfExtractionPlanRangeDetailVO):void {
            this._eprd = eprd;
        }
    }
}