package com.myproject;



import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResultItem {
	public String objectURL;
	public String objectStatus;
	public String objectIcon;
	public String objectPreview1;
	public String objectTitle;
	public String dateAuthored;
	public String previews;
	public int comments;
	public String likes;
	public boolean isFaved;
	public boolean isLiked;
	public boolean isSubscribed;
	
	
	public SearchResultItem(){
	}
	
	public SearchResultItem ( String objectURL, String objectStatus, String objectIcon, 
			   String objectPreview1,String objectTitle,String dateAuthored,String previews,
			   String likes) {

		this.objectURL = objectURL;
		this.objectStatus = objectStatus;
		this.objectIcon = objectIcon;
		this.objectPreview1= objectPreview1;
		this.objectTitle = objectTitle;
		this.dateAuthored = dateAuthored;
		this.previews = previews;
		this.likes = likes;
	
	}
	

/*	
	public void setCartInfo (String cartMsg, String lockedMsg) {
		this.addToCartMsg = cartMsg;
		this.lockedMsg = lockedMsg;
	}*/
}
