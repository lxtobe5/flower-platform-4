package org.flowerplatform.flex_client.core.editor.text {
	
	import org.flowerplatform.flex_client.core.CoreConstants;
	import org.flowerplatform.flex_client.core.editor.EditorDescriptor;
	import org.flowerplatform.flex_client.core.editor.EditorFrontend;
	import org.flowerplatform.flexutil.layout.ViewLayoutData;
	
	/**
	 * @author Mariana Gheorghe
	 */
	public class TextEditorDescriptor extends EditorDescriptor {
		
		override public function getEditorName():String {
			return "text";
		}
		
		override protected function createViewInstance():EditorFrontend	{
			return new TextEditorFrontend();
		}
		
		public override function getId():String {	
			return CoreConstants.TEXT_CONTENT_TYPE;
		}
		
		public override function getIcon(viewLayoutData:ViewLayoutData=null):Object {				
			return null;
		}
		
		public override function getTitle(viewLayoutData:ViewLayoutData=null):String {		
			return viewLayoutData.customData;
		}
	}
}