package com.myproject;



import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResultItem {
	private String id;
	public String objectURL;
	public String objectStatus;
	public String objectIcon;
	public String objectPreview1;
	public String objectTitle;
/*	public String authorName;
	public String dateAuthored;*/
	public String previews;
	public int comments;
	public String likes;
	
	public SearchFilters searchFiletrs;
/*	public boolean isFaved;
	public boolean isLiked;
	public boolean isSubscribed;*/


	public SearchResultItem(){
	}
	
	public SearchResultItem (String id, String objectURL, String objectStatus, String objectIcon, 
			   String objectPreview1,String objectTitle, String authorName, String dateAuthored,String previews,
			   String likes) {

		this.objectURL = objectURL;
		this.objectStatus = objectStatus;
		this.objectIcon = objectIcon;
		this.objectPreview1= objectPreview1;
		this.objectTitle = objectTitle;
/*		this.authorName = authorName;
		this.dateAuthored = dateAuthored;*/
		this.previews = previews;
		this.likes = likes;
		this.id = id;
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public SearchFilters getSearchFiletrs() {
		return searchFiletrs;
	}

	public void setSearchFiletrs(SearchFilters searchFiletrs) {
		this.searchFiletrs = searchFiletrs;
	}
/*	
	public void setCartInfo (String cartMsg, String lockedMsg) {
		this.addToCartMsg = cartMsg;
		this.lockedMsg = lockedMsg;
	}*/
}
