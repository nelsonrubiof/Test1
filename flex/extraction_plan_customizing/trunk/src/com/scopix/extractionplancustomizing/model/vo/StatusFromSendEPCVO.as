package com.scopix.extractionplancustomizing.model.vo {
	import com.scopix.periscope.webservices.businessservices.valueObjects.StatusSendEPCDTO;

	public class StatusFromSendEPCVO {
		public var current:Number;
		public var max:Number;
		public var message:String;
		public var status:Number;

		public function StatusFromSendEPCVO() {
		}

		public function fromDTO(dto:StatusSendEPCDTO):void {
			this.current = dto.current;
			this.max = dto.max;
			this.message = dto.message;
			this.status = dto.status;
		}
	}
}