package com.scopix.util {

    import com.scopix.global.GlobalParameters;
    import com.scopix.periscope.webservices.businessservices.ArrayOfInt;
    import com.scopix.qualitycontrol.model.arrays.ArrayOfStoreVO;
    import com.scopix.qualitycontrol.model.vo.StoreVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.Sort;
    import mx.collections.SortField;
    import mx.resources.ResourceManager;

    public class UtilityFunctions {

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

        public static function getNameOfDay(id:Number):String {
            return GlobalParameters.getInstance().daysOfWeek.getDayOfWeekVOAt(id - 1).name;
        }
    }
}