package org.flowerplatform.flex_client.core.editor.action {
	
	import org.flowerplatform.flex_client.core.CoreConstants;
	import org.flowerplatform.flex_client.core.CorePlugin;
	import org.flowerplatform.flex_client.core.editor.remote.Node;
	import org.flowerplatform.flex_client.core.editor.ui.UploadView;
	import org.flowerplatform.flex_client.resources.Resources;
	import org.flowerplatform.flexutil.FlexUtilGlobals;
	import org.flowerplatform.flexutil.action.MultipleSelectionActionBase;
	
	/**
	 * @author Cristina Constantinescu
	 */ 
	public class UploadAction extends MultipleSelectionActionBase {
		
		public function UploadAction() {
			super();
			label = Resources.getMessage("action.upload");
			icon = Resources.uploadIcon;
		}
		
		override public function get visible():Boolean {
			if (selection.length == 0 || selection.length > 1) {
				return false;
			}
			return super.visible;
		}	
		
		override protected function isVisibleForSelectedElement(element:Object):Boolean {
			return element is Node && Node(element).type == CoreConstants.FILE_NODE_TYPE && Node(element).properties[CoreConstants.FILE_IS_DIRECTORY];
		}
		
		override public function run():void {	
			var node:Node = Node(selection.getItemAt(0));	
			
			var view:UploadView = new UploadView();
			view.uploadLocationAsFullNodeId = node.fullNodeId;
			FlexUtilGlobals.getInstance().popupHandlerFactory.createPopupHandler()				
				.setViewContent(view)
				.setWidth(500)
				.setHeight(180)
				.show();	
		}		
	}
}