/**
 * ArrayOfStoreVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.reporting.model.arrays
{
	import com.scopix.reporting.model.vo.StoreVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfStoreVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfStoreVO(source:Array = null)
		{
			super(source);
		}
        
		public function addStoreVOAt(item:StoreVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addStoreVO(item:StoreVO):void 
		{
			addItem(item);
		} 

		public function getStoreVOAt(index:int):StoreVO 
		{
			return getItemAt(index) as StoreVO;
		}

		public function getStoreVOIndex(item:StoreVO):int 
		{
			return getItemIndex(item);
		}

		public function setStoreVOAt(item:StoreVO,index:int):void 
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
