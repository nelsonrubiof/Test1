package com.scopix.enum
{
	
	import mx.resources.ResourceManager;
	
	public class MetricTypeEnum{
		public static const YES_NO:MetricTypeEnum = new MetricTypeEnum("YES_NO", 0);
		public static const COUNTING:MetricTypeEnum = new MetricTypeEnum("COUNTING", 1);
		public static const MEASURE_TIME:MetricTypeEnum = new MetricTypeEnum("MEASURE_TIME", 2);
		
		public var value:String;
		public var ordinal:int;
		
		public function MetricTypeEnum(value:String, ordinal:int) {
			this.value = value;
			this.ordinal = ordinal;
		}
		
		public static function get list():Array {
			return [YES_NO, COUNTING, MEASURE_TIME];
		}
		
		public static function get comboList():Array {
			var cList:Array = MetricTypeEnum.list;
			//cList.unshift(ACTIVE);
			return cList;
		}
		
		public static function getState(value:String):MetricTypeEnum {
			var ret:MetricTypeEnum;
			
			if (value == YES_NO.value) {
				ret = YES_NO;
			} else if (value == COUNTING.value) {
				ret = COUNTING;
			} else if (value == MEASURE_TIME.value) {
				ret = MEASURE_TIME;
			}
			
			return ret;
		}
		
		public function equals(enum: MetricTypeEnum):Boolean {
			return (this.ordinal == enum.ordinal && this.value == enum.value);
		}
	}
}