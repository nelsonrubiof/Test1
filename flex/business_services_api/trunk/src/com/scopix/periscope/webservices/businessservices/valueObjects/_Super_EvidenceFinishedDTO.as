/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - EvidenceFinishedDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO;
import flash.events.EventDispatcher;
import mx.collections.ArrayCollection;
import mx.events.PropertyChangeEvent;

import flash.net.registerClassAlias;
import flash.net.getClassByAlias;
import com.adobe.fiber.core.model_internal;
import com.adobe.fiber.valueobjects.IPropertyIterator;
import com.adobe.fiber.valueobjects.AvailablePropertyIterator;

use namespace model_internal;

[ExcludeClass]
public class _Super_EvidenceFinishedDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _EvidenceFinishedDTOEntityMetadata;
    model_internal var _changedObjects:mx.collections.ArrayCollection = new ArrayCollection();

    public function getChangedObjects() : Array
    {
        _changedObjects.addItemAt(this,0);
        return _changedObjects.source;
    }

    public function clearChangedObjects() : void
    {
        _changedObjects.removeAll();
    }

    /**
     * properties
     */
    private var _internal_area : String;
    private var _internal_areaId : int;
    private var _internal_camera : String;
    private var _internal_cantDoReason : String;
    private var _internal_endEvaluationDate : Date;
    private var _internal_evaluationDate : Date;
    private var _internal_evaluationInstruction : String;
    private var _internal_evaluationTimeInSeconds : Number;
    private var _internal_evidenceDate : Date;
    private var _internal_evidenceEvaluationId : int;
    private var _internal_evidenceEvaluationResult : int;
    private var _internal_evidenceId : int;
    private var _internal_evidencePath : String;
    private var _internal_evidenceType : String;
    private var _internal_flvPath : String;
    private var _internal_initEvaluationDate : Date;
    private var _internal_metricId : int;
    private var _internal_metricName : String;
    private var _internal_metricTemplateId : int;
    private var _internal_metricType : String;
    private var _internal_observedMetricId : int;
    private var _internal_proofPrePath : String;
    private var _internal_proofs : ArrayCollection;
    model_internal var _internal_proofs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO;
    private var _internal_provider : String;
    private var _internal_rejected : Boolean;
    private var _internal_situationId : int;
    private var _internal_storeName : String;
    private var _internal_totalMeasuredByCamera : int;
    private var _internal_userName : String;
    private var _internal_yesNoType : Boolean;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_EvidenceFinishedDTO()
    {
        _model = new _EvidenceFinishedDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get area() : String
    {
        return _internal_area;
    }

    [Bindable(event="propertyChange")]
    public function get areaId() : int
    {
        return _internal_areaId;
    }

    [Bindable(event="propertyChange")]
    public function get camera() : String
    {
        return _internal_camera;
    }

    [Bindable(event="propertyChange")]
    public function get cantDoReason() : String
    {
        return _internal_cantDoReason;
    }

    [Bindable(event="propertyChange")]
    public function get endEvaluationDate() : Date
    {
        return _internal_endEvaluationDate;
    }

    [Bindable(event="propertyChange")]
    public function get evaluationDate() : Date
    {
        return _internal_evaluationDate;
    }

    [Bindable(event="propertyChange")]
    public function get evaluationInstruction() : String
    {
        return _internal_evaluationInstruction;
    }

    [Bindable(event="propertyChange")]
    public function get evaluationTimeInSeconds() : Number
    {
        return _internal_evaluationTimeInSeconds;
    }

    [Bindable(event="propertyChange")]
    public function get evidenceDate() : Date
    {
        return _internal_evidenceDate;
    }

    [Bindable(event="propertyChange")]
    public function get evidenceEvaluationId() : int
    {
        return _internal_evidenceEvaluationId;
    }

    [Bindable(event="propertyChange")]
    public function get evidenceEvaluationResult() : int
    {
        return _internal_evidenceEvaluationResult;
    }

    [Bindable(event="propertyChange")]
    public function get evidenceId() : int
    {
        return _internal_evidenceId;
    }

    [Bindable(event="propertyChange")]
    public function get evidencePath() : String
    {
        return _internal_evidencePath;
    }

    [Bindable(event="propertyChange")]
    public function get evidenceType() : String
    {
        return _internal_evidenceType;
    }

    [Bindable(event="propertyChange")]
    public function get flvPath() : String
    {
        return _internal_flvPath;
    }

    [Bindable(event="propertyChange")]
    public function get initEvaluationDate() : Date
    {
        return _internal_initEvaluationDate;
    }

    [Bindable(event="propertyChange")]
    public function get metricId() : int
    {
        return _internal_metricId;
    }

    [Bindable(event="propertyChange")]
    public function get metricName() : String
    {
        return _internal_metricName;
    }

    [Bindable(event="propertyChange")]
    public function get metricTemplateId() : int
    {
        return _internal_metricTemplateId;
    }

    [Bindable(event="propertyChange")]
    public function get metricType() : String
    {
        return _internal_metricType;
    }

    [Bindable(event="propertyChange")]
    public function get observedMetricId() : int
    {
        return _internal_observedMetricId;
    }

    [Bindable(event="propertyChange")]
    public function get proofPrePath() : String
    {
        return _internal_proofPrePath;
    }

    [Bindable(event="propertyChange")]
    public function get proofs() : ArrayCollection
    {
        return _internal_proofs;
    }

    [Bindable(event="propertyChange")]
    public function get provider() : String
    {
        return _internal_provider;
    }

    [Bindable(event="propertyChange")]
    public function get rejected() : Boolean
    {
        return _internal_rejected;
    }

    [Bindable(event="propertyChange")]
    public function get situationId() : int
    {
        return _internal_situationId;
    }

    [Bindable(event="propertyChange")]
    public function get storeName() : String
    {
        return _internal_storeName;
    }

    [Bindable(event="propertyChange")]
    public function get totalMeasuredByCamera() : int
    {
        return _internal_totalMeasuredByCamera;
    }

    [Bindable(event="propertyChange")]
    public function get userName() : String
    {
        return _internal_userName;
    }

    [Bindable(event="propertyChange")]
    public function get yesNoType() : Boolean
    {
        return _internal_yesNoType;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set area(value:String) : void
    {
        var oldValue:String = _internal_area;
        if (oldValue !== value)
        {
            _internal_area = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "area", oldValue, _internal_area));
        }
    }

    public function set areaId(value:int) : void
    {
        var oldValue:int = _internal_areaId;
        if (oldValue !== value)
        {
            _internal_areaId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "areaId", oldValue, _internal_areaId));
        }
    }

    public function set camera(value:String) : void
    {
        var oldValue:String = _internal_camera;
        if (oldValue !== value)
        {
            _internal_camera = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "camera", oldValue, _internal_camera));
        }
    }

    public function set cantDoReason(value:String) : void
    {
        var oldValue:String = _internal_cantDoReason;
        if (oldValue !== value)
        {
            _internal_cantDoReason = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "cantDoReason", oldValue, _internal_cantDoReason));
        }
    }

    public function set endEvaluationDate(value:Date) : void
    {
        var oldValue:Date = _internal_endEvaluationDate;
        if (oldValue !== value)
        {
            _internal_endEvaluationDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "endEvaluationDate", oldValue, _internal_endEvaluationDate));
        }
    }

    public function set evaluationDate(value:Date) : void
    {
        var oldValue:Date = _internal_evaluationDate;
        if (oldValue !== value)
        {
            _internal_evaluationDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evaluationDate", oldValue, _internal_evaluationDate));
        }
    }

    public function set evaluationInstruction(value:String) : void
    {
        var oldValue:String = _internal_evaluationInstruction;
        if (oldValue !== value)
        {
            _internal_evaluationInstruction = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evaluationInstruction", oldValue, _internal_evaluationInstruction));
        }
    }

    public function set evaluationTimeInSeconds(value:Number) : void
    {
        var oldValue:Number = _internal_evaluationTimeInSeconds;
        if (oldValue !== value)
        {
            _internal_evaluationTimeInSeconds = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evaluationTimeInSeconds", oldValue, _internal_evaluationTimeInSeconds));
        }
    }

    public function set evidenceDate(value:Date) : void
    {
        var oldValue:Date = _internal_evidenceDate;
        if (oldValue !== value)
        {
            _internal_evidenceDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceDate", oldValue, _internal_evidenceDate));
        }
    }

    public function set evidenceEvaluationId(value:int) : void
    {
        var oldValue:int = _internal_evidenceEvaluationId;
        if (oldValue !== value)
        {
            _internal_evidenceEvaluationId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceEvaluationId", oldValue, _internal_evidenceEvaluationId));
        }
    }

    public function set evidenceEvaluationResult(value:int) : void
    {
        var oldValue:int = _internal_evidenceEvaluationResult;
        if (oldValue !== value)
        {
            _internal_evidenceEvaluationResult = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceEvaluationResult", oldValue, _internal_evidenceEvaluationResult));
        }
    }

    public function set evidenceId(value:int) : void
    {
        var oldValue:int = _internal_evidenceId;
        if (oldValue !== value)
        {
            _internal_evidenceId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceId", oldValue, _internal_evidenceId));
        }
    }

    public function set evidencePath(value:String) : void
    {
        var oldValue:String = _internal_evidencePath;
        if (oldValue !== value)
        {
            _internal_evidencePath = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidencePath", oldValue, _internal_evidencePath));
        }
    }

    public function set evidenceType(value:String) : void
    {
        var oldValue:String = _internal_evidenceType;
        if (oldValue !== value)
        {
            _internal_evidenceType = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceType", oldValue, _internal_evidenceType));
        }
    }

    public function set flvPath(value:String) : void
    {
        var oldValue:String = _internal_flvPath;
        if (oldValue !== value)
        {
            _internal_flvPath = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "flvPath", oldValue, _internal_flvPath));
        }
    }

    public function set initEvaluationDate(value:Date) : void
    {
        var oldValue:Date = _internal_initEvaluationDate;
        if (oldValue !== value)
        {
            _internal_initEvaluationDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "initEvaluationDate", oldValue, _internal_initEvaluationDate));
        }
    }

    public function set metricId(value:int) : void
    {
        var oldValue:int = _internal_metricId;
        if (oldValue !== value)
        {
            _internal_metricId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "metricId", oldValue, _internal_metricId));
        }
    }

    public function set metricName(value:String) : void
    {
        var oldValue:String = _internal_metricName;
        if (oldValue !== value)
        {
            _internal_metricName = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "metricName", oldValue, _internal_metricName));
        }
    }

    public function set metricTemplateId(value:int) : void
    {
        var oldValue:int = _internal_metricTemplateId;
        if (oldValue !== value)
        {
            _internal_metricTemplateId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "metricTemplateId", oldValue, _internal_metricTemplateId));
        }
    }

    public function set metricType(value:String) : void
    {
        var oldValue:String = _internal_metricType;
        if (oldValue !== value)
        {
            _internal_metricType = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "metricType", oldValue, _internal_metricType));
        }
    }

    public function set observedMetricId(value:int) : void
    {
        var oldValue:int = _internal_observedMetricId;
        if (oldValue !== value)
        {
            _internal_observedMetricId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "observedMetricId", oldValue, _internal_observedMetricId));
        }
    }

    public function set proofPrePath(value:String) : void
    {
        var oldValue:String = _internal_proofPrePath;
        if (oldValue !== value)
        {
            _internal_proofPrePath = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "proofPrePath", oldValue, _internal_proofPrePath));
        }
    }

    public function set proofs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_proofs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_proofs = value;
            }
            else if (value is Array)
            {
                _internal_proofs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_proofs = null;
            }
            else
            {
                throw new Error("value of proofs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "proofs", oldValue, _internal_proofs));
        }
    }

    public function set provider(value:String) : void
    {
        var oldValue:String = _internal_provider;
        if (oldValue !== value)
        {
            _internal_provider = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "provider", oldValue, _internal_provider));
        }
    }

    public function set rejected(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_rejected;
        if (oldValue !== value)
        {
            _internal_rejected = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "rejected", oldValue, _internal_rejected));
        }
    }

    public function set situationId(value:int) : void
    {
        var oldValue:int = _internal_situationId;
        if (oldValue !== value)
        {
            _internal_situationId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "situationId", oldValue, _internal_situationId));
        }
    }

    public function set storeName(value:String) : void
    {
        var oldValue:String = _internal_storeName;
        if (oldValue !== value)
        {
            _internal_storeName = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "storeName", oldValue, _internal_storeName));
        }
    }

    public function set totalMeasuredByCamera(value:int) : void
    {
        var oldValue:int = _internal_totalMeasuredByCamera;
        if (oldValue !== value)
        {
            _internal_totalMeasuredByCamera = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "totalMeasuredByCamera", oldValue, _internal_totalMeasuredByCamera));
        }
    }

    public function set userName(value:String) : void
    {
        var oldValue:String = _internal_userName;
        if (oldValue !== value)
        {
            _internal_userName = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "userName", oldValue, _internal_userName));
        }
    }

    public function set yesNoType(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_yesNoType;
        if (oldValue !== value)
        {
            _internal_yesNoType = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "yesNoType", oldValue, _internal_yesNoType));
        }
    }

    /**
     * Data/source property setter listeners
     *
     * Each data property whose value affects other properties or the validity of the entity
     * needs to invalidate all previously calculated artifacts. These include:
     *  - any derived properties or constraints that reference the given data property.
     *  - any availability guards (variant expressions) that reference the given data property.
     *  - any style validations, message tokens or guards that reference the given data property.
     *  - the validity of the property (and the containing entity) if the given data property has a length restriction.
     *  - the validity of the property (and the containing entity) if the given data property is required.
     */


    /**
     * valid related derived properties
     */
    model_internal var _isValid : Boolean;
    model_internal var _invalidConstraints:Array = new Array();
    model_internal var _validationFailureMessages:Array = new Array();

    /**
     * derived property calculators
     */

    /**
     * isValid calculator
     */
    model_internal function calculateIsValid():Boolean
    {
        var violatedConsts:Array = new Array();
        var validationFailureMessages:Array = new Array();

        var propertyValidity:Boolean = true;

        model_internal::_cacheInitialized_isValid = true;
        model_internal::invalidConstraints_der = violatedConsts;
        model_internal::validationFailureMessages_der = validationFailureMessages;
        return violatedConsts.length == 0 && propertyValidity;
    }

    /**
     * derived property setters
     */

    model_internal function set isValid_der(value:Boolean) : void
    {
        var oldValue:Boolean = model_internal::_isValid;
        if (oldValue !== value)
        {
            model_internal::_isValid = value;
            _model.model_internal::fireChangeEvent("isValid", oldValue, model_internal::_isValid);
        }
    }

    /**
     * derived property getters
     */

    [Transient]
    [Bindable(event="propertyChange")]
    public function get _model() : _EvidenceFinishedDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _EvidenceFinishedDTOEntityMetadata) : void
    {
        var oldValue : _EvidenceFinishedDTOEntityMetadata = model_internal::_dminternal_model;
        if (oldValue !== value)
        {
            model_internal::_dminternal_model = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "_model", oldValue, model_internal::_dminternal_model));
        }
    }

    /**
     * methods
     */


    /**
     *  services
     */
    private var _managingService:com.adobe.fiber.services.IFiberManagingService;

    public function set managingService(managingService:com.adobe.fiber.services.IFiberManagingService):void
    {
        _managingService = managingService;
    }

    model_internal function set invalidConstraints_der(value:Array) : void
    {
        var oldValue:Array = model_internal::_invalidConstraints;
        // avoid firing the event when old and new value are different empty arrays
        if (oldValue !== value && (oldValue.length > 0 || value.length > 0))
        {
            model_internal::_invalidConstraints = value;
            _model.model_internal::fireChangeEvent("invalidConstraints", oldValue, model_internal::_invalidConstraints);
        }
    }

    model_internal function set validationFailureMessages_der(value:Array) : void
    {
        var oldValue:Array = model_internal::_validationFailureMessages;
        // avoid firing the event when old and new value are different empty arrays
        if (oldValue !== value && (oldValue.length > 0 || value.length > 0))
        {
            model_internal::_validationFailureMessages = value;
            _model.model_internal::fireChangeEvent("validationFailureMessages", oldValue, model_internal::_validationFailureMessages);
        }
    }


}

}
