package com.scopix.extractionplancustomizing.model {

	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfCorporateVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDetailRequestVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeDetailVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSituationTemplateVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfStoreVO;
	import com.scopix.extractionplancustomizing.model.arrays.ArrayOfString;
	import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
	import com.scopix.extractionplancustomizing.view.lastSent.Day;
	import com.scopix.extractionplancustomizing.view.lastSent.Hour;
	
	import mx.effects.Glow;

	public class ExtractionPlanCustomizingLastSentModel {
		public static var LAST_SENT_MODEL:String = "LAST_SENT_MODEL";
		private static var _instance:ExtractionPlanCustomizingLastSentModel;


		//epcList variables        
		[Bindable]
		public var corporateIdSelected:Number;
		[Bindable]
		public var corporateDescriptionSelected:String;
		[Bindable]
		public var storeIdSelected:Number;
		[Bindable]
		public var storeDescriptionSelected:String;
		[Bindable]
		public var corporateList:ArrayOfCorporateVO;
		[Bindable]
		public var storesFilter:ArrayOfStoreVO;
		[Bindable]
		public var statusFilter:ArrayOfString;
		[Bindable]
		public var epcList:ArrayOfEPCVO;

		//situation template select
		[Bindable]
		public var situationTemplateIdSelected:Number;

		//common to new and edit
		[Bindable]
		public var epc:ExtractionPlanCustomizingVO;

		//epc management
		[Bindable]
		public var enableMulticamera:Boolean;
		[Bindable]
		public var multiCamera:Boolean;
		[Bindable]
		public var editMode:Boolean;
		[Bindable]
		public var onEdition:Boolean;
		[Bindable]
		public var evidenceProviderList:ArrayOfEvidenceProviderVO;
		[Bindable]
		public var sensorList:ArrayOfSensorVO;
        [Bindable]
		public var metricTemplateList:ArrayOfMetricTemplateVO;
		[Bindable]
		public var isModified:Boolean;

		//storeSituationTemplate form
		//situation templates list
		[Bindable]
		public var stList:ArrayOfSituationTemplateVO;
		[Bindable]
		public var priorization:Number;
		
		[Bindable]
		public var randomCamera:Boolean;
		[Bindable]
		public var enableRandomcamera:Boolean;



		//Calendar variables
		/** esta variable esta hecha para evitar cargar datos del calendario cuando aun el
		* componente visual no esta construido
		***/
		[Bindable]
		public var readyToLoadCalendar:Boolean;
		[Bindable]
		public var verticalScrollPositionCalendar:Number
		[Bindable]
		public var dayOfWeekSelected:Day;
		[Bindable]
		public var hourOfDaySelected:Hour;
		[Bindable]
		public var loadFirstTime:Boolean = true;
		[Bindable]
		public var calendarRangesList:ArrayOfExtractionPlanRangeVO;
		[Bindable]
		public var copyCalendarToEditionEffect:Glow;
		[Bindable]
		public var copyFullEPCToEditionEffect:Glow;

		//copy calendar options
		[Bindable]
		public var storeIdToCopy:Number;
		[Bindable]
		public var situationTemplateIdToCopy:Number;

		//days variables
		[Bindable]
		public var sunday:Day;
		[Bindable]
		public var monday:Day;
		[Bindable]
		public var tuesday:Day;
		[Bindable]
		public var wednesday:Day;
		[Bindable]
		public var thursday:Day;
		[Bindable]
		public var friday:Day;
		[Bindable]
		public var saturday:Day;

		//range
		[Bindable]
		public var enableAutomaticSeleccion:Boolean;
		[Bindable]
		public var initialTimeRange:Date;
		[Bindable]
		public var endTimeRange:Date;
		[Bindable]
		public var samplesRange:Number;
		[Bindable]
		public var frequencyRange:Number;
		[Bindable]
		public var durationRange:Number;
		[Bindable]
		public var eprTypeRange:String = "FIXED";

		//range detail
		[Bindable]
		public var eprdList:ArrayOfExtractionPlanRangeDetailVO;

		[Bindable]
		public var rangeIdToEdit:Number;

		//epc detail List
		[Bindable]
		public var epcDetailList:ArrayOfDetailRequestVO;

		public static function getInstance():ExtractionPlanCustomizingLastSentModel {
			if (_instance == null) {
				_instance = new ExtractionPlanCustomizingLastSentModel();
			}
			return _instance;
		}
        
		public function ExtractionPlanCustomizingLastSentModel() {
			if (_instance != null) {
				throw new Error("This class can only be accessed through getInstance method");
			}
		}
	}
}