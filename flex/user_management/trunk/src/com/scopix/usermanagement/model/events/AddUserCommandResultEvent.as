package com.scopix.usermanagement.model.events
{
    import commons.events.GenericEvent;
    
    public class AddUserCommandResultEvent extends GenericEvent
    {
        public static var ADD_USER_EVENT:String = "add_user_event";
        
        public function AddUserCommandResultEvent()
        {
            super(ADD_USER_EVENT);
        }
    }
}