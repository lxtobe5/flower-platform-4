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
package org.flowerplatform.codesync.code.java.feature_provider;

import org.flowerplatform.codesync.code.java.CodeSyncCodeJavaConstants;
import org.flowerplatform.codesync.feature_provider.NodeFeatureProvider;

public class JavaTypeDeclarationFeatureProvider extends NodeFeatureProvider {
	
	public JavaTypeDeclarationFeatureProvider() {
		valueFeatures.add(CodeSyncCodeJavaConstants.DOCUMENTATION);
		valueFeatures.add(CodeSyncCodeJavaConstants.SUPER_CLASS);
		
		containmentFeatures.add(CodeSyncCodeJavaConstants.MODIFIERS);
		containmentFeatures.add(CodeSyncCodeJavaConstants.SUPER_INTERFACES);
		containmentFeatures.add(CodeSyncCodeJavaConstants.TYPE_MEMBERS);
	}
	
}