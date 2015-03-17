/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this service wrapper you may modify the generated sub-class of this class - ExtractionPlanManagerWebServices.as.
 */
package com.scopix.periscope.webservices.businessservices
{
import com.adobe.fiber.core.model_internal;
import com.adobe.fiber.services.wrapper.WebServiceWrapper;
import com.adobe.serializers.utility.TypeUtility;
import com.scopix.periscope.webservices.businessservices.valueObjects.DetalleSolicitudDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDetailDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.MetricTemplateDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.SituationTemplateDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
import mx.collections.ArrayCollection;
import mx.rpc.AbstractOperation;
import mx.rpc.AsyncToken;
import mx.rpc.soap.mxml.Operation;
import mx.rpc.soap.mxml.WebService;

[ExcludeClass]
internal class _Super_ExtractionPlanManagerWebServices extends com.adobe.fiber.services.wrapper.WebServiceWrapper
{
    
    // Constructor
    public function _Super_ExtractionPlanManagerWebServices()
    {
        // initialize service control
        _serviceControl = new mx.rpc.soap.mxml.WebService();
        var operations:Object = new Object();
        var operation:mx.rpc.soap.mxml.Operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "saveEPCGeneral");
        operations["saveEPCGeneral"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getExtractionPlanCustomizingDatosGenerales");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["getExtractionPlanCustomizingDatosGenerales"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "deleteExtractionPlanRange");
        operations["deleteExtractionPlanRange"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "createExtractionPlanCustomizing");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["createExtractionPlanCustomizing"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getExtractionPlanRanges");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
        operations["getExtractionPlanRanges"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "disableExtractionPlanCustomizings");
        operations["disableExtractionPlanCustomizings"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getUltimoExtractionPlanCustomizingEnviado");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["getUltimoExtractionPlanCustomizingEnviado"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "copyEPCFullToEdition");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["copyEPCFullToEdition"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getStores");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
        operations["getStores"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "cleanExtractionPlanCutomizingDay");
        operations["cleanExtractionPlanCutomizingDay"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getMetricTemplates");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.MetricTemplateDTO;
        operations["getMetricTemplates"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getSensors");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO;
        operations["getSensors"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getDetalleSolicitudes");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.DetalleSolicitudDTO;
        operations["getDetalleSolicitudes"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getExtractionPlanCustomizings");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["getExtractionPlanCustomizings"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getSituationTemplates");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.SituationTemplateDTO;
        operations["getSituationTemplates"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getUltimoExtractionPlanCustomizingNoEnviado");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["getUltimoExtractionPlanCustomizingNoEnviado"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "saveExtractionPlanRange");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
        operations["saveExtractionPlanRange"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getExtractionPlanRange");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
        operations["getExtractionPlanRange"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "copyEPCToEdition");
         operation.resultType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
        operations["copyEPCToEdition"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "copyCalendarActions");
         operation.resultElementType = String;
        operations["copyCalendarActions"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getExtractionPlanRangeDetails");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDetailDTO;
        operations["getExtractionPlanRangeDetails"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "regenerateDetailForRange");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDetailDTO;
        operations["regenerateDetailForRange"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "getEvidenceProvidersStoreSituationTemplate");
         operation.resultElementType = com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;
        operations["getEvidenceProvidersStoreSituationTemplate"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "copyDayInDays");
        operations["copyDayInDays"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "regenerateDetailForEPC");
        operations["regenerateDetailForEPC"] = operation;

        operation = new mx.rpc.soap.mxml.Operation(null, "copyCalendar");
        operations["copyCalendar"] = operation;

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


        _serviceControl.service = "ExtractionPlanManagerWebServicesImplService";
        _serviceControl.port = "ExtractionPlanManagerWebServicesImplPort";
        //wsdl = "http://127.0.0.1:28080/bs-cxf/spring/services/ExtractionPlanManagerWebServices?wsdl";
        model_internal::loadWSDLIfNecessary();
    }
    

    /**
      * This method is a generated wrapper used to call the 'saveEPCGeneral' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function saveEPCGeneral(arg0:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("saveEPCGeneral");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getExtractionPlanCustomizingDatosGenerales' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getExtractionPlanCustomizingDatosGenerales(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getExtractionPlanCustomizingDatosGenerales");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'deleteExtractionPlanRange' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function deleteExtractionPlanRange(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("deleteExtractionPlanRange");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'createExtractionPlanCustomizing' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function createExtractionPlanCustomizing(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("createExtractionPlanCustomizing");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getExtractionPlanRanges' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getExtractionPlanRanges(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getExtractionPlanRanges");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'disableExtractionPlanCustomizings' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function disableExtractionPlanCustomizings(arg0:ArrayCollection, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("disableExtractionPlanCustomizings");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getUltimoExtractionPlanCustomizingEnviado' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getUltimoExtractionPlanCustomizingEnviado(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getUltimoExtractionPlanCustomizingEnviado");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'copyEPCFullToEdition' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function copyEPCFullToEdition(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("copyEPCFullToEdition");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
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
      * This method is a generated wrapper used to call the 'cleanExtractionPlanCutomizingDay' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function cleanExtractionPlanCutomizingDay(arg0:int, arg1:ArrayCollection, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("cleanExtractionPlanCutomizingDay");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getMetricTemplates' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getMetricTemplates(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getMetricTemplates");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getSensors' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getSensors(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getSensors");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getDetalleSolicitudes' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getDetalleSolicitudes(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getDetalleSolicitudes");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getExtractionPlanCustomizings' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getExtractionPlanCustomizings(arg0:int, arg1:String, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getExtractionPlanCustomizings");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getSituationTemplates' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getSituationTemplates(arg0:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getSituationTemplates");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getUltimoExtractionPlanCustomizingNoEnviado' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getUltimoExtractionPlanCustomizingNoEnviado(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getUltimoExtractionPlanCustomizingNoEnviado");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'saveExtractionPlanRange' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function saveExtractionPlanRange(arg0:int, arg1:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("saveExtractionPlanRange");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getExtractionPlanRange' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getExtractionPlanRange(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getExtractionPlanRange");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'copyEPCToEdition' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function copyEPCToEdition(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("copyEPCToEdition");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'copyCalendarActions' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function copyCalendarActions(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("copyCalendarActions");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getExtractionPlanRangeDetails' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getExtractionPlanRangeDetails(arg0:int, arg1:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getExtractionPlanRangeDetails");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'regenerateDetailForRange' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function regenerateDetailForRange(arg0:int, arg1:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("regenerateDetailForRange");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'getEvidenceProvidersStoreSituationTemplate' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function getEvidenceProvidersStoreSituationTemplate(arg0:int, arg1:int, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("getEvidenceProvidersStoreSituationTemplate");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'copyDayInDays' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function copyDayInDays(arg0:int, arg1:int, arg2:ArrayCollection, arg3:Boolean, arg4:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("copyDayInDays");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2,arg3,arg4) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'regenerateDetailForEPC' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function regenerateDetailForEPC(arg0:int, arg1:ArrayCollection, arg2:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("regenerateDetailForEPC");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2) ;
        return _internal_token;
    }
     
    /**
      * This method is a generated wrapper used to call the 'copyCalendar' operation. It returns an mx.rpc.AsyncToken whose 
      * result property will be populated with the result of the operation when the server response is received. 
      * To use this result from MXML code, define a CallResponder component and assign its token property to this method's return value. 
      * You can then bind to CallResponder.lastResult or listen for the CallResponder.result or fault events.
      *
      * @see mx.rpc.AsyncToken
      * @see mx.rpc.CallResponder 
      *
      * @return an mx.rpc.AsyncToken whose result property will be populated with the result of the operation when the server response is received.
      */
    public function copyCalendar(arg0:int, arg1:int, arg2:int, arg3:Number) : mx.rpc.AsyncToken
    {
        model_internal::loadWSDLIfNecessary();
        var _internal_operation:mx.rpc.AbstractOperation = _serviceControl.getOperation("copyCalendar");
		var _internal_token:mx.rpc.AsyncToken = _internal_operation.send(arg0,arg1,arg2,arg3) ;
        return _internal_token;
    }
     
}

}
