package org.flowerplatform.desktop.eclipse.parts;

import org.eclipse.swt.widgets.Composite;

/**
 * @author Sebastian Solomon
 */
public class PropertiesView extends FlowerView {
	
	@Override
	public void createPartControl(Composite parent) {
		initBrowser(parent);
		browser.setUrl("http://flower-platform.org/");
		return;
	}

}
