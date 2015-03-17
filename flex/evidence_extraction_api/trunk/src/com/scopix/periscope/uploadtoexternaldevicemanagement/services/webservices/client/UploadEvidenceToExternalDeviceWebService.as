/**
 * UploadEvidenceToExternalDeviceWebServiceService.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
 /**
  * Usage example: to use this service from within your Flex application you have two choices:
  * Use it via Actionscript only
  * Use it via MXML tags
  * Actionscript sample code:
  * Step 1: create an instance of the service; pass it the LCDS destination string if any
  * var myService:UploadEvidenceToExternalDeviceWebService= new UploadEvidenceToExternalDeviceWebService();
  * Step 2: for the desired operation add a result handler (a function that you have already defined previously)  
  * myService.addunMountDeviceEventListener(myResultHandlingFunction);
  * Step 3: Call the operation as a method on the service. Pass the right values as arguments:
  * myService.unMountDevice(myin0);
  *
  * MXML sample code:
  * First you need to map the package where the files were generated to a namespace, usually on the <mx:Application> tag, 
  * like this: xmlns:srv="temp.webservices.*"
  * Define the service and within its tags set the request wrapper for the desired operation
  * <srv:UploadEvidenceToExternalDeviceWebService id="myService">
  *   <srv:unMountDevice_request_var>
  *		<srv:UnMountDevice_request in0=myValue/>
  *   </srv:unMountDevice_request_var>
  * </srv:UploadEvidenceToExternalDeviceWebService>
  * Then call the operation for which you have set the request wrapper value above, like this:
  * <mx:Button id="myButton" label="Call operation" click="myService.unMountDevice_send()" />
  */
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client
{
	import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceDTO;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceResponseDTO;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.FormatDevice_request;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.GetCopyInfo_request;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.MountDevice_request;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.UnMountDevice_request;
	
	import flash.events.EventDispatcher;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.soap.types.*;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.result.FormatDeviceResultEvent;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.result.UnMountDeviceResultEvent;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.result.GetCopyInfoResultEvent;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.result.MountDeviceResultEvent;

    /**
     * Dispatches when a call to the operation unMountDevice completes with success
     * and returns some data
     * @eventType UnMountDeviceResultEvent
     */
    [Event(name="UnMountDevice_result", type="temp.webservices.UnMountDeviceResultEvent")]
    
    /**
     * Dispatches when a call to the operation formatDevice completes with success
     * and returns some data
     * @eventType FormatDeviceResultEvent
     */
    [Event(name="FormatDevice_result", type="temp.webservices.FormatDeviceResultEvent")]
    
    /**
     * Dispatches when a call to the operation getCopyInfo completes with success
     * and returns some data
     * @eventType GetCopyInfoResultEvent
     */
    [Event(name="GetCopyInfo_result", type="temp.webservices.GetCopyInfoResultEvent")]
    
    /**
     * Dispatches when a call to the operation mountDevice completes with success
     * and returns some data
     * @eventType MountDeviceResultEvent
     */
    [Event(name="MountDevice_result", type="temp.webservices.MountDeviceResultEvent")]
    
	/**
	 * Dispatches when the operation that has been called fails. The fault event is common for all operations
	 * of the WSDL
	 * @eventType mx.rpc.events.FaultEvent
	 */
    [Event(name="fault", type="mx.rpc.events.FaultEvent")]

	public class UploadEvidenceToExternalDeviceWebService extends EventDispatcher implements IUploadEvidenceToExternalDeviceWebService
	{
    	private var _baseService:BaseUploadEvidenceToExternalDeviceWebService;
        
        /**
         * Constructor for the facade; sets the destination and create a baseService instance
         * @param The LCDS destination (if any) associated with the imported WSDL
         */  
        public function UploadEvidenceToExternalDeviceWebService(destination:String=null,rootURL:String=null)
        {
        	_baseService = new BaseUploadEvidenceToExternalDeviceWebService(destination,rootURL);
        }
        
		//stub functions for the unMountDevice operation
          

        /**
         * @see IUploadEvidenceToExternalDeviceWebService#unMountDevice()
         */
        public function unMountDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.unMountDevice(in0);
            _internal_token.addEventListener("result",_unMountDevice_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IUploadEvidenceToExternalDeviceWebService#unMountDevice_send()
		 */    
        public function unMountDevice_send():AsyncToken
        {
        	return unMountDevice(_unMountDevice_request.in0);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _unMountDevice_request:UnMountDevice_request;
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#unMountDevice_request_var
		 */
		[Bindable]
		public function get unMountDevice_request_var():UnMountDevice_request
		{
			return _unMountDevice_request;
		}
		
		/**
		 * @private
		 */
		public function set unMountDevice_request_var(request:UnMountDevice_request):void
		{
			_unMountDevice_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _unMountDevice_lastResult:UploadEvidenceToExternalDeviceResponseDTO;
		[Bindable]
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#unMountDevice_lastResult
		 */	  
		public function get unMountDevice_lastResult():UploadEvidenceToExternalDeviceResponseDTO
		{
			return _unMountDevice_lastResult;
		}
		/**
		 * @private
		 */
		public function set unMountDevice_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void
		{
			_unMountDevice_lastResult = lastResult;
		}
		
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#addunMountDevice()
		 */
		public function addunMountDeviceEventListener(listener:Function):void
		{
			addEventListener(UnMountDeviceResultEvent.UnMountDevice_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _unMountDevice_populate_results(event:ResultEvent):void
		{
			var e:UnMountDeviceResultEvent = new UnMountDeviceResultEvent();
		            e.result = event.result as UploadEvidenceToExternalDeviceResponseDTO;
		                       e.headers = event.headers;
		             unMountDevice_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the formatDevice operation
          

        /**
         * @see IUploadEvidenceToExternalDeviceWebService#formatDevice()
         */
        public function formatDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.formatDevice(in0);
            _internal_token.addEventListener("result",_formatDevice_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IUploadEvidenceToExternalDeviceWebService#formatDevice_send()
		 */    
        public function formatDevice_send():AsyncToken
        {
        	return formatDevice(_formatDevice_request.in0);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _formatDevice_request:FormatDevice_request;
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#formatDevice_request_var
		 */
		[Bindable]
		public function get formatDevice_request_var():FormatDevice_request
		{
			return _formatDevice_request;
		}
		
		/**
		 * @private
		 */
		public function set formatDevice_request_var(request:FormatDevice_request):void
		{
			_formatDevice_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _formatDevice_lastResult:UploadEvidenceToExternalDeviceResponseDTO;
		[Bindable]
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#formatDevice_lastResult
		 */	  
		public function get formatDevice_lastResult():UploadEvidenceToExternalDeviceResponseDTO
		{
			return _formatDevice_lastResult;
		}
		/**
		 * @private
		 */
		public function set formatDevice_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void
		{
			_formatDevice_lastResult = lastResult;
		}
		
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#addformatDevice()
		 */
		public function addformatDeviceEventListener(listener:Function):void
		{
			addEventListener(FormatDeviceResultEvent.FormatDevice_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _formatDevice_populate_results(event:ResultEvent):void
		{
			var e:FormatDeviceResultEvent = new FormatDeviceResultEvent();
		            e.result = event.result as UploadEvidenceToExternalDeviceResponseDTO;
		                       e.headers = event.headers;
		             formatDevice_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the getCopyInfo operation
          

        /**
         * @see IUploadEvidenceToExternalDeviceWebService#getCopyInfo()
         */
        public function getCopyInfo(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.getCopyInfo(in0);
            _internal_token.addEventListener("result",_getCopyInfo_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IUploadEvidenceToExternalDeviceWebService#getCopyInfo_send()
		 */    
        public function getCopyInfo_send():AsyncToken
        {
        	return getCopyInfo(_getCopyInfo_request.in0);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _getCopyInfo_request:GetCopyInfo_request;
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#getCopyInfo_request_var
		 */
		[Bindable]
		public function get getCopyInfo_request_var():GetCopyInfo_request
		{
			return _getCopyInfo_request;
		}
		
		/**
		 * @private
		 */
		public function set getCopyInfo_request_var(request:GetCopyInfo_request):void
		{
			_getCopyInfo_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _getCopyInfo_lastResult:UploadEvidenceToExternalDeviceResponseDTO;
		[Bindable]
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#getCopyInfo_lastResult
		 */	  
		public function get getCopyInfo_lastResult():UploadEvidenceToExternalDeviceResponseDTO
		{
			return _getCopyInfo_lastResult;
		}
		/**
		 * @private
		 */
		public function set getCopyInfo_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void
		{
			_getCopyInfo_lastResult = lastResult;
		}
		
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#addgetCopyInfo()
		 */
		public function addgetCopyInfoEventListener(listener:Function):void
		{
			addEventListener(GetCopyInfoResultEvent.GetCopyInfo_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _getCopyInfo_populate_results(event:ResultEvent):void
		{
			var e:GetCopyInfoResultEvent = new GetCopyInfoResultEvent();
		            e.result = event.result as UploadEvidenceToExternalDeviceResponseDTO;
		                       e.headers = event.headers;
		             getCopyInfo_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//stub functions for the mountDevice operation
          

        /**
         * @see IUploadEvidenceToExternalDeviceWebService#mountDevice()
         */
        public function mountDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
        {
         	var _internal_token:AsyncToken = _baseService.mountDevice(in0);
            _internal_token.addEventListener("result",_mountDevice_populate_results);
            _internal_token.addEventListener("fault",throwFault); 
            return _internal_token;
		}
        /**
		 * @see IUploadEvidenceToExternalDeviceWebService#mountDevice_send()
		 */    
        public function mountDevice_send():AsyncToken
        {
        	return mountDevice(_mountDevice_request.in0);
        }
              
		/**
		 * Internal representation of the request wrapper for the operation
		 * @private
		 */
		private var _mountDevice_request:MountDevice_request;
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#mountDevice_request_var
		 */
		[Bindable]
		public function get mountDevice_request_var():MountDevice_request
		{
			return _mountDevice_request;
		}
		
		/**
		 * @private
		 */
		public function set mountDevice_request_var(request:MountDevice_request):void
		{
			_mountDevice_request = request;
		}
		
	  		/**
		 * Internal variable to store the operation's lastResult
		 * @private
		 */
        private var _mountDevice_lastResult:UploadEvidenceToExternalDeviceResponseDTO;
		[Bindable]
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#mountDevice_lastResult
		 */	  
		public function get mountDevice_lastResult():UploadEvidenceToExternalDeviceResponseDTO
		{
			return _mountDevice_lastResult;
		}
		/**
		 * @private
		 */
		public function set mountDevice_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void
		{
			_mountDevice_lastResult = lastResult;
		}
		
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#addmountDevice()
		 */
		public function addmountDeviceEventListener(listener:Function):void
		{
			addEventListener(MountDeviceResultEvent.MountDevice_RESULT,listener);
		}
			
		/**
		 * @private
		 */
        private function _mountDevice_populate_results(event:ResultEvent):void
		{
			var e:MountDeviceResultEvent = new MountDeviceResultEvent();
		            e.result = event.result as UploadEvidenceToExternalDeviceResponseDTO;
		                       e.headers = event.headers;
		             mountDevice_lastResult = e.result;
		             dispatchEvent(e);
	        		}
		
		//service-wide functions
		/**
		 * @see IUploadEvidenceToExternalDeviceWebService#getWebService()
		 */
		public function getWebService():BaseUploadEvidenceToExternalDeviceWebService
		{
			return _baseService;
		}
		
		/**
		 * Set the event listener for the fault event which can be triggered by each of the operations defined by the facade
		 */
		public function addUploadEvidenceToExternalDeviceWebServiceFaultEventListener(listener:Function):void
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
