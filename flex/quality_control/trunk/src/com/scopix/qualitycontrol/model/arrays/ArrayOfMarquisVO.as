/**
 * ArrayOfMarquisDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.MarquisVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	import mx.utils.ObjectProxy;

	/**
	 * Typed array collection
	 */

	public class ArrayOfMarquisVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfMarquisVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addMarquisVOAt(item:MarquisVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addMarquisVO(item:MarquisVO):void 
		{
			addItem(item);
		} 

		public function getMarquisVOAt(index:int):MarquisVO 
		{
			return getItemAt(index) as MarquisVO;
		}

		public function getMarquisVOIndex(item:MarquisVO):int 
		{
			return getItemIndex(item);
		}

		public function setMarquisVOAt(item:MarquisVO,index:int):void 
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
