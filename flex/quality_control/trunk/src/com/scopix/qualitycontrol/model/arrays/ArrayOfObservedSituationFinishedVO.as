/**
 * ArrayOfStoreVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.qualitycontrol.model.arrays
{
	import com.scopix.qualitycontrol.model.vo.ObservedSituationFinishedVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	
	/**
	 * Typed array collection
	 */
	
	public class ArrayOfObservedSituationFinishedVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
		
		public function ArrayOfObservedSituationFinishedVO(source:Array = null)
		{
			super(source);
		}
		
		public function addObservedSituationFinishedVOAt(item:ObservedSituationFinishedVO,index:int):void 
		{
			addItemAt(item,index);
		}
		
		public function addObservedSituationFinishedVO(item:ObservedSituationFinishedVO):void 
		{
			addItem(item);
		} 
		
		public function getObservedSituationFinishedVOAt(index:int):ObservedSituationFinishedVO 
		{
			return getItemAt(index) as ObservedSituationFinishedVO;
		}
		
		public function getObservedSituationFinishedVOIndex(item:ObservedSituationFinishedVO):int 
		{
			return getItemIndex(item);
		}
		
		public function setObservedSituationFinishedVOAt(item:ObservedSituationFinishedVO,index:int):void 
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
