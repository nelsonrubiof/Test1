package com.scopix.qualitycontrol.model.events
{
    import com.scopix.qualitycontrol.model.arrays.ArrayOfObservedSituationFinishedVO;
    
    import commons.events.GenericEvent;
    
    public class GetObservedSituationFinishedComandResultEvent extends GenericEvent
    {
        public static var GET_OSF_LIST_EVENT:String = "get_osf_list_event";
        
        private var _osfList:ArrayOfObservedSituationFinishedVO;
        
        public function GetObservedSituationFinishedComandResultEvent(osfList:ArrayOfObservedSituationFinishedVO = null)
        {
            super(GET_OSF_LIST_EVENT);
            this._osfList = osfList;
        }
        
        public function get osfList():ArrayOfObservedSituationFinishedVO {
            return _osfList;
        }
        
        public function set osfList(list:ArrayOfObservedSituationFinishedVO):void {
            this._osfList = list;
        }
    }
}