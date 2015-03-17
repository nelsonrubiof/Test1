/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - ExtractionPlanCustomizingDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanMetricDTO;
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
public class _Super_ExtractionPlanCustomizingDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanMetricDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.Entry_type.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _ExtractionPlanCustomizingDTOEntityMetadata;
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
    private var _internal_active : Boolean;
    private var _internal_areaType : String;
    private var _internal_areaTypeId : int;
    private var _internal_extractionPlanMetricDTOs : ArrayCollection;
    model_internal var _internal_extractionPlanMetricDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanMetricDTO;
    private var _internal_id : int;
    private var _internal_oneEvaluation : Boolean;
    private var _internal_priorization : int;
    private var _internal_randomCamera : Boolean;
    private var _internal_send : Boolean;
    private var _internal_sensorIds : ArrayCollection;
    private var _internal_situationTemplateId : int;
    private var _internal_status : String;
    private var _internal_storeId : int;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_ExtractionPlanCustomizingDTO()
    {
        _model = new _ExtractionPlanCustomizingDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get active() : Boolean
    {
        return _internal_active;
    }

    [Bindable(event="propertyChange")]
    public function get areaType() : String
    {
        return _internal_areaType;
    }

    [Bindable(event="propertyChange")]
    public function get areaTypeId() : int
    {
        return _internal_areaTypeId;
    }

    [Bindable(event="propertyChange")]
    public function get extractionPlanMetricDTOs() : ArrayCollection
    {
        return _internal_extractionPlanMetricDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get id() : int
    {
        return _internal_id;
    }

    [Bindable(event="propertyChange")]
    public function get oneEvaluation() : Boolean
    {
        return _internal_oneEvaluation;
    }

    [Bindable(event="propertyChange")]
    public function get priorization() : int
    {
        return _internal_priorization;
    }

    [Bindable(event="propertyChange")]
    public function get randomCamera() : Boolean
    {
        return _internal_randomCamera;
    }

    [Bindable(event="propertyChange")]
    public function get send() : Boolean
    {
        return _internal_send;
    }

    [Bindable(event="propertyChange")]
    public function get sensorIds() : ArrayCollection
    {
        return _internal_sensorIds;
    }

    [Bindable(event="propertyChange")]
    public function get situationTemplateId() : int
    {
        return _internal_situationTemplateId;
    }

    [Bindable(event="propertyChange")]
    public function get status() : String
    {
        return _internal_status;
    }

    [Bindable(event="propertyChange")]
    public function get storeId() : int
    {
        return _internal_storeId;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set active(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_active;
        if (oldValue !== value)
        {
            _internal_active = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "active", oldValue, _internal_active));
        }
    }

    public function set areaType(value:String) : void
    {
        var oldValue:String = _internal_areaType;
        if (oldValue !== value)
        {
            _internal_areaType = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "areaType", oldValue, _internal_areaType));
        }
    }

    public function set areaTypeId(value:int) : void
    {
        var oldValue:int = _internal_areaTypeId;
        if (oldValue !== value)
        {
            _internal_areaTypeId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "areaTypeId", oldValue, _internal_areaTypeId));
        }
    }

    public function set extractionPlanMetricDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_extractionPlanMetricDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_extractionPlanMetricDTOs = value;
            }
            else if (value is Array)
            {
                _internal_extractionPlanMetricDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_extractionPlanMetricDTOs = null;
            }
            else
            {
                throw new Error("value of extractionPlanMetricDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "extractionPlanMetricDTOs", oldValue, _internal_extractionPlanMetricDTOs));
        }
    }

    public function set id(value:int) : void
    {
        var oldValue:int = _internal_id;
        if (oldValue !== value)
        {
            _internal_id = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "id", oldValue, _internal_id));
        }
    }

    public function set oneEvaluation(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_oneEvaluation;
        if (oldValue !== value)
        {
            _internal_oneEvaluation = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "oneEvaluation", oldValue, _internal_oneEvaluation));
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

    public function set randomCamera(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_randomCamera;
        if (oldValue !== value)
        {
            _internal_randomCamera = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "randomCamera", oldValue, _internal_randomCamera));
        }
    }

    public function set send(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_send;
        if (oldValue !== value)
        {
            _internal_send = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "send", oldValue, _internal_send));
        }
    }

    public function set sensorIds(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_sensorIds;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_sensorIds = value;
            }
            else if (value is Array)
            {
                _internal_sensorIds = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_sensorIds = null;
            }
            else
            {
                throw new Error("value of sensorIds must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sensorIds", oldValue, _internal_sensorIds));
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

    public function set status(value:String) : void
    {
        var oldValue:String = _internal_status;
        if (oldValue !== value)
        {
            _internal_status = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "status", oldValue, _internal_status));
        }
    }

    public function set storeId(value:int) : void
    {
        var oldValue:int = _internal_storeId;
        if (oldValue !== value)
        {
            _internal_storeId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "storeId", oldValue, _internal_storeId));
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
    public function get _model() : _ExtractionPlanCustomizingDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _ExtractionPlanCustomizingDTOEntityMetadata) : void
    {
        var oldValue : _ExtractionPlanCustomizingDTOEntityMetadata = model_internal::_dminternal_model;
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
