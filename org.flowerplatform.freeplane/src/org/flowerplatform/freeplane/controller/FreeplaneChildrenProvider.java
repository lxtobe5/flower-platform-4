package org.flowerplatform.freeplane.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.flowerplatform.core.node.controller.ChildrenProvider;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.freeplane.FreeplanePlugin;
import org.flowerplatform.util.Pair;
import org.freeplane.features.attribute.Attribute;
import org.freeplane.features.attribute.NodeAttributeTableModel;
import org.freeplane.features.map.NodeModel;

/**
 * @author Cristina Constantinescu
 */
public class FreeplaneChildrenProvider extends ChildrenProvider {
			
	@Override
	public List<Pair<Node, Object>> getChildren(Node node) {
		NodeModel nodeModel = FreeplanePlugin.getInstance().getFreeplaneUtils().getNodeModel(node.getId());
		if (node.getId() == null) {
			return Collections.singletonList(new Pair<Node, Object>(getEmptyNode(nodeModel), nodeModel));
		}
		List<Pair<Node, Object>> children = new ArrayList<Pair<Node, Object>>();		
		for (NodeModel child : nodeModel.getChildren()) {
			children.add(new Pair<Node, Object>(getEmptyNode(child), child));
		}
			
		return children;		
	}
	
		
	public Node getEmptyNode(NodeModel nodeModel) {
		Node node = new Node();
		node.setId(nodeModel.createID());
		// get type from attributes table
		NodeAttributeTableModel attributeTable = (NodeAttributeTableModel) nodeModel.getExtension(NodeAttributeTableModel.class);
		if (attributeTable != null) {
			for (Attribute attribute : attributeTable.getAttributes()) {
				if (attribute.getName().equals("type")) {
					node.setType((String) attribute.getValue());
					break;
				}
			}
		}
		if (node.getType() == null) { 
			// no type provided, maybe this node is provided by a random .mm file, so set type to freeplaneNode
			node.setType(FreeplanePlugin.FREEPLANE_NODE_TYPE);
		}
		return node;
	}
	
//	public Node getNode(String nodeId) {		
//		return convert(getNodeModel(nodeId));
//	}
			
//	public void setBody(String nodeId, String newBodyValue) {
//		NodeModel nodeModel = getNodeModel(nodeId);
//		nodeModel.setText(newBodyValue);
//	}
//		
//	public Node addNode(String parentNodeId, String type) {
//		NodeModel parent = getNodeModel(parentNodeId);
//		NodeModel newNode = new NodeModel("", parent.getMap());
//		newNode.setLeft(false);
//		
//		parent.insert(newNode, parent.getChildCount());
//		
//		return convert(newNode);
//	}
//	
//	public void removeNode(String nodeId) {
//		NodeModel nodeModel = getNodeModel(nodeId);
//		nodeModel.removeFromParent();
//	}	
//		
//	public void moveNode(String nodeId, String newParentId, int newIndex) {
//		NodeModel nodeModel = getNodeModel(nodeId);
//		NodeModel newParentModel = getNodeModel(newParentId);
//		
//		if (newIndex != -1) {
//			if (newIndex == -2) {
//				newIndex = newParentModel.getChildCount();
//			}
//			NodeModel oldParent = nodeModel.getParentNode();
//			oldParent.remove(nodeModel);
//						
//			if (oldParent.equals(newParentModel) && newIndex > oldParent.getChildCount()) {
//				newIndex--;
//			}	
//			newParentModel.insert(nodeModel, newIndex);
//		}	
//	}
			
//	public List<Property> getPropertiesData(String nodeType) {
//		// TODO CC: temporary code (testing properties view)
//		List<Property> properties = new ArrayList<Property>();
//		properties.add(new Property().setNameAs("type").setReadOnlyAs(true));		
//		properties.add(new Property().setNameAs("id").setReadOnlyAs(true));	
//		properties.add(new Property().setNameAs("could_shape").setReadOnlyAs(false));	
//		properties.add(new Property().setNameAs("could_color"));		
//		return properties;	
//	}
//			
//	public void setProperty(String nodeId, String propertyName, String propertyValue) {
//		// TODO CC: temporary code (testing properties view)
//		NodeModel nodeModel = getNodeModel(nodeId);
//		if (propertyName.equals("could_shape")) {
//			CloudModel cloud = CloudModel.createModel(nodeModel);
//			switch (propertyValue) {
//				case "ARC": 
//					cloud.setShape(Shape.ARC);
//					break;
//				case "RECT": 
//					cloud.setShape(Shape.RECT);
//					break;
//				case "STAR": 
//					cloud.setShape(Shape.STAR);
//					break;
//			}
//		}
//	}
		
}
