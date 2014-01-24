package org.flowerplatform.desktop.eclipse.parts;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IStorage;
import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.flowerplatform.desktop.eclipse.util.StringInput;
import org.flowerplatform.desktop.eclipse.util.StringStorage;

public class NewCommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
//		String string = "";
//		IStorage storage = new StringStorage(string);
//	    IStorageEditorInput input = new StringInput(storage);
//		try {
//			IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
//					.getActivePage(), input,
//					FlowerEditor.EDITOR_ID);
//		} catch (PartInitException e) {
//			e.printStackTrace();
//		}
		// TODO
		scriptExecute("command", false);
		return null;
		
		
		
	}
	
	private void scriptExecute(String script, boolean newEditor) {
		
		if (newEditor) {
			String string = "";
			IStorage storage = new StringStorage(string);
		    IStorageEditorInput input = new StringInput(storage);
			FlowerEditor newFlowerEditor;
			try {
				newFlowerEditor = (FlowerEditor)IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage(), input,
						FlowerEditor.EDITOR_ID);
			} catch (PartInitException e) {
				throw new RuntimeException("Could not open editor.", e);
			}
//			newFlowerEditor.getBrowser().execute(script);
		} else {
			FlowerEditor flowerEditor = findLastOpenedEditor();
//			flowerEditor.getBrowser().execute(script);
			if (flowerEditor != null) {
				flowerEditor.getBrowser().setUrl("http://flower-platform.org/");
			} else {
				scriptExecute(script, true);
			}
		}
	}
	
	
	FlowerEditor findLastOpenedEditor(){
		//TODO
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (editor instanceof FlowerEditor) {
			return (FlowerEditor) editor;
		}
		return null;
	}

}