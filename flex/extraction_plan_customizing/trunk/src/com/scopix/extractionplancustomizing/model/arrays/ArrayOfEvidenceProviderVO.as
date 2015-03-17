package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.EvidenceProviderVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;

    public class ArrayOfEvidenceProviderVO extends ArrayCollection
    {
        public function ArrayOfEvidenceProviderVO(source:Array=null)
        {
            super(source);
        }
		public function addEvidenceProviderVOAt(item:EvidenceProviderVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addEvidenceProviderVO(item:EvidenceProviderVO):void 
		{
			addItem(item);
		} 

		public function getEvidenceProviderVOAt(index:int):EvidenceProviderVO 
		{
			return getItemAt(index) as EvidenceProviderVO;
		}

		public function getEvidenceProviderVOIndex(item:EvidenceProviderVO):int 
		{
			return getItemIndex(item);
		}

		public function setEvidenceProviderVOAt(item:EvidenceProviderVO,index:int):void 
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