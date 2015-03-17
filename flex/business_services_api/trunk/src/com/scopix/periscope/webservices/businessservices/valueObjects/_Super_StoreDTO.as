/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - StoreDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.AreaDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.PeriodIntervalDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO;
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
public class _Super_StoreDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.AreaDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.AreaTypeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.PeriodIntervalDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _StoreDTOEntityMetadata;
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
    private var _internal_storeId : int;
    private var _internal_address : String;
    private var _internal_areas : ArrayCollection;
    model_internal var _internal_areas_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.AreaDTO;
    private var _internal_corporate : com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO;
    private var _internal_country : com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO;
    private var _internal_description : String;
    private var _internal_eessId : int;
    private var _internal_id : int;
    private var _internal_name : String;
    private var _internal_periodIntervalDTOs : ArrayCollection;
    model_internal var _internal_periodIntervalDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.PeriodIntervalDTO;
    private var _internal_region : com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_StoreDTO()
    {
        _model = new _StoreDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get storeId() : int
    {
        return _internal_storeId;
    }

    [Bindable(event="propertyChange")]
    public function get address() : String
    {
        return _internal_address;
    }

    [Bindable(event="propertyChange")]
    public function get areas() : ArrayCollection
    {
        return _internal_areas;
    }

    [Bindable(event="propertyChange")]
    public function get corporate() : com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO
    {
        return _internal_corporate;
    }

    [Bindable(event="propertyChange")]
    public function get country() : com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO
    {
        return _internal_country;
    }

    [Bindable(event="propertyChange")]
    public function get description() : String
    {
        return _internal_description;
    }

    [Bindable(event="propertyChange")]
    public function get eessId() : int
    {
        return _internal_eessId;
    }

    [Bindable(event="propertyChange")]
    public function get id() : int
    {
        return _internal_id;
    }

    [Bindable(event="propertyChange")]
    public function get name() : String
    {
        return _internal_name;
    }

    [Bindable(event="propertyChange")]
    public function get periodIntervalDTOs() : ArrayCollection
    {
        return _internal_periodIntervalDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get region() : com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO
    {
        return _internal_region;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set storeId(value:int) : void
    {
        var oldValue:int = _internal_storeId;
        if (oldValue !== value)
        {
            _internal_storeId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "storeId", oldValue, _internal_storeId));
        }
    }

    public function set address(value:String) : void
    {
        var oldValue:String = _internal_address;
        if (oldValue !== value)
        {
            _internal_address = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "address", oldValue, _internal_address));
        }
    }

    public function set areas(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_areas;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_areas = value;
            }
            else if (value is Array)
            {
                _internal_areas = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_areas = null;
            }
            else
            {
                throw new Error("value of areas must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "areas", oldValue, _internal_areas));
        }
    }

    public function set corporate(value:com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO) : void
    {
        var oldValue:com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO = _internal_corporate;
        if (oldValue !== value)
        {
            _internal_corporate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "corporate", oldValue, _internal_corporate));
        }
    }

    public function set country(value:com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO) : void
    {
        var oldValue:com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO = _internal_country;
        if (oldValue !== value)
        {
            _internal_country = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "country", oldValue, _internal_country));
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

    public function set eessId(value:int) : void
    {
        var oldValue:int = _internal_eessId;
        if (oldValue !== value)
        {
            _internal_eessId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "eessId", oldValue, _internal_eessId));
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

    public function set name(value:String) : void
    {
        var oldValue:String = _internal_name;
        if (oldValue !== value)
        {
            _internal_name = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "name", oldValue, _internal_name));
        }
    }

    public function set periodIntervalDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_periodIntervalDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_periodIntervalDTOs = value;
            }
            else if (value is Array)
            {
                _internal_periodIntervalDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_periodIntervalDTOs = null;
            }
            else
            {
                throw new Error("value of periodIntervalDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "periodIntervalDTOs", oldValue, _internal_periodIntervalDTOs));
        }
    }

    public function set region(value:com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO) : void
    {
        var oldValue:com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO = _internal_region;
        if (oldValue !== value)
        {
            _internal_region = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "region", oldValue, _internal_region));
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
    public function get _model() : _StoreDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _StoreDTOEntityMetadata) : void
    {
        var oldValue : _StoreDTOEntityMetadata = model_internal::_dminternal_model;
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
