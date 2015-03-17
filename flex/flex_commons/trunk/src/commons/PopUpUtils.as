package commons
{	
	import commons.components.ErrorPopup;
	import commons.components.LoadingPopup;
	import commons.components.ProgressBarPopUp;
	
	import mx.controls.Alert;
	import mx.managers.PopUpManager;
	import mx.resources.ResourceManager;
	
	public class PopUpUtils
	{
		private static var _instance:PopUpUtils;
		public var loadingPopUp:LoadingPopup;
		public var progressBarPopUp:ProgressBarPopUp;
		public var errorPopUp:ErrorPopup;
		
		function PopUpUtils() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
		}
		
		public static function getInstance():PopUpUtils{
			if(_instance == null){
				_instance = new PopUpUtils();
			}
			
			return _instance;
		}
		
		public function showLoading(show:Boolean):void{
			try{
				
				if(show){
				  	PopUpManager.addPopUp(loadingPopUp, loadingPopUp, true);
				  	PopUpManager.bringToFront(loadingPopUp);
				  	PopUpManager.centerPopUp(loadingPopUp);
				 }else{
					PopUpManager.removePopUp(loadingPopUp);
				 }
				 loadingPopUp.visible=show;
			 }catch(ex:Error){}
		}

		public function showProgressBar(show:Boolean):void{
			try{
				
				if(show){
				  	PopUpManager.addPopUp(progressBarPopUp, progressBarPopUp, true);
				  	PopUpManager.bringToFront(progressBarPopUp);
				  	PopUpManager.centerPopUp(progressBarPopUp);
				 }else{
					PopUpManager.removePopUp(progressBarPopUp);
				 }
				 progressBarPopUp.visible=show;
			 }catch(ex:Error){}
		}		

		public function showMessage(msg:String, title:String):void{
			try{
				var message:String = ResourceManager.getInstance().getString("resources", msg);
				if(message==null || message.length==0){
					message=msg;
				}
                Alert.show(message, ResourceManager.getInstance().getString('resources', title));
			 }catch(ex:Error){}
		}
		public function showMessageParam(msg:String, title:String, param:String):void{
			try{
				var message:String = ResourceManager.getInstance().getString("resources", msg);
				if(message==null || message.length==0){
					message=msg;
				}
				Alert.show(message + "\n" +param, ResourceManager.getInstance().getString('resources', title));
			}catch(ex:Error){}
		}
	}
}