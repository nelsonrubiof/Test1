package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSituationTemplateVO;
    
    import flash.events.Event;
    import commons.events.GenericEvent;
    
    public class GetSituationTemplateListCommandResultEvent extends GenericEvent
    {
        public static var GET_SITUATION_TEMPLATE_LIST_EVENT:String = "get_situation_template_list_event";
        
        private var _stList:ArrayOfSituationTemplateVO;
        
        public function GetSituationTemplateListCommandResultEvent(stList:ArrayOfSituationTemplateVO = null)
        {
            super(GET_SITUATION_TEMPLATE_LIST_EVENT);
            this._stList = stList;
        }
        
        public function get stList():ArrayOfSituationTemplateVO {
            return _stList;
        }
        
        public function set stList(list:ArrayOfSituationTemplateVO):void {
            this._stList = list;
        }
    }
}