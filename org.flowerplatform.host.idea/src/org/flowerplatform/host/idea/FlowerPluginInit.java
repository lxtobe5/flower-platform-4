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

import org.flowerplatform.host.idea.settings.FlowerSettings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Sebastian Solomon
 */
public class FlowerPluginInit {

    private static boolean isStarted = false;

    public static void start() {
        if (!isStarted) {
            startEclipse();
            configXulrunner();
            initFlowerProperties();
            isStarted = true;
        }
    }

    public static void startEclipse() {
//        FlowerFrameworkLauncher framework = new FlowerFrameworkLauncher();
//        framework.init();
//        framework.deploy();
//        framework.start();
    }

    public static void configXulrunner(){
        //TODO
    }

    public static void initFlowerProperties(){
        Properties defaults = new Properties();
        defaults.setProperty("MaxEditorsNo", FlowerSettings.DEFAULT_MAX_EDITORS_NUMBER + "");
        defaults.setProperty("reuseEditor", FlowerSettings.DEFAULT_REUSE_EDITOR + "");
        Properties prop = new Properties(defaults);
        InputStream input = null;

        try {
            input = new FileInputStream("flowerConfig.properties");
            prop.load(input);
            FlowerSettings.getInstance().setMaxEditorsNumber(Integer.parseInt(prop.getProperty("MaxEditorsNo")));
            FlowerSettings.getInstance().setReuseEditor(Boolean.parseBoolean(prop.getProperty("reuseEditor")));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
