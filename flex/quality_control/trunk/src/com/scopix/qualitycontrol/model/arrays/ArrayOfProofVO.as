/**
 * ArrayOfProofDTO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.ProofVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	import mx.utils.ObjectProxy;

	/**
	 * Typed array collection
	 */

	public class ArrayOfProofVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfProofVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addProofVOAt(item:ProofVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addProofVO(item:ProofVO):void 
		{
			addItem(item);
		} 

		public function getProofVOAt(index:int):ProofVO 
		{
			return getItemAt(index) as ProofVO;
		}

		public function getProofVOIndex(item:ProofVO):int 
		{
			return getItemIndex(item);
		}

		public function setProofVOAt(item:ProofVO,index:int):void 
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
