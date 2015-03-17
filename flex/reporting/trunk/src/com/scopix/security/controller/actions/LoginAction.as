package com.scopix.security.controller.actions
{

    import com.adobe.crypto.SHA256;
    import com.scopix.global.GlobalParameters;
    import com.scopix.reporting.controller.actions.ReportingAction;
    import com.scopix.security.controller.commands.LoginCommand;
    import com.scopix.security.model.SecurityModel;
    import com.scopix.security.model.events.LoginCommandResultEvent;
    
    import commons.PopUpUtils;
    import commons.config.CookieManager;
    import commons.events.WSSecurityFaultEvent;
    
    import flash.net.SharedObject;
    
    import mx.core.Application;
    import mx.resources.ResourceManager;
    
    public class LoginAction
    {
	    private static var _instance:LoginAction;
	    private var securityModel:SecurityModel;

        public function LoginAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            securityModel = SecurityModel.getInstance();
        }
        
        public static function getInstance():LoginAction {
            if (_instance == null) {
                _instance = new LoginAction();
            }
            return _instance;
        }
        
	    public function loadUserCookie():void {
	        //Read the cookie in the client with the login and language information
            var userSO:SharedObject = CookieManager.GetCookie(CookieManager.LOGIN);
            
            //If the cookie is loaded, verify the username. If exists, assume that password too and show both
            //if (false) {
            if (userSO && userSO.data.user != null && userSO.data.user.length > 0) {
                //assume that if userSO exists, then have a username and password.
                securityModel.rememberLogin = true;
                
                //Validate because the cookie could be not configured correctly
           	    try {
           	        securityModel.userName = userSO.data.user;
           	        securityModel.password = userSO.data.pass;
    	            
    	            //load the language
    	            if (!userSO.data.hasOwnProperty("lang") || userSO.data.lang == "" || userSO.data.lang == "en_US") {
    	                Application.application.loginMenu.locale.languageGroup.selectedValue = "en_US";
    	                Application.application.loginMenu.locale.loadLanguage("en_US");
    	            }
    	            else {
    	                Application.application.loginMenu.locale.languageGroup.selectedValue = "es_ES";
    	                Application.application.loginMenu.locale.loadLanguage("es_ES");
    	            }
    	            
    	            securityModel.userLoaded = true;
    	        } catch (er:Error) {
    	            trace("error");
    	        }
            } else {
                securityModel.userLoaded = false;
            }
	    }

		public function loginSystem(user:String, pass:String, remember:Boolean):void {
		    GlobalParameters.getInstance().userName = user;
		    securityModel.rememberLogin = remember;
		    
		    PopUpUtils.getInstance().showLoading(true);
		    if (user.length == 0) {
		        PopUpUtils.getInstance().showLoading(false);
		        PopUpUtils.getInstance().showMessage("login.user.error","commons.error");
		        return;
		    }else if (pass.length == 0) {
		        PopUpUtils.getInstance().showLoading(false);
		        PopUpUtils.getInstance().showMessage("login.password.error","commons.error");
		        return;
		    } else {
		        
		        var encryptedPassword:String = "";

		        //verificando si los datos de usuario fueron cargados desde una cookie
		        //if (securityModel.userLoaded) {
		        if (user == securityModel.userName && pass == securityModel.password) {
		            encryptedPassword = securityModel.password;
		        } else {
		            //encriptando password
		            encryptedPassword = SHA256.hash(pass);
		        }
		        
		        //guardando variables en el modelo
		        securityModel.userName = user;
		        securityModel.passwordToSaveInCookie = encryptedPassword;
		        
		        var loginCommand:LoginCommand = new LoginCommand();
		        loginCommand.addCommandListener(loginResult, securityError);
		        loginCommand.execute(user, encryptedPassword);
		    }
		}

        public function loginResult(event:LoginCommandResultEvent):void
        {
            securityModel.sessionId = event.sessionId;

	      	//Create the cookie
	      	var userSO:SharedObject = CookieManager.GetCookie(CookieManager.LOGIN);
	      	
	      	if(securityModel.rememberLogin) {
      			//if the user selected the option to remember 
      			//write the cookie with the login and language information
		        userSO.data.user = securityModel.userName;
		        userSO.data.pass = securityModel.passwordToSaveInCookie;
		        userSO.data.lang = ResourceManager.getInstance().localeChain[0];

	      	} else {
	        	//else clear the cookie because the user dont want
	        	//to mantain the information
	        	userSO.clear();
	      	}

	        //Flush the cookie to save the changes
	        userSO.flush();
	        
	        //changing to reporting action
	        var epcAction:ReportingAction = ReportingAction.getInstance();
	        
	        epcAction.showSelectCorporate();
        }
        
        public function securityError(event:WSSecurityFaultEvent):void {
            var message:String = event.message;
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage(message, 'commons.error');
        }
    }
}