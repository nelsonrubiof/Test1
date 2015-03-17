package com.scopix.usermanagement.model.arrays
{
	import com.scopix.usermanagement.model.vo.RolesGroupVO;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;
	/**
	 * Typed array collection
	 */

	public class ArrayOfRolesGroupVO extends ArrayCollection
	{
		/**
		 * Constructor - initializes the array collection based on a source array
		 */
        
		public function ArrayOfRolesGroupVO(source:Array = null)
		{
			super(source);
		}
        
        
		public function addRolesGroupVOAt(item:RolesGroupVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addRolesGroupVO(item:RolesGroupVO):void 
		{
			addItem(item);
		} 

		public function getRolesGroupVOAt(index:int):RolesGroupVO 
		{
			return getItemAt(index) as RolesGroupVO;
		}

		public function getRolesGroupVOIndex(item:RolesGroupVO):int 
		{
			return getItemIndex(item);
		}

		public function setRolesGroupVOAt(item:RolesGroupVO,index:int):void 
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