package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    
    import commons.events.GenericEvent;
    
    public class GetEvidenceProviderListCommandResultEvent extends GenericEvent
    {
        public static var GET_EVIDENCE_PROVIDER_LIST_EVENT:String = "get_evidence_provider_list_event";
        
        private var _epList:ArrayOfEvidenceProviderVO;
        
        public function GetEvidenceProviderListCommandResultEvent(epList:ArrayOfEvidenceProviderVO = null) {
            super(GET_EVIDENCE_PROVIDER_LIST_EVENT);
            this._epList = epList;
        }
        
        public function get epList():ArrayOfEvidenceProviderVO {
            return _epList;
        }
        
        public function set epList(list:ArrayOfEvidenceProviderVO):void {
            this._epList = list;
        }
    }
}