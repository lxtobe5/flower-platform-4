/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico, <http://www.crispico.com/>.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details, at <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *   Crispico - Initial API and implementation
 *
 * license-end
 */
package org.flowerplatform.codesync.adapter;

import java.util.HashMap;
import java.util.Map;

import org.flowerplatform.codesync.feature_provider.IFeatureProvider;
import org.flowerplatform.core.node.remote.Node;

public class ModelAdapterFactorySet {

	protected ModelAdapterFactory ancestorFactory;

	protected ModelAdapterFactory leftFactory;
	
	protected ModelAdapterFactory rightFactory;

	protected Map<Object, IFeatureProvider> featureProviders = new HashMap<Object, IFeatureProvider>();
	
	protected boolean useUIDs = false;
	
	public ModelAdapterFactory getAncestorFactory() {
		return ancestorFactory;
	}

	public ModelAdapterFactory getLeftFactory() {
		return leftFactory;
	}

	public ModelAdapterFactory getRightFactory() {
		return rightFactory;
	}

	public IFeatureProvider getFeatureProvider(Object element) {
		if (element instanceof Node) {
			IFeatureProvider featureProvider = featureProviders.get(((Node) element).getType());
			if (featureProvider != null) {
				return featureProvider;
			}
		}
		for (Object key : featureProviders.keySet()) {
			if (key instanceof Class) {
				if (((Class) key).isAssignableFrom(element.getClass())) {
					IFeatureProvider featureProvider = featureProviders.get(key);
					if (featureProvider != null) {
						return featureProvider;
					}
				}
			}
		}
		throw new IllegalArgumentException("Cannot find feature provider for " + element);
	}
	
	/**
	 * Feature providers are mapped to either class (e.g. JDT type, attribute), or the type of a {@link Node}.
	 */
	public ModelAdapterFactorySet addFeatureProvider(Object key, IFeatureProvider provider) {
		featureProviders.put(key, provider);
		return this;
	}
	
	public void initialize(String limitedPath, boolean useUIDs) {
		this.useUIDs = useUIDs;
	}
	
	public boolean useUIDs() {
		return useUIDs;
	}
	
	public ModelAdapterFactorySet() {
		
	}
	
	public ModelAdapterFactorySet(ModelAdapterFactory ancestorModelAdapterFactory, ModelAdapterFactory leftModelAdapterFactory, ModelAdapterFactory rightModelAdapterFactory) {
		super();
		this.ancestorFactory = ancestorModelAdapterFactory;
		this.leftFactory = leftModelAdapterFactory;
		this.rightFactory = rightModelAdapterFactory;
	}
	
}