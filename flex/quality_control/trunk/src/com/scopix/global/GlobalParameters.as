package com.scopix.global
{
    import com.scopix.qualitycontrol.model.arrays.ArrayOfCorporateParameter;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfDayOfWeekVO;
    import com.scopix.qualitycontrol.model.vo.CorporateParameter;
    import com.scopix.qualitycontrol.model.vo.DayOfWeekVO;
    
    import mx.resources.ResourceManager;
    
    [Bindable]
    public class GlobalParameters
    {
		public static const DATE_FORMAT:String = "YYYY-MM-DD";
        private var _userName:String;
        private var _sessionId:Number;
        private var _test:Boolean = false;
        private static var _instance:GlobalParameters;
        private var _version:String;
        private static var _resources:String = "resources";
        private var _corporateListParameter:ArrayOfCorporateParameter;
        private var _daysOfWeek:ArrayOfDayOfWeekVO;
        private var _getStatusSendEPCInterval:Number;

        public function GlobalParameters() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }

        public static function getInstance():GlobalParameters {
            if (_instance == null) {
                _instance = new GlobalParameters();
                _instance._daysOfWeek = new ArrayOfDayOfWeekVO();
            }
            
            return _instance;
        }
        
        public function get userName():String {
            return _userName;
        }
        
        public function set userName(name:String):void {
            this._userName = name;
        }
        
        public function get sessionId():Number {
            return _sessionId;
        }
        
        public function set sessionId(sessionId:Number):void {
            this._sessionId = sessionId;
        }
        
        public function get test():Boolean {
            return _test;
        }
        
        public function set test(test:Boolean):void {
            _test = test;
        }
        
        public function get version():String {
            return _version;
        }
        
        public function set version(version:String):void {
            _version = version;
        }
        
        public function get resource():String {
            return _resources;
        }
        
        public function set resource(resource:String):void {
            _resources = resource;
        }
        
        public function get corporateListParameter():ArrayOfCorporateParameter {
            return _corporateListParameter;
        }
        
        public function set corporateListParameter(list:ArrayOfCorporateParameter):void {
            this._corporateListParameter = list;
        }
        
        public function get daysOfWeek():ArrayOfDayOfWeekVO {
            return _daysOfWeek;
        }
        
        public function set daysOfWeek(days:ArrayOfDayOfWeekVO):void {
            this._daysOfWeek = days;
        }
        
        public function getCorporateParameter(corporateId:Number):CorporateParameter {
            var corporateParameter:CorporateParameter = null;
            
            for each (var cp:CorporateParameter in this._corporateListParameter) {
                if (cp.id == corporateId) {
                    corporateParameter = cp;
                }
            }
            
            return corporateParameter;
        }
        
        public function loadDaysOfWeek():void {
            var dayOfWeek:DayOfWeekVO = new DayOfWeekVO();
            
            dayOfWeek.id = 1;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','sunday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);

            dayOfWeek = new DayOfWeekVO();
            dayOfWeek.id = 2;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','monday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);
            
            dayOfWeek = new DayOfWeekVO();
            dayOfWeek.id = 3;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','tuesday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);
            
            dayOfWeek = new DayOfWeekVO();
            dayOfWeek.id = 4;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','wednesday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);

            dayOfWeek = new DayOfWeekVO();
            dayOfWeek.id = 5;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','thursday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);

            dayOfWeek = new DayOfWeekVO();
            dayOfWeek.id = 6;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','friday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);

            dayOfWeek = new DayOfWeekVO();
            dayOfWeek.id = 7;
            dayOfWeek.name = ResourceManager.getInstance().getString('resources','saturday');
            this.daysOfWeek.addDayOfWeekVO(dayOfWeek);
        }
        
        public function get getStatusSendEPCInterval():Number {
        	return _getStatusSendEPCInterval;
        }
        
        public function set getStatusSendEPCInterval(val:Number):void {
        	this._getStatusSendEPCInterval = val;
        }
    }
}