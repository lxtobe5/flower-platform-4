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
package org.flowerplatform.tests.controllers;

import static org.flowerplatform.core.CoreConstants.FILE_NODE_TYPE;
import static org.flowerplatform.core.CoreConstants.FILE_SYSTEM_NODE_TYPE;
import static org.flowerplatform.tests.EclipseIndependentTestSuite.nodeService;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import org.flowerplatform.core.CoreConstants;
import org.flowerplatform.core.CorePlugin;
import org.flowerplatform.core.file.IFileAccessController;
import org.flowerplatform.core.file.PlainFileAccessController;
import org.flowerplatform.core.node.NodeService;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.remote.NodeServiceRemote;
import org.flowerplatform.core.node.remote.ServiceContext;
import org.flowerplatform.tests.EclipseIndependentTestSuite;
import org.flowerplatform.tests.TestUtil;
import org.flowerplatform.util.Utils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Sebastian Solomon
 */
public class FileSystemControllersTest {
	
	public static final String FILE_SYSTEM_CONTROLLERS_DIR = "fileSystemControllers";
	
	public static final String DIR = TestUtil.getResourcesDir(FileSystemControllersTest.class);
	
	private static IFileAccessController fileAccessController = new PlainFileAccessController();
	
	@BeforeClass
	public static void beforeClass() throws Exception {
		if (CorePlugin.getInstance() == null) {
			// initialize in case this test is run alone
			EclipseIndependentTestSuite.beforeClass();
		}
	}
	
	@Before
	public void setUp() {
		EclipseIndependentTestSuite.deleteFiles(FILE_SYSTEM_CONTROLLERS_DIR);
		EclipseIndependentTestSuite.copyFiles(DIR + TestUtil.INITIAL_TO_BE_COPIED, FILE_SYSTEM_CONTROLLERS_DIR);
	}
	
	@Test
	public void testGetChildren() {
		assertEquals(nodeService.getChildren(new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR), FILE_NODE_TYPE), new ServiceContext<NodeService>(nodeService).add(CoreConstants.POPULATE_WITH_PROPERTIES, false)), Arrays.asList(
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/1"), FILE_NODE_TYPE),
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A"), FILE_NODE_TYPE),
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/B"), FILE_NODE_TYPE)));

		assertEquals(nodeService.getChildren(new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A"), FILE_NODE_TYPE), new ServiceContext<NodeService>(nodeService).add(CoreConstants.POPULATE_WITH_PROPERTIES, false)), Arrays.asList(
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/file1"), FILE_NODE_TYPE),
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1"), FILE_NODE_TYPE),
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2"), FILE_NODE_TYPE)));

		assertEquals(nodeService.getChildren(new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1"), FILE_NODE_TYPE), new ServiceContext<NodeService>(nodeService).add(CoreConstants.POPULATE_WITH_PROPERTIES, false)), Arrays.asList(
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1/oneFile"), FILE_NODE_TYPE)));

		assertEquals(nodeService.getChildren(new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2"), FILE_NODE_TYPE), new ServiceContext<NodeService>(nodeService).add(CoreConstants.POPULATE_WITH_PROPERTIES, false)), Arrays.asList(
								new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2/oneFolder"), FILE_NODE_TYPE)));
	}
	
	@Test
	public void addChild() {
		NodeServiceRemote nodeServiceRemote = new NodeServiceRemote();
		//add file
		ServiceContext<NodeService> context = new ServiceContext<NodeService>(nodeService);
		context.add("type", FILE_NODE_TYPE);
		context.add(CoreConstants.NAME, "newFile");
		context.add(CoreConstants.FILE_IS_DIRECTORY, false);

		String fileSystemFullNodeId = (new Node(Utils.getUri(FILE_SYSTEM_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR), FILE_NODE_TYPE)).getNodeUri();
		String fullNodeId = new Node(Utils.getUri(FILE_NODE_TYPE, fileSystemFullNodeId, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1"), FILE_NODE_TYPE).getNodeUri();
	        
		nodeServiceRemote.addChild(fullNodeId, context);
							 
		Object newFile;
		try {
			newFile = fileAccessController.getFile(FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1/newFile");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// test if the needed file was created on disk
		assertEquals(fileAccessController.exists(newFile), true);
		assertEquals(fileAccessController.isDirectory(newFile), false);
		
		//add folder
		context = new ServiceContext<NodeService>(nodeService);
		context.add("type", FILE_NODE_TYPE);
		context.add(CoreConstants.NAME, "newFolder");
		context.add(CoreConstants.FILE_IS_DIRECTORY, true);

		fullNodeId = new Node(Utils.getUri(FILE_NODE_TYPE, fileSystemFullNodeId, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1"), FILE_NODE_TYPE).getNodeUri();

		nodeServiceRemote.addChild(fullNodeId, context);
		Object newFolder;
		try {
			newFolder = fileAccessController.getFile(FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder1/newFolder");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// test if the needed directory was created on disk
		assertEquals(fileAccessController.exists(newFolder), true);
		assertEquals(fileAccessController.isDirectory(newFolder), true);
	}
	
	@Test
	public void removeNode() {
		String fileSystemFullNodeId = (new Node(Utils.getUri(FILE_SYSTEM_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR), FILE_NODE_TYPE)).getNodeUri();

		//delete oneFolder
		nodeService.removeChild(new Node(Utils.getUri(FILE_NODE_TYPE, fileSystemFullNodeId, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2"), FILE_NODE_TYPE), 
								new Node(Utils.getUri(FILE_NODE_TYPE, fileSystemFullNodeId, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2/oneFolder"), FILE_NODE_TYPE), new ServiceContext<NodeService>(nodeService));

		assertEquals(nodeService.getChildren(new Node(Utils.getUri(FILE_NODE_TYPE, null, FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2"), FILE_NODE_TYPE), new ServiceContext<NodeService>(nodeService).add(CoreConstants.POPULATE_WITH_PROPERTIES, false)), 
								Arrays.asList());
		Object newFolder;
		try {
			newFolder = fileAccessController.getFile(FILE_SYSTEM_CONTROLLERS_DIR + "/A/Folder2/oneFolder");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		assertEquals(fileAccessController.exists(newFolder), false);
	}

	public void copyDirectory(File srcPath, File dstPath) throws IOException {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdirs();
			}
			String files[] = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				File src = new File(srcPath, files[i]);
				File dest = new File(dstPath, files[i]);
				copyDirectory(src, dest);
			}
		} else {
			// if file, then copy it
			InputStream in;
			OutputStream out;
			try {
				in = new FileInputStream(srcPath);
				out = new FileOutputStream(dstPath);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			try {
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				in.close();
				out.close();
			}
		}
	}
	
}
