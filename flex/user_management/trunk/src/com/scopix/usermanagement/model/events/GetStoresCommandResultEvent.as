package com.scopix.usermanagement.model.events {
    import com.scopix.usermanagement.model.arrays.ArrayOfStoreVO;

    import commons.events.GenericEvent;

    public class GetStoresCommandResultEvent extends GenericEvent {
        public static var GET_STORES_EVENT:String = "get_stores_event";
        private var _stores:ArrayOfStoreVO;

        public function GetStoresCommandResultEvent(stores:ArrayOfStoreVO = null) {
            super(GET_STORES_EVENT);
            _stores = stores;
        }

        public function get stores():ArrayOfStoreVO {
            return _stores;
        }

        public function set stores(value:ArrayOfStoreVO):void {
            _stores = value;
        }

    }
}
