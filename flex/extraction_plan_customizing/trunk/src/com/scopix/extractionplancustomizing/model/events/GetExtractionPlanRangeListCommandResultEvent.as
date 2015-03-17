package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeVO;
    
    import commons.events.GenericEvent;

    public class GetExtractionPlanRangeListCommandResultEvent extends GenericEvent
    {
        public static var GET_EP_RANGES_EVENT:String = "get_ep_ranges_event";
        
        private var _epRangeList:ArrayOfExtractionPlanRangeVO;
        
        public function GetExtractionPlanRangeListCommandResultEvent(epRangeList:ArrayOfExtractionPlanRangeVO = null)
        {
            super(GET_EP_RANGES_EVENT);
            this._epRangeList = epRangeList;
        }
        
        public function get epRangeList():ArrayOfExtractionPlanRangeVO {
            return _epRangeList;
        }
        
        public function set epRangeList(epRangeList:ArrayOfExtractionPlanRangeVO):void {
            this._epRangeList = epRangeList;
        }
    }
}