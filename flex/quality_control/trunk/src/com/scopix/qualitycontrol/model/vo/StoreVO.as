package com.scopix.qualitycontrol.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.AreaDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.StoreDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfAreaVO;

    [Bindable]
    public class StoreVO {
        public var id:Number;
        public var name:String;
        public var description:String;
        public var areas:ArrayOfAreaVO;
        public var selected:Boolean = false;

        public function StoreVO() {
        }

        public function fromDTO(dto:StoreDTO):void {
            this.name = dto.name;
            this.description = dto.description;
            this.id = dto.id;
            var areaVO:AreaVO = null;
            if (dto.areas != null && dto.areas.length > 0) {
                this.areas = new ArrayOfAreaVO();
                for each (var areaDto:AreaDTO in dto.areas) {
                    areaVO = new AreaVO();
                    areaVO.fromDTO(areaDto);
                    this.areas.addAreaVO(areaVO);
                }
            }
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
