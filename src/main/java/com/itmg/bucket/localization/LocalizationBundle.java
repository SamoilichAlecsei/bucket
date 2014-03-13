package com.itmg.bucket.localization;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LocalizationBundle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3845074471253289266L;
	
	private Map<String, String> bundle;
	private String country;
	
	public LocalizationBundle(String code, Map<String, String> bundle) {
		this(code);		
		this.bundle = bundle;
	}
	
	public LocalizationBundle(String countryCode) {
		this.country = countryCode;
		this.bundle = new HashMap<String, String>();
	}
	
	public void putValues(String code, String translatedValue) {
		getBundle().put(code, translatedValue);
	}
	
	public String localizationFor(String code) {
		return bundle.get(code);
	}
	
	public Map<String, String> getBundle() {
		return (bundle != null) ? bundle : new HashMap<String, String>();
	}
	
	public void setBundle(Map<String, String> bundle) {
		this.bundle = bundle;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "LocalizationBundle [bundle=" + bundle + ", country=" + country
				+ "]";
	}
}
