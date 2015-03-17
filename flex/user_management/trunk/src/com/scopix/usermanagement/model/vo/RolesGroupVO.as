package com.scopix.usermanagement.model.vo
{
    import com.scopix.periscope.security.services.webservices.RolesGroupDTO;
    
    [Bindable]
    public class RolesGroupVO
    {
        public var rolesGroupId:Number;
        public var name:String;
        public var description:String;
        public var selected:Boolean = false;
        
        public function RolesGroupVO() {
            
        }
        
        public function fromDTO(dto:RolesGroupDTO):void {
            this.rolesGroupId = dto.rolesGroupId;
            this.name = dto.name;
            this.description = dto.description;
        }
        
        public function toDTO():RolesGroupDTO {
            var dto:RolesGroupDTO = new RolesGroupDTO();
            
            dto.name = this.name;
            dto.description = this.description;
            dto.rolesGroupId = this.rolesGroupId;
            
            return dto;
        }
        
        public function clone():RolesGroupVO {
            var rg:RolesGroupVO = new RolesGroupVO();
            
            rg.description = this.description;
            rg.name = this.name;
            rg.rolesGroupId = this.rolesGroupId;
            rg.selected = this.selected;
            
            return rg;
        }
    }
}