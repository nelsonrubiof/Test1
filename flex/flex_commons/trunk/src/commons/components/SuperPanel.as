/********************************************
 title   : SuperPanel
 version : 1.5
 author  : Wietse Veenstra
 website : http://www.wietseveenstra.nl
 date    : 2007-03-30
********************************************/
package commons.components {
    import flash.events.MouseEvent;
    import flash.geom.Point;
    import flash.geom.Rectangle;
    
    import mx.containers.Panel;
    import mx.controls.Button;
    import mx.core.Application;
    import mx.core.FlexGlobals;
    import mx.core.UIComponent;
    import mx.effects.Resize;
    import mx.events.DragEvent;
    import mx.events.EffectEvent;
    import mx.managers.CursorManager;
    import mx.styles.StyleManager;
    
    public class SuperPanel extends Panel {
        [Bindable] private var _enableResize:Boolean = false;
        [Bindable] private var _showMinMax:Boolean = false;
        [Bindable] private var _showClose:Boolean = false;
        
        [Embed(source="/commons/images/resizeCursor.png")]
        private static var resizeCursor:Class;
        
        private var pTitleBar:UIComponent;
        private var oW:Number;
        private var oH:Number;
        private var oX:Number;
        private var oY:Number;
        private var normalMaxButton:Button    = new Button();
        private var closeButton:Button        = new Button();
        private var resizeHandler:Button      = new Button();
        private var upMotion:Resize           = new Resize();
        private var downMotion:Resize         = new Resize();
        private var oPoint:Point              = new Point();
        private var resizeCur:Number          = 0;
                
        public function SuperPanel() {}

        override protected function createChildren():void {
            super.createChildren();
            this.pTitleBar = super.titleBar;
            //this.setStyle("headerColors", [0xC3D1D9, 0xD2DCE2]);
            //this.setStyle("borderColor", 0xD2DCE2);
            this.doubleClickEnabled = true;
            
            if (_enableResize) {
                this.resizeHandler.width     = 12;
                this.resizeHandler.height    = 12;
                this.resizeHandler.styleName = "resizeHndlr";
                this.rawChildren.addChild(resizeHandler);
                this.initPos();
            }
            
            if (_showMinMax) {
                this.normalMaxButton.width         = 10;
                this.normalMaxButton.height        = 10;
                this.normalMaxButton.styleName     = "increaseBtn";
                this.pTitleBar.addChild(this.normalMaxButton);
            }
            if (_showClose) {
                this.closeButton.width             = 10;
                this.closeButton.height            = 10;
                this.closeButton.styleName         = "closePanel";
                this.pTitleBar.addChild(this.closeButton);
            }
            
            this.positionChildren();    
            this.addListeners();
        }
        
        public function initPos():void {
            this.oW = this.width;
            this.oH = this.height;
            this.oX = this.x;
            this.oY = this.y;
        }
    
        public function positionChildren():void {
            if (_showMinMax) {
                this.normalMaxButton.buttonMode    = true;
                this.normalMaxButton.useHandCursor = true;
                this.normalMaxButton.x = this.unscaledWidth - this.normalMaxButton.width - 24;
                this.normalMaxButton.y = 8;
            }
            if (_showClose) {
                this.closeButton.buttonMode       = true;
                this.closeButton.useHandCursor    = true;
                this.closeButton.x = this.unscaledWidth - this.closeButton.width - 8;
                this.closeButton.y = 8;
            }
            
            if (_enableResize) {
                this.resizeHandler.y = this.unscaledHeight - resizeHandler.height - 1;
                this.resizeHandler.x = this.unscaledWidth - resizeHandler.width - 1;
            }
        }
        
        public function addListeners():void {
            //this.addEventListener(MouseEvent.CLICK, panelClickHandler);
            //this.pTitleBar.addEventListener(MouseEvent.MOUSE_DOWN, titleBarDownHandler);
            //this.pTitleBar.addEventListener(MouseEvent.DOUBLE_CLICK, titleBarDoubleClickHandler);
            
            if (_showClose) {
                this.closeButton.addEventListener(MouseEvent.CLICK, closeClickHandler);
            }

            if (_showMinMax) {
                this.normalMaxButton.addEventListener(MouseEvent.CLICK, normalMaxClickHandler);
            }
            
            if (_enableResize) {
                this.resizeHandler.addEventListener(MouseEvent.MOUSE_OVER, resizeOverHandler);
                this.resizeHandler.addEventListener(MouseEvent.MOUSE_OUT, resizeOutHandler);
                this.resizeHandler.addEventListener(MouseEvent.MOUSE_DOWN, resizeDownHandler);
            }
        }
        
        public function panelClickHandler(event:MouseEvent):void {
            this.pTitleBar.removeEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
            this.parent.setChildIndex(this, this.parent.numChildren - 1);
            //this.panelFocusCheckHandler();
        }
        
        public function titleBarDownHandler(event:MouseEvent):void {
            this.pTitleBar.addEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
        }
            
        public function titleBarMoveHandler(event:MouseEvent):void {
            if (this.width < screen.width) {
                FlexGlobals.topLevelApplication.parent.addEventListener(MouseEvent.MOUSE_UP, titleBarDragDropHandler);
                this.pTitleBar.addEventListener(DragEvent.DRAG_DROP,titleBarDragDropHandler);
                this.parent.setChildIndex(this, this.parent.numChildren - 1);
                //this.panelFocusCheckHandler();
                this.alpha = 0.5;
                this.startDrag(false, new Rectangle(0, 0, screen.width - this.width, screen.height - this.height));
            }
        }
        
        public function titleBarDragDropHandler(event:MouseEvent):void {
            this.pTitleBar.removeEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
            this.alpha = 1.0;
            this.stopDrag();
        }
        
        public function panelFocusCheckHandler():void {
            for (var i:int = 0; i < this.parent.numChildren; i++) {
                var obj:Object = this.parent.getChildAt(i);
                try {
                    var child:UIComponent = UIComponent(this.parent.getChildAt(i));
                    if (this.parent.getChildIndex(child) < this.parent.numChildren - 1) {
                        //child.setStyle("headerColors", [0xC3D1D9, 0xD2DCE2]);
                        //child.setStyle("borderColor", 0xD2DCE2);
                    } else if (this.parent.getChildIndex(child) == this.parent.numChildren - 1) {
                        //child.setStyle("headerColors", [0xC3D1D9, 0x5A788A]);
                        //child.setStyle("borderColor", 0x5A788A);
                    }
                } catch (e:Error) {
                    //error not display
                }
            }
        }
        
        public function titleBarDoubleClickHandler(event:MouseEvent):void {
            this.pTitleBar.removeEventListener(MouseEvent.MOUSE_MOVE, titleBarMoveHandler);
            FlexGlobals.topLevelApplication.parent.removeEventListener(MouseEvent.MOUSE_UP, resizeUpHandler);
            
            this.upMotion.target = this;
            this.upMotion.duration = 300;
            this.upMotion.heightFrom = oH;
            this.upMotion.heightTo = 28;
            this.upMotion.end();
            
            this.downMotion.target = this;
            this.downMotion.duration = 300;
            this.downMotion.heightFrom = 28;
            this.downMotion.heightTo = oH;
            this.downMotion.end();
            
            if (this.width < screen.width) {
                if (this.height == oH) {
                    this.upMotion.play();
                    this.resizeHandler.visible = false;
                } else {
                    this.downMotion.play();
                    this.downMotion.addEventListener(EffectEvent.EFFECT_END, endEffectEventHandler);
                }
            }
        }
        
        public function endEffectEventHandler(event:EffectEvent):void {
            this.resizeHandler.visible = true;
        }


        public function normalMaxClickHandler(event:MouseEvent):void {
            if (this.normalMaxButton.styleName == "increaseBtn") {
                if (this.height > 28) {
                    this.initPos();
                    this.x = 0;
                    this.y = 0;
                    this.width = screen.width;
                    this.height = screen.height;
                    this.normalMaxButton.styleName = "decreaseBtn";
                    this.positionChildren();
                }
            } else {
                this.x = this.oX;
                this.y = this.oY;
                this.width = this.oW;
                this.height = this.oH;
                this.normalMaxButton.styleName = "increaseBtn";
                this.positionChildren();
            }
        }
        
        public function closeClickHandler(event:MouseEvent):void {
            this.removeEventListener(MouseEvent.CLICK, panelClickHandler);
            this.parent.removeChild(this);
        }
        
        public function resizeOverHandler(event:MouseEvent):void {
            this.resizeCur = CursorManager.setCursor(resizeCursor);
        }
        
        public function resizeOutHandler(event:MouseEvent):void {
            CursorManager.removeCursor(CursorManager.currentCursorID);
        }
        
        public function resizeDownHandler(event:MouseEvent):void {
            FlexGlobals.topLevelApplication.parent.addEventListener(MouseEvent.MOUSE_MOVE, resizeMoveHandler);
            FlexGlobals.topLevelApplication.parent.addEventListener(MouseEvent.MOUSE_UP, resizeUpHandler);
            this.resizeHandler.addEventListener(MouseEvent.MOUSE_OVER, resizeOverHandler);
            this.panelClickHandler(event);
            this.resizeCur = CursorManager.setCursor(resizeCursor);
            this.oPoint.x = mouseX;
            this.oPoint.y = mouseY;
            this.oPoint = this.localToGlobal(oPoint);        
        }
        
        public function resizeMoveHandler(event:MouseEvent):void {
            this.stopDragging();

            var xPlus:Number = FlexGlobals.topLevelApplication.parent.mouseX - this.oPoint.x;            
            var yPlus:Number = FlexGlobals.topLevelApplication.parent.mouseY - this.oPoint.y;
            
            if (this.oW + xPlus > 140) {
                this.width = this.oW + xPlus;
            }
            
            if (this.oH + yPlus > 80) {
                this.height = this.oH + yPlus;
            }
            this.positionChildren();
        }
        
        public function resizeUpHandler(event:MouseEvent):void {
            FlexGlobals.topLevelApplication.parent.removeEventListener(MouseEvent.MOUSE_MOVE, resizeMoveHandler);
            FlexGlobals.topLevelApplication.parent.removeEventListener(MouseEvent.MOUSE_UP, resizeUpHandler);
            CursorManager.removeCursor(CursorManager.currentCursorID);
            this.resizeHandler.addEventListener(MouseEvent.MOUSE_OVER, resizeOverHandler);
            this.initPos();
        }
        
        public function set showControls(value:Boolean):void {
            this._showClose = value;
            this._showMinMax = value;
        }
        
        public function get enableResize():Boolean {
            return this._enableResize;
        }
        
        public function set enableResize(value:Boolean):void {
            this._enableResize = value;
        }
        
        public function get showClose():Boolean {
            return this._showClose;
        }
        
        public function set showClose(value:Boolean):void {
            this._showClose = value;
        }

        public function get showMinMax():Boolean {
            return this._showMinMax;
        }
        
        public function set showMinMax(value:Boolean):void {
            this._showMinMax = value;
        }
    }
}