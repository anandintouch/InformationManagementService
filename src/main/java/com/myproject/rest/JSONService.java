package com.myproject.rest;

import java.util.List;

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

@Path("/json")
public class JSONService {

	@GET
	@Path("/track")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getTrackInJSON() {

		Track track = new Track();
		track.setTitle("Tushion");
		track.setSinger("Metallica");

		return track;

	}
	
	@GET
	@Path("/workbooknames")
	@Produces(MediaType.APPLICATION_JSON)
	public Workbook getWorkbookInJSON() {
		Demo dem = new Demo();
		Workbook workbook = dem.getWorkbookNames();
		
		//Workbook wb = new Workbook();
		workbook.setTitle("Workbooks");
		

		return workbook;

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