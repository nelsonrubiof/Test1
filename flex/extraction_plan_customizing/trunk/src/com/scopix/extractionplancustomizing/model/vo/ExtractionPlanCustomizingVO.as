package com.scopix.extractionplancustomizing.model.vo {
    import com.scopix.enum.EPCStatesEnum;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingLastSentModel;
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingModel;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfInt;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSituationTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfStoreVO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.EvidenceProviderDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanCustomizingDTO;
    import com.scopix.periscope.webservices.businessservices.valueObjects.ExtractionPlanMetricDTO;
    import com.scopix.util.StateUtil;
    import com.scopix.util.UtilityFunctions;

    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.collections.SortField;

    public class ExtractionPlanCustomizingVO {
        public var id:Number;
        public var active:Boolean;
        public var store:StoreVO;
        public var situationTemplate:SituationTemplateVO;
        public var area:AreaTypeVO;
        public var status:EPCStatesEnum;
        public var multiCamera:Boolean;
        //public var evidenceProviders:ArrayOfEvidenceProviderVO;
        public var sensors:ArrayOfSensorVO;
        public var metricTemplates:ArrayOfMetricTemplateVO;
        public var canBeSent:Boolean;
        public var priorization:Number;
        public var randomCamera:Boolean;

        public function ExtractionPlanCustomizingVO() {
        }

        public function fromDTO(dto:ExtractionPlanCustomizingDTO, type:String):void {
            var situationTemplateList:ArrayOfSituationTemplateVO = null;
            var storeList:ArrayOfStoreVO = null;
            var evidenceProviderList:ArrayOfEvidenceProviderVO = null;
            var sensorList:ArrayOfSensorVO = null
            var metricTemplateList:ArrayOfMetricTemplateVO = null;

            if (type == ExtractionPlanCustomizingModel.MODEL) {
                situationTemplateList = ExtractionPlanCustomizingModel.getInstance().stList;
                storeList = ExtractionPlanCustomizingModel.getInstance().storesFilter;
                evidenceProviderList = ExtractionPlanCustomizingModel.getInstance().evidenceProviderList;
                sensorList = ExtractionPlanCustomizingModel.getInstance().sensorList;
                metricTemplateList = ExtractionPlanCustomizingModel.getInstance().metricTemplateList;
            } else if (type == ExtractionPlanCustomizingLastSentModel.LAST_SENT_MODEL) {
                situationTemplateList = ExtractionPlanCustomizingLastSentModel.getInstance().stList;
                storeList = ExtractionPlanCustomizingLastSentModel.getInstance().storesFilter;
                evidenceProviderList = ExtractionPlanCustomizingLastSentModel.getInstance().evidenceProviderList;
                sensorList = ExtractionPlanCustomizingLastSentModel.getInstance().sensorList;
                metricTemplateList = ExtractionPlanCustomizingLastSentModel.getInstance().metricTemplateList;
            }

            this.id = dto.id;
            this.situationTemplate = UtilityFunctions.searchSituationTemplate(dto.situationTemplateId, situationTemplateList);
            this.store = UtilityFunctions.searchStore(dto.storeId, storeList);
            this.status = StateUtil.getInstance().getStateFromWS(dto.status);
            this.multiCamera = dto.oneEvaluation;
            this.active = dto.active;
            this.canBeSent = dto.send;
            this.priorization = dto.priorization;
            this.randomCamera = dto.randomCamera;

            var at:AreaTypeVO = new AreaTypeVO();
            at.id = dto.areaTypeId;
            at.description = dto.areaType;

            this.area = at;

            /*             if (dto.providerIds != null && dto.providerIds.length > 0) {
                            this.evidenceProviders = new ArrayOfEvidenceProviderVO();
                            for each (var epId:Number in dto.providerIds) {
                                //un provider id de un EPC debe existir en el listado completo obtenido previamente
                                var epTemp:EvidenceProviderVO = UtilityFunctions.searchEvidenceProvider(epId, evidenceProviderList);
                                epTemp.selected = true;

                                this.evidenceProviders.addEvidenceProviderVO(epTemp);
                            }
                        } */

            if (dto.sensorIds != null && dto.sensorIds.length > 0) {
                this.sensors = new ArrayOfSensorVO();
                for each (var sensorId:Number in dto.sensorIds) {
                    //un sensor id de un EPC debe existir en el listado completo obtenido previamente
                    var sensorTemp:SensorVO = UtilityFunctions.searchSensor(sensorId, sensorList)
                    sensorTemp.selected = true;

                    this.sensors.addSensorVO(sensorTemp);
                }
            }

            if (dto.extractionPlanMetricDTOs != null && dto.extractionPlanMetricDTOs.length > 0) {
                this.metricTemplates = new ArrayOfMetricTemplateVO();

                var epTemp:EvidenceProviderVO = null;

                for each (var mtDTO:ExtractionPlanMetricDTO in dto.extractionPlanMetricDTOs) {
                    //un extraction plan metric de un EPC debe existir en el listado completo obtenido previamente
                    //El ID del DTO es equivalente al EXTRACTIONPLANMETRICID del VO
                    //EL ID del VO es equivalente al METRICTEMPLATEID del DTO
                    var mtTemp:MetricTemplateVO = UtilityFunctions.searchMetricTemplate(mtDTO.metricTemplateId, metricTemplateList);
                    mtTemp.variableName = mtDTO.metricVariableName;
                    mtTemp.order = mtDTO.evaluationOrder;
                    mtTemp.extractionPlanMetricId = mtDTO.id;
                    mtTemp.selected = true;

                    mtTemp.children = new ArrayOfEvidenceProviderVO();
                    //	for each (var epId:Number in mtDTO.providerIds) {
                    for each (var epDTO:EvidenceProviderDTO in mtDTO.evidenceProviderDTOs) {
                        if (type == ExtractionPlanCustomizingModel.MODEL) { // en edicion
                            epTemp = UtilityFunctions.searchEvidenceProvider(epDTO.id, evidenceProviderList);
                        } else if (type == ExtractionPlanCustomizingLastSentModel.LAST_SENT_MODEL) { //
                            epTemp = new EvidenceProviderVO();
                            epTemp.fromDTO(epDTO);
                        }
                        if (epTemp != null) {
                            mtTemp.children.addEvidenceProviderVO(epTemp);
                        }
                        epTemp = null;
                    }

                    this.metricTemplates.addMetricTemplateVO(mtTemp);
                }
                //ordenando los metric templates de acuerdo al campo "order"
                var sField:SortField = new SortField();
                sField.name = "order";
                sField.numeric = true;

                var sort:Sort = new Sort();
                sort.fields = [sField];

                this.metricTemplates.sort = sort;
                this.metricTemplates.refresh();
            }
        }

        /**
         *
         * @return
         *
         */
        public function toDTO():ExtractionPlanCustomizingDTO {
            var dto:ExtractionPlanCustomizingDTO = new ExtractionPlanCustomizingDTO();

            dto.id = this.id;
            dto.situationTemplateId = this.situationTemplate.id;
            dto.storeId = this.store.id;
            dto.status = StateUtil.getInstance().getStateToSendWS(this.status);
            dto.areaTypeId = this.area.id;
            dto.active = this.active;
            dto.oneEvaluation = this.multiCamera;

            dto.priorization = this.priorization;
            dto.randomCamera = this.randomCamera;

            /*             dto.providerIds = new ArrayOfInt();
                        for each (var epTemp:EvidenceProviderVO in this.evidenceProviders) {
                            dto.providerIds.addNumber(epTemp.id);
                        } */

            dto.sensorIds = new ArrayOfInt();
            for each (var sensorTemp:SensorVO in this.sensors) {
                dto.sensorIds.addItem(sensorTemp.id);
            }

            dto.extractionPlanMetricDTOs = new ArrayCollection();
            var epMT:ExtractionPlanMetricDTO = null;
            for each (var mtTemp:MetricTemplateVO in this.metricTemplates) {
                epMT = new ExtractionPlanMetricDTO();

                epMT.id = mtTemp.extractionPlanMetricId;
                epMT.metricVariableName = mtTemp.variableName;
                epMT.metricTemplateId = mtTemp.id;
                epMT.evaluationOrder = mtTemp.order;

//				epMT.providerIds = new ArrayOfInt();
                epMT.evidenceProviderDTOs = new ArrayCollection();
                for each (var epTemp:EvidenceProviderVO in mtTemp.children) {
                    epMT.evidenceProviderDTOs.addItem(epTemp.toDTO());

//                    epMT.providerIds.addNumber(epTemp.id);
                }

                dto.extractionPlanMetricDTOs.addItem(epMT);
            }

            return dto;
        }

        public function clone():ExtractionPlanCustomizingVO {
            var epcVO:ExtractionPlanCustomizingVO = new ExtractionPlanCustomizingVO();

            epcVO.id = this.id;
            epcVO.store = this.store.clone();
            epcVO.situationTemplate = this.situationTemplate.clone();
            epcVO.area = this.area.clone();
            epcVO.status = this.status;
            epcVO.multiCamera = this.multiCamera;
            epcVO.priorization = this.priorization;
            epcVO.randomCamera = this.randomCamera;

            /*             epcVO.evidenceProviders = new ArrayOfEvidenceProviderVO();
                        for each (var epTemp:EvidenceProviderVO in this.evidenceProviders) {
                            epcVO.evidenceProviders.addEvidenceProviderVO(epTemp.clone());
                        }  */

            epcVO.sensors = new ArrayOfSensorVO();
            for each (var sensorTemp:SensorVO in this.sensors) {
                epcVO.sensors.addSensorVO(sensorTemp.clone());
            }

            epcVO.metricTemplates = new ArrayOfMetricTemplateVO();
            for each (var mtTemp:MetricTemplateVO in this.metricTemplates) {
                epcVO.metricTemplates.addMetricTemplateVO(mtTemp.clone());
            }

            return epcVO;
        }
    }
}
