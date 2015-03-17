package com.scopix.enum
{
    import mx.resources.ResourceManager;
    
    public class EPCStatesEnum
    {
        public static const ALL:EPCStatesEnum = new EPCStatesEnum("epcstate.all", 0);
        public static const EDITION:EPCStatesEnum = new EPCStatesEnum("epcstate.edition", 1);
        public static const SENT:EPCStatesEnum = new EPCStatesEnum("epcstate.sent", 2);
        
        public var value:String;
        public var ordinal:int;
        
        public function EPCStatesEnum(value:String, ordinal:int) {
            this.value = value;
            this.ordinal = ordinal;
        }
        
        public static function get list():Array {
            return [ALL, EDITION, SENT];
        }
        
        public static function get comboList():Array {
            var cList:Array = EPCStatesEnum.list;
            //cList.unshift(ACTIVE);
            return cList;
        }
        
        public static function getState(value:String):EPCStatesEnum {
            var ret:EPCStatesEnum;
            
            if (value == ALL.value) {
                ret = ALL;
            } else if (value == EDITION.value) {
                ret = EDITION;
            } else if (value == SENT.value) {
                ret = SENT;
            }
            
            return ret;
        }
        
        public function equals(enum: EPCStatesEnum):Boolean {
            return (this.ordinal == enum.ordinal && this.value == enum.value);
        }
    }
}