/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico Software, <http://www.crispico.com/>.
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
 * license-end
 */
package org.flowerplatform.codesync.code.java.adapter;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.flowerplatform.codesync.code.java.CodeSyncCodeJavaConstants;
import org.flowerplatform.codesync.code.java.feature_provider.JavaParameterFeatureProvider;
import org.flowerplatform.core.CoreConstants;

/**
 * Mapped to {@link SingleVariableDeclaration}. Children are {@link Modifier}s.
 * 
 * @see JavaParameterFeatureProvider
 * 
 * @author Mariana
 */
public class JavaParameterModelAdapter extends JavaAbstractAstNodeModelAdapter {

	@Override
	public List<?> getChildren(Object modelElement) {
		return Collections.emptyList();
	}

	@Override
	public Object getMatchKey(Object modelElement) {
		return getVariableDeclaration(modelElement).getName().getIdentifier();
	}
	
	@Override
	public Object getValueFeatureValue(Object element, Object feature, Object correspondingValue) {
		if (CoreConstants.NAME.equals(feature)) {
			return getVariableDeclaration(element).getName().getIdentifier();
		} else if (CodeSyncCodeJavaConstants.TYPED_ELEMENT_TYPE.equals(feature)) {
			return getStringFromType(getVariableDeclaration(element).getType());
		}
		return super.getValueFeatureValue(element, feature, correspondingValue);
	}

	@Override
	public void setValueFeatureValue(Object element, Object feature, Object value) {
		if (CoreConstants.NAME.equals(feature)) {
			SingleVariableDeclaration parameter = getVariableDeclaration(element);
			String name = (String) value;
			parameter.setName(parameter.getAST().newSimpleName(name));
		} else if (CodeSyncCodeJavaConstants.TYPED_ELEMENT_TYPE.equals(feature)) {
			SingleVariableDeclaration parameter = getVariableDeclaration(element);
			Type type = getTypeFromString(parameter.getAST(), (String) value);
			parameter.setType(type);
		}
		super.setValueFeatureValue(element, feature, value);
	}

	private SingleVariableDeclaration getVariableDeclaration(Object element) {
		return (SingleVariableDeclaration) element;
	}

}