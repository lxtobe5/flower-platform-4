package org.flowerplatform.desktop.eclipse.parts;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class FlowerEditor extends EditorPart {

	public static final String EDITOR_ID = "FlowerEditor";

	private static final Logger logger = LoggerFactory
			.getLogger(FlowerEditor.class);
	
	private Browser browser;

	private Composite parent;
	
	public Composite getParent() {
		return parent;
	}

	public Browser getBrowser() {
		return browser;
	}

	@Override
	public void doSaveAs() {
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		
		setSite(site);
		setInput(input);
		
		setPartName(getEditorInput().getName());
		setTitleToolTip(getEditorInput().getToolTipText());

		site.getPage().addPartListener(new IPartListener() {

			public void partActivated(IWorkbenchPart part) {
			}

			public void partBroughtToTop(IWorkbenchPart part) {
			}

			public void partClosed(IWorkbenchPart part) {
			}

			public void partDeactivated(IWorkbenchPart part) {
			}

			public void partOpened(IWorkbenchPart part) {
			}

		});

	}

	public boolean isDirty() {
		return false;
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
	}
	
	public boolean isSaveAsAllowed() {
		return false;
	}

	@SuppressWarnings("restriction")
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		browser.setUrl("https://www.google.ro/");
		return;
	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}

}
