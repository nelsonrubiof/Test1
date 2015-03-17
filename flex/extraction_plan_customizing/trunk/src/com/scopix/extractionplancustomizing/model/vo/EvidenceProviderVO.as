package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;

    public class EvidenceProviderVO {
        public var id:Number;
        public var description:String;
        public var selected:Boolean;

        public function EvidenceProviderVO() {
        }

        public function fromDTO(dto:com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO):void {
            this.id = dto.id;
            this.description = dto.description;
            this.selected = false;
        }

        public function toDTO():EvidenceProviderDTO {
            var dto:EvidenceProviderDTO = new EvidenceProviderDTO();

            dto.id = this.id;
            dto.description = this.description;

            return dto;
        }

        public function clone():EvidenceProviderVO {
            var ep:EvidenceProviderVO = new EvidenceProviderVO();

            ep.id = this.id;
            ep.description = this.description;
            ep.selected = this.selected;

            return ep;
        }
    }
}
