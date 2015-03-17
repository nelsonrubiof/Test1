package com.scopix.extractionplancustomizing.model.events {

    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfString;
    
    import commons.events.GenericEvent;

    public class GetCopyCalendarMessagesCommandResultEvent extends GenericEvent {
        public static var GET_COPY_CALENDAR_MESSAGES:String = "get_copy_calendar_messages_event";
        private var _messageList:ArrayOfString;

        public function GetCopyCalendarMessagesCommandResultEvent(list:ArrayOfString = null) {
            super(GET_COPY_CALENDAR_MESSAGES);
            _messageList = list;
        }

        public function get messageList():ArrayOfString {
            return _messageList;
        }

        public function set messageList(list:ArrayOfString):void {
            this._messageList = list;
        }
    }
}
