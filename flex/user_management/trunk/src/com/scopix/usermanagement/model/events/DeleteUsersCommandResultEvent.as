package com.scopix.usermanagement.model.events
{
    import commons.events.GenericEvent;
    
    public class DeleteUsersCommandResultEvent extends GenericEvent
    {
        public static var DELETE_USERS_EVENT:String = "delete_users_event";
        
        public function DeleteUsersCommandResultEvent()
        {
            super(DELETE_USERS_EVENT);
        }
    }
}