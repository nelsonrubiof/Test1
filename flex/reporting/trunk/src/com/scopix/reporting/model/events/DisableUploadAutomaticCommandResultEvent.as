package com.scopix.reporting.model.events
{
	import commons.events.GenericEvent;
	
	public class DisableUploadAutomaticCommandResultEvent extends GenericEvent	{
		public static var DISABLE_UPLOAD_AUTOMATIC_EVENT:String = "disable_upload_automatic_event";
		
		public function DisableUploadAutomaticCommandResultEvent(eventName:String = "") {
			super(DISABLE_UPLOAD_AUTOMATIC_EVENT);
		}
	}
}