package com.scopix.global {

	import com.scopix.reporting.model.arrays.ArrayOfCorporateParameter;
	import com.scopix.reporting.model.vo.CorporateParameter;

	[Bindable]
	public class GlobalParameters {
        //variable usada para enviar la fecha al backend
        public static const DATE_FORMAT:String = "YYYY-MM-DD";
		private var _dateFormat:String;
		private var _dateTimeFormat:String;
		private static var _instance:GlobalParameters;
		private static var _resources:String = "resources";
		private var _userName:String;
		private var _sessionId:Number;
		private var _test:Boolean = false;
		private var _version:String;
		private var _corporateListParameter:ArrayOfCorporateParameter;
		private var _intervalStatusProcess:Number;

		public function GlobalParameters() {
			if (_instance != null) {
				throw new Error("This class can only be accessed through getInstance method");
			}
		}

		public static function getInstance():GlobalParameters {
			if (_instance == null) {
				_instance = new GlobalParameters();
			}

			return _instance;
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

		public function get test():Boolean {
			return _test;
		}

		public function set test(val:Boolean):void {
			_test = val;
		}

		public function get intervalStatusProcess():Number {
			return _intervalStatusProcess;
		}

		public function set intervalStatusProcess(val:Number):void {
			_intervalStatusProcess = val;
		}

		public function get userName():String {
			return _userName;
		}

		public function set userName(val:String):void {
			_userName = val;
		}

		public function get sessionId():Number {
			return _sessionId;
		}

		public function set sessionId(val:Number):void {
			_sessionId = val;
		}

		public function get version():String {
			return _version;
		}

		public function set version(val:String):void {
			_version = val;
		}

		public function get corporateListParameter():ArrayOfCorporateParameter {
			return _corporateListParameter;
		}

		public function set corporateListParameter(val:ArrayOfCorporateParameter):void {
			_corporateListParameter = val;
		}

		public function get dateFormat():String {
			return _dateFormat;
		}

		public function set dateFormat(value:String):void {
			_dateFormat = value;
		}

		public function get dateTimeFormat():String {
			return _dateTimeFormat;
		}

		public function set dateTimeFormat(value:String):void {
			_dateTimeFormat = value;
		}
	}
}