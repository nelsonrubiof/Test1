package com.scopix.extractionplancustomizing.model.vo
{
    public class DayOfWeekVO
    {
        public var id:Number;
        public var name:String;
        public var selected:Boolean;
        
        public function DayOfWeekVO()
        {
        }
        
        public function clone():DayOfWeekVO {
            var dayOfWeek:DayOfWeekVO = new DayOfWeekVO();
            
            dayOfWeek.id = this.id;
            dayOfWeek.name = this.name;
            dayOfWeek.selected = this.selected;
            
            return dayOfWeek;
        }
    }
}