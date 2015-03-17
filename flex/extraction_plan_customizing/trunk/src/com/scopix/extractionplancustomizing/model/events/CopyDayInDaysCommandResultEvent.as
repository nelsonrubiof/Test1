package com.scopix.extractionplancustomizing.model.events
{
    import commons.events.GenericEvent;

    public class CopyDayInDaysCommandResultEvent extends GenericEvent
    {
        public static var COPY_DAY_IN_DAYS_EVENT:String = "copy_day_in_days_event";
        
        public function CopyDayInDaysCommandResultEvent()
        {
            super(COPY_DAY_IN_DAYS_EVENT);
        }
        
    }
}