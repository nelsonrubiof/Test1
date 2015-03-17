package commons.commands
{
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    
    import flash.events.EventDispatcher;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.soap.SOAPFault;

    public class BaseCommand extends EventDispatcher
    {
        private var _method:Function;
        private var _errorFunction:Function;
        private var _genericEvent:GenericEvent;
        private var _genericErrorEvent:GenericErrorEvent;
        private var _webService:EventDispatcher;
        private var _webServiceResultFunction:Function;
        private var _webServiceFaultFunction:Function;
        private var _webServiceOperation:String;
        
        /**
        * Los eventos indicados como parametros, corresponden a los eventos con los cuales responderá el command.
        * GenericEvent sera el evento que se lanzara en caso de que el llamado responda correctamente.
        * GenericErrorEvent sera el evento que se lanzara en caso de error
        **/
        public function BaseCommand(genericEvent:GenericEvent, genericErrorEvent:GenericErrorEvent) {
            this._genericEvent = genericEvent;
            this._genericErrorEvent = genericErrorEvent;
        }
        
        public function addCommandListener(method:Function = null, errorFunction:Function = null):void {
            this._method = method;
            this._errorFunction = errorFunction;
            //this._event = new SendExtractionPlanCustomizingCommandResultEvent();
            //this._genericErrorEvent = new WSFaultEvent();
            //super.addCommandListener();
            if (this._genericEvent != null) {
                this.addEventListener(this._genericEvent.eventName, this._method);
            }
            if (this._genericErrorEvent != null) {
                this.addEventListener(this._genericErrorEvent.eventName, this._errorFunction);
            }
        }
        
        public function removeCommandListener():void {
            //sacando los eventos asociados al command
            if (this._genericEvent != null) {
                this.removeEventListener(this._genericEvent.eventName, this._method);
            }
            if (this._genericErrorEvent != null) {
                this.removeEventListener(this._genericErrorEvent.eventName, this._errorFunction);
            }
        }
        
        public function fault(event:FaultEvent):void {

        	if (event.fault is SOAPFault) {
		    	var fault:SOAPFault=event.fault as SOAPFault;
    			var faultElement:XML=fault.element;
    			var xml:XML = new XML(faultElement.faultstring);

  				if (xml != null) {
  				    this._genericErrorEvent.message = xml;
  				} else {
  				    this._genericErrorEvent.message = "GENERIC_ERROR_MESSAGE";
  				}
            } else {
                if (event.fault != null && event.fault.message != null) {
                    this._genericErrorEvent.message = event.fault.message;
                } else {
                    this._genericErrorEvent.message = "GENERIC_ERROR_MESSAGE";
                }
            }
            
            dispatchEvent(this._genericErrorEvent);
        }
        
        public function addWSListener(operation:String, resultFunction:Function, faultFunction:Function):void {
            _webServiceOperation = operation;
            _webServiceResultFunction = resultFunction;
            _webServiceFaultFunction = faultFunction;
            
            _webService.addEventListener(operation, resultFunction);
            _webService.addEventListener(FaultEvent.FAULT, faultFunction);
        }
        
        public function removeWSListener():void {
            _webService.removeEventListener(_webServiceOperation, _webServiceResultFunction);
            _webService.removeEventListener(FaultEvent.FAULT, _webServiceFaultFunction);
        }
        
        public function set method(func:Function):void {
            this._method = func;
        }
        
        public function set errorFunction(func:Function):void {
            this._errorFunction = func;
        }
        
        public function set event(evt:GenericEvent):void {
            this._genericEvent = evt;
        }
        
        public function get event():GenericEvent {
            return _genericEvent;
        }
        
        public function set genericErrorEvent(evt:GenericErrorEvent):void {
            this._genericErrorEvent = evt;
        }
        
        public function get webService():EventDispatcher {
            return _webService;
        }
        
        public function set webService(ws:EventDispatcher):void {
            this._webService = ws;
        }
    }
}