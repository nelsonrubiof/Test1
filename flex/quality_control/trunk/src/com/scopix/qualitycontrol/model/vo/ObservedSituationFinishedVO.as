package com.scopix.qualitycontrol.model.vo {
	import com.scopix.periscope.webservices.businessservices.valueObjects.ObservedSituationFinishedDTO;
    

    [Bindable]
    public class ObservedSituationFinishedVO {

        public var observedSituationId:Number;
        public var evaluationUser:String;
        public var evidenceDate:String;
        public var product:String;
        public var situationTemplateName:String;
        public var selected:Boolean;
        public var selectedBackgroundColor:Boolean;

        public function ObservedSituationFinishedVO() {
        }

        public function fromDTO(dto:ObservedSituationFinishedDTO):void {
            this.observedSituationId = dto.observedSituationId;
            this.evaluationUser = dto.evaluationUser;
            this.evidenceDate = dto.evidenceDate;
            this.product = dto.product;
            this.situationTemplateName = dto.situationTemplateName;
        }

        public function toDTO():ObservedSituationFinishedDTO {
            var dto:ObservedSituationFinishedDTO = new ObservedSituationFinishedDTO();

            dto.observedSituationId = this.observedSituationId;
            dto.evaluationUser = this.evaluationUser;
            dto.evidenceDate = this.evidenceDate;
            dto.product = this.product;
            dto.situationTemplateName = this.situationTemplateName;
            return dto;
        }

        public function clone():ObservedSituationFinishedVO {
            var cloned:ObservedSituationFinishedVO = new ObservedSituationFinishedVO();

            cloned.observedSituationId = this.observedSituationId;
            cloned.evaluationUser = this.evaluationUser;
            cloned.evidenceDate = this.evidenceDate;
            cloned.product = this.product;
            cloned.situationTemplateName = this.situationTemplateName;
            return cloned;
        }
    }
}
