package com.scopix.reporting.model
{
    import com.scopix.reporting.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.reporting.model.arrays.ArrayOfCorporateVO;
    import com.scopix.reporting.model.arrays.ArrayOfStoreVO;
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    
    import flash.utils.Timer;
    
    public class ReportingModel
    {
        private static var _instance:ReportingModel;

        public var statusProcessTimer:Timer;
        [Bindable]
        public var tnControlPanelSelectedIndex:Number;

        [Bindable]
        public var corporateList:ArrayOfCorporateVO;
        [Bindable]
        public var corporateIdSelected:Number;
        [Bindable]
        public var corporateDescriptionSelected:String;
        
        //variables de estado
        [Bindable]
        public var startDateTime:String;
        [Bindable]
        public var endDateTime:String;
        [Bindable]
        public var status:String;
        [Bindable]
        public var observations:String;
        [Bindable]
        public var globalTotal:Number;
        [Bindable]
        public var globalProcessed:Number;
        [Bindable]
        public var globalPercent:Number;
		[Bindable]
		public var user:String;
		[Bindable]
		public var userRunning:String;
        [Bindable]		
        public var onProcessList:ArrayOfUploadProcessDetailVO;
        [Bindable]
        public var cancelProcessButtonEnable:Boolean;
		[Bindable]
		public var autoRefreshButtonEnable:Boolean;		
		[Bindable]
		public var autoRefreshLabel:String;
        
		[Bindable]
		public var autoRefreshStatus:Boolean;
		
		
		//Manejo de Subida Automatica
		[Bindable]
		public var enableUploadingAutomaticButtonEnable:Boolean;		
		[Bindable]
		public var enableUploadingAutomaticLabel:String;		
		[Bindable]
		public var enableUploadingAutomaticStatus:Boolean;
        
        //variables de definicion del proceso
        [Bindable]
        public var storeList:ArrayOfStoreVO;
        [Bindable]
        public var areaTypeList:ArrayOfAreaTypeVO;
        [Bindable]
        public var toProcessList:ArrayOfUploadProcessDetailVO;
        [Bindable]
        public var deleteButtonEnable:Boolean;
        [Bindable]
        public var runNowButtonEnable:Boolean;
        [Bindable]
        public var endDate:Date;
        [Bindable]
        public var endDateStr:String;
        [Bindable]
        public var selectedIndexStores:Number;
        [Bindable]
        public var selectedIndexAreaType:Number;
        [Bindable]
        public var selectAllValue:Boolean;
        
        
        public static function getInstance():ReportingModel {
            if (_instance == null) {
                _instance = new ReportingModel();
            }
            
            return _instance;
        }
        
        public function ReportingModel() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
    }
}