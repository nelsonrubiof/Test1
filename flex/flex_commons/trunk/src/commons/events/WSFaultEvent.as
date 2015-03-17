/**
 * Clase creada para enviar un evento personalizado a partir de un error en la llamada al webservice
 * del backend.
 **/
package commons.events
{
    import flash.events.Event;

    public class WSFaultEvent extends GenericErrorEvent
    {
        public static var WS_FAULT_EVENT:String = "application_error_event";
        
        public function WSFaultEvent(message:String = null)
        {
            super(WS_FAULT_EVENT, message);
        }
    }
}