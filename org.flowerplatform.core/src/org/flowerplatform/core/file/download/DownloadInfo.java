/* license-start
 * 
 * Copyright (C) 2008 - 2013 Crispico Software, <http://www.crispico.com/>.
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
 * license-end
 */
package org.flowerplatform.core.file.download;

/**
 * @author Cristina Constantinescu
 */
public class DownloadInfo {

	public static final int FILE_TYPE = 0;
	public static final int ARCHIVE_TYPE = 1;

	private String path;
	private int type;
	private long timestamp;
	private String sessionId;
	
	public String getPath() {
		return path;
	}

	public DownloadInfo setPath(String path) {
		this.path = path;
		return this;
	}

	public int getType() {
		return type;
	}

	public DownloadInfo setType(int type) {
		this.type = type;
		return this;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public DownloadInfo setTimestamp(long timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public DownloadInfo setSessionId(String sessionId) {
		this.sessionId = sessionId;
		return this;
	}

	public String toString() {
		return String.format("%s[type=%s; path=%s; timestamp=%s]", getClass().getSimpleName(), getType() == FILE_TYPE ? "file" : "archive", getPath(), getTimestamp());
	}
}