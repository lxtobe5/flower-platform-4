package org.flowerplatform.flex_client.host_app.mobile.view_edit_server_account
{
	import flash.net.SharedObject;
	
	import mx.collections.ArrayCollection;
	import mx.core.IVisualElement;
	
	import org.flowerplatform.flex_client.host_app.mobile.view_server_account.ServerAccountsView;
	import org.flowerplatform.flex_client.resources.Resources;
	import org.flowerplatform.flexutil.FlexUtilAssets;
	import org.flowerplatform.flexutil.FlexUtilGlobals;
	import org.flowerplatform.flexutil.action.ActionBase;
	import org.flowerplatform.flexutil.popup.IMessageBox;

	/**
	 * @author Sebastian Solomon
	 */
	public class RemoveServerAccountAction extends ActionBase {
		private var indexToDelete:int;
		private var editAccountView:EditServerAccountView;
		
		public function RemoveServerAccountAction() {
			super();
			label = Resources.getMessage("mobile.remove.server.account.label");
		}
		
		public function setIndexToDelete(index:int):void {
			indexToDelete = index;
		}
		
		public function setEditAccountView(value:EditServerAccountView):void {
			editAccountView = value;
		}
		
		public function seteditAccountView(editServerAccountView:EditServerAccountView):void {
			editAccountView = editServerAccountView;
		}
		
		override public function run():void {
			var messageBox:IMessageBox =FlexUtilGlobals.getInstance().messageBoxFactory.createMessageBox()
				.setText(Resources.getMessage("mobile.confirm.delete.account.message"))
				.setTitle(Resources.getMessage("mobile.delete.account.title"))
				.setWidth(300)
				.setHeight(200);
			
			messageBox.addButton(FlexUtilAssets.INSTANCE.getMessage('dialog.yes'), function():void {confirmAccountDeleteHandler(true, messageBox);})
					  .addButton(FlexUtilAssets.INSTANCE.getMessage('dialog.no'), function():void {confirmAccountDeleteHandler(false, messageBox);})
   	    			  .showMessageBox();
		}
		
		private function confirmAccountDeleteHandler(yesClicked:Boolean, messageBox:IMessageBox):void {
			if (yesClicked) {
				if (indexToDelete >= 0) {
					var sharedObject:SharedObject = ServerAccountsView.getSharedObject();
					var arrayCollection:ArrayCollection = sharedObject.data.serverAccounts;
					
					arrayCollection.removeItemAt(indexToDelete);
					sharedObject.data.serverAccounts = arrayCollection;
					sharedObject.flush(10000);
					editAccountView.getServerAccountView().refresh();
					FlexUtilGlobals.getInstance().popupHandlerFactory.removePopup(IVisualElement(messageBox));
					FlexUtilGlobals.getInstance().popupHandlerFactory.removePopup(editAccountView);
				}
			}
		}
			
	}
}