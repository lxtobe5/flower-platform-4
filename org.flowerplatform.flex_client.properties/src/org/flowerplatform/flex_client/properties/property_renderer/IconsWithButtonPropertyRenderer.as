package org.flowerplatform.flex_client.properties.property_renderer {
	
	import flash.events.MouseEvent;
	
	import mx.binding.utils.BindingUtils;
	import mx.controls.Spacer;
	import mx.events.FlexEvent;
	
	import org.flowerplatform.flexutil.FlexUtilGlobals;
	import org.flowerplatform.flexutil.FlowerArrayList;
	import org.flowerplatform.flexutil.dialog.IDialogResultHandler;
	import org.flowerplatform.flexutil.renderer.IIconsComponentExtensionProvider;
	import org.flowerplatform.flexutil.renderer.IconsComponentExtension;
	
	import spark.components.Button;
	import spark.layouts.HorizontalLayout;
	
	/**
	 * @author Cristina Constantinescu
	 */ 
	public class IconsWithButtonPropertyRenderer extends BasicPropertyRenderer implements IIconsComponentExtensionProvider, IDialogResultHandler {
		
		public static const ADD:String = "add";
		public static const REMOVE_FIRST:String = "remove_first";
		public static const REMOVE_LAST:String = "remove_last";
		public static const REMOVE_ALL:String = "remove_all";
		
		public static const ICONS_SEPARATOR:String = "|";
		
		protected var iconsArea:IconsComponentExtension;
		
		/**
		 * Signature: function clickHandler(itemRendererHandler:IDialogResultHandler, propertyName:String, propertyValue:Object):void
		 */ 
		public var clickHandler:Function;
		
		/**
		 * Signature: function getNewIconsPropertyHandler(dialogResult:Object):String
		 */ 
		public var getNewIconsPropertyHandler:Function;
		
		public function IconsWithButtonPropertyRenderer() {
			super();
			iconsArea = new IconsComponentExtension(this);
			
			var hLayout:HorizontalLayout = new HorizontalLayout();
			hLayout.gap = 2;
			hLayout.paddingBottom = 2;
			hLayout.paddingTop = 2;
			hLayout.paddingLeft = 2;
			hLayout.paddingRight = 2;
			hLayout.verticalAlign = "middle";
			
			this.layout = hLayout;
			
			addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);			
		}
		
		public function get icons():FlowerArrayList {
			return iconsArea.icons;
		}
		
		public function set icons(value:FlowerArrayList):void {
			iconsArea.icons = value;
		}
		
		protected function creationCompleteHandler(event:FlexEvent):void {
			BindingUtils.bindSetter(iconsChanged, data, "value");
		}

		public function iconsChanged(value:Object):void {
			if (value != null) {					
				icons = new FlowerArrayList(String(value).split(ICONS_SEPARATOR));
			}
		}
		
		override public function set data(value:Object):void {
			super.data = value;	
			
			if (data != null) {			
				if (propertyDescriptor.value != null) {					
					icons = new FlowerArrayList(String(propertyDescriptor.value).split(ICONS_SEPARATOR));
				}
			}			
		}
		
		override protected function createChildren():void {
			super.createChildren();
			
			var spacer:Spacer = new Spacer();	
			spacer.percentWidth = 100;
			addElement(spacer);
			
			var button:Button = new Button();			
			button.percentHeight = 100;
			button.label = "...";
			button.width = 32;
			button.addEventListener(MouseEvent.CLICK, clickHandlerInternal);
			
			addElement(button);
		}
		
		private function clickHandlerInternal(event:MouseEvent):void {
			clickHandler(this, data.name, data.value);
		}
		
		public function handleDialogResult(result:Object):void {
			if (result == null) {
				return;
			}
			var type:String = result.type;
			var oldValue:String = propertyDescriptor.value as String;
			switch (type) {
				case ADD:
					propertyDescriptor.value = (oldValue == null ? "" : (oldValue + ICONS_SEPARATOR)) + result.iconUrl;
					break;
				case REMOVE_FIRST:
					var firstIndexOf:int = oldValue.indexOf(ICONS_SEPARATOR);
					propertyDescriptor.value = firstIndexOf != -1 ? oldValue.substr(firstIndexOf + 1, oldValue.length) : null;
					break;
				case REMOVE_LAST:
					var lastIndexOf:int = oldValue.lastIndexOf(ICONS_SEPARATOR);
					propertyDescriptor.value = lastIndexOf != -1 ? oldValue.substr(0, lastIndexOf) : null;
					break;
				case REMOVE_ALL:
					propertyDescriptor.value = null;
					break;
			}
			sendChangedValuesToServer(null);
		}
				
		public function newIconIndex():int {			
			return numElements - 2;
		}
		
		override public function validateDisplayList():void {
			super.validateDisplayList();			
			iconsArea.validateDisplayList();
		}
		
		override public function validateProperties():void {
			super.validateProperties();
			iconsArea.validateProperties();
		}
		
		override public function validateSize(recursive:Boolean=false):void	{
			iconsArea.validateSize();
			super.validateSize(recursive);
		}
		
	}
}