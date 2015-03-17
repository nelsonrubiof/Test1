package com.scopix.reporting.model.events
{
	import commons.events.GenericEvent;
	
	public class GetStateUploadingAutomaticCommandResultEvent extends GenericEvent
	{
		public static var GET_STATE_UPLOAD_AUTOMATIC_EVENT:String = "get_state_upload_automatic_event";
		private var _state:Boolean;
		public function GetStateUploadingAutomaticCommandResultEvent(val:Boolean = false) {
			super(GET_STATE_UPLOAD_AUTOMATIC_EVENT);
			this._state = val;
		}

		public function get state():Boolean {
			return _state;
		}

		public function set state(value:Boolean):void {
			_state = value;
		}

	}
}