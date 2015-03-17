package com.scopix.periscope.webservices.businessservices {

    public class ExtractionPlanManagerWebServicesClient {
        private static var webService:ExtractionPlanManagerWebServices;
        private static var instance:ExtractionPlanManagerWebServicesClient;

        public static function getInstance():ExtractionPlanManagerWebServicesClient {
            if (instance == null) {
                instance = new ExtractionPlanManagerWebServicesClient();
            }

            return instance;
        }

        public function getWebService():ExtractionPlanManagerWebServices {
            return webService;
        }

        public function config(wsdl:String):void {
            webService = new ExtractionPlanManagerWebServices();
            webService.wsdl = wsdl;
            webService.showBusyCursor = true;
            webService.serviceControl.requestTimeout = 60;
        }
    }
}
