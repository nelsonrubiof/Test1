/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - ExtractionPlanDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.StoreTimeDTO;
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
public class _Super_ExtractionPlanDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.MetricRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestRangeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationExtractionRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.StoreTimeDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _ExtractionPlanDTOEntityMetadata;
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
    private var _internal_extractionPlanDetails : ArrayCollection;
    model_internal var _internal_extractionPlanDetails_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO;
    private var _internal_providerTypeDescription : String;
    private var _internal_situationRequestDTOs : ArrayCollection;
    model_internal var _internal_situationRequestDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestDTO;
    private var _internal_storeId : int;
    private var _internal_storeName : String;
    private var _internal_storeTimeDTOs : ArrayCollection;
    model_internal var _internal_storeTimeDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.StoreTimeDTO;
    private var _internal_timeZone : String;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_ExtractionPlanDTO()
    {
        _model = new _ExtractionPlanDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get extractionPlanDetails() : ArrayCollection
    {
        return _internal_extractionPlanDetails;
    }

    [Bindable(event="propertyChange")]
    public function get providerTypeDescription() : String
    {
        return _internal_providerTypeDescription;
    }

    [Bindable(event="propertyChange")]
    public function get situationRequestDTOs() : ArrayCollection
    {
        return _internal_situationRequestDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get storeId() : int
    {
        return _internal_storeId;
    }

    [Bindable(event="propertyChange")]
    public function get storeName() : String
    {
        return _internal_storeName;
    }

    [Bindable(event="propertyChange")]
    public function get storeTimeDTOs() : ArrayCollection
    {
        return _internal_storeTimeDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get timeZone() : String
    {
        return _internal_timeZone;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set extractionPlanDetails(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_extractionPlanDetails;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_extractionPlanDetails = value;
            }
            else if (value is Array)
            {
                _internal_extractionPlanDetails = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_extractionPlanDetails = null;
            }
            else
            {
                throw new Error("value of extractionPlanDetails must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "extractionPlanDetails", oldValue, _internal_extractionPlanDetails));
        }
    }

    public function set providerTypeDescription(value:String) : void
    {
        var oldValue:String = _internal_providerTypeDescription;
        if (oldValue !== value)
        {
            _internal_providerTypeDescription = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "providerTypeDescription", oldValue, _internal_providerTypeDescription));
        }
    }

    public function set situationRequestDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_situationRequestDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_situationRequestDTOs = value;
            }
            else if (value is Array)
            {
                _internal_situationRequestDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_situationRequestDTOs = null;
            }
            else
            {
                throw new Error("value of situationRequestDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "situationRequestDTOs", oldValue, _internal_situationRequestDTOs));
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

    public function set storeName(value:String) : void
    {
        var oldValue:String = _internal_storeName;
        if (oldValue !== value)
        {
            _internal_storeName = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "storeName", oldValue, _internal_storeName));
        }
    }

    public function set storeTimeDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_storeTimeDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_storeTimeDTOs = value;
            }
            else if (value is Array)
            {
                _internal_storeTimeDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_storeTimeDTOs = null;
            }
            else
            {
                throw new Error("value of storeTimeDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "storeTimeDTOs", oldValue, _internal_storeTimeDTOs));
        }
    }

    public function set timeZone(value:String) : void
    {
        var oldValue:String = _internal_timeZone;
        if (oldValue !== value)
        {
            _internal_timeZone = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "timeZone", oldValue, _internal_timeZone));
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
    public function get _model() : _ExtractionPlanDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _ExtractionPlanDTOEntityMetadata) : void
    {
        var oldValue : _ExtractionPlanDTOEntityMetadata = model_internal::_dminternal_model;
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
