package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServices;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.QualityEvaluationDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfQualityEvaluationVO;
    import com.scopix.qualitycontrol.model.events.RejectedSituationCommandResultEvent;
    import com.scopix.qualitycontrol.model.vo.QualityEvaluationVO;

    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class RejectedSituationCommand extends BaseCommand {
        private var type:String;

        public function RejectedSituationCommand() {
            super(new RejectedSituationCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(vos:ArrayOfQualityEvaluationVO, sessionId:Number, type:String):void {
            this.type = type;
            webService = QualityControlWebServicesClient.getInstance().getWebService();
            var dtos:ArrayCollection = new ArrayCollection();

            for each (var vo:QualityEvaluationVO in vos) {
                var dto:QualityEvaluationDTO = vo.toDTO();
                dtos.addItem(dto);
            }

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                //parametros: filtro, sessionId
                (webService as QualityControlWebServices).saveEvaluations(dtos, sessionId);
            } else {
                //test
                //Generar codigo de test
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            /*			var data:ArrayOfMetricResultDTO = evt.result;
                        var metricResultList:ArrayOfMetricResultVO= transformToVO(data);
                        (event as GetMetricResultByOSListComandResultEvent).metricResultList = metricResultList;
            */
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }
    }
}
