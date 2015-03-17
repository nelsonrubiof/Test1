/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this service wrapper you may modify the generated sub-class of this class - QualityControlWebServices.as.
 */
package com.scopix.periscope.webservices.businessservices
{
import com.adobe.fiber.core.model_internal;
import com.adobe.fiber.services.wrapper.WebServiceWrapper;
import com.adobe.serializers.utility.TypeUtility;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceFinishedDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.FilteringData;
import com.scopix.periscope.webservices.businessservices.valueObjects.MetricResultDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.MotivoRejectedDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.ObservedMetricResultDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.ObservedSituationFinishedDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
import mx.collections.ArrayCollection;
import mx.rpc.AbstractOperation;
import mx.rpc.AsyncToken;
import mx.rpc.soap.mxml.Operation;
import mx.rpc.soap.mxml.WebService;

[ExcludeClass]
internal class _Super_QualityControlWebServices extends com.adobe.fiber.services.wrapper.WebServiceWrapper
{
    
    // Constructor
    public function _Super_QualityControlWebServices()
    {
        // initialize service control
        _serviceControl = new mx.rpc.soap.mxml.WebService();
        var operations:Object = new Object();
        var operation:mx.rpc.soap.mxml.Operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "rejectsEvaluations");
        operations["rejectsEvaluations"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getMotivosRejected");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.MotivoRejectedDTO;
        operations["getMotivosRejected"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getStores");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
        operations["getStores"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "saveEvaluations");
        operations["saveEvaluations"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getObservedSituationFinished");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.ObservedSituationFinishedDTO;
        operations["getObservedSituationFinished"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getEvidenceDTOByMetricResult");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
        operations["getEvidenceDTOByMetricResult"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getEvidenceFinishedList");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceFinishedDTO;
        operations["getEvidenceFinishedList"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getEvidenceFinished");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.ObservedMetricResultDTO;
        operations["getEvidenceFinished"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getMetricResultByObservedSituation");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.MetricResultDTO;
        operations["getMetricResultByObservedSituation"] = operation;

        _serviceControl.operations = operations;
        try
        {
            _serviceControl.convertResultHandler = com.adobe.serializers.utility.TypeUtility.convertResultHandler;
        }
        catch (e: Error)
        { /* Flex 3.4 and earlier does not support the convertResultHandler functionality. */ }


        preInitializeService();
        model_internal::initialize();
    }
    
    //init initialization routine here, child class to override
    protected function preInitializeService():void
    {


        _serviceControl.service = "QualityControlWebServicesImplService";
        _serviceControl.port = "QualityControlWebServicesImplPort";
        //wsdl = "http://127.0.0.1:28080/bs-cxf/spring/services/QualityControlWebServices?wsdl";
        model_internal::loadWSDLIfNecessary();
    }
    

    /**
      * This method is a generated wrapper used to call the 'rejectsEvaluations' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function rejectsEvaluations(arg0:ArrayCollection, arg1:String, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("rejectsEvaluations");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getMotivosRejected' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getMotivosRejected(arg0:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getMotivosRejected");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getStores' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getStores(arg0:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getStores");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'saveEvaluations' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function saveEvaluations(arg0:ArrayCollection, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("saveEvaluations");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getObservedSituationFinished' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getObservedSituationFinished(arg0:com.scopix.periscope.webservices.businessservices.valueObjects.FilteringData, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getObservedSituationFinished");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getEvidenceDTOByMetricResult' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getEvidenceDTOByMetricResult(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getEvidenceDTOByMetricResult");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getEvidenceFinishedList' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getEvidenceFinishedList(arg0:Date, arg1:Date, arg2:Boolean, arg3:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getEvidenceFinishedList");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2,arg3) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getEvidenceFinished' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getEvidenceFinished(arg0:com.scopix.periscope.webservices.businessservices.valueObjects.FilteringData, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getEvidenceFinished");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getMetricResultByObservedSituation' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getMetricResultByObservedSituation(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getMetricResultByObservedSituation");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
}

}
