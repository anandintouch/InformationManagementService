package com.myproject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchSuggest {

	public String objectID;
 	public String objectTitleShort;
 	public String objectDescriptionShort;
 	public String objectIcon;
 	
 	public SearchSuggest() {
		// TODO Auto-generated constructor stub
	}
 	
 	public SearchSuggest(String objectID,String objectDescriptionShort,
 			String objectTitleShort,String objectIcon) {
 		this.objectID = objectID;
 		this.objectDescriptionShort = objectDescriptionShort;
 		this.objectTitleShort = objectTitleShort;
 		this.objectIcon = objectIcon;

 	}
}
