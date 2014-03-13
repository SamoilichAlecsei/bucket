package com.itmg.bucket.response;

import java.io.Serializable;

public class CategoryAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5761094324748815176L;
	private String localized_name;
	private String name;
	
	public String getLocalized_name() {
		return localized_name;
	}
	public void setLocalized_name(String localized_name) {
		this.localized_name = localized_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "CategoryAO [localized_name=" + localized_name + ", name="
				+ name + "]";
	}
}
