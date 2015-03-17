package com.scopix.qualitycontrol.model.events
{
	import com.scopix.qualitycontrol.model.arrays.ArrayOfEvidenceVO;
	import com.scopix.qualitycontrol.model.arrays.ArrayOfMetricResultVO;
	
	import commons.events.GenericEvent;
	
	public class GetMetricResultByOSListComandResultEvent extends GenericEvent {

		public static var GET_METRIC_RESULT_LIST_EVENT:String = "get_observed_metric_result_list_event";
		
		private var _metricResultList:ArrayOfMetricResultVO;
		
		public function GetMetricResultByOSListComandResultEvent(metricResultList:ArrayOfMetricResultVO= null){
			super(GET_METRIC_RESULT_LIST_EVENT);
			this._metricResultList = metricResultList;
		}
		
		public function get metricResultList():ArrayOfMetricResultVO {
			return _metricResultList;
		}
		
		public function set metricResultList(cList:ArrayOfMetricResultVO):void {
			this._metricResultList = cList;
		}
	}
}