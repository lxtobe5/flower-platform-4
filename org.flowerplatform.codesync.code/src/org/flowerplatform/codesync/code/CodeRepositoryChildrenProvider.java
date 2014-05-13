package org.flowerplatform.codesync.code;

import java.util.Collections;
import java.util.List;

import org.flowerplatform.core.CoreConstants;
import org.flowerplatform.core.node.NodeService;
import org.flowerplatform.core.node.controller.ChildrenProvider;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.remote.ServiceContext;

/**
 * @author Cristian Spiescu
 */
public class CodeRepositoryChildrenProvider extends ChildrenProvider {

	public CodeRepositoryChildrenProvider() {
		super();
		setOrderIndex(-10000);
	}

	@Override
	public List<Node> getChildren(Node node, ServiceContext<NodeService> context) {
		return Collections.singletonList(
				new Node(CoreConstants.CODE_TYPE, CoreConstants.SELF_RESOURCE, "codesync.mm", null));
	}

	@Override
	public boolean hasChildren(Node node, ServiceContext<NodeService> context) {
		return true;
	}

}
