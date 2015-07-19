package com.myproject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PreviewReport {
	public List<PreviewReportItem> views = new ArrayList<PreviewReportItem>();
	public int authorCredit;
	public int buyerCredit;
	public int creditToRun;
	
	public PreviewReport(){
	}
	
	public PreviewReport(int aCredit, int  bCredit, int c2run) {
		this.authorCredit = aCredit;
		this.buyerCredit = bCredit;
		this.creditToRun = c2run;
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
