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

package org.flowerplatform.host.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;

/**
 * @author Sebastian Solomon
 */
public class FirstAction extends AnAction {

    public void actionPerformed(AnActionEvent e) {

        VirtualFile vf = new LightVirtualFile("Flower.flower-diagram"){
            public String getPresentableName() {
                return "DUMMY";
            }
        };

        FileEditor[] fileEditors = FileEditorManager.getInstance(e.getProject()).openFile(vf, true, false);
        FlowerSampleEditor flowerEditor = (FlowerSampleEditor) fileEditors[0];
//        flowerEditor.getBrowser().executeJavascript("command");
    }
}
