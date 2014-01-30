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

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

/**
 * @author Sebastian Solomon
 */
public class FlowerDiagramEditorProvider implements ApplicationComponent, FileEditorProvider {

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    //whether the provider can create valid editor for the specified file or not
    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return virtualFile.getName().endsWith("." + I18nSupport.message("flower.diagram.extension"));
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "MyFileEditorProvider";
    }

    public FileEditor createEditor(Project project, VirtualFile file) {
        if (!(file instanceof LightVirtualFile)) {
              file = new LightVirtualFile() {
                  public String getName() {
                      return "empty file";
                  }
            };
        }
        return new FlowerSampleEditor(project, file);
    }

    @Override
    public void disposeEditor(@NotNull FileEditor fileEditor) {
    }

    @NotNull
    @Override
    public FileEditorState readState(@NotNull Element element, @NotNull Project project, @NotNull VirtualFile virtualFile) {
        return FileEditorState.INSTANCE;
    }

    @Override
    public void writeState(@NotNull FileEditorState fileEditorState, @NotNull Project project, @NotNull Element element) {
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return getComponentName();
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }

}
