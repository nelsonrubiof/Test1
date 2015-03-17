/**
 * UnMountDeviceResultEvent.as
 * This file was auto-generated from WSDL
 * Any change made to this file will be overwritten when the code is re-generated.
*/
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client.result
{
	import mx.utils.ObjectProxy;
	import flash.events.Event;
	import flash.utils.ByteArray;
	import mx.rpc.soap.types.*;
	import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceResponseDTO;
	/**
	 * Typed event handler for the result of the operation
	 */
    
	public class UnMountDeviceResultEvent extends Event
	{
		/**
		 * The event type value
		 */
		public static var UnMountDevice_RESULT:String="UnMountDevice_result";
		/**
		 * Constructor for the new event type
		 */
		public function UnMountDeviceResultEvent()
		{
			super(UnMountDevice_RESULT,false,false);
		}
        
		private var _headers:Object;
		private var _result:UploadEvidenceToExternalDeviceResponseDTO;
		public function get result():UploadEvidenceToExternalDeviceResponseDTO
		{
			return _result;
		}

		public function set result(value:UploadEvidenceToExternalDeviceResponseDTO):void
		{
			_result = value;
		}

		public function get headers():Object
		{
			return _headers;
		}

		public function set headers(value:Object):void
		{
			_headers = value;
		}
	}
}