/**
 * MetricResultDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.enum.MetricTypeEnum;
    import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.MetricResultDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfEvidenceVO;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class MetricResultVO {
        public var area:String;
        public var areaId:Number;
        public var evidencePrePath:String;
        public var evidences:ArrayOfEvidenceVO;
        public var metricId:Number;
        public var metricName:String;
        public var metricResult:Number;
        public var metricTemplateId:Number;
        public var metricType:MetricTypeEnum;
        public var observedMetricId:Number;
        public var proofPrePath:String;
        public var situtionTemplateName:String;
        public var descriptionOperator:String;

        /**
         * Constructor, initializes the type class
         */
        public function MetricResultVO() {
        }

        public function fromDTO(dto:MetricResultDTO):void {
            this.area = dto.area;
            this.areaId = dto.areaId;
            this.evidencePrePath = dto.evidencePrePath;

            this.metricId = dto.metricId;
            this.metricName = dto.metricName;
            this.metricResult = dto.metricResult;
            this.metricTemplateId = dto.metricTemplateId;
            this.metricType = MetricTypeEnum.getState(dto.metricType);
            this.observedMetricId = dto.observedMetricId;
            this.proofPrePath = dto.proofPrePath;
            this.situtionTemplateName = dto.situtionTemplateName;
            this.descriptionOperator = dto.descriptionOperator;
            if (dto.evidences != null && dto.evidences.length > 0) {
                this.evidences = new ArrayOfEvidenceVO();
                for each (var evidenceDTO:EvidenceDTO in dto.evidences) {
                    var evidenceVO:EvidenceVO = new EvidenceVO();
                    evidenceVO.fromDTO(evidenceDTO);
                    this.evidences.addEvidenceVO(evidenceVO);
                }
            }

        }

        public function toDTO():MetricResultDTO {
            var dto:MetricResultDTO = new MetricResultDTO();

            dto.area = this.area;
            dto.areaId = this.areaId;
            dto.evidencePrePath = this.evidencePrePath;

            dto.metricId = this.metricId;
            dto.metricName = this.metricName;
            dto.metricResult = this.metricResult;
            dto.metricTemplateId = this.metricTemplateId;
            //dto.metricType = MetricTypeEnum.getState(dto.metricType as String);
            dto.observedMetricId = this.observedMetricId;
            dto.proofPrePath = this.proofPrePath;
            dto.situtionTemplateName = this.situtionTemplateName;
            dto.descriptionOperator = this.descriptionOperator;
            if (this.evidences != null && this.evidences.length > 0) {
                dto.evidences = new ArrayCollection();
                for each (var evidenceVO:EvidenceVO in this.evidences) {
                    var evidenceDTO:EvidenceDTO = evidenceVO.toDTO();
                    dto.evidences.addItem(evidenceDTO);
                }
            }
            return dto;
        }

        public function clone():MetricResultVO {
            var cloned:MetricResultVO = new MetricResultVO();

            cloned.area = this.area;
            cloned.areaId = this.areaId;
            cloned.evidencePrePath = this.evidencePrePath;

            cloned.metricId = this.metricId;
            cloned.metricName = this.metricName;
            cloned.metricResult = this.metricResult;
            cloned.metricTemplateId = this.metricTemplateId;
            //dto.metricType = MetricTypeEnum.getState(dto.metricType as String);
            cloned.observedMetricId = this.observedMetricId;
            cloned.proofPrePath = this.proofPrePath;
            cloned.situtionTemplateName = this.situtionTemplateName;
            cloned.descriptionOperator = this.descriptionOperator;
            if (this.evidences != null && this.evidences.length > 0) {
                cloned.evidences = new ArrayOfEvidenceVO();
                for each (var evidenceVO:EvidenceVO in this.evidences) {
                    var clonedEvidence:EvidenceVO = evidenceVO.clone();
                    cloned.evidences.addEvidenceVO(clonedEvidence);
                }
            }

            return cloned;
        }

    }
}
