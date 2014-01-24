package org.flowerplatform.desktop.eclipse;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.flowerplatform.desktop.FlowerJettyServer;
import org.flowerplatform.desktop.XulrunnerConfig;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EclipsePlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.flowerplatform.desktop.eclipse"; //$NON-NLS-1$

	// The shared instance
	private static EclipsePlugin plugin;
	
	private FlowerJettyServer server;
	
	/**
	 * The constructor
	 */
	public EclipsePlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		server = new FlowerJettyServer();
		server.start();
		if (XulrunnerConfig.getXulrunnerFile() != null) {
		System.setProperty("org.eclipse.swt.browser.XULRunnerPath",
				XulrunnerConfig.getXulrunnerFile());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EclipsePlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
