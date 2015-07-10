package com.myproject;

import java.util.List;

public class Workbook {

	String title;
	String siteId;
	String image;
	List<String> workbookName;
	
/*	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}*/

	public List<String> getWorkbookName() {
		return workbookName;
	}

	public void setWorkbookName(List<String> workbookName) {
		this.workbookName = workbookName;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	@Override
	public String toString() {
		return "Workbook [title=" + title + ", siteId=" + siteId +", workbookName="+workbookName.get(0)
				+"]";
	}

}
