package commons.events
{
    import flash.events.Event;

    public class GenericEvent extends Event
    {
        private var _eventName:String;
        
        public function GenericEvent(eventName:String)
        {
            super(eventName);
            this._eventName = eventName;
        }
        
        public function get eventName():String {
            return this._eventName;
        }
    }
}