package com.scopix.reporting.model.vo
{
    import com.scopix.periscope.webservices.businessservices.UploadProcessDTO;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailDTO;
    import com.scopix.reporting.model.arrays.ArrayOfUploadProcessDetailVO;
    import com.scopix.util.DateUtil;
    
    public class UploadProcessVO
    {
        public var id:Number;
		public var dateProcess:String;
		public var loginUser:String;
		public var loginUserRunning:String;
		public var motiveClosing:String;
		public var processDetails:ArrayOfUploadProcessDetailVO;
		public var processState:String;
		public var globalTotal:Number;
		public var globalProcessed:Number;
		public var globalPercent:Number;
		public var endDate:Date;
		public var startDate:Date;
		public var observations:String;

        public function UploadProcessVO()
        {
            globalTotal = 0;
            globalProcessed = 0;
            globalPercent = 0;
        }
        
        public function fromDTO(dto:UploadProcessDTO):void {
            this.id = dto.id;
            this.dateProcess = dto.dateProcess;
            this.loginUser = dto.loginUser;
			this.loginUserRunning = dto.loginUserRunning;
            this.motiveClosing = dto.motiveClosing;
            this.processState = dto.processState;
            this.processDetails = new ArrayOfUploadProcessDetailVO();
            this.globalTotal = dto.totalGlobal;
            this.globalProcessed = dto.processedGlobal;
            this.globalPercent = dto.percentGlobal;
            
            this.startDate = new Date(DateUtil.parse(dto.startDate));
			if (dto.endDate != null) {
				this.endDate = new Date(DateUtil.parse(dto.endDate));
			}

            this.processDetails = new ArrayOfUploadProcessDetailVO();
            var updVO:UploadProcessDetailVO = null;
            for each (var updDTO:UploadProcessDetailDTO in dto.processDetails) {
                updVO = new UploadProcessDetailVO();
                updVO.fromDTO(updDTO);
                
                this.processDetails.addUploadProcessDetailVO(updVO);
            }
			this.observations = dto.observations;
        }
    }
}