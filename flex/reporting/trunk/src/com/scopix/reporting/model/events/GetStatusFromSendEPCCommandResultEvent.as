package com.scopix.reporting.model.events
{
	import com.scopix.reporting.model.vo.StatusFromSendEPCVO;
	
	import commons.events.GenericEvent;

	public class GetStatusFromSendEPCCommandResultEvent extends GenericEvent
	{
        public static var GET_STATUS_FROM_SEND_EPC_EVENT:String = "get_status_from_send_epc_event";
        
        private var _statusFromSendEPCVO:StatusFromSendEPCVO;

		public function GetStatusFromSendEPCCommandResultEvent(vo:StatusFromSendEPCVO = null)
		{
			super(GET_STATUS_FROM_SEND_EPC_EVENT);
			this._statusFromSendEPCVO = vo;
		}
		
		public function get statusFromSendEPCVO():StatusFromSendEPCVO {
			return _statusFromSendEPCVO;
		}
		
		public function set statusFromSendEPCVO(vo:StatusFromSendEPCVO):void {
			this._statusFromSendEPCVO = vo;
		}
	}
}