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

package org.flowerplatform.host.idea.settings;

/**
 * @author Sebastian Solomon
 */
public class FlowerSettings {
    public static final boolean DEFAULT_REUSE_EDITOR = true;
    public static final int DEFAULT_MAX_EDITORS_NUMBER = 5;

    private boolean reuseEditor;
    private int maxEditorsNumber;
    private static FlowerSettings instance;

    public static FlowerSettings getInstance() {
        if (instance == null) {
            instance =  new FlowerSettings();
        }
        return instance;
    }

    private FlowerSettings() {
        reuseEditor = DEFAULT_REUSE_EDITOR;
        maxEditorsNumber = DEFAULT_MAX_EDITORS_NUMBER;
    }

    public void setReuseEditor(boolean reuseEditor) {
        this.reuseEditor = reuseEditor;
    }

    public void setMaxEditorsNumber(int maxEditorsNumber) {
        this.maxEditorsNumber = maxEditorsNumber;
    }

    public boolean ReuseEditor() {
        return reuseEditor;
    }

    public int getMaxEditorsNumber() {
        return maxEditorsNumber;
    }

}
