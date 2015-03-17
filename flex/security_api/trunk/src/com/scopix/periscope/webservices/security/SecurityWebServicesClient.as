package com.scopix.periscope.webservices.security {

    public class SecurityWebServicesClient {
        private static var webService:SecurityWebServices;
        private static var instance:SecurityWebServicesClient;

        public static function getInstance():SecurityWebServicesClient {
            if (instance == null) {
                instance = new SecurityWebServicesClient();
            }

            return instance;
        }

        public function getWebService():SecurityWebServices {
            return webService;
        }

        public function config(wsdl:String):void {
            webService = new SecurityWebServices();
            webService.wsdl = wsdl;
            webService.showBusyCursor = true;
			webService.serviceControl.requestTimeout = 60;
        }
    }
}
