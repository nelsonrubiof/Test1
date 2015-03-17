package com.scopix.util
{
    public class DateUtil {
        
        public static function getInterval(dateStart:Date, dateEnd:Date):Number {
            var difSeconds:Number = Math.floor((dateEnd.getTime() - dateStart.getTime())/(1000));
            
            return difSeconds;
        }
        
        /**
        * Esta funcion parsea una fecha de la forma YYYY-MM-DD JJ:NN:SS. No formatea horas.
        */
        public static function parse(dateStr:String):Date {
            var dateResult:Date = null;
            
            if (dateStr == null) {
                throw new Error("Fecha no parseable");
            }
            
            var allArray:Array = dateStr.split(" ");
            var dateArray:Array = (allArray[0] as String).split("-");
            var timeArray:Array = null;
            if (allArray.length > 1) {
                timeArray = (allArray[1] as String).split(":");
                
                dateResult = new Date(dateArray[0],dateArray[1] - 1, dateArray[2], timeArray[0], timeArray[1], timeArray[2]);
            } else {
                dateResult = new Date(dateArray[0],dateArray[1] - 1, dateArray[2]);
            }
            
            return dateResult;
        }
    }
}