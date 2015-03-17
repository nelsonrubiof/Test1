/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - ProofDTO.as.
 */

package com.scopix.periscope.webservices.businessservices.valueObjects
{
import com.adobe.fiber.services.IFiberManagingService;
import com.adobe.fiber.valueobjects.IValueObject;
import com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO;
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
public class _Super_ProofDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
        com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO.initRemoteClassAliasSingleChild();
    }

    model_internal var _dminternal_model : _ProofDTOEntityMetadata;
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
    private var _internal_evidenceId : int;
    private var _internal_marquis : ArrayCollection;
    model_internal var _internal_marquis_leaf:com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO;
    private var _internal_order : int;
    private var _internal_pathWithMarks : String;
    private var _internal_pathWithoutMarks : String;
    private var _internal_proofId : int;
    private var _internal_proofResult : int;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_ProofDTO()
    {
        _model = new _ProofDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get evidenceId() : int
    {
        return _internal_evidenceId;
    }

    [Bindable(event="propertyChange")]
    public function get marquis() : ArrayCollection
    {
        return _internal_marquis;
    }

    [Bindable(event="propertyChange")]
    public function get order() : int
    {
        return _internal_order;
    }

    [Bindable(event="propertyChange")]
    public function get pathWithMarks() : String
    {
        return _internal_pathWithMarks;
    }

    [Bindable(event="propertyChange")]
    public function get pathWithoutMarks() : String
    {
        return _internal_pathWithoutMarks;
    }

    [Bindable(event="propertyChange")]
    public function get proofId() : int
    {
        return _internal_proofId;
    }

    [Bindable(event="propertyChange")]
    public function get proofResult() : int
    {
        return _internal_proofResult;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set evidenceId(value:int) : void
    {
        var oldValue:int = _internal_evidenceId;
        if (oldValue !== value)
        {
            _internal_evidenceId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "evidenceId", oldValue, _internal_evidenceId));
        }
    }

    public function set marquis(value:*) : void
    {
        var oldValue:ArrayCollection = _internal_marquis;
        if (oldValue !== value)
        {
            if (value is ArrayCollection)
            {
                _internal_marquis = value;
            }
            else if (value is Array)
            {
                _internal_marquis = new ArrayCollection(value);
            }
            else if (value == null)
            {
                _internal_marquis = null;
            }
            else
            {
                throw new Error("value of marquis must be a collection");
            }
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "marquis", oldValue, _internal_marquis));
        }
    }

    public function set order(value:int) : void
    {
        var oldValue:int = _internal_order;
        if (oldValue !== value)
        {
            _internal_order = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "order", oldValue, _internal_order));
        }
    }

    public function set pathWithMarks(value:String) : void
    {
        var oldValue:String = _internal_pathWithMarks;
        if (oldValue !== value)
        {
            _internal_pathWithMarks = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "pathWithMarks", oldValue, _internal_pathWithMarks));
        }
    }

    public function set pathWithoutMarks(value:String) : void
    {
        var oldValue:String = _internal_pathWithoutMarks;
        if (oldValue !== value)
        {
            _internal_pathWithoutMarks = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "pathWithoutMarks", oldValue, _internal_pathWithoutMarks));
        }
    }

    public function set proofId(value:int) : void
    {
        var oldValue:int = _internal_proofId;
        if (oldValue !== value)
        {
            _internal_proofId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "proofId", oldValue, _internal_proofId));
        }
    }

    public function set proofResult(value:int) : void
    {
        var oldValue:int = _internal_proofResult;
        if (oldValue !== value)
        {
            _internal_proofResult = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "proofResult", oldValue, _internal_proofResult));
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
    public function get _model() : _ProofDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _ProofDTOEntityMetadata) : void
    {
        var oldValue : _ProofDTOEntityMetadata = model_internal::_dminternal_model;
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
