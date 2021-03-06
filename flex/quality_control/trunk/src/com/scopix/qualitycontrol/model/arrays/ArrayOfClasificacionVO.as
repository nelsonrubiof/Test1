/**
 * ArrayOfClasificacionVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.ClasificacionVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	import mx.utils.ObjectProxy;

	/**
	 * Typed array collection
	 */

	public class ArrayOfClasificacionVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfClasificacionVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addClasificacionVOAt(item:ClasificacionVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addClasificacionVO(item:ClasificacionVO):void 
		{
			addItem(item);
		} 

		public function getClasificacionVOAt(index:int):ClasificacionVO 
		{
			return getItemAt(index) as ClasificacionVO;
		}

		public function getClasificacionVOIndex(item:ClasificacionVO):int 
		{
			return getItemIndex(item);
		}

		public function setClasificacionVOAt(item:ClasificacionVO,index:int):void 
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
