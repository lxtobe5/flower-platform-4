package org.flowerplatform.desktop;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class DesktopPlugin implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		DesktopPlugin.context = bundleContext;
		
		// TODO see if here is a better spot to configure xulrunner
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		DesktopPlugin.context = null;
	}

}
