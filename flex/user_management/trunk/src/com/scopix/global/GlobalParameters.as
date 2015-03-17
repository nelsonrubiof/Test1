package com.scopix.global
{
    [Bindable]
    public class GlobalParameters
    {
        private var _userName:String;
        private var _sessionId:Number;
        private var _test:Boolean = false;
        private static var _instance:GlobalParameters;
        private var _version:String;
        
        public static function getInstance():GlobalParameters {
            if (_instance == null) {
                _instance = new GlobalParameters();
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
    }
}