package com.scopix.periscope.webservices.businessservices {

    public class ReportingWebServicesClient {
        private static var webService:ReportingWebServices;
        private static var instance:ReportingWebServicesClient;

        public static function getInstance():ReportingWebServicesClient {
            if (instance == null) {
                instance = new ReportingWebServicesClient();
            }

            return instance;
        }

        public function getWebService():ReportingWebServices {
            return webService;
        }

        public function config(wsdl:String):void {
            webService = new ReportingWebServices();
            webService.wsdl = wsdl;
            webService.showBusyCursor = true;
            webService.serviceControl.requestTimeout = 60;
        }
    }
}
