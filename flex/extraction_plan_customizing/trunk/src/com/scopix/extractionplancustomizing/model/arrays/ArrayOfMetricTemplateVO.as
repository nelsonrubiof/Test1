package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;

    public class ArrayOfMetricTemplateVO extends ArrayCollection
    {
        public function ArrayOfMetricTemplateVO(source:Array=null)
        {
            super(source);
        }
        
        public function addMetricTemplateVOAt(item:MetricTemplateVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addMetricTemplateVO(item:MetricTemplateVO):void 
		{
			addItem(item);
		} 

		public function getMetricTemplateVOAt(index:int):MetricTemplateVO 
		{
			return getItemAt(index) as MetricTemplateVO;
		}

		public function getMetricTemplateVOIndex(item:MetricTemplateVO):int 
		{
			return getItemIndex(item);
		}

		public function setMetricTemplateVOAt(item:MetricTemplateVO,index:int):void 
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