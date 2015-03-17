package commons.events
{
	import flash.events.Event;

	public class AbstractGenericEvent extends Event
	{
		private var _obj:Object;
		
		public function AbstractGenericEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		public function set obj(value:Object):void{
			_obj = value;	
		}
		
		public function get obj():Object{
			return _obj;
		}
		
	}
}