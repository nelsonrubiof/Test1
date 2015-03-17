package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServices;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.MotivoRejectedDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfMotivoRejectedVO;
    import com.scopix.qualitycontrol.model.events.GetMotivoRejectdCommandResultEvent;
    import com.scopix.qualitycontrol.model.vo.MotivoRejectedVO;

    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetMotivoRejectdCommand extends BaseCommand {
        private var type:String;

        public function GetMotivoRejectdCommand() {
            super(new GetMotivoRejectdCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(sessionId:Number, type:String):void {
            this.type = type;
            webService = QualityControlWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                //parametros: filtro, sessionId
                (webService as QualityControlWebServices).getMotivosRejected(sessionId);
            } else {
                //test
                //Generar codigo de test
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
			if (data != null) {
				data.removeItemAt(data.length - 1);
			}
            var motivosRejectdVOList:ArrayOfMotivoRejectedVO = transformToVO(data);

            (event as GetMotivoRejectdCommandResultEvent).motivoRejectedVOList = motivosRejectdVOList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfMotivoRejectedVO {
            var list:ArrayOfMotivoRejectedVO = new ArrayOfMotivoRejectedVO();
            var motivoRejectedVO:MotivoRejectedVO = null;

            for each (var dto:MotivoRejectedDTO in data) {
                motivoRejectedVO = new MotivoRejectedVO;

                motivoRejectedVO.fromDTO(dto);

                list.addMotivoRejectedVO(motivoRejectedVO);
            }

            return list;
        }
    }
}
