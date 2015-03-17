/**
 * This is a generated class and is not intended for modification.  To customize behavior
 * of this value object you may modify the generated sub-class of this class - QualityEvaluationDTO.as.
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
public class _Super_QualityEvaluationDTO extends flash.events.EventDispatcher implements com.adobe.fiber.valueobjects.IValueObject
{
    model_internal static function initRemoteClassAliasSingle(cz:Class) : void
    {
    }

    model_internal static function initRemoteClassAliasAllRelated() : void
    {
    }

    model_internal var _dminternal_model : _QualityEvaluationDTOEntityMetadata;
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
    private var _internal_clasificacion : int;
    private var _internal_fechaEvidencia : String;
    private var _internal_fechaRevision : String;
    private var _internal_messageOperator : String;
    private var _internal_motivoRechazo : int;
    private var _internal_observaciones : String;
    private var _internal_observedSituationId : int;
    private var _internal_operador : String;
    private var _internal_result : String;
    private var _internal_user : String;

    private static var emptyArray:Array = new Array();


    /**
     * derived property cache initialization
     */
    model_internal var _cacheInitialized_isValid:Boolean = false;

    model_internal var _changeWatcherArray:Array = new Array();

    public function _Super_QualityEvaluationDTO()
    {
        _model = new _QualityEvaluationDTOEntityMetadata(this);

        // Bind to own data or source properties for cache invalidation triggering

    }

    /**
     * data/source property getters
     */

    [Bindable(event="propertyChange")]
    public function get clasificacion() : int
    {
        return _internal_clasificacion;
    }

    [Bindable(event="propertyChange")]
    public function get fechaEvidencia() : String
    {
        return _internal_fechaEvidencia;
    }

    [Bindable(event="propertyChange")]
    public function get fechaRevision() : String
    {
        return _internal_fechaRevision;
    }

    [Bindable(event="propertyChange")]
    public function get messageOperator() : String
    {
        return _internal_messageOperator;
    }

    [Bindable(event="propertyChange")]
    public function get motivoRechazo() : int
    {
        return _internal_motivoRechazo;
    }

    [Bindable(event="propertyChange")]
    public function get observaciones() : String
    {
        return _internal_observaciones;
    }

    [Bindable(event="propertyChange")]
    public function get observedSituationId() : int
    {
        return _internal_observedSituationId;
    }

    [Bindable(event="propertyChange")]
    public function get operador() : String
    {
        return _internal_operador;
    }

    [Bindable(event="propertyChange")]
    public function get result() : String
    {
        return _internal_result;
    }

    [Bindable(event="propertyChange")]
    public function get user() : String
    {
        return _internal_user;
    }

    public function clearAssociations() : void
    {
    }

    /**
     * data/source property setters
     */

    public function set clasificacion(value:int) : void
    {
        var oldValue:int = _internal_clasificacion;
        if (oldValue !== value)
        {
            _internal_clasificacion = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "clasificacion", oldValue, _internal_clasificacion));
        }
    }

    public function set fechaEvidencia(value:String) : void
    {
        var oldValue:String = _internal_fechaEvidencia;
        if (oldValue !== value)
        {
            _internal_fechaEvidencia = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "fechaEvidencia", oldValue, _internal_fechaEvidencia));
        }
    }

    public function set fechaRevision(value:String) : void
    {
        var oldValue:String = _internal_fechaRevision;
        if (oldValue !== value)
        {
            _internal_fechaRevision = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "fechaRevision", oldValue, _internal_fechaRevision));
        }
    }

    public function set messageOperator(value:String) : void
    {
        var oldValue:String = _internal_messageOperator;
        if (oldValue !== value)
        {
            _internal_messageOperator = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "messageOperator", oldValue, _internal_messageOperator));
        }
    }

    public function set motivoRechazo(value:int) : void
    {
        var oldValue:int = _internal_motivoRechazo;
        if (oldValue !== value)
        {
            _internal_motivoRechazo = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "motivoRechazo", oldValue, _internal_motivoRechazo));
        }
    }

    public function set observaciones(value:String) : void
    {
        var oldValue:String = _internal_observaciones;
        if (oldValue !== value)
        {
            _internal_observaciones = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "observaciones", oldValue, _internal_observaciones));
        }
    }

    public function set observedSituationId(value:int) : void
    {
        var oldValue:int = _internal_observedSituationId;
        if (oldValue !== value)
        {
            _internal_observedSituationId = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "observedSituationId", oldValue, _internal_observedSituationId));
        }
    }

    public function set operador(value:String) : void
    {
        var oldValue:String = _internal_operador;
        if (oldValue !== value)
        {
            _internal_operador = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "operador", oldValue, _internal_operador));
        }
    }

    public function set result(value:String) : void
    {
        var oldValue:String = _internal_result;
        if (oldValue !== value)
        {
            _internal_result = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "result", oldValue, _internal_result));
        }
    }

    public function set user(value:String) : void
    {
        var oldValue:String = _internal_user;
        if (oldValue !== value)
        {
            _internal_user = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "user", oldValue, _internal_user));
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
    public function get _model() : _QualityEvaluationDTOEntityMetadata
    {
        return model_internal::_dminternal_model;
    }

    public function set _model(value : _QualityEvaluationDTOEntityMetadata) : void
    {
        var oldValue : _QualityEvaluationDTOEntityMetadata = model_internal::_dminternal_model;
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
