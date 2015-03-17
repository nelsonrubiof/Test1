/**
 * ArrayOfUploadProcessVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.reporting.model.arrays
{
	import com.scopix.reporting.model.vo.UploadProcessVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfUploadProcessVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfUploadProcessVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addUploadProcessVOAt(item:UploadProcessVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addUploadProcessVO(item:UploadProcessVO):void 
		{
			addItem(item);
		} 

		public function getUploadProcessVOAt(index:int):UploadProcessVO 
		{
			return getItemAt(index) as UploadProcessVO;
		}

		public function getUploadProcessVOIndex(item:UploadProcessVO):int 
		{
			return getItemIndex(item);
		}

		public function setUploadProcessVOAt(item:UploadProcessVO,index:int):void 
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
