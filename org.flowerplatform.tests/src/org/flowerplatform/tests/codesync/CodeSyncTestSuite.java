/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico Software, <http://www.crispico.com/>.
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
 * license-end
 */
package org.flowerplatform.tests.codesync;

import static org.flowerplatform.tests.EclipseIndependentTestSuite.startPlugin;

import java.io.File;

import org.flowerplatform.codesync.CodeSyncPlugin;
import org.flowerplatform.core.CorePlugin;
import org.flowerplatform.freeplane.FreeplanePlugin;
import org.flowerplatform.mindmap.MindMapPlugin;
import org.flowerplatform.tests.EclipseDependentTestSuiteBase;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Mariana
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	CodeSyncTest.class, 
//	CodeSyncJavascriptTest.class,
//	CodeSyncWikiTest.class 
})
public class CodeSyncTestSuite extends EclipseDependentTestSuiteBase {
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		startPlugin(new CodeSyncPlugin());
		startPlugin(new FreeplanePlugin());
		startPlugin(new MindMapPlugin());
	}
	
	public static File getFile(String path) {
		String absolutePath = /*"/org/ws_trunk/" +*/ path;
		try {
			return (File) CorePlugin.getInstance().getFileAccessController().getFile(absolutePath);
		} catch (Exception e) {			
			throw new RuntimeException(String.format("Error while getting resource %s", absolutePath), e);
		}
	}
	
}