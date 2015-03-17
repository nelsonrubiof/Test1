package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServices;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfEvidenceVO;
    import com.scopix.qualitycontrol.model.events.GetEvidenceVOByMetricResultListComandResultEvent;
    import com.scopix.qualitycontrol.model.vo.EvidenceVO;

    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetEvidenceVOByMetricResultListComand extends BaseCommand {
        private var type:String;

        public function GetEvidenceVOByMetricResultListComand() {
            super(new GetEvidenceVOByMetricResultListComandResultEvent(), new WSFaultEvent());
        }

        public function execute(metricId:Number, situationFinishedId:Number, sessionId:Number, type:String):void {
            this.type = type;
            webService = QualityControlWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                //parametros: filtro, sessionId
                (webService as QualityControlWebServices).getEvidenceDTOByMetricResult(metricId, situationFinishedId, sessionId);
            } else {
                //test
                //Generar codigo de test
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
			if (data != null) {
				data.removeItemAt(data.length-1);
			}
            var evidenceVOList:ArrayOfEvidenceVO = transformToVO(data);

            (event as GetEvidenceVOByMetricResultListComandResultEvent).evidenceVOList = evidenceVOList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfEvidenceVO {
            var list:ArrayOfEvidenceVO = new ArrayOfEvidenceVO();
            var evidenceVO:EvidenceVO = null;

            for each (var dto:EvidenceDTO in data) {
                evidenceVO = new EvidenceVO;

                evidenceVO.fromDTO(dto);

                list.addEvidenceVO(evidenceVO);
            }

            return list;
        }
    }
}
