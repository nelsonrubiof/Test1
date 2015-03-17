/**
 * ArrayOfMotivoRejectedVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.MotivoRejectedVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	import mx.utils.ObjectProxy;

	/**
	 * Typed array collection
	 */

	public class ArrayOfMotivoRejectedVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfMotivoRejectedVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addMotivoRejectedVOAt(item:MotivoRejectedVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addMotivoRejectedVO(item:MotivoRejectedVO):void 
		{
			addItem(item);
		} 

		public function getMotivoRejectedVOAt(index:int):MotivoRejectedVO 
		{
			return getItemAt(index) as MotivoRejectedVO;
		}

		public function getMotivoRejectedVOIndex(item:MotivoRejectedVO):int 
		{
			return getItemIndex(item);
		}

		public function setMotivoRejectedVOAt(item:MotivoRejectedVO,index:int):void 
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
