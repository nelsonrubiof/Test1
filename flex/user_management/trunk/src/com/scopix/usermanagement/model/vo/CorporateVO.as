package com.scopix.usermanagement.model.vo {
    import com.scopix.periscope.security.services.webservices.AreaTypeDTO;
    import com.scopix.periscope.security.services.webservices.CorporateDTO;
    import com.scopix.periscope.security.services.webservices.StoreDTO;
    import com.scopix.usermanagement.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.usermanagement.model.arrays.ArrayOfStoreVO;

    [Bindable]
    public class CorporateVO {
        public var corporateId:Number;
        public var name:String;
        public var description:String;
        public var stores:ArrayOfStoreVO;
        public var areaTypes:ArrayOfAreaTypeVO;
        public var selected:Boolean;

        public function CorporateVO() {
            stores = new ArrayOfStoreVO();
            areaTypes = new ArrayOfAreaTypeVO();
        }

        public function fromDTO(dto:CorporateDTO):void {
            this.corporateId = dto.corporateId;
            this.name = dto.name;
            this.description = dto.description;

        /*
        for each (var storeDTO:StoreDTO in dto.stores) {
            var store:StoreVO = new StoreVO();
            store.fromDTO(storeDTO);
            stores.addStoreVO(store);
        }

        for each (var areaTypeDTO:AreaTypeDTO in dto.areaTypes) {
            var areaType:AreaTypeVO = new AreaTypeVO();
            areaType.fromDTO(areaTypeDTO);
            areaTypes.addAreaTypeVO(areaType);
        }*/
        }

        public function toDTO():CorporateDTO {
            var dto:CorporateDTO = new CorporateDTO();

            dto.corporateId = this.corporateId;
            dto.name = this.name;
            dto.description = this.description;

            return dto;
        }

        public function clone():CorporateVO {
            var co:CorporateVO = new CorporateVO();

            co.corporateId = this.corporateId;
            co.description = this.description;
            co.name = this.name;
            co.selected = this.selected;

            for each (var st:StoreVO in this.stores) {
                co.stores.addStoreVO(st);
            }

            for each (var at:AreaTypeVO in this.areaTypes) {
                co.areaTypes.addAreaTypeVO(at);
            }

            return co;
        }
    }
}
