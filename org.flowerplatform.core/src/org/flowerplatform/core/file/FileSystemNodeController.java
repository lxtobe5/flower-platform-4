package org.flowerplatform.core.file;

import static org.flowerplatform.core.CoreConstants.FILE_SYSTEM_NODE_TYPE;
import static org.flowerplatform.core.CoreConstants.NAME;

import org.flowerplatform.core.node.NodeService;
import org.flowerplatform.core.node.controller.IPropertiesProvider;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.remote.ServiceContext;
import org.flowerplatform.util.controller.AbstractController;

public class FileSystemNodeController extends AbstractController implements IPropertiesProvider {

	public void populateWithProperties(Node node, ServiceContext<NodeService> context) {
		node.getProperties().put(NAME, FILE_SYSTEM_NODE_TYPE);
	}
	
}