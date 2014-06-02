package org.flowerplatform.flex_client.core.editor.action {
	
	import org.flowerplatform.flex_client.core.CoreConstants;
	import org.flowerplatform.flex_client.core.CorePlugin;
	import org.flowerplatform.flexutil.action.ActionBase;
	
	import spark.formatters.DateTimeFormatter;
	
	/**
	 * @author Mariana Gheorghe
	 */
	public class ForceUpdateAction extends ActionBase {
		
		public function ForceUpdateAction() {
			super();
			
			parentId = CoreConstants.DEBUG;
			updateLabel();			
		}
		
		public function updateLabel():void {
			if (CorePlugin.getInstance().resourceNodesManager.lastUpdateTimestampOfClient == -1) {
				label = "No resource updates requested yet";
			} else {
				var formatter:DateTimeFormatter = new DateTimeFormatter();
				formatter.dateTimePattern = "yyyy-MM-dd HH:mm:ss";
				var date:Date = new Date();
				date.time = CorePlugin.getInstance().resourceNodesManager.lastUpdateTimestampOfClient;
				label = "Last update: " + formatter.format(date);
			}
		}
		
		override public function run():void {
			CorePlugin.getInstance().serviceLocator.invoke("resourceService.ping");
		}
		
	}
}