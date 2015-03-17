package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.periscope.webservices.businessservices.valueObjects.SituationSensorDTO;

    public class SensorVO {
        public var id:Number;
        public var name:String;
        public var description:String;
        public var selected:Boolean;

        public function SensorVO() {
        }

        public function fromDTO(dto:SituationSensorDTO):void {
            this.id = dto.id;
            this.name = dto.name;
            this.description = dto.description;
            this.selected = false;
        }

        public function toDTO():SituationSensorDTO {
            var dto:SituationSensorDTO = new SituationSensorDTO();

            dto.id = this.id;
            dto.name = this.name;
            dto.description = this.description;

            return dto;
        }

        public function clone():SensorVO {
            var sensor:SensorVO = new SensorVO();

            sensor.id = this.id;
            sensor.name = this.name;
            sensor.description = this.description;
            sensor.selected = this.selected;

            return sensor;
        }
    }
}
