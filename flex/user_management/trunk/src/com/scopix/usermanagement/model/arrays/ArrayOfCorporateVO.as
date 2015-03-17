/**
 * ArrayOfCorporateVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.usermanagement.model.arrays
{
	import com.scopix.usermanagement.model.vo.CorporateVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfCorporateVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfCorporateVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addCorporateVOAt(item:CorporateVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addCorporateVO(item:CorporateVO):void 
		{
			addItem(item);
		} 

		public function getCorporateVOAt(index:int):CorporateVO 
		{
			return getItemAt(index) as CorporateVO;
		}

		public function getCorporateVOIndex(item:CorporateVO):int 
		{
			return getItemIndex(item);
		}

		public function setCorporateVOAt(item:CorporateVO,index:int):void 
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
		
		public function clone():ArrayOfCorporateVO {
		    var resp:ArrayOfCorporateVO = new ArrayOfCorporateVO();
		    
		    for each (var co:CorporateVO in this) {
		        resp.addCorporateVO(co.clone());
		    }
		    
		    return resp;
		}
	}
}
