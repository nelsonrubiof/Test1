package com.scopix.periscope.webservices.businessservices {

    public class CorporateWebServicesClient {
        private static var webService:CorporateWebServices;
        private static var instance:CorporateWebServicesClient;

        public static function getInstance():CorporateWebServicesClient {
            if (instance == null) {
                instance = new CorporateWebServicesClient();
            }

            return instance;
        }

        public function getWebService():CorporateWebServices {
            return webService;
        }

        public function config(wsdl:String):void {
            webService = new CorporateWebServices();
            webService.wsdl = wsdl;
            webService.showBusyCursor = true;
            webService.serviceControl.requestTimeout = 60;
        }
    }
}
