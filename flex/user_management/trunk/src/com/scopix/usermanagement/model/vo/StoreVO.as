package com.scopix.usermanagement.model.vo
{
    import com.scopix.periscope.security.services.webservices.StoreDTO;
    
    [Bindable]
    public class StoreVO
    {
		public var storeId:Number;
		public var name:String;
		public var description:String;
		public var selected:Boolean = false;
		
        public function StoreVO()
        {
        }
        
        public function fromDTO(dto:StoreDTO):void {
            this.name = dto.name;
            this.description = dto.description;
            this.storeId = dto.storeId;
        }
        
        public function toDTO():StoreDTO {
            var dto:StoreDTO = new StoreDTO();
            
            dto.name = this.name;
            dto.description = this.description;
            dto.storeId = this.storeId;
            
            return dto;
        }
        
        public function clone():StoreVO {
            var st:StoreVO = new StoreVO();
            
            st.description = this.description;
            st.name = this.name;
            st.selected = this.selected;
            st.storeId = this.storeId;
            
            return st;
        }
    }
}