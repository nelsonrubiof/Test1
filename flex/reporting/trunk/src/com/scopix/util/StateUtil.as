package com.scopix.util
{
    import com.scopix.enum.EPCStatesEnum;
    
    public class StateUtil
    {
        private static var _instance:StateUtil;
        private static var ALL:String = "ALL";
        private static var EDITION:String = "EDITION";
        private static var SENT:String = "SENT";
        
        public function StateUtil() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
        
        public static function getInstance():StateUtil {
            if (_instance == null) {
                _instance = new StateUtil();
            }
            return _instance;
        }
        
        public function getStateToSendWS(state:EPCStatesEnum):String {
            var stateReturn:String = "";
            
            switch(state) {
                case EPCStatesEnum.ALL:
                    stateReturn = ALL;
                    break;
                case EPCStatesEnum.EDITION:
                    stateReturn = EDITION;
                    break;
                case EPCStatesEnum.SENT:
                    stateReturn = SENT;
                    break;
            }
            
            return stateReturn;
        }
        
        public function getStateFromWS(state:String):EPCStatesEnum {
            var stateReturn:EPCStatesEnum = null;
            
            switch(state) {
                case ALL:
                    stateReturn = EPCStatesEnum.ALL;
                    break;
                case EDITION:
                    stateReturn = EPCStatesEnum.EDITION;
                    break;
                case SENT:
                    stateReturn = EPCStatesEnum.SENT;
                    break;
            }
            
            return stateReturn;
        }
    }
}