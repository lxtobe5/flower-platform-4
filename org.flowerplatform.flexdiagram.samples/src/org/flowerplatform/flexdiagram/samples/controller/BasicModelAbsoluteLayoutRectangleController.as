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
package org.flowerplatform.flexdiagram.samples.controller {
	import flash.geom.Rectangle;
	
	import org.flowerplatform.flexdiagram.DiagramShellContext;
	import org.flowerplatform.flexdiagram.controller.AbsoluteLayoutRectangleController;
	import org.flowerplatform.flexdiagram.samples.model.BasicModel;
	
	/**
	 * @author Cristian Spiescu
	 */
	public class BasicModelAbsoluteLayoutRectangleController extends AbsoluteLayoutRectangleController {
		
		override public function getBounds(context:DiagramShellContext, model:Object):Rectangle {
			var basicModel:BasicModel = BasicModel(model);
			return new Rectangle(basicModel.x, basicModel.y, basicModel.width, basicModel.height);
		}
		
	}
}