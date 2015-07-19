package com.myproject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResult {
	public SearchResultItem searchResultItem;
	public SearchFilters searchFiletrs;
	
	
	public SearchResultItem getSearchResultItem() {
		return searchResultItem;
	}
	public void setSearchResultItem(SearchResultItem searchResultItem) {
		this.searchResultItem = searchResultItem;
	}
	

}
