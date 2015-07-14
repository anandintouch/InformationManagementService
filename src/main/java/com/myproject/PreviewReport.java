package com.myproject;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PreviewReport {
	public String imageUrl;
	public String desc;
	public int authorCredit;
	public int buyerCredit;
	public int creditToRun;
	
	public PreviewReport(){
	}
	
	public PreviewReport(String url, String desc, int aCredit, int  bCredit, int c2run) {
		this.imageUrl = url;
		this.desc = desc;
		this.authorCredit = aCredit;
		this.buyerCredit = bCredit;
		this.creditToRun = c2run;
	}
}
