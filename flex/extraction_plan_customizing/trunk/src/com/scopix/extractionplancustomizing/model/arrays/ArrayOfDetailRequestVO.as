package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.DetailRequestVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;

    public class ArrayOfDetailRequestVO extends ArrayCollection
    {
        public function ArrayOfDetailRequestVO(source:Array=null)
        {
            super(source);
        }
        
        public function addDetailRequestVOAt(item:DetailRequestVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addDetailRequestVO(item:DetailRequestVO):void 
		{
			addItem(item);
		} 

		public function getDetailRequestVOAt(index:int):DetailRequestVO 
		{
			return getItemAt(index) as DetailRequestVO;
		}

		public function getDetailRequestVOIndex(item:DetailRequestVO):int 
		{
			return getItemIndex(item);
		}

		public function setDetailRequestVOAt(item:DetailRequestVO,index:int):void 
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