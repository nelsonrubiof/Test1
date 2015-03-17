package com.scopix.extractionplancustomizing.model.events {

	import commons.events.GenericEvent;

	public class DisableEPCListCommandResultEvent extends GenericEvent {
        public static var DISABLE_EPC_LIST:String = "disable_epc_list";
        
		public function DisableEPCListCommandResultEvent() {
			super(DISABLE_EPC_LIST);
		}
	}
}