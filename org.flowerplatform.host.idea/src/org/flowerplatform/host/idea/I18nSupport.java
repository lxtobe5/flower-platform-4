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

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * @author Sebastian Solomon
 */
public class I18nSupport {
    @NonNls
    public static final ResourceBundle flowerPluginBundle =
            ResourceBundle.getBundle("org.flowerplatform.host.idea.flowerPlugin");

    public static String message(@PropertyKey(resourceBundle = "org.flowerplatform.host.idea.flowerPlugin") String key, Object... params){
        String value = flowerPluginBundle.getString(key);
        if (params.length > 0) {
            return MessageFormat.format(value, params);
        }
        return value;
    }

}
