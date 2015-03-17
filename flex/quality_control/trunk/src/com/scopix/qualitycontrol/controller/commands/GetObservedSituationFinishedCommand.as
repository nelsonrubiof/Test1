package com.scopix.qualitycontrol.controller.commands {

    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServices;
    import com.scopix.periscope.webservices.businessservices.QualityControlWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.FilteringData;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ObservedSituationFinishedDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfObservedSituationFinishedVO;
    import com.scopix.qualitycontrol.model.events.GetObservedSituationFinishedComandResultEvent;
    import com.scopix.qualitycontrol.model.vo.ObservedSituationFinishedVO;

    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;

    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetObservedSituationFinishedCommand extends BaseCommand {
        private var type:String;

        public function GetObservedSituationFinishedCommand() {
            super(new GetObservedSituationFinishedComandResultEvent(), new WSFaultEvent());
        }

        public function execute(storeId:Number, areaId:Number, date:String, initialTime:String, endTime:String, sessionId:Number, type:String):void {
            this.type = type;
            webService = QualityControlWebServicesClient.getInstance().getWebService();

            var fd:FilteringData = new FilteringData();
            fd._date = new Date();
            fd.store = storeId;
            fd.area = areaId;
            fd.dateFilter = date;
            fd.initialTime = initialTime;
            fd.endTime = endTime;


            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                //parametros: filtro, sessionId
                (webService as QualityControlWebServices).getObservedSituationFinished(fd, sessionId);
            } else {
                //test
                //Generar codigo de test
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            //var data:ArrayOfObservedSituationFinishedDTO = evt.result;
            var data:ArrayCollection = evt.result as ArrayCollection;
			if (data != null) {
				data.removeItemAt(data.length - 1);
			}
            var osfList:ArrayOfObservedSituationFinishedVO = transformToVO(data);

            (event as GetObservedSituationFinishedComandResultEvent).osfList = osfList;
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfObservedSituationFinishedVO {
            var list:ArrayOfObservedSituationFinishedVO = new ArrayOfObservedSituationFinishedVO();
            var osfVO:ObservedSituationFinishedVO = null;

            for each (var dto:ObservedSituationFinishedDTO in data) {
                osfVO = new ObservedSituationFinishedVO();

                osfVO.fromDTO(dto);

                list.addObservedSituationFinishedVO(osfVO);
            }

            return list;
        }
    }
}
