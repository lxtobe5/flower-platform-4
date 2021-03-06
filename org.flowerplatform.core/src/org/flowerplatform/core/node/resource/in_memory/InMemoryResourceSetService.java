package org.flowerplatform.core.node.resource.in_memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowerplatform.core.node.resource.ResourceSetService;
import org.flowerplatform.core.node.update.remote.Update;

/**
 * @author Mariana Gheorghe
 */
public class InMemoryResourceSetService extends ResourceSetService {

	private Map<String, ResourceSetInfo> resourceSetInfos = new HashMap<String, ResourceSetInfo>();

	@Override
	public String addToResourceSet(String resourceSet, String resourceUri) {
		ResourceSetInfo info = resourceSetInfos.get(resourceSet);
		if (info == null) {
			info = new ResourceSetInfo();
			resourceSetInfos.put(resourceSet, info);
		}
		if (!info.getResourceUris().contains(resourceUri)) {
			info.getResourceUris().add(resourceUri);
		}
		return resourceSet;
	}
	
	@Override
	public void removeFromResourceSet(String resourceSet, String resourceUri) {
		ResourceSetInfo info = resourceSetInfos.get(resourceSet);
		info.getResourceUris().remove(resourceUri);
		if (info.getResourceUris().isEmpty()) {
			resourceSetInfos.remove(resourceSet);
		}
	}

	@Override
	protected void doReload(String resourceSet) {
		ResourceSetInfo info = resourceSetInfos.get(resourceSet);
		info.getUpdates().clear();
		info.setLoadedTimestamp(new Date().getTime());
	}

	@Override
	public void addUpdate(String resourceSet, Update update) {
		logger.debug("Adding update {} for resource set {}", update, resourceSet);
		ResourceSetInfo info = resourceSetInfos.get(resourceSet);
		info.getUpdates().add(update);
	}

	@Override
	protected List<Update> getUpdates(String resourceSet) {
		ResourceSetInfo info = resourceSetInfos.get(resourceSet);
		return info.getUpdates();
	}

	@Override
	protected long getLoadedTimestamp(String resourceSet) {
		ResourceSetInfo info = resourceSetInfos.get(resourceSet);
		return info.getLoadedTimestamp();
	}

	@Override
	public List<String> getResourceSets() {
		return new ArrayList<>(resourceSetInfos.keySet());
	}

	@Override
	public List<String> getResourceUris(String resourceSet) {
		ResourceSetInfo resourceSetInfo = resourceSetInfos.get(resourceSet);
		if (resourceSetInfo == null) {
			return Collections.emptyList();
		}
		return resourceSetInfo.getResourceUris();
	}
	
}
