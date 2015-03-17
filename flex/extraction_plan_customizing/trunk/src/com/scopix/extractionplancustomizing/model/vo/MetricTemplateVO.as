package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.MetricTemplateDTO;


    public class MetricTemplateVO {
        //El ID del DTO es equivalente al EXTRACTIONPLANMETRICID del VO
        //EL ID del VO es equivalente al METRICTEMPLATEID del DTO

        //variable con el ID del metric template
        public var id:Number;
        public var name:String;
        public var description:String;
        public var variableName:String;
        public var order:Number;
        //variable con el ID del ExtractionPlanMetric
        public var extractionPlanMetricId:Number;
        public var selected:Boolean;
        //providers asociados. La variable debe llamarse "children" o bien sus metodos get y set deben llamarse children
        //usaremos el segundo caso: variable privada con metodos publicos
        private var _evidenceProvidersIds:ArrayOfEvidenceProviderVO;

        public function MetricTemplateVO() {
        }

        public function get children():ArrayOfEvidenceProviderVO {
            return this._evidenceProvidersIds;
        }

        public function set children(arr:ArrayOfEvidenceProviderVO):void {
            this._evidenceProvidersIds = arr;
        }

        public function fromDTO(dto:MetricTemplateDTO):void {
            this.id = dto.id;
            this.description = dto.description;
            this.name = dto.name;
            this.selected = false;
        }

        /*
               public function toDTO():MetricTemplateDTO {
                   var dto:MetricTemplateDTO = new MetricTemplateDTO();

                   dto.id = this.id;
                   dto.description = this.description;
                   dto.name = this.name;

                   return dto;
               }
       */
        public function clone():MetricTemplateVO {
            var mt:MetricTemplateVO = new MetricTemplateVO();

            mt.id = this.id;
            mt.name = this.name;
            mt.description = this.description;
            mt.selected = this.selected;

            return mt;
        }
    }
}
