package commons.config
{
	import mx.formatters.DateFormatter;
	
	public class CustomDateFormatter
	{		
		[Bindable]
		public static var currentFormat:String;
		private static var _instance:CustomDateFormatter;
		
		public function CustomDateFormatter() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
		}

		public static function getInstance():CustomDateFormatter {
			if(_instance == null){
				_instance = new CustomDateFormatter();
			}
			return _instance;
		}

		public function setLanguageFormat(lang:String):void
		{
			if (lang.substr(0,2) == "en")
	  			currentFormat = "MM/DD/YYYY";
	  		else
	  			currentFormat = "DD/MM/YYYY"; 
		}
		
		public function format(o:Object):String
		{
			var df:DateFormatter = new DateFormatter();
			df.formatString = currentFormat;
	  		return df.format(o);
		}
		
		public function getDateTranslatedId(dt:Date):Number
		{
		  	var df:DateFormatter = new DateFormatter();
		  	df.formatString = "YYYYMMDD";
		  	return Number(df.format(dt));
		}

	}
}