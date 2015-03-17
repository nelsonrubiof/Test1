package com.scopix.util
{
    public class DateUtil {
        
        public static function getInterval(dateStart:Date, dateEnd:Date):Number {
            var difSeconds:Number = Math.floor((dateEnd.getTime() - dateStart.getTime())/(1000));
            
            return difSeconds;
        }
    }
}