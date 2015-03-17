package com.scopix.extractionplancustomizing.model
{
    import com.scopix.enum.EPCStatesEnum;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfCorporateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDetailRequestVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEPCVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeDetailVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfExtractionPlanRangeVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSituationTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfStoreVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfString;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    import com.scopix.extractionplancustomizing.view.Day;
    import com.scopix.extractionplancustomizing.view.Hour;
    import com.scopix.extractionplancustomizing.view.RangeDay;
    
    import flash.utils.Timer;
    
    import mx.effects.Glow;

    public class ExtractionPlanCustomizingModel
    {
        public static var MODEL:String = "MODEL";
        private static var _instance:ExtractionPlanCustomizingModel;
        
        //get status send epc
        public var getStatusSendEPCTimer:Timer;
        
        //send epc
        public var sendType:String;
        
        
        //epcList variables
        [Bindable]
        public var filterEffect:Glow;
        [Bindable]
        public var runFilterEffect:Boolean;
        [Bindable]
        public var filterValue:EPCStatesEnum;  
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
        [Bindable]
        public var disableList:ArrayOfExtractionPlanCustomizingVO;
        [Bindable]
        public var deleteList:ArrayOfExtractionPlanCustomizingVO;
        [Bindale]
        public var epcListToSend:ArrayOfExtractionPlanCustomizingVO;
        
        //situation template select
        [Bindable]
        public var situationTemplateIdSelected:Number;
        
        //common (and/or) new, edit, last sent
        [Bindable]
        public var subtitle:String;
        [Bindable]
        public var epc:ExtractionPlanCustomizingVO;
         [Bindable]
        public var selectedGlobalTabNavIndex:Number;
        [Bindable]
        public var selectedTabNav:Number;
        [Bindable]
        public var selectedTabNavLastSent:Number;
        [Bindable]
        public var verifyLastSent:Boolean;
        [Bindable]
        public var verifyEdition:Boolean;
 
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
        [Bindable]
        public var saveEffect:Glow;
        //[Bindable]
        //public var tnEditVisibleValue:Boolean;
        //[Bindable]
        //public var tnLastSentVisibleValue:Boolean;
        [Bindable]
        public var metricTemplateSelected:MetricTemplateVO;
        [Bindable]
        public var evidenceProviderListEnabled:Boolean;
        
        
        //storeSituationTemplate form
        //situation templates list
        [Bindable]
        public var stList:ArrayOfSituationTemplateVO;
        [Bindable]
        public var stSelectedIndex:Number;
		
		[Bindable]
		public var priorization:Number;
		[Bindable]
		public var randomCamera:Boolean;
		[Bindable]
		public var enableRandomcamera:Boolean;

        
        
        //Calendar variables
        /** 
        * Esta variable esta hecha para evitar cargar datos del calendario cuando aun el 
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
        
        [Bindable]
        public var eprToSave:ExtractionPlanRangeVO;
        /**
        * Variable que indica si es necesario recargar los rangos.
        **/
        [Bindable]
        public var rangeCreatedModified:Boolean;
        
        //range detail
        [Bindable]
        public var eprdList:ArrayOfExtractionPlanRangeDetailVO;
        
        //day of range to delete
        [Bindable]
        public var dayOfRangeToDelete:Day;
        [Bindable]
        public var rangeToDelete:RangeDay;
        [Bindable]
        public var rangeIdToEdit:Number;
        
        //epc detail List
        [Bindable]
        public var epcDetailList:ArrayOfDetailRequestVO;
        
        public static function getInstance():ExtractionPlanCustomizingModel {
            if (_instance == null) {
                _instance = new ExtractionPlanCustomizingModel();
            }
            
            return _instance;
        }
        
        public function ExtractionPlanCustomizingModel() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
    }
}