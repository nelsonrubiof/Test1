package com.scopix.test.webservice
{
    import mx.rpc.AsyncToken;
    import mx.rpc.soap.AbstractWebService;
    
    public class BaseSecurityWebServicesTest extends AbstractWebService
    {
        public function BaseSecurityWebServicesTest()
        {
        }
        
		public function login(in0:String,in1:String,in2:Number):AsyncToken
		{
            return new AsyncToken(null);
		}
    }
}