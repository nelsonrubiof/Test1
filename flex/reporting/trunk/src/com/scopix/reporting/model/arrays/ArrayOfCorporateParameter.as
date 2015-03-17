package com.scopix.reporting.model.arrays
{
	import com.scopix.reporting.model.vo.CorporateParameter;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

    public class ArrayOfCorporateParameter extends ArrayCollection
    {
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfCorporateParameter(source:Array = null)
		{
			super(source);
		}
        
        
		public function addCorporateParameterAt(item:CorporateParameter,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addCorporateParameter(item:CorporateParameter):void 
		{
			addItem(item);
		} 

		public function getCorporateParameterAt(index:int):CorporateParameter
		{
			return getItemAt(index) as CorporateParameter;
		}

		public function getCorporateParameterIndex(item:CorporateParameter):int 
		{
			return getItemIndex(item);
		}

		public function setCorporateParameterAt(item:CorporateParameter,index:int):void 
		{
			setItemAt(item,index);
		}

		public function asIList():IList 
		{
			return this as IList;
		}
        
		public function asICollectionView():ICollectionView 
		{
			return this as ICollectionView;
		}
    }
}