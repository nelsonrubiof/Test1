package com.scopix.qualitycontrol.model {
    import com.scopix.qualitycontrol.model.arrays.ArrayOfAreaVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfClasificacionVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfCorporateVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfEvidenceVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfMetricResultVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfMotivoRejectedVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfObservedSituationFinishedVO;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfStoreVO;
    import com.scopix.qualitycontrol.model.vo.MetricResultVO;
    import com.scopix.qualitycontrol.model.vo.ObservedSituationFinishedVO;
    
    import flash.utils.Timer;
    
    import mx.effects.Glow;

    public class QualityControlModel {
        public static var MODEL:String = "MODEL";
        private static var _instance:QualityControlModel;

        //get status send epc
        public var getStatusTimer:Timer;

        //variables para el model
        [Bindable]
        public var runFilterEffect:Boolean;
        [Bindable]
        public var filterEffect:Glow;
        [Bindable]
        public var corporateList:ArrayOfCorporateVO;
        [Bindable]
        public var corporateIdSelected:Number;
        [Bindable]
        public var corporateDescriptionSelected:String = "";
        [Bindable]
        public var storesFilter:ArrayOfStoreVO;
        [Bindable]
        public var storeIdSelected:Number;
        [Bindable]
        public var storeDescriptionSelected:String;
        [Bindable]
        public var areasFilter:ArrayOfAreaVO;
        [Bindable]
        public var areaIdSelected:Number;
        [Bindable]
        public var areaDescriptionSelected:String;
        [Bindable]
        public var dateSelected:String;
        [Bindable]
        public var startHourSelected:String;
        [Bindable]
        public var endHourSelected:String;
        [Bindable]
        public var observedSituationFinishedList:ArrayOfObservedSituationFinishedVO;
        [Bindable]
        public var observedSituationFinishedSelected:ObservedSituationFinishedVO;
        [Bindable]
        public var metricResultList:ArrayOfMetricResultVO;
        [Bindable]
        public var metricResultSelected:MetricResultVO;
        [Bindable]
        public var evidenceResultList:ArrayOfEvidenceVO;
        [Bindable]
        public var proofPrePath:String;
        [Bindable]
        public var evidencePrePath:String;
        [Bindable]
        public var motivosRejected:ArrayOfMotivoRejectedVO;
        [Bindable]
        public var motivosRejectedSelected:Number;
        [Bindable]
        public var clasificaciones:ArrayOfClasificacionVO;
        [Bindable]
        public var clasificacionSelected:Number;
        [Bindable]
        public var textOperator:String;
        [Bindable]
        public var observaciones:String;

        [Bindable]
        public var evidenceProofUrl:String;
        [Bindable]
        public var templateUrl:String;

        [Bindable]
        public var rejectedVisible:Boolean;
        [Bindable]
        public var acceptVisible:Boolean;

        [Bindable]
        public var viewMakrs:Boolean = true;

        [Bindable]
        public var unmarkObservedSituationList:ArrayOfObservedSituationFinishedVO;
        [Bindable]
        public var remarkObservedSituationList:ArrayOfObservedSituationFinishedVO;
        [Bindable]
        public var percentValue:Number;
        //variable utilizada para indicar si el porcentaje indicado debe volver a ser seleccionado del total de registros
        [Bindable]
        public var reloadPercent:Boolean;

        public function QualityControlModel() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }

        public static function getInstance():QualityControlModel {
            if (_instance == null) {
                _instance = new QualityControlModel();
            }

            return _instance;
        }
    }
}
