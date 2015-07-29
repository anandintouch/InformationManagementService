package com.myproject.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.myproject.manager.UserManager;

public class IMSService {
	
	protected Response validateToken(String token) {
		if (!UserManager.isTokenValid(token)) {
			
			return getResponse()
					.status(Response.Status.UNAUTHORIZED)
					.entity("{\"message\" :  \"Token is either invalid or has expired.\"}")
					.build();
		}
		
		return null;
	}	
	
	protected ResponseBuilder getResponse() {
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
		      	.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	}
}
