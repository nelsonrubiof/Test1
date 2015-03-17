/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - PeriscopeUserDTO.as.
 */

package com.scopix.periscope.webservices.security.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.security.valueObjects.AreaTypeDTO;
import com.scopix.periscope.webservices.security.valueObjects.CorporateDTO;
import com.scopix.periscope.webservices.security.valueObjects.RolesGroupDTO;
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
public class _Super_PeriscopeUserDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.security.valueObjects.AreaTypeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.security.valueObjects.CorporateDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.security.valueObjects.StoreDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.security.valueObjects.RolesGroupDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _PeriscopeUserDTOEntityMetadata;
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
    private var _internal_corporates : ArrayCollection;
    model_internal var _internal_corporates_leaf:com.scopix.periscope.webservices.security.valueObjects.CorporateDTO;
    private var _internal__delete : Boolean;
    private var _internal_email : String;
    private var _internal_fullName : String;
    private var _internal_jobPosition : String;
    private var _internal_mainCorporateId : int;
    private var _internal_modificationDate : Date;
    private var _internal_password : String;
    private var _internal_rolesGroups : ArrayCollection;
    model_internal var _internal_rolesGroups_leaf:com.scopix.periscope.webservices.security.valueObjects.RolesGroupDTO;
    private var _internal_startDate : Date;
    private var _internal_stores : ArrayCollection;
    model_internal var _internal_stores_leaf:com.scopix.periscope.webservices.security.valueObjects.StoreDTO;
    private var _internal_userId : int;
    private var _internal_userName : String;
    private var _internal_userState : String;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_PeriscopeUserDTO()
    {
        _model = new _PeriscopeUserDTOEntityMetadata(this);

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
    public function get corporates() : ArrayCollection
    {
        return _internal_corporates;
    }

    [Bindable(event="propertyChange")]
    public function get _delete() : Boolean
    {
        return _internal__delete;
    }

    [Bindable(event="propertyChange")]
    public function get email() : String
    {
        return _internal_email;
    }

    [Bindable(event="propertyChange")]
    public function get fullName() : String
    {
        return _internal_fullName;
    }

    [Bindable(event="propertyChange")]
    public function get jobPosition() : String
    {
        return _internal_jobPosition;
    }

    [Bindable(event="propertyChange")]
    public function get mainCorporateId() : int
    {
        return _internal_mainCorporateId;
    }

    [Bindable(event="propertyChange")]
    public function get modificationDate() : Date
    {
        return _internal_modificationDate;
    }

    [Bindable(event="propertyChange")]
    public function get password() : String
    {
        return _internal_password;
    }

    [Bindable(event="propertyChange")]
    public function get rolesGroups() : ArrayCollection
    {
        return _internal_rolesGroups;
    }

    [Bindable(event="propertyChange")]
    public function get startDate() : Date
    {
        return _internal_startDate;
    }

    [Bindable(event="propertyChange")]
    public function get stores() : ArrayCollection
    {
        return _internal_stores;
    }

    [Bindable(event="propertyChange")]
    public function get userId() : int
    {
        return _internal_userId;
    }

    [Bindable(event="propertyChange")]
    public function get userName() : String
    {
        return _internal_userName;
    }

    [Bindable(event="propertyChange")]
    public function get userState() : String
    {
        return _internal_userState;
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

    public function set corporates(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_corporates;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_corporates = value;
            }
            else if (value is Array)
            {
                _internal_corporates = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_corporates = null;
            }
            else
            {
                throw new Error("value of corporates must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "corporates", oldValue, _internal_corporates));
        }
    }

    public function set _delete(value:Boolean) : void
    {
        var oldValue:Boolean = _internal__delete;
        if (oldValue !== value)
        {
            _internal__delete = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "_delete", oldValue, _internal__delete));
        }
    }

    public function set email(value:String) : void
    {
        var oldValue:String = _internal_email;
        if (oldValue !== value)
        {
            _internal_email = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "email", oldValue, _internal_email));
        }
    }

    public function set fullName(value:String) : void
    {
        var oldValue:String = _internal_fullName;
        if (oldValue !== value)
        {
            _internal_fullName = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "fullName", oldValue, _internal_fullName));
        }
    }

    public function set jobPosition(value:String) : void
    {
        var oldValue:String = _internal_jobPosition;
        if (oldValue !== value)
        {
            _internal_jobPosition = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "jobPosition", oldValue, _internal_jobPosition));
        }
    }

    public function set mainCorporateId(value:int) : void
    {
        var oldValue:int = _internal_mainCorporateId;
        if (oldValue !== value)
        {
            _internal_mainCorporateId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "mainCorporateId", oldValue, _internal_mainCorporateId));
        }
    }

    public function set modificationDate(value:Date) : void
    {
        var oldValue:Date = _internal_modificationDate;
        if (oldValue !== value)
        {
            _internal_modificationDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "modificationDate", oldValue, _internal_modificationDate));
        }
    }

    public function set password(value:String) : void
    {
        var oldValue:String = _internal_password;
        if (oldValue !== value)
        {
            _internal_password = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "password", oldValue, _internal_password));
        }
    }

    public function set rolesGroups(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_rolesGroups;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_rolesGroups = value;
            }
            else if (value is Array)
            {
                _internal_rolesGroups = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_rolesGroups = null;
            }
            else
            {
                throw new Error("value of rolesGroups must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "rolesGroups", oldValue, _internal_rolesGroups));
        }
    }

    public function set startDate(value:Date) : void
    {
        var oldValue:Date = _internal_startDate;
        if (oldValue !== value)
        {
            _internal_startDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "startDate", oldValue, _internal_startDate));
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

    public function set userId(value:int) : void
    {
        var oldValue:int = _internal_userId;
        if (oldValue !== value)
        {
            _internal_userId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "userId", oldValue, _internal_userId));
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

    public function set userState(value:String) : void
    {
        var oldValue:String = _internal_userState;
        if (oldValue !== value)
        {
            _internal_userState = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "userState", oldValue, _internal_userState));
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
    public function get _model() : _PeriscopeUserDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _PeriscopeUserDTOEntityMetadata) : void
    {
        var oldValue : _PeriscopeUserDTOEntityMetadata = model_internal::_dminternal_model;
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
