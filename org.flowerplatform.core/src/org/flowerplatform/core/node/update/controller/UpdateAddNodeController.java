package org.flowerplatform.core.node.update.controller;

import static org.flowerplatform.core.CoreConstants.INSERT_BEFORE_NODE;
import static org.flowerplatform.core.CoreConstants.UPDATE_CHILD_ADDED;

import org.flowerplatform.core.CorePlugin;
import org.flowerplatform.core.CoreUtils;
import org.flowerplatform.core.ServiceContext;
import org.flowerplatform.core.node.controller.AddNodeController;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.update.remote.ChildrenUpdate;

public class UpdateAddNodeController extends AddNodeController {
		
	public UpdateAddNodeController() {
		// must be invoked last; otherwise the modification may not be fully/correctly recorded
		setOrderIndex(100000);
	}
	
	@Override
	public void addNode(Node node, Node child, ServiceContext context) {		
		Node resourceNode = CoreUtils.getResourceNode(node);
		Node insertBeforeNode = (Node)context.get(INSERT_BEFORE_NODE);
		if (resourceNode != null) {
			CorePlugin.getInstance().getResourceService()
				.addUpdate(resourceNode.getFullNodeId(), 
						new ChildrenUpdate()
							.setTypeAs(UPDATE_CHILD_ADDED)
							.setTargetNodeAs(child)
							.setFullTargetNodeAddedBeforeIdAs(insertBeforeNode != null ? insertBeforeNode.getFullNodeId() : null)
							.setFullNodeIdAs(node.getFullNodeId()));
			
		}
	}

}
