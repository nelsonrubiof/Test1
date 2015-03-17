package com.scopix.reporting.model.vo
{
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailDTO;
    import com.scopix.util.DateUtil;
    
    public class UploadProcessDetailVO
    {
        public var id:Number;
		public var areaType:AreaTypeVO;
		public var dateEnd:Date;
		public var store:StoreVO;
		public var totalRecords:Number;
		public var upRecords:Number;
		public var percent:Number;
		public var selected:Boolean;
		
        public function UploadProcessDetailVO()
        {
        }
        
        public function fromDTO(dto:UploadProcessDetailDTO):void {
            if (dto.areaType != null) {
                this.areaType = new AreaTypeVO();
                //no se puede utilizar el metodo del VO, fromDTO, debido a que el tipo areaType
                //del vo AreaTypeVO, es del package extractionplancustomizing por reutilizacion del WS
                this.areaType.id = dto.areaType.id;
                this.areaType.name = dto.areaType.name;
                this.areaType.description = dto.areaType.description;
            }
            if (dto.store != null) {
                this.store = new StoreVO();
                //no se puede utilizar el metodo del VO, fromDTO, debido a que el tipo store
                //del vo StoreVO, es del package extractionplancustomizing por reutilizacion del WS
                this.store.id = dto.store.id;
                this.store.name = dto.store.name;
                this.store.description = dto.store.description;
            }
            
            this.id = dto.id;
            this.dateEnd = DateUtil.parse(dto.dateEnd);
            this.totalRecords = dto.totalRecords;
            this.upRecords = dto.upRecords;
            this.percent = dto.percent;
            this.selected = false;
        }
    }
}