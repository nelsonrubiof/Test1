package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
    
    import commons.events.GenericEvent;

    public class ValidateSendFullExtractionPlanCustomizingCommandResultEvent extends GenericEvent
    {
        public static var VALIDATE_SEND_FULL_EPC_EVENT:String = "validate_send_full_epc_event";
        
        private var _list:ArrayOfEPCVO;
        
        public function ValidateSendFullExtractionPlanCustomizingCommandResultEvent(list:ArrayOfEPCVO = null)
        {
            super(VALIDATE_SEND_FULL_EPC_EVENT);
            this._list = list;
        }
        
        public function get list():ArrayOfEPCVO {
            return _list;
        }
        
        public function set list(list:ArrayOfEPCVO):void {
            this._list = list;
        }
    }
}