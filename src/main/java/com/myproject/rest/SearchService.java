package com.myproject.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myproject.SearchResultItem;
import com.myproject.SearchSuggest;
import com.myproject.manager.SearchManager;


@Path("/search")
public class SearchService {
	
	@GET
	@Path("/queries")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SearchResultItem> getSearchResults(@QueryParam("userId") String userId,
			@QueryParam("searchString") String searchString,@QueryParam("reportType") String reportType) {
		
		// Call class which would connect to DB and return aggregated search results resource
		List<SearchResultItem> searchResults = SearchManager.getSearchResult(searchString);
		
		//return Response.status(200).entity(searchResults.toString()).build();
		return searchResults;
	}
	
	@GET
	@Path("/querysuggestions")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SearchSuggest> getSearchSuggestiots(@QueryParam("userId") String userId,
			@QueryParam("searchString") String searchString ,@QueryParam("reportType") String reportType) {

		// Call class which would connect to DB and return aggregated search results resource
		List<SearchSuggest> searchSuggest = SearchManager.getSuggestions(reportType);
		
		//List<SearchSuggest> suggestions = new ArrayList<SearchSuggest>();
/*		
		suggestions.add(new SearchSuggest("simple", "long description", "qlk", 22));
		suggestions.add(new SearchSuggest("ergonomic", "long description1", "tab", 1));
		suggestions.add(new SearchSuggest("qwerty",  "long description2", "qlk", 2));
		suggestions.add(new SearchSuggest("azerty",  "long description3", "qlk", 222));
		suggestions.add(new SearchSuggest("qzerty",  "long description4", "tab", 221));
		suggestions.add(new SearchSuggest("dvorak",  "long description5", "msp", 224));
		suggestions.add(new SearchSuggest("colemak",  "long description6", "qlk", 223));
		suggestions.add(new SearchSuggest("minimak",  "long description7", "msp", 226));
		suggestions.add(new SearchSuggest("colman",  "long description8", "qlk", 227));
		suggestions.add(new SearchSuggest("neo",  "long description9", "qlk", 228));
		suggestions.add(new SearchSuggest("plover",  "long description0", "tab", 229));
		suggestions.add(new SearchSuggest("bepo",  "long description22", "tab", 2255));
		suggestions.add(new SearchSuggest("jcuken",  "long description33", "qlk", 220));*/
  	    
		//return Response.status(200).entity(suggestions.toString()).build();
		return searchSuggest;

	}

}
