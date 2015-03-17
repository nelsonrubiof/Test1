package com.scopix.security.model.events
{
    import commons.events.GenericEvent;
    
    public class LoginCommandResultEvent extends GenericEvent
    {
        public static var LOGIN_RESULT_EVENT:String = "login_result_event";
        
        private var _sessionId:Number;

        public function LoginCommandResultEvent(sessionId:Number = -1)
        {
            super(LOGIN_RESULT_EVENT);
            _sessionId = sessionId;
        }

        public function get sessionId():Number {
            return _sessionId;
        }
        
        public function set sessionId(id:Number):void {
            this._sessionId = id;
        }
    }
}