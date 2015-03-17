/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - EvidenceExtractionServicesServerDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;
import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanDTO;
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
public class _Super_EvidenceExtractionServicesServerDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.Entry_type.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.MetricRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationRequestRangeDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationExtractionRequestDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO.initRemoteClassAliasSingleChild();
        com.scopix.periscope.webservices.businessservices.valueObjects.StoreTimeDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _EvidenceExtractionServicesServerDTOEntityMetadata;
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
    private var _internal_evidenceProviderDTOs : ArrayCollection;
    model_internal var _internal_evidenceProviderDTOs_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;
    private var _internal_extractionPlanDTO : com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanDTO;
    private var _internal_idAtBusinessServices : int;
    private var _internal_name : String;
    private var _internal_serverId : int;
    private var _internal_sshAddress : String;
    private var _internal_sshLocalTunnelPort : String;
    private var _internal_sshPassword : String;
    private var _internal_sshPort : String;
    private var _internal_sshRemoteTunnelPort : String;
    private var _internal_sshUser : String;
    private var _internal_url : String;
    private var _internal_useTunnel : Boolean;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_EvidenceExtractionServicesServerDTO()
    {
        _model = new _EvidenceExtractionServicesServerDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get evidenceProviderDTOs() : ArrayCollection
    {
        return _internal_evidenceProviderDTOs;
    }

    [Bindable(event="propertyChange")]
    public function get extractionPlanDTO() : com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanDTO
    {
        return _internal_extractionPlanDTO;
    }

    [Bindable(event="propertyChange")]
    public function get idAtBusinessServices() : int
    {
        return _internal_idAtBusinessServices;
    }

    [Bindable(event="propertyChange")]
    public function get name() : String
    {
        return _internal_name;
    }

    [Bindable(event="propertyChange")]
    public function get serverId() : int
    {
        return _internal_serverId;
    }

    [Bindable(event="propertyChange")]
    public function get sshAddress() : String
    {
        return _internal_sshAddress;
    }

    [Bindable(event="propertyChange")]
    public function get sshLocalTunnelPort() : String
    {
        return _internal_sshLocalTunnelPort;
    }

    [Bindable(event="propertyChange")]
    public function get sshPassword() : String
    {
        return _internal_sshPassword;
    }

    [Bindable(event="propertyChange")]
    public function get sshPort() : String
    {
        return _internal_sshPort;
    }

    [Bindable(event="propertyChange")]
    public function get sshRemoteTunnelPort() : String
    {
        return _internal_sshRemoteTunnelPort;
    }

    [Bindable(event="propertyChange")]
    public function get sshUser() : String
    {
        return _internal_sshUser;
    }

    [Bindable(event="propertyChange")]
    public function get url() : String
    {
        return _internal_url;
    }

    [Bindable(event="propertyChange")]
    public function get useTunnel() : Boolean
    {
        return _internal_useTunnel;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set evidenceProviderDTOs(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_evidenceProviderDTOs;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_evidenceProviderDTOs = value;
            }
            else if (value is Array)
            {
                _internal_evidenceProviderDTOs = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_evidenceProviderDTOs = null;
            }
            else
            {
                throw new Error("value of evidenceProviderDTOs must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceProviderDTOs", oldValue, _internal_evidenceProviderDTOs));
        }
    }

    public function set extractionPlanDTO(value:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanDTO) : void
    {
        var oldValue:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanDTO = _internal_extractionPlanDTO;
        if (oldValue !== value)
        {
            _internal_extractionPlanDTO = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "extractionPlanDTO", oldValue, _internal_extractionPlanDTO));
        }
    }

    public function set idAtBusinessServices(value:int) : void
    {
        var oldValue:int = _internal_idAtBusinessServices;
        if (oldValue !== value)
        {
            _internal_idAtBusinessServices = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "idAtBusinessServices", oldValue, _internal_idAtBusinessServices));
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

    public function set serverId(value:int) : void
    {
        var oldValue:int = _internal_serverId;
        if (oldValue !== value)
        {
            _internal_serverId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "serverId", oldValue, _internal_serverId));
        }
    }

    public function set sshAddress(value:String) : void
    {
        var oldValue:String = _internal_sshAddress;
        if (oldValue !== value)
        {
            _internal_sshAddress = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sshAddress", oldValue, _internal_sshAddress));
        }
    }

    public function set sshLocalTunnelPort(value:String) : void
    {
        var oldValue:String = _internal_sshLocalTunnelPort;
        if (oldValue !== value)
        {
            _internal_sshLocalTunnelPort = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sshLocalTunnelPort", oldValue, _internal_sshLocalTunnelPort));
        }
    }

    public function set sshPassword(value:String) : void
    {
        var oldValue:String = _internal_sshPassword;
        if (oldValue !== value)
        {
            _internal_sshPassword = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sshPassword", oldValue, _internal_sshPassword));
        }
    }

    public function set sshPort(value:String) : void
    {
        var oldValue:String = _internal_sshPort;
        if (oldValue !== value)
        {
            _internal_sshPort = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sshPort", oldValue, _internal_sshPort));
        }
    }

    public function set sshRemoteTunnelPort(value:String) : void
    {
        var oldValue:String = _internal_sshRemoteTunnelPort;
        if (oldValue !== value)
        {
            _internal_sshRemoteTunnelPort = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sshRemoteTunnelPort", oldValue, _internal_sshRemoteTunnelPort));
        }
    }

    public function set sshUser(value:String) : void
    {
        var oldValue:String = _internal_sshUser;
        if (oldValue !== value)
        {
            _internal_sshUser = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "sshUser", oldValue, _internal_sshUser));
        }
    }

    public function set url(value:String) : void
    {
        var oldValue:String = _internal_url;
        if (oldValue !== value)
        {
            _internal_url = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "url", oldValue, _internal_url));
        }
    }

    public function set useTunnel(value:Boolean) : void
    {
        var oldValue:Boolean = _internal_useTunnel;
        if (oldValue !== value)
        {
            _internal_useTunnel = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "useTunnel", oldValue, _internal_useTunnel));
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
    public function get _model() : _EvidenceExtractionServicesServerDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _EvidenceExtractionServicesServerDTOEntityMetadata) : void
    {
        var oldValue : _EvidenceExtractionServicesServerDTOEntityMetadata = model_internal::_dminternal_model;
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
