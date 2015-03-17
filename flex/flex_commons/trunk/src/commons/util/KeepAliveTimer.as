package commons.util {

	import commons.events.KeepAliveEvent;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	import mx.core.UIComponent;

	[Event(name="keep_alive_event", type="commons.events.KeepAliveEvent")]
	public class KeepAliveTimer extends UIComponent {
		public function KeepAliveTimer(interval:Number) {
			// interval in seconds
			var minuteTimer:Timer = new Timer(interval * 1000, 0);

			// designates listeners for the interval and completion events
			minuteTimer.addEventListener(TimerEvent.TIMER, onTick);

			// starts the timer ticking
			minuteTimer.start();
		}

		public function onTick(event:TimerEvent):void {
			dispatchEvent(new KeepAliveEvent());
		}
	}
}
