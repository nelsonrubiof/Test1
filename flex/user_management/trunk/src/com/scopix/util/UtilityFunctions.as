package com.scopix.util
{
    import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
    
    import flash.utils.ByteArray;
    
    import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
    import mx.formatters.DateFormatter;
    import mx.resources.ResourceManager;
    
    public class UtilityFunctions
    {
        public static function formatDate(item:Object, column:AdvancedDataGridColumn):String {
            var resp:String = "";
            var user:PeriscopeUserVO = item as PeriscopeUserVO;
            var df:DateFormatter = new DateFormatter();
            df.formatString = "DD/MM/YYYY HH:NN:SS";
            
            resp = df.format(user[column.dataField]);
            
            return resp;
        }
        
        public static function labelStatus(form:String, item:Object):String {
            var resp:String = "";
            var valor:String = item.value;
            
            resp = ResourceManager.getInstance().getString('resources',form + '.' + valor);
            
            return resp;
        }
    }
}