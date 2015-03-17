package com.scopix.test
{
    import com.scopix.periscope.webservices.businessservices.StatusSendEPCDTO;
    import com.scopix.periscope.webservices.businessservices.AreaTypeDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfEvidenceProviderDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfExtractionPlanCustomizingDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfExtractionPlanMetricDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfExtractionPlanRangeDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfInt;
    import com.scopix.periscope.webservices.businessservices.ArrayOfMetricTemplateDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfSituationSensorDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfSituationTemplateDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfStoreDTO;
    import com.scopix.periscope.webservices.businessservices.ArrayOfString;
    import com.scopix.periscope.webservices.businessservices.EvidenceProviderDTO;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanCustomizingDTO;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanMetricDTO;
    import com.scopix.periscope.webservices.businessservices.ExtractionPlanRangeDTO;
    import com.scopix.periscope.webservices.businessservices.MetricTemplateDTO;
    import com.scopix.periscope.webservices.businessservices.SituationSensorDTO;
    import com.scopix.periscope.webservices.businessservices.SituationTemplateDTO;
    import com.scopix.periscope.webservices.businessservices.StoreDTO;
    
    public class DataTest
    {
        private static var _instance:DataTest;
        private var _statusSendEPCDTO:StatusSendEPCDTO;
        
        public static function getInstance():DataTest {
            if(_instance == null) {
                _instance = new DataTest();
            }
            
            return _instance;
        }
        
        public function DataTest() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }
        
        public function getEPCListDTO(storeId:Number = 1):ArrayOfExtractionPlanCustomizingDTO {
            var list:ArrayOfExtractionPlanCustomizingDTO = new ArrayOfExtractionPlanCustomizingDTO;
            
            var epc:ExtractionPlanCustomizingDTO = new ExtractionPlanCustomizingDTO();
            
            var store:StoreDTO = null;
            var st:SituationTemplateDTO = null;
            var at:AreaTypeDTO = null;
            
            if (storeId == 1) {
                store = new StoreDTO();
                store.id = 1;
                store.description = "Grand Junction";
                store.name = "Grand Junction";
                
                st = new SituationTemplateDTO();
                st.id = 1;
                st.name = "SA_Staffing_Archery";
                
                at = new AreaTypeDTO();
                at.id = 1;
                at.description = "Archery";
                at.name = "archery";
                
                epc.id = 1;
                epc.status = "EDITION";
                epc.storeId = store.id;
                epc.situationTemplateId = st.id;
                epc.areaTypeId = at.id;
                epc.areaType = at.description;
                epc.active = true;
                epc.send = true;
                
                list.addExtractionPlanCustomizingDTO(epc);
                
                epc = new ExtractionPlanCustomizingDTO();
                
                store = new StoreDTO();
                store.id = 1;
                store.description = "Grand Junction";
                store.name = "Grand Junction";
                
                st = new SituationTemplateDTO();
                st.id = 2;
                st.name = "SA_Attention_Archery";
                
                at = new AreaTypeDTO();
                at.id = 1;
                at.description = "Archery";
                at.name = "archery";
                
                epc.id = 3;
                epc.status = "EDITION";
                epc.storeId = store.id;
                epc.situationTemplateId = st.id;
                epc.areaTypeId = at.id;
                epc.areaType = at.description;
                epc.active = true;
                epc.send = true;
                
                list.addExtractionPlanCustomizingDTO(epc);
            } else if (storeId == 2) {
                epc = new ExtractionPlanCustomizingDTO();
                
                store = new StoreDTO();
                store.id = 2;
                store.description = "Rapid City";
                store.name = "Rapid City";
                
                st = new SituationTemplateDTO();
                st.id = 1;
                st.name = "SA_Staffing_Archery";
                
                at = new AreaTypeDTO();
                at.id = 1;
                at.description = "Archery";
                at.name = "archery";
                
                epc.id = 2;
                epc.status = "SENT";
                epc.storeId = store.id;
                epc.situationTemplateId = st.id;
                epc.areaTypeId = at.id;
                epc.areaType = at.description;
                epc.active = true;
                epc.send = true;
                
                list.addExtractionPlanCustomizingDTO(epc);
            }
            
            return list;
        }
        
        public function getStoreListDTO():ArrayOfStoreDTO {
            var list:ArrayOfStoreDTO = new ArrayOfStoreDTO();
            
            var store:StoreDTO = new StoreDTO();
            store.id = 1;
            store.description = "Grand Junction";
            store.name = "grand_junction";
            
            list.addStoreDTO(store);
            
            store = new StoreDTO();
            store.id = 2;
            store.description = "Rapid City";
            store.name = "rapid_city";
            
            list.addStoreDTO(store);

            store = new StoreDTO();
            store.id = 3;
            store.description = "Glendale";
            store.name = "glendale";
            
            list.addStoreDTO(store);
            
            store = new StoreDTO();
            store.id = 4;
            store.description = "Fort Worth";
            store.name = "fort_worth";
            
            list.addStoreDTO(store);

            return list;
        }
        
        public function getSituationTemplateListDTO():ArrayOfSituationTemplateDTO {
            var list:ArrayOfSituationTemplateDTO = new ArrayOfSituationTemplateDTO();
            
            var st:SituationTemplateDTO = new SituationTemplateDTO();
            st.name = "SA_Attention_Archery ";
            st.id = 1;
            
            list.addSituationTemplateDTO(st);
            
            st = new SituationTemplateDTO();
            st.name = "SA_Attention_Camo";
            st.id = 2;
            
            list.addSituationTemplateDTO(st);

            st = new SituationTemplateDTO();
            st.name = "SA_Attention_Camping";
            st.id = 3;
            
            list.addSituationTemplateDTO(st);

            st = new SituationTemplateDTO();
            st.name = "SA_Staffing_Archery";
            st.id = 4;
            
            list.addSituationTemplateDTO(st);

            st = new SituationTemplateDTO();
            st.name = "SA_Staffing_Camo";
            st.id = 5;
            
            list.addSituationTemplateDTO(st);

            st = new SituationTemplateDTO();
            st.name = "SA_Staffing_Camping";
            st.id = 6;
            
            list.addSituationTemplateDTO(st);
            
            return list;
        }
        
        public function getEvidenceProviderList(epcId:Number):ArrayOfEvidenceProviderDTO {
            var epList:ArrayOfEvidenceProviderDTO = new ArrayOfEvidenceProviderDTO();
            
            var ep:EvidenceProviderDTO = new EvidenceProviderDTO();
            ep.id = 1;
            ep.description = "Camara 11";
            
            epList.addEvidenceProviderDTO(ep);
            
            ep = new EvidenceProviderDTO();
            ep.id = 2;
            ep.description = "Camara 12";
            
            epList.addEvidenceProviderDTO(ep);

            ep = new EvidenceProviderDTO();
            ep.id = 3;
            ep.description = "Camara 13";
            
            epList.addEvidenceProviderDTO(ep);

            ep = new EvidenceProviderDTO();
            ep.id = 4;
            ep.description = "Camara 14";
            
            epList.addEvidenceProviderDTO(ep);

            return epList;
        }
        
        public function getSensorList(epcId:Number):ArrayOfSituationSensorDTO {
            var sensorList:ArrayOfSituationSensorDTO = new ArrayOfSituationSensorDTO();
            
            var sensorDTO:SituationSensorDTO = new SituationSensorDTO();
            sensorDTO.id = 1;
            sensorDTO.name = "CAM11";
            sensorDTO.description = "Motion detection en Camara 11";
            
            sensorList.addSituationSensorDTO(sensorDTO);

            sensorDTO = new SituationSensorDTO();
            sensorDTO.id = 2;
            sensorDTO.name = "CAM12";
            sensorDTO.description = "Motion detection en Camara 12";
            
            sensorList.addSituationSensorDTO(sensorDTO);

            sensorDTO = new SituationSensorDTO();
            sensorDTO.id = 3;
            sensorDTO.name = "CAM13";
            sensorDTO.description = "Motion detection en Camara 13";

            sensorList.addSituationSensorDTO(sensorDTO);

            sensorDTO = new SituationSensorDTO();
            sensorDTO.id = 4;
            sensorDTO.name = "CAM14";
            sensorDTO.description = "Motion detection en Camara 14";

            sensorList.addSituationSensorDTO(sensorDTO);
            
            return sensorList;
        }
        
        public function getMetricTemplateList():ArrayOfMetricTemplateDTO {
            var mtList:ArrayOfMetricTemplateDTO = new ArrayOfMetricTemplateDTO();
            
            var mt:MetricTemplateDTO = new MetricTemplateDTO();
            mt.id = 1;
            mt.name = "Approaching Time";
            mt.description = "Elapsed time";
            
            mtList.addMetricTemplateDTO(mt);
            
            mt = new MetricTemplateDTO();
            mt.id = 2;
            mt.name = "Attended/Not Attended";
            mt.description = "Attended/Not Attended";
            
            mtList.addMetricTemplateDTO(mt);

            mt = new MetricTemplateDTO();
            mt.id = 3;
            mt.name = "Count customers";
            mt.description = "Count customers";
            
            mtList.addMetricTemplateDTO(mt);

            mt = new MetricTemplateDTO();
            mt.id = 4;
            mt.name = "Count salespeople ";
            mt.description = "Count salespeople";
            
            mtList.addMetricTemplateDTO(mt);

            mt = new MetricTemplateDTO();
            mt.id = 5;
            mt.name = "Free saleperson ";
            mt.description = "Free saleperson";
            
            mtList.addMetricTemplateDTO(mt);

            return mtList;
        }
        
        public function getEPC(stId:Number, storeId:Number):ExtractionPlanCustomizingDTO {
            var dto:ExtractionPlanCustomizingDTO = new ExtractionPlanCustomizingDTO();
            dto.extractionPlanMetricDTOs = new ArrayOfExtractionPlanMetricDTO();
            
            dto.id = 34543;
            dto.storeId = storeId;
            dto.areaTypeId = 1;
            dto.situationTemplateId = stId;
            dto.active = true;
            dto.oneEvaluation = true;
            //dto.providerIds = new ArrayOfInt([1,2,3]);
            dto.sensorIds = new ArrayOfInt([1,3]);
            
            var metricDTO:ExtractionPlanMetricDTO = new ExtractionPlanMetricDTO();
            metricDTO.id = 1;
            metricDTO.metricTemplateId = 1;
            metricDTO.metricVariableName = "AT";
            metricDTO.evaluationOrder = 3;

            dto.extractionPlanMetricDTOs.addExtractionPlanMetricDTO(metricDTO);
            
            metricDTO = new ExtractionPlanMetricDTO();
            metricDTO.id = 2;
            metricDTO.metricTemplateId = 2;
            metricDTO.metricVariableName = "AD";
            metricDTO.evaluationOrder = 1;

            dto.extractionPlanMetricDTOs.addExtractionPlanMetricDTO(metricDTO);

            metricDTO = new ExtractionPlanMetricDTO();
            metricDTO.id = 3;
            metricDTO.metricTemplateId = 5;
            metricDTO.metricVariableName = "FS";
            metricDTO.evaluationOrder = 4;

            dto.extractionPlanMetricDTOs.addExtractionPlanMetricDTO(metricDTO);

            return dto;
        }
        
        public function getMessages():ArrayOfString {
            var list:ArrayOfString = new ArrayOfString();
            
            list.addString("mensaje 1");
            list.addString("mensaje 2");
            list.addString("mensaje 3");
            list.addString("mensaje 4");
            
            return list;
        }
        
        public function getEPR():ArrayOfExtractionPlanRangeDTO {
            var list:ArrayOfExtractionPlanRangeDTO = new ArrayOfExtractionPlanRangeDTO();
            
            var epr:ExtractionPlanRangeDTO = new ExtractionPlanRangeDTO();
            
            epr.id = 1;
            epr.dayOfWeek = 1;
            epr.duration = 60;
            epr.initialTime = "08:00"; 
            epr.endTime = "10:00";
            epr.frecuency = 15;
            epr.samples = 7;        //no es dato real, solo por poner algo
            
            list.addExtractionPlanRangeDTO(epr);
            
            epr = new ExtractionPlanRangeDTO();
            epr.id = 2;
            epr.dayOfWeek = 1;
            epr.duration = 60;
            epr.initialTime = "10:00"; 
            epr.endTime = "20:00";
            epr.frecuency = 15;
            epr.samples = 15;        //no es dato real, solo por poner algo
            
            list.addExtractionPlanRangeDTO(epr);

            epr = new ExtractionPlanRangeDTO();
            epr.id = 3;
            epr.dayOfWeek = 3;
            epr.duration = 100;
            epr.initialTime = "08:00"; 
            epr.endTime = "20:00";
            epr.frecuency = 15;
            epr.samples = 25;        //no es dato real, solo por poner algo
            
            list.addExtractionPlanRangeDTO(epr);

            epr = new ExtractionPlanRangeDTO();
            epr.id = 4;
            epr.dayOfWeek = 4;
            epr.duration = 100;
            epr.initialTime = "08:00"; 
            epr.endTime = "20:00";
            epr.frecuency = 15;
            epr.samples = 25;        //no es dato real, solo por poner algo
            
            list.addExtractionPlanRangeDTO(epr);
            
            return list;
        }
        
        public function getStatusSendEPCDTO():StatusSendEPCDTO {
        	if (_statusSendEPCDTO == null) {
        		_statusSendEPCDTO = new StatusSendEPCDTO();
        		_statusSendEPCDTO.status = -1;
        		_statusSendEPCDTO.current = 0;
        	}
        	
        	_statusSendEPCDTO.max = 250;
        	_statusSendEPCDTO.current = _statusSendEPCDTO.current + 10;
        	
        	if (_statusSendEPCDTO.current == _statusSendEPCDTO.max) {
        		_statusSendEPCDTO.message = "FINISH";
        		_statusSendEPCDTO.status = 0;
        	}
        	
        	return _statusSendEPCDTO;
        }
    }
}