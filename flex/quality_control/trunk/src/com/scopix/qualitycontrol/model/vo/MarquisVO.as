/**
 * MarquisDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */

package com.scopix.qualitycontrol.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.MarquisDTO;
    
    import mx.rpc.soap.types.*;

    /**
     * Wrapper class for a operation required type
     */

    public class MarquisVO {
        public var color:Number;
        public var height:Number;
        public var width:Number;
        public var x:Number;
        public var y:Number;

        /**
         * Constructor, initializes the type class
         */
        public function MarquisVO() {
        }

        public function fromDTO(dto:MarquisDTO):void {
            this.color = dto.color;
            this.height = dto.height;
            this.width = dto.width;
            this.x = dto.x;
            this.y = dto.y;

        }

        public function toDTO():MarquisDTO {
            var dto:MarquisDTO = new MarquisDTO();

            dto.color = this.color;
            dto.height = this.height;
            dto.width = this.width;
            dto.x = this.x;
            dto.y = this.y;
            return dto;
        }

        public function clone():MarquisVO {
            var cloned:MarquisVO = new MarquisVO();

            cloned.color = this.color;
            cloned.height = this.height;
            cloned.width = this.width;
            cloned.x = this.x;
            cloned.y = this.y;

            return cloned;
        }
    }
}
