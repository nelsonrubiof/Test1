package com.scopix.qualitycontrol.view.render {

    import mx.charts.styles.HaloDefaults;
    import mx.controls.advancedDataGridClasses.AdvancedDataGridItemRenderer;
    import mx.styles.CSSStyleDeclaration;
    import mx.styles.StyleManager;

    [Style(name="rowColor", type="uint", format="Color", inherit="yes")]
    public class ADGItemRendererEx extends AdvancedDataGridItemRenderer {

        /**
         *  @private
         */
        private static var stylesInited:Boolean = initStyles();

        /**
         *  @private
         */
        private static function initStyles():Boolean {
			/*
            HaloDefaults.init(styleManager);

            var ADGItemRendererExStyle:CSSStyleDeclaration =
                HaloDefaults.createSelector("ADGItemRendererEx", styleManager);

			ADGItemRendererExStyle.defaultFactory = function():void {
                this.rowColor = 0xFFFFFF;
            }
			*/
            return true;
        }

        public function ADGItemRendererEx() {
            super();
            background = true;
        }

        override public function validateNow():void {
            backgroundColor = getStyle("rowColor");
            super.validateNow();
        }
    }
}