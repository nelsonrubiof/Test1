/**
 * ClasificacionDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.ClasificacionDTO;
    
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class ClasificacionVO {
        /**
         * Constructor, initializes the type class
         */
        public function ClasificacionVO() {
        }

        public var description:String;
        public var id:Number;

        public function fromDTO(dto:ClasificacionDTO):void {
            this.id = dto.id;
            this.description = dto.description;

        }

        public function toDTO():ClasificacionDTO {
            var dto:ClasificacionDTO = new ClasificacionDTO();

            dto.id = this.id;
            dto.description = this.description;

            return dto;
        }

        public function clone():ClasificacionVO {
            var cloned:ClasificacionVO = new ClasificacionVO();

            cloned.id = this.id;
            cloned.description = this.description;

            return cloned;
        }
    }
}
