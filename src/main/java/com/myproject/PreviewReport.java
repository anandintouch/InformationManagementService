package com.myproject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PreviewReport {
	public List<PreviewReportItem> views = new ArrayList<PreviewReportItem>();
	public String runs;
	public String subscribers;
	public String kits;
	public String favorites;
	public int comment;

	
	public PreviewReport(){
	}
	
	public PreviewReport(String runs,String subscribers,String kits,String favorites,
			int comment) {
		this.runs = runs;
		this.subscribers = subscribers;
		this.kits = kits;
		this.favorites = favorites;
		this.comment = comment;
	}
	
	public static class PreviewReportItem {
		public String imageUrl;
		public String desc;
		
		public PreviewReportItem() {
		}
	
		public PreviewReportItem(String url, String desc) {
			this.imageUrl = url;
			this.desc = desc;
		}
	}
}
