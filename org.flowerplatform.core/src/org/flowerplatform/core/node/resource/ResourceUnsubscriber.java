package org.flowerplatform.core.node.resource;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.flowerplatform.core.CorePlugin;
import org.flowerplatform.core.FlowerProperties;
import org.flowerplatform.core.node.remote.ServiceContext;
import org.flowerplatform.core.session.SessionService;

/**
 * Scheduled task that periodically checks if the subscribed clients are still active.
 * If a resource was not pinged recently, then all the clients subscribed are forcefully 
 * unsubscribed from the resource; when all the clients are removed, the resource will
 * be unloaded.
 * 
 * @author Mariana Gheorghe
 */
public class ResourceUnsubscriber extends TimerTask {

	protected static final String PROP_RESOURCE_UNSUBSCRIBER_DELAY = "resourceUnsubscriberDelay"; 
	protected static final String PROP_DEFAULT_RESOURCE_UNSUBSCRIBER_DELAY = "600000"; 
		
	public ResourceUnsubscriber() {
		super();
		CorePlugin.getInstance().getFlowerProperties().addProperty(new FlowerProperties.AddIntegerProperty(PROP_RESOURCE_UNSUBSCRIBER_DELAY, PROP_DEFAULT_RESOURCE_UNSUBSCRIBER_DELAY));
	}

	@Override
	public void run() {
		long now = new Date().getTime();
		ResourceService service = CorePlugin.getInstance().getResourceService();
		List<String> resourceUris = service.getResources();
		for (String resourceUri : resourceUris) {
			long lastPing = service.getUpdateRequestedTimestamp(resourceUri);
			if (now - lastPing > Long.valueOf(CorePlugin.getInstance().getFlowerProperties().getProperty(PROP_RESOURCE_UNSUBSCRIBER_DELAY))) {
				List<String> sessionIds = service.getSessionsSubscribedToResource(resourceUri);
				for (int i = sessionIds.size() - 1; i >= 0; i--) {
					String sessionId = sessionIds.get(i);
					CorePlugin.getInstance().getResourceService().sessionUnsubscribedFromResource(sessionId, resourceUri,
							new ServiceContext<ResourceService>(CorePlugin.getInstance().getResourceService()));
					CorePlugin.getInstance().getSessionService().sessionUnsubscribedFromResource(sessionId, resourceUri,
							new ServiceContext<SessionService>(CorePlugin.getInstance().getSessionService()));
				}
			}
		}
	}
	
	public void start() {
		new Timer().schedule(this, 0, Long.valueOf(CorePlugin.getInstance().getFlowerProperties().getProperty(PROP_RESOURCE_UNSUBSCRIBER_DELAY)));
	}
	
}
