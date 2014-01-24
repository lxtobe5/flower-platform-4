package org.flowerplatform.desktop;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XulrunnerConfig {
	private static final Logger logger = LoggerFactory
			.getLogger(FlowerJettyServer.class);
	
	public static String getXulrunnerFile(){
		File file = null;
		Bundle bundle = Platform.getBundle("org.flowerplatform.desktop");
		if (bundle != null) {
			URL resourceUrl = bundle
					.getResource("libs/mozilla/xulrunner_3_6_28_win32");
			if (resourceUrl != null) {
				try {
					URL fileUrl = FileLocator.toFileURL(resourceUrl);
					file = new File(fileUrl.toURI());
//					System.setProperty("org.eclipse.swt.browser.XULRunnerPath",
//							file.getAbsolutePath());
				} catch (Exception e) {
					return null;
				}
			}
		}
			return file.getAbsolutePath();
	}
	
}
