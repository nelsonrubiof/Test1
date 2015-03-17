package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanRangeVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;

    public class ArrayOfExtractionPlanRangeVO extends ArrayCollection
    {
        public function ArrayOfExtractionPlanRangeVO(source:Array=null)
        {
            super(source);
        }
        
        public function addExtractionPlanRangeVOAt(item:ExtractionPlanRangeVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addExtractionPlanRangeVO(item:ExtractionPlanRangeVO):void 
		{
			addItem(item);
		} 

		public function getExtractionPlanRangeVOAt(index:int):ExtractionPlanRangeVO 
		{
			return getItemAt(index) as ExtractionPlanRangeVO;
		}

		public function getExtractionPlanRangeVOIndex(item:ExtractionPlanRangeVO):int 
		{
			return getItemIndex(item);
		}

		public function setExtractionPlanRangeVOAt(item:ExtractionPlanRangeVO,index:int):void 
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