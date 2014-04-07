/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico, <http://www.crispico.com/>.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details, at <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *   Crispico - Initial API and implementation
 *
 * license-end
 */

package org.flowerplatform.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Keeps a reference to all created <code>ScheduledExecutorService</code>s.
 * 
 * When the server stops it will ensure that these <code>ScheduledExecutorService</code>s
 * are also shutdown.
 * 
 * @author Sorin
 */
public class ScheduledExecutorServiceFactory {
	
	private Collection<ScheduledExecutorService> executorServices = Collections.synchronizedList(new ArrayList<ScheduledExecutorService>());

	public ScheduledExecutorService createScheduledExecutorService() {
		ScheduledExecutorService executorService =  Executors.newScheduledThreadPool(1);
		executorServices.add(executorService);
		return executorService;
	}

	public void dispose() {
		for (ScheduledExecutorService executorService : executorServices) { 
			executorService.shutdownNow();
		}
	}
}