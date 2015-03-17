/**
 * ArrayOfStoreVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.AreaVO;
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;

	/**
	 * Typed array collection
	 */

	public class ArrayOfAreaVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfAreaVO(source:Array = null)
		{
			super(source);
		}
        
		public function addAreaVOAt(item:AreaVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addAreaVO(item:AreaVO):void 
		{
			addItem(item);
		} 

		public function getAreaVOAt(index:int):AreaVO 
		{
			return getItemAt(index) as AreaVO;
		}

		public function getAreaVOIndex(item:AreaVO):int 
		{
			return getItemIndex(item);
		}

		public function setAreaVOAt(item:AreaVO,index:int):void 
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
