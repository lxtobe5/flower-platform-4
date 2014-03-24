package org.flowerplatform.freeplane.controller;

import org.flowerplatform.core.CorePlugin;
import org.flowerplatform.core.node.controller.RawNodeDataProvider;
import org.flowerplatform.core.node.remote.Node;
import org.freeplane.features.map.MapModel;
import org.freeplane.features.map.NodeModel;

/**
 * @author Mariana Gheorghe
 */
public class FreeplaneResourceRawNodeDataProvider extends RawNodeDataProvider<NodeModel> {

	@Override
	public NodeModel getRawNodeData(Node node) {
		MapModel model = (MapModel) CorePlugin.getInstance().getResourceInfoService()
				.getRawResourceData(node.getFullNodeId());
		// return the root node for the resource
		return model.getRootNode();
	}

}
