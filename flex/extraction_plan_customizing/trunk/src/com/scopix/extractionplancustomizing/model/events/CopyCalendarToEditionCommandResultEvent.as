package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    
    import commons.events.GenericEvent;

    public class CopyCalendarToEditionCommandResultEvent extends GenericEvent
    {
        public static var COPY_CALENDAR_TO_EDITION_EVENT:String = "copy_calendar_to_edition_event";
        
        private var _epc:ExtractionPlanCustomizingVO;
        
        public function CopyCalendarToEditionCommandResultEvent(epc:ExtractionPlanCustomizingVO = null)
        {
            super(COPY_CALENDAR_TO_EDITION_EVENT);
            this._epc = epc;
        }
        
        public function get epc():ExtractionPlanCustomizingVO {
            return this._epc;
        }
        
        public function set epc(epc:ExtractionPlanCustomizingVO):void {
            this._epc = epc;
        }
    }
}