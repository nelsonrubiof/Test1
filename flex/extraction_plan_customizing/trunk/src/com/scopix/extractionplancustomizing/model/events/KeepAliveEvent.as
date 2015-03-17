package com.scopix.extractionplancustomizing.model.events
{
	import commons.events.GenericEvent;

	public class KeepAliveEvent extends GenericEvent
	{
		public static var KEEP_ALIVE_EVENT:String = "keep_alive_event";

	    private var _privilegeId:String;

		public function KeepAliveEvent()
		{
	      super(KEEP_ALIVE_EVENT);
	
	      _privilegeId = privilegeId;

		}

	    public function get privilegeId():String
	    {
	      return _privilegeId;
	    }

	}
}