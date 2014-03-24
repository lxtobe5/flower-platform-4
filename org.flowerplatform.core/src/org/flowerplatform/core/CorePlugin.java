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
package org.flowerplatform.core;

import static org.flowerplatform.core.NodePropertiesConstants.FILE_CREATION_TIME;
import static org.flowerplatform.core.NodePropertiesConstants.FILE_IS_DIRECTORY;
import static org.flowerplatform.core.NodePropertiesConstants.FILE_LAST_ACCESS_TIME;
import static org.flowerplatform.core.NodePropertiesConstants.FILE_LAST_MODIFIED_TIME;
import static org.flowerplatform.core.NodePropertiesConstants.FILE_SIZE;
import static org.flowerplatform.core.NodePropertiesConstants.IS_SUBSCRIBABLE;
import static org.flowerplatform.core.NodePropertiesConstants.NAME;
import static org.flowerplatform.core.node.controller.AddNodeController.ADD_NODE_CONTROLLER;
import static org.flowerplatform.core.node.controller.ChildrenProvider.CHILDREN_PROVIDER;
import static org.flowerplatform.core.node.controller.PropertiesProvider.PROPERTIES_PROVIDER;
import static org.flowerplatform.core.node.controller.PropertySetter.PROPERTY_SETTER;
import static org.flowerplatform.core.node.controller.RemoveNodeController.REMOVE_NODE_CONTROLLER;
import static org.flowerplatform.core.node.remote.AddChildDescriptor.ADD_CHILD_DESCRIPTOR;
import static org.flowerplatform.core.node.remote.PropertyDescriptor.PROPERTY_DESCRIPTOR;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.flowerplatform.core.file.FileAddNodeController;
import org.flowerplatform.core.file.FileChildrenProvider;
import org.flowerplatform.core.file.FilePropertiesProvider;
import org.flowerplatform.core.file.FilePropertySetter;
import org.flowerplatform.core.file.FileRemoveNodeController;
import org.flowerplatform.core.file.IFileAccessController;
import org.flowerplatform.core.file.PlainFileAccessController;
import org.flowerplatform.core.node.NodeService;
import org.flowerplatform.core.node.controller.AddNodeController;
import org.flowerplatform.core.node.controller.ConstantValuePropertyProvider;
import org.flowerplatform.core.node.controller.PropertiesProvider;
import org.flowerplatform.core.node.controller.PropertySetter;
import org.flowerplatform.core.node.controller.RemoveNodeController;
import org.flowerplatform.core.node.controller.ResourceTypeDynamicCategoryProvider;
import org.flowerplatform.core.node.remote.AddChildDescriptor;
import org.flowerplatform.core.node.remote.GenericDescriptor;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.remote.NodeServiceRemote;
import org.flowerplatform.core.node.remote.PropertyDescriptor;
import org.flowerplatform.core.node.remote.ResourceInfoServiceRemote;
import org.flowerplatform.core.node.resource.ResourceDebugControllers;
import org.flowerplatform.core.node.resource.ResourceInfoService;
import org.flowerplatform.core.node.resource.ResourceUnsubscriber;
import org.flowerplatform.core.node.resource.in_memory.InMemoryResourceInfoDAO;
import org.flowerplatform.core.node.update.controller.UpdateAddNodeController;
import org.flowerplatform.core.node.update.controller.UpdatePropertySetterController;
import org.flowerplatform.core.node.update.controller.UpdateRemoveNodeController;
import org.flowerplatform.core.repository.RepositoryChildrenProvider;
import org.flowerplatform.core.repository.RepositoryPropertiesProvider;
import org.flowerplatform.core.repository.RootChildrenProvider;
import org.flowerplatform.core.repository.RootPropertiesProvider;
import org.flowerplatform.util.controller.AllDynamicCategoryProvider;
import org.flowerplatform.util.controller.TypeDescriptorRegistry;
import org.flowerplatform.util.plugin.AbstractFlowerJavaPlugin;
import org.flowerplatform.util.servlet.ResourcesServlet;
import org.osgi.framework.BundleContext;

/**
 * @author Cristian Spiescu
 * @author Cristina Constantinescu
 * @author Mariana Gheorghe
 */
public class CorePlugin extends AbstractFlowerJavaPlugin {

	public static final String TYPE_KEY = "type";
	public static final String FILE_SYSTEM_NODE_TYPE = "fileSystem";
	public static final String FILE_NODE_TYPE = "fileNode";
	public static final String FILE_SYSTEM_PATH = "d:/temp/";
	
	public static final String ROOT_TYPE = "root";
	public static final String REPOSITORY_TYPE = "repository";
	public static final String CODE_TYPE = "code";
	
	public static final String SELF_RESOURCE = "self";
	
	public static final String PROPERTY_FOR_TITLE_DESCRIPTOR = "propertyForTitleDescriptor";
	public static final String PROPERTY_FOR_ICON_DESCRIPTOR = "propertyForIconDescriptor";
	
	protected static CorePlugin INSTANCE;

	protected IFileAccessController fileAccessController = new PlainFileAccessController();

	/**
	 * @author Sebastian Solomon
	 */
	protected RemoteMethodInvocationListener remoteMethodInvocationListener = new RemoteMethodInvocationListener();

	protected ServiceRegistry serviceRegistry = new ServiceRegistry();
	protected TypeDescriptorRegistry nodeTypeDescriptorRegistry = new TypeDescriptorRegistry();
	protected NodeService nodeService = new NodeService(nodeTypeDescriptorRegistry);
	protected ResourceInfoService resourceInfoService = new ResourceInfoService(nodeTypeDescriptorRegistry, new InMemoryResourceInfoDAO());

	private ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();
	
	public static CorePlugin getInstance() {
		return INSTANCE;
	}
	
	/**
	 * @author Sebastian Solomon
	 */
	public RemoteMethodInvocationListener getRemoteMethodInvocationListener() {
		return remoteMethodInvocationListener;
	}
	
	/**
	 * @author Mariana Gheorghe
	 */
	public IFileAccessController getFileAccessController() {
		return fileAccessController;
	}
	
	public ServiceRegistry getServiceRegistry() {
		return serviceRegistry;
	}

	public TypeDescriptorRegistry getNodeTypeDescriptorRegistry() {
		return nodeTypeDescriptorRegistry;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public ResourceInfoService getResourceInfoService() {
		return resourceInfoService;
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
			
		getServiceRegistry().registerService("nodeService", new NodeServiceRemote());
		getServiceRegistry().registerService("resourceInfoService", new ResourceInfoServiceRemote());
		
		new ResourceUnsubscriber().start();
		
		getNodeTypeDescriptorRegistry().getOrCreateTypeDescriptor(ROOT_TYPE)
		.addAdditiveController(PROPERTIES_PROVIDER, new RootPropertiesProvider())
		.addAdditiveController(CHILDREN_PROVIDER, new RootChildrenProvider());

		getNodeTypeDescriptorRegistry().getOrCreateTypeDescriptor(REPOSITORY_TYPE)
		.addAdditiveController(PROPERTIES_PROVIDER, new RepositoryPropertiesProvider())
		.addAdditiveController(CHILDREN_PROVIDER, new RepositoryChildrenProvider());

		getNodeTypeDescriptorRegistry().getOrCreateTypeDescriptor(CODE_TYPE)
		.addAdditiveController(PROPERTIES_PROVIDER, new ConstantValuePropertyProvider(IS_SUBSCRIBABLE, true));
		
		getNodeTypeDescriptorRegistry().addDynamicCategoryProvider(new ResourceTypeDynamicCategoryProvider());
				
		getNodeTypeDescriptorRegistry().getOrCreateCategoryTypeDescriptor(AllDynamicCategoryProvider.CATEGORY_ALL)
		.addAdditiveController(AddNodeController.ADD_NODE_CONTROLLER, new UpdateAddNodeController())
		.addAdditiveController(RemoveNodeController.REMOVE_NODE_CONTROLLER, new UpdateRemoveNodeController())
		.addAdditiveController(PropertySetter.PROPERTY_SETTER, new UpdatePropertySetterController())
		.addSingleController(PROPERTY_FOR_TITLE_DESCRIPTOR, new GenericDescriptor(NodePropertiesConstants.NAME))
		.addSingleController(PROPERTY_FOR_ICON_DESCRIPTOR, new GenericDescriptor(NodePropertiesConstants.ICONS));
		
		registerFileSystemControllers();
		new ResourceDebugControllers().registerControllers();
		
		//TODO use Flower property
		boolean isDeleteTempFolderAtStartProperty = true;
		if (isDeleteTempFolderAtStartProperty) {
			FileUtils.deleteDirectory(ResourcesServlet.TEMP_FOLDER);
		}
	}

	/**
	 * @author Sebastian Solomon
	 */
	private void registerFileSystemControllers() {
		Map<String, Object> filePropertyMap = new HashMap<String, Object>();
		Map<String, Object> folderPropertyMap = new HashMap<String, Object>();
		folderPropertyMap.put(FILE_IS_DIRECTORY, true);
		filePropertyMap.put(FILE_IS_DIRECTORY, false);
		
		getNodeTypeDescriptorRegistry().getOrCreateCategoryTypeDescriptor("category.fileContainer")
		.addAdditiveController(CHILDREN_PROVIDER, new FileChildrenProvider())
		.addAdditiveController(REMOVE_NODE_CONTROLLER, new FileRemoveNodeController())
		.addAdditiveController(ADD_NODE_CONTROLLER, new FileAddNodeController())
		.addAdditiveController(ADD_CHILD_DESCRIPTOR, new AddChildDescriptor().setChildTypeAs("fileNode").setLabelAs("File")
			.setPropertiesAs(filePropertyMap)
			.setIconAs(getResourceUrl("images/file.gif"))
			.setOrderIndexAs(10))
		.addAdditiveController(ADD_CHILD_DESCRIPTOR, new AddChildDescriptor().setChildTypeAs("fileNode").setLabelAs("Folder")
			.setPropertiesAs(folderPropertyMap)
			.setIconAs(getResourceUrl("images/folder.gif"))
			.setOrderIndexAs(20));
	
		getNodeTypeDescriptorRegistry().getOrCreateTypeDescriptor("fileSystem")
		.addAdditiveController(PROPERTIES_PROVIDER, new PropertiesProvider() {
			public void populateWithProperties(Node node, ServiceContext context) {
				node.getProperties().put(NAME, "fileSystem");
			}
		})
		.addAdditiveController(PROPERTIES_PROVIDER, new ConstantValuePropertyProvider(IS_SUBSCRIBABLE, true))
		.addCategory("category.fileContainer");
		
		getNodeTypeDescriptorRegistry().getOrCreateTypeDescriptor("fileNode")
		.addAdditiveController(PROPERTIES_PROVIDER, new FilePropertiesProvider())
		.addAdditiveController(PROPERTY_SETTER, new FilePropertySetter())
		.addAdditiveController(PROPERTY_DESCRIPTOR, new PropertyDescriptor().setNameAs(NAME))
		.addAdditiveController(PROPERTY_DESCRIPTOR, new PropertyDescriptor().setNameAs(FILE_SIZE).setTypeAs("FileSize"))
		.addAdditiveController(PROPERTY_DESCRIPTOR, new PropertyDescriptor().setNameAs(FILE_IS_DIRECTORY).setReadOnlyAs(true))
		.addAdditiveController(PROPERTY_DESCRIPTOR, new PropertyDescriptor().setNameAs(FILE_CREATION_TIME).setReadOnlyAs(true).setTypeAs("Date"))
		.addAdditiveController(PROPERTY_DESCRIPTOR, new PropertyDescriptor().setNameAs(FILE_LAST_MODIFIED_TIME).setReadOnlyAs(true).setTypeAs("Date"))
		.addAdditiveController(PROPERTY_DESCRIPTOR, new PropertyDescriptor().setNameAs(FILE_LAST_ACCESS_TIME).setReadOnlyAs(true).setTypeAs("Date"))
		.addCategory("category.fileContainer");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		super.stop(bundleContext);
		INSTANCE = null;
	}

	@Override
	public void registerMessageBundle() throws Exception {
		// no messages yet
	}
	
	/**
	 * Setting/removing must be done from a try/finally block to make sure that 
	 * the request is cleared, i.e.
	 * 
	 * <pre>
	 * try {
	 * 	CorePlugin.getInstance().getRequestThreadLocal().set(request);
	 * 	
	 * 	// specific logic here
	 * 
	 * } finally {
	 * 	CorePlugin.getInstance().getRequestThreadLocal().remove()
	 * }
	 * </pre> 
	 * 
	 * @see FlowerMessageBrokerServlet
	 */
	public ThreadLocal<HttpServletRequest> getRequestThreadLocal() {
		return requestThreadLocal;
	}
	
}
