package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.events.GetStatusFromSendEPCCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.StatusFromSendEPCVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.CorporateWebServices;
    import com.scopix.periscope.webservices.businessservices.CorporateWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.StatusSendEPCDTO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetInitialStatusFromSendEPCCommand extends BaseCommand {
        public function GetInitialStatusFromSendEPCCommand() {
            super(new GetStatusFromSendEPCCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(sessionId:Number):void {
            webService = CorporateWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as CorporateWebServices).getStatusSendEPC(sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var dto:StatusSendEPCDTO = evt.result as StatusSendEPCDTO;
            var vo:StatusFromSendEPCVO = transformToVO(dto);
            (event as GetStatusFromSendEPCCommandResultEvent).statusFromSendEPCVO = vo;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(dto:StatusSendEPCDTO):StatusFromSendEPCVO {
            var vo:StatusFromSendEPCVO = new StatusFromSendEPCVO();
            vo.fromDTO(dto);
            return vo;
        }
    }
}
