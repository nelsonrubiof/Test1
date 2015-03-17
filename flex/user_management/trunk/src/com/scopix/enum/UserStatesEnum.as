package com.scopix.enum
{
    public class UserStatesEnum
    {
        public static const ACTIVE:UserStatesEnum = new UserStatesEnum("active", 1);
        public static const INACTIVE:UserStatesEnum = new UserStatesEnum("inactive", 2);
        
        public var value:String;
        public var ordinal:int;
        
        public function UserStatesEnum(value:String, ordinal:int) {
            this.value = value;
            this.ordinal = ordinal;
        }
        
        public static function get list():Array {
            return [ACTIVE, INACTIVE];
        }
        
        public static function get comboList():Array {
            var cList:Array = UserStatesEnum.list;
            //cList.unshift(ACTIVE);
            return cList;
        }
        
        public static function getState(value:String):UserStatesEnum {
            var ret:UserStatesEnum;
            
            if (value == ACTIVE.value) {
                ret = ACTIVE;
            } else if (value == INACTIVE.value) {
                ret = INACTIVE;
            }
            
            return ret;
        }
        
        public function equals(enum: UserStatesEnum):Boolean {
            return (this.ordinal == enum.ordinal && this.value == enum.value);
        }
    }
}