package com.myproject.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myproject.PreviewReport;
import com.myproject.manager.ReportManager;


@Path("/reports")
public class ReportService extends IMSService {
	
	@GET
	@Path("/previewreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportPreview(@QueryParam("userId") String userId,
			@QueryParam("reportTitle") String title) {
		// Call class which would connect to DB and return aggregated search results resource
		//return ReportManager.getReportPreview(title);
		
		return getResponse()
				.entity(ReportManager.getReportPreview(title))
				.build();
	}
}
