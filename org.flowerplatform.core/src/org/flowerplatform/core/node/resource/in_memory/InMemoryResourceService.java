package org.flowerplatform.core.node.resource.in_memory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.flowerplatform.core.node.resource.IResourceHandler;
import org.flowerplatform.core.node.resource.ResourceService;
import org.flowerplatform.util.Utils;

/**
 * @author Mariana Gheorghe
 */
public class InMemoryResourceService extends ResourceService {

	protected Map<String, InMemoryResourceInfo> resourceInfos = new HashMap<String, InMemoryResourceInfo>();
	
	/**
	 * Load the resource on first subscription.
	 */
	@Override
	protected void doSessionSubscribedToResource(String sessionId, String resourceUri) {
		if (getSessionsSubscribedToResource(resourceUri).isEmpty()) {
			// load on first subscription
			logger.debug("First subscription; load resource {}", resourceUri);
			String scheme = Utils.getScheme(resourceUri);
			IResourceHandler resourceHandler = getResourceHandler(scheme);
			try {
				registerResourceData(resourceUri, resourceHandler.load(resourceUri));
			} catch (Exception e) {
				throw new RuntimeException("Error loading resource: " + resourceUri, e);
			}
		}
		
		InMemoryResourceInfo resourceInfo = resourceInfos.get(resourceUri);
		if (resourceInfo != null && !resourceInfo.getSessionIds().contains(sessionId)) {
			// add only if not yet added
			resourceInfo.getSessionIds().add(sessionId);
		}
	}
	
	/**
	 * Unload the resource on last unsubscription.
	 */
	@Override
	public void doSessionUnsubscribedFromResource(String sessionId, String resourceUri) {
		InMemoryResourceInfo resourceInfo = resourceInfos.get(resourceUri);
		if (resourceInfo != null) {
			resourceInfo.getSessionIds().remove(sessionId);
			if (resourceInfo.getSessionIds().isEmpty()) {
				// remove the info from the map if it was the last session
				resourceInfos.remove(resourceUri);
			}
		}
		
		if (getSessionsSubscribedToResource(resourceUri).isEmpty()) {
			// unload on last unsubscription
			logger.debug("Last unsubscription; unload resource {}", resourceUri);
			String scheme = Utils.getScheme(resourceUri);
			IResourceHandler resourceHandler = getResourceHandler(scheme);
			try {
				resourceHandler.unload(resourceUri);
			} catch (Exception e) {
				throw new RuntimeException("Error unloading resource: " + resourceUri, e);
			}
		}
	}
	
	@Override
	public Object getResourceData(String resourceUri) {
		InMemoryResourceInfo resourceInfo = resourceInfos.get(resourceUri);
		if (resourceInfo == null) {
//			throw new RuntimeException("Resource " + resourceUri + " is not loaded");
			// dirty is requested from getOrPopulateProperties() for root, repo
			// which are not registered
			return null;
		}
		return resourceInfo.getResourceData();
	}


	@Override
	public void registerResourceData(String resourceUri, Object resourceData) {
		InMemoryResourceInfo resourceInfo = resourceInfos.get(resourceUri);
		if (resourceInfo == null) {
			resourceInfo = new InMemoryResourceInfo();
			resourceInfos.put(resourceUri, resourceInfo);
		}
		resourceInfo.setResourceData(resourceData);
	}

	@Override
	public List<String> getResources() {
		return new ArrayList<>(resourceInfos.keySet());
	}

	@Override
	public List<String> getSessionsSubscribedToResource(String resourceUri) {
		InMemoryResourceInfo resourceInfo = resourceInfos.get(resourceUri);
		if (resourceInfo == null) {
			return Collections.emptyList();
		}
		return resourceInfo.getSessionIds();
	}

	@Override
	public long getUpdateRequestedTimestamp(String resourceNodeId) {
		return resourceInfos.get(resourceNodeId).getLastPing();
	}

	@Override
	public void setUpdateRequestedTimestamp(String resourceUri, long timestamp) {
		resourceInfos.get(resourceUri).setLastPing(timestamp);
	}

}
