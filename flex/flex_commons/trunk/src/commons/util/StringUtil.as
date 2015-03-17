package commons.util {
    import mx.collections.ArrayCollection;

	public class StringUtil {
		public function StringUtil() {
            throw new Error("This class can not be instanciated");
		}
        
        public static function join(list:ArrayCollection, separator:String):String {
            var str:String = "";
            
            var cont:int = 1;
            for each (var strIt:String in list) {
                if (cont == list.length) {
                    str = str.concat(strIt);
                } else {
                    str = str.concat(strIt).concat(separator);
                }
                
                cont++;
            }
            
            return str;
        }
	}
}