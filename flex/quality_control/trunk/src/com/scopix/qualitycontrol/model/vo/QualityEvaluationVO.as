/**
 * QualityEvaluationDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.QualityEvaluationDTO;
    
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class QualityEvaluationVO {
        /**
         * Constructor, initializes the type class
         */
        public function QualityEvaluationVO() {
        }

        public var clasificacion:Number;
        public var fechaEvidencia:String;
        public var fechaRevision:String;
        public var messageOperator:String;
        public var motivoRechazo:Number;
        public var observaciones:String;
        public var observedSituationId:Number;
        public var operador:String;
        public var result:String;
        public var user:String;

        public function toDTO():QualityEvaluationDTO {
            var dto:QualityEvaluationDTO = new QualityEvaluationDTO();

            dto.clasificacion = this.clasificacion;
            dto.fechaEvidencia = this.fechaEvidencia;
            dto.fechaRevision = this.fechaRevision;
            dto.messageOperator = this.messageOperator;
            dto.motivoRechazo = this.motivoRechazo;
            dto.observaciones = this.observaciones;
            dto.observedSituationId = this.observedSituationId;
            dto.operador = this.operador;
            dto.result = this.result;
            dto.user = this.user;

            return dto;
        }

    }
}
