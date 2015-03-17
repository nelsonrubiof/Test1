package com.scopix.global
{
    import mx.effects.Glow;
    
    public class ApplicationEffects
    {
        private static var _instance:ApplicationEffects;
        private static var _glowGreenEffect:Glow;
        
        public function ApplicationEffects() {
            if (_instance != null) {
                throw new Error("This class can only be accessed through getInstance method");
            }
        }

        public static function getInstance():ApplicationEffects {
            if (_instance == null) {
                _instance = new ApplicationEffects();
            }
            
            return _instance;
        }
        
        public function getGlowGreenEffect():Glow {
            _glowGreenEffect = new Glow();
            _glowGreenEffect.duration = 1300;
            _glowGreenEffect.alphaFrom = 1.0;
            _glowGreenEffect.alphaTo = 0.0;
            _glowGreenEffect.blurXFrom = 0.0;
            _glowGreenEffect.blurXTo = 50.0;
            _glowGreenEffect.blurYFrom = 0.0;
            _glowGreenEffect.blurYTo = 50.0;
            _glowGreenEffect.color = 0x22A050;

            return _glowGreenEffect;
        }
    }
}