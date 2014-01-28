package org.flowerplatform.desktop.eclipse.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

/**
 * @author Sebastian Solomon
 */
public abstract class FlowerView extends ViewPart {
	
	protected Browser browser;

	public Browser getBrowser() {
		return browser;
	}

	@Override
	public void createPartControl(Composite parent) {
		initBrowser(parent);
		browser.setUrl("https://www.google.ro/");
		return;
	}

	@Override
	public void setFocus() {
		browser.setFocus();
	}
	
	protected void initBrowser(Composite parent){
		browser = new Browser(parent, SWT.NONE);
		browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

}