package com.scopix.extractionplancustomizing.model.events
{
    import commons.events.GenericEvent;

    public class SaveGeneralDataCommandResultEvent extends GenericEvent
    {
        public static var SAVE_GENERAL_DATA_EVENT:String = "save_general_data_event";
        
        public function SaveGeneralDataCommandResultEvent()
        {
            super(SAVE_GENERAL_DATA_EVENT);
        }
    }
}