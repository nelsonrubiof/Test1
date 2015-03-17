/**
 * ArrayOfAreaTypeVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.usermanagement.model.arrays
{
	import com.scopix.usermanagement.model.vo.AreaTypeVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfAreaTypeVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfAreaTypeVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addAreaTypeVOAt(item:AreaTypeVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addAreaTypeVO(item:AreaTypeVO):void 
		{
			addItem(item);
		} 

		public function getAreaTypeVOAt(index:int):AreaTypeVO 
		{
			return getItemAt(index) as AreaTypeVO;
		}

		public function getAreaTypeVOIndex(item:AreaTypeVO):int 
		{
			return getItemIndex(item);
		}

		public function setAreaTypeVOAt(item:AreaTypeVO,index:int):void 
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
