/**
 * MotivoRejectedDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.ClasificacionDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.MotivoRejectedDTO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfClasificacionVO;
    
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class MotivoRejectedVO {
        /**
         * Constructor, initializes the type class
         */
        public function MotivoRejectedVO() {
        }

        public var clasificacionVOs:ArrayOfClasificacionVO;
        public var description:String;
        public var id:Number;

        public function fromDTO(dto:MotivoRejectedDTO):void {
            this.id = dto.id;
            this.description = dto.description;
            if (dto.clasificacionDTOs != null && dto.clasificacionDTOs.length > 0) {
                this.clasificacionVOs = new ArrayOfClasificacionVO();
                for each (var data:ClasificacionDTO in dto.clasificacionDTOs) {
                    var clasificacion:ClasificacionVO = new ClasificacionVO();
                    clasificacion.fromDTO(data);
                    this.clasificacionVOs.addClasificacionVO(clasificacion);
                }
            }

        }

        public function toDTO():MotivoRejectedDTO {
            var dto:MotivoRejectedDTO = new MotivoRejectedDTO();

            dto.id = this.id;
            dto.description = this.description;

            return dto;
        }

        public function clone():MotivoRejectedVO {
            var cloned:MotivoRejectedVO = new MotivoRejectedVO();

            cloned.id = this.id;
            cloned.description = this.description;

            return cloned;
        }
    }
}
