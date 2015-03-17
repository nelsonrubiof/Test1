package com.scopix.extractionplancustomizing.model.arrays
{
    import com.scopix.extractionplancustomizing.model.vo.SensorVO;
    
    import mx.collections.ArrayCollection;
    import mx.collections.ICollectionView;
    import mx.collections.IList;

    public class ArrayOfSensorVO extends ArrayCollection
    {
        public function ArrayOfSensorVO(source:Array=null)
        {
            super(source);
        }
        
        public function addSensorVOAt(item:SensorVO,index:int):void 
		{
			addItemAt(item,index);
		}

		public function addSensorVO(item:SensorVO):void 
		{
			addItem(item);
		} 

		public function getSensorVOAt(index:int):SensorVO 
		{
			return getItemAt(index) as SensorVO;
		}

		public function getSensorVOIndex(item:SensorVO):int 
		{
			return getItemIndex(item);
		}

		public function setSensorVOAt(item:SensorVO,index:int):void 
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