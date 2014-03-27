package org.flowerplatform.flex_client.core.service {
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.messaging.ChannelSet;
	import mx.rpc.AbstractOperation;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	import org.flowerplatform.flex_client.core.CoreConstants;
	import org.flowerplatform.flex_client.core.CorePlugin;
	import org.flowerplatform.flex_client.core.editor.action.ForceUpdateAction;
	import org.flowerplatform.flex_client.core.editor.remote.Node;
	import org.flowerplatform.flex_client.core.editor.update.NodeUpdateProcessor;
	import org.flowerplatform.flexdiagram.mindmap.MindMapRootModelWrapper;
	import org.flowerplatform.flexutil.FlexUtilGlobals;
	import org.flowerplatform.flexutil.service.ServiceLocator;
	import org.flowerplatform.flexutil.service.ServiceResponder;
	import org.flowerplatform.flexutil.view_content_host.IViewContent;

	/**
	 * Custom behavior to get updates registered after each message invocation.
	 * 
	 * <p>
	 * Before sending message to server, adds additional headers like timestamp of last update, full resourceNode id.
	 * These headers will be used on server to get a list of latest updates.
	 * <p>
	 * The server result contains:
	 * <ul>
	 * 	<li> <code>MESSAGE_RESULT</code> -> the message invocation result; it will be handled by <code>response.resultHandler</code>
	 * 	<li> <code>UPDATES</code> (optional) -> map of full resourceNode id to a list of updates that will be applied to the corresponding processor
	 * </ul>
	 * 
	 * @author Cristina Constantinescu
	 * @author Mariana Gheorghe
	 */ 
	public class UpdatesProcessingServiceLocator extends ServiceLocator {
		
		private var reconnectingViewContent:IViewContent;
		
		public function UpdatesProcessingServiceLocator(channelSet:ChannelSet) {
			super(channelSet);
		}
		
		/**
		 * Uses <code>UpdatesProcessingRemoteObject</code> to instantiate the remoteObject.
		 */
		override protected function createRemoteObject(serviceId:String):RemoteObject {
			return new UpdatesProcessingRemoteObject(serviceId);
		}
		
		/**
		 * Adds specific headers to operation.
		 */
		override protected function getOperation(serviceId:String, name:String):AbstractOperation {
			var operation:UpdatesProcessingOperation = UpdatesProcessingOperation(super.getOperation(serviceId, name));
			
			var headers:Dictionary = new Dictionary();
			var resourceNodeIds:ArrayCollection = CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.getResourceNodeIds();
			if (resourceNodeIds.length > 0) {
				headers[CoreConstants.RESOURCE_NODE_IDS] = resourceNodeIds;
				headers[CoreConstants.LAST_UPDATE_TIMESTAMP] = CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.lastUpdateTimestampOfServer;
			}
			operation.messageHeaders = headers;
			
			return operation;
		}
		
		override public function resultHandler(event:ResultEvent, responder:ServiceResponder):void {			
			var result:Object = event.result;
			
			if (result.hasOwnProperty(CoreConstants.LAST_UPDATE_TIMESTAMP)) {
				CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.lastUpdateTimestampOfServer = result[CoreConstants.LAST_UPDATE_TIMESTAMP];
				CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.lastUpdateTimestampOfClient = new Date().time;
				
				if (CorePlugin.getInstance().debug) {
					CorePlugin.getInstance().debug_forceUpdateAction.updateLabel();
				}
			}
			
			if (result.hasOwnProperty(CoreConstants.UPDATES)) { // updates exists, process them
				sendUpdatesToOpenEditors(result[CoreConstants.UPDATES]);
			}
			
			if (result.hasOwnProperty(CoreConstants.RESOURCE_NODE_IDS_NOT_FOUND)) {
				clearObsoleteResourceNodes(result[CoreConstants.RESOURCE_NODE_IDS_NOT_FOUND]);
			}
			
			CorePlugin.getInstance().updateTimer.restart();
			if (reconnectingViewContent != null) {
				FlexUtilGlobals.getInstance().popupHandlerFactory.removePopup(reconnectingViewContent);
				reconnectingViewContent = null;
			}
			
			// get message invocation result and send it to be processed by resultHandler
			var messageResult:Object = result[CoreConstants.MESSAGE_RESULT];
			if (responder.resultHandler != null) {
				responder.resultHandler(messageResult);
			}
		}
		
		override public function faultHandler(event:FaultEvent, responder:ServiceResponder):void {
			if (event.fault.faultCode == "Channel.Call.Failed" || event.fault.faultCode == "Client.Error.MessageSend") {
				if (reconnectingViewContent == null) {
					reconnectingViewContent = new ReconnectingViewContent();
					FlexUtilGlobals.getInstance().popupHandlerFactory.createPopupHandler()
						.setViewContent(reconnectingViewContent)
						.showModalOverAllApplication();
				}
			} else {
				super.faultHandler(event, responder);
			}
		}
		
		private function sendUpdatesToOpenEditors(resourceNodeIdToUpdates:Object):void {
			for (var resourceNodeId:String in resourceNodeIdToUpdates) {
				var updates:ArrayCollection = resourceNodeIdToUpdates[resourceNodeId];
				var nodeUpdateProcessors:ArrayCollection = CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.getNodeUpdateProcessors(resourceNodeId);
				for each (var nodeUpdateProcessor:NodeUpdateProcessor in nodeUpdateProcessors) {
					nodeUpdateProcessor.resourceNodeUpdatesHandler(nodeUpdateProcessor.context, updates);
				}
			}
		}
		
		/**
		 * Collapse the resource nodes in various editors. If a resource node is the root of an editor,
		 * close the editor.
		 */
		private function clearObsoleteResourceNodes(resourceNodeIds:ArrayCollection):void {
			var idsList:String = "";
			for each (var resourceNodeId:String in resourceNodeIds) {
				var nodeUpdateProcessors:ArrayCollection = CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.getNodeUpdateProcessors(resourceNodeId);
				for each (var nodeUpdateProcessor:NodeUpdateProcessor in nodeUpdateProcessors) {
					var rootModel:MindMapRootModelWrapper = MindMapRootModelWrapper(nodeUpdateProcessor.context.diagramShell.rootModel);
					if (Node(rootModel.model).fullNodeId == resourceNodeId) {
						// remove the editor
						FlexUtilGlobals.getInstance().workbench.closeView(nodeUpdateProcessor.editorFrontend);
					} else {
						// collapse the node
						nodeUpdateProcessor.removeChildrenForNodeId(nodeUpdateProcessor.context, resourceNodeId);
					}
					CorePlugin.getInstance().resourceNodeIdsToNodeUpdateProcessors.removeNodeUpdateProcessor(resourceNodeId, nodeUpdateProcessor);
				}
				idsList += "\n* " + resourceNodeId;
			}
			FlexUtilGlobals.getInstance().messageBoxFactory.createMessageBox()
			.setText(CorePlugin.getInstance().getMessage("editor.error.subscribe.message", [idsList]))
			.setTitle(CorePlugin.getInstance().getMessage("editor.error.subscribe.title"))
			.setWidth(300)
			.setHeight(200)
			.showMessageBox();
		}
		
	}
}
