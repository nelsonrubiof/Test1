package com.scopix.qualitycontrol.model.events
{
	import com.scopix.qualitycontrol.model.arrays.ArrayOfMotivoRejectedVO;
	
	import commons.events.GenericEvent;
	
	public class GetMotivoRejectdCommandResultEvent extends GenericEvent
	{
		public static var GET_MOTIVO_REJECTED_EVENT:String = "get_motivo_rejected_list_event";
		
		private var _motivoRejectedVOList:ArrayOfMotivoRejectedVO;
		public function GetMotivoRejectdCommandResultEvent(motivoRejectedVOList:ArrayOfMotivoRejectedVO= null) {
			super(GET_MOTIVO_REJECTED_EVENT);
			this._motivoRejectedVOList = motivoRejectedVOList;
		}

		public function get motivoRejectedVOList():ArrayOfMotivoRejectedVO
		{
			return _motivoRejectedVOList;
		}

		public function set motivoRejectedVOList(value:ArrayOfMotivoRejectedVO):void
		{
			_motivoRejectedVOList = value;
		}

	}
}