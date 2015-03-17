package com.scopix.extractionplancustomizing.view.lastSent.renderer
{
    import com.scopix.extractionplancustomizing.model.ExtractionPlanCustomizingLastSentModel;
    import com.scopix.extractionplancustomizing.model.vo.EvidenceProviderVO;
    import com.scopix.extractionplancustomizing.model.vo.MetricTemplateVO;
    
    import flash.display.DisplayObject;
    
    import mx.controls.Image;
    import mx.controls.treeClasses.TreeItemRenderer;
    import mx.controls.treeClasses.TreeListData;

    public class CustomTreeItemRenderer extends TreeItemRenderer
    {
        //private var customItem:Image;
        
        [Embed(source="/assets/img/icon_rc_WV-NP304.jpg")] 
        public var cameraIcon:Class;
        public var im:Image;
        
        public function CustomTreeItemRenderer()
        {
            super();
        }

        override protected function createChildren():void {
            super.createChildren();
            im = new Image();
            im.source = cameraIcon;
        }

        override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
            var treeListData:TreeListData = TreeListData(listData);
            if (treeListData == null) {
                return;
            }

            super.updateDisplayList(unscaledWidth,unscaledHeight);
            //si tiene hijos (hasChildren=true) entonces es un MetricVO, de lo contrario es un ProviderVO
            if (treeListData.item is MetricTemplateVO) {
                this.setStyle("fontWeight","bold");
                var desc:String = ((treeListData.item as MetricTemplateVO).description == null) ? "": (treeListData.item as MetricTemplateVO).description;
                var vn:String = ((treeListData.item as MetricTemplateVO).variableName == null) ? "": (treeListData.item as MetricTemplateVO).variableName;
                this.label.text = desc + " (" + vn + ")";
/*                 if (icon) {
                    removeChild(DisplayObject(icon));
                    icon = null;
                } */
                //icon.visible = false;
            } else if (treeListData.item is EvidenceProviderVO) {
                this.setStyle("fontWeight","normal");
                this.label.text = (treeListData.item as EvidenceProviderVO).description;
                
/*                 if (icon) {
                    removeChild(DisplayObject(icon));
                    icon = null;
                }
                
                this.addChild(im);
                this.icon = im; */
                //icon.visible = true;
            }
        }
    }
}