package org.flowerplatform.freeplane.controller;

import org.flowerplatform.core.CoreConstants;
import org.flowerplatform.core.ServiceContext;
import org.flowerplatform.core.node.controller.PropertySetter;
import org.flowerplatform.core.node.controller.PropertyValueWrapper;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.mindmap.MindMapConstants;
import org.freeplane.features.attribute.Attribute;
import org.freeplane.features.attribute.NodeAttributeTableModel;
import org.freeplane.features.map.NodeModel;

/**
 * @author Cristina Constantinescu
 */
public class PersistencePropertySetter extends PropertySetter {

	@Override
	public void setProperty(Node node, String property, PropertyValueWrapper wrapper, ServiceContext context) {
		if (context.getValue(CoreConstants.EXECUTE_ONLY_FOR_UPDATER)) {
			return;
		}
		
		NodeModel rawNodeData = ((NodeModel) node.getOrRetrieveRawNodeData());

		if (MindMapConstants.FREEPLANE_PERSISTENCE_NODE_TYPE_KEY.equals(property)) {
			throw new RuntimeException(String.format("Property with name %s shouldn't be set because it's reserved. Please use another key!", property));
		}
		
		// persist the property value in the attributes table
		NodeAttributeTableModel attributeTable = (NodeAttributeTableModel) rawNodeData.getExtension(NodeAttributeTableModel.class);		
		if (attributeTable == null) {
			attributeTable = new NodeAttributeTableModel(rawNodeData);
			rawNodeData.addExtension(attributeTable);
		}		
		
		boolean set = false;
		for (Attribute attribute : attributeTable.getAttributes()) {
			if (attribute.getName().equals(property)) {
				// there was already an attribute with this value; overwrite it
				attribute.setValue(wrapper.getPropertyValue());
				set = true;
				break;
			}
		}
		if (!set) {
			// new attribute; add it
			attributeTable.getAttributes().add(new Attribute(property, wrapper.getPropertyValue()));
		}
		rawNodeData.getMap().setSaved(false);
		
		// set the property on the node instance too
		node.getOrPopulateProperties().put(property, wrapper.getPropertyValue());
	}

	@Override
	public void unsetProperty(Node node, String property, ServiceContext context) {
		NodeModel rawNodeData = ((NodeModel) node.getOrRetrieveRawNodeData());
		
		if (MindMapConstants.FREEPLANE_PERSISTENCE_NODE_TYPE_KEY.equals(property)) {
			throw new RuntimeException(String.format("Property with name %s shouldn't be un-set!", property));
		}
		
		// remove attribute from the attributes table
		NodeAttributeTableModel attributeTable = (NodeAttributeTableModel) rawNodeData.getExtension(NodeAttributeTableModel.class);	
		if (attributeTable != null) {
			for (Attribute attribute : attributeTable.getAttributes()) {
				if (attribute.getName().equals(property)) {
					attributeTable.getAttributes().remove(attribute);
					rawNodeData.getMap().setSaved(false);
					break;
				}
			}
		}
		
		// remove the property from the node instance too
		node.getOrPopulateProperties().remove(property);
	}
	
}
