package org.flowerplatform.freeplane.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.flowerplatform.core.CorePlugin;
import org.flowerplatform.core.node.remote.Node;
import org.freeplane.features.map.MapModel;
import org.freeplane.features.styles.IStyle;
import org.freeplane.features.styles.MapStyleModel;

/**
 * @author Cristina Constantinescu
 */
public class MindMapServiceRemote {

	public List<String> getStyles(String fullNodeId) {		
		MapModel mapModel = (MapModel) CorePlugin.getInstance().getResourceService().getRawResourceData(new Node(fullNodeId).getResource());
			
		Set<IStyle> styles = MapStyleModel.getExtension(mapModel).getStyles();
		if (styles == null) {	
			return null;
		}
		List<String> children = new ArrayList<String>();
		for(IStyle style : styles) {
			children.add(style.toString());
		}		
		return children;		
	}
	
}