package org.flowerplatform.core.node.resource.in_memory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.flowerplatform.core.node.update.Command;
import org.flowerplatform.core.node.update.remote.Update;

/**
 * @author Cristina Constantinescu
 * @author Mariana Gheorghe
 */
public class ResourceNodeInfo {

	private Object rawResourceData;
	
	private String resourceCategory;
	
	private long loadedTimestamp;
	
	private List<String> sessions = new ArrayList<String>();
	
	private List<Update> updates = new ArrayList<Update>();

	/**
	 * @author Claudiu Matei
	 */
	private List<Command> commandStack = new ArrayList<Command>();
	
	/**
	 * @author Claudiu Matei
	 */
	private String commandToUndoId; 

	/**
	 * @author Claudiu Matei
	 */
	private String commandToRedoId; 
	
	private long updateRequestedTimestamp;

	public Object getRawResourceData() {
		return rawResourceData;
	}
	
	public void setRawResourceData(Object rawResourceData) {
		this.rawResourceData = rawResourceData;
		loadedTimestamp = new Date().getTime();
		updates.clear();
	}
	
	public String getResourceCategory() {
		return resourceCategory;
	}

	public void setResourceCategory(String resourceCategory) {
		this.resourceCategory = resourceCategory;
	}
	
	public long getLoadedTimestamp() {
		return loadedTimestamp;
	}

	public List<String> getSessions() {
		return sessions;
	}
	
	public List<Update> getUpdates() {
		return updates;
	}

	public List<Command> getCommandStack() {
		return commandStack;
	}

	public String getCommandToUndoId() {
		return commandToUndoId;
	}

	public void setCommandToUndoId(String commandToUndoId) {
		this.commandToUndoId = commandToUndoId;
	}
	
	public String getCommandToRedoId() {
		return commandToRedoId;
	}

	public void setCommandToRedoId(String commandToRedoId) {
		this.commandToRedoId = commandToRedoId;
	}

	public long getUpdateRequestedTimestamp() {
		return updateRequestedTimestamp;
	}
	
	public void setUpdateRequestedTimestamp(long updateRequestedTimestamp) {
		this.updateRequestedTimestamp = updateRequestedTimestamp;
	}
	
}
