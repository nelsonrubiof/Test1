package com.scopix.qualitycontrol.controller.commands {
    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.security.SecurityWebServices;
    import com.scopix.periscope.webservices.security.SecurityWebServicesClient;
    import com.scopix.periscope.webservices.security.valueObjects.CorporateDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfCorporateVO;
    import com.scopix.qualitycontrol.model.events.GetCorporateListCommandResultEvent;
    import com.scopix.qualitycontrol.model.vo.CorporateVO;
    
    import commons.commands.BaseCommand;
    import commons.events.WSFaultEvent;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;

    public class GetCorporateListCommand extends BaseCommand {
        public function GetCorporateListCommand() {
            //se configuran los eventos a los cuales respondera este command
            super(new GetCorporateListCommandResultEvent(), new WSFaultEvent());
        }

        public function execute(userName:String, sessionId:Number):void {
            //se obtiene el webservice
            webService = SecurityWebServicesClient.getInstance().getWebService();

            //se agregan los listener para el webservice y al metodo indicado
            addWSListener(ResultEvent.RESULT, resultWS, faultWS);

            //verificamos si estamos en modo test
            if (!GlobalParameters.getInstance().test) {
                //parametros: userName, sessionId
                (webService as SecurityWebServices).getCorporatesForUser(userName, sessionId);
            } else {
                //test
            }
        }

        private function resultWS(evt:ResultEvent):void {
            //una ves recibida la respuesta lo primero que hacemos es remover los listener de los webservices
            removeWSListener();
            //sacamos la informacion necesaria retornada del webservice
            //var data:ArrayOfCorporateDTO = evt.result;
            var data:ArrayCollection = evt.result as ArrayCollection;
			/*if (data != null) {
				data.removeItemAt(data.length - 1);
			}*/
            var corporateList:ArrayOfCorporateVO = transformToVO(data);

			(event as GetCorporateListCommandResultEvent).corporateList = corporateList;
            //lanzamos el evento con la respuesta
            dispatchEvent(event);
            //se remueven los eventos asociados a este command, es decir ya no respondera a los eventos
            //configurados inicialmente
            removeCommandListener();
        }

        private function faultWS(evt:FaultEvent):void {
            //ante fallos en la llamada al webservice, primero lanzamos el evento con el fallo para informar al usuario
            fault(evt);
            //luego se remueven los listener de los webservices
            removeWSListener();
            //finalmente se remueven los eventos asociados a este command
            removeCommandListener();
        }

        /**
        * Funcion para transformar la informacion de respuesta del webservice en objetos conocidos en esta
        * aplicación (DTO to VO)
        **/
        private function transformToVO(data:ArrayCollection):ArrayOfCorporateVO {
            var corporateList:ArrayOfCorporateVO = new ArrayOfCorporateVO();
            var corporate:CorporateVO = null;

            if (data != null) {
                for each (var corporateDTO:CorporateDTO in data) {
                    corporate = new CorporateVO();

                    corporate.fromDTO(corporateDTO);

                    corporateList.addCorporateVO(corporate);
                }
            }

            return corporateList;
        }
    }
}
