package org.flowerplatform.desktop.idea;

import org.flowerplatform.desktop.FlowerJettyServer;
import org.flowerplatform.desktop.XulrunnerConfig;
import org.flowerplatform.host.idea.BridgeUtilIdeaToEclipse;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class IdeaPlugin implements BundleActivator {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.flowerplatform.desktop.eclipse"; //$NON-NLS-1$

	// The shared instance
	private static IdeaPlugin plugin;
	
	private FlowerJettyServer server;
	
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}
	
	public static IdeaPlugin getInstance() {
		return plugin;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		IdeaPlugin.context = bundleContext;
		plugin = this;
		server = new FlowerJettyServer();
		server.start();
		
		// needed to get the jetty port and the xulrunner path
		BridgeUtilIdeaToEclipse.iIdeaToEclipseBridge = new IdeaToEclipseBirdge();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		IdeaPlugin.context = null;
	}
	
	public FlowerJettyServer getServer(){
		return server;
	}

}
