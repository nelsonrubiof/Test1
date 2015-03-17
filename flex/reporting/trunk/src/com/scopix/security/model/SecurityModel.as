package com.scopix.security.model
{
    public class SecurityModel
    {
        private static var _instance:SecurityModel;
        
        /** variable para indicar si los datos del usuario fueron cargados desde una cookie **/
        public var userLoaded:Boolean;
        
        public var sessionId:Number;

        [Bindable]
        public var rememberLogin:Boolean;
        
        [Bindable]
        public var userName:String;
        
        [Bindable]
        public var password:String;
        
        public var passwordToSaveInCookie:String;
        
        public static function getInstance():SecurityModel {
            if (_instance == null) {
                _instance = new SecurityModel();
            }
            
            return _instance;
        }

        public function SecurityModel() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
    }
}