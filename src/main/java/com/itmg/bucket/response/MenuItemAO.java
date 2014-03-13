package com.itmg.bucket.response;

import java.io.Serializable;

public class MenuItemAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 958318398118814267L;
	
	private String name;
	private String url;
	private String code;
	
	public String getCode() {
		int length = getUrl().length();
		int x = getUrl().lastIndexOf("/");
		return (x > 1) ? getUrl().substring(x+1, length) : getUrl();	
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "MenuItemAO [name=" + name + ", url=" + url + "]";
	}
}
