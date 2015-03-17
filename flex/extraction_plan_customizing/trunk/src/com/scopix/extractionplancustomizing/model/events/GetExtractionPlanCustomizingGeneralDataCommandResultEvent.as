package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    
    import commons.events.GenericEvent;

    public class GetExtractionPlanCustomizingGeneralDataCommandResultEvent extends GenericEvent
    {
        public static var GET_EPC_EVENT:String = "get_epc_event";
        
        private var _epc:ExtractionPlanCustomizingVO;
        
        public function GetExtractionPlanCustomizingGeneralDataCommandResultEvent(epc:ExtractionPlanCustomizingVO = null)
        {
            super(GET_EPC_EVENT);
            this._epc = epc;
        }
        
        public function get epc():ExtractionPlanCustomizingVO {
            return _epc;
        }
        
        public function set epc(epc:ExtractionPlanCustomizingVO):void {
            this._epc = epc;
        }
    }
}