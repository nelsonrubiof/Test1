/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - PeriodIntervalDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
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
public class _Super_PeriodIntervalDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
    }

    model_internal var _dminternal_model : _PeriodIntervalDTOEntityMetadata;
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
    private var _internal_endTime : String;
    private var _internal_friday : Boolean;
    private var _internal_id : int;
    private var _internal_initTime : String;
    private var _internal_monday : Boolean;
    private var _internal_saturday : Boolean;
    private var _internal_storeId : int;
    private var _internal_sunday : Boolean;
    private var _internal_thursday : Boolean;
    private var _internal_tuesday : Boolean;
    private var _internal_wednesday : Boolean;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_PeriodIntervalDTO()
    {
        _model = new _PeriodIntervalDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get endTime() : String
    {
        return _internal_endTime;
    }

    [Bindable(event="propertyChange")]
    public function get friday() : Boolean
    {
        return _internal_friday;
    }

    [Bindable(event="propertyChange")]
    public function get id() : int
    {
        return _internal_id;
    }

    [Bindable(event="propertyChange")]
    public function get initTime() : String
    {
        return _internal_initTime;
    }

    [Bindable(event="propertyChange")]
    public function get monday() : Boolean
    {
        return _internal_monday;
    }

    [Bindable(event="propertyChange")]
    public function get saturday() : Boolean
    {
        return _internal_saturday;
    }

    [Bindable(event="propertyChange")]
    public function get storeId() : int
    {
        return _internal_storeId;
    }

    [Bindable(event="propertyChange")]
    public function get sunday() : Boolean
    {
        return _internal_sunday;
    }

    [Bindable(event="propertyChange")]
    public function get thursday() : Boolean
    {
        return _internal_thursday;
    }

    [Bindable(event="propertyChange")]
    public function get tuesday() : Boolean
    {
        return _internal_tuesday;
    }

    [Bindable(event="propertyChange")]
    public function get wednesday() : Boolean
    {
        return _internal_wednesday;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set endTime(value:String) : void
    {
        var oldValue:String = _internal_endTime;
        if (oldValue !== value)
        {
            _internal_endTime = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "endTime", oldValue, _internal_endTime));
        }
    }

    public function set friday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_friday;
        if (oldValue !== value)
        {
            _internal_friday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "friday", oldValue, _internal_friday));
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

    public function set initTime(value:String) : void
    {
        var oldValue:String = _internal_initTime;
        if (oldValue !== value)
        {
            _internal_initTime = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "initTime", oldValue, _internal_initTime));
        }
    }

    public function set monday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_monday;
        if (oldValue !== value)
        {
            _internal_monday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "monday", oldValue, _internal_monday));
        }
    }

    public function set saturday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_saturday;
        if (oldValue !== value)
        {
            _internal_saturday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "saturday", oldValue, _internal_saturday));
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

    public function set sunday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_sunday;
        if (oldValue !== value)
        {
            _internal_sunday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sunday", oldValue, _internal_sunday));
        }
    }

    public function set thursday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_thursday;
        if (oldValue !== value)
        {
            _internal_thursday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "thursday", oldValue, _internal_thursday));
        }
    }

    public function set tuesday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_tuesday;
        if (oldValue !== value)
        {
            _internal_tuesday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "tuesday", oldValue, _internal_tuesday));
        }
    }

    public function set wednesday(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_wednesday;
        if (oldValue !== value)
        {
            _internal_wednesday = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "wednesday", oldValue, _internal_wednesday));
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
    public function get _model() : _PeriodIntervalDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _PeriodIntervalDTOEntityMetadata) : void
    {
        var oldValue : _PeriodIntervalDTOEntityMetadata = model_internal::_dminternal_model;
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
