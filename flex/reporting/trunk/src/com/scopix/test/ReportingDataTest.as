package com.scopix.test
{
    import com.scopix.periscope.webservices.businessservices.StatusSendEPCDTO;
    import com.scopix.periscope.webservices.businessservices.AreaTypeDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfAreaTypeDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfStoreDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfUploadProcessDetailDTO;
    import com.scopix.periscope.webservices.businessservices.StoreDTO;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDTO;
    import com.scopix.periscope.webservices.businessservices.UploadProcessDetailDTO;
    
    public class ReportingDataTest
    {
        private static var _instance:ReportingDataTest;
        private var _statusSendEPCDTO:StatusSendEPCDTO;
        
        public static function getInstance():ReportingDataTest {
            if(_instance == null) {
                _instance = new ReportingDataTest();
            }
            
            return _instance;
        }
        
        public function ReportingDataTest() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
        
        public function getStoreList():ArrayOfStoreDTO {
            var list:ArrayOfStoreDTO = new ArrayOfStoreDTO();
            
            var st:StoreDTO = new StoreDTO();
            
            st.id = 1;
            st.name = "glendale";
            st.description = "Glendale";
            
            list.addStoreDTO(st);
            
            st = new StoreDTO();
            st.id = 2;
            st.name = "rapid_city";
            st.description = "Rapid City";
            
            list.addStoreDTO(st);

            st = new StoreDTO();
            st.id = 3;
            st.name = "hamburg";
            st.description = "Hamburg";
            
            list.addStoreDTO(st);
            
            return list;
        }
        
        public function getAreaTypeList():ArrayOfAreaTypeDTO {
            var list:ArrayOfAreaTypeDTO = new ArrayOfAreaTypeDTO();
            
            var at:AreaTypeDTO = new AreaTypeDTO();
            
            at.id = 1;
            at.name = "FOOTWEAR";
            at.description = "Footwear";
            
            list.addAreaTypeDTO(at);
            
            at = new AreaTypeDTO();
            at.id = 2;
            at.name = "GUN SAFES";
            at.description = "Gun Safes";
            
            list.addAreaTypeDTO(at);
            
            at = new AreaTypeDTO();
            at.id = 3;
            at.name = "OPTICS";
            at.description = "Optics";
            
            list.addAreaTypeDTO(at);
            
            return list;
        }
        
        public function getUploadProcessDTO():UploadProcessDTO {
            var dto:UploadProcessDTO = new UploadProcessDTO();
            
            dto.id = 1;
            dto.dateProcess = new Date().toDateString();
            dto.processState = "Running";
            dto.percentGlobal = 34;
            dto.processedGlobal = 68;
            dto.totalGlobal = 200;
            
            return dto;
        }
        
        private function getOnProcessedList():ArrayOfUploadProcessDetailDTO {
            var list:ArrayOfUploadProcessDetailDTO = new ArrayOfUploadProcessDetailDTO();
            
            var upd:UploadProcessDetailDTO = new UploadProcessDetailDTO();
            
            var at:AreaTypeDTO = new AreaTypeDTO();
            
            at.id = 1;
            at.name = "FOOTWEAR";
            at.description = "Footwear";
            
            upd.id = 1;
            upd.areaType = at;
            upd.totalRecords = 50;
            upd.upRecords = 25;
            upd.percent = 50;

            var st:StoreDTO = new StoreDTO();
            st.id = 3;
            st.name = "hamburg";
            st.description = "Hamburg";

            upd.store = st;
            
            list.addUploadProcessDetailDTO(upd);
            
            
            upd = new UploadProcessDetailDTO();
            
            at = new AreaTypeDTO();
            at.id = 2;
            at.name = "GUN SAFES";
            at.description = "Gun Safes";
            
            upd.id = 2;
            upd.areaType = at;
            upd.totalRecords = 50;
            upd.upRecords = 25;
            upd.percent = 50;

            st = new StoreDTO();
            st.id = 1;
            st.name = "glendale";
            st.description = "Glendale";

            upd.store = st;
            
            list.addUploadProcessDetailDTO(upd);
            
            
            return list;
        }
    }
}