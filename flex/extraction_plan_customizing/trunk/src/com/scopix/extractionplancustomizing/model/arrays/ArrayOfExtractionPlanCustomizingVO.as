package com.scopix.extractionplancustomizing.model.arrays {

	import com.scopix.extractionplancustomizing.model.vo.ExtractionPlanCustomizingVO;

	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.collections.IList;

	public class ArrayOfExtractionPlanCustomizingVO extends ArrayCollection {
		public function ArrayOfExtractionPlanCustomizingVO(source:Array = null) {
			super(source);
		}

		public function addExtractionPlanCustomizingVOAt(item:ExtractionPlanCustomizingVO, index:int):void {
			addItemAt(item, index);
		}

		public function addExtractionPlanCustomizingVO(item:ExtractionPlanCustomizingVO):void {
			addItem(item);
		}

		public function getExtractionPlanCustomizingVOAt(index:int):ExtractionPlanCustomizingVO {
			return getItemAt(index) as ExtractionPlanCustomizingVO;
		}

		public function getExtractionPlanCustomizingVOIndex(item:ExtractionPlanCustomizingVO):int {
			return getItemIndex(item);
		}

		public function setExtractionPlanCustomizingVOAt(item:ExtractionPlanCustomizingVO, index:int):void {
			setItemAt(item, index);
		}

		public function asIList():IList {
			return this as IList;
		}

		public function asICollectionView():ICollectionView {
			return this as ICollectionView;
		}
	}
}