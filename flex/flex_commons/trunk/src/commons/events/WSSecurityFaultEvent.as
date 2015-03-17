/**
 * Clase creada para enviar un evento personalizado a partir de un error en la llamada al webservice
 * de seguridad.
 **/
package commons.events
{
    import flash.events.Event;
    
    public class WSSecurityFaultEvent extends GenericErrorEvent
    {
        public static var WS_SECURITY_FAULT_EVENT:String = "ws_security_fault_event";
        
        public function WSSecurityFaultEvent(message:String = null)
        {
            super(WS_SECURITY_FAULT_EVENT, message);
        }
    }
}