package org.flowerplatform.core.repository;

import org.flowerplatform.core.CoreConstants;
import org.flowerplatform.core.node.NodeService;
import org.flowerplatform.core.node.controller.IPropertiesProvider;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.remote.ServiceContext;
import org.flowerplatform.util.controller.AbstractController;

/**
 * @author Sebastian Solomon
 */
public class RootPropertiesProvider extends AbstractController implements IPropertiesProvider {

	@Override
	public void populateWithProperties(Node node, ServiceContext<NodeService> context) {
		node.getProperties().put(CoreConstants.NAME, "root");
	}

}
