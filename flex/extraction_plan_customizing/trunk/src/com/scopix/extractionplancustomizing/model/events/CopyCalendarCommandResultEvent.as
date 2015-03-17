package com.scopix.extractionplancustomizing.model.events
{
    import commons.events.GenericEvent;

    public class CopyCalendarCommandResultEvent extends GenericEvent
    {
        public static var COPY_CALENDAR_EVENT:String = "copy_calendar_event";
        
        public function CopyCalendarCommandResultEvent()
        {
            super(COPY_CALENDAR_EVENT);
        }
    }
}