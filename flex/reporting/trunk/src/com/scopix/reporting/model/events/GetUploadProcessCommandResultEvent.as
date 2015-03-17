package com.scopix.reporting.model.events
{
    import com.scopix.reporting.model.vo.UploadProcessVO;
    
    import commons.events.GenericEvent;

    public class GetUploadProcessCommandResultEvent extends GenericEvent
    {
        public static var GET_UPLOAD_PROCESS_EVENT:String = "get_upload_process_event";
        private var _uploadProcess:UploadProcessVO;
        
        public function GetUploadProcessCommandResultEvent(val:UploadProcessVO = null)
        {
            super(GET_UPLOAD_PROCESS_EVENT);
            this._uploadProcess = val;
        }
        
        public function get uploadProcess():UploadProcessVO {
            return _uploadProcess;
        }
        
        public function set uploadProcess(val:UploadProcessVO):void {
            _uploadProcess = val;
        }
    }
}