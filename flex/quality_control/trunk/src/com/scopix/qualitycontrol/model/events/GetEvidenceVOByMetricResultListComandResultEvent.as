package com.scopix.qualitycontrol.model.events
{
	import com.scopix.qualitycontrol.model.arrays.ArrayOfEvidenceVO;
	
	import commons.events.GenericEvent;
	
	public class GetEvidenceVOByMetricResultListComandResultEvent extends GenericEvent {
		
		public static var GET_EVIDENCE_LIST_EVENT:String = "get_evidence_vo_list_event";
		
		private var _evidenceVOList:ArrayOfEvidenceVO;

		public function GetEvidenceVOByMetricResultListComandResultEvent(evidenceVOList:ArrayOfEvidenceVO= null){
			super(GET_EVIDENCE_LIST_EVENT);
			this._evidenceVOList = evidenceVOList;
		}

		public function get evidenceVOList():ArrayOfEvidenceVO{
			return _evidenceVOList;
		}

		public function set evidenceVOList(value:ArrayOfEvidenceVO):void{
			_evidenceVOList = value;
		}

	}
}