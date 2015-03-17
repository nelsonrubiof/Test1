package com.scopix.usermanagement.model.events {
    import com.scopix.usermanagement.model.arrays.ArrayOfCorporateVO;

    import commons.events.GenericEvent;

    public class GetCorporatesCommandResultEvent extends GenericEvent {
        public static var GET_CORPORATES_EVENT:String = "get_corporates_event";
        private var _corporates:ArrayOfCorporateVO;

        public function GetCorporatesCommandResultEvent(corporates:ArrayOfCorporateVO = null) {
            super(GET_CORPORATES_EVENT);
            _corporates = corporates;
        }

        public function get corporates():ArrayOfCorporateVO {
            return _corporates;
        }

        public function set corporates(value:ArrayOfCorporateVO):void {
            _corporates = value;
        }
    }
}
