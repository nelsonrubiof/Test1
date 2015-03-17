package commons.config
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	import mx.collections.XMLListCollection;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
    public class Configuration extends EventDispatcher
    {
    	public static var CONFIGURATION_COMPLETE:String = "CONFIGURATION_COMPLETE";
		private static var _instance:Configuration;
		private var service:HTTPService;
		public var config:XML;
		
		function Configuration() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
			config = new XML();
		}
		
		public static function getInstance():Configuration {
			if(_instance == null){
				_instance = new Configuration();
			}
			return _instance;
		}
		
		public function loadParameterFile(file:String, resultFormat:String):void {
    		service = new HTTPService();
    		service.url = file;
    		service.resultFormat = resultFormat;
    		service.addEventListener(ResultEvent.RESULT, loadParameters);
    		service.send();
        }
        
        private function loadParameters(event:ResultEvent):void {
        	service.removeEventListener(ResultEvent.RESULT, loadParameters);        	
            var configXMLList:XMLListCollection = new XMLListCollection();
            configXMLList.source = service.lastResult.Config;    
			config = XML(configXMLList.getItemAt(0));
			dispatchEvent(new Event(CONFIGURATION_COMPLETE));
        }
   }
}