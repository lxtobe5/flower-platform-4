package org.flowerplatform.freeplane;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.flowerplatform.core.NodePropertiesConstants;
import org.flowerplatform.core.node.remote.Node;
import org.flowerplatform.core.node.remote.Node;
import org.freeplane.features.attribute.Attribute;
import org.freeplane.features.attribute.NodeAttributeTableModel;
import org.freeplane.features.attribute.Attribute;
import org.freeplane.features.attribute.NodeAttributeTableModel;
import org.freeplane.features.map.MapModel;
import org.freeplane.features.map.MapWriter.Mode;
import org.freeplane.features.map.NodeModel;
import org.freeplane.features.mode.Controller;
import org.freeplane.features.url.UrlManager;
import org.freeplane.features.url.mindmapmode.MFileManager;
import org.freeplane.main.headlessmode.HeadlessMModeControllerFactory;

/**
 * @author Cristina Constantinescu
 */
public class FreeplaneUtils {

	// TODO CC: temporary code
	public static Map<String, MapModel> maps = new HashMap<String, MapModel>();
				
	// TODO CC: temporary code
	private static final String TEST_PATH = "D:/temp/FAP-FlowerPlatform4.mm";
			
	static {
		// configure Freeplane starter
		new FreeplaneHeadlessStarter().createController().setMapViewManager(new HeadlessMapViewController());		
		HeadlessMModeControllerFactory.createModeController();	
	}
	
	// TODO CC: temporary code
	private URL getTestingURL() {
		try {
			return new File(TEST_PATH).toURI().toURL();
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	public NodeModel getNodeModel(String nodeId) {		
		if (nodeId == null) {
			if (!maps.containsKey(getTestingURL().toString())) {
				try {
					load(null);
				} catch (Exception e) {	
					// TODO CC: To log
					e.printStackTrace();
				}
			}
			return maps.get(getTestingURL().toString()).getRootNode();
		} 
		return maps.get(getTestingURL().toString()).getNodeForID(nodeId);		
	}
	
	public Node getStandardNode(NodeModel nodeModel) {
		String type = null;
		String resource = null;
		
		// get type from attributes table
		NodeAttributeTableModel attributeTable = (NodeAttributeTableModel) nodeModel.getExtension(NodeAttributeTableModel.class);
		if (attributeTable != null) {
			for (Attribute attribute : attributeTable.getAttributes()) {
				if (attribute.getName().equals(NodePropertiesConstants.TYPE)) {
					type = (String) attribute.getValue();
					break;
				}
			}
		}
		if (type == null) { 
			// no type provided, maybe this node is provided by a random .mm file, so set type to freeplaneNode
			type = FreeplanePlugin.FREEPLANE_NODE_TYPE;			
		}
		
		// TODO CC: temporary code
		if (FreeplanePlugin.FREEPLANE_NODE_TYPE.equals(type)) {
			resource = "mm://path_to_resource";
		}
		return new Node(type, resource, nodeModel.createID(), nodeModel);
	}
	
	public void load(URL url) throws Exception {
		url = getTestingURL();
		
		InputStreamReader urlStreamReader = null;
		try {
			urlStreamReader = new InputStreamReader(url.openStream());
			
			MapModel newModel = new MapModel();			
			newModel.setURL(url);
				
			Controller.getCurrentModeController().getMapController().getMapReader().createNodeTreeFromXml(newModel, urlStreamReader, Mode.FILE);		
			maps.put(newModel.getURL().toString(), newModel);
		} finally {
			if (urlStreamReader != null) {
				urlStreamReader.close();
			}
		}
	}
		
	@SuppressWarnings("deprecation")
	public void save() throws IOException {
		MapModel newModel = maps.get(getTestingURL().toString());
		((MFileManager) UrlManager.getController()).writeToFile(newModel, newModel.getFile());		
	}
}
