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

import com.intellij.openapi.options.Configurable;
import org.flowerplatform.host.idea.FlowerPluginInit;
import org.flowerplatform.host.idea.I18nSupport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * @author Sebastian Solomon
 */
public class Configuration implements Configurable {
    private JPanel panel;
    private JRadioButton reuseAnExistingEditorRadioButton;
    private JRadioButton openANewEditorRadioButton;
    private JTextField txtFieldEditorsNo;
    private JButton defaultButton;
    private JLabel lblErrorMessage;
    private boolean validProp;


    public String getDisplayName() {
        return I18nSupport.message("flower");
    }

    public boolean isModified() {
        if (txtFieldEditorsNo.getText().matches("[1-9]")) {
            lblErrorMessage.setVisible(false);
        } else {
            lblErrorMessage.setVisible(true);
            return false;
        }

        if ( (reuseAnExistingEditorRadioButton.isSelected() != FlowerSettings.getInstance().ReuseEditor()) ||
             !txtFieldEditorsNo.getText().equals(FlowerSettings.getInstance().getMaxEditorsNumber()+"")) {
        return true;
        }
        return false;
    }

    public JComponent createComponent() {
        FlowerPluginInit.start();

// todo delete
//        txtFieldEditorsNo.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                if (txtFieldEditorsNo.getText().matches("[1-9]")) {
//                    lblErrorMessage.setVisible(false);
//                    validProp = true;
//                } else {
//                    lblErrorMessage.setVisible(true);
//                    validProp = false;
//                }
//            }
//        });

        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FlowerSettings.DEFAULT_REUSE_EDITOR) {
                    reuseAnExistingEditorRadioButton.doClick();
                } else {
                    openANewEditorRadioButton.doClick();
                }
                txtFieldEditorsNo.setText(FlowerSettings.DEFAULT_MAX_EDITORS_NUMBER + "");
            }
        });

        txtFieldEditorsNo.setText(FlowerSettings.getInstance().getMaxEditorsNumber()+"");
        if (FlowerSettings.getInstance().ReuseEditor()) {
            reuseAnExistingEditorRadioButton.doClick();
        } else {
           openANewEditorRadioButton.doClick();
        }

        return panel;
    }

    public void apply() {
        Properties prop = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("flowerConfig.properties");
            // set the properties value
            prop.setProperty("reuseEditor", reuseAnExistingEditorRadioButton.isSelected()+"");
            prop.setProperty("MaxEditorsNo", txtFieldEditorsNo.getText());
            FlowerSettings.getInstance().setReuseEditor(reuseAnExistingEditorRadioButton.isSelected());
            FlowerSettings.getInstance().setMaxEditorsNumber(Integer.parseInt(txtFieldEditorsNo.getText()));
            // save properties
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void disposeUIResources() {
    }

    public String getHelpTopic() {
        return "preferences.lookFeel";
    }

    public void reset() {
    }
}
