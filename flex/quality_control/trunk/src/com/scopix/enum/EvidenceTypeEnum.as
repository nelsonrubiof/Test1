package com.scopix.enum
{
	import mx.resources.ResourceManager;
	
	public class EvidenceTypeEnum{
		
		public static const IMAGE:EvidenceTypeEnum = new EvidenceTypeEnum("evidencetype.image", 0);
		public static const VIDEO:EvidenceTypeEnum = new EvidenceTypeEnum("evidencetype.video", 1);
		public static const XML:EvidenceTypeEnum = new EvidenceTypeEnum("evidencetype.xml", 2);
		public static const PEOPLE_COUNTING:EvidenceTypeEnum = new EvidenceTypeEnum("evidencetype.people_counting", 3);
		public static const COGNIMATICS_PEOPLE_COUNTER_141:EvidenceTypeEnum = new EvidenceTypeEnum("evidencetype.cognimatics_people_counter_141", 4);
		public static const KUMGO_IMAGE:EvidenceTypeEnum = new EvidenceTypeEnum("evidencetype.kumgo_image", 5);
		
		public var value:String;
		public var ordinal:int;
		
		public function EvidenceTypeEnum(value:String, ordinal:int) {
			this.value = value;
			this.ordinal = ordinal;
		}
		public static function get list():Array {
			return [IMAGE,VIDEO,XML,PEOPLE_COUNTING,COGNIMATICS_PEOPLE_COUNTER_141,KUMGO_IMAGE];
		}
		
		public static function get comboList():Array {
			var cList:Array = EvidenceTypeEnum.list;
			//cList.unshift(ACTIVE);
			return cList;
		}
		
		public static function getState(value:String):EvidenceTypeEnum {
			var ret:EvidenceTypeEnum;
			
			if (value == IMAGE.value) {
				ret = IMAGE;
			} else if (value == VIDEO.value) {
				ret = VIDEO;
			} else if (value == XML.value) {
				ret = XML;
			} else if (value == PEOPLE_COUNTING.value) {
				ret = PEOPLE_COUNTING;
			} else if (value == COGNIMATICS_PEOPLE_COUNTER_141.value) {
				ret = COGNIMATICS_PEOPLE_COUNTER_141;
			} else if (value == KUMGO_IMAGE.value) {
				ret = KUMGO_IMAGE;
			}
			
			return ret;
		}
		
		public function equals(enum: EvidenceTypeEnum):Boolean {
			return (this.ordinal == enum.ordinal && this.value == enum.value);
		}
	}
}