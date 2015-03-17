package com.scopix.extractionplancustomizing.model.vo
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfAreaTypeVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfStoreVO;
    import com.scopix.periscope.webservices.security.valueObjects.CorporateDTO;
    
    [Bindable]
    public class CorporateVO
    {
		public var id:Number;
		public var name:String;
		public var description:String;
		public var stores:ArrayOfStoreVO;
		public var areaTypes:ArrayOfAreaTypeVO;
		public var selected:Boolean;

        public function CorporateVO()
        {
            stores = new ArrayOfStoreVO();
            areaTypes = new ArrayOfAreaTypeVO();
        }
        
        public function fromDTO(dto:CorporateDTO):void {
                this.id = dto.corporateId;
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
            
            dto.corporateId = this.id;
            dto.name = this.name;
            dto.description = this.description;
            
            return dto;
        }
        
        public function cloneBasic():CorporateVO {
            var co:CorporateVO = new CorporateVO();
            
            co.id = this.id;
            co.description = this.description;
            co.name = this.name;
            co.selected = this.selected;
            
            return co;
        }
    }
}