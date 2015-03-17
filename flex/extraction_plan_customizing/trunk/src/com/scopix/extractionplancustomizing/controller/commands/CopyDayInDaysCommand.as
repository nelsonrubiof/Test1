package com.scopix.extractionplancustomizing.controller.commands {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfDayOfWeekVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfInt;
    import com.scopix.extractionplancustomizing.model.events.CopyDayInDaysCommandResultEvent;
    import com.scopix.extractionplancustomizing.model.vo.DayOfWeekVO;
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServices;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanManagerWebServicesClient;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class CopyDayInDaysCommand extends BaseCommand {
        public function CopyDayInDaysCommand() {
            super(new CopyDayInDaysCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(epcId:Number, day:Number, daysVO:ArrayOfDayOfWeekVO, detailAction:String, sessionId:Number):void {
            webService = ExtractionPlanManagerWebServicesClient.getInstance().getWebService();

            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            var days:ArrayOfInt = getDaysList(daysVO);

            var copyDetail:Boolean = false;
            if (detailAction == "COPY") {
                copyDetail = true;
            }

            if (!GlobalParameters.getInstance().test) {
                //parametros: epcId, dia a copiar, dias donde se copiara el dia seleccionado, indicador sobre si debe copiarse el detalle, sessionId
                //si el detalle no se copia, entonces se regenerara por defecto.
                (webService as ExtractionPlanManagerWebServices).copyDayInDays(epcId, day, days, copyDetail, sessionId);
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
