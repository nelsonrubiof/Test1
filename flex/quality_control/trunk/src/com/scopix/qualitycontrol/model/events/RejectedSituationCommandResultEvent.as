package com.scopix.qualitycontrol.model.events
{
	import commons.events.GenericEvent;
	
	public class RejectedSituationCommandResultEvent extends GenericEvent
	{
		public static var REJECTED_SITUATION_EVENT:String = "rejected_situation_event";
		public function RejectedSituationCommandResultEvent() {
			super(REJECTED_SITUATION_EVENT);
		}
	}
}