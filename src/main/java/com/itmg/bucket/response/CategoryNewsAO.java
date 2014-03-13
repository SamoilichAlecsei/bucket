package com.itmg.bucket.response;

import java.io.Serializable;

public class CategoryNewsAO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -953298919141976848L;
	private String title;
	private String url;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "CategoryNewsAO [title=" + title + ", url=" + url + "]";
	}	
}
