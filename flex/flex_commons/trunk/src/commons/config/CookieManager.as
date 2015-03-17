package commons.config
{
	import flash.net.SharedObject;
	
	public class CookieManager
	{
		public static const LOGIN:String = "ScopixLoginInfo";
		public static const SNAPSHOT:String = "ScopixSnapshotBitmap";
		
		public function CookieManager()
		{
		}
		
		public static function GetCookie(type:String):SharedObject
		{
			return SharedObject.getLocal(type, "/");
		}	
	}
}