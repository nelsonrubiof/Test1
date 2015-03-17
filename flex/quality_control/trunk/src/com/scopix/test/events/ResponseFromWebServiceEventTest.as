package com.scopix.test.events
{
    import flash.events.Event;
    
    public class ResponseFromWebServiceEventTest extends Event
    {
        public static var Operation_RESULT:String = "Operation_result";
        private var _data:String;
        private var _id:Number;
        
        public function ResponseFromWebServiceEventTest(data:String = null, id:Number = 1)
        {
            super(Operation_RESULT);
            _data = data;
            _id = id;
        }

        public function get data():String {
            return _data;
        }
        
        public function get id():Number {
            return _id;
        }
    }
}