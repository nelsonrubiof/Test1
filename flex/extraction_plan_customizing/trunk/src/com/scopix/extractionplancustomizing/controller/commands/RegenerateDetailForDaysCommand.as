package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDayOfWeekVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfInt;
    import com.scopix.extractionplancustomizing.model.events.RegenerateDetailForDaysCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.DayOfWeekVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class RegenerateDetailForDaysCommand extends BaseCommand {
        public function RegenerateDetailForDaysCommand() {
            super(new RegenerateDetailForDaysCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcId:Number, daysVO:ArrayOfDayOfWeekVO, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            var days:ArrayOfInt = getDaysList(daysVO);

            if (!GlobalParameters.getInstance().test) {
                (webService as ExtractionPlanManagerWebServices).regenerateDetailForEPC(epcId, days, sessionId);
            }
        }

        private function resultWS(evt:ResultEvent):void {
            removeWSListener();
            dispatchEvent(event);
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            fault(evt);
            removeWSListener();
            removeCommandListener();
        }

        private function getDaysList(days:ArrayOfDayOfWeekVO):ArrayOfInt {
            var list:ArrayOfInt = new ArrayOfInt();

            for each (var d:DayOfWeekVO in days) {
                if (d.selected) {
                    list.addNumber(d.id);
                }
            }

            return list;
        }
    }
}
