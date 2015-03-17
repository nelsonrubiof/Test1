package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO;
    
    import mx.formatters.DateFormatter;

    public class ExtractionPlanRangeVO {
        public var dayOfWeek:Number;
        public var duration:Number;
        public var initialTime:Date;
        public var endTime:Date;
        public var frecuency:Number;
        public var id:Number;
        public var samples:Number;
        public var eprType:String;

        public function ExtractionPlanRangeVO() {
        }

        public function fromDTO(dto:com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanRangeDTO):void {
            this.id = dto.id;
            this.dayOfWeek = dto.dayOfWeek;
            this.duration = dto.duration;
            this.eprType = dto.type;

            if (dto.initialTime != null && dto.initialTime.length > 0) {
                var it:Date = new Date("01/01/2010 " + dto.initialTime);
                this.initialTime = it;
            } else {
                this.initialTime = null;
            }
            if (dto.endTime != null && dto.endTime.length > 0) {
                var et:Date = new Date("01/01/2010 " + dto.endTime);
                this.endTime = et;
            } else {
                this.endTime = null;
            }
            this.frecuency = dto.frecuency;
            this.samples = dto.samples;
        }

        public function toDTO():ExtractionPlanRangeDTO {
            var dto:ExtractionPlanRangeDTO = new ExtractionPlanRangeDTO();

            var df:DateFormatter = new DateFormatter();
            df.formatString = "JJ:NN";

            dto.id = this.id;
            dto.dayOfWeek = this.dayOfWeek;
            dto.duration = this.duration;
            dto.initialTime = df.format(this.initialTime);
            dto.endTime = df.format(this.endTime);
            dto.frecuency = this.frecuency;
            dto.samples = this.samples;
            dto.type = this.eprType;

            return dto;
        }
    }
}
