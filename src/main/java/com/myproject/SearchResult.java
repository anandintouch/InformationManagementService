package com.myproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SearchResult {
	public List<SearchReport> resultItems = new ArrayList<SearchReport>();
	//filter
	public Map<String, Integer> locations = new HashMap<String, Integer>();
	public Map<String, Integer> authors = new HashMap<String, Integer>();
	public Map<String, Integer> timeFilter = new HashMap<String, Integer>();
}
