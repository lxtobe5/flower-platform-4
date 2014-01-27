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

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @author Sebastian Solomon
 */
public class FlowerPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	
	public static final String REUSE_FLOWER_EDITOR = "Reuse an existing editor (if possible)";
	public static final String PERFORMANCE_PAGE_DESCRIPTION = "The Flower Platform editor uses an embeded browser, which contains an embeded Flash (Flex) app. It's recommended to limit the number of open editors, in order to limit resources usage and improve performance and stability.";
	public static final String REUSE_EXISTING_EDITOR_lABEL = "&Reuse an existing editor (if possible)";
	public static final String REUSE_EXISTING_EDITOR_VALUE = "reuse an existing editor"; 
	public static final String OPEN_NEW_EDITOR_LABEL = "Open a &new Editor";
	public static final String OPEN_NEW_EDITOR_VALUE = "Open a new Editor";
	public static final String MAX_EDITORS_NUMBER_NAME = "max editors number";
	public static final String MAX_EDITORS_NUMBER_LABEL= "Maximum number of simultaneous editors. When this limit\nis reached, new editors will always reuse an existing editor.";
	public static final String OPEN_EDITOR_BEHAVIOR_NAME = "Open editor behavior";
	public static final String OPEN_EDITOR_BEHAVIOR_LABEL = "Open editor behavior";
	
	public FlowerPreferencePage() {
		super(GRID);
		setPreferenceStore(EclipsePlugin.getInstance().getPreferenceStore());
	    setDescription(PERFORMANCE_PAGE_DESCRIPTION);
	}
	
	@Override
	public void init(IWorkbench workbench) {
	}

	@Override
	protected void createFieldEditors() {
    addField(new RadioGroupFieldEditor(OPEN_EDITOR_BEHAVIOR_NAME,
        OPEN_EDITOR_BEHAVIOR_LABEL, 1,
        new String[][] { { REUSE_EXISTING_EDITOR_lABEL, REUSE_EXISTING_EDITOR_VALUE },
            { OPEN_NEW_EDITOR_LABEL, OPEN_NEW_EDITOR_VALUE} }, getFieldEditorParent()));
    
    IntegerFieldEditor fe = new IntegerFieldEditor(MAX_EDITORS_NUMBER_NAME, MAX_EDITORS_NUMBER_LABEL, 
			getFieldEditorParent(), 2);
    
	fe.setValidRange(1, 10);
	fe.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
	addField(fe);
	}
	
}
