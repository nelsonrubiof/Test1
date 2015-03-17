/**
 * GetCurrentProcessResultEvent.as
 * This file was auto-generated from WSDL
 * Any change made to this file will be overwritten when the code is re-generated.
*/
package commons.events
{
	import flash.events.Event;
	
	import mx.rpc.soap.types.*;
	/**
	 * Typed event handler for the result of the operation
	 */
    
	public class LoadingEvent extends Event
	{
		/**
		 * The event type value
		 */
		public static const SHOW_LOADING:String="SHOW_LOADING";
		/**
		 * Constructor for the new event type
		 */
		public function LoadingEvent(show:Boolean)
		{
			super(SHOW_LOADING,false,false);
			_showLoading = show;
		}
        
		private var _showLoading:Boolean;
		public function get showLoading():Boolean
		{
			return _showLoading;
		}

		public function set showLoading(value:Boolean):void
		{
			_showLoading = value;
		}
	}
}