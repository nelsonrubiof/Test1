package commons
{
	import commons.components.skins.MovingBarSkin;
	import commons.components.skins.ProgressMaskSkin;
	import commons.components.skins.StaticBarSkin;
	import commons.components.skins.TrackSkin;
	
	/**
	 * Esta clase es utilizada solo para referenciar cierto componentes que no son usados
	 * directamente desde esta API. El problema que sucede es que al no ser referenciados
	 * directamente, el compilador ANT en Hudson no los integra a la API generada, por lo
	 * cual nace la necesidad de esta clase.
	 **/
	public class DummyClass
	{
		//variables para la barra de progreso
		private var movingBarSkin:MovingBarSkin;
		private var progressMaskSkin:ProgressMaskSkin;
		private var staticBarSkin:StaticBarSkin;
		private var trackSkin:TrackSkin;
	}
}