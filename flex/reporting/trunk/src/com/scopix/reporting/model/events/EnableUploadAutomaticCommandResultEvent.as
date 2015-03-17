package com.scopix.reporting.model.events
{
	import commons.events.GenericEvent;
	
	public class EnableUploadAutomaticCommandResultEvent extends GenericEvent
	{
		public static var ENABLE_UPLOAD_AUTOMATIC_EVENT:String = "enable_upload_automatic_event";
		
		public function EnableUploadAutomaticCommandResultEvent(eventName:String = "") {
			super(ENABLE_UPLOAD_AUTOMATIC_EVENT);
		}
	}
}