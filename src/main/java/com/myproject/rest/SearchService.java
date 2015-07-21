package com.myproject.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
		
		return getResponse()
				.entity(SearchManager.getSearchResult(searchString))
				.build();
	}
	
	@GET
	@Path("/searchsuggestions")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchSuggestiots(@QueryParam("userId") String userId,
			@QueryParam("searchString") String searchString ,@QueryParam("reportType") String reportType) throws IMApiServiceException {

		// Call class which would connect to DB and return aggregated search results resource
		List<SearchSuggest> searchSuggest = SearchManager.getSuggestions(searchString);

		return getResponse()
				.entity(searchSuggest)
				.build();

	}

}
