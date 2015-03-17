/**
 * BaseUploadEvidenceToExternalDeviceWebServiceService.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.client
{
	import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceDTO;
	
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.DirectHTTPChannel;
	import mx.messaging.events.MessageFaultEvent;
	import mx.messaging.messages.SOAPMessage;
	import mx.rpc.*;
	import mx.rpc.events.*;
	import mx.rpc.soap.*;
	import mx.rpc.soap.types.*;
	import mx.rpc.wsdl.*;
	import mx.rpc.xml.*;
	import commons.arrays.ArrayOfString;
	
	/**
	 * Base service implementation, extends the AbstractWebService and adds specific functionality for the selected WSDL
	 * It defines the options and properties for each of the WSDL's operations
	 */ 
	public class BaseUploadEvidenceToExternalDeviceWebService extends AbstractWebService
    {
		private var results:Object;
		private var schemaMgr:SchemaManager;
		private var BaseUploadEvidenceToExternalDeviceWebServiceService:WSDLService;
		private var BaseUploadEvidenceToExternalDeviceWebServicePortType:WSDLPortType;
		private var BaseUploadEvidenceToExternalDeviceWebServiceBinding:WSDLBinding;
		private var BaseUploadEvidenceToExternalDeviceWebServicePort:WSDLPort;
		private var currentOperation:WSDLOperation;
		private var internal_schema:BaseUploadEvidenceToExternalDeviceWebServiceSchema;
	
		/**
		 * Constructor for the base service, initializes all of the WSDL's properties
		 * @param [Optional] The LCDS destination (if available) to use to contact the server
		 * @param [Optional] The URL to the WSDL end-point
		 */
		public function BaseUploadEvidenceToExternalDeviceWebService(destination:String=null, rootURL:String=null)
		{
			super(destination, rootURL);
			if(destination == null)
			{
				//no destination available; must set it to go directly to the target
				this.useProxy = false;
			}
			else
			{
				//specific destination requested; must set proxying to true
				this.useProxy = true;
			}
			
			if(rootURL != null)
			{
				this.endpointURI = rootURL;
			} 
			else 
			{
				this.endpointURI = null;
			}
			internal_schema = new BaseUploadEvidenceToExternalDeviceWebServiceSchema();
			schemaMgr = new SchemaManager();
			for(var i:int;i<internal_schema.schemas.length;i++)
			{
				internal_schema.schemas[i].targetNamespace=internal_schema.targetNamespaces[i];
				schemaMgr.addSchema(internal_schema.schemas[i]);
			}
BaseUploadEvidenceToExternalDeviceWebServiceService = new WSDLService("BaseUploadEvidenceToExternalDeviceWebServiceService");
			BaseUploadEvidenceToExternalDeviceWebServicePort = new WSDLPort("BaseUploadEvidenceToExternalDeviceWebServicePort",BaseUploadEvidenceToExternalDeviceWebServiceService);
        	BaseUploadEvidenceToExternalDeviceWebServiceBinding = new WSDLBinding("BaseUploadEvidenceToExternalDeviceWebServiceBinding");
	        BaseUploadEvidenceToExternalDeviceWebServicePortType = new WSDLPortType("BaseUploadEvidenceToExternalDeviceWebServicePortType");
       		BaseUploadEvidenceToExternalDeviceWebServiceBinding.portType = BaseUploadEvidenceToExternalDeviceWebServicePortType;
       		BaseUploadEvidenceToExternalDeviceWebServicePort.binding = BaseUploadEvidenceToExternalDeviceWebServiceBinding;
       		BaseUploadEvidenceToExternalDeviceWebServiceService.addPort(BaseUploadEvidenceToExternalDeviceWebServicePort);
       		BaseUploadEvidenceToExternalDeviceWebServicePort.endpointURI = "http://localhost:8080/ees/spring/services/UploadEvidenceToExternalDeviceWebService";
       		if(this.endpointURI == null)
       		{
       			this.endpointURI = BaseUploadEvidenceToExternalDeviceWebServicePort.endpointURI; 
       		} 
       		
			var requestMessage:WSDLMessage;
			var responseMessage:WSDLMessage;
			//define the WSDLOperation: new WSDLOperation(methodName)
            var unMountDevice:WSDLOperation = new WSDLOperation("unMountDevice");
				//input message for the operation
    	        requestMessage = new WSDLMessage("unMountDevice");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","in0"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceDTO")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","unMountDevice");
                
                responseMessage = new WSDLMessage("unMountDeviceResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","out"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceResponseDTO")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","unMountDeviceResponse");
			unMountDevice.inputMessage = requestMessage;
	        unMountDevice.outputMessage = responseMessage;
            unMountDevice.schemaManager = this.schemaMgr;
            unMountDevice.soapAction = "\"\"";
            unMountDevice.style = "document";
            BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.addOperation(unMountDevice);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var formatDevice:WSDLOperation = new WSDLOperation("formatDevice");
				//input message for the operation
    	        requestMessage = new WSDLMessage("formatDevice");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","in0"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceDTO")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","formatDevice");
                
                responseMessage = new WSDLMessage("formatDeviceResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","out"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceResponseDTO")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","formatDeviceResponse");
			formatDevice.inputMessage = requestMessage;
	        formatDevice.outputMessage = responseMessage;
            formatDevice.schemaManager = this.schemaMgr;
            formatDevice.soapAction = "\"\"";
            formatDevice.style = "document";
            BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.addOperation(formatDevice);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var getCopyInfo:WSDLOperation = new WSDLOperation("getCopyInfo");
				//input message for the operation
    	        requestMessage = new WSDLMessage("getCopyInfo");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","in0"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceDTO")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","getCopyInfo");
                
                responseMessage = new WSDLMessage("getCopyInfoResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","out"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceResponseDTO")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","getCopyInfoResponse");
			getCopyInfo.inputMessage = requestMessage;
	        getCopyInfo.outputMessage = responseMessage;
            getCopyInfo.schemaManager = this.schemaMgr;
            getCopyInfo.soapAction = "\"\"";
            getCopyInfo.style = "document";
            BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.addOperation(getCopyInfo);
			//define the WSDLOperation: new WSDLOperation(methodName)
            var mountDevice:WSDLOperation = new WSDLOperation("mountDevice");
				//input message for the operation
    	        requestMessage = new WSDLMessage("mountDevice");
            				requestMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","in0"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceDTO")));
                requestMessage.encoding = new WSDLEncoding();
                requestMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
			requestMessage.encoding.useStyle="literal";
	            requestMessage.isWrapped = true;
	            requestMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","mountDevice");
                
                responseMessage = new WSDLMessage("mountDeviceResponse");
            				responseMessage.addPart(new WSDLMessagePart(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","out"),null,new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceResponseDTO")));
                responseMessage.encoding = new WSDLEncoding();
                responseMessage.encoding.namespaceURI="http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com";
                responseMessage.encoding.useStyle="literal";				
				
	            responseMessage.isWrapped = true;
	            responseMessage.wrappedQName = new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","mountDeviceResponse");
			mountDevice.inputMessage = requestMessage;
	        mountDevice.outputMessage = responseMessage;
            mountDevice.schemaManager = this.schemaMgr;
            mountDevice.soapAction = "\"\"";
            mountDevice.style = "document";
            BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.addOperation(mountDevice);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceDTO"),com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceDTO);
							SchemaTypeRegistry.getInstance().registerClass(new QName("http://dto.uploadtoexternaldevicemanagement.periscope.scopix.com","UploadEvidenceToExternalDeviceResponseDTO"),com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceResponseDTO);
							SchemaTypeRegistry.getInstance().registerCollectionClass(new QName("http://webservices.services.uploadtoexternaldevicemanagement.periscope.scopix.com","ArrayOfString"),commons.arrays.ArrayOfString);
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param in0
		 * @return Asynchronous token
		 */
		public function unMountDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["in0"] = in0;
	            currentOperation = BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.getOperation("unMountDevice");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param in0
		 * @return Asynchronous token
		 */
		public function formatDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["in0"] = in0;
	            currentOperation = BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.getOperation("formatDevice");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param in0
		 * @return Asynchronous token
		 */
		public function getCopyInfo(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["in0"] = in0;
	            currentOperation = BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.getOperation("getCopyInfo");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
		/**
		 * Performs the low level call to the server for the operation
		 * It passes along the headers and the operation arguments
		 * @param in0
		 * @return Asynchronous token
		 */
		public function mountDevice(in0:UploadEvidenceToExternalDeviceDTO):AsyncToken
		{
			var headerArray:Array = new Array();
            var out:Object = new Object();
            out["in0"] = in0;
	            currentOperation = BaseUploadEvidenceToExternalDeviceWebServiceService.getPort("BaseUploadEvidenceToExternalDeviceWebServicePort").binding.portType.getOperation("mountDevice");
            var pc:PendingCall = new PendingCall(out,headerArray);
            call(currentOperation,out,pc.token,pc.headers);
            return pc.token;
		}
        /**
         * Performs the actual call to the remove server
         * It SOAP-encodes the message using the schema and WSDL operation options set above and then calls the server using 
         * an async invoker
         * It also registers internal event handlers for the result / fault cases
         * @private
         */
        private function call(operation:WSDLOperation,args:Object,token:AsyncToken,headers:Array=null):void
        {
	    	var enc:SOAPEncoder = new SOAPEncoder();
	        var soap:Object = new Object;
	        var message:SOAPMessage = new SOAPMessage();
	        enc.wsdlOperation = operation;
	        soap = enc.encodeRequest(args,headers);
	        message.setSOAPAction(operation.soapAction);
	        message.body = soap.toString();
	        message.url=endpointURI;
            var inv:AsyncRequest = new AsyncRequest();
            inv.destination = super.destination;
            //we need this to handle multiple asynchronous calls 
            var wrappedData:Object = new Object();
            wrappedData.operation = currentOperation;
            wrappedData.returnToken = token;
            if(!this.useProxy)
            {
            	var dcs:ChannelSet = new ChannelSet();	
	        	dcs.addChannel(new DirectHTTPChannel("direct_http_channel"));
            	inv.channelSet = dcs;
            }                
            var processRes:AsyncResponder = new AsyncResponder(processResult,faultResult,wrappedData);
            inv.invoke(message,processRes);
		}
        
        /**
         * Internal event handler to process a successful operation call from the server
         * The result is decoded using the schema and operation settings and then the events get passed on to the actual facade that the user employs in the application 
         * @private
         */
		private function processResult(result:Object,wrappedData:Object):void
           {
           		var headers:Object;
           		var token:AsyncToken = wrappedData.returnToken;
                var currentOperation:WSDLOperation = wrappedData.operation;
                var decoder:SOAPDecoder = new SOAPDecoder();
                decoder.resultFormat = "object";
                decoder.headerFormat = "object";
                decoder.multiplePartsFormat = "object";
                decoder.ignoreWhitespace = true;
                decoder.makeObjectsBindable=false;
                decoder.wsdlOperation = currentOperation;
                decoder.schemaManager = currentOperation.schemaManager;
                var body:Object = result.message.body;
                var stringResult:String = String(body);
                if(stringResult == null  || stringResult == "")
                	return;
                var soapResult:SOAPResult = decoder.decodeResponse(result.message.body);
                if(soapResult.isFault)
                {
	                var faults:Array = soapResult.result as Array;
	                for each (var soapFault:Fault in faults)
	                {
		                var soapFaultEvent:FaultEvent = FaultEvent.createEvent(soapFault,token,null);
		                token.dispatchEvent(soapFaultEvent);
	                }
                } else {
	                result = soapResult.result;
	                headers = soapResult.headers;
	                var event:ResultEvent = ResultEvent.createEvent(result,token,null);
	                event.headers = headers;
	                token.dispatchEvent(event);
                }
           }
           	/**
           	 * Handles the cases when there are errors calling the operation on the server
           	 * This is not the case for SOAP faults, which is handled by the SOAP decoder in the result handler
           	 * but more critical errors, like network outage or the impossibility to connect to the server
           	 * The fault is dispatched upwards to the facade so that the user can do something meaningful 
           	 * @private
           	 */
			private function faultResult(error:MessageFaultEvent,token:Object):void
			{
				//when there is a network error the token is actually the wrappedData object from above	
				if(!(token is AsyncToken))
					token = token.returnToken;
				token.dispatchEvent(new FaultEvent(FaultEvent.FAULT,true,true,new Fault(error.faultCode,error.faultString,error.faultDetail)));
			}
		}
	}

	import mx.rpc.AsyncToken;
	import mx.rpc.AsyncResponder;
	import mx.rpc.wsdl.WSDLBinding;
                
    /**
     * Internal class to handle multiple operation call scheduling
     * It allows us to pass data about the operation being encoded / decoded to and from the SOAP encoder / decoder units. 
     * @private
     */
    class PendingCall
    {
		public var args:*;
		public var headers:Array;
		public var token:AsyncToken;
		
		public function PendingCall(args:Object, headers:Array=null)
		{
			this.args = args;
			this.headers = headers;
			this.token = new AsyncToken(null);
		}
	}