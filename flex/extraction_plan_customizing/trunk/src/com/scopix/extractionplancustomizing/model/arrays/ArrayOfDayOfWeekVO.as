/**
 * ArrayOfAreaTypeVO.as
 * This file was auto-generated from WSDL by the Apache Axis2 generator modified by Adobe
 * Any change made to this file will be overwritten when the code is re-generated.
 */
package com.scopix.extractionplancustomizing.model.arrays
{
	import com.scopix.extractionplancustomizing.model.vo.DayOfWeekVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	import mx.rpc.soap.types.*;
	/**
	 * Typed array collection
	 */

	public class ArrayOfDayOfWeekVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfDayOfWeekVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addDayOfWeekVOAt(item:DayOfWeekVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addDayOfWeekVO(item:DayOfWeekVO):void 
		{
			addItem(item);
		} 

		public function getDayOfWeekVOAt(index:int):DayOfWeekVO 
		{
			return getItemAt(index) as DayOfWeekVO;
		}

		public function getDayOfWeekVOIndex(item:DayOfWeekVO):int 
		{
			return getItemIndex(item);
		}

		public function setDayOfWeekVOAt(item:DayOfWeekVO,index:int):void 
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
		
		public function clone():ArrayOfDayOfWeekVO {
		    var resp:ArrayOfDayOfWeekVO = new ArrayOfDayOfWeekVO();
		    
		    for each (var dow:DayOfWeekVO in this) {
		        resp.addDayOfWeekVO(dow.clone());
		    }
		    
		    return resp;
		}
	}
}
