/**
 * ArrayOfString.as
 */
package com.scopix.extractionplancustomizing.model.arrays {

    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;
    import mx.rpc.soap.types.*;

    /**
     * Typed array collection
     */

    public class ArrayOfString extends ArrayCollection {
        /**
         * Constructor - initializes the array collection based on a source array
         */

        public function ArrayOfString(source:Array=null) {
            super(source);
        }

        public function addStringAt(item:String, index:int):void {
            addItemAt(item, index);
        }

        public function addString(item:String):void {
            addItem(item);
        }

        public function getStringAt(index:int):String {
            return getItemAt(index) as String;
        }

        public function getStringIndex(item:String):int {
            return getItemIndex(item);
        }

        public function setStringAt(item:String, index:int):void {
            setItemAt(item, index);
        }

        public function asIList():IList {
            return this as IList;
        }

        public function asICollectionView():ICollectionView {
            return this as ICollectionView;
        }
    }
}
