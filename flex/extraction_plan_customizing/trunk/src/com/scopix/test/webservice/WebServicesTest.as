package com.scopix.test.webservice
{
	import com.scopix.test.events.ResponseFromWebServiceEventTest;
	
	import flash.events.EventDispatcher;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.soap.types.*;

	/**
	 * Dispatches when the operation that has been called fails. The fault event is common for all operations
	 * of the WSDL
	 * @eventType mx.rpc.events.FaultEvent
	 */
    [Event(name="fault", type="mx.rpc.events.FaultEvent")]

    public class WebServicesTest extends EventDispatcher
    {
        private var _baseService:BaseSecurityWebServicesTest;
        
        public function WebServicesTest(destination:String=null,rootURL:String=null) {
            _baseService = new BaseSecurityWebServicesTest();
        }
        public function set endpointURI(value:String):void {
        }
                  
        /**
         * @see ISecurityWebServices#login()
         */
        public function login(in0:String,in1:String,in2:Number):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.login(in0,in1,in2);
            /* _internal_token.addEventListener("result",_login_populate_results);
            _internal_token.addEventListener("fault",throwFault);  */
            return _internal_token;
		}
		
		/**
		 * @see ISecurityWebServices#addgetCorporatesForUser()
		 */
		public function addgetGenericOperationEventListener(listener:Function):void
		{
			addEventListener(ResponseFromWebServiceEventTest.Operation_RESULT,listener);
		}		
		
		//service-wide functions
		/**
		 * @see ISecurityWebServices#getWebService()
		 */
		public function getWebService():BaseSecurityWebServicesTest
		{
			return _baseService;
		}
		
		/**
		 * Set the event listener for the fault event which can be triggered by each of the operations defined by the facade
		 */
		public function addWebServicesFaultEventListener(listener:Function):void
		{
			addEventListener("fault",listener);
		}
		
		/**
		 * Internal function to re-dispatch the fault event passed on by the base service implementation
		 * @private
		 */
		 
		private function throwFault(event:FaultEvent):void
		{
		    dispatchEvent(event);
		}
    }
}