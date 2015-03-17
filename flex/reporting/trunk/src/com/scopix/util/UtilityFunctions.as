package com.scopix.util
{
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfEvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfMetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSensorVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfSituationTemplateVO;
    import com.scopix.extractionplancustomizing.model.arrays.ArrayOfStoreVO;
    import com.scopix.extractionplancustomizing.model.vo.EvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    import com.scopix.extractionplancustomizing.model.vo.SensorVO;
    import com.scopix.extractionplancustomizing.model.vo.SituationTemplateVO;
    import com.scopix.extractionplancustomizing.model.vo.StoreVO;
    import com.scopix.global.GlobalParameters;
    
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.collections.SortField;
    import mx.resources.ResourceManager;
    
    public class UtilityFunctions
    {
        public static function searchSituationTemplate(stId:Number, list:ArrayOfSituationTemplateVO):SituationTemplateVO {
            var st:SituationTemplateVO = null;
            
            for each (var vo:SituationTemplateVO in list) {
                if (vo.id == stId) {
                    st = vo;
                    break;
                }
            }
            
            return st;
        }
        
        public static function searchStore(storeId:Number, list:ArrayOfStoreVO):StoreVO {
            var store:StoreVO = null;
            
            for each (var vo:StoreVO in list) {
                if (vo.id == storeId) {
                    store = vo;
                    break;
                }
            }
            
            return store;
        }
        
        public static function searchEvidenceProvider(epId:Number, list:ArrayOfEvidenceProviderVO):EvidenceProviderVO {
            var ep:EvidenceProviderVO = new EvidenceProviderVO();
            
            for each (var vo:EvidenceProviderVO in list) {
                if (vo.id == epId) {
                    ep = vo;
                    break;
                }
            }
            
            return ep;
        }

        public static function searchSensor(sensorId:Number, list:ArrayOfSensorVO):SensorVO{
            var sensor:SensorVO = new SensorVO();
            
            for each (var vo:SensorVO in list) {
                if (vo.id == sensorId) {
                    sensor = vo;
                    break;
                }
            }
            
            return sensor;
        }

        public static function searchMetricTemplate(mtId:Number, list:ArrayOfMetricTemplateVO):MetricTemplateVO {
            var mt:MetricTemplateVO = new MetricTemplateVO();
            
            for each (var vo:MetricTemplateVO in list) {
                if (vo.id == mtId) {
                    mt = vo;
                    break;
                }
            }
            
            return mt;
        }
        
        public static function markSelected(allElements:ArrayCollection, selectedElements:ArrayCollection):void {
            for each (var obj:Object in allElements) {
                obj.selected = false;
                for each (var obj2:Object in selectedElements) {
                    if (obj.id == obj2.id) {
                        obj.selected = true;
                        break;
                    }
                }
            }
        }
        
        /**
        * Funcion que ordena los datos de una lista de acuerdo al campo dado como parametro. Sirve para
        * lista de objetos personalizados (por ejemplo: ArrayOfEvidenceProviders, el cual contiene
        * EvidenceProviderVO.
        * La lista es ordenada por referencia.
        * */
        public static function orderList(field:String, list:ArrayCollection):void {
            var sField:SortField = new SortField();
            sField.name = field;
            sField.numeric = true;
            
            var sort:Sort = new Sort();
            sort.fields = [sField];
            
            list.sort = sort;
            list.refresh();
        }
        
        public static function getEPTypeDescription(epType:String):String {
            var rs:String = "";
            
            switch (epType) {
                case "FIXED":
                    rs = ResourceManager.getInstance().getString('resources','rangeday.eprdType.fix');
                    break;
                case "RANDOM":
                    rs = ResourceManager.getInstance().getString('resources','rangeday.eprdType.random');
                    break;
                case "AUTOMATIC_EVIDENCE":
                    rs = ResourceManager.getInstance().getString('resources','rangeday.eprdType.automatic');
                    break;
            }
            
            return rs;
        }
        
        public static function getNameOfDay(id:Number):String {
            return GlobalParameters.getInstance().daysOfWeek.getDayOfWeekVOAt(id - 1).name;
        }
    }
}