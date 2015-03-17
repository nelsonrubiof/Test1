/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - MetricResultDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
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
public class _Super_MetricResultDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _MetricResultDTOEntityMetadata;
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
    private var _internal_descriptionOperator : String;
    private var _internal_evidencePrePath : String;
    private var _internal_evidences : ArrayCollection;
    model_internal var _internal_evidences_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
    private var _internal_metricId : int;
    private var _internal_metricName : String;
    private var _internal_metricResult : int;
    private var _internal_metricTemplateId : int;
    private var _internal_metricType : String;
    private var _internal_observedMetricId : int;
    private var _internal_proofPrePath : String;
    private var _internal_situtionTemplateName : String;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_MetricResultDTO()
    {
        _model = new _MetricResultDTOEntityMetadata(this);

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
    public function get descriptionOperator() : String
    {
        return _internal_descriptionOperator;
    }

    [Bindable(event="propertyChange")]
    public function get evidencePrePath() : String
    {
        return _internal_evidencePrePath;
    }

    [Bindable(event="propertyChange")]
    public function get evidences() : ArrayCollection
    {
        return _internal_evidences;
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
    public function get metricResult() : int
    {
        return _internal_metricResult;
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
    public function get situtionTemplateName() : String
    {
        return _internal_situtionTemplateName;
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

    public function set descriptionOperator(value:String) : void
    {
        var oldValue:String = _internal_descriptionOperator;
        if (oldValue !== value)
        {
            _internal_descriptionOperator = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "descriptionOperator", oldValue, _internal_descriptionOperator));
        }
    }

    public function set evidencePrePath(value:String) : void
    {
        var oldValue:String = _internal_evidencePrePath;
        if (oldValue !== value)
        {
            _internal_evidencePrePath = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidencePrePath", oldValue, _internal_evidencePrePath));
        }
    }

    public function set evidences(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_evidences;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_evidences = value;
            }
            else if (value is Array)
            {
                _internal_evidences = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_evidences = null;
            }
            else
            {
                throw new Error("value of evidences must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidences", oldValue, _internal_evidences));
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

    public function set metricResult(value:int) : void
    {
        var oldValue:int = _internal_metricResult;
        if (oldValue !== value)
        {
            _internal_metricResult = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "metricResult", oldValue, _internal_metricResult));
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

    public function set situtionTemplateName(value:String) : void
    {
        var oldValue:String = _internal_situtionTemplateName;
        if (oldValue !== value)
        {
            _internal_situtionTemplateName = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "situtionTemplateName", oldValue, _internal_situtionTemplateName));
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
    public function get _model() : _MetricResultDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _MetricResultDTOEntityMetadata) : void
    {
        var oldValue : _MetricResultDTOEntityMetadata = model_internal::_dminternal_model;
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
