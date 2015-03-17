package com.scopix.util
{
    import com.scopix.extractionplancustomizing.controller.commands.KeepAliveCommand;
    import com.scopix.security.model.SecurityModel;
    
    import flash.events.TimerEvent;
    import flash.utils.Timer;

	public class KeepAliveTimer
	{
	    private var securityModel:SecurityModel;
	    
		public function KeepAliveTimer(interval:Number)
        {
            securityModel = SecurityModel.getInstance();
            
            // interval in seconds
            var minuteTimer:Timer = new Timer(interval*1000, 0);
            
            // designates listeners for the interval and completion events
            minuteTimer.addEventListener(TimerEvent.TIMER, onTick);
            
            // starts the timer ticking
            minuteTimer.start();
        }

        public function onTick(event:TimerEvent):void 
        {
            var command:KeepAliveCommand = new KeepAliveCommand();
			command.execute(securityModel.sessionId);
        }
    }
}
