/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico Software, <http://www.crispico.com/>.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details, at <http://www.gnu.org/licenses/>.
 * 
 * license-end
 */
package org.flowerplatform.flexutil.renderer {
	import mx.collections.ArrayList;
	import mx.collections.IList;
	import mx.core.IVisualElementContainer;
	import mx.core.UIComponent;
	
	import spark.primitives.BitmapImage;
	
	import org.flowerplatform.flexutil.FlexUtilGlobals;
	import org.flowerplatform.flexutil.FlowerArrayList;
	
	/**
	 * An extension for components that adds multiple icons as children.
	 * 
	 * <p>
	 * The component must implement <code>IIconsComponentExtensionProvider</code>
	 * to manage this "extension".
	 * 
	 * <p>
	 * It is used in different components: node renderer (AbstractMindMapModelRenderer), property renderer (IconsWithButtonPropertyRenderer).
	 * 
	 * @author Cristina Constantinescu
	 */ 
	public class IconsComponentExtension {
		
		/**
		 * Graphical list of icons (list of <code>BitmapImage</code>).
		 */ 
		protected var iconDisplays:IList;
		
		/**
		 * Model list of icons (list of urls as <code>String</code>s).
		 */ 
		protected var _icons:FlowerArrayList = new FlowerArrayList();		
		
		/**
		 * Represents the parent component for icons.
		 */ 
		protected var component:IIconsComponentExtensionProvider;
		
		public function IconsComponentExtension(component:IIconsComponentExtensionProvider) {
			this.component = component;				
		}
				
		public function get icons():FlowerArrayList {
			return _icons;
		}
		
		public function set icons(value:FlowerArrayList):void {
			if (value == _icons)
				return;
			if (value == null) {
				var k:int = 0;
				if (iconDisplays != null) {
					while (k < iconDisplays.length)  {
						removeIconDisplay(BitmapImage(iconDisplays.getItemAt(k)));
					}
				}
				_icons.removeAll();
			} else {
				var i:int;
				var j:int = 0;
				if (_icons.length > 0 && value.length > 0) {
					while (j < value.length && j < _icons.length) {
						BitmapImage(iconDisplays.getItemAt(j)).source = FlexUtilGlobals.getInstance()
							.adjustImageBeforeDisplaying(value.getItemAt(j));
						j++;
					}
				}
				if (j < _icons.length) {
					i = j;
					while (i < iconDisplays.length)  {
						removeIconDisplay(BitmapImage(iconDisplays.getItemAt(i)));
					}
				}
				if (j < value.length) {
					for (i = j; i < value.length; i++) {
						addIconDisplay(value.getItemAt(i));
					}
				}
				_icons = value;
			}			
		}
					
		protected function addIconDisplay(icon:Object):void {
			var iconDisplay:BitmapImage = new BitmapImage();
			iconDisplay.contentLoader = FlexUtilGlobals.getInstance().imageContentCache;
			iconDisplay.source = FlexUtilGlobals.getInstance().adjustImageBeforeDisplaying(icon);
			iconDisplay.verticalAlign = "middle";
			iconDisplay.depth = UIComponent(component).depth;
			
			IVisualElementContainer(component.getMainComponent()).addElementAt(iconDisplay, component.newIconIndex());
			
			if (iconDisplays == null) {
				iconDisplays = new ArrayList();
			}
			iconDisplays.addItem(iconDisplay);
		}
		
		protected function removeIconDisplay(iconDisplay:BitmapImage):void {
			IVisualElementContainer(component.getMainComponent()).removeElement(iconDisplay);
			iconDisplays.removeItemAt(iconDisplays.getItemIndex(iconDisplay));
			
			UIComponent(component).invalidateSize();
		}			
		
		public function validateDisplayList():void {			
			if (iconDisplays && iconDisplays.length > 0) {
				for (var i:int=0; i < iconDisplays.length; i++) {
					var iconDisplay:BitmapImage = BitmapImage(iconDisplays.getItemAt(i));
					iconDisplay.validateDisplayList();
				}
			}
		}
		
		public function validateProperties():void {			
			if (iconDisplays && iconDisplays.length > 0) {
				for (var i:int=0; i < iconDisplays.length; i++) {
					var iconDisplay:BitmapImage = BitmapImage(iconDisplays.getItemAt(i));
					iconDisplay.validateProperties();
				}
			}
		}
		
		public function validateSize():void {
			if (iconDisplays && iconDisplays.length > 0) {
				for (var i:int=0; i < iconDisplays.length; i++) {
					var iconDisplay:BitmapImage = BitmapImage(iconDisplays.getItemAt(i));
					iconDisplay.validateSize();
				}
			}		
		}
	}
}