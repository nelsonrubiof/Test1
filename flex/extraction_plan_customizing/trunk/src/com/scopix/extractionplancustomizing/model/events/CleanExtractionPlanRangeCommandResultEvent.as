package com.scopix.extractionplancustomizing.model.events
{
    import commons.events.GenericEvent;

    public class CleanExtractionPlanRangeCommandResultEvent extends GenericEvent
    {
        public static var CLEAN_EPR_EVENT:String = "clean_epr_event";
        
        public function CleanExtractionPlanRangeCommandResultEvent()
        {
            super(CLEAN_EPR_EVENT);
        }
    }
}