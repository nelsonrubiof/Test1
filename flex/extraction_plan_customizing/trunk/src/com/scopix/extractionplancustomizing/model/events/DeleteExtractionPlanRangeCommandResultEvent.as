package com.scopix.extractionplancustomizing.model.events
{
    import commons.events.GenericEvent;

    public class DeleteExtractionPlanRangeCommandResultEvent extends GenericEvent
    {
        public static var DELETE_EPC_RANGE_EVENT:String = "delete_epc_range_event";
        
        public function DeleteExtractionPlanRangeCommandResultEvent()
        {
            super(DELETE_EPC_RANGE_EVENT);
        }
        
    }
}