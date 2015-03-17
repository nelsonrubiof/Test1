/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - SituationRequestDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderRequestDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.MetricRequestDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestRangeDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO;
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
public class _Super_SituationRequestDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.MetricRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestRangeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationExtractionRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _SituationRequestDTOEntityMetadata;
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
    private var _internal_duration : int;
    private var _internal_evidenceProviderRequestDTOs : ArrayCollection;
    model_internal var _internal_evidenceProviderRequestDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderRequestDTO;
    private var _internal_frecuency : int;
    private var _internal_metricRequestDTOs : ArrayCollection;
    model_internal var _internal_metricRequestDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.MetricRequestDTO;
    private var _internal_priorization : int;
    private var _internal_randonCamera : Boolean;
    private var _internal_situationRequestRangeDTOs : ArrayCollection;
    model_internal var _internal_situationRequestRangeDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestRangeDTO;
    private var _internal_situationSensors : ArrayCollection;
    model_internal var _internal_situationSensors_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO;
    private var _internal_situationTemplateId : int;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_SituationRequestDTO()
    {
        _model = new _SituationRequestDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get duration() : int
    {
        return _internal_duration;
    }

    [Bindable(event="propertyChange")]
    public function get evidenceProviderRequestDTOs() : ArrayCollection
    {
        return _internal_evidenceProviderRequestDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get frecuency() : int
    {
        return _internal_frecuency;
    }

    [Bindable(event="propertyChange")]
    public function get metricRequestDTOs() : ArrayCollection
    {
        return _internal_metricRequestDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get priorization() : int
    {
        return _internal_priorization;
    }

    [Bindable(event="propertyChange")]
    public function get randonCamera() : Boolean
    {
        return _internal_randonCamera;
    }

    [Bindable(event="propertyChange")]
    public function get situationRequestRangeDTOs() : ArrayCollection
    {
        return _internal_situationRequestRangeDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get situationSensors() : ArrayCollection
    {
        return _internal_situationSensors;
    }

    [Bindable(event="propertyChange")]
    public function get situationTemplateId() : int
    {
        return _internal_situationTemplateId;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set duration(value:int) : void
    {
        var oldValue:int = _internal_duration;
        if (oldValue !== value)
        {
            _internal_duration = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "duration", oldValue, _internal_duration));
        }
    }

    public function set evidenceProviderRequestDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_evidenceProviderRequestDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_evidenceProviderRequestDTOs = value;
            }
            else if (value is Array)
            {
                _internal_evidenceProviderRequestDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_evidenceProviderRequestDTOs = null;
            }
            else
            {
                throw new Error("value of evidenceProviderRequestDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceProviderRequestDTOs", oldValue, _internal_evidenceProviderRequestDTOs));
        }
    }

    public function set frecuency(value:int) : void
    {
        var oldValue:int = _internal_frecuency;
        if (oldValue !== value)
        {
            _internal_frecuency = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "frecuency", oldValue, _internal_frecuency));
        }
    }

    public function set metricRequestDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_metricRequestDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_metricRequestDTOs = value;
            }
            else if (value is Array)
            {
                _internal_metricRequestDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_metricRequestDTOs = null;
            }
            else
            {
                throw new Error("value of metricRequestDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "metricRequestDTOs", oldValue, _internal_metricRequestDTOs));
        }
    }

    public function set priorization(value:int) : void
    {
        var oldValue:int = _internal_priorization;
        if (oldValue !== value)
        {
            _internal_priorization = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "priorization", oldValue, _internal_priorization));
        }
    }

    public function set randonCamera(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_randonCamera;
        if (oldValue !== value)
        {
            _internal_randonCamera = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "randonCamera", oldValue, _internal_randonCamera));
        }
    }

    public function set situationRequestRangeDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_situationRequestRangeDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_situationRequestRangeDTOs = value;
            }
            else if (value is Array)
            {
                _internal_situationRequestRangeDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_situationRequestRangeDTOs = null;
            }
            else
            {
                throw new Error("value of situationRequestRangeDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "situationRequestRangeDTOs", oldValue, _internal_situationRequestRangeDTOs));
        }
    }

    public function set situationSensors(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_situationSensors;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_situationSensors = value;
            }
            else if (value is Array)
            {
                _internal_situationSensors = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_situationSensors = null;
            }
            else
            {
                throw new Error("value of situationSensors must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "situationSensors", oldValue, _internal_situationSensors));
        }
    }

    public function set situationTemplateId(value:int) : void
    {
        var oldValue:int = _internal_situationTemplateId;
        if (oldValue !== value)
        {
            _internal_situationTemplateId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "situationTemplateId", oldValue, _internal_situationTemplateId));
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
    public function get _model() : _SituationRequestDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _SituationRequestDTOEntityMetadata) : void
    {
        var oldValue : _SituationRequestDTOEntityMetadata = model_internal::_dminternal_model;
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
