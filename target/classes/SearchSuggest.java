package com.hp.ssp.services;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchSuggest {
	public int id;
	public String name;
	public String desc;
 	public String type;
 	
 	public SearchSuggest() {
		// TODO Auto-generated constructor stub
	}
 	
 	public SearchSuggest(String name, String desc, String type, int id) {
 		this.name = name;
 		this.desc = desc;
 		this.type = type;
 		this.id = id;
 	}
}
