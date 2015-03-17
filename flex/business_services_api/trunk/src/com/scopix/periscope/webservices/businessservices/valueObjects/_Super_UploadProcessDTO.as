/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - UploadProcessDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.UploadProcessDetailDTO;
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
public class _Super_UploadProcessDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.UploadProcessDetailDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.AreaTypeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.AreaDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.CorporateDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.CountryDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.RegionDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.PeriodIntervalDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _UploadProcessDTOEntityMetadata;
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
    private var _internal_dateProcess : String;
    private var _internal_endDate : String;
    private var _internal_id : int;
    private var _internal_loginUser : String;
    private var _internal_loginUserRunning : String;
    private var _internal_motiveClosing : String;
    private var _internal_observations : String;
    private var _internal_percentGlobal : Number;
    private var _internal_processDetails : ArrayCollection;
    model_internal var _internal_processDetails_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.UploadProcessDetailDTO;
    private var _internal_processState : String;
    private var _internal_processedGlobal : int;
    private var _internal_startDate : String;
    private var _internal_totalGlobal : int;

    private static var emptyArray:Array = new Array();

    // Change this value according to your application's floating-point precision
    private static var epsilon:Number = 0.0001;

    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_UploadProcessDTO()
    {
        _model = new _UploadProcessDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get dateProcess() : String
    {
        return _internal_dateProcess;
    }

    [Bindable(event="propertyChange")]
    public function get endDate() : String
    {
        return _internal_endDate;
    }

    [Bindable(event="propertyChange")]
    public function get id() : int
    {
        return _internal_id;
    }

    [Bindable(event="propertyChange")]
    public function get loginUser() : String
    {
        return _internal_loginUser;
    }

    [Bindable(event="propertyChange")]
    public function get loginUserRunning() : String
    {
        return _internal_loginUserRunning;
    }

    [Bindable(event="propertyChange")]
    public function get motiveClosing() : String
    {
        return _internal_motiveClosing;
    }

    [Bindable(event="propertyChange")]
    public function get observations() : String
    {
        return _internal_observations;
    }

    [Bindable(event="propertyChange")]
    public function get percentGlobal() : Number
    {
        return _internal_percentGlobal;
    }

    [Bindable(event="propertyChange")]
    public function get processDetails() : ArrayCollection
    {
        return _internal_processDetails;
    }

    [Bindable(event="propertyChange")]
    public function get processState() : String
    {
        return _internal_processState;
    }

    [Bindable(event="propertyChange")]
    public function get processedGlobal() : int
    {
        return _internal_processedGlobal;
    }

    [Bindable(event="propertyChange")]
    public function get startDate() : String
    {
        return _internal_startDate;
    }

    [Bindable(event="propertyChange")]
    public function get totalGlobal() : int
    {
        return _internal_totalGlobal;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set dateProcess(value:String) : void
    {
        var oldValue:String = _internal_dateProcess;
        if (oldValue !== value)
        {
            _internal_dateProcess = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "dateProcess", oldValue, _internal_dateProcess));
        }
    }

    public function set endDate(value:String) : void
    {
        var oldValue:String = _internal_endDate;
        if (oldValue !== value)
        {
            _internal_endDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "endDate", oldValue, _internal_endDate));
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

    public function set loginUser(value:String) : void
    {
        var oldValue:String = _internal_loginUser;
        if (oldValue !== value)
        {
            _internal_loginUser = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "loginUser", oldValue, _internal_loginUser));
        }
    }

    public function set loginUserRunning(value:String) : void
    {
        var oldValue:String = _internal_loginUserRunning;
        if (oldValue !== value)
        {
            _internal_loginUserRunning = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "loginUserRunning", oldValue, _internal_loginUserRunning));
        }
    }

    public function set motiveClosing(value:String) : void
    {
        var oldValue:String = _internal_motiveClosing;
        if (oldValue !== value)
        {
            _internal_motiveClosing = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "motiveClosing", oldValue, _internal_motiveClosing));
        }
    }

    public function set observations(value:String) : void
    {
        var oldValue:String = _internal_observations;
        if (oldValue !== value)
        {
            _internal_observations = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "observations", oldValue, _internal_observations));
        }
    }

    public function set percentGlobal(value:Number) : void
    {
        var oldValue:Number = _internal_percentGlobal;
        if (isNaN(_internal_percentGlobal) == true || Math.abs(oldValue - value) > epsilon)
        {
            _internal_percentGlobal = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "percentGlobal", oldValue, _internal_percentGlobal));
        }
    }

    public function set processDetails(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_processDetails;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_processDetails = value;
            }
            else if (value is Array)
            {
                _internal_processDetails = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_processDetails = null;
            }
            else
            {
                throw new Error("value of processDetails must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "processDetails", oldValue, _internal_processDetails));
        }
    }

    public function set processState(value:String) : void
    {
        var oldValue:String = _internal_processState;
        if (oldValue !== value)
        {
            _internal_processState = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "processState", oldValue, _internal_processState));
        }
    }

    public function set processedGlobal(value:int) : void
    {
        var oldValue:int = _internal_processedGlobal;
        if (oldValue !== value)
        {
            _internal_processedGlobal = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "processedGlobal", oldValue, _internal_processedGlobal));
        }
    }

    public function set startDate(value:String) : void
    {
        var oldValue:String = _internal_startDate;
        if (oldValue !== value)
        {
            _internal_startDate = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "startDate", oldValue, _internal_startDate));
        }
    }

    public function set totalGlobal(value:int) : void
    {
        var oldValue:int = _internal_totalGlobal;
        if (oldValue !== value)
        {
            _internal_totalGlobal = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "totalGlobal", oldValue, _internal_totalGlobal));
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
    public function get _model() : _UploadProcessDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _UploadProcessDTOEntityMetadata) : void
    {
        var oldValue : _UploadProcessDTOEntityMetadata = model_internal::_dminternal_model;
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
