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
import com.myproject.exception.IMApiServiceException;
import com.myproject.manager.SearchManager;

/**
 * This class represents all the relevant Information mgmt related API calls for 
 * the Self service portal.
 * 
 * @author anand
 *
 */

@Path("/api")
public class SearchService extends IMSService {
	
	@GET
	@Path("/searchresults")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchResults(@QueryParam("userId") String userId,
			@QueryParam("searchString") String searchString,@QueryParam("reportType") String reportType) throws IMApiServiceException {
		
		// Call class which would connect to DB and return aggregated search results resource
		List<SearchResultItem> searchResults = SearchManager.getSearchResult(searchString);
		
		//return Response.status(200).entity(searchResults.toString()).build();

		return getResponse()
				.entity(searchResults)
				.build();
	}
	
	@GET
	@Path("/searchsuggestions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchSuggestiots(@QueryParam("userId") String userId,
			@QueryParam("searchString") String searchString ,@QueryParam("reportType") String reportType) throws IMApiServiceException {

		// Call class which would connect to DB and return aggregated search results resource
		List<SearchSuggest> searchSuggest = SearchManager.getSuggestions(searchString);

		//return Response.status(200).entity(suggestions.toString()).build();

		return getResponse()
				.entity(searchSuggest)
				.build();

	}

}
