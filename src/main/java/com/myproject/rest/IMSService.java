package com.myproject.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

public abstract class IMSService {
	protected ResponseBuilder getResponse() {
		return Response.ok()
				.header("Access-Control-Allow-Origin", "*")
		      	.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	}
}
