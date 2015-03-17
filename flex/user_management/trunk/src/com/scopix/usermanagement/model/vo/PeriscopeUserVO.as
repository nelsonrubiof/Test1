package com.scopix.usermanagement.model.vo
{
    import com.scopix.enum.UserStatesEnum;
    import com.scopix.periscope.security.services.webservices.AreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfAreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfCorporateDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfRolesGroupDTO;
    import com.scopix.periscope.security.services.webservices.ArrayOfStoreDTO;
    import com.scopix.periscope.security.services.webservices.CorporateDTO;
    import com.scopix.periscope.security.services.webservices.PeriscopeUserDTO;
    import com.scopix.periscope.security.services.webservices.RolesGroupDTO;
    import com.scopix.periscope.security.services.webservices.StoreDTO;
    import com.scopix.usermanagement.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfRolesGroupVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfStoreVO;

    [Bindable]
    public class PeriscopeUserVO
    {
		public var userId:Number;
		public var userName:String;
		public var userState:UserStatesEnum;
		public var email:String;
		public var fullName:String;
		public var jobPosition:String;
		public var modificationDate:Date;
		public var startDate:Date;
		public var password:String;
		public var mainCorporateId:Number;
		public var corporates:ArrayOfCorporateVO;
		public var rolesGroups:ArrayOfRolesGroupVO;
		public var stores:ArrayOfStoreVO;
		public var areaTypes:ArrayOfAreaTypeVO;
		public var selected:Boolean = false;

        public function PeriscopeUserVO()
        {
            userId = 0;
            corporates = new ArrayOfCorporateVO();
            rolesGroups = new ArrayOfRolesGroupVO();
            stores = new ArrayOfStoreVO();
            areaTypes = new ArrayOfAreaTypeVO();
            startDate = new Date();
        }
        
        public function fromDTO(dto:PeriscopeUserDTO):void {
            this.userId = dto.userId;
            this.userName = dto.userName;
            this.email = dto.email;
            this.fullName = dto.fullName;
            this.jobPosition = dto.jobPosition;
            this.modificationDate = dto.modificationDate;
            this.startDate = dto.startDate;
            this.password = dto.password;
            this.mainCorporateId = dto.mainCorporateId;
            this.userState = UserStatesEnum.getState(dto.userState.toLowerCase());
            
			/*
            for each (var rolesGroupDTO:RolesGroupDTO in dto.rolesGroups) {
                var rolesGroup:RolesGroupVO = new RolesGroupVO();
                rolesGroup.fromDTO(rolesGroupDTO);
                
                this.rolesGroups.addRolesGroupVO(rolesGroup);
            }
            
            for each (var corporateDTO:CorporateDTO in dto.corporates) {
                var corporate:CorporateVO = new CorporateVO();
                corporate.fromDTO(corporateDTO);
                
                this.corporates.addCorporateVO(corporate);
            }
            
            for each (var storeDTO:StoreDTO in dto.stores) {
                var store:StoreVO = new StoreVO();
                store.fromDTO(storeDTO);
                
                this.stores.addStoreVO(store);
            }
            
            for each (var areaTypeDTO:AreaTypeDTO in dto.areaTypes) {
                var areaType:AreaTypeVO = new AreaTypeVO();
                areaType.fromDTO(areaTypeDTO);
                
                this.areaTypes.addAreaTypeVO(areaType);
            }*/
        }
        
        public function toDTO():PeriscopeUserDTO {
            var periscopeUserDTO:PeriscopeUserDTO = new PeriscopeUserDTO();
            
            periscopeUserDTO.userId = this.userId;
            periscopeUserDTO.userName = this.userName;
            periscopeUserDTO.email = this.email;
            periscopeUserDTO.fullName = this.fullName;
            periscopeUserDTO.jobPosition = this.jobPosition;
            periscopeUserDTO.modificationDate = new Date();
            periscopeUserDTO.startDate = this.startDate;
            periscopeUserDTO.password = this.password;
            periscopeUserDTO.mainCorporateId = this.mainCorporateId;
            periscopeUserDTO.userState = this.userState.value.toUpperCase();
            periscopeUserDTO.rolesGroups = new ArrayOfRolesGroupDTO();
            periscopeUserDTO.stores = new ArrayOfStoreDTO();
            periscopeUserDTO.areaTypes = new ArrayOfAreaTypeDTO();
            periscopeUserDTO.corporates = new ArrayOfCorporateDTO();
            
            var rolesGroupDTO:RolesGroupDTO;
            for each (var rolesGroup:RolesGroupVO in this.rolesGroups) {
                rolesGroupDTO = new RolesGroupDTO();
                rolesGroupDTO = rolesGroup.toDTO();
                
                periscopeUserDTO.rolesGroups.addRolesGroupDTO(rolesGroupDTO);
            }

            var corporateDTO:CorporateDTO;
            for each (var corporateVO:CorporateVO in this.corporates) {
                corporateDTO = new CorporateDTO();
                corporateDTO = corporateVO.toDTO();
                
                periscopeUserDTO.corporates.addCorporateDTO(corporateDTO);
            }
            
            var storeDTO:StoreDTO;
            for each (var storeVO:StoreVO in this.stores) {
                storeDTO = new StoreDTO();
                storeDTO = storeVO.toDTO();
                
                periscopeUserDTO.stores.addStoreDTO(storeDTO);
            }
            
            var areaTypeDTO:AreaTypeDTO;
            for each (var areaTypeVO:AreaTypeVO in this.areaTypes) {
                areaTypeDTO = new AreaTypeDTO();
                areaTypeDTO = areaTypeVO.toDTO();
                
                periscopeUserDTO.areaTypes.addAreaTypeDTO(areaTypeDTO);
            }
            
            return periscopeUserDTO;
        }
        
        public function clone():PeriscopeUserVO {
            var pu:PeriscopeUserVO = new PeriscopeUserVO();
            
            pu.email = this.email;
            pu.fullName = this.fullName;
            pu.jobPosition = this.jobPosition;
            pu.mainCorporateId = this.mainCorporateId;
            pu.modificationDate = this.modificationDate;
            pu.password = this.password;
            pu.selected = this.selected;
            pu.startDate = this.startDate;
            pu.userId = this.userId;
            pu.userName = this.userName;
            pu.userState = this.userState;
            
            for each (var rg:RolesGroupVO in this.rolesGroups) {
                pu.rolesGroups.addRolesGroupVO(rg.clone());
            }
            
            for each (var co:CorporateVO in this.corporates) {
                pu.corporates.addCorporateVO(co.clone());
            }
            
            for each (var st:StoreVO in this.stores) {
                pu.stores.addStoreVO(st.clone());
            }

            for each (var at:AreaTypeVO in this.areaTypes) {
                pu.areaTypes.addAreaTypeVO(at.clone());
            }
            
            return pu;
        }
    }
}