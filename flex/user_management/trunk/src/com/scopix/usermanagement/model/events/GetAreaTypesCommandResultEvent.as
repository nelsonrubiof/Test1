package com.scopix.usermanagement.model.events {
    import com.scopix.usermanagement.model.arrays.ArrayOfAreaTypeVO;

    import commons.events.GenericEvent;

    public class GetAreaTypesCommandResultEvent extends GenericEvent {
        public static var GET_AREA_TYPES_EVENT:String = "get_area_types_event";
        private var _areaTypes:ArrayOfAreaTypeVO;

        public function GetAreaTypesCommandResultEvent(areaTypes:ArrayOfAreaTypeVO = null) {
            super(GET_AREA_TYPES_EVENT);
            _areaTypes = areaTypes;
        }

        public function get areaTypes():ArrayOfAreaTypeVO {
            return _areaTypes;
        }

        public function set areaTypes(value:ArrayOfAreaTypeVO):void {
            _areaTypes = value;
        }
    }
}
