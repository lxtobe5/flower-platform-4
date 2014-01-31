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

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

/**
 * @author Sebastian Solomon
 */
public class FlowerSampleEditor implements FileEditor/*, FileEditorManagerListener*/ {

    JPanel browserPanel;

    final JWebBrowser webBrowser = new JWebBrowser(JWebBrowser.destroyOnFinalization());

    VirtualFile editorFile;

    Project project;

    static {
        FlowerPluginInit.start();
    }


    public FlowerSampleEditor(Project project, VirtualFile file) {
//        FileEditorManager.getInstance(project).addFileEditorManagerListener(this);
        editorFile = file;
        this.project =  project;
        browserPanel = new JPanel(new BorderLayout());
        NativeInterface.open();
        UIUtils.setPreferredLookAndFeel();
        webBrowser.setBarsVisible(false);
        webBrowser.navigate("www.google.ro");
//        System.out.println(BridgeUtilIdeaToEclipse.iIdeaToEclipseBridge.getUrl());
        browserPanel.add(webBrowser, BorderLayout.CENTER);
        browserPanel.setBorder(BorderFactory.createEtchedBorder());
        browserPanel.setVisible(true);
    }

    public JWebBrowser getBrowser(){
        return webBrowser;
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return browserPanel;
    }

    /**
     Returns component to be focused when editor is opened. Method
     should never return null.
     */
    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return browserPanel;
    }

    @NotNull
    @Override
    public String getName() {
        return "Flower Sample Editor";
    }

    @NotNull
    @Override
    public FileEditorState getState(@NotNull FileEditorStateLevel fileEditorStateLevel) {
        return FileEditorState.INSTANCE;
//        return DummyFileEditorState.DUMMY;
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {
    }

    /**
     @return whether the editor's content is modified in comparision
     with its file.
     */
    @Override
    public boolean isModified() {
        return false;
    }
    /**
     @return whether the editor is valid or not. For some reasons
     editor can become invalid. For example, text editor
     becomes invalid when its file is deleted.
     */
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void selectNotify() {
    }

    /**
     This method is invoked each time when the editor is deselected.
     */
    @Override
    public void deselectNotify() {
    }

    /**
     * Removes specified listener
     *
     * @param listener to be added
     */
    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    /**
     * Adds specified listener
     * @param listener to be removed
     */
    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Nullable
    @Override
    public StructureViewBuilder getStructureViewBuilder() {
        return null;
    }

    @Override
    public void dispose() {
    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> tKey) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> tKey, @Nullable T t) {
    }

}
