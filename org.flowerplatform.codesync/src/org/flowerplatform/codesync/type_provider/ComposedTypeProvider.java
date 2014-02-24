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
package org.flowerplatform.codesync.type_provider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mariana Gheorghe
 */
public class ComposedTypeProvider implements ITypeProvider {

	private List<ITypeProvider> typeProviders = new ArrayList<ITypeProvider>();
	
	@Override
	public String getType(Object object) {
		for (ITypeProvider typeProvider : typeProviders) {
			String type = typeProvider.getType(object);
			if (type != null) {
				return type;
			}
		}
		throw new RuntimeException("Cannot provide type for " + object);
	}
	
	public ComposedTypeProvider addTypeProvider(ITypeProvider typeProvider) {
		typeProviders.add(typeProvider);
		return this;
	}

}
