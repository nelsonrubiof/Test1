package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeDetailVO;
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;

    public class ArrayOfExtractionPlanRangeDetailVO extends ArrayCollection
    {
        public function ArrayOfExtractionPlanRangeDetailVO(source:Array=null)
        {
            super(source);
        }
        
        public function addExtractionPlanRangeDetailVOAt(item:ExtractionPlanRangeDetailVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addExtractionPlanRangeDetailVO(item:ExtractionPlanRangeDetailVO):void 
		{
			addItem(item);
		} 

		public function getExtractionPlanRangeDetailVOAt(index:int):ExtractionPlanRangeDetailVO 
		{
			return getItemAt(index) as ExtractionPlanRangeDetailVO;
		}

		public function getExtractionPlanRangeDetailVOIndex(item:ExtractionPlanRangeDetailVO):int 
		{
			return getItemIndex(item);
		}

		public function setExtractionPlanRangeDetailVOAt(item:ExtractionPlanRangeDetailVO,index:int):void 
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