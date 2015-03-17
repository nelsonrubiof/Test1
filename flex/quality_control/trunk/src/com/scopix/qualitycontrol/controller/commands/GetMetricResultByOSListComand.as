package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServices;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.MetricResultDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfMetricResultVO;
    import com.scopix.qualitycontrol.model.events.GetMetricResultByOSListComandResultEvent;
    import com.scopix.qualitycontrol.model.vo.MetricResultVO;
    import com.scopix.qualitycontrol.model.vo.ObservedSituationFinishedVO;

    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetMetricResultByOSListComand extends BaseCommand {
        private var type:String;

        public function GetMetricResultByOSListComand() {
            super(new GetMetricResultByOSListComandResultEvent(), new WSFaultEvent());
        }

        public function execute(vo:ObservedSituationFinishedVO, sessionId:Number, type:String):void {
            this.type = type;
            webService = QualityControlWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                //parametros: filtro, sessionId
                (webService as QualityControlWebServices).getMetricResultByObservedSituation(vo.observedSituationId, sessionId);
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
            var metricResultList:ArrayOfMetricResultVO = transformToVO(data);

            (event as GetMetricResultByOSListComandResultEvent).metricResultList = metricResultList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfMetricResultVO {
            var list:ArrayOfMetricResultVO = new ArrayOfMetricResultVO();
            var metricResultVO:MetricResultVO = null;

            for each (var dto:MetricResultDTO in data) {
                metricResultVO = new MetricResultVO;

                metricResultVO.fromDTO(dto);

                list.addMetricResultVO(metricResultVO);
            }

            return list;
        }
    }
}
