/**
 * ProofDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfMarquisVO;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Image;
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class ProofVO {
        public var evidenceId:Number;
        public var marquis:ArrayOfMarquisVO;
        public var order:Number;
        public var pathWithMarks:String;
        public var pathWithoutMarks:String;
        public var proofId:Number;
        public var proofResult:Number;
        public var image:Image;

        /**
         * Constructor, initializes the type class
         */
        public function ProofVO() {
        }

        public function fromDTO(dto:ProofDTO):void {
            this.evidenceId = dto.evidenceId;
            this.order = dto.order;
            this.pathWithMarks = dto.pathWithMarks;
            this.pathWithoutMarks = dto.pathWithoutMarks;
            this.proofId = dto.proofId;
            this.proofResult = dto.proofResult;
            if (dto.marquis != null && dto.marquis.length > 0) {

                this.marquis = new ArrayOfMarquisVO();
                for each (var marquisDTO:MarquisDTO in dto.marquis) {
                    var marquisVO:MarquisVO = new MarquisVO();
                    marquisVO.fromDTO(marquisDTO);
                    this.marquis.addMarquisVO(marquisVO);
                }
            }

        }

        public function toDTO():ProofDTO {
            var dto:ProofDTO = new ProofDTO();
            dto.evidenceId = this.evidenceId;
            dto.order = this.order;
            dto.pathWithMarks = this.pathWithMarks;
            dto.pathWithoutMarks = this.pathWithoutMarks;
            dto.proofId = this.proofId;
            dto.proofResult = this.proofResult;
            if (this.marquis != null && this.marquis.length > 0) {

                dto.marquis = new ArrayCollection();
                for each (var marquisVO:MarquisVO in this.marquis) {
                    var marquisDTO:MarquisDTO = marquisVO.toDTO();
                    dto.marquis.addItem(marquisDTO);
                }
            }
            return dto;
        }

        public function clone():ProofVO {
            var cloned:ProofVO = new ProofVO();

            cloned.evidenceId = this.evidenceId;
            cloned.order = this.order;
            cloned.pathWithMarks = this.pathWithMarks;
            cloned.pathWithoutMarks = this.pathWithoutMarks;
            cloned.proofId = this.proofId;
            cloned.proofResult = this.proofResult;
            if (this.marquis != null && this.marquis.length > 0) {
                cloned.marquis = new ArrayOfMarquisVO();
                for each (var marquisVO:MarquisVO in this.marquis) {
                    var clonedMarquis:MarquisVO = marquisVO.clone();
                    cloned.marquis.addMarquisVO(clonedMarquis);
                }
            }
            return cloned;


            return cloned;
        }
    }
}
