/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - EvidenceRequestDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO;
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
public class _Super_EvidenceRequestDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _EvidenceRequestDTOEntityMetadata;
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
    private var _internal_businessServicesRequestId : int;
    private var _internal_dayOfWeek : int;
    private var _internal_deviceId : int;
    private var _internal_duration : int;
    private var _internal_extractionPlanDetailDTO : ArrayCollection;
    model_internal var _internal_extractionPlanDetailDTO_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO;
    private var _internal_priorization : int;
    private var _internal_requestType : String;
    private var _internal_requestedTime : Date;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_EvidenceRequestDTO()
    {
        _model = new _EvidenceRequestDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get businessServicesRequestId() : int
    {
        return _internal_businessServicesRequestId;
    }

    [Bindable(event="propertyChange")]
    public function get dayOfWeek() : int
    {
        return _internal_dayOfWeek;
    }

    [Bindable(event="propertyChange")]
    public function get deviceId() : int
    {
        return _internal_deviceId;
    }

    [Bindable(event="propertyChange")]
    public function get duration() : int
    {
        return _internal_duration;
    }

    [Bindable(event="propertyChange")]
    public function get extractionPlanDetailDTO() : ArrayCollection
    {
        return _internal_extractionPlanDetailDTO;
    }

    [Bindable(event="propertyChange")]
    public function get priorization() : int
    {
        return _internal_priorization;
    }

    [Bindable(event="propertyChange")]
    public function get requestType() : String
    {
        return _internal_requestType;
    }

    [Bindable(event="propertyChange")]
    public function get requestedTime() : Date
    {
        return _internal_requestedTime;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set businessServicesRequestId(value:int) : void
    {
        var oldValue:int = _internal_businessServicesRequestId;
        if (oldValue !== value)
        {
            _internal_businessServicesRequestId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "businessServicesRequestId", oldValue, _internal_businessServicesRequestId));
        }
    }

    public function set dayOfWeek(value:int) : void
    {
        var oldValue:int = _internal_dayOfWeek;
        if (oldValue !== value)
        {
            _internal_dayOfWeek = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "dayOfWeek", oldValue, _internal_dayOfWeek));
        }
    }

    public function set deviceId(value:int) : void
    {
        var oldValue:int = _internal_deviceId;
        if (oldValue !== value)
        {
            _internal_deviceId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "deviceId", oldValue, _internal_deviceId));
        }
    }

    public function set duration(value:int) : void
    {
        var oldValue:int = _internal_duration;
        if (oldValue !== value)
        {
            _internal_duration = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "duration", oldValue, _internal_duration));
        }
    }

    public function set extractionPlanDetailDTO(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_extractionPlanDetailDTO;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_extractionPlanDetailDTO = value;
            }
            else if (value is Array)
            {
                _internal_extractionPlanDetailDTO = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_extractionPlanDetailDTO = null;
            }
            else
            {
                throw new Error("value of extractionPlanDetailDTO must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "extractionPlanDetailDTO", oldValue, _internal_extractionPlanDetailDTO));
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

    public function set requestType(value:String) : void
    {
        var oldValue:String = _internal_requestType;
        if (oldValue !== value)
        {
            _internal_requestType = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "requestType", oldValue, _internal_requestType));
        }
    }

    public function set requestedTime(value:Date) : void
    {
        var oldValue:Date = _internal_requestedTime;
        if (oldValue !== value)
        {
            _internal_requestedTime = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "requestedTime", oldValue, _internal_requestedTime));
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
    public function get _model() : _EvidenceRequestDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _EvidenceRequestDTOEntityMetadata) : void
    {
        var oldValue : _EvidenceRequestDTOEntityMetadata = model_internal::_dminternal_model;
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
