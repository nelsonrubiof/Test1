package com.scopix.periscope.webservices.businessservices {

    public class QualityControlWebServicesClient {
        private static var webService:QualityControlWebServices;
        private static var instance:QualityControlWebServicesClient;

        public static function getInstance():QualityControlWebServicesClient {
            if (instance == null) {
                instance = new QualityControlWebServicesClient();
            }

            return instance;
        }

        public function getWebService():QualityControlWebServices {
            return webService;
        }

        public function config(wsdl:String):void {
            webService = new QualityControlWebServices();
            webService.wsdl = wsdl;
            webService.showBusyCursor = true;
            webService.serviceControl.requestTimeout = 60;
        }
    }
}
