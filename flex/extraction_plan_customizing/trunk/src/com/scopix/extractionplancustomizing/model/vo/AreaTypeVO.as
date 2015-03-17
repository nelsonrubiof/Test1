package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.AreaTypeDTO;

    [Bindable]
    public class AreaTypeVO {
        public var id:Number;
        public var name:String;
        public var description:String;
        public var selected:Boolean = false;

        public function AreaTypeVO() {
        }

        public function fromDTO(dto:AreaTypeDTO):void {
            this.name = dto.name;
            this.description = dto.description;
            this.id = dto.id;
        }

        public function toDTO():AreaTypeDTO {
            var dto:AreaTypeDTO = new AreaTypeDTO();

            dto.name = this.name;
            dto.description = this.description;
            dto.id = this.id;

            return dto;
        }

        public function clone():AreaTypeVO {
            var at:AreaTypeVO = new AreaTypeVO();

            at.id = this.id;
            at.description = this.description;
            at.name = this.name;
            at.selected = this.selected;

            return at;
        }
    }
}
