package com.scopix.usermanagement.model.vo
{
    import com.scopix.periscope.security.services.webservices.AreaTypeDTO;
    
    [Bindable]
    public class AreaTypeVO
    {
		public var areaTypeId:Number;
		public var name:String;
		public var description:String;
		public var selected:Boolean = false;

        public function AreaTypeVO()
        {
        }
        
        public function fromDTO(dto:AreaTypeDTO):void {
            this.name = dto.name;
            this.description = dto.description;
            this.areaTypeId = dto.areaTypeId; 
        }

        public function toDTO():AreaTypeDTO {
            var dto:AreaTypeDTO = new AreaTypeDTO();
            
            dto.name = this.name;
            dto.description = this.description;
            dto.areaTypeId = this.areaTypeId;
            
            return dto;
        }
        
        public function clone():AreaTypeVO {
            var at:AreaTypeVO = new AreaTypeVO();
            
            at.areaTypeId = this.areaTypeId;
            at.description = this.description;
            at.name = this.name;
            at.selected = this.selected;
            
            return at;
        }
    }
}