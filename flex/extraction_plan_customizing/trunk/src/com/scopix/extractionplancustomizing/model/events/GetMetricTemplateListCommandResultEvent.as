package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    
    import commons.events.GenericEvent;

    public class GetMetricTemplateListCommandResultEvent extends GenericEvent
    {
        public static var GET_METRIC_TEMPLATE_LIST_EVENT:String = "get_metric_template_list_event";
        
        private var _mtList:ArrayOfMetricTemplateVO;
        
        public function GetMetricTemplateListCommandResultEvent(mtList:ArrayOfMetricTemplateVO = null)
        {
            super(GET_METRIC_TEMPLATE_LIST_EVENT);
            this._mtList = mtList;
        }
        
        public function get mtList():ArrayOfMetricTemplateVO {
            return _mtList;
        }
        
        public function set mtList(list:ArrayOfMetricTemplateVO):void {
            this._mtList = list;
        }
    }
}