package com.scopix.qualitycontrol.model.arrays
{
    import com.scopix.qualitycontrol.model.vo.SituationTemplateVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;
    
    public class ArrayOfSituationTemplateVO extends ArrayCollection
    {
        public function ArrayOfSituationTemplateVO(source:Array = null)
        {
            super(source);
        }
        
		public function addSituationTemplateVOAt(item:SituationTemplateVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addSituationTemplateVO(item:SituationTemplateVO):void 
		{
			addItem(item);
		} 

		public function getSituationTemplateVOAt(index:int):SituationTemplateVO 
		{
			return getItemAt(index) as SituationTemplateVO;
		}

		public function getSituationTemplateVOIndex(item:SituationTemplateVO):int 
		{
			return getItemIndex(item);
		}

		public function setSituationTemplateVOAt(item:SituationTemplateVO,index:int):void 
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