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
package org.flowerplatform.codesync.code.java.feature_provider;

import static org.flowerplatform.codesync.code.java.feature_provider.JavaFeaturesConstants.DOCUMENTATION;
import static org.flowerplatform.codesync.code.java.feature_provider.JavaFeaturesConstants.MODIFIERS;

import java.util.Arrays;
import java.util.List;

import org.flowerplatform.codesync.adapter.IModelAdapter;
import org.flowerplatform.codesync.feature_provider.NodeFeatureProvider;

public class JavaTypeFeatureProvider extends NodeFeatureProvider {
	
	public static final String TYPE_MEMBERS = "typeMembers";
	public static final String SUPER_CLASS = "superClass";
	public static final String SUPER_INTERFACES = "superInterfaces";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<?> getFeatures(Object element) {
		List features = super.getFeatures(element);
		features.addAll(Arrays.asList(
				TYPE_MEMBERS,
				DOCUMENTATION,
				MODIFIERS,
				SUPER_CLASS,
				SUPER_INTERFACES
				));
		return features;
	}

	@Override
	public int getFeatureType(Object feature) {
		if (TYPE_MEMBERS.equals(feature) ||
				MODIFIERS.equals(feature) ||
				SUPER_INTERFACES.equals(feature)) {
			return IModelAdapter.FEATURE_TYPE_CONTAINMENT;
		}
		return super.getFeatureType(feature);
	}
	
}