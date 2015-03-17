package com.scopix.usermanagement.controller.actions
{
    import com.adobe.crypto.SHA256;
    import com.scopix.global.ApplicationStates;
    import com.scopix.global.MassiveModificationType;
    import com.scopix.security.model.SecurityModel;
    import com.scopix.usermanagement.controller.commands.AddUserCommand;
    import com.scopix.usermanagement.controller.commands.DeleteUsersCommand;
    import com.scopix.usermanagement.controller.commands.GetAreaTypesForCorporateCommand;
    import com.scopix.usermanagement.controller.commands.GetAreaTypesForUserCommand;
    import com.scopix.usermanagement.controller.commands.GetClientListCommand;
    import com.scopix.usermanagement.controller.commands.GetCorporatesForUserCommand;
    import com.scopix.usermanagement.controller.commands.GetRolesGroupForUserCommand;
    import com.scopix.usermanagement.controller.commands.GetRolesGroupListCommand;
    import com.scopix.usermanagement.controller.commands.GetStoresForCorporateCommand;
    import com.scopix.usermanagement.controller.commands.GetStoresForUserCommand;
    import com.scopix.usermanagement.controller.commands.GetUserListCommand;
    import com.scopix.usermanagement.controller.commands.UpdateUsersCommand;
    import com.scopix.usermanagement.model.UserManagementModel;
    import com.scopix.usermanagement.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfRolesGroupVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfStoreVO;
    import com.scopix.usermanagement.model.events.AddUserCommandResultEvent;
    import com.scopix.usermanagement.model.events.DeleteUsersCommandResultEvent;
    import com.scopix.usermanagement.model.events.GetAreaTypesCommandResultEvent;
    import com.scopix.usermanagement.model.events.GetClientListCommandResultEvent;
    import com.scopix.usermanagement.model.events.GetCorporatesCommandResultEvent;
    import com.scopix.usermanagement.model.events.GetRolesGroupListCommandResultEvent;
    import com.scopix.usermanagement.model.events.GetStoresCommandResultEvent;
    import com.scopix.usermanagement.model.events.GetUserListCommandResultEvent;
    import com.scopix.usermanagement.model.events.UpdateUsersCommandResultEvent;
    import com.scopix.usermanagement.model.vo.AreaTypeVO;
    import com.scopix.usermanagement.model.vo.CorporateVO;
    import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
    import com.scopix.usermanagement.model.vo.RolesGroupVO;
    import com.scopix.usermanagement.model.vo.StoreVO;
    
    import commons.PopUpUtils;
    import commons.events.GenericErrorEvent;
    
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.events.CloseEvent;
    import mx.resources.ResourceManager;
    
    public class UserManagementAction
    {
        private static var _instance:UserManagementAction;

        //model
        public var umModel:UserManagementModel;
        public var securityModel:SecurityModel;
        
        
        public static function getInstance():UserManagementAction {
            if (_instance == null) {
                _instance = new UserManagementAction();
            }
            
            return _instance;
        }
        
        public function UserManagementAction() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
            securityModel = SecurityModel.getInstance();
            umModel = UserManagementModel.getInstance();
        }
        
        public function logout():void {
        	umModel.clientsFilter = null;
            Application.application.currentState = ApplicationStates.LOGIN;
        }
        
        /****************************************************************************** 
        * Metodos para usuarios normales
        *******************************************************************************/
        public function selectClientView():void {
        	
        	var command:GetClientListCommand = new GetClientListCommand();
        	command.addCommandListener(getClientListResultNormal, applicationError);
        	
            command.execute(securityModel.userName, securityModel.sessionId);
        }
        
        private function getClientListResultNormal(event:GetClientListCommandResultEvent):void {
            
            umModel.corporateList = event.clientList;
            
            umModel.clientsFilter = umModel.corporateList.clone();

            
            //cargando grupo de roles asociados al usuario
            var command:GetRolesGroupForUserCommand = new GetRolesGroupForUserCommand();
            command.addCommandListener(getRolesGroupListCommandResult, applicationError);
            
            command.execute(securityModel.userName, securityModel.sessionId);
            
        }
        
        private function getRolesGroupListCommandResult(event:GetRolesGroupListCommandResultEvent):void {
            umModel.roleList = event.rolesGroupList;
            
			Application.application.currentState = ApplicationStates.CLIENT_SELECT;
			PopUpUtils.getInstance().showLoading(false);
        }
		
        public function userListNormal(corporate:CorporateVO):void {
            if (corporate == null || corporate.corporateId == -1) {
                PopUpUtils.getInstance().showMessage('clientselect.must_select_client', 'commons.warning');
                return;
            }

            PopUpUtils.getInstance().showLoading(true);
            umModel.currentCorporate = corporate;
            
            umModel.corporateTabPresent = false;
            
            if (corporate.corporateId == 0) {
            	umModel.corporateTabPresent = true;
            }
            
            umModel.storesFilter = corporate.stores;
            
            var command:GetUserListCommand = new GetUserListCommand();
            command.addCommandListener(getUserListResultNormal, applicationError);
            
            command.execute(corporate.corporateId, securityModel.sessionId);
        }
        
        public function getUserListResultNormal(event:GetUserListCommandResultEvent):void {

            umModel.periscopeUserList = event.periscopeUserList;
            umModel.stateIndex = -1;
            
            umModel.userListNormalTitle = umModel.currentCorporate.description;
			
			
			var command:GetStoresForCorporateCommand = new GetStoresForCorporateCommand();
			command.addCommandListener(getStoresForCorporateCommandResult, applicationError);
			command.execute(umModel.currentCorporate.corporateId, securityModel.sessionId);
        }

		private function getStoresForCorporateCommandResult(event:GetStoresCommandResultEvent):void {
			umModel.currentCorporate.stores = event.stores;
			
			var command:GetAreaTypesForCorporateCommand = new GetAreaTypesForCorporateCommand();
			command.addCommandListener(getAreaTypesForCorporateCommandResult, applicationError);
			command.execute(umModel.currentCorporate.corporateId, securityModel.sessionId);
		}
		
		private function getAreaTypesForCorporateCommandResult(event:GetAreaTypesCommandResultEvent):void {
			umModel.currentCorporate.areaTypes = event.areaTypes;
			
			Application.application.currentState = ApplicationStates.USER_LIST_NORMAL;
			PopUpUtils.getInstance().showLoading(false);
		}
		
        public function changeClient():void {
            Application.application.currentState = ApplicationStates.CLIENT_SELECT;
        }
        
        /** este metodo es para modificar la variable a un valor cualquiera de las posiciones de scroll de los permisos
        * que se asignan al usuario (vista addUser), con el 
        * objetivo de que en la entrada de modificacion/creacion de usuario, los scroll se ajusten a cero,
        * ya que si dichas variables no cambian su valor inicial (0), el bindable no se lanza y por lo tanto
        * el scroll se mantiene en su ultimo estado 
        * **/
        private function modifyScrollPositions():void {
            umModel.rolesScrollPosition = 1;
            umModel.corporatesScrollPosition = 1;
            umModel.storesScrollPosition = 1;
            umModel.areaTypesScrollPosition = 1;
        }
        
        public function addUserNormalView():void {
            umModel.editMode = false;
            
            umModel.rolesScrollPosition = 0;
            umModel.corporatesScrollPosition = 0;
            umModel.storesScrollPosition = 0;
            umModel.areaTypesScrollPosition = 0;

            umModel.currentUser = new PeriscopeUserVO();

            //cargar datos
            umModel.storeList = umModel.currentCorporate.stores;
            umModel.areaTypeList = umModel.currentCorporate.areaTypes;
                        
            markRolesGroups();
            markStores();
            markAreaTypes();
            
            //cambiar vista
            Application.application.currentState = ApplicationStates.ADD_USER_NORMAL;
            umModel.addUserNormalTitle = ResourceManager.getInstance().getString('resources','addusernormal.add.title');
            loadUserData(umModel.currentUser);
            umModel.chkRolesGroupSelected = false;
            umModel.chkStoresSelected = false;
            umModel.chkAreaTypesSelected = false;
            umModel.tabNavigatorIndex = 0;
        }
        
        public function saveUser(user:PeriscopeUserVO):void {
            //validar datos
            if (user.userState == null || user.userState.ordinal == 0) {
                Alert.show(ResourceManager.getInstance().getString('resources','addusernormal.select_state'));
                return;
            }
            
            if (user.password != umModel.confirmPassword) {
                Alert.show(ResourceManager.getInstance().getString('resources','addusernormal.same_password'));
                return;
            }
            PopUpUtils.getInstance().showLoading(true);
            
            
            //agregando roles, stores, areatype seleccionados.
            user.stores = new ArrayOfStoreVO();
            user.areaTypes = new ArrayOfAreaTypeVO();
            user.rolesGroups = new ArrayOfRolesGroupVO();
            
            for each (var roleGroup:RolesGroupVO in umModel.roleList) {
                if (roleGroup.selected) {
                    user.rolesGroups.addRolesGroupVO(roleGroup);
                }
            }
            
            user.mainCorporateId = umModel.currentCorporate.corporateId;

            if (umModel.currentCorporate.corporateId == 0) {
            	//recorrer los corporates y agregarlos al usuario
            	for each (var co:CorporateVO in umModel.corporateList) {
            		if (co.selected) {
            			user.corporates.addCorporateVO(co);
            		}
            	}
            	
            } else {
            	//asignar el corporate del seleccionado al entrar a la aplicación
            	user.corporates.addCorporateVO(umModel.currentCorporate);
            }
            
            for each (var store:StoreVO in umModel.storeList) {
                if (store.selected) {
                    user.stores.addStoreVO(store);
                }
            }
            
            for each (var areaType:AreaTypeVO in umModel.areaTypeList) {
                if (areaType.selected) {
                    user.areaTypes.addAreaTypeVO(areaType);
                }
            }
            
            var encryptedPassword:String;
            if (umModel.editMode) {
                if (umModel.passwordTemporal != user.password) {
                    //encriptando password
                    encryptedPassword = SHA256.hash(user.password);
                    user.password = encryptedPassword;
                }
                
                user.userId = umModel.currentUser.userId;

                var updateCommand:UpdateUsersCommand = new UpdateUsersCommand();
                updateCommand.addCommandListener(updateUsersResultNormal, applicationError);
                
                updateCommand.execute(new ArrayOfPeriscopeUserVO([user]), securityModel.sessionId);
            } else {
                //encriptando password
                encryptedPassword = SHA256.hash(user.password);
                user.password = encryptedPassword;
                
                var addUserCommand:AddUserCommand = new AddUserCommand();
                addUserCommand.addCommandListener(addUserResultNormal, applicationError);
                
                addUserCommand.execute(user, securityModel.sessionId);
            }
        }
        
        public function updateUsersResultNormal(event:UpdateUsersCommandResultEvent):void {
			modifyScrollPositions();
			
            var command:GetUserListCommand = new GetUserListCommand();
            command.addCommandListener(getUserListResultNormal, applicationError);
            
            command.execute(umModel.currentCorporate.corporateId, securityModel.sessionId);
        }
        
        public function addUserResultNormal(event:AddUserCommandResultEvent):void {
            modifyScrollPositions();
            
            var command:GetUserListCommand = new GetUserListCommand();
            command.addCommandListener(getUserListResultNormal, applicationError);
            
            command.execute(umModel.currentCorporate.corporateId, securityModel.sessionId);
        }
        
        public function backUserListNormal():void {
        	modifyScrollPositions();
            //volver al listado de usuarios
            umModel.editMode = false;
            umModel.stateIndex = -1;
            Application.application.currentState = ApplicationStates.USER_LIST_NORMAL;
        }
        
        public function editUserNormalView(periscopeUser:PeriscopeUserVO):void {
            if (periscopeUser == null) {
                //Debe marcar un usuario para editar
                PopUpUtils.getInstance().showMessage('userlistnormal.select_user_edit', 'commons.error');
                return;
            }
            umModel.rolesScrollPosition = 0;
            umModel.corporatesScrollPosition = 0;
            umModel.storesScrollPosition = 0;
            umModel.areaTypesScrollPosition = 0;
            
			//cargando datos usuariof
			umModel.currentUser = periscopeUser.clone();
			umModel.passwordTemporal = umModel.currentUser.password;
			
			//cargar roles, tiendas y areas del usuario seleccionado
			var comm:GetRolesGroupForUserCommand = new GetRolesGroupForUserCommand();
			comm.addCommandListener(getRolesForUserCommandResult, applicationError);
			comm.execute(umModel.currentUser.userName, securityModel.sessionId);
        }
		
		private function getRolesForUserCommandResult(event:GetRolesGroupListCommandResultEvent):void {
			umModel.currentUser.rolesGroups = event.rolesGroupList;
			
			var comm:GetCorporatesForUserCommand = new GetCorporatesForUserCommand();
			comm.addCommandListener(getCorporatesForUserCommandResult, applicationError);
			comm.execute(umModel.currentUser.userName, securityModel.sessionId);
		}
		
		private function getCorporatesForUserCommandResult(event:GetCorporatesCommandResultEvent):void {
			umModel.currentUser.corporates = event.corporates;
			
			var comm:GetStoresForUserCommand = new GetStoresForUserCommand();
			comm.addCommandListener(getStoresForUserCommandResult, applicationError);
			comm.execute(umModel.currentUser.userName, securityModel.sessionId);
		}
		
		private function getStoresForUserCommandResult(event:GetStoresCommandResultEvent):void {
			umModel.currentUser.stores = event.stores;
			
			var comm:GetAreaTypesForUserCommand = new GetAreaTypesForUserCommand();
			comm.addCommandListener(getAreaTypesForUserCommandResult, applicationError);
			comm.execute(umModel.currentUser.userName, securityModel.sessionId);
		}
		
		private function getAreaTypesForUserCommandResult(event:GetAreaTypesCommandResultEvent):void {
			umModel.currentUser.areaTypes = event.areaTypes;
			
			umModel.editMode = true;
			
			umModel.storeList = umModel.currentCorporate.stores;
			umModel.areaTypeList = umModel.currentCorporate.areaTypes;
			
			//cargando listas de roles, tiendas y tipos de area y marcando los presentes en el usuario
			if (umModel.corporateTabPresent) {
				markCorporates();
			}
			markRolesGroups();
			markStores();
			markAreaTypes();
			
			Application.application.currentState = ApplicationStates.ADD_USER_NORMAL;
			umModel.addUserNormalTitle = ResourceManager.getInstance().getString('resources','addusernormal.edit.title');
			//Application.application.addUserNormal.refresh();
			loadUserData(umModel.currentUser);
			umModel.chkRolesGroupSelected = false;
			umModel.chkStoresSelected = false;
			umModel.chkAreaTypesSelected = false;
			umModel.tabNavigatorIndex = 0;
		}
        
        public function deleteUsersNormal(periscopeUserListParam:Array):void {
            //advertir de la eliminacion del usuario (o los usuarios) seleccionados
            if (periscopeUserListParam == null || periscopeUserListParam.length == 0) {
                //Debe marcar al menos un usuario para eliminar
                PopUpUtils.getInstance().showMessage('userlistnormal.select_user_delete', 'commons.error');
                return;
            }
            
            umModel.periscopeUserListToDelete = new ArrayOfPeriscopeUserVO(periscopeUserListParam);
            
            if (periscopeUserListParam.length == 1) {
                Alert.show(ResourceManager.getInstance().getString('resources','userlistnormal.warning_user_delete'),
                	ResourceManager.getInstance().getString('resources','commons.warning'), 
                	Alert.YES | Alert.NO, null, deleteUserChoice);
            } else if (periscopeUserListParam.length > 1) {
                Alert.show(ResourceManager.getInstance().getString('resources','userlistnormal.warning_users_delete'),
                	ResourceManager.getInstance().getString('resources','commons.warning'), 
                	Alert.YES | Alert.NO, null, deleteUserChoice);
            }
        }
        
        private function deleteUserChoice(event:CloseEvent):void {
            //verificar respuesta
            if (event.detail == Alert.YES) {
                //eliminar el listado de usuarios y recargar la lista de usuarios
                var command:DeleteUsersCommand = new DeleteUsersCommand();
                command.addCommandListener(deleteUserResultNormal, applicationError);
                
                command.execute(umModel.periscopeUserListToDelete, securityModel.sessionId);
            }
        }
        
        private function deleteUserResultNormal(event:DeleteUsersCommandResultEvent):void {
            PopUpUtils.getInstance().showLoading(true);

            var command:GetUserListCommand = new GetUserListCommand();
            command.addCommandListener(getUserListResultNormal, applicationError);
            
            command.execute(umModel.currentCorporate.corporateId, securityModel.sessionId);
        }

        public function cloneUserNormal(periscopeUser:PeriscopeUserVO):void {
            if (periscopeUser == null) {
                PopUpUtils.getInstance().showMessage('userlistnormal.select_user_clone', 'commons.error');
                return;
            }
            umModel.editMode = false;
            
            //esta asignacion es necesaria para marcar los roles, stores y areatypes del nuevo usuario.
            umModel.currentUser = periscopeUser;

            //cargar datos
            umModel.storeList = umModel.currentCorporate.stores;
            umModel.areaTypeList = umModel.currentCorporate.areaTypes;
            
            markRolesGroups();
            markStores();
            markAreaTypes();
            
            //se crea el nuevo usuario
            umModel.currentUser = new PeriscopeUserVO();

            umModel.currentUser.jobPosition = periscopeUser.jobPosition;
            umModel.currentUser.mainCorporateId = periscopeUser.mainCorporateId;
            umModel.currentUser.corporates = periscopeUser.corporates;
            
            //cambiar vista
            Application.application.currentState = ApplicationStates.ADD_USER_NORMAL;
 
            //Application.application.addUserNormal.refresh();
            loadUserData(umModel.currentUser);
            umModel.chkRolesGroupSelected = false;
            umModel.chkStoresSelected = false;
            umModel.chkAreaTypesSelected = false;
            umModel.tabNavigatorIndex = 0;
        }
        
        /******************************************************************************
        * Metodos comunes
        *******************************************************************************/
        public function massiveModification(massiveModificationTypeParam:String, selectedUserList:Array):void {
            umModel.massiveModificationType = massiveModificationTypeParam;
            
            if (selectedUserList.length == 0) {
                PopUpUtils.getInstance().showMessage('massivemodification.select_users', 'commons.warning');
                return;
            } else {
                //registerClassAlias("com.scopix.usermanagement.model.arrays.ArrayOfPeriscopeUserVO", ArrayOfPeriscopeUserVO);
                //usersToModifiedList = ArrayOfPeriscopeUserVO(ObjectUtil.copy(selectedUserList));
                //usersToModifiedList = (UtilityFunctions.clone(new ArrayCollection(selectedUserList).source) as ArrayOfPeriscopeUserVO);
                //var obj:Object = Cloner.byteCopy(new ArrayCollection(selectedUserList).source);
                umModel.usersToModifiedList = new ArrayOfPeriscopeUserVO(selectedUserList).clone();
                
                for each (var periscopeUser:PeriscopeUserVO in umModel.usersToModifiedList) {
                    periscopeUser.selected = true;
                }
                
                if (umModel.massiveModificationType == MassiveModificationType.CLIENTS) {
                    umModel.massiveModificacionTypeDescription = ResourceManager.getInstance().getString('resources','massivemodification.groupToModified.clients');
                    disableCorporateList();
                    umModel.groupToModifiedList = umModel.corporateList;
                    
                } else if (umModel.massiveModificationType == MassiveModificationType.STORES) {
                    umModel.massiveModificacionTypeDescription = ResourceManager.getInstance().getString('resources','massivemodification.groupToModified.stores');
                    disableStoreList();
                    umModel.groupToModifiedList = umModel.currentCorporate.stores;
                    
                } else if (umModel.massiveModificationType == MassiveModificationType.AREATYPE) {
                    umModel.massiveModificacionTypeDescription = ResourceManager.getInstance().getString('resources','massivemodification.groupToModified.areaTypes');
                    disableAreaType();
                    umModel.groupToModifiedList = umModel.currentCorporate.areaTypes;
                    
                } else if (umModel.massiveModificationType == MassiveModificationType.ROLES) {
                    umModel.massiveModificacionTypeDescription = ResourceManager.getInstance().getString('resources','massivemodification.groupToModified.roles');
                    disableRoleGroup();
                    umModel.groupToModifiedList = umModel.roleList;
                    
                }
                Application.application.currentState = ApplicationStates.MASSIVE_MODIFICATION;
            }
        }
        
        public function addToSelected():void {
            PopUpUtils.getInstance().showLoading(false);
            
            //validando que haya seleccionado al menos uno
            var selected:Boolean = false;
            for each (var obj:Object in umModel.groupToModifiedList) {
                if (obj.selected) {
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage("massivemodification.select_elements","commons.warning");
                return;
            }
            
            PopUpUtils.getInstance().showLoading(true);
            
            for each (var periscopeUser:PeriscopeUserVO in umModel.usersToModifiedList) {
                if (umModel.massiveModificationType == MassiveModificationType.ROLES) {
                    var rgToAdd:ArrayOfRolesGroupVO = new ArrayOfRolesGroupVO();
                    for each (var rg:RolesGroupVO in umModel.groupToModifiedList) {
                        if (rg.selected) {
                            var addRG:Boolean = true;
                            for each (var rgUser:RolesGroupVO in periscopeUser.rolesGroups) {
                                if (rg.rolesGroupId == rgUser.rolesGroupId) {
                                    //rol existente en el usuario
                                    addRG = false;
                                    break;
                                }
                            }
                            if (addRG) {
                                rgToAdd.addRolesGroupVO(rg);
                            }
                        }
                    }
                    for each (var roleGroup:RolesGroupVO in rgToAdd) {
                        periscopeUser.rolesGroups.addRolesGroupVO(roleGroup);
                    }
                    
                } else if (umModel.massiveModificationType == MassiveModificationType.CLIENTS) {
                    var coToAdd:ArrayOfCorporateVO  = new ArrayOfCorporateVO();
                    for each (var co:CorporateVO in umModel.groupToModifiedList) {
                        if (co.selected) {
                            var addCO:Boolean = true;
                            for each (var coUser:CorporateVO in periscopeUser.corporates) {
                                if (co.corporateId == coUser.corporateId) {
                                    //rol existente en el usuario
                                    addCO = false;
                                    break;
                                }
                            }
                            if (addCO) {
                                coToAdd.addCorporateVO(co);
                            }
                        }
                    }
                    for each (var corporate:CorporateVO in coToAdd) {
                        periscopeUser.corporates.addCorporateVO(corporate);
                    }
                    
                } else if (umModel.massiveModificationType == MassiveModificationType.STORES) {
                    var stToAdd:ArrayOfStoreVO  = new ArrayOfStoreVO();
                    for each (var st:StoreVO in umModel.groupToModifiedList) {
                        if (st.selected) {
                            var addST:Boolean = true;
                            for each (var stUser:StoreVO in periscopeUser.stores) {
                                if (st.storeId == stUser.storeId) {
                                    //rol existente en el usuario
                                    addST = false;
                                    break;
                                }
                            }
                            if (addST) {
                                stToAdd.addStoreVO(st);
                            }
                        }
                    }
                    for each (var store:StoreVO in stToAdd) {
                        periscopeUser.stores.addStoreVO(store);
                    }
                    
                } else if (umModel.massiveModificationType == MassiveModificationType.AREATYPE) {
                    var atToAdd:ArrayOfAreaTypeVO = new ArrayOfAreaTypeVO();
                    for each (var at:AreaTypeVO in umModel.groupToModifiedList) {
                        if (at.selected) {
                            var addAT:Boolean = true;
                            for each (var atUser:AreaTypeVO in periscopeUser.areaTypes) {
                                if (at.areaTypeId == atUser.areaTypeId) {
                                    //rol existente en el usuario
                                    addAT = false;
                                    break;
                                }
                            }
                            if (addAT) {
                                atToAdd.addAreaTypeVO(at);
                            }
                        }
                    }
                    for each (var areaType:AreaTypeVO in atToAdd) {
                        periscopeUser.areaTypes.addAreaTypeVO(areaType);
                    }
                }
            }
            
            //llamar al command para actualizar
            var command:UpdateUsersCommand = new UpdateUsersCommand();
            command.addCommandListener(updateUsersResultNormal, applicationError);
            
            command.execute(umModel.usersToModifiedList, securityModel.sessionId);
        }
        
        public function removeToSelected():void {
            PopUpUtils.getInstance().showLoading(false);
            
            //validando que haya seleccionado al menos uno
            var selected:Boolean = false;
            for each (var obj:Object in umModel.groupToModifiedList) {
                if (obj.selected) {
                    selected = true;
                    break;
                }
            }
            if (!selected) {
                PopUpUtils.getInstance().showLoading(false);
                PopUpUtils.getInstance().showMessage("massivemodification.select_elements","commons.warning");
                return;
            }

            PopUpUtils.getInstance().showLoading(true);
            
            for each (var periscopeUser:PeriscopeUserVO in umModel.usersToModifiedList) {
                if (umModel.massiveModificationType == MassiveModificationType.ROLES) {
                    for each (var rg:RolesGroupVO in umModel.groupToModifiedList) {
                        if (rg.selected) {
                            for (var i:Number = 0; i < periscopeUser.rolesGroups.length; i++) {
                                if (rg.rolesGroupId == periscopeUser.rolesGroups.getRolesGroupVOAt(i).rolesGroupId) {
                                    periscopeUser.rolesGroups.removeItemAt(i);
                                    break;
                                }
                            }
                        }
                    }
                } else if (umModel.massiveModificationType == MassiveModificationType.CLIENTS) {
                    for each (var co:CorporateVO in umModel.groupToModifiedList) {
                        if (co.selected) {
                            for (var j:Number = 0; j < periscopeUser.corporates.length; j++) {
                                if (co.corporateId == periscopeUser.corporates.getCorporateVOAt(j).corporateId) {
                                    periscopeUser.corporates.removeItemAt(j);
                                    break;
                                }
                            }
                        }
                    }
                } else if (umModel.massiveModificationType == MassiveModificationType.STORES) {
                    for each (var st:StoreVO in umModel.groupToModifiedList) {
                        if (st.selected) {
                            for (var h:Number = 0; h < periscopeUser.stores.length; h++) {
                                if (st.storeId == periscopeUser.stores.getStoreVOAt(h).storeId) {
                                    periscopeUser.stores.removeItemAt(h);
                                    break;
                                }
                            }
                        }
                    }
                } else if (umModel.massiveModificationType == MassiveModificationType.AREATYPE) {
                    for each (var at:AreaTypeVO in umModel.groupToModifiedList) {
                        if (at.selected) {
                            for (var k:Number = 0; k < periscopeUser.areaTypes.length; k++) {
                                if (at.areaTypeId == periscopeUser.areaTypes.getAreaTypeVOAt(k).areaTypeId) {
                                    periscopeUser.areaTypes.removeItemAt(k);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            
            //llamar al command para actualizar
            var command:UpdateUsersCommand = new UpdateUsersCommand();
            command.addCommandListener(updateUsersResultNormal, applicationError);
            
            command.execute(umModel.usersToModifiedList, securityModel.sessionId);
        }
        
        public function backToListFromMassiveModification():void {
            backUserListNormal();
        }
        
        public function markCorporates():void {
            disableCorporateList();
            
            //marcando corporate ya presentes
            for each (var corporate:CorporateVO in umModel.corporateList) {
                for each (var userCorporate:CorporateVO in umModel.currentUser.corporates) {
                    if (corporate.corporateId == userCorporate.corporateId) {
                        corporate.selected = true;
                    }
                }
            }
        }
        
        public function markRolesGroups():void {
            disableRoleGroup();
            
            //marcando roles ya presentes
            for each (var roleGroup:RolesGroupVO in umModel.roleList) {
                for each (var userRoleGroup:RolesGroupVO in umModel.currentUser.rolesGroups) {
                    if (roleGroup.rolesGroupId == userRoleGroup.rolesGroupId) {
                        roleGroup.selected = true;
                    }
                }
            }
        }
        
        public function markStores():void {
            disableStoreList();
            
            //marcando tiendas ya presentes
            for each (var store:StoreVO in umModel.storeList) {
                for each (var userStore:StoreVO in umModel.currentUser.stores) {
                    if (store.storeId == userStore.storeId) {
                        store.selected = true;
                    }
                }
            }
        }
        
        public function markAreaTypes():void {
            disableAreaType();
            
            //marcando area types ya presentes
            for each (var areaType:AreaTypeVO in umModel.areaTypeList) {
                for each (var userAreaType:AreaTypeVO in umModel.currentUser.areaTypes) {
                    if (areaType.areaTypeId == userAreaType.areaTypeId) {
                        areaType.selected = true;
                    }
                }
            }
        }
                
        public function disableCorporateList():void {
            for each (var corporate:CorporateVO in umModel.corporateList) {
                corporate.selected = false;
            }
        }

        public function disableRoleGroup():void {
            for each (var roleGroup:RolesGroupVO in umModel.roleList) {
                roleGroup.selected = false;
            }
        }
        
        public function disableStoreList():void {
            for each (var store:StoreVO in umModel.storeList) {
                store.selected = false;
            }
        }
        
        public function disableAreaType():void {
            for each (var areaType:AreaTypeVO in umModel.areaTypeList) {
                areaType.selected = false;
            }
        }
        
        private function loadUserData(userVO:PeriscopeUserVO):void {
        	umModel.userName = userVO.userName;
        	umModel.fullName = userVO.fullName;
        	umModel.jobPosition = userVO.jobPosition;
        	if (umModel.editMode) {
        		umModel.stateIndex = userVO.userState.ordinal - 1;
        	} else {
        		umModel.stateIndex = 0;
        	}
        	umModel.email = userVO.email;
        	umModel.password = userVO.password;
        	umModel.confirmPasswordValue = userVO.password;
        }
        
        public function applicationError(event:GenericErrorEvent):void {
            var message:String = event.message;
            PopUpUtils.getInstance().showLoading(false);
            PopUpUtils.getInstance().showMessage(message,'commons.error');
        }
   }
}