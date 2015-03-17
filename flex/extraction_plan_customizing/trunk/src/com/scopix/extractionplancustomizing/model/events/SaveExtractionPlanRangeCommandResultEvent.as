package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    
    import commons.events.GenericEvent;

    public class SaveExtractionPlanRangeCommandResultEvent extends GenericEvent
    {
        public static var SAVE_EPR_EVENT:String = "save_epr_event";
        private var _epr:ExtractionPlanRangeVO;
        
        public function SaveExtractionPlanRangeCommandResultEvent(epr:ExtractionPlanRangeVO = null)
        {
            super(SAVE_EPR_EVENT);
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