package com.scopix.test.webservice.client
{
    import com.scopix.test.webservice.WebServicesTest;
    
    public class WebServicesClientTest
    {
        private var url:String;
        private static var webService:WebServicesTest;
        private static var instance:WebServicesClientTest;
        
        public static function getInstance():WebServicesClientTest {
            if (instance == null) {
                instance = new WebServicesClientTest();
            }
            
            return instance;
        }
        
        public function getWebService():WebServicesTest {
             return webService;
        }
        
        public function config(urlParam:String):void {
            webService = new WebServicesTest();
            webService.getWebService().endpointURI = urlParam;
            webService.getWebService().requestTimeout = 60
        }

    }
}