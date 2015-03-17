
/**
 * Service.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client{
	import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceDTO;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceResponseDTO;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.FormatDevice_request;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.GetCopyInfo_request;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.MountDevice_request;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.request.UnMountDevice_request;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.soap.types.*;
               
    public interface IUploadEvidenceToExternalDeviceWebService
    {
    	//Stub functions for the unMountDevice operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param in0
    	 * @return An AsyncToken
    	 */
    	function unMountDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function unMountDevice_send():AsyncToken;
        
        /**
         * The unMountDevice operation lastResult property
         */
        function get unMountDevice_lastResult():UploadEvidenceToExternalDeviceResponseDTO;
		/**
		 * @private
		 */
        function set unMountDevice_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void;
       /**
        * Add a listener for the unMountDevice operation successful result event
        * @param The listener function
        */
       function addunMountDeviceEventListener(listener:Function):void;
       
       
        /**
         * The unMountDevice operation request wrapper
         */
        function get unMountDevice_request_var():UnMountDevice_request;
        
        /**
         * @private
         */
        function set unMountDevice_request_var(request:UnMountDevice_request):void;
                   
    	//Stub functions for the formatDevice operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param in0
    	 * @return An AsyncToken
    	 */
    	function formatDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function formatDevice_send():AsyncToken;
        
        /**
         * The formatDevice operation lastResult property
         */
        function get formatDevice_lastResult():UploadEvidenceToExternalDeviceResponseDTO;
		/**
		 * @private
		 */
        function set formatDevice_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void;
       /**
        * Add a listener for the formatDevice operation successful result event
        * @param The listener function
        */
       function addformatDeviceEventListener(listener:Function):void;
       
       
        /**
         * The formatDevice operation request wrapper
         */
        function get formatDevice_request_var():FormatDevice_request;
        
        /**
         * @private
         */
        function set formatDevice_request_var(request:FormatDevice_request):void;
                   
    	//Stub functions for the getCopyInfo operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param in0
    	 * @return An AsyncToken
    	 */
    	function getCopyInfo(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function getCopyInfo_send():AsyncToken;
        
        /**
         * The getCopyInfo operation lastResult property
         */
        function get getCopyInfo_lastResult():UploadEvidenceToExternalDeviceResponseDTO;
		/**
		 * @private
		 */
        function set getCopyInfo_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void;
       /**
        * Add a listener for the getCopyInfo operation successful result event
        * @param The listener function
        */
       function addgetCopyInfoEventListener(listener:Function):void;
       
       
        /**
         * The getCopyInfo operation request wrapper
         */
        function get getCopyInfo_request_var():GetCopyInfo_request;
        
        /**
         * @private
         */
        function set getCopyInfo_request_var(request:GetCopyInfo_request):void;
                   
    	//Stub functions for the mountDevice operation
    	/**
    	 * Call the operation on the server passing in the arguments defined in the WSDL file
    	 * @param in0
    	 * @return An AsyncToken
    	 */
    	function mountDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken;
        /**
         * Method to call the operation on the server without passing the arguments inline.
         * You must however set the _request property for the operation before calling this method
         * Should use it in MXML context mostly
         * @return An AsyncToken
         */
        function mountDevice_send():AsyncToken;
        
        /**
         * The mountDevice operation lastResult property
         */
        function get mountDevice_lastResult():UploadEvidenceToExternalDeviceResponseDTO;
		/**
		 * @private
		 */
        function set mountDevice_lastResult(lastResult:UploadEvidenceToExternalDeviceResponseDTO):void;
       /**
        * Add a listener for the mountDevice operation successful result event
        * @param The listener function
        */
       function addmountDeviceEventListener(listener:Function):void;
       
       
        /**
         * The mountDevice operation request wrapper
         */
        function get mountDevice_request_var():MountDevice_request;
        
        /**
         * @private
         */
        function set mountDevice_request_var(request:MountDevice_request):void;
                   
        /**
         * Get access to the underlying web service that the stub uses to communicate with the server
         * @return The base service that the facade implements
         */
        function getWebService():BaseUploadEvidenceToExternalDeviceWebService;
	}
}