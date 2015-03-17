/**
 * EvidenceDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.enum.EvidenceTypeEnum;
    import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ProofDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfProofVO;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class EvidenceVO {

        public var camera:String;
        public var cantDoReason:String;
        public var defaultEvidenceProvider:Boolean;
        public var evidenceDate:Date;
        public var evidenceEvaluationId:Number;
        public var evidenceEvaluationResult:Number;
        public var evidenceId:Number;
        public var evidencePath:String;
        public var evidenceType:EvidenceTypeEnum;
        public var flvPath:String;
        public var proofs:ArrayOfProofVO;
        public var viewOrder:Number;

        /**
         * Constructor, initializes the type class
         */
        public function EvidenceVO() {
        }

        public function fromDTO(dto:EvidenceDTO):void {
            this.camera = dto.camera;
            this.cantDoReason = dto.cantDoReason;
            this.defaultEvidenceProvider = dto.defaultEvidenceProvider;
            this.evidenceDate = dto.evidenceDate;
            this.evidenceEvaluationId = dto.evidenceEvaluationId;
            this.evidenceEvaluationResult = dto.evidenceEvaluationResult;
            this.evidenceId = dto.evidenceId;
            this.evidencePath = dto.evidencePath;
            this.evidenceType = EvidenceTypeEnum.getState(dto.evidenceType as String);
            this.flvPath = dto.flvPath;
            this.viewOrder = dto.viewOrder;
            if (dto.proofs != null && dto.proofs.length > 0) {
                this.proofs = new ArrayOfProofVO();

                for each (var proofDTO:ProofDTO in dto.proofs) {
                    var proofVO:ProofVO = new ProofVO();
                    proofVO.fromDTO(proofDTO);
                    this.proofs.addProofVO(proofVO);
                }
            }


        }

        public function toDTO():EvidenceDTO {
            var dto:EvidenceDTO = new EvidenceDTO();
            dto.camera = this.camera;
            dto.cantDoReason = this.cantDoReason;
            dto.defaultEvidenceProvider = this.defaultEvidenceProvider;
            dto.evidenceDate = this.evidenceDate;
            dto.evidenceEvaluationId = this.evidenceEvaluationId;
            dto.evidenceEvaluationResult = this.evidenceEvaluationResult;
            dto.evidenceId = this.evidenceId;
            dto.evidencePath = this.evidencePath;
            //dto.evidenceType = EvidenceType.getState(this.evidenceType as String);
            dto.flvPath = this.flvPath;
            dto.viewOrder = this.viewOrder;
            if (this.proofs != null && this.proofs.length > 0) {
                dto.proofs = new ArrayCollection();

                for each (var proofVO:ProofVO in this.proofs) {
                    var proofDTO:ProofDTO = proofVO.toDTO();
                    dto.proofs.addItem(proofDTO);
                }
            }

            return dto;
        }

        public function clone():EvidenceVO {

            var cloned:EvidenceVO = new EvidenceVO();

            cloned.camera = this.camera;
            cloned.cantDoReason = this.cantDoReason;
            cloned.defaultEvidenceProvider = this.defaultEvidenceProvider;
            cloned.evidenceDate = this.evidenceDate;
            cloned.evidenceEvaluationId = this.evidenceEvaluationId;
            cloned.evidenceEvaluationResult = this.evidenceEvaluationResult;
            cloned.evidenceId = this.evidenceId;
            cloned.evidencePath = this.evidencePath;
            cloned.evidenceType = this.evidenceType;
            cloned.flvPath = this.flvPath;
            cloned.viewOrder = this.viewOrder;
            if (this.proofs != null && this.proofs.length > 0) {
                cloned.proofs = new ArrayOfProofVO();

                for each (var proofVO:ProofVO in this.proofs) {
                    var clonedProof:ProofVO = proofVO.clone();
                    cloned.proofs.addProofVO(clonedProof);
                }
            }

            return cloned;
        }



        public function resultEvidence():String {
            var ret:String = new String();
            if (cantDoReason != null && cantDoReason.length > 0) {
                ret = cantDoReason;
            } else if (isNaN(evidenceEvaluationResult)) {
                ret = "";
            } else if (this.proofs.length == 1) {
                ret = new String(evidenceEvaluationResult.toString());
            } else {
                var proof1:ProofVO = proofs.getProofVOAt(0);
                var proof2:ProofVO = proofs.getProofVOAt(1);
                if (proof1.order == 0) {
                    ret = proof1.proofResult + "-" + proof2.proofResult;
                } else {
                    ret = proof2.proofResult + "-" + proof1.proofResult;
                }
            }
            return ret;
        }

        public function proofVisible(pos:Number):Boolean {
            var ret:Boolean = false;
            if (cantDoReason == null || cantDoReason.length == 0) {
                if (proofs != null && proofs.length > 0) {
                    for each (var proofVO:ProofVO in this.proofs) {
                        if (proofVO.order == pos) {
                            ret = true;
                        }
                    }
                }
            }
            return ret;
        }
    }
}
