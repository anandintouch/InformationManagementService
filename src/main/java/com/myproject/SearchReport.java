package com.myproject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchReport {
	public String objectID;
	public String objectURL;
	public String objectStatus;
	public String objectIcon;
	public String objectPreview1;
	public String objectTitle;
	public String authorName;
	public String dateAuthored;
	public String previews;
	public String location;
	public int comments;
	public String likes;
	
	
	public SearchReport(){
	}
	
	public SearchReport ( String objectID, String objectURL, String objectStatus, String objectIcon, 
			   String objectPreview1,String objectTitle, String authorName, String dateAuthored,String previews,
			   String location, String likes) {

		this.objectID = objectID;
		this.objectURL = objectURL;
		this.objectStatus = objectStatus;
		this.objectIcon = objectIcon;
		this.objectPreview1= objectPreview1;
		this.objectTitle = objectTitle;
		this.authorName = authorName;
		this.dateAuthored = dateAuthored;
		this.location = location;
		this.previews = previews;
		this.likes = likes;
	
	}
	

/*	
	public void setCartInfo (String cartMsg, String lockedMsg) {
		this.addToCartMsg = cartMsg;
		this.lockedMsg = lockedMsg;
	}*/
}
