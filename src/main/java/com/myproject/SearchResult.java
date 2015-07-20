package com.myproject;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResult {
	public List<SearchResultItem> searchResultItem;
	public SearchFilters searchFiletrs;
	
	
	public List<SearchResultItem> getSearchResultItem() {
		return searchResultItem;
	}
	public void setSearchResultItem(List<SearchResultItem> searchResultItem) {
		this.searchResultItem = searchResultItem;
	}
	

}
