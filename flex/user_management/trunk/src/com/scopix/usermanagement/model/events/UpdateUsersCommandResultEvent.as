package com.scopix.usermanagement.model.events
{
    import commons.events.GenericEvent;
    
    public class UpdateUsersCommandResultEvent extends GenericEvent
    {
        public static var UPDATE_USERS_EVENT:String = "update_users_event";
        
        public function UpdateUsersCommandResultEvent()
        {
            super(UPDATE_USERS_EVENT);
        }
    }
}