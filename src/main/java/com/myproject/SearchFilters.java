package com.myproject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchFilters {
	
	public String authorName;
	public String dateAuthored;
	
	
	public SearchFilters(String authorName, String dateAuthored) {
		this.authorName = authorName;
		this.dateAuthored = dateAuthored;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getDateAuthored() {
		return dateAuthored;
	}
	public void setDateAuthored(String dateAuthored) {
		this.dateAuthored = dateAuthored;
	}
	

}
