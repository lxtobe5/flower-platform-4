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

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author Sebastian Solomon
 */
public class ToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory {
    private JPanel panel;
    private final JWebBrowser webBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());

    @Override
    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        NativeInterface.open();
        UIUtils.setPreferredLookAndFeel();
        panel.add(webBrowser, BorderLayout.CENTER);
        panel.setVisible(true);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate("www.google.ro");
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(panel, "properties", false);
        toolWindow.getContentManager().addContent(content);
//        toolWindow.setSplitMode(false, null);
    }
}
