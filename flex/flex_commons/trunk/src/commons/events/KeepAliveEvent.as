package commons.events {

	public class KeepAliveEvent extends GenericEvent {
		public static var KEEP_ALIVE_EVENT:String = "keep_alive_event";

		public function KeepAliveEvent() {
			super(KEEP_ALIVE_EVENT);
		}
	}
}