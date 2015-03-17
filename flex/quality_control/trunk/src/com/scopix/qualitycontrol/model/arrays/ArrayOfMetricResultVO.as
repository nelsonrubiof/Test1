/**
 * ArrayOfMetricResultVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.MetricResultVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	import mx.utils.ObjectProxy;

	/**
	 * Typed array collection
	 */

	public class ArrayOfMetricResultVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfMetricResultVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addMetricResultVOAt(item:MetricResultVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addMetricResultVO(item:MetricResultVO):void 
		{
			addItem(item);
		} 

		public function getMetricResultVOAt(index:int):MetricResultVO 
		{
			return getItemAt(index) as MetricResultVO;
		}

		public function getMetricResultVOIndex(item:MetricResultVO):int 
		{
			return getItemIndex(item);
		}

		public function setMetricResultVOAt(item:MetricResultVO,index:int):void 
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
