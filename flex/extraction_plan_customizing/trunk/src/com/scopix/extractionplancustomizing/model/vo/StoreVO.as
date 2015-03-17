package com.scopix.extractionplancustomizing.model.vo {
	import com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;

    [Bindable]
    public class StoreVO {
        public var id:Number;
        public var name:String;
        public var description:String;
        public var selected:Boolean = false;

        public function StoreVO() {
        }

        public function fromDTO(dto:StoreDTO):void {
            this.name = dto.name;
            this.description = dto.description;
            this.id = dto.id;
        }

        public function toDTO():StoreDTO {
            var dto:StoreDTO = new StoreDTO();

            dto.name = this.name;
            dto.description = this.description;
            dto.id = this.id;

            return dto;
        }

        public function clone():StoreVO {
            var st:StoreVO = new StoreVO();

            st.description = this.description;
            st.name = this.name;
            st.selected = this.selected;
            st.id = this.id;

            return st;
        }
    }
}
