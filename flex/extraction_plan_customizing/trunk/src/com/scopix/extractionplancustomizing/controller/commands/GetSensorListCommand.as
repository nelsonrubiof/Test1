package com.scopix.extractionplancustomizing.controller.commands
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    import com.scopix.extractionplancustomizing.model.events.GetSensorListCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.SensorVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    import com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO;
    import com.scopix.test.EPCDataTest;
    
    import commons.commands.BaseCommand;
    import commons.events.GenericErrorEvent;
    import commons.events.GenericEvent;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetSensorListCommand extends BaseCommand
    {
        public function GetSensorListCommand() {
            super(new GetSensorListCommandResultEvent(), new WSFaultEvent());
        }
        
        public function execute(epcId:Number, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();
            
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).getSensors(epcId, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            var data:ArrayCollection = evt.result as ArrayCollection;
            var sensorList:ArrayOfSensorVO = transformToVO(data);
            (event as GetSensorListCommandResultEvent).sensorList = sensorList;
            dispatchEvent(event);
            removeCommandListener();
        }
        
        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function transformToVO(data:ArrayCollection):ArrayOfSensorVO {
            var list:ArrayOfSensorVO = new ArrayOfSensorVO();
            var sensor:SensorVO = null;
            
            for each(var dto:SituationSensorDTO in data) {
                sensor = new SensorVO();
                sensor.fromDTO(dto);
                list.addSensorVO(sensor);
            }
            
            return list;
        }
    }
}