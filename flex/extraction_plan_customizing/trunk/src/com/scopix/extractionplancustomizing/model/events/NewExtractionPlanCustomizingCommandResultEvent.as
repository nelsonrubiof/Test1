package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    
    import commons.events.GenericEvent;
    
    public class NewExtractionPlanCustomizingCommandResultEvent extends GenericEvent
    {
        public static var NEW_EPC_EVENT:String = "new_epc_event";
        private var _epc:ExtractionPlanCustomizingVO;
        
        public function NewExtractionPlanCustomizingCommandResultEvent()
        {
            super(NEW_EPC_EVENT);
        }
        
        public function get epc():ExtractionPlanCustomizingVO {
            return _epc;
        }
        
        public function set epc(epc:ExtractionPlanCustomizingVO):void {
            this._epc = epc;
        }
    }
}