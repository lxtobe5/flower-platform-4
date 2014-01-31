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

package org.flowerplatform.host.idea.toolWindow;

import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * @author Sebastian Solomon
 */
public class ToolWindowShowAction extends ToggleAction {

    public ToolWindowShowAction() {
        super("Show Properties Tool");
    }

    public boolean isSelected(AnActionEvent event) {
        return ToolWindowManager.getInstance(event.getProject()).getToolWindow("Properties").isAvailable();
    }

    public void setSelected(AnActionEvent event, boolean state) {
        if (!state ){
            ToolWindowManager.getInstance(event.getProject()).getToolWindow("Properties").setAvailable(false, null);
        } else {
            ToolWindowManager.getInstance(event.getProject()).getToolWindow("Properties").setAvailable(true, null);
        }
    }

}
