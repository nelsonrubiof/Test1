package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDetailRequestVO;
    
    import commons.events.GenericEvent;

    public class GetEPCDetailListCommandResultEvent extends GenericEvent
    {
        public static var GET_EPC_DETAIL_LIST_EVENT:String = "get_epc_detail_list_event";
        
        private var _list:ArrayOfDetailRequestVO;
        
        public function GetEPCDetailListCommandResultEvent(list:ArrayOfDetailRequestVO = null)
        {
            super(GET_EPC_DETAIL_LIST_EVENT);
            this._list = list;
        }
        
        public function get list():ArrayOfDetailRequestVO {
            return _list;
        }
        
        public function set list(dl:ArrayOfDetailRequestVO):void {
            this._list = dl;
        }
    }
}