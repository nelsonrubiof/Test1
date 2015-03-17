package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    
    import commons.events.GenericEvent;

    public class GetExtractionPlanRangeCommandResultEvent extends GenericEvent
    {
        public static var GET_EPR_EVENT:String = "get_epr_event";
        private var _epr:ExtractionPlanRangeVO;
        
        public function GetExtractionPlanRangeCommandResultEvent(epr:ExtractionPlanRangeVO = null)
        {
            super(GET_EPR_EVENT);
            this._epr = epr;
        }
        
        public function get epr():ExtractionPlanRangeVO {
            return _epr;
        }
        
        public function set epr(epr:ExtractionPlanRangeVO):void {
            this._epr = epr;
        }
    }
}