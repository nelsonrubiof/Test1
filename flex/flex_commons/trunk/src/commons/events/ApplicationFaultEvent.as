package commons.events
{
    public class ApplicationFaultEvent extends GenericErrorEvent
    {
        public static var APPLICATION_FAULT_EVENT:String = "application_fault_event";
        
        public function ApplicationFaultEvent(msg:String = null)
        {
            super(APPLICATION_FAULT_EVENT, msg);
        }
    }
}