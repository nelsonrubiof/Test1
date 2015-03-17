package com.scopix.extractionplancustomizing.model.vo {
	import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDetailDTO;

    public class ExtractionPlanRangeDetailVO {
        public var extractionPlanRangeId:Number;
        public var id:Number;
        public var timeSample:String;

        public function ExtractionPlanRangeDetailVO() {
        }

        public function fromDTO(dto:ExtractionPlanRangeDetailDTO):void {
            this.extractionPlanRangeId = dto.extractionPlanRangeId;
            this.id = dto.id;
            this.timeSample = dto.timeSample;
        }

        public function toDTO():ExtractionPlanRangeDetailDTO {
            var dto:ExtractionPlanRangeDetailDTO = new ExtractionPlanRangeDetailDTO();

            dto.extractionPlanRangeId = this.extractionPlanRangeId;
            dto.id = this.id;
            dto.timeSample = this.timeSample;

            return dto;
        }
    }
}
