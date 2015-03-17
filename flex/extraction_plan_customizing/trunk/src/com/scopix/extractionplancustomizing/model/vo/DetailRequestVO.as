package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.DetalleSolicitudDTO;

    public class DetailRequestVO {
        public var area:String;
        public var date:String;
        public var day:Number;
        public var duration:String;
        public var situationTemplate:String;
        public var store:String;

        public function fromDTO(dto:com.scopix.periscope.webservices.businessservices.valueObjects.DetalleSolicitudDTO):void {
            this.area = dto.area;
            this.date = dto.date;
            this.day = dto.day;
            this.duration = dto.duration;
            this.situationTemplate = dto.situationTemplate;
            this.store = dto.store;
        }
    }
}
