package com.myproject.rest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myproject.manager.ReportManager;


@Path("/reports")
public class ReportService extends IMSService {
	
	@GET
	@Path("/previewreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportPreview(@HeaderParam("Authorization") String token, @QueryParam("userId") String userId,
			@QueryParam("reportTitle") String title) {
		Response res = validateToken(token);
		if (null == res) {
			res = getResponse()
				.entity(ReportManager.getReportPreview(title))
				.build();
		}
		
		return res;
	}
	
	@PUT
	@Path("/likes/update")
	public Response increaseLikes(@HeaderParam("Authorization") String token, @MatrixParam("userId") String userId,
			@MatrixParam("reportTitle") String title, @MatrixParam("currentLikes") String likes) {
		
		Response res = validateToken(token);
		if (null == res) {
			res = getResponse()
				.entity(String.valueOf(ReportManager.increaseLikes(title, likes)))
				.build();
		}
		
		return res;
	}
}
