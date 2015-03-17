package com.scopix.qualitycontrol.model.events
{
	import commons.events.GenericEvent;
	
	public class AcceptSituationCommandResultEvent extends GenericEvent {
		
		public static var ACCEPT_SITUATION_EVENT:String = "accept_situation_event";
		public function AcceptSituationCommandResultEvent(){
			super(ACCEPT_SITUATION_EVENT);
		}
	}
}