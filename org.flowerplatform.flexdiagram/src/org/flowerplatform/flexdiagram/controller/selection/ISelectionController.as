/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico, <http://www.crispico.com/>.
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
 * Contributors:
 *   Crispico - Initial API and implementation
 *
 * license-end
 */
package org.flowerplatform.flexdiagram.controller.selection {
	import mx.core.IVisualElement;
	
	import org.flowerplatform.flexdiagram.DiagramShellContext;

	/**
	 * @author Cristina Constantinescu
	 */ 
	public interface ISelectionController {
		function setSelectedState(context:DiagramShellContext, model:Object, renderer:IVisualElement, isSelected:Boolean, isMainSelection:Boolean):void;
		function associatedModelToSelectionRenderer(context:DiagramShellContext, model:Object, renderer:IVisualElement):void;
		function unassociatedModelFromSelectionRenderer(context:DiagramShellContext, model:Object, renderer:IVisualElement):void;
	}
	
}