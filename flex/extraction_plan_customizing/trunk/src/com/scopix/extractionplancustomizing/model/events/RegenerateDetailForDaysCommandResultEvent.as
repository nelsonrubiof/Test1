package com.scopix.extractionplancustomizing.model.events
{
    import commons.events.GenericEvent;

    public class RegenerateDetailForDaysCommandResultEvent extends GenericEvent
    {
        public static var REGENERATE_DETAIL_FOR_DAYS_EVENT:String = "regenerate_detail_for_days_event";
        
        public function RegenerateDetailForDaysCommandResultEvent()
        {
            super(REGENERATE_DETAIL_FOR_DAYS_EVENT);
        }
    }
}