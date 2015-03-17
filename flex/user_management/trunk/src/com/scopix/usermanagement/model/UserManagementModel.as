package com.scopix.usermanagement.model
{
	import com.scopix.usermanagement.model.arrays.ArrayOfAreaTypeVO;
	import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;
	import com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO;
	import com.scopix.usermanagement.model.arrays.ArrayOfRolesGroupVO;
	import com.scopix.usermanagement.model.arrays.ArrayOfStoreVO;
	import com.scopix.usermanagement.model.vo.CorporateVO;
	import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
	
	import mx.collections.ArrayCollection;
	
	public class UserManagementModel
	{
		private static var _instance:UserManagementModel;
		
        public var statusList:ArrayCollection;
        
        //variables para addUser
        [Bindable]
        public var corporateTabPresent:Boolean;
        [Bindable]
        public var rolesScrollPosition:Number;
        [Bindable]
        public var corporatesScrollPosition:Number;
        [Bindable]
        public var storesScrollPosition:Number;
        [Bindable]
        public var areaTypesScrollPosition:Number;
        [Bindable]
        public var userName:String;
        [Bindable]
        public var fullName:String;
        [Bindable]
        public var jobPosition:String;
        [Bindable]
        public var stateIndex:Number;
        [Bindable]
        public var email:String;
        [Bindable]
        public var password:String;
        [Bindable]
        public var confirmPasswordValue:String;
        [Bindable]
        public var chkCorporatesSelected:Boolean;
        [Bindable]
        public var chkAreaTypesSelected:Boolean;
        [Bindable]
        public var chkRolesGroupSelected:Boolean;
        [Bindable]
        public var chkStoresSelected:Boolean;
        [Bindable]
        public var tabNavigatorIndex:Number;
        
        [Bindable]
        public var periscopeUserList:ArrayOfPeriscopeUserVO;
        
        [Bindable]
        public var clientsFilter:ArrayOfCorporateVO;

        [Bindable]
        public var storesFilter:ArrayOfStoreVO;
        
        /** lista de clientes disponibles para el usuario logueado **/
        [Bindable]
        public var corporateList:ArrayOfCorporateVO;

        /** corporates presentes en el usuario seleccionado **/
        [Bindable]
        public var corporateSelectedUserList:ArrayOfCorporateVO;
        
        [Bindable]
        public var storeList:ArrayOfStoreVO;

        [Bindable]
        public var areaTypeList:ArrayOfAreaTypeVO;
        
        [Bindable]
        public var roleList:ArrayOfRolesGroupVO;
        
        [Bindable]
        public var companyList:ArrayOfCorporateVO;
        
        [Bindable]
        public var usersToModifiedList:ArrayOfPeriscopeUserVO;
        
        [Bindable]
        public var massiveModificacionTitle:String;
        
        [Bindable]
        public var massiveModificacionTypeDescription:String;
        
        [Bindable]
        public var massiveModificationType:String;
        
        [Bindable]
        public var groupToModifiedList:ArrayCollection;

        [Bindable]
        public var currentCorporate:CorporateVO;
        
        [Bindable]
        public var currentUser:PeriscopeUserVO = new PeriscopeUserVO();
        
        [Bindable]
        public var confirmPassword:String;
        
        public var passwordTemporal:String;
        
        public var editMode:Boolean = false;
        
        public var periscopeUserListToDelete:ArrayOfPeriscopeUserVO;
        
        [Bindable]
        public var addUserNormalTitle:String = "";
        
        [Bindable]
        public var userListNormalTitle:String = "";
        
        //roles, tiendas, tipos de area presentes en el usuario
        public var roleListUser:ArrayOfRolesGroupVO;
        public var storeListUser:ArrayOfStoreVO;
        public var areaTypeListUser:ArrayOfAreaTypeVO;

        public static function getInstance():UserManagementModel {
            if (_instance == null) {
                _instance = new UserManagementModel();
            }
            return _instance;
        }
        
        public function UserManagementModel() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
	}
}