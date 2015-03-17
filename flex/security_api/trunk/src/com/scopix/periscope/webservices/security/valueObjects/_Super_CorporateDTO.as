/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - CorporateDTO.as.
 */

package com.scopix.periscope.webservices.security.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.security.valueObjects.AreaTypeDTO;
import com.scopix.periscope.webservices.security.valueObjects.StoreDTO;
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
public class _Super_CorporateDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.security.valueObjects.AreaTypeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.security.valueObjects.StoreDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _CorporateDTOEntityMetadata;
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
    private var _internal_areaTypes : ArrayCollection;
    model_internal var _internal_areaTypes_leaf:com.scopix.periscope.webservices.security.valueObjects.AreaTypeDTO;
    private var _internal_corporateId : int;
    private var _internal_description : String;
    private var _internal_name : String;
    private var _internal_stores : ArrayCollection;
    model_internal var _internal_stores_leaf:com.scopix.periscope.webservices.security.valueObjects.StoreDTO;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_CorporateDTO()
    {
        _model = new _CorporateDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get areaTypes() : ArrayCollection
    {
        return _internal_areaTypes;
    }

    [Bindable(event="propertyChange")]
    public function get corporateId() : int
    {
        return _internal_corporateId;
    }

    [Bindable(event="propertyChange")]
    public function get description() : String
    {
        return _internal_description;
    }

    [Bindable(event="propertyChange")]
    public function get name() : String
    {
        return _internal_name;
    }

    [Bindable(event="propertyChange")]
    public function get stores() : ArrayCollection
    {
        return _internal_stores;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set areaTypes(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_areaTypes;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_areaTypes = value;
            }
            else if (value is Array)
            {
                _internal_areaTypes = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_areaTypes = null;
            }
            else
            {
                throw new Error("value of areaTypes must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "areaTypes", oldValue, _internal_areaTypes));
        }
    }

    public function set corporateId(value:int) : void
    {
        var oldValue:int = _internal_corporateId;
        if (oldValue !== value)
        {
            _internal_corporateId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "corporateId", oldValue, _internal_corporateId));
        }
    }

    public function set description(value:String) : void
    {
        var oldValue:String = _internal_description;
        if (oldValue !== value)
        {
            _internal_description = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "description", oldValue, _internal_description));
        }
    }

    public function set name(value:String) : void
    {
        var oldValue:String = _internal_name;
        if (oldValue !== value)
        {
            _internal_name = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "name", oldValue, _internal_name));
        }
    }

    public function set stores(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_stores;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_stores = value;
            }
            else if (value is Array)
            {
                _internal_stores = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_stores = null;
            }
            else
            {
                throw new Error("value of stores must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "stores", oldValue, _internal_stores));
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
    public function get _model() : _CorporateDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _CorporateDTOEntityMetadata) : void
    {
        var oldValue : _CorporateDTOEntityMetadata = model_internal::_dminternal_model;
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
