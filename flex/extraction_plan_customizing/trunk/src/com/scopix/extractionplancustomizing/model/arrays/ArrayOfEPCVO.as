package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;
    
    public class ArrayOfEPCVO extends ArrayCollection
    {
        public function ArrayOfEPCVO(source:Array = null)
        {
            super(source);
        }
        
		public function addEPCVOAt(item:ExtractionPlanCustomizingVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addEPCVO(item:ExtractionPlanCustomizingVO):void 
		{
			addItem(item);
		} 

		public function getEPCVOAt(index:int):ExtractionPlanCustomizingVO 
		{
			return getItemAt(index) as ExtractionPlanCustomizingVO;
		}

		public function getEPCVOIndex(item:ExtractionPlanCustomizingVO):int 
		{
			return getItemIndex(item);
		}

		public function setEPCVOAt(item:ExtractionPlanCustomizingVO,index:int):void 
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