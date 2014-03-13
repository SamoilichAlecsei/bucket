package com.itmg.bucket.response;

import java.io.Serializable;

public class WeatherData  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1144590476273466382L;
	private String location;
	private String degree;
	private String imgUrl;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	@Override
	public String toString() {
		return "WeatherData [location=" + location + ", degree=" + degree
				+ ", imgUrl=" + imgUrl + "]";
	}
}
