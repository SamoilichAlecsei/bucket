package com.itmg.bucket.response;

import java.io.Serializable;
import java.util.List;

public class SearchResultAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -170850801157606659L;
	
	private String category;
	private String country;
	private String pageId;
	private int total;
	private List<NewsContent> searched_news;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<NewsContent> getSearched_news() {
		return searched_news;
	}
	public void setSearched_news(List<NewsContent> searched_news) {
		this.searched_news = searched_news;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((pageId == null) ? 0 : pageId.hashCode());
		result = prime * result
				+ ((searched_news == null) ? 0 : searched_news.hashCode());
		result = prime * result + total;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResultAO other = (SearchResultAO) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (pageId == null) {
			if (other.pageId != null)
				return false;
		} else if (!pageId.equals(other.pageId))
			return false;
		if (searched_news == null) {
			if (other.searched_news != null)
				return false;
		} else if (!searched_news.equals(other.searched_news))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SearchResultAO [category=" + category + ", country=" + country
				+ ", pageId=" + pageId + ", total=" + total
				+ ", searched_news=" + searched_news + "]";
	}
	
	
}
