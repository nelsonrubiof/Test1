/**
 * ArrayOfEvidenceVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.EvidenceVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;

	/**
	 * Typed array collection
	 */

	public class ArrayOfEvidenceVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfEvidenceVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addEvidenceVOAt(item:EvidenceVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addEvidenceVO(item:EvidenceVO):void 
		{
			addItem(item);
		} 

		public function getEvidenceVOAt(index:int):EvidenceVO 
		{
			return getItemAt(index) as EvidenceVO;
		}

		public function getEvidenceVOIndex(item:EvidenceVO):int 
		{
			return getItemIndex(item);
		}

		public function setEvidenceVOAt(item:EvidenceVO,index:int):void 
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
