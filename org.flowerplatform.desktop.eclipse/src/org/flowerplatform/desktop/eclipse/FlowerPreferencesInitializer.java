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
package org.flowerplatform.desktop.eclipse;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * @author Sebastian Solomon
 */
public class FlowerPreferencesInitializer extends AbstractPreferenceInitializer {

	public FlowerPreferencesInitializer() {
	}

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = EclipsePlugin.getInstance().getPreferenceStore();
	    store.setDefault(FlowerPreferencePage.MAX_EDITORS_NUMBER_NAME, 5);
	    store.setDefault(FlowerPreferencePage.OPEN_EDITOR_BEHAVIOR_NAME, FlowerPreferencePage.REUSE_EXISTING_EDITOR_VALUE);
	}

}
