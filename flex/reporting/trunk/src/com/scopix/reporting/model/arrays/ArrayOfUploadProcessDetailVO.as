/**
 * ArrayOfUploadProcessVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.reporting.model.arrays
{
	import com.scopix.reporting.model.vo.UploadProcessDetailVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfUploadProcessDetailVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfUploadProcessDetailVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addUploadProcessDetailVOAt(item:UploadProcessDetailVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addUploadProcessDetailVO(item:UploadProcessDetailVO):void 
		{
			addItem(item);
		} 

		public function getUploadProcessDetailVOAt(index:int):UploadProcessDetailVO 
		{
			return getItemAt(index) as UploadProcessDetailVO;
		}

		public function getUploadProcessDetailVOIndex(item:UploadProcessDetailVO):int 
		{
			return getItemIndex(item);
		}

		public function setUploadProcessDetailVOAt(item:UploadProcessDetailVO,index:int):void 
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
