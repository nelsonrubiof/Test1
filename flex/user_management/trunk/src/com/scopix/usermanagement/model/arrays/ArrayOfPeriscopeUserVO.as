/**
 * ArrayOfUserVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.usermanagement.model.arrays
{
	import com.scopix.usermanagement.model.vo.PeriscopeUserVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfPeriscopeUserVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfPeriscopeUserVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addUserVOAt(item:PeriscopeUserVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addUserVO(item:PeriscopeUserVO):void 
		{
			addItem(item);
		} 

		public function getUserVOAt(index:int):PeriscopeUserVO 
		{
			return getItemAt(index) as PeriscopeUserVO;
		}

		public function getUserVOIndex(item:PeriscopeUserVO):int 
		{
			return getItemIndex(item);
		}

		public function setUserVOAt(item:PeriscopeUserVO,index:int):void 
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
		
		public function clone():ArrayOfPeriscopeUserVO {
		    var resp:ArrayOfPeriscopeUserVO = new ArrayOfPeriscopeUserVO();
		    
		    for each (var pu:PeriscopeUserVO in this) {
		        resp.addUserVO(pu.clone());
		    }
		    
		    return resp;
		}
	}
}
