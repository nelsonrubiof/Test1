package com.scopix.test
{
    import com.scopix.enum.UserStatesEnum;
    import com.scopix.periscope.security.services.webservices.AreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfAreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfCorporateDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfPeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfRolesGroupDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfStoreDTO;
    import com.scopix.periscope.security.services.webservices.CorporateDTO;
    import com.scopix.periscope.security.services.webservices.PeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.RolesGroupDTO;
    import com.scopix.periscope.security.services.webservices.StoreDTO;
    
    public class SecurityDataTest
    {
        private static var instance:SecurityDataTest;
        
        public static function getInstance():SecurityDataTest {
            if(instance == null) {
                instance = new SecurityDataTest();
            }
            
            return instance;
        }
        
        public function getClientsData():ArrayOfCorporateDTO {
            var corporateList:ArrayOfCorporateDTO = new ArrayOfCorporateDTO();

            var corporate:CorporateDTO = new CorporateDTO();
            corporate.corporateId = 0;
            corporate.name = "scopix";
            corporate.description = "Scopix";
            corporate.areaTypes = getAreaTypesCabelas();
            corporate.stores = getStoreListCabelas();
            
            corporateList.addCorporateDTO(corporate);

            //cabelas
            corporate = new CorporateDTO();
            corporate.corporateId = 1;
            corporate.name = "cabelas";
            corporate.description = "Cabelas";
            corporate.areaTypes = getAreaTypesCabelas();
            corporate.stores = getStoreListCabelas();
            
            corporateList.addCorporateDTO(corporate);
            
            //lowes
            corporate = new CorporateDTO();
            corporate.corporateId = 2;
            corporate.name = "lowes";
            corporate.description = "Lowes";
            corporate.areaTypes = getAreaTypesLowes();
            corporate.stores = getStoreListLowes();
            
            corporateList.addCorporateDTO(corporate);
            
            return corporateList;
        }
        
        public function getStoreListCabelas():ArrayOfStoreDTO {
            var storeList:ArrayOfStoreDTO = new ArrayOfStoreDTO();
            
            var store:StoreDTO = new StoreDTO();
            store.storeId = 1;
            store.name = "glendale";
            store.description = "Glendale AZ";
            storeList.addStoreDTO(store);
             
            store = new StoreDTO();
            store.storeId = 2;
            store.name = "rapid_city";
            store.description = "Rapid City";
            storeList.addStoreDTO(store);

            store = new StoreDTO();
            store.storeId = 3;
            store.name = "hamburg";
            store.description = "Hamburg";
            storeList.addStoreDTO(store);
            
            return storeList;
        }
        
        public function getStoreListCabelasUser1():ArrayOfStoreDTO {
            var storeList:ArrayOfStoreDTO = new ArrayOfStoreDTO();
            
            var store:StoreDTO = new StoreDTO();
            store.storeId = 1;
            store.name = "glendale";
            store.description = "Glendale AZ";
            storeList.addStoreDTO(store);
             
            return storeList;
        }
        
        public function getStoreListCabelasUser2():ArrayOfStoreDTO {
            var storeList:ArrayOfStoreDTO = new ArrayOfStoreDTO();
            
            var store:StoreDTO = new StoreDTO();
            store.storeId = 1;
            store.name = "glendale";
            store.description = "Glendale AZ";
            storeList.addStoreDTO(store);
             
            store = new StoreDTO();
            store.storeId = 3;
            store.name = "hamburg";
            store.description = "Hamburg";
            storeList.addStoreDTO(store);
            
            return storeList;
        }
        
        public function getStoreListLowes():ArrayOfStoreDTO {
            var storeList:ArrayOfStoreDTO = new ArrayOfStoreDTO();

            var store:StoreDTO = new StoreDTO();
            store.storeId = 1;
            store.name = "north_lake";
            store.description = "North Lake";
            storeList.addStoreDTO(store);
             
            store = new StoreDTO();
            store.storeId = 2;
            store.name = "lexington";
            store.description = "Lexington";
            storeList.addStoreDTO(store);
            
            return storeList;
        }
        
        public function getStoreListLowesUser1():ArrayOfStoreDTO {
            var storeList:ArrayOfStoreDTO = new ArrayOfStoreDTO();

            var store:StoreDTO = new StoreDTO();
            store.storeId = 1;
            store.name = "north_lake";
            store.description = "North Lake";
            storeList.addStoreDTO(store);
             
            return storeList;
        }
        
        public function getStoreListLowesUser2():ArrayOfStoreDTO {
            var storeList:ArrayOfStoreDTO = new ArrayOfStoreDTO();

            var store:StoreDTO = new StoreDTO();
            store.storeId = 2;
            store.name = "lexington";
            store.description = "Lexington";
            storeList.addStoreDTO(store);
            
            return storeList;
        }
        
        public function getAreaTypesCabelas():ArrayOfAreaTypeDTO {
            var areaTypeList:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();

            var at:AreaTypeDTO = new AreaTypeDTO();
            at.areaTypeId = 1;
            at.name = "CUSTOMER SERVICE DESK";
            at.description = "Customer Service Desk";
            areaTypeList.addAreaTypeDTO(at);
            
            at = new AreaTypeDTO();
            at.areaTypeId = 2;
            at.name = "GUN COUNTER";
            at.description = "Gun Counter";
            areaTypeList.addAreaTypeDTO(at);

            at = new AreaTypeDTO();
            at.areaTypeId = 3;
            at.name = "GUN SAFES";
            at.description = "Gun Safes";
            areaTypeList.addAreaTypeDTO(at);

            at = new AreaTypeDTO();
            at.areaTypeId = 4;
            at.name = "FOOTWEAR";
            at.description = "Footwear";
            areaTypeList.addAreaTypeDTO(at);
            
            return areaTypeList;
        }
        
        public function getAreaTypesLowes():ArrayOfAreaTypeDTO {
            var areaTypeList:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();
            
            var at:AreaTypeDTO = new AreaTypeDTO();
            at.areaTypeId = 1;
            at.name = "Lawn_Garden_Chemicals";
            at.description = "Lawn / Garden Chemicals";
            areaTypeList.addAreaTypeDTO(at);
            
            at = new AreaTypeDTO();
            at.areaTypeId = 2;
            at.name = "Holiday_Trees_&_Ornaments";
            at.description = "Holiday Trees & Ornaments";
            areaTypeList.addAreaTypeDTO(at);

            at = new AreaTypeDTO();
            at.areaTypeId = 3;
            at.name = "Fashion_baths_bathtubs";
            at.description = "Fashion baths / bathtubs";
            areaTypeList.addAreaTypeDTO(at);

            at = new AreaTypeDTO();
            at.areaTypeId = 4;
            at.name = "Plumbing";
            at.description = "Plumbing";
            areaTypeList.addAreaTypeDTO(at);
            
            return areaTypeList;
        }
        
        public function getAreaTypesCabelasUser1():ArrayOfAreaTypeDTO {
            var areaTypeList:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();

            var at:AreaTypeDTO = new AreaTypeDTO();
            at.areaTypeId = 1;
            at.name = "CUSTOMER SERVICE DESK";
            at.description = "Customer Service Desk";
            areaTypeList.addAreaTypeDTO(at);
            
            at = new AreaTypeDTO();
            at.areaTypeId = 2;
            at.name = "GUN COUNTER";
            at.description = "Gun Counter";
            areaTypeList.addAreaTypeDTO(at);

            return areaTypeList;
        }
        
        public function getAreaTypesCabelasUser2():ArrayOfAreaTypeDTO {
            var areaTypeList:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();

            var at:AreaTypeDTO = new AreaTypeDTO();
            at.areaTypeId = 3;
            at.name = "GUN SAFES";
            at.description = "Gun Safes";
            areaTypeList.addAreaTypeDTO(at);

            at = new AreaTypeDTO();
            at.areaTypeId = 4;
            at.name = "FOOTWEAR";
            at.description = "Footwear";
            areaTypeList.addAreaTypeDTO(at);
            
            return areaTypeList;
        }
        
        public function getAreaTypesLowesUser1():ArrayOfAreaTypeDTO {
            var areaTypeList:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();
            
            var at:AreaTypeDTO = new AreaTypeDTO();
            at.areaTypeId = 1;
            at.name = "Lawn_Garden_Chemicals";
            at.description = "Lawn / Garden Chemicals";
            areaTypeList.addAreaTypeDTO(at);
            
            at = new AreaTypeDTO();
            at.areaTypeId = 2;
            at.name = "Holiday_Trees_&_Ornaments";
            at.description = "Holiday Trees & Ornaments";
            areaTypeList.addAreaTypeDTO(at);

            return areaTypeList;
        }
        
        public function getAreaTypesLowesUser2():ArrayOfAreaTypeDTO {
            var areaTypeList:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();
            
            var at:AreaTypeDTO = new AreaTypeDTO();
            at.areaTypeId = 3;
            at.name = "Fashion_baths_bathtubs";
            at.description = "Fashion baths / bathtubs";
            areaTypeList.addAreaTypeDTO(at);

            at = new AreaTypeDTO();
            at.areaTypeId = 4;
            at.name = "Plumbing";
            at.description = "Plumbing";
            areaTypeList.addAreaTypeDTO(at);
            
            return areaTypeList;
        }
        
        public function getUserList():ArrayOfPeriscopeUserDTO {
            var list:ArrayOfPeriscopeUserDTO = new ArrayOfPeriscopeUserDTO();
            var userDTO:PeriscopeUserDTO = new PeriscopeUserDTO();
            
            userDTO.userId = 1;
            userDTO.userName = "marko";
            userDTO.fullName = "Marko Perich";
            userDTO.email = "marko.perich@scopixsolutions.com";
            userDTO.jobPosition = "TI Manager";
            userDTO.userState = UserStatesEnum.ACTIVE.value;
            userDTO.startDate = new Date();
            userDTO.mainCorporateId = 0;
            userDTO.rolesGroups = this.getRolesGroupList1();
            userDTO.stores = this.getStoreListCabelasUser1();
            userDTO.areaTypes = this.getAreaTypesCabelasUser1();
            
            list.addPeriscopeUserDTO(userDTO);
            
            userDTO = new PeriscopeUserDTO();
            userDTO.userId = 2;
            userDTO.userName = "galvarez";
            userDTO.fullName = "Gustavo Alvarez";
            userDTO.email = "gustavo.alvarez@scopixsolutions.com";
            userDTO.jobPosition = "Developer";
            userDTO.userState = UserStatesEnum.ACTIVE.value;
            userDTO.startDate = new Date();
            userDTO.mainCorporateId = 0;
            userDTO.rolesGroups = this.getRolesGroupList1();
            userDTO.stores = this.getStoreListCabelasUser2();
            userDTO.areaTypes = this.getAreaTypesCabelasUser2();
            
            list.addPeriscopeUserDTO(userDTO);
            
            userDTO = new PeriscopeUserDTO();
            userDTO.userId = 3;
            userDTO.userName = "lvera";
            userDTO.fullName = "Luis Vera";
            userDTO.email = "luis.vera@scopixsolutions.com";
            userDTO.jobPosition = "General Manager";
            userDTO.userState = UserStatesEnum.ACTIVE.value;
            userDTO.startDate = new Date();
            userDTO.mainCorporateId = 1;
            userDTO.rolesGroups = this.getRolesGroupList2();
            userDTO.stores = this.getStoreListCabelasUser2();
            userDTO.areaTypes = this.getAreaTypesCabelasUser1();
            
            list.addPeriscopeUserDTO(userDTO);

            userDTO = new PeriscopeUserDTO();
            userDTO.userId = 4;
            userDTO.userName = "orietta";
            userDTO.fullName = "Orietta Alegria";
            userDTO.email = "orietta.alegria@scopixsolutions.com";
            userDTO.jobPosition = "Operator Supervisor";
            userDTO.userState = UserStatesEnum.ACTIVE.value;
            userDTO.startDate = new Date();
            userDTO.mainCorporateId = 1;
            userDTO.rolesGroups = this.getRolesGroupList1();
            userDTO.stores = this.getStoreListCabelasUser2();
            userDTO.areaTypes = this.getAreaTypesCabelasUser1();
            
            list.addPeriscopeUserDTO(userDTO);
            
            return list;
        }
        
        public function getRolesGroupList1():ArrayOfRolesGroupDTO {
            var resp:ArrayOfRolesGroupDTO = new ArrayOfRolesGroupDTO();
            
            var dto:RolesGroupDTO = new RolesGroupDTO();
            dto.rolesGroupId = 1;
            dto.name = "Administrators";
            dto.description = "Adminstrators Group";
            resp.addRolesGroupDTO(dto);
            
            dto = new RolesGroupDTO();
            dto.rolesGroupId = 2;
            dto.name = "Operator Roles Group";
            dto.description = "Access for operators";
            resp.addRolesGroupDTO(dto);
            
            dto = new RolesGroupDTO();
            dto.rolesGroupId = 3;
            dto.name = "Quality Control Supervisor";
            dto.description = "Quality Control Supervisor";
            resp.addRolesGroupDTO(dto);

            dto = new RolesGroupDTO();
            dto.rolesGroupId = 4;
            dto.name = "Queue Management Supervisor";
            dto.description = "Supervisor for Queue Management";
            resp.addRolesGroupDTO(dto);
            
            return resp;
        }

        public function getRolesGroupList2():ArrayOfRolesGroupDTO {
            var resp:ArrayOfRolesGroupDTO = new ArrayOfRolesGroupDTO();
            
            var dto:RolesGroupDTO = new RolesGroupDTO();
            dto.rolesGroupId = 2;
            dto.name = "Operator Roles Group";
            dto.description = "Access for operators";
            resp.addRolesGroupDTO(dto);
            
            dto = new RolesGroupDTO();
            dto.rolesGroupId = 4;
            dto.name = "Queue Management Supervisor";
            dto.description = "Supervisor for Queue Management";
            resp.addRolesGroupDTO(dto);
            
            return resp;
        }
    }
}