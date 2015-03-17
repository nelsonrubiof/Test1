package com.scopix.extractionplancustomizing.model.events
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    
    import commons.events.GenericEvent;

    public class GetSensorListCommandResultEvent extends GenericEvent
    {
        public static var GET_SENSOR_LIST_EVENT:String = "get_sensor_list_event";
        
        private var _sensorList:ArrayOfSensorVO;
        
        public function GetSensorListCommandResultEvent(sensorList:ArrayOfSensorVO = null) {
            super(GET_SENSOR_LIST_EVENT);
            this._sensorList = sensorList;
        }
        
        public function get sensorList():ArrayOfSensorVO {
            return _sensorList;
        }
        
        public function set sensorList(list:ArrayOfSensorVO):void {
            this._sensorList = list;
        }
    }
}