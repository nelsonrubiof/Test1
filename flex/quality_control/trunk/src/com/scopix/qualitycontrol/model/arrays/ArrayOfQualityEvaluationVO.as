/**
 * ArrayOfQualityEvaluationVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.QualityEvaluationVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	import mx.utils.ObjectProxy;

	/**
	 * Typed array collection
	 */

	public class ArrayOfQualityEvaluationVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfQualityEvaluationVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addQualityEvaluationVOAt(item:QualityEvaluationVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addQualityEvaluationVO(item:QualityEvaluationVO):void 
		{
			addItem(item);
		} 

		public function getQualityEvaluationVOAt(index:int):QualityEvaluationVO 
		{
			return getItemAt(index) as QualityEvaluationVO;
		}

		public function getQualityEvaluationVOIndex(item:QualityEvaluationVO):int 
		{
			return getItemIndex(item);
		}

		public function setQualityEvaluationVOAt(item:QualityEvaluationVO,index:int):void 
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
