package com.scopix.reporting.model.events
{
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    
    import commons.events.GenericEvent;

    public class GetUploadProcessDetailListCommandResultEvent extends GenericEvent
    {
        public static var GET_UPLOAD_PROCESS_DETAIL_EVENT:String = "get_upload_process_detail_event";
        private var _uploadProcessDetailList:ArrayOfUploadProcessDetailVO;
        
        public function GetUploadProcessDetailListCommandResultEvent(val:ArrayOfUploadProcessDetailVO = null)
        {
            super(GET_UPLOAD_PROCESS_DETAIL_EVENT);
            _uploadProcessDetailList = val;
        }
        
        public function get uploadProcessDetailList():ArrayOfUploadProcessDetailVO {
            return _uploadProcessDetailList;
        }
        
        public function set uploadProcessDetailList(val:ArrayOfUploadProcessDetailVO):void {
            _uploadProcessDetailList = val;
        }
    }
}