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

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Sebastian Solomon
 */
public class FlowerDiagramFileType extends LanguageFileType {
    public static final FlowerDiagramFileType INSTANCE = new FlowerDiagramFileType();

    private FlowerDiagramFileType() {
        super(PlainTextLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Flower Diagram";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Flower Diagram Editor";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        // TODO
        return "flower-diagram";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FlowerDiagramIcon.FILE;
    }
}
