package com.scopix.qualitycontrol.model.vo {
	import com.scopix.periscope.webservices.businessservices.valueObjects.AreaDTO;
    

    [Bindable]
    public class AreaVO {
        public var id:Number;
        public var name:String;
        public var description:String;
        public var selected:Boolean = false;

        public function AreaVO() {
        }

        public function fromDTO(dto:AreaDTO):void {
            this.name = dto.name;
            this.description = dto.description;
            this.id = dto.id;
        }

        public function toDTO():AreaDTO {
            var dto:AreaDTO = new AreaDTO();

            dto.name = this.name;
            dto.description = this.description;
            dto.id = this.id;

            return dto;
        }

        public function clone():AreaVO {
            var st:AreaVO = new AreaVO();

            st.description = this.description;
            st.name = this.name;
            st.selected = this.selected;
            st.id = this.id;

            return st;
        }
    }
}
