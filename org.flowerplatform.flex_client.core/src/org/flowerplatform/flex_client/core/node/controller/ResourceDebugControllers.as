package org.flowerplatform.flex_client.core.node.controller {
	import mx.collections.ArrayCollection;
	
	import org.flowerplatform.flex_client.core.CoreConstants;
	import org.flowerplatform.flex_client.core.CorePlugin;
	import org.flowerplatform.flex_client.core.editor.remote.Node;
	import org.flowerplatform.flex_client.core.node.NodeRegistry;
	import org.flowerplatform.flexdiagram.FlexDiagramConstants;

	/**
	 * @author Mariana Gheorghe
	 */
	public class ResourceDebugControllers {

		public const RESOURCES:String = "debugClientResources";
		public const RESOURCE_SET:String = "debugClientResourceSet";
		public const RESOURCE_URI:String = "debugClientResourceUri";
		public const NODE_REGISTRY:String = "debugNodeRegistry";
		
		public function registerControllers():void {
			var nodeController:ResourceNodeController = new ResourceNodeController(this);
			
			addNodeController(RESOURCES, nodeController);
			addNodeController(RESOURCE_SET, nodeController);
			addNodeController(RESOURCE_URI, nodeController);
		}
		
		private function addNodeController(type:String, controller:ResourceNodeController):void {
			CorePlugin.getInstance().nodeTypeDescriptorRegistry.getOrCreateTypeDescriptor(type)
				.addSingleController(FlexDiagramConstants.MINDMAP_MODEL_CONTROLLER, controller);
		}
		
		public function getResourceSets():ArrayCollection {
			var resourceSets:ArrayCollection = new ArrayCollection();
			for each (var resourceSet:String in CorePlugin.getInstance().resourceNodesManager.nodeRegistryManager.getResourceSets()) {
				var node:Node = new Node(RESOURCE_SET + ":" + resourceSet);
				node.type = RESOURCE_SET;
				node.properties = new Object();
				node.properties[CoreConstants.NAME] = "ResourceSet: " + resourceSet;
				node.properties[CoreConstants.HAS_CHILDREN] = true;
				resourceSets.addItem(node);
			}
			return resourceSets;
		}
		
		public function getChildrenForResourceSet(resourceSet:String):ArrayCollection {
			var children:ArrayCollection = new ArrayCollection();
			var index:int = 0;
			for each (var resourceUri:String in 
				CorePlugin.getInstance().resourceNodesManager.nodeRegistryManager.getResourceUrisForResourceSet(resourceSet)) {
				var node:Node = new Node(RESOURCE_URI + ":" + resourceUri + "#" + index++);
				node.type = RESOURCE_URI;
				node.properties = new Object();
				node.properties[CoreConstants.NAME] = "ResourceURI: " + resourceUri;
				node.properties[CoreConstants.HAS_CHILDREN] = false;
				children.addItem(node);
			}
			return children;
		}
		
	}
}
import mx.collections.ArrayCollection;
import mx.collections.IList;
import mx.core.mx_internal;

import org.flowerplatform.flex_client.core.editor.remote.Node;
import org.flowerplatform.flex_client.core.node.NodeRegistry;
import org.flowerplatform.flex_client.core.node.controller.ResourceDebugControllers;
import org.flowerplatform.flexdiagram.DiagramShellContext;
import org.flowerplatform.flexdiagram.mindmap.MindMapDiagramShell;
import org.flowerplatform.flexdiagram.mindmap.controller.MindMapModelController;

use namespace mx_internal;

class ResourceNodeController extends MindMapModelController {
	
	private var debug:ResourceDebugControllers;
	
	public function ResourceNodeController(debug:ResourceDebugControllers) {
		// override the default NodeController
		orderIndex = -100;
		
		this.debug = debug;
	}
	
	override public function getChildren(context:DiagramShellContext, model:Object):IList {
		return Node(model).children;
	}
	
	override public function getExpanded(context:DiagramShellContext, model:Object):Boolean {
		return Node(model).children != null && Node(model).children.length > 0;
	}
	
	override public function setExpanded(context:DiagramShellContext, model:Object, value:Boolean):void {
		var nodeRegistry:NodeRegistry = Object(context.diagramShell).nodeRegistry;
		var node:Node = Node(model);
		if (value) {
			var children:ArrayCollection;
			if (node.type == debug.RESOURCES) {
				children = debug.getResourceSets();
			} else if (node.type == debug.RESOURCE_SET) {
				children = debug.getChildrenForResourceSet(node.schemeSpecificPart);
			}
			nodeRegistry.expandCallbackHandler(node, children);
		} else {
			nodeRegistry.collapse(node);
		}
	}
	
	override public function getSide(context:DiagramShellContext, model:Object):int {
		// default side
		return MindMapDiagramShell.POSITION_RIGHT;
	}
	
	override public function setSide(context:DiagramShellContext, model:Object, value:int):void {
	}
	
	override public function isRoot(context:DiagramShellContext, model:Object):Boolean {
		return Node(model).parent == null;
	}
	
}