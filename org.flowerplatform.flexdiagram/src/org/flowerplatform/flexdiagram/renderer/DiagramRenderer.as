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
package org.flowerplatform.flexdiagram.renderer {
	import flash.events.FocusEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import mx.core.IVisualElement;
	import mx.managers.IFocusManagerComponent;
	
	import org.flowerplatform.flexdiagram.ControllerUtils;
	import org.flowerplatform.flexdiagram.DiagramShellContext;
	import org.flowerplatform.flexdiagram.IDiagramShellContextAware;
	import org.flowerplatform.flexdiagram.controller.visual_children.VisualChildrenController;
	import org.flowerplatform.flexdiagram.util.RectangularGrid;
	import org.flowerplatform.flexdiagram.util.infinitegroup.InfiniteDataRenderer;
	
	import spark.core.NavigationUnit;
	
	/**
	 * @author Cristian Spiescu
	 */
	public class DiagramRenderer extends InfiniteDataRenderer implements IDiagramShellContextAware, IVisualChildrenRefreshable, IAbsoluteLayoutRenderer, IFocusManagerComponent {

		protected var _context:DiagramShellContext;
		protected var visualChildrenController:VisualChildrenController;
		private var _shouldRefreshVisualChildren:Boolean;
		private var _noNeedToRefreshRect:Rectangle;
		
		public var viewPortRectOffsetTowardOutside:int = 0;
		
		/**
		 * @author Mircea Negreanu
		 * 
		 * The background grid
		 */
		private var _grid:IVisualElement;
		
		/**
		 * If we want grid or not
		 * 
		 * @author Mircea Negreanu
		 */
		public var useGrid:Boolean = true;
		
		/**
		 * @author Cristina Constantinescu
		 */
		public var verticalScrollBarStepSize:Number = 10;
		
		public function get diagramShellContext():DiagramShellContext {			
			return _context;
		}
		
		public function set diagramShellContext(value:DiagramShellContext):void {
			this._context = value;
		}	
		
		public function get shouldRefreshVisualChildren():Boolean {
			return _shouldRefreshVisualChildren;
		}
		
		public function set shouldRefreshVisualChildren(value:Boolean):void {
			_shouldRefreshVisualChildren = value;
		}
		
		public function get noNeedToRefreshRect():Rectangle {
			return _noNeedToRefreshRect;
		}
		
		public function set noNeedToRefreshRect(value:Rectangle):void {
			_noNeedToRefreshRect = value; 
		}

		/**
		 * Get the grid used by this component.
		 * 
		 * @author Mircea Negreanu
		 */
		public function get grid():IVisualElement {
			if (useGrid && _grid == null) {
				_grid = new RectangularGrid();
			}
			return _grid;
		}
		
		override public function set data(value:Object):void {
			super.data = value;
			if (data == null) {
				visualChildrenController = null;
			} else {
				visualChildrenController = ControllerUtils.getVisualChildrenController(diagramShellContext, data);
			}
		}
		
		public function getViewportRect():Rectangle {
			return new Rectangle(horizontalScrollPosition - viewPortRectOffsetTowardOutside, verticalScrollPosition - viewPortRectOffsetTowardOutside, width + 2 * viewPortRectOffsetTowardOutside, height + 2 * viewPortRectOffsetTowardOutside);
		}
		
		public function setContentRect(rect:Rectangle):void {		
			contentRect = rect;
		}
		
		/**
		 * @author Cristian Spiescu
		 * @author Mircea Negreanu
		 * @author Cristina Constantinescu
		 */
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			if (visualChildrenController != null) {
				visualChildrenController.refreshVisualChildren(diagramShellContext, data);
			}
			
			// resize/move the grid (depending on the viewport dimensions)
			sizeGrid();
			
			super.updateDisplayList(unscaledWidth, unscaledHeight);

			// draw a border around visible area
			graphics.clear();
			graphics.beginFill(0xCCCCCC, 0);			
			graphics.drawRect(horizontalScrollPosition, verticalScrollPosition, width - 1, height - 1);				
		}
	
		/**
		 * @author Cristina Constantinescu
		 */ 
		override protected function focusInHandler(event:FocusEvent):void {
			super.focusInHandler(event);		
			if (diagramShellContext.diagramShell != null && stage != null) { 
				// stage == null -> save dialog closes, the focusManager tries to put focus on diagram,
				// but it will be removed shortly, so don't take this in consideration
				diagramShellContext.diagramShell.activateTools();
			}
		}
		
		override protected function focusOutHandler(event:FocusEvent):void {
			super.focusOutHandler(event);
			
			if (stage == null) {			
				return;
			}
			var point:Point = globalToContent(new Point(stage.mouseX, stage.mouseY));			
			if (!getViewportRect().containsPoint(point)) { // if outside diagram area
				diagramShellContext.diagramShell.deactivateTools();	
			}							
		}		
		
		/**
		 * In case we want grid, add it to the container
		 * 
		 * @author Mircea Negreanu
		 */
		override protected function createChildren():void {
			super.createChildren();
			
			if (useGrid) {
				grid.x = grid.y = 0;
				// we want dash
				RectangularGrid(grid).dashSize = 1;
			
				// add it
				addElement(grid);
			}			
		}
			
		/**
		 * Size the grid based on scroll position and width/height.
		 * <p>
		 * 	Make the grid invisible when the scale is lower the 0.75
		 * </p>
		 * 
		 * @author Mircea Negreanu
		 */
		protected function sizeGrid():void {
			if (useGrid) {
				if (this.scaleX < 0.75) {
					grid.visible = false;
				} else {
					grid.visible = true;
				}
				
				// resize/move the grid, only if its dimensions or position were changed
				if (grid.visible &&
					(grid.x != horizontalScrollPosition
						|| grid.y != verticalScrollPosition
						|| grid.width != width
						|| grid.height != height)) {
					grid.x = horizontalScrollPosition;
					grid.y = verticalScrollPosition;
					grid.width = width;
					grid.height = height;
				}
			}
		}
		
		/**
		 * @author Cristina Constantinescu
		 */ 
		override public function getVerticalScrollPositionDelta(navigationUnit:uint):Number {
			var n:Number = super.getVerticalScrollPositionDelta(navigationUnit);
			if (navigationUnit == NavigationUnit.DOWN || navigationUnit == NavigationUnit.UP) {
				return verticalScrollBarStepSize * n;
			}
			return n;
		}
		
		
	}
}
