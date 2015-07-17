package com.myproject.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.myproject.Image;
import com.myproject.Track;
import com.myproject.Workbook;

/**
 * 
 * @author anand
 *
 */

@Path("/json")
public class JSONService extends IMSService {

	@GET
	@Path("/track")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTrackInJSON() {

		Track track = new Track();
		track.setTitle("Tushion");
		track.setSinger("Metallica");

		return getResponse()
				.entity(track)
				.build();

	}
	
	@GET
	@Path("/workbooknames")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorkbookInJSON() {
		Demo dem = new Demo();
		Workbook workbook = dem.getWorkbookNames();
		
		//Workbook wb = new Workbook();
		workbook.setTitle("Workbooks");
		
		return getResponse()
				.entity(workbook)
				.build();
	}
	
	@GET
	@Path("/workbook")
	@Produces(MediaType.APPLICATION_JSON)
	public Image getWeorkbookPreviewImage() {
		Demo dem = new Demo();
		//String image = dem.getWorkbookImage();
		Image image = dem.getWorkbookImage();
		 /*Image im = new Image();
  	     im.setImageName("Test");
  	     im.setImage(image);*/
  	     
		//return Response.status(200).entity(image).build();
		return image;
	}
	
	@GET
	@Path("/dashboard")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDashboardPreview() {
		Demo dem = new Demo();
		String image = dem.getDashboard();
		
		//return Response.status(200).entity(image).build();
		return image;
	}
	
	@GET
	@Path("/token")
	@Produces(MediaType.APPLICATION_JSON)
	public String getToken() {
		Demo dem = new Demo();
		String image = dem.getToken();
		
		//return Response.status(200).entity(image).build();
		return image;
	}

	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTrackInJSON(Track track) {

		String result = "Track saved : " + track;
		return Response.status(201).entity(result).build();
		
	}
	
}