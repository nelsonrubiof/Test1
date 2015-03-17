package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
    
    import commons.events.GenericEvent;
    
    public class GetExtractionPlanCustomizingListCommandResultEvent extends GenericEvent
    {
        public static var GET_EPC_LIST_EVENT:String = "get_epc_list_event";
        
        private var _epcList:ArrayOfEPCVO;
        
        public function GetExtractionPlanCustomizingListCommandResultEvent(epcList:ArrayOfEPCVO = null)
        {
            super(GET_EPC_LIST_EVENT);
            this._epcList = epcList;
        }
        
        public function get epcList():ArrayOfEPCVO {
            return _epcList;
        }
        
        public function set epcList(list:ArrayOfEPCVO):void {
            this._epcList = list;
        }
    }
}