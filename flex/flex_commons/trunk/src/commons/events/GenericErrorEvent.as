package commons.events
{
    import flash.events.Event;

    public class GenericErrorEvent extends Event
    {
        private var _eventName:String;
        private var _message:String;
        
        public function GenericErrorEvent(eventName:String, msg:String = null)
        {
            super(eventName);
            this._eventName = eventName;
            this._message = msg;
        }
        
        public function get eventName():String {
            return this._eventName;
        }
        
        public function get message():String {
            return _message;
        }
        
        public function set message(msg:String):void {
            this._message = msg;
        }
    }
}