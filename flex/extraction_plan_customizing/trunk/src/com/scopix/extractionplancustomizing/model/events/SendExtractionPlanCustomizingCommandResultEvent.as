package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.vo.StatusFromSendEPCVO;
    
    import commons.events.GenericEvent;
    
    /**
    * Esta clase es usada como notificacion de respuesta al enviar un plan de extracción, ya sea
    * para uno en particular o completo
    **/
    public class SendExtractionPlanCustomizingCommandResultEvent extends GenericEvent
    {
        public static var SEND_EPC_EVENT:String = "send_epc_event";
        private var _statusFromSendEPCVO:StatusFromSendEPCVO;
        
        public function SendExtractionPlanCustomizingCommandResultEvent(vo:StatusFromSendEPCVO = null)
        {
            super(SEND_EPC_EVENT);
            this._statusFromSendEPCVO = vo;
        }
        
        public function get statusFromSendEPCVO():StatusFromSendEPCVO {
        	return _statusFromSendEPCVO;
        }
        
        public function set statusFromSendEPCVO(vo:StatusFromSendEPCVO):void {
        	this._statusFromSendEPCVO = vo;
        }
    }
}