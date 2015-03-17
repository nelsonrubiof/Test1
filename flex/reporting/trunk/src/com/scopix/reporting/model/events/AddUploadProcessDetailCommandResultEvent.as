package com.scopix.reporting.model.events
{
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    
    import commons.events.GenericEvent;

    public class AddUploadProcessDetailCommandResultEvent extends GenericEvent
    {
        public static var ADD_UPLOAD_PROCESS_DETAIL_EVENT:String = "add_upload_process_detail_event";
        
        private var _uploadProcessDetailListAggregate:ArrayOfUploadProcessDetailVO;
        private var _uploadProcessDetailListUnknow:ArrayOfUploadProcessDetailVO;
        
        public function AddUploadProcessDetailCommandResultEvent(valAggregate:ArrayOfUploadProcessDetailVO = null, valUnknow:ArrayOfUploadProcessDetailVO = null)
        {
            super(ADD_UPLOAD_PROCESS_DETAIL_EVENT);
            this._uploadProcessDetailListAggregate = valAggregate;
            this._uploadProcessDetailListUnknow = valUnknow;
        }
        
        public function get uploadProcessDetailListAggregate():ArrayOfUploadProcessDetailVO {
            return _uploadProcessDetailListAggregate
        }
        
        public function set uploadProcessDetailListAggregate(val:ArrayOfUploadProcessDetailVO):void {
            _uploadProcessDetailListAggregate = val;
        }
        
        public function get uploadProcessDetailListUnknow():ArrayOfUploadProcessDetailVO {
            return _uploadProcessDetailListUnknow;
        }
        
        public function set uploadProcessDetailListUnknow(val:ArrayOfUploadProcessDetailVO):void {
            _uploadProcessDetailListUnknow = val;
        }
    }
}