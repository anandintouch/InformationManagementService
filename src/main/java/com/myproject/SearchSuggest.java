package com.myproject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchSuggest {

 	public String objectTitleShort;
 	public String objectDescriptionShort;
 	public String objectIcon;
 	
 	public SearchSuggest() {
		// TODO Auto-generated constructor stub
	}
 	
 	public SearchSuggest(String objectDescriptionShort,
 			String objectTitleShort,String objectIcon) {
 		this.objectDescriptionShort = objectDescriptionShort;
 		this.objectTitleShort = objectTitleShort;
 		this.objectIcon = objectIcon;
 	}
}
