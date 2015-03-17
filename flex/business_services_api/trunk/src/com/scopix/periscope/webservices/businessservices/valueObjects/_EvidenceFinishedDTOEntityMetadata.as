
/**
 * This is a generated class and is not intended for modification.  
 */
package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.styles.IStyle;
import com.adobe.fiber.styles.Style;
import com.adobe.fiber.valueobjects.AbstractEntityMetadata;
import com.adobe.fiber.valueobjects.AvailablePropertyIterator;
import com.adobe.fiber.valueobjects.IPropertyIterator;
import com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO;
import mx.collections.ArrayCollection;
import com.adobe.fiber.core.model_internal;
import com.adobe.fiber.valueobjects.IModelType;
import mx.events.PropertyChangeEvent;

use namespace model_internal;

[ExcludeClass]
internal class _EvidenceFinishedDTOEntityMetadata extends com.adobe.fiber.valueobjects.AbstractEntityMetadata
{
    private static var emptyArray:Array = new Array();

    model_internal static var allProperties:Array = new Array("area", "areaId", "camera", "cantDoReason", "endEvaluationDate", "evaluationDate", "evaluationInstruction", "evaluationTimeInSeconds", "evidenceDate", "evidenceEvaluationId", "evidenceEvaluationResult", "evidenceId", "evidencePath", "evidenceType", "flvPath", "initEvaluationDate", "metricId", "metricName", "metricTemplateId", "metricType", "observedMetricId", "proofPrePath", "proofs", "provider", "rejected", "situationId", "storeName", "totalMeasuredByCamera", "userName", "yesNoType");
    model_internal static var allAssociationProperties:Array = new Array();
    model_internal static var allRequiredProperties:Array = new Array();
    model_internal static var allAlwaysAvailableProperties:Array = new Array("area", "areaId", "camera", "cantDoReason", "endEvaluationDate", "evaluationDate", "evaluationInstruction", "evaluationTimeInSeconds", "evidenceDate", "evidenceEvaluationId", "evidenceEvaluationResult", "evidenceId", "evidencePath", "evidenceType", "flvPath", "initEvaluationDate", "metricId", "metricName", "metricTemplateId", "metricType", "observedMetricId", "proofPrePath", "proofs", "provider", "rejected", "situationId", "storeName", "totalMeasuredByCamera", "userName", "yesNoType");
    model_internal static var guardedProperties:Array = new Array();
    model_internal static var dataProperties:Array = new Array("area", "areaId", "camera", "cantDoReason", "endEvaluationDate", "evaluationDate", "evaluationInstruction", "evaluationTimeInSeconds", "evidenceDate", "evidenceEvaluationId", "evidenceEvaluationResult", "evidenceId", "evidencePath", "evidenceType", "flvPath", "initEvaluationDate", "metricId", "metricName", "metricTemplateId", "metricType", "observedMetricId", "proofPrePath", "proofs", "provider", "rejected", "situationId", "storeName", "totalMeasuredByCamera", "userName", "yesNoType");
    model_internal static var sourceProperties:Array = emptyArray
    model_internal static var nonDerivedProperties:Array = new Array("area", "areaId", "camera", "cantDoReason", "endEvaluationDate", "evaluationDate", "evaluationInstruction", "evaluationTimeInSeconds", "evidenceDate", "evidenceEvaluationId", "evidenceEvaluationResult", "evidenceId", "evidencePath", "evidenceType", "flvPath", "initEvaluationDate", "metricId", "metricName", "metricTemplateId", "metricType", "observedMetricId", "proofPrePath", "proofs", "provider", "rejected", "situationId", "storeName", "totalMeasuredByCamera", "userName", "yesNoType");
    model_internal static var derivedProperties:Array = new Array();
    model_internal static var collectionProperties:Array = new Array("proofs");
    model_internal static var collectionBaseMap:Object;
    model_internal static var entityName:String = "EvidenceFinishedDTO";
    model_internal static var dependentsOnMap:Object;
    model_internal static var dependedOnServices:Array = new Array();
    model_internal static var propertyTypeMap:Object;


    model_internal var _instance:_Super_EvidenceFinishedDTO;
    model_internal static var _nullStyle:com.adobe.fiber.styles.Style = new com.adobe.fiber.styles.Style();

    public function _EvidenceFinishedDTOEntityMetadata(value : _Super_EvidenceFinishedDTO)
    {
        // initialize property maps
        if (model_internal::dependentsOnMap == null)
        {
            // dependents map
            model_internal::dependentsOnMap = new Object();
            model_internal::dependentsOnMap["area"] = new Array();
            model_internal::dependentsOnMap["areaId"] = new Array();
            model_internal::dependentsOnMap["camera"] = new Array();
            model_internal::dependentsOnMap["cantDoReason"] = new Array();
            model_internal::dependentsOnMap["endEvaluationDate"] = new Array();
            model_internal::dependentsOnMap["evaluationDate"] = new Array();
            model_internal::dependentsOnMap["evaluationInstruction"] = new Array();
            model_internal::dependentsOnMap["evaluationTimeInSeconds"] = new Array();
            model_internal::dependentsOnMap["evidenceDate"] = new Array();
            model_internal::dependentsOnMap["evidenceEvaluationId"] = new Array();
            model_internal::dependentsOnMap["evidenceEvaluationResult"] = new Array();
            model_internal::dependentsOnMap["evidenceId"] = new Array();
            model_internal::dependentsOnMap["evidencePath"] = new Array();
            model_internal::dependentsOnMap["evidenceType"] = new Array();
            model_internal::dependentsOnMap["flvPath"] = new Array();
            model_internal::dependentsOnMap["initEvaluationDate"] = new Array();
            model_internal::dependentsOnMap["metricId"] = new Array();
            model_internal::dependentsOnMap["metricName"] = new Array();
            model_internal::dependentsOnMap["metricTemplateId"] = new Array();
            model_internal::dependentsOnMap["metricType"] = new Array();
            model_internal::dependentsOnMap["observedMetricId"] = new Array();
            model_internal::dependentsOnMap["proofPrePath"] = new Array();
            model_internal::dependentsOnMap["proofs"] = new Array();
            model_internal::dependentsOnMap["provider"] = new Array();
            model_internal::dependentsOnMap["rejected"] = new Array();
            model_internal::dependentsOnMap["situationId"] = new Array();
            model_internal::dependentsOnMap["storeName"] = new Array();
            model_internal::dependentsOnMap["totalMeasuredByCamera"] = new Array();
            model_internal::dependentsOnMap["userName"] = new Array();
            model_internal::dependentsOnMap["yesNoType"] = new Array();

            // collection base map
            model_internal::collectionBaseMap = new Object();
            model_internal::collectionBaseMap["proofs"] = "com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO";
        }

        // Property type Map
        model_internal::propertyTypeMap = new Object();
        model_internal::propertyTypeMap["area"] = "String";
        model_internal::propertyTypeMap["areaId"] = "int";
        model_internal::propertyTypeMap["camera"] = "String";
        model_internal::propertyTypeMap["cantDoReason"] = "String";
        model_internal::propertyTypeMap["endEvaluationDate"] = "Date";
        model_internal::propertyTypeMap["evaluationDate"] = "Date";
        model_internal::propertyTypeMap["evaluationInstruction"] = "String";
        model_internal::propertyTypeMap["evaluationTimeInSeconds"] = "Number";
        model_internal::propertyTypeMap["evidenceDate"] = "Date";
        model_internal::propertyTypeMap["evidenceEvaluationId"] = "int";
        model_internal::propertyTypeMap["evidenceEvaluationResult"] = "int";
        model_internal::propertyTypeMap["evidenceId"] = "int";
        model_internal::propertyTypeMap["evidencePath"] = "String";
        model_internal::propertyTypeMap["evidenceType"] = "String";
        model_internal::propertyTypeMap["flvPath"] = "String";
        model_internal::propertyTypeMap["initEvaluationDate"] = "Date";
        model_internal::propertyTypeMap["metricId"] = "int";
        model_internal::propertyTypeMap["metricName"] = "String";
        model_internal::propertyTypeMap["metricTemplateId"] = "int";
        model_internal::propertyTypeMap["metricType"] = "String";
        model_internal::propertyTypeMap["observedMetricId"] = "int";
        model_internal::propertyTypeMap["proofPrePath"] = "String";
        model_internal::propertyTypeMap["proofs"] = "ArrayCollection";
        model_internal::propertyTypeMap["provider"] = "String";
        model_internal::propertyTypeMap["rejected"] = "Boolean";
        model_internal::propertyTypeMap["situationId"] = "int";
        model_internal::propertyTypeMap["storeName"] = "String";
        model_internal::propertyTypeMap["totalMeasuredByCamera"] = "int";
        model_internal::propertyTypeMap["userName"] = "String";
        model_internal::propertyTypeMap["yesNoType"] = "Boolean";

        model_internal::_instance = value;
    }

    override public function getEntityName():String
    {
        return model_internal::entityName;
    }

    override public function getProperties():Array
    {
        return model_internal::allProperties;
    }

    override public function getAssociationProperties():Array
    {
        return model_internal::allAssociationProperties;
    }

    override public function getRequiredProperties():Array
    {
         return model_internal::allRequiredProperties;   
    }

    override public function getDataProperties():Array
    {
        return model_internal::dataProperties;
    }

    public function getSourceProperties():Array
    {
        return model_internal::sourceProperties;
    }

    public function getNonDerivedProperties():Array
    {
        return model_internal::nonDerivedProperties;
    }

    override public function getGuardedProperties():Array
    {
        return model_internal::guardedProperties;
    }

    override public function getUnguardedProperties():Array
    {
        return model_internal::allAlwaysAvailableProperties;
    }

    override public function getDependants(propertyName:String):Array
    {
       if (model_internal::nonDerivedProperties.indexOf(propertyName) == -1)
            throw new Error(propertyName + " is not a data property of entity EvidenceFinishedDTO");
            
       return model_internal::dependentsOnMap[propertyName] as Array;  
    }

    override public function getDependedOnServices():Array
    {
        return model_internal::dependedOnServices;
    }

    override public function getCollectionProperties():Array
    {
        return model_internal::collectionProperties;
    }

    override public function getCollectionBase(propertyName:String):String
    {
        if (model_internal::collectionProperties.indexOf(propertyName) == -1)
            throw new Error(propertyName + " is not a collection property of entity EvidenceFinishedDTO");

        return model_internal::collectionBaseMap[propertyName];
    }
    
    override public function getPropertyType(propertyName:String):String
    {
        if (model_internal::allProperties.indexOf(propertyName) == -1)
            throw new Error(propertyName + " is not a property of EvidenceFinishedDTO");

        return model_internal::propertyTypeMap[propertyName];
    }

    override public function getAvailableProperties():com.adobe.fiber.valueobjects.IPropertyIterator
    {
        return new com.adobe.fiber.valueobjects.AvailablePropertyIterator(this);
    }

    override public function getValue(propertyName:String):*
    {
        if (model_internal::allProperties.indexOf(propertyName) == -1)
        {
            throw new Error(propertyName + " does not exist for entity EvidenceFinishedDTO");
        }

        return model_internal::_instance[propertyName];
    }

    override public function setValue(propertyName:String, value:*):void
    {
        if (model_internal::nonDerivedProperties.indexOf(propertyName) == -1)
        {
            throw new Error(propertyName + " is not a modifiable property of entity EvidenceFinishedDTO");
        }

        model_internal::_instance[propertyName] = value;
    }

    override public function getMappedByProperty(associationProperty:String):String
    {
        switch(associationProperty)
        {
            default:
            {
                return null;
            }
        }
    }

    override public function getPropertyLength(propertyName:String):int
    {
        switch(propertyName)
        {
            default:
            {
                return 0;
            }
        }
    }

    override public function isAvailable(propertyName:String):Boolean
    {
        if (model_internal::allProperties.indexOf(propertyName) == -1)
        {
            throw new Error(propertyName + " does not exist for entity EvidenceFinishedDTO");
        }

        if (model_internal::allAlwaysAvailableProperties.indexOf(propertyName) != -1)
        {
            return true;
        }

        switch(propertyName)
        {
            default:
            {
                return true;
            }
        }
    }

    override public function getIdentityMap():Object
    {
        var returnMap:Object = new Object();

        return returnMap;
    }

    [Bindable(event="propertyChange")]
    override public function get invalidConstraints():Array
    {
        if (model_internal::_instance.model_internal::_cacheInitialized_isValid)
        {
            return model_internal::_instance.model_internal::_invalidConstraints;
        }
        else
        {
            // recalculate isValid
            model_internal::_instance.model_internal::_isValid = model_internal::_instance.model_internal::calculateIsValid();
            return model_internal::_instance.model_internal::_invalidConstraints;        
        }
    }

    [Bindable(event="propertyChange")]
    override public function get validationFailureMessages():Array
    {
        if (model_internal::_instance.model_internal::_cacheInitialized_isValid)
        {
            return model_internal::_instance.model_internal::_validationFailureMessages;
        }
        else
        {
            // recalculate isValid
            model_internal::_instance.model_internal::_isValid = model_internal::_instance.model_internal::calculateIsValid();
            return model_internal::_instance.model_internal::_validationFailureMessages;
        }
    }

    override public function getDependantInvalidConstraints(propertyName:String):Array
    {
        var dependants:Array = getDependants(propertyName);
        if (dependants.length == 0)
        {
            return emptyArray;
        }

        var currentlyInvalid:Array = invalidConstraints;
        if (currentlyInvalid.length == 0)
        {
            return emptyArray;
        }

        var filterFunc:Function = function(element:*, index:int, arr:Array):Boolean
        {
            return dependants.indexOf(element) > -1;
        }

        return currentlyInvalid.filter(filterFunc);
    }

    /**
     * isValid
     */
    [Bindable(event="propertyChange")] 
    public function get isValid() : Boolean
    {
        if (model_internal::_instance.model_internal::_cacheInitialized_isValid)
        {
            return model_internal::_instance.model_internal::_isValid;
        }
        else
        {
            // recalculate isValid
            model_internal::_instance.model_internal::_isValid = model_internal::_instance.model_internal::calculateIsValid();
            return model_internal::_instance.model_internal::_isValid;
        }
    }

    [Bindable(event="propertyChange")]
    public function get isAreaAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isAreaIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isCameraAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isCantDoReasonAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEndEvaluationDateAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvaluationDateAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvaluationInstructionAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvaluationTimeInSecondsAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvidenceDateAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvidenceEvaluationIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvidenceEvaluationResultAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvidenceIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvidencePathAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isEvidenceTypeAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isFlvPathAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isInitEvaluationDateAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isMetricIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isMetricNameAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isMetricTemplateIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isMetricTypeAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isObservedMetricIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isProofPrePathAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isProofsAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isProviderAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isRejectedAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isSituationIdAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isStoreNameAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isTotalMeasuredByCameraAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isUserNameAvailable():Boolean
    {
        return true;
    }

    [Bindable(event="propertyChange")]
    public function get isYesNoTypeAvailable():Boolean
    {
        return true;
    }


    /**
     * derived property recalculation
     */

    model_internal function fireChangeEvent(propertyName:String, oldValue:Object, newValue:Object):void
    {
        this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, propertyName, oldValue, newValue));
    }

    [Bindable(event="propertyChange")]   
    public function get areaStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get areaIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get cameraStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get cantDoReasonStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get endEvaluationDateStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evaluationDateStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evaluationInstructionStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evaluationTimeInSecondsStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evidenceDateStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evidenceEvaluationIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evidenceEvaluationResultStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evidenceIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evidencePathStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get evidenceTypeStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get flvPathStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get initEvaluationDateStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get metricIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get metricNameStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get metricTemplateIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get metricTypeStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get observedMetricIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get proofPrePathStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get proofsStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get providerStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get rejectedStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get situationIdStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get storeNameStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get totalMeasuredByCameraStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get userNameStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }

    [Bindable(event="propertyChange")]   
    public function get yesNoTypeStyle():com.adobe.fiber.styles.Style
    {
        return model_internal::_nullStyle;
    }


     /**
     * 
     * @inheritDoc 
     */ 
     override public function getStyle(propertyName:String):com.adobe.fiber.styles.IStyle
     {
         switch(propertyName)
         {
            default:
            {
                return null;
            }
         }
     }
     
     /**
     * 
     * @inheritDoc 
     *  
     */  
     override public function getPropertyValidationFailureMessages(propertyName:String):Array
     {
         switch(propertyName)
         {
            default:
            {
                return emptyArray;
            }
         }
     }

}

}
