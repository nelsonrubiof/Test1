package com.scopix.usermanagement.model.events {
    import com.scopix.usermanagement.model.arrays.ArrayOfRolesGroupVO;

    import commons.events.GenericEvent;

    public class GetRolesGroupListCommandResultEvent extends GenericEvent {
        public static var GET_ROLES_GROUP_LIST_EVENT:String = "get_roles_group_list_event";

        private var _rolesGroupList:ArrayOfRolesGroupVO;

        public function GetRolesGroupListCommandResultEvent(rolesGroup:ArrayOfRolesGroupVO = null) {
            super(GET_ROLES_GROUP_LIST_EVENT);
            _rolesGroupList = rolesGroup;
        }

        public function get rolesGroupList():ArrayOfRolesGroupVO {
            return _rolesGroupList;
        }

        public function set rolesGroupList(list:ArrayOfRolesGroupVO):void {
            _rolesGroupList = list;
        }
    }
}
